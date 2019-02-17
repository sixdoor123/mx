package com.baiyi.jj.app.activity.user.login;

import android.accounts.Account;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.main.NewsBaseActivity;
import com.baiyi.jj.app.activity.user.UsLoginAct;
import com.baiyi.jj.app.activity.user.ValidateEmailActivity;
import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.manager.ChannelDataManager;
import com.baiyi.jj.app.manager.facebook.FaceBookLogin;
import com.baiyi.jj.app.activity.user.net.HeadUtils;
import com.baiyi.jj.app.activity.user.net.UserNetCallBack;
import com.baiyi.jj.app.activity.user.net.register.entity.StateEnties;
import com.baiyi.jj.app.activity.user.net.setting.SettingManager;
import com.baiyi.jj.app.activity.user.net.setting.SettingUrl;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.cache.Dao.ConfigDao;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.manager.googleauth.GoogleLoginMannager;
import com.baiyi.jj.app.utils.Base64;
import com.baiyi.jj.app.utils.Installation;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.turbo.turbo.mexico.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/2 0002.
 */


public class BaseLoginActivity extends NewsBaseActivity implements FaceBookLogin.FacebookListener {

    private EditText editUsername;
    private EditText editPwd;
    private MyLoadingBar loadingBar;

    LoginManager loginManager;
    CallbackManager callbackManager;

    FaceBookLogin faceBookLogin;

    private LoginComplete loginComplete;

    private String logoLogin = "";

    // google+ login
    private GoogleApiClient mGoogleApiClient;
    private static int RC_SIGN_IN = 10001;

    GoogleLoginMannager googleLoginMannager;

//    protected void initWin(boolean hasScrollView, boolean isAnimal,EditText eidtUserName,EditText editPwd,MyLoadingBar loadingBar) {
//        Log.e("log","init-------------");
//        this.editUsername = eidtUserName;
//        this.editPwd = editPwd;
//        this.loadingBar = loadingBar;
//        super.initWin(hasScrollView, isAnimal);
//    }

    public void initEdit(FragmentActivity activity, EditText eidtUserName, EditText editPwd, MyLoadingBar loadingBar) {
        this.editUsername = eidtUserName;
        this.editPwd = editPwd;
        this.loadingBar = loadingBar;
        faceBookLogin = FaceBookLogin.getInstence(activity);
        faceBookLogin.setFacebookListener(this);
//        googleLoginMannager = GoogleLoginMannager.getInstence(activity);
//        mGoogleApiClient = googleLoginMannager.getmGoogleApiClient();

    }

    public void initEdit(FragmentActivity activity, EditText eidtUserName, EditText editPwd, MyLoadingBar loadingBar, String LogoLogin) {
        this.editUsername = eidtUserName;
        this.editPwd = editPwd;
        this.loadingBar = loadingBar;
        faceBookLogin = FaceBookLogin.getInstence(activity);
        faceBookLogin.setFacebookListener(this);
//        googleLoginMannager = GoogleLoginMannager.getInstence(activity);
//        mGoogleApiClient = googleLoginMannager.getmGoogleApiClient();
        logoLogin = LogoLogin;
    }





    public void googleSignIn(FragmentActivity activity) {
//       googleLoginMannager.googleSignIn(activity);
    }
    public void googlelogout(){
        googleLoginMannager.signOut();
    }

    public void setLoginComplete(LoginComplete loginComplete) {
        this.loginComplete = loginComplete;
    }

    public interface LoginComplete {
        void callComplete();
    }

    public void Login() {

        if (!ContextUtil.isNetWorking(this)) {
            displayToast(getResources().getString(R.string.tip_net_fault));
            return;
        }

        startLoading();
        new UserNetCallBack(this, new UserNetCallBack.CallBack() {

            @Override
            public void callBackLister(int state, UserInfoEntity entity) {

                stopLoading();
                if (state == UserNetCallBack.SUCCESS) {

                    entity.setAccount(editUsername.getText().toString());
                    entity.setPwd(editPwd.getText().toString());
                    CmsApplication.setUserInfoEntity(entity, BaseLoginActivity.this);
                    AccountManager.getInstance().setMid(entity.getMID());
                    AccountManager.getInstance().setAccount(editUsername.getText().toString());
                    AccountManager.getInstance().setPwd(editPwd.getText().toString());
                    AccountManager.getInstance().setIsStayLogin(String.valueOf(true));
                    AccountManager.getInstance().reset(false);
                    ChannelDataManager.getInstance(BaseLoginActivity.this).updateSQLChannel(ChannelDataManager.ChannelType_News,null);
                    HeadUtils.resetHeadBitmap();
//                    new UserNetCallBack(BaseLoginActivity.this, null).loadLoginTrigger(CmsApplication.getUserToken());
                    new ConfigDao(BaseLoginActivity.this).updateAccoutAndPwd(editUsername.getText().toString(),
                            Base64.encode(editPwd.getText().toString().getBytes()));
                    if (Constant.WELCOME_LOGIN_TYPE.equals(logoLogin)) {
                        setResult(Constant.WELCOME_Login_OK);
                    }
                    if (loginComplete != null) {
                        loginComplete.callComplete();
                    } else {
                        setExitSwichLayout();
                    }
                    return;
                }

                if (state == UserNetCallBack.FAIL) {
                    displayToast(getResources().getString(R.string.tip_login_failure));
                    return;
                }
//                displayToast(HttpCode.getLoginErrorMsg(BaseLoginActivity.this, state));

            }
        }).loadLogin(editUsername.getText()
                .toString(), editPwd.getText().toString(), UserInfoEntity.UserType_Accout, null);
    }

    //Member Info
    Profile profile;

    public void entryForgetPassword() {
        Intent intent = new Intent(this, ValidateEmailActivity.class);
        intent.putExtra(MemberConfig.Key, MemberConfig.Forgot_LoginPwd);
        startActivity(intent);
        setEnterSwichLayout();
    }

    @Override
    public void facebookLoginSuccess(LoginResult loginResult) {
        AccessToken access_token = loginResult.getAccessToken();
        String token = access_token.getToken();
        try {
            CmsApplication.setUserToken("Bearer " + token);
            TLog.e("abc", "savetoken---" + token);

            otherLogin(token, "facebook");
        } catch (Exception e) {
//            displayToast(e.toString());
        }

    }

    @Override
    public void facebookLoginCancel() {
        displayToast(" FaceBook Login Cancel ");
    }

    @Override
    public void facebookLoginError(FacebookException error) {
//        displayToast("Facebook Login error: "+error.toString());

        TLog.e("aa", error.toString());
        displayToast(getResources().getString(R.string.tip_login_failure));
    }

    private void otherLogin(final String token, final String loginType) {

        startLoading();

        JsonLoader loader = new JsonLoader(this);
        loader.setUrl(SettingUrl.getOtherLoginUrl());
        loader.setUrlName(MemberConfig.Other_Login);
        loader.setType(BaseNetLoder.APPLICATION_JSON);
        loader.setPostData(SettingManager.getOtherLoginPostData(loginType, token, Installation.getDeviceId(this)));
        loader.setMethod(BaseNetLoder.Method_Post);
        loader.setLoaderListener(new Loader.LoaderListener() {

            @Override
            public void onProgress(Object tag, long curByteNum,
                                   long totalByteNum) {
            }

            @Override
            public void onError(Object tag, int responseCode,
                                String errorMessage) {
                stopLoading();
                displayToast(getResources().getString(R.string.tip_login_failure));
            }

            @Override
            public void onCompelete(Object tag, Object result) {
                // TODO Auto-generated method stub
                stopLoading();

                try {
                    JSONArray array = (JSONArray) result;
                    System.out.println(MemberConfig.Other_Login + "  : " + result);
                    StateEnties stateEnties = SettingManager.getOtherLoginResult(array);
                    JSONObject object = array.getJSONObject(0);
                    JSONObject dataobj = object.getJSONObject("data");
                    if (JsonParse.getState200(object) && dataobj.has("username")) {
                        new ConfigDao(BaseLoginActivity.this).updateOtherToken(token, String.valueOf(true));
                        loadMemInfo(UserInfoEntity.UserType_Three);

                    } else {
                        displayToast(getResources().getString(R.string.tip_login_failure));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    displayToast(getResources().getString(R.string.tip_login_failure));
                }catch (ClassCastException e){
                    e.printStackTrace();
                    displayToast(getResources().getString(R.string.tip_login_failure));
                }
            }
        });
        CmsApplication.getDataStratey().startLoader(loader);
    }

    //    public void setLanguage(String language) {
//        if (!Utils.isStringEmpty(language))
//        {
//            SwitchLanguageUtils.selectLanguage(this,language);
//            intent.putExtra(Define.TabId,3);
//            startActivityForResult(intent,MemberConfig.Login_Success);
//            finish();
//        }
//    }
    private void loadMemInfo(int UserType) {
        startLoading();

        new UsInfoCallBack(this, new UsInfoCallBack.CallBack() {
            @Override
            public void callBack(int state, UserInfoEntity entity) {

                stopLoading(loadingBar);

                if (UsInfoCallBack.SUCCESS == state) {
                    CmsApplication.setUserInfoEntity(entity, BaseLoginActivity.this);
                    if (entity.getUserType() != UserInfoEntity.UserType_Three) {
                    }
                    if (loginComplete != null) {
                        loginComplete.callComplete();
                    } else {
                        if (editUsername != null){
                            setExitSwichLayout();
                        }
                    }
                    if (Constant.WELCOME_LOGIN_TYPE.equals(logoLogin)) {
                        setResult(Constant.WELCOME_Login_OK);
                    }
                } else {
                    displayToast(getResources().getString(R.string.tip_login_failure));
                    return;
                }
            }
        }).loadMemberInfo(UserType);
    }

    public void facebookLogin() {
        faceBookLogin.logout();
        faceBookLogin.login();
    }

    public void startLoading() {
        loadingBar.setVisibility(View.VISIBLE);
        loadingBar.start();
    }

    public void stopLoading() {
        if (loadingBar != null) {
            loadingBar.stop();
            loadingBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (mGoogleApiClient != null) {
//            mGoogleApiClient.connect();
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if (mGoogleApiClient != null) {
//            if (mGoogleApiClient.isConnected()) {
//                mGoogleApiClient.disconnect();
//            }
//        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (faceBookLogin != null){
            faceBookLogin.getCallbackManager().onActivityResult(requestCode, resultCode, data);
        }
//        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            handleSignInResult(result);
//        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        TLog.e("robin", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            TLog.e("robin", "Success");
            GoogleSignInAccount acct = result.getSignInAccount();

            if (acct != null) {
                //之后就可以更新UI了
//                if (!Utils.isStringEmpty(googleLoginMannager.getAccessToken())){
//                    CmsApplication.setUserToken("Bearer " + googleLoginMannager.getAccessToken());
//                    otherLogin(googleLoginMannager.getAccessToken(), "google");
//                    return;
//                }
                TLog.e("request","aci===="+acct.getServerAuthCode());
//                googleLoginMannager.getGoogleToken(acct.getServerAuthCode());
//                TLog.e("robin", acct.getIdToken().length() + "---IdToken:" + acct.getIdToken());
//                CmsApplication.setUserToken("Bearer " + acct.getIdToken());
//                otherLogin(acct.getIdToken(), "google");
            }
        } else {
            TLog.i("robin", "shibai" + result.getStatus());
            displayToast(getResources().getString(R.string.tip_login_failure));
        }
    }

}
