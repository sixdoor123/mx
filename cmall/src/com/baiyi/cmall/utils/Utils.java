package com.baiyi.cmall.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.TypedValue;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.dialog.MsgBoxNotice;
import com.baiyi.core.util.ContextUtil;

public class Utils {

	public static String getDataBasePath() {
		String path = ContextUtil.getSDPath() + "/" + Config.Root_File_Path
				+ "/database/";
		return path;
	}

	public static String getDataPath(Context context) {
		File file = new File(context.getFilesDir(), "data");
		file.mkdirs();
		return context.getFilesDir().getPath();
	}

	public static boolean checkEmail(String email) {
		Pattern pattern = Pattern.compile(
				"[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);
		System.out.println(matcher.matches());
		return matcher.matches();
	}

	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	public static boolean isPhoneNumberValid(String phoneNumber) {

		String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)"
				+ "|(^0[3-9]{1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)"
				+ "|(^0[3-9]{1}\\d{2}-?\\d{7,8}-(\\d{1,4})$))";
		CharSequence inputStr = phoneNumber;

		Pattern pattern = Pattern.compile(expression);

		Matcher matcher = pattern.matcher(inputStr);

		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	public static int sp2px(Context context, int spVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				spVal, context.getResources().getDisplayMetrics());
	}

	public static int dp2px(Context context, int dpVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpVal, context.getResources().getDisplayMetrics());

	}

	public static String replaceRefund(String sourceStr, String[][] strings) {
		String temp = sourceStr;
		for (int i = 0; i < strings.length; i++) {
			String[] result = strings[i];
			Pattern pattern = Pattern.compile(result[0]);
			Matcher matcher = pattern.matcher(temp);
			temp = matcher.replaceAll(result[1]);
		}
		return temp;

	}

	public static boolean isPhoneNum(String phoneNumber) {

		if (phoneNumber.length() == 11 || phoneNumber.length() == 7
				|| phoneNumber.length() == 8) {
			return true;
		} else {
			return false;
		}
	}

	// ��.��ͷ������ת����0.��ͷ
	public static String getTrueNumber(String number) {
		if (!Utils.isStringEmpty(number)) {
			number.substring(0, 1).equals(".");
			return "0" + number;
		} else {
			return "0";
		}
	}

	public byte[] eccrypt(String info) throws NoSuchAlgorithmException {
		// ����MD5�㷨����MessageDigest����
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] srcBytes = info.getBytes();
		// ʹ��srcBytes����ժҪ
		md5.update(srcBytes);
		// ��ɹ�ϣ���㣬�õ�result
		byte[] resultBytes = md5.digest();
		return resultBytes;
	}

	/**
	 * ������λС��
	 * 
	 * @param d
	 * @return
	 */
	public static String getPoint2Float(double d) {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		return decimalFormat.format(d);
	}

	public static String getMD5Code(String inStr) {

		MessageDigest md = null;

		String out = null;

		try {

			md = MessageDigest.getInstance("MD5");

			byte[] digest = md.digest(inStr.getBytes());

			out = byte2hex(digest);

		} catch (NoSuchAlgorithmException e) {

			System.out.println(e.toString());
			e.printStackTrace();

		}

		return out.toUpperCase();

	}

	private static String byte2hex(byte[] b) {

		String hs = "";

		String stmp = "";

		for (int n = 0; n < b.length; n++) {

			stmp = (Integer.toHexString(b[n] & 0XFD));

			if (stmp.length() == 1) {

				hs = hs + "0" + stmp;

			} else {

				hs = hs + stmp;

			}

		}

		return hs;

	}

	public static boolean isSDCardExists() {
		return Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * 
	 * @return ������ByteΪ��λ
	 */
	public static long getSpace() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			String sdcard = Environment.getExternalStorageDirectory().getPath();
			StatFs statFs = new StatFs(sdcard);
			long blockSize = statFs.getBlockSize();
			long blocks = statFs.getAvailableBlocks();
			long availableSpare = blocks * blockSize;
			// Logger.error("ʣ��ռ�", "availableSpare = " + availableSpare);
			// if (availableSpare > sizeMb) {
			// ishasSpace = true;
			// }
			return availableSpare;
		}
		return 0;
	}

	public static boolean isNum(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	public static boolean getSDCard1M() {
		if (getSpace() < 1)
			return false;
		return true;
	}

	public static boolean isStringEmpty(String data) {
		if (data == null || "".equals(data)||"null".equals(data))
			return true;
		return false;
	}

	public static boolean isStringEmpty(List<?> list) {
		if (list == null || list.size() == 0)
			return true;
		return false;
	}

	public static String getTimeHHMM(Long time) {
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		Date data = new Date(time);
		return timeFormat.format(data);
	}

	public static String getTimeHHMMSS(Long time) {
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss.SSS");
		Date data = new Date(time);
		return timeFormat.format(data);
	}

	public static String getTimeMMDD(Long time) {
		SimpleDateFormat timeFormat = new SimpleDateFormat("MM-dd");
		Date data = new Date(time);
		return timeFormat.format(data);
	}

	public static String getTimeYMDHHMM(Long time) {
		Date date = new Date(time);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		// 2015-09-08 10:16:47
		return format.format(date);
	}

	public static String getCurrentTime2(long date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String str = format.format(new Date(date));
		return str;
	}

	public static String getTimeYMD(Long time) {
		Date date = new Date(time);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}

	public static String getTimeYMD2(Long time) {
		Date date = new Date(time);
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
		return format.format(date);
	}

	public static String getTime2Date(long time) {
		return new Date(time).toString();
	}

	public static String getListHeadTime() {
		String time = getTimeYMD(System.currentTimeMillis());
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
		if ("1".equals(mWay)) {
			mWay = "������";
		} else if ("2".equals(mWay)) {
			mWay = "����һ";
		} else if ("3".equals(mWay)) {
			mWay = "���ڶ�";
		} else if ("4".equals(mWay)) {
			mWay = "������";
		} else if ("5".equals(mWay)) {
			mWay = "������";
		} else if ("6".equals(mWay)) {
			mWay = "������";
		} else if ("7".equals(mWay)) {
			mWay = "������";
		}
		return time + " " + mWay;
	}

	public static long getTimeSecond(String time) {
		long seconds = 0;
		if (Utils.isStringEmpty(time)) { // ���ж�ʱ���Ƿ�Ϊ��
			return seconds;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			seconds = format.parse(time).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return seconds;
	}

	public static long getTimeSecond2(String time) {
		long seconds = 0;
		if (Utils.isStringEmpty(time)) { // ���ж�ʱ���Ƿ�Ϊ��
			return seconds;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			seconds = format.parse(time).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return seconds;
	}

	public static String getTimeName(String time) {

		if (Utils.isStringEmpty(time)) { // ���ж�ʱ���Ƿ�Ϊ��
			return "";
		}

		// ��ʱ���ʽת���ɺ�����
		long seconds = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			seconds = format.parse(time).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return time;
		}

		// ���㵱ǰʱ����뷢��ʱ��ĺ�����
		long currentTime = System.currentTimeMillis();
		long timeDef = currentTime - seconds;
		if (timeDef < 60 * 1000) { // ���ж�һ�����ڵ�
			return "�ո�";
		} else if (timeDef < 60 * 60 * 1000) { // �ж�һСʱ�ڵ�
			return (timeDef / 1000 / 60) + "����ǰ";
		} else if (timeDef < 24 * 60 * 60 * 1000) { // �ж�һ���ڵ�
			return (timeDef / 1000 / 60 / 60) + "Сʱǰ";
		} else if (timeDef < 3 * 24 * 60 * 60 * 1000) { // �ж������ڵ�
			return (timeDef / 1000 / 60 / 60 / 24) + "��ǰ";
		} else { // ���������������
			return getTimeYMD(getTimeSecond(time));
		}
	}

	/**
	 * dipתΪ px
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * px תΪ dip
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static String getCount4(String sr) {
		if (isStringEmpty(sr))
			return null;
		return null;
	}

	public static void goIntentTel(Context context, String phoneNum) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.DIAL");
		intent.setData(Uri.parse("tel:" + phoneNum));
		context.startActivity(intent);
	}

	public static String[] split(String original, String regex) {
		int startIndex = 0;
		Vector v = new Vector();
		String[] str = null;
		int index = 0;
		startIndex = original.indexOf(regex);
		while (startIndex < original.length() && startIndex != -1) {
			String temp = original.substring(index, startIndex);
			System.out.println(" " + startIndex);
			v.addElement(temp);
			index = startIndex + regex.length();
			startIndex = original.indexOf(regex, startIndex + regex.length());
		}
		v.addElement(original.substring(index + 1 - regex.length()));
		str = new String[v.size()];
		for (int i = 0; i < v.size(); i++) {
			str[i] = (String) v.elementAt(i);
		}
		return str;
	}

	// public static String getHiddenPhoneNum(String phoneNum) {
	// String start = phoneNum.substring(0, 3);
	// String end = phoneNum.substring(7, phoneNum.length());
	// return start + "****" + end;
	// }

	public static String getTimeFormat(String timeStr) {
		if (timeStr.equals("") || timeStr == null) {
			return "";
		} else {
			String time = timeStr.substring(0, timeStr.lastIndexOf("-") + 3);
			return timeStr == null ? "û����" : time;
		}
	}

	public static String getTimeFormat2(String timeStr) {
		String time = timeStr.substring(0, timeStr.lastIndexOf(":"));
		String time2 = time.replace("T", "  ");
		return timeStr == null ? "û����" : time2;
	}

	public static String getCurrentTime(long date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String str = format.format(new Date(date));
		return str;
	}

	public static String getCurrentTime1(long date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:dmm:ss");
		String str = format.format(new Date(date));
		return str;
	}

	// �����Ǯ������ ȥ��С����
	public static String getIntegerToPrice(double d) {
		int n = (int) d;
		if ((d - n) == 0) {
			return String.valueOf(n);
		} else {
			return String.valueOf(d);
		}
	}

	/**
	 * ���ַ������з���
	 * 
	 * @param string
	 *            279225644
	 * @param num
	 *            3
	 * @return 279 225 644
	 */
	public static String getFomatStr(String string, int num) {
		if (isStringEmpty(string)) {
			return "";
		}
		String Str = string.replace(" ", "");
		char[] chars = Str.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i < chars.length + 1; i++) {
			sb.append(chars[i - 1]);
			if (i % num == 0 && i != 0) {
				sb.append(" ");
			}
		}
		return sb.toString().trim();
	}

	/**
	 * ���ֻ�����������ش���
	 * 
	 * @param phone
	 *            18712345678
	 * @return 187****5678
	 */
	public static String getHiddenPhone(String phone) {
		if (isStringEmpty(phone)) {
			return "";
		}
		String str = phone.replace(" ", "");
		return str.substring(0, 3) + "****" + str.substring(7, str.length());
	}

	/**
	 * �ж�ʱ����Ƿ����2����
	 * 
	 * @param locTime
	 * @return
	 */
	public static boolean isSecondMinite(long locTime) {
		long currentTime = System.currentTimeMillis();
		long temp = currentTime - locTime;
		if ((temp / 1000) > 120) {
			return true;
		}
		return false;
	}

	/**
	 * �ж��Ƿ����
	 * 
	 * @param locTime
	 * @return
	 */
	public static boolean isDay(long locTime) {
		Date locDate = new Date(locTime);
		long curTime = System.currentTimeMillis();
		Date curDate = new Date(curTime);
		int currentDay = curDate.getDate();
		int locDay = locDate.getDate();
		if (currentDay != locDay) {
			return true;
		}
		return false;
	}

	public static String getStringToInts(int[] tags) {
		if (tags == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < tags.length; i++) {
			sb.append(tags[i]);
			if (i != tags.length - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	public static List<Integer> getTagsList(int[] tags) {
		if (tags == null) {
			return null;
		}
		List<Integer> tagsList = new ArrayList<Integer>();
		for (int i = 0; i < tags.length; i++) {
			tagsList.add(tags[i]);
		}
		return tagsList;
	}

	public static int[] getTagsArray(String tag) {
		if (Utils.isStringEmpty(tag)) {
			return null;
		}
		String[] tagArray = tag.split(",");
		int[] tags = new int[tagArray.length];
		for (int i = 0; i < tagArray.length; i++) {
			tags[i] = Integer.parseInt(tagArray[i]);
		}
		return tags;
	}

	/**
	 * ���ַ����еĶ���ת��Ϊ�ո�
	 * 
	 * @param str
	 * @return
	 */
	public static String getCommaToSpace(String str) {
		if (isStringEmpty(str)) {
			return "";
		}
		// String string = str.replace(" ", "");

		return str.replace(",", "  ");
	}

	// �ַ�������תList<String>
	public static String[] switchToStringArray(List<String> list) {
		String[] strings = new String[list.size()];
		for (int i = 0; i < strings.length; i++) {
			strings[i] = list.get(i);
		}
		return strings;
	}

	// �ַ�������תList<String>
	public static List<String> switchToStringList(String[] strings) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < strings.length; i++) {
			list.add(strings[i]);
		}
		return list;
	}

	// �ж�һ���ַ����������Ƿ����ĳһ���ַ���
	public static boolean isExistInStringArray(String[] strings, String recom) {
		boolean isExist = false;
		for (String string : strings) {
			if (string.equals(recom)) {
				isExist = true;
				break;
			}
		}
		return isExist;
	}

	// �ж�һ��List<String>���Ƿ����ĳһ���ַ���
	public static boolean isExistInStringList(List<String> list, String recom) {
		boolean isExist = false;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(recom)) {
				isExist = true;
				break;
			}
		}
		return isExist;
	}

	public static int pixToDip(int pix) {
		int Dip = 0;

		return Dip;
	}

	// ���������
	public static int getRandom() {

		Random random = new Random();
		int num = random.nextInt(9) + 1;
		return num;
	}

	/**
	 * ��ʽ��ʱ��
	 * 
	 * @param year
	 * @param i
	 * @param day
	 * @return
	 */
	public static String getDateYYYYMMDD(int year, int month, int day) {

		String y = year + "";
		String m = month + "";
		String d = day + "";

		if (y.length() <= 2) {
			y = "20" + y;
		}
		if (m.length() <= 1) {
			m = "0" + m;
		}
		if (d.length() <= 1) {
			d = "0" + d;
		}

		return y + "-" + m + "-" + d;
	}

	/**
	 * ��С����ʽ���ɰٷֱ�
	 * 
	 * @param dou
	 * @return
	 */
	public static String decimalData(Double dou) {
		DecimalFormat df = new DecimalFormat("0");
		return df.format(dou * 100) + "%";

	}

	/**
	 * ��һ���ַ�����ȡ�����ֲ���
	 * 
	 * @param string
	 * @return
	 */
	public static String getNumberOfString(String string) {
		if (string == null) {
			return null;
		}
		if ("".equals(string)) {
			return null;
		}
		String regEx = "[^0-9.]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(string);
		System.out.println(m.replaceAll("").trim());
		return m.replaceAll("").trim();
	}

	/**
	 * ��ʱ��ת����long��
	 * 
	 * @param trim
	 * @return
	 */
	public static long getLongTime(String trim) {
		if ("��ѡ��".equals(trim)) {
			return 0;
		}
		if (trim == null) {
			return 0;
		}
		if (trim.equals("")) {
			return 0;
		}
		String[] times = trim.split("-");

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.parseInt(times[0]));
		calendar.set(Calendar.MONTH, Integer.parseInt(times[1]) - 1);
		calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(times[2]));

		long time = calendar.getTimeInMillis();
		return time;
	}

	/**
	 * ��ʱ��(2015-1-1)��ʽ������long����
	 * 
	 * 
	 * @param trim
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static long getLongTime1(String trim) {

		if (trim == null) {
			return 0;
		}
		if (trim.equals("")) {
			return 0;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = dateFormat.parse(trim);
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * ��ʱ��(2015-1-1)��ʽ������long����
	 * 
	 * 
	 * @param trim
	 * @return
	 */
	public static long getLongTime2(String trim) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
		Date date = null;
		try {
			date = dateFormat.parse(trim);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime();
	}

	/**
	 * �����ȥĳ��ʱ��������ڵ�ʱ���
	 * 
	 * @param time
	 * @return
	 */
	public static boolean getTimeFromNow(String time) {

		if (Utils.isStringEmpty(time)) { // ���ж�ʱ���Ƿ�Ϊ��
			return true;
		}

		// ��ʱ���ʽת���ɺ�����
		long seconds = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			seconds = format.parse(time).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}

		// ���㵱ǰʱ����뷢��ʱ��ĺ�����
		long currentTime = System.currentTimeMillis();
		long timeDef = currentTime - seconds;
		if (timeDef <= 2 * 60 * 60 * 1000) { // �ж�1���ڵ�
			return false;
		} else { // 1�������������
			return true;
		}
	}

	/**
	 * DecimalFormatת������ װ���ɱ�����λС��
	 */
	public static String twoDecimals(float f) {

		DecimalFormat df = new DecimalFormat("#.00");
		System.out.println(df.format(f));
		return String.valueOf(df.format(f));
	}

	/**
	 * DecimalFormatת������ װ���ɱ�����λС��
	 */
	public static String twoDecimals(String f) {
		if ("null".equals(f)) {
			return "0";
		}
		if (null == f) {
			return "0";
		}
		if ("".equals(f)) {
			return "0";
		}
		DecimalFormat df = new DecimalFormat("#.00");
		String _result = df.format(Double.parseDouble(f));
		if (_result.startsWith(".")) {
			return "0" + _result;
		} else {
			return _result;
		}
	}

	/**
	 * ����۸�
	 */
	public static String dealPrice(String f) {
		if (TextUtils.isEmpty(f) || "null".equals(f)) {
			return "0";
		}
		// ������λС����
		DecimalFormat df1 = new DecimalFormat("#.00");
		// С����ʡ��
		// DecimalFormat df2 = new DecimalFormat("#");
		double data = Double.parseDouble(f);

		if (data >= 10000) {
			return "Լ" + df1.format(data / 10000) + "��";
		} else {

			if (df1.format(data).startsWith(".")) {
				return "0" + df1.format(data);
			} else {
				return df1.format(data);
			}
		}
	}

	public static String getWeight(String weight) {
		if (null == weight) {
			return "0";
		}
		if (TextUtils.isEmpty(weight)) {
			return "0";
		}
		int data = Integer.parseInt(weight);
		if (data >= 10000) {
			return ">" + data / 10000 + "��";
		}

		return weight;
	}

	/**
	 * ��������
	 */
	public static String dealWeight(String f) {
		if (null == f || "null".equals(f)) {
			return "0";
		}
		int data = Integer.parseInt(f);

		if (data >= 10000) {
			return "Լ" + data / 10000 + "��";
		} else {
			return f;
		}
	}

	/*********************** ��֤�ַ����ĳ��� ********************/

	/**
	 * ��֤30���ַ�
	 */
	public static boolean isExceed_30(String s) {
		if ("null".equals(s)) {
			return true;
		}
		if (null == s) {
			return true;
		}
		if (TextUtils.isEmpty(s)) {
			return true;
		}
		if (s.length() > 30) {
			return false;
		}
		return true;
	}

	/**
	 * ��֤1000���ַ�
	 */
	public static boolean isExceed_1000(String s) {
		if ("null".equals(s)) {
			return true;
		}
		if (null == s) {
			return true;
		}
		if (TextUtils.isEmpty(s)) {
			return true;
		}
		if (s.length() > 1000) {
			return false;
		}
		return true;
	}

	/**
	 * ��֤20���ַ�
	 */
	public static boolean isExceed_20(String s) {
		if ("null".equals(s)) {
			return true;
		}
		if (null == s) {
			return true;
		}
		if (TextUtils.isEmpty(s)) {
			return true;
		}
		if (s.length() > 20) {
			return false;
		}
		return true;
	}

	/**
	 * ��֤7���ַ�
	 */
	public static boolean isExceed_7(String s) {
		if ("null".equals(s)) {
			return true;
		}
		if (null == s) {
			return true;
		}
		if (TextUtils.isEmpty(s)) {
			return true;
		}
		if (s.length() > 7) {
			return false;
		}
		return true;
	}

	/**
	 * ��֤8���ַ�
	 */
	public static boolean isExceed_8(String s) {
		if ("null".equals(s)) {
			return true;
		}
		if (null == s) {
			return true;
		}
		if (TextUtils.isEmpty(s)) {
			return true;
		}
		if (s.length() > 8) {
			return false;
		}
		return true;
	}

	/**
	 * ��֤10���ַ�
	 */
	public static boolean isExceed_10(String s) {
		if ("null".equals(s)) {
			return true;
		}
		if (null == s) {
			return true;
		}
		if (TextUtils.isEmpty(s)) {
			return true;
		}
		if (s.length() > 10) {
			return false;
		}
		return true;
	}
	/**
	 * ��֤10���ַ�
	 */
	public static int stringToInt(String s) {
		if ("null".equals(s)) {
			return 0;
		}
		if (null == s) {
			return 0;
		}
		if (TextUtils.isEmpty(s)) {
			return 0;
		}
		return Integer.valueOf(s);
	}
	
	public static byte[] bmpToByteArray(final Bitmap bmp,
			final boolean needRecycle) {

		int i;
		int j;
		if (bmp.getHeight() > bmp.getWidth()) {
			i = bmp.getWidth();
			j = bmp.getWidth();
		} else {
			i = bmp.getHeight();
			j = bmp.getHeight();
		}

		Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.RGB_565);
		Canvas localCanvas = new Canvas(localBitmap);

		while (true) {
			localCanvas.drawBitmap(bmp, new Rect(0, 0, i, j), new Rect(0, 0, i,
					j), null);
			if (needRecycle)
				bmp.recycle();
			ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
			localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
					localByteArrayOutputStream);
			localBitmap.recycle();
			byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
			try {
				localByteArrayOutputStream.close();
				return arrayOfByte;
			} catch (Exception e) {
				// F.out(e);
			}
			i = bmp.getHeight();
			j = bmp.getHeight();
		}
	}

}
