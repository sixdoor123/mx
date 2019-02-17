package com.baiyi.jj.app.application;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;

import com.baiyi.core.cache.Cache;
import com.baiyi.core.cache.MD5KeyMaker;
import com.baiyi.core.cache.NetFileCache;
import com.baiyi.core.file.NormalFileIO;
import com.baiyi.core.file.Preference;
import com.baiyi.core.file.SimpleNormalFileIO;
import com.baiyi.core.loader.AsynLoaderStrategy;
import com.baiyi.core.loader.LoaderStrategy;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.cache.Dao.UserDao;
import com.baiyi.jj.app.language.SwitchLanguageUtils;
import com.baiyi.jj.app.manager.InitService;
import com.baiyi.jj.app.utils.TLog;
import com.facebook.appevents.AppEventsLogger;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;
import com.tencent.bugly.crashreport.CrashReport;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.language.PreferenceUtil;
import com.baiyi.jj.app.theme.ThemeConstant;
import com.baiyi.jj.app.theme.ThemeManager;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.skin.loader.SkinManager;
import com.facebook.FacebookSdk;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class CmsApplication extends OneSignalApplication {

    private static CmsApplication instance = null;

    private static LoaderStrategy dataStratey = null;
    private static LoaderStrategy imageStrategy = null;

    private static Integer maxSize = null;
    private static Typeface titleType;
    private static Typeface titleTopType;
    private static Typeface detailContentType;
    public static Context context;

    // ע���û�
    public static UserInfoEntity user = null;
    // token
    public static String UserToken = null;
    // Refreshtoken
    public static String RefreshToken = null;

    private static Cache fileFeiYeNews = null;
    private static Cache fileFeiYeInformation = null;

    public boolean isFirstOpen = true;// �Զ������£��Ƿ��ǵ�һ�ν���ҳ��

    public static boolean isGprs = false;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        TLog.e("performInit begin:" + System.currentTimeMillis());
        instance = this;
        context = this;
        isGprs = ContextUtil.isGprs(context);
        PreferenceUtil.init(getApplicationContext());
        Preference.Init(new File(this.getFilesDir(), Config.XmlFileName));
//        InitService.start(context);

        //根据上次的语言设置，重新设置语言
        SwitchLanguageUtils.switchLanguage(getApplicationContext(),PreferenceUtil.getString(MemberConfig.Language, ""));
        FacebookSdk.sdkInitialize(getApplicationContext());


        /*
			第三个参数为SDK调试模式开关，调试模式的行为特性如下：
			● 输出详细的Bugly SDK的Log；
			● 每一条Crash都会被立即上报；
			● 自定义日志将会在Logcat中输出。
 			建议在测试阶段建议设置成true，发布时设置为false。
 			900059040（印度）   29afcd90ce（美国）  ed3cc34e66（墨西哥）
		 */
        CrashReport.initCrashReport(getApplicationContext(), "ed3cc34e66", false);
        TLog.e("performInit end:" + System.currentTimeMillis());

    }

    public static CmsApplication getInstance(){
        if(instance == null){
            instance = new CmsApplication();
        }
        return instance;
    }

    public static CmsApplication getApp() {
        return instance;
    }

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

    public static Typeface getDetailConten(Context context) {
        if (detailContentType == null) {
            detailContentType = Typeface.createFromAsset(context.getAssets(), "fonts/detail_regular.ttf");
        }
        return detailContentType;
    }

    public static Typeface getTitleFace(Context context) {
        if (titleType == null) {
            titleType = Typeface.createFromAsset(context.getAssets(), "fonts/title_k.ttf");
        }
        return titleType;
    }

    public static Typeface getListTitleFace(Context context) {
        if (titleTopType == null) {
            titleTopType = Typeface.createFromAsset(context.getAssets(), "fonts/newslist_medium.ttf");
        }
        return titleTopType;
    }

    public static void setUserInfoEntity(UserInfoEntity userInfoEntity,
                                         Context context) {
        user = userInfoEntity;
        if (userInfoEntity == null) {
            return;
        }
        TLog.e("login", "logintest--------" + userInfoEntity.getUserType());
        new UserDao(context).UpdateUserInfo(userInfoEntity, userInfoEntity.getUserType());
//		AccountManager.getInstance().setAccount(userInfoEntity.getAccount());
//		AccountManager.getInstance().setPwd(userInfoEntity.getPwd());
    }


    public static UserInfoEntity getUserInfoEntity() {
        return user;
    }

    public static void setUserToken(String usertoken) {
        if (Utils.isStringEmpty(usertoken))
            return;
        UserToken = usertoken;
    }

    public static String getUserToken() {
        return UserToken;
    }

    public static void setRefreshToken(String refreshToken) {
        if (Utils.isStringEmpty(refreshToken))
            return;
        RefreshToken = refreshToken;
    }

    public static String getRefreshToken() {
        return RefreshToken;
    }


    //��ҳ
    public static Cache getFileFeiYeNewsCache(Context context) {
        if (fileFeiYeNews == null) {
            String path = ContextUtil.getSDPath() + Config.FILE_FeiYe_Article;
            NormalFileIO fileIo = new SimpleNormalFileIO();
            try {
                fileFeiYeNews = new NetFileCache(path,
                        Config.MAX_DOWN_COVER_NO, fileIo, true);
                ((NetFileCache) fileFeiYeNews)
                        .setKeyMaker(new MD5KeyMaker());
            } catch (Exception e) {
                fileFeiYeNews = null;
            }
        }
        return fileFeiYeNews;
    }

    public static Cache getFileFeiYeInformationCache(Context context) {
        if (fileFeiYeInformation == null) {
            String path = ContextUtil.getSDPath() + Config.FILE_FeiYe_Information;
            NormalFileIO fileIo = new SimpleNormalFileIO();
            try {
                fileFeiYeInformation = new NetFileCache(path,
                        Config.MAX_DOWN_COVER_NO, fileIo, true);
                ((NetFileCache) fileFeiYeInformation)
                        .setKeyMaker(new MD5KeyMaker());
            } catch (Exception e) {
                fileFeiYeInformation = null;
            }
        }
        return fileFeiYeInformation;
    }


}
