package com.zmy.knowledge.swipeback;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zmy.knowledge.base.app.BaseActivity;

import me.imid.swipebacklayout.lib.SwipeBackLayout;

import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;


/**
 * Created by win7 on 2017/5/18.
 */

public abstract class MySwipeBackActivity extends BaseActivity implements SwipeBackActivityBase {
    private SwipeBackHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackHelper(this);
        mHelper.onActivityCreate();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        SwipeBackUtils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

}
