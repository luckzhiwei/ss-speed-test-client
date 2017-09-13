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
        servers.add("http://www.baidu.com");
        servers.add("http://www.expekt.com");
        servers.add("http://www.expekt.com");
        servers.add("http://www.extmatrix.com");
        servers.add("http://www.extmatrix.com");
        servers.add("http://www.filesor.com");
        servers.add("http://www.financetwitter.com");
        servers.add("http://www.findmima.com");
        servers.add("http://www.funkyimg.com");
        servers.add("http://www.fxnetworks.com");
        servers.add("http://www.g-area.org");
        servers.add("http://www.gettyimages.com");
        servers.add("https://github.com/programthink/zhao");
        servers.add("http://www.glass8.eu");
        servers.add("http://www.go141.com");
        servers.add("http://www.homedepot.com");
        servers.add("http://www.hoovers.com");
        servers.add("http://www.hulu.com");
        servers.add("http://secure.hustler.com");
        servers.add("http://hustlercash.com");
        servers.add("http://www.hustlercash.com");
        servers.add("http://www.hybrid-analysis.com");
        servers.add("http://www.ilovelongtoes.com");
        servers.add("http://imgur.com/upload");
        servers.add("https://imgur.com/upload");
        servers.add("http://www.javhub.net");
        servers.add("http://www.javlibrary.com");
        servers.add("http://www.javlibrary.com");
        servers.add("http://www.jcpenney.com");
        servers.add("http://www.juliepost.com");
        servers.add("http://www.kawaiikawaii.jp");
        servers.add("http://www.kendatire.com");
        servers.add("http://www.khatrimaza.org");
        servers.add("http://www.kkbox.com");
        servers.add("http://www.leisurepro.com");
        servers.add("http://www.longtoes.com");
        servers.add("http://www.lovetvshow.com");
        servers.add("http://www.m-sport.co.uk");
        servers.add("http://www.macgamestore.com");
        servers.add("http://www.madonna-av.com");
        servers.add("http://www.mangafox.com");
        servers.add("http://www.mangafox.me");
        servers.add("http://www.matome-plus.com");
        servers.add("http://www.matome-plus.net");
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
