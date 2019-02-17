package com.baiyi.cmall.activities.user.login.web;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.utils.MobileStateUtils;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.XmlName;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.core.util.ContextUtil;

import android.graphics.Bitmap;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout.LayoutParams;

/**
 * 公共的 WebView加载页面
 * 
 * @author sunxy
 * 
 */
public abstract class BaseWebActivity extends BaseActivity {
	public String ROOT_HTML = /* "http://10.20.22.5:8063/" */null;
	public String BACK_TO_APP = "backtoapp";
	// 所加载的数据
	private String entry;

	// 加载数据的状态
	private int loadStyleState;

	// 视图控件
	private WebView webView;

	// 会哦去加载数据的方式
	public abstract int getLoadStyle();

	// 是否显示标题栏
	public abstract boolean isShowTitleInfo();

	// 获取标题栏信息
	public abstract String getTitleInfo();

	// 获取加载数据
	public abstract String getUrlInfo();

	// 获取加载数据
	public abstract void setWebView(WebView webView);

	// URL格式数据
	public static final int URL_STATE = 1;

	// HTML格式数据
	public static final int DATA_STATE = 2;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);

		initData();
		initTitle();
		initView();
	}

	private EventTopTitleView topTitleView;

	/**
	 * 初始化标题栏
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName(getTitleInfo());
		win_title.addView(topTitleView);

		if (isShowTitleInfo()) {
			win_title.setVisibility(View.VISIBLE);
		} else {
			win_title.setVisibility(View.GONE);
		}
	}

	public LayoutParams params;

	/**
	 * 初始话数据（URL）
	 */
	private void initData() {

		int stateHeight = MobileStateUtils.getStatusHeight(this);
		int h = (stateHeight == 0 ? 40 : stateHeight);
		params = new LayoutParams(getScreenWidth(), //
				getScreenHeight() - h);

		ROOT_HTML = CmallApplication.getUserInfo().getBaseAddress();
		if (!TextViewUtil.isStringEmpty(ROOT_HTML)) {
			if (!ROOT_HTML.startsWith("http://")) {
				ROOT_HTML = XmlUtils.getInstence(this).getXmlStringValue(XmlName.Base_Address);
			}
			if (!ROOT_HTML.endsWith("/")) {
				ROOT_HTML += "/";
			}
		} else {
			// http://117.34.95.103:8029
			ROOT_HTML = "http://117.34.95.103:8029/";
		}
		entry = getUrlInfo();
		loadStyleState = getLoadStyle();
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		// displayToast("height：" + getScreenHeight());
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
			ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_webview_h, win_content);
		} else {
			ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_webview_l, win_content);
		}
		startLoading();
		webView = (WebView) findViewById(R.id.web_zhaohang);
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);// 设置支持javascript脚本
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);

		// 组织弹出保存密码对话框
		settings.setSavePassword(false);
		settings.setSaveFormData(false);

		if (URL_STATE == loadStyleState) {

			webView.loadUrl(entry);
		} else if (DATA_STATE == loadStyleState) {
			webView.loadDataWithBaseURL(null, entry, null, "utf-8", null);
		}

		// setWebView(webView);

		webView.setWebChromeClient(new WebChromeClient());
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				startLoading();
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				stopLoading();
				super.onPageFinished(view, url);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// if (url.endsWith("Home/Fail")) {
				//
				// return true;
				// }
				stopLoading();
				return shouldOverrideUrlLoad(view, url);
			}
		});

	}

	public abstract boolean shouldOverrideUrlLoad(WebView view, String url);

	/**
	 * 坚挺返回键
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack(); // goBack()表示返回WebView的上一页面
			return true;
		}
		finish();// 结束退出程序
		return false;
	}
}
