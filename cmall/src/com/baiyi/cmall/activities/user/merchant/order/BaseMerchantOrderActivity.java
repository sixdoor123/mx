package com.baiyi.cmall.activities.user.merchant.order;

import org.json.JSONObject;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.user.merchant.IntentionOrderUtils;
import com.baiyi.cmall.activities.user.merchant.intention.ProviderIntentionOrderActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.OrderEntity;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.model.Odm;
import com.baiyi.cmall.request.manager.MerchantCenterManager;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.views.LoadingBar;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 商家订单详情基类
 * 
 * @author sunxy
 */
public abstract class BaseMerchantOrderActivity extends BaseActivity implements OnClickListener {
	// 订单ID
	public int orderId = 0;
	// 数据实体
	public OrderEntity entity;
	// 订单状态
	public int orderState = 0;

	public Odm odm;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);
		initData();
		topTitle();
		loadData();
	}

	private int state;
	public GoodsSourceInfo info = null;

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
		} else {
			orderId = Integer.valueOf(getIntent().getStringExtra("temp"));
			orderState = getIntent().getIntExtra("state", 0);
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
				MsgItemUtil.onclickPopItem(state, BaseMerchantOrderActivity.this);
			}
		});
		topTitleView.setEventName("订单详情");
		win_title.addView(topTitleView);
	}

	/**
	 * 从网络加载数据
	 */
	private void loadData() {
		startLoading();

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(getOrderDetailUrl(orderId));
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
				stopLoading();
				finish();
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				// entity =
				// MerchantCenterManager.getProviderOrderDetailsInfo(arg1);

				// 解析数据
				parseDetailDatas(arg1);
				stopLoading();
				if (null != entity || odm != null) {
					initContent();
					// 初始化不一样的界面
					initUnlikeView();
					// 跟新界面
					updateView();
					// 跟新不一样界面
					updateUnlikeView();
					// 添加按钮
					addFootView();
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * 获取订单访问路径
	 * 
	 * @param orderId2
	 * @return
	 */
	public abstract String getOrderDetailUrl(int orderId2);

	/**
	 * 解析数据
	 * 
	 * @param arg1
	 */
	public abstract void parseDetailDatas(Object arg1);

	/**
	 * 更新界面
	 */
	public abstract void updateView();

	/**
	 * 初始化下面不同的view
	 */
	public abstract void initUnlikeView();

	/**
	 * 跟新不一样界面
	 */
	public abstract void updateUnlikeView();

	/**
	 * 初始化内容
	 */
	@SuppressLint("InflateParams")
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this)//
				.inflate(R.layout.activity_pro_intention_order_detail, null);
		win_content.addView(view);

		// 找控件
		findViewById(view);
	}

	// 订单号
	public TextView mTxtOrderNumber;
	// 交易状态
	public TextView mTxtTradeState;
	// 收货人
	public TextView mTxtConsignee;
	// 收货地址
	public TextView mTxtAcceptAddress;
	// 发票类型
	public TextView mTxtBillType;
	// 发票抬头
	public TextView mTxtBillRise;
	// 发票内容
	public TextView mTxtBillContent;
	// 意向编号
	public TextView mTxtIntentionNum;
	// 地区
	public TextView mTxtOrderEare;
	// 联系人
	public TextView mTxtTelPhone;

	// 进入意向详情
	private LinearLayout mLlIntentionDetail;

	private void findViewById(View view) {
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

		// 联系人
		mTxtTelPhone = (TextView) view.findViewById(R.id.tv_tel_phone);

		mLlIntentionDetail = (LinearLayout) view.findViewById(R.id.lin_num);
		mLlIntentionDetail.setOnClickListener(this);
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

	private View view;

	public void addFootView() {
		view = ContextUtil.getLayoutInflater(this)//
				.inflate(R.layout.activity_purchase_intention_foot_copy, null);
		win_content.addView(view);
		
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

		if (null != entity) {
			if (entity.getBinaryvalue().contains("1")) {
//				win_content.addView(view);
			}
			orderState(entity.getBinaryvalue());
		}
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
			goIntentionDetail();
			break;
		}
	}

	/**
	 * 进入意向详情
	 */
	public abstract void goIntentionDetail();

	/**
	 * 拒绝退款
	 */
	private void refuseMoney() {
		orderOpration(providerOrderRefuseMoneyUrl(orderId));
	}

	/**
	 * 删除
	 */
	private void delete() {
		orderOpration(providerOrderdeleteUrl(orderId));

	}

	/**
	 * 确认收货
	 */
	private void sureMoney() {
		orderOpration(providerOrderSureMoneyUrl(orderId));
	}

	/**
	 * 同意退款
	 */
	private void agreenRefund() {
		orderOpration(providerOrderAgreenRefundUrl(orderId));
	}

	/**
	 * 拒绝订单
	 */
	private void refuseOrder() {
		orderOpration(providerOrderRefuseOrderUrl(orderId));
	}

	/**
	 * 发货
	 */
	private void sendGoodS() {
		orderOpration(providerOrderSendGoodSUrl(orderId));
	}

	/**
	 * 拒绝退款
	 * 
	 * @param orderId
	 * @return
	 */
	public abstract String providerOrderRefuseMoneyUrl(int orderId);

	/**
	 * 删除
	 */
	public abstract String providerOrderdeleteUrl(int orderId);

	/**
	 * 确认收货
	 */
	public abstract String providerOrderSureMoneyUrl(int orderId);

	/**
	 * 同意退款
	 */
	public abstract String providerOrderAgreenRefundUrl(int orderId);

	/**
	 * 拒绝订单
	 */
	public abstract String providerOrderRefuseOrderUrl(int orderId);

	/**
	 * 发货
	 */
	public abstract String providerOrderSendGoodSUrl(int orderId);

	/**
	 * 发货、拒绝订单、同意退款共同请求网络的方法
	 * 
	 * @param url
	 */
	public void orderOpration(String url) {
		startLoading();

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(url);
		loader.setPostData(new JSONObject().toString());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.addRequestHeader("token", CmallApplication.getUserInfo().getToken());
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
				loadingBar.setProgressInfo("正在解析");
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				displayToast(arg2);
				stopLoading();
			}

			@SuppressWarnings("unused")
			@Override
			public void onCompelete(Object arg0, Object arg1) {
				GoodsSourceInfo sourceInfo = MerchantCenterManager.getProviderOrderSendGoodSInfo(arg1);
				stopLoading();
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

	private LoadingBar loadingBar;

	public void startLoading() {
		loadingBar = new LoadingBar(this);
		loadingBar.start();
	}

	public void stopLoading() {
		loadingBar.stop();
	}
}
