/**
 * 
 */
package com.baiyi.jj.app.views;

import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.theme.FontUtil;
import com.baiyi.jj.app.theme.ThemeUtil;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author tangkun
 *
 */
public class MyTextView extends TextView{
	
	private static final String tag = MyTextView.class.getName();
	
	private Drawable dayBg;
	private Drawable nightBg;
	
	private ColorStateList dayTextColor;
	private ColorStateList nightTextColor;
	
	private int minTextSize;
	private int minSpaceExtra;

	private int middleTextSize;
	private int middleSpaceExtra;
	
	private int maxTextSize;
	private int maxSpaceExtra;
	
	private int bigMaxTextSize;
	private int bigMaxSpaceExtra;
	
	private int minMarginTop;
	private int minMarginBottom;
	private int minMarginLeft;
	private int minMarginRight;
	
	private int middleMarginTop;
	private int middleMarginBottom;
	private int middleMarginLeft;
	private int middleMarginRgiht;
	
	private int maxMarginTop;
	private int maxMarginBottom;
	private int maxMarginLeft;
	private int maxMarginRgiht;
	
	private int bigMaxMarginBottom;
	
	private int themetype = -1;
	private int fontType = -1;

	/**
	 * @param context
	 * @param attrs
	 */
	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyTextView);
        
        dayBg = ta.getDrawable(R.styleable.MyTextView_day_bg_res);
        nightBg = ta.getDrawable(R.styleable.MyTextView_night_bg_res);
        
        dayTextColor = ta.getColorStateList(R.styleable.MyTextView_day_text_color);
        nightTextColor = ta.getColorStateList(R.styleable.MyTextView_night_text_color);
        
        minTextSize = ta.getDimensionPixelSize(R.styleable.MyTextView_min_text_size, 0);
        minSpaceExtra = ta.getDimensionPixelSize(R.styleable.MyTextView_min_spaceextra, 0);
        
        middleTextSize = ta.getDimensionPixelSize(R.styleable.MyTextView_middle_text_size, 0);
        middleSpaceExtra = ta.getDimensionPixelSize(R.styleable.MyTextView_middle_spaceextra, 0);
        
        maxTextSize = ta.getDimensionPixelSize(R.styleable.MyTextView_max_text_size, 0);
        maxSpaceExtra = ta.getDimensionPixelSize(R.styleable.MyTextView_max_spaceextra, 0);
        
        bigMaxTextSize = ta.getDimensionPixelSize(R.styleable.MyTextView_big_max_text_size, 0);
        bigMaxSpaceExtra = ta.getDimensionPixelSize(R.styleable.MyTextView_big_max_spaceextra, 0);
        
        initMargin(ta);
        
        ta.recycle();
	}
	
	private void initMargin(TypedArray ta)
	{
		minMarginTop = ta.getDimensionPixelSize(R.styleable.MyTextView_min_margin_top, 0);
		minMarginBottom = ta.getDimensionPixelSize(R.styleable.MyTextView_min_margin_bottom, 0);
		minMarginLeft = ta.getDimensionPixelSize(R.styleable.MyTextView_min_margin_left, 0);
		minMarginRight = ta.getDimensionPixelSize(R.styleable.MyTextView_min_margin_right, 0);

		middleMarginTop = ta.getDimensionPixelSize(R.styleable.MyTextView_middle_margin_top, 0);
		middleMarginBottom = ta.getDimensionPixelSize(R.styleable.MyTextView_middle_margin_bottom, 0);
		middleMarginLeft = ta.getDimensionPixelSize(R.styleable.MyTextView_middle_margin_left, 0);
		middleMarginRgiht = ta.getDimensionPixelSize(R.styleable.MyTextView_middle_margin_right, 0);

		maxMarginTop = ta.getDimensionPixelSize(R.styleable.MyTextView_max_margin_top, 0);
		maxMarginBottom = ta.getDimensionPixelSize(R.styleable.MyTextView_max_margin_bottom, 0);
		maxMarginLeft = ta.getDimensionPixelSize(R.styleable.MyTextView_max_margin_left, 0);
		maxMarginRgiht = ta.getDimensionPixelSize(R.styleable.MyTextView_max_margin_right, 0);

        bigMaxMarginBottom = ta.getDimensionPixelSize(R.styleable.MyTextView_bigmax_margin_bottom, 0);
	}
	
	public void setTheme(int themeType)
	{
		if(this.themetype == themeType)
		{
			return;
		}
		if(themeType == ThemeUtil.Theme_day)
		{
			if(dayTextColor != null)
			{
				this.setTextColor(dayTextColor);
			}
			if(dayBg != null)
			{
				this.setBackgroundDrawable(dayBg);
			}
		}else if(themeType == ThemeUtil.Theme_night)
		{
			if(nightTextColor != null)
			{
				this.setTextColor(nightTextColor);
			}
			if(nightBg != null)
			{
				this.setBackgroundDrawable(nightBg);
			}
		}
		this.themetype = themeType;
	}
	
	public void setFontTheme(int fontType)
	{
		if(this.fontType == fontType)
		{
			return;
		}
		if(fontType == FontUtil.Font_Min)
		{
            if (minTextSize != 0) {
                setTextSize(TypedValue.COMPLEX_UNIT_PX, minTextSize);
            }
            if (minSpaceExtra != 0) {
                setLineSpacing(minSpaceExtra, 1.0f);
            }
		}else if(fontType == FontUtil.Font_middle)
		{
            if (middleTextSize != 0) {
                setTextSize(TypedValue.COMPLEX_UNIT_PX, middleTextSize);
            }
            if (middleSpaceExtra != 0) {
                setLineSpacing(middleSpaceExtra, 1.0f);
            }
		}else if(fontType == FontUtil.Font_Max)
		{
            if (maxTextSize != 0) {
                setTextSize(TypedValue.COMPLEX_UNIT_PX, maxTextSize);
            }
            if (maxSpaceExtra != 0) {
                setLineSpacing(maxSpaceExtra, 1.0f);
            }
		}else if(fontType == FontUtil.Font_Big_Max)
		{
            if (bigMaxTextSize != 0) {
                setTextSize(TypedValue.COMPLEX_UNIT_PX, bigMaxTextSize);
            }
            if (bigMaxSpaceExtra != 0) {
                setLineSpacing(bigMaxSpaceExtra, 1.0f);
            }
		}
		setMargin(fontType);
		this.fontType = fontType;
	}

    public void setMargin(int fontType)
    {
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) getLayoutParams();
    	if(fontType == FontUtil.Font_Min)
    	{
            if (minMarginTop != 0) {
                mlp.topMargin = minMarginTop;
            }
            if (minMarginBottom != 0) {
                mlp.bottomMargin = minMarginBottom;
            }
            if (minMarginLeft != 0) {
                mlp.leftMargin = minMarginLeft;
            }
            if (minMarginRight != 0) {
                mlp.rightMargin = minMarginRight;
            }
//    		setPadding(minMarginLeft, minMarginTop, minMarginRight, minMarginBottom);
    	}else if(fontType == FontUtil.Font_middle)
    	{
            if (middleMarginTop != 0) {
                mlp.topMargin = middleMarginTop;
            }
            if (middleMarginBottom != 0) {
                mlp.bottomMargin = middleMarginBottom;
            }
            if (middleMarginLeft != 0) {
                mlp.leftMargin = middleMarginLeft;
            }
            if (middleMarginRgiht != 0) {
                mlp.rightMargin = middleMarginRgiht;
            }
//    		setPadding(middleMarginLeft, middleMarginTop, middleMarginRgiht, middleMarginBottom);
    	}else if(fontType == FontUtil.Font_Max)
    	{
            if (maxMarginTop != 0) {
                mlp.topMargin = maxMarginTop;
            }
            if (middleMarginBottom != 0) {
                mlp.bottomMargin = maxMarginBottom;
            }
            if (maxMarginLeft != 0) {
                mlp.leftMargin = maxMarginLeft;
            }
            if (maxMarginRgiht != 0) {
                mlp.rightMargin = maxMarginRgiht;
            }
//    		setPadding(maxMarginLeft, maxMarginTop, maxMarginRgiht, maxMarginBottom);
    	}else if(fontType == FontUtil.Font_Big_Max)
    	{
            if (maxMarginTop != 0) {
                mlp.topMargin = maxMarginTop;
            }
            if (middleMarginBottom != 0) {
                mlp.bottomMargin = bigMaxMarginBottom;
            }
            if (maxMarginLeft != 0) {
                mlp.leftMargin = maxMarginLeft;
            }
            if (maxMarginRgiht != 0) {
                mlp.rightMargin = maxMarginRgiht;
            }
//    		setPadding(maxMarginLeft, maxMarginTop, maxMarginRgiht, maxMarginBottom);
    	}
        setLayoutParams(mlp);
    }

}
