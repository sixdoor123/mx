package com.baiyi.cmall.activities.user.merchant.intention;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.user.merchant.order.adapter.BaseRecycleViewAdapter;
import com.baiyi.cmall.entity.GoodSOriginInfo;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.util.ContextUtil;

/**
 * ί���б�����������ί�вɹ���ί��������ί��������
 * 
 * @author sunxy
 * @param <T>
 */
public class IntentionOrderAdapter<T> extends BaseRecycleViewAdapter<T> {

	public IntentionOrderAdapter(Context context) {
		super(context);
	}

	@Override
	public void onInitViewHolder(android.support.v7.widget.RecyclerView.ViewHolder viewHolder, int position) {
		MyViewHolder holder = (MyViewHolder) viewHolder;
		GoodsSourceInfo info = (GoodsSourceInfo) datas.get(position);
		holder.formName.setText(info.getGoodSName());
		holder.state.setText(info.getIntentionOrderState());
		holder.category.setText(info.getUserName());
		holder.quantity.setText(Utils.dealWeight(info.getGoodSWeight()) + "��");
		holder.price.setText(Utils.dealPrice("" + info.getGoodSPrePrice()));
	}

	@Override
	public android.support.v7.widget.RecyclerView.ViewHolder reateViewHolder(ViewGroup parent, int viewType) {
		View view = ContextUtil.getLayoutInflater(context).inflate(R.layout.item_5_view, parent, false);
		MyViewHolder holder = new MyViewHolder(view);
		return holder;
	}

	public static class MyViewHolder extends RecyclerView.ViewHolder {

		TextView formName;// ����
		TextView quantity;// ����
		TextView state;// ����
		TextView category;// ����
		TextView price;// �۸�

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