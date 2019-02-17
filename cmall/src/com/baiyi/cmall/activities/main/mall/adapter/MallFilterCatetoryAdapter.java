/**
 * 
 */
package com.baiyi.cmall.activities.main.mall.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.mall.entity.MallCategoryEntity;
import com.baiyi.core.util.ContextUtil;

/**
 * @author tangkun
 *
 */
public class MallFilterCatetoryAdapter extends BaseExpandableListAdapter{
	
	private List<MallCategoryEntity> dataList = new ArrayList<MallCategoryEntity>();
	private Context context = null;
	
	public MallFilterCatetoryAdapter(Context context)
	{
		this.context = context;
	}
	
	public void setParentData(List<MallCategoryEntity> data)
	{
		dataList.addAll(data);
		this.notifyDataSetChanged();
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return dataList.get(groupPosition).getChildList().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return dataList.get(groupPosition).getName();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return dataList.get(groupPosition).getChildList().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = ContextUtil.getLayoutInflater(context).inflate(R.layout.item_mall_filter_catetory, null);
		TextView name = (TextView)view.findViewById(R.id.txt_root_name);
		ImageView arrow = (ImageView)view.findViewById(R.id.img_mall_arrow);
		if(isExpanded)
		{
			arrow.setBackgroundResource(R.drawable.down_arrow);
		}else
		{
			arrow.setBackgroundResource(R.drawable.right_arrow);
		}
		name.setText(getGroup(groupPosition).toString());
		return view;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = ContextUtil.getLayoutInflater(context).inflate(R.layout.item_mall_filter_catetory_child, null);
		TextView name = (TextView)view.findViewById(R.id.txt_child_name);
		name.setText(getChild(groupPosition, childPosition)  
                .toString());
		return view;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
