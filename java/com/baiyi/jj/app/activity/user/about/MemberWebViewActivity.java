package com.baiyi.jj.app.activity.user.about;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.user.net.AppUtils;
import com.baiyi.jj.app.activity.user.net.NetUrl;
import com.baiyi.jj.app.activity.user.net.URLUtils;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.turbo.turbo.mexico.R;

/**
 * ��Աwebview
 *  �����̳�
 */
public class MemberWebViewActivity extends BaseActivity {
	
	private String url;
	private String name;
	private WebView member_webview;
	// ������
	private ProgressBar pbarMember = null;
	private boolean isMarket = false; //=1��ʱ�����̳�
	
	private MyLoadingBar loadingBar = null;

	private View titleView;
	
	// �ǲ������µ�½����
	private boolean isInvaild = false;
	
	@Override
	protected void initWin(boolean hasScrollView, boolean isAnimal) {
		super.initWin(false, true);
		titleView = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.title_left, null);
		win_title.addView(titleView);

		View contentView = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_member_web, null);
		win_content.addView(contentView);

		name = getIntent().getStringExtra(Define.MemberNAME);
		isMarket = getIntent().getBooleanExtra(Define.IsMarket, false);
		findTitleViews();
		initdate();
	}

	private void findTitleViews() {
		if (isMarket) {
			titleView.setVisibility(View.GONE);
		}
		TextView titleText = (TextView) findViewById(R.id.title_name);
//		titleText.setTypeface(CmsApplication.getTitleFace(this));
		if (name.equals("support")){
			titleText.setText(getResources().getString(R.string.title_support));
			url = NetUrl.getUrlSupport();
		}else if (name.equals("terms")){
			titleText.setText(getResources().getString(R.string.txt_terms));
			url = NetUrl.getUrlTerms();
		}else if (name.equals("about")){
			titleText.setText(getResources().getString(R.string.title_about));
			url = NetUrl.getUrlAbout()+ Config.getInstance().getVersionName(this);
		}else if (name.equals("credits")){
			titleText.setText(getResources().getString(R.string.txt_policies));
			url = NetUtils.getPointsPoliciesUrl();
		}else {
			titleText.setText(name);
	}
		ImageView btn_back = (ImageView) findViewById(R.id.img_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setExitSwichLayout();
			}
		});
	}

	boolean isVeriSuc = false;
	@SuppressLint("SetJavaScriptEnabled")
	private void initdate() {
		loadingBar = (MyLoadingBar)findViewById(R.id.loading);
		pbarMember = (ProgressBar) findViewById(R.id.rbar_member);
		member_webview = (WebView) findViewById(R.id.member_webview);

		WebSettings seting = member_webview.getSettings();
		// ����WebView���ԣ��ܹ�ִ�еĽű�
		seting.setJavaScriptEnabled(true);
		// ���ÿ��Է����ļ�
		seting.setAllowFileAccess(true);
		// ����֧������
		seting.setBuiltInZoomControls(false);

		seting.setSavePassword(false);
		
		seting.setLoadWithOverviewMode(true);
		member_webview.setWebChromeClient(new WebChromeClienter());
		member_webview.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {

				loadingBar.setVisibility(View.VISIBLE);
				loadingBar.start();
				super.onPageStarted(view, url, favicon);
			}
			@Override
			public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
				loadingBar.setVisibility(View.GONE);
				loadingBar.stop();
				super.onReceivedError(view, request, error);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return false;
			}
		});

		member_webview.loadUrl(url);
		
//		member_webview.postUrl(url, postData)
	}

	class WebChromeClienter extends WebChromeClient{


		public void onProgressChanged(WebView view, int newProgress) {
			
			if (newProgress == 100) {
//				pbarMember.setVisibility(View.GONE);
				loadingBar.setVisibility(View.GONE);
				loadingBar.stop();
			}
			super.onProgressChanged(view, newProgress);

		};
	};
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (isInvaild && isMarket) {
			UserInfoEntity userInfoEntity = CmsApplication.getUserInfoEntity();
			String currentUrl = URLUtils.getMemberMarketReturn(Constant.SYS_ID,
					userInfoEntity.getToken(), userInfoEntity.getMID(), member_webview.getUrl());
			member_webview.loadUrl(currentUrl);
			isInvaild = false;
		} 
		if (isMarket) {
//			UserInfoEntity userInfoEntity = CmsApplication.getUserInfoEntity();
//			String currentUrl = URLUtils.getMemberMarketReturn(Config.SYS_ID,
//					userInfoEntity.getToken(), userInfoEntity.getMID(), member_webview.getUrl());
//			System.out.println("resurl--"+currentUrl);
		}
	}
	
	/**
	 *  ������ǰ%80�Ķ���
	 */
	private void ProgressLoad() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				int counter = 0;
				for (int i = 0; i < 7; i++) {
					counter = (i+1)*10;
					pbarMember.setProgress(counter);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (member_webview.canGoBack()) {
				member_webview.goBack();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
