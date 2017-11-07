package com.vecent.ssspeedtest.model;

import com.vecent.ssspeedtest.util.LogUtil;

import java.io.IOException;
import java.util.List;

/**
 * Created by zhiwei on 2017/11/6.
 */

public class GuradProcess {

    private Thread ssLocalThread;

    public void startProcess(final List<String> cmd) {
        if (this.ssLocalThread == null) {
            this.ssLocalThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ProcessBuilder processBuilder = new ProcessBuilder().command(cmd);
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
