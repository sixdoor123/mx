//package com.baiyi.jj.wbshare;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import com.turbo.turbo.mexico.R;
//import com.baiyi.jj.app.activity.BaseActivity;
//import com.tencent.connect.UserInfo;
//import com.tencent.connect.auth.QQAuth;
//import com.tencent.connect.common.Constants;
//import com.tencent.tauth.IUiListener;
//import com.tencent.tauth.Tencent;
//import com.tencent.tauth.UiError;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//
//public class QQLoginActivity extends BaseActivity{
//
//	Button button = null;
//    private Tencent mTencent;
//    private QQAuth mQQAuth;
//    private UserInfo userInfo;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.act_loginbyqq);
//        button = (Button) findViewById(R.id.btn_login);
//
//          init("1104870066");
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onClickLogin();
//            }
//        });
//
//    }
//
//    private void init( String app_Id){
//        if (TextUtils.isEmpty(app_Id)) {
//            displayToast(getResources().getString(R.string.txt_share12));
//            return;
//        }
//        if (mTencent == null)
//            mTencent = Tencent.createInstance(app_Id, this);
//        mQQAuth = QQAuth.createInstance(app_Id, this);
//
//    }
//
//
//    public void onClickLogin() {
//        if (!mQQAuth.isSessionValid()) {
//            mTencent.login(this, "all", loginListener);
//        } else {
//            mQQAuth.logout(this);
//            updateUserInfo();
//        }
//    }
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
////        if (requestCode == Constants.REQUEST_API) {
//            if (requestCode == Constants.RESULT_LOGIN) {
//                mTencent.handleLoginData(data, loginListener);
//            }
//            super.onActivityResult(requestCode, resultCode, data);
////        }
//    }
//
//
//
//
//    private void updateUserInfo() {
//        userInfo = new UserInfo(this, mTencent.getQQToken());
//        userInfo.getUserInfo(updatelistener);
//    }
//
//
//    IUiListener loginListener = new BaseUiListener() {
//        @Override
//        protected void doComplete(JSONObject values) {
//            try {
//                String openID = values.getString("openid");
//                String accessToken = values.getString("access_token");
//                String expires = values.getString("expires_in");
//                mTencent.setOpenId(openID);
//                mTencent.setAccessToken(accessToken, expires);
//                updateUserInfo();
//            }catch (JSONException e){
//
//            }
//
//
//        }
//    };
//
//    IUiListener updatelistener = new BaseUiListener() {
//        @Override
//        public void onComplete(Object response) {
//            JSONObject jsonObject = (JSONObject) response;
//            Message message = new Message();
//            String nick = null;
//            String head = null;
//            try {
//                nick = jsonObject.getString("nickname");
//                head = jsonObject.getString("figureurl_qq_2");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            message.getData().putString("nickname", nick);
//            message.getData().putString("head", head);
//            message.what = 0;
//            mHandler.sendMessage(message);
//
//
//        }
//    };
//
//    Handler mHandler = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.what == 0) {
//                String nick = msg.getData().getString("nickname");
//                String head = msg.getData().getString("head");
//            }
//        }
//    };
//
//
//    /**
//     * ����SDK��װ�õĽ�ڣ���Ҫ����ص���ʵ�� �᷵�ط���������Ϣ
//     */
//    class BaseUiListener implements IUiListener {
//        /**
//         * �ɹ�
//         */
//        @Override
//        public void onComplete(Object response) {
////            backInfo.setText(response.toString());
//            doComplete((JSONObject) response);
//        }
//
//        /**
//         * �����ص���Ϣ �����jsonת��Ϊ����ʲô��
//         *
//         * @param values
//         */
//        protected void doComplete(JSONObject values) {
//
//        }
//
//        @Override
//        public void onError(UiError e) {
//            displayToast( e.toString());
//        }
//
//        @Override
//        public void onCancel() {
//            displayToast(getResources().getString(R.string.txt_share13));
//        }
//    }
//
//}
