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
 * �༭ί�вɹ�����
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-17 ����2:20:09
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
	 * ��ť
	 */
	private void initButton() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_button, null);
		win_content.addView(view);
		mBtnCompleted = (TextView) view.findViewById(R.id.btn_commit_modify);
		mBtnCompleted.setText("����ί��");
		mBtnCompleted.setOnClickListener(this);
		TextView mTextView = (TextView) view
				.findViewById(R.id.btn_cancel_modify);
		mTextView.setVisibility(View.GONE);
	}

	// ����Դ
	private GoodsSourceInfo info;
	// ״̬
	// 1:��ί�й�Ӧ��
	// 0:��ί�вɹ���
	private int state;

	/**
	 * ��������
	 */
	private void initData() {
		info = (GoodsSourceInfo) getIntent().getSerializableExtra("key");
		state = getIntent().getIntExtra("temp", 0);
	}

	// �Զ������
	private EventTopTitleView topTitleView;

	/**
	 * ������
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		if (1 == state) {
			topTitleView.setEventName("�༭ί�й�Ӧ");
		} else {
			topTitleView.setEventName("�༭ί�вɹ�");
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
	 * ��������
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_edit_delegation_pur_details, null);

		win_content.addView(view);

		findViewById(view);
		updateViewData();
	}

	// ��Ʒ����
	private EditText mEdtTitle;
	// �̼�����
	private EditText mEdtMerchant;
	// ��ϵ��
	private EditText mEdtContact;
	// ��ϵ��ʽ
	private EditText mEdtContactWay;
	// ��������
	private EditText mEdtNeedContent;
	// ����������Ϣ�Ƿ���ʾ
	private LinearLayout mLlShow;
	// ����
	private TextView mTxtController;
	// ��ϵ�˳���
	private TableRow mTbPersonCity;
	// ��ʾ��ϵ�˳���
	private TextView mTxtPersonCity;
	// ��ϵ��ַ
	private TableRow mTbAddress;
	// ��ʾ��ϵ��ַ
	private EditText mTxtAddress;
	// ����
	private TableRow mTbCategory;
	// ��ʾ����
	private TextView mTxtCategory;
	// �����
	private TableRow mTbDelivery;
	// ��ʾ�����
	private TextView mTxtDelivery;
	// ����
	private TableRow mTbPlace;
	// ��ʾ����
	private TextView mTxtPlace;
	// ��ʾƷ��
	private EditText mTxtBrand;
	// ����
	private EditText mEdtWeight;
	// �۸�
	private EditText mEdtPrice;

	// ����ί�а�ť
	private TextView mBtnCompleted;
	// ����
	private MyLoadingBar loadingBar;

	/**
	 * �ҿؼ�
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

		// ��ӡ�*��
		TextViewUtil.setText(R.id.contact, "��ϵ��", view);
		TextViewUtil.setText(R.id.mobile, "��ϵ��ʽ", view);
		TextViewUtil.setText(R.id.title, "����", view);
		TextViewUtil.setText(R.id.content, "��������", view);
		// TextViewUtil.setText(R.id.company, "��˾����", view);

		mEdtNeedContent.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				// ��仰˵����˼���߸�View���Լ����¼����Լ�����
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}

		});
	}

	/**
	 * ���½�������
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
			// ��������
			mEdtWeight
					.setText("0".equals(getEditString(info.getGoodSWeight())) ? ""
							: getEditString(info.getGoodSWeight()));
			// ���ü�Ǯ
			String price = Utils.twoDecimals(info.getGoodSPrePrice());
			if ("0".equals(price) || "0.00".equals(price)) {
				price = "";
			}
			mEdtPrice.setText(price);
		}
	}

	private String getSelectString(String s) {

		if ("null".equals(s) || null == s || "".equals(s)) {
			return "��ѡ��";
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
	 * ����¼�
	 * 
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_commit_modify:// ���
			editCompeleted();
			break;
		case R.id.tb_category:// ����
			goActivity(mTxtCategory.getText().toString().trim(),
					GoodSCategoryActivity.class);
			break;
		// case R.id.tb_brand:// Ʒ��
		// goActivity(GoodSBrandActivity.class);
		// break;
		case R.id.txt_control:// ��������������Ƿ���ʾ
			controlItem();
			break;
		case R.id.tb_person_city:// ��ϵ�˳���
			goActivity(mTxtPersonCity.getText().toString().trim(),
					CitySelectionActivity.class, 1);
			break;
		// case R.id.tb_address:// ��ϵ��ַ
		// goActivity(info != null ? info.getAddress() : "",
		// CitySelectionActivity.class, 2);
		// break;
		case R.id.tb_delivery:// �����
			goActivity(mTxtDelivery.getText().toString().trim(),
					CitySelectionActivity.class, 3);
			break;
		case R.id.tb_place:// ����
			goActivity(mTxtPlace.getText().toString().trim(),
					CitySelectionActivity.class, 6);
			break;
		}
	}

	// ���� 
	private SelectedInfo categoryInfo;
	// Ʒ��
	// private BrandEntities brandInfo;
	// ��ϵ�˳���
	private SelectedInfo personCityInfo;
	// ��ϵ�˵�ַ
	private SelectedInfo addressInfo;
	// ��ϵ�˵���ϸ��ַ
	private String address;
	// �����
	private SelectedInfo deliveryInfo;
	// ����
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

	// ���λ
	private boolean flag = true;

	/**
	 * ��������������Ƿ���ʾ
	 */
	private void controlItem() {
		if (!flag) {
			mLlShow.setVisibility(View.GONE);
			mTxtController.setText("ί�����ơ�");
			flag = true;
		} else {
			mLlShow.setVisibility(View.VISIBLE);
			mTxtController.setText("�����");
			flag = false;
		}
	}

	/**
	 * ���
	 */
	private void editCompeleted() {
		String goodSName = mEdtTitle.getText().toString().trim();
		if (TextUtils.isEmpty(goodSName)) {
			displayToast("���ⲻ��Ϊ��");
			return;
		}
		if (!Utils.isExceed_30(goodSName)) {
			displayToast("�����������");
			return;
		}
		String merchaseName = mEdtMerchant.getText().toString().trim();
		if (!Utils.isExceed_30(merchaseName)) {
			displayToast("��˾�����������");
			return;
		}
		/*
		 * �ж�����-----------------------------------
		 */
		String weight = Utils
				.getNumberOfString(mEdtWeight.getText().toString());
		if (!"OK".equals(HintUtils.getWeight(weight))) {
			displayToast(HintUtils.getWeight(weight));
			return;
		}
		if (!Utils.isExceed_7(weight)) {
			displayToast("�����������");
			return;
		}
		String contact = mEdtContact.getText().toString().trim();
		if (TextUtils.isEmpty(contact)) {
			displayToast("��ϵ�˲���Ϊ��");
			return;
		}
		if (!Utils.isExceed_20(contact)) {
			displayToast("��ϵ���������");
			return;
		}
		String number = mEdtContactWay.getText().toString().trim();
		if (TextUtils.isEmpty(number)) {
			displayToast("��ϵ��ʽ����Ϊ��");
			return;
		}
		if (!Utils.isExceed_20(number)) {
			displayToast("��ϵ��ʽ�������");
			return;
		}
		if (!(Utils.isPhoneNum(number) && Utils.isPhoneNumberValid(number))) {
			displayToast("��ϵ��ʽ��д����ȷ");
			return;
		}

		String needContent = mEdtNeedContent.getText().toString().trim();
		if (TextUtils.isEmpty(needContent)) {
			displayToast("������������Ϊ��");
			return;
		}
		if (!Utils.isExceed_1000(needContent)) {
			displayToast("���������������");
			return;
		}

		/*
		 * �жϼ۸�-----------------------------------
		 */
		String price = Utils.getNumberOfString(mEdtPrice.getText().toString());

		if (!"OK".equals(HintUtils.getPrice(price))) {
			displayToast(HintUtils.getPrice(price));
			return;
		}
		if (!Utils.isExceed_8(price)) {
			displayToast("�۸��������");
			return;
		}

		String brand = mTxtBrand.getText().toString().trim();
		if (!Utils.isExceed_20(brand)) {
			displayToast("Ʒ���������");
			return;
		}
		String personDetailAddress = mTxtAddress.getText().toString().trim();
		if (!Utils.isExceed_30(brand)) {
			displayToast("��ϵ����ϸ��ַ�������");
			return;
		}

		saveModefied(goodSName, merchaseName, weight, contact, number,
				needContent, price, brand, personDetailAddress);
	}

	/**
	 * �����޸�
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
		mTxtController.setText("����ί�С�");
		flag = false;

		loadingBar.setVisibility(View.VISIBLE);
		loadingBar.setProgressInfo("���ڼ�����...");
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
				loadingBar.setProgressInfo("���ڽ���...");
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
	 * ������һ��
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
