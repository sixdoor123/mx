package com.baiyi.cmall.activities.user.login.web;


import com.baiyi.cmall.Config;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.cmall.activities.user.login.web.MerchantCenterActivity;
import com.baiyi.cmall.utils.XmlName;
import android.util.Log;
import android.webkit.WebView;

/**
 * ��Ӧ������(�ɹ�˾���Ƶ������) ��ҵ��Ա��ҳ�棺/Enterprise/Index?token={��ҵ��Աtoken}&sysId={ϵͳID}
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-3-10 ����11:13:36
 */
public class MerchantCenterActivity extends BaseWebActivity {
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
		return "";
	}

	/**
	 * APP:
	 * ���˻�Ա���棺http://10.20.22.5:8061/Member/Index?token=BZ6VELR7E9&sysId=100
	 * ���˻�Աע�᣺http://10.20.22.5:8061/home/Register_ST
	 */
	@Override
	public String getUrlInfo() {
		token = getIntent().getStringExtra("temp");
		return ROOT_HTML + "Enterprise/Index?token=" + token + "&sysId="
				+ Config.System_Id;
	}

	@Override
	public void setWebView(WebView webView) {
		// webView.addJavascriptInterface(new Back(), "onclick");
	}

	class Back extends Object {
		public void onBack() {
			displayToast("------------onBack-------------");
		}
	}

	@Override
	public boolean shouldOverrideUrlLoad(WebView view, String url) {
		if (url.endsWith(BACK_TO_APP)) {
			finish();
			return false;
		} else if (url.endsWith("loginout") || url.endsWith("entlogin")) {
			// ע��
			XmlUtils.getInstence(MerchantCenterActivity.this).deleteXmlValue(XmlName.Company_Token);
			displayToast("��ҵ�˺��˳��ɹ�");
			finish();
			return false;
		} else {
			if (!url.endsWith(ROOT_HTML.split(":")[2])) {
				view.loadUrl(url);
				view.setLayoutParams(params);
			}
		}
		Log.d("TAG", "url---" + url);
		return true;
	}
}
