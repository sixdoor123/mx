package com.baiyi.cmall.activities.user.merchant.order.product.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 *	…Ãº“∂©µ•viewPager  ≈‰∆˜
 * @author sunxy
 */
public class ProductOrderListFragmentAdapter  extends FragmentPagerAdapter {

	private String[] titles = null;
	private List<Fragment> datas = null;

	public ProductOrderListFragmentAdapter(FragmentManager fm) {
		super(fm);
		this.datas = new ArrayList<Fragment>();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return titles[position];
	}

	@Override
	public int getCount() {
		return titles.length;
	}

	@Override
	public Fragment getItem(int position) {
		return datas.get(position);
	}

	public void setTitles(String[] titles) {
		this.titles = titles;
	}

	public void setDatas(List<Fragment> datas) {
		this.datas = datas;
		notifyDataSetChanged();
	}
}
