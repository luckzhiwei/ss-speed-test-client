package com.vecent.ssspeedtest.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.vecent.ssspeedtest.R;
import com.vecent.ssspeedtest.util.Constant;

/**
 * Created by zhiwei on 2018/1/2.
 */

public class AboutActivity extends AppCompatActivity {

    private WebView mWebContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.common_static_web_layout);
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setCustomView(R.layout.action_bar_common);
        initView();
    }

    private void initView() {
        this.mWebContainer = (WebView) this.findViewById(R.id.web_container);
        this.mWebContainer.loadUrl(Constant.ABOUT_URL);
    }
}
