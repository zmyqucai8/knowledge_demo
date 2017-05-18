package com.zmy.knowledge.utlis;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by win7 on 2017/4/28.
 * SharedPreferences快捷操作类， 以后都写在这
 */
public class SPUtils {

    public static SharedPreferences mSp;
    /**
     * xml 文件名 ， 禁止修改
     */
    public static final String SP_NAME = "knowledge";

    /**
     * 获取Preferences 对象
     *
     * @param context
     * @return
     */
    public static SharedPreferences getPreferences(Context context) {
        if (mSp == null) {
            mSp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return mSp;
    }


    /**
     * 设置 user.username
     */
    public static void setUserName(Context context, String name) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString("username", name);
        editor.commit();
    }

    /**
     * 获取user.username
     */
    public static String getUserName(Context context) {
        return getPreferences(context).getString("username", "");
    }

    /**
     * 设置用户密码
     */
    public static void setUserPwd(Context context, String pwd) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString("user.pwd", pwd);
        editor.commit();
    }

    /**
     * 获取用户密码
     */
    public static String getUserPwd(Context context) {
        return getPreferences(context).getString("user.pwd", "");
    }

    /**
     * 设置用户手机
     */
    public static void setUserPhone(Context context, String pwd) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString("user.phone", pwd);
        editor.commit();
    }

    /**
     * 获取用户手机
     */
    public static String getUserPhone(Context context) {
        return getPreferences(context).getString("user.phone", "");
    }


    /**
     * 设置是否显示过guide
     */
    public static void setGuideApp(Context context, boolean guide) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean("user.guide", guide);
        editor.commit();
    }

    /**
     * 获取是否显示过guide
     */
    public static boolean getGuideApp(Context context) {
        return getPreferences(context).getBoolean("user.guide", false);
    }

    /**
     * 清除用户信息
     */
    public static void cleanUserInfo(Context context){
        SharedPreferences.Editor edit = getPreferences(context).edit();
        edit.clear();
        edit.commit();
    }

}
