package com.vecent.ssspeedtest.controller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vecent.ssspeedtest.Adpater.SpeedTestAdapter;
import com.vecent.ssspeedtest.R;
import com.vecent.ssspeedtest.model.INetImpl;
import com.vecent.ssspeedtest.model.SpeedTest;
import com.vecent.ssspeedtest.model.bean.PingResult;
import com.vecent.ssspeedtest.util.LogUtil;

import java.util.ArrayList;

/**
 * Created by zhiwei on 2017/9/9.
 */

public class SpeedTestActivity extends Activity {

    private SpeedTest mSpeedTest;
    private ListView mContentListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speed_test_layout);
        this.initView();
        this.initData();
//        this.startSpeedTest();
    }

    private void initView() {
        this.mContentListView = this.findViewById(R.id.common_list_view);
    }

    private void initData() {
        ArrayList<String> servers = new ArrayList<>();
        servers.add("taobao.com");
        servers.add("baidu.com");
        servers.add("www.google.com.hk");
        this.mSpeedTest = new SpeedTest(servers);
        ArrayList<PingResult> mock = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            PingResult pingResult = new PingResult();
            pingResult.setExecRetCode(0);
            pingResult.setServerToTest("www.taobao.com");
            pingResult.setLossRate("0%");
            pingResult.setTimeAvg(45);
            pingResult.setTimeMin(42);
            pingResult.setTimeMax(48);
            pingResult.setTotalPackets(4);
            mock.add(pingResult);
        }
        this.mContentListView.setAdapter(new SpeedTestAdapter(getApplicationContext(),
                R.layout.speed_test_item_layout, mock));

    }

    private void startSpeedTest() {
        this.mSpeedTest.setPingCallBack(new SpeedTest.OnPingCallBack() {
            @Override
            public void onPingRetListener(PingResult result) {

            }
        });
        this.mSpeedTest.startTest(new INetImpl());
    }


}
