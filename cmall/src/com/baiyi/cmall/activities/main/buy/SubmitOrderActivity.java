package com.baiyi.cmall.activities.main.buy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import net.sf.json.JSONObject;

import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.buy.adapter.ProductThumbnailAdapter;
import com.baiyi.cmall.activities.main.buy.adapter.SubmmitOrderViewPagerAdapter;
import com.baiyi.cmall.activities.main.buy.entity.OwmEntity;
import com.baiyi.cmall.activities.main.mall.MallDefine;
import com.baiyi.cmall.activities.main.mall.entity.PnvmlEntity;
import com.baiyi.cmall.activities.user.buyer.intention.AddInvoiceActivity;
import com.baiyi.cmall.activities.user.buyer.intention.AddNewConsigneeActivity;
import com.baiyi.cmall.activities.user.buyer.intention.ConsigneeAddressActivity;
import com.baiyi.cmall.activities.user.buyer.intention.InVoiceSelectActivity;
import com.baiyi.cmall.activities.user.login.ExitLogin;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.OrderEntity;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.listitem.InScrollGridView;
import com.baiyi.cmall.request.manager.MyPurAttentionManager;
import com.baiyi.cmall.request.manager.NullJsonData;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.IntentClassChangeUtils;
import com.baiyi.cmall.utils.JsonParse_User;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.core.loader.ImageLoader;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.port.OnRecycleViewItemClickListener;
import com.baiyi.cmall.R;
import com.baiyi.cmall.TLog;

/**
 * 购物车结算后进入改界面(订单列表)
 * 
 * @author sunxy
 * 
 */
public class SubmitOrderActivity extends BaseActivity implements OnClickListener, OnRecycleViewItemClickListener {

	// 选择收货人信息
	private LinearLayout mLinConsignee;
	// 收货人信息
	private LinearLayout mLinConsigneeHide;
	// 无收货人信息时提示语
	private TextView mTvConsigneeHite;
	// 总布局
	// private LinearLayout layout;
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

	private String token;
	// 用户ID
	private String userId;

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

	// 公司名称
	private TextView mTxtCompanyName;

	@Override
	protected void initWin(boolean hasScrollView) {
		super.initWin(true);
		initId();
		// 加载收货地址
		initAddressData();
		// 发票类型
		initInvoiceData();

		initData();

		initTitle();
		initContent();

	}

	// 产品列表
	private List<OwmEntity> entities = null;

	// 订单总价
	private String totalPrice;
	// 公司名称
	private String companyName = null;
	private String result = null;

	private void initData() {
		totalPrice = getIntent().getStringExtra(MallDefine.OrderTotalPrice);
		result = getIntent().getStringExtra(MallDefine.Result);
		entities = JsonParseBuy.getOrderList(result);
		if (Utils.isStringEmpty(entities)) {
			displayToast("解析错误");
			return;
		}

		companyName = entities.get(0).getCn();
	}

	/**
	 * Token与用户ID获取
	 */
	private void initId() {
		token = CmallApplication.getUserInfo().getToken();
		userId = CmallApplication.getUserInfo().getUserID();
	}

	/**
	 * 标题栏
	 */
	public void initTitle() {
		EventTopTitleView topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("订单详情");
		win_title.addView(topTitleView);
	}

	// 收货人地址
	private ArrayList<OrderEntity> addresses = null;
	private OrderEntity addressEntity;

	/**
	 * 加载收货人地址数据
	 */
	private void initAddressData() {

		loaderProgress();

		JsonLoader jsonLoader = new JsonLoader(this);
		jsonLoader.setUrl(AppNetUrl.getConsigneeUrl(userId, 1, 5));
		jsonLoader.setPostData(NullJsonData.getPostData());
		jsonLoader.setMethod(BaseNetLoder.Method_Post);
		jsonLoader.addRequestHeader("token", token);
		jsonLoader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object tag, int responseCode, String errorMessage) {
				displayToast(errorMessage);
			}

			@Override
			public void onCompelete(Object arg0, Object result) {
				stopProgress();
				JSONArray array = (JSONArray) result;
				addresses = MyPurAttentionManager.getConsigneeList(array);
				// 服务器返回结果
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);

				// 处理请先登录

				if (1 == info.getStatus()) {
					// 不为空
					if (!Utils.isStringEmpty(addresses)) {
						// 取出默认的
						addressEntity = getDefaultEntity(addresses);
						// 没有默认的 取出第一条
						if (addressEntity == null) {
							addressEntity = addresses.get(0);
						}
					} else {
						// 无数据 (进入编辑界面)
						addressEntity = null;
					}
				} else {
					// 无数据 (进入编辑界面)
					addressEntity = null;

					if (NumEntity.PLEASE_LOG.equals(info.getMsg())) {
						ExitLogin.getInstence(SubmitOrderActivity.this)//
								.cleer();
					}
				}
				setAddressDdata();
			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);
	}

	/**
	 * 取出默认的
	 * 
	 * @param addresses
	 * @return
	 */
	protected OrderEntity getDefaultEntity(ArrayList<OrderEntity> addresses) {

		for (OrderEntity orderEntity : addresses) {

			if (orderEntity.isDefault()) {
				return orderEntity;
			}
		}
		return null;
	}

	private OrderEntity allEntity;
	// 列表
	private ArrayList<OrderEntity> orderListInfo = null;
	// 发票内容
	private ArrayList<OrderEntity> contextDatas = null;
	// 发票类型
	private ArrayList<OrderEntity> typeDatas = null;

	private OrderEntity invoiEntity = null;

	/**
	 * 加载数据
	 */
	private void initInvoiceData() {
		loaderProgress();

		JsonLoader jsonLoader = new JsonLoader(this);
		jsonLoader.setUrl(AppNetUrl.getInVoiceUrl(userId, 1, 5));
		jsonLoader.setPostData(NullJsonData.getPostData());
		jsonLoader.setMethod(BaseNetLoder.Method_Post);
		jsonLoader.addRequestHeader("token", token);
		jsonLoader.addRequestHeader("iscompany", iscompany);
		jsonLoader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object tag, int responseCode, String errorMessage) {
				// listView.onRefreshComplete();
				stopProgress();
				displayToast(errorMessage);
			}

			@Override
			public void onCompelete(Object arg0, Object result) {
				// listView.onRefreshComplete();
				stopProgress();
				JSONArray array = (JSONArray) result;
				allEntity = MyPurAttentionManager.getInvoiceList(array);
				// 服务器返回结果
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);
				orderListInfo = allEntity.getInvoiceList();

				if (1 == info.getStatus()) {
					// 不为空
					if (!Utils.isStringEmpty(orderListInfo)) {
						// 取出默认的
						invoiEntity = getDefaultInvoiEntity(orderListInfo);
						// 没有默认的 取出第一条
						if (invoiEntity == null) {
							invoiEntity = orderListInfo.get(0);
						}
					} else {
						// 无数据 (进入编辑界面)
						invoiEntity = null;
					}
				} else {
					// 无数据 (进入编辑界面)
					invoiEntity = null;
					if (NumEntity.PLEASE_LOG.equals(info.getMsg())) {
						ExitLogin.getInstence(SubmitOrderActivity.this)//
								.cleer();
					}
				}

				contextDatas = allEntity.getContextList();
				typeDatas = allEntity.getTypeList();

				setInvoiceData();
			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);
	}

	/**
	 * 默认的
	 * 
	 * @param orderListInfo
	 * @return
	 */
	protected OrderEntity getDefaultInvoiEntity(ArrayList<OrderEntity> orderListInfo) {

		for (OrderEntity orderEntity : orderListInfo) {

			if (orderEntity.isDefault()) {
				return orderEntity;
			}
		}
		return null;
	}

	// 点击查看产品列表详情
	private LinearLayout mLlMore;
	// 显示产品总数量
	private TextView mTxtProductNum;
	private LinearLayout mGridView;
	private SubmmitOrderViewPagerAdapter adapter;

	/**
	 * 设置界面的内容
	 */
	private void initContent() {

		View view = ContextUtil.getLayoutInflater(this)//
				.inflate(R.layout.activity_st_order_list, null);
		win_content.addView(view);

		// 进入界面没有数据，先不显示界面内容，只加载进度条
		// layout = (LinearLayout) findViewById(R.id.lin_all_data);
		// layout.setVisibility(View.GONE);
		/*
		 * 收货人、收货人电话、收货人地址、收货人城市
		 */
		mTvReceivePerson = (TextView) view.findViewById(R.id.tv_receive_person);
		mTvReceivePersonPhone = (TextView) view.findViewById(R.id.tv_tel_phone);
		mTvReceivePersonAdress = (TextView) view.findViewById(R.id.tv_goods_address);
		mTvReceiveCity = (TextView) view.findViewById(R.id.tv_city);
		/*
		 * 发票内容、发票抬头、发票类型
		 */
		mTvInvoiceContent = (TextView) view.findViewById(R.id.tv_invoice_content);
		mTvInvoiceTitle = (TextView) view.findViewById(R.id.tv_invoice_title);
		mTvInvoiceType = (TextView) view.findViewById(R.id.tv_invoice_type);

		mLinConsignee = (LinearLayout) view.findViewById(R.id.lin_consignee);
		mLinConsigneeHide = (LinearLayout) view.findViewById(R.id.lin_consignee_hide);
		mTvConsigneeHite = (TextView) view.findViewById(R.id.tv_new_consignee);

		mLinInvoice = (LinearLayout) view.findViewById(R.id.lin_invoice);
		mLinInvoiceHide = (LinearLayout) view.findViewById(R.id.lin_invoice_hide);
		mTvInvoiceHite = (TextView) view.findViewById(R.id.tv_new_invoice);

		mLinConsignee.setOnClickListener(this);
		mLinInvoice.setOnClickListener(this);

		mTxtCompanyName = (TextView) view.findViewById(R.id.txt_company_name);
		mTxtCompanyName.setText(companyName);

		View listView = ContextUtil.getLayoutInflater(this)//
				.inflate(R.layout.submit_order_product_list, null);
		win_content.addView(listView);

		mGridView = (LinearLayout) listView.findViewById(R.id.grd);

		// 显示图片
		for (OwmEntity entity : entities) {
			displayPic(entity.getIp());
		}

		mLlMore = (LinearLayout) listView.findViewById(R.id.ll_more);
		mTxtProductNum = (TextView) listView.findViewById(R.id.txt_num);
		mLlMore.setOnClickListener(this);
		mTxtProductNum.setText("共" + entities.size() + "件");

		// 加入总价格
		View footView = ContextUtil.getLayoutInflater(this)//
				.inflate(R.layout.total_price_item, null);
		win_content.addView(footView);

		TextView mTxtPrice = (TextView) footView.findViewById(R.id.tv_total_price);
		mTxtPrice.setText("合计 : ¥" + totalPrice);

		mBtnCommitForm = (TextView) footView.findViewById(R.id.btn_commit);
		mBtnCancelForm = (TextView) footView.findViewById(R.id.btn_cancel_form);
		mBtnCommitForm.setText("提交订单");
		mBtnCancelForm.setText("返回");
		mBtnCommitForm.setOnClickListener(this);
		mBtnCancelForm.setOnClickListener(this);
	}

	private void displayPic(String ip) {

		View view = ContextUtil.getLayoutInflater(this)//
				.inflate(R.layout.img_item, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.img_pic);
		loadImg(/*"http://p3.so.qhimg.com/t0157b1888aea3551f8.jpg"*/ip, imageView);
		LayoutParams params = new LayoutParams(200, LayoutParams.MATCH_PARENT);
		imageView.setLayoutParams(params);

		TextView text = new TextView(this);
		LayoutParams textParams = new LayoutParams(10, LayoutParams.MATCH_PARENT);
		text.setLayoutParams(textParams);
		mGridView.addView(text);
		mGridView.addView(imageView);
	}

	private int count = 0;

	/**
	 * 图片下载
	 * 
	 * @param urlStr
	 * @param view
	 */

	private void loadImg(String urlStr, final ImageView view) {

		ImageLoader loader = new ImageLoader(this, true);
		loader.setUrl(urlStr);
		loader.setPostData(new JSONObject().toString());
		loader.setMethod(BaseNetLoder.Method_Get);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum, long totalByteNum) {
			}

			@Override
			public void onError(Object tag, int responseCode, String errorMessage) {
				Log.d("TT", "errorMessage");
			}

			@Override
			public void onCompelete(Object tag, Object result) {

				BitmapDrawable drawable = (BitmapDrawable) result;
				if (drawable == null) {
					view.setBackgroundResource(R.drawable.icon_no);
				} else {
					view.setBackground(drawable);
				}
			}
		});

		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * 首次设置数据商
	 */
	private String allMoney;

	private void setAddressDdata() {

		/*
		 * 收货人
		 */
		if (addressEntity != null) {
			receiverName = addressEntity.getReceivername();
			receiverCityId = addressEntity.getOrderCityId();
			receiverCityName = addressEntity.getOrderCity();
			reciverAddress = addressEntity.getAddress();
			receiverPhone = addressEntity.getPhone();

		}
		setConsigneeData();
	}

	private void setInvoiceData() {

		/*
		 * 发票
		 */
		if (invoiEntity != null) {
			invoiceTypeId = invoiEntity.getInvoicetypeId();
			invoiceType = invoiEntity.getInvoicetypename();
			invoiceTitle = invoiEntity.getTitle();
			invoiceContext = invoiEntity.getContext();

		}
		setInvoiceViewData();

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
		case R.id.ll_more:// 查看产品列表
			IntentClassChangeUtils.startOrderDetailListActivity(this, result);
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
			intentEntity.setContextList(contextDatas);
			intentEntity.setTypeList(typeDatas);

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

		if (TextUtils.isEmpty(receiverName) || TextUtils.isEmpty(receiverCityId) || TextUtils.isEmpty(receiverCityName)
				|| TextUtils.isEmpty(reciverAddress) || TextUtils.isEmpty(receiverPhone)) {

			displayToast("请完善收货人信息");
			return;
		}
		if (TextUtils.isEmpty(invoiceTypeId) || TextUtils.isEmpty(invoiceType) || TextUtils.isEmpty(invoiceTitle)
				|| TextUtils.isEmpty(invoiceContext)) {

			displayToast("请完善发票信息");
			return;
		}

		sendOrder();
	}

	/**
	 * 提交订单
	 * 
	 * @param postData
	 */
	private void sendOrder() {

		loaderProgress();

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(BuyNetUrl.getOrderSubmit());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setPostData(getSendOrderPostData());
		loader.addRequestHeader("token", token);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum, long totalByteNum) {
			}

			@Override
			public void onError(Object tag, int responseCode, String errorMessage) {
				displayToast(errorMessage + ":" + responseCode);
				stopProgress();
			}

			@Override
			public void onCompelete(Object tag, Object result) {
				// String orderId = JsonParseBase.getDataStr((JSONArray)
				// result);
				JSONArray array = (JSONArray) result;
				// 服务器返回结果
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);

				displayToast(info.getMsg());

				stopProgress();

				if (1 != info.getStatus()) {
					return;
				}
				IntentClassChangeUtils.startMyPurOrderActivity(SubmitOrderActivity.this, 1);
				// finish();
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * 提交订单的数据
	 * 
	 * @return
	 */
	public String getSendOrderPostData() {
		StringBuilder sb = new StringBuilder();
		if (invoiEntity == null) {
			invoiEntity = invoiceEntity;
		}
		int index = 0;
		sb.append("st=" + 1);
		sb.append("&");
		sb.append("opm.on=" + entities.get(0).getPn());
		sb.append("&");
		sb.append("opm.init=" + invoiEntity.getTitle());
		sb.append("&");
		sb.append("opm.ic=" + invoiEntity.getInvoicetypename());
		sb.append("&");
		sb.append("opm.it=" + invoiEntity.getInvoicetypeId());// 发票类型
		sb.append("&");
		sb.append("opm.ci=" + entities.get(0).getCi());
		sb.append("&");
		sb.append("opm.ot=" + 0); // 订单类型
		sb.append("&");
		sb.append("opm.cn=" + entities.get(0).getCn());
		sb.append("&");
		sb.append("opm.ra=" + totalPrice);
		sb.append("&");
		sb.append("opm.rn=" + receiverName);
		sb.append("&");
		sb.append("opm.rci=" + receiverCityId);
		sb.append("&");
		sb.append("opm.radd=" + reciverAddress);
		sb.append("&");
		sb.append("opm.rp=" + receiverPhone);
		for (OwmEntity order : entities) {
			List<PnvmlEntity> list = order.getEntities();
			Map<Integer, PnvmlEntity> map = new HashMap<Integer, PnvmlEntity>();
			for (int i = 0; i < list.size(); i++) {
				map.put(list.get(i).getId(), list.get(i));
				// sb.append(getPostData(order, list, i));
			}
			Collection<PnvmlEntity> collections = map.values();

			for (PnvmlEntity pnvmlEntity : collections) {
				sb.append(getPostData(order, list, index));
				index++;
			}
			// return sb.toString();
		}

		TLog.e("ReqOrder-postdata", "post:" + sb.toString());
		return sb.toString();

	}

	public String getPostData(OwmEntity order, List<PnvmlEntity> list, int i) {
		if (Utils.isStringEmpty(list)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("&");
		sb.append("oswm[" + i + "].pid=" + order.getPi());
		sb.append("&");
		sb.append("oswm[" + i + "].pn=" + order.getPn());
		sb.append("&");
		sb.append("oswm[" + i + "].pc=" + order.getPc());
		for (int j = 0; j < list.size(); j++) {
			sb.append("&");
			sb.append("oswm[" + i + "].nvids[" + j + "]=" + list.get(j).getNvi());
		}
		return sb.toString();
	}
	
	// 返回的 收货人信息实体类
	private OrderEntity consigneeEntity;
	// 返回的 发票信息实体类
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
	private void setInvoiceViewData() {

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

		if (TextViewUtil.isStringEmpty(receiverName)) {
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

		if (TextViewUtil.isStringEmpty(invoiceType)) {
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
			setInvoiceViewData();
			break;
		}

	}

	/**
	 * 条目点击事件
	 * 
	 * @param view
	 * @param position
	 * @param t
	 */
	@Override
	public <T> void onItemClick(View view, int position, T t) {
		OwmEntity entity = (OwmEntity) t;
		// displayToast(entity.toString());
		// IntentClassChangeUtils.startOrderDetailListActivity(this, result);
	}

}
