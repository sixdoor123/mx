package com.baiyi.cmall.views.itemview;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * 
 * �Զ�������Բ���
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-18 ����2:48:33
 */
public class MyLinearLayout extends LinearLayout {

	public MyLinearLayout(Context context) {
		super(context);
		init();
	}

	/**
	 * ��ʼ��
	 */
	private void init() {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		this.setLayoutParams(params);
		this.setOrientation(LinearLayout.VERTICAL);
	}

}
