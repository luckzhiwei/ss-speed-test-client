package com.vecent.ssspeedtest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.vecent.ssspeedtest.adpater.SSServerAdapter;
import com.vecent.ssspeedtest.dao.DaoManager;
import com.vecent.ssspeedtest.dao.SSServer;
import com.vecent.ssspeedtest.greendao.DaoSession;

import java.util.List;


public class MainActivity extends AppCompatActivity {


    private ListView serverList;
    private Handler mHandler;
    private SSServerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        this.serverList = (ListView) this.findViewById(R.id.common_list_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mHandler == null) mHandler = new Handler(Looper.getMainLooper());
        lodaData();
    }

    private void lodaData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DaoSession daoSession = DaoManager.getInstance(getApplicationContext()).getDaoSession();
                final List<SSServer> ssServerList = daoSession.getSSServerDao().loadAll();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new SSServerAdapter(getApplicationContext(),
                                R.layout.ss_server_item_layout, ssServerList);
                        serverList.setAdapter(adapter);

                    }
                });
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
