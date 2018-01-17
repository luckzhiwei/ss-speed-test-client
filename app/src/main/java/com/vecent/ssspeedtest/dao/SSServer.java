package com.vecent.ssspeedtest.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.vecent.ssspeedtest.util.Constant;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

/**
 * Created by zhiwei on 2017/11/7.
 */

@Entity
public class SSServer implements Parcelable {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "serverAddr")
    @NotNull
    private String serverAddr;

    @Property(nameInDb = "serverPort")
    @NotNull
    private int serverPort;

    @Property(nameInDb = "password")
    @NotNull
    private String password;

    @Property(nameInDb = "method")
    @NotNull
    private String method;

    @Property(nameInDb = "score")
    @NotNull
    private int score = -1;


    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Property(nameInDb = "grade")
    @NotNull
    private int grade = -1;

    public Long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getServerAddr() {
        return this.serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public int getServerPort() {
        return this.serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static final Parcelable.Creator<SSServer> CREATOR = new Parcelable.Creator<SSServer>() {
        public SSServer createFromParcel(Parcel in) {
            return new SSServer(in);
        }

        public SSServer[] newArray(int size) {
            return new SSServer[size];
        }
    };


    public int describeContents() {
        //几乎所有情况都返回0，仅在当前对象中存在文件描述符时返回1
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(id);
        out.writeString(serverAddr);
        out.writeInt(serverPort);
        out.writeString(password);
        out.writeString(method);
        out.writeInt(score);
        out.writeInt(grade);
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    protected SSServer(Parcel in) {
        this.id = in.readLong();
        this.serverAddr = in.readString();
        this.serverPort = in.readInt();
        this.password = in.readString();
        this.method = in.readString();
        this.score = in.readInt();
        this.grade = in.readInt();
    }

    @Generated(hash = 20000939)
    public SSServer(Long id, @NotNull String serverAddr, int serverPort, @NotNull String password,
            @NotNull String method, int score, int grade) {
        this.id = id;
        this.serverAddr = serverAddr;
        this.serverPort = serverPort;
        this.password = password;
        this.method = method;
        this.score = score;
        this.grade = grade;
    }

    @Generated(hash = 1471507233)
    public SSServer() {
    }

    public boolean isSystemProxy() {
        return this.serverAddr.equals(Constant.SYSTEM_PROXY);
    }

}
