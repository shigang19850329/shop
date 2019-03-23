package com.atguigu.shoppingmall.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
 * 频道的适配器
 */
public class ChannelAdapter extends BaseAdapter {
    private final Context mContext;
    private List<ResultBeanData.ResultBean.ChannelInfoBean> dates;

    public ChannelAdapter(Context context, List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info) {
        this.mContext = context;
        this.dates = channel_info;
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_channel, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_channel);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_channel);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //根据位置得到对应的数据
        ResultBeanData.ResultBean.ChannelInfoBean channelInfoBean = dates.get(position);
        //用Glide加载图片。
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + channelInfoBean.getImage()).into(viewHolder.iv_icon);
        viewHolder.tv_title.setText(channelInfoBean.getChannel_name());
        return convertView;
    }

    static class ViewHolder {
        ImageView iv_icon;
        TextView tv_title;
    }
}
