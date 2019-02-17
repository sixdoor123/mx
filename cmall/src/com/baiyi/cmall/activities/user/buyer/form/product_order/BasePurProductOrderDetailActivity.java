package com.baiyi.cmall.activities.user.buyer.form.product_order;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.home_pager.MainActivity;
import com.baiyi.cmall.activities.user.buyer.intention.PurchaseIntentionOrderActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.OrderEntity;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.request.manager.MyPurFormManager;
import com.baiyi.cmall.request.manager.NullJsonData;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.IntentClassChangeUtils;
import com.baiyi.cmall.utils.JsonParse_User;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.cmall.views.LoadingBar;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * �ҵĶ���-�ɹ�����-����
 * 
 * @author lizl
 * 
 */
public abstract class BasePurProductOrderDetailActivity extends BaseActivity {

	// ����ID
	public String orderID;
	// ״̬����
	public String orderState;
	// ����ͷ
	private String token;
	// ������
	private LoadingBar progressBar;
	// �ܿؼ�
	private View view;
	// ������
	private TextView mTvOrderNumber;
	// ����״̬
	private TextView mTvTradeState;
	// ͼƬ
	private ImageView mImgTu;
	// �ջ���
	public TextView mTvConsignee;
	// �绰
	public TextView mTvPhone;
	// ����
	public TextView mTvAcceptCity;
	// �ջ���ַ
	public TextView mTvAcceptAddress;
	// ��Ʊ����
	public TextView mTvBillType;
	// ��Ʊ̧ͷ
	public TextView mTvBillRise;
	// ��Ʊ����
	public TextView mTvBillContent;
	// ��˾�������Բ���
	public LinearLayout mLinCom;
	// �̼�
	private TextView mTvMerchant;

	// ��ʾ��Ʒ��Ϣ�����Բ���
	private LinearLayout mLinChanPin;

	public abstract String getStateReminder();

	public abstract int getImage();

	public abstract void addFoot();

	// ����ʵ��
	public OrderEntity entity;

	public int goState;

	@Override
	protected void initWin(boolean hasScrollView) {
		super.initWin(true);

		initTitle();
		loaderProgress();
		initContent();
		initNetData();

	}

	/**
	 * ����
	 */
	private void initTitle() {

		EventTopTitleView topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("��������");
		win_title.addView(topTitleView);
	}

	/**
	 * ��ʼ������
	 */
	private void initContent() {
		view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_pur_product_order_detail, null);

		// ������
		mTvOrderNumber = (TextView) view.findViewById(R.id.tv_order_id);
		// ����״̬
		mTvTradeState = (TextView) view.findViewById(R.id.tv_warm_info);
		// ͼƬ
		mImgTu = (ImageView) view.findViewById(R.id.img_tu);
		// �ջ���
		mTvConsignee = (TextView) view.findViewById(R.id.tv_get_goods_person);
		// �绰
		mTvPhone = (TextView) view.findViewById(R.id.tv_tel_phone);
		// ����
		mTvAcceptCity = (TextView) view.findViewById(R.id.tv_get_goods_city);
		// �ջ���ַ
		mTvAcceptAddress = (TextView) view
				.findViewById(R.id.tv_get_goods_address);
		// ��Ʊ����
		mTvBillType = (TextView) view.findViewById(R.id.tv_invoice_type);
		// ��Ʊ̧ͷ
		mTvBillRise = (TextView) view.findViewById(R.id.tv_invoice_title);
		// ��Ʊ����
		mTvBillContent = (TextView) view.findViewById(R.id.tv_invoice_content);
		// ��˾�������Բ���
		mLinCom = (LinearLayout) view.findViewById(R.id.lin_com);

		// ������ת���鿴�������
		mLinCom.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				entity.getCompanyid();
				// TODO �ڴ�����Ŀ�����ת�¼�
				IntentClassChangeUtils.startShopDetailActivity(
						BasePurProductOrderDetailActivity.this, Utils
								.isStringEmpty(entity.getCompanyid()) ? 1
								: Integer.valueOf(entity.getCompanyid()));
			}
		});

		mLinChanPin = (LinearLayout) view.findViewById(R.id.lin_chanpin);

		// ��˾����
		mTvMerchant = (TextView) view.findViewById(R.id.tv_merchact_name);
	}

	/**
	 * ������������
	 */
	private void initNetData() {

		token = CmallApplication.getUserInfo().getToken();
		orderState = getIntent().getStringExtra("temp");
		orderID = getIntent().getStringExtra("arg");

		Log.d("MM", orderID);

		JsonLoader jsonLoader = new JsonLoader(this);
		jsonLoader.setUrl(AppNetUrl.getMyPurProductFormDetailUrl(orderID));
		jsonLoader.setPostData(NullJsonData.getPostData());
		jsonLoader.setMethod(BaseNetLoder.Method_Post);
		jsonLoader.addRequestHeader("token", token);
		jsonLoader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				finish();
				displayToast(errorMessage);
			}

			@Override
			public void onCompelete(Object arg0, Object result) {
				JSONArray array = (JSONArray) result;

				entity = MyPurFormManager.getMyPurProductFormDetail(array);
				stopProgress();

				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);
				if (1 == info.getStatus()) {

					// ��ȡ���ݳɹ�����������
					win_content.addView(view);
					setData();
					addFoot();
				} else {
					finish();
					displayToast(info.getMsg());
				}

			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);
	}

	/**
	 * ��������
	 */
	private void setData() {

		// ������
		mTvOrderNumber.setText(String.format(
				getResources().getString(R.string.form_num),
				entity.getOrderNumber()));
		// ����״̬
		mTvTradeState.setText(getStateReminder());
		// ͼƬ
		mImgTu.setImageResource(getImage());
		// �ջ���
		mTvConsignee.setText(entity.getReceivername());
		// �绰
		mTvPhone.setText(entity.getPhone());
		// ����
		mTvAcceptCity.setText(entity.getOrderCity());
		// �ջ���ַ
		mTvAcceptAddress.setText(entity.getAddress());
		// ��Ʊ����
		mTvBillType.setText(entity.getInvoicetypename());
		// ��Ʊ̧ͷ
		mTvBillRise.setText(entity.getTitle());
		// ��Ʊ����
		mTvBillContent.setText(entity.getContext());
		// �̼�
		mTvMerchant.setText(entity.getCompanyname());

		/*
		 * ��Ʒ������Ŀ
		 */
		ArrayList<OrderEntity> pdmlList = entity.getPdmlList();
		for (int i = 0; i < pdmlList.size(); i++) {

			ProductOrderDetailItem pinItem = new ProductOrderDetailItem(this);
			mLinChanPin.addView(pinItem.disItem(pdmlList.get(i)));
		}

	}

	/**
	 * ��������
	 * 
	 * @param url
	 * 
	 */
	public void formOpration(String url) {

		JsonLoader jsonLoader = new JsonLoader(this);
		jsonLoader.setUrl(url);
		jsonLoader.setPostData(NullJsonData.getPostData());
		jsonLoader.addRequestHeader("token", CmallApplication.getUserInfo()
				.getToken());
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
				// �ҷ�������������
				Log.d("MM", "����������" + array.toString());

				// ���������ؽ��
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);
				displayToast(info.getMsg());
				if (1 == info.getStatus()) {

					Intent intent = new Intent();
					setResult(NumEntity.RESULT_FORM_OK, intent);
					finish();

				}
			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);

	}
}