package com.zmy.knowledge.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import com.zmy.knowledge.utlis.AUtils;

/**
 * Created by win7 on 2017/5/17.
 * 画画板
 */
public class DrawingBoardView extends SurfaceView implements
        SurfaceHolder.Callback, Runnable {
    // SurfaceHolder实例
    private SurfaceHolder mSurfaceHolder;
    // Canvas对象
    private Canvas mCanvas;
    // 控制子线程是否运行
    private boolean startDraw;
    // Path实例
    private Path mPath = new Path();
    // Paint实例
    private Paint mpaint = new Paint();

    /**
     * 构造方法
     *
     * @param context
     * @param attrs
     */
    public DrawingBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mpaint.setStrokeWidth(AUtils.px2dp(getContext(), 30));//画笔宽度
        mpaint.setColor(Color.BLACK);//画笔颜色
        mpaint.setAntiAlias(true);
        initView(); // 初始化
    }

    /**
     * 初始化view
     */
    private void initView() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        // 设置可获得焦点
        setFocusable(true);
        setFocusableInTouchMode(true);
        // 设置常亮
        this.setKeepScreenOn(true);

    }

    @Override
    public void run() {
        // 如果不停止就一直绘制
        while (startDraw) {
            // 绘制
            draw();
        }
    }

    /*
     * 创建
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startDraw = true;
        new Thread(this).start();
    }

    /*
     * 改变
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    /*
     * 销毁
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        startDraw = false;
    }


    private void draw() {
        try {
            mCanvas = mSurfaceHolder.lockCanvas();//获取画布
            mCanvas.drawColor(Color.WHITE);//设置画布白底
            mpaint.setStyle(Paint.Style.STROKE);//画笔模式


            mCanvas.drawPath(mPath, mpaint);

        } catch (Exception e) {

        } finally {
            // 对画布内容进行提交
            if (mCanvas != null) {
                try {
                    mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                } catch (Exception e) {

                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();    //获取手指移动的x坐标
        int y = (int) event.getY();    //获取手指移动的y坐标
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                mCanvas.save();//手指抬起的时候保存下当前步骤
                break;
        }
        return true;
    }

    // 重置画布
    public void reset() {
        mPath.reset();
    }


    //设置画笔颜色
    public void setColor(int color) {
        mCanvas.save();
        mpaint.setColor(color);
    }

    //设置画笔大小
    public void setStrokeWidth(int width) {
        mCanvas.save();
        mpaint.setStrokeWidth(width);
    }

    //返回上一步
    public void restore() {
        mpaint.setStrokeWidth(AUtils.px2dp(getContext(), 30));//画笔宽度
        mpaint.setColor(Color.BLACK);//画笔颜色
        mpaint.setAntiAlias(true);
    }

}

