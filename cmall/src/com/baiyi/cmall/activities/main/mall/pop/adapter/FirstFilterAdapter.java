package com.baiyi.cmall.activities.main.mall.pop.adapter;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.mall.pop.entity.Qcm;
import com.baiyi.cmall.activities.user.merchant.order.adapter.BaseRecycleViewAdapter;
import com.baiyi.core.util.ContextUtil;
import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 筛选一级分类
 * 
 * @author sunxy
 * @param <T>
 */
public class FirstFilterAdapter<T> extends BaseRecycleViewAdapter<T> {

	public FirstFilterAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onInitViewHolder(ViewHolder viewHolder, int position) {
		Qcm qcm = (Qcm) datas.get(position);

		MyViewHolder holder = (MyViewHolder) viewHolder;
		holder.mTxtCatogeryName.setText(qcm.getQn());
		holder.mTxtSonName.setText(qcm.getSubName());
	}

	@Override
	public ViewHolder reateViewHolder(ViewGroup parent, int viewType) {
		View view = ContextUtil.getLayoutInflater(context)//
				.inflate(R.layout.first_filter_item, parent, false);
		MyViewHolder holder = new MyViewHolder(view);
		return holder;
	}

	static class MyViewHolder extends ViewHolder {

		// 显示返回的数据
		TextView mTxtSonName;
		// 一级分类名称
		TextView mTxtCatogeryName;

		public MyViewHolder(View itemView) {
			super(itemView);

			mTxtCatogeryName = (TextView) itemView.findViewById(R.id.txt_first_name);
			mTxtSonName = (TextView) itemView.findViewById(R.id.txt_son_name);
		}
	}
}
