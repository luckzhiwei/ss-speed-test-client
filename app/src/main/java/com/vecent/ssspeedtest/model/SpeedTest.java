package com.vecent.ssspeedtest.model;

import com.vecent.ssspeedtest.model.bean.PingResult;

import java.util.ArrayList;

import android.os.Handler;

/**
 * Created by zhiwei on 2017/9/4.
 */

public class SpeedTest {

    private ArrayList<String> mServersForTest;
    private ThreadPool mThreadPool;

    private Handler mHandler;
    private OnPingCallBack mPingCallBack;

    public static interface OnPingCallBack {
         void onPingRetListener(PingResult result);
    }

    public SpeedTest(ArrayList<String> serversForTest) {
        this.mServersForTest = serversForTest;
        mThreadPool = ThreadPool.getInstance();
        mHandler = new Handler();
    }

    public PingResult ping(INet net, String serverToPing) {
        PingResult pingRet = net.ping(serverToPing);
        if (pingRet != null) {
            if (pingRet.getExecRetCode() == 0) {
                this.parsePingRetStr(pingRet);
            }
        }
        return pingRet;
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
        for (final String servers : mServersForTest) {
            mThreadPool.execTask(new Runnable() {
                @Override
                public void run() {
                    final PingResult pingResult = ping(net, servers);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mPingCallBack.onPingRetListener(pingResult);
                        }
                    });
                }
            });
        }
    }

    public void setPingCallBack(OnPingCallBack fun) {
        this.mPingCallBack = fun;
    }

}
