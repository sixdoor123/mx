package com.baiyi.cmall.activities.webview;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 新闻页
 * 
 * @author lizl
 * 
 */
public class NewsWebViewActivity extends BaseWebViewActivity {

	@Override
	public int getLoadStyle() {

		return URL_STATE;
	}

	@Override
	public boolean isShowTitleInfo() {
		return true;
	}

	@Override
	public String getTitleInfo() {
		return "新闻";
	}

	@Override
	public String getUrlInfo() {
		return getIntent().getStringExtra("arg");
	}

	@Override
	public void setWebView(WebView webView) {

		webView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {

				super.onPageFinished(view, url);
				stopProgress();
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

		});
	}

}
