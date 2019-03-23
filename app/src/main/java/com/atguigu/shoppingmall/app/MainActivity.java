package com.atguigu.shoppingmall.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.atguigu.shoppingmall.community.fragment.CommunityFragment;
import com.atguigu.shoppingmall.home.fragment.HomeFragment;
import com.atguigu.shoppingmall.shoppingcart.fragment.ShoppingCartFragment;
import com.atguigu.shoppingmall.type.fragment.TypeFragment;
import com.atguigu.shoppingmall.user.fragment.UserFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {


    @Bind(R.id.frameLayout)
    FrameLayout frameLayout;
    @Bind(R.id.rg_main)
    RadioGroup rgMain;
    /**
     * 装多个Fragment的实例集合
     */
    private ArrayList<BaseFragment> mFragments;
    private int position = 0;
    /**
     * 缓存的Fragment或者上次显示的Fragment
     */

    private Fragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ButterKnife和当前Activity绑定
        ButterKnife.bind(this);
        //初始化Fragment
        initFragment();
        //设置RadioGroup的监听
        initListener();

    }

    private void initListener() {
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    //主页
                    case R.id.rb_home:
                        position = 0;
                        break;
                    //分类
                    case R.id.rb_type:
                        position = 1;
                        break;
                    //社区
                    case R.id.rb_community:
                        position = 2;
                        break;
                    //购物车
                    case R.id.rb_cart:
                        position = 3;
                        break;
                    //用户中心
                    case R.id.rb_user:
                        position = 4;
                        break;
                    default:
                        position = 0;
                        break;
                }
                //根据位置去取不同的Fragment
                BaseFragment baseFragment = getFragment(position);
                /**
                 * 第一参数，上次显示的Fragment
                 * 第二个参数，当前正要显示的Fragment。
                 */
                switchFragment(tempFragment, baseFragment);
            }
        });
        rgMain.check(R.id.rb_home);
    }

    /**
     * 添加的时候要按照顺序
     */
    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
        mFragments.add(new TypeFragment());
        mFragments.add(new CommunityFragment());
        mFragments.add(new ShoppingCartFragment());
        mFragments.add(new UserFragment());
    }

    private BaseFragment getFragment(int position) {
        if (mFragments != null && mFragments.size() > 0) {
            BaseFragment baseFragment = mFragments.get(position);
            return baseFragment;
        }
        return null;
    }

    private void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {
        //点的不相同的Fragment才进入这段代码
        if (tempFragment != nextFragment) {
            tempFragment = nextFragment;
            if (nextFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加过
                if (!nextFragment.isAdded()) {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    //添加Fragment
                    transaction.add(R.id.frameLayout, nextFragment).commit();
                } else {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }
}
