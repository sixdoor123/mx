package com.baiyi.cmall.activities.user.buyer.form;

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
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.user.buyer.intention.ConsigneeAddressActivity;
import com.baiyi.cmall.activities.user.buyer.intention.InVoiceSelectActivity;
import com.baiyi.cmall.activities.user.buyer.intention.PurchaseIntentionOrderActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.OrderEntity;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.request.manager.MyPurFormManager;
import com.baiyi.cmall.request.manager.NullJsonData;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.JsonParse_User;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.itemview.EventTopTitleView;

/**
 * �ҵ����򡪡��µ��ɹ�����
 * 
 * @author lizl
 * 
 */
public class TiJiaoOkDetailActivity extends BaseActivity implements
		OnClickListener {

	// ����ID
	public String orderID;
	// �ܿؼ�
	private View view;
	// ������
	private TextView mTvOrderNumber;
	// ����״̬
	private TextView mTvTradeState;
	// ͼƬ
	private ImageView mImgTu;
	// �ջ��˲���
	private LinearLayout mLinConsigneeLayout;
	// ��Ʊ����
	private LinearLayout mLinInvoiceLayout;
	// �ջ��˲��������С��ͷ
	private ImageView mImgConsigneeImageView;
	// ��Ʊ���������С��ͷ
	private ImageView mImgInvoiceImageView;
	// �ջ���
	private TextView mTvConsignee;
	// �绰
	private TextView mTvPhone;
	// ����
	private TextView mTvAcceptCity;
	// �ջ���ַ
	private TextView mTvAcceptAddress;
	// ��Ʊ����
	private TextView mTvBillType;
	// ��Ʊ̧ͷ
	private TextView mTvBillRise;
	// ��Ʊ����
	private TextView mTvBillContent;
	// ������
	private TextView mTvIntentionNum;
	// ����Ѿ�
	public LinearLayout mLinIntention;
	// �̼�
	private TextView mTvMerchant;
	// ����
	private TextView mTvCategory;
	// Ʒ��
	private TextView mTvBrandName;
	// ����
	private TextView mTvWeight;
	// Ԥ��
	private TextView mTvPrepayment;
	// �۸�
	private TextView mTvPrice;
	// �ܼ۸�
	private TextView mTvAllPrice;
	// ����״̬
	private TextView mTvOrderState;
	// ����ʵ��
	public OrderEntity entity;
	// ���ؼ�
	private LinearLayout mImgBack;

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
	protected void initWin(boolean hasScrollView) {
		super.initWin(true);
		initTitle();
		initData();
		initContent();
		setData();
		addFoot();
	}

	/**
	 * ����
	 */
	private void initTitle() {

		EventTopTitleView topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("��������");
		win_title.addView(topTitleView);
		mImgBack = (LinearLayout) findViewById(R.id.img_back);
		mImgBack.setOnClickListener(this);
	}

	/**
	 * ������������
	 */
	private void initData() {

		entity = (OrderEntity) getIntent().getSerializableExtra("orderEntity");

		orderID = entity.getId();

		setZhiFuData();
	}

	/**
	 * ��ʼ������
	 */
	private void initContent() {
		view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_pur_intention_order_detail, null);

		win_content.addView(view);
		// ������
		mTvOrderNumber = (TextView) view.findViewById(R.id.tv_order_id);
		// ����״̬
		mTvTradeState = (TextView) view.findViewById(R.id.tv_warm_info);
		// ͼƬ
		mImgTu = (ImageView) view.findViewById(R.id.img_tu);
		// �ջ���
		mTvConsignee = (TextView) view.findViewById(R.id.tv_get_goods_person);
		// �绰
		mTvPhone = (TextView) view.findViewById(R.id.tv_tel_phone);
		// ����
		mTvAcceptCity = (TextView) view.findViewById(R.id.tv_get_goods_city);
		// �ջ���ַ
		mTvAcceptAddress = (TextView) view
				.findViewById(R.id.tv_get_goods_address);
		// ��Ʊ����
		mTvBillType = (TextView) view.findViewById(R.id.tv_invoice_type);
		// ��Ʊ̧ͷ
		mTvBillRise = (TextView) view.findViewById(R.id.tv_invoice_title);
		// ��Ʊ����
		mTvBillContent = (TextView) view.findViewById(R.id.tv_invoice_content);
		// ������
		mTvIntentionNum = (TextView) view.findViewById(R.id.tv_attention_id);
		// �������Բ���
		mLinIntention = (LinearLayout) view.findViewById(R.id.lin_num);
		mLinIntention.setOnClickListener(this);
		// �̼�
		mTvMerchant = (TextView) view.findViewById(R.id.tv_merchact_name);
		// ����
		mTvCategory = (TextView) view.findViewById(R.id.tv_category);
		// Ʒ��
		mTvBrandName = (TextView) view.findViewById(R.id.tv_brand);
		// ����
		mTvWeight = (TextView) view.findViewById(R.id.tv_weight);
		// Ԥ��
		mTvPrepayment = (TextView) view.findViewById(R.id.tv_pre_price);
		// �۸�
		mTvPrice = (TextView) view.findViewById(R.id.tv_price);
		// �ܼ۸�
		mTvAllPrice = (TextView) view.findViewById(R.id.tv_all_price);
		// ����״̬
		mTvOrderState = (TextView) view.findViewById(R.id.tv_state);

	}

	// ȡ������
	private TextView mBtnCancelForm;
	// ȥ֧��
	private TextView mBtnCommitForm;

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

	/**
	 * ��������
	 */
	private void setData() {

		// ������
		mTvOrderNumber.setText(String.format(
				getResources().getString(R.string.form_num),
				entity.getOrderNumber()));
		// ����״̬
		mTvTradeState.setText(R.string.dai_fukuan_hint);
		// ͼƬ
		mImgTu.setImageResource(R.drawable.icon_payment);

		// �ջ���
		setConsigneeData();

		// ��Ʊ
		setInvoiceData();

		// ������
		mTvIntentionNum.setText(entity.getIntentionNumber());
		// �̼�
		mTvMerchant.setText(entity.getCompanyname());
		// ����
		mTvCategory.setText(entity.getCategoryName());
		// Ʒ��
		mTvBrandName.setText(entity.getBrandname());
		// ����
		mTvWeight.setText(entity.getInventory() + "��");
		// Ԥ��
		mTvPrepayment.setText(Utils.twoDecimals(entity.getPrepayment()) + "Ԫ");
		// �۸�
		mTvPrice.setText(Utils.twoDecimals(entity.getPrice()) + "Ԫ/��");

		/*
		 * �ܶ�= ����*����
		 */
		String allMoney = (Double.parseDouble(entity.getPrice()) * Long
				.parseLong(entity.getInventory())) + "";
		// �ܼ۸�
		mTvAllPrice.setText(Utils.twoDecimals(allMoney) + "Ԫ");
		// ����״̬
		mTvOrderState.setText("������");
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

	/**
	 * ר�Ż�ȡ���������ص�����
	 * 
	 * @param url
	 *            ��ַ
	 */
	public void formOpration(String url) {

		JsonLoader jsonLoader = new JsonLoader(this);
		jsonLoader.setUrl(url);
		jsonLoader.setPostData(NullJsonData.getPostData());
		jsonLoader.addRequestHeader("token", CmallApplication.getUserInfo()
				.getToken());
		jsonLoader.setMethod(BaseNetLoder.Method_Post);
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
				Log.d("MM", "����������" + array.toString());

				// ���������ؽ��
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);
				displayToast(info.getMsg());

				if (1 == info.getStatus()) {
					finish();
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.lin_num:// �鿴������Ϣ
			goActivity(PurchaseIntentionOrderActivity.class,
					Integer.valueOf(entity.getIntentionNumber()), -1);
			break;
		case R.id.img_back:// ���ؼ�
			finish();
			break;
		case R.id.btn_commit:// ȥ֧��
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
	 * ����֧������
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

				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);
				if (1 == info.getStatus()) {
					Intent intent = new Intent(TiJiaoOkDetailActivity.this,
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
