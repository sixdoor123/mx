package com.baiyi.cmall.listitem;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * ScrollView + GridView 实现方案
 * 
 * @author lizl
 * 
 */

public class InScrollGridView extends GridView {

	public InScrollGridView(Context context) {
		super(context);
	}

	public InScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public InScrollGridView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
