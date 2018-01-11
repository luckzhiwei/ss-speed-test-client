package com.vecent.ssspeedtest.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.vecent.ssspeedtest.R;
import com.vecent.ssspeedtest.util.Constant;
import com.vecent.ssspeedtest.util.LogUtil;

/**
 * Created by zhiwei on 2018/1/2.
 */

public class ShowWebPageActivity extends AppCompatActivity {

    private WebView mWebContainer;
    private ProgressBar mWebProgressBar;

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
        this.mWebProgressBar = (ProgressBar) this.findViewById(R.id.common_progress);
        this.mWebProgressBar.setMax(100);
        this.mWebContainer.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                mWebProgressBar.setProgress(progress);
                if (progress == 100)
                    mWebProgressBar.setVisibility(View.INVISIBLE);
            }

        });
        String url = getIntent().getStringExtra("url");
        if (url == null) {
            url = Constant.ABOUT_URL;
        }
        this.mWebContainer.loadUrl(url);
    }
}
