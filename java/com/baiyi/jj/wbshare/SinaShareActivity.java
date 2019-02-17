//package com.baiyi.jj.wbshare;
//
//import com.turbo.turbo.mexico.R;
//import com.baiyi.jj.app.Config;
//import com.baiyi.jj.app.activity.BaseActivity;
//import com.sina.weibo.sdk.api.ImageObject;
//import com.sina.weibo.sdk.api.TextObject;
//import com.sina.weibo.sdk.api.WebpageObject;
//import com.sina.weibo.sdk.api.WeiboMultiMessage;
//import com.sina.weibo.sdk.api.share.BaseResponse;
//import com.sina.weibo.sdk.api.share.IWeiboHandler;
//import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
//import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
//import com.sina.weibo.sdk.api.share.WeiboShareSDK;
//import com.sina.weibo.sdk.constant.WBConstants;
//import com.sina.weibo.sdk.utils.Utility;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.widget.Button;
//
//public class SinaShareActivity extends BaseActivity implements IWeiboHandler.Response {
//
//	private Button btnShare;
//
//	public IWeiboShareAPI weiboShareAPI;
//	private String app_keyString = "3831822777";
//
//	 private static String shareTitle; // ����ı��⣬�30���ַ�
//	    private static String shareUrl; //���֮����ת��url
//	    private static String shareAbstract; //�����ժҪ���ݣ�ѡ��40����
//	    private static String shareImageUrl; //�����ͼƬurl�򱾵�ͼƬ·��
//	    private static String titleWord; //�ֻ�qq�����ֶΣ������滻�����ء�����
//
////	    @Override
////	    protected void onCreate(Bundle savedInstanceState, boolean isHasTitle) {
////	    	// TODO Auto-generated method stub
////	    	super.onCreate(savedInstanceState, isHasTitle);
////	    	setContentView(R.layout.act_sinashare);
////	    	shareTitle = "����΢�����������";
////			shareUrl = Config.LOGO_IMAGEURL;
////			shareAbstract = "����΢�����������,����΢���������������΢�����������";
////			shareUrl = Config.LOGO_JUJiaoUrl;
////			titleWord = "�۽�";
////
////			weiboShareAPI = WeiboShareSDK.createWeiboAPI(this, app_keyString);
////	        weiboShareAPI.registerApp(); // ע��app
////
////	     // �� Activity �����³�ʼ��ʱ���� Activity ���ں�̨ʱ�����ܻ������ڴ治�㱻ɱ���ˣ���
////	        // ��Ҫ���� {@link IWeiboShareAPI#handleWeiboResponse} ������΢���ͻ��˷��ص����ݡ�
////	        // ִ�гɹ������� true�������� {@link IWeiboHandler.Response#onResponse}��
////	        // ʧ�ܷ��� false�������������ص�
////	        if (savedInstanceState != null) {
////	            weiboShareAPI.handleWeiboResponse(getIntent(), this);
////	        }
////
////	    }
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.act_sinashare);
////		setContentView(R.layout.act_loginbyqq);
//
////		btnShare = (Button) findViewById(R.id.btn_login);
////		btnShare.setText("����");
////		btnShare.setOnClickListener(new OnClickListener() {
////
////			@Override
////			public void onClick(View v) {
////				// TODO Auto-generated method stub
////				shareToSina();
////			}
////		});
//		shareTitle = "����΢�����������";
//		shareUrl = Config.LOGO_IMAGEURL;
//		shareAbstract = "����΢�����������,����΢���������������΢�����������";
//		shareUrl = Config.LOGO_JUJiaoUrl;
//		titleWord = "�۽�";
//
//		weiboShareAPI = WeiboShareSDK.createWeiboAPI(this, app_keyString);
//        weiboShareAPI.registerApp(); // ע��app
//
//     // �� Activity �����³�ʼ��ʱ���� Activity ���ں�̨ʱ�����ܻ������ڴ治�㱻ɱ���ˣ���
//        // ��Ҫ���� {@link IWeiboShareAPI#handleWeiboResponse} ������΢���ͻ��˷��ص����ݡ�
//        // ִ�гɹ������� true�������� {@link IWeiboHandler.Response#onResponse}��
//        // ʧ�ܷ��� false�������������ص�
//        if (savedInstanceState != null) {
//            weiboShareAPI.handleWeiboResponse(getIntent(), this);
//        }
//
//        shareToSina();
//	}
//
//	@Override
//	protected void onNewIntent(Intent intent) {
//		// TODO Auto-generated method stub
//		super.onNewIntent(intent);
//		// �ӵ�ǰӦ�û���΢�������з���󣬷��ص���ǰӦ��ʱ����Ҫ�ڴ˴����øú���
//        // ������΢���ͻ��˷��ص����ݣ�ִ�гɹ������� true��������
//        // {@link IWeiboHandler.Response#onResponse}��ʧ�ܷ��� false�������������ص�
//        weiboShareAPI.handleWeiboResponse(intent, this);
//	}
//
//
//	public void shareToSina() {
//
//		System.out.println("s----------------");
//		if (!weiboShareAPI.isWeiboAppInstalled()){
//            displayToast("���Ȱ�װ����΢��");
//            return;
//        }
//        if (!weiboShareAPI.isWeiboAppSupportAPI()){
//            displayToast("���ȸ���΢�������°汾");
//            return;
//        }
//
//        int supportId = weiboShareAPI.getWeiboAppSupportAPI();
//        System.out.println(supportId+"---------------");
//        if (supportId > 10351){
//            sendMultiMessage();
//        }else {
//        	sendSingleMessage();
//        }
//
//	}
//
//
//
//    private void sendMultiMessage(){
//        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
//        weiboMessage.textObject = getTextObj();
//        weiboMessage.imageObject = getImageObj(shareImageUrl);
//
//        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
//        request.transaction = String.valueOf(System.currentTimeMillis());
//        request.multiMessage = weiboMessage;
//
//        weiboShareAPI.sendRequest(this, request);
////        AuthInfo authInfo = new AuthInfo(this, Constans.SinaApp_Key, Constans.REDIRECT_URL, Constans.SCOPE);
////        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(this);
////        String token = "";
////        if (accessToken != null) {
////            token = accessToken.getToken();
////        }
////        weiboShareAPI.sendRequest(this, request, authInfo, token, new WeiboAuthListener() {
////
////            @Override
////            public void onWeiboException( WeiboException arg0 ) {
////            	System.out.println("ex--------"+arg0);
////            }
////
////            @Override
////            public void onComplete( Bundle bundle ) {
////                // TODO Auto-generated method stub
////                Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
////                AccessTokenKeeper.writeAccessToken(SinaShareActivity.this, newToken);
////                displayToast( "onAuthorizeComplete token = " + newToken.getToken());
////            }
////
////            @Override
////            public void onCancel() {
////            	System.out.println("cancel----------");
////            }
////        });
//
//    }
//
//    private void sendSingleMessage(){
//        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
//        weiboMessage.textObject = getTextObj();
////        weiboMessage.imageObject = getImageObj(shareImageUrl);
//        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
//        request.transaction = String.valueOf(System.currentTimeMillis());
//        request.multiMessage = weiboMessage;
//
//        weiboShareAPI.sendRequest(SinaShareActivity.this, request);
//
//    }
//
//    /**
//     * �����ı���Ϣ����
//     *
//     * @return �ı���Ϣ����
//     */
//    private TextObject getTextObj() {
//        TextObject textObject = new TextObject();
//        textObject.text = shareAbstract;
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
//
//    /**
//     * ������ý�壨��ҳ����Ϣ����
//     *
//     * @return ��ý�壨��ҳ����Ϣ����
//     */
//    private WebpageObject getWebpageObj() {
//        WebpageObject mediaObject = new WebpageObject();
//        mediaObject.identify = Utility.generateGUID();
//        mediaObject.title = shareTitle;
//        mediaObject.description = shareAbstract;
//
//        Bitmap  bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//        // ���� Bitmap ���͵�ͼƬ����Ƶ������         ��������ͼ�� ע�⣺����ѹ����������ͼ��С���ó��� 32kb��
//        mediaObject.setThumbImage(bitmap);
//        mediaObject.actionUrl = shareUrl;
//        mediaObject.defaultText = titleWord;
//        return mediaObject;
//    }
//
//
//	@Override
//	public void onResponse(BaseResponse baseResp) {
//		if(baseResp!= null){
////			finish();
//            switch (baseResp.errCode) {
//            case WBConstants.ErrorCode.ERR_OK:
//            	displayToast(getResources().getString((R.string.tip_share_success)));
//                break;
//            case WBConstants.ErrorCode.ERR_CANCEL:
//            	displayToast(getResources().getString(R.string.tip_share_cancel));
//                break;
//            case WBConstants.ErrorCode.ERR_FAIL:
//            	displayToast(getResources().getString(R.string.tip_share_failure)+"Error Message: " + baseResp.errMsg);
//                break;
//            }
//            finish();
//        }
//
//	}
//
//}
