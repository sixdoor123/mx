package com.baiyi.cmall.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.baiyi.cmall.entity.MessageEntity;
import com.baiyi.cmall.listitem.ListItemNews;

public class MyMsgAdapter extends BaseAdapter{

	private final static String TAG = MyMsgAdapter.class.getName();
	private List<MessageEntity> itemList;
	private String newsName;
	private final Context context;

	public MyMsgAdapter(Context context) {
		this.context = context;
		this.itemList = new ArrayList<MessageEntity>();
	}
	

	public void setData(List<MessageEntity> list) {
		this.itemList.clear();
		this.itemList.addAll(list);
		notifyDataSetChanged();
	}
	
	public void setNewsName(String newsName)
	{
		this.newsName = newsName;
	}

	public void addData(List<MessageEntity> list) {
		this.itemList.addAll(list);
		notifyDataSetChanged();
	}
	
	public List<MessageEntity> getDataList()
	{
		return itemList;
	}

	@Override
	public int getCount() {
		if (itemList == null)
			return 0;
		return itemList.size();
	}

	@Override
	public Object getItem(int position) {
		return itemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(int id, View view, ViewGroup parent) {

		MessageEntity entity = (MessageEntity) itemList.get(id);
		if(entity == null)
			return null;
		
		ListItemNews itemNews = null;
		if (view == null) {
			view = new ListItemNews(context, entity, newsName);
			itemNews = (ListItemNews)view;
		} else {
			itemNews = (ListItemNews)view;
			if(itemNews.getData().getMsgId().equals(entity.getMsgId()))
			{
				if(itemNews.getState().equals(entity.getMsgState()))
				{
					return itemNews;
				}
			}
			itemNews.setData(entity);
		}
//		ThemeUtil.setFontOrTheme(parent, ThemeUtil.getAppTheme(), FontUtil.getFontSizeType());
		
		return itemNews;
	}
	
}
