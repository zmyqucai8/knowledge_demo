package com.zmy.knowledge.view;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @Created by zmy.
 * @Date 2016/12/22 0022.
 * 任意View 的刷新控件 ， 直接把View包裹在此layout下 即可
 * 此控件基于Ultra-Pull-To-Refresh 可选配置参数6项
 * 传送门 https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh/blob/master/README-cn.md
 */
public class WfViewRefreshFrameLayout extends PtrFrameLayout {

    public WfViewRefreshFrameLayout(Context context) {
        super(context);
        initView();
    }

    public WfViewRefreshFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public WfViewRefreshFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    /**
     * 初始化View 添加头部 设置监听
     */
    private void initView() {
        WfViewRefreshHeaderView mHeaderView = new WfViewRefreshHeaderView(getContext());
        setHeaderView(mHeaderView);
        addPtrUIHandler(mHeaderView);
    }
}
