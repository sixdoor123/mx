package com.baiyi.cmall.activities.user.login.web;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.activities.user.login.ExitLogin;
import com.baiyi.cmall.application.CmallApplication;

import android.content.Intent;
import android.util.Log;
import android.webkit.WebView;

/**
 * 会员中心 个人会员主页面：/Member/Index?token={个人会员token}&sysId={系统ID}
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-3-10 上午11:13:36
 */
public class MemberCenterWebActivity extends BaseWebActivity {

	private String token = "";
	private String sysId = "";

	@Override
	public int getLoadStyle() {
		return URL_STATE;
	}

	@Override
	public boolean isShowTitleInfo() {
		return false;
	}

	@Override
	public String getTitleInfo() {
		return "";
	}

	/**
	 * APP:http://10.20.22.5:8061/Member/Index?token=HH6ZMKEI3G&sysId=100
	 * 个人会员界面：http://10.20.22.5:8061/Member/Index?token=BZ6VELR7E9&sysId=100
	 * 个人会员注册：http://10.20.22.5:8061/home/Register_ST
	 */
	@Override
	public String getUrlInfo() {
		token = CmallApplication.getUserInfo().getToken();
		String url = ROOT_HTML + "Member/Index?token=" + token + "&sysId=" + Config.System_Id;
		Log.d("TAG", "个人会员中心---" + url);
		return url;

	}

	@Override
	public void setWebView(WebView webView) {
	
	}

	@Override
	public boolean shouldOverrideUrlLoad(WebView view, String url) {
		if (url.endsWith(BACK_TO_APP)) {
			finish();
			return false;
		} else if (url.endsWith("loginout")) {
			// 注销
			ExitLogin.getInstence(MemberCenterWebActivity.this).cleer();
			Intent intent = new Intent();
			setResult(1, intent);
			finish();
			displayToast("退出成功");
			return false;
		} else {
			// "8029/"
			if (!url.endsWith(ROOT_HTML.split(":")[2])) {
				view.loadUrl(url);
			}
			view.setLayoutParams(params);
		}
		Log.d("TAG", "shouldOverrideUrlLoading---" + url);
		return true;
	}
}
