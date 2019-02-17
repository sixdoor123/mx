package com.baiyi.cmall.utils;

import java.util.ArrayList;

import com.baiyi.cmall.adapter.ShareAdapter;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.views.pulldownview.PullToRefreshListView;

import android.app.Activity;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;

/**
 * 得到手机相关参数的工具类
 * 
 * @author sunxy
 * 
 */
public class MobileStateUtils {
	/**
	 * 获取手机状态栏的高度
	 * 
	 * @param activity
	 * @return > 0 success; <= 0 fail
	 */
	public static int getStatusHeight(Activity activity) {
		int statusHeight = 0;
		Rect localRect = new Rect();
		activity.getWindow().getDecorView()
				.getWindowVisibleDisplayFrame(localRect);
		statusHeight = localRect.top;
		if (0 == statusHeight) {
			Class<?> localClass;
			try {
				localClass = Class.forName("com.android.internal.R$dimen");
				Object localObject = localClass.newInstance();
				int i5 = Integer.parseInt(localClass
						.getField("status_bar_height").get(localObject)
						.toString());
				statusHeight = activity.getResources()
						.getDimensionPixelSize(i5);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		return statusHeight;
	}

	/**
	 * 动态获取所有ListView条目的总高度
	 * 
	 * @param orderAdapter2
	 * @param listView
	 */
	public static int getTotalHeightofListView(BaseAdapter orderAdapter2,
			PullToRefreshListView listView) {
		if (orderAdapter2 == null) {
			return 0;
		}
		int totalHeight = 0;
		for (int i = 0; i < orderAdapter2.getCount(); i++) {
			View mView = orderAdapter2.getView(i, null, listView);
			mView.measure(
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			// mView.measure(0, 0);
			totalHeight += mView.getMeasuredHeight() + 1;
			Log.w("HEIGHT" + i, String.valueOf(totalHeight));
		}

		return totalHeight;
	}

	public static void setHight(BaseAdapter adapter,
			PullToRefreshListView listView, ArrayList<?> datas) {

		LayoutParams params = listView.getLayoutParams();
		params.height = MobileStateUtils.getTotalHeightofListView(adapter,
				listView) + (adapter.getCount() * (datas.size() - 1));
		listView.setLayoutParams(params);
	}

	/**
	 * 计算ListView的高度
	 * 
	 * @param orderAdapter2
	 * @param listView
	 * @return
	 */
	public static int getTotalHeightofListView(BaseAdapter orderAdapter2,
			ListView listView) {
		if (orderAdapter2 == null) {
			return 0;
		}
		int totalHeight = 0;
		for (int i = 0; i < orderAdapter2.getCount(); i++) {
			View mView = orderAdapter2.getView(i, null, listView);
			mView.measure(
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			// mView.measure(0, 0);
			totalHeight += mView.getMeasuredHeight();
			Log.w("HEIGHT" + i, String.valueOf(totalHeight));
		}
		// totalHeight += (listView.getDividerHeight() *
		// (orderAdapter2.getCount() - 1));
		return totalHeight;
	}

	/**
	 * 判断最后一个child是否完全显示出来
	 * 
	 * @return true完全显示出来，否则false
	 */
	public static boolean isLastItemVisible(PullToRefreshListView mListView) {
		final Adapter adapter = mListView.getRefreshableView().getAdapter();

		if (null == adapter || adapter.isEmpty()) {
			return true;
		}

		final int lastItemPosition = adapter.getCount() - 1;
		final int lastVisiblePosition = mListView.getRefreshableView()
				.getLastVisiblePosition();

		/**
		 * This check should really just be: lastVisiblePosition ==
		 * lastItemPosition, but ListView internally uses a FooterView which
		 * messes the positions up. For me we'll just subtract one to account
		 * for it and rely on the inner condition which checks getBottom().
		 */
		if (lastVisiblePosition >= lastItemPosition - 1) {
			final int childIndex = lastVisiblePosition
					- mListView.getRefreshableView().getFirstVisiblePosition();
			final int childCount = mListView.getChildCount();
			final int index = Math.min(childIndex, childCount - 1);
			final View lastVisibleChild = mListView.getChildAt(index);
			if (lastVisibleChild != null) {
				return lastVisibleChild.getBottom() <= mListView.getBottom();
			}
		}

		return false;
	}

	public static int getTotalHeightofGridView(BaseAdapter adapter,
			GridView mGdShare) {
		if (adapter == null) {
			return 0;
		}
		int totalHeight = 0;
		for (int i = 0; i < adapter.getCount() / 4; i++) {
			View mView = adapter.getView(i, null, mGdShare);
			mView.measure(
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			// mView.measure(0, 0);
			totalHeight += mView.getMeasuredHeight() + 50;
			Log.w("TAG" + i, String.valueOf(totalHeight)
					+ "-------adapter.getCount()--" + (adapter.getCount() / 4));
		}
		// totalHeight += (listView.getDividerHeight() *
		// (orderAdapter2.getCount() - 1));
		return totalHeight;
	}
}
