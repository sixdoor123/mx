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
 * ��Ӧ����-����-�༭��������
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-16 ����10:40:09
 */
public class EditProviderIntentionOrderDetailActivity extends BaseMsgActivity
		implements OnCheckedChangeListener {

	// ����Դ
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
	 * ����Դ
	 */
	private void intData() {
		info = (IntentionProviderDetailInfo) getIntent().getSerializableExtra(
				"key");
		
	}

	/**
	 * ����
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
		topTitleView.setEventName("�༭��������");
		win_title.addView(topTitleView);
	}

	// �ɹ�����
	private RadioButton mRbProviderDetails;
	// ��������
	private RadioButton mRbProviderStandard;
	// RadioGroup
	private RadioGroup mRgCollecteGroup;
	// ��Ӧ�����µ�����
	private ImageView mImgDetailsChose;
	// �����µ�����
	private ImageView mImgStandardChose;

	// �Զ����ViewPager
	private MyViewPager mVpProvider;

	/**
	 * ��ʼ��RadioGroup������
	 */
	private void initRBContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_collection_list, null);
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
	}

	// ��Ӧ����ViewPager
	private EditIntentionOrderProviderDetailViewPager detailsPager;
	// ���Ե�ViewPager
	private EditIntentionOrderStandardDetailViewPager standardViewPager;
	// ���ViewPager�ļ���
	private ArrayList<PageView> pageViews;

	/**
	 * ��ʼ��ViewPage
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
			id = 0;// ��Ӧ��Ϣ
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
	
}
