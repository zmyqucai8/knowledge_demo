package com.zmy.knowledge.main.activity;

import android.content.Intent;
import android.os.Build;
import android.view.View;

import com.zmy.knowledge.R;
import com.zmy.knowledge.base.app.BaseActivity;
import com.zmy.knowledge.base.app.ViewHolder;
import com.zmy.knowledge.swipeback.MySwipeBackActivity;

/**
 * Created by win7 on 2017/6/23.
 */

public class ZhuanChangActivity extends MySwipeBackActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.zc_activity_1;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {

    }


    public void 缩放(View view) {
        //5.0 -
        Intent it = new Intent(this, ZhuanChangActivity2.class);
        startActivity(it);
        if (1 == 1) {
            overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0 +
        } else {
        }

    }
}
