package com.vecent.ssspeedtest.model.bean;

/**
 * Created by lzw on 17-9-22.
 */

public class Server {

    private String web;
    private String type;

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isWhiteListAddr() {
        return this.type.equals("w");
    }

}
