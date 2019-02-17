//package com.baiyi.jj.wxapi;
//
//import android.content.Intent;
//import android.os.Bundle;
//
//import com.turbo.turbo.mexico.R;
//import com.baiyi.jj.app.Config;
//import com.baiyi.jj.app.activity.BaseActivity;
//import com.baiyi.jj.app.utils.Utils;
//import com.baiyi.jj.wbshare.Constans;
//import com.tencent.mm.sdk.modelbase.BaseReq;
//import com.tencent.mm.sdk.modelbase.BaseResp;
//import com.tencent.mm.sdk.modelmsg.SendAuth;
//import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
//import com.tencent.mm.sdk.openapi.WXAPIFactory;
//
//public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
//
//	private static final String WenXinAPPID = "wx30cdaf96b4b204d3";
//	private IWXAPI api;
//
//	public static BaseResp mResp = null;
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		api = WXAPIFactory.createWXAPI(this, WenXinAPPID, false);
//		api.handleIntent(getIntent(), this);
//
//	}
//
//	@Override
//	protected void onNewIntent(Intent intent) {
//		// TODO Auto-generated method stub
//		super.onNewIntent(intent);
//		setIntent(intent);
//		api.handleIntent(intent, this);
//	}
//
//	@Override
//	public void onReq(BaseReq req) {
//
//	}
//
//	@Override
//	public void onResp(BaseResp resp) {
//
//		String codeString = ((SendAuth.Resp) resp).code;
//		loadGetUserInfo(codeString);
//		String result = "";
//		switch (resp.errCode) {
//		case BaseResp.ErrCode.ERR_AUTH_DENIED:
//			//��֤�����
//			result = getResources().getString(R.string.tip_share_failure);
//			break;
//		case BaseResp.ErrCode.ERR_COMM:
//			//һ�����
//			break;
//		case BaseResp.ErrCode.ERR_OK:
//			result = getResources().getString(R.string.tip_share_success);
//			break;
//		case BaseResp.ErrCode.ERR_SENT_FAILED:
//			//����ʧ��
//			result = getResources().getString(R.string.tip_share_success);
//			break;
//		case BaseResp.ErrCode.ERR_UNSUPPORT:
//			//��֧�ִ���
//			result = getResources().getString(R.string.tip_share_success);
//			break;
//		case BaseResp.ErrCode.ERR_USER_CANCEL:
//			//�û�ȡ��
//			result = getResources().getString(R.string.tip_share_cancel);
//			break;
//		default:
//			result = getResources().getString(R.string.tip_share_success);
//			break;
//		}
//
//		displayToast(result);
//		this.finish();
//	}
//
//	private void loadGetUserInfo(String code) {
//		// TODO Auto-generated method stub
//		if (Utils.isStringEmpty(code)) {
//			return;
//		}
//		/**
//		 *  ��������ӿ�ȡ���Ľ�������û���Ϣ
//		 */
//		String path = getUserInfoUrl(code);
//	}
//
//	private String getUserInfoUrl(String code){
//		return String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=%s",
//				Constans.WeiXinAPP_ID,Constans.WenXinAppSecret,code,"authorization_code");
//	}
//
//}