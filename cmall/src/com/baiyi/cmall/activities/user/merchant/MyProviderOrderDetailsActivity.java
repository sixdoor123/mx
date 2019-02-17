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
 * ���ǹ�Ӧ��-�ҵĶ���-���򶩵�����
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-19 ����4:07:42
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

	// ����
	private TextView mBtnSendGoodS;
	// �ܾ�����
	private TextView mBtnRefuseOrder;
	// ͬ���˿�(��ȷ���˿�)
	private TextView mBtnAgreenRefund;
	// ȷ���տť
	private TextView mBtnSureMoney;
	// ɾ��
	private TextView mBtnDelete;
	// �ܾ��˿�
	private TextView mBtnRefuseMoney;

	// ��ȷ���˿�
	private TextView mBtnRefundAreadySure;

	// ����ʱ������״̬
	private int orderState;
	private View view;

	/**
	 * ��ʼ����ť
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

		// mBtnRefuseOrder.setText("�ܾ�����");
		mBtnSendGoodS.setText("ȷ�Ϸ���");
		mBtnRefundAreadySure.setText("��ȷ���˿�");
		mBtnRefuseMoney.setText("�ܾ��˿�");
		mBtnAgreenRefund.setText("ͬ���˿�");
		mBtnSureMoney.setText("ȷ���տ�");
		mBtnDelete.setText("ɾ��");

		orderState(entity.getBinaryvalue());
	}

	/**
	 * ����״̬���ж�
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
			// �ж��Ƿ񷵻�
			// if (IntentionOrderUtils.isFinish(status)) {
			// Intent intent = new Intent();
			// setResult(7, intent);
			// finish();
			// }

			// �ж��Ƿ��а�ť��ʾ
			if (IntentionOrderUtils.isAddView(status)) {
				win_content.removeView(view);
				win_content.addView(view);
			}
		}
	}

	// �Զ������
	private EventTopTitleView topTitleView;

	/**
	 * ����
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
		topTitleView.setEventName("��������");
		win_title.addView(topTitleView);
	}

	// ����Դ
	private GoodsSourceInfo info;
	// ����ID
	private int orderId;

	private int state;

	/**
	 * ��ȡ����
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

	// ��������ʵ����
	private OrderEntity entity;

	/**
	 * �������������
	 */
	private void loadData() {
		loadingBar.setVisibility(View.VISIBLE);
		loadingBar.setProgressInfo("���ڼ�����...");
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
	 * ��ʼ������
	 */
	@SuppressLint("InflateParams")
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_pur_intention_order_detail, null);
		win_content.addView(view);

		// �ҿؼ�
		findViewById(view);

	}

	// ��ʾ����
	private MyLoadingBar loadingBar;
	// ������
	private TextView mTxtOrderNumber;
	// ����״̬
	private TextView mTxtTradeState;
	// �ջ���
	private TextView mTxtConsignee;
	// �ջ���ַ
	private TextView mTxtAcceptAddress;
	// ��Ʊ����
	private TextView mTxtBillType;
	// ��Ʊ̧ͷ
	private TextView mTxtBillRise;
	// ��Ʊ����
	private TextView mTxtBillContent;
	// ������
	private TextView mTxtIntentionNum;
	// ����
	private TextView mTxtOrderEare;

	// �̼�
	private TextView mTxtMerchant;
	// ����
	private TextView mTxtCategory;
	// Ʒ��
	private TextView mTxtBrandName;
	// ����
	private TextView mTxtWeight;
	// Ԥ��
	private TextView mTxtPrepayment;
	// �۸�
	private TextView mTxtTotalPrice;
	// ����״̬
	private TextView mTxtOrderState;

	// ��ϵ��
	private TextView mTxtTelPhone;

	// �ɹ���
	private TextView mTxtPurCompanyNamy;

	// ���ƽ��������������
	private LinearLayout mLlEntryIntentionDetail;

	// �ܽ��
	private TextView mTvAllPrice;

	/**
	 * �ҿؼ�
	 * 
	 * @param view
	 */
	private void findViewById(View view) {
		loadingBar = (MyLoadingBar) view.findViewById(R.id.load);
		// ������
		mTxtOrderNumber = (TextView) view.findViewById(R.id.tv_order_id);
		// ����״̬
		mTxtTradeState = (TextView) view.findViewById(R.id.tv_warm_info);
		// �ջ���
		mTxtConsignee = (TextView) view.findViewById(R.id.tv_get_goods_person);
		// �ջ���ַ
		mTxtAcceptAddress = (TextView) view.findViewById(R.id.tv_get_goods_address);
		// ��Ʊ����
		mTxtBillType = (TextView) view.findViewById(R.id.tv_invoice_type);
		// ��Ʊ̧ͷ
		mTxtBillRise = (TextView) view.findViewById(R.id.tv_invoice_title);
		// ��Ʊ����
		mTxtBillContent = (TextView) view.findViewById(R.id.tv_invoice_content);
		// ������
		mTxtIntentionNum = (TextView) view.findViewById(R.id.tv_attention_id);
		// ����
				mTxtOrderEare = (TextView) view.findViewById(R.id.tv_get_goods_city);
		
		
		// �̼�
		mTxtMerchant = (TextView) view.findViewById(R.id.tv_merchact_name);
		// ����
		mTxtCategory = (TextView) view.findViewById(R.id.tv_category);
		// Ʒ��
		mTxtBrandName = (TextView) view.findViewById(R.id.tv_brand);
		// ����
		mTxtWeight = (TextView) view.findViewById(R.id.tv_weight);
		// Ԥ��
		mTxtPrepayment = (TextView) view.findViewById(R.id.tv_pre_price);
		// �۸�
		mTxtTotalPrice = (TextView) view.findViewById(R.id.tv_price);
		// ����״̬
		mTxtOrderState = (TextView) view.findViewById(R.id.tv_state);
		// ��ϵ��
		mTxtTelPhone = (TextView) view.findViewById(R.id.tv_tel_phone);
		

		mTxtPurCompanyNamy = (TextView) view.findViewById(R.id.pur_company_name);
		mTxtPurCompanyNamy.setText("���:");

		mLlEntryIntentionDetail = (LinearLayout) view.findViewById(R.id.lin_num);
		mLlEntryIntentionDetail.setOnClickListener(this);

		// �ܼ۸�
		mTvAllPrice = (TextView) view.findViewById(R.id.tv_all_price);

	}

	/**
	 * ���Ľ�������
	 */
	private void updateViewData() {
		if (null != entity) {
			// ������
			mTxtOrderNumber.setText(String.format(getResources().getString(R.string.form_num), entity.getId()));
			// ����״̬
			mTxtTradeState.setText(info.getIntentionOrderState());
			// �ջ���
			mTxtConsignee.setText(entity.getReceivername());

			// �ջ���ַ
			mTxtAcceptAddress.setText(entity.getAddress());
			// ��Ʊ����
			mTxtBillType.setText(entity.getInvoicetypename());
			// ��Ʊ̧ͷ
			mTxtBillRise.setText(entity.getTitle());
			// ��Ʊ����
			mTxtBillContent.setText(entity.getContext());
			// ������
			mTxtIntentionNum.setText(entity.getIntentionid());
			// ����״̬
						mTxtOrderState.setText(info.getIntentionOrderState());
						// ����
									mTxtOrderEare.setText(entity.getEare());
									
			// �̼�
			mTxtMerchant.setText(entity.getCompanyname().equals("null") ? "����" : entity.getCompanyname());
			// ����
			mTxtCategory.setText(entity.getCategoryName());
			// Ʒ��
			mTxtBrandName.setText(entity.getBrandname());
			// ����
			mTxtWeight.setText(entity.getInventory() + "��");
			// Ԥ��
			String prepayment = Utils.twoDecimals(entity.getPrepayment());
			if (prepayment.startsWith(".")) {
				prepayment = "0" + prepayment;
			}
			mTxtPrepayment.setText(prepayment + "Ԫ");
			// �۸�
			String price = Utils.twoDecimals(entity.getPrice());
			if (price.startsWith(".")) {
				price = "0" + price;
			}
			mTxtTotalPrice.setText(price + "Ԫ/��");
			
			mTxtTelPhone.setText(entity.getPhone());

			/*
			 * �ܶ�= ����*����
			 */
			String allMoney = (Double.parseDouble(entity.getPrice()) * Long.parseLong(entity.getInventory())) + "";
			// �ܼ۸�
			mTvAllPrice.setText(Utils.twoDecimals(allMoney) + "Ԫ");
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_sure_purchase_order_copy:// ����
			sendGoodS();
			break;
		case R.id.btn_cancel_purchase_order_copy:// �ܾ��˿�
			refuseMoney();
			break;
		case R.id.btn_commit_purchase_order_copy:// ͬ���˿�
			agreenRefund();
			break;
		case R.id.btn_cancel_modify:// ȷ���տ�
			sureMoney();
			break;
		case R.id.btn_commit_modify:// ɾ��
			delete();
			break;
		case R.id.btn_cancel:// �ܾ�����
			refuseOrder();
			break;
		case R.id.lin_num:// ��������
			if (null == entity) {
				displayToast("�������������Ϊ��");
				return;
			}
			goActivity(entity.getIntentionid(), ProviderIntentionOrderActivity.class, 9);
			break;
		}
	}

	/**
	 * �ܾ��˿�
	 */
	private void refuseMoney() {
		orderOpration(AppNetUrl.getProviderOrderRefuseMoneyUrl(orderId));
	}

	/**
	 * ɾ��
	 */
	private void delete() {
		orderOpration(AppNetUrl.getProviderOrderdeleteUrl(orderId));

	}

	/**
	 * ȷ���ջ�
	 */
	private void sureMoney() {
		orderOpration(AppNetUrl.getProviderOrderSureMoneyUrl(orderId));
	}

	/**
	 * ͬ���˿�
	 */
	private void agreenRefund() {
		orderOpration(AppNetUrl.getProviderOrderAgreenRefundUrl(orderId));
	}

	/**
	 * �ܾ�����
	 */
	private void refuseOrder() {
		orderOpration(AppNetUrl.getProviderOrderRefuseOrderUrl(orderId));
	}

	/**
	 * ����
	 */
	private void sendGoodS() {
		orderOpration(AppNetUrl.getProviderOrderSendGoodSUrl(orderId));
	}

	/**
	 * �������ܾ�������ͬ���˿ͬ��������ķ���
	 * 
	 * @param url
	 */
	private void orderOpration(String url) {
		loadingBar.setVisibility(View.VISIBLE);
		loadingBar.setProgressInfo("���ڼ�����...");
		loadingBar.start();

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(url);
		loader.setPostData(new JSONObject().toString());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.addRequestHeader("token", token);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
				loadingBar.setProgressInfo("���ڽ���");
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
