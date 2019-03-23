package com.atguigu.shoppingmall.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.home.bean.GoodsBean;
import com.atguigu.shoppingmall.shoppingcart.utils.CartStorage;
import com.atguigu.shoppingmall.utils.Constants;
import com.bumptech.glide.Glide;

public class GoodsInfoActivity extends Activity implements View.OnClickListener {
    private ImageButton ibGoodInfoBack;
    private ImageButton ibGoodInfoMore;
    private ImageView ivGoodInfoImage;
    private TextView tvGoodInfoName;
    private TextView tvGoodInfoDesc;
    private TextView tvGoodInfoPrice;
    private TextView tvGoodInfoStore;
    private TextView tvGoodInfoStyle;
    private WebView wbGoodInfoMore;
    private LinearLayout llGoodsRoot;
    private TextView tvGoodInfoCallcenter;
    private TextView tvGoodInfoCollection;
    private TextView tvGoodInfoCart;
    private Button btnGoodInfoAddcart;
    /**
     * 自己手动实例化的
     */
    private TextView tv_more_share;
    private TextView tv_more_search;
    private TextView tv_more_home;
    private Button btn_more;
    private GoodsBean goodsBean;

    /**
     * findViewById
     */
    private void findViews() {
        ibGoodInfoBack = (ImageButton) findViewById(R.id.ib_good_info_back);
        ibGoodInfoMore = (ImageButton) findViewById(R.id.ib_good_info_more);
        ivGoodInfoImage = (ImageView) findViewById(R.id.iv_good_info_image);
        tvGoodInfoName = (TextView) findViewById(R.id.tv_good_info_name);
        tvGoodInfoDesc = (TextView) findViewById(R.id.tv_good_info_desc);
        tvGoodInfoPrice = (TextView) findViewById(R.id.tv_good_info_price);
        tvGoodInfoStore = (TextView) findViewById(R.id.tv_good_info_store);
        tvGoodInfoStyle = (TextView) findViewById(R.id.tv_good_info_style);
        wbGoodInfoMore = (WebView) findViewById(R.id.wb_good_info_more);
        llGoodsRoot = (LinearLayout) findViewById(R.id.ll_goods_root);

        tv_more_share = (TextView) findViewById(R.id.tv_more_share);
        tv_more_home = (TextView) findViewById(R.id.tv_more_home);
        tv_more_search = (TextView) findViewById(R.id.tv_more_search);
        btn_more = (Button) findViewById(R.id.btn_more);

        tvGoodInfoCallcenter = (TextView) findViewById(R.id.tv_good_info_callcenter);
        tvGoodInfoCollection = (TextView) findViewById(R.id.tv_good_info_collection);
        tvGoodInfoCart = (TextView) findViewById(R.id.tv_good_info_cart);
        btnGoodInfoAddcart = (Button) findViewById(R.id.btn_good_info_addcart);

        ibGoodInfoBack.setOnClickListener(this);
        ibGoodInfoMore.setOnClickListener(this);
        btnGoodInfoAddcart.setOnClickListener(this);

        //联系客服
        tvGoodInfoCallcenter.setOnClickListener(this);
        //收藏
        tvGoodInfoCollection.setOnClickListener(this);
        //跳转到购物车
        tvGoodInfoCart.setOnClickListener(this);

        //设置点击事件
        tv_more_search.setOnClickListener(this);
        tv_more_share.setOnClickListener(this);
        tv_more_home.setOnClickListener(this);
    }

    /**
     * 设置点击事件
     */
    @Override
    public void onClick(View v) {
        if (v == ibGoodInfoBack) {
            // 返回
            finish();
        } else if (v == ibGoodInfoMore) {
            //更多
            Toast.makeText(this, "更多", Toast.LENGTH_SHORT).show();
        } else if (v == btnGoodInfoAddcart) {
            CartStorage.getInstance().addData(goodsBean);
            //添加到购物车
            Toast.makeText(this, "添加成功了", Toast.LENGTH_SHORT).show();
        } else if (v == tvGoodInfoCallcenter) {
            //联系客服
            Toast.makeText(this, "呼叫客服中心", Toast.LENGTH_SHORT).show();
        } else if (v == tvGoodInfoCollection) {
            //收藏
            Toast.makeText(this, "收藏", Toast.LENGTH_SHORT).show();
        } else if (v == tvGoodInfoCart) {
            //跳转到购物车
            Toast.makeText(this, "跳转到购物车", Toast.LENGTH_SHORT).show();
        }else if (v==tv_more_share){
            //分享
            Toast.makeText(this,"分享", Toast.LENGTH_SHORT).show();
        }else if (v==tv_more_search){
            //搜索
            Toast.makeText(this,"搜索", Toast.LENGTH_SHORT).show();
        }else if (v==tv_more_home){
            Toast.makeText(this,"主页面", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        findViews();

        //接收数据 CTRL+ALT+F 抽取全局变量
        goodsBean = (GoodsBean) getIntent().getSerializableExtra("goodsBean");
        if (goodsBean!=null){
            //Toast.makeText(this,"goodsBean=="+goodsBean.toString(), Toast.LENGTH_SHORT).show();
            setDataForView(goodsBean);
        }
    }

    /**
     * 设置商品页面的数据
     * @param goodsBean
     */
    private void setDataForView(GoodsBean goodsBean) {
        //设置图片，iv_good_info_image
        Glide.with(this).load(Constants.BASE_URL_IMAGE+goodsBean.getFigure()).into(ivGoodInfoImage);
        //设置文本
        tvGoodInfoName.setText(goodsBean.getName());
       //设置价格
        tvGoodInfoPrice.setText("¥"+goodsBean.getCover_price());
        //将来根据产品号去加载对应的网页
        setWebViewData(goodsBean.getProduct_id());

    }

    private void setWebViewData(String product_id) {
        if (product_id!=null){
            wbGoodInfoMore.loadUrl("http://www.atguigu.com");
            //设置支持JavaScript,得到一个设置
            WebSettings webSettings = wbGoodInfoMore.getSettings();
            //支持双击页面变小变大
            webSettings.setUseWideViewPort(true);
            //支持JavaScript
            webSettings.setJavaScriptEnabled(true);
            //优先使用缓存
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            //设置一个浏览器监听
            wbGoodInfoMore.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    //返回值是true的时候用WebView打开，为false调用系统浏览器或第三方
                    view.loadUrl(url);
                    return true;
                }
//                @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    //在这个版本以上才可以。
//                    view.loadUrl(request.getUrl().toString());
//                }
//                //要返回true，监听这个打开浏览器的行为。
//                return true;
//            }
        });
        }
    }
}
