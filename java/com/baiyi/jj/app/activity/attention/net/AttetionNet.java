package com.baiyi.jj.app.activity.attention.net;

import android.content.Context;
import android.widget.Button;

import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.attention.AttentionWordsEntity;
import com.baiyi.jj.app.activity.user.net.NetUrl;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.cache.Dao.AttetionWordsDao;
import com.baiyi.jj.app.cache.Dao.ChannelDao;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.turbo.turbo.mexico.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/2 0002.
 */
public class AttetionNet {


    public static final int State_Success = 1;
    public static final int State_Failure = 2;

    public void loaddelAttention(final String hotid, final delCallBack callBack) {
        TLog.e("abc","loaddel------------2");
        if (Utils.isStringEmpty(CmsApplication.getUserToken())) {
            if (callBack != null) {
                callBack.callBack(State_Failure);
            }
            return;
        }
        TLog.e("abc","loaddel------------3");
        OkHttpClient client = new OkHttpClient.Builder().build();
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                getPostBody(hotid));
        TLog.e("delat", hotid + "=-====" + NetUrl.delAttetion());
        Request request = new Request.Builder()
                .post(requestBody)
                .url(NetUrl.delAttetion())
                .header(Constant.ContentType, Constant.ContentType_Json)
                .header(Constant.HEAD_NAME, CmsApplication.getUserToken())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack != null) {
                    callBack.callBack(State_Failure);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String StrJson = response.body().string();
                TLog.e("attetion", StrJson);
                if (callBack != null) {
                    if (JsonParse.getState200(StrJson)) {
                        callBack.callBack(State_Success);
                    } else {
                        callBack.callBack(State_Failure);
                    }
                }
            }
        });


    }

    private String getPostBody(String hotid) {
        JSONObject object = new JSONObject();
        try {
            object.put("hotword_id", hotid);
        } catch (JSONException e) {
            e.toString();
        }
        return object.toString();
    }

    public void loadAddAttention(String hotid, final addCallBack callBack) {
        if (Utils.isStringEmpty(CmsApplication.getUserToken())) {
            if (callBack != null) {
                callBack.callBack(State_Failure);
            }
            return;
        }

        OkHttpClient client = new OkHttpClient.Builder().build();
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                getAddPostBody(hotid));
        Request request = new Request.Builder()
                .post(requestBody)
                .url(NetUrl.addAttetion())
                .header(Constant.ContentType, Constant.ContentType_Json)
                .header(Constant.HEAD_NAME, CmsApplication.getUserToken())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack != null) {
                    callBack.callBack(State_Failure);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String StrJson = response.body().string();
                TLog.e("attetion", StrJson);
                if (callBack != null) {
                    if (JsonParse.getState200(StrJson)) {
                        callBack.callBack(State_Success);
                    } else {
                        callBack.callBack(State_Failure);
                    }
                }
            }
        });


    }

    private String getAddPostBody(String hotid) {
        JSONObject object = new JSONObject();
        try {
            object.put("hotword_id", hotid);
        } catch (JSONException e) {
            e.toString();
        }
        return object.toString();
    }

    public void loadAllWordsList(final Context context, final String channelID, final LoadWordsCallBack callBack,boolean isNet) {

        if (!isNet){
            if (CmsApplication.getUserInfoEntity() != null){
                final List<AttentionWordsEntity> listEntities = new AttetionWordsDao(context).queryForChannel(channelID, CmsApplication.getUserInfoEntity().getMID());
                TLog.e("channal",channelID+"======cahce======size="+listEntities.size());
                if (!Utils.isStringEmpty(listEntities)){
                    callBack.callBack(listEntities);
                    return;
                };
            }
        }


        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .get()
                .url(NetUrl.getNoFollowUrl(channelID, 1, Constant.limit_attetion))
                .header(Constant.HEAD_NAME, CmsApplication.getUserToken())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                TLog.e("tag",json);
                List<AttentionWordsEntity> listEntities = JsonAttention.getNoFollowWords(json);
                TLog.e("channal",channelID+"=========size="+listEntities.size());
                callBack.callBack(listEntities);
                new AttetionWordsDao(context).addCacheList(listEntities,channelID);
            }
        });

    }

    public void loadAllTagChannel(final Context context, final LoadChannelCallBack callBack,boolean isNet) {

        if (!isNet){
            final List<ChannelItem> listEntities = new ChannelDao(context).queryForAllTag();
            if (!Utils.isStringEmpty(listEntities)){
                callBack.callBack(listEntities);
                return;
            };
        }


        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .get()
                .url(NetUrl.getTagChannel())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                TLog.e("tag",json);
                List<ChannelItem> listEntities = JsonAttention.getTagChannels(json);
                callBack.callBack(listEntities);
                new ChannelDao(context).updateTags(listEntities);
            }
        });

    }

    public interface addCallBack {
        public void callBack(int state);
    }

    public interface delCallBack {
        public void callBack(int state);
    }

    public interface LoadWordsCallBack {
        public void callBack(List<AttentionWordsEntity> listEntities);
    }

    public interface LoadChannelCallBack {
        public void callBack(List<ChannelItem> listEntities);
    }
}
