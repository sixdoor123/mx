package com.baiyi.cmall.activities.user.help.web;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.activities.user.login.web.BaseWebActivity;

import android.webkit.WebView;

/**
 * ֧�ְ���
 * @author sunxy
 */

//FIXME (������)
public class SupportHelpeActivity extends BaseWebActivity {

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
		return "֧�ְ���";
	}

	@Override
	public String getUrlInfo() {
		
		return Config.ROOT_URL+"page/help/help.html";
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
