package com.zmy.knowledge.http;

import com.google.gson.Gson;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Created by win7 on 2017/5/15.
 */

public abstract class MyAbsCallback<T> extends AbsCallback<T> {

    @Override
    public T convertSuccess(Response response) throws Exception {
        String s = StringConvert.create().convertSuccess(response);
        response.close();
        Type type = this.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            //如果用户写了泛型，就会进入这里，否者不会执行
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Class beanClass = (Class) parameterizedType.getActualTypeArguments()[0];
            Gson gson = new Gson();
            return (T) gson.fromJson(s, beanClass);
        }
        return (T) response;
    }
}
