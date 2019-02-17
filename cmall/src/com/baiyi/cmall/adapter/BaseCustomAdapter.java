package com.baiyi.cmall.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

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
public abstract class BaseCustomAdapter extends BaseAdapter {

	//数据源
	public ArrayList<Serializable> datas;
	//上下文
	public Context context;
	
	//布局解析器
	public LayoutInflater inflater;

	public BaseCustomAdapter(Context context) {
		
		this.datas = new ArrayList<Serializable>();
		this.context = context;
		
		inflater = ContextUtil.getLayoutInflater(context);
	}

	@Override
	public int getCount() {
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
	/**
	 * 定义布局，加载数据
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 */
	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);

	/**
	 * 设置数据
	 * 
	 * @param datas
	 */
	public void setData(ArrayList<Serializable> datas) {
		if (null != datas && datas.size() > 0) {
			this.datas.clear();
			this.datas.addAll(datas);
			notifyDataSetChanged();
		}
	}

	/**
	 * 添加数据
	 * 
	 * @param datas
	 */
	public void addData(ArrayList<Serializable> datas) {
		if (null != datas && datas.size() > 0) {
			this.datas.addAll(datas);
			notifyDataSetChanged();
		}
	}
}
