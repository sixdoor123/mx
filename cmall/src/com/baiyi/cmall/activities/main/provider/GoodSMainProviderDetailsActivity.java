package com.baiyi.cmall.activities.main.provider;

import java.util.ArrayList;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.provider.viewpager.BaseProviderDetailsViewPager;
import com.baiyi.cmall.activities.main.provider.viewpager.BaseProviderDetailsViewPager.OnDataInitFinishedListener;
import com.baiyi.cmall.activities.main.provider.viewpager.ProviderDetailsViewPager;
import com.baiyi.cmall.activities.main.provider.viewpager.ProviderStandardArgumentViewPager;
import com.baiyi.cmall.activities.user.login.ExitLogin;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.LoginInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.pageviews.MyViewPager;
import com.baiyi.cmall.pageviews.PageView;
import com.baiyi.cmall.pageviews.ViewPagerSelected;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.utils.XmlName;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.core.util.ContextUtil;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * 
 * �ҵĹ�Ӧ�Ĺ�Ӧ����
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-23 ����9:22:03
 */
public class GoodSMainProviderDetailsActivity extends BaseActivity implements
		OnCheckedChangeListener, OnClickListener {
	private GoodsSourceInfo sourceInfo;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);

		initData();
		initTitle();
		initRBContent();
		initViewPager();
	}

	// ����ʵ����
	private GoodsSourceInfo info;
	// ��ƷID
	private String id;
	// �û�ID
	private String userID;
	// �̼�ID
	private int companyID;
	//
	private String token;
	// state 1����ʾ�Ǵӹ�ע��Ӧ�����ҳ��
	private int state;

	/**
	 * ��ʼ������
	 */
	private void initData() {
		state = getIntent().getIntExtra("temp", 0);
		sourceInfo = (GoodsSourceInfo) getIntent().getSerializableExtra("key");
		id = getIntent().getStringExtra("temp");
		if (id == null) {
			id = sourceInfo.getOfferid();
		}
		LoginInfo loginInfo = CmallApplication.getUserInfo();
		if (loginInfo != null) {
			userID = loginInfo.getUserID();
			String companyId = loginInfo.getCompanyID();
			if ("null".equals(companyId) || companyId == null) {
				companyId = String.valueOf(0);
			}
			companyID = Integer.parseInt(companyId);
		}
		token = XmlUtils.getInstence(this).getXmlStringValue(XmlName.TOKEN);
	}

	// �Զ��������
	protected EventTopTitleView topTitleView = null;

	/**
	 * ������
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("��Ӧ����");
		topTitleView.setTitleNewsOnclick(new TitleNewsOnclick() {

			@Override
			public void setTitleNewsOnclickLister(boolean isPop) {

				topTitleView.displayPoP(MsgItemUtil.Pop_Default_txt,
						MsgItemUtil.Pop_Default_img);

			}
		});
		topTitleView.setNewsPopItemOnclick(new NewsPopItemOnclick() {

			@Override
			public void setNewsPopItemOnclickLister(int state) {
				MsgItemUtil.onclickPopItem(state,
						GoodSMainProviderDetailsActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}

	// ��Ӧ����
	private RadioButton mRbProviderDetails;
	// ����
	private RadioButton mRbProviderStandard;
	// RadioGroup
	private RadioGroup mRgCollecteGroup;
	// ��Ӧ�����µ�����
	private ImageView mImgDetailsChose;
	// �����µ�����
	private ImageView mImgStandardChose;

	// �Զ����ViewPager
	private MyViewPager mVpProvider;
	// ί�вɹ���ť
	private TextView mTxtDelegationPurchase;
	// �����ע��ť
	private TextView mTxtAttention;
	// ��Ӱ�ť�Ĳ���
	private LinearLayout mLlAddButton;

	/**
	 * ��ʼ��RadioGroup������
	 */
	private void initRBContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_pro_details, null);
		win_title.addView(view);

		mRgCollecteGroup = (RadioGroup) view.findViewById(R.id.collect_group);
		mRbProviderDetails = (RadioButton) view
				.findViewById(R.id.provider_collection);
		mRbProviderDetails.setText("��Ӧ��Ϣ");

		mRbProviderStandard = (RadioButton) view
				.findViewById(R.id.purchase_collection);
		mRbProviderStandard.setText("����");

		mImgDetailsChose = (ImageView) view.findViewById(R.id.provider_choose);
		mImgStandardChose = (ImageView) view.findViewById(R.id.purchase_choose);

		mVpProvider = (MyViewPager) view.findViewById(R.id.vp_collection);

		mRbProviderDetails.setChecked(true);

		mRgCollecteGroup.setOnCheckedChangeListener(this);

		mLlAddButton = (LinearLayout) view.findViewById(R.id.ll_add_button);
	}

	// ��Ӧ����ViewPager
	private BaseProviderDetailsViewPager detailsPager;
	// ���Ե�ViewPager
	private BaseProviderDetailsViewPager standardViewPager;
	// ���ViewPager�ļ���
	private ArrayList<PageView> pageViews;

	// �ж��Ƿ��ѹ�ע
	private boolean isFollow;

	/**
	 * ��ʼ��ViewPage
	 */
	private void initViewPager() {
		pageViews = new ArrayList<PageView>();
		detailsPager = new ProviderDetailsViewPager(this, id, userID,
				sourceInfo);
		standardViewPager = new ProviderStandardArgumentViewPager(this, id,
				userID);

		pageViews.add(detailsPager);
		pageViews.add(standardViewPager);

		mVpProvider.init(pageViews, 0);
		mVpProvider.setViewPageSelectedLister(new ViewPagerSelected() {

			@Override
			public void onPageSelected(int currentNum) {
				setButtonPerformClick(currentNum);
			}
		});
		detailsPager.setListener(new OnDataInitFinishedListener() {
			@Override
			public void onCurrentCompanyID( boolean isowner,
					RequestNetResultInfo resultInfo) {
				initButton(isowner);
			}

			@Override
			public void ondataFinished(boolean isFollow,
					RequestNetResultInfo resultInfo) {
				GoodSMainProviderDetailsActivity.this.isFollow = isFollow;
				if (resultInfo.getStatus() == 1) {
					if (TextUtils.isEmpty(token)) {
						mTxtAttention.setText("δ��¼,�޷���ע");
						mTxtAttention.setEnabled(false);
					} else {
						if (isFollow) {
							if (1 == state) {
								mTxtAttention.setText("ȡ����ע");
								mTxtAttention.setEnabled(true);
								if (-1 == resultInfo.getStatus()) {
									mTxtDelegationPurchase.setVisibility(View.GONE);
								}
							} else {
								mTxtAttention.setText("�ѹ�ע");
								mTxtAttention.setEnabled(false);
							}
						} else {
							mTxtAttention.setText("�����ע");
							mTxtAttention.setEnabled(true);
						}

					}
				} else {
					mTxtAttention.setText("ȡ����ע");
					mTxtAttention.setEnabled(true);
					mTxtDelegationPurchase.setVisibility(View.GONE);
				}
			}

			@Override
			public void onTokenFailure(RequestNetResultInfo resultInfo) {
				ExitLogin.getInstence(GoodSMainProviderDetailsActivity.this).cleer();
				mTxtAttention.setText("�˺Ź��ڣ������µ�¼");
				mTxtAttention.setEnabled(false);
				return;
			}
		});
	}

	/**
	 * ��ʼ����ť
	 */
	protected void initButton(boolean isowner) {
		// <include layout="@layout/activity_pro_detail_foot"/>
//		if (companyID != companyId || -1 == resultInfo.getStatus()) {
			View buttonView = ContextUtil.getLayoutInflater(this).inflate(
					R.layout.activity_pro_detail_foot, null);

			mTxtDelegationPurchase = (TextView) buttonView
					.findViewById(R.id.btn_sure_purchase_order);
			mTxtDelegationPurchase.setOnClickListener(this);

			mTxtAttention = (TextView) buttonView
					.findViewById(R.id.btn_commit_purchase_order);
			
			if (isowner) {
				mTxtAttention.setVisibility(View.GONE);
			}

			TextView mTxtNoUse = (TextView) buttonView
					.findViewById(R.id.btn_cancel_purchase_order);
			mTxtNoUse.setVisibility(View.GONE);
//			if (-1 == resultInfo.getStatus()) {
//				mTxtDelegationPurchase.setVisibility(View.GONE);
//			}

			mTxtAttention.setOnClickListener(this);
			mTxtDelegationPurchase.setOnClickListener(this);

			mLlAddButton.addView(buttonView);
//		}
	}

	/**
	 * ����Button�ļ���
	 * 
	 * @param position
	 */
	private void setButtonPerformClick(int position) {
		if (position == 0) {
			mRbProviderDetails.performClick();
		} else if (position == 1) {
			mRbProviderStandard.performClick();
		}
	}

	/**
	 * RadioGroup �ļ����¼��Ļٵ�����
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		int id = 0;
		if (checkedId == R.id.provider_collection) {
			id = 0;// ��Ӧ����
			mImgDetailsChose.setVisibility(View.VISIBLE);
			mImgStandardChose.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.purchase_collection) {
			id = 1;// ����
			mImgDetailsChose.setVisibility(View.INVISIBLE);
			mImgStandardChose.setVisibility(View.VISIBLE);
		}
		mVpProvider.setPageIndex(id);
		mVpProvider.setCurrentItem(id);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_sure_purchase_order:// ί�вɹ�
			detailsPager.goDelegation();
			// ((BaseActivity) context).finish();
			break;
		case R.id.btn_commit_purchase_order:// �����ע
			if (isFollow) {// ȡ����ע
				detailsPager.cancelAttention();
			} else {// �����ע
				detailsPager.joinAttention();
			}
			break;
		}
	}
}
