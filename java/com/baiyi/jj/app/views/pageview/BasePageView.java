package com.baiyi.jj.app.views.pageview;

import java.util.List;

import com.baiyi.jj.skin.entity.DynamicAttr;
import com.baiyi.jj.skin.listener.IDynamicNewView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author tangkun
 *
 */
public class BasePageView extends LinearLayout implements PageView, IDynamicNewView{
	private boolean isCreate = false;
	private boolean isVisible = false;
	private IDynamicNewView mIDynamicNewView;
	public BasePageView(Context context) {
		super(context);
		this.setOrientation(LinearLayout.VERTICAL);
	}
	
	public void notifyVisitorChanged() {
	}
	
	protected void setCreate(boolean isCreate){
		this.isCreate = isCreate;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		isCreate = true;
		try{
			mIDynamicNewView = (IDynamicNewView)getContext();
		}catch(ClassCastException e){
			mIDynamicNewView = null;
		}
		
	}
	@Override
	public void visible() {
		isVisible = true;
		
	}
	@Override
	public void disVisible() {
		isVisible = false;
		
	}
	protected void setVisible(boolean visible){
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

	@Override
	public void dynamicAddView(View arg0, List<DynamicAttr> arg1) {
		if(mIDynamicNewView == null){
			throw new RuntimeException("IDynamicNewView should be implements !");
		}else{
			mIDynamicNewView.dynamicAddView(arg0, arg1);
		}
	}

	/* (non-Javadoc)
	 * @see com.baiyi.jj.app.views.pageview.PageView#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.baiyi.jj.app.views.pageview.PageView#onDestroy()
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.baiyi.jj.app.views.pageview.PageView#onRefresh(boolean)
	 */
	@Override
	public void onRefresh(boolean isFontChange) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.baiyi.jj.app.views.pageview.PageView#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.baiyi.jj.app.views.pageview.PageView#onRefreshToNetWorkChange(boolean)
	 */
	@Override
	public void onRefreshToNetWorkChange(boolean isGprs) {
		// TODO Auto-generated method stub
		
	}
}
