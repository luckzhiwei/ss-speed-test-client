package com.vecent.ssspeedtest.adpater;

import android.content.Context;
import android.content.res.Resources;
import android.widget.TextView;

import com.vecent.ssspeedtest.R;
import com.vecent.ssspeedtest.model.bean.TotalSpeedTestResult;
import com.vecent.ssspeedtest.view.ResultLayout;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by zhiwei on 2017/11/21.
 */

public class ServiceResultAdapter extends CommonAdapter<TotalSpeedTestResult> {

    public ServiceResultAdapter(Context context, int layoutId, List<TotalSpeedTestResult> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, TotalSpeedTestResult item, int position) {
        TextView addr2Test = viewHolder.getView(R.id.server_2_test_addr);
        TextView whiteAddrSuccessRatio = viewHolder.getView(R.id.textview_white_server_ratio);
        TextView blackAddrSuccessRatio = viewHolder.getView(R.id.textview_black_server_ratio);
        TextView whiteAddrSpeedAvg = viewHolder.getView(R.id.textview_white_server_avg_speed);
        TextView blackAddrSpeedAvg = viewHolder.getView(R.id.textview_black_speed_avg);
        Resources res = mContext.getResources();
        addr2Test.setText(item.getServer2TestAddr());
        whiteAddrSuccessRatio.setText(res.getString(R.string.white_list_success_rate) + " : " + item.getWhiteAddrConnectSuccesRate());
        whiteAddrSpeedAvg.setText(res.getString(R.string.white_avg_speed) + " : " + item.getSpeedWhiteAddrDownLoadAvg() + res.getString(R.string.kb_by_second));
        blackAddrSuccessRatio.setText(res.getString(R.string.black_list_success_rate) + " : " + item.getBlackAddrConnectSuccesRate());
        blackAddrSpeedAvg.setText(res.getString(R.string.black_avg_speed) + " : " + item.getSpeedBlackAddrDownLoadAvg() + res.getString(R.string.kb_by_second));
    }
}
