package com.vecent.ssspeedtest.model;

import com.vecent.ssspeedtest.model.bean.PingResult;
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
            if (pingRet != null) {
                if (pingRet.getExecRet() == 0) {
                    this.parsePingRetStr(pingRet);
                }
                ret.add(pingRet);
            }
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
                    int totalPackets = sentence.charAt(0) - 0x30;
                    pingResult.setTotalPackets(totalPackets);
                } else if (sentence.endsWith("received")) {
                    int recevied = sentence.charAt(1) - 0x30;
                    pingResult.setReceivedPackets(recevied);
                } else if (sentence.endsWith("packet loss")) {
                    pingResult.setLossRate(sentence.substring(0, 6));
                } else if (sentence.startsWith("rtt")) {
                    String[] datas = sentence.split("=")[1].trim().split("/");
                    for (int i = 0; i < datas.length - 1; i++) {
                        if (i == 0) {
                            pingResult.setTimeMin(Float.parseFloat(datas[i]));
                        } else if (i == 1) {
                            pingResult.setTimeAvg(Float.parseFloat(datas[i]));
                        } else if (i == 2) {
                            pingResult.setTimeMax(Float.parseFloat(datas[i]));
                        }
                    }
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

}
