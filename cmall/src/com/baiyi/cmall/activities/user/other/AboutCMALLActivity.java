package com.baiyi.cmall.activities.user.other;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.activities.user.login.web.BaseWebActivity;

import android.webkit.WebView;


/**
 * 关于CMALL
 * 
 * @author sunxy
 *
 */
public class AboutCMALLActivity extends BaseWebActivity {

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
		// TODO Auto-generated method stub
		return "支持帮助";
	}

	@Override
	public String getUrlInfo() {
		
		return  Config.ROOT_URL+"page/about/about.html";
	}

	@Override
	public void setWebView(WebView webView) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean shouldOverrideUrlLoad(WebView view, String url) {
		if (url.endsWith(BACK_TO_APP)) {
			finish();
		}
		return true;
	}
}
