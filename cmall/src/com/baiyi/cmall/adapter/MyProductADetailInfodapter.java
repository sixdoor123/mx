package com.baiyi.cmall.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.baiyi.cmall.R;
import com.baiyi.cmall.entity.IndutryArgumentInfo;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.util.ContextUtil;
import com.jcodecraeer.xrecyclerview.port.OnRecycleViewItemClickListener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 我的产品的适配器
 * 
 * @author sunxy
 * @param <IndutryArgumentInfo>
 */
public class MyProductADetailInfodapter extends RecyclerView.Adapter<ViewHolder> {

	private Context context;
	private List<IndutryArgumentInfo> datas = null;
	// 条目点击事件回调接口
	private OnRecycleViewItemClickListener onItemClickListener;

	public MyProductADetailInfodapter(Context context) {
		this.context = context;
		if (this.datas == null) {
			this.datas = new ArrayList<IndutryArgumentInfo>();
		}
	}

	@Override
	public int getItemCount() {
		 return Utils.isStringEmpty(datas) ? 0 : datas.size();

	}

	@Override
	public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
		final IndutryArgumentInfo info = datas.get(position);
		MyViewHolder holder = (MyViewHolder) viewHolder;
		holder.mTxtName.setText(info.getArgNmae());
		holder.mTxtCAS.setText(info.getArgValue());
		// 条目点击事件
		holder.itemView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != onItemClickListener) {
					// 第三个惨数传入Model
					onItemClickListener.onItemClick(viewHolder.itemView, position, info);
				}
			}
		});
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = ContextUtil.getLayoutInflater(context)//
				.inflate(R.layout.item_my_product_detail, null);
		MyViewHolder holder = new MyViewHolder(view);
		return holder;
	}

	public static class MyViewHolder extends ViewHolder {

		TextView mTxtName;
		TextView mTxtCAS;

		public MyViewHolder(View view) {
			super(view);

			mTxtName = (TextView) view.findViewById(R.id.txt_product_name);
			mTxtCAS = (TextView) view.findViewById(R.id.txt_cas);
		}

	}

	/**
	 * 设置数据
	 * 
	 * @param <IndutryArgumentInfo>
	 * 
	 * @param datas
	 */
	public void setDatas(List<IndutryArgumentInfo> datas) {
		this.datas = datas;
		notifyDataSetChanged();
	}

	/**
	 * 添加数据
	 * 
	 * @param <IndutryArgumentInfo>
	 * 
	 * @param datas
	 */
	public void addDatas(ArrayList<IndutryArgumentInfo> datas) {
		this.datas.addAll(datas);
		notifyDataSetChanged();
	}

	public void setOnItemClickListener(OnRecycleViewItemClickListener listener) {
		this.onItemClickListener = listener;
	}
}
