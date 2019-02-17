package com.baiyi.cmall.activities.main.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import android.support.v7.widget.RecyclerView;

import com.baiyi.cmall.activities.main.mall.entity.MallProEntity;

/**
 * Adapter holding a list of animal names of type String. Note that each item
 * must be unique.
 */
public abstract class AnimalsAdapter<VH extends RecyclerView.ViewHolder>
		extends RecyclerView.Adapter<VH> {
	private ArrayList<MallProEntity> items = new ArrayList<MallProEntity>();

	public AnimalsAdapter() {
		setHasStableIds(true);
	}

	public void add(MallProEntity object) {
		items.add(object);
		notifyDataSetChanged();
	}

	public void add(int index, MallProEntity object) {
		items.add(index, object);
		notifyDataSetChanged();
	}

	public void addAll(Collection<? extends MallProEntity> collection) {
		if (collection != null) {
			items.addAll(collection);
			notifyDataSetChanged();
		}
	}

	public void addAll(MallProEntity... items) {
		addAll(Arrays.asList(items));
	}

	public void clear() {
		items.clear();
		notifyDataSetChanged();
	}

	public void remove(String object) {
		items.remove(object);
		notifyDataSetChanged();
	}

	public MallProEntity getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).hashCode();
	}

	@Override
	public int getItemCount() {
		return 1;
	}
}
