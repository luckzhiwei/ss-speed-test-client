package com.vecent.ssspeedtest.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.vecent.ssspeedtest.model.SpeedTest;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

import java.util.ArrayList;
import java.util.List;


import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lzw on 17-10-1.
 */
public class TotalSpeedTestResult implements Parcelable {


    private Long id;

    private float totalTimeUsed;

    private int whiteAddrServerCount;

    private int blackAddrServerCount;

    private int whiteAddrServerConnectedCount;

    private int blackAddrServerConnectedCount;

    private float whiteAddrConnectSuccesRate;

    private float blackAddrConnectSuccesRate;

    private int curServerCount;

    private int totalServerSize;

    private int totalByteSize = 0;

    private float speedWhiteAddrSum;

    private float speedBlackAddrSum;

    private float speedWhiteAddrDownLoadAvg = 0;

    private float speedBlackAddrDownLoadAvg = 0;

    private String server2TestAddr;

    private List<SpeedTestResult> mResults;


    public TotalSpeedTestResult() {
        this.mResults = new ArrayList<>();
    }

    protected TotalSpeedTestResult(Parcel in) {
        totalTimeUsed = in.readFloat();
        whiteAddrServerCount = in.readInt();
        blackAddrServerCount = in.readInt();
        whiteAddrConnectSuccesRate = in.readFloat();
        blackAddrConnectSuccesRate = in.readFloat();
        curServerCount = in.readInt();
        totalServerSize = in.readInt();
        totalByteSize = in.readInt();
        speedWhiteAddrDownLoadAvg = in.readFloat();
        speedBlackAddrDownLoadAvg = in.readFloat();
        server2TestAddr = in.readString();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(totalTimeUsed);
        dest.writeInt(whiteAddrServerCount);
        dest.writeInt(blackAddrServerCount);
        dest.writeFloat(whiteAddrConnectSuccesRate);
        dest.writeFloat(blackAddrConnectSuccesRate);
        dest.writeInt(curServerCount);
        dest.writeInt(totalServerSize);
        dest.writeInt(totalByteSize);
        dest.writeFloat(speedWhiteAddrDownLoadAvg);
        dest.writeFloat(speedBlackAddrDownLoadAvg);
        dest.writeString(server2TestAddr);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TotalSpeedTestResult> CREATOR = new Creator<TotalSpeedTestResult>() {
        @Override
        public TotalSpeedTestResult createFromParcel(Parcel in) {
            return new TotalSpeedTestResult(in);
        }

        @Override
        public TotalSpeedTestResult[] newArray(int size) {
            return new TotalSpeedTestResult[size];
        }
    };

    public float getSpeedBlackAddrDownLoadAvg() {
        if (blackAddrServerCount == 0 || blackAddrServerConnectedCount == 0)
            this.speedBlackAddrDownLoadAvg = 0;
        else
            this.speedBlackAddrDownLoadAvg = (1.0f * speedBlackAddrSum / blackAddrServerConnectedCount);
        return this.speedBlackAddrDownLoadAvg;
    }

    public float getSpeedWhiteAddrDownLoadAvg() {
        if (whiteAddrServerCount == 0 || whiteAddrServerConnectedCount == 0)
            this.speedWhiteAddrDownLoadAvg = 0;
        else
            this.speedWhiteAddrDownLoadAvg = (1.0f * speedWhiteAddrSum / whiteAddrServerConnectedCount);
        return this.speedWhiteAddrDownLoadAvg;
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

    public int getBlackAddrServerCount() {
        return blackAddrServerCount;
    }


    public float getWhiteAddrConnectSuccesRate() {
        if (whiteAddrServerCount == 0 || whiteAddrServerConnectedCount == 0)
            this.whiteAddrConnectSuccesRate = 0;
        else
            this.whiteAddrConnectSuccesRate = (1.0f * whiteAddrServerConnectedCount / whiteAddrServerCount);

        return this.whiteAddrConnectSuccesRate;
    }


    public float getBlackAddrConnectSuccesRate() {
        if (blackAddrServerCount == 0 || blackAddrServerConnectedCount == 0)
            this.blackAddrConnectSuccesRate = 0;
        else
            this.blackAddrConnectSuccesRate = (1.0f * blackAddrServerConnectedCount / blackAddrServerCount);
        return this.blackAddrConnectSuccesRate;
    }


    public void addResult2List(SpeedTestResult result) {
        this.mResults.add(result);
        this.statResult();
    }

    public List<SpeedTestResult> getResultList() {
        return this.mResults;
    }


    private void statResult() {
        curServerCount++;
        SpeedTestResult newResult = mResults.get(mResults.size() - 1);
        if (newResult.isWhiteAddr()) {
            whiteAddrServerCount++;
            if (!newResult.isExceptionOccured()) {
                whiteAddrServerConnectedCount++;
                speedWhiteAddrSum += newResult.getDownLoadSpeed();
            }

        } else {
            blackAddrServerCount++;
            if (!newResult.isExceptionOccured()) {
                blackAddrServerConnectedCount++;
                speedBlackAddrSum += newResult.getDownLoadSpeed();
            }
        }
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServer2TestAddr() {
        return this.server2TestAddr;
    }

    public void setServer2TestAddr(String server2TestAddr) {
        this.server2TestAddr = server2TestAddr;
    }


}
