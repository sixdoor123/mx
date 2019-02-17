package com.baiyi.cmall.activities.user.buyer.intention;

import org.json.JSONArray;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.user.buyer.form.TiJiaoOkDetailActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.OrderEntity;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.request.manager.MyPurAttentionManager;
import com.baiyi.cmall.request.manager.MyPurFormManager;
import com.baiyi.cmall.request.manager.NullJsonData;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.JsonParse_User;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * ���ҵĲɹ�-�����Ĳɹ�-����Ŀ���-�ɹ����򵥣����Ñ��µ�����ת���˽���
 * 
 * @author lizl
 * 
 */
public class PurchaseOrderDetailActivity extends BaseActivity implements
		OnClickListener {

	// ѡ���ջ�����Ϣ
	private LinearLayout mLinConsignee;
	// �ջ�����Ϣ
	private LinearLayout mLinConsigneeHide;
	// ���ջ�����Ϣʱ��ʾ��
	private TextView mTvConsigneeHite;
	// �ܲ���
	private LinearLayout layout;
	/*
	 * �ջ�����Ϣ
	 */
	private TextView mTvReceivePerson;
	private TextView mTvReceivePersonPhone;
	private TextView mTvReceivePersonAdress;
	private TextView mTvReceiveCity;
	/*
	 * ��Ʊ��Ϣ
	 */
	private TextView mTvInvoiceContent;
	private TextView mTvInvoiceTitle;
	private TextView mTvInvoiceType;
	/*
	 * ��Ʒ��Ϣ
	 */
	private TextView mTvMerchantname;
	private TextView mTvCategoryname;
	private TextView mTvBrandName;
	private TextView mTvKuCun;
	private TextView mTvPrice;
	private TextView mTvPrePrice;
	private TextView mTvAllMoney;

	// ��Ʊ��Ϣ
	private LinearLayout mLinInvoice;
	// ��Ʊ��Ϣ
	private LinearLayout mLinInvoiceHide;
	// �޷�Ʊ��Ϣʱ��ʾ��
	private TextView mTvInvoiceHite;

	// ȡ������
	private TextView mBtnCancelForm;
	// �ύ�µ�
	private TextView mBtnCommitForm;
	// �ɹ�����
	private GoodsSourceInfo purInfo;
	// ����ID
	private int attentionID;
//	private String token;
	// �û�ID
	private String userId;
	// �̼�Id
	private String companyId;

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

	// �����ɹ���������
	private OrderEntity orderEntity;

	@Override
	protected void initWin(boolean hasScrollView) {
		super.initWin(hasScrollView);
		initTitle();
		initContent();
		initId();
		initData();
	}

	/**
	 * Token���û�ID��ȡ
	 */
	private void initId() {

		attentionID = getIntent().getIntExtra("temp", 0);
		userId = CmallApplication.getUserInfo().getUserID();
	}

	/**
	 * �µ���Ķ�������
	 */
	private void initData() {
		loaderProgress();
		JsonLoader jsonLoader = new JsonLoader(this);
		jsonLoader.setUrl(AppNetUrl.getMyPurXiaOrderUrl(attentionID, userId));
		jsonLoader.addRequestHeader("token", token);
		jsonLoader.setPostData(NullJsonData.getPostData());
		jsonLoader.setMethod(BaseNetLoder.Method_Post);
		jsonLoader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				displayToast(errorMessage);
				finish();
			}

			@Override
			public void onCompelete(Object arg0, Object result) {
				JSONArray array = (JSONArray) result;

				purInfo = MyPurAttentionManager.getMyPurXiaOrderData(array);

				// ���������ؽ��
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);

				// ���ɹ�ʱ������������ʾ����
				if (1 == info.getStatus()) {

					stopProgress();
					// ��ȡ���ݳɹ�����������
					setData();
					layout.setVisibility(View.VISIBLE);

				} else {
					displayToast(info.getMsg());
					finish();
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);
	}

	/**
	 * ������
	 */
	public void initTitle() {
		EventTopTitleView topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("��������");
		win_title.addView(topTitleView);
	}

	/**
	 * ���ý��������
	 */
	private void initContent() {

		ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_purchase_order_detail, win_content);
		// �������û�����ݣ��Ȳ���ʾ�������ݣ�ֻ���ؽ�����
		layout = (LinearLayout) findViewById(R.id.lin_all_data);
		layout.setVisibility(View.GONE);
		/*
		 * �ջ��ˡ��ջ��˵绰���ջ��˵�ַ���ջ��˳���
		 */
		mTvReceivePerson = (TextView) findViewById(R.id.tv_receive_person);
		mTvReceivePersonPhone = (TextView) findViewById(R.id.tv_tel_phone);
		mTvReceivePersonAdress = (TextView) findViewById(R.id.tv_goods_address);
		mTvReceiveCity = (TextView) findViewById(R.id.tv_city);
		/*
		 * ��Ʊ���ݡ���Ʊ̧ͷ����Ʊ����
		 */
		mTvInvoiceContent = (TextView) findViewById(R.id.tv_invoice_content);
		mTvInvoiceTitle = (TextView) findViewById(R.id.tv_invoice_title);
		mTvInvoiceType = (TextView) findViewById(R.id.tv_invoice_type);

		/*
		 * ���ࡢƷ�֡��������۸�Ԥ���ѡ��ܶ�
		 */

		mTvMerchantname = (TextView) findViewById(R.id.tv_merchant_name);
		mTvCategoryname = (TextView) findViewById(R.id.tv_category_name);
		mTvBrandName = (TextView) findViewById(R.id.tv_brand_name);
		mTvKuCun = (TextView) findViewById(R.id.tv_weight);
		mTvPrice = (TextView) findViewById(R.id.tv_price);
		mTvPrePrice = (TextView) findViewById(R.id.tv_pre_price);
		mTvAllMoney = (TextView) findViewById(R.id.tv_all_money);

		mLinConsignee = (LinearLayout) findViewById(R.id.lin_consignee);
		mLinConsigneeHide = (LinearLayout) findViewById(R.id.lin_consignee_hide);
		mTvConsigneeHite = (TextView) findViewById(R.id.tv_new_consignee);

		mLinInvoice = (LinearLayout) findViewById(R.id.lin_invoice);
		mLinInvoiceHide = (LinearLayout) findViewById(R.id.lin_invoice_hide);
		mTvInvoiceHite = (TextView) findViewById(R.id.tv_new_invoice);

		mLinConsignee.setOnClickListener(this);
		mLinInvoice.setOnClickListener(this);
		mBtnCommitForm = (TextView) findViewById(R.id.btn_commit);
		mBtnCancelForm = (TextView) findViewById(R.id.btn_cancel_form);
		mBtnCommitForm.setText("�ύ����");
		mBtnCancelForm.setText("����");
		mBtnCommitForm.setOnClickListener(this);
		mBtnCancelForm.setOnClickListener(this);

	}

	/**
	 * �״�����������
	 */
	private String allMoney;

	private void setData() {

		/*
		 * �ջ���
		 */
		receiverName = purInfo.getGoodSContactPerson();
		receiverCityId = purInfo.getCityid();
		receiverCityName = purInfo.getCity();
		reciverAddress = purInfo.getGoodSArea();
		receiverPhone = purInfo.getGoodSContactWay();
		setConsigneeData();

		/*
		 * ��Ʊ
		 */
		invoiceTypeId = purInfo.getMoreTypeId();
		invoiceType = purInfo.getMoreType();
		invoiceTitle = purInfo.getGoodSTitle();
		invoiceContext = purInfo.getGoodSContent();
		setInvoiceData();

		/*
		 * �̼�ID
		 */
		companyId = purInfo.getCompanyId();

		/*
		 * �ܶ�= ����*����
		 */
		allMoney = (Double.parseDouble(purInfo.getGoodSPrice()) * Long
				.parseLong(purInfo.getKuCun())) + "";

		mTvInvoiceContent.setText(purInfo.getGoodSContent());
		mTvInvoiceTitle.setText(purInfo.getGoodSTitle());
		mTvInvoiceType.setText(purInfo.getMoreType());
		mTvMerchantname.setText(purInfo.getGoodSMerchant());
		mTvCategoryname.setText(purInfo.getGoodSCategory());
		mTvBrandName.setText(purInfo.getGoodSBrand());
		mTvKuCun.setText(purInfo.getKuCun() + "��");
		mTvPrice.setText(Utils.twoDecimals(purInfo.getGoodSPrice()) + "Ԫ/��");
		mTvPrePrice.setText(Utils.twoDecimals(purInfo.getGoodSPrePrice() + "")
				+ "Ԫ");
		mTvAllMoney.setText(Utils.twoDecimals(allMoney) + "Ԫ");

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.lin_consignee:// ѡ���ջ�����Ϣ
			selectConsigneeAddress();
			break;
		case R.id.lin_invoice:// ��Ʊ��Ϣ
			selcetInVoiceInfo();
			break;
		case R.id.btn_commit:// �ύ����
			commitOrder();
			break;
		case R.id.btn_cancel_form:// ���ض���

			finish();
			break;
		default:
			break;
		}
	}

	/**
	 * ѡ���ջ���ַ��Ϣ
	 */
	private void selectConsigneeAddress() {

		if (isNullConsignee()) {

			Intent intent = new Intent(this, AddNewConsigneeActivity.class);
			intent.putExtra("state", NumEntity.RESULT_CONSIGNEE);
			startActivityForResult(intent, 1);

		} else {

			Intent intent = new Intent(this, ConsigneeAddressActivity.class);
			startActivityForResult(intent, 1);
		}

	}

	/**
	 * ѡ��Ʊ��Ϣ
	 */
	private void selcetInVoiceInfo() {

		if (isNullInvoice()) {

			/*
			 * ����һ��model��Ҫ����ӷ�Ʊ���洫��---��Ʊ���ݺͷ�Ʊ����
			 */
			OrderEntity intentEntity = new OrderEntity();
			intentEntity.setContextList(purInfo.getContextList());
			intentEntity.setTypeList(purInfo.getTypeList());

			Intent intent = new Intent(this, AddInvoiceActivity.class);
			intent.putExtra("info", intentEntity);
			intent.putExtra("state", NumEntity.RESULT_INVOICE);
			startActivityForResult(intent, 2);

		} else {

			Intent intent = new Intent(this, InVoiceSelectActivity.class);
			startActivityForResult(intent, 2);
		}
	}

	/**
	 * �ύ����
	 */
	private void commitOrder() {

		if (TextUtils.isEmpty(receiverName)
				|| TextUtils.isEmpty(receiverCityId)
				|| TextUtils.isEmpty(receiverCityName)
				|| TextUtils.isEmpty(reciverAddress)
				|| TextUtils.isEmpty(receiverPhone)) {

			displayToast("�������ջ�����Ϣ");
			return;
		}
		if (TextUtils.isEmpty(invoiceTypeId) || TextUtils.isEmpty(invoiceType)
				|| TextUtils.isEmpty(invoiceTitle)
				|| TextUtils.isEmpty(invoiceContext)) {

			displayToast("�����Ʒ�Ʊ��Ϣ");
			return;
		}

		// TODO �ܽ��/Ԥ���Ѵ�ֵ����ȷ
		getResult(AppNetUrl.getCommitOrderUrl(),
				MyPurAttentionManager.getCommitOrderPostData(receiverName,
						receiverCityId, receiverCityName, reciverAddress,
						receiverPhone, invoiceTypeId, invoiceType,
						invoiceTitle, invoiceContext, attentionID, userId,
						companyId, Utils.getNumberOfString(purInfo
								.getGoodSPrePrice() + ""), allMoney));
	}

	/**
	 * ���ʷ������ķ���
	 * 
	 * @param url
	 * @param postData
	 */
	public void getResult(String url, String postData) {

		loaderProgress();

		JsonLoader jsonLoader = new JsonLoader(this);
		jsonLoader.setUrl(url);
		jsonLoader.setPostData(postData);
		jsonLoader.setMethod(BaseNetLoder.Method_Post);
		jsonLoader.addRequestHeader("token", token);
		jsonLoader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		jsonLoader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				stopProgress();
				displayToast(errorMessage);
			}

			@Override
			public void onCompelete(Object arg0, Object result) {
				JSONArray array = (JSONArray) result;
				// ���������ؽ��
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);

				stopProgress();
				if (1 == info.getStatus()) {

					orderEntity = MyPurFormManager.getMyTiJiaoDetailData(array);

					// TODO��ת��ȥ����
					Intent intent = new Intent(
							PurchaseOrderDetailActivity.this,
							TiJiaoOkDetailActivity.class);
					intent.putExtra("orderEntity", orderEntity);
					startActivity(intent);
					finish();

				} else {
					displayToast(info.getMsg());
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);

	}

	// �ջ�����Ϣʵ����
	private OrderEntity consigneeEntity;
	// ��Ʊ��Ϣʵ����
	private OrderEntity invoiceEntity;

	/**
	 * �����ջ�����Ϣ
	 */
	private void setConsigneeData() {

		if (isNullConsignee()) {
			mLinConsigneeHide.setVisibility(View.GONE);
			mTvConsigneeHite.setVisibility(View.VISIBLE);
			return;
		}

		mLinConsigneeHide.setVisibility(View.VISIBLE);
		mTvConsigneeHite.setVisibility(View.GONE);
		// �л��ջ���ַ�������µ��ջ���ID
		mTvReceivePerson.setText(receiverName);
		mTvReceivePersonPhone.setText(receiverPhone);
		mTvReceivePersonAdress.setText(reciverAddress);
		mTvReceiveCity.setText(receiverCityName);
	}

	/**
	 * ���÷�Ʊ��Ϣ
	 */
	private void setInvoiceData() {

		if (isNullInvoice()) {
			mLinInvoiceHide.setVisibility(View.GONE);
			mTvInvoiceHite.setVisibility(View.VISIBLE);
			return;
		}

		mLinInvoiceHide.setVisibility(View.VISIBLE);
		mTvInvoiceHite.setVisibility(View.GONE);
		// �л��ջ���ַ�������µ��ջ���ID
		mTvInvoiceContent.setText(invoiceContext);
		mTvInvoiceTitle.setText(invoiceTitle);
		mTvInvoiceType.setText(invoiceType);
	}

	/**
	 * �ж���؛����Ϣ�Ƿ�Ϊ��
	 * 
	 * @return
	 */
	private boolean isNullConsignee() {

		if (TextUtils.isEmpty(receiverName)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �жϷ�Ʊ��Ϣ�Ƿ�Ϊ��
	 * 
	 * @return
	 */
	private boolean isNullInvoice() {

		if (TextUtils.isEmpty(invoiceTitle)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {

		case NumEntity.RESULT_CONSIGNEE:// �ջ�����Ϣ���淵����Ϣ
			consigneeEntity = (OrderEntity) data.getSerializableExtra("info");

			receiverName = consigneeEntity.getReceivername();
			receiverCityId = consigneeEntity.getOrderCityId();
			receiverCityName = consigneeEntity.getOrderCity();
			reciverAddress = consigneeEntity.getAddress();
			receiverPhone = consigneeEntity.getPhone();
			setConsigneeData();
			break;
		case NumEntity.RESULT_INVOICE:// ��Ʊ��Ϣ���淵����Ϣ
			invoiceEntity = (OrderEntity) data.getSerializableExtra("info");

			invoiceTypeId = invoiceEntity.getInvoicetypeId();
			invoiceType = invoiceEntity.getInvoicetypename();
			invoiceTitle = invoiceEntity.getTitle();
			invoiceContext = invoiceEntity.getContext();
			setInvoiceData();
			break;
		}

	}
}
