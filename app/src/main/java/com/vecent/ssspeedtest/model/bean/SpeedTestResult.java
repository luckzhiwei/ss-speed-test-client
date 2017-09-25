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

    public boolean is2ManyTimeRelocation() {
        return is2ManyTimeRelocation;
    }

    public void setIs2ManyTimeRelocation(boolean is2ManyTimeRelocation) {
        this.is2ManyTimeRelocation = is2ManyTimeRelocation;
    }

    private boolean is2ManyTimeRelocation = false;
    private float downLoadSpeed;

    public float getDownLoadSpeed() {
        return downLoadSpeed;
    }

    public void setDownLoadSpeed(float downLoadSpeed) {
        this.downLoadSpeed = downLoadSpeed;
    }

    public void setDownLoadSpeed() {
        if (this.totalSize != 0 && this.timeUsed != 0) {
            this.downLoadSpeed = this.totalSize * 1.0f / this.timeUsed;
        }
    }

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
