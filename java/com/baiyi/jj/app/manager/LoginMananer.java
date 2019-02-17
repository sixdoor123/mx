package com.baiyi.jj.app.manager;

import android.content.Context;
import android.util.Log;

import com.baiyi.core.file.Preference;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.user.net.JsonParse_User;
import com.baiyi.jj.app.activity.user.net.NetUrl;
import com.baiyi.jj.app.activity.user.net.register.RegisterManager;
import com.baiyi.jj.app.activity.user.net.register.entity.ResultEntities;
import com.baiyi.jj.app.activity.user.net.register.entity.StateEnties;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.cache.Dao.ConfigDao;
import com.baiyi.jj.app.cache.bean.ConfigurationBean;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.utils.Base64;
import com.baiyi.jj.app.utils.Installation;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.utils.XMLName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/7 0007.
 */
public class LoginMananer {
    public static final int SUCCESS = 1;
    public static final int FAIL = 2;
    FirstLoginCallBack callBack;

    private static LoginMananer instance = null;

    public static LoginMananer getInstance()
    {
        if(instance == null)
        {
            instance = new LoginMananer();
        }
        return instance;
    }

    public void LoginFirst(final Context context) {

        Log.e("skip", "login----create start1----" + System.currentTimeMillis());

        OkHttpClient client = new OkHttpClient.Builder().build();
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                getVisitorPostData(context));
        Request request = new Request.Builder()
                .post(requestBody)
                .url(NetUrl.getVisitorRegisterInfoUrl())
                .header(Constant.ContentType, Constant.ContentType_Json)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e("skip", "login----creat gust success----" + System.currentTimeMillis());
                Log.e("log",str);
                try {
                    JSONObject object = new JSONObject(str);
                    JSONArray array = new JSONArray();
                    array.put(object);
                    ResultEntities res = RegisterManager.getRegisterResult((JSONArray) array);
                    if (res != null) {
                        StateEnties stateEmtities = res.getStateEnties();
                        if (stateEmtities.getState() != 200) {
                            callBack.callBackLister(FAIL, null);
                            return;
                        }
                        gustLogin(context,res.getUserName(), res.getUserName(),res.getId());
                    } else {
                        callBack.callBackLister(FAIL, null);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }

        });
    }

    public String getVisitorPostData(Context context) {
        try {
//                String inUUID = Installation.GetUUID(context);
            String inUUID = Installation.getClientId(context);
            saveUUID(inUUID,context);
            return new JSONObject().put("client_code", inUUID).toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    private void saveUUID(final String inUUID,final Context context){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ConfigurationBean bean = new ConfigurationBean();
                bean.setUuid(inUUID);
                new ConfigDao(context).add(bean);
            }
        }).start();
    }


    public void gustLogin(final Context context,final String account,final String pwd,final String gustid) {

        Log.e("skip", "login----gust login start----" + System.currentTimeMillis());

        OkHttpClient client = new OkHttpClient.Builder().build();
        RequestBody formBody = new FormBody.Builder()
                .add("username", account)
                .add("password", pwd)
                .add("grant_type", "password")
                .build();

        String appid = Constant.APP_ID + ":" + Constant.APP_Secret;
        String authStr = new String(Base64.encode(appid.getBytes()));

        Request request = new Request.Builder()
                .post(formBody)
                .url(NetUrl.getPostMemberLogin())
                .header(Constant.ContentType, Constant.ContentType_form)
                .header(Constant.HEAD_NAME, "Basic " + authStr)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = "["+response.body().string()+"]";
                Log.e("skip", "login----gust login success----" + System.currentTimeMillis());
                try {
                    JSONArray array = new JSONArray(str);
                    UserInfoEntity entity = JsonParse_User.getToken(array);
                    if (entity == null) {
                        callBack.callBackLister(FAIL, null);
                        return;
                    }
                    // 用于匿名用户
                    entity.setAccount(account);
                    entity.setPwd(pwd);
                    entity.setMID(gustid);
                    callBack.callBackLister(SUCCESS,entity);
                    CmsApplication.setUserInfoEntity(entity, context);
                    Log.e("skip", "----gustlogin callback----" + System.currentTimeMillis());
                    new ConfigDao(context).updateGustToken(entity.getToken(), entity.getRefreshToken());
                    CmsApplication.setUserToken(entity.getTokenType() + " " + entity.getToken());
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    public void setCallBack(FirstLoginCallBack callBack) {
        this.callBack = callBack;
    }

    public interface FirstLoginCallBack{
        void callBackLister(int state,UserInfoEntity entity);
    }

}
