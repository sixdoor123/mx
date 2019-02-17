/**
 * 
 */
package com.baiyi.cmall.application;

import android.content.Context;

import com.baiyi.cmall.dialog.DailogNoLogin;
import com.baiyi.cmall.dialog.DialogBase;

/**
 * @author tangkun
 * 
 */
public class AppUtils {

	/**
	 * Î´µÇÂ¼ÌáÊ¾
	 * 
	 * @param context
	 * @param title
	 * @param message
	 */
	public static void loginRemind(final Context context, String title,
			String message) {
		DailogNoLogin dailog = new DailogNoLogin(context, message,
				DialogBase.Win_Center);
		dailog.show();
	}
}
