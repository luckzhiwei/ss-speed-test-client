package com.vecent.ssspeedtest.model.bean;

import com.vecent.ssspeedtest.model.SpeedTest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

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
    private float speedWhiteAddrDownLoadAvg = 0;
    private float speedBlackAddrDownLoadAvg = 0;
    private List<SpeedTestResult> mResults;

    public TotalSpeedTestResult() {
        this.mResults = new ArrayList<>();
    }

    public float getSpeedBlackAddrDownLoadAvg() {
        return speedBlackAddrDownLoadAvg;
    }

    public void setSpeedBlackAddrDownLoadAvg(float speedBlackAddrDownLoadAvg) {
        this.speedBlackAddrDownLoadAvg = speedBlackAddrDownLoadAvg;
    }

    public float getSpeedWhiteAddrDownLoadAvg() {
        return speedWhiteAddrDownLoadAvg;
    }

    public void setSpeedWhiteAddrDownLoadAvg(float speedWhiteAddrDownLoadAvg) {
        this.speedWhiteAddrDownLoadAvg = speedWhiteAddrDownLoadAvg;
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

    public void addResult2List(SpeedTestResult result) {
        this.mResults.add(result);
        this.statResult();
    }

    public List<SpeedTestResult> getResultList() {
        return this.mResults;
    }


    private void statResult() {
        int whiteAddrSize = 0, connectWhiteAddrSize = 0,
                blackAddrSize = 0, connectBlackAddrSize = 0,
                whiteAddrSpeedAvgSum = 0, blackAddrSpeedAvgSum = 0;
        for (SpeedTestResult oneRet : this.mResults) {
            if (oneRet.isWhiteAddr()) {
                whiteAddrSize++;
                if (!oneRet.isExceptionOccured()) {
                    connectWhiteAddrSize++;
                    whiteAddrSpeedAvgSum += oneRet.getDownLoadSpeed();

                }
            } else {
                blackAddrSize++;
                if (!oneRet.isExceptionOccured()) {
                    connectBlackAddrSize++;
                    blackAddrSpeedAvgSum += oneRet.getDownLoadSpeed();
                }
            }
        }
        if (whiteAddrSize == 0) {
            this.setWhiteAddrServerCount(0);
            this.setWhiteAddrConnectSuccesRate(0);
            this.setSpeedWhiteAddrDownLoadAvg(0.0f);
        } else {
            this.setWhiteAddrServerCount(whiteAddrSize);
            this.setWhiteAddrConnectSuccesRate(1.0f * connectWhiteAddrSize / whiteAddrSize);
            if (connectWhiteAddrSize == 0) {
                this.setSpeedWhiteAddrDownLoadAvg(0.0f);
            } else {
                this.setSpeedWhiteAddrDownLoadAvg(whiteAddrSpeedAvgSum * 1.0f / connectWhiteAddrSize);
            }
        }
        if (blackAddrSize == 0) {
            this.setBlackAddrServerCount(0);
            this.setBlackAddrConnectSuccesRate(0);
            this.setSpeedBlackAddrDownLoadAvg(0.0f);
        } else {
            this.setBlackAddrServerCount(blackAddrSize);
            this.setBlackAddrConnectSuccesRate(1.0f * connectBlackAddrSize / blackAddrSize);
            if (connectBlackAddrSize == 0) {
                this.setSpeedBlackAddrDownLoadAvg(0);
            } else {
                this.setSpeedBlackAddrDownLoadAvg(1.0f * blackAddrSpeedAvgSum / connectBlackAddrSize);
            }
        }
        this.setCurServerCount(whiteAddrSize + blackAddrSize);
    }


}
