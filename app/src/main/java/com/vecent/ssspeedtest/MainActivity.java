package com.vecent.ssspeedtest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.vecent.ssspeedtest.adpater.SSServerAdapter;
import com.vecent.ssspeedtest.aidl.ISpeedTestInterface;
import com.vecent.ssspeedtest.aidl.ITestFinishListener;
import com.vecent.ssspeedtest.controller.ShowWebPageActivity;
import com.vecent.ssspeedtest.controller.AppConigActivity;
import com.vecent.ssspeedtest.controller.SpeedTestActivity;
import com.vecent.ssspeedtest.dao.SSServer;
import com.vecent.ssspeedtest.model.SSServers;
import com.vecent.ssspeedtest.service.SpeedTestService;
import com.vecent.ssspeedtest.util.Constant;
import com.vecent.ssspeedtest.view.EditSSServerSettingDialog;
import com.vecent.ssspeedtest.view.MoveWithFingerImageView;
import com.vecent.ssspeedtest.util.ScannerUtil;


public class MainActivity extends AppCompatActivity {

    private ListView contentListView;
    private Handler mHandler;
    private MoveWithFingerImageView addServerImg;
    private SSServerAdapter adapter;
    private DrawerLayout mDrawerLayout;
    private SSServers mSServers;
    private ImageView getGradeImg;
    private ProgressBar progressBarBackground;
    private ISpeedTestInterface iSpeedTestInterface;
    private ITestFinishListener iTestFinishListener = new ITestFinishListenerImpl();
    private RelativeLayout serverConfigLayout;
    private RelativeLayout appConfigLayout;
    private RelativeLayout helpLayout;
    private RelativeLayout aboutLayout;
    private Toolbar mToolBar;

    public static final int REQUEST_CODE = 1;

    private ServiceConnection speedTestServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iSpeedTestInterface = ISpeedTestInterface.Stub.asInterface(iBinder);
            try {
                iSpeedTestInterface.startTest(iTestFinishListener);
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
        public void onConfirm(int pos, SSServer server) {
            if (pos == -1) {
                mSServers.add(server);
            } else {
                mSServers.update(pos, server);
            }
        }

        @Override
        public void onCacnel(int pos, SSServer server) {
            if (server != null && pos != -1) {
                mSServers.remove(pos, server);
            }
        }
    };

    private SSServers.OnDataChangedListener mSsServersDataListener = new SSServers.OnDataChangedListener() {
        @Override
        public void onDataInit() {
            updateListView();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    initService();
                }
            });
        }

        @Override
        public void onDataRemove(int pos) {
            updateListView();
        }

        @Override
        public void onDataAdd() {
            updateListView();
        }

        @Override
        public void onDataUpdate(final int pos, final SSServer server) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    updateSSServerItem(pos, server);
                }
            });
        }

        @Override
        public void onAllDataUpdate() {
            updateListView();
        }

        @Override
        public void onFininshForUnitTest() {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mSServers.init();
    }

    private void initService() {
        Intent intent = new Intent().setClass(getApplicationContext(), SpeedTestService.class);
        startService(intent);
        bindService(new Intent().setClass(getApplicationContext(), SpeedTestService.class), speedTestServiceConnection, BIND_AUTO_CREATE);
    }

    private class ITestFinishListenerImpl extends ITestFinishListener.Stub {
        @Override
        public void onTestFinish(final boolean isRealRunning) throws RemoteException {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    getGradeImg.setVisibility(View.VISIBLE);
                    progressBarBackground.setVisibility(View.GONE);
                    if (isRealRunning) {
                        Toast.makeText(getApplicationContext(), R.string.back_test_finish_toast_msg, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public void onOneItemFinish(final long id, final int grade) throws RemoteException {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mSServers.updateServerGrade(id, grade);
                }
            });
        }

        @Override
        public void onTestStart() throws RemoteException {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mSServers.clearAllGrade();
                    getGradeImg.setVisibility(View.GONE);
                    progressBarBackground.setVisibility(View.VISIBLE);
                }
            });
        }
    }


    private void initView() {
        this.addServerImg = this.findViewById(R.id.add_ss_server_img);
        this.addServerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //jumptoscancode
                ScannerUtil.Companion.jumpToScanAcivity(MainActivity.this);

            }
        });
        if (mHandler == null) mHandler = new Handler(Looper.getMainLooper());
        initListView();
        initActionBar();
        initMenuLayout();
        initDrawerLayout();
    }


    private void initListView() {

        this.contentListView = (ListView) this.findViewById(R.id.common_list_view);
        this.mSServers = new SSServers(this);
        this.mSServers.setOnDataChangedListener(mSsServersDataListener);
        adapter = new SSServerAdapter(MainActivity.this,
                R.layout.ss_server_item_layout, mSServers.getData(), mOnDialogChangeListener);
        contentListView.setAdapter(adapter);
    }


    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        View cutsomView = LayoutInflater.from(this).inflate(R.layout.action_bar_main_activity, null);
        actionBar.setCustomView(cutsomView);
        this.getGradeImg = cutsomView.findViewById(R.id.img_get_grade);
        actionBar.setDisplayShowCustomEnabled(true);
        this.progressBarBackground = cutsomView.findViewById(R.id.progress_background);
        this.progressBarBackground.setVisibility(View.GONE);
        this.getGradeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    iSpeedTestInterface.startTest(iTestFinishListener);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        this.mToolBar = cutsomView.findViewById(R.id.action_bar_root);
    }

    private void initMenuLayout() {
        this.aboutLayout = (RelativeLayout) this.findViewById(R.id.layout_about);
        this.helpLayout = (RelativeLayout) this.findViewById(R.id.layout_help);
        this.serverConfigLayout = (RelativeLayout) this.findViewById(R.id.layout_server_config);
        this.appConfigLayout = (RelativeLayout) this.findViewById(R.id.layout_app_config);
        this.serverConfigLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawers();
            }
        });
        this.appConfigLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent().setClass(getApplicationContext(), AppConigActivity.class));
            }
        });
        this.aboutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShowWebPageActivity.class);
                intent.putExtra("url", Constant.ABOUT_URL);
                startActivity(intent);
            }
        });
        this.helpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShowWebPageActivity.class);
                intent.putExtra("url", Constant.FAQ_URL);
                startActivity(intent);
            }
        });
    }

    private void initDrawerLayout() {
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                this.mDrawerLayout, this.mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        this.mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SpeedTestActivity.REQUSET_CODE) {
            if (resultCode == SpeedTestActivity.TEST_FINISHED) {
                int pos = data.getIntExtra("pos", -1);
                SSServer server = data.getParcelableExtra("ssServer");
                if (pos != -1 && server != null) {
                    mSServers.updateServerScore(pos, server.getScore());
                }
            }
        } else if (requestCode == IntentIntegrator.REQUEST_CODE) {
            SSServer ssServer2Add = ScannerUtil.Companion.parseScannerResult(requestCode, resultCode, data);
            if (ssServer2Add != null) {
                EditSSServerSettingDialog dialog = new EditSSServerSettingDialog(this, ssServer2Add);
                dialog.setOnDialogChange(mOnDialogChangeListener);
                dialog.show();
                dialog.setWindowAttr(this.getWindowManager());
            } else {
                Toast.makeText(getApplicationContext(), "code is invaild", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * local refresh
     */
    private void updateSSServerItem(int pos, SSServer server) {
        if (pos <= contentListView.getLastVisiblePosition() && pos >= contentListView.getFirstVisiblePosition()) {
            View view = contentListView.getChildAt(pos - contentListView.getFirstVisiblePosition());
            adapter.updateView(view, server, pos);
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }


    private void updateListView() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(speedTestServiceConnection);
    }


}
