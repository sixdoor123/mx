package com.baiyi.jj.app.manager.googleauth;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.baiyi.jj.app.manager.NotifactionManager;
import com.baiyi.jj.app.utils.TLog;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.turbo.turbo.mexico.R;

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
 * Created by Administrator on 2017/4/10 0010.
 */
public class GoogleLoginMannager {


    private GoogleApiClient mGoogleApiClient;
    private static int RC_SIGN_IN = 10001;
    
    private static GoogleLoginMannager googleLogin = null;

    private String accessToken;


    public static GoogleLoginMannager getInstence(FragmentActivity activity) {
        if (null == googleLogin) {
            synchronized (activity) {
                if (null == googleLogin) {
                    googleLogin = new GoogleLoginMannager(activity);
                }
            }
        }

        return googleLogin;
    }

    private GoogleLoginMannager(FragmentActivity activity) {
        final String clintId = NotifactionManager.getMetaValue(activity, "google_webclint_id");
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
                .requestId().requestIdToken(clintId).build();
        mGoogleApiClient = new GoogleApiClient.Builder(activity).
                addConnectionCallbacks(connectionCallbacks).
                addOnConnectionFailedListener(failedListener).
                enableAutoManage(activity, failedListener)/* FragmentActivity *//* OnConnectionFailedListener */.
                addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        getAccessToken(activity);
    }

    public void googleSignIn(Activity activity) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public GoogleApiClient getmGoogleApiClient() {
        return mGoogleApiClient;
    }

    public void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // ...
                    }
                });
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void getAccessToken(final Activity activity) {
        TLog.e("abc","token----------");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    String oAuthScopes = "oauth2:";
                    oAuthScopes += " https://www.googleapis.com/auth/userinfo.profile";
                    android.accounts.AccountManager manager = android.accounts.AccountManager.get(activity);
                    Account[] accounts = manager.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
                    if (accounts == null || accounts.length == 0) {
                        return;
                    }
                    for (int i = 0;i<accounts.length;i++){
                        Account accountss = accounts[i];
                        TLog.e("acc"+i,accountss.name+"===");
                    }
                    String Token = GoogleAuthUtil.getToken(activity, accounts[0].name, oAuthScopes);
                    accessToken  = Token;
                    TLog.e("abc", "------" + accessToken);
                    //ya29.GlwqBFmaeCTOp8OlqGBjFDENPrQlUxGRrRyHtI1JuMCI74K4LTjR_s9740Jek1p46BKhYMFXhCCTeVXWfaHAC6nbMNOYj1K4f0zDWR7NWiHEnCWnArDtX6PtPl8F2g

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    GoogleApiClient.ConnectionCallbacks connectionCallbacks = new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(@Nullable Bundle bundle) {

        }

        @Override
        public void onConnectionSuspended(int i) {

        }
    };

    GoogleApiClient.OnConnectionFailedListener failedListener = new GoogleApiClient.OnConnectionFailedListener() {
        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        }
    };

    public void getGoogleToken(String authcode){
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("grant_type", "authorization_code")
                .add("client_id", "894590119585-sv61kkd1d1jshh81vgad4lc0dsvglr4q.apps.googleusercontent.com")
                .add("client_secret", "{clientSecret}")
                .add("redirect_uri","")
                .add("code", authcode)
                .build();
        final Request request = new Request.Builder()
                .url("https://www.googleapis.com/oauth2/v4/token")
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    TLog.e("response",response.body().string());
                    JSONObject jsonObject = new JSONObject(response.body().string());
//                    final String message = jsonObject.toString(5);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
