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
 * ����֧��ѡ��View
 * 
 * @author lizl
 * 
 */
public class OnlinePayView extends BasePageView implements OnClickListener {

	private Context context;
	// ���֧��
	private LinearLayout mLinBalancePay;
	// ���֧��
	private LinearLayout mLinbankCardPay;
	// ���ÿ�֧��
	private LinearLayout mLinCreditCardPay;
	// ������֧��
	private LinearLayout mLinThreePartyPay;
	// ��������֧��
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
	 * ��ȡ����֧���Ľӿ� ����
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
	 * ��ʼ������
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
	 * ��ʾ���֧��pop�Ի���
	 */
	private void showYuEPop() {
		View popView = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.pop_balance_pay, null);

		payYuEPopupWindow = new PayPopupWindow(context);
		payYuEPopupWindow.showPop(this, popView, Gravity.BOTTOM, 0);

		initYuEPopView(popView);
	}

	private void initYuEPopView(View popView) {
		// ȡ�����֧���Ի���İ�ť
		ImageView mImgDeletePop = (ImageView) popView
				.findViewById(R.id.img_delete_pop);
		// ȷ��֧���İ�ť
		TextView mTvPayOk = (TextView) popView.findViewById(R.id.btn_pay_ok);
		mImgDeletePop.setOnClickListener(this);
		mTvPayOk.setOnClickListener(this);
	}

	/**
	 * ��ʾ���֧��pop�Ի���
	 */
	private void showPassPop() {
		View popView = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.pay_pass_edit_text, null);

		passPopupWindow = new PayPopupWindow(context);
		passPopupWindow.showPop(this, popView, Gravity.CENTER_VERTICAL, 1);

		initPassPopView(popView);
	}

	private void initPassPopView(View popView) {
		// ȡ�����֧���Ի���İ�ť
		ImageView mImgDeletePop = (ImageView) popView
				.findViewById(R.id.img_delete_pop);
		mImgDeletePop.setOnClickListener(this);
		PayPasswordEditText passwordEditText = (PayPasswordEditText) popView
				.findViewById(R.id.tv_pay_pass);
		passwordEditText
				.setSecurityEditCompleListener(new SecurityEditCompleListener() {

					@Override
					public void onNumCompleted(String num) {

						((BaseActivity) context).displayToast("֧���ɹ�");
					}
				});

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.lin_balance_pay:// ��ʾ���֧���Ĵ���
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
		case R.id.img_delete_pop:// �˳����֧����pop
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
		case R.id.btn_pay_ok:// ȷ��֧�����o
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
