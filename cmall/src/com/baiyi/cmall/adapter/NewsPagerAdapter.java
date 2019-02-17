package com.baiyi.cmall.adapter;

import java.util.ArrayList;

import com.baiyi.cmall.utils.Utils;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * ��ҳ����������
 * 
 * @author lizl
 * 
 */
public class NewsPagerAdapter extends PagerAdapter {

	// ��ҳ��ʾ��view����
	private ArrayList<View> itemViews;

	public NewsPagerAdapter() {
		itemViews = new ArrayList<View>();
	}

	@Override
	public int getCount() {
		return itemViews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		View layout = itemViews.get(position);
		container.addView(layout);
		return layout;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {

		container.removeView(itemViews.get(position));
	}

	/**
	 * Ϊ�� ˢ�����ݣ�����ˢ�����ݺ󷵻�--POSITION_NONE
	 */
	@Override
	public int getItemPosition(Object object) {

		Log.d("TT", "getItemPosition");
		return POSITION_NONE;

	}

	/**
	 * ��������
	 * 
	 * @param itemViews
	 */
	public void setData(ArrayList<View> itemViews) {

		if (Utils.isStringEmpty(itemViews)) {
			itemViews = new ArrayList<View>();
			return;
		}
		this.itemViews.clear();
		this.itemViews.addAll(itemViews);
		notifyDataSetChanged();
	}
}
