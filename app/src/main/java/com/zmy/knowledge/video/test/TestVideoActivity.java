package com.zmy.knowledge.video.test;

import android.os.Environment;
import android.view.View;

import com.mabeijianxi.smallvideorecord2.DeviceUtils;
import com.mabeijianxi.smallvideorecord2.JianXiCamera;
import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;
import com.mabeijianxi.smallvideorecord2.model.AutoVBRMode;
import com.mabeijianxi.smallvideorecord2.model.BaseMediaBitrateConfig;
import com.mabeijianxi.smallvideorecord2.model.MediaRecorderConfig;
import com.zmy.knowledge.R;
import com.zmy.knowledge.base.app.BaseActivity;
import com.zmy.knowledge.base.app.ViewHolder;
import com.zmy.knowledge.video.SendSmallVideoActivity;

import java.io.File;

/**
 * Created by win7 on 2017/7/31.
 */

public class TestVideoActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.testvideo;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        initSmallVideo();
    }

    public void go(View view) {
        BaseMediaBitrateConfig recordMode;
        BaseMediaBitrateConfig compressMode = null;
        recordMode = new AutoVBRMode();

        //设置压缩转码速度
//        if (!spinner_record.getSelectedItem().toString().equals("none")) {
//            recordMode.setVelocity(spinner_record.getSelectedItem().toString());
//        }

        MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
                .fullScreen(true)//全屏
                .smallVideoWidth(0)//全屏宽度为0
                .smallVideoHeight(480)//高度
                .recordTimeMax(30000)//最大时长
                .recordTimeMin(10000)//最小时长
                .maxFrameRate(20)//帧率
                .videoBitrate(580000)//比特率
                .captureThumbnailsTime(1)//捕捉缩略图时间
                .build();
        MediaRecorderActivity.goSmallVideoRecorder(this, SendSmallVideoActivity.class.getName(), config);

    }
    public static void initSmallVideo() {
        // 设置拍摄视频缓存路径
        File dcim = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (DeviceUtils.isZte()) {
            if (dcim.exists()) {
                JianXiCamera.setVideoCachePath(dcim + "/mabeijianxi/");
            } else {
                JianXiCamera.setVideoCachePath(dcim.getPath().replace("/sdcard/",
                        "/sdcard-ext/")
                        + "/mabeijianxi/");
            }
        } else {
            JianXiCamera.setVideoCachePath(dcim + "/mabeijianxi/");
        }
        // 初始化拍摄
        JianXiCamera.initialize(false, null);
    }
}
