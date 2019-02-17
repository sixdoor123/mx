/**
 * 
 */
package com.baiyi.jj.app.theme;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.baiyi.core.file.Preference;
import com.baiyi.core.util.ContextUtil;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.baiyi.jj.skin.listener.ILoaderListener;
import com.baiyi.jj.skin.loader.SkinManager;

import java.io.File;
import java.util.Calendar;

/**
 * @author tangkun
 *
 */
public class ThemeUtil {

	public static final int Theme_day = 0;
	public static final int Theme_night = 1;
	public static final String nightChangenDay = "nightChangenDay";
	public static final String nightChangenHour = "nightChangenHour";
	
	/**
	 * ��ȡƤ��
	 * @return
	 */
	public static int getAppTheme() {
		return SkinManager.getInstance().isExternalSkin() ? 1 : 0;
	}
	
	public static String getAppThemeStr() {
		return SkinManager.getInstance().isExternalSkin() ? "night" : "daylight";
	}
	
	public static int getAppThemeBgImg() {
		return /**SkinManager.getInstance().isExternalSkin() ? R.drawable.bj_place_10 :*/ R.drawable.bj_place_10;
	}
	
	/**
	 * �л�Ƥ��-ҹ��ģʽ
	 */
	public static void onChangeSkin(final Context context, final MyLoadingBar progressBar) {
		
		File skin = new File(ContextUtil.getSDPath() + Config.FILE_SKIN_PATH + Config.FILE_SKIN_NAME);

		SkinManager.getInstance().load(skin.getAbsolutePath(),
				new ILoaderListener() {
					@Override
					public void onStart() {
						if (progressBar != null) {
							progressBar.start();
							progressBar.setVisibility(View.VISIBLE);
							progressBar.setProgressInfo(context.getResources().getString(R.string.txt_progress_loading4));
						}
					}

					@Override
					public void onSuccess() {
//						Toast.makeText(CmsApplication.context, "�л��ɹ�", Toast.LENGTH_SHORT).show();
						if (progressBar != null) {
							progressBar.setVisibility(View.GONE);
							progressBar.stop();
						}
						saveNightChanger();
					}

					@Override
					public void onFailed() {
						if (progressBar != null) {
							progressBar.setVisibility(View.GONE);
							progressBar.stop();
						}
					}
				});
	}
	
	
	private static void saveNightChanger() {
		Calendar ca = Calendar.getInstance();
		int hour = ca.get(Calendar.HOUR_OF_DAY);
		int day = ca.get(Calendar.DAY_OF_YEAR);
		Preference.getInstance().Set(nightChangenDay, String.valueOf(day));
		Preference.getInstance().Set(nightChangenHour, String.valueOf(hour));
		Preference.getInstance().saveConfig();
	}

	/**
	 * Ĭ��Ƥ��
	 */
	public static void onResetSkin() {
		SkinManager.getInstance().restoreDefaultTheme();
	}

}
