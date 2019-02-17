package com.baiyi.jj.app.utils;

import android.content.Context;

import com.turbo.turbo.mexico.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {
	public static String getTimeHHMM(Long time) {
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
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

	public static String getTimeYMDHMS(Long time) {
		Date date = new Date(time);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	public static String getCurentDay(){
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int mouth = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String stringBuffer = year+""+mouth+""+day;
		return stringBuffer;
	}
	/**
	 *  ȫдʱ��
	 */
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
	
	/**
	 *  ��Լ����
	 */
	public static String getTimeYMD3(Long time) {
		Date date = new Date(time);
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
		return format.format(date);
	}
	/**
	 *  ��Լʱ��
	 */
	public static String getTimeYMD4(Long time) {
		Date date = new Date(time);
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd hh:mm");
		return format.format(date);
	}

	public static long getTimeSecond(String time,boolean isUTC) {
		long seconds = 0;
		if (Utils.isStringEmpty(time)) { // ���ж�ʱ���Ƿ�Ϊ��
			return seconds;
		}
		if (time.contains("T"))
		{
			time = time.replace("T"," ");
		}
		if (time.contains("Z"))
		{
			time = time.replace("Z"," ");
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (isUTC){
			format.setTimeZone(TimeZone.getTimeZone("UTC"));
		}
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

	public static String getTimeName(Context context, String time,boolean isUTC) {

		if (Utils.isStringEmpty(time)) { // ���ж�ʱ���Ƿ�Ϊ��
			return "";
		}

		// ��ʱ���ʽת���ɺ�����
		long seconds = 0;

		if (time.contains("T"))
		{
			time = time.replace("T"," ");
		}


		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (isUTC){
			format.setTimeZone(TimeZone.getTimeZone("UTC"));
		}
		try {
			seconds = format.parse(time).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return time;
		}

		// ���㵱ǰʱ����뷢��ʱ��ĺ�����
		long currentTime = System.currentTimeMillis();
//		Calendar calendar = Calendar.getInstance(Locale.US);
//		long currentTime = calendar.getTime().getTime();

		long timeDef = currentTime - seconds;
		if (timeDef < 60 * 2 * 1000) { // ���ж��������ڵ�
			return  context.getResources().getString(R.string.name_just);
		} else if (timeDef < 60 * 3 * 1000) {
			return  context.getResources().getString(R.string.txt_time1);
		} else if (timeDef < 60 * 5 * 1000) {
			return  context.getResources().getString(R.string.txt_time2);
		} else if (timeDef < 60 * 10 * 1000) {
			return  context.getResources().getString(R.string.txt_time3);
		} else if (timeDef < 60 * 15 * 1000) {
			return  context.getResources().getString(R.string.txt_time4);
		} else if (timeDef < 60 * 30 * 1000) {
			return  context.getResources().getString(R.string.txt_time5);
		} else if (timeDef < 60 * 60 * 1000) { // �ж�һСʱ�ڵ�
			return  context.getResources().getString(R.string.txt_time6);
		} else if (timeDef < 60 * 60 * 2 * 1000) { // �ж�һСʱ�ڵ�
			return  context.getResources().getString(R.string.txt_time7);
		} else if (timeDef < 60 * 60 * 3 * 1000) {
			return  context.getResources().getString(R.string.txt_time8);
		} else if (timeDef < 60 * 60 * 6 * 1000) {
			return  context.getResources().getString(R.string.txt_time9);
		} else if (timeDef < 60 * 60 * 12 * 1000) {
			return  context.getResources().getString(R.string.txt_time10);
		} else if (timeDef < 60 * 60 * 24 * 1000) {
			return  context.getResources().getString(R.string.txt_time11);
		} else if (timeDef < 2 * 24 * 60 * 60 * 1000) { // �ж������ڵ�
			return  context.getResources().getString(R.string.txt_time12);
		} else if (timeDef < 3 * 24 * 60 * 60 * 1000) { // �ж������ڵ�
			return  context.getResources().getString(R.string.txt_time13);
		} else if (timeDef < 7 * 24 * 60 * 60 * 1000) { // �ж������ڵ�
			return  context.getResources().getString(R.string.txt_time14);
		} else { // ���������������
			return getTimeYMD3(getTimeSecond(time,false));
		}
	}
	
	/**
	 *  ��Լʱ��
	 */
	public static String getDZTime(String time)
	{
		if (Utils.isStringEmpty(time)) { // ���ж�ʱ���Ƿ�Ϊ��
			return "";
		}

		// ��ʱ���ʽת���ɺ�����
		long seconds = 0;
		if (time.contains("T"))
		{
			time = time.replace("T"," ");
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			seconds = format.parse(time).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return time;
		}
		
		return getTimeYMD4(seconds);
	}
	
	/**
	 *  ��Լ����
	 */
	public static String getCollectTime(String time)
	{
		if (Utils.isStringEmpty(time)) { // ���ж�ʱ���Ƿ�Ϊ��
			return "";
		}

		if (time.contains("T")){
			time = time.replace("T"," ");
		}

		long seconds = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			seconds = format.parse(time).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return time;
		}
		
		return getTimeYMD3(seconds);
	}
	
	public static String getCurrentTime(long date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(new Date(date));
		return str;
	}
	public static String getCurrentTime2(long date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String str = format.format(new Date(date));
		return str;
	}
	public static String getCurrentTime3(long date) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		String str = format.format(new Date(date));
		return str;
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
}
