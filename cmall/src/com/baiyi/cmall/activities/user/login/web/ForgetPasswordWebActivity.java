package com.baiyi.cmall.activities.user.login.web;


import com.baiyi.cmall.Config;

import android.R.integer;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 忘记密码 个人会员忘记密码：/Home/FindPwd?sysId={系统ID}
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-3-10 上午11:13:36
 */
public class ForgetPasswordWebActivity extends BaseWebActivity {

	private String token = "";
	private String sysId = "";

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
	 * Web路径
	 * 
	 * 企业会员忘记密码 /Home/FindEnPwd?sysId={系统ID}
	 */
	@Override
	public String getUrlInfo() {
		int state = getIntent().getIntExtra("temp", 0);
		String url = ROOT_HTML;
		if (1==state) {
			return url + "Home/FindEnPwd?sysId="+Config.System_Id;
		}
		return url + "Home/FindPwd?sysId="+Config.System_Id;
	}

	@Override
	public void setWebView(WebView webView) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean shouldOverrideUrlLoad(WebView view, String url) {
		Log.d("TAG", "shouldOverrideUrlLoading---" + url);
		if (url.endsWith(BACK_TO_APP) || url.equals(BACK_TO_APP)
				|| url.endsWith("loginout") || url.equals("loginout")) {
			finish();
			return true;
		}
		view.loadUrl(url);
		view.setLayoutParams(params);
		return true;
	}

}
