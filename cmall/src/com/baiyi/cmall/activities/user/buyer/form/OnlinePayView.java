package com.baiyi.cmall.activities.user.buyer.form;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.user.buyer.form.PayPasswordEditText.SecurityEditCompleListener;
import com.baiyi.cmall.activities.webview.ZhaoHangPayActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.pageviews.BasePageView;
import com.baiyi.cmall.popupwindow.PayPopupWindow;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 线上支付选择View
 * 
 * @author lizl
 * 
 */
public class OnlinePayView extends BasePageView implements OnClickListener {

	private Context context;
	// 余额支付
	private LinearLayout mLinBalancePay;
	// 储蓄卡支付
	private LinearLayout mLinbankCardPay;
	// 信用卡支付
	private LinearLayout mLinCreditCardPay;
	// 第三方支付
	private LinearLayout mLinThreePartyPay;
	// 招行在线支付
	private LinearLayout mLinZhaoHangPay;

	private String zhaoHangForm;
	private String orderID;

	public OnlinePayView(Context context, String orderID) {
		super(context);
		this.context = context;
		this.orderID = orderID;
		httpTest();
		initContent();

	}

	/**
	 * 获取招行支付的接口 数据
	 */
	private void httpTest() {

		new Thread(new Runnable() {

			@Override
			public void run() {

				HttpGet get = new HttpGet(AppNetUrl.getZhiFuUrl(orderID));
				get.addHeader("token", CmallApplication.getUserInfo()
						.getToken());
				HttpClient client = new DefaultHttpClient();
				try {

					HttpResponse response = client.execute(get);
					int code = response.getStatusLine().getStatusCode();
					if (HttpStatus.SC_OK == code) {
						zhaoHangForm = EntityUtils.toString(response
								.getEntity());
						Log.d("TT", zhaoHangForm);
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 初始化内容
	 */
	private void initContent() {

		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.pay_item_online, null);
		this.addView(view);

		mLinBalancePay = (LinearLayout) findViewById(R.id.lin_balance_pay);
		mLinbankCardPay = (LinearLayout) findViewById(R.id.lin_bank_card_pay);
		mLinCreditCardPay = (LinearLayout) findViewById(R.id.lin_credit_card_pay);
		mLinThreePartyPay = (LinearLayout) findViewById(R.id.lin_three_party_pay);
		mLinZhaoHangPay = (LinearLayout) findViewById(R.id.lin_zhao_hang_pay);

		mLinBalancePay.setOnClickListener(this);
		mLinbankCardPay.setOnClickListener(this);
		mLinCreditCardPay.setOnClickListener(this);
		mLinThreePartyPay.setOnClickListener(this);
		mLinZhaoHangPay.setOnClickListener(this);

	}

	private PayPopupWindow payYuEPopupWindow;
	private PayPopupWindow passPopupWindow;

	/**
	 * 显示余额支付pop对话框
	 */
	private void showYuEPop() {
		View popView = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.pop_balance_pay, null);

		payYuEPopupWindow = new PayPopupWindow(context);
		payYuEPopupWindow.showPop(this, popView, Gravity.BOTTOM, 0);

		initYuEPopView(popView);
	}

	private void initYuEPopView(View popView) {
		// 取消余额支付对话框的按钮
		ImageView mImgDeletePop = (ImageView) popView
				.findViewById(R.id.img_delete_pop);
		// 确认支付的按钮
		TextView mTvPayOk = (TextView) popView.findViewById(R.id.btn_pay_ok);
		mImgDeletePop.setOnClickListener(this);
		mTvPayOk.setOnClickListener(this);
	}

	/**
	 * 显示余额支付pop对话框
	 */
	private void showPassPop() {
		View popView = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.pay_pass_edit_text, null);

		passPopupWindow = new PayPopupWindow(context);
		passPopupWindow.showPop(this, popView, Gravity.CENTER_VERTICAL, 1);

		initPassPopView(popView);
	}

	private void initPassPopView(View popView) {
		// 取消余额支付对话框的按钮
		ImageView mImgDeletePop = (ImageView) popView
				.findViewById(R.id.img_delete_pop);
		mImgDeletePop.setOnClickListener(this);
		PayPasswordEditText passwordEditText = (PayPasswordEditText) popView
				.findViewById(R.id.tv_pay_pass);
		passwordEditText
				.setSecurityEditCompleListener(new SecurityEditCompleListener() {

					@Override
					public void onNumCompleted(String num) {

						((BaseActivity) context).displayToast("支付成功");
					}
				});

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.lin_balance_pay:// 显示余额支付的窗口
			showYuEPop();
			break;
		case R.id.lin_bank_card_pay:
			break;
		case R.id.lin_credit_card_pay:
			break;
		case R.id.lin_three_party_pay:
			break;
		case R.id.lin_zhao_hang_pay:
			((BaseActivity) context).goActivity(ZhaoHangPayActivity.class,
					zhaoHangForm);
			break;
		case R.id.img_delete_pop:// 退出余额支付的pop
			if (null != payYuEPopupWindow) {
				if (payYuEPopupWindow.isShowing()) {
					payYuEPopupWindow.dismiss();
				}
			}
			if (null != passPopupWindow) {
				if (passPopupWindow.isShowing()) {
					passPopupWindow.dismiss();
				}
			}
			break;
		case R.id.btn_pay_ok:// 确认支付按鈕
			payYuEPopupWindow.dismiss();
			showPassPop();
			break;

		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	}

	@Override
	public void onDestroy() {
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	}

}
