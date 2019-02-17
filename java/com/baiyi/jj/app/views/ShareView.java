package com.baiyi.jj.app.views;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.manager.facebook.FaceBookLogin;
import com.baiyi.jj.app.utils.Utils;
//import com.baiyi.jj.wbshare.AccessTokenKeeper;
import com.turbo.turbo.mexico.R;
//import com.sina.weibo.sdk.api.ImageObject;
//import com.sina.weibo.sdk.api.TextObject;
//import com.sina.weibo.sdk.api.WebpageObject;
//import com.sina.weibo.sdk.api.WeiboMultiMessage;
//import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
//import com.sina.weibo.sdk.auth.AuthInfo;
//import com.sina.weibo.sdk.auth.Oauth2AccessToken;
//import com.sina.weibo.sdk.utils.Utility;
//import com.tencent.connect.share.QQShare;
//import com.tencent.connect.share.QzoneShare;
//import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
//import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
//import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
//import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.WXAPIFactory;
//import com.tencent.open.utils.ThreadManager;
//import com.tencent.tauth.IUiListener;
//import com.tencent.tauth.Tencent;
//import com.tencent.tauth.UiError;


public class ShareView extends LinearLayout {

	private LinearLayout viewParent = null;
	private Button btnCancel = null;
//	/**
//	 * ��������
//	 */
//	private LinearLayout btnWinxin = null; // ΢��
//	private LinearLayout btnWinxinFriend = null; // ΢������Ȧ
//	private LinearLayout btnQQ = null; // QQ
//	private LinearLayout btnQQZone = null; // QQ�ռ�
//	private LinearLayout btnSina = null; // ����΢��
	private LinearLayout btnE = null;
	private LinearLayout btnCopyurl = null; // ��������
	private LinearLayout btnEMail = null; // �ʼ�
	private LinearLayout btnFacebook = null; // facebook

//	/**
//	 * ��Ѷ����ƽ̨
//	 */
//	private static final String TencentAPP_ID = "1104870066";
//	private static final String TencentAPPKEY = "64ktSeVylFKxCX0c";
//	public static Tencent mTencent = null;
//	private static boolean isServerSideLogin = false;
//	private int shareTypeQQ = QQShare.SHARE_TO_QQ_TYPE_DEFAULT;
//
//	/**
//	 * ΢��
//	 */
//	private static final String WenXinAPPID = "wx30cdaf96b4b204d3";
//	private static final String WenXinAppSecret = "496ad1638d9e6927bb7f6492a63f6f57";
//	private IWXAPI wxapi = null;
//	private static final int THUMB_SIZE = 150;
//	private static final int WType_wx = 0; // ���������
//	private static final int WType_friend = 1; // ��������Ȧ

	/**
	 * ����΢��
	 */
//	private IWeiboShareAPI weiboShareAPI;
//	public static final String app_keyString = "3831822777";
	private OnSinaClick sinaClick = null;

	/**
	 * ���������
	 */
	private String TitleStr = null;
	private String ContentStr = null;
	private String UrlStr = null;
	private String ImageUrlStr = null;

	/**
	 * ����ҳ������
	 */
	public static int TitleId_Share = 0;
	public static int TitleId_ShareSet = 1;
	public static int TitleId_ShareInvite = 2;
	private int titleId = 0;

	private Context context;
	
	private OnFinishClick finishClick;

	public ShareView(Context context, int height, int Type,
			String title, String content, String url, String imgUrl) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.titleId = Type;
		this.TitleStr = title;
		this.ContentStr = content;
		this.UrlStr = url;
		this.ImageUrlStr = imgUrl;
		if (Utils.isStringEmpty(imgUrl)) {
			this.ImageUrlStr = Config.LOGO_IMAGEURL;
		}
		ContextUtil.getLayoutInflater(getContext()).inflate(
				R.layout.layout_share, this);
		initView();

//		weiboShareAPI = WeiboShareSDK.createWeiboAPI(context, app_keyString);
//        weiboShareAPI.registerApp(); // ע��app
//        weiboShareAPI = shareAPI;
     
	}

	private void initView() {
//		initShareData();
		viewParent = (LinearLayout) findViewById(R.id.view_parent);
//		btnWinxin = (LinearLayout) findViewById(R.id.lin_weixin);
//		btnWinxinFriend = (LinearLayout) findViewById(R.id.lin_weixin_friend);
//		btnQQ = (LinearLayout) findViewById(R.id.lin_qq);
//		btnQQZone = (LinearLayout) findViewById(R.id.lin_qqzone);
//		btnSina = (LinearLayout) findViewById(R.id.lin_sina);
		btnE = (LinearLayout) findViewById(R.id.lin_e);
		btnCopyurl = (LinearLayout) findViewById(R.id.lin_copyurl);
		btnEMail = (LinearLayout) findViewById(R.id.lin_email);
		btnFacebook = (LinearLayout) findViewById(R.id.lin_facebook);
		btnEMail.setOnClickListener(viewOnClickListen);
//		btnWinxin.setOnClickListener(viewOnClickListen);
//		btnWinxinFriend.setOnClickListener(viewOnClickListen);
//		btnQQ.setOnClickListener(viewOnClickListen);
//		btnQQZone.setOnClickListener(viewOnClickListen);
//		btnSina.setOnClickListener(viewOnClickListen);
		btnE.setOnClickListener(viewOnClickListen);
		btnCopyurl.setOnClickListener(viewOnClickListen);
		btnCopyurl.setOnClickListener(viewOnClickListen);
		btnFacebook.setOnClickListener(viewOnClickListen);

	}

	OnClickListener viewOnClickListen = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int id = v.getId();

//			if (id == R.id.lin_weixin) {
//				if (titleId == TitleId_ShareSet) {
//					// ��������
//				} else {
//					ShareWenxin(WType_wx); // ����΢�ź���
//				}
//
//			} else if (id == R.id.lin_weixin_friend) {
//				if (titleId == TitleId_ShareSet) {
//					// ��������
//				} else {
//					ShareWenxin(WType_friend); // ����΢������Ȧ
//				}
//
//			} else if (id == R.id.lin_qq) {
//				if (titleId == TitleId_ShareSet) {
//					shareQQLogin();
//				} else {
//					shareQQ();
//				}
//			} else if (id == R.id.lin_qqzone) {
//				if (titleId == TitleId_ShareSet) {
//					shareQQLogin();
//				} else {
//					shareQQZone();
//				}
//
//			} else if (id == R.id.lin_sina) {
////				((BaseActivity) context).displayToast("�����ڴ�");
////				Intent intent = new Intent(context,SinaShareActivity.class);
////				context.startActivity(intent);
////				shareToSina();
//			}else

			if (id == R.id.lin_e){
				goE();
			}else if (id == R.id.lin_copyurl) {
				shareCopyLink();
			} else if (id == R.id.lin_email) {
				ShareEMail();
			}else if (id == R.id.lin_facebook){
				shareFaceBook();
			}
			if (finishClick != null) {
				finishClick.onClick();
			}

		}
	};
	private void shareFaceBook() {

		FaceBookLogin.getInstence(getContext()).shareUrl((BaseActivity)getContext(),
				UrlStr, TitleStr, ContentStr,ImageUrlStr);
	}
	public void setFinishClick(OnFinishClick finishClick) {
		this.finishClick = finishClick;
	}



	public interface OnFinishClick {
		public void onClick();
	}

//	private void initShareData() {
//		/**
//		 * ��Ѷ
//		 */
//		mTencent = Tencent.createInstance(TencentAPP_ID, context);
//		/**
//		 * ΢��
//		 */
//		wxapi = WXAPIFactory.createWXAPI(context, WenXinAPPID, true);
//		wxapi.registerApp(WenXinAPPID);
//
//	}

	private void ShareEMail() {
		Intent email = new Intent(android.content.Intent.ACTION_SEND);
		email.setType("plain/text");
		// �����ʼ�Ĭ�ϵ�ַ
		// email.putExtra(android.content.Intent.EXTRA_EMAIL, "");
		// �����ʼ�Ĭ�ϱ���
		email.putExtra(android.content.Intent.EXTRA_SUBJECT, TitleStr);
		// ����ҪĬ�Ϸ��͵�����
		email.putExtra(android.content.Intent.EXTRA_TEXT, ContentStr + "\n"
				+ UrlStr + "\n "+getResources().getString(R.string.txt_share1));

		// UrlStr

		// ����ϵͳ���ʼ�ϵͳ
		((BaseActivity) context).startActivityForResult(
				Intent.createChooser(email, getResources().getString(R.string.txt_share2)), 1001);

	}

	/**
	 * ΢�ŷ��� flag == 0 ��΢�ź��� flag == 1 ��΢������Ȧ
//	 */
//	private void ShareWenxin(final int flag) {
//		if (!wxapi.isWXAppInstalled()) {
//			((BaseActivity) context).displayToast(getResources().getString(R.string.txt_share3));
//			return;
//		}
//		if (!wxapi.isWXAppSupportAPI()) {
//			((BaseActivity) context).displayToast(getResources().getString(R.string.txt_share4));
//			return;
//		}
//		setImgConver(ImageUrlStr, flag);
//
//	}

	// ΢��Ψһ��ʾ
	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	/**
	 * 浏览器打开网页
	 */
	private void goE() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(UrlStr));
		((BaseActivity) context).startActivity(intent);
	}

	/**
	 * 复制链接
	 */
	private void shareCopyLink() {
		ClipboardManager copy = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
		copy.setText(UrlStr);
		((BaseActivity) context).displayToast(getResources().getString(R.string.txt_share5));
	}

	/**
	 * qq��Ȩ��½
	 */
//	private void shareQQLogin() {
//		if (!mTencent.isSessionValid()) {
//			mTencent.login((BaseActivity) context, "all", loginListener);
//			isServerSideLogin = false;
//		} else {
//			if (isServerSideLogin) { // Server-Side ģʽ�ĵ�¼, ���˳����ٽ���SSO��¼
//				mTencent.logout(context);
//				mTencent.login((BaseActivity) context, "all",
//						loginListener);
//				isServerSideLogin = false;
//				Log.d("SDKQQAgentPref",
//						"FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
//				return;
//			}
//			mTencent.logout(context);
//			((BaseActivity) context).displayToast(getResources().getString(R.string.txt_share6));
//		}
//
//	}

//	IUiListener loginListener = new BaseUiListener() {
//		@Override
//		protected void doComplete(JSONObject values) {
//			initOpenidAndToken(values);
//		}
//	};
//
//	public static void initOpenidAndToken(JSONObject jsonObject) {
//		try {
//			String token = jsonObject
//					.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
//			String expires = jsonObject
//					.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
//			String openId = jsonObject
//					.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);
//			if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
//					&& !TextUtils.isEmpty(openId)) {
//				mTencent.setAccessToken(token, expires);
//				mTencent.setOpenId(openId);
//			}
//		} catch (Exception e) {
//		}
//	}

//	private void shareQQ() {
//		Bundle bundle = new Bundle();
//		bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
//				QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
//		bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, context.getResources()
//				.getString(R.string.app_name));
//		bundle.putString(QQShare.SHARE_TO_QQ_TITLE, TitleStr);
//		bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, ContentStr);
//		bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, UrlStr);
//		if (!Utils.isStringEmpty(ImageUrlStr)) {
//			bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, ImageUrlStr);
//		}
//		doShareToQQ(bundle);
//
//	}
//
//	private void doShareToQQ(final Bundle params) {
//		// QQ����Ҫ�����߳���
//		ThreadManager.getMainHandler().post(new Runnable() {
//
//			@Override
//			public void run() {
//				if (null != mTencent) {
//					mTencent.shareToQQ((BaseActivity) context, params,
//							lUiListener);
//				}
//			}
//		});
//	}
//
//	IUiListener lUiListener = new IUiListener() {
//
//		public void onError(UiError arg0) {
//			((BaseActivity) context).displayToast("onError: "
//					+ arg0.errorMessage);
//		}
//
//		public void onComplete(Object arg0) {
//			((BaseActivity) context).displayToast(getResources().getString(R.string.txt_share7));
//		}
//
//		public void onCancel() {
//			if (shareTypeQQ != QQShare.SHARE_TO_QQ_TYPE_IMAGE) {
//				((BaseActivity) context).displayToast(getResources().getString(R.string.txt_share8));
//			}
//		}
//	};
//
//	private void shareQQZone() {
//		final Bundle params = new Bundle();
//		params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,
//				QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
//		params.putString(QzoneShare.SHARE_TO_QQ_TITLE, TitleStr);
//		params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, ContentStr);
//		params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, UrlStr);
//
//		ArrayList<String> imageUrls = new ArrayList<String>();
//		imageUrls.add(ImageUrlStr);
//		params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
//
//		doShareToQzone(params);
//	}
//
//	/**
//	 * ���첽��ʽ��������
//	 *
//	 * @param params
//	 */
//	private void doShareToQzone(final Bundle params) {
//		// QZone����Ҫ�����߳���
//		ThreadManager.getMainHandler().post(new Runnable() {
//
//			@Override
//			public void run() {
//				if (null != mTencent) {
//					mTencent.shareToQzone((BaseActivity) context, params,
//							lUiListener);
//				}
//			}
//		});
//	}
//
//	private class BaseUiListener implements IUiListener {
//
//		@Override
//		public void onComplete(Object response) {
//			if (null == response) {
//				((BaseActivity) context).displayToast(getResources().getString(R.string.txt_share9));
//				return;
//			}
//			JSONObject jsonResponse = (JSONObject) response;
//			if (null != jsonResponse && jsonResponse.length() == 0) {
//				((BaseActivity) context).displayToast(getResources().getString(R.string.txt_share9));
//				return;
//			}
//			((BaseActivity) context).displayToast(getResources().getString(R.string.txt_share10));
//			doComplete((JSONObject) response);
//		}
//
//		protected void doComplete(JSONObject values) {
//
//		}
//
//		@Override
//		public void onError(UiError e) {
//			((BaseActivity) context).displayToast(e.errorDetail);
//		}
//
//		@Override
//		public void onCancel() {
//			((BaseActivity) context).displayToast(getResources().getString(R.string.txt_share11));
//			if (isServerSideLogin) {
//				isServerSideLogin = false;
//			}
//		}
//	}
//
//
//	private void shareToFriend(int flag, Bitmap result) {
//		WXWebpageObject webpage = new WXWebpageObject();
//		webpage.webpageUrl = UrlStr;
//		WXMediaMessage msg1 = new WXMediaMessage(webpage);
//		msg1.title = TitleStr;
//		msg1.description = ContentStr;
//		Bitmap bm = (Bitmap) result;
//		Bitmap thumbBmp = Bitmap.createScaledBitmap(bm, THUMB_SIZE, THUMB_SIZE,
//				true);
//		msg1.thumbData = Utils.bmpToByteArray(thumbBmp, true);
//
//		SendMessageToWX.Req req = new SendMessageToWX.Req();
//		req.transaction = buildTransaction("webpage");
//		req.message = msg1;
//		req.scene = flag == WType_wx ? SendMessageToWX.Req.WXSceneSession
//				: SendMessageToWX.Req.WXSceneTimeline;
//		wxapi.sendReq(req);
//
//	}

	
	public interface OnSinaClick{
		public void onclick(); 
	}
	public void setSinaClick(OnSinaClick sinaClick) {
		this.sinaClick = sinaClick;
	}


	private void shareToSina() {
		if (sinaClick!=null) {
			sinaClick.onclick();
		}
//		if (!weiboShareAPI.isWeiboAppInstalled()){
//			((BaseActivity)context).displayToast("���Ȱ�װ����΢��");
//            return;
//        }
//        if (!weiboShareAPI.isWeiboAppSupportAPI()){
//        	((BaseActivity)context).displayToast("���ȸ���΢�������°汾");
//            return;
//        }
//
//        int supportId = weiboShareAPI.getWeiboAppSupportAPI();
//        if (supportId > 10351){
//            sendMultiMessage();
//        }else {  //�Ͱ汾ʱֻ�ܷ�һ��
//        	sendSingleMessage();
//        }



	}


//    private void sendMultiMessage(){
//        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
//        weiboMessage.textObject = getTextObj();
//        weiboMessage.imageObject = getImageObj(ImageUrlStr);
//
//        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
//        request.transaction = String.valueOf(System.currentTimeMillis());
//        request.multiMessage = weiboMessage;
//
////        weiboShareAPI.sendRequest((BaseActivity)context, request);
//        AuthInfo authInfo = new AuthInfo(context, Constans.SinaApp_Key, Constans.REDIRECT_URL, Constans.SCOPE);
//        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(context);
//        String token = "";
//        if (accessToken != null) {
//            token = accessToken.getToken();
//        }
////        weiboShareAPI.sendRequest((BaseActivity)context, request, authInfo, token, new WeiboAuthListener() {
////
////            @Override
////            public void onWeiboException( WeiboException arg0 ) {
////            }
////
////            @Override
////            public void onComplete( Bundle bundle ) {
////                // TODO Auto-generated method stub
////                Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
////                AccessTokenKeeper.writeAccessToken(context, newToken);
////                Toast.makeText(context, "onAuthorizeComplete token = " + newToken.getToken(), 0).show();
////            }
////
////            @Override
////            public void onCancel() {
////            }
////        });
//    }

//    private void sendSingleMessage(){
//        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
//        weiboMessage.textObject = getTextObj();
////        weiboMessage.imageObject = getImageObj(shareImageUrl);
//        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
//        request.transaction = String.valueOf(System.currentTimeMillis());
//        request.multiMessage = weiboMessage;
//
////        weiboShareAPI.sendRequest((BaseActivity)context, request);
//
//    }

    /**
     * �����ı���Ϣ����
     *
     * @return �ı���Ϣ����
     */
//    private TextObject getTextObj() {
//        TextObject textObject = new TextObject();
//        textObject.text = ContentStr+UrlStr;
//        return textObject;
//    }
//
//    /**
//     * ����ͼƬ��Ϣ����
//     *
//     * @return ͼƬ��Ϣ����
//     */
//    private ImageObject getImageObj(String shareImageUrl) {
//
//        //��������ͼ�� ע�⣺����ѹ����������ͼ��С���ó��� 32kb��
//        final ImageObject imageObject = new ImageObject();
//        Bitmap bmp = android.graphics.BitmapFactory.decodeResource(
//                getResources(), R.mipmap.ic_launcher);
//        imageObject.setImageObject(bmp);
//
////        Picasso.with(this).load(shareImageUrl).into(new Target() {
////            @Override
////            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
////                if (bitmap == null) {
////                    return;
////                }
////                imageObject.setImageObject(bitmap);
////            }
////
////            @Override
////            public void onBitmapFailed(Drawable errorDrawable) {
////                Bitmap bmp = android.graphics.BitmapFactory.decodeResource(
////                        getResources(), R.mipmap.ic_launcher);
////                imageObject.setImageObject(bmp);
////            }
////
////            @Override
////            public void onPrepareLoad(Drawable placeHolderDrawable) {
////
////            }
////        });
//
//        return imageObject;
//    }

    /**
     * ������ý�壨��ҳ����Ϣ����
     *
     * @return ��ý�壨��ҳ����Ϣ����
     */
//    private WebpageObject getWebpageObj() {
//        WebpageObject mediaObject = new WebpageObject();
//        mediaObject.identify = Utility.generateGUID();
//        mediaObject.title = TitleStr;
//        mediaObject.description = ContentStr;
//
//        Bitmap  bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//        // ���� Bitmap ���͵�ͼƬ����Ƶ������         ��������ͼ�� ע�⣺����ѹ����������ͼ��С���ó��� 32kb��
//        mediaObject.setThumbImage(bitmap);
//        mediaObject.actionUrl = UrlStr;
//        mediaObject.defaultText = getResources().getString(R.string.app_name);
//        return mediaObject;
//    }

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		FaceBookLogin.getInstence(getContext()).getCallbackManager().onActivityResult(requestCode,resultCode,data);
	}

	public static void shareMsg(Context context,String msgTitle, String msgContent,
						 String imgPath) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
		intent.putExtra(Intent.EXTRA_TEXT, msgContent);
//		intent.putExtra(Intent.EXTRA_STREAM, urlStr);
		context.startActivity(Intent.createChooser(intent, context.getResources().getString(R.string.name_title_share)));
	}
}
