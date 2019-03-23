package com.atguigu.shoppingmall.shoppingcart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.atguigu.shoppingmall.app.MyApplication;
import com.atguigu.shoppingmall.home.bean.GoodsBean;
import com.atguigu.shoppingmall.utils.CacheUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者 石刚
 * QQ 342532640
 * @版本 v1.0
 * 购物车存储类，单例模式
 */
public class CartStorage {
    public static final String JSON_CART = "json_cart";
    private static CartStorage instance;
    private final Context mContext;
    /**
     * SparseArray性能优于HashMap
     */
    private SparseArray<GoodsBean> sparseArray;

    private CartStorage(Context context){
        this.mContext = context;
        /**
        把之前存储的数据读取出来。它不方便转换成Json，还需要用list转换
         */
        sparseArray = new SparseArray<>(100);
        listToSparseArray();
    }

    /**
     * 从本地读取的数据加入到SparseArray中
     */
    private void listToSparseArray() {
        List<GoodsBean> goodsBeanList = getAllData();
        //把列表数据转换成SparseArray
        for (int i = 0; i < goodsBeanList.size(); i++) {
            GoodsBean goodsBean = goodsBeanList.get(i);
            //用product_id作为key存放进去，需要将String类型转换为int类型。
            sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);
        }
    }

    /**
     * 获取本地所有的数据，在其他地方要用
     * @return
     */
    public List<GoodsBean> getAllData() {
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        //1.首先，要从本地获取。CTRL+ALT+C 抽取常量
         String json = CacheUtils.getString(mContext, JSON_CART);
        //2.使用Gson转换成列表,判断如果这个字符串不为空，才去解析
        if (!TextUtils.isEmpty(json)){
            //把String转换成一个List!!!
          goodsBeanList = new Gson().fromJson(json,new TypeToken<List<GoodsBean>>(){}.getType());
        }
         return goodsBeanList;
    }

    /**
     * 得到购物车实例
     * @return
     */
    public static CartStorage getInstance(){
        if (instance == null) {
            instance = new CartStorage(MyApplication.getContext());
        }
        return instance;
    }

    /**
     * 添加数据到购物车
     * @param goodsBean
     */
    public void addData(GoodsBean goodsBean){
        /**
         * 1.添加到内存中SparseArray，如果当前数据已经存在,
         * 就修改成Nunber递增
         */
        GoodsBean tempData = sparseArray.get(Integer.parseInt(goodsBean.getProduct_id()));
        if (tempData!=null){
            //内存中有了这条数据,number加1,因为已经有了这条数据
            tempData.setNumber(tempData.getNumber()+1);
        }else{
            tempData=goodsBean;
            /**
             * 确保有1个产品
             */
            tempData.setNumber(1);
        }
        //同步到内存中
        sparseArray.put(Integer.parseInt(tempData.getProduct_id()),tempData);

        //2.同步到本地，再保存一下
        saveLocal();
    }

    /**
     * 删除数据
     * @param goodsBean
     */
   public void deleteData(GoodsBean goodsBean){
        //1.内存中删除
        sparseArray.delete(Integer.parseInt(goodsBean.getProduct_id()));
       //2.再把内存的保存到本地
       saveLocal();
   }

    /**
     * 更新数据
     * @param goodsBean
     */
   public void updateData(GoodsBean goodsBean){
       //内存中更新
       sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);
       //同步到本地
       saveLocal();
   }

    /**
     * 保存到本地
     */
    private void saveLocal() {
        //SparseArray转换成List
        List<GoodsBean> goodsBeanList = sparseToList();

        //把列表转化为String类型，把一个List转换成Json字符串
        String json = new Gson().toJson(goodsBeanList);

        //把String数据保存
        CacheUtils.saveString(mContext,JSON_CART,json);
    }

    /**
     * 将sparseArray转换成List
     * @return
     */
    private List<GoodsBean> sparseToList() {
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        for (int i = 0; i < sparseArray.size(); i++) {
            //valueAt()或者get()都可以获取
            GoodsBean goodsBean = sparseArray.valueAt(i);
            goodsBeanList.add(goodsBean);
        }
         return goodsBeanList;
    }
}
