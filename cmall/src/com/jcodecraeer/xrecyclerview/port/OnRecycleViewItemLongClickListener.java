package com.jcodecraeer.xrecyclerview.port;

import java.io.Serializable;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;

/**
 *	RecycleView 的长按事件
 * @author sunxy
 */
public interface OnRecycleViewItemLongClickListener {
	<T> void onItemLongClick(View view ,int position,T t);
}
