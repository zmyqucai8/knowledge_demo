package com.zmy.knowledge.main.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.zmy.knowledge.R;
import com.zmy.knowledge.base.app.BaseActivity;
import com.zmy.knowledge.base.app.ViewHolder;
import com.zmy.knowledge.main.bean.AppInfoBean;
import com.zmy.knowledge.main.bean.AppViewPagerAdapter;
import com.zmy.knowledge.main.bean.PagerBean;
import com.zmy.knowledge.swipeback.MySwipeBackActivity;
import com.zmy.knowledge.utlis.AUtils;
import com.zmy.knowledge.utlis.LoadingUtlis;
import com.zmy.knowledge.view.TransparentView;

import java.net.BindException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by win7 on 2017/5/18.
 * 透明桌面
 */

public class TransparentActivityDemo extends BaseActivity {
    ImageView item_image1, item_image2, item_image3, item_image4;
    TextView item_text1, item_text2, item_text3, item_text4;
    TransparentView transparentView;
    ViewPager view_pager;
    PackageManager pm;
    List<AppInfoBean> mlistAppInfo = new ArrayList<AppInfoBean>();//总app数据，格式化后只剩下上面的数据
    List<AppInfoBean> mListAppIonfoBottom = new ArrayList<AppInfoBean>();//底下的app数据
    public static final int FILTER_ALL_APP = 0; // 所有应用程序
    public static final int FILTER_SYSTEM_APP = 1; // 系统程序
    public static final int FILTER_THIRD_APP = 2; // 第三方应用程序
    public static final int FILTER_SDCARD_APP = 3; // 安装在SDCard的应用程序
    LoadingUtlis utlis;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                utlis.hideLoading();
                //数据加载完毕 设置viewpager 1个viewpager 页面最多显示20个
                if (mlistAppInfo.size() > 0) {

                    setBottomAPP();
                    List<PagerBean> pagerBeen = formData();
                    setViewPagerData(pagerBeen);
                } else {
                    //没有第三方应用？ 那你是怎么打开我的应用的？
                }
            }
        }
    };

    /**
     * 设置底部几个app
     */
    private void setBottomAPP() {
        for (int i = 0; i < mListAppIonfoBottom.size(); i++) {
            String appLabel = mListAppIonfoBottom.get(i).getAppLabel();
            Drawable appIcon = mListAppIonfoBottom.get(i).getAppIcon();
            final String pkgName = mListAppIonfoBottom.get(i).getPkgName();
            switch (appLabel) {
                case "电话":
                    item_image1.setImageDrawable(appIcon);
                    item_text1.setText(appLabel);
                    item_image1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AUtils.startAppByPackagName(TransparentActivityDemo.this, pkgName);
                        }
                    });
                    break;
                case "QQ":
                    item_image2.setImageDrawable(appIcon);
                    item_text2.setText(appLabel);
                    item_image2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AUtils.startAppByPackagName(TransparentActivityDemo.this, pkgName);
                        }
                    });
                    break;
                case "微信":
                    item_image3.setImageDrawable(appIcon);
                    item_text3.setText(appLabel);
                    item_image3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AUtils.startAppByPackagName(TransparentActivityDemo.this, pkgName);
                        }
                    });
                    break;
                case "微博":
                    item_image4.setImageDrawable(appIcon);
                    item_text4.setText(appLabel);
                    item_image4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AUtils.startAppByPackagName(TransparentActivityDemo.this, pkgName);
                        }
                    });
                    break;
            }
        }
    }

    /**
     * 设置viewpager数据
     *
     * @param pagerBeen
     */
    private void setViewPagerData(List<PagerBean> pagerBeen) {
        AppViewPagerAdapter mAdapter = new AppViewPagerAdapter(this, pagerBeen);
        view_pager.setAdapter(mAdapter);
    }


    /**
     * 格式化数据成viewpager的数据
     *
     * @return
     */
    private List<PagerBean> formData() {
        List<PagerBean> pagerData = new ArrayList<PagerBean>();
        List<AppInfoBean> list = new ArrayList<AppInfoBean>();
        int y = mlistAppInfo.size() % 20;
        int s = (mlistAppInfo.size() - y) / 20;
        AUtils.log("余=" + y);
        AUtils.log("商=" + s);
        for (int i = 0; i < mlistAppInfo.size(); i++) {
            list.add(mlistAppInfo.get(i));
            if (i == mlistAppInfo.size() - 1 || list.size() == 20) {
                PagerBean bean = new PagerBean();
                bean.list = new ArrayList<>();
                bean.list.addAll(list);
                //添加到集合
                pagerData.add(bean);
                //清除变量数据
                list.clear();
            }
        }

        AUtils.log("格式化后的数据长度= " + pagerData.size());
        return pagerData;
    }

    @Override
    protected int getLayoutId() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        return R.layout.transparent_activity;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {
//        AUtils.setStatusBarColor(this, R.color.black);
        transparentView = holder.get(R.id.transparentView);
        view_pager = holder.get(R.id.view_pager);
        item_image1 = holder.get(R.id.item_image1);
        item_text1 = holder.get(R.id.item_text1);
        item_image2 = holder.get(R.id.item_image2);
        item_text2 = holder.get(R.id.item_text2);
        item_image3 = holder.get(R.id.item_image3);
        item_text3 = holder.get(R.id.item_text3);
        item_image4 = holder.get(R.id.item_image4);
        item_text4 = holder.get(R.id.item_text4);
        initData();
    }

    /*获取数据*/
    private void initData() {


        utlis = new LoadingUtlis(this);
        utlis.showLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mlistAppInfo.addAll(queryFilterAppInfo(1));
                mlistAppInfo.addAll(queryFilterAppInfo(2));
                AUtils.log("应用数量= " + mlistAppInfo.size());
                for (int i = 0; i < mlistAppInfo.size(); i++) {
                    AUtils.log(mlistAppInfo.get(i).toString());
                    String pkgName = mlistAppInfo.get(i).getPkgName();
                    String appLabel = mlistAppInfo.get(i).getAppLabel();
                    if ("电话".equals(appLabel)
                            || "QQ".equals(appLabel)
                            || "微信".equals(appLabel)
                            || "微博".equals(appLabel)) {
                        mListAppIonfoBottom.add(mlistAppInfo.get(i));
                    }
                    if (appLabel.contains("com") || pkgName.contains("vivo") || pkgName.contains("android") || pkgName.contains("google") || pkgName.contains("org")) {

                        mlistAppInfo.remove(i);
                        AUtils.log("移除的包名=" + pkgName);
                    }

                }
                handler.sendEmptyMessage(1);
            }
        }).start();


    }

    // 根据查询条件，查询特定的ApplicationInfo
    private List<AppInfoBean> queryFilterAppInfo(int filter) {

        pm = getPackageManager();
        // 查询所有已经安装的应用程序
        List<ApplicationInfo> listAppcations = pm
                .getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        Collections.sort(listAppcations,
                new ApplicationInfo.DisplayNameComparator(pm));// 排序
        List<AppInfoBean> appInfos = new ArrayList<AppInfoBean>(); // 保存过滤查到的AppInfo
        // 根据条件来过滤
        switch (filter) {
            case FILTER_ALL_APP: // 所有应用程序
                appInfos.clear();
                for (ApplicationInfo app : listAppcations) {
                    appInfos.add(getAppInfo(app));
                }
                return appInfos;
            case FILTER_SYSTEM_APP: // 系统程序
                appInfos.clear();
                for (ApplicationInfo app : listAppcations) {
                    if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                        appInfos.add(getAppInfo(app));
                    }
                }
                return appInfos;
            case FILTER_THIRD_APP: // 第三方应用程序
                appInfos.clear();
                for (ApplicationInfo app : listAppcations) {
                    //非系统程序
                    if ((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                        appInfos.add(getAppInfo(app));
                    }
                    //本来是系统程序，被用户手动更新后，该系统程序也成为第三方应用程序了
                    else if ((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                        appInfos.add(getAppInfo(app));
                    }
                }
                break;
            case FILTER_SDCARD_APP: // 安装在SDCard的应用程序
                appInfos.clear();
                for (ApplicationInfo app : listAppcations) {
                    if ((app.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
                        appInfos.add(getAppInfo(app));
                    }
                }
                return appInfos;
            default:
                return null;
        }
        return appInfos;
    }

    // 构造一个AppInfo对象 ，并赋值
    private AppInfoBean getAppInfo(ApplicationInfo app) {
        AppInfoBean appInfo = new AppInfoBean();
        appInfo.setAppLabel((String) app.loadLabel(pm));
        appInfo.setAppIcon(app.loadIcon(pm));
        appInfo.setPkgName(app.packageName);
        return appInfo;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU) {
            AUtils.log(keyCode);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
