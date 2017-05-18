package com.zmy.knowledge.utlis;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.mingle.widget.LoadingView;
import com.zmy.knowledge.R;

/**
 * Created by Yyyyyyy on 2017/4/19.
 */
public class LoadingUtlis {

    LoadingView loadView;
    Activity activity;

    ViewGroup viewGroup;

    View view;

    public LoadingUtlis(Activity activity) {

        this.activity = activity;
        initView(activity);
    }

    /**
     * 初始化view
     *
     * @param activity
     */
    public void initView(Activity activity) {
        view = View.inflate(activity, R.layout.loading, null);
        viewGroup = (ViewGroup) activity.getWindow().getDecorView();
        loadView = (LoadingView) view.findViewById(R.id.loadView);
        view.findViewById(R.id.ll_root).setOnClickListener(null);
    }

    /**
     * 显示loading
     */
    public void showLoading() {

        if (viewGroup.indexOfChild(view) == -1) {
            viewGroup.addView(view);
        }
        loadView.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏loading
     */
    public void hideLoading() {
        viewGroup.removeView(view);
        loadView.setVisibility(View.GONE);
    }
}
