package com.vecent.ssspeedtest.adpater;

import android.content.Context;
import android.content.res.Resources;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vecent.ssspeedtest.R;
import com.vecent.ssspeedtest.model.bean.SpeedTestResult;
import com.vecent.ssspeedtest.view.KeyValueView;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by lzw on 17-9-11.
 */

public class SpeedTestAdapter extends CommonAdapter<SpeedTestResult> {

    private Resources res;

    public SpeedTestAdapter(Context context, final int layoutId, List<SpeedTestResult> datas) {
        super(context, layoutId, datas);
        res = context.getResources();
    }

    @Override
    public void convert(ViewHolder holder, SpeedTestResult ret, int post) {
        LinearLayout container = holder.getView(R.id.speed_test_layout_container);
        if (container.getChildCount() == 0) {
            this.initView(container, mContext.getResources());
        } else {
            this.setServerNameItem(container, ret.getServerToTest());
            if (ret.getExecRetCode() == 0) {
                this.setStatus(container, res.getString(R.string.ping_avaliable));
                this.setRetContent(container, ret);
                this.show(container);
            } else {
                this.setStatus(container, res.getString(R.string.ping_unavaliable));
                this.hiden(container);
            }
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
        view.addView(new KeyValueView(mContext).setKey(key)
                .setValue(value));
    }

    private void setServerNameItem(ViewGroup view, String serverName) {
        ((KeyValueView) view.getChildAt(0)).setValue(serverName);
    }

    private void hiden(ViewGroup container) {
        for (int i = 2; i < container.getChildCount(); i++) {
            ((KeyValueView) container.getChildAt(i)).hidden();
        }
    }

    private void show(ViewGroup container) {
        for (int i = 2; i < container.getChildCount(); i++) {
            ((KeyValueView) container.getChildAt(i)).show();
        }
    }

    private void setRetContent(ViewGroup container, SpeedTestResult result) {
        ((KeyValueView) container.getChildAt(2)).setValue(result.getTotalPackets() + "");
        ((KeyValueView) container.getChildAt(3)).setValue(result.getLossRate() + "");
        ((KeyValueView) container.getChildAt(4)).setValue(result.getTimeMin() + "");
        ((KeyValueView) container.getChildAt(5)).setValue(result.getTimeMax() + "");
        ((KeyValueView) container.getChildAt(6)).setValue(result.getTimeAvg() + "");
    }

    private void setStatus(ViewGroup container, String pingRet) {
        ((KeyValueView) container.getChildAt(1)).setValue(pingRet);
    }


}
