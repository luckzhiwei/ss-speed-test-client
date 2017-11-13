package com.vecent.ssspeedtest.controller;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.vecent.ssspeedtest.R;
import com.vecent.ssspeedtest.adpater.SpeedTestAdapter;
import com.vecent.ssspeedtest.dao.SSServer;
import com.vecent.ssspeedtest.model.ProxyGuradProcess;
import com.vecent.ssspeedtest.model.ThreadPool;
import com.vecent.ssspeedtest.model.net.INetImplDefault;
import com.vecent.ssspeedtest.model.Servers;
import com.vecent.ssspeedtest.model.SpeedTest;
import com.vecent.ssspeedtest.model.bean.SpeedTestResult;
import com.vecent.ssspeedtest.model.bean.TotalSpeedTestResult;
import com.vecent.ssspeedtest.model.net.INetImplWithProxy;
import com.vecent.ssspeedtest.view.ResultLayout;

/**
 * Created by zhiwei on 2017/9/9.
 */

public class SpeedTestActivity extends Activity {

    private SpeedTest mSpeedTest;
    private ListView mContentListView;
    private SpeedTestAdapter mAdapter;
    private ProgressBar mProgressBar;
    private ResultLayout mResultLayout;
    private TotalSpeedTestResult mResult;
    private int totalSize;
    private Handler mHandler;
    private SSServer mProxyServer;
    private ProxyGuradProcess mGuradProcess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speed_test_layout);
        this.initView();
        this.initModel();
    }

    private void initView() {
        this.mResultLayout = this.findViewById(R.id.speed_test_result_layout);
        this.mResultLayout.post(new Runnable() {
            @Override
            public void run() {
                mResultLayout.setHeaderShowCorOnInit(SpeedTestActivity.this);
            }
        });
        this.mContentListView = this.findViewById(R.id.common_list_view);
        this.mProgressBar = this.findViewById(R.id.speed_test_progress);
        this.mProgressBar.setMax(100);
    }

    private void initModel() {
        mResult = new TotalSpeedTestResult();
        this.mAdapter = new SpeedTestAdapter(getApplicationContext(),
                R.layout.speed_test_item_layout, mResult.getResultList());
        this.mContentListView.setAdapter(this.mAdapter);
        Servers servers2Test = new Servers(this.getApplicationContext());
        this.mHandler = new Handler(getMainLooper());
        this.mSpeedTest = new SpeedTest(servers2Test.getServers(), this.mHandler);
        this.totalSize = servers2Test.getServers().size();
        mResult.setTotalServerSize(this.totalSize);
        this.mProxyServer = getIntent().getParcelableExtra("ssServer");
        this.startSpeedTest();
    }

    private void startSpeedTest() {
        this.mSpeedTest.setRequestCallBack(new SpeedTest.RequestCallBack() {
            @Override
            public void onOneRequestFinishListener(SpeedTestResult result) {
                mResult.addResult2List(result);
                updateView();
            }

            @Override
            public void onAllRequestFinishListener(float timeUsed, int totalReqSize) {
                setResultView(timeUsed);
                if (mProxyServer != null) {
                    mGuradProcess.destory();
                }
            }
        });
        if (mProxyServer != null) {
            startTestWithProxy();
        } else {
            startTestWithoutProxy();
        }
    }

    private void startTestWithoutProxy() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mSpeedTest.startTest(new INetImplDefault());
            }
        }).start();
    }

    private void startTestWithProxy() {
        this.mResultLayout.setProxyServerInfo(mProxyServer);
        mGuradProcess = new ProxyGuradProcess(mProxyServer, this);
        mGuradProcess.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mSpeedTest.startTest(new INetImplWithProxy());
            }
        }).start();
    }


    private void updateView() {
        mAdapter.notifyDataSetChanged();
        mProgressBar.setProgress((int) (100.0f * mResult.getCurServerCount() / totalSize));
        setResultView((System.currentTimeMillis() - mSpeedTest.getTimeStart()) / 1000.0f);
    }

    private void setResultView(float timeUsed) {
        mResult.setTotalTimeUsed(timeUsed);
        mResultLayout.update(mResult);
    }

    @Override
    public void onBackPressed() {
        mSpeedTest.cancel();
        if (mProxyServer != null) {
            mGuradProcess.destory();
        }
        finish();
    }


}
