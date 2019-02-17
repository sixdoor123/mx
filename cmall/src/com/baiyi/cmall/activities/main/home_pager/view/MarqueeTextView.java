package com.baiyi.cmall.activities.main.home_pager.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * ÅÜÂíµÆÏÔÊ¾ÎÄ×Ö
 * @author lizl
 *
 */
public class MarqueeTextView extends TextView {

	public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public MarqueeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MarqueeTextView(Context context) {
		super(context);
	}

	@Override
	public boolean isFocused() {
		return true;
	}
}
