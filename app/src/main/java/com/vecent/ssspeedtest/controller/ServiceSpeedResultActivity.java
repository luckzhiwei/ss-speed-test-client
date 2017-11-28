package com.vecent.ssspeedtest.controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.vecent.ssspeedtest.R;
import com.vecent.ssspeedtest.adpater.ServiceResultAdapter;
import com.vecent.ssspeedtest.dao.DaoManager;
import com.vecent.ssspeedtest.greendao.DaoSession;
import com.vecent.ssspeedtest.model.bean.TotalSpeedTestResult;
import com.vecent.ssspeedtest.util.LogUtil;

import java.util.List;

/**
 * Created by zhiwei on 2017/11/21.
 */

public class ServiceSpeedResultActivity extends Activity {


    private ListView contentListView;
    private List<TotalSpeedTestResult> mResultList;
    private ImageView imgEmptyView;
    private TextView textViewEmpty;
    private ServiceResultAdapter mAdapter;

    @Override
    public void onCreate(Bundle saveInstance) {
        super.onCreate(saveInstance);
        this.setContentView(R.layout.service_result_layout);
        initView();
        initData();
    }

    private void initView() {
        contentListView = this.findViewById(R.id.common_list_view);
        imgEmptyView = this.findViewById(R.id.empty_img);
        textViewEmpty = this.findViewById(R.id.empty_textview);
    }

    private void initData() {
        DaoSession session = DaoManager.getInstance(this).getDaoSession();
        mResultList = session.getTotalSpeedTestResultDao().loadAll();
        if (mResultList != null) {
            if (mResultList.size() == 0) {
                imgEmptyView.setVisibility(View.VISIBLE);
                textViewEmpty.setVisibility(View.VISIBLE);
                contentListView.setVisibility(View.GONE);
            } else {
                imgEmptyView.setVisibility(View.GONE);
                textViewEmpty.setVisibility(View.GONE);
                mAdapter = new ServiceResultAdapter(this, R.layout.serivce_result_item, mResultList);
                contentListView.setAdapter(mAdapter);
            }
        }
    }





}
