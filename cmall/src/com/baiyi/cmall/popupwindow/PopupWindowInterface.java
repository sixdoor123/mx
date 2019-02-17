package com.baiyi.cmall.popupwindow;

import android.content.Context;
import android.view.View;

/**
 * PopupWindow�ӿ�
 * 
 * @author sunxy
 * 
 */
public interface PopupWindowInterface {

	/**
	 * ��ʾ��ʾ
	 * 
	 * @param height
	 *            ��ʾ���붥���ľ���
	 * @param context
	 * @param v
	 *            ��v��ɶλ����ʾ
	 */
	public void showPop(String typeString, int height, Context context, View v,
			int gravity);

	public void showPop(int height, View v, int gravity);

	/**
	 * ȡ��
	 */
	public void dismissPop();
}
