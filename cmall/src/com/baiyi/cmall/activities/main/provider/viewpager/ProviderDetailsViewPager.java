package com.baiyi.cmall.activities.main.provider.viewpager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.MyLinearLayout;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;
import android.view.View.OnClickListener;

/**
 * 购货详情
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-23 上午9:38:49
 */
public class ProviderDetailsViewPager extends BaseProviderDetailsViewPager
		implements OnClickListener {

	public ProviderDetailsViewPager(Context context, String id, String userID,
			GoodsSourceInfo sourceInfo) {
		super(context);
		this.id = id;
		this.userID = userID;
		this.sourceInfo = sourceInfo;
	}

	// 供应商名称
	private TextView mTxtDetailMerchant;
	// 名称
	private TextView mTxtProviderName;
	// 分类
	private TextView mTxtDetailCategory;
	// 品牌
	private TextView mTxtDetailBreed;
	// 产地
	private TextView mTxtDetailArea;
	// 库存
	private TextView mTxtDetailInventory;
	// 交割地
	private TextView mTxtDetailDelivery;
	// 价格
	private TextView mTxtDetailPrice;
	// 预付金额
	private TextView mTxtDetailPayment;
	// 发布时间
	private TextView mTxtDetailPutTime;
	// 采购需求
	private TextView mTxtDetailPurNeed;

	/**
	 * 初始化货物列表
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_provider_information_content, null);
		layout.addView(view);

		findViewById(view);

		updateView();
	}

	// 进度条
	private MyLoadingBar loadingBar;

	/**
	 * 找控件
	 * 
	 * @param view
	 */
	private void findViewById(View view) {

		mTxtDetailMerchant = (TextView) view
				.findViewById(R.id.txt_detail_merchant);
		mTxtProviderName = (TextView) view.findViewById(R.id.txt_provider_name);
		mTxtDetailCategory = (TextView) view
				.findViewById(R.id.txt_detail_category);
		mTxtDetailBreed = (TextView) view.findViewById(R.id.txt_detail_breed);
		mTxtDetailArea = (TextView) view.findViewById(R.id.txt_detail_area);
		mTxtDetailInventory = (TextView) view
				.findViewById(R.id.txt_detail_weight_01);
		mTxtDetailPrice = (TextView) view.findViewById(R.id.txt_detail_price);

		mTxtDetailDelivery = (TextView) view
				.findViewById(R.id.txt_detail_delivery);
		mTxtDetailPayment = (TextView) view
				.findViewById(R.id.txt_detail_put_time);
		mTxtDetailPutTime = (TextView) view
				.findViewById(R.id.txt_detail_price_range);
		mTxtDetailPurNeed = (TextView) view
				.findViewById(R.id.txt_detail_price_way);

		loadingBar = (MyLoadingBar) view.findViewById(R.id.load);
	}

	/**
	 * 更新界面数据
	 */
	private void updateView() {
		if (null != info) {
			mTxtDetailMerchant.setText(info.getGoodSMerchant());
			mTxtProviderName.setText(info.getGoodSName());
			mTxtDetailCategory.setText(info.getGoodSCategory());
			mTxtDetailBreed.setText(info.getGoodSBrand());
			mTxtDetailArea.setText(info.getGoodSPlace());
			mTxtDetailInventory.setText(info.getGoodSWeight() + "吨");
			mTxtDetailPrice.setText(info.getGoodSPrePrice() + "元/吨");
			mTxtDetailDelivery.setText(info.getGoodSDelivery());
			mTxtDetailPayment.setText(info.getPrepayment() + "元");
			mTxtDetailPutTime.setText(info.getGoodSPutTime());
			mTxtDetailPurNeed.setText(info.getGoodSDetails());
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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initContentView(MyLinearLayout layout) {
		if (-1 == resultInfo.getStatus()) {
			initNotContent();
		} else {
			initContent();
		}
	}

	
}
