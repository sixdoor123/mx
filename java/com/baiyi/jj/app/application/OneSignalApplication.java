package com.baiyi.jj.app.application;

import android.util.Log;

import com.baiyi.jj.app.utils.TLog;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/6/7 0007.
 */
public class OneSignalApplication extends AnalyticsApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        OneSignal.startInit(this)
                .autoPromptLocation(false)
                .setNotificationReceivedHandler(new ExampleNotificationReceivedHandler())
                .setNotificationOpenedHandler(new ExampleNotificationOpenedHandler())
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.None)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }



    private class ExampleNotificationReceivedHandler implements OneSignal.NotificationReceivedHandler {
        @Override
        public void notificationReceived(OSNotification notification) {
            JSONObject data = notification.payload.additionalData;
            String notificationID = notification.payload.notificationID;
            String title = notification.payload.title;
            String body = notification.payload.body;
            String smallIcon = notification.payload.smallIcon;
            String largeIcon = notification.payload.largeIcon;
            String bigPicture = notification.payload.bigPicture;
            String smallIconAccentColor = notification.payload.smallIconAccentColor;
            String sound = notification.payload.sound;
            String ledColor = notification.payload.ledColor;
            int lockScreenVisibility = notification.payload.lockScreenVisibility;
            String groupKey = notification.payload.groupKey;
            String groupMessage = notification.payload.groupMessage;
            String fromProjectNumber = notification.payload.fromProjectNumber;
            String rawPayload = notification.payload.rawPayload;

            TLog.e("abc",title+"=========="+body+"===="+smallIcon+"===="+bigPicture);

        }
    }


    private class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            TLog.e("open","--open-------");
        }
    }
}
