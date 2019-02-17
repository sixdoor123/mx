package com.baiyi.cmall.activities.user.merchant.order.adapter;

import java.util.ArrayList;
import java.util.List;

import com.baiyi.cmall.utils.Utils;
import com.jcodecraeer.xrecyclerview.port.OnRecycleViewItemClickListener;
import com.jcodecraeer.xrecyclerview.port.OnRecycleViewItemLongClickListener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;

/**
 *   ≈‰∆˜ª˘¿‡
 * 
 * @author sunxy
 * @param <T>
 */
public abstract class BaseRecycleViewAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

	public Context context = null;
	public List<T> datas = null;

	private OnRecycleViewItemClickListener itemClickListener;
	private OnRecycleViewItemLongClickListener longClickListener;

	public static int itemHeight = 0;

	public BaseRecycleViewAdapter(Context context) {
		this.context = context;
		this.datas = new ArrayList<T>();
	}

	public BaseRecycleViewAdapter(Context context, ArrayList<T> datas) {
		this.context = context;
		this.datas = datas;
	}

	@Override
	public int getItemCount() {
		if (Utils.isStringEmpty(datas)) {
			return 0;
		}
		return datas.size();
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {

		onInitViewHolder(viewHolder, position);
		viewHolder.itemView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (null != itemClickListener) {
					itemClickListener.onItemClick(viewHolder.itemView, position,
							position < datas.size() ? datas.get(position) : null);
				}
			}
		});
		viewHolder.itemView.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				if (null != longClickListener) {
					longClickListener.onItemLongClick(viewHolder.itemView, position,
							position < datas.size() ? datas.get(position) : null);
				}
				return true;
			}
		});
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		return reateViewHolder(parent, viewType);
	}

	public abstract void onInitViewHolder(ViewHolder holder, int position);

	public abstract ViewHolder reateViewHolder(ViewGroup parent, int viewType);

	public int getItemHeight() {
		return itemHeight;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
		notifyDataSetChanged();
	}

	public void addDatas(List<T> datas) {
		this.datas.addAll(datas);
		notifyDataSetChanged();
	}

	public void setItemClickListener(OnRecycleViewItemClickListener itemClickListener) {
		this.itemClickListener = itemClickListener;
	}

	public void setLongClickListener(OnRecycleViewItemLongClickListener longClickListener) {
		this.longClickListener = longClickListener;
	}

}
