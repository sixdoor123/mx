package com.baiyi.cmall.activities.main.buy;

import com.baiyi.cmall.R;
import com.baiyi.cmall.TLog;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.mall.MallDefine;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.core.util.ContextUtil;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WebPayActivity extends BaseActivity {
	private WebView web;
	private ImageView btnBack;
	private EventTopTitleView topTitleView = null;
	private ProgressBar progressBar;
	private static final String Tag = WebPayActivity.class.getName();

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void initWin(boolean hasScrollView) {
		super.initWin(true);
		Intent itent = getIntent();
		String url = itent.getStringExtra(MallDefine.PayWebUrl);
		// 内容视图
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_pay_web, null);
		win_content.addView(view);
		initTitleView();
		_getContentViews();
		_getClosePayFor();
		WebSettings seting = web.getSettings();
//		seting.setTextSize(TextSize.SMALLER);
//		seting.setTextSize(TextSize.NORMAL);
//		seting.setTextSize(TextSize.LARGER);
//		seting.setTextSize(TextSize.LARGEST);
		
//        SMALLEST(50),
//        SMALLER(75),
//        NORMAL(100),
//        LARGER(150),
//        LARGEST(200);
		// 设置WebView属性，能够执行的脚本
		seting.setJavaScriptEnabled(true);
		// 设置可以访问文件
		seting.setAllowFileAccess(true);
		// 设置支持缩放
		seting.setBuiltInZoomControls(true);
		CookieManager.getInstance().setAcceptCookie(true);
		web.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onJsAlert(WebView view, final String url, String message,
					JsResult result) {
				// TODO Auto-generated method stub
				TLog.i(Tag, "onJsAlert支付当前网址111 => "+url+"----"+message);
				AlertDialog.Builder builder = new AlertDialog.Builder(
						WebPayActivity.this);

				builder.setMessage(message)
						.setNeutralButton("确定", new OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								TLog.i(Tag, "onJsAlert支付当前网址 => /////////////////"+url);
								arg0.dismiss();
							}
						}).show();

				result.cancel();
				return true;
			}

			public void onProgressChanged(WebView view, int newProgress) {

				// if (progressBar.getProgress() < newProgress) {
				progressBar.setProgress(newProgress);
				// }
				if (newProgress == 100) {
					progressBar.setVisibility(View.GONE);
				}
				// if (newProgress == 100) {
				// progressBar.setVisibility(View.GONE);
				// } else {
				// if (progressBar.getVisibility() == View.GONE)
				// progressBar.setVisibility(View.VISIBLE);
				//
				// progressBar.setProgress(newProgress);
				// }

				super.onProgressChanged(view, newProgress);

			};
		});
//		web.addJavascriptInterface(new Object()
//		{
//			OnClickListener()
//			{
//				
//			}
//		}, "");
		web.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				TLog.i(Tag, "支付当前网址 => "+url);
				web.requestFocus();
				return false;
			}
			@Override
			public void onLoadResource(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onLoadResource(view, url);
				TLog.i(Tag, "onLoadResource支付当前网址 => "+url);
				
			}
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
//				System.out.println("=="+url);
				if(URLRequest(url))
				{
					finishCurrent();
				}
				TLog.i(Tag, "onPageFinished支付当前网址 => "+url);
			}
			
		});
		// web.setWebViewClient(new WebViewClient());
		// 加载需要显示的网页
		web.loadUrl(url);
	}

	private void initTitleView() {

		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("确认支付");
		topTitleView.setTitleNewsOnclick(new TitleNewsOnclick() {

			@Override
			public void setTitleNewsOnclickLister(boolean isPop) {

				topTitleView.displayPoP(MsgItemUtil.Pop_Default_txt, MsgItemUtil.Pop_Default_img);

			}
		});
		topTitleView.setNewsPopItemOnclick(new NewsPopItemOnclick() {

			@Override
			public void setNewsPopItemOnclickLister(int state) {
				MsgItemUtil.onclickPopItem(state, WebPayActivity.this);
			}
		});
		win_title.addView(topTitleView);

	}
	
	private void finishCurrent()
	{
		Intent intent = new Intent();
		this.setResult(RESULT_OK, intent);
		finish();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
//		Intent intent = new Intent();
//		intent.putExtra(DeFine.Bank_Pay_State, true);
//		this.setResult(RESULT_OK, intent);
		super.onBackPressed();
	}
	
	/**
	 * 获取页面控件
	 */
	private void _getContentViews() {
		web = (WebView) findViewById(R.id.bankweb);
//		btnBack = (ImageView) findViewById(R.id.icon_back);
//		btnBack.setOnClickListener(new BtnBackListener());
		progressBar = (ProgressBar) findViewById(R.id.probar_bank);
	}

	/***
	 * 
	 * @author leict 返回事件 关闭页面
	 */
	private final class BtnBackListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			finishCurrent();
		}
	}

	/**
	 * 关闭
	 */
	public void _getClosePayFor() {
//		TextView close = (TextView) findViewById(R.id.right_text);
//		close.setVisibility(View.VISIBLE);
//		close.setText("关闭");
//		close.setOnClickListener(new PayCloseListener());
	}

	/**
	 * 注册关闭事件
	 * 
	 * @author leict
	 * 
	 */
	private final class PayCloseListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			finishCurrent();
		}
	}

	public static boolean URLRequest(String URL) {
		if(Utils.isStringEmpty(URL))
		{
			return false;
		}
		String[] arrSplit = null;

		String strUrlParam = TruncateUrlPage(URL);
		if (strUrlParam == null) {
			return false;
		}
		arrSplit = strUrlParam.split("[&]");
		for (String strSplit : arrSplit) {
			String[] arrSplitEqual = null;
			arrSplitEqual = strSplit.split("[=]");
			if(arrSplitEqual[0].equals("Succeed") || arrSplitEqual[0].equals("succeed"))
			{
				if(arrSplitEqual[1] == null)
				{
					continue;
				}
				if(arrSplitEqual[1].equals("Y") || arrSplitEqual[1].equals("y"))
				{
					return true;
				}
			}
		}
		return false;
	}

	private static String TruncateUrlPage(String strURL) {
		String strAllParam = null;
		String[] arrSplit = null;

		strURL = strURL.trim().toLowerCase();

		arrSplit = strURL.split("[?]");
		if (strURL.length() > 1) {
			if (arrSplit.length > 1) {
				if (arrSplit[1] != null) {
					strAllParam = arrSplit[1];
				}
			}
		}

		return strAllParam;
	}

}
