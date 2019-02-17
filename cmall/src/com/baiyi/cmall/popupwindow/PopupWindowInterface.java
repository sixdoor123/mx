package com.baiyi.cmall.popupwindow;

import android.content.Context;
import android.view.View;

/**
 * PopupWindow接口
 * 
 * @author sunxy
 * 
 */
public interface PopupWindowInterface {

	/**
	 * 显示显示
	 * 
	 * @param height
	 *            显示距离顶部的距离
	 * @param context
	 * @param v
	 *            在v的啥位置显示
	 */
	public void showPop(String typeString, int height, Context context, View v,
			int gravity);

	public void showPop(int height, View v, int gravity);

	/**
	 * 取消
	 */
	public void dismissPop();
}
