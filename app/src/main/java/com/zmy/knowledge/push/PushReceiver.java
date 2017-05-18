package com.zmy.knowledge.push;

import android.content.Context;

import com.baidu.android.pushservice.PushMessageReceiver;
import com.zmy.knowledge.utlis.AUtils;

import java.util.List;

/**
 * Created by win7 on 2017/5/16.
 */

public class PushReceiver extends PushMessageReceiver {
    @Override
    public void onBind(Context context, int i, String s, String s1, String s2, String s3) {
        AUtils.log("绑定成功");
        AUtils.log(s);
        AUtils.log(s1);
        AUtils.log(s2);
        AUtils.log(s3);
    }

    @Override
    public void onUnbind(Context context, int i, String s) {
        AUtils.log("解绑成功");
        AUtils.log(i+"");
        AUtils.log(s);

    }

    @Override
    public void onSetTags(Context context, int i, List<String> list, List<String> list1, String s) {

    }

    @Override
    public void onDelTags(Context context, int i, List<String> list, List<String> list1, String s) {

    }

    @Override
    public void onListTags(Context context, int i, List<String> list, String s) {

    }

    @Override
    public void onMessage(Context context, String s, String s1) {
        AUtils.log("接收到透传消息");
        AUtils.log(s);
        AUtils.log(s1);
    }

    @Override
    public void onNotificationClicked(Context context, String s, String s1, String s2) {
        AUtils.log("用户点击通知回调");
        AUtils.log(s);
        AUtils.log(s1);
        AUtils.log(s2);

    }

    @Override
    public void onNotificationArrived(Context context, String s, String s1, String s2) {

    }
}
