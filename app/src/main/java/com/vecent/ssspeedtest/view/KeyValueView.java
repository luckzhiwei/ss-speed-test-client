package com.vecent.ssspeedtest.view;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vecent.ssspeedtest.R;

/**
 * Created by lzw on 17-9-11.
 */

public class KeyValueView extends RelativeLayout {

    private TextView textViewKey;
    private TextView textViewValue;
    private Context mContext;

    public KeyValueView(Context context) {
        super(context);
        init(context);
    }

    public KeyValueView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public KeyValueView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.key_value_item, this);
        textViewKey = this.findViewById(R.id.item_key_value_key_content);
        textViewValue = this.findViewById(R.id.item_key_value_value_content);
    }

    public KeyValueView setKey(String key) {
        textViewKey.setText(key);
        return this;
    }

    public KeyValueView setValue(String value) {
        textViewValue.setText(value);
        return this;
    }


    public KeyValueView hidden() {
        this.textViewKey.setVisibility(View.GONE);
        this.textViewValue.setVisibility(View.GONE);
        return this;
    }

    public KeyValueView show() {
        this.textViewKey.setVisibility(View.VISIBLE);
        this.textViewValue.setVisibility(View.VISIBLE);
        return this;
    }

    public KeyValueView setTextSize(int dimen) {
        float size = mContext.getResources().getDimension(dimen);
        this.textViewKey.setTextSize(size);
        this.textViewValue.setTextSize(size);
        return this;
    }

    public KeyValueView setKeyTextColor(int colorId) {
        this.textViewKey.setTextColor(mContext.getResources().getColor(colorId));
        return this;
    }

    public KeyValueView setValueTextColor(int colorId) {
        this.textViewValue.setTextColor(mContext.getResources().getColor(colorId));
        return this;
    }


}
