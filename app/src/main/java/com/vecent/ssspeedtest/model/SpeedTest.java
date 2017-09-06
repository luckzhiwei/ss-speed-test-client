package com.vecent.ssspeedtest.model;

import com.vecent.ssspeedtest.model.bean.PingResult;
import com.vecent.ssspeedtest.util.LogUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by zhiwei on 2017/9/4.
 */

public class SpeedTest {

    private ArrayList<String> mServersForTest;


    public SpeedTest(ArrayList<String> serversForTest) {
        this.mServersForTest = serversForTest;
    }

    public ArrayList<PingResult> ping(INet net) {
        ArrayList<PingResult> ret = new ArrayList<>(this.mServersForTest.size());
        for (String server : mServersForTest) {
            PingResult pingRet = net.ping(server);
            if (pingRet.getExecRet() == 0) {
                this.parsePingRetStr(pingRet);
            }
            ret.add(pingRet);
        }
        return ret;
    }

    private void parsePingRetStr(PingResult pingResult) {
        String strRet = pingResult.getPingRet();
        String[] arr = strRet.split("\n");
        for (String line : arr) {
            String[] sentences = line.split(",");
            for (String sentence : sentences) {
                if (sentence.endsWith("packets transmitted")) {
                    pingResult
                } else if (sentence.endsWith("received")) {

                } else if (sentence.endsWith("packet loss")) {

                } else if (sentence.startsWith("rtt")) {

                }
            }
        }
    }

    public void startTest(final INet net) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ping(net);
            }
        }).start();
    }

    public ArrayList<PingResult> getPingResult() {
        return null;
    }


}
