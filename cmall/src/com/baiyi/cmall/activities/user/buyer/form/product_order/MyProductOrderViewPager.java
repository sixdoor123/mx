package com.baiyi.cmall.activities.user.buyer.form.product_order;

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

import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.pageviews.BasePageView;
import com.baiyi.cmall.pageviews.MyViewPager;
import com.baiyi.cmall.pageviews.PageView;
import com.baiyi.cmall.pageviews.ViewPagerSelected;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * �û�����-�ҵĲɹ�������
 * 
 * �ǂ������࣬�̳����������ʵ������δʵ�ֵķ���
 * 
 * @author lizl
 * 
 */
public class MyProductOrderViewPager extends BasePageView implements
		OnClickListener, OnCheckedChangeListener {

	private Context context;

	public MyProductOrderViewPager(Context context) {
		super(context);
		this.context = context;
		initRg();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initViewPager();
	}

	// ����Դ
	public ArrayList<GoodsSourceInfo> datas;

	private MyViewPager myViewPager = null;

	private List<PageView> pageViews = null;

	/*
	 * ViewPager��ӵ��ĸ�����
	 */
	private ProductOrderView orderView1 = null;
	private ProductOrderView orderView2 = null;
	private ProductOrderView orderView3 = null;
	private ProductOrderView orderView4 = null;

	// �ҵĲɹ�������
	private RadioGroup mRgMyOrderGroup;
	// ȫ����
	private RadioButton mRbAllOrderButton;
	// δȷ�ϵ�
	private RadioButton mRbNotSureOrderButton;
	// ȷ�ϵ�
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

	/**
	 * ��ʼ��RadioButton
	 */
	public void initRg() {

		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_my_purchase_order, null);
		this.addView(view);
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
		mRbAllOrderButton.setText("ȫ��");
		mRbNotSureOrderButton = (RadioButton) view
				.findViewById(R.id.rb_not_sure_intent_order);
		mRbNotSureOrderButton.setText("������");
		mRbSureOrderButton = (RadioButton) view
				.findViewById(R.id.rb_sure_intent_order);
		mRbSureOrderButton.setText("���ջ�");
		mRbCompleteOrderButton = (RadioButton) view
				.findViewById(R.id.rb_complete_intent_order);
		mRbCompleteOrderButton.setText("�����");
		mRgMyOrderGroup.setOnCheckedChangeListener(this);

	}

	/**
	 * ��ʼ��ViewPager
	 */
	private void initViewPager() {
		pageViews = new ArrayList<PageView>();

		orderView1 = new ProductOrderView(context, 0);
		orderView2 = new ProductOrderView(context, 1);
		orderView3 = new ProductOrderView(context, 2);
		orderView4 = new ProductOrderView(context, 3);

		pageViews.add(orderView1);
		pageViews.add(orderView2);
		pageViews.add(orderView3);
		pageViews.add(orderView4);

		myViewPager.init(pageViews, 0);

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

		int id = 0;
		if (checkedId == R.id.rb_all_intent_order) {
			id = 0;// ȫ��
			mImgAllProviderChosed.setVisibility(View.VISIBLE);
			mImgNotAudirProvider.setVisibility(View.INVISIBLE);
			mImgAuditProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.rb_not_sure_intent_order) {
			id = 1;// ������
			mImgAllProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotAudirProvider.setVisibility(View.VISIBLE);
			mImgAuditProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.rb_sure_intent_order) {
			id = 2;// ���ջ�
			mImgAllProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotAudirProvider.setVisibility(View.INVISIBLE);
			mImgAuditProviderChosed.setVisibility(View.VISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.rb_complete_intent_order) {
			id = 3;// �����
			mImgAllProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotAudirProvider.setVisibility(View.INVISIBLE);
			mImgAuditProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.VISIBLE);
		}
		// ����radioButton�ĵ�����õ�ǰ��ҳ��λ��
		myViewPager.setPageIndex(id);
		myViewPager.setCurrentItem(id);

	}

	/**
	 * �������еİ�ť�����ɹ�ʱ������һ�·���
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (null == orderView1 || null == orderView2 || null == orderView3
				|| null == orderView4) {
			return;
		}
		if (resultCode == NumEntity.RESULT_FORM_OK) {
			orderView1.onActivityResult(requestCode, resultCode, data);
			orderView2.onActivityResult(requestCode, resultCode, data);
			orderView3.onActivityResult(requestCode, resultCode, data);
			orderView4.onActivityResult(requestCode, resultCode, data);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

	}
}
