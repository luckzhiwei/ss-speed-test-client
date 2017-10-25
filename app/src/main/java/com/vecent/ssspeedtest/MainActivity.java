package com.vecent.ssspeedtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.vecent.ssspeedtest.controller.SpeedTestActivity;
import com.vecent.ssspeedtest.controller.TestSSLocalActivity;

public class MainActivity extends AppCompatActivity {

    private Button testSpeedBtn;
    private Button predictBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        this.testSpeedBtn = (Button) this.findViewById(R.id.main_test_speed_btn);
        this.predictBtn = (Button) this.findViewById(R.id.predict_btn);
        this.testSpeedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SpeedTestActivity.class));
            }
        });
        this.predictBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TestSSLocalActivity.class));

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
