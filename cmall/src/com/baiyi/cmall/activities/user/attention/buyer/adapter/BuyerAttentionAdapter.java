package com.baiyi.cmall.activities.user.attention.buyer.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Âò¼Ò¹Ø×¢µÄÊÊÅäÆ÷
 * 
 * @author sunxy
 */
public class BuyerAttentionAdapter extends FragmentPagerAdapter {

	private String[] ttitles = null;
	private ArrayList<Fragment> datas = null;

	public BuyerAttentionAdapter(FragmentManager fm) {
		super(fm);
		this.datas = new ArrayList<Fragment>();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return ttitles[position];
	}

	@Override
	public int getCount() {
		return ttitles.length;
	}

	@Override
	public Fragment getItem(int position) {
		return datas.get(position);
	}

	public void setTitles(String[] titles) {
		this.ttitles = titles;
	}

	public void setDatas(ArrayList<Fragment> datas) {
		this.datas = datas;
		notifyDataSetChanged();
	}
}
