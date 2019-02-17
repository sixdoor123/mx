/**
 * 
 */
package com.baiyi.jj.app.activity.main;

import android.content.IntentFilter;

import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.sevice.NetChangeCallBack;
import com.baiyi.jj.app.sevice.NetChangeReceiver;

/**
 * @author tangkun
 *
 */
public class MainNetChangeActivity extends BaseActivity{
	
	private NetChangeReceiver receiver = null;
	
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		super.onRefresh();
		if(receiver == null)
		{
			receiver = new NetChangeReceiver();
			receiver.setCallback(new NetChangeCallBack() {
				
				@Override
				public void callBack(boolean isGprs) {
					// TODO Auto-generated method stub
					netWorkChange(isGprs);
				}
			});
		}
	    try{
	    	IntentFilter filter = new IntentFilter();
	    	filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
	    	filter.setPriority(1000);
	    	registerReceiver(receiver, filter);
	    }catch (Exception e) {
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (receiver!=null){
			unregisterReceiver(receiver);
		}

	}

	protected void netWorkChange(boolean isGprs)
	{
		//����仯 ����
	}

}
