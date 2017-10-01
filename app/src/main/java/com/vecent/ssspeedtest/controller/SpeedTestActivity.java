package com.vecent.ssspeedtest.controller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.vecent.ssspeedtest.R;
import com.vecent.ssspeedtest.adpater.SpeedTestAdapter;
import com.vecent.ssspeedtest.model.INetImpl;
import com.vecent.ssspeedtest.model.Servers;
import com.vecent.ssspeedtest.model.SpeedTest;
import com.vecent.ssspeedtest.model.bean.SpeedTestResult;
import com.vecent.ssspeedtest.model.bean.TotalSpeedTestResult;
import com.vecent.ssspeedtest.view.KeyValueView;
import com.vecent.ssspeedtest.view.ResultLayout;

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
    private ResultLayout resultView;
    private ProgressBar mProgressBar;
    private KeyValueView totalServerItem;
    private KeyValueView currentServerItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speed_test_layout);
        this.initView();
        this.initData();
    }

    private void initView() {
        this.mContentListView = this.findViewById(R.id.common_list_view);
        this.mProgressBar = this.findViewById(R.id.speed_test_progress);
        this.totalServerItem = this.findViewById(R.id.item_total_server_count);
        this.currentServerItem = this.findViewById(R.id.item_total_server_count_current);
        this.totalServerItem.setKey(getResources().getString(R.string.total_server_count)).setKeyTextColor(R.color.colorWhite).setValueTextColor(R.color.colorWhite);
        this.currentServerItem.setKey(getResources().getString(R.string.cur_server_count)).setKeyTextColor(R.color.colorWhite).setValueTextColor(R.color.colorWhite);
        this.mProgressBar.setMax(100);
    }

    private void initData() {
        this.mSpeedTestResults = new Vector<>();
        this.mAdapter = new SpeedTestAdapter(getApplicationContext(),
                R.layout.speed_test_item_layout, mSpeedTestResults);
        this.mContentListView.setAdapter(this.mAdapter);
        Servers servers2Test = new Servers(this.getApplicationContext());
        this.mSpeedTest = new SpeedTest(servers2Test.getServers());
        this.startSpeedTest();
    }

    private void startSpeedTest() {
        this.mSpeedTest.setPingCallBack(new SpeedTest.RequestCallBack() {
            @Override
            public void onOneRequestFinishListener(SpeedTestResult result, int totalSize) {
                mSpeedTestResults.add(result);
                mAdapter.notifyDataSetChanged();
                int curRequestedServerCount = mSpeedTestResults.size();
                int progress = (int) (100.0f * curRequestedServerCount / totalSize);
                totalServerItem.setValue(totalSize + "");
                currentServerItem.setValue(curRequestedServerCount + "");
                mProgressBar.setProgress(progress);
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
        if (this.resultView == null) {
            this.resultView = new ResultLayout(this.getApplicationContext());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            this.resultView.setLayoutParams(params);
        }
        TotalSpeedTestResult result = new TotalSpeedTestResult();
        result.setTotalTimeUsed(timeUsed);
        result.setTotalSize(totalReqSize);
        result.setWhiteAddrServerCount(mSpeedTest.countWhiteListAddr(mSpeedTestResults));
        result.setBlackAddrServerCount(mSpeedTest.countBlackListAddr(mSpeedTestResults));
        result.setWhiteAddrConnectSuccesRate(mSpeedTest.calConnectRateWhiteList(mSpeedTestResults));
        result.setBlackAddrConnectSuccesRate(mSpeedTest.calConnectRateBalckList(mSpeedTestResults));
        resultView.setReuslt(result);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mSpeedTest.cancel();
    }


}
