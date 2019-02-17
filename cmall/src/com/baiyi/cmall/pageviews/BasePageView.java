package com.baiyi.cmall.pageviews;

import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.LoginInfo;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * @author tangkun
 * 
 */
public abstract class BasePageView extends LinearLayout implements PageView {
	private boolean isCreate = false;
	private boolean isVisible = false;

	public String token;
	public String iscompany;
	
	public BasePageView(Context context) {
		super(context);
		this.setOrientation(LinearLayout.VERTICAL);
		
		LoginInfo info = CmallApplication.getUserInfo();
		if (null != info) {
			token = info.getToken();
			iscompany = info.isIscompany()+"";
		}
	}

	public void notifyVisitorChanged() {
	}

	protected void setCreate(boolean isCreate) {
		this.isCreate = isCreate;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		isCreate = true;
	}

	@Override
	public void visible() {
		isVisible = true;
	}

	@Override
	public void disVisible() {
		isVisible = false;
	}

	protected void setVisible(boolean visible) {
		this.isVisible = visible;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return isVisible;
	}

	@Override
	public boolean isCreated() {
		return isCreate;
	}
}
