package com.vecent.ssspeedtest.controller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView mSpeadText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speed_test_layout);
        this.initView();
        Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();
        ArrayList<String> servers = new ArrayList<>();
        servers.add("taobao.com");
        servers.add("baidu.com");
        servers.add("www.google.com.hk");
        this.mSpeedTest = new SpeedTest(servers);
        this.mSpeedTest.setPingCallBack(new SpeedTest.OnPingCallBack() {
            @Override
            public void onPingRetListener(PingResult result) {
//                LogUtil.logDebug(getClass().getName(), result.getServerToTest());
//                LogUtil.logDebug(getClass().getName(), result.getExecRet() + "");
//                mSpeadText.setText(result.getServerToTest());
            }
        });
        this.mSpeedTest.startTest(new INetImpl());

    }

    private void initView() {
        this.mSpeadText = (TextView) this.findViewById(R.id.speed_test_testview);
    }


}
