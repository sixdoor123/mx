package com.baiyi.cmall.activities.main.provider;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.user.merchant.order.adapter.BaseRecycleViewAdapter;
import com.baiyi.cmall.entity.GoodSOriginInfo;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.util.ContextUtil;

/**
 * 供应列表
 * 
 * @author sunxy
 * @param <T>
 */
public class ProvideListAdapter<T> extends BaseRecycleViewAdapter<T> {

	public ProvideListAdapter(Context context) {
		super(context);
	}

	@Override
	public void onInitViewHolder(
			android.support.v7.widget.RecyclerView.ViewHolder viewHolder,
			int position) {
		MyViewHolder holder = (MyViewHolder) viewHolder;
		GoodSOriginInfo info = (GoodSOriginInfo) datas.get(position);
		holder.formName.setText(info.getOfferName());
		holder.category.setText(info.getCategoryName());
		holder.quantity.setText(Utils.getWeight(info.getInventory())
				+ "吨");
		String price = Utils.dealPrice(info.getPrice());
		if (price.startsWith(".")) {
			price = "0" + price;
		}
		holder.price.setText(price + "元/吨");
		holder.state.setVisibility(View.GONE);
	}

	@Override
	public android.support.v7.widget.RecyclerView.ViewHolder reateViewHolder(
			ViewGroup parent, int viewType) {
		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.item_5_view, parent, false);
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
			category = (TextView) convertView
					.findViewById(R.id.tv_content3);
			quantity = (TextView) convertView
					.findViewById(R.id.tv_content4);
			price  = (TextView) convertView
					.findViewById(R.id.tv_content5);
		}
		
	}
}
