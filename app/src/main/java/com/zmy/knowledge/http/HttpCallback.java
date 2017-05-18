package com.zmy.knowledge.http;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by win7 on 2017/5/15.
 */

public interface HttpCallback<T> {
    void onSuccess(T object, Call call, Response response);


    void onError(Call call, Response response, Exception e);
}
