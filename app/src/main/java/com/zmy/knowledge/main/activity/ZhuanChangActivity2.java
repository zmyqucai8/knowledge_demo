package com.zmy.knowledge.main.activity;

import android.os.Build;
import android.view.KeyEvent;
import android.view.View;

import com.zmy.knowledge.R;
import com.zmy.knowledge.base.app.BaseActivity;
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

    public void 缩放(View v){
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


}
