package com.baiyi.jj.skin.entity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.baiyi.jj.skin.loader.SkinManager;

public class BackgroundAttr extends SkinAttr {

	@SuppressLint("NewApi")
	@Override
	public void apply(View view) {
		
		if(RES_TYPE_NAME_COLOR.equals(attrValueTypeName)){
			view.setBackgroundColor(SkinManager.getInstance().getColor(attrValueRefId));
		}else if(RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)){
			Drawable bg = SkinManager.getInstance().getDrawable(attrValueRefId);
			view.setBackground(bg);
		}
	}
}
