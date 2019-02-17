package com.baiyi.cmall.activities.user.merchant.provider;

import java.util.ArrayList;

import android.R.integer;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.baiyi.cmall.activities.base.BaseMsgActivity;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.user.merchant.intention.ProviderIntentionOrderActivity;
import com.baiyi.cmall.activities.user.merchant.provider.viewpager.BeseEditMyProviderDetailsViewPager;
import com.baiyi.cmall.activities.user.merchant.provider.viewpager.EditMyProStandardargDetailViewPager;
import com.baiyi.cmall.activities.user.merchant.provider.viewpager.EditMyProviderDetailViewPager;
import com.baiyi.cmall.activities.user.merchant.provider.viewpager.BeseEditMyProviderDetailsViewPager.OnEditSuccessClickListener;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.pageviews.MyViewPager;
import com.baiyi.cmall.pageviews.PageView;
import com.baiyi.cmall.pageviews.ViewPagerSelected;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 
 * 我是供应商-我的供应-编辑属性和详情
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-16 上午10:40:09
 */
public class EditMyProviderStandardArgDetailActivity extends BaseMsgActivity
		implements OnCheckedChangeListener, OnClickListener {

	// 数据源
	// private GoodsSourceInfo info;
	// 状态 0 表示供应信息跳转
	// 状态 1 表示属性跳转

	private int state;
	// id
	private String id;

	// 3:是模型数据 提交时id为null
	private int idState;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);
		intData();
		initTitle();
		initRBContent();
		// initButton();
		initViewPager();

	}

	private String intentionID;
	// 判断编辑按钮是否显示
	private boolean isEditDetailEnable;
	private boolean isProNameEdit;

	/**
	 * 数据源
	 */
	private void intData() {
		idState = getIntent().getIntExtra("idState", 0);
		intentionID = getIntent().getStringExtra("IntentionID");
		state = getIntent().getIntExtra("state", 0);
		id = getIntent().getStringExtra("temp");
		isEditDetailEnable = getIntent().getBooleanExtra("isEditDetailEnable",
				false);
		if (9 == state) {
			isEditDetailEnable = true;
		}
		isProNameEdit = getIntent().getBooleanExtra("orderState", false);
	}

	/**
	 * 标题
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
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
						EditMyProviderStandardArgDetailActivity.this);
			}
		});
		topTitleView.setEventName("编辑供应信息");
		win_title.addView(topTitleView);
	}

	// 采购详情
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
	// 完成按钮
	private TextView mBtnComplete;
	// 取消按钮
	private TextView mBtnCancel;

	// Button的各部分布局控件
	private LinearLayout mLlRoot;

	/**
	 * 初始化RadioGroup的内容
	 */
	private void initRBContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_my_pro_edit, null);
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

		mLlRoot = (LinearLayout) view.findViewById(R.id.lin_foot_button);
		mBtnCancel = (TextView) view.findViewById(R.id.btn_cancel_modify);
		mBtnComplete = (TextView) view.findViewById(R.id.btn_commit_modify);
		mBtnComplete.setText("完成");
		mBtnComplete.setOnClickListener(this);
		
		if (1==state) {
			mBtnCancel.setVisibility(View.GONE);
		}else {
			mBtnCancel.setOnClickListener(this);
			mBtnCancel.setText("删除");
		}

		if (!isEditDetailEnable) {
			mLlRoot.setVisibility(View.GONE);
		}

		mRbProviderDetails.setChecked(true);
		mImgDetailsChose.setVisibility(View.VISIBLE);
		mImgStandardChose.setVisibility(View.INVISIBLE);
		mRgCollecteGroup.setOnCheckedChangeListener(this);
	}

	// 供应详情ViewPager
	private BeseEditMyProviderDetailsViewPager detailsPager;
	// 属性的ViewPager
	private BeseEditMyProviderDetailsViewPager standardViewPager;
	// 存放ViewPager的集合
	private ArrayList<PageView> pageViews;

	/**
	 * 初始化ViewPage
	 */
	private void initViewPager() {
		pageViews = new ArrayList<PageView>();
		detailsPager = new EditMyProviderDetailViewPager(this, id, state,
				isEditDetailEnable,isProNameEdit);
		standardViewPager = new EditMyProStandardargDetailViewPager(this, id,
				isEditDetailEnable);

		pageViews.add(detailsPager);
		pageViews.add(standardViewPager);

		// if (0 == state) {
		mVpProvider.init(pageViews, 0);
		// } else if (1 == state) {
		// mVpProvider.init(pageViews, 1);
		// }

		mVpProvider.setViewPageSelectedLister(new ViewPagerSelected() {

			@Override
			public void onPageSelected(int currentNum) {
				setButtonPerformClick(currentNum);
			}
		});
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
			id = 0;// 供应信息
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		detailsPager.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_commit_modify:// 完成
			detailsPager.editComplete(intentionID, idState);
			detailsPager.setListener(new OnEditSuccessClickListener() {

				@Override
				public void onSuccess(RequestNetResultInfo info) {
					if (1 == info.getStatus()) {
						Intent intent = new Intent();
						setResult(10, intent);
						finish();
					}
				}
			});
			break;
		case R.id.btn_cancel_modify:// 取消
		detailsPager.editDelete(id);
			break;
		}
	}
}
