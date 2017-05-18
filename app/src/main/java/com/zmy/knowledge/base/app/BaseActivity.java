/*
 * Copyright 2017 GcsSloop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 2017-03-11 22:24:54
 *
 * GitHub:  https://github.com/GcsSloop
 * Website: http://www.gcssloop.com
 * Weibo:   http://weibo.com/GcsSloop
 */

package com.zmy.knowledge.base.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.SortedList;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.zmy.knowledge.R;


import java.io.Serializable;
import java.util.ArrayList;


/**
 * 不要侧滑关闭继承BaseActivity
 * <p>
 * 需要侧滑关闭继承MySwipeBackActivity
 */
public abstract class BaseActivity extends AppCompatActivity {

    //    protected Diycode mDiycode;
    protected ViewHolder mViewHolder;
    private Toast mToast;

    public static ArrayList<Activity> list = new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mDiycode = Diycode.getSingleInstance();
        list.add(this);
        initSwipeBack();
        mViewHolder = new ViewHolder(getLayoutInflater(), null, getLayoutId());
        setContentView(mViewHolder.getRootView());
//        IMMLeaks.fixFocusedViewLeak(this.getApplication()); // 修复 InputMethodManager 引发的内存泄漏
        initActionBar(mViewHolder);
        initDatas();
        initViews(mViewHolder, mViewHolder.getRootView());

    }

    /**
     * 初始化侧滑返回
     */
    private void initSwipeBack() {
        // 可以调用该方法，设置是否允许滑动退出
//        setSwipeBackEnable(true);
//        SwipeBackLayout mSwipeBackLayout = getSwipeBackLayout();
        // 设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
//        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        // 滑动退出的效果只能从边界滑动才有效果，如果要扩大touch的范围，可以调用这个方法
        //mSwipeBackLayout.setEdgeSize(200);

    }

    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 初始化数据，调用位置在 initViews 之前
     */

    protected void initDatas() {
    }

    /**
     * 初始化 View， 调用位置在 initDatas 之后
     */
    protected abstract void initViews(ViewHolder holder, View root);


    // 初始化 ActiobBar
    private void initActionBar(ViewHolder holder) {
        Toolbar toolbar = holder.get(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    // 默认点击左上角是结束当前 Activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public ViewHolder getViewHolder() {
        return mViewHolder;
    }

    /**
     * 发出一个短Toast
     *
     * @param text 内容
     */
    public void toastShort(String text) {
        toast(text, Toast.LENGTH_SHORT);
    }

    /**
     * 发出一个长toast提醒
     *
     * @param text 内容
     */
    public void toastLong(String text) {
        toast(text, Toast.LENGTH_LONG);
    }


    private void toast(final String text, final int duration) {
        if (!TextUtils.isEmpty(text)) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mToast == null) {
                        mToast = Toast.makeText(getApplicationContext(), text, duration);
                    } else {
                        mToast.setText(text);
                        mToast.setDuration(duration);
                    }
                    mToast.show();
                }
            });
        }
    }


    protected void openActivity(Class<?> cls) {
        openActivity(this, cls);
    }

    public static void openActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    /**
     * 打开 Activity 的同时传递一个数据
     */
    protected <V extends Serializable> void openActivity(Class<?> cls, String key, V value) {
        openActivity(this, cls, key, value);
    }


    /**
     * 打开 Activity 的同时传递一个数据
     */
    public <V extends Serializable> void openActivity(Context context, Class<?> cls, String key, V value) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (list.contains(this)) {
            list.remove(this);
        }

    }


    /**
     * 退出
     */
    public void exit() {
        for (Activity a : list) {
            if (null != a && !a.isFinishing()) {
                a.finish();
            }
        }
    }
}
