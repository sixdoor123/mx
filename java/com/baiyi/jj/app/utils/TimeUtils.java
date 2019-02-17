package com.baiyi.jj.app.utils;

import android.content.Context;

import com.baiyi.core.file.Preference;

public class TimeUtils {

	private static TimeUtils instance = null;
	
	public static final String LOCATION_TIME = "location_time";
	public static final String MERCHANT_LIKE_TIME = "merchant_like_time";
	public static final String GROUP_LIKE_TIME = "group_like_time";
	
	private Preference pref = null;
	
	private TimeUtils()
	{
		pref = Preference.getInstance();
	}
	
	public static TimeUtils getInstance()
	{
		if(instance == null)
		{
			instance = new TimeUtils();
		}
		return instance;
	}
	
	public void addLocTime(Context context, long time)
	{
		pref.Set(LOCATION_TIME, String.valueOf(time));
		pref.saveConfig();
	}
	
	public long getLocTime(Context context)
	{
		return pref.getLong(LOCATION_TIME, 0);
	}
	
	public void addMerchantLikeTime(Context context, long time)
	{
		pref.Set(MERCHANT_LIKE_TIME, String.valueOf(time));
		pref.saveConfig();
	}
	
	public long getMerchantLikeTime(Context context)
	{
		return pref.getLong(MERCHANT_LIKE_TIME, 0);
	}
	
	public void addGroupLikeTime(Context context, long time)
	{
		pref.Set(GROUP_LIKE_TIME, String.valueOf(time));
		pref.saveConfig();
	}
	
	public long getGroupLikeTime(Context context)
	{
		return pref.getLong(GROUP_LIKE_TIME, 0);
	}

}
