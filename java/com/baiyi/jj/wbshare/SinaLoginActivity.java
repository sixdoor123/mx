//package com.baiyi.jj.wbshare;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Message;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import com.turbo.turbo.mexico.R;
//import com.baiyi.jj.app.activity.BaseActivity;
//import com.sina.weibo.sdk.auth.AuthInfo;
//import com.sina.weibo.sdk.auth.Oauth2AccessToken;
//import com.sina.weibo.sdk.auth.WeiboAuthListener;
//import com.sina.weibo.sdk.auth.sso.SsoHandler;
//import com.sina.weibo.sdk.exception.WeiboException;
//import com.sina.weibo.sdk.net.RequestListener;
//
//public class SinaLoginActivity extends BaseActivity{
//
//	private AuthInfo authInfo;
//    private SsoHandler ssoHandler;
//    private Oauth2AccessToken accessToken;
//    Button button = null;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		 setContentView(R.layout.act_loginbyqq);
//	        button = (Button) findViewById(R.id.btn_login);
//
//	        button.setOnClickListener(new View.OnClickListener() {
//	            @Override
//	            public void onClick(View view) {
//	                onClickLogin();
//	            }
//	        });
//
//	        authInfo = new AuthInfo(SinaLoginActivity.this, Constans.SinaApp_Key,
//	        		Constans.REDIRECT_URL, Constans.SCOPE);
//	        ssoHandler = new SsoHandler(this, authInfo);
//
//	}
//
//	private void onClickLogin(){
//		 ssoHandler.authorize(new MyAuthListener());
//	}
//
//	/**
//     * �� SSO ��Ȩ Activity �˳�ʱ���ú��������á�
//     *
//     * @see {@link Activity#onActivityResult}
//     */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // SSO ��Ȩ�ص�
//        // ��Ҫ������ SSO ��½�� Activity ������д onActivityResults
//        if (ssoHandler != null) {
//        	ssoHandler.authorizeCallBack(requestCode, resultCode, data);
//        }
//
//    }
//
//    /**
//     *  ��Ȩ�Ļص�
//     * @author Administrator
//     *
//     */
//	class MyAuthListener implements WeiboAuthListener {
//
//        @Override
//        public void onCancel() {
//            Toast.makeText(SinaLoginActivity.this, getResources().getString(R.string.txt_share14), Toast.LENGTH_LONG).show();
//        }
//
//        @SuppressWarnings("static-access")
//        @Override
//        public void onComplete(Bundle values) {
//            // TODO Auto-generated method stub
//            accessToken = Oauth2AccessToken.parseAccessToken(values);
//
//            if (accessToken.isSessionValid()) {
//                // ���� Token �� SharedPreferences
//                AccessTokenKeeper.writeAccessToken(SinaLoginActivity.this, accessToken);
////                Toast.makeText(SinaLoginActivity.this, "΢����Ȩ�ɹ�", Toast.LENGTH_SHORT).show();
//                String uid = accessToken.getUid(); // �û�id ������ȡ�û���Ϣ
//                getUserInfo();
//            } else {
//                // ���¼�������������յ� Code��
//                // 1. ����δ��ƽ̨��ע���Ӧ�ó���İ�����ǩ��ʱ��
//                // 2. ����ע���Ӧ�ó��������ǩ������ȷʱ��
//                // 3. ������ƽ̨��ע��İ�����ǩ��������ǰ���Ե�Ӧ�õİ�����ǩ����ƥ��ʱ��
//                String code = values.getString("code");
//                String message = getResources().getString(R.string.txt_share15);
//                if (!TextUtils.isEmpty(code)) {
//                    message = message + "\nObtained the code: " + code;
//                }
//                Toast.makeText(SinaLoginActivity.this, message, Toast.LENGTH_LONG).show();
//            }
//
//        }
//
//        @Override
//        public void onWeiboException(WeiboException arg0) {
//            System.out.println("Auth Exception" + arg0.getMessage());
//        }
//
//
//    }
//
//	/**
//	 *  ���������ȡ����΢����½��Ϣ
//	 */
//	private void getUserInfo() {
//		 new Thread(new Runnable() {
//
//             @Override
//             public void run() {
//                 // TODO Auto-generated method stub
//             UsersAPI usersAPI = new UsersAPI(SinaLoginActivity.this, Constans.SinaApp_Key, accessToken);
//             usersAPI.show(Long.valueOf(accessToken.getUid()), new SinaRequestListener());
//
//             }
//         }).start();
//	}
//
//	/**
//	 *  ��ȡ�û���Ϣ�ص�
//	 */
//	  class SinaRequestListener implements RequestListener{ //����΢������ӿ�
//
//	        @Override
//	        public void onComplete(String response) {
//	            // TODO Auto-generated method stub
//	            try {
//
//	                JSONObject jsonObject = new JSONObject(response);
//	                /**
//	            	 *  ���������ȡ����΢����½��Ϣ
//	            	 */
//	                  String idStr = jsonObject.getString("idstr");// Ψһ��ʶ��(uid)
//	                  String name = jsonObject.getString("name");// ����
//	                  String avatarHd = jsonObject.getString("avatar_hd");// ͷ��
//	                  Toast.makeText(SinaLoginActivity.this, jsonObject.toString(), Toast.LENGTH_LONG).show();
////	                  Message message = Message.obtain();
////	                  message.what = 1;
////	                  handler.sendMessage(message);
//
//	            } catch (JSONException e) {
//	                // TODO Auto-generated catch block
//	                e.printStackTrace();
//	            }
//	        }
//
//	        @Override
//	        public void onWeiboException(WeiboException e) {
//	            // TODO Auto-generated method stub
//	            System.out.println( "Auth exception : " + e.getMessage());
//	        }
//
//
//	    }
//
//}
