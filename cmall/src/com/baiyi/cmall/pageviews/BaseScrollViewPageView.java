package com.baiyi.cmall.pageviews;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * 
 * 可以处置滚动的ViewPager
 * 
 * @author sunxy
 * 
 */
public abstract class BaseScrollViewPageView extends ScrollView implements PageView {
	private boolean isCreate = false;
	private boolean isVisible = false;

	public BaseScrollViewPageView(Context context) {
		super(context);
		setHorizontalScrollBarEnabled(false);
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
