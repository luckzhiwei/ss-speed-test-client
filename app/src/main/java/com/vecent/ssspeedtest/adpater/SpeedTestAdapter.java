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
    public void convert(ViewHolder holder, SpeedTestResult result, int post) {
        LinearLayout container = holder.getView(R.id.speed_test_layout_container);
        if (container.getChildCount() == 0) {
            this.initView(container, mContext.getResources());
        }
        this.setCommonInfo(container, result, res);
        if (result.isExceptionOccured()) {
            this.setExceptionInfo(container, result, res);
        } else {
            this.setNormalInfo(container, result, res);
        }
    }

    private void initView(ViewGroup container, Resources res) {
        this.addKVView(container, res.getString(R.string.server_address), "");
        this.addKVView(container, res.getString(R.string.redirect_address), "");
        this.addKVView(container, res.getString(R.string.request_result), "");

        this.addKVView(container, res.getString(R.string.exception_msg), "");
        this.addKVView(container, res.getString(R.string.is_url_wrong), "");
        this.addKVView(container, res.getString(R.string.is_timed_out), "");

        this.addKVView(container, res.getString(R.string.response_byte_size), "");
        this.addKVView(container, res.getString(R.string.time_used), "");
        this.addKVView(container, res.getString(R.string.dowload_speed), "");
    }

    private void addKVView(ViewGroup view, String key, String value) {
        view.addView(new KeyValueView(mContext).setKey(key)
                .setValue(value));
    }


    private void setCommonInfo(ViewGroup view, SpeedTestResult result, Resources res) {
        ((KeyValueView) view.getChildAt(0)).setValue(result.getRequestServer());
        String redirectServer = result.isRedirect() ? result.getRedirectServer() : res.getString(R.string.empty);
        ((KeyValueView) view.getChildAt(1)).setValue(redirectServer);
        String requestResult = result.isExceptionOccured() ? res.getString(R.string.execption_occured) : res.getString(R.string.result_ok);
        ((KeyValueView) view.getChildAt(2)).setValue(requestResult);
    }

    private void setExceptionInfo(ViewGroup view, SpeedTestResult result, Resources res) {
        ((KeyValueView) view.getChildAt(3)).setValue(result.getExceptionMsg());
        String isUrlWrong = result.isUrlWrong() ? res.getString(R.string.yes) : res.getString(R.string.no);
        ((KeyValueView) view.getChildAt(4)).setValue(isUrlWrong);
        String isTimedOut = result.isTimedOut() ? res.getString(R.string.yes) : res.getString(R.string.no);
        ((KeyValueView) view.getChildAt(5)).setValue(isTimedOut);
        this.hidenNormalInfo(view);
        this.showExceptionInfo(view);
    }

    private void setNormalInfo(ViewGroup view, SpeedTestResult result, Resources res) {
        ((KeyValueView) view.getChildAt(6)).setValue(result.getTotalSize() + "");
        ((KeyValueView) view.getChildAt(7)).setValue(result.getTimeUsed() + "");
        ((KeyValueView) view.getChildAt(8)).setValue(result.getDownLoadSpeed() + res.getString(R.string.kb_by_second));
        this.hidenExceptionInfo(view);
        this.showNormalInfo(view);
    }


    private void hidenExceptionInfo(ViewGroup container) {
        for (int i = 3; i <= 5; i++) {
            ((KeyValueView) container.getChildAt(i)).hidden();
        }
    }

    private void hidenNormalInfo(ViewGroup container) {
        for (int i = 6; i <= 8; i++) {
            ((KeyValueView) container.getChildAt(i)).hidden();
        }
    }

    private void showNormalInfo(ViewGroup container) {
        for (int i = 6; i <= 8; i++) {
            ((KeyValueView) container.getChildAt(i)).show();
        }
    }

    private void showExceptionInfo(ViewGroup container) {
        for (int i = 3; i <= 5; i++) {
            ((KeyValueView) container.getChildAt(i)).show();
        }
    }

}
