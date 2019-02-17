package com.baiyi.jj.skin.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public abstract class CommonBaseAdapter<T> extends BaseAdapter {

	protected List<T> mDatas;
	protected LayoutInflater mInflater;
	protected Context context;
	protected int itemLayoutId;
	
	/** 同一个列表中的item可能有多种布�?文件 */
	private int[] itemLayoutIds;
	
	public CommonBaseAdapter(Context context, List<T> mDatas, int[] itemLayoutIds) {
		this.context = context;
		this.mDatas= mDatas;
		this.itemLayoutIds = itemLayoutIds;
	}
	
	public List<T> getList(){
		if(mDatas == null){
			mDatas = new ArrayList<T>();
		}
		
		return mDatas;
	}
	
	public void setList(List<T> mDatas){
		this.mDatas= mDatas;
		this.notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return (mDatas == null) ? 0 : mDatas.size();
	}

	@Override
	public T getItem(int position) {
		if(mDatas != null && position < mDatas.size()){
			return mDatas.get(position);
		}
		else{ 
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	/**
	 * 默认指定第一种item布局
	 * 如果要实现列表中的不同布�?,请复写此方法<br>
	 * 根据position对应的bean相关属�?�指定itemLayoutId
	 * @param position
	 * @return 对应的itemlayout
	 */
	protected int getItemLayout(int position){
		return itemLayoutIds[0];
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		CommonViewHolder holder = CommonViewHolder.get(context, convertView, parent, getItemLayout(position), position);
		
		convertItemView(holder, getItem(position), position);
		
		return holder.getConvertView();
	}
	
	public abstract void convertItemView(CommonViewHolder holder, T item, int position);

}
