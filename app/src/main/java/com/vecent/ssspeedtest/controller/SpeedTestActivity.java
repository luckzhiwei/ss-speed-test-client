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
        servers.add("httpSpeedTest://www.baidu.com");
        servers.add("httpSpeedTest://www.expekt.com");
        servers.add("httpSpeedTest://www.expekt.com");
        servers.add("httpSpeedTest://www.extmatrix.com");
        servers.add("httpSpeedTest://www.extmatrix.com");
        servers.add("httpSpeedTest://www.filesor.com");
        servers.add("httpSpeedTest://www.financetwitter.com");
        servers.add("httpSpeedTest://www.findmima.com");
        servers.add("httpSpeedTest://www.funkyimg.com");
        servers.add("httpSpeedTest://www.fxnetworks.com");
        servers.add("httpSpeedTest://www.g-area.org");
        servers.add("httpSpeedTest://www.gettyimages.com");
        servers.add("https://github.com/programthink/zhao");
        servers.add("httpSpeedTest://www.glass8.eu");
        servers.add("httpSpeedTest://www.go141.com");
        servers.add("httpSpeedTest://www.homedepot.com");
        servers.add("httpSpeedTest://www.hoovers.com");
        servers.add("httpSpeedTest://www.hulu.com");
        servers.add("httpSpeedTest://secure.hustler.com");
        servers.add("httpSpeedTest://hustlercash.com");
        servers.add("httpSpeedTest://www.hustlercash.com");
        servers.add("httpSpeedTest://www.hybrid-analysis.com");
        servers.add("httpSpeedTest://www.ilovelongtoes.com");
        servers.add("httpSpeedTest://imgur.com/upload");
        servers.add("https://imgur.com/upload");
        servers.add("httpSpeedTest://www.javhub.net");
        servers.add("httpSpeedTest://www.javlibrary.com");
        servers.add("httpSpeedTest://www.javlibrary.com");
        servers.add("httpSpeedTest://www.jcpenney.com");
        servers.add("httpSpeedTest://www.juliepost.com");
        servers.add("httpSpeedTest://www.kawaiikawaii.jp");
        servers.add("httpSpeedTest://www.kendatire.com");
        servers.add("httpSpeedTest://www.khatrimaza.org");
        servers.add("httpSpeedTest://www.kkbox.com");
        servers.add("httpSpeedTest://www.leisurepro.com");
        servers.add("httpSpeedTest://www.longtoes.com");
        servers.add("httpSpeedTest://www.lovetvshow.com");
        servers.add("httpSpeedTest://www.m-sport.co.uk");
        servers.add("httpSpeedTest://www.macgamestore.com");
        servers.add("httpSpeedTest://www.madonna-av.com");
        servers.add("httpSpeedTest://www.mangafox.com");
        servers.add("httpSpeedTest://www.mangafox.me");
        servers.add("httpSpeedTest://www.matome-plus.com");
        servers.add("httpSpeedTest://www.matome-plus.net");
        this.mSpeedTest = new SpeedTest(servers);
        this.startSpeedTest();
    }

    private void startSpeedTest() {
        this.mSpeedTest.setPingCallBack(new SpeedTest.OnPingCallBack() {
            @Override
            public void onPingRetListener(SpeedTestResult result) {

            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                mSpeedTest.startTest(new INetImpl());
            }
        }).start();

    }


}
