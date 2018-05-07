package com.vecent.ssspeedtest.model;

import android.content.Context;

import com.vecent.ssspeedtest.dao.DaoManager;
import com.vecent.ssspeedtest.dao.SSServer;
import com.vecent.ssspeedtest.greendao.DaoSession;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;

/**
 * Created by zhiwei on 2018/5/7.
 */

public class SSServers {

    private Context context;
    private List<SSServer> mData;
    private OnDataChangedListener mListener;

    public interface OnDataChangedListener {
        void onDataInit();

        void onDataRemove(int pos);
    }

    public SSServers(Context context, List<SSServer> data) {
        this.context = context;
        this.mData = data;
    }

    public void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DaoSession daoSession = DaoManager.getInstance(context).getDaoSession();
                for (SSServer server : daoSession.getSSServerDao().loadAll()) {
                    mData.add(server);
                }
                if (mListener != null) {
                    mListener.onDataInit();
                }
            }
        }).start();
    }

    public void remove(final int pos, final SSServer server) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DaoSession daoSession = DaoManager.getInstance(context).getDaoSession();
                daoSession.getSSServerDao().delete(server);
                mData.remove(pos);
                if (mListener != null) {
                    mListener.onDataRemove(pos);
                }
            }
        }).start();
    }

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        this.mListener = listener;
    }

    public List<SSServer> getData() {
        return mData;
    }


}
