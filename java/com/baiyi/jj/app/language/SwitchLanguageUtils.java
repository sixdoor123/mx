package com.baiyi.jj.app.language;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.turbo.turbo.mexico.R;

import java.util.Locale;

/**
 * Created by Administrator on 2016/9/24.
 */

public class SwitchLanguageUtils {

    public static final String local_en = "en";
    public static final String local_es = "es";

    public static final String coutry_US = "US";
    public static final String coutry_MX = "MX";

    public static final int languageid_EN = 0;
    public static final int languageid_IN = 4;
    public static final int languageid_ES = 5;


    public static void switchLanguage(Context context, String language) {

        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        if (Utils.isStringEmpty(language)) {
            language = local_es;
        }
        if (language.equals(local_es)) {
            config.locale = new Locale(local_es, coutry_MX);
        } else {
            config.locale = new Locale(local_en, coutry_US);
        }
        resources.updateConfiguration(config, dm);
        SwitchLanguageUtils.saveLanguage(language);
    }

    public static boolean isHi(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith(local_es))
            return true;
        else
            return false;
    }

    public static boolean isHibyStr() {
        String language = getCurrentLanguage();
        if (language.endsWith(local_es))
            return true;
        else
            return false;
    }

    public static boolean isMatched(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String syslanguage = locale.getLanguage();
        String currentlanguage = getCurrentLanguage();
//        TLog.e("sys",syslanguage+"========");
//        TLog.e("cur",currentlanguage+"========");
        return syslanguage.equals(currentlanguage);
    }

    public static void saveLanguage(String language) {
        PreferenceUtil.commitString(MemberConfig.Language, language);
    }

    public static String getCurrentLanguage() {
        return PreferenceUtil.getString(MemberConfig.Language, local_es);
    }
    public static String getCurrentLanguageStr() {
        String language = PreferenceUtil.getString(MemberConfig.Language, local_es);
        if (language.equals(local_es)) {
            return coutry_MX;
        } else {
            return coutry_US;
        }
    }

    public static void selectLanguage(Context context, String language) {
        if (context.getString(R.string.txt_language_hi).equals(language)) {
            SwitchLanguageUtils.saveLanguage(local_es);
        } else {
            SwitchLanguageUtils.saveLanguage(local_en);
        }
        switchLanguage(context.getApplicationContext(), PreferenceUtil.getString(MemberConfig.Language, local_es));
    }

    public static void selectLanguagebyName(Context context, String language) {
        if (local_es.equals(language)) {
            SwitchLanguageUtils.saveLanguage(local_es);
        } else {
            SwitchLanguageUtils.saveLanguage(local_en);
        }
        switchLanguage(context.getApplicationContext(), PreferenceUtil.getString(MemberConfig.Language, local_es));
    }
}
