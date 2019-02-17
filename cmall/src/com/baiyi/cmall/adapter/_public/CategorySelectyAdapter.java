package com.baiyi.cmall.adapter._public;

import java.util.ArrayList;

import com.baiyi.cmall.entity.SelectedInfo;

import android.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * 分类选择
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-1-8 下午3:41:12
 */
public class CategorySelectyAdapter extends BaseExpandableListAdapter {

	private Context context;
	private ArrayList<SelectedInfo> datas;

	public CategorySelectyAdapter(Context context, ArrayList<SelectedInfo> datas) {
		this.context = context;
		this.datas = datas;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return datas.get(groupPosition).getSonDatas().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return datas.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return datas.get(groupPosition).getSonDatas().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return datas.get(groupPosition).hashCode();
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return datas.get(groupPosition).getSonDatas().get(childPosition)
				.hashCode();
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		TextView textView = new TextView(context);
		textView.setTextSize(16);
		textView.setPadding(40, 15, 10, 15);
		textView.setText(datas.get(groupPosition).getCm_categoryname());
		return textView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		TextView textView = new TextView(context);
		textView.setTextSize(15);
		textView.setPadding(50, 15, 10, 15);
		textView.setText(datas.get(groupPosition).getSonDatas()
				.get(childPosition).getCm_categoryname());
		textView.setTextColor(context.getResources().getColor(
				com.baiyi.cmall.R.color.bg_hui1));
		return textView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
