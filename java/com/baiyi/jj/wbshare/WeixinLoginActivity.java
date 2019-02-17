//package com.baiyi.jj.wbshare;
//
//import com.turbo.turbo.mexico.R;
//import com.baiyi.jj.app.activity.BaseActivity;
//import com.baiyi.jj.app.utils.Utils;
//import com.baiyi.jj.wbshare.SinaLoginActivity.MyAuthListener;
//import com.sina.weibo.sdk.auth.AuthInfo;
//import com.sina.weibo.sdk.auth.sso.SsoHandler;
//import com.tencent.mm.sdk.modelmsg.SendAuth;
//import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.WXAPIFactory;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//public class WeixinLoginActivity extends BaseActivity {
//
//	private IWXAPI wxapi = null;
//	Button button = null;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//
//		setContentView(R.layout.act_loginbyqq);
//		button = (Button) findViewById(R.id.btn_login);
//
//		button.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				onClickLogin();
//			}
//		});
//
//		init();
//
//	}
//
//	private void init() {
//		if (Utils.isStringEmpty(Constans.WeiXinAPP_ID)) {
//			displayToast(getResources().getString(R.string.txt_share12));
//			return;
//		}
//		wxapi = WXAPIFactory.createWXAPI(this, Constans.WeiXinAPP_ID, true);
//
//	}
//
//	private void onClickLogin() {
//		if (!wxapi.isWXAppInstalled()) {
//			displayToast(getResources().getString(R.string.txt_share3));
//			return;
//		}
//		if (!wxapi.isWXAppSupportAPI()) {
//			displayToast(getResources().getString(R.string.txt_share16));
//			return;
//		}
//		wxapi.registerApp(Constans.WeiXinAPP_ID);
//		final SendAuth.Req req = new SendAuth.Req();
//		req.scope = Constans.WX_APP_SCOPE;
//		req.state = Constans.WX_APP_STATE;
//		wxapi.sendReq(req);
//	}
//
//}
