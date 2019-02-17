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
 * 我的意向——下单成功界面
 * 
 * @author lizl
 * 
 */
public class TiJiaoOkDetailActivity extends BaseActivity implements
		OnClickListener {

	// 订单ID
	public String orderID;
	// 总控件
	private View view;
	// 订单号
	private TextView mTvOrderNumber;
	// 交易状态
	private TextView mTvTradeState;
	// 图片
	private ImageView mImgTu;
	// 收货人布局
	private LinearLayout mLinConsigneeLayout;
	// 发票布局
	private LinearLayout mLinInvoiceLayout;
	// 收货人布局右面的小箭头
	private ImageView mImgConsigneeImageView;
	// 发票布局右面的小箭头
	private ImageView mImgInvoiceImageView;
	// 收货人
	private TextView mTvConsignee;
	// 电话
	private TextView mTvPhone;
	// 地区
	private TextView mTvAcceptCity;
	// 收货地址
	private TextView mTvAcceptAddress;
	// 发票类型
	private TextView mTvBillType;
	// 发票抬头
	private TextView mTvBillRise;
	// 发票内容
	private TextView mTvBillContent;
	// 意向编号
	private TextView mTvIntentionNum;
	// 意向佈局
	public LinearLayout mLinIntention;
	// 商家
	private TextView mTvMerchant;
	// 分类
	private TextView mTvCategory;
	// 品牌
	private TextView mTvBrandName;
	// 数量
	private TextView mTvWeight;
	// 预付
	private TextView mTvPrepayment;
	// 价格
	private TextView mTvPrice;
	// 总价格
	private TextView mTvAllPrice;
	// 订单状态
	private TextView mTvOrderState;
	// 数据实体
	public OrderEntity entity;
	// 返回键
	private LinearLayout mImgBack;

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
	protected void initWin(boolean hasScrollView) {
		super.initWin(true);
		initTitle();
		initData();
		initContent();
		setData();
		addFoot();
	}

	/**
	 * 标题
	 */
	private void initTitle() {

		EventTopTitleView topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("订单详情");
		win_title.addView(topTitleView);
		mImgBack = (LinearLayout) findViewById(R.id.img_back);
		mImgBack.setOnClickListener(this);
	}

	/**
	 * 加载网络数据
	 */
	private void initData() {

		entity = (OrderEntity) getIntent().getSerializableExtra("orderEntity");

		orderID = entity.getId();

		setZhiFuData();
	}

	/**
	 * 初始化内容
	 */
	private void initContent() {
		view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_pur_intention_order_detail, null);

		win_content.addView(view);
		// 订单号
		mTvOrderNumber = (TextView) view.findViewById(R.id.tv_order_id);
		// 交易状态
		mTvTradeState = (TextView) view.findViewById(R.id.tv_warm_info);
		// 图片
		mImgTu = (ImageView) view.findViewById(R.id.img_tu);
		// 收货人
		mTvConsignee = (TextView) view.findViewById(R.id.tv_get_goods_person);
		// 电话
		mTvPhone = (TextView) view.findViewById(R.id.tv_tel_phone);
		// 地区
		mTvAcceptCity = (TextView) view.findViewById(R.id.tv_get_goods_city);
		// 收货地址
		mTvAcceptAddress = (TextView) view
				.findViewById(R.id.tv_get_goods_address);
		// 发票类型
		mTvBillType = (TextView) view.findViewById(R.id.tv_invoice_type);
		// 发票抬头
		mTvBillRise = (TextView) view.findViewById(R.id.tv_invoice_title);
		// 发票内容
		mTvBillContent = (TextView) view.findViewById(R.id.tv_invoice_content);
		// 意向编号
		mTvIntentionNum = (TextView) view.findViewById(R.id.tv_attention_id);
		// 意向线性布局
		mLinIntention = (LinearLayout) view.findViewById(R.id.lin_num);
		mLinIntention.setOnClickListener(this);
		// 商家
		mTvMerchant = (TextView) view.findViewById(R.id.tv_merchact_name);
		// 分类
		mTvCategory = (TextView) view.findViewById(R.id.tv_category);
		// 品牌
		mTvBrandName = (TextView) view.findViewById(R.id.tv_brand);
		// 数量
		mTvWeight = (TextView) view.findViewById(R.id.tv_weight);
		// 预付
		mTvPrepayment = (TextView) view.findViewById(R.id.tv_pre_price);
		// 价格
		mTvPrice = (TextView) view.findViewById(R.id.tv_price);
		// 总价格
		mTvAllPrice = (TextView) view.findViewById(R.id.tv_all_price);
		// 订单状态
		mTvOrderState = (TextView) view.findViewById(R.id.tv_state);

	}

	// 取消订单
	private TextView mBtnCancelForm;
	// 去支付
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

	/**
	 * 设置数据
	 */
	private void setData() {

		// 订单号
		mTvOrderNumber.setText(String.format(
				getResources().getString(R.string.form_num),
				entity.getOrderNumber()));
		// 交易状态
		mTvTradeState.setText(R.string.dai_fukuan_hint);
		// 图片
		mImgTu.setImageResource(R.drawable.icon_payment);

		// 收货人
		setConsigneeData();

		// 发票
		setInvoiceData();

		// 意向编号
		mTvIntentionNum.setText(entity.getIntentionNumber());
		// 商家
		mTvMerchant.setText(entity.getCompanyname());
		// 分类
		mTvCategory.setText(entity.getCategoryName());
		// 品牌
		mTvBrandName.setText(entity.getBrandname());
		// 数量
		mTvWeight.setText(entity.getInventory() + "吨");
		// 预付
		mTvPrepayment.setText(Utils.twoDecimals(entity.getPrepayment()) + "元");
		// 价格
		mTvPrice.setText(Utils.twoDecimals(entity.getPrice()) + "元/吨");

		/*
		 * 总额= 单价*数量
		 */
		String allMoney = (Double.parseDouble(entity.getPrice()) * Long
				.parseLong(entity.getInventory())) + "";
		// 总价格
		mTvAllPrice.setText(Utils.twoDecimals(allMoney) + "元");
		// 订单状态
		mTvOrderState.setText("待付款");
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

	/**
	 * 专门获取服务器返回的数据
	 * 
	 * @param url
	 *            地址
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
				Log.d("MM", "订单操作：" + array.toString());

				// 服务器返回结果
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
		case R.id.lin_num:// 查看意向信息
			goActivity(PurchaseIntentionOrderActivity.class,
					Integer.valueOf(entity.getIntentionNumber()), -1);
			break;
		case R.id.img_back:// 返回键
			finish();
			break;
		case R.id.btn_commit:// 去支付
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
	 * 进入支付界面
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
