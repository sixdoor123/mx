package com.baiyi.jj.app.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.baiyi.core.database.AbstractBaseModel;
import com.baiyi.jj.app.utils.Utils;

public abstract class BaseListAdapter<T extends AbstractBaseModel> extends BaseAdapter{

	private List<T> list;
	private LayoutInflater inflater;

	/**
	 * @param listView
	 */
	public BaseListAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.list = new ArrayList<T>();
		this.inflater = LayoutInflater.from(context);
		
	}

	/** 插入一条数据 */
	public void insert(T data) {
		list.add(0, data);
		notifyDataSetChanged();
	}

	/** 追加一条数据 */
	public void append(T data) {
		list.add(data);
		notifyDataSetChanged();
	}

	/** 替换一条数据 */
	public void replace(T data) {
		int index = list.indexOf(data);
		this.replace(index, data);
	}

	/** 替换一条数据 */
	public void replace(int index, T data) {
		if (index < 0 || index > list.size() - 1) {
			return;
		}

		list.set(index, data);
		notifyDataSetChanged();
	}

	/** 设置初始数据 */
	public void setData(List<T> list) {
		this.list.clear();
		this.list.addAll(list);
		notifyDataSetChanged();
	}
	
	public List<?> getData()
	{
		return this.list;
	}

	/** 追加几条数据 */
	public void addData(List<T> list) {
		this.list.addAll(list);
		notifyDataSetChanged();
	}

	public int getCount() {
		return list.size();
	}

	public List<T> getItems() {
		return list;
	}

	public void removeItem(int position) {
		if (Utils.isStringEmpty(list)) {
			return;
		}
		if (position < 0 || position > list.size() - 1) {
			return;
		}
		list.remove(position);
		notifyDataSetChanged();

	}

	/** 清空数据 */
	public void clear() {
		list.clear();
		notifyDataSetChanged();
	}
	
	protected void setListNull()
	{
		if(!Utils.isStringEmpty(list))
		{
			list.clear();
			list = null;
		}
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	/** 获取item的控件id */
	public abstract int[] getFindViewByIds();

	/** 返回item的布局文件 */
	public abstract View getLayout(int position, DataViewHolder dv);

	/** 配置数据 */
	public abstract void renderData(int position, DataViewHolder dv);
	
	public final View getResourceView(int id) {
		return inflater.inflate(id, null);
	}

	public View getView(int id, View view, ViewGroup parent) {
		DataViewHolder viewHolder;
		if (view == null) {
			viewHolder = new DataViewHolder();
			view = this.getLayout(id, viewHolder);
			if (view == null) {
				return null;
			}
			int[] ids = this.getFindViewByIds();
			if (ids == null) {
				ids = new int[] {};
			}
			for (int viewid : ids) {
				viewHolder.setView(viewid, view.findViewById(viewid));
			}
			view.setTag(viewHolder);

		} else {
			viewHolder = (DataViewHolder) view.getTag();
		}
//		ThemeUtil.setFontOrTheme(parent, ThemeUtil.getAppTheme(), FontUtil.getFontSizeType());
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int position = v.getId();

			}
		});
		
		this.renderData(id, viewHolder);

		return view;
	}

}
