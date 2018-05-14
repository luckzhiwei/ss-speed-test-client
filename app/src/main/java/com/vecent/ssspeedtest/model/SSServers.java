package com.vecent.ssspeedtest.model;

import android.content.Context;

import com.vecent.ssspeedtest.dao.DaoManager;
import com.vecent.ssspeedtest.dao.SSServer;
import com.vecent.ssspeedtest.greendao.DaoSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zhiwei on 2018/5/7.
 */

public class SSServers {

    private DaoSession mDaoSession;
    private List<SSServer> mData;
    private OnDataChangedListener mListener;

    public interface OnDataChangedListener {
        void onDataInit();

        void onDataRemove(int pos);

        void onDataAdd();

        void onDataUpdate(int pos, SSServer server);

        void onAllDataUpdate();
    }

    public SSServers(Context context) {
        this.mData = new ArrayList<>();
        this.mDaoSession = DaoManager.getInstance(context).getDaoSession();
    }

    public void init() {
        mDaoSession.getSSServerDao().
                rx().
                loadAll().
                observeOn(Schedulers.newThread()).
                doOnNext(new Action1<List<SSServer>>() {
                    @Override
                    public void call(List<SSServer> ssServers) {
                        mData.clear();
                        mData.addAll(ssServers);
                    }
                }).
                subscribeOn(AndroidSchedulers.mainThread()).
                subscribe(new Action1<List<SSServer>>() {
                    @Override
                    public void call(List<SSServer> ssServers) {
                        if (mListener != null) {
                            mListener.onDataInit();
                        }
                    }
                });
    }


    public void remove(final int pos, final SSServer server) {
        mDaoSession.getSSServerDao().
                rx().
                delete(server).
                observeOn(Schedulers.newThread()).
                subscribeOn(AndroidSchedulers.mainThread()).
                doOnNext(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mData.remove(pos);
                    }
                }).
                subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (mListener != null) {
                            mListener.onDataRemove(pos);
                        }
                    }
                });

    }

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        this.mListener = listener;
    }

    public void add(final SSServer serverAdd) {
        mDaoSession.getSSServerDao().
                rx().
                insert(serverAdd).
                observeOn(Schedulers.newThread()).
                subscribeOn(AndroidSchedulers.mainThread()).
                doOnNext(new Action1<SSServer>() {
                    @Override
                    public void call(SSServer server) {
                        mData.add(server);
                    }
                }).
                subscribe(new Action1<SSServer>() {
                    @Override
                    public void call(SSServer server) {
                        if (mListener != null) {
                            mListener.onDataAdd();
                        }
                    }
                });
    }

    public void update(final int pos, final SSServer server) {
        mDaoSession.getSSServerDao().
                rx().
                update(server).
                observeOn(Schedulers.newThread()).
                subscribeOn(AndroidSchedulers.mainThread()).
                doOnNext(new Action1<SSServer>() {
                    @Override
                    public void call(SSServer server) {
                        mData.set(pos, server);
                    }
                }).
                subscribe(new Action1<SSServer>() {
                    @Override
                    public void call(SSServer server) {
                        if (mListener != null) {
                            mListener.onDataUpdate(pos, server);
                        }
                    }
                });
    }


    //更新测评分数(黑白名单)
    public void updateServerGrade(final long id, final int grade) {
        SSServer target = new SSServer();
        target.setId(id);
        Observable.
                just(target).
                map(new Func1<SSServer, Integer>() {
                    @Override
                    public Integer call(SSServer target) {
                        target.setId(id);
                        return Collections.binarySearch(mData, target, new Comparator<SSServer>() {
                            @Override
                            public int compare(SSServer ssServer, SSServer t1) {
                                return ssServer.getId().compareTo(t1.getId());
                            }
                        });

                    }
                }).doOnNext(new Action1<Integer>() {
            @Override
            public void call(Integer pos) {
                SSServer target = mData.get(pos);
                target.setGrade(grade);
                mDaoSession.getSSServerDao().update(mData.get(pos));
            }
        }).observeOn(Schedulers.newThread()).
                subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer pos) {
                        if (mListener != null) {
                            mListener.onDataUpdate(pos, mData.get(pos));
                        }
                    }
                });
    }

    //更新跑分
    public void updateServerScore(final int pos, final int score) {
        mData.get(pos).setScore(score);
        mDaoSession.getSSServerDao().
                rx().
                update(mData.get(pos)).
                observeOn(Schedulers.newThread()).
                subscribeOn(AndroidSchedulers.mainThread()).
                subscribe(new Action1<SSServer>() {
                    @Override
                    public void call(SSServer server) {
                        if (mListener != null) {
                            mListener.onDataUpdate(pos, mData.get(pos));
                        }
                    }
                });
    }

    public void clearAllGrade() {
        for (SSServer server : mData) {
            server.setGrade(-1);
        }
        mDaoSession.getSSServerDao().rx().updateInTx(mData)
                .observeOn(Schedulers.newThread()).
                subscribeOn(AndroidSchedulers.mainThread()).
                subscribe(new Action1<Iterable<SSServer>>() {
                    @Override
                    public void call(Iterable<SSServer> ssServers) {
                        if (mListener != null) {
                            mListener.onAllDataUpdate();
                        }
                    }
                });

    }


    public List<SSServer> getData() {
        return mData;
    }


}
