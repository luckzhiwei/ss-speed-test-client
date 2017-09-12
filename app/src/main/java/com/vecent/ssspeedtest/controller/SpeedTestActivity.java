package com.vecent.ssspeedtest.controller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.vecent.ssspeedtest.adpater.SpeedTestAdapter;
import com.vecent.ssspeedtest.R;
import com.vecent.ssspeedtest.model.INetImpl;
import com.vecent.ssspeedtest.model.SpeedTest;
import com.vecent.ssspeedtest.model.bean.SpeedTestResult;

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
    }

    private void initView() {
        this.mContentListView = this.findViewById(R.id.common_list_view);
    }

    private void initData() {
        ArrayList<String> servers = new ArrayList<>();
        servers.add("http://www.baidu.com/");
        this.mSpeedTest = new SpeedTest(servers);
        this.startSpeedTest();
    }

    private void startSpeedTest() {
        this.mSpeedTest.setPingCallBack(new SpeedTest.OnPingCallBack() {
            @Override
            public void onPingRetListener(SpeedTestResult result) {

            }
        });
        this.mSpeedTest.startTest(new INetImpl());
    }


}
