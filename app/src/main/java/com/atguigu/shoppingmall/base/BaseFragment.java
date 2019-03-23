package com.atguigu.shoppingmall.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @作者 石刚
 * QQ 342532640
 * @版本 v1.0
 * 作用：基类Fragment
 * 首页：HomeFragment
 * 分类：TypeFragment
 * 发现: CommunityFragment
 * 购物车：ShoppingCartFragment
 * 用户中心：UserFragment
 * 都要继承该类
 */
public abstract class BaseFragment extends Fragment {
    protected Context mContext;

    /**
     * 当该类被创建的时候回调
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    /**
     * 抽象方法，由孩子实现
     *
     * @return
     */
    public abstract View initView();

    /**
     * 当Activity被创建的时候回调了这个方法
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 当子类需要联网请求数据的时候可以重写该方法。
     * 在该方法中请求联网请求，initData()方法必须
     * 执行在initView()后面，先有视图，后有数据。
     */
    public void initData() {
    }
}
