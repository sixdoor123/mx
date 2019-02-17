package com.baiyi.cmall.activities.user.merchant.provider;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.user.merchant.MyProviderDetailsActivity;
import com.baiyi.cmall.activities.user.merchant.intention.ProviderIntentionOrderActivity;
import com.baiyi.cmall.entity.GoodSOriginInfo;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 我是供应商-我的供应-ViewPager的三个子条目
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-18 上午10:50:29
 */
public class MerchantProviderItem extends LinearLayout implements
		OnClickListener {

	// 上下文
	private Context context;

	public MerchantProviderItem(Context context) {
		this(context, null);
	}

	public MerchantProviderItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

		initView();
	}

	// 商家姓名
	private TextView mTxtMerchantName;
	// 地方
	private TextView mTxtMerchantPlace;
	// 数量
	private TextView mTxtMerchantWeight;
	// 状态
	private TextView mTxtMerchantState;
	// 数量
	private TextView mTxtMerchantPrice;

	/**
	 * 创建布局界面
	 */
	private void initView() {
		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.item_5_view, null);
		addView(view);

		mTxtMerchantName = (TextView) view.findViewById(R.id.tv_content1);
		mTxtMerchantPlace = (TextView) view.findViewById(R.id.tv_content2);
		mTxtMerchantWeight = (TextView) view.findViewById(R.id.tv_content3);
		mTxtMerchantState = (TextView) view.findViewById(R.id.tv_content4);
		mTxtMerchantPrice = (TextView) view.findViewById(R.id.tv_content5);

		this.setOnClickListener(this);
	}

	// 数据源
	private GoodsSourceInfo info;

	/**
	 * 显示
	 * 
	 * @param info
	 */
	public void setDisplay(GoodsSourceInfo info) {
		this.info = info;
		mTxtMerchantName.setText(info.getPurchasename());
		mTxtMerchantPlace.setText(info.getCompanyName());
		mTxtMerchantWeight.setText(Utils.dealWeight(info.getInventory()) + "吨");
		mTxtMerchantState.setText(info.getIntentionstatename());
		mTxtMerchantPrice.setText(Utils.dealPrice(info.getPrice()) + "元/吨");
	}

	@Override
	public void onClick(View v) {
		((BaseActivity) context).goActivity(info,
				ProviderIntentionOrderActivity.class);
	}

}
