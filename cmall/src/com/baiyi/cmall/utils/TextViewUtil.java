package com.baiyi.cmall.utils;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

/**
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-3-7 下午3:35:44
 */
public class TextViewUtil {

	public static void setText(int resId, String showText, View view) {
		TextView textView = (TextView) view.findViewById(resId);
		textView.setText(Html.fromHtml("<font color=\"red\">*</font>"
				+ showText));
	}

	public static String getEditString(String s) {
		if ("null".equals(s) || null == s || "".equals(s)||TextUtils.isEmpty(s)) {
			return "";
		}

		return s;
	}

	public static boolean isStringEmpty(String s) {
		if ("null".equals(s) || null == s || "".equals(s)) {
			return true;
		}
		return false;
	}

	public static String getSelect(String s) {
		if ("null".equals(s) || null == s || "".equals(s)) {
			return "请选择";
		}
		return s;
	}

	public static String getDateSelect(String s) {
		if ("null".equals(s) || null == s || "".equals(s)) {
			return "请选择";
		}

		// 当前时间的年——月——日 毫秒数
		long nowYMDTime = Utils.getLongTime1(Utils.getTimeYMD(System
				.currentTimeMillis()));
		long startTime = Utils.getLongTime(s);
		if (startTime < nowYMDTime) {
			return "请选择";
		}
		return s;
	}
}
