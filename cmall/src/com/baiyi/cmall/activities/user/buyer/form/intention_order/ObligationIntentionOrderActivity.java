package com.baiyi.cmall.activities.user.buyer.form.intention_order;

import org.json.JSONArray;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.user.buyer.form.PayActivity;
import com.baiyi.cmall.activities.user.buyer.intention.ConsigneeAddressActivity;
import com.baiyi.cmall.activities.user.buyer.intention.InVoiceSelectActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.OrderEntity;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.request.manager.MyPurFormManager;
import com.baiyi.cmall.request.manager.NullJsonData;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.JsonParse_User;

/**
 * �����������
 * 
 * @author Administrator
 * 
 */
public class ObligationIntentionOrderActivity extends
		BasePurIntentionOrderDetailActivity implements OnClickListener {

	// ȡ������
	private TextView mBtnCancelForm;
	// �ύ����
	private TextView mBtnCommitForm;
	// �ջ��˲���
	private LinearLayout mLinConsigneeLayout;
	// ��Ʊ����
	private LinearLayout mLinInvoiceLayout;

	private ImageView mImgConsigneeImageView;
	private ImageView mImgInvoiceImageView;

	// �ջ���
	private String receiverName;
	// �ջ��˳���ID
	private String receiverCityId;
	// �ջ��˳���
	private String receiverCityName;
	// �ջ��˵�ַ
	private String reciverAddress;
	// ��؛���Ԓ
	private String receiverPhone;
	// ��Ʊ��������ID
	private String invoiceTypeId;
	// ��Ʊ����
	private String invoiceType;
	// ��Ʊ̧ͷ
	private String invoiceTitle;
	// ��Ʊ����
	private String invoiceContext;

	@Override
	public String getStateReminder() {
		// TODO Auto-generated method stub

		return getString(R.string.dai_fukuan_hint);
	}

	@Override
	public int getImage() {
		// TODO Auto-generated method stub
		return R.drawable.icon_payment;
	}

	@Override
	public void addFoot() {

		// ------------------------------------------------------------
		mLinConsigneeLayout = (LinearLayout) findViewById(R.id.lin_form_consignee);
		mLinInvoiceLayout = (LinearLayout) findViewById(R.id.lin_form_invoice);
		mLinConsigneeLayout.setOnClickListener(this);
		mLinInvoiceLayout.setOnClickListener(this);
		mImgConsigneeImageView = (ImageView) findViewById(R.id.img_consignee_right);
		mImgInvoiceImageView = (ImageView) findViewById(R.id.img_invoice_right);
		mImgConsigneeImageView.setVisibility(View.VISIBLE);
		mImgInvoiceImageView.setVisibility(View.VISIBLE);

		setZhiFuData();

		/*
		 * ���Ը���ɹ���ʧ��
		 */
		ContextUtil.getLayoutInflater(this).inflate(R.layout.test, win_content);
		TextView mBtOk = (TextView) findViewById(R.id.btn_ok);
		TextView mBtNo = (TextView) findViewById(R.id.btn_no);
		mBtOk.setOnClickListener(this);
		mBtNo.setOnClickListener(this);

		// -------------------------------------------------------------

		ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_form_foot, win_content);

		mBtnCommitForm = (TextView) findViewById(R.id.btn_commit);
		mBtnCancelForm = (TextView) findViewById(R.id.btn_cancel_form);
		mBtnCommitForm.setText("ȥ֧��");
		mBtnCancelForm.setText("ȡ������");
		mBtnCommitForm.setOnClickListener(this);
		mBtnCancelForm.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_commit:// ֧��
			zhiFu();
			break;
		case R.id.btn_cancel_form:// ȡ������
			formOpration(AppNetUrl.getIntentionCancelOrderUrl(orderID));
			break;
		case R.id.btn_ok:// ����ɹ�
			formOpration(AppNetUrl.getPaymentSuccessUrl(orderID));
			break;
		case R.id.btn_no:// ����ʧ��
			formOpration(AppNetUrl.getPaymentFailUrl(orderID));
			break;
		case R.id.lin_form_consignee:// �ջ��˸���
			Intent intent1 = new Intent(this, ConsigneeAddressActivity.class);
			startActivityForResult(intent1, NumEntity.RESULT_CONSIGNEE);
			break;
		case R.id.lin_form_invoice:// ��Ʊ����

			Intent intent2 = new Intent(this, InVoiceSelectActivity.class);
			startActivityForResult(intent2, NumEntity.RESULT_INVOICE);
			break;
		}
	}

	/**
	 * �����ջ�����Ϣ
	 */
	private void setConsigneeData() {

		// �л��ջ���ַ�������µ��ջ���ID
		mTvConsignee.setText(receiverName);
		mTvPhone.setText(receiverPhone);
		mTvAcceptCity.setText(receiverCityName);
		mTvAcceptAddress.setText(reciverAddress);
	}

	/**
	 * ���÷�Ʊ��Ϣ
	 */
	private void setInvoiceData() {

		// �л��ջ���ַ�������µ��ջ���ID
		mTvBillContent.setText(invoiceContext);
		mTvBillRise.setText(invoiceTitle);
		mTvBillType.setText(invoiceType);
	}

	/**
	 * ����֧������
	 */
	private void setZhiFuData() {

		// �ջ���
		receiverName = entity.getReceivername();
		// �ջ��˳���ID
		receiverCityId = entity.getOrderCityId();
		// �ջ��˳���
		receiverCityName = entity.getOrderCity();
		// �ջ��˵�ַ
		reciverAddress = entity.getAddress();
		// ��؛���Ԓ
		receiverPhone = entity.getPhone();
		// ��Ʊ��������ID
		invoiceTypeId = entity.getInvoicetypeId();
		// ��Ʊ����
		invoiceType = entity.getInvoicetypename();
		// ��Ʊ̧ͷ
		invoiceTitle = entity.getTitle();
		// ��Ʊ����
		invoiceContext = entity.getContext();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {

		case NumEntity.RESULT_CONSIGNEE:// �ջ�����Ϣ���淵����Ϣ
			OrderEntity consigneeEntity = (OrderEntity) data
					.getSerializableExtra("info");

			receiverName = consigneeEntity.getReceivername();
			if (TextUtils.isEmpty(receiverName)) {
				return;
			}
			receiverCityId = consigneeEntity.getOrderCityId();
			receiverCityName = consigneeEntity.getOrderCity();
			reciverAddress = consigneeEntity.getAddress();
			receiverPhone = consigneeEntity.getPhone();
			setConsigneeData();
			break;
		case NumEntity.RESULT_INVOICE:// ��Ʊ��Ϣ���淵����Ϣ
			OrderEntity invoiceEntity = (OrderEntity) data
					.getSerializableExtra("info");

			invoiceTypeId = invoiceEntity.getInvoicetypeId();
			if (TextUtils.isEmpty(invoiceTypeId)) {
				return;
			}
			invoiceType = invoiceEntity.getInvoicetypename();
			invoiceTitle = invoiceEntity.getTitle();
			invoiceContext = invoiceEntity.getContext();
			setInvoiceData();
			break;
		}

	}

	/**
	 * �M��֧������
	 */
	public void zhiFu() {

		JsonLoader jsonLoader = new JsonLoader(this);
		jsonLoader.setUrl(AppNetUrl.getIntentionZhiFuURL());
		jsonLoader.setPostData(MyPurFormManager.getZhiFuData(orderID,
				receiverName, receiverCityId, receiverCityName, reciverAddress,
				receiverPhone, invoiceTypeId, invoiceType, invoiceTitle,
				invoiceContext));
		jsonLoader.addRequestHeader("token", CmallApplication.getUserInfo()
				.getToken());
		jsonLoader.setMethod(BaseNetLoder.Method_Post);
		jsonLoader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		jsonLoader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				displayToast(errorMessage);
			}

			@Override
			public void onCompelete(Object arg0, Object result) {
				JSONArray array = (JSONArray) result;

				OrderEntity entity = MyPurFormManager
						.getMyPurIntentionFormDetail(array);

				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);
				if (1 == info.getStatus()) {

					Intent intent = new Intent(
							ObligationIntentionOrderActivity.this,
							PayActivity.class);
					intent.putExtra("id", orderID);
					intent.putExtra("tag", entity);
					startActivity(intent);
					finish();
				} else {
					displayToast(info.getMsg());
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);

	}
}
