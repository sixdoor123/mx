package com.baiyi.cmall.activities.user.merchant.provider;

import org.json.JSONObject;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.baiyi.cmall.activities.base.BaseMsgActivity;
import com.baiyi.cmall.activities.user.merchant.MyProviderDetailsActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.IntentionProviderDetailInfo;
import com.baiyi.cmall.entity.IntentionPurchaseDetailInfo;
import com.baiyi.cmall.request.manager.MerchantCenterManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.DataUtils;
import com.baiyi.cmall.utils.MsgItemUtil;
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
 * 我是供应商-对应的采购商列表进入-供应意向
 * 
 * @author sunxy
 * 
 */
public class MyProviderIntentionOrderActivity extends BaseMsgActivity implements
		OnClickListener {

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);
		initData();
		initTitle();
		loadData();
	}

	// 供应信息（包含采购信息）
	private IntentionProviderDetailInfo proinfo;

	/**
	 * 加载数据
	 */
	private void loadData() {
		final MyLoadingBar myLoadingBar = new MyLoadingBar(this);
		myLoadingBar.setPadding(0, getScreenHeight() / 3, 0, 0);
		myLoadingBar.setProgressInfo("加载中,请稍后...");
		myLoadingBar.start();
		win_content.addView(myLoadingBar);

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getProviderIntentionDetailsUrl(id));
		loader.setPostData(new JSONObject().toString());
		loader.setMethod(BaseNetLoder.Method_Post);

		loader.setLoaderListener(new LoaderListener() {
			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				win_content.removeView(myLoadingBar);
				myLoadingBar.stop();
				displayToast(arg2);
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				proinfo = MerchantCenterManager
						.getProviderIntentationDetail(arg1);
				win_content.removeView(myLoadingBar);
				myLoadingBar.stop();

				initPurchaseContent();
				initProviderContent();
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	// 修改后的信息
	// private GoodsSourceInfo editInfo;

	// 数据源
	private GoodsSourceInfo info;
	// id
	private String id;

	/**
	 * 数据源
	 */
	private void initData() {
		id = getIntent().getStringExtra("temp");
		// id = info.getGoodSID();

	}

	/**
	 * 供应信息
	 */
	private void initProviderContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_provider_intention_detail, null);
		win_content.addView(view);
		// 找控件
		findProviderViewById(view);
		// 更新信息
		updateProviderView();
	}

	// 供应进度
	private MyLoadingBar bar;
	// 名称
	private TextView mEditProName;
	// 分类
	private LinearLayout mLlProCategory;
	// 记录选择分类结果
	private TextView mTxtCategory;
	// 品牌
	private LinearLayout mLlProCoalType;
	// 记录品牌的信息
	private TextView mTxtCoal;
	// 产地
	private LinearLayout mLlProPlace;
	// 记录产地
	private TextView mTxtPlace;
	// 库存
	private TextView mEdtInventory;
	// 价格
	private TextView mEdtPrice;
	// 预付金额
	private TextView mEdtPrepayment;
	// 交割地
	private LinearLayout mLlProDelivery;
	// 记录交割地
	private TextView mTxtDelivery;
	// 详细信息
	private TextView mTxtProDetails;

	// 供应信息条目进入下一级目录
	private LinearLayout mLlPro;

	/**
	 * 找供应信息的控件
	 * 
	 * @param view
	 */
	private void findProviderViewById(View view) {
		mEditProName = (TextView) view.findViewById(R.id.edt_goods_provider_name);
		mLlProCategory = (LinearLayout) view.findViewById(R.id.ll_provider_categatory);
		mTxtCategory = (TextView) view.findViewById(R.id.txt_category);
		mLlProCoalType = (LinearLayout) view.findViewById(R.id.ll_provider_coal_type);
		mTxtCoal = (TextView) view.findViewById(R.id.txt_coal_type);
		mLlProPlace = (LinearLayout) view.findViewById(R.id.ll_provider_place);
		mTxtPlace = (TextView) view.findViewById(R.id.txt_place);
		mEdtInventory = (TextView) view.findViewById(R.id.edt_goods_provider_weight);
		mEdtPrice = (TextView) view.findViewById(R.id.edt_goods_provider_price);
		mEdtPrepayment = (TextView) view.findViewById(R.id.edt_goods_provider_prepayment);
		mLlProDelivery = (LinearLayout) view.findViewById(R.id.ll_provider_delivery);
		mTxtDelivery = (TextView) view.findViewById(R.id.txt_delivery);
		mTxtProDetails = (TextView) view.findViewById(R.id.txt_pro_details);

		bar = (MyLoadingBar) view.findViewById(R.id._load);
		
		mLlProCategory.setOnClickListener(this);
		mLlProCoalType.setOnClickListener(this);
		mLlProPlace.setOnClickListener(this);
		mLlProDelivery.setOnClickListener(this);

		mLlPro = (LinearLayout) view.findViewById(R.id.ll_control);
		mLlPro.setOnClickListener(this);
	}

	/**
	 * 更新供应信息的界面
	 * 
	 * @param info
	 */
	private void updateProviderView() {
		if (null != proinfo) {
			mEditProName.setText(proinfo.getOfferName());
			mTxtCategory.setText(proinfo.getCategoryName());
			mTxtCoal.setText(proinfo.getBrandName());
			mTxtPlace.setText(proinfo.getOriginPlaceName());
			mEdtInventory.setText(proinfo.getInventory());
			mEdtPrice.setText(proinfo.getPrice()+"元/吨");
			mEdtPrepayment.setText(proinfo.getPrepayment()+"元");
			mTxtDelivery.setText(proinfo.getDeliveryPlaceName());
			mTxtProDetails.setText(proinfo.getOfferDetail());
		}
	}

	/**
	 * 采购信息
	 */
	private void initPurchaseContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_purchase_intention, null);
		win_content.addView(view);
		// 找控件
		findPurchaseViewById(view);
		// 更新界面数据
		updatePurchaseView();
	}

	// 订单名称
	private TextView mTxtOrderPurName;
	// 商品分类
	private TextView mTxtOrderCategory;
	// 品牌
	private TextView mTxtOrderCoalType;
	// 产地
	private TextView mTxtOrderPlace;
	// 数量
	private TextView mTxtOrderWeight;
	// 价格
	private TextView mTxtOrderPrePrice;
	// 交割地
	private TextView mTxtOrderDelivery;
	// 预付金额
	private TextView mTxtOrderPayment;
	// 详细信息
	private TextView mTxtOrderDetails;

	// 进度
	private MyLoadingBar loadingBar;
	// 采购信息条目进入下一级
	private LinearLayout mLlPur;

	/**
	 * 找采购信息的控件
	 * 
	 * @param view
	 */
	private void findPurchaseViewById(View view) {
		mTxtOrderPurName = (TextView) view
				.findViewById(R.id.txt_order_pur_name);
		mTxtOrderCategory = (TextView) view.findViewById(R.id.txt_pur_category);
		mTxtOrderCoalType = (TextView) view
				.findViewById(R.id.txt_order_coal_type);
		mTxtOrderPlace = (TextView) view.findViewById(R.id.txt_order_place);
		mTxtOrderWeight = (TextView) view
				.findViewById(R.id.txt_order_pur_weight);
		mTxtOrderPrePrice = (TextView) view.findViewById(R.id.txt_order_price);
		mTxtOrderDelivery = (TextView) view
				.findViewById(R.id.txt_order_delivery);
		mTxtOrderPayment = (TextView) view.findViewById(R.id.txt_order_payment);
		mTxtOrderDetails = (TextView) view.findViewById(R.id.txt_order_details);

		loadingBar = (MyLoadingBar) view.findViewById(R.id.load);

		mLlPur = (LinearLayout) view.findViewById(R.id.lin_pur_order_huan);
		mLlPur.setOnClickListener(this);

	}

	/**
	 * 更新界面信息
	 * 
	 * @param info
	 */
	private void updatePurchaseView() {
		if (null != proinfo) {
			IntentionPurchaseDetailInfo purInfo = proinfo
					.getPurchaseDetailInfo();
			if (null != purInfo) {
				mTxtOrderPurName.setText(purInfo.getPurchasename());
				mTxtOrderCategory.setText(purInfo.getCategoryname());
				mTxtOrderCoalType.setText(purInfo.getBrandname());
				mTxtOrderPlace.setText(purInfo.getOriginplacename());
				mTxtOrderWeight.setText(purInfo.getAmount() + "吨");
				mTxtOrderPrePrice.setText(purInfo.getPrice() + "元/吨");
				mTxtOrderDelivery.setText(purInfo.getDeliveryplacename());
				mTxtOrderPayment.setText(purInfo.getPrepayment());
				mTxtOrderDetails.setText(purInfo.getPurchasedetail());
			}
		}
	}

	/**
	 * 标题栏
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("供应意向单");
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
						MyProviderIntentionOrderActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_provider_delivery:// 交割地
			break;
		case R.id.ll_provider_coal_type:// 品牌
			break;
		case R.id.ll_provider_place:// 产地
			break;
		case R.id.ll_provider_categatory:// 分类
			break;
		case R.id.lin_pur_order_huan:// 采购条目进入下一级(详细信戏)
			goActivity(id, MyProviderDetailsActivity.class);
			break;
		case R.id.ll_control:// 供应条目进入下一级（编辑界面）
			goActivity(proinfo.getId(), EditMyProviderStandardArgDetailActivity.class);
			break;
		default:
			break;
		}
	}

}
