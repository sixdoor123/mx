package com.baiyi.cmall.activities.user.merchant.order.inttetion.detail;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.user.merchant.intention.ProviderIntentionOrderActivity;
import com.baiyi.cmall.activities.user.merchant.order.BaseMerchantOrderActivity;
import com.baiyi.cmall.activities.user.merchant.order.manager.MerchantOrderManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.util.ContextUtil;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 意向订单详情
 * 
 * @author sunxy
 */
public class IntentionOrderDetailActivity extends BaseMerchantOrderActivity {

	@Override
	public String getOrderDetailUrl(int orderId2) {
		String url = Config.ROOT_URL + "Company/Order/GetOrderDetail/%d";
		url = String.format(url, orderId);
		return url;
	}

	@Override
	public void parseDetailDatas(Object arg1) {
		Log.d("TAG", "订单详情 ：\n" + arg1);
		entity = MerchantOrderManager.parseIntebtionDetail(arg1);
	}

	@Override
	public void updateView() {
		if (null != entity) {
			// 订单号
			mTxtOrderNumber.setText(String.format(getResources().getString(R.string.form_num), entity.getId()));
			// 交易状态
			mTxtTradeState.setText(info.getIntentionOrderState());
			// 收货人
			mTxtConsignee.setText(entity.getReceivername());

			// 收货地址
			mTxtAcceptAddress.setText(entity.getAddress());
			// 发票类型
			mTxtBillType.setText(entity.getInvoicetypename());
			// 发票抬头
			mTxtBillRise.setText(entity.getTitle());
			// 发票内容
			mTxtBillContent.setText(entity.getContext());
			// 意向编号
			mTxtIntentionNum.setText(entity.getIntentionid());
			// 地区
			mTxtOrderEare.setText(entity.getEare());
			
			mTxtTelPhone.setText(entity.getPhone());
		}
	}

	@Override
	public void initUnlikeView() {
		View view = ContextUtil.getLayoutInflater(this)//
				.inflate(R.layout.provider_intention_details, null);
		win_content.addView(view);

		findUnlikeViewById(view);
	}

	// 商家
	private TextView mTxtMerchant;
	// 分类
	private TextView mTxtCategory;
	// 品牌
	private TextView mTxtBrandName;
	// 数量
	private TextView mTxtWeight;
	// 预付
	private TextView mTxtPrepayment;
	// 价格
	private TextView mTxtTotalPrice;
	// 订单状态
	private TextView mTxtOrderState;

	// 采购商
	private TextView mTxtPurCompanyNamy;

	// 控制进入意向详情界面
	private LinearLayout mLlEntryIntentionDetail;

	// 总金额
	private TextView mTvAllPrice;

	/**
	 * 找控件
	 * 
	 * @param view
	 */
	private void findUnlikeViewById(View view) {

		// 商家
		mTxtMerchant = (TextView) view.findViewById(R.id.tv_merchact_name);
		// 分类
		mTxtCategory = (TextView) view.findViewById(R.id.tv_category);
		// 品牌
		mTxtBrandName = (TextView) view.findViewById(R.id.tv_brand);
		// 数量
		mTxtWeight = (TextView) view.findViewById(R.id.tv_weight);
		// 预付
		mTxtPrepayment = (TextView) view.findViewById(R.id.tv_pre_price);
		// 价格
		mTxtTotalPrice = (TextView) view.findViewById(R.id.tv_price);
		// 订单状态
		mTxtOrderState = (TextView) view.findViewById(R.id.tv_state);

		mTxtPurCompanyNamy = (TextView) view.findViewById(R.id.pur_company_name);
		mTxtPurCompanyNamy.setText("买家:");

		// mLlEntryIntentionDetail = (LinearLayout)
		// view.findViewById(R.id.lin_num);
		// mLlEntryIntentionDetail.setOnClickListener(this);

		// 总价格
		mTvAllPrice = (TextView) view.findViewById(R.id.tv_all_price);
	}

	@Override
	public void updateUnlikeView() {
		if (null != entity) {
			// 商家
			mTxtMerchant.setText(entity.getCompanyname().equals("null") ? "暂无" : entity.getCompanyname());
			// 分类
			mTxtCategory.setText(entity.getCategoryName());
			// 品牌
			mTxtBrandName.setText(entity.getBrandname());
			// 数量
			mTxtWeight.setText(entity.getInventory() + "吨");
			// 预付
			String prepayment = Utils.twoDecimals(entity.getPrepayment());
			if (prepayment.startsWith(".")) {
				prepayment = "0" + prepayment;
			}
			mTxtPrepayment.setText(prepayment + "元");
			// 价格
			String price = Utils.twoDecimals(entity.getPrice());
			if (price.startsWith(".")) {
				price = "0" + price;
			}
			mTxtTotalPrice.setText(price + "元/吨");

			/*
			 * 总额= 单价*数量
			 */
			String allMoney = (Double.parseDouble(entity.getPrice()) * Long.parseLong(entity.getInventory())) + "";
			// 总价格
			mTvAllPrice.setText(Utils.twoDecimals(allMoney) + "元");
		}
	}

	/**
	 * 拒绝退款
	 * 
	 * @param orderId
	 * @return
	 */
	@Override
	public String providerOrderRefuseMoneyUrl(int orderId) {
		return AppNetUrl.getProviderOrderRefuseMoneyUrl(orderId);
	}

	/**
	 * 删除
	 */
	@Override
	public String providerOrderdeleteUrl(int orderId) {
		return AppNetUrl.getProviderOrderdeleteUrl(orderId);
	}

	/**
	 * 确认收货
	 */
	@Override
	public String providerOrderSureMoneyUrl(int orderId) {
		return AppNetUrl.getProviderOrderSureMoneyUrl(orderId);
	}

	/**
	 * 同意退款
	 */
	@Override
	public String providerOrderAgreenRefundUrl(int orderId) {
		return AppNetUrl.getProviderOrderAgreenRefundUrl(orderId);
	}

	/**
	 * 拒绝订单
	 */
	@Override
	public String providerOrderRefuseOrderUrl(int orderId) {
		return AppNetUrl.getProviderOrderRefuseOrderUrl(orderId);
	}

	/**
	 * 发货
	 */
	@Override
	public String providerOrderSendGoodSUrl(int orderId) {
		return AppNetUrl.getProviderOrderSendGoodSUrl(orderId);
	}

	/**
	 * 进入意向详情
	 */
	@Override
	public void goIntentionDetail() {
		if (null == entity) {
			displayToast("订单详情个数据为空");
			return;
		}
		goActivity(entity.getIntentionid(), ProviderIntentionOrderActivity.class, 9);
	}

}
