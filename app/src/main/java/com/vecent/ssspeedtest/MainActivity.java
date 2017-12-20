package com.vecent.ssspeedtest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

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
import com.vecent.ssspeedtest.view.EditSSServerSettingDialog;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ListView contentListView;
    private Handler mHandler;
    private ImageView addServerImg;
    private SSServerAdapter adapter;
    private List<SSServer> ssServerList;
    private DrawerLayout mDrawerLayout;
    private ImageView menuImg;
    private ImageView getGradeImg;
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

    private EditSSServerSettingDialog.OnDialogChange onDialogChange = new EditSSServerSettingDialog.OnDialogChange() {
        @Override
        public void onConfirm() {
            loadData();
        }

        @Override
        public void onCacnel(SSServer server) {
            if (server != null) {
                loadData();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        this.addServerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditSSServerSettingDialog dialog = new EditSSServerSettingDialog(MainActivity.this, null);
                dialog.setOnDialogChange(onDialogChange);
                dialog.show();
                dialog.setWindowAttr(getWindowManager());
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
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        initActionBar();
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        View cutsomView = LayoutInflater.from(this).inflate(R.layout.action_bar_main_activity, null);
        actionBar.setCustomView(cutsomView);
        this.menuImg = cutsomView.findViewById(R.id.img_menu);
        this.getGradeImg = cutsomView.findViewById(R.id.img_get_grade);
        actionBar.setDisplayShowCustomEnabled(true);
        this.menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
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
                ssServerList.add(0, new SSServer());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new SSServerAdapter(MainActivity.this,
                                R.layout.ss_server_item_layout, ssServerList);
                        contentListView.setAdapter(adapter);
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

}
