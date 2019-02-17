package com.baiyi.jj.app.activity.user.net.head;

import android.graphics.Bitmap;
import android.util.Log;

import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.user.net.NetUrl;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.utils.JsonParseBase;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 请求上传头像
 * 作者：lizl on 2016/11/24 13:49
 */
public class UserHeadNet {

    public UserHeadNet(OnUpLoadHeadCallBack headCallBack) {
        this.headCallBack = headCallBack;
    }

    public void UpLoadHead(String imagePath) {

//        OkHttpClient httpClient = new OkHttpClient.Builder().build();
        OkHttpClient httpClient =
                new OkHttpClient.Builder()
                        .readTimeout(20, TimeUnit.SECONDS)//设置读取超时时间
                        .writeTimeout(20,TimeUnit.SECONDS)//设置写的超时时间
                        .connectTimeout(20,TimeUnit.SECONDS)//设置连接超时时间
                        .build();
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), new File(imagePath));
        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("avatar", "head",fileBody)
                .build();
        TLog.d("ATG","avatar:"+imagePath);
        Request request = new Request.Builder()
                .url(NetUrl.getUpHeadUrl())
                .post(formBody)
                .header(Constant.HEAD_NAME, CmsApplication.getUserToken())
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (headCallBack != null) {
                    headCallBack.isSuccessful(false);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                TLog.d("ATG",str);
                try {
                    JSONObject object = new JSONObject(str);
                    if (JsonParseBase.getState200(object)) {
                        headCallBack.isSuccessful(true);
                    }else{
                        headCallBack.isSuccessful(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    headCallBack.isSuccessful(false);
                }
            }

        });
    }

    private OnUpLoadHeadCallBack headCallBack;

    public interface OnUpLoadHeadCallBack {
        void isSuccessful(boolean success);
    }
}
