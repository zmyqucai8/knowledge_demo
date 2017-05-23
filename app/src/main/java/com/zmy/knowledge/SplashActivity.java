package com.zmy.knowledge;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.CookieStore;
import com.zmy.knowledge.base.app.BaseActivity;
import com.zmy.knowledge.base.app.ViewHolder;
import com.zmy.knowledge.main.MainActivity;
import com.zmy.knowledge.utlis.AUtils;
import com.zmy.knowledge.utlis.SPUtils;

import java.util.List;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.Response;

/**
 * Created by win7 on 2017/5/16.
 * <p>
 * 写一个视频的启动页面
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        AUtils.hideNAVIGATION(this);
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        TextView tv_text = holder.get(R.id.tv_text);
        TextView tv_icon = holder.get(R.id.tv_icon);
        LinearLayout ll_view = holder.get(R.id.ll_view);
        tv_text.setTypeface(AUtils.getTTF());
        tv_icon.setTypeface(AUtils.getTTF());

        init();
    }

    /**
     * 初始化跳转等
     */
    private void init() {
        String userName = SPUtils.getUserName(this);
        final String userPhone = SPUtils.getUserPhone(this);
        final String userPwd = SPUtils.getUserPwd(this);
        String url = "http://qn.winfreeinfo.com:2234/names.nsf?Login";
        OkGo.post(url)
                .tag(this)
                .params("UserName", "admin")
                .params("Password", "wf1008").execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
//                Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                AUtils.log("登录成功");
                AUtils.logCookie();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!TextUtils.isEmpty(userPhone) && !TextUtils.isEmpty(userPwd)) {
                            //登录状态
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            finish();
                        } else if (SPUtils.getGuideApp(SplashActivity.this)) {
                            //非登录状态， 显示过guide
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            //非登录状态， 没有显示过guide
                            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                            finish();
                        }
                    }
                }, 2000);

            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                AUtils.log("登录错误");
                if (SPUtils.getGuideApp(SplashActivity.this)) {
                    //非登录状态， 显示过guide
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                } else {
                    //非登录状态， 没有显示过guide
                    startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    finish();
                }
            }
        });


    }
}
