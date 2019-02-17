/**
 * 
 */
package com.baiyi.jj.app.views.viewpager;

import java.util.List;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import com.baiyi.jj.app.adapter.PageViewAdapter;
import com.baiyi.jj.app.views.pageview.PageView;

/**
 * @author tangkun
 *
 */
public class MyViewPager extends ViewPager{

	private List<PageView> pageViewList = null;
	private ViewPagerSelected viewPageSelectedLister = null;
	private int currNum = 0;
	private int oldNum=0;

	/**
	 * @param context
	 */
	public MyViewPager(Context context) {
		super(context);
	}
	
	/**
	 * @param context
	 * @param attrs
	 */
	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@SuppressWarnings("deprecation")
	public void init(List<PageView> pageViews, int pageIndex)
	{
		this.pageViewList = pageViews;
		this.setAdapter(new PageViewAdapter(pageViewList));
		this.setCurrentItem(pageIndex);
		 PageView view = pageViewList.get(pageIndex);
		 view.onCreate(null);
		 view.visible();
		 oldNum = pageIndex;
		 currNum = pageIndex;
		 
		 this.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int currentNum) {
				// TODO Auto-generated method stub
				if(viewPageSelectedLister != null)
				{
					viewPageSelectedLister.onPageSelected(currentNum);
				}
				if (oldNum != currNum) {
					PageView oldView = pageViewList.get(oldNum);
					if (oldView.isCreated()) {
						oldView.disVisible();
					}
					PageView newView = pageViewList.get(currNum);
					if (!newView.isCreated()) {
						newView.onCreate(null);
					}
					newView.visible();
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				/** arg0==0ʱchange ���� **/
//				if (arg0 == 0) {
//					if (oldNum != currNum) {
//						PageView oldView = pageViewList.get(oldNum);
//						if (oldView.isCreated()) {
//							oldView.disVisible();
//						}
//						PageView newView = pageViewList.get(currNum);
//						if (!newView.isCreated()) {
//							newView.onCreate(null);
//						}
//						newView.visible();
//					}
//				}
				
			}
		});
	}
	
	public void setPageIndex(int currentPageNum)
	{
		oldNum = currNum;
		currNum = currentPageNum;
	}

	public void setViewPageSelectedLister(ViewPagerSelected viewPageSelectedLister) {
		this.viewPageSelectedLister = viewPageSelectedLister;
	}

}
