package com.baiyi.cmall.utils;

import android.content.Context;
import android.text.TextUtils;

public class NetUtils {

	public static boolean isAlreadyLogin(Context context) {
		String token = XmlUtils.getInstence(context).getXmlStringValue(
				XmlName.TOKEN);
		if (TextUtils.isEmpty(token)) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isCompany(Context context) {
		boolean isCompany = XmlUtils.getInstence(context).getXmlBooleanValue(
				XmlName.Is_Company, false);
		return isCompany;
	}
}
