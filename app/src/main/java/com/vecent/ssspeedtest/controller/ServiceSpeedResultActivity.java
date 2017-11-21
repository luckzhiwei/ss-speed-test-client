package com.vecent.ssspeedtest.controller;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.vecent.ssspeedtest.R;
import com.vecent.ssspeedtest.dao.DaoManager;
import com.vecent.ssspeedtest.greendao.DaoSession;
import com.vecent.ssspeedtest.model.bean.TotalSpeedTestResult;
import com.vecent.ssspeedtest.util.LogUtil;

import java.util.List;

/**
 * Created by zhiwei on 2017/11/21.
 */

public class ServiceSpeedResultActivity extends Activity {


    private ListView resultListView;
    private List<TotalSpeedTestResult> mResultList;
    private ImageView imgEmptyView;
    private TextView textViewEmpty;

    @Override
    public void onCreate(Bundle saveInstance) {
        super.onCreate(saveInstance);
        mResultList = this.getIntent().getParcelableArrayListExtra("backGroundResult");
        initView();
        initData();
    }

    private void initView() {
        resultListView = this.findViewById(R.id.common_list_view);
        imgEmptyView = this.findViewById(R.id.empty_img);
        textViewEmpty = this.findViewById(R.id.empty_textview);
    }

    private void initData() {
        DaoSession session = DaoManager.getInstance(this).getDaoSession();
        mResultList = session.getTotalSpeedTestResultDao().loadAll();
        LogUtil.logDebug(getClass().getName(), mResultList.size() + "");
    }

}
