package com.vecent.ssspeedtest.model.guradprocess;

import android.content.Context;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhiwei on 2017/12/7.
 */

public class PrivoxyGuradProcess extends GuradProcess {

    private String configFile;

    public PrivoxyGuradProcess(Context context, String configFile) {
        super(context);
        this.configFile = configFile;
    }

    @Override
    protected List<String> initCmds() {
        List<String> chmod = new ArrayList<>();
        chmod.add("chmod");
        chmod.add("744");
        chmod.add(mContext.getFilesDir().getAbsolutePath() + "/privoxy");
        ProcessBuilder processBuilder = new ProcessBuilder().command(chmod);
        try {
            Process p = processBuilder.start();
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.getMessage();
        }
        List<String> cmds = new ArrayList<>();
        cmds.add(mContext.getFilesDir().getAbsolutePath() + "/privoxy");
        cmds.add("--no-daemon");
        cmds.add(mContext.getFilesDir().getAbsolutePath() + "/" + configFile);
        return cmds;
    }
}
