package com.baiyi.cmall.activities.user.merchant.order.product;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.baiyi.cmall.R;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.pageviews.BasePageView;
import com.baiyi.cmall.pageviews.MyViewPager;
import com.baiyi.cmall.pageviews.PageView;
import com.baiyi.cmall.pageviews.ViewPagerSelected;
import com.baiyi.core.util.ContextUtil;

/**
 * 产品订单
 * 
 * @author sunxy
 */
public class ProductOrderListViewPager extends BasePageView implements
		OnClickListener, OnCheckedChangeListener {

	private Context context;
	// 数据源
	public ArrayList<GoodsSourceInfo> datas;

	private MyViewPager myViewPager = null;

	private List<PageView> pageViews = null;

	public ProductOrderListViewPager(Context context) {
		super(context);
		this.context = context;
		initRg();
		initViewPager();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	// 我的采购单分组
	private RadioGroup mRgMyOrderGroup;
	// 全部的
	private RadioButton mRbAllOrderButton;
	// 未确认的
	private RadioButton mRbNotSureOrderButton;
	// 确认的
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

	/**
	 * 初始化RadioButton
	 */
	public void initRg() {

		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_my_purchase_order, null);
		this.addView(view);
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
		mRbAllOrderButton.setText("全部");
		mRbNotSureOrderButton = (RadioButton) view
				.findViewById(R.id.rb_not_sure_intent_order);
		mRbNotSureOrderButton.setText("待付款");
		mRbSureOrderButton = (RadioButton) view
				.findViewById(R.id.rb_sure_intent_order);
		mRbSureOrderButton.setText("待收货");
		mRbCompleteOrderButton = (RadioButton) view
				.findViewById(R.id.rb_complete_intent_order);
		mRbCompleteOrderButton.setText("已完成");
		mRgMyOrderGroup.setOnCheckedChangeListener(this);

	}

	private ProductOrderCategoryViewPager viewPager1;// 全部
	private ProductOrderCategoryViewPager viewPager2;// 已确认
	private ProductOrderCategoryViewPager viewPager3;// 未确认
	private ProductOrderCategoryViewPager viewPager4;// 已完成

	/**
	 * 初始化ViewPager
	 */
	private void initViewPager() {
		pageViews = new ArrayList<PageView>();

		viewPager1 = new ProductOrderCategoryViewPager(context, "0");
		viewPager2 = new ProductOrderCategoryViewPager(context, "1");
		viewPager3 = new ProductOrderCategoryViewPager(context, "2");
		viewPager4 = new ProductOrderCategoryViewPager(context, "3");

		pageViews.add(viewPager1);
		pageViews.add(viewPager2);
		pageViews.add(viewPager3);
		pageViews.add(viewPager4);

		myViewPager.init(pageViews, 0);

		/**
		 * 当viewPager页面变化的时候RadioButton也一起发生变化
		 */
		myViewPager.setViewPageSelectedLister(new ViewPagerSelected() {
			@Override
			public void onPageSelected(int currentNum) {
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
	 * RadioGroup 的监听事件的回调方法
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		int id = 0;
		if (checkedId == R.id.rb_all_intent_order) {
			id = 0;// 全部
			mImgAllProviderChosed.setVisibility(View.VISIBLE);
			mImgNotAudirProvider.setVisibility(View.INVISIBLE);
			mImgAuditProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.rb_not_sure_intent_order) {
			id = 1;// 代付款
			mImgAllProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotAudirProvider.setVisibility(View.VISIBLE);
			mImgAuditProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.rb_sure_intent_order) {
			id = 2;// 待收货
			mImgAllProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotAudirProvider.setVisibility(View.INVISIBLE);
			mImgAuditProviderChosed.setVisibility(View.VISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.rb_complete_intent_order) {
			id = 3;// 已完成
			mImgAllProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotAudirProvider.setVisibility(View.INVISIBLE);
			mImgAuditProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.VISIBLE);
		}
		// 跟随radioButton的点击设置当前的页面位置
		myViewPager.setPageIndex(id);
		myViewPager.setCurrentItem(id);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {

	}

}
