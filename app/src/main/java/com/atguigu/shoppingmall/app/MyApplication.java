package com.atguigu.shoppingmall.app;

import android.app.Application;
import android.content.Context;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @作者 石刚
 * QQ 342532640
 * @版本 v1.0
 * 整个软件
 */
public class MyApplication extends Application {
    public static Context getContext() {
        return mContext;
    }

    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        //初始化OkhttpUtils
        initOkhttpClient();
    }

    private void initOkhttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                //拦截器.addInterceptor(new LoggerInterceptor("TAG"))
                //链接超时
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                //读取超时
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }
}
