package com.baiyi.cmall.activities.user.merchant.order.product;

import java.util.ArrayList;
import java.util.List;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.business.page.BaseViewPagerFragment;
import com.baiyi.cmall.activities.user.merchant.order.product.adapter.ProductOrderListFragmentAdapter;
import com.baiyi.cmall.slidetab.weight.PagerSlidingTabStrip;
import com.baiyi.core.util.ContextUtil;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 * @author sunxy
 */
public class ProductOrderListFragment extends Fragment {

	private String[] titles = { "全部", "已确认", "未确认", "已完成" };
private Context context = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.context = getActivity();
		View view = ContextUtil//
				.getLayoutInflater(context)//
				.inflate(R.layout.buyer_attention, container, false);
		initView(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	private PagerSlidingTabStrip tabStrip = null;
	private ViewPager mViewPager = null;
	private ProductOrderListFragmentAdapter adapter = null;

	/**
	 * 初始化View
	 */
	private void initView(View view) {
		tabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
		mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
		adapter = new ProductOrderListFragmentAdapter(getActivity().getSupportFragmentManager());

		adapter.setTitles(titles);
		adapter.setDatas(getFragments());
		mViewPager.setAdapter(adapter);
		tabStrip.setViewPager(mViewPager);

		tabStrip.init();
	}

	private ProductOrderCategoryFragment allFragment;// 全部
	private ProductOrderCategoryFragment confirmedFragment;// 已确认
	private ProductOrderCategoryFragment unconfirmedFragment;// 未确认
	private ProductOrderCategoryFragment completedFragment;// 全部

	private List<Fragment> getFragments() {
		List<Fragment> fragments = new ArrayList<Fragment>();

		allFragment = new ProductOrderCategoryFragment("1");
		confirmedFragment = new ProductOrderCategoryFragment("2");
		unconfirmedFragment = new ProductOrderCategoryFragment("3");
		completedFragment = new ProductOrderCategoryFragment("4");

		fragments.add(allFragment);
		fragments.add(confirmedFragment);
		fragments.add(unconfirmedFragment);
		fragments.add(completedFragment);

		return fragments;
	}

}
