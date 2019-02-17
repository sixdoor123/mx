package com.baiyi.jj.app.manager.facebook;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * FaceBook Encapsulation
 * Created by Administrator on 2016/9/23.
 */

public class FaceBookLogin {

    private Context activity;
    private CallbackManager callbackManager;
    private FacebookListener facebookListener;
    private List<String> permissions = Collections.<String>emptyList();
    private LoginManager loginManager;

    private String testImgUrl = "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1474615353&di=7b69da9763c575d1b1a23464d79ce423&src=http://img2.a0bi.com/upload/ttq/20140709/1404879192218_middle.jpg";

    public static final int SHARE_REQUEST_CODE = 10010;

    private static FaceBookLogin faceBookLogin = null;

    public static FaceBookLogin getInstence(Context activity) {
        if (null == faceBookLogin) {
            synchronized (activity) {
                if (null == faceBookLogin) {
                    faceBookLogin = new FaceBookLogin(activity);
                }
            }
        }

        return faceBookLogin;
    }

    private FaceBookLogin(Context activity) {
        this.activity = activity;

        //��ʼ��facebook��¼����
        callbackManager = CallbackManager.Factory.create();
        getLoginManager().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // login success
//                AccessToken accessToken = loginResult.getAccessToken();
//                getLoginInfo(accessToken);
                if (facebookListener != null) {
                    facebookListener.facebookLoginSuccess(loginResult);
                }
            }

            @Override
            public void onCancel() {
                //ȡ����¼
                if (facebookListener != null) {
                    facebookListener.facebookLoginCancel();
                }
            }

            @Override
            public void onError(FacebookException error) {
                if (error instanceof FacebookAuthorizationException) {
                    if (AccessToken.getCurrentAccessToken() != null) {
                        LoginManager.getInstance().logOut();
                        return;
                    }
                }
                if (facebookListener != null) {
                    facebookListener.facebookLoginError(error);
                }
            }
        });

        permissions = Arrays
                .asList("email", "user_likes", "user_status", "user_photos", "user_birthday", "public_profile", "user_friends");
    }

    /**
     * ��¼
     */
    public void login() {
        getLoginManager().logInWithReadPermissions(
                (Activity) activity, permissions);

    }

    /**
     * �˳�
     */
    public void logout() {
        String logout = activity.getResources().getString(
                com.facebook.R.string.com_facebook_loginview_log_out_action);
        String cancel = activity.getResources().getString(
                com.facebook.R.string.com_facebook_loginview_cancel_action);
        String message;
        Profile profile = Profile.getCurrentProfile();
        if (profile != null && profile.getName() != null) {
            message = String.format(
                    activity.getResources().getString(
                            com.facebook.R.string.com_facebook_loginview_logged_in_as),
                    profile.getName());
        } else {
            message = activity.getResources().getString(
                    com.facebook.R.string.com_facebook_loginview_logged_in_using_facebook);
        }
        getLoginManager().logOut();
        faceBookLogin = null;
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        builder.setMessage(message)
//                .setCancelable(true)
//                .setPositiveButton(logout, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        getLoginManager().logOut();
//                    }
//                })
//                .setNegativeButton(cancel, null);
//        builder.create().show();
    }

    /**
     * ��ȡloginMananger
     *
     * @return
     */
    private LoginManager getLoginManager() {
        if (loginManager == null) {
            loginManager = LoginManager.getInstance();
        }
        return loginManager;
    }

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }

    /**
     * ���õ�¼������
     *
     * @param facebookListener
     */
    public void setFacebookListener(FacebookListener facebookListener) {
        this.facebookListener = facebookListener;
    }



    public interface FacebookListener {
        void facebookLoginSuccess(LoginResult loginResult);

        void facebookLoginCancel();

        void facebookLoginError(FacebookException error);

//        void facebookLoginInfo(Bundle bundle);
    }


    private ShareDialog shareDialog = null;
    private ShareLinkContent linkContent = null;

    /**
     * ""
     *
     * @param activity
     * @param newsUrl
     * @param title
     * @param desc
     * @param shareCallback
     */
    public void shareUrl(Activity activity, String newsUrl, String title,
                         String desc, String imgUrl) {

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            if (shareDialog == null) {
                shareDialog = new ShareDialog(activity);
                linkContent = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse(newsUrl))
                        .setContentTitle(title)
                        .setContentDescription(desc)
                        .setImageUrl(Uri.parse(imgUrl))
                        .build();
            }
//           shareDialog.registerCallback(getCallbackManager(), shareCallback, FaceBookLogin.SHARE_REQUEST_CODE);
            shareDialog.show(linkContent);

        }
    }
}
