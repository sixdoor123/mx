/**
 * 
 */
package com.baiyi.jj.app.sevice;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.utils.TLog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author tangkun
 *
 */
public class NetChangeReceiver extends BroadcastReceiver{
	
	private NetChangeCallBack callback = null;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		boolean isGprs = ContextUtil.isGprs(context);
		if(CmsApplication.isGprs == isGprs)
		{
			return;
		}
		CmsApplication.isGprs = isGprs;
		if(callback != null)
		{
			callback.callBack(isGprs);
		}
	}

	public void setCallback(NetChangeCallBack callback) {
		this.callback = callback;
	}

}
