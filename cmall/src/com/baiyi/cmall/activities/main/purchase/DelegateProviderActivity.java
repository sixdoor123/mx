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
 * ί�й�Ӧ����
 * 
 * @author sunxy
 * 
 */
public class DelegateProviderActivity extends BaseMsgActivity implements
		OnClickListener {

	// ����Դ
	private GoodsSourceInfo info;

	// ��Ӧ��Ϣ��Ŀ
	private LinearLayout mLlSupplyInfo;
	// ���»������ϵļ�ͷ
	private ImageView mImgUpDown;
	// �������ʾ���߲���ʾ
	boolean flag = true;

	// �����Ӧ��Ϣ��Ŀʱ����ʾ��ListView
	private PullToRefreshListView mLstDetails;

	// �ѹ�Ӧ��Ϣװ�ڼ�����
	private ArrayList<String> providerInfos;

	// �����ʵ��
	private EditText mEdtProviderName;// ��Ӧ������
	private EditText mEdtContactName;// ��ϵ������
	private EditText mEdtContactNumber;// �绰����
	private EditText mEdtPriceInfo;// ������Ϣ
	// ��ť
	// �ύ��Ӧί��
	private TextView mBtnProvider;
	// ȡ��
	private TextView mBtnProviderCancel;
	// ��˾ID
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
	 * ��ʼ���̼�ID
	 */
	private void initCompanyId() {
		companyId = CmallApplication.getUserInfo().getCompanyID();
	}

	private LoginInfo loginInfo;
	private String userID;

	/**
	 * �õ�����
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
	 * ����
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("ί�й�Ӧ");
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

	// �������
	private LinearLayout mLlAddLayout;

	/**
	 * ��ʼ����Ӧ��Ϣ��Ŀ
	 */
	@SuppressWarnings("unchecked")
	private void initContent() {

		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_delegate_provider, null);
		win_content.addView(view);

		// ������Ӿ������ϸ����
		mLlAddLayout = (LinearLayout) view.findViewById(R.id.ll_pur_add);
		// ��ť���л�����ʾ/���� ��
		mLlSupplyInfo = (LinearLayout) view.findViewById(R.id.ll_provider_info);
		// ָʾ��ͷ
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
	 * ��ʼ��һЩ��������Ϣ
	 */
	private void initInputContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_delegate_input, null);
		win_content.addView(view);

		// ʵ����
		findViewById(view);
		updataView();
	}

	/**
	 * ����������е�����
	 */
	private void updataView() {
		if (null != loginInfo) {
			mEdtProviderName.setText(loginInfo.getCompanyName());// ��Ӧ������
			mEdtContactName.setText(loginInfo.getUserName());// ��ϵ������
			mEdtContactNumber.setText(loginInfo.getMobile());// �绰����
			// mEdtPriceInfo.setText();// ������Ϣ
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
			// ���ÿ�ʼʱ��
			// if (Utils.getLongTime1(info.getGoodSPutTime()) <= 0) {
			// mTxtStartTime.setText("");
			// } else {
			// // mTxtStartTime.setText(info.getGoodSPutTime());
			// }
			// // ���ý���ʱ��
			// if (Utils.getLongTime1(info.getGoodSEndTime()) <= 0) {
			// // mTxtEndTime.setText("");
			// } else {
			// mTxtEndTime.setText(info.getGoodSEndTime());
			// }
		}
	}

	// ����������Ϣ�Ƿ���ʾ
	private LinearLayout mLlShow;
	// ��ʾί�����ƻ�������
	private TextView mTxtController;
	// ��ϵ�˳���
	private TableRow mTbPersonCity;
	// ��ʾ��ϵ�˳���
	private TextView mTxtPersonCity;
	// ��ϵ��ַ
	private TableRow mTbAddress;
	// ��ʾ��ϵ��ַ
	private EditText mTxtAddress;
	// ��ʾ����
	private TextView mTxtCategory;
	// �����
	private TableRow mTbDelivery;
	// ��ʾ�����
	private TextView mTxtDelivery;
	// ��ʾ����
	private TextView mTxtPlace;
	// ��ʾƷ��
	private TextView mTxtBrand;
	// ����
	private EditText mEdtWeight;
	// �۸�
	private EditText mEdtPrice;
	// ����
	private MyLoadingBar loadingBar;

	
	/**
	 * �ҿؼ�
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
				// ��仰˵����˼���߸�View���Լ����¼����Լ�����
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}

		});
		TextView mTxtMerchantNmae = (TextView) view
				.findViewById(R.id.txt_merchant_name);
		mTxtMerchantNmae.setText("��Ӧ��");
		TextView mTxtSupplyPrice = (TextView) view
				.findViewById(R.id.txt_supply_price);

		mBtnProvider = (TextView) view.findViewById(R.id.btn_commit_modify);
		mBtnProviderCancel = (TextView) view
				.findViewById(R.id.btn_cancel_modify);
		mBtnProvider.setText("�ύ��Ӧί��");

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

		TextViewUtil.setText(R.id.contact, "��ϵ��", view);
		TextViewUtil.setText(R.id.mobile, "��ϵ��ʽ", view);
		// TextViewUtil.setText(R.id.txt_merchant_name, "��Ӧ��", view);
		TextViewUtil.setText(R.id.txt_supply_price, "��������", view);
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
		case R.id.btn_commit_modify:// �ύί�й�Ӧ
			supplyProvider();
			break;
		case R.id.btn_cancel_modify:// ȡ����ť�ĵ���¼�
			finish();
			break;
		case R.id.txt_control:// ��������������Ƿ���ʾ
			controlItem();
			break;
		case R.id.tb_delivery:// �����
			goActivity(mTxtDelivery.getText().toString().trim(),
					CitySelectionActivity.class, 3);
			break;
		case R.id.tb_person_city:// ѡ�����
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

			// ��ϵ�˳���
			if (resultCode == 1) {
				String text = data.getStringExtra("text");
				city = info.getId();
				mTxtPersonCity.setText(text);
			}

			// �����
			if (resultCode == 3) {
				String text = data.getStringExtra("text");
				devalite = info.getId();
				mTxtDelivery.setText(text);
			}
		}
	}


	// ���λ
	private boolean showFlag = true;

	/**
	 * ��������������Ƿ���ʾ
	 */
	private void controlItem() {
		if (!showFlag) {
			mLlShow.setVisibility(View.GONE);
			mTxtController.setText("ί�����ơ�");
			showFlag = true;
		} else {
			mLlShow.setVisibility(View.VISIBLE);
			mTxtController.setText("�����");
			showFlag = false;
		}
	}

	/**
	 * �����ύ�ɹ�����
	 */
	private void supplyProvider() {
		
		// ��Ӧ��
		String providerName = mEdtProviderName.getText().toString();

		// ��ϵ������
		String contactNam = mEdtContactName.getText().toString();
		if (TextUtils.isEmpty(contactNam)) {
			displayToast("��ϵ�˲���Ϊ��");
			return;
		}
		// ��ϵ�绰����
		String contactNumber = mEdtContactNumber.getText().toString();
		if (TextUtils.isEmpty(contactNumber)) {
			displayToast("��ϵ��ʽ����Ϊ��");
			return;
		}
		if (!(Utils.isPhoneNum(contactNumber) && Utils
				.isPhoneNumberValid(contactNumber))) {
			displayToast("��ϵ��ʽ��д����ȷ");
			return;
		}
		// ��������
		String priceInfo = mEdtPriceInfo.getText().toString();
		if (TextUtils.isEmpty(priceInfo)) {
			displayToast("������������Ϊ��");
			return;
		}

		/*
		 * �ж�����-----------------------------------
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
		 * �жϼ۸�-----------------------------------
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
		sourceInfo.setGoodSMerchant(providerName);// ��˾����
		sourceInfo.setGoodSContactPerson(contactNam);// ��ϵ������
		sourceInfo.setGoodSContactWay(contactNumber);// ��ϵ��ʽ
		sourceInfo.setGoodSPurchaseNeed(priceInfo);// �ɹ�����
		sourceInfo.setGoodSPrice(price);
		sourceInfo.setGoodSWeight(weight);
		// ��ϵ�˳���ID
		if (TextUtils.isEmpty(city)) {
			city = info.getCityid();
		}
		sourceInfo.setCityid(city);
		// �����
		if (TextUtils.isEmpty(devalite)) {
			devalite = info.getDeliverycityid();
		}
		sourceInfo.setDeliverycityid(devalite);
		sourceInfo.setAddress(mTxtAddress.getText().toString());
		
		loadingBar.setProgressInfo("������,���Ժ�...");
		loadingBar.setVisibility(View.VISIBLE);
		loadingBar.start();
		/**
		 * �ύ����
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
				// ������������Ϣ
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);
				loadingBar.setVisibility(View.GONE);
				loadingBar.stop();
				displayToast(info.getMsg());
				// ���ɹ�ʱ��ʼ������
				if (1 == info.getStatus()) {
					goActivity(SucceeMainActivity.class, 1);
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);

	}
}
