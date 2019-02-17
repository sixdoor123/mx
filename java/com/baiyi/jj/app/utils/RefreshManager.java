package com.baiyi.jj.app.utils;

import java.util.ArrayList;
import java.util.List;

public class RefreshManager {
	
	private static final String TAG = RefreshManager.class.getName();
	public static final String TAG_COLLECT_DEL = "TAG_COLLECT_DEL";
	private static RefreshManager instance = null;
	private List<String> list = null;

	public RefreshManager() {
		list = new ArrayList<String>();
	}
	

	public static RefreshManager getInstance()
	{
		if(instance == null)
		{
			instance = new RefreshManager();
		}
		return instance;
	}
	
	public void add(String tag){
		list.add(tag);
	}
	
	public boolean isHas(String tag)
	{
		if(Utils.isStringEmpty(list))
		{
			return false;
		}
		if (!list.contains(tag)) {
			return false;
		}
		return true;
	}
	
	public void clear()
	{
		list.clear();
	}
}
