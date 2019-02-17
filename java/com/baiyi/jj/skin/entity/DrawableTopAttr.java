package com.baiyi.jj.skin.entity;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.RadioButton;

import com.baiyi.jj.skin.loader.SkinManager;

public class DrawableTopAttr extends SkinAttr {

	@Override
	public void apply(View view) {
		if(view instanceof RadioButton)
		{
			if(RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)){
				Drawable bg = SkinManager.getInstance().getDrawable(attrValueRefId);
				((RadioButton) view).setCompoundDrawablesWithIntrinsicBounds(null, bg, null, null);
			}
		}
	}
}
