package com.vecent.ssspeedtest.model.bean;

/**
 * Created by lzw on 17-10-1.
 */

public class TotalSpeedTestResult {

    private float totalTimeUsed;
    private int whiteAddrServerCount;
    private int blackAddrServerCount;
    private float whiteAddrConnectSuccesRate;
    private float blackAddrConnectSuccesRate;
    private int curServerCount;
    private int totalSize;

    public int getCurServerCount() {
        return curServerCount;
    }

    public void setCurServerCount(int curServerCount) {
        this.curServerCount = curServerCount;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public float getTotalTimeUsed() {
        return totalTimeUsed;
    }

    public void setTotalTimeUsed(float totalTimeUsed) {
        this.totalTimeUsed = totalTimeUsed;
    }

    public int getWhiteAddrServerCount() {
        return whiteAddrServerCount;
    }

    public void setWhiteAddrServerCount(int whiteAddrServerCount) {
        this.whiteAddrServerCount = whiteAddrServerCount;
    }

    public int getBlackAddrServerCount() {
        return blackAddrServerCount;
    }

    public void setBlackAddrServerCount(int blackAddrServerCount) {
        this.blackAddrServerCount = blackAddrServerCount;
    }

    public float getWhiteAddrConnectSuccesRate() {
        return whiteAddrConnectSuccesRate;
    }

    public void setWhiteAddrConnectSuccesRate(float whiteAddrConnectSuccesRate) {
        this.whiteAddrConnectSuccesRate = whiteAddrConnectSuccesRate;
    }

    public float getBlackAddrConnectSuccesRate() {
        return blackAddrConnectSuccesRate;
    }

    public void setBlackAddrConnectSuccesRate(float blackAddrConnectSuccesRate) {
        this.blackAddrConnectSuccesRate = blackAddrConnectSuccesRate;
    }


}
