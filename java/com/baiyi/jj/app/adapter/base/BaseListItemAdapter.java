package com.baiyi.jj.app.adapter.base;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.baiyi.core.database.AbstractBaseModel;
import com.baiyi.jj.app.adapter.DataViewHolder;
import com.baiyi.jj.app.utils.Utils;

public abstract class BaseListItemAdapter <T extends AbstractBaseModel> extends BaseAdapter{

	private List<T> list;
	private LayoutInflater inflater;
	
	protected BaseListItemAdapter(Context context) {
//		super(listView);
		this.list = new ArrayList<T>();
		this.inflater = LayoutInflater.from(context);
	}

	/** ����һ������ */
	public void insert(T data) {
		list.add(0, data);
		notifyDataSetChanged();
	}

	/** ׷��һ������ */
	public void append(T data) {
		list.add(data);
		notifyDataSetChanged();
	}

	/** �滻һ������ */
	public void replace(T data) {
		int index = list.indexOf(data);
		this.replace(index, data);
	}

	/** �滻һ������ */
	private void replace(int index, T data) {
		if (index < 0 || index > list.size() - 1) {
			return;
		}

		list.set(index, data);
		notifyDataSetChanged();
	}

	/** ���ó�ʼ���� */
	public void setData(List<T> list) {
		this.list.clear();
		this.list.addAll(list);
		notifyDataSetChanged();
	}

	/** ׷�Ӽ������� */
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

	/** ������� */
	public void clear() {
		list.clear();
		notifyDataSetChanged();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}
	
	/** ����item�Ĳ����ļ� */
	public abstract View getItemView(int position,View conviertView,T entity);
	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		T entity = list.get(position);
		
		return this.getItemView(position,convertView, entity);
	}
	
	

}
