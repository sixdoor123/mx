///**
// * Copyright 2016 Google Inc. All Rights Reserved.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.baiyi.jj.app.manager.fcm;
//
//import android.preference.PreferenceManager;
//import android.util.Log;
//
//import com.baiyi.jj.app.application.CmsApplication;
//import com.baiyi.jj.app.entity.UserInfoEntity;
//import com.baiyi.jj.app.manager.NotifactionManager;
//import com.baiyi.jj.app.utils.TLog;
//import com.baiyi.jj.app.utils.XMLName;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.FirebaseInstanceIdService;
//import com.onesignal.OneSignal;
//
//
//public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
//
//    private static final String TAG = "MyFirebaseIIDService";
//
//    /**
//     * Called if InstanceID token is updated. This may occur if the security of
//     * the previous token had been compromised. Note that this is called when the InstanceID token
//     * is initially generated so this is where you would retrieve the token.
//     */
//    // [START refresh_token]
//    @Override
//    public void onTokenRefresh() {
//        // Get updated InstanceID token.
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
////        TLog.d(TAG, "Refreshed token: " + refreshedToken);
//        TLog.e("refreshtoken",refreshedToken);
//        // If you want to send messages to this application instance or
//        // manage this apps subscriptions on the server side, send the
//        // Instance ID token to your app server.
//        OneSignal.sendTag("deviceToken",refreshedToken);
//        sendRegistrationToServer(refreshedToken);
//
//
//    }
//    // [END refresh_token]
//
//    /**
//     * Persist token to third-party servers.
//     *
//     * Modify this method to associate the user's FCM InstanceID token with any server-side account
//     * maintained by your application.
//     *
//     * @param token The new token.
//     */
//    private void sendRegistrationToServer(String token) {
//        // TODO: Implement this method to send token to your app server.
//        UserInfoEntity userInfoEntity = CmsApplication.getUserInfoEntity();
//        String endpoint = PreferenceManager.getDefaultSharedPreferences(
//                this)
//                .getString(XMLName.XML_AWS_endpoint, "refreshtoken");
//        if (userInfoEntity != null){
//            NotifactionManager.updateUserAWS(userInfoEntity.getMID(),token,endpoint);
//        }
//    }
//}
