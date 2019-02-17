package com.baiyi.cmall.activities.user.merchant;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TableRow;
import android.widget.TextView;

import com.baiyi.cmall.activities.base.BaseMsgActivity;
import com.baiyi.cmall.activities.main.purchase.PurchaseDetailViewPager;
import com.baiyi.cmall.activities.main.purchase.PurchaseStandardArgumentViewPager;
import com.baiyi.cmall.activities.user.merchant.intention_viewpage.EditIntentionOrderProviderDetailViewPager;
import com.baiyi.cmall.activities.user.merchant.intention_viewpage.EditIntentionOrderStandardDetailViewPager;
import com.baiyi.cmall.activities.user.merchant.provider.viewpager.EditMyProStandardargDetailViewPager;
import com.baiyi.cmall.activities.user.merchant.provider.viewpager.EditMyProviderDetailViewPager;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.IntentionDetailStandardInfo;
import com.baiyi.cmall.entity.IntentionProviderDetailInfo;
import com.baiyi.cmall.entity.TransforData;
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
 * 供应意向单-详情-编辑意向单详情
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-16 上午10:40:09
 */
public class EditProviderIntentionOrderDetailActivity extends BaseMsgActivity
		implements OnCheckedChangeListener {

	// 数据源
	private IntentionProviderDetailInfo info;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);
		intData();
		initTitle();
		initRBContent();
		initViewPager();
	}

	/**
	 * 数据源
	 */
	private void intData() {
		info = (IntentionProviderDetailInfo) getIntent().getSerializableExtra(
				"key");
		
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
						EditProviderIntentionOrderDetailActivity.this);
			}
		});
		topTitleView.setEventName("编辑意向详情");
		win_title.addView(topTitleView);
	}

	// 采购详情
	private RadioButton mRbProviderDetails;
	// 属性属性
	private RadioButton mRbProviderStandard;
	// RadioGroup
	private RadioGroup mRgCollecteGroup;
	// 供应向情下的三角
	private ImageView mImgDetailsChose;
	// 属性下的三角
	private ImageView mImgStandardChose;

	// 自定义的ViewPager
	private MyViewPager mVpProvider;

	/**
	 * 初始化RadioGroup的内容
	 */
	private void initRBContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_collection_list, null);
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
	}

	// 供应详情ViewPager
	private EditIntentionOrderProviderDetailViewPager detailsPager;
	// 属性的ViewPager
	private EditIntentionOrderStandardDetailViewPager standardViewPager;
	// 存放ViewPager的集合
	private ArrayList<PageView> pageViews;

	/**
	 * 初始化ViewPage
	 */
	private void initViewPager() {
		pageViews = new ArrayList<PageView>();
		detailsPager = new EditIntentionOrderProviderDetailViewPager(this, info);
		standardViewPager = new EditIntentionOrderStandardDetailViewPager(this,
				info.getProDetailStandardInfos());

		pageViews.add(detailsPager);
		pageViews.add(standardViewPager);

		mVpProvider.init(pageViews, 0);

		mVpProvider.setViewPageSelectedLister(new ViewPagerSelected() {

			@Override
			public void onPageSelected(int currentNum) {
				setButtonPerformClick(currentNum);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		for(PageView pv : pageViews)
		{
			pv.onActivityResult(requestCode, resultCode, data);
		}
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
	
}
