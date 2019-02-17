package com.baiyi.jj.app.manager;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.renderscript.RenderScript;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import com.baiyi.core.file.Preference;
import com.baiyi.core.loader.ImageLoader;
import com.baiyi.core.loader.Loader;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.BaseAnalyticsActivity;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.utils.XMLName;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.turbo.turbo.mexico.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/6/6 0006.
 */
public class NotifactionManager {

    public static int tem_noimg = 0;
    public static int tem_small_one = 1;
    public static int tem_small_three = 3;
    public static int tem_big_one = 2;
    public static int tem_video = 5;
    public static int tem_facebook = 6;
    public static int tem_youtube = 7;
    public static final String packageStr = "com.turbo.turbo.mexico";

    public static void sendNotifica(Context context, Tracker tracker,int type, String title, String content, String url, String artid, String imgurl, String temp, boolean isShowT, String callbackArray) {


        Preference preference = Preference.getInstance();
        long lasttime = preference.getLong(XMLName.XML_LastNotiTime,0);
        long currentTime = System.currentTimeMillis();
        TLog.e("acs",currentTime+"---------"+lasttime);
        if (currentTime - lasttime < 10000){
            return;
        }
        preference.Set(XMLName.XML_LastNotiTime,String.valueOf(System.currentTimeMillis()));
        preference.saveConfig();

        if (!Utils.isStringEmpty(artid) && tracker != null) {
            TLog.e("MyGCMListener", "articls-------------------------" + artid);
            tracker.send(new HitBuilders.EventBuilder()
                    .setCategory(BaseAnalyticsActivity.Envent_Cage_1)
                    .setValue(1)
                    .setLabel(artid)
                    .setAction(BaseAnalyticsActivity.Envent_ReceiveNotif)
                    .build());
            String userid = preference.Get(XMLName.XML_UserID,"noid");
            TLog.e("useridss",userid+"--");
            if (!Utils.isStringEmpty(userid)){
                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory(BaseAnalyticsActivity.Envent_Cage_1)
                        .setValue(1)
                        .setLabel(userid)
                        .setAction(BaseAnalyticsActivity.Envent_ReceiveNotiUser)
                        .build());
            }

        }

        int templete = 0;
        try {
            templete = Integer.parseInt(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isShowT && (Utils.isStringEmpty(imgurl) || templete == tem_noimg)) {
            sendNotification(context,type, title, content, url, artid,String.valueOf(templete),callbackArray);
        } else {
//            getImage(context, title, content, url, artid, imgurl, isShowT, templete,callbackArray);
            if (callbackArray != null){
//                String imgUrl = "http://lumen-mx.fission.arityapp.com/12fd8908-55bb-11e7-b6bccf2cfc5c.jpg?thumbnail/3/690x459/quality/75";
                getImage2(context,type, title, content, url, artid, imgurl, isShowT, templete,callbackArray);
            }
//            else {
//                getImage(context, title, content, url, artid, imgurl, isShowT, templete,callbackArray);
//            }

        }

    }

    private static void sendNoticiaMore(Context context,int type, String title, String content, String url, String artid, int templete, boolean isShowT, Bitmap bitmap2,String callbackArray) {
        Intent launchIntent = new Intent(context, NotificationReceiver.class);
        Bundle args = new Bundle();
        args.putString(Define.ArticleUrl, url);
        args.putString(Define.ArticleId, artid);
        args.putString(Define.ArticleSource, title);
        args.putString(Define.ArticleTitle, content);
        args.putString(Define.ArticleTemp,String.valueOf(templete));
        args.putString(Define.CallbackArray,callbackArray);
        args.putString(Define.NoticaType,String.valueOf(type));
        launchIntent.putExtra(Define.EXTRA_BUNDLE, args);

        int requestCode = (int) SystemClock.uptimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode /* Request code */, launchIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_notifiction);
        remoteViews.setTextViewText(R.id.noti_title, title);
        remoteViews.setTextViewText(R.id.noti_content, content);
        remoteViews.setTextViewText(R.id.noti_content2line, content);
        remoteViews.setTextViewText(R.id.noti_time, Utils.getCurrentTime3(System.currentTimeMillis()));
        if (templete == tem_video) {
            remoteViews.setViewVisibility(R.id.img_play_icon, View.VISIBLE);
        }
        if (bitmap2 != null) {
            remoteViews.setImageViewBitmap(R.id.img_left, bitmap2);
        } else {
            remoteViews.setViewVisibility(R.id.img_left, View.GONE);
            remoteViews.setViewVisibility(R.id.img_iconlogo, View.VISIBLE);
            remoteViews.setViewVisibility(R.id.icon_smallright, View.GONE);
        }
        if (!isShowT) {
            remoteViews.setViewVisibility(R.id.noti_title, View.GONE);
            remoteViews.setViewVisibility(R.id.noti_content, View.GONE);
            remoteViews.setViewVisibility(R.id.noti_time, View.GONE);
            remoteViews.setViewVisibility(R.id.noti_content2line, View.VISIBLE);
        }
        if (ContextUtil.isDarkNotificationTheme(context)) {
//            TLog.e("theme","true------------");
            remoteViews.setTextColor(R.id.noti_time, Color.WHITE);
            remoteViews.setTextColor(R.id.noti_content, Color.WHITE);
            remoteViews.setTextColor(R.id.noti_title, Color.WHITE);
            remoteViews.setTextColor(R.id.noti_content2line, Color.WHITE);
        }


//        remoteViews.setInt(R.id.close_iv, "setColorFilter", getIconColor());

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_sns_logo);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setContent(remoteViews)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.icon_sms3x)
                .setLargeIcon(bitmap)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
//                .setColor(context.getResources().getColor(R.color.day_text_color_smallicon))
                .setPriority(100)
                .setContentIntent(pendingIntent);

        int id = 0;
        try {
            id = Integer.parseInt(artid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id /* ID of notification */, mBuilder.build());
    }


    /**
     * Create and show a simple notification containing the received GCM me ssage.
     *
     * @param message GCM message received.
     */
    private static void sendNotification(Context context,int type, String title, String message, String url, String artiId,String temp,String callbackArray) {
        Intent launchIntent = new Intent(context, NotificationReceiver.class);
        Bundle args = new Bundle();
        args.putString(Define.ArticleUrl, url);
        args.putString(Define.ArticleId, artiId);
        args.putString(Define.ArticleSource, title);
        args.putString(Define.ArticleTitle, message);
        args.putString(Define.CallbackArray,callbackArray);
        args.putString(Define.ArticleTemp,temp);
        args.putString(Define.NoticaType,String.valueOf(type));
        launchIntent.putExtra(Define.EXTRA_BUNDLE, args);
        int requestCode = (int) SystemClock.uptimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode /* Request code */, launchIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_sns_logo);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.icon_sms3x)
                .setLargeIcon(bitmap)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
//                .setColor(context.getResources().getColor(R.color.day_text_color_smallicon))
                .setPriority(100)
                .setContentIntent(pendingIntent);

        int id = 0;
        try {
            id = Integer.parseInt(artiId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id /* ID of notification */, notificationBuilder.build());
//        notificationManager.notify(0,notification);
    }

    private static void sendBigNotification(Context context,int type, String title, String message, String url, String artiId, Bitmap bitmap,String templete,String callbackArray) {

        Intent launchIntent = new Intent(context, NotificationReceiver.class);
        Bundle args = new Bundle();
        args.putString(Define.ArticleUrl, url);
        args.putString(Define.ArticleId, artiId);
        args.putString(Define.ArticleSource, title);
        args.putString(Define.ArticleTitle, message);
        args.putString(Define.ArticleTemp,String.valueOf(templete));
        args.putString(Define.CallbackArray,callbackArray);
        args.putString(Define.NoticaType,String.valueOf(type));
        launchIntent.putExtra(Define.EXTRA_BUNDLE, args);
        int requestCode = (int) SystemClock.uptimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode /* Request code */, launchIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        android.support.v7.app.NotificationCompat.BigPictureStyle picStyle = new android.support.v7.app.NotificationCompat.BigPictureStyle();
        picStyle.bigPicture(bitmap);
        picStyle.setBigContentTitle(title);
        picStyle.setSummaryText(message);

        Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_sns_logo);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.icon_sms3x)
                .setLargeIcon(bitmap2)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
//                .setColor(context.getResources().getColor(R.color.day_text_color_smallicon))
                .setContentIntent(pendingIntent)
                .setPriority(100)
                .setStyle(picStyle);


        int id = 0;
        try {
            id = Integer.parseInt(artiId);
        } catch (Exception e) {
            e.printStackTrace();
            id = 0;
        }
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id /* ID of notification */, notificationBuilder.build());
//        notificationManager.notify(0,notification);
    }

    private static void getImage2(final Context context, final int type, final String title, final String content, final String url, final String artid, final String imgUrl, final boolean isShowT, final int templete, final String array){

        if (Utils.isStringEmpty(imgUrl) || templete == tem_noimg) {
            sendNoticiaMore(context,type, title, content, url, artid, templete, isShowT, null,array);
            return;
        }

        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                try {
                    Bitmap bitmap = Glide.with(context)
                            .load(imgUrl)
                            .asBitmap()
                            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();
                    return bitmap;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            @Override
            protected void onPostExecute(Bitmap result) {
                super.onPostExecute(result);
                if (result != null) {
                    if (templete == tem_big_one) {
                        sendBigNotification(context,type, title, content, url, artid, result,String.valueOf(templete),array);
                    } else {
                        sendNoticiaMore(context,type, title, content, url, artid, templete, isShowT, result,array);
                    }

                } else {
                    sendNoticiaMore(context,type, title, content, url, artid, templete, isShowT, result,array);
                }
            }
        }.execute(imgUrl);
    }

//    public static void getImage(final Context context, final String title, final String content, final String url, final String artid, final String imgUrl, final boolean isShowT, final int templete, final String array) {
//        if (Utils.isStringEmpty(imgUrl) || templete == tem_noimg) {
//            sendNoticiaMore(context, title, content, url, artid, templete, isShowT, null,array);
//            return;
//        }
//
//        new AsyncTask<String, Void, Bitmap>() {
//            @Override
//            protected Bitmap doInBackground(String... params) {
//                try {
//                    URL url = new URL(params[0]);
//                    HttpURLConnection conn = (HttpURLConnection) url
//                            .openConnection();
//                    conn.setConnectTimeout(6000);// ���ó�ʱ
//                    conn.setDoInput(true);
//                    conn.setUseCaches(false);// ������
//                    conn.connect();
//                    int code = conn.getResponseCode();
//                    Bitmap bitmap = null;
//                    if (code == 200) {
//                        InputStream is = conn.getInputStream();// ���ͼƬ��������
//                        bitmap = BitmapFactory.decodeStream(is);
//                    }
//
//                    return bitmap;
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                    return null;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return null;
//                }
//            }
//            @Override
//            protected void onPostExecute(Bitmap result) {
//                super.onPostExecute(result);
//                if (result != null) {
//                    if (templete == tem_big_one) {
//                        sendBigNotification(context, title, content, url, artid, result,String.valueOf(templete),array);
//                    } else {
//                        sendNoticiaMore(context, title, content, url, artid, templete, isShowT, result,array);
//                    }
//                } else {
//                    sendNoticiaMore(context, title, content, url, artid, templete, isShowT, result,array);
//                }
//            }
//        }.execute(imgUrl);
//    }

    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            TLog.e("topname", topActivity.getClassName() + "======sss" + topActivity.getPackageName());
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isForeground(Context context) {
        // Gets a list of running processes.
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();

        // On some versions of android the first item in the list is what runs in the foreground,
        // but this is not true on all versions.  Check the process importance to see if the app
        // is in the foreground.
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : tasks) {
            if (ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND == appProcess.importance
                    && packageName.equals(appProcess.processName)) {
                return true;
            }
        }
        return false;
    }

    public static void updateUserAWS(String userid,String devicetoken,String endpointarn){
        OkHttpClient client = new OkHttpClient.Builder().build();
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                getPostData(userid,devicetoken,endpointarn));
        Request request = new Request.Builder()
                .post(requestBody)
                .url(NetUtils.getUpdateAWS())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String strJson = response.body().string();
                TLog.e("str","---"+strJson);
            }
        });
    }
    private static String getPostData(String userid,String devicetoken,String endpointarn) {
        JSONObject object = new JSONObject();
        try {
            object.put("user_id", userid);
            object.put("device_token", devicetoken);
            object.put("arn", endpointarn);
        } catch (JSONException e) {
            e.toString();
        }
        return object.toString();
    }

    public static NotificationCompat.Builder getSmallBuidler(Context context, String title, String content, String url, String artid, int templete, boolean isShowT, Bitmap bitmap2) {
        Intent launchIntent = new Intent(context, NotificationReceiver.class);
        Bundle args = new Bundle();
        args.putString(Define.ArticleUrl, url);
        args.putString(Define.ArticleId, artid);
        args.putString(Define.ArticleTitle, content);
//        args.putString(Define.CallbackArray,callbackArray);
        launchIntent.putExtra(Define.EXTRA_BUNDLE, args);

        int requestCode = (int) SystemClock.uptimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode /* Request code */, launchIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_notifiction);
        remoteViews.setTextViewText(R.id.noti_title, title);
        remoteViews.setTextViewText(R.id.noti_content, content);
        remoteViews.setTextViewText(R.id.noti_content2line, content);
        remoteViews.setTextViewText(R.id.noti_time, Utils.getCurrentTime3(System.currentTimeMillis()));
        if (templete == tem_video) {
            remoteViews.setViewVisibility(R.id.img_play_icon, View.VISIBLE);
        }
        if (bitmap2 != null) {
            remoteViews.setImageViewBitmap(R.id.img_left, bitmap2);
        } else {
            remoteViews.setViewVisibility(R.id.img_left, View.GONE);
            remoteViews.setViewVisibility(R.id.img_iconlogo, View.VISIBLE);
            remoteViews.setViewVisibility(R.id.icon_smallright, View.GONE);
        }
        if (!isShowT) {
            remoteViews.setViewVisibility(R.id.noti_title, View.GONE);
            remoteViews.setViewVisibility(R.id.noti_content, View.GONE);
            remoteViews.setViewVisibility(R.id.noti_time, View.GONE);
            remoteViews.setViewVisibility(R.id.noti_content2line, View.VISIBLE);
        }
        if (ContextUtil.isDarkNotificationTheme(context)) {
//            TLog.e("theme","true------------");
            remoteViews.setTextColor(R.id.noti_time, Color.WHITE);
            remoteViews.setTextColor(R.id.noti_content, Color.WHITE);
            remoteViews.setTextColor(R.id.noti_title, Color.WHITE);
            remoteViews.setTextColor(R.id.noti_content2line, Color.WHITE);
        }


//        remoteViews.setInt(R.id.close_iv, "setColorFilter", getIconColor());

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_sns_logo);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setContent(remoteViews)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.icon_sms2x)
                .setLargeIcon(bitmap)
                .setContentTitle(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        return mBuilder;

    }

    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getString(metaKey);
            }
        } catch (PackageManager.NameNotFoundException e) {
//            TLog.e(TAG, "error " + e.getMessage());
        }
        return apiKey;
    }

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static boolean checkPlayServices(Activity context) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                if (Preference.getInstance().getBoolean(XMLName.XML_AWS_First_servrce, true)) {
                    apiAvailability.getErrorDialog(context, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                            .show();
                    Preference.getInstance().Set(XMLName.XML_AWS_First_servrce, String.valueOf(false));
                    Preference.getInstance().saveConfig();
                }
            } else {
                TLog.i("aws", "This device is not supported.");
            }
            return false;
        }
        return true;
    }

    public static void updatePlayId(String userid,String playid){
        OkHttpClient client = new OkHttpClient.Builder().build();
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                getPlayIdPost(userid,playid));
        Request request = new Request.Builder()
                .post(requestBody)
                .url(NetUtils.getUpdatePlayId())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String strJson = response.body().string();
                TLog.e("str-playid","---"+strJson);
            }
        });
    }
    private static String getPlayIdPost(String userid,String playid) {
        JSONObject object = new JSONObject();
        try {
            object.put("user_id", userid);
            object.put("player_id", playid);
        } catch (JSONException e) {
            e.toString();
        }
        return object.toString();
    }
}
