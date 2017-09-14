package com.vecent.ssspeedtest.model.bean;

/**
 * Created by lzw on 17-9-5.
 */

public class SpeedTestResult {

    private String requestServer;
    private int totalSize;
    private int statusCode;
    private long timeUsed;
    private String exceptionMsg;
    private boolean isUrlWrong = false;
    private boolean isRedirect = false;
    private boolean isTimedOut = false;
    private String redirectServer;
    private boolean isExceptionOccured = false;

    public boolean isUrlWrong() {
        return isUrlWrong;
    }

    public void setUrlWrong(boolean urlWrong) {
        isUrlWrong = urlWrong;
    }

    public boolean isExceptionOccured() {
        return isExceptionOccured;
    }

    public void setExceptionOccured(boolean exceptionOccured) {
        isExceptionOccured = exceptionOccured;
    }

    public boolean isRedirect() {
        return isRedirect;
    }

    public void setRedirect(boolean redirect) {
        isRedirect = redirect;
    }

    public boolean isTimedOut() {
        return isTimedOut;
    }

    public void setTimedOut(boolean timedOut) {
        isTimedOut = timedOut;
    }

    public String getRedirectServer() {
        return redirectServer;
    }

    public void setRedirectServer(String redirectServer) {
        this.redirectServer = redirectServer;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public String getRequestServer() {
        return requestServer;
    }

    public void setRequestServer(String requestServer) {
        this.requestServer = requestServer;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public long getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(long timeUsed) {
        this.timeUsed = timeUsed;
    }
}
