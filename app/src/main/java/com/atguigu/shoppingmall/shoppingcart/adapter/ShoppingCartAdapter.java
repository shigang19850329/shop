package com.atguigu.shoppingmall.shoppingcart.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.home.bean.GoodsBean;
import com.atguigu.shoppingmall.shoppingcart.utils.CartStorage;
import com.atguigu.shoppingmall.shoppingcart.view.AddSubView;
import com.atguigu.shoppingmall.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * @作者 石刚
 * QQ 342532640
 * @版本 v1.0
 * 适配器的构造方法
 */
public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {
    private final Context mContext;
    private final List<GoodsBean> datas;
    private final TextView tvShopcartTotal;
    private final CheckBox checkboxAll;
    /**
     * 完成状态下的删除CheckBox
     */
    private final CheckBox cbAll;

    public ShoppingCartAdapter(Context mContext, List<GoodsBean> goodsBeanList, TextView tvShopcartTotal, CheckBox checkboxAll,CheckBox cbAll) {
        this.mContext = mContext;
        this.datas = goodsBeanList;
        this.tvShopcartTotal = tvShopcartTotal;
        this.checkboxAll = checkboxAll;
        this.cbAll = cbAll;
        showTotalPrice();

        //设置点击事件
        setListener();
        //校验是否全选
        checkAll();
    }

    /**
     * 监听点击某一条的更改选中状态
     */
    private void setListener() {
        setOnItemClickListener(new onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //1.根据位置找到对应的Bean对象
                GoodsBean goodsBean = datas.get(position);
                //2.设置取反状态
                goodsBean.setSelected(!goodsBean.isSelected());
                //3.刷新状态
                notifyItemChanged(position);
                //4.检验是否全选
                checkAll();
                //5.重新计算总价格
                showTotalPrice();
            }
        });

        //设置checkBox的点击事件
        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1.得到状态
                boolean isCheck = checkboxAll.isChecked();

                //2.根据状态设置我们的全选和非全选
                checkAll_None(isCheck);
                //3.计算总价格
                showTotalPrice();
            }
        });
        //设置完成状态下的CheckBox的点击事件
        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCheck = cbAll.isChecked();
               checkAll_None(isCheck);
               //不需要再计算总价格，因为价格没有显示。
            }
        });
    }

    /**
     * 设置全选和非全选
     *
     * @param isCheck
     */
   public void checkAll_None(boolean isCheck) {
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                //设置跟随全选一致
                goodsBean.setSelected(isCheck);
                //更新适配器信息
                notifyItemChanged(i);
            }
        }
    }

    /**
     * 校验是否所有条目被全部选中
     */
    public void checkAll() {
        //选判断集合是否为空
        if (datas != null && datas.size() > 0) {
            int number = 0;
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (!goodsBean.isSelected()) {
                    //非全选
                    checkboxAll.setChecked(false);
                    cbAll.setChecked(false);
                } else {
                    //选中的
                    number++;
                }
            }
            if (number == datas.size()) {
                //全选
                checkboxAll.setChecked(true);
                cbAll.setChecked(true);
            }
        }else{
            //非全选
            checkboxAll.setChecked(false);
            cbAll.setChecked(false);
        }
    }

    public void showTotalPrice() {
        tvShopcartTotal.setText("合计：" + getTotalPrice());
    }


    /**
     * 计算总价格
     *
     * @return
     */
    public double getTotalPrice() {
        double totalPrice = 0.0;
        //先判断集合是否有数据
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                //只计算选中的
                if (goodsBean.isSelected()) {
                    totalPrice = totalPrice + Double.valueOf(goodsBean.getNumber()) * Double.valueOf(goodsBean.getCover_price());
                }
            }
        }
        return totalPrice;
    }

    /**
     * 删除购物车中的条目
     */
    public void deleteData() {
        if (datas!=null&&datas.size()>0){
            //删除选中的
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                if (goodsBean.isSelected()){
                    //内存中移除，列表中移除
                    datas.remove(goodsBean);
                    //本地要保存，刷新
                    CartStorage.getInstance().deleteData(goodsBean);
                    //刷新
                    notifyItemRemoved(i);
                    //移除以后size()变小；
                    i--;
                }
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox cb_gov;
        private ImageView iv_gov;
        private TextView tv_desc_gov;
        private TextView tv_price_gov;
        private AddSubView addSubView;

        /**
         * 构造方法
         *
         * @param itemView
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cb_gov = (CheckBox) itemView.findViewById(R.id.cb_gov);
            iv_gov = (ImageView) itemView.findViewById(R.id.iv_gov);
            tv_desc_gov = (TextView) itemView.findViewById(R.id.tv_desc_gov);
            tv_price_gov = (TextView) itemView.findViewById(R.id.tv_price_gov);
            addSubView = (AddSubView) itemView.findViewById(R.id.addSubView);

            //设置Item的点击事件，itemView
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        //把这个位置回传回去了
                        mOnItemClickListener.onItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = View.inflate(mContext, R.layout.item_shop_cart, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //1.根据位置得到对应的Bean对象。
        final GoodsBean goodsBean = datas.get(position);
        //2.设置数据,设置商品是否选中。
        holder.cb_gov.setChecked(goodsBean.isSelected());
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE + goodsBean.getFigure()).into(holder.iv_gov);
        holder.tv_desc_gov.setText(goodsBean.getName());
        holder.tv_price_gov.setText("¥" + goodsBean.getCover_price());
        //设置控件显示商品数量
        holder.addSubView.setValue(goodsBean.getNumber());
        //设置商品数量的最小值
        holder.addSubView.setMinValue(1);
        //设置商品数量的最大值
        holder.addSubView.setMaxValue(8);

        //设置商品数量的变化，监听
        holder.addSubView.setOnNumberChangeListener(new AddSubView.onNumberChangeListener() {
            @Override
            public void onNumberChange(int value) {
                //1.当前列表内存中
                goodsBean.setNumber(value);
                //2.本地更新
                CartStorage.getInstance().updateData(goodsBean);
                //3.刷新适配器,点那一条就刷新哪一条的适配器。
                notifyItemChanged(position);
                //4.再次计算总价格
                showTotalPrice();
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * 监听者，点击Item的监听。
     */
    public interface onItemClickListener {
        //当点击某一条的时候会回调
        public void onItemClick(int position);
    }

    /**
     * 设置item的监听
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private onItemClickListener mOnItemClickListener;

}
