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

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vecent.ssspeedtest.adpater.SSServerAdapter;
import com.vecent.ssspeedtest.aidl.ISpeedTestInterface;
import com.vecent.ssspeedtest.aidl.ITestFinishListener;
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
    private ProgressBar progressBarBackground;
    private ISpeedTestInterface iSpeedTestInterface;
    private ITestFinishListener iTestFinishListener = new ITestFinishListenerImpl();

    public static final int REQUEST_CODE = 1;

    private ServiceConnection speedTestServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iSpeedTestInterface = ISpeedTestInterface.Stub.asInterface(iBinder);
            try {
                iSpeedTestInterface.startTest();
                iSpeedTestInterface.setOnTestFinishListener(iTestFinishListener);
                if (iSpeedTestInterface.isTestRuning()) {
                    getGradeImg.setVisibility(View.GONE);
                    progressBarBackground.setVisibility(View.VISIBLE);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            iSpeedTestInterface = null;
        }
    };

    private EditSSServerSettingDialog.OnDialogChange mOnDialogChangeListener = new EditSSServerSettingDialog.OnDialogChange() {
        @Override
        public void onConfirm() {
            loadData();
        }

        @Override
        public void onCacnel(SSServer server) {
            if (server != null) {
                deleteServer(server);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initService();
        loadData();
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
                    getGradeImg.setVisibility(View.VISIBLE);
                    progressBarBackground.setVisibility(View.GONE);
                }
            });
        }

        @Override
        public void onTestStart() throws RemoteException {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    getGradeImg.setVisibility(View.GONE);
                    progressBarBackground.setVisibility(View.VISIBLE);
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
                dialog.setOnDialogChange(mOnDialogChangeListener);
                dialog.show();
                dialog.setWindowAttr(getWindowManager());
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
        this.progressBarBackground = cutsomView.findViewById(R.id.progress_background);
        this.progressBarBackground.setVisibility(View.GONE);
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


    public void loadData() {
        if (mHandler == null) mHandler = new Handler(Looper.getMainLooper());
        new Thread(new Runnable() {
            @Override
            public void run() {
                DaoSession daoSession = DaoManager.getInstance(getApplicationContext()).getDaoSession();
                ssServerList = daoSession.getSSServerDao().loadAll();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new SSServerAdapter(MainActivity.this,
                                R.layout.ss_server_item_layout, ssServerList, mOnDialogChangeListener);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == SpeedTestActivity.TEST_FINISHED) {
            int pos = data.getIntExtra("pos", -1);
            SSServer server = data.getParcelableExtra("ssServer");
            if (pos != -1 && server != null) {
                updateSSServerList(pos, server);
            }
        }
    }

    private void updateSSServerList(int pos, SSServer server) {
        this.ssServerList.set(pos, server);
        if (pos <= contentListView.getLastVisiblePosition() && pos >= contentListView.getFirstVisiblePosition()) {
            View view = contentListView.getChildAt(pos - contentListView.getFirstVisiblePosition());
            adapter.updateView(view, server);
        }
    }

}
