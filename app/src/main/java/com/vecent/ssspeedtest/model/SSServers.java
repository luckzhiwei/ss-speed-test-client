package com.vecent.ssspeedtest.model;

import android.content.Context;

import com.vecent.ssspeedtest.dao.DaoManager;
import com.vecent.ssspeedtest.dao.SSServer;
import com.vecent.ssspeedtest.greendao.DaoSession;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

        void onDataAdd();

        void onDataUpdate(int pos, SSServer server);

        void onAllDataUpdate();
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

    public void add(final SSServer serverAdd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DaoSession daoSession = DaoManager.getInstance(context).getDaoSession();
                daoSession.getSSServerDao().insert(serverAdd);
                mData.add(serverAdd);
                if (mListener != null) {
                    mListener.onDataAdd();
                }
            }
        }).start();
    }

    public void update(final int pos, final SSServer server) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DaoSession daoSession = DaoManager.getInstance(context).getDaoSession();
                daoSession.getSSServerDao().update(server);
                mData.set(pos, server);
                if (mListener != null) {
                    mListener.onDataUpdate(pos, server);
                }
            }
        }).start();
    }

    //更新测评分数(黑白名单)
    public void updateServerGrade(final long id, final int grade) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SSServer target = new SSServer();
                target.setId(id);
                int pos = Collections.binarySearch(mData, target, new Comparator<SSServer>() {
                    @Override
                    public int compare(SSServer ssServer, SSServer t1) {
                        return ssServer.getId().compareTo(t1.getId());
                    }
                });
                if (pos == -1) return;
                mData.get(pos).setGrade(grade);
                DaoSession daoSession = DaoManager.getInstance(context).getDaoSession();
                daoSession.getSSServerDao().update(mData.get(pos));
                if (mListener != null) {
                    mListener.onDataUpdate(pos, mData.get(pos));
                }
            }
        }).start();
    }

    //更新跑分
    public void updateServerScore(final int pos, final int score) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mData.get(pos).setScore(score);
                DaoSession daoSession = DaoManager.getInstance(context).getDaoSession();
                daoSession.getSSServerDao().update(mData.get(pos));
                if (mListener != null) {
                    mListener.onDataUpdate(pos, mData.get(pos));
                }
            }
        }).start();
    }

    public void clearAllGrade() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mData != null) {
                    for (SSServer server : mData) {
                        server.setGrade(-1);
                    }
                    DaoSession daoSession = DaoManager.getInstance(context).getDaoSession();
                    daoSession.getSSServerDao().updateInTx(mData);
                    if (mListener != null) {
                        mListener.onAllDataUpdate();
                    }
                }
            }
        }).start();
    }


    public List<SSServer> getData() {
        return mData;
    }


}
