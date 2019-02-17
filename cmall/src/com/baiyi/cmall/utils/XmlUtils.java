package com.baiyi.cmall.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * ²Ù×÷SharedPreferences
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-31 ÏÂÎç2:53:20
 */
public class XmlUtils {

	private Context context;
	private SharedPreferences pre;
	private Editor editor;
	private static XmlUtils utils;

	private XmlUtils(Context context) {
		this.context = context;
		pre = context.getSharedPreferences(XmlName.NAME_ID,
				Context.MODE_PRIVATE);
		// editor = pre.edit();
	}

	public static XmlUtils getInstence(Context context) {
		if (null == utils) {
			utils = new XmlUtils(context);
		}
		return utils;
	}

	/**
	 * ¶Á
	 * 
	 * @param name
	 * @return
	 */
	public String getXmlStringValue(String name) {
		if (name == null) {
			return null;
		}
		return pre.getString(name, null);
	}

	/**
	 * ¶Á
	 * 
	 * @param name
	 * @return
	 */
	public int getXmlIntValue(String name) {
		if (name == null) {
			return 0;
		}
		return pre.getInt(name, 0);
	}

	public int getXmlIntValue(String name, int defualtValue) {
		if (name == null) {
			return defualtValue;
		}
		return pre.getInt(name, defualtValue);
	}

	public boolean getXmlBooleanValue(String name, boolean defualtValue) {
		if (name == null) {
			return defualtValue;
		}
		return pre.getBoolean(name, defualtValue);
	}

	/**
	 * ±£´æ
	 * 
	 * @param name
	 * @param value
	 */
	public void savaXmlValue(String name, String value) {
		Editor e = pre.edit();
		e.putString(name, value);
		e.commit();
	}

	/**
	 * ±£´æ
	 * 
	 * @param name
	 * @param value
	 */
	public void savaXmlValue(String name, boolean value) {
		Editor e = pre.edit();
		e.putBoolean(name, value);
		e.commit();
	}

	/**
	 * ±£´æ
	 * 
	 * @param name
	 * @param value
	 */
	public void savaXmlValue(String name, int value) {
		Editor e = pre.edit();
		e.putInt(name, value);
		e.commit();
	}

	/**
	 * É¾³ý
	 * 
	 * @param name
	 * @param value
	 */
	public boolean deleteXmlValue(String name) {
		if (name == null) {
			return false;
		}
		Editor e = pre.edit();
		e.remove(name);
		return e.commit();
	}

	public boolean delete() {

		Editor e = pre.edit();
		e.clear();
		return e.commit();
	}

}
