package com.vecent.ssspeedtest.model;

import android.content.Context;

import com.vecent.ssspeedtest.model.bean.Server;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * Created by lzw on 17-9-22.
 */

public class Servers4Test {

    private List<Server> mServers;

    public Servers4Test(Context context, String configFilePath) {
        byte[] content = getContentFromResFile(context, configFilePath);
        if (content != null) {
            mServers = JSON.parseArray(new String(content), Server.class);
        }
    }

    public List<Server> getServers() {
        return this.mServers;
    }

    private byte[] getContentFromResFile(Context context, String configFilePath) {
        BufferedInputStream stream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            stream = new BufferedInputStream(context.getAssets().open(configFilePath));
            byte[] buf = new byte[512];
            int len = -1;
            while ((len = stream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }


}
