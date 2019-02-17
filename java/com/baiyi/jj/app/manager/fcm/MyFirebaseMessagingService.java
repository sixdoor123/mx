//
//package com.baiyi.jj.app.manager.fcm;
//
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.preference.PreferenceManager;
//import android.support.v4.app.NotificationCompat;
//import android.util.Log;
//
//import com.baiyi.jj.app.activity.BaseAnalyticsActivity;
//import com.baiyi.jj.app.application.CmsApplication;
//import com.baiyi.jj.app.manager.NotifactionManager;
//import com.baiyi.jj.app.utils.TLog;
//import com.baiyi.jj.app.utils.Utils;
//import com.baiyi.jj.app.utils.XMLName;
//import com.google.android.gms.analytics.HitBuilders;
//import com.google.android.gms.analytics.Tracker;
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//import com.turbo.turbo.mexico.R;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.Map;
//
//public class MyFirebaseMessagingService extends FirebaseMessagingService {
//
//    private static final String TAG = "MyFirebaseMsgService";
//
//    /**
//     * Called when message is received.
//     *
//     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
//     */
//    // [START receive_message]
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        // [START_EXCLUDE]
//        // There are two types of messages data messages and notification messages. Data messages are handled
//        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
//        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
//        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
//        // When the user taps on the notification they are returned to the app. Messages containing both notification
//        // and data payloads are treated as notification messages. The Firebase console always sends notification
//        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
//        // [END_EXCLUDE]
//
//        TLog.e("message----","messagsss================================");
//        // TODO(developer): Handle FCM messages here.
//        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
//        Log.d(TAG, "From: " + remoteMessage.getFrom());
//
//        // Check if message contains a data payload.
//        if (remoteMessage.getData().size() > 0) {
//            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
//            // {template=1, id=124664, url=https://www.youtube.com/watch?v=Aeyq47BtKyI,
//            // show=true, image={"url":"https:\/\/i.ytimg.com\/vi\/Aeyq47BtKyI\/sddefault.jpg?thumbnail\/3\/640x480\/quality\/60"},
//            // title=YouTube--to-test7, abstract=R��pidos Y Furiosos 8 Pel��cula Completa en espa?ol (HD) 2017}
//
//            getData(remoteMessage);
//
//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                scheduleJob();
//            } else {
//                // Handle message within 10 seconds
//                handleNow();
//            }
//        }
//
//        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//        }
//
//        // Also if you intend on generating your own notifications as a result of a received FCM
//        // message, here is where that should be initiated. See sendNotification method below.
//    }
//    // [END receive_message]
//
//    private void getData(RemoteMessage remoteMessage){
//        Map<String,String> map = remoteMessage.getData();
//        String title = "",url = "",msgid = "",abstractStr = "",template="",show = "",imgurls = "",iimgurls = "";
//        for (String key : map.keySet()) {
////                System.out.println("key= "+ key + " and value= " + map.get(key));
//            if (key.equals("title")){
//                title = map.get("title");
//            }
//            if (key.equals("url")){
//                url = map.get("url");
//            }
//            if (key.equals("id")){
//                msgid = map.get("id");
//            }
//            if (key.equals("abstract")){
//                abstractStr = map.get("abstract");
//            }
//            if (key.equals("template")){
//                template = map.get("template");
//            }
//            if (key.equals("show")){
//                show = map.get("show");
//            }
//            if (key.equals("image")){
//                imgurls = map.get("image");
//            }
//        }
////        TLog.e(TAG, "Message: " + "====" + url + "====" + msgid + "----" + title + "----" + abstractStr);
////        TLog.e(TAG, "NEWWWW: " + template + "===" + show + "==="+iimgurls+"==="+imgurls);
//        if (!Utils.isStringEmpty(imgurls)){
//            try {
//                JSONObject object = new JSONObject(imgurls);
//                iimgurls = object.getString("url");
//            }catch (JSONException e){
//                e.printStackTrace();
//            }
//        }
//        if (Utils.isStringEmpty(msgid)){
//            return;
//        }
//        sendMsg(title,abstractStr,url,msgid,iimgurls,template,show.equals("true"));
//
//    }
//
//    private void sendMsg(String title,String content,String url,String msgid,String imgurl,String templete,boolean isShowT){
//
//        CmsApplication application = (CmsApplication) getApplication();
//        Tracker mTracker = null;
//        if (application != null){
//            mTracker = application.getDefaultTracker();
//        }
//        NotifactionManager.sendNotifica(getApplicationContext(),mTracker,title,content,url,msgid,imgurl,templete,isShowT,null);
//    }
//
//    /**
//     * Schedule a job using FirebaseJobDispatcher.
//     */
//    private void scheduleJob() {
//        // [START dispatch_job]
////        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
////        Job myJob = dispatcher.newJobBuilder()
////                .setService(MyJobService.class)
////                .setTag("my-job-tag")
////                .build();
////        dispatcher.schedule(myJob);
//        // [END dispatch_job]
//    }
//
//    /**
//     * Handle time allotted to BroadcastReceivers.
//     */
//    private void handleNow() {
//        Log.d(TAG, "Short lived task is done.");
//    }
//
//    /**
//     * Create and show a simple notification containing the received FCM message.
//     *
//     * @param messageBody FCM message body received.
//     */
////    private void sendNotification(String messageBody) {
////        Intent intent = new Intent(this, MainActivity.class);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
////                PendingIntent.FLAG_ONE_SHOT);
////
////        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
////        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
////                .setSmallIcon(R.drawable.icon_sms2x)
////                .setContentTitle("FCM Message")
////                .setContentText(messageBody)
////                .setAutoCancel(true)
////                .setSound(defaultSoundUri)
////                .setContentIntent(pendingIntent);
////
////        NotificationManager notificationManager =
////                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
////
////        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
////    }
//}
