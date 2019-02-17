package com.baiyi.cmall.activities.user.merchant;

import org.json.JSONObject;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.user.merchant.intention.ProviderIntentionOrderActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.OrderEntity;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.request.manager.MerchantCenterManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 我是供应商-我的订单-意向订单详情
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-19 下午4:07:42
 */
public class MyProviderOrderDetailsActivity extends BaseActivity implements OnClickListener {

	private String token;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);
		topTitle();
		initContent();
		initData();
		loadData();
	}

	// 发货
	private TextView mBtnSendGoodS;
	// 拒绝订单
	private TextView mBtnRefuseOrder;
	// 同意退款(已确认退款)
	private TextView mBtnAgreenRefund;
	// 确认收款按钮
	private TextView mBtnSureMoney;
	// 删除
	private TextView mBtnDelete;
	// 拒绝退款
	private TextView mBtnRefuseMoney;

	// 已确认退款
	private TextView mBtnRefundAreadySure;

	// 进入时订单的状态
	private int orderState;
	private View view;

	/**
	 * 初始化按钮
	 */
	private void initFooter() {
		view = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_purchase_intention_foot_copy, null);

		mBtnSendGoodS = (TextView) view.findViewById(R.id.btn_sure_purchase_order_copy);
		mBtnAgreenRefund = (TextView) view.findViewById(R.id.btn_commit_purchase_order_copy);
		mBtnRefuseOrder = (TextView) view.findViewById(R.id.btn_cancel);

		view.findViewById(R.id.view_line_15).setVisibility(View.GONE);
		mBtnDelete = (TextView) view.findViewById(R.id.btn_commit_modify);

		mBtnSureMoney = (TextView) view.findViewById(R.id.btn_cancel_modify);

		mBtnRefuseMoney = (TextView) view.findViewById(R.id.btn_cancel_purchase_order_copy);

		mBtnRefundAreadySure = (TextView) view.findViewById(R.id.btn_sure);
		mBtnRefundAreadySure.setEnabled(false);

		mBtnRefuseOrder.setVisibility(View.GONE);

		mBtnSendGoodS.setOnClickListener(this);
		mBtnDelete.setOnClickListener(this);
		// mBtnRefuseOrder.setOnClickListener(this);
		mBtnSureMoney.setOnClickListener(this);
		mBtnRefuseMoney.setOnClickListener(this);
		mBtnRefundAreadySure.setOnClickListener(this);
		mBtnAgreenRefund.setOnClickListener(this);

		// mBtnRefuseOrder.setText("拒绝订单");
		mBtnSendGoodS.setText("确认发货");
		mBtnRefundAreadySure.setText("已确认退款");
		mBtnRefuseMoney.setText("拒绝退款");
		mBtnAgreenRefund.setText("同意退款");
		mBtnSureMoney.setText("确认收款");
		mBtnDelete.setText("删除");

		orderState(entity.getBinaryvalue());
	}

	/**
	 * 订单状态的判断
	 * 
	 * @param orderState
	 * 
	 */
	private void orderState(String binaryvalue) {
		if (entity != null) {
			int[] status = IntentionOrderUtils.getStatus(binaryvalue);

			mBtnSendGoodS.setVisibility(IntentionOrderUtils.isVisableOrGone(status[1]));
			mBtnRefundAreadySure.setVisibility(IntentionOrderUtils.isVisableOrGone(status[3]));
			mBtnRefuseMoney.setVisibility(IntentionOrderUtils.isVisableOrGone(status[5]));
			mBtnAgreenRefund.setVisibility(IntentionOrderUtils.isVisableOrGone(status[2]));
			mBtnSureMoney.setVisibility(IntentionOrderUtils.isVisableOrGone(status[4]));
			mBtnDelete.setVisibility(IntentionOrderUtils.isVisableOrGone(status[0]));

			// mBtnRefuseOrder.setVisibility(OrderUtils.getRefuseOrder(orderState));
			// 判断是否返回
			// if (IntentionOrderUtils.isFinish(status)) {
			// Intent intent = new Intent();
			// setResult(7, intent);
			// finish();
			// }

			// 判断是否有按钮显示
			if (IntentionOrderUtils.isAddView(status)) {
				win_content.removeView(view);
				win_content.addView(view);
			}
		}
	}

	// 自定义标题
	private EventTopTitleView topTitleView;

	/**
	 * 标题
	 */
	private void topTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setTitleNewsOnclick(new TitleNewsOnclick() {

			@Override
			public void setTitleNewsOnclickLister(boolean isPop) {

				topTitleView.displayPoP(MsgItemUtil.Pop_Default_txt, MsgItemUtil.Pop_Default_img);

			}
		});
		topTitleView.setNewsPopItemOnclick(new NewsPopItemOnclick() {

			@Override
			public void setNewsPopItemOnclickLister(int state) {
				MsgItemUtil.onclickPopItem(state, MyProviderOrderDetailsActivity.this);
			}
		});
		topTitleView.setEventName("订单详情");
		win_title.addView(topTitleView);
	}

	// 数据源
	private GoodsSourceInfo info;
	// 订单ID
	private int orderId;

	private int state;

	/**
	 * 获取数据
	 */
	private void initData() {
		token = CmallApplication.getUserInfo().getToken();

		state = getIntent().getIntExtra("state", 0);
		info = (GoodsSourceInfo) getIntent().getSerializableExtra("key");
		if (null != info) {
			orderId = info.getGoodSProviderOrderId();
			orderState = info.getOrderstate();
		}
	}

	// 订单内容实体类
	private OrderEntity entity;

	/**
	 * 从网络加载数据
	 */
	private void loadData() {
		loadingBar.setVisibility(View.VISIBLE);
		loadingBar.setProgressInfo("正在加载中...");
		loadingBar.start();

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getProviderOrderDetailUrl(orderId));
		loader.setPostData(new JSONObject().toString());
		loader.addRequestHeader("token", token);
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				displayToast(arg2);
				loadingBar.setVisibility(View.GONE);
				loadingBar.stop();
				finish();
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				entity = MerchantCenterManager.getProviderOrderDetailsInfo(arg1);
				loadingBar.setVisibility(View.GONE);
				loadingBar.stop();
				if (null != entity) {
					updateViewData();
					initFooter();
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * 初始化内容
	 */
	@SuppressLint("InflateParams")
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_pur_intention_order_detail, null);
		win_content.addView(view);

		// 找控件
		findViewById(view);

	}

	// 显示进度
	private MyLoadingBar loadingBar;
	// 订单号
	private TextView mTxtOrderNumber;
	// 交易状态
	private TextView mTxtTradeState;
	// 收货人
	private TextView mTxtConsignee;
	// 收货地址
	private TextView mTxtAcceptAddress;
	// 发票类型
	private TextView mTxtBillType;
	// 发票抬头
	private TextView mTxtBillRise;
	// 发票内容
	private TextView mTxtBillContent;
	// 意向编号
	private TextView mTxtIntentionNum;
	// 地区
	private TextView mTxtOrderEare;

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

	// 联系人
	private TextView mTxtTelPhone;

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
	private void findViewById(View view) {
		loadingBar = (MyLoadingBar) view.findViewById(R.id.load);
		// 订单号
		mTxtOrderNumber = (TextView) view.findViewById(R.id.tv_order_id);
		// 交易状态
		mTxtTradeState = (TextView) view.findViewById(R.id.tv_warm_info);
		// 收货人
		mTxtConsignee = (TextView) view.findViewById(R.id.tv_get_goods_person);
		// 收货地址
		mTxtAcceptAddress = (TextView) view.findViewById(R.id.tv_get_goods_address);
		// 发票类型
		mTxtBillType = (TextView) view.findViewById(R.id.tv_invoice_type);
		// 发票抬头
		mTxtBillRise = (TextView) view.findViewById(R.id.tv_invoice_title);
		// 发票内容
		mTxtBillContent = (TextView) view.findViewById(R.id.tv_invoice_content);
		// 意向编号
		mTxtIntentionNum = (TextView) view.findViewById(R.id.tv_attention_id);
		// 地区
				mTxtOrderEare = (TextView) view.findViewById(R.id.tv_get_goods_city);
		
		
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
		// 联系人
		mTxtTelPhone = (TextView) view.findViewById(R.id.tv_tel_phone);
		

		mTxtPurCompanyNamy = (TextView) view.findViewById(R.id.pur_company_name);
		mTxtPurCompanyNamy.setText("买家:");

		mLlEntryIntentionDetail = (LinearLayout) view.findViewById(R.id.lin_num);
		mLlEntryIntentionDetail.setOnClickListener(this);

		// 总价格
		mTvAllPrice = (TextView) view.findViewById(R.id.tv_all_price);

	}

	/**
	 * 舒心界面数据
	 */
	private void updateViewData() {
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
			// 订单状态
						mTxtOrderState.setText(info.getIntentionOrderState());
						// 地区
									mTxtOrderEare.setText(entity.getEare());
									
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
			
			mTxtTelPhone.setText(entity.getPhone());

			/*
			 * 总额= 单价*数量
			 */
			String allMoney = (Double.parseDouble(entity.getPrice()) * Long.parseLong(entity.getInventory())) + "";
			// 总价格
			mTvAllPrice.setText(Utils.twoDecimals(allMoney) + "元");
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_sure_purchase_order_copy:// 发货
			sendGoodS();
			break;
		case R.id.btn_cancel_purchase_order_copy:// 拒绝退款
			refuseMoney();
			break;
		case R.id.btn_commit_purchase_order_copy:// 同意退款
			agreenRefund();
			break;
		case R.id.btn_cancel_modify:// 确认收款
			sureMoney();
			break;
		case R.id.btn_commit_modify:// 删除
			delete();
			break;
		case R.id.btn_cancel:// 拒绝订单
			refuseOrder();
			break;
		case R.id.lin_num:// 进入详情
			if (null == entity) {
				displayToast("订单详情个数据为空");
				return;
			}
			goActivity(entity.getIntentionid(), ProviderIntentionOrderActivity.class, 9);
			break;
		}
	}

	/**
	 * 拒绝退款
	 */
	private void refuseMoney() {
		orderOpration(AppNetUrl.getProviderOrderRefuseMoneyUrl(orderId));
	}

	/**
	 * 删除
	 */
	private void delete() {
		orderOpration(AppNetUrl.getProviderOrderdeleteUrl(orderId));

	}

	/**
	 * 确认收货
	 */
	private void sureMoney() {
		orderOpration(AppNetUrl.getProviderOrderSureMoneyUrl(orderId));
	}

	/**
	 * 同意退款
	 */
	private void agreenRefund() {
		orderOpration(AppNetUrl.getProviderOrderAgreenRefundUrl(orderId));
	}

	/**
	 * 拒绝订单
	 */
	private void refuseOrder() {
		orderOpration(AppNetUrl.getProviderOrderRefuseOrderUrl(orderId));
	}

	/**
	 * 发货
	 */
	private void sendGoodS() {
		orderOpration(AppNetUrl.getProviderOrderSendGoodSUrl(orderId));
	}

	/**
	 * 发货、拒绝订单、同意退款共同请求网络的方法
	 * 
	 * @param url
	 */
	private void orderOpration(String url) {
		loadingBar.setVisibility(View.VISIBLE);
		loadingBar.setProgressInfo("正在加载中...");
		loadingBar.start();

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(url);
		loader.setPostData(new JSONObject().toString());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.addRequestHeader("token", token);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
				loadingBar.setProgressInfo("正在解析");
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				displayToast(arg2);
				loadingBar.setVisibility(View.GONE);
				loadingBar.stop();
			}

			@SuppressWarnings("unused")
			@Override
			public void onCompelete(Object arg0, Object arg1) {
				GoodsSourceInfo sourceInfo = MerchantCenterManager.getProviderOrderSendGoodSInfo(arg1);
				loadingBar.setVisibility(View.GONE);
				loadingBar.stop();
				if (null != sourceInfo) {
					RequestNetResultInfo info = sourceInfo.getResultInfo();
					displayToast(info.getMsg());
					if (1 == info.getStatus()) {
						orderState(sourceInfo.getBinaryvalue());
						Intent intent = new Intent();
						intent.putExtra("state", state);
						setResult(7, intent);
						finish();
					} else {
						return;
					}
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}
}
