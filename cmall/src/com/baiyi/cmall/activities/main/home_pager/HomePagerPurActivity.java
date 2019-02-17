package com.baiyi.cmall.activities.main.home_pager;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
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
import com.baiyi.cmall.request.manager.ProviderSourceManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.DataUtils;
import com.baiyi.cmall.utils.MobileStateUtils;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.utils.XmlName;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.cmall.views.pulldownview.PullToRefreshListView;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase.Mode;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * ��Ӧ-ί�вɹ�
 * 
 * @author sunxy
 * 
 */
@SuppressLint("ClickableViewAccessibility")
public class HomePagerPurActivity extends BaseMsgActivity implements
		OnClickListener {

	// ��Ӧ��Ϣ��Ŀ
	private LinearLayout mLlSupplyInfo;
	// ���»������ϵļ�ͷ
	private ImageView mImgUpDown;

	// �ѹ�Ӧ��Ϣװ�ڼ�����
	private ArrayList<String> supplyInfos;

	// ����ʵ����
	private GoodsSourceInfo info;

	// �������ʾ���߲���ʾ
	boolean flag = true;

	private View listView;
	// �����Ӧ��Ϣ��Ŀʱ����ʾ��ListView
	private PullToRefreshListView mLstDetails;

	// �����ʵ��
	private EditText mEdtPurchaseName;// �̼�����
	private EditText mEdtContactName;// ��ϵ������
	private EditText mEdtContactNumber;// �绰����
	private EditText mEdtPurchaseNeed;// �ɹ�����

	// ��ť
	// �ύί�вɹ�
	private TextView mBtnSuuplyBuy;
	// ȡ��
	private TextView mBtnSuuplyCancel;

	// �û�ID
	private String userID;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);
		ActivityStackControlUtil.add(this);
		initData();
		initTitle();
		initContent();
		initInputContent();
		requestNet(null, 1);
	}

	/**
	 * ��ʼ��һЩ��������Ϣ
	 */
	private void initInputContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_delegate_input, null);
		win_content.addView(view);

		// ʵ����
		findEditTextViewById(view);

	}

	/**
	 * ���½���
	 */
	private void upDataView() {
		if (loginInfo != null) {
			mEdtContactName.setText(loginInfo.getUserName() == null ? ""
					: loginInfo.getUserName());
			mEdtContactNumber.setText(loginInfo.getMobile() == null ? ""
					: loginInfo.getMobile());
			mEdtPurchaseName.setText(loginInfo.getCompanyName() == null ? ""
					: loginInfo.getCompanyName());
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.list_item_pop, R.id.txt_item, supplyInfos);
		mLstDetails.setAdapter(adapter);
		mLlAddLayout
				.addView(listView, getScreenWidth(), MobileStateUtils
						.getTotalHeightofListView(adapter, mLstDetails));
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

	/**
	 * �ҿؼ�
	 * 
	 * @param view
	 */
	private void findEditTextViewById(View view) {
		mEdtContactName = (EditText) view.findViewById(R.id.edt_contact_name);
		mEdtContactNumber = (EditText) view
				.findViewById(R.id.edt_contact_number);
		mEdtPurchaseName = (EditText) view.findViewById(R.id.edt_supply_name);
		mEdtPurchaseNeed = (EditText) view.findViewById(R.id.edt_supply_need);

		mBtnSuuplyBuy = (TextView) view.findViewById(R.id.btn_commit_modify);
		mBtnSuuplyBuy.setText("�ύ�ɹ�ί��");
		mBtnSuuplyCancel = (TextView) view.findViewById(R.id.btn_cancel_modify);

		mBtnSuuplyBuy.setOnClickListener(this);
		mBtnSuuplyCancel.setOnClickListener(this);

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

		// ��ӡ�*��
		TextViewUtil.setText(R.id.contact, "��ϵ��", view);
		TextViewUtil.setText(R.id.mobile, "��ϵ��ʽ", view);
		// TextViewUtil.setText(R.id.txt_merchant_name, "�ɹ���", view);
		TextViewUtil.setText(R.id.txt_supply_price, "��������", view);

		mEdtPurchaseNeed.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				// ��仰˵����˼���߸�View���Լ����¼����Լ�����
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}

		});
	}

	private LoginInfo loginInfo;
	private String id;

	/**
	 * ������������뼯����
	 */
	private void initData() {

		id = getIntent().getStringExtra("arg");

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

	// �������
	private LinearLayout mLlAddLayout;

	/**
	 * ��ʼ����Ӧ��Ϣ��Ŀ
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_delegate_buy, null);

		win_content.addView(view);
		findProviderViewById(view);
	}

	// ��������ʾ��������
	private LinearLayout mLlControl;

	/**
	 * �ҹ�Ӧ��Ϣ�Ŀؼ�
	 * 
	 * @param view
	 */
	private void findProviderViewById(View view) {
		mLlAddLayout = (LinearLayout) view.findViewById(R.id.ll_add);

		listView = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.listview_not_divider, null);

		mLstDetails = (PullToRefreshListView) listView
				.findViewById(R.id.lst_in_details);
		mLlControl = (LinearLayout) listView.findViewById(R.id.ll_con);
		mLstDetails.setMode(Mode.DISABLED);
		mLlSupplyInfo = (LinearLayout) view.findViewById(R.id.ll_supply_info);
		mImgUpDown = (ImageView) view.findViewById(R.id.img_up_down);
		mLlSupplyInfo.setOnClickListener(this);
	}

	/**
	 * ��ʼ��������
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
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
				MsgItemUtil.onclickPopItem(state, HomePagerPurActivity.this);
			}
		});
		topTitleView.setEventName("ί�вɹ�");
		win_title.addView(topTitleView);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_supply_info:

			if (flag) {
				mLlControl.setVisibility(View.VISIBLE);
				mImgUpDown.setImageResource(R.drawable.up_arrow);
				flag = false;
			} else {
				mLlControl.setVisibility(View.GONE);
				mImgUpDown.setImageResource(R.drawable.down_arrow);
				flag = true;
			}
			break;
		case R.id.btn_commit_modify:// �ύ��ť
			supplyBuy();
			break;
		case R.id.btn_cancel_modify:// ȡ��
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
	 * ��Ӧ-ί�вɹ� �����ύ�ɹ�����
	 */
	private void supplyBuy() {
		// �ɹ�������
		String purchaseNam = mEdtPurchaseName.getText().toString();

		if (!Utils.isExceed_30(purchaseNam)) {
			displayToast("�ɹ������ƹ���");
			return;
		}
		// ��ϵ������
		String contactNam = mEdtContactName.getText().toString();
		if (TextUtils.isEmpty(contactNam)) {
			displayToast("��ϵ�˲���Ϊ��");
			return;
		}
		if (!Utils.isExceed_20(contactNam)) {
			displayToast("��ϵ�����ƹ���");
			return;
		}
		// ��ϵ�绰����
		String contactNumber = mEdtContactNumber.getText().toString().trim();
		if (TextUtils.isEmpty(contactNumber)) {
			displayToast("��ϵ��ʽ����Ϊ��");
			return;
		}
		if (!Utils.isExceed_20(contactNumber)) {
			displayToast("��ϵ��ʽ�ƹ���");
			return;
		}
		if (!(Utils.isPhoneNum(contactNumber) && Utils
				.isPhoneNumberValid(contactNumber))) {
			displayToast("��ϵ��ʽ��д����ȷ");
			return;
		}

		// �ɹ�����
		String purchaseNeed = mEdtPurchaseNeed.getText().toString();
		if (TextUtils.isEmpty(purchaseNeed)) {
			displayToast("������������Ϊ��");
			return;
		}
		if (!Utils.isExceed_1000(purchaseNeed)) {
			displayToast("������������");
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
			if (!Utils.isExceed_7(weight)) {
				displayToast("������д����");
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
			if (!Utils.isExceed_8(weight)) {
				displayToast("�۸���д����");
				return;
			}
		} else {
			price = info.getGoodSPrePrice();
		}

		GoodsSourceInfo sourceInfo = new GoodsSourceInfo();
		sourceInfo.setGoodSMerchant(purchaseNam);// ��˾����
		sourceInfo.setGoodSContactPerson(contactNam);// ��ϵ������
		sourceInfo.setGoodSContactWay(contactNumber);// ��ϵ��ʽ
		sourceInfo.setGoodSPurchaseNeed(purchaseNeed);// �ɹ�����
		sourceInfo.setGoodSPrice(price);//�۸�
		sourceInfo.setGoodSWeight(weight);//����
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

		requestNet(sourceInfo, 2);
	}

	/**
	 * ���������
	 * 
	 * @param info2
	 */
	private void requestNet(GoodsSourceInfo sourceInfo, final int state) {
		loaderProgress();

		final JsonLoader loader = new JsonLoader(this);
		// ��ȡ������ʾ
		if (1 == state) {
			loader.setUrl(AppNetUrl.getHomePurDetailUrl(id));
			loader.setPostData("");
		}
		// �ύ����
		if (2 == state) {
			loader.setUrl(AppNetUrl.getDetegationDetailUrl());
			loader.setPostData(ProviderSourceManager.getDetegationPostData(
					sourceInfo, info, userID));
		}
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				displayToast(arg2);
				stopProgress();
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				RequestNetResultInfo infos = ProviderSourceManager
						.getDetegationResult(arg1);
				stopProgress();

				switch (state) {
				case 1:
					Log.d("TAG", "case 1---" + arg1.toString());
					info = ProviderSourceManager.getHomePurDetailInfo(arg1);
					supplyInfos = DataUtils.getShowList(info);
					upDataView();
					break;
				case 2:
					if (1 == infos.getStatus()) {
						displayToast(infos.getMsg());
						goActivity(SucceeMainActivity.class, 0);
					} else {
						displayToast(infos.getMsg());
						return;
					}
					break;
				}
			}

		});
		CmallApplication.getDataStratey().startLoader(loader);
	}
}
