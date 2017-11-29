#!/usr/bin/python  
#-*- coding: utf-8 -*-

import re
import sqlite3
import urllib
import urllib.request
import socket
import ssl
import os

import queue
import threading
from threading import Thread
import time
import json
import sys, getopt
import base64
import time
from bs4 import BeautifulSoup

# 取消验证ssl中域名与证书一致
# https://stackoverflow.com/questions/28768530/certificateerror-hostname-doesnt-match
# import ssl
# ssl.match_hostname = lambda cert, hostname: True

class gfwlist2web:
    gfwlist_txt = 'gfwlist.txt'
    gfwlist_db  = 'gfwlist.db'
    # http_proxy = {'http': '127.0.0.1:1087'}
    http_proxy = None
    # 网址正则
    pattern = re.compile("([a-zA-Z0-9-]+\.)+([a-zA-Z0-9-_/])+$")
    static_id = 0
    json_file = "./gfwweb.json"
    gfwlist_http = "https://raw.githubusercontent.com/gfwlist/gfwlist/master/gfwlist.txt"

    def __init__(self, gfwlist_file = None, gfwlist_database = None, proxy = None):
        if gfwlist_file:
            self.gfwlist_txt = gfwlist_file
        else :
            filemt= time.localtime(os.stat(self.gfwlist_txt).st_mtime)
            print("------更新gfwlist------")
            print(time.strftime("本地 gfwlist.txt 文件更新时间 %Y-%m-%d", filemt))
            print(time.strftime("当前时间 %Y-%m-%d %H:%M:%S", time.localtime()) )
            if time.strftime("%Y-%m-%d" , filemt) == time.strftime("%Y-%m-%d" , time.localtime()):
                print("已经更新过gfwlist，跳过更新")
            else :
                print("下载gfwlist.txt")
                err_count = 4
                while err_count > 0:
                    try:
                        opener = urllib.request.build_opener()
                        response = opener.open(self.gfwlist_http, timeout=6)
                        raw = response.read().decode('utf-8')
                        # print(base64.b64decode(raw))
                        print("下载成功")
                        with open(self.gfwlist_txt, "wb") as f:
                            f.write(base64.b64decode(raw))
                        break
                    except Exception as error:
                        err_count -= 1
                        if err_count > 0 :
                            print("下载失败，正在重试")
                if err_count == 0:
                    print("下载失败，使用自带gfwlist.txt") 

        if gfwlist_database:
            self.gfwlist_db = gfwlist_database
        if proxy:
            self.http_proxy = proxy
        self.conn = sqlite3.connect(self.gfwlist_db)

    def createDatabase(self):
        print("Open database")
        try:
            self.conn.execute("DROP TABLE GFWLIST")
            self.conn.commit()
        except sqlite3.OperationalError:
            pass
        cursor = self.conn.cursor()
        cursor.execute("""
        CREATE TABLE GFWLIST
        (
        ID INT PRIMARY KEY NOT NULL,
        RAW             CHAR(200)   NOT NULL,
        IF_ONELINE      INT         NOT NULL,
        IF_TWOLINE      INT         NOT NULL,
        IF_AT           INT         NOT NULL,
        IF_STAR         INT         NOT NULL,
        DIRECTGUESS     CHAR(200),
        GOOGLEGUESS     CHAR(200),
        VERIFYWEB       CHAR(200),
        CONN_INFO       CHAR(200)
        );        
        """)
        print("create database")
        self.conn.commit()

    def addHttp(self, url):
        if "www" in url:
            return "http://" + url
        else :
            return "http://www." + url

    def insertBasicInfo(self, RAW, IF_ONELINE, IF_TWOLINE, IF_AT, IF_STAR):
        self.conn.execute("INSERT INTO GFWLIST (ID, RAW, IF_ONELINE, IF_TWOLINE, IF_AT, IF_STAR) \
        VALUES (%d, '%s', %d, %d, %d, %d )" % (self.static_id, RAW, IF_ONELINE, IF_TWOLINE, IF_AT, IF_STAR) )
        self.static_id += 1

    def directGuessFromDatabase(self):
        c = self.conn.execute("SELECT ID, RAW, IF_ONELINE, IF_TWOLINE from GFWLIST WHERE IF_STAR IS 0 AND IF_AT IS 0")
        # 处理非* 黑名单
        for row in c:
            ID, RAW, IF_ONELINE, IF_TWOLINE = row
            if IF_ONELINE == 1:
                self.conn.execute("UPDATE GFWLIST set DIRECTGUESS = '%s' where ID=%d" % (RAW[1:], ID) )
            if IF_TWOLINE == 1:
                url_match = self.pattern.search(RAW[2:])
                if url_match:
                    self.conn.execute("UPDATE GFWLIST set DIRECTGUESS = '%s' where ID=%d" % (self.addHttp(url_match.group()), ID) )
            if IF_ONELINE == 0 and IF_TWOLINE ==0:
                url_match = self.pattern.search(RAW)
                if url_match:
                    self.conn.execute("UPDATE GFWLIST set DIRECTGUESS = '%s' where ID=%d" % (self.addHttp(url_match.group()), ID) )
        # self.conn.commit()
        # 处理非* 白名单
        c = self.conn.execute("SELECT ID, RAW, IF_ONELINE, IF_TWOLINE from GFWLIST WHERE IF_STAR IS 0 AND IF_AT IS 1")
        for row in c:
            ID, RAW, IF_ONELINE, IF_TWOLINE = row
            if IF_ONELINE == 1:
                url_match = self.pattern.search(RAW[3:])
                if url_match:
                    self.conn.execute("UPDATE GFWLIST set DIRECTGUESS = '%s' where ID=%d" % (self.addHttp(url_match.group()), ID) )
            if IF_TWOLINE == 1:
                url_match = self.pattern.search(RAW[4:])
                if url_match:
                    self.conn.execute("UPDATE GFWLIST set DIRECTGUESS = '%s' where ID=%d" % (self.addHttp(url_match.group()), ID) )
        self.conn.commit()  

    def readGfwlistToDatabase(self, gfwlist_file = None):
        if not gfwlist_file:
            gfwlist_file = self.gfwlist_txt
        with open(gfwlist_file, "r") as f:
            for line in f:
                raw = line[:-1]
                if_star = 0
                if_twoline = 0
                if_oneline = 0
                if_at = 0
                if line == "\n" or "[AutoProxy" in line:
                    # 过滤空白行 和 第一行
                    continue
                if line[0:1] == "!":
                    # 这部分是注释，直接忽略
                    continue
                if "*" in line:
                    if_star = 1
                if "||" in line:
                    if_twoline = 1
                if "|" in line and "||" not in line:
                    if_oneline = 1
                if line[0:2] == "@@":
                    if_at = 1
                self.insertBasicInfo(raw, if_oneline, if_twoline, if_at, if_star)
            self.conn.commit()

    def do_job(self):
        while True:
            row = self.queue.get()
            id = row[0]
            url = row[2]
            if self.http_proxy:
                proxy = urllib.request.ProxyHandler(self.http_proxy)
                opener = urllib.request.build_opener(proxy)
            else :
                opener = urllib.request.build_opener()
            opener.addheaders = [('User-agent', 'Mozilla/5.0 (X11; U; Windows NT 6; en-US) AppleWebKit/534.12 (KHTML, like Gecko) Chrome/9.0.587.0 Safari/534.12'),('connection','keep-alive'),('Accept-Encoding', 'gzip')]
            # print('url %s, curent: %s' % (url, threading.current_thread()))
            # print(row)
            retryTime=0
            while retryTime < 2:
                try:
                    if retryTime == 1:
                        url = url.replace("www.","")
                        response = opener.open(url, timeout=6)
                        self.threadResultDict[id] = "OK but remove www."
                        if row[0] % 10 == 0 :
                            print(row[0], "OK but remove www.")
                    else:
                        response = opener.open(url, timeout=6)
                        self.threadResultDict[id] = "OK"
                        if row[0] % 10 == 0 : 
                            print(row[0], "OK")
                    break
                except (socket.timeout , urllib.error.URLError) as error:
                    if row[0] % 10 == 0 :
                        print(row[0], str(error))
                    self.threadResultDict[id] = str(error)
                    retryTime+=1
                except ssl.CertificateError as error:
                    if row[0] % 10 == 0 :
                        print(row[0], str(error))
                    self.threadResultDict[id] = str(error)
                    retryTime+=1
                except Exception as error:
                    if row[0] % 10 == 0 :
                        print(row[0], str(error))
                    self.threadResultDict[id] = str(error)
                    retryTime+=1
            self.queue.task_done()

    def testConnect(self, max_count = None, max_thread = None):
    # 创建线程池
        print("------开始测试------")
        self.threadResultDict = dict()
        self.queue = queue.Queue()
        if max_thread is None:
            max_thread = 256
        for i in range(max_thread):
            t = Thread(target=self.do_job)
            t.daemon=True # 设置线程daemon  主线程退出，daemon线程也会推出，即时正在运行
            t.start()

        c = self.conn.execute("SELECT ID, RAW, DIRECTGUESS from GFWLIST WHERE DIRECTGUESS IS NOT NULL")
        count = 0
        for row in c:
            count += 1
            if testCount and count > testCount:
                break
            self.queue.put(row)

        self.queue.join()

        for k,v in self.threadResultDict.items():
            try:
                self.conn.execute("""UPDATE GFWLIST set CONN_INFO = "%s" where ID=%d""" % (v, k) )
            except sqlite3.OperationalError as e:
                print(v,k)
        self.conn.commit()

    def setVerifyToDatabase(self):
        c = self.conn.execute("SELECT ID, DIRECTGUESS, CONN_INFO, IF_AT from GFWLIST WHERE CONN_INFO IS NOT NULL")
        for row in c:
            ID, DIRECTGUESS, CONN_INFO, IF_AT = row
            web = ''
            if CONN_INFO == "OK":
                web = DIRECTGUESS
            if CONN_INFO == "OK but remove www.":
                web = DIRECTGUESS.replace("www.","")
            if web:
                self.conn.execute("""UPDATE GFWLIST set VERIFYWEB = "%s" where ID=%d""" % (web, ID) )
        self.conn.commit()

    def writeJsonFromDatabase(self,file = None):
        if file is None:
            file = self.json_file
        c = self.conn.execute("SELECT VERIFYWEB, IF_AT from GFWLIST WHERE VERIFYWEB IS NOT NULL")
        json_list=list()
        for row in c:
            VERIFYWEB, IF_AT = row
            _dict = dict()
            _dict["type"] = 'w' if IF_AT else 'b'
            _dict["web"] = VERIFYWEB
            json_list.append(_dict)
        with open(file, "w") as f:
            f.write(json.dumps(json_list, indent=4))    
        print("------json文件已生成------") 

    @staticmethod
    def alexaSpider(alexaweb):
        json_list=list()
        opener = urllib.request.build_opener()
        response = opener.open(alexaweb, timeout=6)
        raw = response.read().decode('utf-8')
        soup = BeautifulSoup(raw, "lxml")
        tags = soup.find_all('div', class_="rowbox")
        for tag in tags:
            alla = tag.find_all('a')
            for a in alla:
                if 'chinaz' not in a['href']:
                    _dict = dict()
                    _dict["type"] = 'b' 
                    _dict["web"] = a['href']
                    json_list.append(_dict)
                    print(a['href'])
        return json_list

    def writeAlexaJson(self, file = None):
        if file is None:
            return
        print("-----开始获取Alexa排名-----")
        json_list = self.alexaSpider("http://alexa.chinaz.com/Country/index_US.html")
        json_list += self.alexaSpider("http://alexa.chinaz.com/Country/index_US_2.html")
        json_list += self.alexaSpider("http://alexa.chinaz.com/Country/index_US_3.html")
        json_list += self.alexaSpider("http://alexa.chinaz.com/Country/index_US_4.html")
        with open(file, "w") as f:
            f.write(json.dumps(json_list, indent=4))    
        print("------json文件已生成------")

if __name__ == '__main__':
    

    gfwlist_file = None
    json_file = None
    proxy = None
    testCount = None
    thread = None
    alexa = None
    print("参数 -i 输入文件 -o 输出文件 -p 代理端口 -c 输出数量 -t 线程数量 -h 帮助 -a 输出Alexa排名文件")
    try:
        options,args = getopt.getopt(sys.argv[1:],"hi:o:p:c:t:a:", ["help","input=","output="])
    except getopt.GetoptError:
        sys.exit()
    for name,value in options:
        if name in ("-h","--help"):
            print("示例：gfwlist2web.py -i ./gfwlist -o ./json")
            print("-i 不输入默认使用网络gfwlist，无法下载则使用本地gfwlist")
            print("-c 不输入默认输出所有http")
            print("-t 不输入默认线程数量256")
            print("-p 不输入默认代理不设置")
            print("-o 不输入默认该目录下的gfwweb.json")
            print("-a 不输入默认不输出Alexa排名")
        if name in ("-i","--input"):
            gfwlist_file = value
            print("使用输入文件", value)
        if name in ("-o","--output"):
            json_file = value
            print("输出文件路径", value)
        if name in ("-p"):
            port = value
            proxy = {'http': '127.0.0.1:%d' % int(port)}
            print("代理端口号" , port)
        if name in ("-c"):
            testCount = int(value)
            print("只测试前%d个" % testCount)
        if name in ("-t"):
            thread = int(value)
            print("线程数量设置为%d个" % thread)
        if name in ("-a"):
            alexa = value
            print("Alexa排名文件路径%s" % alexa)
        

    g = gfwlist2web(gfwlist_file = gfwlist_file, proxy = proxy)
    g.writeAlexaJson(alexa)
    g.createDatabase()
    g.readGfwlistToDatabase()
    g.directGuessFromDatabase()
    g.testConnect(max_count = testCount, max_thread = thread)
    g.setVerifyToDatabase()
    g.writeJsonFromDatabase(json_file)

    os.remove(g.gfwlist_db)
