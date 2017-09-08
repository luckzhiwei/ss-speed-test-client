package com.vecent.ssspeedtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vecent.ssspeedtest.model.INetImpl;
import com.vecent.ssspeedtest.model.SpeedTest;
import com.vecent.ssspeedtest.util.Constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button testSpeedBtn;
    private SpeedTest mSpeedTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initModel();
    }

    private void initView() {
        this.testSpeedBtn = (Button) this.findViewById(R.id.main_test_speed_btn);
        this.testSpeedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSpeedTest.startTest(new INetImpl());
            }
        });
    }

    private void initModel() {
        ArrayList<String> servers = new ArrayList<>();
        servers.add("taobao.com");
        servers.add("baidu.com");
        servers.add("www.google.com.hk");
        this.mSpeedTest = new SpeedTest(servers);
    }
}
