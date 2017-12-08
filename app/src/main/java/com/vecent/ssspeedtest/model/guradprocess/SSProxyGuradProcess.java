package com.vecent.ssspeedtest.model.guradprocess;

import android.content.Context;

import com.vecent.ssspeedtest.dao.SSServer;
import com.vecent.ssspeedtest.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhiwei on 2017/11/6.
 */

public class SSProxyGuradProcess extends GuradProcess {

    private int mPort;
    private SSServer mSSServer;

    public SSProxyGuradProcess(SSServer ssServer, Context context, int port) {
        super(context);
        this.mSSServer = ssServer;
        this.mPort = port;
    }

    @Override
    protected List<String> initCmds() {
        List<String> cmds = new ArrayList<>();
        cmds.add(mContext.getApplicationInfo().nativeLibraryDir + "/libss-local.so");
        cmds.add("-u");
        cmds.add("-b");
        cmds.add(Constant.LOCAL_HOST + "");
        cmds.add("-l");
        cmds.add(mPort + "");
        cmds.add("-s");
        cmds.add(mSSServer.getServerAddr());
        cmds.add("-p");
        cmds.add(mSSServer.getServerPort() + "");
        cmds.add("-m");
        cmds.add(mSSServer.getMethod());
        cmds.add("-k");
        cmds.add(mSSServer.getPassword());
        return cmds;
    }

}
