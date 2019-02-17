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
 * 代付款订单界面
 * 
 * @author Administrator
 * 
 */
public class ObligationIntentionOrderActivity extends
		BasePurIntentionOrderDetailActivity implements OnClickListener {

	// 取消订单
	private TextView mBtnCancelForm;
	// 提交订单
	private TextView mBtnCommitForm;
	// 收货人布局
	private LinearLayout mLinConsigneeLayout;
	// 发票布局
	private LinearLayout mLinInvoiceLayout;

	private ImageView mImgConsigneeImageView;
	private ImageView mImgInvoiceImageView;

	// 收货人
	private String receiverName;
	// 收货人城市ID
	private String receiverCityId;
	// 收货人城市
	private String receiverCityName;
	// 收货人地址
	private String reciverAddress;
	// 收貨人電話
	private String receiverPhone;
	// 发票类型名称ID
	private String invoiceTypeId;
	// 发票类型
	private String invoiceType;
	// 发票抬头
	private String invoiceTitle;
	// 发票内容
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
		 * 测试付款成功与失败
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
		mBtnCommitForm.setText("去支付");
		mBtnCancelForm.setText("取消订单");
		mBtnCommitForm.setOnClickListener(this);
		mBtnCancelForm.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_commit:// 支付
			zhiFu();
			break;
		case R.id.btn_cancel_form:// 取消订单
			formOpration(AppNetUrl.getIntentionCancelOrderUrl(orderID));
			break;
		case R.id.btn_ok:// 付款成功
			formOpration(AppNetUrl.getPaymentSuccessUrl(orderID));
			break;
		case R.id.btn_no:// 付款失败
			formOpration(AppNetUrl.getPaymentFailUrl(orderID));
			break;
		case R.id.lin_form_consignee:// 收货人更换
			Intent intent1 = new Intent(this, ConsigneeAddressActivity.class);
			startActivityForResult(intent1, NumEntity.RESULT_CONSIGNEE);
			break;
		case R.id.lin_form_invoice:// 发票更换

			Intent intent2 = new Intent(this, InVoiceSelectActivity.class);
			startActivityForResult(intent2, NumEntity.RESULT_INVOICE);
			break;
		}
	}

	/**
	 * 设置收货人信息
	 */
	private void setConsigneeData() {

		// 切换收货地址，出现新的收获人ID
		mTvConsignee.setText(receiverName);
		mTvPhone.setText(receiverPhone);
		mTvAcceptCity.setText(receiverCityName);
		mTvAcceptAddress.setText(reciverAddress);
	}

	/**
	 * 设置发票信息
	 */
	private void setInvoiceData() {

		// 切换收货地址，出现新的收获人ID
		mTvBillContent.setText(invoiceContext);
		mTvBillRise.setText(invoiceTitle);
		mTvBillType.setText(invoiceType);
	}

	/**
	 * 设置支付数据
	 */
	private void setZhiFuData() {

		// 收货人
		receiverName = entity.getReceivername();
		// 收货人城市ID
		receiverCityId = entity.getOrderCityId();
		// 收货人城市
		receiverCityName = entity.getOrderCity();
		// 收货人地址
		reciverAddress = entity.getAddress();
		// 收貨人電話
		receiverPhone = entity.getPhone();
		// 发票类型名称ID
		invoiceTypeId = entity.getInvoicetypeId();
		// 发票类型
		invoiceType = entity.getInvoicetypename();
		// 发票抬头
		invoiceTitle = entity.getTitle();
		// 发票内容
		invoiceContext = entity.getContext();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {

		case NumEntity.RESULT_CONSIGNEE:// 收货人信息界面返回信息
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
		case NumEntity.RESULT_INVOICE:// 发票信息界面返回信息
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
	 * 進入支付界面
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
