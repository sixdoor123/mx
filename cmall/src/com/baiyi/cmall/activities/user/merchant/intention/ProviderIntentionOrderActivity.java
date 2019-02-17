package com.baiyi.cmall.activities.user.merchant.intention;

import org.json.JSONObject;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TextView;

import com.baiyi.cmall.activities.base.BaseMsgActivity;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main._public.CitySelectionActivity;
import com.baiyi.cmall.activities.main._public.GoodSBrandActivity;
import com.baiyi.cmall.activities.main._public.GoodSCategoryActivity;
import com.baiyi.cmall.activities.user.buyer.HintUtils;
import com.baiyi.cmall.activities.user.merchant.IntentionOrderUtils;
import com.baiyi.cmall.activities.user.merchant.MyProviderDetailsActivity;
import com.baiyi.cmall.activities.user.merchant.provider.EditMyProviderStandardArgDetailActivity;
import com.baiyi.cmall.activities.user.merchant.provider.intention.EditIntentionProviderDetailActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.IntentionProviderDetailInfo;
import com.baiyi.cmall.entity.IntentionPurchaseDetailInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.entity._public.BrandEntities;
import com.baiyi.cmall.request.manager.MerchantCenterManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.utils.XmlUtils;
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
 * �ɹ�(��Ӧ)����
 * 
 * @author sunxy
 * 
 */
@SuppressLint("NewApi")
public class ProviderIntentionOrderActivity extends BaseMsgActivity implements
		OnClickListener {

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);
		// boolean isRefresh = getIntent().getBooleanExtra("isRefresh", false);
		initData();
		initTitle();
		loadData();
	}

	@Override
	protected void onStart() {
		super.onStart();
		// boolean isRefresh = getIntent().getBooleanExtra("isRefresh", false);
		// if (isRefresh) {
		// win_content.removeAllViews();
		// loadData();
		// }
	}

	// ��Ӧ��Ϣ�������ɹ���Ϣ��
	private IntentionProviderDetailInfo proinfo;
	private String token;

	/**
	 * ��������
	 */
	private void loadData() {
		final MyLoadingBar myLoadingBar = new MyLoadingBar(this);
		myLoadingBar.setPadding(0, getScreenHeight() / 3, 0, 0);
		myLoadingBar.setProgressInfo("������,���Ժ�...");
		myLoadingBar.start();
		win_content.addView(myLoadingBar);

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getProviderIntentionDetailsUrl(id));
		loader.setPostData(new JSONObject().toString());
		loader.addRequestHeader("token", token);
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
				finish();
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				proinfo = MerchantCenterManager
						.getProviderIntentationDetails(arg1);
				win_content.removeView(myLoadingBar);
				myLoadingBar.stop();

				if (null != proinfo) {
					win_content.removeAllViews();
					initPurchaseContent();
					initProviderContent();
					if (9 != state) {
						initFootButton();
					}
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	// �޸ĺ����Ϣ
	// private GoodsSourceInfo editInfo;

	// ����Դ
	private GoodsSourceInfo info;
	// id
	private String id;
	// 9�� ��ʾ�ӹ�Ӧ��������
	private int state;

	// ��������
	// private int intentionType;

	/**
	 * ����Դ
	 */
	private void initData() {

		info = (GoodsSourceInfo) getIntent().getSerializableExtra("key");
		if (null != info) {
			id = info.getGoodSID();
		} else {
			id = getIntent().getStringExtra("temp");
			state = getIntent().getIntExtra("state", 0);
		}
		token = CmallApplication.getUserInfo().getToken();
	}

	// ȷ������
	private TextView mBtnSureOrder;
	// �ܾ�����
	private TextView mBtnRefuseOrder;
	// ɾ��
	private TextView mBtnDeleteOrder;
	// ȡ������
	private TextView mBtnCancel;
	// ��������
	private TextView mBtnSendIntention;
	// ��Ӧ
	private TextView mBtnProvider;

	/**
	 * ��ť
	 */
	private void initFootButton() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_pur_foot, null);
		win_content.addView(view);
		View view1 = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_purchase_intention_foot_01, null);
		win_content.addView(view1);

		mBtnSureOrder = (TextView) view
				.findViewById(R.id.btn_sure_purchase_order);
		mBtnRefuseOrder = (TextView) view
				.findViewById(R.id.btn_cancel_purchase_order);
		mBtnDeleteOrder = (TextView) view
				.findViewById(R.id.btn_commit_purchase_order);

		mBtnSendIntention = (TextView) view1
				.findViewById(R.id.btn_sure_purchase_order_copy);

		// view1.findViewById(R.id.view_line).setVisibility(View.GONE);
		view.findViewById(R.id.view_black).setVisibility(View.GONE);
		mBtnCancel = (TextView) view1
				.findViewById(R.id.btn_cancel_purchase_order_copy);

		mBtnProvider = (TextView) view1
				.findViewById(R.id.btn_commit_purchase_order_copy);

		mBtnSureOrder.setText("�༭");
		mBtnDeleteOrder.setText("��������");
		mBtnRefuseOrder.setText("��Ӧ");

		mBtnSendIntention.setText("�ܾ�");
		mBtnProvider.setText("ɾ��");
		mBtnCancel.setText("ȡ��");

		mBtnCancel.setOnClickListener(this);
		mBtnRefuseOrder.setOnClickListener(this);
		mBtnSureOrder.setOnClickListener(this);
		mBtnDeleteOrder.setOnClickListener(this);
		mBtnProvider.setOnClickListener(this);
		mBtnSendIntention.setOnClickListener(this);

		if (null != proinfo) {
			buttonState(proinfo.getBinaryvalue());
		}
	}

	// �ж���һ���༭��ɰ�ť�Ƿ�
	private boolean isEditDetailEnable;
	// �ж������Ƿ�ɱ༭
	private boolean isProNameEdit;

	/**
	 * ���򵥰�ť״̬�ж�
	 * 
	 * @param intentionState
	 * @param deletebyuser
	 */
	private void buttonState(String binaryvalue) {
		int[] status = IntentionOrderUtils.getStatus(binaryvalue);
		if (null != status) {

			mBtnSureOrder.setVisibility(IntentionOrderUtils
					.isVisableOrGone(status[0]));
			mBtnCancel.setVisibility(IntentionOrderUtils
					.isVisableOrGone(status[1]));
			mBtnProvider.setVisibility(IntentionOrderUtils
					.isVisableOrGone(status[5]));
			mBtnSendIntention.setVisibility(IntentionOrderUtils
					.isVisableOrGone(status[3]));
			mBtnRefuseOrder.setVisibility(IntentionOrderUtils
					.isVisableOrGone(status[2]));
			mBtnDeleteOrder.setVisibility(IntentionOrderUtils
					.isVisableOrGone(status[4]));
			// ��һ���༭��ɰ�ť�Ƿ���ʾ
			isEditDetailEnable = IntentionOrderUtils.isEditDetailEnable(status);
		}
	}

	/**
	 * ��Ӧ��Ϣ
	 */
	private void initProviderContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_provider_intention_details, null);
		win_content.addView(view);
		// �ҿؼ�
		findProviderViewById(view);
		// ������Ϣ
		updateProviderView();
	}

	// ��Ӧ����
	private MyLoadingBar bar;
	// ����
	private EditText mEditProName;
	// ����
	private LinearLayout mLlProCategory;
	// ��¼ѡ�������
	private TextView mTxtCategory;
	// Ʒ��
	private LinearLayout mLlProCoalType;
	// ��¼Ʒ�Ƶ���Ϣ
	private TextView mTxtCoal;
	// ����
	private LinearLayout mLlProPlace;
	// ��¼����
	private TextView mTxtPlace;
	// ���
	private EditText mEdtInventory;
	// �۸�
	private EditText mEdtPrice;
	// Ԥ�����
	private EditText mEdtPrepayment;
	// �����
	private LinearLayout mLlProDelivery;
	// ��¼�����
	private TextView mTxtDelivery;
	// ��ϸ��Ϣ
	private EditText mTxtProDetails;
	// ��Ӧ��Ϣ��Ŀ������һ��Ŀ¼
	private LinearLayout mLlPro;

	/**
	 * �ҹ�Ӧ��Ϣ�Ŀؼ�
	 * 
	 * @param view
	 */
	private void findProviderViewById(View view) {
		mEditProName = (EditText) view
				.findViewById(R.id.edt_goods_provider_name);
		mLlProCategory = (LinearLayout) view
				.findViewById(R.id.ll_provider_categatory);
		mTxtCategory = (TextView) view.findViewById(R.id.txt_category);
		mLlProCoalType = (LinearLayout) view
				.findViewById(R.id.ll_provider_coal_type);
		mTxtCoal = (TextView) view.findViewById(R.id.txt_coal_type);
		mLlProPlace = (LinearLayout) view.findViewById(R.id.ll_provider_place);
		mTxtPlace = (TextView) view.findViewById(R.id.txt_place);
		mEdtInventory = (EditText) view
				.findViewById(R.id.edt_goods_provider_weight);
		mEdtPrice = (EditText) view.findViewById(R.id.edt_goods_provider_price);
		mEdtPrepayment = (EditText) view
				.findViewById(R.id.edt_goods_provider_prepayment);
		mLlProDelivery = (LinearLayout) view
				.findViewById(R.id.ll_provider_delivery);
		mTxtDelivery = (TextView) view.findViewById(R.id.txt_delivery);
		mTxtProDetails = (EditText) view.findViewById(R.id.txt_pro_details_01);

		bar = (MyLoadingBar) view.findViewById(R.id._load);

		mLlProCategory.setOnClickListener(this);
		mLlProCoalType.setOnClickListener(this);
		mLlProPlace.setOnClickListener(this);
		mLlProDelivery.setOnClickListener(this);

		mLlPro = (LinearLayout) view.findViewById(R.id.ll_control);
		mLlPro.setOnClickListener(this);

		mLlProCategory.setEnabled(false);
		mLlProPlace.setEnabled(false);
		mLlProCoalType.setEnabled(false);

		TextViewUtil.setText(R.id.pro_name, "����", view);
		TextViewUtil.setText(R.id.invo, "���", view);
		TextViewUtil.setText(R.id.price, "�۸�", view);
		TextViewUtil.setText(R.id.prepayment, "Ԥ�����", view);
		TextViewUtil.setText(R.id.delivery, "�����", view);

		if (null != info) {
			if (1 == info.getState() && purInfo == null) {
				mEditProName.setEnabled(true);
				isProNameEdit = true;
			} else {
				isProNameEdit = false;
				mEditProName.setEnabled(false);
			}

			if (IntentionOrderUtils.isInputProEnable(info.getType(),
					info.getState(), info.getDeletebyuser())) {
				orperatioInput();
			}
		} else if (9 == state) {
			orperatioInput();
		}

		mTxtProDetails.setOnTouchListener(new OnTouchListener() {

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
	 * ������ǽ������ߴ���״̬�²��ɱ༭
	 */
	private void orperatioInput() {
		mEditProName.setEnabled(false);
		mEdtInventory.setEnabled(false);
		mEdtPrice.setEnabled(false);
		mEdtPrepayment.setEnabled(false);
		mTxtProDetails.setEnabled(false);
		mLlProDelivery.setEnabled(false);
	}

	/**
	 * ���¹�Ӧ��Ϣ�Ľ���
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
			String price = Utils
					.twoDecimals(proinfo.getPrice().equals("null") ? String
							.valueOf(0) : proinfo.getPrice());
			if (price.startsWith(".")) {
				price = "0" + price;
			}
			mEdtPrice.setText(price);
			String prepayMent = Utils.twoDecimals(proinfo.getPrepayment()
					.equals("null") ? String.valueOf(0) : String
					.valueOf(proinfo.getPrepayment()));
			if (prepayMent.startsWith(".")) {
				prepayMent = "0" + prepayMent;
			}
			mEdtPrepayment.setText(prepayMent);

			mTxtDelivery.setText(proinfo.getDeliveryPlaceName());
			mTxtProDetails.setText(proinfo.getOfferDetail());
		}
	}

	/**
	 * �ɹ���Ϣ
	 */
	private void initPurchaseContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_purchase_intention, null);

		// �ҿؼ�
		findPurchaseViewById(view);
		// ���½�������
		updatePurchaseView(view);
	}

	// ��������
	private TextView mTxtOrderPurName;
	// ��Ʒ����
	private TextView mTxtOrderCategory;
	// Ʒ��
	private TextView mTxtOrderCoalType;
	// ����
	private TextView mTxtOrderPlace;
	// ����
	private TextView mTxtOrderWeight;
	// �۸�
	private TextView mTxtOrderPrePrice;
	// �����
	private TextView mTxtOrderDelivery;
	// Ԥ�����
	private TextView mTxtOrderPayment;
	// ��ϸ��Ϣ
	private TextView mTxtOrderDetails;

	// ����
	private MyLoadingBar loadingBar;

	// ���Ʋɹ���Ϣ��Ŀ
	private LinearLayout mLlPur;

	// û�вɹ���Ϣ����ʾ
	private TableLayout mLlNotShow;
	private TextView mTxtNoResponse;
	// ���ҵļ�ͷ
	private ImageView mImgright;

	/**
	 * �Ҳɹ���Ϣ�Ŀؼ�
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

		mLlNotShow = (TableLayout) view.findViewById(R.id.tab);
		mTxtNoResponse = (TextView) view.findViewById(R.id.txt_no_response);
		mImgright = (ImageView) view.findViewById(R.id.img_right_pur);

	}

	private IntentionPurchaseDetailInfo purInfo;
	private String purId = "";

	/**
	 * ���½�����Ϣ
	 * 
	 * @param info
	 */
	private void updatePurchaseView(View view) {
		if (null != proinfo) {
			purInfo = proinfo.getPurchaseDetailInfo();
			if (null != purInfo) {
				// purId = purInfo.getId();
				// if (!purId.equals("null")) {
				mTxtOrderPurName.setText(purInfo.getPurchasename());
				mTxtOrderCategory.setText(purInfo.getCategoryname());
				mTxtOrderCoalType.setText(purInfo.getBrandname());
				mTxtOrderPlace.setText(purInfo.getOriginplacename());
				mTxtOrderWeight.setText(purInfo.getAmount() + "��");
				String price = Utils.twoDecimals(purInfo.getPrice());
				if (price.startsWith(".")) {
					price = "0" + price;
				}
				mTxtOrderPrePrice.setText(price + "Ԫ/��");
				mTxtOrderDelivery.setText(purInfo.getDeliveryplacename());
				String prepayment = Utils.twoDecimals(purInfo.getPrepayment());
				if (prepayment.startsWith(".")) {
					prepayment = "0" + prepayment;
				}
				mTxtOrderPayment.setText(prepayment + "Ԫ");
				mTxtOrderDetails.setText(purInfo.getPurchasedetail());

				win_content.addView(view);
			} else {
				mLlNotShow.setVisibility(View.GONE);
				mLlPur.setEnabled(false);
				mImgright.setVisibility(View.GONE);
				win_content.addView(view);
				mTxtNoResponse.setVisibility(View.VISIBLE);
				if (9 == state) {
					mTxtNoResponse.setText(proinfo.getIntentionstatename()
							+ " ! ! !");
				} else {
					if (1 == info.getState() || -6 == info.getState()) {
						mTxtNoResponse.setText("��δ�ظ�" + " ! ! !");
					} else {
						mTxtNoResponse.setText(proinfo.getIntentionstatename()
								+ " ! ! !");
					}
				}
			}
		}
	}

	/**
	 * ������
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("��������");
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
						ProviderIntentionOrderActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_sure_purchase_order:// �༭
		case R.id.btn_cancel_purchase_order:// ��������
		case R.id.btn_commit_purchase_order:// ��Ӧ
			sureOrder();
			break;
		case R.id.btn_sure_purchase_order_copy:// �ܾ�
			refuseOrder();
			break;
		case R.id.btn_cancel_purchase_order_copy:// ȡ��
			cancelOrder();
			break;
		case R.id.btn_commit_purchase_order_copy:// ɾ������
			deleteOrder();
			break;
		case R.id.ll_provider_delivery:// �����
			goActivity(mTxtDelivery.getText().toString().trim(),
					CitySelectionActivity.class, 2);
			break;
		case R.id.ll_provider_coal_type:// Ʒ��
			goActivity(GoodSBrandActivity.class);
			break;
		case R.id.ll_provider_place:// ����
			goActivity(proinfo.getOriginPlaceName(),
					CitySelectionActivity.class, 1);
			break;
		case R.id.ll_provider_categatory:// ����
			goActivity(proinfo.getCategoryName(), GoodSCategoryActivity.class);
			break;
		case R.id.lin_pur_order_huan:// �ɹ���Ŀ������һ��
			goActivity(/* purInfo.getId() */id,
					MyProviderDetailsActivity.class);
			break;
		case R.id.ll_control:// ��Ӧ��Ŀ������һ��-- �༭
			String proId = proinfo.getId();
			if (proId.equals("null")) {
				// �������TagId
				String tagId = proinfo.getTargetId();
				goActivity(id, tagId,
						EditIntentionProviderDetailActivity.class, 1, 3,
						isEditDetailEnable, isProNameEdit);
			} else {
				// ������ǹ�Ӧ��Ϣ��id
				goActivity(id, proId,
						EditIntentionProviderDetailActivity.class, 1, 0,
						isEditDetailEnable, isProNameEdit);
			}
			break;

		}
	}

	/**
	 * ȡ������
	 */
	private void cancelOrder() {
		orderOpration(AppNetUrl.getIntentionOrderCancel(id), 0);
	}

	/**
	 * ɾ������
	 */
	private void deleteOrder() {
		orderOpration(AppNetUrl.getIntentionOrderdelete(id), 1);
	}

	/**
	 * �ܾ�����
	 */
	private void refuseOrder() {
		orderOpration(AppNetUrl.getIntentionOrderRefuse(id), 0);
	}

	/**
	 * �ܾ���ɾ��
	 * 
	 * @param url
	 */
	private void orderOpration(String url, final int state) {
		bar.setVisibility(View.VISIBLE);
		bar.setProgressInfo("���ڼ�����...");
		bar.start();

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(url);
		loader.setPostData(new JSONObject().toString());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.addRequestHeader("token", token);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
				bar.setProgressInfo("���ڽ���");
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				displayToast(arg2);
				bar.setVisibility(View.GONE);
				bar.stop();
			}

			@SuppressWarnings("unused")
			@Override
			public void onCompelete(Object arg0, Object arg1) {
				GoodsSourceInfo sourceInfo = MerchantCenterManager
						.getProviderIntentationSureInfo(arg1, state);
				bar.setVisibility(View.GONE);
				bar.stop();
				if (null != sourceInfo) {
					RequestNetResultInfo info = sourceInfo.getResultInfo();
					displayToast(info.getMsg());
					if (1 == info.getStatus()) {
						finish();
					}
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	// �����ʵ��
	private SelectedInfo deliveryInfo;
	// ����ʵ��
	private SelectedInfo areaInfo;
	// Ʒ�� Ʒ��
	private BrandEntities brandInfo;
	// ����
	private SelectedInfo categoryInfo;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 10) {
			// �༭��������Ժ󷵻� ˢ��
			loadData();
		} else if (resultCode == 9) {
			win_content.removeAllViews();
			loadData();
		} else if (resultCode == 1) {
			String text = data.getStringExtra("text");
			areaInfo = (SelectedInfo) data.getSerializableExtra("key");
			mTxtPlace.setText(text);
		} else if (resultCode == 2) {
			String text = data.getStringExtra("text");
			deliveryInfo = (SelectedInfo) data.getSerializableExtra("key");
			mTxtDelivery.setText(text);
		} else if (resultCode == 5) {
			brandInfo = (BrandEntities) data.getSerializableExtra("key");
			mTxtCoal.setText(brandInfo.getBrandName());
		} else if (resultCode == 4) {
			categoryInfo = (SelectedInfo) data.getSerializableExtra("key");
			String text = data.getStringExtra("text");
			mTxtCategory.setText(text);
		}
	}

	/**
	 * ȷ������
	 */
	private void sureOrder() {
		String proName = mEditProName.getText().toString().trim();
		if (TextUtils.isEmpty(proName)) {
			displayToast("���Ʋ���Ϊ��");
			return;
		}
		if (!Utils.isExceed_30(proName)) {
			displayToast("������д����");
			return ;
		}
		String detail = mTxtProDetails.getText().toString().trim();
		
		String inventory = mEdtInventory.getText().toString().trim();
		inventory = Utils.getNumberOfString(inventory);
		if (!"OK".equals(HintUtils.weightHint(inventory))) {
			displayToast(HintUtils.weightHint(inventory));
			return;
		}
		if (!Utils.isExceed_7(inventory)) {
			displayToast("�����д����");
			return;
		}
		String price = mEdtPrice.getText().toString().trim();
		price = Utils.getNumberOfString(price);
		if (!"OK".equals(HintUtils.priceHint(price))) {
			displayToast(HintUtils.priceHint(price));
			return;
		}
		if (!Utils.isExceed_8(price)) {
			displayToast("�۸���д����");
			return;
		}

		String prepayment = mEdtPrepayment.getText().toString().trim();
		prepayment = Utils.getNumberOfString(prepayment);
		if (!"OK".equals(HintUtils.prePriceHint(prepayment))) {
			displayToast(HintUtils.prePriceHint(prepayment));
			return;
		}
		if (!Utils.isExceed_10(prepayment)) {
			displayToast("�۸���д����");
			return;
		}

		if (!Utils.isExceed_1000(detail)) {
			displayToast("��ϸ������д����");
			return;
		}

		commit(inventory, proName, inventory, price,
				prepayment, detail);
	}

	/**
	 * �ύ
	 * 
	 * @param number
	 * @param contact
	 * @param sumMoney
	 * @param weight
	 */
	private void commit(String weight, String proName, String inventory,
			String price, String prepayment, String detail) {

		bar.setVisibility(View.VISIBLE);
		bar.setProgressInfo("������,���Ժ�...");
		bar.start();

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getMyProviderIntentionSureUrl());
		loader.setPostData(MerchantCenterManager
				.getSureIntentionOrderPostDates(deliveryInfo, areaInfo,
						brandInfo, categoryInfo, proinfo, id, weight, proName,
						inventory, price, prepayment, detail));
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.addRequestHeader("token", token);
		loader.setType("application/json");
		loader.setLoaderListener(new LoaderListener() {
			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				bar.setVisibility(View.GONE);
				bar.stop();
				displayToast(arg2);
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				GoodsSourceInfo sourceInfo = MerchantCenterManager
						.getProviderIntentationSureInfo(arg1, 0);
				bar.setVisibility(View.GONE);
				bar.stop();

				if (null != sourceInfo) {
					RequestNetResultInfo info = sourceInfo.getResultInfo();
					displayToast(info.getMsg());
					if (1 == info.getStatus()) {
						finish();
					}
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}
}
