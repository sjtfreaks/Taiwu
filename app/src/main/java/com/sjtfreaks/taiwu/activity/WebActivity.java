package com.sjtfreaks.taiwu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sjtfreaks.taiwu.R;
import com.sjtfreaks.taiwu.utils.L;


/**
 * Created by jet on 2018-10-24.
 */

public class WebActivity extends BaseActivity{
    private WebView mweb;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        initView();
    }
    //初始化
    private void initView() {
        mweb = (WebView) findViewById(R.id.mweb);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        final String url = intent.getStringExtra("url");
        L.i("url"+url);
        //标题
        getSupportActionBar().setTitle(title);
        //加载网页
        //支持javascript
        mweb.getSettings().setJavaScriptEnabled(true);
        //支持缩放
        mweb.getSettings().setSupportZoom(true);
        //控制器
        mweb.getSettings().setBuiltInZoomControls(true);
        //mac port
        //mweb.setWebChromeClient(new WebViewClient());
        //loading web
        mweb.loadUrl(url);
        //本地显示
        mweb.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }
        });

    }
}
