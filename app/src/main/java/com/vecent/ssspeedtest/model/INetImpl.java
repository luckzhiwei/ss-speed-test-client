package com.vecent.ssspeedtest.model;

import com.vecent.ssspeedtest.model.bean.SpeedTestResult;
import com.vecent.ssspeedtest.util.LogUtil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.ExtendedSSLSession;
import javax.net.ssl.HttpsURLConnection;

/**
 * Created by lzw on 17-9-5.
 */

public class INetImpl implements INet {

    public SpeedTestResult httpRequest(String server) {
        SpeedTestResult result = new SpeedTestResult();
        result.setRequestServer(server);
        HttpURLConnection conn = null;
        try {
            boolean redirect = false;
            URL url = new URL(server);
            long startTime = System.currentTimeMillis();
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setConnectTimeout(5000);
            int code = conn.getResponseCode();
            if (code != HttpURLConnection.HTTP_OK) {
                if (code == HttpURLConnection.HTTP_MOVED_PERM
                        || code == HttpURLConnection.HTTP_MOVED_TEMP
                        || code == HttpURLConnection.HTTP_SEE_OTHER) {
                    redirect = true;
                }
            }
            if (redirect) {
                String newUrl = conn.getHeaderField("Location");
                conn=(HttpURLConnection)new URL(newUrl).openConnection();
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setConnectTimeout(5000);
                LogUtil.logInfo("Redirect Url", newUrl.toString());
            }
            BufferedInputStream inputStream = new BufferedInputStream(conn.getInputStream());
            byte[] buf = new byte[1024];
            int len = 0;
            int totalSize = 0;
            while ((len = inputStream.read(buf)) != -1) {
                totalSize += len;
            }
            inputStream.close();
            long endTime = System.currentTimeMillis();
            long timeUsed = endTime - startTime;
            result.setTimeUsed(timeUsed);
            result.setTotalSize(totalSize);
            result.setStatusCode(conn.getResponseCode());
        } catch (MalformedURLException e) {
            result.setExceptionMsg(e.getMessage());
        } catch (IOException e) {
            result.setExceptionMsg(e.getMessage());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }
}
