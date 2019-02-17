package com.baiyi.jj.app.manager;

import android.content.Context;

import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.activity.main.task.WebLoadManager;
import com.baiyi.jj.app.activity.main.task.WebTask;
import com.baiyi.jj.app.cache.Dao.WebDetailDao;
import com.baiyi.jj.app.cache.bean.WebDetailBean;
import com.baiyi.jj.app.entity.MorningHeadEntity;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.TextLengthUtils;
import com.baiyi.jj.app.utils.Utils;
import com.turbo.turbo.mexico.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/6/14 0014.
 */
public class MorningNewsManager {

    public static void loadMornData(final LoadComplete loadComplete,final Context context) {


        //http://api.mx.turboapp.xyz:8080/api/v1/morningpaper/articles/
        //http://api.mx.turboapp.xyz:8080/api/v1/morningpaper/

        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .url(NetUtils.getMorningData())
                .get()
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                TLog.e("ss","falihead-----------"+e.toString());
                if (loadComplete!=null){
                    loadComplete.loadcomplte(null,null);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                try {
                    if (Utils.isStringEmpty(str)){
                        return;
                    }
                    TLog.e("ss","headdata--------"+str);
//                    if (!Config.isRelease && str.equals("{}")){
//                        str = TextLengthUtils.getAssetsStr(context, "morntest");
//                    }
                    JSONObject object = new JSONObject(str);
                    //{"cover": "http://lumen-mx.fission.arityapp.com/73624d2450ac11e7a7ba061a158aa55d.jpg", "id": 22, "template": 1, "title": "Morning Paper"}
                    List<MorningHeadEntity> morningHeadEntities = new ArrayList<MorningHeadEntity>();
                    List<String> imagList = new ArrayList<String>();
                    if (object.has("articles")){
                        JSONArray array = object.getJSONArray("articles");
                        for (int i = 0;i<array.length();i++){
                            MorningHeadEntity morningHeadEntity = new MorningHeadEntity();
                            JSONObject jsonObject = array.getJSONObject(i);
                            morningHeadEntity.setPaperid(object.optString("morningpaper_id","-1"));
                            morningHeadEntity.setId(jsonObject.optString("id",""));
                            morningHeadEntity.setIndex(jsonObject.optInt("index",0));
                            morningHeadEntity.setTemplate(jsonObject.optInt("template",0));
                            morningHeadEntity.setTitleStr(jsonObject.optString("title",""));
                            List<String> list = Utils.splitToList(jsonObject.optString("cover"),",");
                            morningHeadEntity.setImageList(list);
                            imagList.addAll(list);
                            morningHeadEntities.add(morningHeadEntity);
                        }

                    }
                    if (loadComplete!=null){
                        loadComplete.loadcomplte(morningHeadEntities,imagList);
                    }
                }catch (JSONException e){
                    if (loadComplete!=null){
                        loadComplete.loadcomplte(null,null);
                    }
                    e.printStackTrace();
                }

            }
        });

    }

    public static boolean isShowHead(){
        long curentTime = System.currentTimeMillis();
        long oneday = 1000*60*60*24;
        TimeZone tz = TimeZone.getDefault();
        long curentMi2 = (curentTime+tz.getRawOffset())%oneday;
        long time1030 = (int)(1000*60*60*10.5);
        return curentMi2<time1030;
    }
    public static String getDayTitle(Context context){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(context.getResources().getString(R.string.tip_morning_title));
        return stringBuffer.toString();
    }

    public static String getDayTitleTime(Context context){
        StringBuffer stringBuffer = new StringBuffer();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int mouth = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        stringBuffer.append(day+" ");
        stringBuffer.append(mouths_es[mouth-1]);
        stringBuffer.append(" "+year);

        return stringBuffer.toString();
    }
    private static String[] mouths_es = {"ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGO", "SEP", "OCT", "NOV", "DIC"};
    private static String[] mouths_en = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};

    public interface LoadComplete{
        void loadcomplte(List<MorningHeadEntity> entity,List<String> imagList);
    }

    public static List<NewsListEntity> getMorningDataList(String str){
        List<NewsListEntity> listEntities = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(str);
            if (root.has("articles")){
                JSONArray array = root.getJSONArray("articles");
                for (int i=0;i<array.length();i++){
                    NewsListEntity entity = new NewsListEntity();
                    JSONObject object = array.getJSONObject(i);
                    entity.setArticle_title(object.optString("title"));
                    entity.setArticle_source(object.optString("source"));
                    entity.setArticle_pubDate(object.optString("create_date"));
                    entity.setArticle_id(object.optString("id"));
                    entity.setPageUrl(object.optString("page_url"));
                    if (!object.isNull("tag")) {
                        try {
                            JSONArray array2 = object.getJSONArray("tag");
                            List<Integer> tagList = JsonParse.getTagList(array2, entity);
                            entity.updateTags(tagList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!object.isNull("cover")) {
                        entity.updateImageList(JsonParse.getImageInfo(object));
                    }
                    listEntities.add(entity);
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return listEntities;
    }


}
