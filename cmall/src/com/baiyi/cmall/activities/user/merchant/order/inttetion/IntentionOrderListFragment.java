package com.baiyi.cmall.activities.user.merchant.order.inttetion;

import java.security.KeyStore.PrivateKeyEntry;
import java.util.ArrayList;
import java.util.List;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.business.page.BaseViewPagerFragment;
import com.baiyi.cmall.activities.user.merchant.order.inttetion.adapter.IntentionOrderListFragmentAdapter;
import com.baiyi.cmall.slidetab.weight.PagerSlidingTabStrip;
import com.baiyi.core.util.ContextUtil;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 * @author sunxy
 */
public class IntentionOrderListFragment extends Fragment {

	private String[] titles = { "ȫ��", "��ȷ��", "δȷ��", "�����" };

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

	FragmentManager m;

	@SuppressLint("CommitTransaction")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		this.context = getActivity();
		m.beginTransaction();
	}

	private PagerSlidingTabStrip tabStrip = null;
	private ViewPager mViewPager = null;
	private IntentionOrderListFragmentAdapter adapter;

	/**
	 * ��ʼ��View
	 */
	private void initView(View view) {
		tabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
		mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
		adapter = new IntentionOrderListFragmentAdapter(getActivity().getSupportFragmentManager());

		adapter.setTitles(titles);
		adapter.setDatas(getFragments());
		mViewPager.setAdapter(adapter);
		tabStrip.setViewPager(mViewPager);

		tabStrip.init();
	}

	private IntentionOrderCategoryFragment allFragment;// ȫ��
	private IntentionOrderCategoryFragment confirmedFragment;// ��ȷ��
	private IntentionOrderCategoryFragment unconfirmedFragment;// δȷ��
	private IntentionOrderCategoryFragment conpletedFragment;// ȫ��

	private List<Fragment> getFragments() {
		List<Fragment> fragments = new ArrayList<Fragment>();

		allFragment = new IntentionOrderCategoryFragment("1");
		confirmedFragment = new IntentionOrderCategoryFragment("2");
		unconfirmedFragment = new IntentionOrderCategoryFragment("3");
		conpletedFragment = new IntentionOrderCategoryFragment("4");

		fragments.add(allFragment);
		fragments.add(confirmedFragment);
		fragments.add(unconfirmedFragment);
		fragments.add(conpletedFragment);

		return fragments;
	}

}
