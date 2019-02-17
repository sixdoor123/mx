package com.baiyi.cmall.activities.main.baseList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.core.util.ContextUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 公共类的适配器
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-31 上午11:16:25
 */
public abstract class BaseListAdapter extends BaseAdapter {

	public ArrayList<Serializable> datas;
	public Context context;

	public LayoutInflater inflater;

	public BaseListAdapter(Context context) {
		this.datas = new ArrayList<Serializable>();
		this.context = context;

		inflater = ContextUtil.getLayoutInflater(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas == null ? 0 : datas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getItemView(position, convertView, parent);
	}

	/**
	 * 适配条目数据
	 * 
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 */
	public abstract View getItemView(int position, View convertView,
			ViewGroup parent);

	/**
	 * 设置数据
	 * 
	 * @param datas
	 */
	public void setData(ArrayList<Serializable> datas) {
		if (null != datas && datas.size() > 0) {
			// this.datas.clear();
			this.datas = datas;
			notifyDataSetChanged();
		}
	}

	/**
	 * 添加数据
	 */

	public void addData(ArrayList<Serializable> datas) {
		if (null != datas && datas.size() > 0) {
			this.datas.addAll(datas);
			notifyDataSetChanged();
		}
	}
}
