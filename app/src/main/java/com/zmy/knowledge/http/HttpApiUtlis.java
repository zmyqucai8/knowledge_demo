package com.zmy.knowledge.http;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.zmy.knowledge.http.bean.HomeDataBean;
import com.zmy.knowledge.utlis.AUtils;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by win7 on 2017/5/15.
 */

public class HttpApiUtlis {

    /**
     * 页码索引 从1开始
     */
    public static int index = 0;
    /**
     * 10=数量
     * index=页码
     */
    public static String URL_GET_HOME_DATA = "http://gank.io/api/data/Android/10/";


    public static HttpCallback mCallback;//回调接口

    /**
     * 获取home 数据
     */
    public static void getHomeData(Context context, int index, HttpCallback callback) {
        mCallback = callback;
        OkGo.get(URL_GET_HOME_DATA + index).tag(context)
                .connTimeOut(10000)
                .execute(new MyAbsCallback<HomeDataBean>() {
                    @Override
                    public void onSuccess(HomeDataBean homeDataBean, Call call, Response response) {
                        AUtils.log(homeDataBean.toString());
                        if (null != mCallback) {
                            mCallback.onSuccess(homeDataBean, call, response);
                        }
                    }
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        if (null != mCallback) {
                            mCallback.onError(call, response, e);
                        }
                    }
                });


    }
}
