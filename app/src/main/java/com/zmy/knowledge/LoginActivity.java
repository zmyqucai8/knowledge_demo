package com.zmy.knowledge;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.zmy.knowledge.base.app.BaseActivity;
import com.zmy.knowledge.base.app.ViewHolder;
import com.zmy.knowledge.main.MainActivity;
import com.zmy.knowledge.utlis.AUtils;
import com.zmy.knowledge.utlis.LoadingUtlis;
import com.zmy.knowledge.utlis.SPUtils;
import com.zmy.knowledge.view.MyActionBar;

/**
 * Created by win7 on 2017/5/16.
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoading.hideLoading();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        }, 5000);


    }
}
