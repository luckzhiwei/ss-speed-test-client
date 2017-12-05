package com.vecent.ssspeedtest.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vecent.ssspeedtest.R;
import com.vecent.ssspeedtest.dao.SSServer;
import com.vecent.ssspeedtest.model.bean.TotalSpeedTestResult;

/**
 * Created by lzw on 17-10-1.
 */

public class ResultLayout extends LinearLayout {

    private TextView mTextViewWhiteListServerCount;
    private TextView mTextViewBlackListServerCount;
    private TextView mTextViewWhiteListServerRatio;
    private TextView mTextViewBlackListServerRatio;
    private TextView mTextViewWhiteAddrSpeedAvg;
    private TextView mTextViewBlackAddrSpeedAvg;
    private TextView mTextViewTotalTimeUsed;
    private TextView mTextViewTotalSize;
    private TextView mTextViewCurServerCount;
    private RelativeLayout mHeaderLayout;
    private ImageView mImageClose;
    private ImageView mImageRefresh;
    private float lastYUserTouch;
    private TextView mTextViewServerInfo;
    private int mTouchSlop;
    private int onlyShowTitleCoordinateY;
    private int showAllContentCoordinateY;
    private TranslateAnimation mTranslateAnimationUp;
    private TranslateAnimation mTranslateAnimationDown;
    public boolean showAll;
    private String totalServerSize;
    private String curServerSize;
    private String curTimeUsed;
    private String whiteListCount;
    private String blackListCount;
    private String whiteListRadio;
    private String blackListRadio;
    private String whiteSpeedAvg;
    private String blackSpeedAvg;
    private String speedUnit;
    private String timeUnit;


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
        this.mHeaderLayout = this.findViewById(R.id.header_layout);
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mTextViewWhiteListServerRatio = this.findViewById(R.id.textview_white_server_ratio);
        this.mTextViewWhiteListServerCount = this.findViewById(R.id.textview_white_server_count);
        this.mTextViewBlackListServerRatio = this.findViewById(R.id.textview_black_server_ratio);
        this.mTextViewBlackListServerCount = this.findViewById(R.id.textview_black_server_count);
        this.mTextViewWhiteAddrSpeedAvg = this.findViewById(R.id.textview_white_server_speed_avg);
        this.mTextViewBlackAddrSpeedAvg = this.findViewById(R.id.textview_black_add_speed_avg);
        this.mTextViewCurServerCount = this.findViewById(R.id.textview_cur_server_count);
        this.mTextViewTotalTimeUsed = this.findViewById(R.id.textview_total_time_used);
        this.mTextViewTotalSize = this.findViewById(R.id.textview_total_server_count);
        this.mImageClose = this.findViewById(R.id.img_view_close);
        this.mImageRefresh = this.findViewById(R.id.img_view_refresh);
        this.mTextViewServerInfo = this.findViewById(R.id.test_server_setting);
        this.showAll = false;
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
        initText(context.getResources());
    }

    private void initText(Resources res) {
        this.totalServerSize = res.getString(R.string.total_server_count) + " ";
        this.curServerSize = res.getString(R.string.cur_server_count) + " ";
        this.whiteListCount = res.getString(R.string.white_list_addr_count) + " ";
        this.whiteListRadio = res.getString(R.string.white_list_success_rate) + " ";
        this.blackListCount = res.getString(R.string.black_list_addr_count) + " ";
        this.blackListRadio = res.getString(R.string.black_list_success_rate) + " ";
        this.curTimeUsed = res.getString(R.string.time_used) + " ";
        this.speedUnit = res.getString(R.string.kb_by_second);
        this.timeUnit = res.getString(R.string.unit_second);
        this.whiteSpeedAvg = res.getString(R.string.white_avg_speed) + " ";
        this.blackSpeedAvg = res.getString(R.string.black_avg_speed) + " ";
    }


    public void update(TotalSpeedTestResult totalResult) {
        this.mTextViewTotalSize.setText(this.totalServerSize + totalResult.getTotalServerSize());
        this.mTextViewCurServerCount.setText(this.curServerSize + totalResult.getCurServerCount());
        this.mTextViewWhiteListServerCount.setText(this.whiteListCount + totalResult.getWhiteAddrServerCount());
        this.mTextViewWhiteListServerRatio.setText(this.whiteListRadio + totalResult.getWhiteAddrConnectSuccesRate());
        this.mTextViewBlackListServerCount.setText(this.blackListCount + totalResult.getBlackAddrServerCount());
        this.mTextViewBlackListServerRatio.setText(this.blackListRadio + totalResult.getBlackAddrConnectSuccesRate());
        this.mTextViewTotalTimeUsed.setText(this.curTimeUsed + totalResult.getTotalTimeUsed() + this.timeUnit);
        this.mTextViewWhiteAddrSpeedAvg.setText(this.whiteSpeedAvg + totalResult.getSpeedWhiteAddrDownLoadAvg() + this.speedUnit);
        this.mTextViewBlackAddrSpeedAvg.setText(this.blackSpeedAvg + totalResult.getSpeedBlackAddrDownLoadAvg() + this.speedUnit);
    }

    public void setProxyServerInfo(SSServer server) {
        this.mTextViewServerInfo.setText(server.getServerAddr() + ":" + server.getServerPort());
    }

    /**
     * getY 是相对于父控件的长度:（ ps:这里默认loc[1]是父控件相对与屏幕的距离）
     *
     * @param context
     * @return
     */
    public void setHeaderShowCorOnInit(Activity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenHeight = size.y;
        int loc[] = new int[2];
        this.getLocationOnScreen(loc);
        this.onlyShowTitleCoordinateY = screenHeight - mHeaderLayout.getHeight() - loc[1];
        this.showAllContentCoordinateY = screenHeight - this.getHeight() - loc[1];
        this.setY(this.onlyShowTitleCoordinateY);
        setMyAnimation();
    }

    private void setMyAnimation() {
        this.mTranslateAnimationUp = new TranslateAnimation(0, 0, 0, this.showAllContentCoordinateY - onlyShowTitleCoordinateY);
        this.mTranslateAnimationUp.setDuration(500);
        this.mTranslateAnimationUp.setFillAfter(true);
        this.mTranslateAnimationUp.setAnimationListener(upAnimListener);
        this.mTranslateAnimationDown = new TranslateAnimation(0, 0, 0, this.onlyShowTitleCoordinateY - this.showAllContentCoordinateY);
        this.mTranslateAnimationDown.setDuration(500);
        this.mTranslateAnimationDown.setFillAfter(true);
        this.mTranslateAnimationDown.setAnimationListener(downAnimListener);
    }

    /**
     * getRawY 和 getY() =>点击事件相对控件距离y，点击事件相对屏幕的距离y
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.lastYUserTouch = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                float delta = event.getY() - this.lastYUserTouch;
                if (delta < 0 && !this.showAll) {
                    this.startAnimation(this.mTranslateAnimationUp);
                } else if (delta >= 0 && this.showAll) {
                    this.startAnimation(this.mTranslateAnimationDown);
                }
                break;
        }
        return true;
    }

    Animation.AnimationListener upAnimListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            clearAnimation();
            setY(showAllContentCoordinateY);
            showAll = true;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    Animation.AnimationListener downAnimListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            clearAnimation();
            setY(onlyShowTitleCoordinateY);
            showAll = false;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };


}
