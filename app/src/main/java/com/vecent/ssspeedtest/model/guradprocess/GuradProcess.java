package com.vecent.ssspeedtest.model.guradprocess;

import android.content.Context;

import java.lang.Process;

import com.vecent.ssspeedtest.util.LogUtil;

import java.io.IOException;
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
                        LogUtil.logDebug(getClass().getName(), "RET IS " + mProcess.waitFor() + "");
                    } catch (IOException e) {

                    } catch (InterruptedException e) {
                        return;
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

}
