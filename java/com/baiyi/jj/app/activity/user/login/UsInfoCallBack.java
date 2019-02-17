package com.baiyi.jj.app.activity.user.login;

import android.content.Context;
import android.util.Log;

import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.activity.user.net.JsonParse_User;
import com.baiyi.jj.app.activity.user.net.NetUrl;
import com.baiyi.jj.app.activity.user.net.setting.SettingManager;
import com.baiyi.jj.app.activity.user.net.setting.SettingUrl;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.cache.Dao.ConfigDao;
import com.baiyi.jj.app.cache.bean.ConfigurationBean;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.utils.Base64;
import com.baiyi.jj.app.utils.JsonParse;
import com.turbo.turbo.mexico.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * ע���û��ĸ�����Ϣ
 * Created by Administrator on 2016/10/17.
 */

public class UsInfoCallBack
{
    private Context context = null;
    private CallBack callBack = null ;

    private RefreshTComplete refreshTComplete;

    //��ȡ������Ϣ�ɹ�
    public static final int SUCCESS = 1;
    //ʧ��
    public static final int FAIL = 2;

    private int refreshNum = 0;

    public UsInfoCallBack(Context context,CallBack callBack)
    {
        this.context = context;
        this.callBack = callBack;
    }

    public interface CallBack
    {
        void  callBack(int state,UserInfoEntity entity);
    }

    /**
     * ��ȡ������Ϣ
     */
    public void loadMemberInfo(final int UserType)
    {
        final JsonLoader loader = new JsonLoader(context);
        loader.setUrl(SettingUrl.getMemberInfoUrl());
        loader.setUrlName(MemberConfig.Member_Info);
        loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
        loader.setType(BaseNetLoder.POST_FORM);
        loader.setPostData(new JSONObject().toString());
        loader.setMethod(BaseNetLoder.Method_Get);
        loader.setLoaderListener(new Loader.LoaderListener() {

            @Override
            public void onProgress(Object tag, long curByteNum,
                                   long totalByteNum) {
            }

            @Override
            public void onError(Object tag, int responseCode,
                                String errorMessage) {
                if (callBack != null)
                {
                    callBack.callBack(FAIL,null);
                }
            }

            @Override
            public void onCompelete(Object tag, Object result) {
                JSONArray array = (JSONArray) result;
                final UserInfoEntity entitie = SettingManager.getMemberInfo(array,UserType);
                if (entitie.getState() == 200) {

                    if (callBack != null)
                    {
                        new ConfigDao(context).updateUserType(UserType);
                        callBack.callBack(SUCCESS,entitie);
                    }
                }else if (entitie.getState() == 521){
                    // token shixiao

                    refreshNum++;
                    refreshToken(new RefreshTComplete() {
                        @Override
                        public void refreshComplete(boolean isSuccess, String token) {
                            if (isSuccess && refreshNum<2){
                                loadMemberInfo(UserType);
                            }else {
                                if (callBack != null)
                                {
                                    callBack.callBack(FAIL,entitie);
                                }
                            }
                            refreshNum++;

                        }
                    });
                }
            }
        });
        CmsApplication.getDataStratey().startLoader(loader);
    }

    public interface RefreshTComplete{
        void refreshComplete(boolean isSuccess,String token);
    }


    public void refreshToken(final RefreshTComplete refreshTComplete){
        ConfigurationBean bean = new ConfigDao(context).queryCofig();
        final UserInfoEntity entity = CmsApplication.getUserInfoEntity();
        if (entity == null || bean == null){
            return;
        }
        String refreshToken = entity.getUserType() == UserInfoEntity.UserType_Gust ? bean.getRefreshGustToken() : bean.getRefreshToken();
        if (refreshToken == null){
            return;
        }
        JsonLoader loader = new JsonLoader(context);
        loader.setUrl(NetUrl.getPostMemberLogin());
        loader.setUrlName(MemberConfig.Login);
        String appid = Constant.APP_ID + ":" + Constant.APP_Secret;
        String authStr = new String(Base64.encode(appid.getBytes()));
        loader.addRequestHeader(Constant.HEAD_NAME, "Basic " + authStr);
        loader.setType(BaseNetLoder.POST_FORM);
        loader.setContentTextList(getRereshToken(refreshToken));
        loader.setMethod(BaseNetLoder.Method_Post);
        loader.setLoaderListener(new Loader.LoaderListener() {

            @Override
            public void onProgress(Object tag, long curByteNum,
                                   long totalByteNum) {
            }

            @Override
            public void onError(Object tag, int responseCode,
                                String errorMessage) {
                if (refreshTComplete != null){
                    refreshTComplete.refreshComplete(false,null);
                }
            }

            @Override
            public void onCompelete(Object tag, Object result) {
                JSONArray array = (JSONArray) result;
                if (array == null) {
                    return;
                }
                JSONObject o;
                try {
                    o = array.getJSONObject(0);
                    if (o.isNull("access_token")) {
                        if (refreshTComplete != null){
                            refreshTComplete.refreshComplete(false,null);
                        }
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                UserInfoEntity userInfoEntity = JsonParse_User.getToken(array);
                if (entity.getUserType() == UserInfoEntity.UserType_Gust){
                    new ConfigDao(context).updateGustToken(userInfoEntity.getToken(),userInfoEntity.getRefreshToken());
                }else {
                    new ConfigDao(context).updateUserToken(userInfoEntity.getToken(),userInfoEntity.getRefreshToken());
                }
                CmsApplication.setUserToken(userInfoEntity.getTokenType() + " " +userInfoEntity.getToken());
                if (refreshTComplete != null){
                    refreshTComplete.refreshComplete(true,userInfoEntity.getToken());
                }
            }
        });
        CmsApplication.getDataStratey().startLoader(loader);
    }

    private ArrayList<String[]> getRereshToken(String refreshtoken) {


        ArrayList<String[]> textList = new ArrayList<String[]>();
        textList.add(new String[]{"grant_type", "refresh_token"});
        textList.add(new String[]{"refresh_token", refreshtoken});
        textList.add(new String[]{"scope", "read write"});
        return textList;

    }
}
