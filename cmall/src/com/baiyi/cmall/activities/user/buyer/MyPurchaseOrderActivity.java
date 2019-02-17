package com.baiyi.cmall.activities.user.buyer;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.pageviews.MyViewPager;
import com.baiyi.cmall.pageviews.PageView;
import com.baiyi.cmall.pageviews.ViewPagerSelected;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 我是采购商-我的采购（公共类）
 * 
 * @author lizl
 * 
 */
public  class MyPurchaseOrderActivity extends BaseActivity implements
		OnClickListener, OnCheckedChangeListener {

	// 数据源
	public ArrayList<GoodsSourceInfo> datas;

	private MyViewPager myViewPager = null;

	private List<PageView> pageViews = null;

	/*
	 * ViewPager添加的四个界面
	 */
	private PurchaseView purchaseAll = null;
	private PurchaseView purchaseNotAudit = null;
	private PurchaseView purchaseAudit = null;
	private PurchaseView purchaseNotPAss = null;
	// 我的采购单分组
	private RadioGroup mRgMyOrderGroup;
	// 全部的
	private RadioButton mRbAllOrderButton;
	// 未审核
	private RadioButton mRbNotSureOrderButton;
	// 已审核
	private RadioButton mRbSureOrderButton;
	// 完成的
	private RadioButton mRbCompleteOrderButton;

	// 全部下面的图标
	private ImageView mImgAllProviderChosed;
	// 未审核下面的图标
	private ImageView mImgNotAudirProvider;
	// 已审核下面的图标
	private ImageView mImgAuditProviderChosed;
	// 审核未通过下面的图标
	private ImageView mImgNotPassProviderChosed;
	// 控制
	private int pagerIndex = 0;

	@Override
	protected void initWin(boolean hasScrollView) {
		super.initWin(true);
		initTitle();
		initRg();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		initViewPager();
	}

	/**
	 * 初始化RadioButton
	 */
	public void initRg() {

		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_my_purchase_order, null);
		win_title.addView(view);
		// 找下面三角小图标
		mImgAllProviderChosed = (ImageView) view
				.findViewById(R.id.all_provider_choose);
		mImgNotAudirProvider = (ImageView) view
				.findViewById(R.id.not_audit_provider_choose);
		mImgAuditProviderChosed = (ImageView) view
				.findViewById(R.id.audit_provider_choose);
		mImgNotPassProviderChosed = (ImageView) view
				.findViewById(R.id.not_pass_provider_choose);

		myViewPager = (MyViewPager) findViewById(R.id.vp_pager);
		mRgMyOrderGroup = (RadioGroup) view.findViewById(R.id.rg_my_order);
		mRbAllOrderButton = (RadioButton) view
				.findViewById(R.id.rb_all_intent_order);
		mRbAllOrderButton.setChecked(true);
		mRbNotSureOrderButton = (RadioButton) view
				.findViewById(R.id.rb_not_sure_intent_order);
		mRbSureOrderButton = (RadioButton) view
				.findViewById(R.id.rb_sure_intent_order);
		mRbCompleteOrderButton = (RadioButton) view
				.findViewById(R.id.rb_complete_intent_order);
		mRgMyOrderGroup.setOnCheckedChangeListener(this);

	}

	/**
	 * 初始化ViewPager
	 */
	private void initViewPager() {
		pageViews = new ArrayList<PageView>();

		/*
		 * -2:全部 0:创建(未审核) 1:审核通过 -1:审核未通过
		 */
		purchaseAll = new PurchaseView(this, -2);// 全部
		purchaseNotAudit = new PurchaseView(this, 0);// 未审核
		purchaseAudit = new PurchaseView(this, 1);// 已审核
		purchaseNotPAss = new PurchaseView(this, -1);// 未通过

		pageViews.add(purchaseAll);
		pageViews.add(purchaseNotAudit);
		pageViews.add(purchaseAudit);
		pageViews.add(purchaseNotPAss);

		myViewPager.init(pageViews, pagerIndex);

		/**
		 * 当viewPager页面变化的时候RadioButton也一起发生变化
		 * 
		 */
		myViewPager.setViewPageSelectedLister(new ViewPagerSelected() {

			@Override
			public void onPageSelected(int currentNum) {
				// TODO Auto-generated method stub
				setButtonPerformClick(currentNum);
			}
		});
	}

	public void setButtonPerformClick(int position) {
		if (position == 0) {
			mRbAllOrderButton.performClick();

		} else if (position == 1) {
			mRbNotSureOrderButton.performClick();
		} else if (position == 2) {
			mRbSureOrderButton.performClick();
		} else if (position == 3) {
			mRbCompleteOrderButton.performClick();
		}

	}

	/**
	 * 标题栏
	 */
	public void initTitle() {
		EventTopTitleView topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("我的采购");
		win_title.addView(topTitleView);
	}

	/**
	 * 监听事件回调方法
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		default:
			break;
		}
	}

	/**
	 * RadioGroup 的监听事件的回调方法
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		if (checkedId == R.id.rb_all_intent_order) {
			pagerIndex = 0;// 全部
			mImgAllProviderChosed.setVisibility(View.VISIBLE);
			mImgNotAudirProvider.setVisibility(View.INVISIBLE);
			mImgAuditProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.rb_not_sure_intent_order) {
			pagerIndex = 1;// 未确认 (未审核)
			mImgAllProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotAudirProvider.setVisibility(View.VISIBLE);
			mImgAuditProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.rb_sure_intent_order) {
			pagerIndex = 2;// 确认 （已审核）
			mImgAllProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotAudirProvider.setVisibility(View.INVISIBLE);
			mImgAuditProviderChosed.setVisibility(View.VISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.rb_complete_intent_order) {
			pagerIndex = 3;// 完成 （审核未通过）
			mImgAllProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotAudirProvider.setVisibility(View.INVISIBLE);
			mImgAuditProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.VISIBLE);
		}
		// 跟随radioButton的点击设置当前的页面位置
		myViewPager.setPageIndex(pagerIndex);
		myViewPager.setCurrentItem(pagerIndex);

	}

}
