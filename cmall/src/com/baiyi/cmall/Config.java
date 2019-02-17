package com.baiyi.cmall;

import java.io.InputStream;
import java.util.Properties;

import org.apache.http.util.EncodingUtils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Display;
import android.view.WindowManager;

import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.utils.Utils;

public class Config {
	
	private Integer screenWidth;
	private Integer screenHeight;

	private static Config instance = null;

	public static final int DB_VERSION = 1;

	private int versionCode;
	private String versionName; 
	
	//测试
	public static final String ROOT_URL = "http://10.20.22.254:8080/cmallwebservice/";
//	public static final String ROOT_URL = "http://u1q4567516.iask.in/cmallwebservice/";
//	public static final String ROOT_URL = "http://330561b9.nat123.net/cmallwebservice/";
	
	public static String ROOT_HTML = CmallApplication.getUserInfo().getBaseAddress() + "/";

	// 系统ID
	public static final String System_Id = "500";

	// 访问新闻的URL
	public static final String NEW_URL = "http://121.201.58.72:8000/";

	public static final String Root_File_Path = "cmall";

	public static final String IMAGE_CACHE_PATH = "/" + Root_File_Path + "/files/coverimgs/";
	public static final String IMAGE_CACHE_HEAD_PATH = "/" + Root_File_Path + "/imgs/head/";

	public static final String User_File_Path = "/" + Root_File_Path + "/files/user/";
	public static final String Base_Data_File_Path = "/" + Root_File_Path + "/files/basebatas/";
	public static final int IMAGE_CACHE_COUNT = 100;
	public static final int LIST_ITEM_COUNT = 10;
	public static final int LIST_SIX_COUNT = 6;
	public static final int LIST_ITEM_COUNT_Message = 10;
	public static final int MAX_DOWN_COVER_NO = Integer.MAX_VALUE;

	public static final String XmlFileName = "cmall";

	private Config() {

	}

	public static Config getInstance() {
		if (instance == null)
			instance = new Config();
		return instance;
	}

	@SuppressWarnings("deprecation")
	public void initScreenParams(Context context) {
		// 初始化屏幕参数
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		screenWidth = display.getWidth();
		screenHeight = display.getHeight();
	}

	private void initVersionParams(Context context) {
		// 初始化版本变量
		try {
			PackageInfo pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(),
					PackageManager.GET_CONFIGURATIONS);
			versionCode = pinfo.versionCode;
			versionName = pinfo.versionName;
		} catch (NameNotFoundException e) {
		}

	}

	// 版本号
	public int getVersionCode(Context context) {
		if (versionCode == 0) {
			initVersionParams(context);
		}
		return versionCode;
	}

	public String getVersionName(Context context) {
		if (Utils.isStringEmpty(versionName)) {
			initVersionParams(context);
		}
		return versionName;
	}

	public int getScreenWidth(Context context) {
		if (screenWidth == null) {
			initScreenParams(context);
			if (screenWidth == null) {
				return 0;
			}
		}
		return screenWidth;
	}

	public int getScreenHeight(Context context) {
		if (screenHeight == null) {
			initScreenParams(context);
			if (screenHeight == null) {
				return 0;
			}
		}
		return screenHeight;
	}

	public int getSideTextSize(Context context) {
		float ratioWidth = (float) getScreenWidth(context) / 720;
		float ratioHeight = (float) getScreenHeight(context) / 1280;

		float RATIO = Math.min(ratioWidth, ratioHeight);
		return Math.round(30 * RATIO);
	}

	public static String getFromAssets(Context context, String fileName) {
		String result = "";
		try {
			InputStream in = context.getResources().getAssets().open(fileName);
			// 获取文件的字节数
			int lenght = in.available();
			// 创建byte数组
			byte[] buffer = new byte[lenght];
			// 将文件中的数据读到byte数组中
			in.read(buffer);
			result = EncodingUtils.getString(buffer, "GBK");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
