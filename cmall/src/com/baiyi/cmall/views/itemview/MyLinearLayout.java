package com.baiyi.cmall.views.itemview;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * 
 * 自定义的线性布局
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-18 下午2:48:33
 */
public class MyLinearLayout extends LinearLayout {

	public MyLinearLayout(Context context) {
		super(context);
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		this.setLayoutParams(params);
		this.setOrientation(LinearLayout.VERTICAL);
	}

}
