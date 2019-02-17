package com.baiyi.cmall.activities.user.buyer.form.intention_order;

import org.json.JSONArray;

import android.R.string;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.user.buyer.intention.PurchaseIntentionOrderActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.OrderEntity;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.request.manager.MyPurFormManager;
import com.baiyi.cmall.request.manager.NullJsonData;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.JsonParse_User;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 我的订单-采购订单-详情
 * 
 * @author lizl
 * 
 */
public abstract class BasePurIntentionOrderDetailActivity extends BaseActivity {

	// 订单ID
	public String orderID;
	// 状态名称
	public String orderState;
	// 请求头
	private String token;
	// 进度条
	private MyLoadingBar progressBar;
	// 总控件
	private View view;
	// 订单号
	private TextView mTvOrderNumber;
	// 交易状态
	private TextView mTvTradeState;
	// 图片
	private ImageView mImgTu;
	// 收货人
	public TextView mTvConsignee;
	// 电话
	public TextView mTvPhone;
	// 地区
	public TextView mTvAcceptCity;
	// 收货地址
	public TextView mTvAcceptAddress;
	// 发票类型
	public TextView mTvBillType;
	// 发票抬头
	public TextView mTvBillRise;
	// 发票内容
	public TextView mTvBillContent;
	// 意向编号
	private TextView mTvIntentionNum;
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

	public abstract String getStateReminder();

	public abstract int getImage();

	public abstract void addFoot();

	// 数据实体
	public OrderEntity entity;

	public int goState;

	@Override
	protected void initWin(boolean hasScrollView) {
		super.initWin(true);

		initTitle();
		loaderProgress();
		initContent();
		initNetData();
	}

	/**
	 * 标题
	 */
	private void initTitle() {

		EventTopTitleView topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("订单详情");
		win_title.addView(topTitleView);
	}

	/**
	 * 初始化内容
	 */
	private void initContent() {
		view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_pur_intention_order_detail, null);

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

		// 意向，跳转至查看意向界面
		mLinIntention.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				goActivity(PurchaseIntentionOrderActivity.class,
						Integer.valueOf(entity.getIntentionNumber()), -1);
			}
		});

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

	/**
	 * 加载网络数据
	 */
	private void initNetData() {

		token = CmallApplication.getUserInfo().getToken();
		orderState = getIntent().getStringExtra("temp");
		orderID = getIntent().getStringExtra("arg");

		Log.d("MM", orderID);

		JsonLoader jsonLoader = new JsonLoader(this);
		jsonLoader.setUrl(AppNetUrl.getMyPurIntentionFormDetailUrl(orderID));
		jsonLoader.setPostData(NullJsonData.getPostData());
		jsonLoader.setMethod(BaseNetLoder.Method_Post);
		jsonLoader.addRequestHeader("token", token);
		jsonLoader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				finish();
				displayToast(errorMessage);
			}

			@Override
			public void onCompelete(Object arg0, Object result) {
				JSONArray array = (JSONArray) result;
				// 我发出的意向数据
				entity = MyPurFormManager.getMyPurIntentionFormDetail(array);
				stopProgress();
				// 服务器返回结果
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);
				// 当成功时设置适配器显示数据
				if (1 == info.getStatus()) {

					// 获取数据成功，设置数据
					win_content.addView(view);
					setData();
					addFoot();
				} else {
					finish();
					displayToast(info.getMsg());
				}

			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);
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
		mTvTradeState.setText(getStateReminder());
		// 图片
		mImgTu.setImageResource(getImage());
		// 收货人
		mTvConsignee.setText(entity.getReceivername());
		// 电话
		mTvPhone.setText(entity.getPhone());
		// 地区
		mTvAcceptCity.setText(entity.getOrderCity());
		// 收货地址
		mTvAcceptAddress.setText(entity.getAddress());
		// 发票类型
		mTvBillType.setText(entity.getInvoicetypename());
		// 发票抬头
		mTvBillRise.setText(entity.getTitle());
		// 发票内容
		mTvBillContent.setText(entity.getContext());
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
		String d = entity.getPrice();
		/*
		 * 总额= 单价*数量
		 */
		String allMoney = (Double.parseDouble(entity.getPrice()) * Long
				.parseLong(entity.getInventory())) + "";
		// 总价格
		mTvAllPrice.setText(Utils.twoDecimals(allMoney) + "元");
		// 订单状态
		mTvOrderState.setText(TextViewUtil.getEditString(orderState));
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
				// 我发出的意向数据
				Log.d("MM", "订单操作：" + array.toString());

				// 服务器返回结果
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);
				displayToast(info.getMsg());
				if (1 == info.getStatus()) {

					Intent intent = new Intent();
					setResult(NumEntity.RESULT_FORM_OK, intent);
					finish();

				}
			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);

	}
}
