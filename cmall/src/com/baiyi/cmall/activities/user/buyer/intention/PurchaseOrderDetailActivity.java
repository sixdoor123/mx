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
 * （我的采购-发出的采购-子条目点击-采购意向单）—用戶下单后，跳转至此界面
 * 
 * @author lizl
 * 
 */
public class PurchaseOrderDetailActivity extends BaseActivity implements
		OnClickListener {

	// 选择收货人信息
	private LinearLayout mLinConsignee;
	// 收货人信息
	private LinearLayout mLinConsigneeHide;
	// 无收货人信息时提示语
	private TextView mTvConsigneeHite;
	// 总布局
	private LinearLayout layout;
	/*
	 * 收货人信息
	 */
	private TextView mTvReceivePerson;
	private TextView mTvReceivePersonPhone;
	private TextView mTvReceivePersonAdress;
	private TextView mTvReceiveCity;
	/*
	 * 发票信息
	 */
	private TextView mTvInvoiceContent;
	private TextView mTvInvoiceTitle;
	private TextView mTvInvoiceType;
	/*
	 * 商品信息
	 */
	private TextView mTvMerchantname;
	private TextView mTvCategoryname;
	private TextView mTvBrandName;
	private TextView mTvKuCun;
	private TextView mTvPrice;
	private TextView mTvPrePrice;
	private TextView mTvAllMoney;

	// 发票信息
	private LinearLayout mLinInvoice;
	// 发票信息
	private LinearLayout mLinInvoiceHide;
	// 无发票信息时提示语
	private TextView mTvInvoiceHite;

	// 取消订单
	private TextView mBtnCancelForm;
	// 提交下单
	private TextView mBtnCommitForm;
	// 采购数据
	private GoodsSourceInfo purInfo;
	// 意向ID
	private int attentionID;
//	private String token;
	// 用户ID
	private String userId;
	// 商家Id
	private String companyId;

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

	// 订单成功返回数据
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
	 * Token与用户ID获取
	 */
	private void initId() {

		attentionID = getIntent().getIntExtra("temp", 0);
		userId = CmallApplication.getUserInfo().getUserID();
	}

	/**
	 * 下单后的订单数据
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

				// 服务器返回结果
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);

				// 当成功时设置适配器显示数据
				if (1 == info.getStatus()) {

					stopProgress();
					// 获取数据成功，设置数据
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
	 * 标题栏
	 */
	public void initTitle() {
		EventTopTitleView topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("订单详情");
		win_title.addView(topTitleView);
	}

	/**
	 * 设置界面的内容
	 */
	private void initContent() {

		ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_purchase_order_detail, win_content);
		// 进入界面没有数据，先不显示界面内容，只加载进度条
		layout = (LinearLayout) findViewById(R.id.lin_all_data);
		layout.setVisibility(View.GONE);
		/*
		 * 收货人、收货人电话、收货人地址、收货人城市
		 */
		mTvReceivePerson = (TextView) findViewById(R.id.tv_receive_person);
		mTvReceivePersonPhone = (TextView) findViewById(R.id.tv_tel_phone);
		mTvReceivePersonAdress = (TextView) findViewById(R.id.tv_goods_address);
		mTvReceiveCity = (TextView) findViewById(R.id.tv_city);
		/*
		 * 发票内容、发票抬头、发票类型
		 */
		mTvInvoiceContent = (TextView) findViewById(R.id.tv_invoice_content);
		mTvInvoiceTitle = (TextView) findViewById(R.id.tv_invoice_title);
		mTvInvoiceType = (TextView) findViewById(R.id.tv_invoice_type);

		/*
		 * 分类、品种、数量、价格、预付费、总额
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
		mBtnCommitForm.setText("提交订单");
		mBtnCancelForm.setText("返回");
		mBtnCommitForm.setOnClickListener(this);
		mBtnCancelForm.setOnClickListener(this);

	}

	/**
	 * 首次设置数据商
	 */
	private String allMoney;

	private void setData() {

		/*
		 * 收货人
		 */
		receiverName = purInfo.getGoodSContactPerson();
		receiverCityId = purInfo.getCityid();
		receiverCityName = purInfo.getCity();
		reciverAddress = purInfo.getGoodSArea();
		receiverPhone = purInfo.getGoodSContactWay();
		setConsigneeData();

		/*
		 * 发票
		 */
		invoiceTypeId = purInfo.getMoreTypeId();
		invoiceType = purInfo.getMoreType();
		invoiceTitle = purInfo.getGoodSTitle();
		invoiceContext = purInfo.getGoodSContent();
		setInvoiceData();

		/*
		 * 商家ID
		 */
		companyId = purInfo.getCompanyId();

		/*
		 * 总额= 单价*数量
		 */
		allMoney = (Double.parseDouble(purInfo.getGoodSPrice()) * Long
				.parseLong(purInfo.getKuCun())) + "";

		mTvInvoiceContent.setText(purInfo.getGoodSContent());
		mTvInvoiceTitle.setText(purInfo.getGoodSTitle());
		mTvInvoiceType.setText(purInfo.getMoreType());
		mTvMerchantname.setText(purInfo.getGoodSMerchant());
		mTvCategoryname.setText(purInfo.getGoodSCategory());
		mTvBrandName.setText(purInfo.getGoodSBrand());
		mTvKuCun.setText(purInfo.getKuCun() + "吨");
		mTvPrice.setText(Utils.twoDecimals(purInfo.getGoodSPrice()) + "元/吨");
		mTvPrePrice.setText(Utils.twoDecimals(purInfo.getGoodSPrePrice() + "")
				+ "元");
		mTvAllMoney.setText(Utils.twoDecimals(allMoney) + "元");

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.lin_consignee:// 选择收货人信息
			selectConsigneeAddress();
			break;
		case R.id.lin_invoice:// 发票信息
			selcetInVoiceInfo();
			break;
		case R.id.btn_commit:// 提交订单
			commitOrder();
			break;
		case R.id.btn_cancel_form:// 返回订单

			finish();
			break;
		default:
			break;
		}
	}

	/**
	 * 选择收货地址信息
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
	 * 选择发票信息
	 */
	private void selcetInVoiceInfo() {

		if (isNullInvoice()) {

			/*
			 * 构建一个model需要往添加发票界面传递---发票内容和发票类型
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
	 * 提交订单
	 */
	private void commitOrder() {

		if (TextUtils.isEmpty(receiverName)
				|| TextUtils.isEmpty(receiverCityId)
				|| TextUtils.isEmpty(receiverCityName)
				|| TextUtils.isEmpty(reciverAddress)
				|| TextUtils.isEmpty(receiverPhone)) {

			displayToast("请完善收货人信息");
			return;
		}
		if (TextUtils.isEmpty(invoiceTypeId) || TextUtils.isEmpty(invoiceType)
				|| TextUtils.isEmpty(invoiceTitle)
				|| TextUtils.isEmpty(invoiceContext)) {

			displayToast("请完善发票信息");
			return;
		}

		// TODO 总金额/预付费传值不正确
		getResult(AppNetUrl.getCommitOrderUrl(),
				MyPurAttentionManager.getCommitOrderPostData(receiverName,
						receiverCityId, receiverCityName, reciverAddress,
						receiverPhone, invoiceTypeId, invoiceType,
						invoiceTitle, invoiceContext, attentionID, userId,
						companyId, Utils.getNumberOfString(purInfo
								.getGoodSPrePrice() + ""), allMoney));
	}

	/**
	 * 访问服务器的方法
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
				// 服务器返回结果
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);

				stopProgress();
				if (1 == info.getStatus()) {

					orderEntity = MyPurFormManager.getMyTiJiaoDetailData(array);

					// TODO跳转，去付款
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

	// 收货人信息实体类
	private OrderEntity consigneeEntity;
	// 发票信息实体类
	private OrderEntity invoiceEntity;

	/**
	 * 设置收货人信息
	 */
	private void setConsigneeData() {

		if (isNullConsignee()) {
			mLinConsigneeHide.setVisibility(View.GONE);
			mTvConsigneeHite.setVisibility(View.VISIBLE);
			return;
		}

		mLinConsigneeHide.setVisibility(View.VISIBLE);
		mTvConsigneeHite.setVisibility(View.GONE);
		// 切换收货地址，出现新的收获人ID
		mTvReceivePerson.setText(receiverName);
		mTvReceivePersonPhone.setText(receiverPhone);
		mTvReceivePersonAdress.setText(reciverAddress);
		mTvReceiveCity.setText(receiverCityName);
	}

	/**
	 * 设置发票信息
	 */
	private void setInvoiceData() {

		if (isNullInvoice()) {
			mLinInvoiceHide.setVisibility(View.GONE);
			mTvInvoiceHite.setVisibility(View.VISIBLE);
			return;
		}

		mLinInvoiceHide.setVisibility(View.VISIBLE);
		mTvInvoiceHite.setVisibility(View.GONE);
		// 切换收货地址，出现新的收获人ID
		mTvInvoiceContent.setText(invoiceContext);
		mTvInvoiceTitle.setText(invoiceTitle);
		mTvInvoiceType.setText(invoiceType);
	}

	/**
	 * 判断收貨人信息是否为空
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
	 * 判断发票信息是否为空
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

		case NumEntity.RESULT_CONSIGNEE:// 收货人信息界面返回信息
			consigneeEntity = (OrderEntity) data.getSerializableExtra("info");

			receiverName = consigneeEntity.getReceivername();
			receiverCityId = consigneeEntity.getOrderCityId();
			receiverCityName = consigneeEntity.getOrderCity();
			reciverAddress = consigneeEntity.getAddress();
			receiverPhone = consigneeEntity.getPhone();
			setConsigneeData();
			break;
		case NumEntity.RESULT_INVOICE:// 发票信息界面返回信息
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
