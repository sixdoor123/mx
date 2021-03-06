package com.baiyi.jj.app.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.baiyi.jj.skin.entity.DynamicAttr;
import com.baiyi.jj.skin.listener.IDynamicNewView;
import com.baiyi.jj.skin.listener.ISkinUpdate;
import com.baiyi.jj.skin.loader.SkinInflaterFactory;
import com.baiyi.jj.skin.loader.SkinManager;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Base Fragment Activity for development
 * 
 * <p>NOTICE:<br> 
 * You should extends from this if you want to do skin change
 * 
 * @author fengjun
 */
public class BaseSkinFragmentActivity extends FragmentActivity implements ISkinUpdate, IDynamicNewView{
	
	/**
	 * Whether response to skin changing after create
	 */
	private boolean isResponseOnSkinChanging = true;
	
	private SkinInflaterFactory mSkinInflaterFactory;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
        try { 
            Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
            field.setAccessible(true);
            field.setBoolean(getLayoutInflater(), false);
            
    		mSkinInflaterFactory = new SkinInflaterFactory();
    		getLayoutInflater().setFactory(mSkinInflaterFactory);
    		
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } 
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		SkinManager.getInstance().attach(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		SkinManager.getInstance().detach(this);
	}
	
	protected void dynamicAddSkinEnableView(View view, String attrName, int attrValueResId){	
		mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, attrName, attrValueResId);
	}
	
	protected void dynamicAddSkinEnableView(View view, List<DynamicAttr> pDAttrs){	
		mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, pDAttrs);
	}
	
	final protected void enableResponseOnSkinChanging(boolean enable){
		isResponseOnSkinChanging = enable;
	}

	@Override
	public void onThemeUpdate() {
		if(!isResponseOnSkinChanging) return;
		mSkinInflaterFactory.applySkin();
	}
	
	@Override
	public void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
		mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, pDAttrs);
	}
}
