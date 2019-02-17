package com.baiyi.cmall.utils;

import java.util.ArrayList;

import com.baiyi.cmall.popupwindow.ShowRBPopupWindow;

import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * �������б�Pop���в���
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-4 ����3:39:56
 */
public class PopoupWindowUtil {
	
	private static ArrayList<ShowRBPopupWindow> pops =new ArrayList<ShowRBPopupWindow>();

	// ���View�ļ���
	private static ArrayList<View> views = new ArrayList<View>();
	// ���TextView�ļ���
	private static ArrayList<TextView> textViews = new ArrayList<TextView>();
	//���ImageView�ļ���
	private static ArrayList<ImageView> imageViews = new ArrayList<ImageView>();

	/**
	 * @return the pops
	 */
	public static ArrayList<ShowRBPopupWindow> getPops() {
		return pops;
	}
	/**
	 * @param pops the pops to set
	 */
	public static void setPops(ShowRBPopupWindow pop) {
		PopoupWindowUtil.pops.add(pop);
	}
	public static void setImageViews(ImageView image) {
		imageViews.add(image);
	}
	/**
	 * ��ȡimageViews
	 * @return
	 */
	public static ArrayList<ImageView> getImageViews() {
		return imageViews;
	}
	/**
	 * ��ȡView�ļ���
	 * 
	 * @return
	 */
	public static ArrayList<View> getViews() {
		return views;
	}

	/**
	 * �����View�ļ�������ֵ
	 * 
	 * @param view
	 */
	public static void setViews(View view) {
//		views = new ArrayList<View>();
		views.add(view);
	}

	/**
	 * ��ȡ���TextView�ļ���
	 * 
	 * @return
	 */
	public static ArrayList<TextView> getTextViews() {
		return textViews;
	}

	/**
	 * �����TextView�ļ�������ֵ
	 * 
	 * @param view
	 */
	public static void setTextViews(TextView view) {
//		textViews = new ArrayList<TextView>();
		textViews.add(view);
	}

	public static void clearList(){
		views.clear();
		textViews.clear();
		imageViews.clear();
	}
}
