package com.atguigu.shoppingmall.community.fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.shoppingmall.base.BaseFragment;

/**
 * @作者 石刚
 * QQ 342532640
 * @版本 v1.0
 * 作用：发现页面的Fragment
 */
public class CommunityFragment extends BaseFragment {
    public static final String TAG = "CommunityFragment";
    private TextView mTextView;

    @Override
    public View initView() {
        Log.e(TAG, "发现页面的Fragment的UI被初始化了");
        //这里用的父类的context
        mTextView = new TextView(mContext);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setTextSize(25);
        return mTextView;
    }

    @Override
    public void initData() {
        Log.e(TAG, "发现页面的Fragment的数据被初始化了");
        super.initData();
        mTextView.setText("我是发现页面的内容");
    }
}

