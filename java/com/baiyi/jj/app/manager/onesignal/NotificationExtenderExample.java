package com.baiyi.jj.app.manager.onesignal;

import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.manager.NotifactionManager;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.google.android.gms.analytics.Tracker;
import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationDisplayedResult;
import com.onesignal.OSNotificationReceivedResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;

/**
 * Created by lzl on 2017/6/7.
 */

public class NotificationExtenderExample extends NotificationExtenderService {
    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult receivedResult) {

//        Log.e("data22","--"+receivedResult.payload.rawPayload);
//        TLog.e("1111","send------------------------------------------------------------------9");
        try {
            JSONObject data = receivedResult.payload.additionalData;
            if (data != null){
                JSONArray array = new JSONArray("["+receivedResult.payload.rawPayload+"]");
                getData(data,array);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
//        OverrideSettings overrideSettings = new OverrideSettings();
//        overrideSettings.extender = new NotificationCompat.Extender() {
//            @Override
//            public NotificationCompat.Builder extend(NotificationCompat.Builder builder) {
//                // Sets the background notification color to Green on Android 5.0+ devices.
//                return builder.setColor(new BigInteger("00000000", 16).intValue());
//
//            }
//        };
//
//        OSNotificationDisplayedResult displayedResult = displayNotification(overrideSettings);
//        Log.d("OneSignalExample", "Notification displayed with id: " + displayedResult.androidNotificationId);
        return true;
    }
    private void getData(JSONObject data, JSONArray array){
        TLog.e("jsondata","00--"+data.toString());
        String title = data.optString("title","");
        String url = data.optString("url","");
        String abstractStr = data.optString("abstract","");
        String msgid = data.optString("id","0");
        String template = data.optString("template","0");
        String show = data.optString("show","true");
        String imgurls = data.optString("image","");
        String iimgurls = "";
        int type = data.optInt("type",0);
        if (!Utils.isStringEmpty(imgurls)){
            try {
                JSONObject object2 = new JSONObject(imgurls);
                iimgurls = object2.getString("url");
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        if (Utils.isStringEmpty(title) || Utils.isStringEmpty(abstractStr)){
            return;
        }
        sendMsg(type,title,abstractStr,url,msgid,iimgurls,template,show.equals("true"),array);



    }
    private void sendMsg(int type,String title, String content, String url, String msgid, String imgurl, String templete, boolean isShowT, JSONArray array){
        CmsApplication application = (CmsApplication) getApplication();
        Tracker mTracker = null;
        if (application != null){
            mTracker = application.getDefaultTracker();
        }
//        TLog.e("array","-"+array.toString());
        if (Utils.isStringEmpty(msgid)){
            return;
        }
        NotifactionManager.sendNotifica(getApplicationContext(),mTracker,type,title,content,url,msgid,imgurl,templete,isShowT,array.toString());

//        OneSignal.handleNotificationOpen(getApplicationContext(),array,false);
    }
}
