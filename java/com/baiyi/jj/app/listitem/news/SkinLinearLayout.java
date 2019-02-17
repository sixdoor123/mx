/**
 * 
 */
package com.baiyi.jj.app.listitem.news;

import java.util.List;

import com.baiyi.jj.skin.entity.DynamicAttr;
import com.baiyi.jj.skin.listener.IDynamicNewView;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author tangkun
 *
 */
public class SkinLinearLayout extends LinearLayout implements IDynamicNewView{
	private IDynamicNewView mIDynamicNewView;

	/**
	 * @param context
	 */
	public SkinLinearLayout(Context context) {
		super(context);
		try{
			mIDynamicNewView = (IDynamicNewView)context;
		}catch(ClassCastException e){
			mIDynamicNewView = null;
		}
	}

	@Override
	public void dynamicAddView(View arg0, List<DynamicAttr> arg1) {
		if(mIDynamicNewView == null){
			throw new RuntimeException("IDynamicNewView should be implements !");
		}else{
			mIDynamicNewView.dynamicAddView(arg0, arg1);
		}
	}

}
