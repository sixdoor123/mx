package com.baiyi.cmall.activities.user.login;

import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.main.cache.DataCache;
import com.baiyi.cmall.utils.XmlUtils;
import android.content.Context;

/**
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-3-29 上午11:20:42
 */
public class ExitLogin {

	private static ExitLogin login;
	private Context context;

	public ExitLogin(Context context) {
		this.context = context;
	}

	public static ExitLogin getInstence(Context context) {
		if (login == null) {
			login = new ExitLogin(context);
		}
		return login;
	}

	/**
	 * 清空数据
	 */
	public void cleer() {
		new Thread() {
			@Override
			public void run() {
				XmlUtils.getInstence(context).delete();
//				CmallApplication.setUserInfo(null);
				DataCache.getInstence(context).clear();
			};
		}.start();
	}
}
