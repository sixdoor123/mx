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
 * ������ WebView����ҳ��
 * 
 * @author lizl
 * 
 */
public abstract class BaseWebViewActivity extends BaseMsgActivity {

	// �����Ա����·��
	public String HTML_PATH = "";

	public String BACK_TO_APP = "backtoapp";
	// �����ص�����
	private String entry;

	// �������ݵ�״̬
	private int loadStyleState;

	// ��ͼ�ؼ�
	private WebView webView;

	// ������
	private MyLoadingBar progressBar;

	// ��Ŷȥ�������ݵķ�ʽ
	public abstract int getLoadStyle();

	// �Ƿ���ʾ������
	public abstract boolean isShowTitleInfo();

	// ��ȡ��������Ϣ
	public abstract String getTitleInfo();

	// ��ȡ��������
	public abstract String getUrlInfo();

	// ��ȡ��������
	public abstract void setWebView(WebView webView);

	// URL��ʽ����
	public static final int URL_STATE = 1;

	// HTML��ʽ����
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
	 * ��ʼ��������
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
	 * ��ʼ�����ݣ�URL��
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
	 * ��ʼ���ؼ�
	 */
	@SuppressLint("SetJavaScriptEnabled")
	private void initView() {

		ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_webview,
				win_content);

		startProgress();
		webView = (WebView) findViewById(R.id.web_zhaohang);
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);// ����֧��JavaScript�ű�
		settings.setUseWideViewPort(true);// �ؼ���
		settings.setAllowFileAccess(true);// ��������ļ�
		settings.setSupportZoom(true); // ֧������
		settings.setBuiltInZoomControls(false); // ������ʾ���Ű�ť
		settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		settings.setLoadWithOverviewMode(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);// ֧��ͨ��JS���µĴ���

		if (URL_STATE == loadStyleState) {

			webView.loadUrl(entry);
		} else if (DATA_STATE == loadStyleState) {

			webView.loadDataWithBaseURL(null, entry, null, "utf-8", null);
		}

		setWebView(webView);

	}

	/**
	 * ���ؽ�����
	 */
	public void startProgress() {
		progressBar = (MyLoadingBar) findViewById(R.id.web_load);
		progressBar
				.setPadding(
						0,
						Config.getInstance().getScreenHeight(
								BaseWebViewActivity.this) / 2, 0, 0);
		progressBar.setVisibility(View.VISIBLE);
		progressBar.setProgressInfo("���ڼ�����...");
		progressBar.start();

	}

	/**
	 * ֹͣ������
	 */
	public void stopProgress() {

		progressBar.stop();
		progressBar.setVisibility(View.GONE);

	}

	/**
	 * ��ͦ���ؼ�
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack(); // goBack()��ʾ����WebView����һҳ��
			return true;
		}
		finish();// �����˳�����
		return false;
	}
}
