package com.baiyi.jj.app.activity.user.net;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.baiyi.core.file.Preference;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.activity.BaseAnalyticsActivity;
import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.cache.Dao.ConfigDao;
import com.baiyi.jj.app.cache.bean.ConfigurationBean;
import com.baiyi.jj.app.utils.Installation;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.utils.TLog;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.user.net.register.RegisterManager;
import com.baiyi.jj.app.activity.user.net.register.entity.ResultEntities;
import com.baiyi.jj.app.activity.user.net.register.entity.StateEnties;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.utils.Base64;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.utils.XMLName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserNetCallBack {

    public static final int SUCCESS = 1;
    public static final int FAIL = 2;
    private Context context;
    private CallBack callBack;


    public UserNetCallBack() {;
    }

    public UserNetCallBack(Context context, CallBack callBack) {
        this.context = context;
        this.callBack = callBack;
    }

    /**
     * 获取游客账号
     */
    public void loadAuthGust(boolean isReLogin) {
        if (!ContextUtil.isNetWorking(context)) {
            ((BaseActivity) context).displayToast(context.getResources().getString(R.string.tip_net_fault));
            callBack.callBackLister(FAIL, null);
            return;
        }
        ConfigurationBean configBean = new ConfigDao(context).queryCofig();
        String uuid = null;
        if (configBean != null) {
            uuid = configBean.getUuid();
        }
        if (!Utils.isStringEmpty(uuid) && !Utils.isStringEmpty(configBean.getGustToken()) && !isReLogin) {
            CmsApplication.setUserToken("Bearer " + configBean.getGustToken());
            getTicketKey(configBean.getGustToken(), "", "", UserInfoEntity.UserType_Gust);
            return;
        }
        final long start = System.currentTimeMillis();
        JsonLoader loader = new JsonLoader(context);
        loader.setUrl(NetUrl.getVisitorRegisterInfoUrl());
        TLog.e("url",NetUrl.getVisitorRegisterInfoUrl());
        loader.setMethod(BaseNetLoder.Method_Post);
        loader.setPostData(getVisitorPostData(uuid));
        TLog.e("post",getVisitorPostData(uuid));
        loader.setType(BaseNetLoder.APPLICATION_JSON);
        loader.setLoaderListener(new LoaderListener() {

            @Override
            public void onProgress(Object arg0, long arg1, long arg2) {

            }

            @Override
            public void onError(Object arg0, int arg1, String arg2) {
                TLog.e("abc","error-----"+arg1);
                callBack.callBackLister(FAIL, null);
            }

            @Override
            public void onCompelete(Object arg0, Object arg1) {
                TLog.e("arg",((JSONArray) arg1).toString());

                long value = System.currentTimeMillis() - start;
                ((BaseActivity)context).addRequestTime(this.getClass().getName(), BaseAnalyticsActivity.Category_net, BaseAnalyticsActivity.Net_Creatgust,value);

                ResultEntities res = RegisterManager.getRegisterResult((JSONArray) arg1);
                if (res != null) {
                    StateEnties stateEmtities = res.getStateEnties();
                    if (stateEmtities.getState() != 200) {
                        callBack.callBackLister(FAIL, null);
                        return;
                    }

                    loadLogin(res.getUserName(), res.getUserName(), UserInfoEntity.UserType_Gust,res.getId());
                } else {
                    callBack.callBackLister(FAIL, null);
                }
            }
        });
        CmsApplication.getDataStratey().startLoader(loader);
    }

    public String getVisitorPostData(String uuid) {
        try {
            if (Utils.isStringEmpty(uuid)) {
//                String inUUID = Installation.GetUUID(context);
                String inUUID = Installation.getClientId(context);
                ConfigurationBean bean = new ConfigurationBean();
                bean.setUuid(inUUID);
                new ConfigDao(context).add(bean);
                return new JSONObject().put("client_code", inUUID).toString();
            } else {
                return new JSONObject().put("client_code", uuid).toString();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void loadLogin(final String account, final String pwd, final int UserType, final String gustid) {

        final long start = System.currentTimeMillis();
        JsonLoader loader = new JsonLoader(context);
        loader.setUrl(NetUrl.getPostMemberLogin());
        loader.setUrlName(MemberConfig.Login);
        TLog.e("url",NetUrl.getPostMemberLogin());
        String appid = Constant.APP_ID + ":" + Constant.APP_Secret;
        String authStr = new String(Base64.encode(appid.getBytes()));
        loader.addRequestHeader(Constant.HEAD_NAME, "Basic " + authStr);
        TLog.e("head",Constant.HEAD_NAME+ "===Basic " + authStr);
//        if (CountryMannager.getInstance().getCurrentCountry() == CountryMannager.Country_Test){
            loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
//        }else {
//            loader.setType(BaseNetLoder.POST_FORM);
//        }
//        loader.addRequestHeader("Content-Type","application/x-www-form-urlencoded");
        loader.setContentTextList(getPostData(account, pwd));
        loader.setMethod(BaseNetLoder.Method_Post);
        loader.setLoaderListener(new LoaderListener() {

            @Override
            public void onProgress(Object tag, long curByteNum,
                                   long totalByteNum) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(Object tag, int responseCode,
                                String errorMessage) {
                // TODO Auto-generated method stub
                TLog.e("tokenerror", errorMessage + "=++++");
                callBack.callBackLister(FAIL, null);
            }

            @Override
            public void onCompelete(Object tag, Object result) {
                long value = System.currentTimeMillis() - start;
                ((BaseActivity)context).addRequestTime(this.getClass().getName(), BaseAnalyticsActivity.Category_net, BaseAnalyticsActivity.Net_Login,value);

                JSONArray array = null;
                try {
                    array = (JSONArray) result;
                }catch (Exception e){
                    e.printStackTrace();
                }
                if (array == null){
                    callBack.callBackLister(FAIL, null);
                    return;
                }
                TLog.e("token", array.toString());

                JSONObject o;
                try {
                    o = array.getJSONObject(0);
                    //int state = JsonParse.getIntNodeValue(o, "state");
                    if (o.isNull("access_token")) {
//                        loadAuthGust(true);
                        callBack.callBackLister(FAIL, null);
                        return;
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
                UserInfoEntity entity = JsonParse_User.getToken(array);
                if (entity == null) {
                    callBack.callBackLister(FAIL, null);
                    return;
                }
                if (UserType == UserInfoEntity.UserType_Gust) {
                    new ConfigDao(context).updateGustToken(entity.getToken(), entity.getRefreshToken());
                } else {
                    new ConfigDao(context).updateUserToken(entity.getToken(), entity.getRefreshToken());
                }

                // 用于匿名用户
                entity.setAccount(account);
                entity.setPwd(pwd);
                CmsApplication.setUserToken(entity.getTokenType() + " " + entity.getToken());
                if (!Utils.isStringEmpty(gustid)){
                    entity.setMID(gustid);
                    callBack.callBackLister(SUCCESS,entity);
                    return;
                }
//                Log.e("login", entity.getTokenType() + " " + entity.getToken());
                getTicketKey(entity.getToken(), account, pwd, UserType);
            }
        });
        CmsApplication.getDataStratey().startLoader(loader);
    }

    boolean isFirstL = true;
    int restart = 0;
    public void getTicketKey(final String token,
                             final String accout, final String pwd, final int UserType) {

        final long start = System.currentTimeMillis();
        JsonLoader loader = new JsonLoader(context);
        loader.setUrl(NetUrl.getTicketKey());
        TLog.e("url--",NetUrl.getTicketKey());
        loader.setUrlName("获取信息");
        loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
        loader.setMethod(BaseNetLoder.Method_Get);
        loader.setLoaderListener(new LoaderListener() {

            @Override
            public void onProgress(Object tag, long curByteNum,
                                   long totalByteNum) {
            }

            @Override
            public void onError(Object tag, int responseCode,
                                String errorMessage) {
                if (isFirstL) {
                    if (UserType == UserInfoEntity.UserType_Gust) {
                        loadInitGust(true);
                    } else if (!Utils.isStringEmpty(accout) && !Utils.isStringEmpty(pwd) && UserType == UserInfoEntity.UserType_Accout) {
                        loadLogin(accout, pwd, UserType, null);
                    } else {
                        loadInitGust(true);
                    }
                    isFirstL = false;
                }else {
                    callBack.callBackLister(FAIL, null);
                }

            }

            @Override
            public void onCompelete(Object tag, Object result) {
                // TODO Auto-generated method stub
                String obj = result.toString();
//                TLog.e("obj","--------"+obj);
                try {
                    JSONArray array = (JSONArray) result;
                    long value = System.currentTimeMillis() - start;
                    ((BaseActivity)context).addRequestTime(this.getClass().getName(), BaseAnalyticsActivity.Category_net, BaseAnalyticsActivity.Net_Get_Member,value);
//                    JSONArray array = new JSONArray(obj);
                    TLog.e("login", array.toString());
                    JSONObject root = array.getJSONObject(0);
                    // token invalid
                    restart++;
                    if (JsonParse.getIntState(root) == 521 && restart<6) {

                        if (UserType == UserInfoEntity.UserType_Gust) {
                            loadInitGust(true);
                        } else if (!Utils.isStringEmpty(accout) && !Utils.isStringEmpty(pwd) && UserType == UserInfoEntity.UserType_Accout) {
                            loadLogin(accout, pwd, UserType,null);
                        } else {
                            loadInitGust(true);
                        }
                        return;
                    }
                    UserInfoEntity newEntity = JsonParse_User.getUserInfoEntity(array, UserType);
                    if (newEntity == null) {
                        callBack.callBackLister(FAIL, null);
                        return;
                    }
                    newEntity.setToken(token);
                    new ConfigDao(context).updateUserType(UserType);
                    callBack.callBackLister(SUCCESS, newEntity);
//                    sendCast(context, newEntity.getMID());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        CmsApplication.getDataStratey().startLoader(loader);
    }

//    private void sendCast(Context context, String mid) {
//        Intent intent = new Intent();
//        intent.setAction(MsgFragment.MsgAction);
////        intent.setAction(MsgPager.MsgAction);
//        intent.putExtra("mid", mid);
//        context.sendBroadcast(intent);
//    }

    private ArrayList<String[]> getPostData(String account, String pwd) {


        ArrayList<String[]> textList = new ArrayList<String[]>();
        textList.add(new String[]{"username", account});
        textList.add(new String[]{"password", pwd});
        textList.add(new String[]{"grant_type", "password"});
        return textList;

    }

    private String getMemberPost(int MActionType, String token,
                                 String inviteCode) {
        JSONObject object = new JSONObject();
        try {
            JSONArray array = new JSONArray();
            JSONObject obj = new JSONObject();
            obj.put("MAction", MActionType);
            array.put(obj);
            object.put("MVList", array);
            object.put("token", token);
            if (!Utils.isStringEmpty(inviteCode)
                    && Utils.isPhoneNumberValid(inviteCode)) {
                object.put("invitecode", "");
                object.put("invitemobile", inviteCode);
            } else {
                object.put("invitecode", inviteCode);
                object.put("invitemobile", "");
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return object.toString();
    }

    public interface CallBack {
        public void callBackLister(int state, UserInfoEntity entity);
    }

    public void loadLoginTrigger(String token) {
        if (Utils.isStringEmpty(token)) {
            return;
        }

        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .url(NetUrl.getLoginTrigger())
                .get()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                TLog.e("str", str);
            }

        });
    }

    public interface CallIntegralBack {
        public void callIntegralBackLister(String s);
    }

    public void loadIntegral(final CallIntegralBack callIntegralBack) {
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .url(NetUrl.updateIntegral())
                .get()
                .header(Constant.HEAD_NAME, CmsApplication.getUserToken())
                .build();
        Log.d("ATG",CmsApplication.getUserToken()+"///"+NetUrl.updateIntegral());
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callIntegralBack.callIntegralBackLister("");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
//                TLog.e("strintegal",str.toString());
                JSONObject object = null;
                try {
                    object = new JSONObject(str);
                    if (!JsonParse.getState200(object)){
                        callIntegralBack.callIntegralBackLister("");
                        return;
                    }
                    JSONObject dataObj = JsonParse.getResultObj(object);
//                    {"state": 200, "data": {"integral": 53}}
                    callIntegralBack.callIntegralBackLister(JsonParse.getStringNodeValue(dataObj,"integral"));

                } catch (Exception e) {
                    callIntegralBack.callIntegralBackLister("");
                }
            }

        });
    }


    /**
     * 获取游客账号
     */
    public void loadInitGust(boolean isReLogin) {

        if (!ContextUtil.isNetWorking(context)) {
            ((BaseActivity) context).displayToast(context.getResources().getString(R.string.tip_net_fault));
            callBack.callBackLister(FAIL, null);
            return;
        }
        ConfigurationBean configBean = new ConfigDao(context).queryCofig();
        String uuid = null;
        if (configBean != null) {
            uuid = configBean.getUuid();
        }
        if (!Utils.isStringEmpty(uuid) && !Utils.isStringEmpty(configBean.getGustToken()) && !isReLogin) {
            CmsApplication.setUserToken("Bearer " + configBean.getGustToken());
            getTicketKey(configBean.getGustToken(), "", "", UserInfoEntity.UserType_Gust);
            return;
        }
        final long start = System.currentTimeMillis();
        JsonLoader loader = new JsonLoader(context);
        loader.setUrl(NetUrl.getInitGustUrl());
        loader.setMethod(BaseNetLoder.Method_Post);
        loader.setPostData(getVisitorPostData(uuid));
        loader.setType(BaseNetLoder.APPLICATION_JSON);
        loader.setLoaderListener(new LoaderListener() {

            @Override
            public void onProgress(Object arg0, long arg1, long arg2) {

            }

            @Override
            public void onError(Object arg0, int arg1, String arg2) {
                TLog.e("loadInitGust","onError-----"+arg1);
                callBack.callBackLister(FAIL, null);
            }

            @Override
            public void onCompelete(Object arg0, Object arg1) {
                try {
                    long value = System.currentTimeMillis() - start;
                    ((BaseActivity)context).addRequestTime(this.getClass().getName(), BaseAnalyticsActivity.Category_net, BaseAnalyticsActivity.Net_Creatgust,value);
                    UserInfoEntity entity = JsonParse_User.getGustToken(arg1);
                    if (entity == null) {
                        callBack.callBackLister(FAIL, null);
                        return;
                    }
                    new ConfigDao(context).updateGustToken(entity.getToken(), entity.getRefreshToken());
                    CmsApplication.setUserToken(entity.getTokenType() + " " + entity.getToken());
                    if (!Utils.isStringEmpty(entity.getMID())){
                        callBack.callBackLister(SUCCESS,entity);
                        return;
                    }
                    getTicketKey(entity.getToken(), "", "", UserInfoEntity.UserType_Gust);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        CmsApplication.getDataStratey().startLoader(loader);
    }


}
