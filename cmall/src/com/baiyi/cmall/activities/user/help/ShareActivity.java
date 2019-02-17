package com.baiyi.cmall.activities.user.help;

import java.util.ArrayList;
import org.json.JSONObject;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.adapter.ShareAdapter;
import com.baiyi.cmall.utils.DataUtils;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.activities.user.help.ShareActivity;
import com.baiyi.cmall.utils.ImageTools;
import com.baiyi.cmall.utils.Utils;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

/**
 * 分享界面
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-2-5 下午2:09:34
 */
public class ShareActivity extends BaseActivity implements OnItemClickListener {
	/**
	 * 腾讯开放平台
	 */
	private static final String TencentAPP_ID = "1105255682";
	private static final String TencentAPPKEY = "TiS4l0ejToWqVmv9";
	public static Tencent mTencent = null;
	private static boolean isServerSideLogin = false;
	private int shareTypeQQ = QQShare.SHARE_TO_QQ_TYPE_DEFAULT;
	/**
	 * 微信
	 */
	private static final String WenXinAPPID = "wx9f81ccc69d3db03f";
	private static final String WenXinAppSecret = "8eb9ecdb3ed14ccfcb361815ba05b5c8";
	private IWXAPI wxapi = null;
	private static final int THUMB_SIZE = 150;
	private static final int WType_wx = 0; // 分享给好友
	private static final int WType_friend = 1; // 分享到朋友圈

	/**
	 * 分享的内容
	 */
	private String TitleStr = "化工港";
	private String ContentStr = "化工港二维码分享";
	private String UrlStr = "http://www.meitangang.com/webservice/page/qrcode/android.html";
	private String ImageUrlStr = "http://wenwen.soso.com/p/20121011/20121011222952-938267560.jpg";

	/**
	 * 分享页面类型
	 */
	public static int TitleId_Share = 0;
	public static int TitleId_ShareSet = 1;
	public static int TitleId_ShareInvite = 2;

	// private int titleId = 0;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(false);
		initShareData();
		initData();
		initTitle();
		initContent();
	}

	// 文字，名字
	private ArrayList<String> names;
	// 图片资源
	private ArrayList<Integer> imgs;

	/**
	 * 加载数据
	 */
	private void initData() {
		names = DataUtils.getShareName();
		imgs = DataUtils.getShareImgs();
	}

	// 自定义标题栏
	protected EventTopTitleView topTitleView = null;

	/**
	 * 标题栏
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("邀请好友");
		topTitleView.setTitleNewsOnclick(new TitleNewsOnclick() {

			@Override
			public void setTitleNewsOnclickLister(boolean isPop) {

				topTitleView.displayPoP(MsgItemUtil.Pop_Default_txt,
						MsgItemUtil.Pop_Default_img);

			}
		});
		topTitleView.setNewsPopItemOnclick(new NewsPopItemOnclick() {

			@Override
			public void setNewsPopItemOnclickLister(int state) {
				MsgItemUtil.onclickPopItem(state, ShareActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}

	//
	private GridView mGdShare;
	// 适配器
	private ShareAdapter adapter;

	/**
	 * 初始化内容
	 */
	private void initContent() {
		ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_share,
				win_content);

		mGdShare = (GridView) findViewById(R.id.gd_share);
		adapter = new ShareAdapter(this, names, imgs);
		mGdShare.setAdapter(adapter);

		mGdShare.setOnItemClickListener(this);
	}

	/**
	 * 分享条目点击事件
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		case 0:// 微信
			ShareWenxin(WType_wx); // 分享到微信好友
			break;
		case 1:// 微信朋友圈
			ShareWenxin(WType_friend); // 分享到微信朋友圈
			break;
		case 2:// 手机QQ
			shareQQ();
			break;
		case 3:
			// qq空间
			shareQQZone();
			break;
		case 4:// 邮件
			displayToast("邮件");
			ShareEMail();
			break;
		case 5:// 复制链接
			shareCopyLink();
			break;
		}
	}

	// ========================================================================================================

	private void initShareData() {
		/**
		 * 腾讯
		 */
		mTencent = Tencent.createInstance(TencentAPP_ID, this);
		/**
		 * 微信
		 */
		wxapi = WXAPIFactory.createWXAPI(this, WenXinAPPID, false);
		wxapi.registerApp(WenXinAPPID);

	}

	private void ShareEMail() {
		Intent email = new Intent(android.content.Intent.ACTION_SEND);
		email.setType("plain/text");
		// 设置邮件默认地址
		// email.putExtra(android.content.Intent.EXTRA_EMAIL, "");
		// 设置邮件默认标题
		email.putExtra(android.content.Intent.EXTRA_SUBJECT, TitleStr);
		// 设置要默认发送的内容
		email.putExtra(android.content.Intent.EXTRA_TEXT, ContentStr + "\n"
				+ UrlStr + "\n 这是我在煤炭港分享的内容");

		// UrlStr

		// 调用系统的邮件系统
		startActivityForResult(Intent.createChooser(email, "请选择邮件发送软件"), 1001);

	}

	/**
	 * 微信分享 flag == 0 是微信好友 flag == 1 是微信朋友圈
	 */
	private void ShareWenxin(final int flag) {
		if (!wxapi.isWXAppInstalled()) {
			((BaseActivity) this).displayToast("请先安装微信应用");
			return;
		}
		if (!wxapi.isWXAppSupportAPI()) {
			((BaseActivity) this).displayToast("请先更新微信应用");
			return;
		}
		Drawable drawable = getResources().getDrawable(R.drawable.app_icon);
		shareToFriend(flag, ImageTools.drawableToBitmap(drawable));

	}

	// 微信唯一标示
	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	/**
	 * 复制链接
	 */
	private void shareCopyLink() {
		ClipboardManager copy = (ClipboardManager) this
				.getSystemService(this.CLIPBOARD_SERVICE);
		copy.setText(UrlStr);
		((BaseActivity) this).displayToast("已将链接复制到剪贴板");
	}

	/**
	 * qq授权登陆
	 */
	private void shareQQLogin() {
		if (!mTencent.isSessionValid()) {
			mTencent.login((BaseActivity) this, "all", loginListener);
			isServerSideLogin = false;
		} else {
			if (isServerSideLogin) { // Server-Side 模式的登录, 先退出，再进行SSO登录
				mTencent.logout(this);
				mTencent.login((BaseActivity) this, "all", loginListener);
				isServerSideLogin = false;
				Log.d("SDKQQAgentPref",
						"FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
				return;
			}
			mTencent.logout(this);
			((BaseActivity) this).displayToast("用户已登出");
		}

	}

	IUiListener loginListener = new BaseUiListener() {
		@Override
		protected void doComplete(JSONObject values) {
			initOpenidAndToken(values);
		}
	};

	public static void initOpenidAndToken(JSONObject jsonObject) {
		try {
			String token = jsonObject
					.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
			String expires = jsonObject
					.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
			String openId = jsonObject
					.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);
			if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
					&& !TextUtils.isEmpty(openId)) {
				mTencent.setAccessToken(token, expires);
				mTencent.setOpenId(openId);
			}
		} catch (Exception e) {
		}
	}

	private void shareQQ() {
		Bundle bundle = new Bundle();
		bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
				QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
		bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, this.getResources()
				.getString(R.string.app_name));
		bundle.putString(QQShare.SHARE_TO_QQ_TITLE, TitleStr);
		bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, ContentStr);
		bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, UrlStr);
		if (!Utils.isStringEmpty(ImageUrlStr)) {
			bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, ImageUrlStr);
		}
		doShareToQQ(bundle);

	}

	private void doShareToQQ(final Bundle params) {
		// QQ分享要在主线程做
		ThreadManager.getMainHandler().post(new Runnable() {

			@Override
			public void run() {
				if (null != mTencent) {
					mTencent.shareToQQ(ShareActivity.this, params, lUiListener);
				}
			}
		});
	}

	IUiListener lUiListener = new IUiListener() {

		public void onError(UiError arg0) {
			// displayToast("onError: " + arg0.errorMessage);
		}

		public void onComplete(Object arg0) {
			displayToast("分享成功");
		}

		public void onCancel() {
			if (shareTypeQQ != QQShare.SHARE_TO_QQ_TYPE_IMAGE) {
				displayToast("取消分享");
			}
		}
	};

	private void shareQQZone() {
		final Bundle params = new Bundle();
		params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE,
				QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
		params.putString(QzoneShare.SHARE_TO_QQ_TITLE, TitleStr);
		params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, ContentStr);
		params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, UrlStr);

		ArrayList<String> imageUrls = new ArrayList<String>();
		imageUrls.add(ImageUrlStr);
		params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);

		doShareToQzone(params);
	}

	/**
	 * 用异步方式启动分享
	 * 
	 * @param params
	 */
	private void doShareToQzone(final Bundle params) {
		// QZone分享要在主线程做
		ThreadManager.getMainHandler().post(new Runnable() {

			@Override
			public void run() {
				if (null != mTencent) {
					mTencent.shareToQzone(ShareActivity.this, params,
							lUiListener);
				}
			}
		});
	}

	private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(Object response) {
			if (null == response) {
				displayToast("登录失败");
				return;
			}
			JSONObject jsonResponse = (JSONObject) response;
			if (null != jsonResponse && jsonResponse.length() == 0) {
				displayToast("登录失败");
				return;
			}
			displayToast("登录成功");
			doComplete((JSONObject) response);
		}

		protected void doComplete(JSONObject values) {

		}

		@Override
		public void onError(UiError e) {
			displayToast(e.errorDetail);
		}

		@Override
		public void onCancel() {
			displayToast("登录取消");
			if (isServerSideLogin) {
				isServerSideLogin = false;
			}
		}
	}

	private void shareToFriend(int flag, Bitmap result) {
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = UrlStr;
		WXMediaMessage msg1 = new WXMediaMessage(webpage);
		msg1.title = TitleStr;
		msg1.description = ContentStr;
		Bitmap bm = (Bitmap) result;
		Bitmap thumbBmp = Bitmap.createScaledBitmap(bm, THUMB_SIZE, THUMB_SIZE,
				true);
		msg1.thumbData = Utils.bmpToByteArray(thumbBmp, true);

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg1;
		req.scene = flag == WType_wx ? SendMessageToWX.Req.WXSceneSession
				: SendMessageToWX.Req.WXSceneTimeline;
		wxapi.sendReq(req);
	}
}