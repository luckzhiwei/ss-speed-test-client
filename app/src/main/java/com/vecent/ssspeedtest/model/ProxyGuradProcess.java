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
    private Process process;

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

    public void start() {
        if (this.ssLocalThread == null) {
            this.ssLocalThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        LogUtil.logDebug(getClass().getName(), "start proxy");
                        ProcessBuilder processBuilder = new ProcessBuilder().command(cmds);
                        process = processBuilder.start();
                        LogUtil.logDebug(getClass().getName(), "RET IS " + process.waitFor() + "");
                    } catch (IOException e) {

                    } catch (InterruptedException e) {
                        return;
                    }
                }
            });
        }
        this.ssLocalThread.start();
    }

    public void destory() {
        this.ssLocalThread.interrupt();
        process.destroy();
        LogUtil.logDebug(getClass().getName(), "destory the proxy");
    }


}
