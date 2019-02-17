package com.baiyi.cmall.activities.user.buyer.intention;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.activities.base.BaseMsgActivity;
import com.baiyi.cmall.activities.main._public.CitySelectionActivity;
import com.baiyi.cmall.activities.user.buyer.EditPurchaselActivity;
import com.baiyi.cmall.activities.user.buyer.HintUtils;
import com.baiyi.cmall.activities.user.buyer.form.FormStateUtils;
import com.baiyi.cmall.activities.user.buyer.intention.detail.DetailsProActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.request.manager.MyPurAttentionManager;
import com.baiyi.cmall.request.manager.NullJsonData;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.JsonParse_User;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.Utils;
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
 * �ҵ����-�ɹ�����-����Ŀ���-->�ɹ�����
 * 
 * @author lizl
 * 
 */
public class PurchaseIntentionOrderActivity extends BaseMsgActivity implements
		OnClickListener {

	// ȷ�ϲɹ�����
	private TextView mBtnSurePurchaseOrder;
	// ȡ���ɹ�����
	private TextView mBtnCancelPurchaseOrder;
	// ɾ���ɹ�����
	private TextView mBtnDeletePurchaseOrder;
	// �ܾ��ɹ�����
	private TextView mBtnRefushPurchaseOrder;
	// �µ�
	private TextView mBtnCommitPurchaseOrder;
	// �ɹ�����
	private GoodsSourceInfo purInfo;
	// ��Ӧ����
	private GoodsSourceInfo priInfo;

	/**
	 * �ɹ���Ϣ�ؼ�
	 */
	// ����
	private EditText mEtpurchasename;
	// ����
	private TextView mTxtOrderCategory;
	// ����
	private TextView mTxtOrderPlace;
	// Ʒ��
	private TextView mTxtOrderCoalType;
	// ����
	private EditText mEtOrderWeighView;
	// �۸�
	private EditText mEtOrderPrice;
	// Ԥ����
	private EditText mEtOrderPrePrice;
	// �����
	private TextView mTxtOrderDelivate;
	// ��ϸ����
	private EditText mEtOrderDetail;
	// �����ѡ��ؼ�
	private TableRow mTbrDelivery;
	// �����ID
	private String delivateId;
	/**
	 * ��Ӧ��Ϣ�ؼ�
	 */
	// ��Ӧ��
	private TextView mTxtProviderMerchant;
	// ����
	private TextView mTxtProviderCategory;
	// ����
	private TextView mTxtProviderPlace;
	// Ʒ��
	private TextView mTxtProviderCoalCategory;
	// ��������棩
	private TextView mTxtProviderWeight;
	// �۸�Χ
	private TextView mTxtProviderPrice;
	// Ԥ�����
	private TextView mTxtProviderPrePrice;
	// �����
	private TextView mTxtProviderDelivery;
	// ��ϸ����
	private TextView mTxtProviderDetail;

	// ����ID
	private int attentionID;
	// ״̬����
	private String stateName;
	// ����ͷ
	private String token;
	// ������
	private MyLoadingBar progressBar;
	// �ӷ��������ص�ʵ���࣬��������ɹ�model����Ӧmodel
	private GoodsSourceInfo arrayListInfo;
	// �ж��Ƿ��ǵ�һ�ν����ҳ��
	private boolean isFirst = true;
	// �ж���ʾ��Щ��ť
	private String binaryValue;
	// �ж��ܷ�༭���Ʊ�־λ
	boolean isEditPurName = true;
	// ��־�����˽����Ƿ�Ӷ���������ת������=-1���ǣ�
	private int formFlag;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);
		initTitle();
		loaderProgress();
		initData();
		initNetData();
	}

	/**
	 * ��ʼ������
	 */
	private void initData() {

		// ����ID
		attentionID = getIntent().getIntExtra("temp", 0);

		token = CmallApplication.getUserInfo().getToken();

		formFlag = getIntent().getIntExtra("id", 0);
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
						PurchaseIntentionOrderActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}

	/**
	 * �õ���Ӧ����
	 */
	private void initNetData() {

		JsonLoader jsonLoader = new JsonLoader(this);
		// ������---����ID
		jsonLoader.setUrl(AppNetUrl.getPurAttentionDetailUrl(attentionID));
		jsonLoader.setPostData(NullJsonData.getPostData());
		jsonLoader.addRequestHeader("token", token);
		jsonLoader.setMethod(BaseNetLoder.Method_Post);
		jsonLoader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				stopProgress();
				finish();
				displayToast(errorMessage);
			}

			@Override
			public void onCompelete(Object arg0, Object result) {

				stopProgress();
				JSONArray array = (JSONArray) result;

				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);
				// ��ȡ����ʧ�ܣ�����
				if (1 != info.getStatus()) {

					displayToast(info.getMsg());
					finish();
					return;
				}

				arrayListInfo = MyPurAttentionManager
						.getMyPurchaseAttentionDetail(array);

				// �ɹ���Ϣ
				purInfo = arrayListInfo.getPurSourceInfo();
				// �״λ�ȡ�����
				delivateId = purInfo.getDeliverycityid();
				// ��Ӧ��Ϣ
				priInfo = arrayListInfo.getProSourceInfo();
				// ��ť״̬�ж�ֵ
				binaryValue = arrayListInfo.getBinaryvalue();
				// ����״̬����
				stateName = arrayListInfo.getIntentionstatename();

				if (isFirst) {

					// ����ʾ��Ӧ�� ,����ʾ�ɹ���
					initProviderContent();
					initPurchaseContent();
					initFootButton();
					isFirst = false;
				} else {
					updatePurchaseView();
				}
			}

		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);

	}

	/**
	 * �ɹ���Ϣ
	 */
	private LinearLayout mLinPur;

	private void initPurchaseContent() {

		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_purchase_intention_order, null);
		win_content.addView(view);

		findPurchaseViewById(view);
		updatePurchaseView();
	}

	/**
	 * �Ҳɹ���Ϣ�Ŀؼ�
	 * 
	 * @param view
	 */
	private void findPurchaseViewById(View view) {

		mLinPur = (LinearLayout) view.findViewById(R.id.lin_pur_order_huan);
		mLinPur.setOnClickListener(this);

		mEtpurchasename = (EditText) view.findViewById(R.id.et_buy_name);
		mTxtOrderCategory = (TextView) view
				.findViewById(R.id.txt_order_category);
		mTxtOrderPlace = (TextView) view.findViewById(R.id.tv_place);
		mTxtOrderCoalType = (TextView) view
				.findViewById(R.id.tv_order_mei_zhong);
		mEtOrderWeighView = (EditText) view.findViewById(R.id.et_order_weight);
		mEtOrderPrice = (EditText) view.findViewById(R.id.et_order_price);
		mEtOrderPrePrice = (EditText) view.findViewById(R.id.et_yu_fu);
		mTxtOrderDelivate = (TextView) view.findViewById(R.id.tv_devilate);
		mEtOrderDetail = (EditText) view.findViewById(R.id.et_detail_content);
		mTbrDelivery = (TableRow) findViewById(R.id.tbr_devilate);

		mTbrDelivery.setOnClickListener(this);

		TextViewUtil.setText(R.id.purname, "����", view);
		TextViewUtil.setText(R.id.weight, "����", view);
		TextViewUtil.setText(R.id.price, "�۸�", view);
		TextViewUtil.setText(R.id.prepayment, "Ԥ�����", view);
		TextViewUtil.setText(R.id.delivery, "�����", view);

	}

	/**
	 * ���½�����Ϣ
	 */
	private void updatePurchaseView() {

		mEtpurchasename.setText(purInfo.getGoodSName());// ����
		mTxtOrderCategory.setText(purInfo.getGoodSCategory());// ����
		mTxtOrderPlace.setText(purInfo.getGoodSPlace());// ����
		mTxtOrderCoalType.setText(purInfo.getGoodSBrand());// Ʒ��
		mEtOrderWeighView.setText(purInfo.getGoodSWeight());// ����
		String price = Utils.twoDecimals(purInfo.getGoodSPrice());
		String prepayMent = Utils.twoDecimals(purInfo.getGoodSPrePrice());
		mEtOrderPrice.setText(price);// ����۸񣨵��ۣ�
		mEtOrderPrePrice.setText(prepayMent);// Ԥ����
		mTxtOrderDelivate.setText(purInfo.getGoodSDelivery());// �����
		mEtOrderDetail.setText(purInfo.getGoodSPurchaseContent());// ��ϸ����
	}

	/**
	 * ��Ӧ��Ϣ
	 */
	private LinearLayout mLinPro;

	@SuppressLint("ResourceAsColor")
	private void initProviderContent() {

		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_provider_intention_line, null);
		win_content.addView(view);

		if (null == priInfo) {

			/*
			 * ������ʾ���� ������
			 */
			TableLayout layout = (TableLayout) view
					.findViewById(R.id.tb_layout);
			layout.setVisibility(View.GONE);
			/*
			 * ���ù�Ӧ��Ϣ��ĿΪ��ɫ
			 */
			mLinPro = (LinearLayout) view.findViewById(R.id.ll_control);
			mLinPro.setEnabled(false);
			/*
			 * ��ʾ��δ�ָ�����
			 */
			TextView mTvReplyState = (TextView) view.findViewById(R.id.tv_tint);
			/*
			 * Ϊ0 ʱ��û���ݣ���ʾ״̬����
			 */
			if (!stateName.equals("����") && !stateName.equals("��ȡ��")) {

				mTvReplyState.setText(stateName + "������");
			}
			mTvReplyState.setVisibility(View.VISIBLE);
		} else {
			// �ҿؼ�
			findProviderViewById(view);
			// ������Ϣ
			updateProviderView();
		}
	}

	/**
	 * �ҹ�Ӧ��Ϣ�Ŀؼ�
	 * 
	 * @param view
	 */
	private void findProviderViewById(View view) {

		mLinPro = (LinearLayout) view.findViewById(R.id.ll_control);
		mLinPro.setOnClickListener(this);

		mTxtProviderMerchant = (TextView) view
				.findViewById(R.id.txt_pro_merchant);
		mTxtProviderCategory = (TextView) view
				.findViewById(R.id.txt_pro_category);
		mTxtProviderPlace = (TextView) view.findViewById(R.id.txt_pro_place);
		mTxtProviderCoalCategory = (TextView) view
				.findViewById(R.id.txt_pro_brand);
		mTxtProviderWeight = (TextView) view.findViewById(R.id.txt_pro_weight);
		mTxtProviderPrice = (TextView) view.findViewById(R.id.txt_pro_price);
		mTxtProviderPrePrice = (TextView) view
				.findViewById(R.id.txt_pro_pre_price);
		mTxtProviderDelivery = (TextView) view
				.findViewById(R.id.txt_pro_delivery);
		mTxtProviderDetail = (TextView) view.findViewById(R.id.txt_pro_detail);

	}

	/**
	 * ���¹�Ӧ��Ϣ�Ľ���
	 */
	private void updateProviderView() {

		mTxtProviderMerchant.setText(priInfo.getGoodSName());// ����
		mTxtProviderCategory.setText(priInfo.getGoodSCategory());// ����
		mTxtProviderPlace.setText(priInfo.getGoodSPlace());// ����
		mTxtProviderCoalCategory.setText(priInfo.getGoodSBrand());// Ʒ��
		mTxtProviderWeight.setText(priInfo.getGoodSWeight() + "��");// ����
		String price = Utils.twoDecimals(priInfo.getGoodSPrice());
		mTxtProviderPrice.setText(price + "Ԫ/��");// ����۸񣨵��ۣ�
		String prepayMent = Utils.twoDecimals(priInfo.getGoodSPrePrice());
		mTxtProviderPrePrice.setText(prepayMent + "Ԫ");// Ԥ����
		mTxtProviderDelivery.setText(priInfo.getGoodSDelivery());// �����
		mTxtProviderDetail.setText(priInfo.getGoodSPurchaseContent());// ��ϸ����

	}

	/**
	 * �ײ�������ť
	 */
	private void initFootButton() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_pur_state_foot, null);
		win_content.addView(view);
		View viewLine = view.findViewById(R.id.view_line);
		viewLine.setVisibility(View.GONE);
		mBtnSurePurchaseOrder = (TextView) view
				.findViewById(R.id.btn_sure_order);
		mBtnCancelPurchaseOrder = (TextView) view
				.findViewById(R.id.btn_cancel_order);
		mBtnDeletePurchaseOrder = (TextView) view
				.findViewById(R.id.btn_delete_order);
		mBtnRefushPurchaseOrder = (TextView) view
				.findViewById(R.id.btn_refush_order);
		mBtnCommitPurchaseOrder = (TextView) view
				.findViewById(R.id.btn_commit_order);

		/*
		 * �����е�����βŻ����µ���ť �����е����򵥣�ȷ�ϰ�ť����ʾ---��������
		 */
		if (stateName.equals("����")) {
			mBtnCommitPurchaseOrder.setVisibility(View.VISIBLE);
		}
		/*
		 * ֻ���Լ������������ڴ���״̬�²��ܱ༭���ƣ�����״̬�¾����ܱ༭
		 */
		if (!(stateName.equals("����") && null == priInfo)) {
			mEtpurchasename.setEnabled(false);
			isEditPurName = false;
		}

		mBtnSurePurchaseOrder.setVisibility(View.VISIBLE);
		// �Ƿ���ʾ�༭��ť
		if (View.VISIBLE == FormStateUtils.isShow(binaryValue,// �༭��ť
				FormStateUtils.STATE_EDIT)) {
			mBtnSurePurchaseOrder.setText("�༭");
		} else if (View.VISIBLE == FormStateUtils.isShow(binaryValue,// �ɹ���ť
				FormStateUtils.STATE_BUY)) {
			mBtnSurePurchaseOrder.setText("�ɹ�");
		} else if (View.VISIBLE == FormStateUtils.isShow(binaryValue,// ��������ť
				FormStateUtils.STATE_FASONG)) {
			mBtnSurePurchaseOrder.setText("��������");
		} else {
			mBtnSurePurchaseOrder.setVisibility(View.GONE);
			setNoEdit();
		}

		// ��ʾȡ���ɹ�����
		mBtnCancelPurchaseOrder.setVisibility(FormStateUtils.isShow(
				binaryValue, FormStateUtils.STATE_CANCEL));
		// ��ʾ�ܾ��ɹ�����
		mBtnRefushPurchaseOrder.setVisibility(FormStateUtils.isShow(
				binaryValue, FormStateUtils.STATE_JUJUE));
		// ��ʾɾ���ɹ�����
		mBtnDeletePurchaseOrder.setVisibility(FormStateUtils.isShow(
				binaryValue, FormStateUtils.STATE_DELETE));

		/*
		 * ״̬����Ϊ�գ�����Ӷ����б�����ת��������ʱ�ް�ť����
		 */
		if (-1 == formFlag) {
			win_content.removeView(view);
		}

		mBtnSurePurchaseOrder.setOnClickListener(this);
		mBtnCancelPurchaseOrder.setOnClickListener(this);
		mBtnDeletePurchaseOrder.setOnClickListener(this);
		mBtnRefushPurchaseOrder.setOnClickListener(this);
		mBtnCommitPurchaseOrder.setOnClickListener(this);
	}

	/**
	 * ��״̬��Ϊ����/���������������������Ϊ���ɲ���״̬
	 */
	private void setNoEdit() {
		mEtOrderWeighView.setEnabled(false);
		mEtOrderPrice.setEnabled(false);
		mEtOrderDetail.setEnabled(false);
		mEtOrderPrePrice.setEnabled(false);
		mTbrDelivery.setEnabled(false);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tbr_devilate:// �����
			Intent intent = new Intent(this, CitySelectionActivity.class);
			intent.putExtra("temp", mTxtOrderDelivate.getText().toString());
			startActivityForResult(intent, 1);
			break;
		case R.id.btn_sure_order:// ȷ������
			sureOrder();
			break;
		case R.id.btn_cancel_order:// ȡ������
			cancelOrder(AppNetUrl.getCancelPurAttentionUrl(attentionID));
			break;
		case R.id.btn_delete_order:// ɾ������
			cancelOrder(AppNetUrl.getDeletePurAttentionUrl(attentionID));
			break;
		case R.id.btn_refush_order:// �ܾ�����
			cancelOrder(AppNetUrl.getRefushPurAttentionUrl(attentionID));
			break;
		case R.id.btn_commit_order:// �µ�-- ֻ�вɹ����򵥲����µ�

			goActivity(PurchaseOrderDetailActivity.class, attentionID);
			finish();
			break;
		case R.id.lin_pur_order_huan:// �鿴�ɹ�����

			// ��1������:���ֲɹ��༭��������༭��trueΪ�ɹ���falseΪ����
			// ��2������:����ID
			// ��3������:������ʾ״̬������������
			// ��4������:�����Ƿ���Ա༭����
			goToActivity(EditPurchaselActivity.class, false, attentionID,
					binaryValue, isEditPurName);
			break;
		case R.id.ll_control:// �鿴��Ӧ����

			goActivity(DetailsProActivity.class, attentionID + "");
			break;
		}
	}

	/**
	 * ȡ��/ɾ��/�ܾ�---�ɹ�����
	 * 
	 * @param url
	 */
	private void cancelOrder(String url) {
		JsonLoader jsonLoader = new JsonLoader(this);

		jsonLoader.setUrl(url);
		jsonLoader.setPostData(NullJsonData.getPostData());
		jsonLoader.addRequestHeader("token", token);
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

				// ���������ؽ��
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);
				displayToast(info.getMsg());
				if (1 == info.getStatus()) {
					finish();
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);

	}

	/**
	 * ȷ������
	 */
	private void sureOrder() {

		GoodsSourceInfo editInfo = new GoodsSourceInfo();

		/**
		 * �ж�����
		 */
		String purName = mEtpurchasename.getText().toString();
		if (TextUtils.isEmpty(purName)) {
			displayToast("���Ʋ���Ϊ��");
			return;
		}
		editInfo.setGoodSName(purName);// ����

		/*
		 * �ж�����-----------------------------------
		 */
		String weight = Utils.getNumberOfString(mEtOrderWeighView.getText()
				.toString());
		if (!"OK".equals(HintUtils.weightHint(weight))) {
			displayToast(HintUtils.weightHint(weight));
			return;
		}
		editInfo.setGoodSWeight(weight);// ����

		/*
		 * �жϼ۸�-----------------------------------
		 */
		String price = Utils.getNumberOfString(mEtOrderPrice.getText()
				.toString());

		if (!"OK".equals(HintUtils.priceHint(price))) {
			displayToast(HintUtils.priceHint(price));
			return;
		}
		editInfo.setGoodSPrice(price);// �۸�

		/*
		 * �ж�Ԥ�����-----------------------------------
		 */
		String prepayment = Utils.getNumberOfString(mEtOrderPrePrice.getText()
				.toString());

		if (!"OK".equals(HintUtils.prePriceHint(prepayment))) {
			displayToast(HintUtils.prePriceHint(prepayment));
			return;
		}
		editInfo.setGoodSPrePrice(prepayment);// Ԥ����

		editInfo.setGoodSDelivery(mTxtOrderDelivate.getText().toString());// �����
		editInfo.setGoodSPurchaseContent(mEtOrderDetail.getText().toString());// ����
		editInfo.setDeliverycityid(delivateId);// �����ID

		JsonLoader jsonLoader = new JsonLoader(this);
		jsonLoader.setUrl(AppNetUrl.getEditPurAttentionUrl());
		jsonLoader.setPostData(MyPurAttentionManager
				.getEditPurAttentionPostData(attentionID, editInfo));
		jsonLoader.setMethod(BaseNetLoder.Method_Post);
		jsonLoader.addRequestHeader("token", token);
		jsonLoader.setType("application/json");
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
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);

				displayToast(info.getMsg());
				if (1 == info.getStatus()) {
					finish();
				}

			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		// �����־Ϊ100ʱ���༭�ɹ���ˢ�½���
		if (100 == resultCode) {
			initNetData();
			return;
		}
		if (null == data) {
			return;
		}
		SelectedInfo info = (SelectedInfo) data.getSerializableExtra("key");

		if (null != info) {

			mTxtOrderDelivate.setText(data.getStringExtra("text"));
			delivateId = info.getId();
		}
	}
}
