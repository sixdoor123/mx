package com.baiyi.cmall.activities.user.merchant;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.pageviews.MyViewPager;
import com.baiyi.cmall.pageviews.PageView;
import com.baiyi.cmall.pageviews.ViewPagerSelected;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.pageview.MyProviderOrderFormView;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 
 * �ҵĹ�Ӧ������
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-15 ����10:18:10
 */
public class MyProviderOrderFormActivity extends BaseActivity implements
		OnCheckedChangeListener {

	// ����Դ
	public ArrayList<GoodsSourceInfo> datas;
	// ���ViewPage �ļ���
	private List<PageView> pageViews = null;

	/**
	 * ������תѡ��λ 0 ��ת����Ӧ���򵥽��� 1 ��ת����Ӧ�������
	 */
	// private int select;

	// ���ؽ���
	private MyLoadingBar loadingBar;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);

		initTitle();
		initContent();
		init();
		myViewPager.setVisibility(View.VISIBLE);
		initViewPager();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	// �̼�ID
	private String companyID;

	private void init() {
		companyID = XmlUtils.getInstence(this).getXmlStringValue("companyID");

	}

	/**
	 * ������
	 */
	private void initTitle() {
		EventTopTitleView topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("�ҵĹ�Ӧ��");
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
				R.layout.activity_my_provider_order, null);
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
			mImgAllProviderChosed.setVisibility(View.VISIBLE);
			mImgNotAudirProvider.setVisibility(View.INVISIBLE);
			mImgAuditProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.rb_not_sure_intent_order) {
			id = 1;// δȷ�� (δ���)
			mImgAllProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotAudirProvider.setVisibility(View.VISIBLE);
			mImgAuditProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.rb_sure_intent_order) {
			id = 2;// ȷ�� ������ˣ�
			mImgAllProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotAudirProvider.setVisibility(View.INVISIBLE);
			mImgAuditProviderChosed.setVisibility(View.VISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.rb_complete_intent_order) {
			id = 3;// ��� �����δͨ����
			mImgAllProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotAudirProvider.setVisibility(View.INVISIBLE);
			mImgAuditProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.VISIBLE);
		}
		myViewPager.setPageIndex(id);
		myViewPager.setCurrentItem(id);

		// mCurrentCheckedRadioLeft = getCurrentCheckedRadioLeft();
		// scrollView.smoothScrollTo((int)mCurrentCheckedRadioLeft-(int)getResources().getDimension(R.dimen.margin_bottom_20),
		// 0);
	}

	// ���е�����
	private MyProviderOrderFormView allPager;
	// ����˵�����
	private MyProviderOrderFormView surePager;
	// δ��˵�����
	private MyProviderOrderFormView notSurePager;
	// ȡ��������
	// private IntentionOrderPager cancelPager;
	// ��ɵ�����
	private MyProviderOrderFormView completePager;
	// �Զ����ViewPager
	private MyViewPager myViewPager;

	private int state = 0;

	/**
	 * ��ʼ��ViewPage
	 */
	private void initViewPager() {
		allPager = new MyProviderOrderFormView(this, companyID, "-1");// ȫ��
		surePager = new MyProviderOrderFormView(this, companyID, "1");// ������
		notSurePager = new MyProviderOrderFormView(this, companyID, "2");// ������
		completePager = new MyProviderOrderFormView(this, companyID, "3");// �ѷ���

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
			mRbAllOrderButton.performClick();
		} else if (position == 1) {
			mRbNotSureOrderButton.performClick();
		} else if (position == 2) {
			mRbSureOrderButton.performClick();
		} else if (position == 3) {
			mRbCompleteOrderButton.performClick();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == 7) {
			state = data.getIntExtra("state", 0);
		}
		surePager.onActivityResult(requestCode, resultCode, data);
		notSurePager.onActivityResult(requestCode, resultCode, data);
		completePager.onActivityResult(requestCode, resultCode, data);
		allPager.onActivityResult(requestCode, resultCode, data);
	}
}
