/**
 * 
 */
package com.baiyi.cmall.views.pop;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.baiyi.cmall.adapter.PopoupWindowAdapter;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.views.pulldownview.PullToRefreshListView;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase.Mode;
import com.baiyi.cmall.R;

/**
 * Pop 单一列表
 * 
 * @author tangkun
 * 
 */
public class PopSortList extends BasePopupWindow implements OnItemClickListener {
	private PullToRefreshListView listView = null;
	private PopoupWindowAdapter adapter;

	private String sortWords;

	/**
	 * @param contentView
	 * @param context
	 * @param height
	 */
	public PopSortList(View contentView, Context context, int width, int height,
			ArrayList<SelectedInfo> sortWays, String sortWords) {
		super(contentView, context, width, height);
		// TODO Auto-generated constructor stub
//		sortWays.addAll(sortWays);
		listView = (PullToRefreshListView) contentView
				.findViewById(R.id.lst_industry_trends);
		listView.setMode(Mode.DISABLED);
		adapter = new PopoupWindowAdapter(context, sortWays, 0, sortWords);
		listView.setAdapter(adapter);
		adapter.setData(sortWays);

		listView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		SelectedInfo info = (SelectedInfo) parent.getItemAtPosition(position);
		adapter.setSortWords(info.getCm_categoryname());

		if (null != popListener) {
			popListener.onItemClick(info);
		}
		dismiss();
	}

	public void setSortWords(String sortWords) {
		this.sortWords = sortWords;
	}

	private OnPopItemClickListener popListener;

	public void setPopListener(OnPopItemClickListener popListener) {
		this.popListener = popListener;
	}

	public interface OnPopItemClickListener {
		void onItemClick(SelectedInfo info);
	}

}
