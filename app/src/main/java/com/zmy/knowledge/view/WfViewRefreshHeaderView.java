package com.zmy.knowledge.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.zmy.knowledge.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;


/**
 * @Created by zmy.
 * @Date 2016/12/22 0022.
 * XdaViewRefreshHeaderView  View头部刷新View
 * 此类仅做view显示不直接使用 ， 而是使用XdaViewRefreshFrameLayout类，详情看注释
 */

public class WfViewRefreshHeaderView extends FrameLayout implements PtrUIHandler {

    //下拉根布局
//    private RelativeLayout head_root;
    //完成状态
    private ImageView img_state;
    //下拉箭头
    private ImageView img_pull;
    //下拉或加载中等文字提示
    private TextView tv_state;
    //刷新中图片
    private ImageView img_refreshing;
    // 下拉箭头的转180°动画
    private RotateAnimation rotateAnimation;
    // 均匀旋转动画
    private RotateAnimation refreshingAnimation;
    //是否可以显示 释放立即刷新 默认true
    private boolean isShowRefresh = true;
    //view 设置paddingtop的回弹动画
//    private ValueAnimator valueAnimator;

    public WfViewRefreshHeaderView(Context context) {
        super(context);
        initView(context);
    }

    public WfViewRefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public WfViewRefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    /**
     * 初始化view
     */
    private void initView(Context context) {
        View mHeadView = LayoutInflater.from(getContext()).inflate(R.layout.refresh_head, this);
//        head_root = (RelativeLayout) mHeadView.findViewById(R.id.head_view);
        img_pull = (ImageView) mHeadView.findViewById(R.id.pull_icon);
        img_refreshing = (ImageView) mHeadView.findViewById(R.id.refreshing_icon);
        tv_state = (TextView) mHeadView.findViewById(R.id.state_tv);
        img_state = (ImageView) mHeadView.findViewById(R.id.state_iv);
        rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(context, R.anim.reverse_anim);
        refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(context, R.anim.rotating);
        // 添加动画差值器 匀速运动
        LinearInterpolator lir = new LinearInterpolator();
        rotateAnimation.setInterpolator(lir);
        refreshingAnimation.setInterpolator(lir);

    }


    @Override
    public void onUIReset(PtrFrameLayout frame) {
        tv_state.setText(R.string.pull_to_refresh);
        img_refreshing.clearAnimation();
        img_refreshing.setVisibility(View.GONE);
        img_pull.clearAnimation();
        img_pull.setVisibility(View.VISIBLE);
        img_state.setVisibility(GONE);
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        img_pull.clearAnimation();
        img_pull.setVisibility(View.GONE);
        img_refreshing.setVisibility(View.VISIBLE);
        img_refreshing.startAnimation(refreshingAnimation);
        tv_state.setText(R.string.refreshing);
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        img_pull.clearAnimation();
        img_refreshing.clearAnimation();
        img_refreshing.setVisibility(View.GONE);
        img_pull.setVisibility(View.GONE);
        tv_state.setText(R.string.refresh_succeed);
        img_state.setVisibility(VISIBLE);
        img_state.setBackgroundResource(R.drawable.load_succeed);
    }


    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        // 获取总的头部可下拉的距离：
        final int offsetToRefresh = frame.getOffsetToRefresh();
        // 获取当前手指已经下拉的距离：
        final int currentPos = ptrIndicator.getCurrentPosY();
        //设置rootView的paddingTop来达到无限下拉的效果
        // 如果使用这种方式那么会导致松开刷新之前，头部无法回弹，在刷新完成后才会回弹
        // 注意还要设置ptr_ratio_of_header_height_to_refresh = 0.5 否则下拉刷新点的高度会出现问题
//            head_root.setPadding(0, currentPos, 0, 0);
        //或者设置头部高度match_parent，在设置比例0.X也可以无限下拉，但是也会出现头部在刷新完成前无法回弹的问题
        if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
            if (currentPos >= offsetToRefresh && isShowRefresh) {
                //显示释放刷新
                img_pull.startAnimation(rotateAnimation);
                tv_state.setText(R.string.release_to_refresh);
                isShowRefresh = false;//状态取反
            } else if (isUnderTouch && currentPos <= offsetToRefresh && !isShowRefresh) {
                img_pull.clearAnimation();
                tv_state.setText(R.string.pull_to_refresh);
                isShowRefresh = true;
            }
        }
    }
}
