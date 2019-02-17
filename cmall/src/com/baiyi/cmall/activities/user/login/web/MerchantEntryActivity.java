package com.baiyi.cmall.activities.user.login.web;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.activities.user.login.CompanyLoginActivity;
import com.baiyi.cmall.activities.user.login.ExitLogin;
import com.baiyi.cmall.activities.user.login.MemberLoginActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.utils.TextViewUtil;

import android.util.Log;
import android.webkit.WebView;

/**
 * 供应商入驻
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-3-10 上午11:13:36
 */
public class MerchantEntryActivity extends BaseWebActivity {

	private String token;

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
		return "供应商入驻";
	}

	/**
	 * APP:
	 * 个人会员界面：http://10.20.22.5:8061/Member/Index?token=BZ6VELR7E9&sysId=100
	 * 个人会员注册：http://10.20.22.5:8061/home/Register_ST
	 */
	@Override
	public String getUrlInfo() {
		token = CmallApplication.getUserInfo().getToken();
		// return Config.ROOT_HTML + "enterprise/register?token="+token;
		return ROOT_HTML + "Enterprise/Reg?sysId=" + Config.System_Id + "&token="
				+ TextViewUtil.getEditString(token);
	}

	@Override
	public void setWebView(WebView webView) {

		// webView.addJavascriptInterface(new Back(), "");
	}

	@Override
	public boolean shouldOverrideUrlLoad(WebView view, String url) {
		Log.d("TAG", "url-234543564654--" + url);
		if (url.endsWith(BACK_TO_APP)) {
			finish();
			return true;
		} else if (url.endsWith("entlogin")) {
			goActivity(CompanyLoginActivity.class);
			finish();
			return true;
		} else if (url.endsWith("loginout")) {
			ExitLogin.getInstence(MerchantEntryActivity.this).cleer();
			goActivity(MemberLoginActivity.class);
			finish();
			return true;
		} else {
		
			if (!url.endsWith("success")) {
				view.loadUrl(url);
				view.setLayoutParams(params);
			}
		}
		return true;
	}
}
