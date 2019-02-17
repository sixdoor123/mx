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
 * ��Ʒ����
 * 
 * @author sunxy
 */
public class ProductOrderListViewPager extends BasePageView implements
		OnClickListener, OnCheckedChangeListener {

	private Context context;
	// ����Դ
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

	private ProductOrderCategoryViewPager viewPager1;// ȫ��
	private ProductOrderCategoryViewPager viewPager2;// ��ȷ��
	private ProductOrderCategoryViewPager viewPager3;// δȷ��
	private ProductOrderCategoryViewPager viewPager4;// �����

	/**
	 * ��ʼ��ViewPager
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
		 * ��viewPagerҳ��仯��ʱ��RadioButtonҲһ�����仯
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
