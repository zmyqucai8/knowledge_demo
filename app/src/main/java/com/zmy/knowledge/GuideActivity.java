package com.zmy.knowledge;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.VideoView;

import com.zmy.knowledge.base.app.BaseActivity;
import com.zmy.knowledge.base.app.ViewHolder;
import com.zmy.knowledge.utlis.SPUtils;


/**
 * Created by win7 on 2017/5/16.
 */

public class GuideActivity extends BaseActivity {

    VideoView mVideoView;//视频播放控件

    @Override
    protected int getLayoutId() {
//        AUtils.hideNAVIGATION(this);
        return R.layout.activity_guide;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
        //这里肯定是要播放一个mp4的视频 ，我去哪里找一个免费的呢。
        mVideoView = holder.get(R.id.videoview);
        final View view = holder.get(R.id.btn_ok);
        //本地的视频 需要在手机SD卡根目录添加一个 fl1234.mp4 视频
        mVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.test1));
        //播放完成回调
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                view.setVisibility(View.VISIBLE);
                mVideoView.start();
            }
        });
        //开始播放视频
        mVideoView.start();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击next
                SPUtils.setGuideApp(GuideActivity.this, true);
                startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                finish();
            }
        });

    }


}
