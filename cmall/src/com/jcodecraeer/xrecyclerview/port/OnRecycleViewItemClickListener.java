package com.jcodecraeer.xrecyclerview.port;

import android.view.View;

/**
 *	RecycleView 条目点击事件
 * @author sunxy
 */
public interface OnRecycleViewItemClickListener {

	<T> void  onItemClick(View view ,int position,T t);
}
