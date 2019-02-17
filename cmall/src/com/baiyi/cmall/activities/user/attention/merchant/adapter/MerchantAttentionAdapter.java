package com.baiyi.cmall.activities.user.attention.merchant.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 商家关注的适配器
 * 
 * @author sunxy
 */
public class MerchantAttentionAdapter extends FragmentPagerAdapter {

	private String[] ttitles = null;
	private ArrayList<Fragment> datas = null;

	public MerchantAttentionAdapter(FragmentManager fm) {
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
