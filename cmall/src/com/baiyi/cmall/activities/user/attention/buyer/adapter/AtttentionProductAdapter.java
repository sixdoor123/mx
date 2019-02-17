package com.baiyi.cmall.activities.user.attention.buyer.adapter;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.user.attention.merchant.adapter.AttentionLogisticsAdapter.MyViewHolder;
import com.baiyi.cmall.activities.user.merchant.order.adapter.BaseRecycleViewAdapter;
import com.baiyi.cmall.model.Blm;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.core.util.ContextUtil;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 关注的产品
 *
 * @author sunxy
 * @param <T>
 */
public class AtttentionProductAdapter<T> extends BaseRecycleViewAdapter<T> {

	public AtttentionProductAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onInitViewHolder(ViewHolder viewHolder, int position) {
		Blm blm = (Blm) datas.get(position);
		
		MyViewHolder holder = (MyViewHolder) viewHolder;
		
		holder.c1.setText(TextViewUtil.getEditString(blm.getC1()));
		holder.c2.setText(TextViewUtil.getEditString(blm.getC2()));
		holder.c3.setText(TextViewUtil.getEditString(blm.getC3()));
		holder.c4.setText(TextViewUtil.getEditString(blm.getC4()));
		holder.c5.setText(TextViewUtil.getEditString(blm.getC5()));
	}

	@Override
	public ViewHolder reateViewHolder(ViewGroup parent, int viewType) {
		View view = ContextUtil.getLayoutInflater(context).inflate(R.layout.item_5_view, parent, false);
		MyViewHolder holder = new MyViewHolder(view);
		return holder;
	}

	static class MyViewHolder extends ViewHolder {

		TextView c1;//
		TextView c2;// 
		TextView c3;// 
		TextView c4;//
		TextView c5;// 

		public MyViewHolder(View convertView) {
			super(convertView);

			c1 = (TextView) convertView.findViewById(R.id.tv_content1);
			c2 = (TextView) convertView.findViewById(R.id.tv_content2);
			c3 = (TextView) convertView.findViewById(R.id.tv_content3);
			c4 = (TextView) convertView.findViewById(R.id.tv_content4);
			c5 = (TextView) convertView.findViewById(R.id.tv_content5);

		}

	}
}
