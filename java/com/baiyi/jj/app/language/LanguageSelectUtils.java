package com.baiyi.jj.app.language;

import android.app.Activity;
import android.content.Context;

import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.activity.user.entity.LanguageEntities;
import com.baiyi.jj.app.utils.Utils;
import com.turbo.turbo.mexico.R;

import java.util.List;

/**
 * Created by Administrator on 2016/9/18.
 */

public class LanguageSelectUtils {

    public static String getLocaleLanguage(String locale, Context context) {
        if (SwitchLanguageUtils.local_en.equalsIgnoreCase(locale))
            return context.getString(R.string.txt_language_en);//
        if (SwitchLanguageUtils.local_es.equalsIgnoreCase(locale))
            return context.getString(R.string.txt_language_hi);//
        return context.getString(R.string.txt_language_en);
    }

    public static LanguageEntities getCurrentLanguageEntities(List<LanguageEntities> entities, Context context) {

        String currentLanguage = getLocaleLanguage(SwitchLanguageUtils.getCurrentLanguage(),context);

        for (LanguageEntities e : entities) {
            if (currentLanguage.equals(e.getName())) {
                return e;
            }
        }
        return null;
    }

    public static LanguageEntities getCurrentCountryEntities( List<LanguageEntities> entities, String currentCountry) {

        if (Utils.isStringEmpty(entities)) {
            return null;
        }

        if (Utils.isStringEmpty(currentCountry)) {
            currentCountry = PreferenceUtil.getString(MemberConfig.App_Country, "");
        }

        if (Utils.isStringEmpty(currentCountry)) {
            return null;
        }

        for (LanguageEntities e : entities) {
            if (currentCountry.equals(e.getName())) {
                return e;
            }
        }

        return null;
    }

    public static int getCurrentId(LanguageEntities currentLanguage, Activity activity) {
        if (currentLanguage != null) {
            if (Utils.isStringEmpty(currentLanguage.getName())) {
                return -1;
            }
        } else {
            return -1;
        }
        return Integer.parseInt(currentLanguage.getId());
    }
}
