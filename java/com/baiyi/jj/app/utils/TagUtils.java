package com.baiyi.jj.app.utils;

import android.content.Context;

import java.util.List;

import com.baiyi.jj.app.language.SwitchLanguageUtils;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.theme.FontUtil;
import com.baiyi.jj.app.theme.ThemeUtil;


public class TagUtils {

    public static final int Tag_TOP = 1; // �ƹ�
    public static final int Tag_EDITION = 2; // �ȵ�
    public static final int Tag_TREND = 3; // �Ƽ�
    public static final int Tag_REALTIME = 4; // ����
    public static final int Tag_RECOMEND = 5; // new
    public static final int Tag_EXPLORE = 6; // new

    public static final String Tag_TOP_Str = "TOP"; // �ƹ�
//    public static final String Tag_EDITION_Str = "EDICIÓN"; // �ȵ�
    public static final String Tag_TREND_Str = "HOT"; // �Ƽ�
    public static final String Tag_REALTIME_Str = "NOTICIA"; // ����
    public static final String Tag_RECOMEND_Str = "SUGERENCIAS"; // new
    public static final String Tag_EXPLORE_Str = "EXPLORAR"; // new

    public static final String StorType_Real = "1";
    public static final String StorType_expro = "2";
    public static final String StorType_eidtion = "3";
    public static final String StorType_eidtion_top = "4";

    public static final String StorType_video = "6";
    public static final String StorType_videoJson = "3";


    public static String[] getTagStrByTagId(List<Integer> tags,Context context) {

        if (Utils.isStringEmpty(tags)) {
            return null;
        }
        String[] tagStrs = new String[tags.size()];
        for (int i = 0; i < tags.size(); i++) {
            if (tags.get(i) == Tag_TOP) {
                tagStrs[i] = Tag_TOP_Str;
            } else if (tags.get(i) == Tag_EDITION) {
                tagStrs[i] = context.getResources().getString(R.string.tip_tag_edition);
            } else if (tags.get(i) == Tag_EXPLORE) {
                tagStrs[i] = "";
            } else if (tags.get(i) == Tag_REALTIME) {
                tagStrs[i] = Tag_REALTIME_Str;
            } else if (tags.get(i) == Tag_RECOMEND) {
                tagStrs[i] = Tag_RECOMEND_Str;
            } else if (tags.get(i) == Tag_TREND) {
                tagStrs[i] = Tag_TREND_Str;
            } else {
                tagStrs[i] = "";
            }

        }
        return tagStrs;
    }

    public static int[] getTagStrColor(List<Integer> tags) {

        if (Utils.isStringEmpty(tags)) {
            return null;
        }
        int[] tagStrs = new int[tags.size()];
        for (int i = 0; i < tags.size(); i++) {
            if (tags.get(i) == Tag_TOP) {
                tagStrs[i] = R.color.day_tag_top;
//                tagStrs[i] = R.color.day_tag_news;
            } else if (tags.get(i) == Tag_EDITION) {
                tagStrs[i] = R.color.day_tag_edition;
//                tagStrs[i] = R.color.day_tag_news;
            } else if (tags.get(i) == Tag_EXPLORE) {
                tagStrs[i] = -1;
            } else if (tags.get(i) == Tag_REALTIME) {
                tagStrs[i] = R.color.day_tag_news;
            } else if (tags.get(i) == Tag_RECOMEND) {
                tagStrs[i] = R.color.day_tag_suggestion;
//                tagStrs[i] = R.color.day_tag_news;
            } else if (tags.get(i) == Tag_TREND) {
                tagStrs[i] = R.color.day_tag_top;
//                tagStrs[i] = R.color.day_tag_news;
            } else {
                tagStrs[i] = -1;
            }

        }
        return tagStrs;
    }

    /**
     *
     */
    public static int[] getNewsTagByTagId(List<Integer> tags, Context context) {
        if (Utils.isStringEmpty(tags)) {
            return null;
        }
        int[] resIds = new int[tags.size()];
//		for(Integer i : tags)
//		{
//			resIds[i] = getNewsViewId(i);
//		}

//        if (SwitchLanguageUtils.isHi(context)) {
//            for (int i = 0; i < resIds.length; i++) {
//                resIds[i] = getNewsViewId(tags.get(i));
//            }
//        } else {
//            for (int i = 0; i < resIds.length; i++) {
//                resIds[i] = getNewsViewIdEn(tags.get(i));
//            }
//        }
        return resIds;
    }

//    public static int getNewsViewId(int tagId) {
//        int resId;
//        if (tagId == Tag_TOP) {
//            resId = ThemeUtil.getAppTheme() == ThemeUtil.Theme_day ?
//                    R.drawable.label_top2x_en : R.drawable.label_top2x_en;
//        } else if (tagId == Tag_EDITION) {
//            resId = ThemeUtil.getAppTheme() == ThemeUtil.Theme_day ?
//                    R.drawable.label_edition2x_en : R.drawable.label_edition2x_en;
//        } else if (tagId == Tag_EXPLORE) {
////			resId = ThemeUtil.getAppTheme() == ThemeUtil.Theme_day ?
////					R.drawable.label_explore_en : R.drawable.label_explore_en;
//            return -1;
//        } else if (tagId == Tag_REALTIME) {
//            resId = ThemeUtil.getAppTheme() == ThemeUtil.Theme_day ?
//                    R.drawable.label_news2x_en : R.drawable.label_news2x_en;
//        } else if (tagId == Tag_RECOMEND) {
//            resId = ThemeUtil.getAppTheme() == ThemeUtil.Theme_day ?
//                    R.drawable.label_suggested2x_en : R.drawable.label_suggested2x_en;
//        } else if (tagId == Tag_TREND) {
//            resId = ThemeUtil.getAppTheme() == ThemeUtil.Theme_day ?
//                    R.drawable.label_hot2x_en : R.drawable.label_hot2x_en;
//        } else {
//            resId = -1;
//        }
//        return resId;
//    }

//    public static int getNewsViewIdEn(int tagId) {
//        int resId;
//        if (tagId == Tag_TOP) {
//            resId = ThemeUtil.getAppTheme() == ThemeUtil.Theme_day ?
//                    R.drawable.label_top2x_en : R.drawable.label_top2x_en;
//        } else if (tagId == Tag_EDITION) {
//            resId = ThemeUtil.getAppTheme() == ThemeUtil.Theme_day ?
//                    R.drawable.label_edition2x_en : R.drawable.label_edition2x_en;
//        } else if (tagId == Tag_EXPLORE) {
////			resId = ThemeUtil.getAppTheme() == ThemeUtil.Theme_day ?
////					R.drawable.label_explore_en : R.drawable.label_explore_en;
//            return -1;
//        } else if (tagId == Tag_REALTIME) {
//            resId = ThemeUtil.getAppTheme() == ThemeUtil.Theme_day ?
//                    R.drawable.label_news2x_en : R.drawable.label_news2x_en;
//        } else if (tagId == Tag_RECOMEND) {
//            resId = ThemeUtil.getAppTheme() == ThemeUtil.Theme_day ?
//                    R.drawable.label_suggested2x_en : R.drawable.label_suggested2x_en;
//        } else if (tagId == Tag_TREND) {
//            resId = ThemeUtil.getAppTheme() == ThemeUtil.Theme_day ?
//                    R.drawable.label_hot2x_en : R.drawable.label_hot2x_en;
//        } else {
//            resId = -1;
//        }
//        return resId;
//    }

    public static int getLine(int line) {
        int lines;
        if (line == 0) {
            lines = 0;
        } else if (line == 1) {
            lines = 5;
        } else if (line == 2) {
            lines = 7;
        } else {
            lines = 0;
        }
        return lines;
    }

    public static boolean getTagIs6(List<Integer> tags) {

        if (Utils.isStringEmpty(tags)) {
            return false;
        }
        for (int a : tags) {
            if (a == Tag_EXPLORE) {
                return true;
            }
        }
        return false;
    }
}
