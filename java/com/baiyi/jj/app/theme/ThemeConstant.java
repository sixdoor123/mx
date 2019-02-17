package com.baiyi.jj.app.theme;

import java.io.File;

import android.content.Context;

import com.baiyi.core.file.Preference;

/**
 * 
 * @author tangkun
 *
 */
public class ThemeConstant {

	public static String SKIN_DIR = null;

	/**
	 * 创建皮肤文件夹 主目录
	 * @param context
	 */
	public static void initThemeDir(Context context) {

		SKIN_DIR = context.getFilesDir().getPath() + File.separator + "theme" + File.separator;
		File file = new File(SKIN_DIR);
		if (!file.exists()) {
			file.mkdirs();
		}

	}

	/**
	 * 设置皮肤
	 * @param context
	 * @param packAgeName
	 */
	public static void setAppTheme(Context context, String packAgeName) {
		Preference pref = Preference.getInstance();
		pref.Set("currnetskin", packAgeName);
		pref.saveConfig();

	}

	/**
	 * 获取皮肤
	 * @param context
	 * @return
	 */
	public static String getAppTheme(Context context) {
		return Preference.getInstance().Get("currnetskin", context.getPackageName());

	}

	/**
	 * 是否是内置皮肤
	 * @param context
	 * @return
	 */
	public static boolean isFromZip(Context context) {
		return Preference.getInstance().getBoolean("fromzip", false);
	}

	/**
	 * 内置皮肤
	 * @param context
	 * @param fromzip
	 */
	public static void setFromZip(Context context, boolean fromzip) {
		Preference pref = Preference.getInstance();
		pref.Set("fromzip", String.valueOf(fromzip));
		pref.saveConfig();
	}

}
