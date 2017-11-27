package com.vecent.ssspeedtest.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.vecent.ssspeedtest.R;

/**
 * Created by zhiwei on 2017/11/21.
 */

public class HeadBeatImage extends ImageView {

    private Animation mAnimation;
    private Context context;

    public HeadBeatImage(Context context) {
        super(context);
        this.init(context);
    }

    public HeadBeatImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public HeadBeatImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }

    private void init(Context context) {
        this.context = context;
        this.setImageResource(R.mipmap.heartbeat_normal);
    }

    public void headBestActive() {
        if (this.mAnimation == null) {
            this.mAnimation = AnimationUtils.loadAnimation(context, R.anim.head_beat_active);
            this.mAnimation.setFillAfter(false);
            this.mAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    setImageResource(R.mipmap.heartbeat_active);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    setImageResource(R.mipmap.heartbeat_normal);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        this.startAnimation(mAnimation);
    }
}
