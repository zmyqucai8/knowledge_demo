package com.zmy.knowledge.webx5;

import android.view.View;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zmy.knowledge.R;
import com.zmy.knowledge.base.app.BaseActivity;
import com.zmy.knowledge.base.app.ViewHolder;
import com.zmy.knowledge.chat.Constant;
import com.zmy.knowledge.utlis.AUtils;

/**
 * Created by win7 on 2017/6/20.
 */

public class X5TestActivity extends BaseActivity {
    WebView mWebView;

    @Override
    protected int getLayoutId() {
        return R.layout.x5_activity;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {

        String url = "file:///android_asset/webpage/hitTestResult.html";
        mWebView = holder.get(R.id.mWebView);
        mWebView.loadUrl(url);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


        if (mWebView.getX5WebViewExtension() != null) {
            AUtils.showToast("X5内核 版本=" + QbSdk.getTbsVersion(this));
            AUtils.log("X5内核 版本=" + QbSdk.getTbsVersion(this));
        }

    }
}
