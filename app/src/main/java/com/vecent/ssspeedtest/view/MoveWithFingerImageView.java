package com.vecent.ssspeedtest.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;


public class MoveWithFingerImageView extends android.support.v7.widget.AppCompatImageView {

    private int totalX;
    private int totalY;
    private int lastX;
    private int lastY;
    private int touchSlop;
    private int mWidth;
    private int mHeight;

    public MoveWithFingerImageView(Context context) {
        super(context);
        init(context);
    }

    public MoveWithFingerImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.setOnTouchListener(mTouchListener);
        this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            this.mWidth = size.x;
            this.mHeight = size.y;
        }
    }

    private OnTouchListener mTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    totalX = 0;
                    totalY = 0;
                    lastY = y;
                    lastX = x;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int delatX = x - lastX;
                    int delatY = y - lastY;
                    totalX += Math.abs(delatX);
                    totalY += Math.abs(delatY);
                    lastY = y;
                    lastX = x;
                    if (Math.abs(delatX) < (touchSlop) / 3.0 && Math.abs(delatY) < (touchSlop) / 3.0)
                        break;
                    if (getLeft() + delatX < 0 || getTop() + delatY < 0 || getRight() + delatX > mWidth || getBottom() + delatY > mHeight)
                        break;
                    layout(getLeft() + delatX, getTop() + delatY, getRight() + delatX, getBottom() + delatY);
                    break;
                case MotionEvent.ACTION_UP:
                    view.performClick();
                    return totalX > touchSlop || totalY > touchSlop;
            }
            return false;
        }
    };
}
