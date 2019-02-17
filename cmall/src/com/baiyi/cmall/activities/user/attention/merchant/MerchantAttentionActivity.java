package com.baiyi.cmall.activities.user.attention.merchant;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.user.attention.base.BaseAttentionActivity;
import com.baiyi.cmall.activities.user.attention.buyer.adapter.BuyerAttentionAdapter;
import com.baiyi.cmall.activities.user.attention.buyer.fragment.MerAttentionFragment;
import com.baiyi.cmall.activities.user.attention.buyer.fragment.ProAttentionFragment;
import com.baiyi.cmall.activities.user.attention.merchant.adapter.MerchantAttentionAdapter;
import com.baiyi.cmall.activities.user.attention.merchant.fragment.BuyerAttentionFragment;
import com.baiyi.cmall.activities.user.attention.merchant.fragment.LogAttentionFragment;
import com.baiyi.cmall.activities.user.attention.merchant.fragment.PurAttentionFragment;
import com.baiyi.cmall.entity.LoginInfo;
import com.baiyi.cmall.slidetab.weight.PagerSlidingTabStrip;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.core.util.ContextUtil;

/**
 * �̼ҹ�ע
 * 
 * @author sunxy
 */
public class MerchantAttentionActivity extends BaseActivity {
	// ����
	public static String[] titles = { "��ע�ɹ�", "��ע����", "��ע���" };

	// ��ע �ɹ�
	private PurAttentionFragment purAttentionFragment = null;
	// ��ע����
	private LogAttentionFragment logAttentionFragment = null;
	// ��ע���
	private BuyerAttentionFragment buyerAttentionFragment = null;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(false);

		initTitle();
		initView();

	}

	/**
	 * ������
	 */
	private void initTitle() {
		EventTopTitleView topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("�̼ҹ�ע");
		win_title.addView(topTitleView);
	}

	private View view = null;
	private PagerSlidingTabStrip tabStrip = null;
	private ViewPager mViewPager = null;

	private MerchantAttentionAdapter adapter = null;

	/**
	 * ��ʼ������
	 */
	@SuppressLint("ResourceAsColor")
	private void initView() {
		view = ContextUtil.getLayoutInflater(this).inflate(R.layout.buyer_attention, null);
		win_title.addView(view);

		tabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
		mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
		adapter = new MerchantAttentionAdapter(getSupportFragmentManager());

		adapter.setTitles(titles);
		adapter.setDatas(getFragments());
		mViewPager.setAdapter(adapter);
		tabStrip.setViewPager(mViewPager);

		tabStrip.init();

	}

	private ArrayList<Fragment> getFragments() {
		ArrayList<Fragment> datas = new ArrayList<Fragment>();

		purAttentionFragment = new PurAttentionFragment();
		logAttentionFragment = new LogAttentionFragment();
		buyerAttentionFragment = new BuyerAttentionFragment();

		datas.add(purAttentionFragment);
		datas.add(logAttentionFragment);
		datas.add(buyerAttentionFragment);

		return datas;
	}
	
}
