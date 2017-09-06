package com.vecent.ssspeedtest.model.bean;

/**
 * Created by lzw on 17-9-5.
 */

public class PingResult {

    private int execRet;
    private String pingRet;
    private float timeMax;
    private float timeMin;
    private float timeAvg;
    private String lossRate;

    public String getLossRate() {
        return lossRate;
    }

    public void setLossRate(String lossRate) {
        this.lossRate = lossRate;
    }

    private int totalPackets;

    public int getReceivedPackets() {
        return receivedPackets;
    }

    public void setReceivedPackets(int receivedPackets) {
        this.receivedPackets = receivedPackets;
    }

    private int receivedPackets;

    public int getTotalPackets() {
        return totalPackets;
    }

    public void setTotalPackets(int totalPackets) {
        this.totalPackets = totalPackets;
    }

    public float getTimeMax() {
        return timeMax;
    }

    public void setTimeMax(float timeMax) {
        this.timeMax = timeMax;
    }

    public float getTimeMin() {
        return timeMin;
    }

    public void setTimeMin(float timeMin) {
        this.timeMin = timeMin;
    }

    public float getTimeAvg() {
        return timeAvg;
    }

    public void setTimeAvg(float timeAvg) {
        this.timeAvg = timeAvg;
    }

    public void setExecRet(int execRet) {
        this.execRet = execRet;
    }

    public int getExecRet() {
        return this.execRet;
    }

    public void setPingRet(String pingRet) {
        this.pingRet = pingRet;
    }

    public String getPingRet() {
        return this.pingRet;
    }

}
