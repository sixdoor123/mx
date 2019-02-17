package com.baiyi.cmall.adapter._public;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.baiyi.cmall.entity.GoodsSourceInfo;
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
public abstract class BasepublicAdapter extends BaseAdapter {

	public ArrayList<?> datas;
	public Context context;

	public LayoutInflater inflater;

	public BasepublicAdapter(Context context) {
		this.context = context;
		datas = new ArrayList<Object>();
		inflater = ContextUtil.getLayoutInflater(context);
	}

	public BasepublicAdapter(ArrayList<?> datas, Context context) {
		this.datas = datas;
		this.context = context;
		if (this.datas == null) {
			this.datas = new ArrayList();
		}
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
	 * 
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 */
	public abstract View getItemView(int position, View convertView,
			ViewGroup parent);

	
	/**
	 * 更新数据源
	 * 
	 * @param datas
	 */

	public void setData(ArrayList<?> datas) {
		if (null != datas) {
			this.datas = datas;
		} else {
			this.datas = new ArrayList<Object>();
		}
		notifyDataSetChanged();
	}
}
