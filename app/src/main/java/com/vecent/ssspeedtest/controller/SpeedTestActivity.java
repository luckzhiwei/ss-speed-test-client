package com.vecent.ssspeedtest.controller;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vecent.ssspeedtest.R;
import com.vecent.ssspeedtest.adpater.SpeedTestAdapter;
import com.vecent.ssspeedtest.dao.DaoManager;
import com.vecent.ssspeedtest.dao.SSServer;
import com.vecent.ssspeedtest.greendao.DaoSession;
import com.vecent.ssspeedtest.model.evaluter.Evaluter4Score;
import com.vecent.ssspeedtest.model.guradprocess.PrivoxyGuradProcess;
import com.vecent.ssspeedtest.model.guradprocess.SSProxyGuradProcess;
import com.vecent.ssspeedtest.model.net.INetImplDefault;
import com.vecent.ssspeedtest.model.Servers4Test;
import com.vecent.ssspeedtest.model.SpeedTest;
import com.vecent.ssspeedtest.model.bean.SpeedTestResult;
import com.vecent.ssspeedtest.model.bean.TotalSpeedTestResult;
import com.vecent.ssspeedtest.model.net.INetImplWithPrivoxy;
import com.vecent.ssspeedtest.model.net.INetImplWithSSProxy;
import com.vecent.ssspeedtest.util.Constant;
import com.vecent.ssspeedtest.view.ResultLayout;

/**
 * Created by zhiwei on 2017/9/9.
 */

public class SpeedTestActivity extends AppCompatActivity {

    private SpeedTest mSpeedTest;
    private ListView mContentListView;
    private SpeedTestAdapter mAdapter;
    private ProgressBar mProgressBar;
    private ResultLayout mResultLayout;
    private TextView ssServerInfo;
    private TextView scoreContent;
    private TotalSpeedTestResult mResult;
    private int totalSize;
    private Handler mHandler;
    private SSServer mProxyServer;
    private SSProxyGuradProcess mSSProxyProcess;
    private PrivoxyGuradProcess mPrivoxyProcess;
    //    private int score;
    private boolean isTestFinished = false;

    public static final int TEST_FINISHED = 1;

    public static final int REQUSET_CODE = 0x00020;

    private SpeedTest.RequestCallBack callBack = new SpeedTest.RequestCallBack() {
        @Override
        public void onAllRequestFinishListener(float timeUsed, int totalReqSize) {
            setResultView(timeUsed);
            mProxyServer.setScore(mResult.getResultScore());
            if (!mProxyServer.isSystemProxy())
                mSSProxyProcess.destory();
            if (mPrivoxyProcess != null)
                mPrivoxyProcess.destory();
            isTestFinished = true;
        }

        @Override
        public void onOneRequestFinishListener(SpeedTestResult result) {
            mResult.addResult2List(result);
            updateView();
        }
    };


    private static class ModelHodler {
        public Handler mSaveHandler;
        public SSServer mSavedProxyServer;
        public int mSavedTotalSize;
        public int score;
        public TotalSpeedTestResult mSaveResult;
        public SpeedTestAdapter mSaveAdapter;
        public SpeedTest mSaveSpeedTest;
        public SSProxyGuradProcess mSavedSSGuradProcess;
        public PrivoxyGuradProcess mSavedPrivoxyProcess;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speed_test_layout);
        ModelHodler holder = (ModelHodler) this.getLastCustomNonConfigurationInstance();
        this.initView();
        if (holder == null) {
            this.initModel();
            this.initTitleContent();
        } else {
            this.continueView(holder);
        }
    }


    private void continueView(ModelHodler hodler) {
        this.mAdapter = hodler.mSaveAdapter;
        this.mSpeedTest = hodler.mSaveSpeedTest;
        this.mHandler = hodler.mSaveHandler;
        this.mSSProxyProcess = hodler.mSavedSSGuradProcess;
        this.mPrivoxyProcess = hodler.mSavedPrivoxyProcess;
        this.mResult = hodler.mSaveResult;
        this.totalSize = hodler.mSavedTotalSize;
        this.mProxyServer = hodler.mSavedProxyServer;
        this.mContentListView.setAdapter(this.mAdapter);
        this.mSpeedTest.setRequestCallBack(callBack);
        this.updateView();
    }

    private void initView() {
        this.mResultLayout = (ResultLayout) this.findViewById(R.id.speed_test_result_layout);
        this.mResultLayout.post(new Runnable() {
            @Override
            public void run() {
                mResultLayout.setHeaderShowCorOnInit(SpeedTestActivity.this);
            }
        });
        this.mContentListView = (ListView) this.findViewById(R.id.common_list_view);
        this.mContentListView.setDivider(new ColorDrawable(getResources().getColor(R.color.list_separator)));
        this.mContentListView.setDividerHeight(1);
        this.mProgressBar = (ProgressBar) this.findViewById(R.id.common_progress);
        this.mProgressBar.setMax(100);
        this.ssServerInfo = (TextView) this.findViewById(R.id.textview_ss_server_info);
        this.scoreContent = (TextView) this.findViewById(R.id.textview_score_value);
        initActionBar();
    }

    private void initActionBar() {
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setCustomView(R.layout.action_bar_go_back);
        actionBar.getCustomView().findViewById(R.id.action_back_root_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initTitleContent() {
        if (!mProxyServer.isSystemProxy()) {
            this.ssServerInfo.setText(mProxyServer.getServerAddr() + ":" + mProxyServer.getServerPort());
        }
    }

    private void initModel() {
        mResult = new TotalSpeedTestResult(new Evaluter4Score());
        this.mAdapter = new SpeedTestAdapter(getApplicationContext(),
                R.layout.speed_test_item_layout, mResult.getResultList());
        this.mContentListView.setAdapter(this.mAdapter);
        Servers4Test servers2Test = new Servers4Test(this.getApplicationContext(), "gfwlistOutput.json");
        this.mHandler = new Handler(getMainLooper());
        this.mSpeedTest = new SpeedTest(servers2Test.getServers(), this.mHandler);
        this.totalSize = servers2Test.getServers().size();
        mResult.setTotalServerSize(this.totalSize);
        this.mProxyServer = getIntent().getParcelableExtra("ssServer");
        this.startSpeedTest();
    }

    private void startSpeedTest() {
        this.mSpeedTest.setRequestCallBack(callBack);
        if (!mProxyServer.isSystemProxy()) {
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT < 24) {
                    mPrivoxyProcess = new PrivoxyGuradProcess(getApplicationContext(), Constant.FRONT_PRIVOXY_CONFIG_FILE_NAME);
                    mPrivoxyProcess.start();
                    mPrivoxyProcess.waitFor(Constant.PRIVOXY_LOCAL_PORT_FONT);
                }
                mSSProxyProcess = new SSProxyGuradProcess(mProxyServer, getApplicationContext(), Constant.SOCKS_SERVER_LOCAL_PORT_FONT);
                mSSProxyProcess.start();
                mSSProxyProcess.waitFor(Constant.SOCKS_SERVER_LOCAL_PORT_FONT);
                if (Build.VERSION.SDK_INT < 24) {
                    mSpeedTest.startTest(new INetImplWithPrivoxy(Constant.PRIVOXY_LOCAL_PORT_FONT));
                } else {
                    mSpeedTest.startTest(new INetImplWithSSProxy(Constant.SOCKS_SERVER_LOCAL_PORT_FONT));
                }
            }
        }).start();
    }


    private void updateView() {
        mAdapter.notifyDataSetChanged();
        scoreContent.setText(mResult.getResultScore() + "");
        mProgressBar.setProgress((int) (100.0f * mResult.getCurServerCount() / totalSize));
        setResultView((System.currentTimeMillis() - mSpeedTest.getTimeStart()) / 1000.0f);
    }

    private void setResultView(float timeUsed) {
        mResult.setTotalTimeUsed(timeUsed);
        mResultLayout.update(mResult);
    }

    @Override
    public void onBackPressed() {
        if (!isTestFinished) {
            mSpeedTest.cancel();
            if (!mProxyServer.isSystemProxy())
                mSSProxyProcess.destory();
            if (mPrivoxyProcess != null)
                mPrivoxyProcess.destory();
            this.mProxyServer.setScore(mResult.getResultScore());
        }
        Intent dataBack = new Intent();
        dataBack.putExtra("pos", getIntent().getIntExtra("pos", -1));
        dataBack.putExtra("ssServer", mProxyServer);
        setResult(TEST_FINISHED, dataBack);
        finish();
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
    }


    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        ModelHodler holder = new ModelHodler();
        holder.mSaveSpeedTest = mSpeedTest;
        holder.mSaveAdapter = mAdapter;
        holder.mSavedProxyServer = mProxyServer;
        holder.mSavedPrivoxyProcess = mPrivoxyProcess;
        holder.mSaveResult = mResult;
        holder.mSavedTotalSize = totalSize;
        holder.mSaveHandler = mHandler;
        holder.mSavedSSGuradProcess = mSSProxyProcess;
        holder.mSavedProxyServer = mProxyServer;
        return holder;
    }


}
