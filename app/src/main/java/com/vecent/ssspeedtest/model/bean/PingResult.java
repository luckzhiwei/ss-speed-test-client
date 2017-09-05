package com.vecent.ssspeedtest.model.bean;

/**
 * Created by lzw on 17-9-5.
 */

public class PingResult {

    private int execRet;
    private String pingRet;
    private float timeMax;
    private float timeMin;
    private float lossRate;
    private float timeAvg;

    public float getTimeMax() {
        return timeMax;
    }

    public void setTimeMax(int timeMax) {
        this.timeMax = timeMax;
    }

    public float getTimeMin() {
        return timeMin;
    }

    public void setTimeMin(int timeMin) {
        this.timeMin = timeMin;
    }

    public float getLossRate() {
        return lossRate;
    }

    public void setLossRate(float lossRate) {
        this.lossRate = lossRate;
    }

    public float getTimeAvg() {
        return timeAvg;
    }

    public void setTimeAvg(int timeAvg) {
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
