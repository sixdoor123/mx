package com.baiyi.jj.app.activity.user.channel;

import android.content.Context;
import android.util.Log;

import com.baiyi.core.file.Preference;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.jj.app.activity.BaseAnalyticsActivity;
import com.baiyi.jj.app.activity.user.login.UsInfoCallBack;
import com.baiyi.jj.app.cache.Dao.ChannelDao;
import com.baiyi.jj.app.cache.Dao.ChannelTendDao;
import com.baiyi.jj.app.utils.TextLengthUtils;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.application.accont.PrefUtils;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.entity.article.ArticleChannel;
import com.baiyi.jj.app.utils.ArticleHistoryUtils;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.utils.XMLName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ChannelDataUtils {
    //�Ѽ�Ƶ��
    private List<ChannelItem> defaultUserChannels = new ArrayList<ChannelItem>();
    //δ��Ƶ��
    private List<ChannelItem> defaultOtherChannels = new ArrayList<ChannelItem>();

    private List<ChannelItem> addDefaultList = null;
    private List<ChannelItem> addOtherList = null;

    public static final String Key_Channel_Default = "KeyChannelDefault";
    public static final String Key_Channel_Other = "keyChannelOther";

    private static ChannelDataUtils instance = null;

//    private ChannelResultCallBack callback = null;

    public static final String Channel_All = "channel_alls";
    public static final String Channel_News = "channel_news";
    public static final String Channel_Read = "channel_read"; //�ڶ���
    //	public static final String Channel_Health = "channel_health"; //����
    public static final String Channel_Interest = "channel_interest"; // Ȥζ
    public static final String Channel_Video = "channel_video"; // Ȥζ
    public static final String Channel_Photo = "channel_photo"; // Ȥζ
    //	public static final String Channel_Discovery = "channel_discovery";
//	public static final String Channel_ShiJue = "channel_shijue"; //�Ӿ�
//	public static final String Channel_Sheying = "channel_sheying"; //��Ӱ
    private int refreshNum = 0;

    public static final int AllChannelNum = 20;

    private String channelType;

//    public ChannelDataUtils() {
//        defaultOtherChannels = new ArrayList<ChannelItem>();
//        defaultUserChannels = new ArrayList<ChannelItem>();
//        this.channelType = Channel_News;
//    }
//
//    public static ChannelDataUtils getInstance() {
//        if (instance == null) {
//            instance = new ChannelDataUtils();
//        }
//        return instance;
//    }
//
//    public static String getChannelType(String tablename) {
//        if (tablename.equals(ArticleHistoryUtils.Tablename_News)) {
//            return Channel_News;
//        } else if (tablename.equals(ArticleHistoryUtils.Tablename_Read)) {
//            return Channel_Read;
//        } else if (tablename.equals(ArticleHistoryUtils.Tablename_Interest)) {
//            return Channel_Interest;
//        } else if (tablename.equals(ArticleHistoryUtils.Tablename_Video)) {
//            return Channel_Video;
//        } else if (tablename.equals(ArticleHistoryUtils.Tablename_Photo)) {
//            return Channel_Photo;
//        }
//        return Channel_All;
//    }
//
//    public static String getChannelTable(String channelType) {
//        if (channelType.equals(Channel_News)) {
//            return "news";
//        } else if (channelType.equals(Channel_Read)) {
//            return ArticleHistoryUtils.Tablename_Read;
//        } else if (channelType.equals(Channel_Interest)) {
//            return ArticleHistoryUtils.Tablename_Interest;
//        } else if (channelType.equals(Channel_Video)) {
//            return ArticleHistoryUtils.Tablename_Video;
//        } else if (channelType.equals(Channel_Photo)) {
//            return ArticleHistoryUtils.Tablename_Photo;
//        }
//        return Channel_All;
//    }
//
//    public static String getTableName(String channelName) {
//        if (channelName.equals(Channel_News)) {
//            return (ArticleHistoryUtils.Tablename_News);
//        } else if (channelName.equals(Channel_Read)) {
//            return ArticleHistoryUtils.Tablename_Read;
//        } else if (channelName.equals(Channel_Interest)) {
//            return ArticleHistoryUtils.Tablename_Interest;
//        } else if (channelName.equals(Channel_Photo)) {
//            return ArticleHistoryUtils.Tablename_Photo;
//        }
//        return ArticleHistoryUtils.ArticleType_News;
//    }
//
//    public void deleteCache(final Context context, final String mid, final boolean isLoad, final ChannelSaveCallBack callback) {
//        new ChannelDao(context).deleteAll();
//        if (isLoad) {
//            loadNetChannelList(context, mid, callback);
//        }
//    }
//
//    /**
//     * ��ȡ��ǰ��ʾƵ��
//     *
//     * @param context
//     * @param callback
//     */
//    public void loadChannelList(final Context context, final ChannelResultCallBack callback,
//                                String channelType, final String mid, boolean isLoadNet) {
//        if (Utils.isStringEmpty(mid)) {
//            callback.onResultCallBack(null, null);
//            return;
//        }
//        this.channelType = channelType;
//        this.callback = callback;
//
//        if (isLoadNet) {
//            deleteCache(context, mid, true, null);
////            loadNetChannelList(context, mid,null);
//            return;
//        }
//
//        ChannelDao channelDao = new ChannelDao(context);
//        boolean isMid2Channel = channelDao.isChannelsToMid(mid);
//        TLog.e("ChannelDataUtils-isMid2Channel", "isMid2Channel:" + isMid2Channel);
//        if (isMid2Channel) {
//            loadNetChannelList(context, mid, null);
//            return;
//        }
////        if (PrefUtils.isFirstDayTimes(XMLName.XML_DAY_Channel_First_Times)) {
////            deleteCache(context, mid, true,null);
////            return;
////        }
//        List<ChannelItem> allList = channelDao.queryByTablename(channelType, mid);
//        Log.d("ATGD", channelType + "*************" + allList.size());
//        if (Utils.isStringEmpty(allList)) {
//            loadNetChannelList(context, mid, null);
//        } else {
//            setChannelList(allList);
//            if (PrefUtils.isFirstDayTimes(XMLName.XML_DAY_Channel_First_Times) &&
//                    Preference.getInstance().getBoolean(XMLName.XML_First_LoadChannel, false)) {
//                deleteCache(context, mid, true, null);
//                Preference.getInstance().Set(XMLName.XML_First_LoadChannel, String.valueOf(true));
//                Preference.getInstance().saveConfig();
//            }
//        }
//
//    }
//
//    private void setChannelList(List<ChannelItem> allList) {
//        defaultUserChannels.clear();
//        defaultOtherChannels.clear();
//        for (ChannelItem item : allList) {
//            if (item.getIs_default().equals("true")) {
//                defaultUserChannels.add(item);
//            } else {
//                defaultOtherChannels.add(item);
//            }
//        }
//        if (callback != null) {
//            callback.onResultCallBack(defaultUserChannels, defaultOtherChannels);
//        }
//
//    }
//
//    /**
//     * ����Ƶ���б�
//     *
//     * @param context
//     * @return
//     */
//    private void loadNetChannelList(final Context context, final String mid, final ChannelSaveCallBack saveCallBack) {
//        final long start = System.currentTimeMillis();
//        JsonLoader loader = new JsonLoader(context);
////        if (Utils.isStringEmpty(channelType)) {
////            loader.setUrl(NetUtils.getChannelList());
////        } else {
//        loader.setUrl(NetUtils.getChannelListByUser());
//        System.out.println(NetUtils.getChannelListByUser());
////        }
//        if (Utils.isStringEmpty(CmsApplication.getUserToken()))
//            return;
//        loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
//        loader.setMethod(BaseNetLoder.Method_Get);
//        loader.setLoaderListener(new LoaderListener() {
//
//            @Override
//            public void onProgress(Object arg0, long arg1, long arg2) {
//
//            }
//
//            @Override
//            public void onError(Object arg0, int arg1, String arg2) {
//                // TODO Auto-generated method stub
//                callback.onResultCallBack(null, null);
//
//                if (arg1 == 401) {
//                    new UsInfoCallBack(context, null).refreshToken(new UsInfoCallBack.RefreshTComplete() {
//                        @Override
//                        public void refreshComplete(boolean isSuccess, String token) {
//
//                            if (isSuccess && refreshNum < 2) {
//                                loadNetChannelList(context, mid, saveCallBack);
//                            } else {
//                                ((BaseActivity) context).displayToast(context.getResources().getString(R.string.tip_loaddata_failure));
//                                if (saveCallBack != null) {
//                                    saveCallBack.onSaveCallBack(false, null);
//                                }
//                            }
//                            refreshNum++;
//                        }
//                    });
//                    return;
//                }
//                ((BaseActivity) context).displayToast(context.getResources().getString(R.string.tip_loaddata_failure));
//                if (saveCallBack != null) {
//                    saveCallBack.onSaveCallBack(false, null);
//                }
//            }
//
//            @Override
//            public void onCompelete(Object arg0, Object arg1) {
//                long value = System.currentTimeMillis() - start;
//                ((BaseActivity) context).addRequestTime(this.getClass().getName(), BaseAnalyticsActivity.Category_net, BaseAnalyticsActivity.Net_Get_Channel, value);
//
//                // TODO Auto-generated method stub
//                JSONArray array = (JSONArray) arg1;
//                TLog.e("arraychannel", array.toString());
//                List<List<ChannelItem>> dataList = JsonParse.getChannelList(array, channelType, mid);
//                loadArticleChannel(context, dataList);
//                defaultUserChannels = dataList.get(0);
//                defaultOtherChannels = dataList.get(1);
//                Comparator comp = new SortComparator();
//                Collections.sort(defaultOtherChannels, comp);
//                Collections.sort(defaultUserChannels, comp);
//                saveDefaultDataBase(context);
//                saveOtherDataBase(context);
//                List<ChannelItem> allList = new ChannelDao(context).queryByTablename(channelType, mid);
//                if (null != callback) {
//                    callback.onResultCallBack(defaultUserChannels, defaultOtherChannels);
//                }
//                if (saveCallBack != null) {
//                    saveCallBack.onSaveCallBack(true, null);
//                }
//            }
//        });
//        CmsApplication.getDataStratey().startLoader(loader);
//    }
//
//
//    public class SortComparator implements Comparator {
//        @Override
//        public int compare(Object lhs, Object rhs) {
//            ChannelItem item = (ChannelItem) lhs;
//            ChannelItem item2 = (ChannelItem) rhs;
//
//
//            return (item2.getChannel_index() - item.getChannel_index());
//        }
//    }
//
//    public List<List<ChannelItem>> loadFirstChannel(Context context, String mid,ChannelResultCallBack callback) {
//        this.callback = callback;
//        String channel = TextLengthUtils.getAssetsStr(context, "channel_init");
//        if (Utils.isStringEmpty(channel)) {
//            return null;
//        }
//        try {
//            JSONArray array1 = new JSONArray(channel);
//            List<List<ChannelItem>> dataList = JsonParse.getChannelList(array1, ChannelDataUtils.Channel_News, mid);
//            defaultUserChannels = dataList.get(0);
//            defaultOtherChannels = dataList.get(1);
//            Comparator comp = new SortComparator();
//            Collections.sort(defaultOtherChannels, comp);
//            Collections.sort(defaultUserChannels, comp);
//            saveDefaultDataBase(context);
//            saveOtherDataBase(context);
//            String channelIds[] = Utils.getStringToList(defaultUserChannels);
//            updateNetChannel(context, mid,  channelIds, new ChannelSaveCallBack() {
//                @Override
//                public void onSaveCallBack(boolean isComplete, String errorMsg) {
//                    TLog.e("abcsss", "xiazaiss--------------------" + isComplete);
//                }
//            });
//            return dataList;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private void loadArticleChannel(Context context, List<List<ChannelItem>> dataList) {
//
//        List<ChannelItem> allList = new ArrayList<ChannelItem>();
//        allList.addAll(dataList.get(0));
//        allList.addAll(dataList.get(1));
//
//        List<ArticleChannel> ArticleChannelList = new ArrayList<ArticleChannel>();
//        for (ChannelItem item : allList) {
//            ArticleChannel ac = new ArticleChannel();
//            ac.setChannel_id(Utils.isStringEmpty(item.getCid()) ? -1 : Integer.parseInt(item.getCid()));
//            ac.setNew_index(0);
//            ac.setNew_version(0);
//            ac.setSub(item.getIs_default());
//            ac.setTrend_index(0);
//            ac.setTrend_version(0);
//            ac.setTableName(item.getChannel_type());
//            ac.setIsSub("false"); //��ʾ���û��Լ���Ƶ����true��ʱ���ǽӿڷ�������
//            ArticleChannelList.add(ac);
//        }
//        new ChannelTendDao(context).add(ArticleChannelList);
//    }
//
//    public void updateNetChannel(final Context context, final String mid, String[] channelIds, final ChannelSaveCallBack callback) {
//        JsonLoader loader = new JsonLoader(context);
//        loader.setUrl(NetUtils.getUpdateChannel());
//        loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
//        //Log.e("channel",CmsApplication.getUserToken());
//        loader.setType("application/json");
//        loader.setMethod(BaseNetLoder.Method_Post);
//        loader.setPostData(getUpdateData(channelIds));
//        loader.setLoaderListener(new LoaderListener() {
//
//            @Override
//            public void onProgress(Object arg0, long arg1, long arg2) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onError(Object arg0, int arg1, String arg2) {
//                if (callback != null) {
//                    callback.onSaveCallBack(false, arg2);
//                }
//            }
//
//            @Override
//            public void onCompelete(Object arg0, Object arg1) {
//                // TODO Auto-generated method stub
////				saveChannel(callback, defaultChannelList, otherChannelList);
//                JSONArray array = (JSONArray) arg1;
//                TLog.e("arrayup",array.toString());
//                if (callback != null) {
//                    defaultUserChannels.clear();
//                    defaultOtherChannels.clear();
////					callback.onSaveCallBack(true, null);
//                }
//                deleteCache(context, mid, true, callback);
//
//            }
//        });
//        CmsApplication.getDataStratey().startLoader(loader);
//    }
//
//    public String getUpdateData(String[] channelIds) {
//        JSONObject o = new JSONObject();
//        try {
//            JSONArray array = new JSONArray();
//            if (channelIds == null || channelIds.length == 0) {
//                o.put("channel_id", new JSONArray());
//                return o.toString();
//            }
//            for (int i = 0; i < channelIds.length; i++) {
//                array.put(Integer.parseInt(channelIds[i]));
//            }
//            o.put("channel_id", array);
//
//        } catch (JSONException e) {
//        }
//        Log.e("channel", o.toString());
//        return o.toString();
//    }
//
//    /**
//     * �������ݺ� �������ݿ�
//     *
//     * @param callback
//     */
//    public void saveChannel(final Context context, final String mid,final String channelIds[], final ChannelSaveCallBack callback,
//                            final List<ChannelItem> defaultChannelList, final List<ChannelItem> otherChannelList) {
//        addDefaultList = defaultChannelList;
//        addOtherList = otherChannelList;
//
//        new ChannelDao(context).deleteAll();
//        savaDataBase(context, mid, channelIds, callback, defaultChannelList, otherChannelList);
//
//    }
//
//    /**
//     * �������ݿ�
//     *
//     * @param callback
//     */
//    private void savaDataBase(final Context context, final String mid, final String[] channelIds, final ChannelSaveCallBack callback,
//                              final List<ChannelItem> defaultChannelList, final List<ChannelItem> otherChannelList) {
//        List<ChannelItem> dataList = new ArrayList<ChannelItem>();
//        dataList.addAll(addDefaultList);
//        dataList.addAll(addOtherList);
//
//        new ChannelDao(context).add(dataList);
//        if (callback != null) {
//            defaultUserChannels.clear();
//            defaultOtherChannels.clear();
//            callback.onSaveCallBack(true, null);
//        }
//        updateNetChannel(context, mid,channelIds,
//                callback);
//
//    }
//
//    private void saveDefaultDataBase(Context context) {
//        new ChannelDao(context).add(defaultUserChannels);
//    }
//
//    private void saveOtherDataBase(Context context) {
//        new ChannelDao(context).add(defaultOtherChannels);
//
//
//    }
//
//    public interface ChannelResultCallBack {
//        public void onResultCallBack(List<ChannelItem> userChannelList, List<ChannelItem> otherChannelList);
//    }
//
//    public interface ChannelSaveCallBack {
//        public void onSaveCallBack(boolean isComplete, String errorMsg);
//    }

}
