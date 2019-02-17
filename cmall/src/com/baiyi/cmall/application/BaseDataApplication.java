package com.baiyi.cmall.application;

import com.baiyi.cmall.entity.GoodsSourceInfo;

/**
*
*@author sunxy
*/
public class BaseDataApplication extends ExceptionApplication {

	private static GoodsSourceInfo publicData;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	public static GoodsSourceInfo getPublicData() {
		return publicData;
	}

	public static void setPublicData(GoodsSourceInfo info) {
		publicData = info;
	}

}
