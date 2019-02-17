package com.baiyi.cmall.activities.user.buyer.intention;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.activities.base.BaseMsgActivity;
import com.baiyi.cmall.activities.main._public.CitySelectionActivity;
import com.baiyi.cmall.activities.user.buyer.EditPurchaselActivity;
import com.baiyi.cmall.activities.user.buyer.HintUtils;
import com.baiyi.cmall.activities.user.buyer.form.FormStateUtils;
import com.baiyi.cmall.activities.user.buyer.intention.detail.DetailsProActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.request.manager.MyPurAttentionManager;
import com.baiyi.cmall.request.manager.NullJsonData;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.JsonParse_User;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 我的买家-采购意向-子条目点击-->采购意向单
 * 
 * @author lizl
 * 
 */
public class PurchaseIntentionOrderActivity extends BaseMsgActivity implements
		OnClickListener {

	// 确认采购意向单
	private TextView mBtnSurePurchaseOrder;
	// 取消采购意向单
	private TextView mBtnCancelPurchaseOrder;
	// 删除采购意向单
	private TextView mBtnDeletePurchaseOrder;
	// 拒绝采购意向单
	private TextView mBtnRefushPurchaseOrder;
	// 下单
	private TextView mBtnCommitPurchaseOrder;
	// 采购数据
	private GoodsSourceInfo purInfo;
	// 供应数据
	private GoodsSourceInfo priInfo;

	/**
	 * 采购信息控件
	 */
	// 名称
	private EditText mEtpurchasename;
	// 分类
	private TextView mTxtOrderCategory;
	// 产地
	private TextView mTxtOrderPlace;
	// 品牌
	private TextView mTxtOrderCoalType;
	// 数量
	private EditText mEtOrderWeighView;
	// 价格
	private EditText mEtOrderPrice;
	// 预付费
	private EditText mEtOrderPrePrice;
	// 交割地
	private TextView mTxtOrderDelivate;
	// 详细内容
	private EditText mEtOrderDetail;
	// 交割地选择控件
	private TableRow mTbrDelivery;
	// 交割地ID
	private String delivateId;
	/**
	 * 供应信息控件
	 */
	// 供应商
	private TextView mTxtProviderMerchant;
	// 分类
	private TextView mTxtProviderCategory;
	// 产地
	private TextView mTxtProviderPlace;
	// 品牌
	private TextView mTxtProviderCoalCategory;
	// 数量（库存）
	private TextView mTxtProviderWeight;
	// 价格范围
	private TextView mTxtProviderPrice;
	// 预付金额
	private TextView mTxtProviderPrePrice;
	// 交割地
	private TextView mTxtProviderDelivery;
	// 详细内容
	private TextView mTxtProviderDetail;

	// 意向ID
	private int attentionID;
	// 状态名称
	private String stateName;
	// 请求头
	private String token;
	// 进度条
	private MyLoadingBar progressBar;
	// 从服务器返回的实体类，里面包含采购model，供应model
	private GoodsSourceInfo arrayListInfo;
	// 判断是否是第一次进入此页面
	private boolean isFirst = true;
	// 判断显示哪些按钮
	private String binaryValue;
	// 判断能否编辑名称标志位
	boolean isEditPurName = true;
	// 标志——此界面是否从订单界面跳转过来（=-1则是）
	private int formFlag;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);
		initTitle();
		loaderProgress();
		initData();
		initNetData();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {

		// 意向ID
		attentionID = getIntent().getIntExtra("temp", 0);

		token = CmallApplication.getUserInfo().getToken();

		formFlag = getIntent().getIntExtra("id", 0);
	}

	/**
	 * 标题栏
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("意向单详情");
		topTitleView.setTitleNewsOnclick(new TitleNewsOnclick() {

			@Override
			public void setTitleNewsOnclickLister(boolean isPop) {

				topTitleView.displayPoP(MsgItemUtil.Pop_Default_txt,
						MsgItemUtil.Pop_Default_img);

			}
		});
		topTitleView.setNewsPopItemOnclick(new NewsPopItemOnclick() {

			@Override
			public void setNewsPopItemOnclickLister(int state) {
				MsgItemUtil.onclickPopItem(state,
						PurchaseIntentionOrderActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}

	/**
	 * 得到供应数据
	 */
	private void initNetData() {

		JsonLoader jsonLoader = new JsonLoader(this);
		// 参数：---意向ID
		jsonLoader.setUrl(AppNetUrl.getPurAttentionDetailUrl(attentionID));
		jsonLoader.setPostData(NullJsonData.getPostData());
		jsonLoader.addRequestHeader("token", token);
		jsonLoader.setMethod(BaseNetLoder.Method_Post);
		jsonLoader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				stopProgress();
				finish();
				displayToast(errorMessage);
			}

			@Override
			public void onCompelete(Object arg0, Object result) {

				stopProgress();
				JSONArray array = (JSONArray) result;

				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);
				// 获取数据失败，弹框
				if (1 != info.getStatus()) {

					displayToast(info.getMsg());
					finish();
					return;
				}

				arrayListInfo = MyPurAttentionManager
						.getMyPurchaseAttentionDetail(array);

				// 采购信息
				purInfo = arrayListInfo.getPurSourceInfo();
				// 首次获取交割地
				delivateId = purInfo.getDeliverycityid();
				// 供应信息
				priInfo = arrayListInfo.getProSourceInfo();
				// 按钮状态判断值
				binaryValue = arrayListInfo.getBinaryvalue();
				// 意向状态名称
				stateName = arrayListInfo.getIntentionstatename();

				if (isFirst) {

					// 先显示供应方 ,再显示采购方
					initProviderContent();
					initPurchaseContent();
					initFootButton();
					isFirst = false;
				} else {
					updatePurchaseView();
				}
			}

		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);

	}

	/**
	 * 采购信息
	 */
	private LinearLayout mLinPur;

	private void initPurchaseContent() {

		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_purchase_intention_order, null);
		win_content.addView(view);

		findPurchaseViewById(view);
		updatePurchaseView();
	}

	/**
	 * 找采购信息的控件
	 * 
	 * @param view
	 */
	private void findPurchaseViewById(View view) {

		mLinPur = (LinearLayout) view.findViewById(R.id.lin_pur_order_huan);
		mLinPur.setOnClickListener(this);

		mEtpurchasename = (EditText) view.findViewById(R.id.et_buy_name);
		mTxtOrderCategory = (TextView) view
				.findViewById(R.id.txt_order_category);
		mTxtOrderPlace = (TextView) view.findViewById(R.id.tv_place);
		mTxtOrderCoalType = (TextView) view
				.findViewById(R.id.tv_order_mei_zhong);
		mEtOrderWeighView = (EditText) view.findViewById(R.id.et_order_weight);
		mEtOrderPrice = (EditText) view.findViewById(R.id.et_order_price);
		mEtOrderPrePrice = (EditText) view.findViewById(R.id.et_yu_fu);
		mTxtOrderDelivate = (TextView) view.findViewById(R.id.tv_devilate);
		mEtOrderDetail = (EditText) view.findViewById(R.id.et_detail_content);
		mTbrDelivery = (TableRow) findViewById(R.id.tbr_devilate);

		mTbrDelivery.setOnClickListener(this);

		TextViewUtil.setText(R.id.purname, "名称", view);
		TextViewUtil.setText(R.id.weight, "数量", view);
		TextViewUtil.setText(R.id.price, "价格", view);
		TextViewUtil.setText(R.id.prepayment, "预付金额", view);
		TextViewUtil.setText(R.id.delivery, "交割地", view);

	}

	/**
	 * 更新界面信息
	 */
	private void updatePurchaseView() {

		mEtpurchasename.setText(purInfo.getGoodSName());// 名称
		mTxtOrderCategory.setText(purInfo.getGoodSCategory());// 分类
		mTxtOrderPlace.setText(purInfo.getGoodSPlace());// 产地
		mTxtOrderCoalType.setText(purInfo.getGoodSBrand());// 品牌
		mEtOrderWeighView.setText(purInfo.getGoodSWeight());// 数量
		String price = Utils.twoDecimals(purInfo.getGoodSPrice());
		String prepayMent = Utils.twoDecimals(purInfo.getGoodSPrePrice());
		mEtOrderPrice.setText(price);// 货物价格（单价）
		mEtOrderPrePrice.setText(prepayMent);// 预付费
		mTxtOrderDelivate.setText(purInfo.getGoodSDelivery());// 交割地
		mEtOrderDetail.setText(purInfo.getGoodSPurchaseContent());// 详细内容
	}

	/**
	 * 供应信息
	 */
	private LinearLayout mLinPro;

	@SuppressLint("ResourceAsColor")
	private void initProviderContent() {

		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_provider_intention_line, null);
		win_content.addView(view);

		if (null == priInfo) {

			/*
			 * 将不显示供方 方界面
			 */
			TableLayout layout = (TableLayout) view
					.findViewById(R.id.tb_layout);
			layout.setVisibility(View.GONE);
			/*
			 * 设置供应信息题目为灰色
			 */
			mLinPro = (LinearLayout) view.findViewById(R.id.ll_control);
			mLinPro.setEnabled(false);
			/*
			 * 显示尚未恢复提醒
			 */
			TextView mTvReplyState = (TextView) view.findViewById(R.id.tv_tint);
			/*
			 * 为0 时，没数据，显示状态名称
			 */
			if (!stateName.equals("创建") && !stateName.equals("已取消")) {

				mTvReplyState.setText(stateName + "！！！");
			}
			mTvReplyState.setVisibility(View.VISIBLE);
		} else {
			// 找控件
			findProviderViewById(view);
			// 更新信息
			updateProviderView();
		}
	}

	/**
	 * 找供应信息的控件
	 * 
	 * @param view
	 */
	private void findProviderViewById(View view) {

		mLinPro = (LinearLayout) view.findViewById(R.id.ll_control);
		mLinPro.setOnClickListener(this);

		mTxtProviderMerchant = (TextView) view
				.findViewById(R.id.txt_pro_merchant);
		mTxtProviderCategory = (TextView) view
				.findViewById(R.id.txt_pro_category);
		mTxtProviderPlace = (TextView) view.findViewById(R.id.txt_pro_place);
		mTxtProviderCoalCategory = (TextView) view
				.findViewById(R.id.txt_pro_brand);
		mTxtProviderWeight = (TextView) view.findViewById(R.id.txt_pro_weight);
		mTxtProviderPrice = (TextView) view.findViewById(R.id.txt_pro_price);
		mTxtProviderPrePrice = (TextView) view
				.findViewById(R.id.txt_pro_pre_price);
		mTxtProviderDelivery = (TextView) view
				.findViewById(R.id.txt_pro_delivery);
		mTxtProviderDetail = (TextView) view.findViewById(R.id.txt_pro_detail);

	}

	/**
	 * 更新供应信息的界面
	 */
	private void updateProviderView() {

		mTxtProviderMerchant.setText(priInfo.getGoodSName());// 名称
		mTxtProviderCategory.setText(priInfo.getGoodSCategory());// 分类
		mTxtProviderPlace.setText(priInfo.getGoodSPlace());// 产地
		mTxtProviderCoalCategory.setText(priInfo.getGoodSBrand());// 品牌
		mTxtProviderWeight.setText(priInfo.getGoodSWeight() + "吨");// 数量
		String price = Utils.twoDecimals(priInfo.getGoodSPrice());
		mTxtProviderPrice.setText(price + "元/吨");// 货物价格（单价）
		String prepayMent = Utils.twoDecimals(priInfo.getGoodSPrePrice());
		mTxtProviderPrePrice.setText(prepayMent + "元");// 预付费
		mTxtProviderDelivery.setText(priInfo.getGoodSDelivery());// 交割地
		mTxtProviderDetail.setText(priInfo.getGoodSPurchaseContent());// 详细内容

	}

	/**
	 * 底部操作按钮
	 */
	private void initFootButton() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_pur_state_foot, null);
		win_content.addView(view);
		View viewLine = view.findViewById(R.id.view_line);
		viewLine.setVisibility(View.GONE);
		mBtnSurePurchaseOrder = (TextView) view
				.findViewById(R.id.btn_sure_order);
		mBtnCancelPurchaseOrder = (TextView) view
				.findViewById(R.id.btn_cancel_order);
		mBtnDeletePurchaseOrder = (TextView) view
				.findViewById(R.id.btn_delete_order);
		mBtnRefushPurchaseOrder = (TextView) view
				.findViewById(R.id.btn_refush_order);
		mBtnCommitPurchaseOrder = (TextView) view
				.findViewById(R.id.btn_commit_order);

		/*
		 * 交流中的意向單才会有下单按钮 交流中的意向单，确认按钮均显示---发送意向
		 */
		if (stateName.equals("交流")) {
			mBtnCommitPurchaseOrder.setVisibility(View.VISIBLE);
		}
		/*
		 * 只有自己创建的意向，在创建状态下才能编辑名称，其他状态下均不能编辑
		 */
		if (!(stateName.equals("创建") && null == priInfo)) {
			mEtpurchasename.setEnabled(false);
			isEditPurName = false;
		}

		mBtnSurePurchaseOrder.setVisibility(View.VISIBLE);
		// 是否显示编辑按钮
		if (View.VISIBLE == FormStateUtils.isShow(binaryValue,// 编辑按钮
				FormStateUtils.STATE_EDIT)) {
			mBtnSurePurchaseOrder.setText("编辑");
		} else if (View.VISIBLE == FormStateUtils.isShow(binaryValue,// 采购按钮
				FormStateUtils.STATE_BUY)) {
			mBtnSurePurchaseOrder.setText("采购");
		} else if (View.VISIBLE == FormStateUtils.isShow(binaryValue,// 发送意向按钮
				FormStateUtils.STATE_FASONG)) {
			mBtnSurePurchaseOrder.setText("发送意向");
		} else {
			mBtnSurePurchaseOrder.setVisibility(View.GONE);
			setNoEdit();
		}

		// 显示取消采购意向单
		mBtnCancelPurchaseOrder.setVisibility(FormStateUtils.isShow(
				binaryValue, FormStateUtils.STATE_CANCEL));
		// 显示拒绝采购意向单
		mBtnRefushPurchaseOrder.setVisibility(FormStateUtils.isShow(
				binaryValue, FormStateUtils.STATE_JUJUE));
		// 显示删除采购意向单
		mBtnDeletePurchaseOrder.setVisibility(FormStateUtils.isShow(
				binaryValue, FormStateUtils.STATE_DELETE));

		/*
		 * 状态名称为空，代表从订单列表中跳转过来，此时无按钮操作
		 */
		if (-1 == formFlag) {
			win_content.removeView(view);
		}

		mBtnSurePurchaseOrder.setOnClickListener(this);
		mBtnCancelPurchaseOrder.setOnClickListener(this);
		mBtnDeletePurchaseOrder.setOnClickListener(this);
		mBtnRefushPurchaseOrder.setOnClickListener(this);
		mBtnCommitPurchaseOrder.setOnClickListener(this);
	}

	/**
	 * 当状态不为创建/交流，怎将其输入框设置为不可操作状态
	 */
	private void setNoEdit() {
		mEtOrderWeighView.setEnabled(false);
		mEtOrderPrice.setEnabled(false);
		mEtOrderDetail.setEnabled(false);
		mEtOrderPrePrice.setEnabled(false);
		mTbrDelivery.setEnabled(false);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tbr_devilate:// 交割地
			Intent intent = new Intent(this, CitySelectionActivity.class);
			intent.putExtra("temp", mTxtOrderDelivate.getText().toString());
			startActivityForResult(intent, 1);
			break;
		case R.id.btn_sure_order:// 确认意向单
			sureOrder();
			break;
		case R.id.btn_cancel_order:// 取消意向单
			cancelOrder(AppNetUrl.getCancelPurAttentionUrl(attentionID));
			break;
		case R.id.btn_delete_order:// 删除意向单
			cancelOrder(AppNetUrl.getDeletePurAttentionUrl(attentionID));
			break;
		case R.id.btn_refush_order:// 拒绝订单
			cancelOrder(AppNetUrl.getRefushPurAttentionUrl(attentionID));
			break;
		case R.id.btn_commit_order:// 下单-- 只有采购意向单才有下单

			goActivity(PurchaseOrderDetailActivity.class, attentionID);
			finish();
			break;
		case R.id.lin_pur_order_huan:// 查看采购详情

			// 第1个参数:区分采购编辑还是意向编辑（true为采购、false为意向）
			// 第2个参数:意向ID
			// 第3个参数:区分显示状态（二进制数）
			// 第4个参数:区分是否可以编辑名称
			goToActivity(EditPurchaselActivity.class, false, attentionID,
					binaryValue, isEditPurName);
			break;
		case R.id.ll_control:// 查看供应详情

			goActivity(DetailsProActivity.class, attentionID + "");
			break;
		}
	}

	/**
	 * 取消/删除/拒绝---采购意向单
	 * 
	 * @param url
	 */
	private void cancelOrder(String url) {
		JsonLoader jsonLoader = new JsonLoader(this);

		jsonLoader.setUrl(url);
		jsonLoader.setPostData(NullJsonData.getPostData());
		jsonLoader.addRequestHeader("token", token);
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

	/**
	 * 确认意向单
	 */
	private void sureOrder() {

		GoodsSourceInfo editInfo = new GoodsSourceInfo();

		/**
		 * 判断名称
		 */
		String purName = mEtpurchasename.getText().toString();
		if (TextUtils.isEmpty(purName)) {
			displayToast("名称不能为空");
			return;
		}
		editInfo.setGoodSName(purName);// 名称

		/*
		 * 判断数量-----------------------------------
		 */
		String weight = Utils.getNumberOfString(mEtOrderWeighView.getText()
				.toString());
		if (!"OK".equals(HintUtils.weightHint(weight))) {
			displayToast(HintUtils.weightHint(weight));
			return;
		}
		editInfo.setGoodSWeight(weight);// 数量

		/*
		 * 判断价格-----------------------------------
		 */
		String price = Utils.getNumberOfString(mEtOrderPrice.getText()
				.toString());

		if (!"OK".equals(HintUtils.priceHint(price))) {
			displayToast(HintUtils.priceHint(price));
			return;
		}
		editInfo.setGoodSPrice(price);// 价格

		/*
		 * 判断预付金额-----------------------------------
		 */
		String prepayment = Utils.getNumberOfString(mEtOrderPrePrice.getText()
				.toString());

		if (!"OK".equals(HintUtils.prePriceHint(prepayment))) {
			displayToast(HintUtils.prePriceHint(prepayment));
			return;
		}
		editInfo.setGoodSPrePrice(prepayment);// 预付费

		editInfo.setGoodSDelivery(mTxtOrderDelivate.getText().toString());// 交割地
		editInfo.setGoodSPurchaseContent(mEtOrderDetail.getText().toString());// 内容
		editInfo.setDeliverycityid(delivateId);// 交割地ID

		JsonLoader jsonLoader = new JsonLoader(this);
		jsonLoader.setUrl(AppNetUrl.getEditPurAttentionUrl());
		jsonLoader.setPostData(MyPurAttentionManager
				.getEditPurAttentionPostData(attentionID, editInfo));
		jsonLoader.setMethod(BaseNetLoder.Method_Post);
		jsonLoader.addRequestHeader("token", token);
		jsonLoader.setType("application/json");
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

				displayToast(info.getMsg());
				if (1 == info.getStatus()) {
					finish();
				}

			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		// 结果标志为100时，编辑成功，刷新界面
		if (100 == resultCode) {
			initNetData();
			return;
		}
		if (null == data) {
			return;
		}
		SelectedInfo info = (SelectedInfo) data.getSerializableExtra("key");

		if (null != info) {

			mTxtOrderDelivate.setText(data.getStringExtra("text"));
			delivateId = info.getId();
		}
	}
}
