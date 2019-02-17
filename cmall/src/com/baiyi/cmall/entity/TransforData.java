package com.baiyi.cmall.entity;

import java.io.Serializable;
import java.util.ArrayList;

import android.widget.ImageView;

/**
 *	传递数据
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-29 
 *       上午9:58:04
 */
public class TransforData implements Serializable{

	//存放采购属性的集合
	private static ArrayList<IntentionDetailStandardInfo> purStandardInfos;
	// 存放供应信息属性的集合
	private static ArrayList<IntentionDetailStandardInfo> proStandardInfos;
	/**
	 * @return the proStandardInfos
	 */
	public static ArrayList<IntentionDetailStandardInfo> getProStandardInfos() {
		return proStandardInfos;
	}
	/**
	 * @param proStandardInfos the proStandardInfos to set
	 */
	public static void setProStandardInfos(
			ArrayList<IntentionDetailStandardInfo> proStandardInfos) {
		TransforData.proStandardInfos = proStandardInfos;
	}
	/**
	 * @return the purStandardInfos
	 */
	public static ArrayList<IntentionDetailStandardInfo> getPurStandardInfos() {
		return purStandardInfos;
	}
	/**
	 * @param purStandardInfos the purStandardInfos to set
	 */
	public static void setPurStandardInfos(
			ArrayList<IntentionDetailStandardInfo> purStandardInfos) {
		TransforData.purStandardInfos = purStandardInfos;
	}
	
}
