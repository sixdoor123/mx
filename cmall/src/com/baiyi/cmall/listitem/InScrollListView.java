package com.baiyi.cmall.listitem;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * ScrollView + ListView ʵ�ַ���
 * 
 * @author lizl
 * 
 */
public class InScrollListView extends ListView {

	public InScrollListView(Context context) {
		super(context);
	}

	public InScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
