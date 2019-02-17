package com.baiyi.cmall.activities.user.attention.merchant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.user.merchant.order.adapter.BaseRecycleViewAdapter;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.core.util.ContextUtil;

/**
 * 委托列表的适配器（委托采购、委托物流、委托物流）
 * 
 * @author sunxy
 * @param <T>
 */
public class AttentionBuyerAdapter<T> extends BaseRecycleViewAdapter<T> {

	public AttentionBuyerAdapter(Context context) {
		super(context);
	}

	@Override
	public void onInitViewHolder(android.support.v7.widget.RecyclerView.ViewHolder viewHolder, int position) {
		MyViewHolder holder = (MyViewHolder) viewHolder;
		GoodsSourceInfo info = (GoodsSourceInfo) datas.get(position);
		holder.formName.setText(TextViewUtil.getEditString(info.getUserName()));
		holder.state.setText(TextViewUtil.getEditString(info.getAddress()));
		holder.category.setText(TextViewUtil.getEditString(info.getEmail()));
		holder.quantity.setText(TextViewUtil.getEditString(info.getGoodSContactWay()));
		holder.price.setVisibility(View.GONE);
	}

	@Override
	public android.support.v7.widget.RecyclerView.ViewHolder reateViewHolder(ViewGroup parent, int viewType) {
		View view = ContextUtil.getLayoutInflater(context).inflate(R.layout.item_5_view, parent, false);
		MyViewHolder holder = new MyViewHolder(view);
		return holder;
	}

	public static class MyViewHolder extends RecyclerView.ViewHolder {

		TextView formName;// 名称
		TextView quantity;// 數量
		TextView state;// 暂无
		TextView category;// 分类
		TextView price;// 价格

		public MyViewHolder(View convertView) {
			super(convertView);
			formName = (TextView) convertView.findViewById(R.id.tv_content1);
			state = (TextView) convertView.findViewById(R.id.tv_content2);
			category = (TextView) convertView.findViewById(R.id.tv_content3);
			quantity = (TextView) convertView.findViewById(R.id.tv_content4);
			price = (TextView) convertView.findViewById(R.id.tv_content5);
		}

	}
}
