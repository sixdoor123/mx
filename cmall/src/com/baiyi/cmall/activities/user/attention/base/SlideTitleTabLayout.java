package com.baiyi.cmall.activities.user.attention.base;

import java.util.ArrayList;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.user.attention.buyer.BuyerAttentionActivity;
import com.baiyi.cmall.activities.user.attention.buyer.fragment.ProductAttentionFragment;
import com.baiyi.cmall.activities.user.attention.merchant.fragment.BuyerAttentionFragment;
import com.baiyi.cmall.activities.user.attention.merchant.fragment.PurAttentionFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import me.relex.seamlessviewpagerheader.widget.SlidingTabLayout;

/**
 * 水平滚动标题栏
 * 
 * @author Administrator
 *
 */
public class SlideTitleTabLayout extends LinearLayout {

	private String[] titles = null;
	private ArrayList<Fragment> fragments = null;
	private Context context = null;

	public SlideTitleTabLayout(Context context) {
		this(context, null);
	}

	public SlideTitleTabLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		this.fragments = new ArrayList<Fragment>();
	}

	/**
	 * 最后调用此方法执行
	 */
	public void init() {
		ViewPager pager = new ViewPager(context);
		pager.setAdapter(new ViewPagerAdapter(((BaseActivity) context).getSupportFragmentManager()));
		SlidingTabLayout lay = new SlidingTabLayout(context);
		lay.setViewPager(pager);
		addView(lay);
	}

	/**
	 * 设置标题栏内容
	 * 
	 * @param titles
	 */
	public void setTitles(String[] titles) {
		this.titles = titles;
	}

	/**
	 * 设置Fragment
	 * 
	 * @param fragments
	 */
	public void setFragments(ArrayList<Fragment> fragments) {
		this.fragments = fragments;
	}

	private class ViewPagerAdapter extends FragmentPagerAdapter {

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}

		@Override
		public Fragment getItem(int position) {
			// if (position== 0) {
			// return PurAttentionFragment.newInsetence(position);
			// }else if (1==position) {
			// return LogAttentionFragment.newInsetence(position);
			// }else {
			// return BuyerAttentionFragment.newInsetence(position);
			// }
			return fragments.get(position);
		}
	}
}
