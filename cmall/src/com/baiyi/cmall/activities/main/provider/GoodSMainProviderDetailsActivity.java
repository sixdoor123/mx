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
 * 我的供应的供应详情
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-23 上午9:22:03
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

	// 数据实体类
	private GoodsSourceInfo info;
	// 商品ID
	private String id;
	// 用户ID
	private String userID;
	// 商家ID
	private int companyID;
	//
	private String token;
	// state 1：表示是从关注供应进入此页面
	private int state;

	/**
	 * 初始化数据
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

	// 自定义标题栏
	protected EventTopTitleView topTitleView = null;

	/**
	 * 标题栏
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("供应详情");
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

	// 供应详情
	private RadioButton mRbProviderDetails;
	// 属性
	private RadioButton mRbProviderStandard;
	// RadioGroup
	private RadioGroup mRgCollecteGroup;
	// 供应向情下的三角
	private ImageView mImgDetailsChose;
	// 属性下的三角
	private ImageView mImgStandardChose;

	// 自定义的ViewPager
	private MyViewPager mVpProvider;
	// 委托采购按钮
	private TextView mTxtDelegationPurchase;
	// 加入关注按钮
	private TextView mTxtAttention;
	// 添加按钮的布局
	private LinearLayout mLlAddButton;

	/**
	 * 初始化RadioGroup的内容
	 */
	private void initRBContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_pro_details, null);
		win_title.addView(view);

		mRgCollecteGroup = (RadioGroup) view.findViewById(R.id.collect_group);
		mRbProviderDetails = (RadioButton) view
				.findViewById(R.id.provider_collection);
		mRbProviderDetails.setText("供应信息");

		mRbProviderStandard = (RadioButton) view
				.findViewById(R.id.purchase_collection);
		mRbProviderStandard.setText("属性");

		mImgDetailsChose = (ImageView) view.findViewById(R.id.provider_choose);
		mImgStandardChose = (ImageView) view.findViewById(R.id.purchase_choose);

		mVpProvider = (MyViewPager) view.findViewById(R.id.vp_collection);

		mRbProviderDetails.setChecked(true);

		mRgCollecteGroup.setOnCheckedChangeListener(this);

		mLlAddButton = (LinearLayout) view.findViewById(R.id.ll_add_button);
	}

	// 供应详情ViewPager
	private BaseProviderDetailsViewPager detailsPager;
	// 属性的ViewPager
	private BaseProviderDetailsViewPager standardViewPager;
	// 存放ViewPager的集合
	private ArrayList<PageView> pageViews;

	// 判断是否已关注
	private boolean isFollow;

	/**
	 * 初始化ViewPage
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
						mTxtAttention.setText("未登录,无法关注");
						mTxtAttention.setEnabled(false);
					} else {
						if (isFollow) {
							if (1 == state) {
								mTxtAttention.setText("取消关注");
								mTxtAttention.setEnabled(true);
								if (-1 == resultInfo.getStatus()) {
									mTxtDelegationPurchase.setVisibility(View.GONE);
								}
							} else {
								mTxtAttention.setText("已关注");
								mTxtAttention.setEnabled(false);
							}
						} else {
							mTxtAttention.setText("加入关注");
							mTxtAttention.setEnabled(true);
						}

					}
				} else {
					mTxtAttention.setText("取消关注");
					mTxtAttention.setEnabled(true);
					mTxtDelegationPurchase.setVisibility(View.GONE);
				}
			}

			@Override
			public void onTokenFailure(RequestNetResultInfo resultInfo) {
				ExitLogin.getInstence(GoodSMainProviderDetailsActivity.this).cleer();
				mTxtAttention.setText("账号过期，请重新登录");
				mTxtAttention.setEnabled(false);
				return;
			}
		});
	}

	/**
	 * 初始化按钮
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
	 * 设置Button的监听
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
	 * RadioGroup 的监听事件的毁掉方法
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		int id = 0;
		if (checkedId == R.id.provider_collection) {
			id = 0;// 供应详情
			mImgDetailsChose.setVisibility(View.VISIBLE);
			mImgStandardChose.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.purchase_collection) {
			id = 1;// 属性
			mImgDetailsChose.setVisibility(View.INVISIBLE);
			mImgStandardChose.setVisibility(View.VISIBLE);
		}
		mVpProvider.setPageIndex(id);
		mVpProvider.setCurrentItem(id);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_sure_purchase_order:// 委托采购
			detailsPager.goDelegation();
			// ((BaseActivity) context).finish();
			break;
		case R.id.btn_commit_purchase_order:// 加入关注
			if (isFollow) {// 取消关注
				detailsPager.cancelAttention();
			} else {// 加入关注
				detailsPager.joinAttention();
			}
			break;
		}
	}
}
