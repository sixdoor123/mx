package com.baiyi.cmall.views.pageview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.pageviews.BaseScrollViewPageView;
import com.baiyi.cmall.views.itemview.MyLinearLayout;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

import android.view.View.OnClickListener;

/**
 * 我是供应商-我的供应-采购详情
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-23 上午9:38:49
 */
@SuppressLint("CutPasteId")
public class MyProviderDetailsViewPager extends BaseScrollViewPageView
		implements OnClickListener {

	// 数据源
	private GoodsSourceInfo info;
	// 上下文
	private Context context;
	// 商品 ID
	private String id;
	// 用户ID
	private String userID;

	// 是否显示编辑
	// private int state;
	private MyLinearLayout layout;

	public MyProviderDetailsViewPager(Context context, GoodsSourceInfo info) {
		super(context);
		this.context = context;
		this.info = info;
		// this.state = state;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		layout = new MyLinearLayout(context);
		addView(layout);
//		userID = XmlUtils.getInstence(context).getXmlStringValue("userID");
		// initData();
		initContent();
	}

	// 供应商名称
	private TextView mTxtDetailMerchant;
	// 名称
	private TextView mTxtDetailName;
	// 分类
	private TextView mTxtDetailCategory;
	// 产地
	private TextView mTxtDetailArea;
	// 品种
	private TextView mTxtDetailBreed;
	// 数量
	private TextView mTxtDetailWeight;
	//价格
	private TextView mTxtDetailPrice;
	//预付金额
	private TextView mTxtDetailPrepayment;
	// 交割地
	private TextView mTxtDetailDelivery;
	//详细内容
	private TextView mTxtDetailContent;
	// 发布时间
	private TextView mTxtDetailPutTime;

	/**
	 * 初始化货物列表
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_provider_details_content, null);
		layout.addView(view);

		// 进行实例化
		findViewById(view);

		updateView();
	}

	/**
	 * 找控件
	 * 
	 * @param view
	 */
	private void findViewById(View view) {

		mTxtDetailMerchant = (TextView) view
				.findViewById(R.id.txt_detail_merchant);
		mTxtDetailName = (TextView) view.findViewById(R.id.txt_detail_name);
		mTxtDetailCategory = (TextView) view
				.findViewById(R.id.txt_detail_category);
		mTxtDetailArea = (TextView) view.findViewById(R.id.txt_detail_area);
		mTxtDetailBreed = (TextView) view.findViewById(R.id.txt_detail_breed);
		mTxtDetailWeight = (TextView) view.findViewById(R.id.txt_detail_weight);
		mTxtDetailPrice = (TextView) view.findViewById(R.id.txt_detail_weight);
		mTxtDetailPrepayment = (TextView) view.findViewById(R.id.txt_detail_prepayment);
		mTxtDetailDelivery = (TextView) view
				.findViewById(R.id.txt_detail_delivery);
		mTxtDetailContent = (TextView) view
				.findViewById(R.id.txt_detail_content);
		mTxtDetailPutTime = (TextView) view
				.findViewById(R.id.txt_detail_put_time);

		mTxtDetailWeight = (TextView) view.findViewById(R.id.txt_detail_price);
	}

	/**
	 * 更新界面数据
	 */

	private void updateView() {
		if (null != info) {
			
			mTxtDetailMerchant.setText(info.getGoodSMerchant());
			mTxtDetailName.setText(info.getGoodSName());
			mTxtDetailCategory.setText(info.getGoodSCategory());
			mTxtDetailArea.setText(info.getGoodSPlace());
			mTxtDetailBreed.setText(info.getGoodSBrand());
			mTxtDetailWeight.setText(info.getGoodSPrePrice()+"元/吨");
			mTxtDetailPrice.setText(info.getGoodSWeight()+"吨");
			mTxtDetailPrepayment.setText(info.getPrepayment()+"元");
			mTxtDetailDelivery.setText(info.getGoodSDelivery());
			mTxtDetailContent.setText(info.getGoodSDetails());
			mTxtDetailPutTime.setText(info.getGoodSPutTime());
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

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
	}
}
