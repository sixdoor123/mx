package com.baiyi.cmall.activities.user.delegation;

import org.json.JSONObject;

import android.R.integer;
import android.content.Intent;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main._public.CitySelectionActivity;
import com.baiyi.cmall.activities.main._public.GoodSBrandActivity;
import com.baiyi.cmall.activities.main._public.GoodSCategoryActivity;
import com.baiyi.cmall.activities.user.buyer.HintUtils;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.entity._public.BrandEntities;
import com.baiyi.cmall.request.manager.DelegationManager;
import com.baiyi.cmall.request.manager.UserLogisticsManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.DateSelectorView;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.DateSelectorView.OnDateSelectedClickListener;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 编辑委托采购详情
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-17 下午2:20:09
 */
public class EditDelegationPurchaseDetailsActivity extends BaseActivity
		implements OnClickListener {
	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);
		initData();
		initTitle();
		initContent();
		if (null != info) {
			if (2 != info.getState()) {
				initButton();
			}
		}
	}

	/**
	 * 按钮
	 */
	private void initButton() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_button, null);
		win_content.addView(view);
		mBtnCompleted = (TextView) view.findViewById(R.id.btn_commit_modify);
		mBtnCompleted.setText("快速委托");
		mBtnCompleted.setOnClickListener(this);
		TextView mTextView = (TextView) view
				.findViewById(R.id.btn_cancel_modify);
		mTextView.setVisibility(View.GONE);
	}

	// 数据源
	private GoodsSourceInfo info;
	// 状态
	// 1:从委托供应来
	// 0:从委托采购来
	private int state;

	/**
	 * 加载数据
	 */
	private void initData() {
		info = (GoodsSourceInfo) getIntent().getSerializableExtra("key");
		state = getIntent().getIntExtra("temp", 0);
	}

	// 自定义标题
	private EventTopTitleView topTitleView;

	/**
	 * 标题栏
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		if (1 == state) {
			topTitleView.setEventName("编辑委托供应");
		} else {
			topTitleView.setEventName("编辑委托采购");
		}
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
						EditDelegationPurchaseDetailsActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}

	/**
	 * 加载内容
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_edit_delegation_pur_details, null);

		win_content.addView(view);

		findViewById(view);
		updateViewData();
	}

	// 商品名称
	private EditText mEdtTitle;
	// 商家名称
	private EditText mEdtMerchant;
	// 联系人
	private EditText mEdtContact;
	// 联系方式
	private EditText mEdtContactWay;
	// 需求描述
	private EditText mEdtNeedContent;
	// 控制下面信息是否显示
	private LinearLayout mLlShow;
	// 控制
	private TextView mTxtController;
	// 联系人城市
	private TableRow mTbPersonCity;
	// 显示联系人城市
	private TextView mTxtPersonCity;
	// 联系地址
	private TableRow mTbAddress;
	// 显示联系地址
	private EditText mTxtAddress;
	// 分类
	private TableRow mTbCategory;
	// 显示分类
	private TextView mTxtCategory;
	// 交割地
	private TableRow mTbDelivery;
	// 显示交割地
	private TextView mTxtDelivery;
	// 产地
	private TableRow mTbPlace;
	// 显示产地
	private TextView mTxtPlace;
	// 显示品牌
	private EditText mTxtBrand;
	// 数量
	private EditText mEdtWeight;
	// 价格
	private EditText mEdtPrice;

	// 快速委托按钮
	private TextView mBtnCompleted;
	// 进度
	private MyLoadingBar loadingBar;

	/**
	 * 找控件
	 * 
	 * @param view
	 */
	private void findViewById(View view) {
		mEdtTitle = (EditText) view.findViewById(R.id.edt_title);
		mEdtMerchant = (EditText) view.findViewById(R.id.edt_merchant_name);
		mEdtContact = (EditText) view.findViewById(R.id.edt_contact);
		mEdtContactWay = (EditText) view.findViewById(R.id.edt_contact_way);
		mEdtNeedContent = (EditText) view.findViewById(R.id.edt_need_content);
		mLlShow = (LinearLayout) view.findViewById(R.id.ll_show);
		mTxtController = (TextView) view.findViewById(R.id.txt_control);
		mTxtController.setOnClickListener(this);
		mTbPersonCity = (TableRow) view.findViewById(R.id.tb_person_city);
		mTbPersonCity.setOnClickListener(this);
		mTxtPersonCity = (TextView) view.findViewById(R.id.txt_person_city);
		// mTbAddress = (TableRow) view.findViewById(R.id.tb_address);
		// mTbAddress.setOnClickListener(this);
		mTxtAddress = (EditText) view.findViewById(R.id.txt_address);
		mTbCategory = (TableRow) view.findViewById(R.id.tb_category);
		mTbCategory.setOnClickListener(this);
		mTxtCategory = (TextView) view.findViewById(R.id.txt_category);
		mTbDelivery = (TableRow) view.findViewById(R.id.tb_delivery);
		mTbDelivery.setOnClickListener(this);
		mTxtDelivery = (TextView) view.findViewById(R.id.txt_delivery);
		mTbPlace = (TableRow) view.findViewById(R.id.tb_place);
		mTbPlace.setOnClickListener(this);
		mTxtPlace = (TextView) view.findViewById(R.id.txt_place);
		mTxtBrand = (EditText) view.findViewById(R.id.edt_brand);
		mEdtWeight = (EditText) view.findViewById(R.id.edt_weight);
		mEdtPrice = (EditText) view.findViewById(R.id.edt_price);
		loadingBar = (MyLoadingBar) view.findViewById(R.id.load);

		// 添加“*”
		TextViewUtil.setText(R.id.contact, "联系人", view);
		TextViewUtil.setText(R.id.mobile, "联系方式", view);
		TextViewUtil.setText(R.id.title, "标题", view);
		TextViewUtil.setText(R.id.content, "需求描述", view);
		// TextViewUtil.setText(R.id.company, "公司名称", view);

		mEdtNeedContent.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				// 这句话说的意思告诉父View我自己的事件我自己处理
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}

		});
	}

	/**
	 * 更新界面数据
	 */
	private void updateViewData() {
		if (null != info) {
			mEdtTitle.setText(info.getGoodSTitle());
			mEdtMerchant.setText(info.getGoodSMerchant());
			mEdtContact.setText(info.getGoodSContactPerson());
			mEdtContactWay.setText(info.getGoodSContactWay());
			mEdtNeedContent.setText(info.getGoodSContent());
			mTxtPersonCity.setText(getSelectString(info.getGoodSArea()));
			mTxtAddress.setText(getEditString(info.getAddress()));
			mTxtCategory.setText(getSelectString(info.getGoodSCategory()));
			mTxtDelivery.setText(getSelectString(info.getGoodSDelivery()));
			mTxtPlace.setText(getSelectString(info.getStartAddress()));
			mTxtBrand.setText(getEditString(info.getGoodSBrand()));
			// 设置数量
			mEdtWeight
					.setText("0".equals(getEditString(info.getGoodSWeight())) ? ""
							: getEditString(info.getGoodSWeight()));
			// 设置价钱
			String price = Utils.twoDecimals(info.getGoodSPrePrice());
			if ("0".equals(price) || "0.00".equals(price)) {
				price = "";
			}
			mEdtPrice.setText(price);
		}
	}

	private String getSelectString(String s) {

		if ("null".equals(s) || null == s || "".equals(s)) {
			return "请选择";
		}

		return s;
	}

	private String getEditString(String s) {

		if ("null".equals(s) || null == s || "".equals(s)) {
			return "";
		}

		return s;
	}

	/**
	 * 点击事件
	 * 
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_commit_modify:// 完成
			editCompeleted();
			break;
		case R.id.tb_category:// 分类
			goActivity(mTxtCategory.getText().toString().trim(),
					GoodSCategoryActivity.class);
			break;
		// case R.id.tb_brand:// 品牌
		// goActivity(GoodSBrandActivity.class);
		// break;
		case R.id.txt_control:// 控制下面的内容是否显示
			controlItem();
			break;
		case R.id.tb_person_city:// 联系人城市
			goActivity(mTxtPersonCity.getText().toString().trim(),
					CitySelectionActivity.class, 1);
			break;
		// case R.id.tb_address:// 联系地址
		// goActivity(info != null ? info.getAddress() : "",
		// CitySelectionActivity.class, 2);
		// break;
		case R.id.tb_delivery:// 交割地
			goActivity(mTxtDelivery.getText().toString().trim(),
					CitySelectionActivity.class, 3);
			break;
		case R.id.tb_place:// 产地
			goActivity(mTxtPlace.getText().toString().trim(),
					CitySelectionActivity.class, 6);
			break;
		}
	}

	// 分类 
	private SelectedInfo categoryInfo;
	// 品牌
	// private BrandEntities brandInfo;
	// 联系人城市
	private SelectedInfo personCityInfo;
	// 联系人地址
	private SelectedInfo addressInfo;
	// 联系人的详细地址
	private String address;
	// 交割地
	private SelectedInfo deliveryInfo;
	// 产地
	private SelectedInfo placeInfo;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (4 == resultCode) {
			categoryInfo = (SelectedInfo) data.getSerializableExtra("key");
			String text = data.getStringExtra("text");
			mTxtCategory.setText(text);
		} else if (1 == resultCode) {
			personCityInfo = (SelectedInfo) data.getSerializableExtra("key");
			String text = data.getStringExtra("text");
			mTxtPersonCity.setText(text);

		} else if (2 == resultCode) {
			addressInfo = (SelectedInfo) data.getSerializableExtra("key");
			address = data.getStringExtra("text");
			mTxtAddress.setText(address);
		} else if (3 == resultCode) {
			deliveryInfo = (SelectedInfo) data.getSerializableExtra("key");
			String text = data.getStringExtra("text");
			mTxtDelivery.setText(text);
		} else if (6 == resultCode) {
			placeInfo = (SelectedInfo) data.getSerializableExtra("key");
			String text = data.getStringExtra("text");
			mTxtPlace.setText(text);
		}
	}

	// 标记位
	private boolean flag = true;

	/**
	 * 控制线面的内容是否显示
	 */
	private void controlItem() {
		if (!flag) {
			mLlShow.setVisibility(View.GONE);
			mTxtController.setText("委托完善↓");
			flag = true;
		} else {
			mLlShow.setVisibility(View.VISIBLE);
			mTxtController.setText("收起↑");
			flag = false;
		}
	}

	/**
	 * 完成
	 */
	private void editCompeleted() {
		String goodSName = mEdtTitle.getText().toString().trim();
		if (TextUtils.isEmpty(goodSName)) {
			displayToast("标题不能为空");
			return;
		}
		if (!Utils.isExceed_30(goodSName)) {
			displayToast("标题输入过长");
			return;
		}
		String merchaseName = mEdtMerchant.getText().toString().trim();
		if (!Utils.isExceed_30(merchaseName)) {
			displayToast("公司名称输入过长");
			return;
		}
		/*
		 * 判断数量-----------------------------------
		 */
		String weight = Utils
				.getNumberOfString(mEdtWeight.getText().toString());
		if (!"OK".equals(HintUtils.getWeight(weight))) {
			displayToast(HintUtils.getWeight(weight));
			return;
		}
		if (!Utils.isExceed_7(weight)) {
			displayToast("数量输入过长");
			return;
		}
		String contact = mEdtContact.getText().toString().trim();
		if (TextUtils.isEmpty(contact)) {
			displayToast("联系人不能为空");
			return;
		}
		if (!Utils.isExceed_20(contact)) {
			displayToast("联系人输入过长");
			return;
		}
		String number = mEdtContactWay.getText().toString().trim();
		if (TextUtils.isEmpty(number)) {
			displayToast("联系方式不能为空");
			return;
		}
		if (!Utils.isExceed_20(number)) {
			displayToast("联系方式输入过长");
			return;
		}
		if (!(Utils.isPhoneNum(number) && Utils.isPhoneNumberValid(number))) {
			displayToast("联系方式填写不正确");
			return;
		}

		String needContent = mEdtNeedContent.getText().toString().trim();
		if (TextUtils.isEmpty(needContent)) {
			displayToast("需求描述不能为空");
			return;
		}
		if (!Utils.isExceed_1000(needContent)) {
			displayToast("需求描述输入过长");
			return;
		}

		/*
		 * 判断价格-----------------------------------
		 */
		String price = Utils.getNumberOfString(mEdtPrice.getText().toString());

		if (!"OK".equals(HintUtils.getPrice(price))) {
			displayToast(HintUtils.getPrice(price));
			return;
		}
		if (!Utils.isExceed_8(price)) {
			displayToast("价格输入过长");
			return;
		}

		String brand = mTxtBrand.getText().toString().trim();
		if (!Utils.isExceed_20(brand)) {
			displayToast("品牌输入过长");
			return;
		}
		String personDetailAddress = mTxtAddress.getText().toString().trim();
		if (!Utils.isExceed_30(brand)) {
			displayToast("联系人详细地址输入过长");
			return;
		}

		saveModefied(goodSName, merchaseName, weight, contact, number,
				needContent, price, brand, personDetailAddress);
	}

	/**
	 * 保存修改
	 * 
	 * @param goodSName
	 * @param merchaseName
	 * @param weight
	 * @param brand
	 * @param price
	 * @param needContent
	 * @param number
	 * @param contact
	 * @param personDetailAddress
	 */
	private void saveModefied(final String goodSName,
			final String merchaseName, final String weight, String contact,
			String number, String needContent, String price, String brand,
			String personDetailAddress) {

		mLlShow.setVisibility(View.GONE);
		mTxtController.setText("完善委托↓");
		flag = false;

		loadingBar.setVisibility(View.VISIBLE);
		loadingBar.setProgressInfo("正在加载中...");
		loadingBar.start();

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getEditDelegationPurProUrl(state));
		loader.setPostData(DelegationManager.getDeletegationPurProData(
				goodSName, merchaseName, weight, contact, number, needContent,
				price, brand, categoryInfo, personCityInfo, addressInfo,
				deliveryInfo, placeInfo, info, address, personDetailAddress));
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.addRequestHeader("token", CmallApplication.getUserInfo()
				.getToken());
		loader.setLoaderListener(new LoaderListener() {
			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
				loadingBar.setProgressInfo("正在解析...");
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				loadingBar.setVisibility(View.GONE);
				loadingBar.stop();
				displayToast(arg2);
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				RequestNetResultInfo info = UserLogisticsManager
						.getEditDelegationProDetailsResultInfo(arg1);
				loadingBar.setVisibility(View.GONE);
				loadingBar.stop();
				displayToast(info.getMsg());
				if (1 == info.getStatus()) {
					finish();
				} else {
					return;
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * 返回上一级
	 * 
	 * @param goodSName
	 * @param merchaseName
	 * @param weight
	 */
	// private void backPreviousPage(String goodSName, String merchaseName,
	// String weight) {
	// Intent intent = new Intent(this,
	// DelegationProviderDetailsActivity.class);
	// // intent.putExtra("goodSName", goodSName);
	// // intent.putExtra("merchaseName", merchaseName);
	// // intent.putExtra("weight", weight);
	// // startActivity(intent);
	// finish();
	// }

}
