package com.baiyi.jj.app.adapter;

import android.widget.AbsListView;

/**
 * 
 * @author tangkun
 *
 */
public interface OnAdapterScrollListener extends AbsListView.OnScrollListener {
	
	/**
	 * �Ƿ����������
	 * @param view
	 */
    void onTopWhenScrollIdle(AbsListView view);

    /**
     * �Ƿ�������ײ�
     * @param view
     */
    void onBottomWhenScrollIdle(AbsListView view);
    
    /**
     * ֹͣ����
     * @param view
     */
    public void onScrollIdle(AbsListView view);

}
