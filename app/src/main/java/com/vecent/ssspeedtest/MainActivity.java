package com.vecent.ssspeedtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.vecent.ssspeedtest.model.SpeedTest;

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
//                mSpeedTest.startTest();
                Thread a = new Thread("thread-a") {
                    @Override
                    public void run() {
                        int a = 2;
                        Log.v("thread", "thread-a");
                    }
                };

                Thread b = new Thread("thread-b") {
                    @Override
                    public void run() {
                        int b = 3;
                        Log.v("thread", "thread-b");
                    }
                };
                a.start();
                b.start();
            }
        });
    }

    private void initModel() {
        this.mSpeedTest = new SpeedTest();
    }
}
