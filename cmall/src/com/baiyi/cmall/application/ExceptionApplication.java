package com.baiyi.cmall.application;

import com.baiyi.cmall.exception.UnCaughtOrCrashException;

import android.app.Application;

/**
 * �쳣����
 * @author sunxy
 */
public class ExceptionApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
//		UnCaughtOrCrashException.getInstence(getApplicationContext()).init();
	}
}
