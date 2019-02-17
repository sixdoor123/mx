/**
 * 
 */
package com.baiyi.cmall.activities.main.mall.pop;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.mall.adapter.MallFilterCatetoryAdapter;
import com.baiyi.cmall.activities.main.mall.entity.MallCategoryEntity;
import com.baiyi.cmall.views.pop.BasePopupWindow;

/**
 * 商城-筛选-分类Pop
 * @author tangkun
 *
 */
public class PopMallMenuFilterCatetory extends BasePopupWindow{
	
	private ImageView btnBack = null;
	private MallFilterCatetoryAdapter adapter = null;
	
//	private XRecyclerView listView = null;
	private Context context = null;
	
	private ExpandableListView listView = null;
	private PopMallFilterCategoryItemClick popMallFilterCategoryItemClick = null;
	
	private final String[] FILTER_TEXT = {"全部品牌", "医药中间体", "生物化学品", "药用辅料", "医药中间体","生物化学品", "药用辅料"};

	/**
	 * @param contentView
	 * @param context
	 * @param width
	 * @param height
	 */
	public PopMallMenuFilterCatetory(View contentView, Context context, int width,
			int height) {
		super(contentView, context, width, height);
		this.context = context;
		
		initViews(contentView);
	}
	
	private void initViews(View contentView)
	{
		btnBack = (ImageView)contentView.findViewById(R.id.btn_back);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		listView = (ExpandableListView)contentView.findViewById(R.id.pop_mall_filter_all_listview);
		listView.setGroupIndicator(null);
		adapter = new MallFilterCatetoryAdapter(context);
		listView.setAdapter(adapter);
		listView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				if(popMallFilterCategoryItemClick != null)
				{
					popMallFilterCategoryItemClick.setPopMallFilterCategoryItemClickLister(groupPosition, childPosition, adapter.getChild(groupPosition, childPosition).toString());
				}
				return true;
			}
		});
		
		List<MallCategoryEntity> dataList = new ArrayList<MallCategoryEntity>();
		for(int i = 0; i < 8; i++)
		{
			MallCategoryEntity entity = new MallCategoryEntity();
			List<String> childList = new ArrayList<String>();
			for(int j = 0; j < 4; j++)
			{
				childList.add("生物化学品" + (j+1));
				entity.setChildList(childList);
			}
			entity.setName("生物及医药化学品");
			dataList.add(entity);
		}
		adapter.setParentData(dataList);
	}
	
	public void setPopMallFilterCategoryItemClick(
			PopMallFilterCategoryItemClick popMallFilterCategoryItemClick) {
		this.popMallFilterCategoryItemClick = popMallFilterCategoryItemClick;
	}

	public interface PopMallFilterCategoryItemClick
	{
		public void setPopMallFilterCategoryItemClickLister(int parentIndex, int childIndex, String name);
	}

}
