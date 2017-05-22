package com.zmy.knowledge.view;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.zmy.knowledge.utlis.AUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by win7 on 2017/5/19.
 * <p>
 * 视频预览
 */

public class TransparentView extends SurfaceView implements
        SurfaceHolder.Callback, Camera.AutoFocusCallback {
    private static final String TAG = "Kintai";

    private static SurfaceHolder holder;
    private Camera mCamera;

    public TransparentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "new View ...");

        holder = getHolder();//后面会用到！
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        Log.i(TAG, "surfaceCreated...");
        if (mCamera == null) {
            initCamera();
        }
        try {
            mCamera.setPreviewDisplay(holder);//整个程序的核心，相机预览的内容放在 holder
            mCamera.startPreview();//该方法只有相机开启后才能调用
            //自动聚焦
            mCamera.autoFocus(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化相机
     */
    private void initCamera() {
        mCamera = Camera.open();//开启相机，可以放参数 0 或 1，分别代表前置、后置摄像头，默认为 0
        //判断是否可以自动聚焦
        Camera.Parameters params = mCamera.getParameters();
        List<String> focusModes = params.getSupportedFocusModes();
        if (focusModes.contains("auto")) {
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }
        //判断白平衡模式
        List<String> whiteBlanceMode = params.getSupportedWhiteBalance();
        if (whiteBlanceMode.contains("auto")) {
            params.setWhiteBalance("auto");
        }
        mCamera.setParameters(params);
        mCamera.setDisplayOrientation(90);
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        Log.i(TAG, "surfaceChanged...");

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        Log.i(TAG, "surfaceChanged...");
        if (mCamera != null) {
            mCamera.release();//释放相机资源
            mCamera = null;
        }
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        AUtils.log("对焦结果：" + success);
    }


}
