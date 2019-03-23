package com.atguigu.shoppingmall.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @作者 石刚
 * QQ 342532640
 * @版本 v1.0
 * 作用：缓存工具类
 */
public class CacheUtils {
    /**
     * 得到保存的String类型的数据
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
        //如果没有值就返回一个空字符串，千万不要返回null。
        return sp.getString(key,"");
    }

    /**
     * 保存数据类型
     * @param context 上下文
     * @param key 键
     * @param value 值
     */
    public static void saveString(Context context,String key,String value){
        SharedPreferences sp = context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }
}
