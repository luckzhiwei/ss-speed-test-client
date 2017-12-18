package com.vecent.ssspeedtest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.vecent.ssspeedtest.adpater.SSServerAdapter;
import com.vecent.ssspeedtest.aidl.ISpeedTestInterface;
import com.vecent.ssspeedtest.aidl.ITestFinishListener;
import com.vecent.ssspeedtest.controller.InputSSServerSettingActivity;
import com.vecent.ssspeedtest.controller.ServiceSpeedResultActivity;
import com.vecent.ssspeedtest.controller.SpeedTestActivity;
import com.vecent.ssspeedtest.dao.DaoManager;
import com.vecent.ssspeedtest.dao.SSServer;
import com.vecent.ssspeedtest.greendao.DaoSession;
import com.vecent.ssspeedtest.model.bean.TotalSpeedTestResult;
import com.vecent.ssspeedtest.service.SpeedTestService;


import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ListView contentListView;
    private Handler mHandler;
    private ImageView addServerImg;
    private SSServerAdapter adapter;
    private List<SSServer> ssServerList;
    private TextView pleaseAddTextView;
    private ImageView pleaseAddImageView;
    private ISpeedTestInterface iSpeedTestInterface;
    private ITestFinishListener iTestFinishListener = new ITestFinishListenerImpl();


    private ServiceConnection speedTestServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iSpeedTestInterface = ISpeedTestInterface.Stub.asInterface(iBinder);
            try {
                iSpeedTestInterface.startTest();
                iSpeedTestInterface.setOnTestFinishListener(iTestFinishListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            iSpeedTestInterface = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setCustomView(R.layout.action_bar_main_activity);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        initView();
        initService();
    }


    private void initService() {
        Intent intent = new Intent().setClass(getApplicationContext(), SpeedTestService.class);
        startService(intent);
        bindService(new Intent().setClass(getApplicationContext(), SpeedTestService.class), speedTestServiceConnection, BIND_AUTO_CREATE);
    }

    private class ITestFinishListenerImpl extends ITestFinishListener.Stub {
        @Override
        public void onTestFinish(List<TotalSpeedTestResult> results) throws RemoteException {
            mHandler.post(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    }

    private void initView() {
        this.contentListView = (ListView) this.findViewById(R.id.common_list_view);
        this.addServerImg = (ImageView) this.findViewById(R.id.add_ss_server_img);
        this.pleaseAddTextView = (TextView) this.findViewById(R.id.plead_add_textview);
        this.pleaseAddImageView = (ImageView) this.findViewById(R.id.plead_add_img);
        this.addServerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), InputSSServerSettingActivity.class));
            }
        });
        this.contentListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                deleteServer(ssServerList.get(i));
                return true;
            }
        });
        this.contentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("ssServer", ssServerList.get(i));
                intent.setClass(getApplicationContext(), SpeedTestActivity.class);
                startActivity(intent);
            }
        });
    }

    private void goBackGroundResult() {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), ServiceSpeedResultActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mHandler == null) mHandler = new Handler(Looper.getMainLooper());
        loadData();
    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DaoSession daoSession = DaoManager.getInstance(getApplicationContext()).getDaoSession();
                ssServerList = daoSession.getSSServerDao().loadAll();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new SSServerAdapter(MainActivity.this,
                                R.layout.ss_server_item_layout, ssServerList);
                        contentListView.setAdapter(adapter);
                        pleaseAddImageView.setVisibility(View.GONE);
                        pleaseAddTextView.setVisibility(View.GONE);
                        if (ssServerList.size() == 0) {
                            pleaseAddImageView.setVisibility(View.VISIBLE);
                            pleaseAddTextView.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }).start();
    }

    private void deleteServer(SSServer server) {
        DaoSession daoSession = DaoManager.getInstance(getApplicationContext()).getDaoSession();
        daoSession.getSSServerDao().delete(server);
        loadData();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
