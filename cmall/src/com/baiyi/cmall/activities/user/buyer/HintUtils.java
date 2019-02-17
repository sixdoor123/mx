package com.baiyi.cmall.activities.user.buyer;

import com.baiyi.cmall.utils.Utils;

import android.R.string;
import android.text.TextUtils;
import android.util.Log;

/**
 * ��ʾ����
 * 
 * @author lizl
 * 
 */
public class HintUtils {

	public static final String OK_STRING = "OK";

	/**
	 * ����������ʾ
	 * 
	 * @param s
	 * @return
	 */
	public static String weightHint(String s) {

		if (TextUtils.isEmpty(s)) {
			return "��������Ϊ��";
		}
		if (Double.parseDouble(s) > 9999999) {
			return "���������ֵΪ9999999";
		}
		if (s.contains(".")) {
			return "����Ϊ����";
		}
		if (Double.parseDouble(s) <= 0) {
			return "��������Ϊ0";
		}
		return OK_STRING;
	}

	public static String getWeight(String s) {

		if (!TextUtils.isEmpty(s)) {
			if (Double.parseDouble(s) > 9999999) {
				return "���������ֵΪ9999999";
			}
			if (s.contains(".")) {
				return "����Ϊ����";
			}
			if (Double.parseDouble(s) <= 0) {
				return "��������Ϊ0";
			}
		}
		return OK_STRING;
	}

	/**
	 * ���������ʾ
	 * 
	 * @param s
	 * @return
	 */
	public static String inventoryHint(String s) {

		if (TextUtils.isEmpty(s)) {
			return "��治��Ϊ��";
		}
		if (Double.parseDouble(s) > 9999999) {
			return "�������ֵΪ9999999";
		}
		if (s.contains(".")) {
			return "���Ϊ����";
		}
		if (Double.parseDouble(s) <= 0) {
			return "��治��Ϊ0";
		}
		return OK_STRING;
	}

	/**
	 * ��Ǯ������ʾ
	 * 
	 * @param s
	 * @return
	 */
	public static String priceHint(String s) {

		if (TextUtils.isEmpty(s)) {
			return "�۸���Ϊ��";
		}
		if (Double.parseDouble(s) > 99999.99) {
			return "�۸����ֵΪ99999.99Ԫ";
		}

		if (Double.parseDouble(s) <= 0) {
			return "�۸���Ϊ0";
		}

		if (s.contains(".")) {
			if (s.substring(s.indexOf(".") + 1, s.length()).length() > 2) {
				return "�۸�ֻ�ܱ���С�����2λ";
			}
		}
		return OK_STRING;
	}

	public static String getPrice(String s) {
		if (!TextUtils.isEmpty(s)) {
			if (Double.parseDouble(s) > 99999.99) {
				return "�۸����ֵΪ99999.99Ԫ";
			}

			if (Double.parseDouble(s) <= 0) {
				return "�۸���Ϊ0";
			}

			if (s.contains(".")) {
				if (s.substring(s.indexOf(".") + 1, s.length()).length() > 2) {
					return "�۸�ֻ�ܱ���С�����2λ";
				}
			}
		}
		return OK_STRING;
	}

	/**
	 * Ԥ�����������ʾ
	 * 
	 * @param s
	 * @return
	 */
	public static String prePriceHint(String s) {

		if (TextUtils.isEmpty(s)) {
			return "Ԥ������Ϊ��";
		}
		if (Double.parseDouble(s) > 9999999.99) {
			return "Ԥ��������ֵΪ9999999.99Ԫ";
		}

		if (Double.parseDouble(s) <= 0) {
			return "Ԥ������Ϊ0";
		}

		String[] strings = s.split(".");
		int i = strings.length;

		if (s.contains(".")) {
			if (s.substring(s.indexOf(".") + 1, s.length()).length() > 2) {
				return "Ԥ����ֻ�ܱ���С�����2λ";
			}
		}
		return OK_STRING;
	}

	/**
	 * ����ʱ�䡢����ʱ����ʾ
	 * 
	 * @param s
	 * @return
	 */
	public static String timeHint(String start, String end) {

		// ��ǰʱ��
		long nowTime = System.currentTimeMillis();
		// ��ǰʱ����ꡪ���¡����� ������
		long nowYMDTime = Utils.getLongTime1(Utils.getTimeYMD(nowTime));
		// ��ǰʱ���ʱ�����֡����� ������
		long nowHMSTime = Utils.getLongTime2(Utils.getTimeHHMMSS(nowTime));

		long startTime = 0;
		long endTime = 0;
		if (!"��ѡ��".equals(start) && "��ѡ��".equals(end)) {
			startTime = Utils.getLongTime(start);
			if (startTime < nowYMDTime) {
				return "��ʼʱ�䲻��С�ڵ�ǰʱ��,���޸�";
			}
		} else if (!"��ѡ��".equals(end) && "��ѡ��".equals(start)) {
			endTime = Utils.getLongTime(end);
			if (endTime < nowYMDTime) {
				return "����ʱ�䲻��С�ڵ�ǰʱ�䣬���޸�";
			}
		} else if (!"��ѡ��".equals(end) && !"��ѡ��".equals(start)) {
			startTime = Utils.getLongTime(start);
			endTime = Utils.getLongTime(end);
			if (startTime < nowYMDTime) {
				return "��ʼʱ�䲻��С�ڵ�ǰʱ��,���޸�";
			}
			if (endTime < nowYMDTime) {
				return "����ʱ�䲻��С�ڵ�ǰʱ�䣬���޸�";
			}
			if (endTime < startTime) {
				return "����ʱ�䲻��С�ڷ���ʱ�䣬���޸�";
			}
		}
		return OK_STRING;
	}
}
