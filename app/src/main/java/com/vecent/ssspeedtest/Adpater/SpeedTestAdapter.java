package com.vecent.ssspeedtest.Adpater;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vecent.ssspeedtest.R;
import com.vecent.ssspeedtest.model.bean.PingResult;
import com.vecent.ssspeedtest.util.LogUtil;
import com.vecent.ssspeedtest.view.KeyValueView;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by lzw on 17-9-11.
 */

public class SpeedTestAdapter extends CommonAdapter<PingResult> {

    private Resources res;

    public SpeedTestAdapter(Context context, final int layoutId, List<PingResult> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, PingResult ret, int post) {
        LinearLayout container = holder.getView(R.id.speed_test_layout_container);
        if (container.getChildCount() == 0) {
            this.initView(container, mContext.getResources());
        } else {

        }


    }

    private void initView(ViewGroup container, Resources res) {
        this.addKVView(container, res.getString(R.string.server_test), "");
        this.addKVView(container, res.getString(R.string.status), "");
        this.addKVView(container, res.getString(R.string.icmp_total_packets), "");
        this.addKVView(container, res.getString(R.string.loss_packets_rate), "");
        this.addKVView(container, res.getString(R.string.time_min_of_ping), "");
        this.addKVView(container, res.getString(R.string.time_max_of_ping), "");
        this.addKVView(container, res.getString(R.string.time_avg_of_ping), "");
    }

    private void addKVView(ViewGroup view, String key, String value) {
        view.addView(new KeyValueView(key, value, mContext).getView());
    }

}
