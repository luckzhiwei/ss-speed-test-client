package com.vecent.ssspeedtest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vecent.ssspeedtest.R;
import com.vecent.ssspeedtest.model.bean.TotalSpeedTestResult;

/**
 * Created by lzw on 17-10-1.
 */

public class ResultLayout extends LinearLayout {

    private TextView mTextViewWhiteListServerCount;
    private TextView mTextViewBlackListServerCount;
    private TextView mTextViewWhiteListServerRatio;
    private TextView mTextViewBlackListServerRatio;
    private TextView mTextViewTotalTimeUsed;
    private TextView mTextViewTotalSize;
    private ImageView mImageClose;
    private ImageView mImageRefresh;

    public ResultLayout(Context context) {
        super(context);
        init(context);
    }

    public ResultLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ResultLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.speed_total_result_layout, this);
        this.mTextViewWhiteListServerRatio = this.findViewById(R.id.textview_white_server_ratio);
        this.mTextViewWhiteListServerCount = this.findViewById(R.id.textview_white_server_count);
        this.mTextViewBlackListServerRatio = this.findViewById(R.id.textview_black_server_ratio);
        this.mTextViewBlackListServerCount = this.findViewById(R.id.textview_black_server_count);
        this.mTextViewTotalTimeUsed = this.findViewById(R.id.textview_total_time_used);
        this.mTextViewTotalSize = this.findViewById(R.id.textview_total_server_count);
        this.mImageClose = this.findViewById(R.id.img_view_close);
        this.mImageRefresh = this.findViewById(R.id.img_view_refresh);
        this.mImageClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        this.mImageRefresh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    public void setReuslt(TotalSpeedTestResult totalResult) {
        this.mTextViewTotalSize.append("   " + totalResult.getTotalSize());
        this.mTextViewWhiteListServerCount.append("  " + totalResult.getWhiteAddrServerCount());
        this.mTextViewWhiteListServerRatio.append("  " + totalResult.getWhiteAddrConnectSuccesRate());
        this.mTextViewBlackListServerCount.append("  " + totalResult.getBlackAddrServerCount());
        this.mTextViewBlackListServerRatio.append("  " + totalResult.getBlackAddrConnectSuccesRate());
        this.mTextViewTotalTimeUsed.append("  " + totalResult.getTotalTimeUsed());
    }

}
