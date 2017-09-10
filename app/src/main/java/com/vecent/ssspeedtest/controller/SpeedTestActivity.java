package com.vecent.ssspeedtest.controller;

import android.app.Activity;
import android.os.Bundle;

import com.vecent.ssspeedtest.model.INetImpl;
import com.vecent.ssspeedtest.model.SpeedTest;

import java.util.ArrayList;

/**
 * Created by zhiwei on 2017/9/9.
 */

public class SpeedTestActivity extends Activity{

    private SpeedTest mSpeedTest;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       ArrayList<String> servers = new ArrayList<>();
       servers.add("taobao.com");
       servers.add("baidu.com");
       servers.add("www.google.com.hk");
       this.mSpeedTest = new SpeedTest(servers);
       this.mSpeedTest.startTest(new INetImpl());
       this.getApplicationContext();
   }




}
