package com.zmy.knowledge;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.zmy.knowledge.base.app.BaseActivity;
import com.zmy.knowledge.base.app.BaseApplication;
import com.zmy.knowledge.base.app.ViewHolder;
import com.zmy.knowledge.chat.DemoHelper;
import com.zmy.knowledge.chat.PreferenceManager;
import com.zmy.knowledge.main.MainActivity;
import com.zmy.knowledge.utlis.AUtils;
import com.zmy.knowledge.utlis.LoadingUtlis;
import com.zmy.knowledge.utlis.SPUtils;
import com.zmy.knowledge.view.MyActionBar;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by win7 on 2017/5/16.
 * 登录界面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    LoadingUtlis mLoading;
    TextInputEditText et_pwd;
    TextInputEditText et_phone;
    TextView tv_logo;
    MyActionBar action_bar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        holder.get(R.id.btn_login).setOnClickListener(this);
        et_phone = holder.get(R.id.et_phone);
        action_bar = holder.get(R.id.action_bar);
        tv_logo = holder.get(R.id.tv_logo);
        tv_logo.setTypeface(AUtils.getTTF());
        et_pwd = holder.get(R.id.et_pwd);
        mLoading = new LoadingUtlis(this);
        action_bar.setTitle("登录");
        action_bar.hideBtn();
        et_phone.setText("15356105789");
        et_pwd.setText("123456");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
        }
    }

    /**
     * 登录方法
     */
    private void login() {
        String phone = et_phone.getText().toString().trim();
        String pwd = et_pwd.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            AUtils.showToast("请输入手机号");
            return;
        } else if (!AUtils.isMobileNO(phone)) {
            AUtils.showToast("请输入正确的手机号");
            return;
        } else if (TextUtils.isEmpty(pwd)) {
            AUtils.showToast("请输入密码");
            return;
        }
        SPUtils.setUserPhone(this, phone);
        SPUtils.setUserPwd(this, pwd);
        mLoading.showLoading();

        loginHttp();

    }

    private void loginHttp() {

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
                AUtils.log("login 登录成功");
                //登录环信聊天服务器
                //注册失败会抛出HyphenateException
                if (!EMClient.getInstance().isConnected() && !DemoHelper.getInstance().isLoggedIn()) {
                    AUtils.log("login 手动登录chat");
                    loginChat("zmy", "123");
                } else {
                    //已经连接到服务器 直接进入主界面
                    AUtils.log("login 已是登录状态直接进入主页");
                    startActivity(userPhone, userPwd);
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                AUtils.showToast("用户名或密码错误");
            }
        });
    }


    /**
     * 登录聊天服务器
     */
    private void loginChat(final String userPhone, final String userPwd) {
        EMClient.getInstance().login(userPhone, userPwd, new EMCallBack() {
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.e("login", "登录聊天服务器成功！");

                // update current user's display name for APNs
                boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(
                        BaseApplication.currentUserNick.trim());
                if (!updatenick) {
                    Log.e("LoginActivity", "更新当前用户名称失败");
                }
                // get user's info (this should be get from App's server or 3rd party service)
                //设置当前用户名和头像地址 保存在sp

                DemoHelper.getInstance().asyncGetCurrentUserInfo(userPhone, "https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=3007804917,3081619109&fm=85&s=1706B54816333594480C4D030300E0C2");
                startActivity(userPhone, userPwd);
            }

            @Override
            public void onError(int i, String s) {
                Log.d("main", "登录聊天服务器失败！");
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    public void startActivity(final String userPhone, final String userPwd) {

        if (!TextUtils.isEmpty(userPhone) && !TextUtils.isEmpty(userPwd)) {
            //登录状态
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            //非登录状态， 没有显示过guide
            startActivity(new Intent(LoginActivity.this, GuideActivity.class));
            finish();
        }
    }
}
