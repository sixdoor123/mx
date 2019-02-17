/**
 * 
 */
package com.baiyi.jj.app.views;

import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.theme.ThemeUtil;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * @author tangkun
 *
 */
public class BaseLayout extends RelativeLayout{
	
	private Drawable dayColor;
	private Drawable nightColor;

	/**
	 * @param context
	 * @param attrs
	 */
	public BaseLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.baseLayout);
        dayColor = ta.getDrawable(R.styleable.baseLayout_day_layout_color);
        nightColor = ta.getDrawable(R.styleable.baseLayout_night_layout_color);
        ta.recycle();
	}
	
	public void setThemeBg(int themeType)
	{
		if(themeType == ThemeUtil.Theme_day)
		{
			if(dayColor != null)
			{
				this.setBackgroundDrawable(dayColor);
			}
		}else if(themeType == ThemeUtil.Theme_night)
		{
			if(nightColor != null)
			{
				this.setBackgroundDrawable(nightColor);
			}
		}
	}
	
}
