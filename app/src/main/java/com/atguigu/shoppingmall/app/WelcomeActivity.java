package com.atguigu.shoppingmall.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.atguigu.shoppingmall.R;

public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //设置两秒钟进入主页面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //启动主页面
                //在主线程，不能做耗时操作
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                //关闭当前页面
                finish();
            }
        }, 2000);
    }
}
