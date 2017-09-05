package com.vecent.ssspeedtest.model;

import android.util.Log;

import com.vecent.ssspeedtest.util.Constant;
import com.vecent.ssspeedtest.util.LogUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zhiwei on 2017/9/4.
 */

public class SpeedTest {

    private boolean ping(String server) {
        Process p;
        try {
            p = Runtime.getRuntime().exec("/system/bin/ping -c 4" + server);
            int status = p.waitFor();
            LogUtil.logDebug(getClass().getName(), status + "");
            StringBuilder strBuilder = new StringBuilder();
            InputStream in = p.getInputStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = bReader.readLine()) != null) {
                strBuilder.append(line);
            }
            bReader.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void startTest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ping("www.baidu.com");
            }
        }).start();
    }
}
