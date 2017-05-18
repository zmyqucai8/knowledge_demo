package com.zmy.knowledge.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zmy.knowledge.R;
import com.zmy.knowledge.utlis.AUtils;

/**
 * Created by win7 on 2017/5/17.
 */

public class DrawingBoardLayout extends FrameLayout {
    TextView tv_paint;//画笔
    DrawingBoardView db_view;
    public DrawingBoardLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }


    int startX;
    int startY;

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.draing_board_layout, null);

        db_view = (DrawingBoardView) view.findViewById(R.id.db_view);
        tv_paint = (TextView) view.findViewById(R.id.tv_paint);
        tv_paint.setTypeface(AUtils.getTTF());
        db_view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 获取手指按下的坐标
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        int x = (int) event.getX();
                        int y = (int) event.getY();
                        int height = tv_paint.getHeight();
                        int width = tv_paint.getWidth();
                        AUtils.log("startX="+startX);
                        AUtils.log("startY="+startY);
                        AUtils.log("x="+x);
                        AUtils.log("y="+y);
                        AUtils.log("height="+height);
                        AUtils.log("width="+width);

                        tv_paint.layout(x-(width/2-25),y-height+10,x+(width/2+25),y+10);
                        invalidate();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        setPaintXY(event);
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }

                int left = tv_paint.getLeft();
                int right = tv_paint.getRight();
                int top = tv_paint.getTop();
                int bottom = tv_paint.getBottom();
                AUtils.log("left="+left);
                AUtils.log("right="+right);
                AUtils.log("top="+top);
                AUtils.log("bottom="+bottom);
                return false;//如果返回true,从手指接触屏幕到手指离开屏幕，将不会触发点击事件。
            }
        });

        addView(view);
    }


    /**
     * 设置画笔坐标
     */
    private void setPaintXY(MotionEvent event) {

        // 获取手指移动到了哪个点的坐标
        int movingX = (int) event.getRawX();
        int movingY = (int) event.getRawY();
        // 相对于上一个点，手指在X和Y方向分别移动的距离
        int dx = movingX - startX;
        int dy = movingY - startY;
        // 获取TextView上一次上 下 左 右各边与父控件的距离
        int left = tv_paint.getLeft();
        int right = tv_paint.getRight();
        int top = tv_paint.getTop();
        int bottom = tv_paint.getBottom();
        // 设置本次TextView的上 下 左 右各边与父控件的距离
        tv_paint.layout(left + dx, top + dy, right + dx, bottom + dy);
        // 本次移动的结尾作为下一次移动的开始
        startX = (int) event.getRawX();
        startY = (int) event.getRawY();
        invalidate();
    }

    // 重置画布
    public void reset() {
        db_view.reset();
    }


    //设置画笔颜色
    public void setColor(int color) {
        db_view.setColor(color);
    }

    //设置画笔大小
    public void setStrokeWidth(int width) {
        db_view.setStrokeWidth(width);
    }

    //返回上一步
    public void restore() {
        db_view.restore();
    }
}
