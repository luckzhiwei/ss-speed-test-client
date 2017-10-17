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
    private int totalServerSize;
    private int totalByteSize = 0;
    private float speedDownLoad = 0;


    public float getSpeedDownLoad() {
        return speedDownLoad;
    }

    public void setSpeedDownLoad(float speedDownLoad) {
        this.speedDownLoad = speedDownLoad;
    }

    public int getCurServerCount() {
        return curServerCount;
    }

    public int getTotalByteSize() {
        return totalByteSize;
    }

    public void setTotalByteSize(int totalByteSize) {
        this.totalByteSize = totalByteSize;
    }

    public void setCurServerCount(int curServerCount) {
        this.curServerCount = curServerCount;
    }

    public int getTotalServerSize() {
        return totalServerSize;
    }

    public void setTotalServerSize(int totalSize) {
        this.totalServerSize = totalSize;
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
