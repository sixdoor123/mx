package com.baiyi.cmall.activities.main.mall.adapter;

import java.util.ArrayList;

import com.baiyi.cmall.utils.Utils;
import com.tencent.open.utils.Util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	public ArrayList<T> objects;
	public Context context;

	public BaseRecyclerAdapter(Context context) {
		this.context = context;
		objects = new ArrayList<T>();
	}
	public BaseRecyclerAdapter(Context context, ArrayList<T> objects) {
		this.context = context;
		this.objects = objects;
	}

	public void addDatas(ArrayList<T> objects) {
		this.objects.addAll(objects);
		notifyDataSetChanged();
	}

	public void setDatas(ArrayList<T> objects) {
		if (Utils.isStringEmpty(objects)) {
			this.objects = new ArrayList<T>();
		}else {
			this.objects = objects;
		}
		notifyDataSetChanged();
	}

	public ArrayList<T> getDataList() {
		return objects;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return onCreateVH(parent, viewType);
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null == onItemClickListener)
					return;
				onItemClickListener.onItemClick(position, holder.itemView);
			}
		});
		onBindVH(holder, position);
	}

	// @Override
	// public int getItemViewType(int position) {
	// // TODO Auto-generated method stub
	// return itemViewType(position);
	// }

	public abstract RecyclerView.ViewHolder onCreateVH(ViewGroup parent, int viewType);

	public abstract void onBindVH(RecyclerView.ViewHolder holder, int position);

	@Override
	public int getItemCount() {
		return objects == null ? 0 : objects.size();
	}

	private OnItemClickListener onItemClickListener;

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public interface OnItemClickListener {
		public void onItemClick(int position, View itemView);
	}

}
