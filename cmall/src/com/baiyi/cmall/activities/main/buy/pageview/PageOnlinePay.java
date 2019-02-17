package com.baiyi.cmall.activities.main.buy.pageview;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.buy.BuyNetUrl;
import com.baiyi.cmall.activities.main.buy.dialog.DialogYuePay;
import com.baiyi.cmall.dialog.DialogBase;
import com.baiyi.cmall.pageviews.BasePageView;
import com.baiyi.cmall.utils.IntentClassChangeUtils;
import com.baiyi.core.util.ContextUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

public class PageOnlinePay extends BasePageView{
	
	private LinearLayout btnYue = null;
	private LinearLayout btnCard = null;
	private LinearLayout btnCredit = null;
	private LinearLayout btnOther = null;
	
	private DialogYuePay dialogYuePay;
	//订单id
	private String oi = null;
	//单价金额
	private String unPrice = null;

	public PageOnlinePay(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		ContextUtil.getLayoutInflater(getContext()).inflate(R.layout.page_online_pay, this);
		
		btnYue = (LinearLayout)findViewById(R.id.btn_yue);
		btnCard = (LinearLayout)findViewById(R.id.btn_card);
		btnCredit = (LinearLayout)findViewById(R.id.btn_credit);
		btnOther = (LinearLayout)findViewById(R.id.btn_other);
		btnYue.setOnClickListener(new PaymentMethodOnclick());
		btnCard.setOnClickListener(new PaymentMethodOnclick());
		btnCredit.setOnClickListener(new PaymentMethodOnclick());
		btnOther.setOnClickListener(new PaymentMethodOnclick());
		
	}

	public String getOi() {
		return oi;
	}

	public void setOi(String oi) {
		this.oi = oi;
	}

	public String getUnPrice() {
		return unPrice;
	}

	public void setUnPrice(String unPrice) {
		this.unPrice = unPrice;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
	}
	
	private void disYuePayDialog()
	{
		if(dialogYuePay == null)
		{
			dialogYuePay = new DialogYuePay(getContext(), DialogBase.Win_bottom_fill, oi, unPrice);
		}
		dialogYuePay.showDialogAnimal(DialogBase.AnimalBottom);
	}
	
	WebView s;
	
	class PaymentMethodOnclick implements View.OnClickListener
	{

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if(id == R.id.btn_yue)
			{
				disYuePayDialog();
			}else if(id == R.id.btn_card)
			{
				IntentClassChangeUtils.startPayWebActivity(getContext(), BuyNetUrl.getOrderPay(oi));
			}else if(id == R.id.btn_credit)
			{
				
			}else if(id == R.id.btn_other)
			{
				
			}
		}
		
	}

}
