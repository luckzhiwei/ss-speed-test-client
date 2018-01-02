package com.vecent.ssspeedtest.controller;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.vecent.ssspeedtest.R;
import com.vecent.ssspeedtest.aidl.ISpeedTestInterface;
import com.vecent.ssspeedtest.service.SpeedTestService;

/**
 * Created by zhiwei on 2017/12/30.
 */

public class AppConigActivity extends AppCompatActivity {

    private Switch autoTestSwitch;
    private Switch onlyWifiTestSwitch;
    private ISpeedTestInterface iSpeedTestInterface;

    private ServiceConnection configServerConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iSpeedTestInterface = ISpeedTestInterface.Stub.asInterface(iBinder);
            try {
                autoTestSwitch.setChecked(iSpeedTestInterface.getAllowTestRuning());
                onlyWifiTestSwitch.setChecked(iSpeedTestInterface.getOnlyWifiTest());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.app_config_layout);
        initView();
        initService();
    }

    private void initService() {
        bindService(new Intent().setClass(getApplicationContext(), SpeedTestService.class), configServerConnection, BIND_AUTO_CREATE);
    }

    private void initView() {
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.action_bar_common);
        this.autoTestSwitch = (Switch) this.findViewById(R.id.switch_auto_test);
        this.onlyWifiTestSwitch = (Switch) this.findViewById(R.id.switch_only_wifi_test);
        this.autoTestSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try {
                    iSpeedTestInterface.setAllowTestRunning(b);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.onlyWifiTestSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try {
                    iSpeedTestInterface.setOnlyWifiTest(b);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
