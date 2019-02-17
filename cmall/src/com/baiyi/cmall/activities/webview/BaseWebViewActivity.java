package com.baiyi.cmall.activities.webview;

import android.annotation.SuppressLint;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import com.baiyi.cmall.Config;
import com.baiyi.cmall.activities.base.BaseMsgActivity;
import com.baiyi.cmall.activities.user.login.utils.BaseAddressUtils;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 公共的 WebView加载页面
 * 
 * @author lizl
 * 
 */
public abstract class BaseWebViewActivity extends BaseMsgActivity {

	// 进入会员中心路径
	public String HTML_PATH = "";

	public String BACK_TO_APP = "backtoapp";
	// 所加载的数据
	private String entry;

	// 加载数据的状态
	private int loadStyleState;

	// 视图控件
	private WebView webView;

	// 进度条
	private MyLoadingBar progressBar;

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

	/**
	 * 初始话数据（URL）
	 */
	private void initData() {

		HTML_PATH = CmallApplication.getUserInfo().getBaseAddress();
		if (!HTML_PATH.startsWith("http://")) {
			HTML_PATH = BaseAddressUtils.readBaseUrl();
		}

		entry = getUrlInfo();
		loadStyleState = getLoadStyle();
	}

	/**
	 * 初始化控件
	 */
	@SuppressLint("SetJavaScriptEnabled")
	private void initView() {

		ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_webview,
				win_content);

		startProgress();
		webView = (WebView) findViewById(R.id.web_zhaohang);
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);// 设置支持JavaScript脚本
		settings.setUseWideViewPort(true);// 关键点
		settings.setAllowFileAccess(true);// 允许访问文件
		settings.setSupportZoom(true); // 支持缩放
		settings.setBuiltInZoomControls(false); // 设置显示缩放按钮
		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		settings.setLoadWithOverviewMode(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);// 支持通过JS打开新的窗口

		if (URL_STATE == loadStyleState) {

			webView.loadUrl(entry);
		} else if (DATA_STATE == loadStyleState) {

			webView.loadDataWithBaseURL(null, entry, null, "utf-8", null);
		}

		setWebView(webView);

	}

	/**
	 * 加载进度条
	 */
	public void startProgress() {
		progressBar = (MyLoadingBar) findViewById(R.id.web_load);
		progressBar
				.setPadding(
						0,
						Config.getInstance().getScreenHeight(
								BaseWebViewActivity.this) / 2, 0, 0);
		progressBar.setVisibility(View.VISIBLE);
		progressBar.setProgressInfo("正在加载中...");
		progressBar.start();

	}

	/**
	 * 停止进度条
	 */
	public void stopProgress() {

		progressBar.stop();
		progressBar.setVisibility(View.GONE);

	}

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
