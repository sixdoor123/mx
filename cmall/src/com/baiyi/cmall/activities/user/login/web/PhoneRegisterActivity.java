package com.baiyi.cmall.activities.user.login.web;

import com.baiyi.cmall.activities.webview.BaseWebViewActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 手机注册
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-3-10 上午11:13:36
 */
public class PhoneRegisterActivity extends BaseWebActivity {

	@Override
	public int getLoadStyle() {
		// TODO Auto-generated method stub
		return URL_STATE;
	}

	@Override
	public boolean isShowTitleInfo() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getTitleInfo() {
		return "";
	}

	/**
	 * APP:
	 * 个人会员界面：http://10.20.22.5:8061/Member/Index?token=BZ6VELR7E9&sysId=100
	 * 个人会员注册：http://10.20.22.5:8061/home/Register_ST
	 */
	@Override
	public String getUrlInfo() {
		// return Config.ROOT_HTML + "home/Register_ST";
		return ROOT_HTML + "Home/Reg?sysId=" + com.baiyi.cmall.Config.System_Id;
	}

	@Override
	public void setWebView(WebView webView) {
		// webView.addJavascriptInterface(new Back(), "");
	}

	class Back extends Object {
		public void onBack() {

		}
	}

	@Override
	public boolean shouldOverrideUrlLoad(WebView view, String url) {
		if (url.endsWith(BACK_TO_APP)) {
			finish();
			return false;
		}
		Log.d("TAG", "url---" + url);
		view.loadUrl(url);
		view.setLayoutParams(params);
		return true;
	}
}
