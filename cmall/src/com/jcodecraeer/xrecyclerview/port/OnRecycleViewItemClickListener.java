package com.jcodecraeer.xrecyclerview.port;

import android.view.View;

/**
 *	RecycleView ��Ŀ����¼�
 * @author sunxy
 */
public interface OnRecycleViewItemClickListener {

	<T> void  onItemClick(View view ,int position,T t);
}
