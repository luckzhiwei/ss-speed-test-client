package com.vecent.ssspeedtest.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhiwei on 2017/11/7.
 */

@Entity
public class SSServer {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "serverAddr")
    @NotNull
    private String serverAddr;

    @Property(nameInDb = "serverPort")
    @NotNull
    private int serverSort;

    @Property(nameInDb = "password")
    @NotNull
    private String password;

    @Property(nameInDb = "method")
    @NotNull
    private String method;

    @Generated(hash = 1731999373)
    public SSServer(Long id, @NotNull String serverAddr, int serverSort,
            @NotNull String password, @NotNull String method) {
        this.id = id;
        this.serverAddr = serverAddr;
        this.serverSort = serverSort;
        this.password = password;
        this.method = method;
    }

    @Generated(hash = 1471507233)
    public SSServer() {
    }

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

    public int getServerSort() {
        return this.serverSort;
    }

    public void setServerSort(int serverSort) {
        this.serverSort = serverSort;
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
}
