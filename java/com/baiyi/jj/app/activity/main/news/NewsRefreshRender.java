/**
 * 
 */
package com.baiyi.jj.app.activity.main.news;

import com.baiyi.core.util.ContextUtil;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.adapter.base.GdapterTypeRender;
import com.baiyi.jj.app.entity.NewsListEntity;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author tangkun
 *
 */
public class NewsRefreshRender extends BaseHolder/* implements GdapterTypeRender*/{
	
	private LinearLayout btnRefresh = null;
	private RefreshListOnclick refreshListOnclick = null;
	
	public NewsRefreshRender(View arg0, Context context)
	{
		super(arg0);
		setEvents(arg0);
	}

	public void setEvents(View convertView) {
		btnRefresh = (LinearLayout)convertView.findViewById(R.id.btn_refresh);
		btnRefresh.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(refreshListOnclick != null)
				{
					refreshListOnclick.setOnRefreshListLister();
				}
			}
		});
	}

	public void setDatas(int position, boolean isLoading) {
		
	}

	public void setRefreshListOnclick(RefreshListOnclick refreshListOnclick) {
		this.refreshListOnclick = refreshListOnclick;
	}

	public interface RefreshListOnclick 
	{
		public void setOnRefreshListLister();
	}

}
