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
 * �̼Ҷ����������
 * 
 * @author sunxy
 */
public abstract class BaseMerchantOrderActivity extends BaseActivity implements OnClickListener {
	// ����ID
	public int orderId = 0;
	// ����ʵ��
	public OrderEntity entity;
	// ����״̬
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
	 * ��ȡ����
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
				MsgItemUtil.onclickPopItem(state, BaseMerchantOrderActivity.this);
			}
		});
		topTitleView.setEventName("��������");
		win_title.addView(topTitleView);
	}

	/**
	 * �������������
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

				// ��������
				parseDetailDatas(arg1);
				stopLoading();
				if (null != entity || odm != null) {
					initContent();
					// ��ʼ����һ���Ľ���
					initUnlikeView();
					// ���½���
					updateView();
					// ���²�һ������
					updateUnlikeView();
					// ��Ӱ�ť
					addFootView();
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * ��ȡ��������·��
	 * 
	 * @param orderId2
	 * @return
	 */
	public abstract String getOrderDetailUrl(int orderId2);

	/**
	 * ��������
	 * 
	 * @param arg1
	 */
	public abstract void parseDetailDatas(Object arg1);

	/**
	 * ���½���
	 */
	public abstract void updateView();

	/**
	 * ��ʼ�����治ͬ��view
	 */
	public abstract void initUnlikeView();

	/**
	 * ���²�һ������
	 */
	public abstract void updateUnlikeView();

	/**
	 * ��ʼ������
	 */
	@SuppressLint("InflateParams")
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this)//
				.inflate(R.layout.activity_pro_intention_order_detail, null);
		win_content.addView(view);

		// �ҿؼ�
		findViewById(view);
	}

	// ������
	public TextView mTxtOrderNumber;
	// ����״̬
	public TextView mTxtTradeState;
	// �ջ���
	public TextView mTxtConsignee;
	// �ջ���ַ
	public TextView mTxtAcceptAddress;
	// ��Ʊ����
	public TextView mTxtBillType;
	// ��Ʊ̧ͷ
	public TextView mTxtBillRise;
	// ��Ʊ����
	public TextView mTxtBillContent;
	// ������
	public TextView mTxtIntentionNum;
	// ����
	public TextView mTxtOrderEare;
	// ��ϵ��
	public TextView mTxtTelPhone;

	// ������������
	private LinearLayout mLlIntentionDetail;

	private void findViewById(View view) {
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

		// ��ϵ��
		mTxtTelPhone = (TextView) view.findViewById(R.id.tv_tel_phone);

		mLlIntentionDetail = (LinearLayout) view.findViewById(R.id.lin_num);
		mLlIntentionDetail.setOnClickListener(this);
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

		// mBtnRefuseOrder.setText("�ܾ�����");
		mBtnSendGoodS.setText("ȷ�Ϸ���");
		mBtnRefundAreadySure.setText("��ȷ���˿�");
		mBtnRefuseMoney.setText("�ܾ��˿�");
		mBtnAgreenRefund.setText("ͬ���˿�");
		mBtnSureMoney.setText("ȷ���տ�");
		mBtnDelete.setText("ɾ��");

		if (null != entity) {
			if (entity.getBinaryvalue().contains("1")) {
//				win_content.addView(view);
			}
			orderState(entity.getBinaryvalue());
		}
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
			goIntentionDetail();
			break;
		}
	}

	/**
	 * ������������
	 */
	public abstract void goIntentionDetail();

	/**
	 * �ܾ��˿�
	 */
	private void refuseMoney() {
		orderOpration(providerOrderRefuseMoneyUrl(orderId));
	}

	/**
	 * ɾ��
	 */
	private void delete() {
		orderOpration(providerOrderdeleteUrl(orderId));

	}

	/**
	 * ȷ���ջ�
	 */
	private void sureMoney() {
		orderOpration(providerOrderSureMoneyUrl(orderId));
	}

	/**
	 * ͬ���˿�
	 */
	private void agreenRefund() {
		orderOpration(providerOrderAgreenRefundUrl(orderId));
	}

	/**
	 * �ܾ�����
	 */
	private void refuseOrder() {
		orderOpration(providerOrderRefuseOrderUrl(orderId));
	}

	/**
	 * ����
	 */
	private void sendGoodS() {
		orderOpration(providerOrderSendGoodSUrl(orderId));
	}

	/**
	 * �ܾ��˿�
	 * 
	 * @param orderId
	 * @return
	 */
	public abstract String providerOrderRefuseMoneyUrl(int orderId);

	/**
	 * ɾ��
	 */
	public abstract String providerOrderdeleteUrl(int orderId);

	/**
	 * ȷ���ջ�
	 */
	public abstract String providerOrderSureMoneyUrl(int orderId);

	/**
	 * ͬ���˿�
	 */
	public abstract String providerOrderAgreenRefundUrl(int orderId);

	/**
	 * �ܾ�����
	 */
	public abstract String providerOrderRefuseOrderUrl(int orderId);

	/**
	 * ����
	 */
	public abstract String providerOrderSendGoodSUrl(int orderId);

	/**
	 * �������ܾ�������ͬ���˿ͬ��������ķ���
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
				loadingBar.setProgressInfo("���ڽ���");
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
