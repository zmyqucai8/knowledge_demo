package com.zmy.knowledge.utlis;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.Toast;

import com.gitonway.lee.niftynotification.lib.Configuration;
import com.gitonway.lee.niftynotification.lib.Effects;
import com.gitonway.lee.niftynotification.lib.NiftyNotificationView;
import com.zmy.knowledge.R;
import com.zmy.knowledge.base.app.BaseActivity;
import com.zmy.knowledge.base.app.BaseApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by win7 on 2017/5/11.
 */

public class AUtils {

    /*是否显示log*/
    public static final boolean isShowLog = true;
    /*log tag */
    public static final String TAG = "Log";

    /*显示log*/
    public static void log(String text) {
        if (isShowLog) {
            log(TAG, text + "");
        }
    }

    public static void log(int text) {
        if (isShowLog) {
            log(TAG, String.valueOf(text));
        }
    }

    /*显示log*/
    public static void log(String tag, String text) {
        if (isShowLog) {
            Log.e(tag, text + "");
        }
    }


    /*唯一toast实例*/
    private static Toast mToast;

    /*显示一个toast*/
    public static void showToast(String msg) {

        if (mToast == null) {
            mToast = Toast.makeText(BaseApplication.getContext(), msg, Toast.LENGTH_SHORT);
        }
        if (!TextUtils.isEmpty(msg)) {
            mToast.setText(msg);
            mToast.show();
        }
    }

    //应用内通知的cfg
    public static Configuration cfg = new Configuration.Builder()
            .setAnimDuration(700)
            .setDispalyDuration(1500)
            .setBackgroundColor("#3daeb1")
            .setTextColor("#FFFFFFFF")
            .setIconBackgroundColor("#3daeb1")
            .setTextPadding(5)                      //dp
            .setViewHeight(45)                      //dp
            .setTextLines(2)                        //You had better use setViewHeight and setTextLines together
            .setTextGravity(Gravity.CENTER)         //only text def  Gravity.CENTER,contain icon Gravity.CENTER_VERTICAL
            .build();

    /**
     * 在app内顶部显示一条 notification
     */
    public static void showNotification(Activity activity, String msg, int id, Effects e) {
        showNotification(activity, msg, id, e, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * 在app内顶部显示一条 notification 默认样式
     */
    public static void showNotification(Activity activity, String msg, int id) {
        showNotification(activity, msg, id, Effects.slideIn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * 在app内顶部显示一条 notification
     */
    public static void showNotification(Activity activity, String msg) {
        showNotification(activity, msg, activity.getWindow().getDecorView().getId(), Effects.slideOnTop, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * 在app内顶部显示一条 notification
     */
    public static void showNotification(Activity activity, String msg, int id, Effects e, View.OnClickListener listener) {
        NiftyNotificationView.build(activity, msg, e, id, cfg)
                .setIcon(R.drawable.logo_withe)               //remove this line ,only text
                .setOnClickListener(listener)
                .show();
    }

    /**
     * 字体库
     */
    public static Typeface mTypeface;

    /**
     * 获取ttf字体库
     *
     * @return
     */
    public static synchronized Typeface getTTF() {
        if (mTypeface == null) {
            mTypeface = Typeface.createFromAsset(BaseApplication.getContext().getResources().getAssets(), "iconfont.ttf");
        }
        return mTypeface;
    }

    /**
     * 判断是否手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();

    }


    /**
     * 获取导航栏的高度
     *
     * @return
     */
    public static int getNavigationBarHeight(Context context) {

        boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        if (!hasMenuKey && !hasBackKey) {
            Resources resources = context.getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            //获取NavigationBar的高度
            int height = resources.getDimensionPixelSize(resourceId);
            return height;
        } else {
            return 0;
        }
    }


    /**
     * 隐藏导航栏
     *
     * @param activity
     */
    public static void hideNAVIGATION(Activity activity) {
//        View decorView = activity.getWindow().getDecorView();
//        // Hide both the navigation bar and the status bar.
//        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
//        // a general rule, you should design your app to hide the status bar whenever you
//        // hide the navigation bar.
//        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);
        if (Build.VERSION.SDK_INT < 16) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN //hide statusBar
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION; //hide navigationBar
            activity.getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        }
    }


    /**
     * 显示一个 Snackbar
     *
     * @param view
     * @param msg
     */
    public static void showSnackbar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }


    public static int index;

    public static List<String> getTestData(int count) {
        int length = index + count;
        List<String> mList = new ArrayList<>();
        for (int i = index; i < length; i++) {
            mList.add("test item = " + i);
        }
        return mList;
    }

    /**
     * dp转px
     */
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * px转dp
     */
    public static int px2dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }


}
