package com.vecent.ssspeedtest.model;

import com.vecent.ssspeedtest.model.bean.SpeedTestResult;
import com.vecent.ssspeedtest.util.LogUtil;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by lzw on 17-9-5.
 */

public class INetImpl implements INet {

    public SpeedTestResult getHttpTestResult(String server) {
        SpeedTestResult result = new SpeedTestResult();
        result.setRequestServer(server);
        HttpURLConnection conn = null;
        try {
            long startTime = System.currentTimeMillis();
            conn = this.getConnection(server);
            int responseCode = conn.getResponseCode();
            if (this.isRedirect(responseCode)) {
                String newUrl = conn.getHeaderField("Location");
                conn = getConnection(newUrl);
                LogUtil.logInfo("Redirect Url", newUrl.toString());
            }
            result.setTotalSize(this.getResponseSize(conn.getInputStream()));
            result.setStatusCode(conn.getResponseCode());
            result.setTimeUsed(System.currentTimeMillis() - startTime);
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


    private HttpURLConnection getConnection(String server) throws
            MalformedURLException, ProtocolException, IOException {
        URL url = new URL(server);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.setConnectTimeout(5000);
        return conn;
    }

    private boolean isRedirect(int code) {
        if (code == HttpURLConnection.HTTP_OK) {
            return false;
        }
        return code == HttpURLConnection.HTTP_MOVED_PERM
                || code == HttpURLConnection.HTTP_MOVED_TEMP
                || code == HttpURLConnection.HTTP_SEE_OTHER;
    }

    private int getResponseSize(InputStream in) throws IOException {
        BufferedInputStream inputStream = new BufferedInputStream(in);
        byte[] buf = new byte[1024];
        int len = 0;
        int totalSize = 0;
        while ((len = inputStream.read(buf)) != -1) {
            totalSize += len;
        }
        inputStream.close();
        return totalSize;
    }


}
