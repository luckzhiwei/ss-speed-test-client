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

    public boolean ping(INet net) {
        return false;
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
