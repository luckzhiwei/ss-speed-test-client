package com.vecent.ssspeedtest.controller;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vecent.ssspeedtest.R;
import com.vecent.ssspeedtest.adpater.SpeedTestAdapter;
import com.vecent.ssspeedtest.model.INetImpl;
import com.vecent.ssspeedtest.model.Servers;
import com.vecent.ssspeedtest.model.SpeedTest;
import com.vecent.ssspeedtest.model.bean.SpeedTestResult;
import com.vecent.ssspeedtest.util.LogUtil;
import com.vecent.ssspeedtest.view.KeyValueView;

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
    private ViewGroup footerView;

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
        this.mSpeedTest.setPingCallBack(new SpeedTest.RequestCallBack() {
            @Override
            public void onOneRequestFinishListener(SpeedTestResult result) {
                mSpeedTestResults.add(result);
                LogUtil.logDebug(getClass().getName(), result.getRequestServer() + "");
                LogUtil.logDebug(getClass().getName(), mSpeedTestResults.size() + "");
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onAllRequestFinishListener(float timeUsed, int totalReqSize) {
                setResult(timeUsed, totalReqSize);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                mSpeedTest.startTest(new INetImpl());
            }
        }).start();
    }

    private void setResult(float timeUsed, int totalReqSize) {
        if (this.footerView == null) {
            this.footerView = (ViewGroup) LayoutInflater.from(this.getApplicationContext()).
                    inflate(R.layout.speed_total_result_layout, null);
        }
        Resources res = getApplicationContext().getResources();

        float whiteListConnectRate = this.mSpeedTest.calConnectRateWhiteList(this.mSpeedTestResults);
        float balckListConnectRate = this.mSpeedTest.calConnectRateBalckList(this.mSpeedTestResults);
        int whiteAddrCount = this.mSpeedTest.countWhiteListAddr(this.mSpeedTestResults);
        int blackAddrCount = this.mSpeedTest.countBlackListAddr(this.mSpeedTestResults);

        footerView.addView(new KeyValueView(getApplicationContext()).
                setKey(res.getString(R.string.total_server_count)).setValue(totalReqSize + ""));
        footerView.addView(new KeyValueView(getApplicationContext()).
                setKey(res.getString(R.string.total_used_time)).setValue(timeUsed + "  " + res.getString(R.string.unit_second)));
        footerView.addView(new KeyValueView(getApplicationContext()).
                setKey(res.getString(R.string.white_list_success_rate)).setValue(whiteListConnectRate + ""));
        footerView.addView(new KeyValueView(getApplication()).
                setKey(res.getString(R.string.white_list_addr_count)).setValue(whiteAddrCount + ""));
        footerView.addView(new KeyValueView(getApplicationContext()).
                setKey(res.getString(R.string.black_list_success_rate)).setValue(balckListConnectRate + ""));
        footerView.addView(new KeyValueView(getApplicationContext()).
                setKey(res.getString(R.string.black_list_addr_count)).setValue(blackAddrCount + ""));
        this.mContentListView.addFooterView(footerView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mSpeedTest.cancel();
    }


}
