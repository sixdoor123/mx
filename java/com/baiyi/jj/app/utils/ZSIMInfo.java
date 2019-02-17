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
		return tm.getDeviceId();   //ȡ��IMEI

	}

	public static int getSIMState(Context context) {
		TelephonyManager tm = (TelephonyManager)

		context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getSimState();
	}

	// ����
	public static String getSIMNumber(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService

		(Context.TELEPHONY_SERVICE);
		return tm.getSimSerialNumber(); 
	}

	// ��Ӫ������
	public static String

	getSIMOperatorName(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService

		(Context.TELEPHONY_SERVICE);
		return tm.getSimOperatorName();
	}

	// ������ʽ
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
			return "δ֪";
		}
	}

	// ��Ӫ��
	public static String getSIMOperator(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String operator = tm.getSimOperator();
		if ("46000".equals(operator) || "46002".equals(operator)
				|| "45412".equals(operator) || "46007".equals(operator)) {
			// QPreference.getInstance().setPreference(QProDefine.T_SENDER,
			// QProDefine.SENDER_CMCC);
			return "�й��ƶ�";

		} else if ("46001".equals(operator)) {
			// QPreference.getInstance().setPreference(QProDefine.T_SENDER,
			// QProDefine.SENDER_UNICOM);
			return "�й���ͨ";
		} else if ("46003".equals(operator)) {
			// QPreference.getInstance().setPreference(QProDefine.T_SENDER,
			// QProDefine.SENDER_UNICOM);
			return "�й�����";

		} else {

			return "δ֪";
		}

	}

	// ����
	public static String getSIMCountryIso(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService

		(Context.TELEPHONY_SERVICE);
		return tm.getSimCountryIso();
	}
}
