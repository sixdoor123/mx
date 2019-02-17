package com.baiyi.cmall.activities.user.merchant.provider.viewpager;

import java.util.ArrayList;
import java.util.HashMap;

import com.baiyi.cmall.entity.IntentionDetailStandardInfo;
import com.baiyi.cmall.entity.NewsEntity;

import android.R.integer;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 存放显示修改详情和属性的控件
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-1-11 上午9:54:29
 */
public class ProDataUtils {

	private static int index = 0;
	private static int count = 0;
	public static int shu = 0;
	public static ArrayList<Integer> shuList = new ArrayList<Integer>();
	// 存放详情的修改的控件
	public static ArrayList<TextView> details = new ArrayList<TextView>();
	// 存放属性的修改控件
	public static ArrayList<EditText> standards = new ArrayList<EditText>();
	// 存放修改属性的
	public static HashMap<String, EditText> map = new HashMap<String, EditText>();
	// 存放属性基本信息
	public static HashMap<String, IntentionDetailStandardInfo> infos = new HashMap<String, IntentionDetailStandardInfo>();

	/**
	 * @return the infos
	 */
	public static HashMap<String, IntentionDetailStandardInfo> getInfos() {
		return infos;
	}

	/**
	 * @param infos
	 *            the infos to set
	 */
	public static void setInfos(String key, IntentionDetailStandardInfo info) {
		ProDataUtils.infos.put(key, info);
	}

	public static void setMap(String string, EditText text) {
		map.put(string, text);
	}

	public static HashMap<String, EditText> getMap() {
		return map;
	}

	// 存放修改后属性的集合
	private static ArrayList<IntentionDetailStandardInfo> datas = new ArrayList<IntentionDetailStandardInfo>();

	/**
	 * @return the datas
	 */
	public static ArrayList<IntentionDetailStandardInfo> getDatas() {
		return datas;
	}

	/**
	 * @param datas
	 *            the datas to set
	 */
	public static void setDatas(IntentionDetailStandardInfo info) {
		datas.add(index++, info);
	}

	/**
	 * @return the details
	 */
	public static ArrayList<TextView> getDetails() {
		return details;
	}

	/**
	 * @param details
	 *            the details to set
	 */
	public static void setDetails(TextView tView) {
		details.add(index++, tView);
	}

	/**
	 * @return the standards
	 */
	public static ArrayList<EditText> getStandards() {
		return standards;
	}

	/**
	 * @param standards
	 *            the standards to set
	 */
	public static void setStandards(EditText text) {
		standards.add(count++, text);
	}

	public static void setIndex(int index1) {
		index = index1;
	}

	public static void setCount(int count1) {
		count = count1;
	}
	public static void setShu(int cshu) {
		shu = cshu;
	}

	/**
	 * @return the shuList
	 */
	public static ArrayList<Integer> getShuList() {
		return shuList;
	}

	/**
	 * @param shuList the shuList to set
	 */
	public static void setShuList(int j) {
		shuList.add(j);
	}
	
	public static void clareMap(){
		if (map != null) {
			map.clear();
		}
		if (null != infos) {
			infos.clear();
		}
	}
}
