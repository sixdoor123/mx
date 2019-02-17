/**
 * 
 */
package com.baiyi.jj.app.adapter.base;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;

import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.listitem.news.ListItemBaseNews;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.pulldownview.PullToRefreshListView;

/**
 * ����ֹͣ����
 * @author tangkun
 * 
 */
public abstract class ScrollAdapter<NewsListEntity> extends BaseAdapter implements AbsListView.RecyclerListener{

	private PullToRefreshListView mListView = null;
	private List<NewsListEntity> data = new ArrayList<NewsListEntity>();
	private boolean isLoading = true;

	public ScrollAdapter(PullToRefreshListView mListView, List<NewsListEntity> data) {
		this.mListView = mListView;
		if(!Utils.isStringEmpty(data))
		{
			this.data = data;
		}
		if (mListView == null) {
			return;
		}
		setListViewScroll();
	}

	public void setListViewScroll() {
		if(mListView == null)
		{
			return;
		}
		mListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				switch (scrollState) {
				// scroll stop
				case OnScrollListener.SCROLL_STATE_IDLE:
					setIsImgLoading(true);
					displayScrollStop();
					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
					// ��ֹ����ͼƬ
					setIsImgLoading(false);
					break;
				case OnScrollListener.SCROLL_STATE_FLING:
					// ��ֹ����ͼƬ
					setIsImgLoading(false);
					break;
				}

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

			}
		});
	}

	public void setIsImgLoading(boolean isLoading) {
		this.isLoading = isLoading;
	}
	
	public PullToRefreshListView getListView()
	{
		return mListView;
	}
	
	public List<NewsListEntity> getData()
	{
		return data;
	}
	
	public NewsListEntity getLastEntity()
	{
		if(Utils.isStringEmpty(data))
		{
			return null;
		}
		return data.get(data.size() - 1);
	}
	public void addDataHead(List<NewsListEntity> list) {
		this.data.addAll(0, list);
		notifyDataSetChanged();
	}
	
	public void addData(List<NewsListEntity> data)
	{
		this.data.addAll(data);
		this.notifyDataSetChanged();
	}
	
	public void clear()
	{
		this.data.clear();
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		GdapterTypeRender typeRender;
		if (null == convertView) {
			typeRender = getAdapterTypeRender(position);
			convertView = typeRender.getConvertView();
			convertView.setTag(R.id.id_adapter_item_type_render, typeRender);
			typeRender.setEvents();
		} else {
			typeRender = (GdapterTypeRender) convertView.getTag(R.id.id_adapter_item_type_render);
		}
		convertView.setTag(R.id.id_adapter_item_position, position);

		if (null != typeRender) {
			typeRender.setDatas(position, isLoading);
		}
		return convertView;
	}
	
	public abstract GdapterTypeRender getAdapterTypeRender(int position);
	public abstract void displayScrollStop();

}
