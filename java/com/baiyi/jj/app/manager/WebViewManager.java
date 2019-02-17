package com.baiyi.jj.app.manager;

import android.content.Context;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.main.task.WebLoadManager;
import com.baiyi.jj.app.activity.main.task.WebTask;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.cache.Dao.WebDetailDao;
import com.baiyi.jj.app.cache.bean.WebDetailBean;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.pageview.JsonParse_Collect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/2/16 0016.
 */
public class WebViewManager {

    // ��ȡ img ��ǩ����
    private static final String IMAGE_URL_TAG = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
    // ��ȡ src ·��������
    private static final String IMAGE_URL_CONTENT = "src\\s*=\\s*\"?(.*?)(\"|>|\\s+)";

    private static String TAG = "WebViewManager";

    public static void parseHTML(WebView view){
        view.loadUrl("javascript:window.local_obj.showSource(document.getElementsByTagName('html')[0].innerHTML);");
    }
    public static void addImageClickListener(WebView view) {
        view.loadUrl("javascript:(function(){"
                + "var objs = document.getElementsByTagName(\"img\"); "
                + "for(var i=0;i<objs.length;i++)  " + "{"
                + "    objs[i].onclick=function()  " + "    {  "
                + "        window.imageListener.openImage(this.src);  "
                + "    }  " + "}" + "})()");
    }

    public static void getImageList(WebView view){
        view.loadUrl("javascript:(function(){"
                + "   var img = all_images; "
                + "        window.imageListener.getImages(img);  "
                + " })()");
    }

    public static int getSrcIndex(List<String> stringList,String url){
        int index = 0;
        for (int i = 0;i<stringList.size();i++){
            String strUrl = stringList.get(i);
            if (strUrl.equals(url)){
                index = i;
                break;
            }
        }
        return index;
    }

    public static List<String> getAllImageUrlFromHtml(String html){
        List<String> listImage = new ArrayList<>();
        Matcher matcher = Pattern.compile(IMAGE_URL_TAG).matcher(html);
        while (matcher.find()){
            listImage.add(matcher.group());
//            TLog.e("img--",matcher.group());
        }

        getAllImageUrlFromSrc(listImage);
        return listImage;
    }
    public static List<String> getAllImageUrlFromSrc(List<String> list){
        List<String> listSrc = new ArrayList<>();
        for (String url :list){
            Matcher matcher = Pattern.compile(IMAGE_URL_CONTENT).matcher(url);
            while (matcher.find()){
                String srcStr = matcher.group();
                String src = srcStr.substring(srcStr.indexOf("=")+2,srcStr.length()-1);
                listSrc.add(src);
//                TLog.e(TAG,matcher.group());
            }
        }
        return listSrc;
    }

    public static void loadLocalDetail(final WebLoadManager manager, final Context context, final String articleid, final LoadWebComplete loadComplete) {


//        TLog.e(TAG,"start------"+articleid+"------"+System.currentTimeMillis());
        if (Utils.isStringEmpty(articleid)){
            return;
        }
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .url(NetUtils.getDetailContent(articleid))
                .get()
                .build();
        ConnectionPool pool = mOkHttpClient.connectionPool();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                if (manager != null){
//                    manager.removeTask(articleid);
//                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                TLog.e(TAG,"stop--------"+System.currentTimeMillis());
                String str = response.body().string();
//                TLog.e(TAG+"str-0------",str);
                WebDetailBean bean = new WebDetailBean();
                bean.setArticleid(articleid);
                bean.setWebcontent(str);
                bean.setDownload(System.currentTimeMillis());
                new WebDetailDao(context).add(bean);

                if (loadComplete != null){
                    loadComplete.loadComplete(bean);
                }
            }
        });

        if (manager != null){
            WebTask task = new WebTask();
            task.setCall(call);
            task.setTag(articleid);
            manager.addTask(task);
        }
    }



    public static void loadWebUrlStr(final WebLoadManager manager, final Context context, String Url, final String articleid, final LoadWebComplete loadComplete) {


//        TLog.e(TAG,"start------"+articleid+"------"+System.currentTimeMillis());
        if (Utils.isStringEmpty(articleid)){
            return;
        }
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .url(Url)
                .get()
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                if (manager != null){
//                    manager.removeTask(articleid);
//                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                TLog.e(TAG,"stop--------"+System.currentTimeMillis());
                String str = response.body().string();
//                TLog.e(TAG+"str-0------",str);
                WebDetailBean bean = new WebDetailBean();
                bean.setArticleid(articleid);
                bean.setWebcontent(str);
                bean.setDownload(System.currentTimeMillis());
                new WebDetailDao(context).add(bean);

                if (loadComplete != null){
                    loadComplete.loadComplete(bean);
                }
//                if (manager != null){
//                    manager.removeTask(articleid);
//                }
            }
        });

        if (manager != null){
            WebTask task = new WebTask();
            task.setCall(call);
            task.setTag(articleid);
            manager.addTask(task);
        }
    }

    public interface LoadWebComplete{
       public void  loadComplete(WebDetailBean bean);
    }
}
