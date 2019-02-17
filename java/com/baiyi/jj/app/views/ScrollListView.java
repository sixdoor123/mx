package com.baiyi.jj.app.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.ListView;

public class ScrollListView extends ListView{
	
	public ScrollListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	public ScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public ScrollListView(Context context) {
		super(context);
	}

	
	
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { 
	     // TODO Auto-generated method stub 
	     int expandSpec = MeasureSpec.makeMeasureSpec(  
	                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);  
	      
	     super.onMeasure(widthMeasureSpec, expandSpec); 
	 }

}
