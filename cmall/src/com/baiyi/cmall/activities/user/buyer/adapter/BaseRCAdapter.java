package com.baiyi.cmall.activities.user.buyer.adapter;

import java.util.ArrayList;

import com.baiyi.cmall.utils.Utils;

import android.content.ClipData.Item;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseRCAdapter<T> extends
		RecyclerView.Adapter<RecyclerView.ViewHolder> {

	public ArrayList<T> objects;
	public Context context;

	public BaseRCAdapter(Context context) {
		this.context = context;
		this.objects = new ArrayList<T>();
	}

	public BaseRCAdapter(Context context, ArrayList<T> objects) {
		this.context = context;
		this.objects = objects;
	}

	public void setDatas(ArrayList<T> objects) {
		this.objects = objects;
		if (Utils.isStringEmpty(objects)) {
			this.objects = new ArrayList<T>();
		}
		notifyDataSetChanged();
	}

	public void addDatas(ArrayList<T> objects) {
		this.objects.addAll(objects);
		notifyDataSetChanged();
	}

	public ArrayList<T> getDataList() {
		return objects;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
			int viewType) {
		return onCreateVH(parent, viewType);
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder,
			final int position) {
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null == onItemClickListeners)
					return;
				onItemClickListeners.onItemClick(position, holder.itemView);
			}
		});
		onBindVH(holder, position);
	}

	public abstract RecyclerView.ViewHolder onCreateVH(ViewGroup parent,
			int viewType);

	public abstract void onBindVH(RecyclerView.ViewHolder holder, int position);

	@Override
	public int getItemCount() {
		return Utils.isStringEmpty(objects) ? 0 : objects.size();
	}

	private OnItemClickListeners onItemClickListeners;

	public void setOnItemClickListener(OnItemClickListeners onItemClickListeners) {
		this.onItemClickListeners = onItemClickListeners;
	}

	public interface OnItemClickListeners {
		public void onItemClick(int position, View itemView);
	}

}
