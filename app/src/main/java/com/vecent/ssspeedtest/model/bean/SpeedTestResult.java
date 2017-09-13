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
