package com.baiyi.cmall.activities.user.buyer.intention;

import org.json.JSONArray;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.activities.base.BaseMsgActivity;
import com.baiyi.cmall.activities.main._public.CitySelectionActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.OrderEntity;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.request.manager.MyPurAttentionManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.JsonParse_User;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 添加新的收货人信息
 * 
 * @author Administrator
 * 
 */
public class AddNewConsigneeActivity extends BaseMsgActivity implements
		OnClickListener {

	// 收货人
	private EditText mEtPersonName;
	// 手机号码
	private EditText mEtPhoneNum;
	// 地区
	private TextView mTvCity;
	// 地址详情
	private EditText mEtAddressDetail;
	// 地址详情
	private CheckBox mCbIsDefault;
	// 地址选择
	private TableRow mTbCity;
	// 保存按钮
	private TextView mBtSave;

	// 判断从哪个页面跳转过
	private int comeState;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);
		initData();
		initTitle();
		setContent();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {

		comeState = getIntent().getIntExtra("state", 0);
	}

	/**
	 * 初始化标题信息
	 */
	private void initTitle() {
		EventTopTitleView topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("新增收货地址");
		win_title.addView(topTitleView);

		// 在此不显示更多按钮，所以将其隐藏
		FrameLayout frameLayout = (FrameLayout) findViewById(R.id.msg_layout);
		frameLayout.setVisibility(View.INVISIBLE);
	}

	/**
	 * 设置内容
	 */
	private void setContent() {

		ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_edit_consignee, win_content);

		mEtPersonName = (EditText) findViewById(R.id.edt_person_name);
		mEtPhoneNum = (EditText) findViewById(R.id.edt_phone_num);
		mTvCity = (TextView) findViewById(R.id.tv_edit_city);
		mEtAddressDetail = (EditText) findViewById(R.id.edt_edit_address);
		mCbIsDefault = (CheckBox) findViewById(R.id.rb_is_default);
		mTbCity = (TableRow) findViewById(R.id.trb_city);
		mTbCity.setOnClickListener(this);
		mBtSave = (TextView) findViewById(R.id.btn_save);
		mBtSave.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.trb_city:

			Intent intent = new Intent(this, CitySelectionActivity.class);
			intent.putExtra("temp", mTvCity.getText().toString());
			startActivityForResult(intent, 1);

			break;
		case R.id.btn_save:// 保存添加的收貨人信息

			getAddInfo();
			break;

		default:
			break;
		}
	}

	// 收货人
	private String personName;
	// 手机号码
	private String phoneNum;
	// 地区ID
	private String city;
	// 地区名称
	private String cityName;
	// 地址详情
	private String addressDetail;
	// 是否默认
	private boolean isDefault;

	// 用戶ID获取
	private String userId;

	/**
	 * 获取填写的收货人信息
	 */
	private void getAddInfo() {

		userId = CmallApplication.getUserInfo().getUserID();

		personName = mEtPersonName.getText().toString();
		phoneNum = mEtPhoneNum.getText().toString();
		addressDetail = mEtAddressDetail.getText().toString();
		isDefault = mCbIsDefault.isChecked();
		/*
		 * 判断输入的信息是否合格
		 */
		if (TextUtils.isEmpty(personName)) {
			displayToast("收货人不能为空");
			return;
		} else if (personName.length() > 50) {
			displayToast("收获人输入过长");
			return;
		}
		if (TextUtils.isEmpty(phoneNum)) {
			displayToast("手机号码不能为空");
			return;
		} else if (!(Utils.isPhoneNum(phoneNum) && Utils
				.isPhoneNumberValid(phoneNum))) {
			displayToast("手机号码填写不正确");
			return;
		}
		if (TextUtils.isEmpty(city)) {
			displayToast("请选择地区");
			return;
		} 
		if (TextUtils.isEmpty(addressDetail)) {
			displayToast("详细地址不能为空");
			return;
		} else if (addressDetail.length() > 50) {
			displayToast("详细地址输入过长");
			return;
		}

		saveInfo();
	}

	/**
	 * 保存操作
	 */
	private void saveInfo() {

		JsonLoader jsonLoader = new JsonLoader(this);
		jsonLoader.setUrl(AppNetUrl.getAddConsigneeUrl());
		jsonLoader.setPostData(MyPurAttentionManager.getReceiverPostData(
				userId, personName, city, addressDetail, phoneNum, isDefault));
		jsonLoader.setMethod(BaseNetLoder.Method_Post);
		jsonLoader.addRequestHeader("token", CmallApplication.getUserInfo()
				.getToken());
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

				// 服务器返回结果
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);

				if (1 == info.getStatus()) {

					/*
					 * 状态为0说明从收货人列表跳转过来
					 * 状态不为0说明从提交订单页面跳转过来 
					 */
					if (0 == comeState) {
						finish();
					} else {
						/*
						 * 构造一个model传值
						 */
						OrderEntity entity = new OrderEntity();
						entity.setReceivername(personName);
						entity.setOrderCityId(city);
						entity.setOrderCity(cityName);
						entity.setAddress(addressDetail);
						entity.setPhone(phoneNum);
						// 传值操作
						Intent intent = new Intent();
						intent.putExtra("info", entity);
						setResult(NumEntity.RESULT_CONSIGNEE, intent);
						finish();
					}
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (null == data) {
			return;
		}
		SelectedInfo info = (SelectedInfo) data.getSerializableExtra("key");
		if (null != info) {
			city = info.getId();
			cityName = data.getStringExtra("text");
			mTvCity.setText(cityName);
		}
	}
}
