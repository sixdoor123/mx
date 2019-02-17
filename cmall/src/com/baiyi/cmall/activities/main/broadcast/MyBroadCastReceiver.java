package com.baiyi.cmall.activities.main.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;

/**
 * 点击供应和采购刷新数据
 * @author Administrator
 *
 */
public class MyBroadCastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("TAG", "--------------------onReceive");
		if (null != onFlushClickListener) {
			onFlushClickListener.onFlush(intent);
		}
	}

	private static OnFlushClickListener onFlushClickListener;
	public static void setListener(OnFlushClickListener listener) {
		onFlushClickListener = listener;
	}
	public static interface OnFlushClickListener{
		void onFlush(Intent intent);
	}
}
