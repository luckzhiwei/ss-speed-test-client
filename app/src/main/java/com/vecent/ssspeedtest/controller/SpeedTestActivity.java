package com.vecent.ssspeedtest.controller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.vecent.ssspeedtest.R;
import com.vecent.ssspeedtest.adpater.SpeedTestAdapter;
import com.vecent.ssspeedtest.model.INetImpl;
import com.vecent.ssspeedtest.model.Servers;
import com.vecent.ssspeedtest.model.SpeedTest;
import com.vecent.ssspeedtest.model.bean.SpeedTestResult;
import com.vecent.ssspeedtest.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by zhiwei on 2017/9/9.
 */

public class SpeedTestActivity extends Activity {

    private SpeedTest mSpeedTest;
    private ListView mContentListView;
    private List<SpeedTestResult> mSpeedTestResults;
    private SpeedTestAdapter mAdapter;

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
        this.mSpeedTestResults = new Vector<>();
        this.mAdapter = new SpeedTestAdapter(getApplicationContext(),
                R.layout.speed_test_item_layout, mSpeedTestResults);
        this.mContentListView.setAdapter(this.mAdapter);
        Servers servers2Test = new Servers(this.getApplicationContext());
        this.mSpeedTest = new SpeedTest(servers2Test.getServers());
        LogUtil.logDebug(getClass().getName(), servers2Test.getServers().size() + "");
        this.startSpeedTest();
    }

    private void startSpeedTest() {
        this.mSpeedTest.setPingCallBack(new SpeedTest.OnPingCallBack() {
            @Override
            public void onPingRetListener(SpeedTestResult result) {
                mSpeedTestResults.add(result);
                if (mSpeedTestResults.size() % 100 == 0 || mSpeedTestResults.size() > 560) {
                    LogUtil.logDebug(getClass().getName(), mSpeedTestResults.size() + "");
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                mSpeedTest.startTest(new INetImpl());
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mSpeedTest.cancel();
        LogUtil.logDebug(getClass().getName(), "destory the speedtest activity");
    }


}
