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
 * ���ǲɹ���-�ҵĲɹ��������ࣩ
 * 
 * @author lizl
 * 
 */
public  class MyPurchaseOrderActivity extends BaseActivity implements
		OnClickListener, OnCheckedChangeListener {

	// ����Դ
	public ArrayList<GoodsSourceInfo> datas;

	private MyViewPager myViewPager = null;

	private List<PageView> pageViews = null;

	/*
	 * ViewPager��ӵ��ĸ�����
	 */
	private PurchaseView purchaseAll = null;
	private PurchaseView purchaseNotAudit = null;
	private PurchaseView purchaseAudit = null;
	private PurchaseView purchaseNotPAss = null;
	// �ҵĲɹ�������
	private RadioGroup mRgMyOrderGroup;
	// ȫ����
	private RadioButton mRbAllOrderButton;
	// δ���
	private RadioButton mRbNotSureOrderButton;
	// �����
	private RadioButton mRbSureOrderButton;
	// ��ɵ�
	private RadioButton mRbCompleteOrderButton;

	// ȫ�������ͼ��
	private ImageView mImgAllProviderChosed;
	// δ��������ͼ��
	private ImageView mImgNotAudirProvider;
	// ����������ͼ��
	private ImageView mImgAuditProviderChosed;
	// ���δͨ�������ͼ��
	private ImageView mImgNotPassProviderChosed;
	// ����
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
	 * ��ʼ��RadioButton
	 */
	public void initRg() {

		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_my_purchase_order, null);
		win_title.addView(view);
		// ����������Сͼ��
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
	 * ��ʼ��ViewPager
	 */
	private void initViewPager() {
		pageViews = new ArrayList<PageView>();

		/*
		 * -2:ȫ�� 0:����(δ���) 1:���ͨ�� -1:���δͨ��
		 */
		purchaseAll = new PurchaseView(this, -2);// ȫ��
		purchaseNotAudit = new PurchaseView(this, 0);// δ���
		purchaseAudit = new PurchaseView(this, 1);// �����
		purchaseNotPAss = new PurchaseView(this, -1);// δͨ��

		pageViews.add(purchaseAll);
		pageViews.add(purchaseNotAudit);
		pageViews.add(purchaseAudit);
		pageViews.add(purchaseNotPAss);

		myViewPager.init(pageViews, pagerIndex);

		/**
		 * ��viewPagerҳ��仯��ʱ��RadioButtonҲһ�����仯
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
	 * ������
	 */
	public void initTitle() {
		EventTopTitleView topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("�ҵĲɹ�");
		win_title.addView(topTitleView);
	}

	/**
	 * �����¼��ص�����
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		default:
			break;
		}
	}

	/**
	 * RadioGroup �ļ����¼��Ļص�����
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		if (checkedId == R.id.rb_all_intent_order) {
			pagerIndex = 0;// ȫ��
			mImgAllProviderChosed.setVisibility(View.VISIBLE);
			mImgNotAudirProvider.setVisibility(View.INVISIBLE);
			mImgAuditProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.rb_not_sure_intent_order) {
			pagerIndex = 1;// δȷ�� (δ���)
			mImgAllProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotAudirProvider.setVisibility(View.VISIBLE);
			mImgAuditProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.rb_sure_intent_order) {
			pagerIndex = 2;// ȷ�� ������ˣ�
			mImgAllProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotAudirProvider.setVisibility(View.INVISIBLE);
			mImgAuditProviderChosed.setVisibility(View.VISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.rb_complete_intent_order) {
			pagerIndex = 3;// ��� �����δͨ����
			mImgAllProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotAudirProvider.setVisibility(View.INVISIBLE);
			mImgAuditProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.VISIBLE);
		}
		// ����radioButton�ĵ�����õ�ǰ��ҳ��λ��
		myViewPager.setPageIndex(pagerIndex);
		myViewPager.setCurrentItem(pagerIndex);

	}

}
