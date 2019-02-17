package com.baiyi.jj.app.adapter;

import android.widget.AbsListView;

/**
 * 
 * @author tangkun
 *
 */
public interface OnAdapterScrollListener extends AbsListView.OnScrollListener {
	
	/**
	 * 是否滚动到顶部
	 * @param view
	 */
    void onTopWhenScrollIdle(AbsListView view);

    /**
     * 是否滚动到底部
     * @param view
     */
    void onBottomWhenScrollIdle(AbsListView view);
    
    /**
     * 停止滑动
     * @param view
     */
    public void onScrollIdle(AbsListView view);

}
