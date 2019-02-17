/**
 * 
 */
package com.baiyi.cmall.activities.main.mall.adapter;

import java.util.ArrayList;

import com.baiyi.cmall.utils.Utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author tangkun
 * 
 */
public abstract class BaseGridViewAdpater<T> extends BaseAdapter {

	private ArrayList<T> dataList = new ArrayList<T>();

	private int selectId = 0;

	public void setData(ArrayList<T> data) {
		if (Utils.isStringEmpty(data)) {
			this.dataList = new ArrayList<T>();
		} else {
			this.dataList = data;
		}
		this.notifyDataSetChanged();
	}

	public void setSelect(int selectId) {
		this.selectId = selectId;
		this.notifyDataSetChanged();
	}

	public int getSelectId() {
		return this.selectId;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getBindView(position, convertView, parent);
	}

	public abstract View getBindView(int position, View convertView,
			ViewGroup parent);

}
