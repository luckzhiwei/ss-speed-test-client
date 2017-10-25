package com.vecent.ssspeedtest.controller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.vecent.ssspeedtest.R;
import com.vecent.ssspeedtest.util.LogUtil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhiwei on 2017/10/24.
 */

public class TestSSLocalActivity extends Activity {


    private Button ssproxyTestBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_ss_local_layout);
        final List<String> cmd = new ArrayList<>();
        cmd.add(getApplicationInfo().nativeLibraryDir + "/libss-local.so");
        cmd.add("-u");
        cmd.add("-b");
        cmd.add("127.0.0.1");
        cmd.add("-l");
        cmd.add("1080");
        cmd.add("-t");
        cmd.add("600");
        cmd.add("-s");
        cmd.add("hk-01.flashss.win");
        cmd.add("-p");
        cmd.add("49414");
        cmd.add("-m");
        cmd.add("aes-256-cfb");
        cmd.add("-k");
        cmd.add("Ln120190201");
        String str1 = "";
        for (String str : cmd) {
            str1 += str + " ";
        }
        LogUtil.logDebug(getClass().getName(), str1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessBuilder processBuilder = new ProcessBuilder().command(cmd);
                    Process p = processBuilder.start();
                    LogUtil.logDebug(getClass().getName(), "RET IS " + p.waitFor() + "");
                } catch (IOException e) {

                } catch (InterruptedException e) {

                }
            }
        }).start();
        this.ssproxyTestBtn = this.findViewById(R.id.proxy_test_speed_btn);
        this.ssproxyTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            SocketAddress addr = new InetSocketAddress("0.0.0.0", 1080);
                            Proxy proxy = new Proxy(Proxy.Type.SOCKS, addr);
                            URL url = new URL("http://www.baidu.com");
                            URLConnection conn = url.openConnection(proxy);
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            String buf = "";
                            StringBuffer stringBuffer = new StringBuffer();
                            while ((buf = bufferedReader.readLine()) != null) {
                                stringBuffer.append(buf);
                            }
                            bufferedReader.close();
                            LogUtil.logDebug(getClass().getName(), "proxy result is" + stringBuffer.toString());
                            bufferedReader.close();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                            LogUtil.logDebug(getClass().getName(), e.getMessage());
                        } catch (IOException e) {
                            e.printStackTrace();
                            LogUtil.logDebug(getClass().getName(), e.getMessage());
                        }
                    }
                }).start();
            }
        });
    }


}
