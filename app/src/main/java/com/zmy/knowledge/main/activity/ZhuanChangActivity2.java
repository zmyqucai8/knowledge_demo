package com.zmy.knowledge.main.activity;


import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.zmy.knowledge.R;
import com.zmy.knowledge.base.app.ViewHolder;
import com.zmy.knowledge.swipeback.MySwipeBackActivity;

/**
 * Created by win7 on 2017/6/23.
 */

public class ZhuanChangActivity2 extends MySwipeBackActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.zc_activity_2;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {

    }

    public void 缩放(View v) {  //进行一个缩放的动画  test 测试
        finish();
        overridePendingTransition(R.anim.zoom_enter2, R.anim.zoom_exit);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(R.anim.anim_phone_card_pop_in, R.anim.anim_phone_card_pop_out);
            return false;
        }
        return false;
    }

    public boolean 女朋友绿了吗;

    /**
     * 这里是一个测试的方法 。 明天转区
     */
    public void test() {
        if (女朋友绿了吗) {
            Toast.makeText(this, "绿了啊", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "没绿啊", Toast.LENGTH_LONG).show();
        }


    }


}
