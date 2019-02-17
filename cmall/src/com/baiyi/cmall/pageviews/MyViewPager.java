/**
 * 
 */
package com.baiyi.cmall.pageviews;

import java.util.List;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author tangkun
 * 
 */
public class MyViewPager extends ViewPager {

	private List<PageView> pageViewList = null;

	private ViewPagerSelected viewPageSelectedLister = null;

	private int currNum = 0;
	private int oldNum = 0;

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

	/**
	 * 
	 * @param pageViews
	 * @param pageIndex
	 *            1开始
	 */
	@SuppressWarnings("deprecation")
	public void init(List<PageView> pageViews, int pageIndex) {
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
				if (viewPageSelectedLister != null) {
					viewPageSelectedLister.onPageSelected(currentNum);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				/** arg0==0时change 结束 **/
				if (arg0 == 0) {
					if (oldNum != currNum) {
						PageView oldView = pageViewList.get(oldNum);
						if (null != oldView) {
							if (oldView.isCreated()) {
								oldView.disVisible();
							}
						}
						PageView newView = pageViewList.get(currNum);
						if (null != newView) {
							if (!newView.isCreated()) {
								newView.onCreate(null);
							}
							newView.visible();
						}
					}
				}

			}
		});
	}

	public void setPageIndex(int currentPageNum) {
		oldNum = currNum;
		currNum = currentPageNum;
	}

	public void setViewPageSelectedLister(
			ViewPagerSelected viewPageSelectedLister) {
		this.viewPageSelectedLister = viewPageSelectedLister;
	}

	private boolean scrollble = true;

	public void setScanScroll(boolean scrollble) {
		this.scrollble = scrollble;
	}

	/**
	 * 设置viewPager是否能滑动
	 * true:可以滑动
	 * false：不可以滑动
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (!scrollble) {
			return true;
		}
		return super.onTouchEvent(ev);
	}

}
