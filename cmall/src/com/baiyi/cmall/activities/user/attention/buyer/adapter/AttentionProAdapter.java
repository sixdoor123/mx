package com.baiyi.cmall.activities.user.attention.buyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.user.merchant.order.adapter.BaseRecycleViewAdapter;
import com.baiyi.cmall.entity.GoodSOriginInfo;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.util.ContextUtil;

/**
 * 委托列表的适配器（委托采购、委托物流、委托物流）
 * 
 * @author sunxy
 * @param <T>
 */
public class AttentionProAdapter<T> extends BaseRecycleViewAdapter<T> {

	public AttentionProAdapter(Context context) {
		super(context);
	}

	@Override
	public void onInitViewHolder(android.support.v7.widget.RecyclerView.ViewHolder viewHolder, int position) {
		MyViewHolder holder = (MyViewHolder) viewHolder;
		GoodsSourceInfo info = (GoodsSourceInfo) datas.get(position);
		holder.formName.setText(info.getGoodSName());
		String stateName = info.getPublicstatename();
		if ("撤销发布".equals(stateName)) {
			stateName = "已下架";
		} else if ("用户已删除".equals(stateName)) {
			stateName = "已下架";
		}
		holder.state.setText(stateName);
		holder.category.setText(getCategory(info.getGoodSCategory(), info.getGoodSBrand()));
		holder.quantity.setText(getQuantity(Utils.dealWeight(info.getGoodSWeight())));
		String price = Utils.dealPrice("" + (info.getGoodSPrePrice() == null ? 0 : info.getGoodSPrePrice()));
		if (price.startsWith(".")) {
			price = "0" + price;
		}
		holder.price.setText(price + "元/吨");
	}

	private CharSequence getCategory(String goodSCategory, String goodSBrand) {
		if (TextViewUtil.isStringEmpty(goodSCategory)) {
			if (TextViewUtil.isStringEmpty(goodSBrand)) {
				return "";
			} else {
				return goodSBrand;
			}
		} else {
			if (TextViewUtil.isStringEmpty(goodSBrand)) {
				return goodSCategory;
			} else {
				return goodSCategory + "/" + goodSBrand;
			}
		}
	}

	private CharSequence getQuantity(String dealWeight) {
		if (TextViewUtil.isStringEmpty(dealWeight)) {
			return "";
		}
		return dealWeight + "吨";
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
