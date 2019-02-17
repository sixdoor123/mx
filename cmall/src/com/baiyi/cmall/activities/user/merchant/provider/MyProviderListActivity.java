package com.baiyi.cmall.activities.user.merchant.provider;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.LoginInfo;
import com.baiyi.cmall.pageviews.MyViewPager;
import com.baiyi.cmall.pageviews.PageView;
import com.baiyi.cmall.pageviews.ViewPagerSelected;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * ���ǹ�Ӧ��-�ҵĹ�Ӧ�б�
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-30 ����4:39:13
 */
public class MyProviderListActivity extends BaseActivity implements
		OnCheckedChangeListener {

	// ����Դ
	public ArrayList<GoodsSourceInfo> datas;
	// ���ViewPage �ļ���
	private List<PageView> pageViews = null;

	/**
	 * ������תѡ��λ 0 ��ת����Ӧ���򵥽��� 1 ��ת����Ӧ�������
	 */
	private int select;

	// ���ؽ���
	private MyLoadingBar loadingBar;
	// �̼�Id
	public String companyId;

	private String token;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);
		init();
		initTitle();
		initContent();
		myViewPager.setVisibility(View.VISIBLE);
	}

	private int state;

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		initViewPager();
	}

	private void init() {
		LoginInfo info = CmallApplication.getUserInfo();
		token = info.getToken();
		companyId = info.getCompanyID();
	}

	/**
	 * ������
	 */
	private void initTitle() {
		EventTopTitleView topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("�ҵĹ�Ӧ");
		win_title.addView(topTitleView);
	}

	// �ҵĹ�Ӧ������
	private RadioGroup mRgMyOrderGroup;
	// ȫ��������ť
	private RadioButton mRbAllOrderButton;
	// δȷ�ϵ����� ��δ��ˣ�
	private RadioButton mRbNotSureOrderButton;
	// ȷ�ϵ����� ������ˣ�
	private RadioButton mRbSureOrderButton;
	// ��ɵ����� �����δͨ����
	private RadioButton mRbCompleteOrderButton;
	// ȡ��������
	// private RadioButton mRbCancelOrderButton;

	// ȫ�������ͼ��
	private ImageView mImgAllProviderChosed;
	// δ��������ͼ��
	private ImageView mImgNotAudirProvider;
	// ����������ͼ��
	private ImageView mImgAuditProviderChosed;
	// ���δͨ�������ͼ��
	private ImageView mImgNotPassProviderChosed;

	// ��ǰѡ�е�RadioButton�������ľ���
	private float mCurrentCheckedRadioLeft;

	// ˮƽ������
	private HorizontalScrollView scrollView;

	/**
	 * ��ϸ�б�
	 */
	private void initContent() {

		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_my_purchase_order, null);
		win_title.addView(view);

		findViewById(view);

	}

	/**
	 * �ҿؼ�
	 * 
	 * @param view
	 */
	@SuppressLint("WrongViewCast")
	private void findViewById(View view) {

		scrollView = (HorizontalScrollView) view.findViewById(R.id.scrollview);
		// ����������Сͼ��
		mImgAllProviderChosed = (ImageView) view
				.findViewById(R.id.all_provider_choose);
		mImgNotAudirProvider = (ImageView) view
				.findViewById(R.id.not_audit_provider_choose);
		mImgAuditProviderChosed = (ImageView) view
				.findViewById(R.id.audit_provider_choose);
		mImgNotPassProviderChosed = (ImageView) view
				.findViewById(R.id.not_pass_provider_choose);
		mRgMyOrderGroup = (RadioGroup) view.findViewById(R.id.rg_my_order);
		mRgMyOrderGroup.setOnCheckedChangeListener(this);
		mRbAllOrderButton = (RadioButton) view
				.findViewById(R.id.rb_all_intent_order);
		mRbNotSureOrderButton = (RadioButton) view
				.findViewById(R.id.rb_not_sure_intent_order);
		mRbSureOrderButton = (RadioButton) view
				.findViewById(R.id.rb_sure_intent_order);
		mRbCompleteOrderButton = (RadioButton) view
				.findViewById(R.id.rb_complete_intent_order);
		// mRbCancelOrderButton = (RadioButton) view
		// .findViewById(R.id.rb_cancel_intent_order);

		myViewPager = (MyViewPager) findViewById(R.id.vp_pager);
		myViewPager.setVisibility(View.GONE);
		// Ĭ��ȫ��ѡ��
		mRbAllOrderButton.setChecked(true);
		// mCurrentCheckedRadioLeft = getCurrentCheckedRadioLeft();

	}

	// /**
	// * ��ȡ��ǰ��ý����RadioButton����ߵľ���
	// * @return
	// */
	// private float getCurrentCheckedRadioLeft() {
	// if (mRbAllOrderButton.isChecked()) {
	// return getResources().getDimension(R.dimen.margin_left);
	// }else if (mRbNotSureOrderButton.isChecked()){
	// return getResources().getDimension(R.dimen.margin_left_20);
	// }else if (mRbSureOrderButton.isChecked()){
	// return getResources().getDimension(R.dimen.margin_left_50);
	// }else if (mRbCompleteOrderButton.isChecked()){
	// return getResources().getDimension(R.dimen.margin_left_150);
	// }
	// return 0f;
	// }

	/**
	 * RadioGroup �ļ����¼��Ļٵ�����
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		int id = 0;
		if (checkedId == R.id.rb_all_intent_order) {
			id = 0;// ȫ��
			state = 0;
			mImgAllProviderChosed.setVisibility(View.VISIBLE);
			mImgNotAudirProvider.setVisibility(View.INVISIBLE);
			mImgAuditProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.rb_not_sure_intent_order) {
			id = 1;// δȷ�� (δ���)
			state = 1;
			mImgAllProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotAudirProvider.setVisibility(View.VISIBLE);
			mImgAuditProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.rb_sure_intent_order) {
			id = 2;// ȷ�� ������ˣ�
			state = 2;
			mImgAllProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotAudirProvider.setVisibility(View.INVISIBLE);
			mImgAuditProviderChosed.setVisibility(View.VISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.rb_complete_intent_order) {
			id = 3;// ��� �����δͨ����
			state = 3;
			mImgAllProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotAudirProvider.setVisibility(View.INVISIBLE);
			mImgAuditProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.VISIBLE);
		}
		myViewPager.setPageIndex(id);
		myViewPager.setCurrentItem(id);

	}

	// ���е�����
	private BaseMerchantProviderViewPager allPager;
	// ������
	private BaseMerchantProviderViewPager surePager;
	// δ��˵�����
	private BaseMerchantProviderViewPager notSurePager;
	// �����
	private BaseMerchantProviderViewPager completePager;
	// ���δͨ��
	private MyViewPager myViewPager;

	/**
	 * ��ʼ��ViewPage
	 */
	private void initViewPager() {

		allPager = new AllMerchantProviderViewPage(this, "-2", companyId);// ȫ��
		// ��һ��״̬���ڶ���companyid
		surePager = new NotPassMerchantProviderViewPage(this, "0", companyId);// ������
		notSurePager = new NotAuditMerchantProviderViewPage(this, "1",
				companyId);// �����
		completePager = new AlreadyAuditMerchantProviderViewPage(this, "-1",
				companyId);// ���δͨ��

		pageViews = new ArrayList<PageView>();

		pageViews.add(allPager);
		pageViews.add(surePager);
		pageViews.add(notSurePager);
		pageViews.add(completePager);

		myViewPager.init(pageViews, state);
		myViewPager.setViewPageSelectedLister(new ViewPagerSelected() {

			@Override
			public void onPageSelected(int currentNum) {
				// TODO Auto-generated method stub
				setButtonPerformClick(currentNum);
			}
		});
	}

	/**
	 * ����Button�ļ���
	 * 
	 * @param position
	 */
	private void setButtonPerformClick(int position) {
		if (position == 0) {
			state = 0;
			mRbAllOrderButton.performClick();
		} else if (position == 1) {
			state = 1;
			mRbNotSureOrderButton.performClick();
		} else if (position == 2) {
			state = 2;
			mRbSureOrderButton.performClick();
		} else if (position == 3) {
			state = 3;
			mRbCompleteOrderButton.performClick();
		}
	}

}
