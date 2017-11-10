package com.vecent.ssspeedtest.model;

import android.content.Context;

import com.vecent.ssspeedtest.dao.SSServer;
import com.vecent.ssspeedtest.util.Constant;
import com.vecent.ssspeedtest.util.LogUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhiwei on 2017/11/6.
 */

public class ProxyGuradProcess {

    private Thread ssLocalThread;
    private List<String> cmds;

    public ProxyGuradProcess(SSServer ssServer, Context context) {
        this.cmds = new ArrayList<>();
        cmds.add(context.getApplicationInfo().nativeLibraryDir + "/libss-local.so");
        cmds.add("-u");
        cmds.add("-b");
        cmds.add(Constant.SOCKS_SERVER_LOCAL_ADDR + "");
        cmds.add("-l");
        cmds.add(Constant.SOCKS_SERVER_LOCAL_PORT + "");
        cmds.add("-s");
        cmds.add(ssServer.getServerAddr());
        cmds.add("-p");
        cmds.add(ssServer.getServerPort() + "");
        cmds.add("-m");
        cmds.add(ssServer.getMethod());
        cmds.add("-k");
        cmds.add(ssServer.getPassword());
    }

    public void startProcess() {
        if (this.ssLocalThread == null) {
            this.ssLocalThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ProcessBuilder processBuilder = new ProcessBuilder().command(cmds);
                        Process p = processBuilder.start();
                        LogUtil.logDebug(getClass().getName(), "startsslocal");
                        LogUtil.logDebug(getClass().getName(), "RET IS " + p.waitFor() + "");
                    } catch (IOException e) {

                    } catch (InterruptedException e) {
                        LogUtil.logDebug(getClass().getName(), "exit ss local");
                        return;
                    }
                }
            });
        }
        this.ssLocalThread.start();
    }

    public void destoryProcess() {
        this.ssLocalThread.interrupt();
    }


}
