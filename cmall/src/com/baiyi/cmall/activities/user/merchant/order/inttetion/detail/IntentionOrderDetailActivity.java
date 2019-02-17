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
 * ���򶩵�����
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
		Log.d("TAG", "�������� ��\n" + arg1);
		entity = MerchantOrderManager.parseIntebtionDetail(arg1);
	}

	@Override
	public void updateView() {
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
			// ����
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
	private void findUnlikeViewById(View view) {

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

		mTxtPurCompanyNamy = (TextView) view.findViewById(R.id.pur_company_name);
		mTxtPurCompanyNamy.setText("���:");

		// mLlEntryIntentionDetail = (LinearLayout)
		// view.findViewById(R.id.lin_num);
		// mLlEntryIntentionDetail.setOnClickListener(this);

		// �ܼ۸�
		mTvAllPrice = (TextView) view.findViewById(R.id.tv_all_price);
	}

	@Override
	public void updateUnlikeView() {
		if (null != entity) {
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

			/*
			 * �ܶ�= ����*����
			 */
			String allMoney = (Double.parseDouble(entity.getPrice()) * Long.parseLong(entity.getInventory())) + "";
			// �ܼ۸�
			mTvAllPrice.setText(Utils.twoDecimals(allMoney) + "Ԫ");
		}
	}

	/**
	 * �ܾ��˿�
	 * 
	 * @param orderId
	 * @return
	 */
	@Override
	public String providerOrderRefuseMoneyUrl(int orderId) {
		return AppNetUrl.getProviderOrderRefuseMoneyUrl(orderId);
	}

	/**
	 * ɾ��
	 */
	@Override
	public String providerOrderdeleteUrl(int orderId) {
		return AppNetUrl.getProviderOrderdeleteUrl(orderId);
	}

	/**
	 * ȷ���ջ�
	 */
	@Override
	public String providerOrderSureMoneyUrl(int orderId) {
		return AppNetUrl.getProviderOrderSureMoneyUrl(orderId);
	}

	/**
	 * ͬ���˿�
	 */
	@Override
	public String providerOrderAgreenRefundUrl(int orderId) {
		return AppNetUrl.getProviderOrderAgreenRefundUrl(orderId);
	}

	/**
	 * �ܾ�����
	 */
	@Override
	public String providerOrderRefuseOrderUrl(int orderId) {
		return AppNetUrl.getProviderOrderRefuseOrderUrl(orderId);
	}

	/**
	 * ����
	 */
	@Override
	public String providerOrderSendGoodSUrl(int orderId) {
		return AppNetUrl.getProviderOrderSendGoodSUrl(orderId);
	}

	/**
	 * ������������
	 */
	@Override
	public void goIntentionDetail() {
		if (null == entity) {
			displayToast("�������������Ϊ��");
			return;
		}
		goActivity(entity.getIntentionid(), ProviderIntentionOrderActivity.class, 9);
	}

}
