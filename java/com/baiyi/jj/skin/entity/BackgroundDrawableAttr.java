/**
 * 
 */
package com.baiyi.jj.skin.entity;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.baiyi.jj.skin.loader.SkinManager;

/**
 * @author tangkun
 *
 */
public class BackgroundDrawableAttr extends SkinAttr{

	@SuppressWarnings("deprecation")
	@Override
	public void apply(View view) {
		
		if(RES_TYPE_NAME_COLOR.equals(attrValueTypeName)){
			view.setBackgroundColor(SkinManager.getInstance().getColor(attrValueRefId));
		}else if(RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)){
			Drawable bg = SkinManager.getInstance().getDrawable(attrValueRefId);
			if(view instanceof ImageView)
			{
				((ImageView) view).setImageDrawable(bg);
			}else
			{
				view.setBackgroundDrawable(bg);
			}
		}
	}

}
