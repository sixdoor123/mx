//package com.baiyi.jj.wbshare;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;
//
//import com.sina.weibo.sdk.auth.Oauth2AccessToken;
//
///**
// * ���ඨ����΢����Ȩʱ����Ҫ�Ĳ�����
// *
// * @author SINA
// * @since 2013-10-07
// */
//
//public class AccessTokenKeeper {
//
//	 private static final String PREFERENCES_NAME = "com_weibo_sdk_android";
//
//	    private static final String KEY_UID           = "uid";
//	    private static final String KEY_ACCESS_TOKEN  = "access_token";
//	    private static final String KEY_EXPIRES_IN    = "expires_in";
//	    private static final String KEY_REFRESH_TOKEN    = "refresh_token";
//
//	    /**
//	     * ���� Token ���� SharedPreferences��
//	     *
//	     * @param context Ӧ�ó��������Ļ���
//	     * @param token   Token ����
//	     */
//	    public static void writeAccessToken(Context context, Oauth2AccessToken token) {
//	        if (null == context || null == token) {
//	            return;
//	        }
//
//	        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
//	        Editor editor = pref.edit();
//	        editor.putString(KEY_UID, token.getUid());
//	        editor.putString(KEY_ACCESS_TOKEN, token.getToken());
//	        editor.putString(KEY_REFRESH_TOKEN, token.getRefreshToken());
//	        editor.putLong(KEY_EXPIRES_IN, token.getExpiresTime());
//	        editor.commit();
//	    }
//
//	    /**
//	     * �� SharedPreferences ��ȡ Token ��Ϣ��
//	     *
//	     * @param context Ӧ�ó��������Ļ���
//	     *
//	     * @return ���� Token ����
//	     */
//	    public static Oauth2AccessToken readAccessToken(Context context) {
//	        if (null == context) {
//	            return null;
//	        }
//
//	        Oauth2AccessToken token = new Oauth2AccessToken();
//	        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
//	        token.setUid(pref.getString(KEY_UID, ""));
//	        token.setToken(pref.getString(KEY_ACCESS_TOKEN, ""));
//	        token.setRefreshToken(pref.getString(KEY_REFRESH_TOKEN, ""));
//	        token.setExpiresTime(pref.getLong(KEY_EXPIRES_IN, 0));
//
//	        return token;
//	    }
//
//	    /**
//	     * ��� SharedPreferences �� Token��Ϣ��
//	     *
//	     * @param context Ӧ�ó��������Ļ���
//	     */
//	    public static void clear(Context context) {
//	        if (null == context) {
//	            return;
//	        }
//
//	        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
//	        Editor editor = pref.edit();
//	        editor.clear();
//	        editor.commit();
//	    }
//
//}
