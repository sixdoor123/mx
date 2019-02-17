package com.baiyi.cmall.activities.user.merchant.intention_viewpage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main._public.CitySelectionActivity;
import com.baiyi.cmall.activities.main._public.GoodSBrandActivity;
import com.baiyi.cmall.activities.main._public.GoodSCategoryActivity;
import com.baiyi.cmall.activities.main._public.PriceRangeActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.IntentionProviderDetailInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.pageviews.BaseScrollViewPageView;
import com.baiyi.cmall.request.manager.MerchantCenterManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.MyLinearLayout;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 我是供应商-我的供应意向单-编辑供应详情
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-4 下午2:24:25
 */
public class EditIntentionOrderProviderDetailViewPager extends
		BaseScrollViewPageView implements OnClickListener {

	// 上下文
	private Context context;
	// 数据源
	private IntentionProviderDetailInfo info;

	// 自定义的线性布局
	private MyLinearLayout layout;

	public EditIntentionOrderProviderDetailViewPager(Context context,
			IntentionProviderDetailInfo info) {
		super(context);
		this.context = context;
		this.info = info;

		layout = new MyLinearLayout(context);
		initContent();
		initButton();
		addView(layout);
	}

	/**
	 * 初始化按钮
	 */
	private void initButton() {
		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_button, null);
		layout.addView(view);
		mBtnEditCommitComplete = (TextView) view
				.findViewById(R.id.btn_commit_modify);
		mBtnEditCommitComplete.setText("完成");
		mBtnEditCommitComplete.setOnClickListener(this);
		mBtnEditCancelComplete = (TextView) view
				.findViewById(R.id.btn_cancel_modify);
		mBtnEditCancelComplete.setOnClickListener(this);
	}

	/**
	 * 内容
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_edit_provider_details, null);
		layout.addView(view);

		findViewById(view);
		updateEdtView();
	}

	// 完成按钮(提交修改)
	private TextView mBtnEditCommitComplete;
	// 取消
	private TextView mBtnEditCancelComplete;

	// 名称
	private EditText mEdtGoodSNam;
	// 控制分类的条目
//	private TableRow mLlGoodSCategory;
	// 显示分类的
	private TextView mTxtCategory;
	// 控制品牌种的条目
//	private TableRow mLlGoodSBrand;
	// 显示品牌
	private TextView mTxtBrand;
	// 控制产地的条目
//	private TableRow mLlConductPlace;
	// 显示产地
	private TextView mTxtPlace;
	// 数量 库存
	private EditText mEdtGoodSInventory;
	// 价格
	private EditText mEdtGoodSPric;
	// 预付金额
	private EditText mEdtGoodSPrepayment;
	// 发布时间
	private TableRow mLlGoodSPutTime;
	// 显示发布时间
	private TextView mTxtPutTime;
	// 结束时间
	private TableRow mLlGoodSEndTime;
	// 显示结束时间
	private TextView mTxtEndTime;
	// 控制到期自动开
	private TableRow mLlAutoStart;
	// 显示是否制动开
	private TextView mTxtStart;
	// 控制到期自动结束
	private TableRow mLlAutoEnd;
	// 显示是否自动关
	private TextView mTxtOver;
	// 控制交割地的条目
	private TableRow mLlGoodSDelivery;
	// 显示交割地
	private TextView mTxtDelivery;
	// 详细内容
	private EditText mTxtDetails;

	// 进度
	private MyLoadingBar loadingBar;

	/**
	 * 找控件
	 * 
	 * @param view
	 */
	private void findViewById(View view) {
		// 名称
		mEdtGoodSNam = (EditText) view.findViewById(R.id.edt_goods_name);
		// 显示分类的
		mTxtCategory = (TextView) view.findViewById(R.id.txt_catagory);
		// 显示品牌
		mTxtBrand = (TextView) view.findViewById(R.id.txt_brand);
		// 显示产地
		mTxtPlace = (TextView) view.findViewById(R.id.txt_place);
		// 数量 库存
		mEdtGoodSInventory = (EditText) view
				.findViewById(R.id.edt_goods_inventory);
		// 价格
		mEdtGoodSPric = (EditText) view.findViewById(R.id.edt_goods_price);
		// 预付金额
		mEdtGoodSPrepayment = (EditText) view
				.findViewById(R.id.edt_goods_prepayment);
		// 发布时间
		mLlGoodSPutTime = (TableRow) view
				.findViewById(R.id.ll_conduct_put_time);
		// 显示发布时间
		mTxtPutTime = (TextView) view.findViewById(R.id.txt_put_time);
		// 结束时间
		mLlGoodSEndTime = (TableRow) view
				.findViewById(R.id.ll_conduct_end_time);
		// 显示结束时间
		mTxtEndTime = (TextView) view.findViewById(R.id.txt_end_time);
		// 控制到期自动开
		mLlAutoStart = (TableRow) view.findViewById(R.id.ll_conduct_auto_start);
		// 显示是否制动开
		mTxtStart = (TextView) view.findViewById(R.id.txt_auto_start);
		// 控制到期自动结束
		mLlAutoEnd = (TableRow) view.findViewById(R.id.ll_conduct_auto_over);
		// 显示是否自动关
		mTxtOver = (TextView) view.findViewById(R.id.txt_auto_over);
		// 控制交割地的条目
		mLlGoodSDelivery = (TableRow) view
				.findViewById(R.id.ll_conduct_delivery);
		// 显示交割地
		mTxtDelivery = (TextView) view.findViewById(R.id.txt_delivery);
		// 详细内容
		mTxtDetails = (EditText) view.findViewById(R.id.txt_details_01);

		loadingBar = (MyLoadingBar) view.findViewById(R.id.load);

		mLlGoodSPutTime.setOnClickListener(this);
		mLlGoodSEndTime.setOnClickListener(this);
		mLlAutoStart.setOnClickListener(this);
		mLlAutoEnd.setOnClickListener(this);
		mLlGoodSDelivery.setOnClickListener(this);
	}

	/**
	 * 更新输入框中的内容
	 */
	private void updateEdtView() {
		if (null != info) {
			// 名称
			mEdtGoodSNam.setText(info.getPurchasename());
			// 显示分类的
			mTxtCategory.setText(info.getCategoryName());
			// 显示品牌
			mTxtBrand.setText(info.getBrandName());
			// 显示产地
			mTxtPlace.setText(info.getOriginPlaceName());
			// 数量 库存
			mEdtGoodSInventory.setText(info.getInventory());
			// 价格
			mEdtGoodSPric.setText(info.getPrice() + "元/吨");
			// 预付金额
			mEdtGoodSPrepayment.setText(info.getPrepayment() + "元");
			// 显示发布时间
			mTxtPutTime.setText(info.getPutTime());
			// 显示结束时间
			mTxtEndTime.setText(info.getEndTime());
			// 显示是否制动开
			mTxtStart.setText(info.isAutoSatrt() ? "是" : "否");
			// 显示是否自动关
			mTxtOver.setText(info.isAutoEnd() ? "是" : "否");
			// 显示交割地
			mTxtDelivery.setText(info.getDeliveryPlaceName());
			// 详细内容
			mTxtDetails.setText(info.getOfferDetail());
		}
	}

	// 修改的信息
	private GoodsSourceInfo sourceInfo;

	/**
	 * 获取输入的商品的信息 封装成实体类
	 * 
	 * @return
	 */
	private void getInputGoodSInfo() {

		sourceInfo = new GoodsSourceInfo();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_commit_modify:// 完成
			editComplete();
			break;
		case R.id.btn_cancel_modify:// 取消
			((BaseActivity) context).finish();
			break;
		case R.id.ll_conduct_delivery:// 交割地
			((BaseActivity) context).goActivity(GoodSCategoryActivity.class);
			break;
		case R.id.ll_conduct_end_time:// 结束时间
			// ((BaseActivity) context).goActivity(PriceRangeActivity.class);
			break;
		case R.id.ll_conduct_put_time:// 发布时间
			break;
		case R.id.ll_conduct_auto_start:// 自动开始
			break;
		case R.id.ll_conduct_auto_over:// 自动结束
			break;
		}
	}

	/**
	 * 我的供应 编辑完成提交修改按钮
	 */
	private void editComplete() {
		// 得到输入的所有信息
		// getInputGoodSInfo();

		loadingBar.setVisibility(View.VISIBLE);
		loadingBar.setProgressInfo("正在加载中...");
		loadingBar.start();

		JsonLoader loader = new JsonLoader(context);
		loader.setUrl(AppNetUrl.getMyIntentionProviderDetailEditUrl());
		loader.setPostData(MerchantCenterManager
				.getMyIntentionProviderDetailEditArg(info));
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
				loadingBar.setProgressInfo("正在解析...");
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				loadingBar.setVisibility(View.GONE);
				loadingBar.stop();
				((BaseActivity) context).displayToast(arg2.toString());
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				RequestNetResultInfo info = MerchantCenterManager
						.getgetMyProviderDetailsEditComplete(arg1);
				if (info.getStatus() == 1) {
					((BaseActivity) context).displayToast(info.getMsg());
					// ((BaseActivity) context).finish();
				} else {
					((BaseActivity) context).displayToast(info.getMsg());
				}
				loadingBar.setVisibility(View.GONE);
				loadingBar.stop();
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
		// goActivity(SucceeMainActivity.class);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 0) {
			//品牌
			
		}
	}
}
