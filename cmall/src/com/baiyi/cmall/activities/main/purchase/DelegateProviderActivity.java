package com.baiyi.cmall.activities.main.purchase;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Intent;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.baiyi.cmall.activities.base.BaseMsgActivity;
import com.baiyi.cmall.activities.main.SucceeMainActivity;
import com.baiyi.cmall.activities.main._public.CitySelectionActivity;
import com.baiyi.cmall.activities.user.buyer.HintUtils;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.LoginInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.request.manager.PurchaseSourceManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.DataUtils;
import com.baiyi.cmall.utils.JsonParse_User;
import com.baiyi.cmall.utils.MobileStateUtils;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.utils.XmlName;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.DateSelectorView;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.DateSelectorView.OnDateSelectedClickListener;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.cmall.views.pulldownview.PullToRefreshListView;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 委托供应界面
 * 
 * @author sunxy
 * 
 */
public class DelegateProviderActivity extends BaseMsgActivity implements
		OnClickListener {

	// 数据源
	private GoodsSourceInfo info;

	// 供应信息条目
	private LinearLayout mLlSupplyInfo;
	// 向下或者向上的箭头
	private ImageView mImgUpDown;
	// 标记是显示或者不显示
	boolean flag = true;

	// 点击供应信息条目时，显示此ListView
	private PullToRefreshListView mLstDetails;

	// 把供应信息装在集合中
	private ArrayList<String> providerInfos;

	// 输入框实例
	private EditText mEdtProviderName;// 供应商名称
	private EditText mEdtContactName;// 联系人姓名
	private EditText mEdtContactNumber;// 电话号码
	private EditText mEdtPriceInfo;// 报价信息
	// 按钮
	// 提交供应委托
	private TextView mBtnProvider;
	// 取消
	private TextView mBtnProviderCancel;
	// 公司ID
	private String companyId;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);
		ActivityStackControlUtil.add(this);
		initData();
		initCompanyId();
		initTitle();
		initContent();
		initInputContent();
	}

	/**
	 * 初始化商家ID
	 */
	private void initCompanyId() {
		companyId = CmallApplication.getUserInfo().getCompanyID();
	}

	private LoginInfo loginInfo;
	private String userID;

	/**
	 * 得到数据
	 */
	private void initData() {
		info = (GoodsSourceInfo) getIntent().getSerializableExtra("key");

		providerInfos = DataUtils.getProviderShowList(info);

		XmlUtils utils = XmlUtils.getInstence(this);
		userID = utils.getXmlStringValue(XmlName.USER_ID);
		if (!TextUtils.isEmpty(userID)) {
			loginInfo = CmallApplication.getUserInfo();
			if (null == loginInfo) {
				loginInfo.setCompanyName(utils
						.getXmlStringValue(XmlName.Company_Name));
				loginInfo.setUserName(utils
						.getXmlStringValue(XmlName.USER_NAME));
				loginInfo.setMobile(utils.getXmlStringValue(XmlName.Mobile));
			}
		}
	}

	/**
	 * 标题
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("委托供应");
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
				MsgItemUtil
						.onclickPopItem(state, DelegateProviderActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}

	// 添加详情
	private LinearLayout mLlAddLayout;

	/**
	 * 初始化供应信息条目
	 */
	@SuppressWarnings("unchecked")
	private void initContent() {

		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_delegate_provider, null);
		win_content.addView(view);

		// 用于添加具体的详细内容
		mLlAddLayout = (LinearLayout) view.findViewById(R.id.ll_pur_add);
		// 按钮的切换（显示/隐藏 ）
		mLlSupplyInfo = (LinearLayout) view.findViewById(R.id.ll_provider_info);
		// 指示箭头
		mImgUpDown = (ImageView) view.findViewById(R.id.img_provider_up_down);

		View listView = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_pur_detils, null);
		mLstDetails = (PullToRefreshListView) listView
				.findViewById(R.id.lst_in_details);
		mLstDetails.setVisibility(View.GONE);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.list_item_pop, R.id.txt_item, providerInfos);
		mLstDetails.setAdapter(adapter);

		mLlAddLayout
				.addView(listView, getScreenWidth(), MobileStateUtils
						.getTotalHeightofListView(adapter, mLstDetails));
		mLlSupplyInfo.setOnClickListener(this);

	}

	/**
	 * 初始化一些输入框的信息
	 */
	private void initInputContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_delegate_input, null);
		win_content.addView(view);

		// 实例化
		findViewById(view);
		updataView();
	}

	/**
	 * 更新输入框中的内容
	 */
	private void updataView() {
		if (null != loginInfo) {
			mEdtProviderName.setText(loginInfo.getCompanyName());// 供应商名称
			mEdtContactName.setText(loginInfo.getUserName());// 联系人姓名
			mEdtContactNumber.setText(loginInfo.getMobile());// 电话号码
			// mEdtPriceInfo.setText();// 报价信息
		}

		if (null != info) {
			mTxtPersonCity.setText(info.getCity());
			mTxtAddress.setText(info.getAddress().equals("null") ? "" : info
					.getAddress());
			mTxtCategory.setText(info.getGoodSCategory());
			mTxtDelivery.setText(info.getGoodSDelivery());
			mTxtPlace.setText(info.getGoodSArea());
			mTxtBrand.setText(info.getGoodSBrand());
			mEdtWeight.setText(info.getGoodSWeight());
			mEdtPrice.setText(Utils.twoDecimals(info.getGoodSpriceInterval()));
			// 设置开始时间
			// if (Utils.getLongTime1(info.getGoodSPutTime()) <= 0) {
			// mTxtStartTime.setText("");
			// } else {
			// // mTxtStartTime.setText(info.getGoodSPutTime());
			// }
			// // 设置结束时间
			// if (Utils.getLongTime1(info.getGoodSEndTime()) <= 0) {
			// // mTxtEndTime.setText("");
			// } else {
			// mTxtEndTime.setText(info.getGoodSEndTime());
			// }
		}
	}

	// 控制下面信息是否显示
	private LinearLayout mLlShow;
	// 显示委托完善或者收起
	private TextView mTxtController;
	// 联系人城市
	private TableRow mTbPersonCity;
	// 显示联系人城市
	private TextView mTxtPersonCity;
	// 联系地址
	private TableRow mTbAddress;
	// 显示联系地址
	private EditText mTxtAddress;
	// 显示分类
	private TextView mTxtCategory;
	// 交割地
	private TableRow mTbDelivery;
	// 显示交割地
	private TextView mTxtDelivery;
	// 显示产地
	private TextView mTxtPlace;
	// 显示品牌
	private TextView mTxtBrand;
	// 数量
	private EditText mEdtWeight;
	// 价格
	private EditText mEdtPrice;
	// 进度
	private MyLoadingBar loadingBar;

	
	/**
	 * 找控件
	 * 
	 * @param view
	 */
	private void findViewById(View view) {
		mEdtProviderName = (EditText) view.findViewById(R.id.edt_supply_name);
		mEdtContactName = (EditText) view.findViewById(R.id.edt_contact_name);
		mEdtContactNumber = (EditText) view
				.findViewById(R.id.edt_contact_number);
		mEdtPriceInfo = (EditText) view.findViewById(R.id.edt_supply_need);
		mEdtPriceInfo.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				// 这句话说的意思告诉父View我自己的事件我自己处理
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}

		});
		TextView mTxtMerchantNmae = (TextView) view
				.findViewById(R.id.txt_merchant_name);
		mTxtMerchantNmae.setText("供应商");
		TextView mTxtSupplyPrice = (TextView) view
				.findViewById(R.id.txt_supply_price);

		mBtnProvider = (TextView) view.findViewById(R.id.btn_commit_modify);
		mBtnProviderCancel = (TextView) view
				.findViewById(R.id.btn_cancel_modify);
		mBtnProvider.setText("提交供应委托");

		mBtnProvider.setOnClickListener(this);
		mBtnProviderCancel.setOnClickListener(this);

		mLlShow = (LinearLayout) view.findViewById(R.id.ll_show);
		mTxtController = (TextView) view.findViewById(R.id.txt_control);
		mTxtController.setOnClickListener(this);
		mTbPersonCity = (TableRow) view.findViewById(R.id.tb_person_city);
		mTbPersonCity.setOnClickListener(this);
		mTxtPersonCity = (TextView) view.findViewById(R.id.txt_person_city);
		// mTbAddress = (TableRow) view.findViewById(R.id.tb_address);
		// mTbAddress.setOnClickListener(this);
		mTxtAddress = (EditText) view.findViewById(R.id.txt_address);
		mTxtCategory = (TextView) view.findViewById(R.id.txt_category);
		mTbDelivery = (TableRow) view.findViewById(R.id.tb_delivery);
		mTbDelivery.setOnClickListener(this);
		mTxtDelivery = (TextView) view.findViewById(R.id.txt_delivery);
		mTxtPlace = (TextView) view.findViewById(R.id.txt_place);
		mTxtBrand = (TextView) view.findViewById(R.id.txt_brand);
		mEdtWeight = (EditText) view.findViewById(R.id.edt_weight);
		mEdtPrice = (EditText) view.findViewById(R.id.edt_price);
		loadingBar = (MyLoadingBar) view.findViewById(R.id.load);

		TextViewUtil.setText(R.id.contact, "联系人", view);
		TextViewUtil.setText(R.id.mobile, "联系方式", view);
		// TextViewUtil.setText(R.id.txt_merchant_name, "供应商", view);
		TextViewUtil.setText(R.id.txt_supply_price, "需求描述", view);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_provider_info:
			if (flag) {
				mLstDetails.setVisibility(View.VISIBLE);
				mImgUpDown.setImageResource(R.drawable.up_arrow);
				flag = false;
			} else {
				mLstDetails.setVisibility(View.GONE);
				mImgUpDown.setImageResource(R.drawable.down_arrow);
				flag = true;
			}
			break;
		case R.id.btn_commit_modify:// 提交委托供应
			supplyProvider();
			break;
		case R.id.btn_cancel_modify:// 取消按钮的点击事件
			finish();
			break;
		case R.id.txt_control:// 控制下面的内容是否显示
			controlItem();
			break;
		case R.id.tb_delivery:// 交割地
			goActivity(mTxtDelivery.getText().toString().trim(),
					CitySelectionActivity.class, 3);
			break;
		case R.id.tb_person_city:// 选择城市
			goActivity(mTxtPersonCity.getText().toString(),
					CitySelectionActivity.class, 1);
			break;
		}
	}

	private String city;
	private String devalite;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (null == data) {
			return;
		}

		SelectedInfo info = (SelectedInfo) data.getSerializableExtra("key");

		if (null != info) {

			// 联系人城市
			if (resultCode == 1) {
				String text = data.getStringExtra("text");
				city = info.getId();
				mTxtPersonCity.setText(text);
			}

			// 交割地
			if (resultCode == 3) {
				String text = data.getStringExtra("text");
				devalite = info.getId();
				mTxtDelivery.setText(text);
			}
		}
	}


	// 标记位
	private boolean showFlag = true;

	/**
	 * 控制线面的内容是否显示
	 */
	private void controlItem() {
		if (!showFlag) {
			mLlShow.setVisibility(View.GONE);
			mTxtController.setText("委托完善↓");
			showFlag = true;
		} else {
			mLlShow.setVisibility(View.VISIBLE);
			mTxtController.setText("收起↑");
			showFlag = false;
		}
	}

	/**
	 * 进入提交成功界面
	 */
	private void supplyProvider() {
		
		// 供应商
		String providerName = mEdtProviderName.getText().toString();

		// 联系人姓名
		String contactNam = mEdtContactName.getText().toString();
		if (TextUtils.isEmpty(contactNam)) {
			displayToast("联系人不能为空");
			return;
		}
		// 联系电话号码
		String contactNumber = mEdtContactNumber.getText().toString();
		if (TextUtils.isEmpty(contactNumber)) {
			displayToast("联系方式不能为空");
			return;
		}
		if (!(Utils.isPhoneNum(contactNumber) && Utils
				.isPhoneNumberValid(contactNumber))) {
			displayToast("联系方式填写不正确");
			return;
		}
		// 需求描述
		String priceInfo = mEdtPriceInfo.getText().toString();
		if (TextUtils.isEmpty(priceInfo)) {
			displayToast("需求描述不能为空");
			return;
		}

		/*
		 * 判断数量-----------------------------------
		 */
		String weight = Utils
				.getNumberOfString(mEdtWeight.getText().toString());
		if (!TextUtils.isEmpty(weight)) {

			if (!"OK".equals(HintUtils.weightHint(weight))) {
				displayToast(HintUtils.weightHint(weight));
				return;
			}
		} else {
			weight = info.getGoodSWeight();
		}

		/*
		 * 判断价格-----------------------------------
		 */
		String price = Utils.getNumberOfString(mEdtPrice.getText().toString());

		if (!TextUtils.isEmpty(price)) {
			if (!"OK".equals(HintUtils.priceHint(price))) {
				displayToast(HintUtils.priceHint(price));
				return;
			}
		} else {
			price = info.getGoodSPrePrice();
		}

		
		GoodsSourceInfo sourceInfo = new GoodsSourceInfo();
		sourceInfo.setGoodSMerchant(providerName);// 公司名称
		sourceInfo.setGoodSContactPerson(contactNam);// 联系人姓名
		sourceInfo.setGoodSContactWay(contactNumber);// 联系方式
		sourceInfo.setGoodSPurchaseNeed(priceInfo);// 采购需求
		sourceInfo.setGoodSPrice(price);
		sourceInfo.setGoodSWeight(weight);
		// 联系人城市ID
		if (TextUtils.isEmpty(city)) {
			city = info.getCityid();
		}
		sourceInfo.setCityid(city);
		// 交割地
		if (TextUtils.isEmpty(devalite)) {
			devalite = info.getDeliverycityid();
		}
		sourceInfo.setDeliverycityid(devalite);
		sourceInfo.setAddress(mTxtAddress.getText().toString());
		
		loadingBar.setProgressInfo("加载中,请稍后...");
		loadingBar.setVisibility(View.VISIBLE);
		loadingBar.start();
		/**
		 * 提交数据
		 */
		JsonLoader jsonLoader = new JsonLoader(this);
		jsonLoader.setUrl(AppNetUrl.getDelSupplyUrl());
		jsonLoader.setMethod(BaseNetLoder.Method_Post);
		jsonLoader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		jsonLoader.setPostData(PurchaseSourceManager.getDelegateSupplyPostData(
				companyId, info, sourceInfo));
		jsonLoader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				displayToast(errorMessage);
				loadingBar.setVisibility(View.GONE);
				loadingBar.stop();
			}

			@Override
			public void onCompelete(Object arg0, Object result) {
				// TODO Auto-generated method stub
				JSONArray array = (JSONArray) result;
				// 服务器返回信息
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);
				loadingBar.setVisibility(View.GONE);
				loadingBar.stop();
				displayToast(info.getMsg());
				// 当成功时初始化界面
				if (1 == info.getStatus()) {
					goActivity(SucceeMainActivity.class, 1);
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);

	}
}
