package com.baiyi.jj.app.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

public class ZSIMInfo {

	public ZSIMInfo() {

	}

	public static String getIMSI(Context context) {

		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return

		tm.getSubscriberId();
	}

	// imei
	public static String getDeviceID(Context context) {

		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();   //取出IMEI

	}

	public static int getSIMState(Context context) {
		TelephonyManager tm = (TelephonyManager)

		context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getSimState();
	}

	// 串号
	public static String getSIMNumber(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService

		(Context.TELEPHONY_SERVICE);
		return tm.getSimSerialNumber(); 
	}

	// 运营商名称
	public static String

	getSIMOperatorName(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService

		(Context.TELEPHONY_SERVICE);
		return tm.getSimOperatorName();
	}

	// 网络制式
	public static String

	getPhoneType(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService

		(Context.TELEPHONY_SERVICE);
		int type = tm.getPhoneType();
		if (type == TelephonyManager.PHONE_TYPE_GSM) {

			return "GSM";
		} else if (type == 2) {
			return "CDMA";
		} else {
			return "未知";
		}
	}

	// 运营商
	public static String getSIMOperator(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String operator = tm.getSimOperator();
		if ("46000".equals(operator) || "46002".equals(operator)
				|| "45412".equals(operator) || "46007".equals(operator)) {
			// QPreference.getInstance().setPreference(QProDefine.T_SENDER,
			// QProDefine.SENDER_CMCC);
			return "中国移动";

		} else if ("46001".equals(operator)) {
			// QPreference.getInstance().setPreference(QProDefine.T_SENDER,
			// QProDefine.SENDER_UNICOM);
			return "中国联通";
		} else if ("46003".equals(operator)) {
			// QPreference.getInstance().setPreference(QProDefine.T_SENDER,
			// QProDefine.SENDER_UNICOM);
			return "中国电信";

		} else {

			return "未知";
		}

	}

	// 国家
	public static String getSIMCountryIso(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService

		(Context.TELEPHONY_SERVICE);
		return tm.getSimCountryIso();
	}
}
