package com.baiyi.jj.app.manager;

import android.content.Context;
import android.util.Log;

import com.baiyi.core.file.Preference;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.BaseAnalyticsActivity;
import com.baiyi.jj.app.activity.user.login.UsInfoCallBack;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.application.accont.PrefUtils;
import com.baiyi.jj.app.cache.Dao.ChannelDao;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.language.SwitchLanguageUtils;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.TextLengthUtils;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.utils.XMLName;
import com.turbo.turbo.mexico.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2017/5/12 0012.
 */
public class ChannelDataManager {

    public static String ChannelType_News = "news";
    public static String ChannelType_Video = "video";


    public static ChannelDataManager instance;
    private ChannelDao channelDao;
    private int refreshNum = 0;
    private Context context;

    public static ChannelDataManager getInstance(Context context) {
        if (instance == null) {
            instance = new ChannelDataManager(context);
        }
        return instance;
    }

    public ChannelDataManager(Context context) {
        this.context = context;
        channelDao = new ChannelDao(context);
    }

    public void loadCacheChannel( String channelType, final ChannelResultCallBack callback) {

        List<ChannelItem> allList = channelDao.queryByType(channelType);
        Log.d("CHANNEL", channelType + "*************" + allList.size());
        if (Utils.isStringEmpty(allList)) {
            loadNetChannelList(channelType, callback);
            return;
        }

        setChannelListResult(allList,callback);
        int num = Preference.getInstance().getInt(XMLName.XML_Three_Start, 2);
//        if (PrefUtils.isFirstDayTimes(XMLName.XML_First_LoadChannel+channelType)) {
        if (num>2) {
            Log.d("CHANNEL","update channel day first");
            loadNetChannelList(channelType,null);
        }

    }
    private void setChannelListResult(List<ChannelItem> allList,ChannelResultCallBack callback) {
        List<ChannelItem> defaultUserChannels = new ArrayList<>();
        List<ChannelItem> defaultOtherChannels = new ArrayList<>();
        for (ChannelItem item : allList) {
            if (item.getIs_default().equals("true")) {
                defaultUserChannels.add(item);
            } else {
                defaultOtherChannels.add(item);
            }
        }
        if (callback != null) {
            callback.onResultCallBack(defaultUserChannels, defaultOtherChannels);
        }
    }

    public void updateSQLChannel(String channelType,ChannelResultCallBack resultCallBack){
        loadNetChannelList(channelType,resultCallBack);
    }



    private void deleteCacheByType(String channeType){
        channelDao.deleteByType(channeType);
    }


    public void updateNetChannel(final String channelType, List<ChannelItem> useList,List<ChannelItem> otheList, final ChannelSaveCallBack saveCallBack,String source){
        TLog.e("updatesource","-----------"+source);
        if (Utils.isStringEmpty(useList)){
            return;
        }
        saveChannel(useList,otheList,channelType);
        if (saveCallBack != null) {
            saveCallBack.onSaveCallBack(true, null);
        }
        if (channelType.equals(ChannelType_Video)){
            return;
        }
        if (Utils.isStringEmpty(CmsApplication.getUserToken())){
            TLog.e("nologin","save-----------null");
            return;
        }

        String channelIds[] = Utils.getStringToList(useList);
        JsonLoader loader = new JsonLoader(context);
        loader.setUrl(NetUtils.getUpdateChannel());
        loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
        loader.setType("application/json");
        loader.setMethod(BaseNetLoder.Method_Post);
        loader.setPostData(getUpdateData(channelIds));
        loader.setLoaderListener(new Loader.LoaderListener() {

            @Override
            public void onProgress(Object arg0, long arg1, long arg2) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onError(Object arg0, int arg1, String arg2) {
                if (saveCallBack != null) {
                    saveCallBack.onSaveCallBack(false, arg2);
                }
            }

            @Override
            public void onCompelete(Object arg0, Object arg1) {
                // TODO Auto-generated method stub
                try {
                    JSONArray array = (JSONArray) arg1;
                    TLog.e("update-channel",array.toString());
//                    if (saveCallBack != null) {
//                        saveCallBack.onSaveCallBack(true, null);
//                    }
                    updateSQLChannel(channelType,null);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        CmsApplication.getDataStratey().startLoader(loader);
    }


    public void loadNetChannelList(String channelType,ChannelResultCallBack resultCallBack){
        refreshNum = 0;
        loadNetChannelList2(channelType,resultCallBack);
    }

    private String getChannelUrl(String channelType){
        if (channelType.equals(ChannelType_News)){
            return NetUtils.getChannelListByUser();
        }else if (channelType.equals(ChannelType_Video)){
            return NetUtils.getVideoChannelListByUser();
        }else {
            return NetUtils.getChannelListByUser();
        }
    }

    private void loadNetChannelList2(final String channelType, final ChannelResultCallBack resultCallBack) {
        final long start = System.currentTimeMillis();
        JsonLoader loader = new JsonLoader(context);
        loader.setUrl(getChannelUrl(channelType));
        System.out.println(getChannelUrl(channelType));
        if (Utils.isStringEmpty(CmsApplication.getUserToken())){
            if (resultCallBack != null){
                resultCallBack.onResultCallBack(null,null);
            }
            return;
        }
        loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
        loader.setMethod(BaseNetLoder.Method_Get);
        loader.setLoaderListener(new Loader.LoaderListener() {

            @Override
            public void onProgress(Object arg0, long arg1, long arg2) {

            }

            @Override
            public void onError(Object arg0, int arg1, String arg2) {
                // TODO Auto-generated method stub
                if (resultCallBack != null){
                    resultCallBack.onResultCallBack(null, null);
                }

                if (arg1 == 401) {
                    new UsInfoCallBack(context, null).refreshToken(new UsInfoCallBack.RefreshTComplete() {
                        @Override
                        public void refreshComplete(boolean isSuccess, String token) {

                            if (isSuccess && refreshNum < 2) {
                                loadNetChannelList(channelType,resultCallBack);
                            } else {
                                ((BaseActivity) context).displayToast(context.getResources().getString(R.string.tip_loaddata_failure));
                                if (resultCallBack != null) {
                                    resultCallBack.onResultCallBack(null, null);
                                }
                            }
                            refreshNum++;
                        }
                    });
                    return;
                }
                ((BaseActivity) context).displayToast(context.getResources().getString(R.string.tip_loaddata_failure));
                if (resultCallBack != null) {
                    resultCallBack.onResultCallBack(null, null);
                }
            }

            @Override
            public void onCompelete(Object arg0, Object arg1) {
                long value = System.currentTimeMillis() - start;
                ((BaseActivity) context).addRequestTime(this.getClass().getName(), BaseAnalyticsActivity.Category_net, BaseAnalyticsActivity.Net_Get_Channel, value);
                // TODO Auto-generated method stub
                try {
                    JSONArray array = (JSONArray) arg1;
                    TLog.e("arraychannel--"+channelType, array.toString());
                    List<List<ChannelItem>> dataList = JsonParse.getChannelList2(array, channelType);
                    if (Utils.isStringEmpty(dataList)){
                        if (resultCallBack != null) {
                            resultCallBack.onResultCallBack(null, null);
                        }
                        return;
                    }
//                    ChannelDao channelDao = new ChannelDao(context);
//                    int chansub = channelDao.queryByTypeSub(channelType).size();
//                    int all = channelDao.queryForAll().size();
//                    int chan = channelDao.queryByType(channelType).size();
                    List<ChannelItem> defaultUserChannels = dataList.get(0);
                    List<ChannelItem> defaultOtherChannels = dataList.get(1);
                    saveChannel(defaultUserChannels,defaultOtherChannels,channelType);
                    if (resultCallBack != null) {
                        resultCallBack.onResultCallBack(defaultUserChannels, defaultOtherChannels);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        CmsApplication.getDataStratey().startLoader(loader);
    }

    private void saveChannel(List<ChannelItem> defaultUserChannels,List<ChannelItem> defaultOtherChannels,String channelType) {
        Comparator comp = new SortChannelComparator();
        Collections.sort(defaultUserChannels, comp);
        Collections.sort(defaultOtherChannels, comp);
        channelDao.deleteByType(channelType);
        channelDao.add(defaultUserChannels);
        channelDao.add(defaultOtherChannels);
    }

    public List<List<ChannelItem>> loadFirstChannel(ChannelResultCallBack callback,String channeType) {

        String channel;
        if (channeType.equals(ChannelType_Video)){
            channel = TextLengthUtils.getAssetsStr(context, "channel_init_video");
        }else {
            channel = TextLengthUtils.getAssetsStr(context, "channel_init");
        }
        if (Utils.isStringEmpty(channel)) {
            return null;
        }
        try {
            JSONArray array1 = new JSONArray(channel);
            List<List<ChannelItem>> dataList = JsonParse.getChannelList2(array1,channeType);
            List<ChannelItem> defaultUserChannels = dataList.get(0);
            List<ChannelItem> defaultOtherChannels = dataList.get(1);
            saveChannel(defaultUserChannels,defaultOtherChannels,channeType);
            if (callback != null){
                callback.onResultCallBack(defaultUserChannels,defaultOtherChannels);
            }
            TLog.e("firstload",channeType+"-----s----"+defaultUserChannels.size()+"==="+defaultOtherChannels.size());
            Comparator comp = new SortChannelComparator();
            Collections.sort(defaultUserChannels, comp);
            updateNetChannel(channeType,defaultUserChannels,defaultOtherChannels,null,"loadfirstchannel");
            return dataList;
        } catch (JSONException e) {
//            TLog.e("sss","eeeee---------");
            e.printStackTrace();
        }
        return null;
    }



    public String getUpdateData(String[] channelIds) {
        JSONObject o = new JSONObject();
        try {
            JSONArray array = new JSONArray();
            if (channelIds == null || channelIds.length == 0) {
                o.put("channel_id", new JSONArray());
                o.put("lang", SwitchLanguageUtils.getCurrentLanguage());
                return o.toString();
            }
            for (int i = 0; i < channelIds.length; i++) {
                array.put(Integer.parseInt(channelIds[i]));
            }
            o.put("channel_id", array);
            o.put("lang", SwitchLanguageUtils.getCurrentLanguage());

        } catch (JSONException e) {
        }
        Log.e("channel", o.toString());
        return o.toString();
    }

    public interface ChannelResultCallBack {
        public void onResultCallBack(List<ChannelItem> userChannelList, List<ChannelItem> otherChannelList);
    }

    public interface ChannelSaveCallBack {
        public void onSaveCallBack(boolean isComplete, String errorMsg);
    }

}
