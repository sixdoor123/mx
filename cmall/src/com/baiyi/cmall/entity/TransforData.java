package com.baiyi.cmall.entity;

import java.io.Serializable;
import java.util.ArrayList;

import android.widget.ImageView;

/**
 *	��������
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-29 
 *       ����9:58:04
 */
public class TransforData implements Serializable{

	//��Ųɹ����Եļ���
	private static ArrayList<IntentionDetailStandardInfo> purStandardInfos;
	// ��Ź�Ӧ��Ϣ���Եļ���
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
