package com.baiyi.cmall.exception;

import java.lang.Thread.UncaughtExceptionHandler;

import com.baiyi.cmall.application.UserApplication;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

/**
 * 异常(FC,ANR...)
 * 
 * @author sunxy
 */
public class UnCaughtOrCrashException implements UncaughtExceptionHandler {

	private static UnCaughtOrCrashException exception = null;
	private Context context = null;

	private UnCaughtOrCrashException(Context context) {
		this.context = context;
	}

	public static UnCaughtOrCrashException getInstence(Context context) {
		if (exception == null) {
			exception = new UnCaughtOrCrashException(context);
		}
		return exception;
	}

	public void init() {
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		Log.d("Exception",
				"ex.getLocalizedMessage()--" + ex.getLocalizedMessage()
						+ "\n\n" + "ex.getMessage()--" + ex.getMessage()
						+ "\n\n" + "ex.toString()--" + ex.toString() + "\n\n"
						+ "thread.getName()--" + thread.getName());

		String threadName = thread.getName();

		// 主线程
		if ("main".equals(threadName)) {
			Log.d("Exception", "主线程---" + threadName);
		} else {
			// 其他线程
			Log.d("Exception", "其他线程---" + threadName);
		}

		// 闪退
		// ActivityManager manager = (ActivityManager) context
		// .getSystemService(Context.ACTIVITY_SERVICE);
		// manager.killBackgroundProcesses(context.getPackageName());
		System.exit(0);
	}

}
