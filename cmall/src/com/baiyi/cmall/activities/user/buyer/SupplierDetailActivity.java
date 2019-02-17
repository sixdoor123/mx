package com.baiyi.cmall.activities.user.buyer;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.user.buyer.intention.PurchaseIntentionOrderActivity;
import com.baiyi.cmall.adapter.MyPurchaseOrderAdapter;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.listitem.InScrollListView;
import com.baiyi.cmall.request.manager.MyPurchaseManager;
import com.baiyi.cmall.request.manager.NullJsonData;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.JsonParse_User;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.LoadingBar;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;

/**
 * 
 * 我是采购商-我的采购-供应商界面
 * 
 * @author lizl
 */
public class SupplierDetailActivity extends BaseActivity implements
		OnItemClickListener, OnClickListener {

	/**
	 * 采购信息控件
	 */
	// 相当于切换按钮
	private LinearLayout mLinPurOrderEdit;
	private LinearLayout layout;
	// 数据源
	private ArrayList<GoodsSourceInfo> datas;
	// 采购信息
	private GoodsSourceInfo purInfo;
	// 把供应信息装在集合中
	private GoodsSourceInfo info;
	// 采购的ID
	private int purId;
	// 显示全部数据的状态
	private int DATA_ALL = 1;
	// 采购详情列表，刷新时变化
	private View purView;

	// 名称-商品名称
	private TextView mTxtPurName;
	// 状态（审核 未审核）
	private TextView mTxtPurState;
	// 商品分类
	private TextView mTxtCategoryName;
	// 品牌/煤种
	private TextView mTxtPurCoalCategory;
	// 产地
	private TextView mTxtPurPlace;
	// 库存
	private TextView mTxtPurInverory;
	// 价格
	private TextView mTxtPurPrice;
	// 交割地
	private TextView mTxtPurDelivery;
	// 预付金额
	private TextView mTxtPurPayment;
	// 发布时间
	private TextView mTxtPurPutTime;
	// 結束时间
	private TextView mTxtPurEndTime;
	// 发布状态
	private TextView mTxtPurPublicState;
	// 显示备注信息
	private TextView mTxtPurRemark;
	// 控制备注的布局
	private LinearLayout mLlRemark;
	// 进度条
	private LoadingBar bar;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);
		initData();
		initTitle();
		initPurchaseContent();
		initDetailList();
		initFoot();
		// 添加采购信息
		initPurData();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {

		info = (GoodsSourceInfo) getIntent().getSerializableExtra("data");
		purId = info.getGoodSPurchaseOrderId();
	}

	/**
	 * 初始化标题栏
	 */
	private void initTitle() {
		final EventTopTitleView topTitleView = new EventTopTitleView(this, true);
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
				MsgItemUtil.onclickPopItem(state, SupplierDetailActivity.this);
			}
		});
		topTitleView.setEventName("采购详情");
		win_title.addView(topTitleView);
	}

	/**
	 * 采购信息
	 */
	private void initPurchaseContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_purchase_intention_lin, null);
		win_content.addView(view);
		// 查看具体的采购详情控件
		mLinPurOrderEdit = (LinearLayout) view
				.findViewById(R.id.lin_pur_order_huan);
		mLinPurOrderEdit.setOnClickListener(this);
		layout = (LinearLayout) view.findViewById(R.id.lin_layout);

		// 采购详情
		purView = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_purchase_detail, null);
		purView.setVisibility(View.GONE);
		layout.addView(purView);

		findPurViewById();

		bar = new LoadingBar(this);

	}

	/**
	 * 找 控件
	 */
	private void findPurViewById() {

		mTxtPurName = (TextView) findViewById(R.id.txt_pur_name);
		mTxtPurState = (TextView) findViewById(R.id.txt_pur_state);
		mTxtCategoryName = (TextView) findViewById(R.id.txt_pur_type);
		mTxtPurCoalCategory = (TextView) findViewById(R.id.txt_pur_coal_type);
		mTxtPurPlace = (TextView) findViewById(R.id.txt_pur_place);
		mTxtPurInverory = (TextView) findViewById(R.id.txt_pur_weight);
		mTxtPurPrice = (TextView) findViewById(R.id.txt_pur_price);
		mTxtPurDelivery = (TextView) findViewById(R.id.txt_pur_delivery);
		mTxtPurPayment = (TextView) findViewById(R.id.txt_pur_payment);
		mTxtPurPutTime = (TextView) findViewById(R.id.txt_pur_put_time);
		mTxtPurEndTime = (TextView) findViewById(R.id.txt_pur_end_time);
		mTxtPurPublicState = (TextView) findViewById(R.id.txt_pur_public_state);
		mTxtPurRemark = (TextView) findViewById(R.id.txt_pur_remark);
		mLlRemark = (LinearLayout) findViewById(R.id.ll_remark);
	}

	/*
	 * 采购详情适配器
	 */
	private void updatePurInfo() {

		purView.setVisibility(View.VISIBLE);

		mTxtPurName.setText(purInfo.getGoodSName());
		mTxtPurState.setText(purInfo.getIntentionOrderState());
		mTxtCategoryName.setText(purInfo.getGoodSCategory());
		mTxtPurCoalCategory.setText(purInfo.getGoodSBrand());
		mTxtPurPlace.setText(purInfo.getGoodSPlace());
		mTxtPurInverory.setText(purInfo.getGoodSWeight() + "吨");
		mTxtPurPrice
				.setText(Utils.twoDecimals(purInfo.getGoodSPrice()) + "元/吨");
		mTxtPurDelivery.setText(purInfo.getGoodSDelivery());
		mTxtPurPayment.setText(Utils.twoDecimals(purInfo.getGoodSPrePrice())
				+ "元");
		mTxtPurPutTime.setText(purInfo.getGoodSPutTime());
		mTxtPurEndTime.setText(purInfo.getGoodSEndTime());
		mTxtPurPublicState.setText(purInfo.getPubStateName());
		mTxtPurRemark.setText(purInfo.getGoodSRemark());
		if ("审核未通过".equals(purInfo.getIntentionOrderState())) {
			mLlRemark.setVisibility(View.VISIBLE);
		} else {
			mLlRemark.setVisibility(View.GONE);
		}
	}

	// 我的供应单的适配器
	private MyPurchaseOrderAdapter orderAdapter;
	// 没有数据的时候
	private View noDataView;

	/**
	 * 供应详情列表
	 */
	private void initDetailList() {

		ContextUtil.getLayoutInflater(this).inflate(R.layout.item_provider,
				win_content);
		TextView mView = (TextView) findViewById(R.id.txt_provider_info);
		mView.setText("供应商列表");

		// 没有供应数据的时候显示的提示信息
		noDataView = ContextUtil.getLayoutInflater(SupplierDetailActivity.this)
				.inflate(R.layout.item_reply_state, null);
		noDataView.setVisibility(View.GONE);
		win_content.addView(noDataView);

		// 供应信息列表
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.list_inscroll_view, null);
		InScrollListView listView = (InScrollListView) view
				.findViewById(R.id.lst_in_scroll);
		// 解决listView嵌套在scrollView中首先会显示ListView的问题
		listView.setFocusable(false);
		// 隐藏ListView的滚动条
		listView.setVerticalScrollBarEnabled(true);

		orderAdapter = new MyPurchaseOrderAdapter(this);
		// 此状态表示显示全部状态
		orderAdapter.setState(DATA_ALL);
		listView.setAdapter(orderAdapter);
		listView.setOnItemClickListener(this);

		win_content.addView(view);

		// mScrollView.scrollTo(x, y)

	}

	/**
	 * ListView的点击事件
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		GoodsSourceInfo info = (GoodsSourceInfo) parent
				.getItemAtPosition(position);

		goActivity(PurchaseIntentionOrderActivity.class,
				info.getGoodSPurchaseOrderId());

	}

	// 发布状态
	private static final int COMMIT_STATE = 0;
	// 撤销状态
	private static final int CANCEL_STATE = 1;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.lin_pur_order_huan:
			goActivity(EditPurchaselActivity.class,
					info.getGoodSPurchaseOrderId(), true);
			break;
		case R.id.btn_commit:// 发布采购

			// // 当前信息发布的时间
			// String pubTime = purInfo.getGoodSPutTime();
			// // 当前信息结束的时间
			// String endTime = purInfo.getGoodSEndTime();
			// // 时间提示语
			// String timeHintInfo = HintUtils.timeHint(pubTime, endTime);
			// if (!"OK".equals(timeHintInfo)) {
			// displayToast(timeHintInfo);
			// return;
			// }

			getResult(
					AppNetUrl.getPubPurchaseUrl(info.getGoodSPurchaseOrderId()),
					COMMIT_STATE);
			break;
		case R.id.btn_cancel_form:// 撤销采购
			getResult(
					AppNetUrl.getRevPurchaseUrl(info.getGoodSPurchaseOrderId()),
					CANCEL_STATE);
			break;

		}

	}

	public void getResult(String url, final int btnState) {

		JsonLoader jsonLoader = new JsonLoader(this);
		jsonLoader.setUrl(url);
		jsonLoader.setPostData(NullJsonData.getPostData());
		jsonLoader.addRequestHeader("token", CmallApplication.getUserInfo()
				.getToken());
		jsonLoader.addRequestHeader("token", token);
		jsonLoader.addRequestHeader("iscompany", iscompany);
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

				if (COMMIT_STATE == btnState) {
					// 发布成功，隐藏发布按钮，显示撤销按钮
					mBtnFaBu.setVisibility(View.GONE);
					mBtnCheXiao.setVisibility(View.VISIBLE);
					mTxtPurPublicState.setText("发布");
				} else if (CANCEL_STATE == btnState) {
					// 撤销成功，隐藏撤销按钮，显示发布按钮
					mBtnCheXiao.setVisibility(View.GONE);
					mBtnFaBu.setVisibility(View.VISIBLE);
					mTxtPurPublicState.setText("撤销发布");
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);

	}

	// 发布采购
	private TextView mBtnFaBu;
	// 撤销采购
	private TextView mBtnCheXiao;

	private void initFoot() {

		ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_form_foot, win_menu);
		win_menu.setVisibility(View.GONE);
		mBtnFaBu = (TextView) findViewById(R.id.btn_commit);
		mBtnCheXiao = (TextView) findViewById(R.id.btn_cancel_form);
		mBtnFaBu.setText("发布采购");
		mBtnCheXiao.setText("撤销发布");
		mBtnFaBu.setOnClickListener(this);
		mBtnCheXiao.setOnClickListener(this);

		setShow();

	}

	/**
	 * 根据状态判断按钮的显示
	 * 
	 * 发布状态 (0:未发布,1:已发布,-1:撤销发布)
	 */
	private void setShow() {
		int pubState = info.getPubState();
		String stateName = info.getIntentionOrderState();
		if (!stateName.equals("已审核")) {
			win_menu.removeAllViews();
			return;
		}
		if (pubState == 0 || pubState == -1) {
			mBtnCheXiao.setVisibility(View.GONE);
		}
		if (pubState == 1) {
			mBtnFaBu.setVisibility(View.GONE);
		}

	}

	/**
	 * 加载网络数据
	 */
	private void initPurData() {

		bar.start();
		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getUpdateDetailUrl(purId + ""));
		loader.setPostData(NullJsonData.getPostData());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.addRequestHeader("token", token);
		loader.addRequestHeader("iscompany", iscompany);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum,
					long totalByteNum) {
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				bar.stop();
				displayToast(errorMessage);
			}

			@Override
			public void onCompelete(Object tag, Object result) {

				bar.stop();
				JSONArray array = (JSONArray) result;

				// 服务器返回失败结果
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);
				// 当没有数据时，弹出toast提示框
				if (1 != info.getStatus()) {
					displayToast(info.getMsg());
					return;
				}

				// 解析网络数据
				purInfo = MyPurchaseManager.getUpdatePurchaseDetail(array);
				datas = purInfo.getOfferInfos();
				updatePurInfo();

				/*
				 * 当没有供应数据的时候显示---暂无供应信息的提示
				 */
				if (Utils.isStringEmpty(datas)) {

					noDataView.setVisibility(View.VISIBLE);
				} else {
					noDataView.setVisibility(View.GONE);
				}
				orderAdapter.setData(datas);
				win_menu.setVisibility(View.VISIBLE);

			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (NumEntity.RESULT_CANCEL == resultCode) {
			finish();
			return;
		}

		initPurData();

		if (100 == resultCode) {
			win_menu.removeAllViews();
		}
	}

}
