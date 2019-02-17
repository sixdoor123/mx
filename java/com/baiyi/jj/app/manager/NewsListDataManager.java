package com.baiyi.jj.app.manager;

import android.content.Context;

import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.application.accont.PrefUtils;
import com.baiyi.jj.app.cache.bean.ConfigurationBean;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.entity.article.ArticleEntity;
import com.baiyi.jj.app.entity.localnews.LocationEntity;
import com.baiyi.jj.app.language.SwitchLanguageUtils;
import com.baiyi.jj.app.utils.ArticleHistoryUtils;
import com.baiyi.jj.app.utils.Installation;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.utils.XMLName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2017/1/11 0011.
 */
public class NewsListDataManager {

    public static final int Refresh_Type_Pull = 1;
    public static final int Refresh_Type_More = 2;
    public static final int Refresh_Type_New = 3;

    public static boolean isFY_FirstDayTimes() {
        // TODO Auto-generated method stub
        return PrefUtils.isFirstDayTimes(XMLName.XML_DAY_First_Times);
    }

    public static boolean isFY_FirstDayChannel(String channelid) {
        // TODO Auto-generated method stub
        return PrefUtils.isFirstDayTimes(XMLName.XML_DAY_First_Times + channelid);
    }

    public static boolean isLOADURL_FirstDayTimes() {
        // TODO Auto-generated method stub
        return PrefUtils.isFirstDayTimes(XMLName.XML_DAY_First_LOADURL);
    }

    public static String getUrl(boolean isMix) {
        if (isMix) {
            return NetUtils.getHomeNewsList(null);
        } else {
            return NetUtils.getNewsToChannelId(null);
        }
    }
    public static String getUrlV5() {
        return NetUtils.getHomeNewsListv5();
    }

    public static ArticleEntity getParseData(JSONArray array, boolean isMix, boolean isCache, String cid,boolean isVideoList,String curentArea) {
        if (isMix) {
            return JsonParse.getArticleEntity2(array, isCache, false,isVideoList);
        } else {
            return JsonParse.getArticleChannel(array, isCache, cid,curentArea);
        }
    }

    public static String getPostData(boolean isMix, List<ChannelItem> userChannelList, String channelId, String fidStr, boolean iswifi, boolean isVideo, Context context) {
        // TODO Auto-generated method stub
        if (isMix) {
            return getMixPostData(userChannelList, fidStr, iswifi,isVideo, context);
        } else {
            return getUnMixPostData(channelId, fidStr, iswifi, isVideo,context);
        }
    }

    private static String getMixPostData(List<ChannelItem> list, String fid, boolean iswifi,boolean isVideo, Context context) {
        JSONObject o = new JSONObject();
        try {
            o.put("channel_ids", getChannelList(list));
            o.put("fid", fid);
            o.put("mixed_channel", true);
            if (SwitchLanguageUtils.isHibyStr()) {
                o.put("lang", SwitchLanguageUtils.getCurrentLanguage());
            }
            if (isVideo){
                o.put("tab", "video");
            }
            o.put("network", iswifi ? "network_wifi" : "network_cellular");
            if (Utils.isStringEmpty(CmsApplication.getUserToken())){
                o.put("device_token",getSavedUUid(context));
            }
//            o.put("wifi",iswifi);
//            o.put("mix_channels", true);

        } catch (Exception e) {

        }
        TLog.e("mixpost", o.toString());
        return o.toString();
    }
    private static String getSavedUUid(Context context){
        ConfigurationBean bean = new ConfigurationBean();
        String inUUID = bean.getUuid();
        if (Utils.isStringEmpty(inUUID)){
            inUUID = Installation.getClientId(context);
        }
        return inUUID;
    }

    private static String getUnMixPostData(String channelid, String fid, boolean iswifi,boolean isVideo,  Context context) {
        JSONObject o = new JSONObject();
        if (Utils.isStringEmpty(channelid)) {
            return o.toString();
        }
        if (fid == null) {
            fid = "";
        }
        try {
            o.put("channel_ids", new JSONArray().put(Integer.parseInt(channelid)));
            o.put("fid", fid);
            o.put("mixed_channel", false);
            if (SwitchLanguageUtils.isHibyStr()) {
                o.put("lang", SwitchLanguageUtils.getCurrentLanguage());
            }
            if (isVideo){
                o.put("tab", "video");
            }
            o.put("network", iswifi ? "network_wifi" : "network_cellular");
            if (Utils.isStringEmpty(CmsApplication.getUserToken())){
                o.put("device_token",getSavedUUid(context));
            }
//            o.put("wifi",iswifi);
//            o.put("mix_channels", false);
        } catch (Exception e) {

        }
        TLog.e("unmixpost", o.toString());
        return o.toString();
    }

    private static JSONArray getChannelList(List<ChannelItem> channelItems) {
        JSONArray array = new JSONArray();
        if (Utils.isStringEmpty(channelItems)) {
            return array;
        }
        for (int i = 0; i < channelItems.size(); i++) {
            array.put(Integer.parseInt(channelItems.get(i).getCid()));
        }
        return array;
    }

    public static String getLocalPost(Context context,LocationEntity entity,String fid,boolean iswifi,String token) {
        JSONObject o = new JSONObject();
        if (fid == null) {
            fid = "";
        }
        try {
            o.put("city", Utils.isStringEmpty(entity.getCityName()) ? "" : entity.getCityName());
            o.put("fid", fid);
            o.put("administrativeArea",Utils.isStringEmpty(entity.getAreaName()) ? "" : entity.getAreaName());
            o.put("longitude", entity.getLongitude());
            o.put("latitude", entity.getLatitude());
            o.put("network", iswifi ? "network_wifi" : "network_cellular");
            o.put("device_token",getSavedUUid(context));
            o.put("token",token);
            o.put("country",Utils.isStringEmpty(entity.getCountryName()) ? "" : entity.getCountryName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        TLog.e("localpost", o.toString());
        return o.toString();
    }

}
