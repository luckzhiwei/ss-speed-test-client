package com.vecent.ssspeedtest.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vecent.ssspeedtest.R;

/**
 * Created by lzw on 17-9-11.
 */

public class KeyValueView {

    private ViewGroup mLayout;
    private TextView textViewKey;
    private TextView textViewValue;

    public KeyValueView(String key, String value, Context context) {
        this.mLayout = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.key_value_item, null);
        textViewKey = this.mLayout.findViewById(R.id.item_key_value_key_content);
        textViewValue = this.mLayout.findViewById(R.id.item_key_value_value_content);
        textViewKey.setText(key);
        textViewValue.setText(value);
    }

    public void setKey(String key) {
        textViewKey.setText(key);
    }

    public void setValue(String value) {
        textViewValue.setText(value);
    }

    public View getView() {
        return this.mLayout;
    }

}
