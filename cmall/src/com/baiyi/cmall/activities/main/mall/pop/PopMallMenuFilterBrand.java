/**
 * 
 */
package com.baiyi.cmall.activities.main.mall.pop;

import java.util.ArrayList;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.mall.adapter.BaseRecyclerAdapter.OnItemClickListener;
import com.baiyi.cmall.activities.main.mall.adapter.MallFilterBrandAdapter;
import com.baiyi.cmall.activities.main.mall.entity.MallFilterBrandEntity;
import com.baiyi.cmall.views.pop.BasePopupWindow;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * 商城-筛选-品牌
 * @author tangkun
 *
 */
public class PopMallMenuFilterBrand extends BasePopupWindow{
	
	private MallFilterBrandAdapter adapter = null;
	
	private Context context = null;

	private XRecyclerView listView = null;
	
	private PopMallFilterBrandItemClick popMallFilterBrandItemClick = null;
	
	private final String[] FILTER_TEXT = {"全部品牌", "医药中间体", "生物化学品", "药用辅料", "医药中间体","生物化学品", "药用辅料"};

	/**
	 * @param contentView
	 * @param context
	 * @param width
	 * @param height
	 */
	public PopMallMenuFilterBrand(View contentView, Context context, int width,
			int height) {
		super(contentView, context, width, height);
		this.context = context;
		
		initViews(contentView);
	}
	
	private void initViews(View contentView)
	{
		listView = (XRecyclerView)contentView.findViewById(R.id.mall_brand_listview);
		LinearLayoutManager layoutManager = new LinearLayoutManager(context);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(layoutManager);
        listView.setPullRefreshEnabled(false);
        
        ArrayList<MallFilterBrandEntity> dataList = new ArrayList<MallFilterBrandEntity>();
        for(int i =0 ; i < FILTER_TEXT.length; i++)
        {
        	MallFilterBrandEntity entity = new MallFilterBrandEntity();
        	entity.setBid(i);
        	entity.setName(FILTER_TEXT[i]);
        	dataList.add(entity);
        }
        adapter = new MallFilterBrandAdapter(context, dataList);
        listView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(int position, View itemView) {
				ArrayList<MallFilterBrandEntity> dataList = adapter.getDataList();
				MallFilterBrandEntity entity = dataList.get(position);
				adapter.setSelectId(position);
				if(popMallFilterBrandItemClick != null)
				{
					popMallFilterBrandItemClick.setPopMallFilterBrandItemClickLister(position, entity);
				}
			}
		});
	}
	
	public void setPopMallFilterBrandItemClick(
			PopMallFilterBrandItemClick popMallFilterBrandItemClick) {
		this.popMallFilterBrandItemClick = popMallFilterBrandItemClick;
	}

	public interface PopMallFilterBrandItemClick
	{
		public void setPopMallFilterBrandItemClickLister(int position, MallFilterBrandEntity entity);
	}

}
