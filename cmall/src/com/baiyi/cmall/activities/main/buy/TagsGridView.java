package com.baiyi.cmall.activities.main.buy;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * ²»ÄÜ»¬¶¯
 * 
 * @author sunxy
 */
public class TagsGridView extends GridView {

	public TagsGridView(Context context) {
		super(context);
	}

	public TagsGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
