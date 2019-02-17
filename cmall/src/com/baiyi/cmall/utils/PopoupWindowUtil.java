package com.baiyi.cmall.utils;

import java.util.ArrayList;

import com.baiyi.cmall.popupwindow.ShowRBPopupWindow;

import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 对下拉列表Pop进行操作
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-4 下午3:39:56
 */
public class PopoupWindowUtil {
	
	private static ArrayList<ShowRBPopupWindow> pops =new ArrayList<ShowRBPopupWindow>();

	// 存放View的集合
	private static ArrayList<View> views = new ArrayList<View>();
	// 存放TextView的集合
	private static ArrayList<TextView> textViews = new ArrayList<TextView>();
	//存放ImageView的集合
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
	 * 获取imageViews
	 * @return
	 */
	public static ArrayList<ImageView> getImageViews() {
		return imageViews;
	}
	/**
	 * 获取View的集合
	 * 
	 * @return
	 */
	public static ArrayList<View> getViews() {
		return views;
	}

	/**
	 * 给存放View的集合设置值
	 * 
	 * @param view
	 */
	public static void setViews(View view) {
//		views = new ArrayList<View>();
		views.add(view);
	}

	/**
	 * 获取存放TextView的集合
	 * 
	 * @return
	 */
	public static ArrayList<TextView> getTextViews() {
		return textViews;
	}

	/**
	 * 给存放TextView的集合设置值
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
