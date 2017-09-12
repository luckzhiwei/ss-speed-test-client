package com.vecent.ssspeedtest.model;

import com.vecent.ssspeedtest.model.bean.SpeedTestResult;
import com.vecent.ssspeedtest.util.LogUtil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lzw on 17-9-5.
 */

public class INetImpl implements INet {

    public SpeedTestResult httpRequest(String server) {
        SpeedTestResult result = new SpeedTestResult();
        result.setRequestServer(server);
        try {
            URL url = new URL(server);
            long startTime = System.currentTimeMillis();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setConnectTimeout(5000);
            BufferedInputStream inputStream = new BufferedInputStream(conn.getInputStream());
            byte[] buf = new byte[1024];
            int len = 0;
            int totalSize = 0;
            while ((len = inputStream.read(buf)) != -1) {
                totalSize += len;
            }
            inputStream.close();
            conn.disconnect();
            long endTime = System.currentTimeMillis();
            long timeUsed = endTime - startTime;
            result.setTimeUsed(timeUsed);
            result.setTotalSize(totalSize);
            result.setStatusCode(conn.getResponseCode());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
