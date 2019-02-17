package com.baiyi.cmall.activities.webview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.baiyi.cmall.TLog;

/**
 * 招商银行支付界面
 * @author lizl
 *
 */
public class ZhaoHangPayActivity extends BaseWebViewActivity {

	@Override
	public int getLoadStyle() {
		return DATA_STATE;
	}

	@Override
	public boolean isShowTitleInfo() {
		return true;
	}

	@Override
	public String getTitleInfo() {
		return "招商银行在线支付";
	}

	@Override
	public String getUrlInfo() {
		return getIntent().getStringExtra("arg");
	}

	@Override
	public void setWebView(WebView webView) {

		webView.setWebChromeClient(new WebChromeClient() {

			@Override
			public boolean onJsAlert(WebView view, final String url,
					String message, JsResult result) {

				AlertDialog.Builder builder = new AlertDialog.Builder(
						ZhaoHangPayActivity.this);

				builder.setMessage(message)
						.setNeutralButton("确定", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								TLog.i("TT", "onJsAlert支付当前网址 => /////" + url);
								dialog.dismiss();
							}

						}).show();

				result.cancel();
				return true;
			}

			@Override
			public void onProgressChanged(WebView view, int newProgress) {

				if (100 == newProgress) {
					stopProgress();
				}
				super.onProgressChanged(view, newProgress);
			}
		});
	}
}
