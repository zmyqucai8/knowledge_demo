package com.zmy.knowledge.main.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.zmy.knowledge.R;

import com.zmy.knowledge.base.app.ViewHolder;
import com.zmy.knowledge.swipeback.MySwipeBackActivity;
import com.zmy.knowledge.utlis.AUtils;
import com.zmy.knowledge.view.DrawingBoardLayout;

import com.zmy.knowledge.view.MyActionBar;

/**
 * Created by win7 on 2017/5/17.
 * 你画我猜
 */

public class DrawingBoardActivityDemo extends MySwipeBackActivity implements View.OnClickListener {
    DrawingBoardLayout db_view;

    @Override
    protected int getLayoutId() {
        return R.layout.drawing_board_demo;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        MyActionBar action_bar = holder.get(R.id.action_bar);

        db_view = holder.get(R.id.db_view);

        TextView tv_retrun = holder.get(R.id.tv_retrun);
        TextView tv_paint = holder.get(R.id.tv_paint);
        TextView tv_color = holder.get(R.id.tv_color);
        TextView tv_delete = holder.get(R.id.tv_delete);

        tv_retrun.setTypeface(AUtils.getTTF());
        tv_paint.setTypeface(AUtils.getTTF());
        tv_color.setTypeface(AUtils.getTTF());
        tv_delete.setTypeface(AUtils.getTTF());
        tv_retrun.setOnClickListener(this);
        tv_paint.setOnClickListener(this);
        tv_color.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        action_bar.setTitle("你画我猜");
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
            case R.id.tv_retrun:
                db_view.restore();
                break;
            case R.id.tv_color:
                db_view.setColor(Color.RED);
                break;
            case R.id.tv_delete:
                db_view.reset();
                break;
            case R.id.tv_paint:
                db_view.setStrokeWidth(80);
                break;
        }
    }
}
