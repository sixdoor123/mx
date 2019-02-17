package com.baiyi.cmall.activities.user.buyer;

import com.baiyi.cmall.utils.Utils;

import android.R.string;
import android.text.TextUtils;
import android.util.Log;

/**
 * 提示工具
 * 
 * @author lizl
 * 
 */
public class HintUtils {

	public static final String OK_STRING = "OK";

	/**
	 * 数量输入提示
	 * 
	 * @param s
	 * @return
	 */
	public static String weightHint(String s) {

		if (TextUtils.isEmpty(s)) {
			return "数量不能为空";
		}
		if (Double.parseDouble(s) > 9999999) {
			return "数量的最大值为9999999";
		}
		if (s.contains(".")) {
			return "数量为整数";
		}
		if (Double.parseDouble(s) <= 0) {
			return "数量不能为0";
		}
		return OK_STRING;
	}

	public static String getWeight(String s) {

		if (!TextUtils.isEmpty(s)) {
			if (Double.parseDouble(s) > 9999999) {
				return "数量的最大值为9999999";
			}
			if (s.contains(".")) {
				return "数量为整数";
			}
			if (Double.parseDouble(s) <= 0) {
				return "数量不能为0";
			}
		}
		return OK_STRING;
	}

	/**
	 * 库存输入提示
	 * 
	 * @param s
	 * @return
	 */
	public static String inventoryHint(String s) {

		if (TextUtils.isEmpty(s)) {
			return "库存不能为空";
		}
		if (Double.parseDouble(s) > 9999999) {
			return "库存的最大值为9999999";
		}
		if (s.contains(".")) {
			return "库存为整数";
		}
		if (Double.parseDouble(s) <= 0) {
			return "库存不能为0";
		}
		return OK_STRING;
	}

	/**
	 * 价钱输入提示
	 * 
	 * @param s
	 * @return
	 */
	public static String priceHint(String s) {

		if (TextUtils.isEmpty(s)) {
			return "价格不能为空";
		}
		if (Double.parseDouble(s) > 99999.99) {
			return "价格最大值为99999.99元";
		}

		if (Double.parseDouble(s) <= 0) {
			return "价格不能为0";
		}

		if (s.contains(".")) {
			if (s.substring(s.indexOf(".") + 1, s.length()).length() > 2) {
				return "价格只能保留小数点后2位";
			}
		}
		return OK_STRING;
	}

	public static String getPrice(String s) {
		if (!TextUtils.isEmpty(s)) {
			if (Double.parseDouble(s) > 99999.99) {
				return "价格最大值为99999.99元";
			}

			if (Double.parseDouble(s) <= 0) {
				return "价格不能为0";
			}

			if (s.contains(".")) {
				if (s.substring(s.indexOf(".") + 1, s.length()).length() > 2) {
					return "价格只能保留小数点后2位";
				}
			}
		}
		return OK_STRING;
	}

	/**
	 * 预付金额输入提示
	 * 
	 * @param s
	 * @return
	 */
	public static String prePriceHint(String s) {

		if (TextUtils.isEmpty(s)) {
			return "预付金额不能为空";
		}
		if (Double.parseDouble(s) > 9999999.99) {
			return "预付金额最大值为9999999.99元";
		}

		if (Double.parseDouble(s) <= 0) {
			return "预付金额不能为0";
		}

		String[] strings = s.split(".");
		int i = strings.length;

		if (s.contains(".")) {
			if (s.substring(s.indexOf(".") + 1, s.length()).length() > 2) {
				return "预付费只能保留小数点后2位";
			}
		}
		return OK_STRING;
	}

	/**
	 * 发布时间、结束时间提示
	 * 
	 * @param s
	 * @return
	 */
	public static String timeHint(String start, String end) {

		// 当前时间
		long nowTime = System.currentTimeMillis();
		// 当前时间的年——月——日 毫秒数
		long nowYMDTime = Utils.getLongTime1(Utils.getTimeYMD(nowTime));
		// 当前时间的时——分——秒 毫秒数
		long nowHMSTime = Utils.getLongTime2(Utils.getTimeHHMMSS(nowTime));

		long startTime = 0;
		long endTime = 0;
		if (!"请选择".equals(start) && "请选择".equals(end)) {
			startTime = Utils.getLongTime(start);
			if (startTime < nowYMDTime) {
				return "开始时间不能小于当前时间,请修改";
			}
		} else if (!"请选择".equals(end) && "请选择".equals(start)) {
			endTime = Utils.getLongTime(end);
			if (endTime < nowYMDTime) {
				return "结束时间不能小于当前时间，请修改";
			}
		} else if (!"请选择".equals(end) && !"请选择".equals(start)) {
			startTime = Utils.getLongTime(start);
			endTime = Utils.getLongTime(end);
			if (startTime < nowYMDTime) {
				return "开始时间不能小于当前时间,请修改";
			}
			if (endTime < nowYMDTime) {
				return "结束时间不能小于当前时间，请修改";
			}
			if (endTime < startTime) {
				return "结束时间不能小于发布时间，请修改";
			}
		}
		return OK_STRING;
	}
}
