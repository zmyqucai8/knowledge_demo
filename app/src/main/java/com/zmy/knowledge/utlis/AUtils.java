package com.zmy.knowledge.utlis;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.gitonway.lee.niftynotification.lib.Configuration;
import com.gitonway.lee.niftynotification.lib.Effects;
import com.gitonway.lee.niftynotification.lib.NiftyNotificationView;
import com.zmy.knowledge.R;
import com.zmy.knowledge.base.app.BaseActivity;
import com.zmy.knowledge.base.app.BaseApplication;
import com.zmy.knowledge.contacts.ContactBean;

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
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        }
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();

    }

    //修改状态栏颜色
    public static void setStatusBarColor(Activity activity, int colorId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorId));
                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过包名 启动一个应用
     *
     * @param packagename
     */
    public static void startAppByPackagName(Context context, String packagename) {
        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = context.getPackageManager().getPackageInfo(packagename, 0);

            if (packageinfo == null) {
                return;
            }
            // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(packageinfo.packageName);
            // 通过getPackageManager()的queryIntentActivities方法遍历
            List<ResolveInfo> resolveinfoList = context.getPackageManager().queryIntentActivities(resolveIntent, 0);
            ResolveInfo resolveinfo = resolveinfoList.iterator().next();
            if (resolveinfo != null) {
                // packagename = 参数packname
                String packageName = resolveinfo.activityInfo.packageName;
                // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
                String className = resolveinfo.activityInfo.name;
                // LAUNCHER Intent
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                // 设置ComponentName参数1:packagename参数2:MainActivity路径
                ComponentName cn = new ComponentName(packageName, className);
                intent.setComponent(cn);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //是否含有中文（也包含日文和韩文）
    public static boolean isChineseChar(String str) {
        String reg = "[\u4E00-\u9FA5\uF900-\uFA2D]";
        return str.matches(reg);
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
    /**
     * 获取联系人
     */
    public static List<ContactBean> getContact(Context context) {
        List<ContactBean> list = new ArrayList<>();
        //获得所有的联系人
        Cursor cur = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        //循环遍历
        if (cur.moveToFirst()) {
            int idColumn = cur.getColumnIndex(ContactsContract.Contacts._ID);
            int displayNameColumn = cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            int sortKeyColumn = cur.getColumnIndex(ContactsContract.Contacts.SORT_KEY_PRIMARY);
            int photoIdColumn = cur.getColumnIndex(ContactsContract.Contacts.PHOTO_ID);
            int lookUpKeyColumn = cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY);
            int contactIdColumn = cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID);
            do {
                //获得联系人的ID号
                String contactId = cur.getString(idColumn);
                //获得联系人姓名
                String disPlayName = cur.getString(displayNameColumn);
                if ("啊胜".equals(disPlayName)) {
                    AUtils.log(disPlayName);
                }
                //查看该联系人有多少个电话号码。如果没有这返回值为0
                int phoneCount = cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                String sort_key = cur.getString(sortKeyColumn);
                long photoId = cur.getLong(photoIdColumn);
                String lookUpKey = cur.getString(lookUpKeyColumn);
//                int cid = cur.getInt(contactIdColumn);
                if (phoneCount > 0) {
                    String phoneNumber = "";
                    //获得联系人的电话号码 只查询类型为mobile
//                    Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                    Cursor phones = context.getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId + " and " + ContactsContract.CommonDataKinds.Phone.TYPE + "=" + ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE, null, null);
                    if (phones.moveToFirst()) {
                        do {
                            //遍历所有的电话号码
                            phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                            AUtils.log(disPlayName + "的电话=" + phoneNumber);
                        } while (phones.moveToNext());
                    }

                    if (isMobileNO(phoneNumber)) {
                        //格式化一下
                        if (phoneNumber.contains("-")) {
                            phoneNumber = phoneNumber.replace("-", "");
                        }
                        if (phoneNumber.contains(" ")) {
                            phoneNumber = phoneNumber.replace(" ", "");
                        }
                        // 创建联系人对象

                        ContactBean contact = new ContactBean();
                        contact.setDesplayName(disPlayName);
                        contact.setPhoneNum(phoneNumber);
                        contact.setSortKey(sort_key);
                        contact.setPhotoId(photoId);
                        contact.setLookUpKey(lookUpKey);
                        contact.setContactId(Integer.valueOf(contactId));
                        if (!list.contains(contact)) {
                            list.add(contact);
                        }
                    }
                    if (null != cur) {
                        phones.close();
                    }
                }

            } while (cur.moveToNext());

        }
        if (null != cur) {
            cur.close();
        }

        return list;
    }

    /**
     * 根据联系人id查询其类型为手机号的电话
     *
     * @param context
     * @param contactId
     * @param phoneCount
     * @param number
     * @return number
     */
    public static String getPhone(Context context, int contactId, int phoneCount, String number) {
//    在联系人的电话号码中有很多种，如果只想获得手机号码
        try {
            if (phoneCount >= 1) {
                Cursor phones = context.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                + " = " + contactId + " and " + ContactsContract.CommonDataKinds.Phone.TYPE + "=" + ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE, null, null);
                if (phones.moveToFirst()) {
                    do {
                        //遍历所有的电话号码
                        String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        AUtils.log("电话=" + phoneNumber);
                        number = phoneNumber;
                    } while (phones.moveToNext());
                }
                phones.close();
            }
        } catch (Exception e) {

        }
        //格式化一下
        if (number.contains("-")) {
            number = number.replace("-", "");
        }
        if (number.contains(" ")) {
            number = number.replace(" ", "");
        }

        return number;
    }


}
