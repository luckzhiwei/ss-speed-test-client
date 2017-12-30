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

import javax.xml.transform.Result;

import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lzw on 17-10-1.
 */
@Entity
public class TotalSpeedTestResult implements Parcelable {


    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "totalTimeUsed")
    private float totalTimeUsed;

    @Property(nameInDb = "whiteAddrServerCount")
    private int whiteAddrServerCount;

    @Property(nameInDb = "blackAddrServerCount")
    private int blackAddrServerCount;

    @Property(nameInDb = "whiteAddrConnectSuccesRate")
    private float whiteAddrConnectSuccesRate;

    @Property(nameInDb = "blackAddrConnectSuccesRate")
    private float blackAddrConnectSuccesRate;

    @Transient
    private int curServerCount;

    @Property(nameInDb = "totalServerSize")
    private int totalServerSize;

    @Property(nameInDb = "totalByteSize")
    private int totalByteSize = 0;

    @Property(nameInDb = "speedWhiteAddrDownLoadAvg")
    private float speedWhiteAddrDownLoadAvg = 0;

    @Property(nameInDb = "speedBlackAddrDownLoadAvg")
    private float speedBlackAddrDownLoadAvg = 0;

    @Property(nameInDb = "server2TestAddr")
    private String server2TestAddr;

    @Transient
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

    @Generated(hash = 552862759)
    public TotalSpeedTestResult(Long id, float totalTimeUsed, int whiteAddrServerCount,
                                int blackAddrServerCount, float whiteAddrConnectSuccesRate,
                                float blackAddrConnectSuccesRate, int totalServerSize, int totalByteSize,
                                float speedWhiteAddrDownLoadAvg, float speedBlackAddrDownLoadAvg, String server2TestAddr) {
        this.id = id;
        this.totalTimeUsed = totalTimeUsed;
        this.whiteAddrServerCount = whiteAddrServerCount;
        this.blackAddrServerCount = blackAddrServerCount;
        this.whiteAddrConnectSuccesRate = whiteAddrConnectSuccesRate;
        this.blackAddrConnectSuccesRate = blackAddrConnectSuccesRate;
        this.totalServerSize = totalServerSize;
        this.totalByteSize = totalByteSize;
        this.speedWhiteAddrDownLoadAvg = speedWhiteAddrDownLoadAvg;
        this.speedBlackAddrDownLoadAvg = speedBlackAddrDownLoadAvg;
        this.server2TestAddr = server2TestAddr;
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
