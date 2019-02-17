package com.baiyi.cmall.activities.user.attention.buyer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.user.attention.base.BaseAttentionActivity;
import com.baiyi.cmall.activities.user.attention.buyer.fragment.MerAttentionFragment;
import com.baiyi.cmall.activities.user.attention.buyer.fragment.ProAttentionFragment;
import com.baiyi.cmall.database.Guanzhu;
import com.baiyi.cmall.activities.user.attention.buyer.adapter.BuyerAttentionAdapter;
import com.baiyi.cmall.activities.user.attention.buyer.fragment.ProductAttentionFragment;
import com.baiyi.cmall.slidetab.weight.PagerSlidingTabStrip;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.core.util.ContextUtil;

/**
 * 买家关注
 * 
 * @author sunxy
 */
public class BuyerAttentionActivity extends BaseActivity {
	// 标题
	public String[] titles = { "关注供应", "关注产品", "关注商家" };

	// 关注的物流
	private ProductAttentionFragment logAttentionFragment = null;
	// 关注的商家
	private MerAttentionFragment merAttentionFragment;
	// 关注的供应
	private ProAttentionFragment proAttentionFragment;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(false);

		initTitle();
		initView();

	}

	/**
	 * 标题栏
	 */
	private void initTitle() {
		EventTopTitleView topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("买家关注");
		win_title.addView(topTitleView);
	}

	private View view = null;
	private PagerSlidingTabStrip tabStrip = null;
	private ViewPager mViewPager = null;
	private BuyerAttentionAdapter adapter = null;

	/**
	 * 初始化内容
	 */
	@SuppressLint("ResourceAsColor")
	private void initView() {
		view = ContextUtil.getLayoutInflater(this).inflate(R.layout.buyer_attention, null);
		win_title.addView(view);

		tabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
		mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
		adapter = new BuyerAttentionAdapter(getSupportFragmentManager());

		adapter.setTitles(titles);
		adapter.setDatas(getFragments());
		mViewPager.setAdapter(adapter);
		tabStrip.setViewPager(mViewPager);

		tabStrip.init();

	}

	private ArrayList<Fragment> getFragments() {
		ArrayList<Fragment> datas = new ArrayList<Fragment>();

		proAttentionFragment = new ProAttentionFragment();
		logAttentionFragment = new ProductAttentionFragment();
		merAttentionFragment = new MerAttentionFragment();

		datas.add(proAttentionFragment);
		datas.add(logAttentionFragment);
		datas.add(merAttentionFragment);

		return datas;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		proAttentionFragment.onActivityResult(requestCode, resultCode, data);
		logAttentionFragment.onActivityResult(requestCode, resultCode, data);
		merAttentionFragment.onActivityResult(requestCode, resultCode, data);
	}
}
