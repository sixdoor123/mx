package com.baiyi.jj.app;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.listitem.news.ListItemBaseNews;
import com.baiyi.jj.app.manager.CountryMannager;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.RemoteImageView;
import com.turbo.turbo.mexico.R;

import org.apache.http.util.EncodingUtils;

import java.io.InputStream;
import java.lang.reflect.Method;

public class Config {

    public static boolean isRelease = true;

    private Integer screenWidth;
    private Integer screenHeight;

    private static Config instance = null;

    public static final int DB_VERSION = 1;

    private int versionCode;
    private String versionName;


    public static final String Root_File_Path = "com.cms.jjen";
    public static final String NEW_URL_US = "https://turboapp.xyz/";
    public static final String NEW_URL_India = "http://in.turboapp.xyz/";
    public static final String NEW_URL_MX = "http://api.mx.turboapp.xyz:8080/";
    public static final String Detal_URL_US = "https://stories.cdn.turboapp.xyz";
    public static final String Detal_URL_India = "http://stories.cdn.in.turboapp.xyz";
    public static final String NEW_URL_Test = "http://10.20.22.103:8000/";

    public static final String HostName = "turboapp.xyz";
    //	public static final String CHAT_SERVER_URL_US = "http://52.54.41.253:3000";
//	public static final String CHAT_SERVER_URL_IN = "http://52.220.204.85:3000";
    public static final String CHAT_SERVER_URL_US = "http://turboapp.xyz:3000";
    public static final String CHAT_SERVER_URL_IN = "http://in.turboapp.xyz:3000";
    public static final String CHAT_SERVER_URL_MX = "http://in.turboapp.xyz:3000";
    public static final String CHAT_SERVER_URL_Test = "http://10.20.22.211:3000";

    public static final String IMAGE_CACHE_PATH = "/" + Root_File_Path + "/files/coverimgs/";
    public static final String CACHE_FILE_CHANNEL_PATH = "/" + Root_File_Path + "/files/channel/";
    public static final String FILE_CACHE_USER_PK = "/" + Root_File_Path + "/files/userpk/";
    public static final String FILE_CACHE_USER_TK = "/" + Root_File_Path + "/files/usertk/";
    public static final String FILE_NEWS_HISTORY = "/" + Root_File_Path + "/files/history/news/";
    public static final String FILE_OTHER_HISTORY = "/" + Root_File_Path + "/files/history/ohter/";
    public static final String IMAGE_CACHE_HEAD_PATH = "/" + Root_File_Path + "/imgs/head/";
    public static final String IMAGE_CACHE_AD_PATH = "/" + Root_File_Path + "/imgs/ads/";

    public static final String FILE_NEWS_Article = "/" + Root_File_Path + "/files/news/article/";
    public static final String FILE_NEWS_Information = "/" + Root_File_Path + "/files/news/information/";
    public static final String FILE_NEWS_Interest = "/" + Root_File_Path + "/files/news/interest/";
    public static final String FILE_NEWS_Photo = "/" + Root_File_Path + "/files/news/photo/";
    public static final String FILE_NEWS_Video = "/" + Root_File_Path + "/files/news/video/";
    public static final String FILE_NEWS_Sheying = "/" + Root_File_Path + "/files/news/sheying/";

    public static final String FILE_FeiYe_Article = "/" + Root_File_Path + "/files/feiye/article/";
    public static final String FILE_FeiYe_Information = "/" + Root_File_Path + "/files/feiye/information/";
    public static final String FILE_FeiYe_Interest = "/" + Root_File_Path + "/files/feiye/interest/";
    public static final String FILE_FeiYe_Photo = "/" + Root_File_Path + "/files/feiye/photo/";

    public static final String FILE_SKIN_PATH = "/" + Root_File_Path + "/files/skin/";
    public static final String FILE_SKIN_NAME = "JJSkinRes.skin";


    public static final int IMAGE_AD_COUNT = 5;
    public static final int IMAGE_CACHE_COUNT = 100;
    public static final int LIST_ITEM_COUNT = 10;
    public static final int MAX_DOWN_COVER_NO = Integer.MAX_VALUE;

    public static final String XmlFileName = "Cms365";

    public static final String IMG_FORMAT = "JPG";

    //Ƥ�����汾
    public static final String Skin_Version = "skin_version";

    public static final String LOGO_IMAGEURL = "http://119.254.98.65:8001/content/img/app/ico80.png";
    public static final String LOGO_LumenUrl = "https://lumen.site/";
    // ���������ύ URL

    public static String getNewURL() {
        CountryMannager mannager = CountryMannager.getInstance();
        if (mannager.getCurrentCountry() == CountryMannager.Country_India) {
            return NEW_URL_India;
        } else if (mannager.getCurrentCountry() == CountryMannager.Country_Mexico) {
            return NEW_URL_MX;
        } else if (mannager.getCurrentCountry() == CountryMannager.Country_Test) {
            return NEW_URL_Test;
        } else {
            return NEW_URL_US;
        }
    }

    public static String getDetailUrl() {
        CountryMannager mannager = CountryMannager.getInstance();
        if (mannager.getCurrentCountry() == CountryMannager.Country_India) {
            return Detal_URL_India;
        } else if (mannager.getCurrentCountry() == CountryMannager.Country_Test) {
            return Detal_URL_India;
        } else {
            return Detal_URL_India;
        }
    }

    private Config() {
    }

    public static Config getInstance() {
        if (instance == null)
            instance = new Config();
        return instance;
    }

    public void initScreenParams(Context context) {
        // ��ʼ����Ļ����
        Display display = ((WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        screenWidth = display.getWidth();
        screenHeight = display.getHeight();
    }

    private void initVersionParams(Context context) {
        // ��ʼ���汾����
        try {
            PackageInfo pinfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(),
                            PackageManager.GET_CONFIGURATIONS);
            versionCode = pinfo.versionCode;
            versionName = pinfo.versionName;
        } catch (NameNotFoundException e) {

        }

    }

    // �汾��
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

    public int getHasVirtualKey(Activity context) {
        int dpi = 0;
        Display display = context.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            dpi = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
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
            // ��ȡ�ļ����ֽ���
            int lenght = in.available();
            // ����byte����
            byte[] buffer = new byte[lenght];
            // ���ļ��е����ݶ���byte������
            in.read(buffer);
            result = EncodingUtils.getString(buffer, "GBK");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取设备appId
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        return android.provider.Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 按比例设置gif图片的高度
     *
     * @param context
     * @param imgInfo
     * @param view
     */
    public void setGifH(Context context, String imgInfo, ImageView view) {
        try {
            float w = Float.parseFloat(imgInfo.substring(imgInfo.indexOf("width=") + 6, imgInfo.indexOf("&")));
            float h = Float.parseFloat(imgInfo.substring(imgInfo.indexOf("height=") + 7, imgInfo.length()));

            int imgW = getScreenWidth(context);
            int imgH = (int) (h / w > 1.6 ? imgW * 1.6 : imgW * h / w);
            view.getLayoutParams().height = imgH;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setGifH_padding10(Context context, String imgInfo, ImageView view) {
        try {
            float w;
            float h;
            int imgW = getScreenWidth(context) - ContextUtil.dip2px(context, ListItemBaseNews.ThreeImg_Dip_Offset);
            if (!imgInfo.contains("width=")) {
                String wthstr = imgInfo.substring(imgInfo.lastIndexOf("/") + 1, imgInfo.length());
                if (wthstr.contains("x")) {
                    String[] wss = Utils.split(wthstr, "x");
                    String with = wss[0];
                    String heght = wss[1];
                    w = Float.parseFloat(with);
                    h = Float.parseFloat(heght);
                } else {
                    w = imgW;
                    h = (int) (w*0.48);
                }
            } else {
                w = Float.parseFloat(imgInfo.substring(imgInfo.indexOf("width=") + 6, imgInfo.indexOf("&")));
                h = Float.parseFloat(imgInfo.substring(imgInfo.indexOf("height=") + 7, imgInfo.length()));
            }
            int imgH = (int) (h / w > 1.6 ? imgW * 1.6 : imgW * h / w);
            view.getLayoutParams().height = imgH;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getShareUrl(String articleId) {
        return String.format("http://s.mx.lumen.today/share/article/%s/", articleId);
    }
}
