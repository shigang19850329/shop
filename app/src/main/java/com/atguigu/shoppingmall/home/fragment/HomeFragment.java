package com.atguigu.shoppingmall.home.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.atguigu.shoppingmall.home.adapter.HomeFragmentAdapter;
import com.atguigu.shoppingmall.home.bean.ResultBeanData;
import com.atguigu.shoppingmall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * @作者 石刚
 * QQ 342532640
 * @版本 v1.0
 * 作用：主页面的Fragment
 */
public class HomeFragment extends BaseFragment {
    public static final String TAG = "HomeFragment";
    private TextView tv_search_home;
    private TextView tv_message_home;
    private RecyclerView rvHome;
    private ImageView ib_Top;
    /**
     * 返回的数据
     */
    private ResultBeanData.ResultBean mResultBean;
    private HomeFragmentAdapter adapter;

    @Override
    public View initView() {
        Log.e(TAG, "主页面Fragment的UI被初始化了");
        //这里用的父类的context
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        tv_search_home = (TextView) view.findViewById(R.id.tv_search_home);
        rvHome = (RecyclerView) view.findViewById(R.id.rv_home);
        tv_message_home = (TextView) view.findViewById(R.id.tv_message_home);
        ib_Top = (ImageView) view.findViewById(R.id.ib_top);

        //设置点击事件
        initListener();
        return view;
    }

    private void initListener() {
        //置顶的监听
        ib_Top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击这里回到顶部，这里用到的是RecycleView的scrollToPosition()方法，回到顶部
                rvHome.scrollToPosition(0);
            }
        });
        //搜索的监听
        tv_search_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
            }
        });
        //消息的监听
        tv_message_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "进入消息中心", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG, "主页面Fragment的数据被初始化了");
        //联网请求主页数据
        getDataFromNet();
    }

    private void getDataFromNet() {
        String url = Constants.HOME_URL;
        OkHttpUtils.get()
                .url(url)
                // .addParams("username","hyman")
                // .addParams("password","123")
                .build()
                .execute(new StringCallback() {
                    /**
                     * 当请求失败的时候回调这个方法
                     * @param call
                     * @param e
                     * @param id
                     */
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "首页请求失败==" + e.getMessage());
                    }

                    /**
                     * 当联网请求成功的时候回调这个方法。
                     * @param response 请求成功的数据
                     * @param id
                     */
                    @Override
                    public void onResponse(String response, int id) {
                        //Log.e(TAG, "首页请求成功==" + response);
                        //解析数据
                        processData(response);
                    }
                });
    }

    private void processData(String json) {
        ResultBeanData resultBeanData = JSON.parseObject(json, ResultBeanData.class);
        //写成类的成员变量
        mResultBean = resultBeanData.getResult();
        if (mResultBean!=null){
            //不为空则表示有数据，设置适配器。
            adapter = new HomeFragmentAdapter(mContext,mResultBean);
            rvHome.setAdapter(adapter);
            GridLayoutManager manager = new GridLayoutManager(mContext,1);
            //设置跨度大小监听
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position<=3){
                        //设置悬浮按钮隐藏
                        ib_Top.setVisibility(View.GONE);
                    }else{
                        //显示
                        ib_Top.setVisibility(View.VISIBLE);
                    }
                    //这里只能返回1
                    return 1;
                }
            });
            //设置布局管理者,用一个表格的布局管理者，只有一列
            rvHome.setLayoutManager(manager);
        }else{
            //为空表示没有数据

        }
        Log.e(TAG, "解析成功===" + mResultBean.getHot_info().get(0).getName());
    }
}
