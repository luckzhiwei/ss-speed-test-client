package com.vecent.ssspeedtest.controller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.vecent.ssspeedtest.R;
import com.vecent.ssspeedtest.adpater.SpeedTestAdapter;
import com.vecent.ssspeedtest.model.INetImpl;
import com.vecent.ssspeedtest.model.Servers;
import com.vecent.ssspeedtest.model.SpeedTest;
import com.vecent.ssspeedtest.model.bean.SpeedTestResult;
import com.vecent.ssspeedtest.model.bean.TotalSpeedTestResult;
import com.vecent.ssspeedtest.util.LogUtil;
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
    private ProgressBar mProgressBar;
    private ResultLayout mResultLayout;
    private TotalSpeedTestResult result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speed_test_layout);
        this.initView();
        this.initData();
    }

    private void initView() {
        this.mResultLayout = this.findViewById(R.id.speed_test_result_layout);
        this.mContentListView = this.findViewById(R.id.common_list_view);
        this.mProgressBar = this.findViewById(R.id.speed_test_progress);
        this.mProgressBar.setMax(100);
    }

    private void initData() {
        this.mSpeedTestResults = new Vector<>();
        this.mAdapter = new SpeedTestAdapter(getApplicationContext(),
                R.layout.speed_test_item_layout, mSpeedTestResults);
        this.mContentListView.setAdapter(this.mAdapter);
        Servers servers2Test = new Servers(this.getApplicationContext());
        this.mSpeedTest = new SpeedTest(servers2Test.getServers());
        result = new TotalSpeedTestResult();
        result.setTotalServerSize(servers2Test.getServers().size());
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
                mProgressBar.setProgress(progress);
                float timeUsed = (System.currentTimeMillis() - mSpeedTest.getTimeStart()) / 1000.0f;
                setResult(timeUsed);
            }

            @Override
            public void onAllRequestFinishListener(float timeUsed, int totalReqSize) {
                setResult(timeUsed);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                mSpeedTest.startTest(new INetImpl());
            }
        }).start();
    }

    private void setResult(float timeUsed) {
        result.setTotalTimeUsed(timeUsed);
        mSpeedTest.countResult(result, mSpeedTestResults);
        mResultLayout.setReuslt(result);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mSpeedTest.cancel();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mResultLayout.setHeaderShowCor(this);
    }

}
