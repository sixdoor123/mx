package com.baiyi.jj.app.manager;

import android.app.Notification;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.DownloadListener;

import com.baiyi.core.file.Preference;
import com.baiyi.core.loader.BaseLoader;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader;
import com.baiyi.core.loader.LoaderStrategy;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.BaseAnalyticsActivity;
import com.baiyi.jj.app.activity.main.task.NewsLoaderManager;
import com.baiyi.jj.app.activity.main.task.Task;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.cache.Dao.NewsListDao;
import com.baiyi.jj.app.cache.Dao.OfflineChannelDao;
import com.baiyi.jj.app.cache.Dao.WebDetailDao;
import com.baiyi.jj.app.cache.bean.OfflineChannelBean;
import com.baiyi.jj.app.cache.bean.WebDetailBean;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.entity.article.ArticleEntity;
import com.baiyi.jj.app.language.SwitchLanguageUtils;
import com.baiyi.jj.app.utils.ArticleHistoryUtils;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.utils.XMLName;
import com.turbo.turbo.mexico.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/18 0018.
 */
public class OfflineDownManager {

    public static OfflineDownManager instance;

    private DownNewsListener downloadListener;

    private Preference preference;
    private int pagenum = 0;
    private int lastnum = 0;
    private int pageindex = 0;
    private int alrdown = 0;
    private  String fidStr = "";
    private long downoadTime;
    private long downoadTime2;

    private List<NewsListEntity> newsListEntities;

    public static Executor executor;

    private  Context context;

    public OfflineDownManager() {
        preference = Preference.getInstance();
        fidStr = preference.Get(XMLName.XML_NewsFID, "");
    }

    public static OfflineDownManager getInstance()
    {
        if(instance == null)
        {
            instance = new OfflineDownManager();
        }
        return instance;
    }

    private int getOffLineSize(){
        int offline = AccountManager.getInstance().getOffline_Size();
        if (offline == 0){
            return 10;
        }else if (offline == 1){
            return 20;
        }else if (offline == 2){
            return 30;
        }else {
            return 10;
        }
    }

    private void saveFid() {
        if (preference != null) {
            preference.Set(XMLName.XML_NewsFID, fidStr);
            preference.saveConfig();
        }
    }

    public void downloadList(final Context context){
        this.context = context;
        int offsize = getOffLineSize();
        String language = SwitchLanguageUtils.getCurrentLanguage();
        List<OfflineChannelBean> list = new OfflineChannelDao(context).queryByOffline(true,language);

        fidStr = preference.Get(XMLName.XML_NewsFID, "");

        JsonLoader loader = new JsonLoader(context);
        loader.setMethod(BaseNetLoder.Method_Post);
        loader.setPostData(getMixPostData(list,fidStr,offsize,context));
        loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
        loader.setUrl(NetUtils.getHomeNewsList(null));
        loader.setLoaderListener(new Loader.LoaderListener() {

            public void onProgress(Object arg0, long arg1, long arg2) {

            }

            public void onError(Object arg0, int arg1, String arg2) {

            }

            public void onCompelete(Object arg0, Object arg1) {
                JSONArray array = (JSONArray) arg1;
                TLog.e("arrays",array.toString());
                ArticleEntity articleEntity =  JsonParse.getArticleEntity2(array, false,true,false);
                newsListEntities = articleEntity.toNewsList();
                if (articleEntity != null) {
                    fidStr = articleEntity.getFidStr();
                    saveFid();
                    if (Utils.isStringEmpty(newsListEntities)) {
                        Message message = new Message();
                        message.what = 4;
                        handler.sendMessage(message);
                        return;
                    }
                    downoadTime = newsListEntities.get(0).getDownLoadTime()-1;
                    downoadTime2 = newsListEntities.get(newsListEntities.size()-1).getDownLoadTime()+1;
                    TLog.e("ss",downoadTime+"=============="+downoadTime2);
                    saveCacheDataBase(newsListEntities);
                    TLog.e("size---","downsize=="+newsListEntities.size());
                    loadDetail();
                }else{
                    Message message = new Message();
                    message.what = 4;
                    handler.sendMessage(message);
                }
            }
        });

        CmsApplication.getDataStratey().startLoader(loader);
    }
    private void saveCacheDataBase(final List<NewsListEntity> newsList) {
        if (Utils.isStringEmpty(newsList)) {
            return;
        }
        new NewsListDao(context).add(newsList);
    }

    private void loadDetail(){

        mOkHttpClient = new OkHttpClient.Builder()
                .build();
        connectPool = mOkHttpClient.connectionPool();

//        WebViewManager.loadLocalDetail(null, context, articlid, new WebViewManager.LoadWebComplete() {
//            @Override
//            public void loadComplete(final WebDetailBean beans) {
//
//            }
//        });
        pagenum = (int)(newsListEntities.size()/5);
        lastnum = newsListEntities.size()%5;
        //  21   d=4  l=1 i=0
        load5Content(newsListEntities,pageindex);
    }

    private void load5Content(List<NewsListEntity> entityList,int index){
        TLog.e("num",index+"=========="+pagenum+"==========="+lastnum);
        if (index < pagenum){
            for (int i = 0;i<5;i++){
                NewsListEntity entity = entityList.get(index*5+i);
                loadLocaDetal(entity.getArticle_id(),index*5+i);
            }
        }else {
            for (int i = 0;i<lastnum;i++){
                NewsListEntity entity = entityList.get(index*5+i);
                loadLocaDetal(entity.getArticle_id(),index*5+i);
            }
        }

    }

    private OkHttpClient mOkHttpClient;
    private ConnectionPool connectPool;
    private void loadLocaDetal(final String articleid, final int downnumindex){
        if (Utils.isStringEmpty(articleid)){
            return;
        }
        Request request = new Request.Builder()
                .url(NetUtils.getDetailContent(articleid))
                .get()
                .build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
//                TLog.e(TAG+"str-0------",str);
                WebDetailBean bean = new WebDetailBean();
                bean.setArticleid(articleid);
                bean.setWebcontent(str);
                bean.setDownload(System.currentTimeMillis());
                new WebDetailDao(context).add(bean);
//                TLog.e("downcomplete",connectPool.connectionCount()+"============sss=="+downnumindex);
                if ((downnumindex+1)%5 == 0){
                    pageindex++;
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
//                    TLog.e("aaa","complete====================1 --"+pageindex);
                }
                if (downnumindex == newsListEntities.size()-1){
                    Message message = new Message();
                    message.what = 2;
                    handler.sendMessage(message);
                }

                Message message = new Message();
                message.what = 3;
                message.arg1 = downnumindex;
                handler.sendMessage(message);
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    load5Content(newsListEntities,pageindex);
                    break;
                case 2:
//                    if (downloadListener != null){
//                        downloadListener.downComplete(downoadTime,downoadTime2,newsListEntities.size());
//                    }
                    break;
                case 3:
                    alrdown++;
//                    TLog.e("index","----------------------------"+alrdown);
                    if (downloadListener != null){
                        float pro = (float)alrdown / (float) newsListEntities.size();
                        downloadListener.downProgress((int) (pro*100));
                    }
                    if (alrdown == newsListEntities.size()){
                        if (downloadListener != null){
                            downloadListener.downComplete(downoadTime,downoadTime2,newsListEntities.size());
                        }
                        initNummms();
                    }
                    break;
                case 4:
                    if (downloadListener != null){
                        downloadListener.downComplete(0,0,0);
                    }
                    break;
            }
        }
    };

    private void initNummms(){
        pagenum = 0;
        lastnum = 0;
        pageindex = 0;
        alrdown = 0;
    }

    private static String getMixPostData(List<OfflineChannelBean> list, String fid,int offsize,  Context context){
        JSONObject o = new JSONObject();
        try {
            o.put("channel_ids", getChannelList(list));
            o.put("fid", fid);
            o.put("mixed_channel", true);
            if (SwitchLanguageUtils.isHi(context)){
                o.put("lang", SwitchLanguageUtils.getCurrentLanguage());
            }
            o.put("size",offsize);
//            o.put("mode",2);
        }catch (Exception e){

        }
        TLog.e("postdown",o.toString());
        return o.toString();
    }

    private static JSONArray getChannelList(List<OfflineChannelBean> channelItems){
        JSONArray array = new JSONArray();
        if(Utils.isStringEmpty(channelItems))
        {
            return array;
        }
        for(int i = 0; i < channelItems.size(); i++)
        {
            array.put(Integer.parseInt(channelItems.get(i).getChannelid()));
        }
        return array;
    }

    public void setDownloadListener(DownNewsListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    public interface DownNewsListener{
        void downProgress(int progrss);
        void downComplete(long downloadtime,long downloadtime2,int downsize);
        void downFailure();
    }
}
