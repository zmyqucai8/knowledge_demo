package com.zmy.knowledge.main.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.zmy.knowledge.R;
import com.zmy.knowledge.utlis.AUtils;

/**
 * Created by win7 on 2017/7/7.
 */

public class MsgPop extends PopupWindow {

    public Activity activity;
    View conentView;

    public interface onClickListener {
        void onTopClick();

        void onDeleteClick();
    }

    public onClickListener mListener;

    View btn_top;
    View btn_delete;

    public MsgPop(Activity context, onClickListener listener) {
        this.activity = context;
        this.mListener = listener;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        conentView = inflater.inflate(R.layout.msg_pop, null);


        btn_top = conentView.findViewById(R.id.btn_top);

        btn_delete = conentView.findViewById(R.id.btn_delete);
        btn_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onTopClick();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeleteClick();
            }
        });
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(conentView);
        setBackgroundDrawable(new ColorDrawable(0x00000000));
        setOutsideTouchable(false);
        setFocusable(true);
    }

    public void show(View view, int position) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        int w = AUtils.getW(activity);
        int h = AUtils.getH(activity);
        int viewW = conentView.getMeasuredHeight();
        int viewH = conentView.getMeasuredWidth();
        AUtils.log("屏幕宽=" + w);
        AUtils.log("屏幕高=" + h);
        AUtils.log("view宽=" + viewW);
        AUtils.log("view高=" + viewH);
        AUtils.log("x=" + location[0]);
        AUtils.log("y=" + location[1]);
        int y = location[1];

        int i = AUtils.dp2px(activity, btn_delete.getVisibility() == View.VISIBLE ? 120 : 70);
        if (h - y > i) {
            //显示在下面
            showAsDropDown(view, w / 2 - viewW / 2, -(view.getHeight() / 2));
        } else {
            //要显示在上面
            showAsDropDown(view, w / 2 - getWidth() / 2, -i);
        }
    }
}
