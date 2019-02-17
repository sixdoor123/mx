package com.baiyi.cmall.application;

import java.io.File;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import com.baiyi.cmall.Config;
import com.baiyi.cmall.application.AppForegroundStateManager.AppForegroundState;
import com.baiyi.cmall.application.AppForegroundStateManager.OnAppForegroundStateChangeListener;
import com.baiyi.cmall.entity.UserInfoEntity;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.utils.XmlName;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.core.database.manager.SimpleSQLDataBaseManager;
import com.baiyi.core.file.Preference;
import com.baiyi.core.loader.AsynLoaderStrategy;
import com.baiyi.core.loader.LoaderStrategy;
import com.baiyi.cmall.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class CmallApplication extends UserApplication implements
		OnAppForegroundStateChangeListener {

	private static CmallApplication instance = null;

	private static LoaderStrategy dataStratey = null;
	private static LoaderStrategy imageStrategy = null;

	private static Integer maxSize = null;
	public static Context context;
	public static UserInfoEntity user = null;

	@Override
	public void onCreate() {

		super.onCreate();
		instance = this;
		context = this;
		Preference.Init(new File(this.getFilesDir(), Config.XmlFileName));
		initORM();
		AppForegroundStateManager.getInstance().addListener(this);
		initImageLoader2(getApplicationContext());
	}

	private void initORM() {
		SimpleSQLDataBaseManager manager = SimpleSQLDataBaseManager
				.getInstance();
		String[] beans = this.getResources().getStringArray(
				R.array.single_db_beans);
		int[] versions = this.getResources().getIntArray(
				R.array.single_db_version);
		manager.init(this, Utils.getDataPath(this), beans, versions);
	}

	public static CmallApplication getApp() {
		return instance;
	}

	public void changTheme() {
		// ZipUtil.unZip(getResources().openRawResource(),
		// ThemeConstant.SKIN_DIR+"shandian.zip", ThemeConstant.SKIN_DIR);
		// ThemeConstant.setAppSkin(this, getPackageName());
		// ThemeConstant.setFromZip(this, true);
	}

	public static void setUserInfoEntity(UserInfoEntity userInfoEntity,
			Context context) {
		user = userInfoEntity;
		if (userInfoEntity == null) {
			return;
		}
		Preference preference = Preference.getInstance();
		if (null != preference) {
			preference.Set(XmlName.NAME_user_account,
					userInfoEntity.getAccount());
			preference.Set(XmlName.NAME_user_mm, userInfoEntity.getPwd());
			preference.Set(XmlName.NAME_user_MID, userInfoEntity.getMID());
			preference.saveConfig();
		}
		Preference.getInstance().Set(XmlName.NAME_user_account,
				userInfoEntity.getAccount());
		Preference.getInstance().Set(XmlName.NAME_user_mm,
				userInfoEntity.getPwd());
		Preference.getInstance().saveConfig();
	}

	public static UserInfoEntity getUserInfoEntity() {
		if (null == user) {
			user = new UserInfoEntity();
			Preference preference = Preference.getInstance();
			if (null != preference) {
				user.setMID(preference.Get(XmlName.NAME_user_MID));
				user.setAccount(preference.Get(XmlName.NAME_user_account));
				user.setPwd(preference.Get(XmlName.NAME_user_mm));
			}
		}
		return user;
	}

	public static void setNews(Context context, int multiMaxID,
			int userUnReadCount) {
		if (user == null) {
			return;
		}
		int tempId = Preference.getInstance().getInt(
				XmlName.NAME_MultiMaxID + getUserInfoEntity().getMID(), -1);
		int multiId = 0;
		if (tempId != -1 && multiMaxID > 0) {
			multiId = multiMaxID - tempId;
		} else {
			multiId = multiMaxID;
		}
		user.setMultiMaxID(multiId);
		user.setUserUnReadCount(userUnReadCount);
		setUserInfoEntity(user, context);
	}

	public static void clearUserNews() {
		if (user == null) {
			return;
		}
		user.setUserUnReadCount(0);
		setUserInfoEntity(user, context);
	}

	public static void clearSystemNews() {
		if (user == null) {
			return;
		}
		user.setMultiMaxID(0);
		setUserInfoEntity(user, context);
	}

	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				
				break;

			default:
				break;
			}
		};
	};
	public static LoaderStrategy getDataStratey() {
		if (dataStratey == null) {
			dataStratey = new AsynLoaderStrategy(3, "ds", Thread.NORM_PRIORITY);
		}
		return dataStratey;
	}

	public static LoaderStrategy getImageStrategy() {
		if (imageStrategy == null) {
			if (getMaxSize() == 16) {
				imageStrategy = new AsynLoaderStrategy(1, "is",
						Thread.NORM_PRIORITY);
			} else {
				imageStrategy = new AsynLoaderStrategy(6, "is",
						Thread.NORM_PRIORITY);
			}
		}
		return imageStrategy;
	}

	public static int getMaxSize() {
		if (maxSize == null) {
			ActivityManager activityManager = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			maxSize = activityManager.getMemoryClass();
		}
		return maxSize;
	}

	/**
	 * 
	 */
	@Override
	public void onAppForegroundStateChange(AppForegroundState newState) {
		XmlUtils utils = XmlUtils.getInstence(this);
		boolean b = utils.delete();
	}
	
	public static void initImageLoader2(Context context) {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
				.considerExifParams(true)
				.displayer(new FadeInBitmapDisplayer(300, true, true, true))
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
				context).defaultDisplayImageOptions(defaultOptions)
				.memoryCache(new WeakMemoryCache());
		ImageLoaderConfiguration config = builder.build();
		ImageLoader.getInstance().init(config);
	}
	
	
}
