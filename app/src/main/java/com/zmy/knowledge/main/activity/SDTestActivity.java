package com.zmy.knowledge.main.activity;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.view.View;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.zmy.knowledge.R;
import com.zmy.knowledge.base.app.BaseActivity;
import com.zmy.knowledge.base.app.ViewHolder;
import com.zmy.knowledge.utlis.AUtils;


import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by win7 on 2017/5/27.
 */

public class SDTestActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_sd;
    }

    @Override
    protected void initViews(ViewHolder holder, View root) {

    }


    /**
     * 这个方法名是不是有点牛逼
     *
     * @param v
     */
    public void 获取外置sd卡路径(View v) {
        String storagePath = getStoragePath(this, true);
        AUtils.showToast("外置sd卡路径1=" + storagePath);
        //外置置sd卡路径
        String extSdcardPath = System.getenv("SECONDARY_STORAGE");
        AUtils.showToast("外置sd卡路径2=" + extSdcardPath);
    }

    /**
     * 获取内置sd卡路径
     *
     * @param v
     */
    public void 获取内置sd卡路径(View v) {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        AUtils.showToast("获取内置sd卡路径=" + externalStorageDirectory.getAbsolutePath());
    }


    /**
     * 获取扩展sd卡路径
     *
     * @param mContext
     * @param is_removale
     * @return
     */
    private static String getStoragePath(Context mContext, boolean is_removale) {

        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (is_removale == removable) {
                    return path;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 通过接口 Java Servlet 连接jdbc 获取数据返回 json
     *
     * @param view
     */
    public void httpGet(View view) {
        String url = "http://192.168.0.129:8080/MyServer/StudentInq";
        OkGo.get(url).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                AUtils.log("数据="+s);
                TextView v = (TextView) findViewById(R.id.text);
                v.setText(s);
            }
        });
    }




}
