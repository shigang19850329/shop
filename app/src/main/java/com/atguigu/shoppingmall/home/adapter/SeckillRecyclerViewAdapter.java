package com.atguigu.shoppingmall.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.home.bean.ResultBeanData;
import com.atguigu.shoppingmall.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * @作者 石刚
 * QQ 342532640
 * @版本 v1.0
 * 秒杀适配器
 */
public class SeckillRecyclerViewAdapter extends RecyclerView.Adapter<SeckillRecyclerViewAdapter.ViewHolder> {
    private final Context mContext;
    private final List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> list;

    public SeckillRecyclerViewAdapter(Context mContext, List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = View.inflate(mContext, R.layout.item_seckill, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        //1.根据位置得到对应数据，
        ResultBeanData.ResultBean.SeckillInfoBean.ListBean listBean = list.get(position);
        // 2.绑定数据，加载图片
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + listBean.getFigure()).into(viewHolder.iv_figure);
        //设置价格
        viewHolder.tv_cover_price.setText(listBean.getCover_price());
        viewHolder.tv_origin_price.setText(listBean.getOrigin_price());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_figure;
        private TextView tv_cover_price;
        private TextView tv_origin_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_figure = (ImageView) itemView.findViewById(R.id.iv_figure);
            tv_cover_price = (TextView) itemView.findViewById(R.id.tv_cover_price);
            tv_origin_price = (TextView) itemView.findViewById(R.id.tv_origin_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSeckillRecycleView!=null){
                        onSeckillRecycleView.onItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    /**
     * 监听器
     */
    public interface OnSeckillRecycleView {
        /**
         * 当某条被点击的时候回调。
         *
         * @param position
         */
        public void onItemClick(int position);
    }

    private OnSeckillRecycleView onSeckillRecycleView;

    /**
     * 设置item的监听
     * @param onSeckillRecycleView
     */
    public void setOnSeckillRecycleView(OnSeckillRecycleView onSeckillRecycleView) {
        this.onSeckillRecycleView = onSeckillRecycleView;
    }
}
