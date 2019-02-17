package com.baiyi.cmall.activities.user.buyer.form;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.baiyi.cmall.pageviews.BasePageView;

public class OfflinePayView extends BasePageView {

	private Context context;
	private String orderID;

	public OfflinePayView(Context context, String orderID) {
		super(context);
		this.context = context;
		this.orderID = orderID;
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

}
