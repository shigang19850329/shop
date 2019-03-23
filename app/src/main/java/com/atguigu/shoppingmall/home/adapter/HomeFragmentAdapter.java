package com.atguigu.shoppingmall.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.app.GoodsInfoActivity;
import com.atguigu.shoppingmall.home.bean.GoodsBean;
import com.atguigu.shoppingmall.home.bean.ResultBeanData;
import com.atguigu.shoppingmall.utils.Constants;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnLoadImageListener;
import com.zhy.magicviewpager.transformer.AlphaPageTransformer;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @作者 石刚
 * QQ 342532640
 * @版本 v1.0
 */
public class HomeFragmentAdapter extends RecyclerView.Adapter {
    /**
     * 广告条幅类型
     */
    private static final int BANNER = 0;
    /**
     * 频道
     */
    private static final int CHANNEL = 1;
    /**
     * 活动
     */
    private static final int ACT = 2;
    /**
     * 秒杀类型
     */
    private static final int SECKILL = 3;
    /**
     * 推荐类型
     */
    private static final int RECOMMEND = 4;
    /**
     * 热卖类型
     */
    private static final int HOT = 5;
    private static final String GOODS_BEAN = "goodsBean";
    /**
     * 当前类型
     */
    private int currentType = BANNER;
    private Context mContext;
    private ResultBeanData.ResultBean resultBean;
    /**
     * 初始化布局
     */
    private LayoutInflater mLayoutInflater;

    public HomeFragmentAdapter(Context mContext, ResultBeanData.ResultBean resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    /**
     * 相当于getView。
     * 创建ViewHolder部分代码
     *
     * @param viewGroup
     * @param viewType  当前的类型
     * @return
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == BANNER) {
            //如果类型等于banner，要返回一个BANNER的viewHolder。先不在这里传数据，后面再传也可以。
            return new BannerViewHolder(mContext, mLayoutInflater.inflate(R.layout.banner_viewpager, null));
        } else if (viewType == CHANNEL) {
            return new ChannelViewHolder(mContext, mLayoutInflater.inflate(R.layout.channel_item, null));
        } else if (viewType == ACT) {
            //这里R.layout.act_item写成了R.layout.channel_item报错了。NullPointException，空指针
            return new ActViewHolder(mContext, mLayoutInflater.inflate(R.layout.act_item, null));
        } else if (viewType == SECKILL) {
            return new SeckillViewHolder(mContext, mLayoutInflater.inflate(R.layout.seckill_item, null));
        } else if (viewType == RECOMMEND) {
            return new RecommendViewHolder(mContext, mLayoutInflater.inflate(R.layout.recommend_item, null));
        }else if (viewType==HOT){
            return new HotViewHolder(mContext,mLayoutInflater.inflate(R.layout.hot_item,null));
        }
        return null;
    }
    class HotViewHolder extends RecyclerView.ViewHolder{

        private final Context mContext;
        private TextView tv_more_hot;
        private GridView gv_hot;
        private HotGridViewAdapter adapter;

        public HotViewHolder(final Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            tv_more_hot = (TextView) itemView.findViewById(R.id.tv_more_hot);
            gv_hot =(GridView) itemView.findViewById(R.id.gv_hot);
        }
        public void setData(final List<ResultBeanData.ResultBean.HotInfoBean> hot_info) {
            //1.有数据了，2.设置GridView的适配器
            adapter=new HotGridViewAdapter(mContext,hot_info);
            gv_hot.setAdapter(adapter);

            //设置item的点击事件
            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext,"position=="+position, Toast.LENGTH_SHORT).show();
                    //热卖商品信息类
                    ResultBeanData.ResultBean.HotInfoBean hotInfoBean = hot_info.get(position);
                    //商品信息类
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setCover_price(hotInfoBean.getCover_price());
                    goodsBean.setFigure(hotInfoBean.getFigure());
                    goodsBean.setName(hotInfoBean.getName());
                    goodsBean.setProduct_id(hotInfoBean.getProduct_id());
                    startGoodsInfoActivity(goodsBean);
                }
            });
        }
    }
    class RecommendViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        private TextView tv_more_recommend;
        private GridView gv_recommend;
        private RecommendGridViewAdapter adapter;

        public RecommendViewHolder(final Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            tv_more_recommend = (TextView) itemView.findViewById(R.id.tv_more_recommend);
            gv_recommend = (GridView) itemView.findViewById(R.id.gv_recommend);
        }

        public void setData(final List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info) {
            //1.有数据了
            //2.设置适配器
            adapter = new RecommendGridViewAdapter(mContext, recommend_info);
            gv_recommend.setAdapter(adapter);

            //设置点击事件
            gv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();
                    ResultBeanData.ResultBean.RecommendInfoBean recommendInfoBean = recommend_info.get(position);
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setCover_price(recommendInfoBean.getCover_price());
                    goodsBean.setProduct_id(recommendInfoBean.getProduct_id());
                    goodsBean.setName(recommendInfoBean.getName());
                    goodsBean.setFigure(recommendInfoBean.getFigure());
                    startGoodsInfoActivity(goodsBean);
                }
            });
        }
    }

    class SeckillViewHolder extends RecyclerView.ViewHolder {
        //初始化控件
        private Context mContext;
        private TextView tv_time_seckill;
        private TextView tv_more_seckill;
        private RecyclerView rv_seckill;
        private SeckillRecyclerViewAdapter adapter;

        public SeckillViewHolder(Context mContext, View itemView) {
            super(itemView);
            tv_time_seckill = (TextView) itemView.findViewById(R.id.tv_time_seckill);
            tv_more_seckill = (TextView) itemView.findViewById(R.id.tv_more_seckill);
            rv_seckill = (RecyclerView) itemView.findViewById(R.id.rv_seckill);
            this.mContext = mContext;
        }

        /**
         * 还相差多少时间-单位毫秒
         */
        private long dt = 0;
        private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                dt = dt - 1000;
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                String time = formatter.format(new Date(dt));
                tv_time_seckill.setText(time);

                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0, 1000);
                if (dt <= 0) {
                    //把消息全部移除
                    handler.removeCallbacksAndMessages(null);
                }
            }
        };

        public void setData(final ResultBeanData.ResultBean.SeckillInfoBean seckill_info) {
            /**
             1.得到数据了
             2.设置数据，文本和RecyclerView的数据
             */
            adapter = new SeckillRecyclerViewAdapter(mContext, seckill_info.getList());
            rv_seckill.setAdapter(adapter);
            //要想适配器起作用，还要设置布局管理器
            rv_seckill.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            adapter.setOnSeckillRecycleView(new SeckillRecyclerViewAdapter.OnSeckillRecycleView() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(mContext, "秒杀==" + position, Toast.LENGTH_SHORT).show();
                    ResultBeanData.ResultBean.SeckillInfoBean.ListBean listBean =seckill_info.getList().get(position);
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setCover_price(listBean.getCover_price());
                    goodsBean.setProduct_id(listBean.getProduct_id());
                    goodsBean.setName(listBean.getName());
                    goodsBean.setFigure(listBean.getFigure());
                    startGoodsInfoActivity(goodsBean);
                }
            });

            //秒杀倒计时 -毫秒
            dt = Integer.valueOf(seckill_info.getEnd_time()) - Integer.valueOf(seckill_info.getStart_time());

            handler.sendEmptyMessageDelayed(0, 1000);
        }
    }

    class ActViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private ViewPager act_viewpager;

        public ActViewHolder(Context mContext, View itemView) {
            super(itemView);
            act_viewpager = (ViewPager) itemView.findViewById(R.id.act_viewpager);
            this.mContext = mContext;
        }

        public void setData(final List<ResultBeanData.ResultBean.ActInfoBean> act_info) {
            //设置两张图片中间的间距
            act_viewpager.setPageMargin(40);
            //ViewPage设置预加载页面数量
            act_viewpager.setOffscreenPageLimit(3);
            //设置动画，行下旋转页面动画。
            act_viewpager.setPageTransformer(true, new AlphaPageTransformer(new ScaleInTransformer()));
            //1.有数据了。2.设置适配器
            act_viewpager.setAdapter(new PagerAdapter() {
                //返回总条数
                @Override
                public int getCount() {
                    return act_info.size();
                }

                /**
                 *
                 * @param container viewpager
                 * @param position  对应页面的位置
                 * @return
                 */
                @NonNull
                @Override
                public Object instantiateItem(@NonNull ViewGroup container, final int position) {
                    //instantiate举例说明
                    ImageView imageView = new ImageView(mContext);
                    //设置它的拉伸，XY轴上的拉伸
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    Glide.with(mContext).load(Constants.BASE_URL_IMAGE + act_info.get(position).getIcon_url()).into(imageView);
                    //添加到容器当中
                    container.addView(imageView);
                    //设置点击事件
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();
                        }
                    });
                    return imageView;
                }

                @Override
                public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                    container.removeView((View) object);
                }

                /**
                 *
                 * @param view 页面
                 * @param o instantiateItem方法返回的值
                 * @return
                 */
                @Override
                public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                    return view == o;
                }
            });
        }
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private GridView gv_channel;
        private ChannelAdapter adapter;

        public ChannelViewHolder(final Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.gv_channel = (GridView) itemView.findViewById(R.id.gv_channel);
            //设置点击事件
            gv_channel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext, "position" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setData(List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info) {
            //得到数据了，再去设置GridView的适配器。
            //初始化
            adapter = new ChannelAdapter(mContext, channel_info);
            //设置适配器
            gv_channel.setAdapter(adapter);
        }
    }

    /**
     * 相当于getView中的绑定数据模块
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(resultBean.getBanner_info());
        } else if (getItemViewType(position) == CHANNEL) {
            //绑定数据
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(resultBean.getChannel_info());
        } else if (getItemViewType(position) == ACT) {
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(resultBean.getAct_info());
        } else if (getItemViewType(position) == SECKILL) {
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.setData(resultBean.getSeckill_info());
        } else if (getItemViewType(position) == RECOMMEND) {
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData(resultBean.getRecommend_info());
        }else if (getItemViewType(position)==HOT){
            HotViewHolder hotViewHolder=(HotViewHolder)holder;
            hotViewHolder.setData(resultBean.getHot_info());
        }
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private View itemView;
        /**
         * 传递进来的itemView包含banner，所以得把它实例化出来。
         */
        private Banner banner;

        public BannerViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.banner = (Banner) itemView.findViewById(R.id.banner);
        }

        public void setData(List<ResultBeanData.ResultBean.BannerInfoBean> banner_info) {
            //设置Banner数据，得到图片地址。
            List<String> imagesUrl = new ArrayList<>();
            for (int i = 0; i < banner_info.size(); i++) {
                //得到图片路径，添加进集合imagesUrl中
                String imageUrl = banner_info.get(i).getImage();
                imagesUrl.add(imageUrl);
            }
            //设置循环指示点
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            //设置手风琴效果
            banner.setBannerAnimation(Transformer.Accordion);
            //这个方法在1.3.3以后被弃用了。
            banner.setImages(imagesUrl, new OnLoadImageListener() {
                @Override
                public void OnLoadImage(ImageView view, Object url) {
                    //联网请求图片Glide
                    Glide.with(mContext).load(Constants.BASE_URL_IMAGE + url).into(view);
                }
            });
            //设置item的点击事件
            banner.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void OnBannerClick(int position) {
                    Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();
                    //启动商品信息页面
                    //startGoodsInfoActivity(goodsBean);
                }
            });
        }
    }

        /**
         * 启动商品信息列表页面
         * @param goodsBean
         */
        private void startGoodsInfoActivity(GoodsBean goodsBean) {
            Intent intent = new Intent(mContext, GoodsInfoActivity.class);
            intent.putExtra(GOODS_BEAN,goodsBean);
            mContext.startActivity(intent);
        }

    /**
     * 得到类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        //这个方法系统会调用。
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
        }
        return currentType;
    }

    /**
     * 总共有多少个item
     *
     * @return
     */
    @Override
    public int getItemCount() {
        //开发过程中，从1到6，先开发这一部分，让他运行起来，先改为1。
        return 6;
    }
}
