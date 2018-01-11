package com.vecent.ssspeedtest.controller;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.vecent.ssspeedtest.R;
import com.vecent.ssspeedtest.aidl.ISpeedTestInterface;
import com.vecent.ssspeedtest.service.SpeedTestService;
import com.vecent.ssspeedtest.util.Constant;
import com.vecent.ssspeedtest.view.ListPopWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhiwei on 2017/12/30.
 */

public class AppConigActivity extends AppCompatActivity {

    private Switch autoTestSwitch;
    private Switch onlyWifiTestSwitch;
    private ISpeedTestInterface iSpeedTestInterface;
    private TextView timeIntervalTextView;
    private List<String> timeData;
    private ListPopWindow mListPopWindow;

    private ServiceConnection configServerConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iSpeedTestInterface = ISpeedTestInterface.Stub.asInterface(iBinder);
            try {
                autoTestSwitch.setChecked(iSpeedTestInterface.getAllowTestRuning());
                onlyWifiTestSwitch.setChecked(iSpeedTestInterface.getOnlyWifiTest());
                initTimeIntervalContent(iSpeedTestInterface.getTimeInterval());
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
        initData();
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
        this.timeIntervalTextView = (TextView) this.findViewById(R.id.textview_interval_time);
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
        this.timeIntervalTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeIntervalSetting();
            }
        });
    }

    private void initData() {
        this.timeData = new ArrayList<>();
        this.timeData.add(getString(R.string.two_min));
        this.timeData.add(getString(R.string.fifty_min));
        this.timeData.add(getString(R.string.thirty_min));
        this.timeData.add(getString(R.string.sixty_min));
        this.timeData.add(getString(R.string.three_hour));
        this.timeData.add(getString(R.string.six_hour));
        this.timeData.add(getString(R.string.one_day));
        this.mListPopWindow = new ListPopWindow(getApplicationContext(), this.timeData);
        this.mListPopWindow.setOnPopItemClickListener(new ListPopWindow.OnPopItemClick() {
            @Override
            public void onItemSelected(String content) {
                timeIntervalTextView.setText(content);
                mListPopWindow.dismiss();
                try {
                    iSpeedTestInterface.setTimeInterval(switchString2Time(content));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initTimeIntervalContent(long timeInterval) {
        timeIntervalTextView.setText(swtichTime2String(timeInterval));
    }

    private long switchString2Time(String time) {
        if (time.equals(getString(R.string.two_min))) {
            return Constant.TWO_MIN;
        } else if (time.equals(getString(R.string.fifty_min))) {
            return Constant.FIFEEN_MIN;
        } else if (time.equals(getString(R.string.thirty_min))) {
            return Constant.THIRTY_MIN;
        } else if (time.equals(getString(R.string.sixty_min))) {
            return Constant.SIXTY_MIN;
        } else if (time.equals(getString(R.string.three_hour))) {
            return Constant.THREE_HOUR;
        } else if (time.equals(getString(R.string.six_hour))) {
            return Constant.SIX_HOUR;
        } else if (time.equals(getString(R.string.one_day))) {
            return Constant.ONE_DAY;
        } else {
            return Constant.FIFEEN_MIN;
        }
    }

    private String swtichTime2String(long timeInterval) {
        int id = R.string.fifty_min;
        if (timeInterval == Constant.TWO_MIN) {
            id = R.string.two_min;
        } else if (timeInterval == Constant.FIFEEN_MIN) {
            id = R.string.fifty_min;
        } else if (timeInterval == Constant.THIRTY_MIN) {
            id = R.string.thirty_min;
        } else if (timeInterval == Constant.SIXTY_MIN) {
            id = R.string.sixty_min;
        } else if (timeInterval == Constant.THREE_HOUR) {
            id = R.string.three_hour;
        } else if (timeInterval == Constant.SIX_HOUR) {
            id = R.string.six_hour;
        } else if (timeInterval == Constant.ONE_DAY) {
            id = R.string.one_day;
        }
        return getString(id);
    }

    private void showTimeIntervalSetting() {
        this.mListPopWindow.show(this.timeIntervalTextView, 120, 150);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(configServerConnection);
    }
}
