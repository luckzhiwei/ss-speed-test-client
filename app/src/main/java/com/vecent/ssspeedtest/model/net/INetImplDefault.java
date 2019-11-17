package com.vecent.ssspeedtest.model.net;

import com.vecent.ssspeedtest.model.SpeedTest;
import com.vecent.ssspeedtest.model.bean.Server;
import com.vecent.ssspeedtest.model.bean.SpeedTestResult;
import com.vecent.ssspeedtest.model.net.INet;
import com.vecent.ssspeedtest.util.Constant;
import com.vecent.ssspeedtest.util.LogUtil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Connection;

/**
 * Created by lzw on 17-9-5.
 */

public class INetImplDefault implements INet {

    public static final String TOO_MANY_TIMES_TO_REDIRECT = "too many times to redriect";


    @Override
    public SpeedTestResult getHttpTestResult(Server server) {
        SpeedTestResult result = new SpeedTestResult();
        result.setRequestServer(server.getWeb());
        result.setWhiteAddr(server.isWhiteListAddr());
        HttpURLConnection conn = null;
        try {
            result.setStartTime(System.currentTimeMillis());
            conn = this.getConnection(server.getWeb());
            if (conn == null) {
                LogUtil.logDebug(getClass().getName(), "connection is null");
                throw new IOException("conn init fail");
            }
            result.setStartTime(System.currentTimeMillis());
            GetResponseCodeThread thread = new GetResponseCodeThread(conn);
            thread.start();
            thread.join(Constant.RESLOVE_DNS_TIME_OUT);
            int responseCode = thread.getResponseCode();
            if (responseCode == -1) {
                throw new IOException("DNS resolved timeout");
            }
            result.setStatusCode(responseCode);
            int countNum = 0;
            while (this.isRedirect(responseCode)) {
                String newUrl = conn.getHeaderField("Location");
                conn = getConnection(newUrl);
                result.setRedirect(true);
                result.setRedirectServer(newUrl);
                responseCode = conn.getResponseCode();
                result.setStatusCode(responseCode);
                if (++countNum > Constant.MAX_REDIRECT_TIMES) {
                    this.setResultExceptionMsg(result, TOO_MANY_TIMES_TO_REDIRECT);
                    result.setIs2ManyTimeRelocation(true);
                    return result;
                }
            }
            this.getResponseSize(conn.getInputStream(), result);
        } catch (MalformedURLException e) {
            result.setUrlWrong(true);
            setResultExceptionMsg(result, e.getMessage());
        } catch (IOException e) {
            result.setTimedOut(true);
            setResultExceptionMsg(result, e.getMessage());
        } catch (NullPointerException e) {
            result.setTimedOut(true);
            setResultExceptionMsg(result, e.getMessage());
        } catch (InterruptedException e) {
            result.setTimedOut(true);
            setResultExceptionMsg(result, e.getMessage());
        } finally {
            result.setEndTime(System.currentTimeMillis());
            result.setTimeUsed();
            if (!result.isExceptionOccured()) {
                result.setDownLoadSpeed();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }

    private void setResultExceptionMsg(SpeedTestResult ret, String msg) {
        ret.setExceptionOccured(true);
        ret.setExceptionMsg(msg);
    }

    protected HttpURLConnection getConnection(String server) throws
            MalformedURLException, ProtocolException, IOException {
        URL url = new URL(server);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.setConnectTimeout(Constant.CONNECTION_TIME_OUT);
        conn.setReadTimeout(Constant.READ_TIME_OUT);
        conn.setRequestProperty("User-agent", "Mozilla/5.0 (Linux; Android 4.2.1; Nexus 7 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166  Safari/535.19");
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

    private int getResponseSize(InputStream in, SpeedTestResult result) throws IOException {
        BufferedInputStream inputStream = new BufferedInputStream(in);
        byte[] buf = new byte[8192];
        int len = 0;
        int totalSize = 0;
        while ((len = inputStream.read(buf)) != -1) {
            totalSize += len;
            if (System.currentTimeMillis() - result.getStartTime() > (Constant.TOTAL_TIME_OUT)) {
                throw new IOException("total time out");
            }
        }
        result.setTotalSize(totalSize);
        inputStream.close();
        return totalSize;
    }

    public class GetResponseCodeThread extends Thread {

        private int code = -1;
        private HttpURLConnection connection;

        public GetResponseCodeThread(HttpURLConnection connection) {
            this.connection = connection;
        }

        @Override
        public void run() {
            try {
                if (this.connection != null)
                    this.code = this.connection.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        public int getResponseCode() {
            return this.code;
        }
    }

}
