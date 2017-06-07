package com.zmy.knowledge.main.activity;

import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.zmy.knowledge.R;
import com.zmy.knowledge.base.app.BaseActivity;
import com.zmy.knowledge.base.app.ViewHolder;
import com.zmy.knowledge.utlis.AUtils;
import com.zmy.knowledge.utlis.SPUtils;

/**
 * Created by win7 on 2017/5/24.
 * 这里是一个JavaScript测试页面 用来测试JavaScript方法
 */

public class JsTestActivity extends BaseActivity {
    WebView mWebView;
    TextView tv_title;
    public String url = "http://192.168.0.129:8080/cloud/Test.html";

    @Override
    protected int getLayoutId() {
        return R.layout.web_activity;
    }

    protected WebSettings webSettings;

    @Override
    protected void initViews(ViewHolder holder, View root) {
        tv_title = holder.get(R.id.tv_title);
        mWebView = holder.get(R.id.webview);
        webSettings = mWebView.getSettings();
        // webSettings.setLoadsImagesAutomatically(true);
        mWebView.addJavascriptInterface(this, "webactivity");
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
//		mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setAllowContentAccess(true);
        webSettings.setBuiltInZoomControls(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSaveFormData(false);
        mWebView.getSettings().setSavePassword(false);
        mWebView.getSettings().setLightTouchEnabled(true);

        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                AUtils.log("url=" + url);
                view.loadUrl(url);
                return true;
            }
        });


    }

    @JavascriptInterface
    public void getPhoneNumber() {


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String userPhone = SPUtils.getUserPhone(JsTestActivity.this);
                mWebView.loadUrl("javascript:getPhoneNumberResult('" + userPhone + "');");
            }
        });


    }

    /**
     * 震动1次
     */
    @JavascriptInterface
    public void vibrator() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {100, 400, 100, 400};   // 停止 开启 停止 开启
        vibrator.vibrate(pattern, -1);           //震动一次，index设为-1
    }

    @JavascriptInterface
    public void toast() {
        AUtils.showToast("js接收到了电话，并且回调了方法");
    }

}
