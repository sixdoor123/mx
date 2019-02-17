/**
 * 
 */
package com.baiyi.jj.app.application.accont;

import java.util.Calendar;

import com.baiyi.core.file.Preference;

/**
 * @author tangkun
 *
 */
public class PrefUtils {

	/**
	 * 每天第一次loadData
	 * 列表，扉页l，频道管理
	 * @param key
	 * @return
	 */
	public static boolean isFirstDayTimes(String key)
	{
		Preference pref = Preference.getInstance();
		int times = pref.getInt(key, -1);
		Calendar ca = Calendar.getInstance();
		int curDay = ca.get(Calendar.DAY_OF_MONTH);
		if(times == -1)
		{
			pref.Set(key, String.valueOf(curDay));
			pref.saveConfig();
			return true;
		}
		if(times == curDay)
		{
			return false;
		}
		pref.Set(key, String.valueOf(curDay));
		pref.saveConfig();
		return true;
	}
	
	public static boolean isFirstUserLogin(String key)
	{
		Preference pref = Preference.getInstance();
		int times = pref.getInt(key, -1);
		Calendar ca = Calendar.getInstance();
		int curDay = ca.get(Calendar.DAY_OF_MONTH);
		if(times == -1)
		{
			pref.Set(key, String.valueOf(curDay));
			pref.saveConfig();
			return true;
		}
		if(times == curDay)
		{
			return false;
		}
		pref.Set(key, String.valueOf(curDay));
		pref.saveConfig();
		return true;
	}

}
