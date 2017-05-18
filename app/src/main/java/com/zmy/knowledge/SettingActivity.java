package com.zmy.knowledge;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import android.widget.TextView;


import com.gitonway.lee.niftynotification.lib.Effects;
import com.zmy.knowledge.base.app.ViewHolder;
import com.zmy.knowledge.swipeback.MySwipeBackActivity;
import com.zmy.knowledge.utlis.AUtils;
import com.zmy.knowledge.utlis.SPUtils;
import com.zmy.knowledge.view.MyActionBar;

/**
 * Created by win7 on 2017/5/16.
 */

public class SettingActivity extends MySwipeBackActivity implements View.OnClickListener {
    MyActionBar action_bar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {

        action_bar = holder.get(R.id.action_bar);
        holder.get(R.id.rl_check).setOnClickListener(this);
        holder.get(R.id.rl_delete).setOnClickListener(this);
        holder.get(R.id.rl_dz).setOnClickListener(this);
        TextView tv_right = holder.get(R.id.tv_right);
        tv_right.setTypeface(AUtils.getTTF());
        holder.get(R.id.btn_out).setOnClickListener(this);

        action_bar.setTitle("设置");
        action_bar.setActionBarClickListener(new MyActionBar.onActionBarClickListener() {
            @Override
            public void onOkClick() {

            }

            @Override
            public void onBackClick() {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_check:
                AUtils.showNotification(this, "已经是最新版本", R.id.rl_check, Effects.flip);
                break;
            case R.id.rl_delete:
                AUtils.showNotification(this, "缓存已清除", R.id.rl_delete, Effects.thumbSlider);
                break;
            case R.id.rl_dz:
                AUtils.showNotification(this, "谢谢亲的赞 ⊙_⊙");
                break;
            case R.id.btn_out:
                AUtils.showNotification(this, "如果你点到我，我就放你走 ⊙_⊙", R.id.rl_view, Effects.slideIn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        out();
                    }
                });
                break;
        }
    }

    /**
     * 退出登录
     */
    private void out() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定要退出登录吗？");
        builder.setTitle("提示");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AUtils.showNotification(SettingActivity.this, "我就知道你舍不得走 略略略 ⊙_⊙", R.id.rl_view, Effects.scale);
            }
        });
        builder.setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SPUtils.cleanUserInfo(SettingActivity.this);
                        exit();
                        startActivity(new Intent(SettingActivity.this, LoginActivity.class));

                    }
                });
        builder.create().show();

    }
}
