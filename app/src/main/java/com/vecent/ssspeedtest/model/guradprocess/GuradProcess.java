package com.vecent.ssspeedtest.model.guradprocess;

import android.content.Context;

import java.lang.Process;

import com.vecent.ssspeedtest.util.Constant;
import com.vecent.ssspeedtest.util.LogUtil;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * Created by zhiwei on 2017/12/4.
 */

public abstract class GuradProcess {

    protected Thread mProcessThread;
    protected Process mProcess;
    protected List<String> mCmds;
    protected Context mContext;

    public GuradProcess(Context context) {
        mContext = context;
    }

    protected abstract List<String> initCmds();

    public void start() {
        if (mCmds == null) {
            this.mCmds = initCmds();
        }
        if (this.mProcessThread == null) {
            this.mProcessThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ProcessBuilder processBuilder = new ProcessBuilder().command(mCmds);
                        mProcess = processBuilder.start();
                        LogUtil.logDebug(getClass().getName(), "cmds header " + mCmds.get(0));
                        LogUtil.logDebug(getClass().getName(), "RET IS " + mProcess.waitFor() + "");
                    } catch (IOException e) {
                        LogUtil.logDebug(getClass().getName(), "io execption" + e.getMessage());
                    } catch (InterruptedException e) {
                    }
                }
            });
        }
        this.mProcessThread.start();
    }

    public void destory() {
        this.mProcessThread.interrupt();
        mProcess.destroy();

    }

    public void waitFor(int port) {
        WaitForStartThread waitForStartThread = new WaitForStartThread(port);
        try {
            waitForStartThread.start();
            waitForStartThread.join(Constant.WAIT_PROCESS_TIME_OUT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void waitForClose(int port) {
        WaitForCloseThread waitForCloseThread = new WaitForCloseThread(port);
        try {
            waitForCloseThread.start();
            waitForCloseThread.join(Constant.WAIT_PROCESS_TIME_OUT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class WaitForStartThread extends Thread {
        private int port;

        public WaitForStartThread(int port) {
            this.port = port;
        }

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            while (true) {
                try {
                    if ((System.currentTimeMillis() - startTime) > Constant.WAIT_PROCESS_TIME_OUT) {
                        break;
                    }
                    Socket socket = new Socket("127.0.0.1", this.port);
                    socket.close();
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class WaitForCloseThread extends Thread {
        private int port;

        public WaitForCloseThread(int port) {
            this.port = port;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Socket socket = new Socket("127.0.0.1", this.port);
                    socket.close();
                } catch (IOException e) {
                    break;
                }
            }
        }
    }

}
