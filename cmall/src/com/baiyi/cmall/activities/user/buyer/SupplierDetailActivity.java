package com.baiyi.cmall.activities.user.buyer;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.user.buyer.intention.PurchaseIntentionOrderActivity;
import com.baiyi.cmall.adapter.MyPurchaseOrderAdapter;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.listitem.InScrollListView;
import com.baiyi.cmall.request.manager.MyPurchaseManager;
import com.baiyi.cmall.request.manager.NullJsonData;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.JsonParse_User;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.LoadingBar;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;

/**
 * 
 * ���ǲɹ���-�ҵĲɹ�-��Ӧ�̽���
 * 
 * @author lizl
 */
public class SupplierDetailActivity extends BaseActivity implements
		OnItemClickListener, OnClickListener {

	/**
	 * �ɹ���Ϣ�ؼ�
	 */
	// �൱���л���ť
	private LinearLayout mLinPurOrderEdit;
	private LinearLayout layout;
	// ����Դ
	private ArrayList<GoodsSourceInfo> datas;
	// �ɹ���Ϣ
	private GoodsSourceInfo purInfo;
	// �ѹ�Ӧ��Ϣװ�ڼ�����
	private GoodsSourceInfo info;
	// �ɹ���ID
	private int purId;
	// ��ʾȫ�����ݵ�״̬
	private int DATA_ALL = 1;
	// �ɹ������б�ˢ��ʱ�仯
	private View purView;

	// ����-��Ʒ����
	private TextView mTxtPurName;
	// ״̬����� δ��ˣ�
	private TextView mTxtPurState;
	// ��Ʒ����
	private TextView mTxtCategoryName;
	// Ʒ��/ú��
	private TextView mTxtPurCoalCategory;
	// ����
	private TextView mTxtPurPlace;
	// ���
	private TextView mTxtPurInverory;
	// �۸�
	private TextView mTxtPurPrice;
	// �����
	private TextView mTxtPurDelivery;
	// Ԥ�����
	private TextView mTxtPurPayment;
	// ����ʱ��
	private TextView mTxtPurPutTime;
	// �Y��ʱ��
	private TextView mTxtPurEndTime;
	// ����״̬
	private TextView mTxtPurPublicState;
	// ��ʾ��ע��Ϣ
	private TextView mTxtPurRemark;
	// ���Ʊ�ע�Ĳ���
	private LinearLayout mLlRemark;
	// ������
	private LoadingBar bar;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);
		initData();
		initTitle();
		initPurchaseContent();
		initDetailList();
		initFoot();
		// ��Ӳɹ���Ϣ
		initPurData();
	}

	/**
	 * ��ʼ������
	 */
	private void initData() {

		info = (GoodsSourceInfo) getIntent().getSerializableExtra("data");
		purId = info.getGoodSPurchaseOrderId();
	}

	/**
	 * ��ʼ��������
	 */
	private void initTitle() {
		final EventTopTitleView topTitleView = new EventTopTitleView(this, true);
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
				MsgItemUtil.onclickPopItem(state, SupplierDetailActivity.this);
			}
		});
		topTitleView.setEventName("�ɹ�����");
		win_title.addView(topTitleView);
	}

	/**
	 * �ɹ���Ϣ
	 */
	private void initPurchaseContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_purchase_intention_lin, null);
		win_content.addView(view);
		// �鿴����Ĳɹ�����ؼ�
		mLinPurOrderEdit = (LinearLayout) view
				.findViewById(R.id.lin_pur_order_huan);
		mLinPurOrderEdit.setOnClickListener(this);
		layout = (LinearLayout) view.findViewById(R.id.lin_layout);

		// �ɹ�����
		purView = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_purchase_detail, null);
		purView.setVisibility(View.GONE);
		layout.addView(purView);

		findPurViewById();

		bar = new LoadingBar(this);

	}

	/**
	 * �� �ؼ�
	 */
	private void findPurViewById() {

		mTxtPurName = (TextView) findViewById(R.id.txt_pur_name);
		mTxtPurState = (TextView) findViewById(R.id.txt_pur_state);
		mTxtCategoryName = (TextView) findViewById(R.id.txt_pur_type);
		mTxtPurCoalCategory = (TextView) findViewById(R.id.txt_pur_coal_type);
		mTxtPurPlace = (TextView) findViewById(R.id.txt_pur_place);
		mTxtPurInverory = (TextView) findViewById(R.id.txt_pur_weight);
		mTxtPurPrice = (TextView) findViewById(R.id.txt_pur_price);
		mTxtPurDelivery = (TextView) findViewById(R.id.txt_pur_delivery);
		mTxtPurPayment = (TextView) findViewById(R.id.txt_pur_payment);
		mTxtPurPutTime = (TextView) findViewById(R.id.txt_pur_put_time);
		mTxtPurEndTime = (TextView) findViewById(R.id.txt_pur_end_time);
		mTxtPurPublicState = (TextView) findViewById(R.id.txt_pur_public_state);
		mTxtPurRemark = (TextView) findViewById(R.id.txt_pur_remark);
		mLlRemark = (LinearLayout) findViewById(R.id.ll_remark);
	}

	/*
	 * �ɹ�����������
	 */
	private void updatePurInfo() {

		purView.setVisibility(View.VISIBLE);

		mTxtPurName.setText(purInfo.getGoodSName());
		mTxtPurState.setText(purInfo.getIntentionOrderState());
		mTxtCategoryName.setText(purInfo.getGoodSCategory());
		mTxtPurCoalCategory.setText(purInfo.getGoodSBrand());
		mTxtPurPlace.setText(purInfo.getGoodSPlace());
		mTxtPurInverory.setText(purInfo.getGoodSWeight() + "��");
		mTxtPurPrice
				.setText(Utils.twoDecimals(purInfo.getGoodSPrice()) + "Ԫ/��");
		mTxtPurDelivery.setText(purInfo.getGoodSDelivery());
		mTxtPurPayment.setText(Utils.twoDecimals(purInfo.getGoodSPrePrice())
				+ "Ԫ");
		mTxtPurPutTime.setText(purInfo.getGoodSPutTime());
		mTxtPurEndTime.setText(purInfo.getGoodSEndTime());
		mTxtPurPublicState.setText(purInfo.getPubStateName());
		mTxtPurRemark.setText(purInfo.getGoodSRemark());
		if ("���δͨ��".equals(purInfo.getIntentionOrderState())) {
			mLlRemark.setVisibility(View.VISIBLE);
		} else {
			mLlRemark.setVisibility(View.GONE);
		}
	}

	// �ҵĹ�Ӧ����������
	private MyPurchaseOrderAdapter orderAdapter;
	// û�����ݵ�ʱ��
	private View noDataView;

	/**
	 * ��Ӧ�����б�
	 */
	private void initDetailList() {

		ContextUtil.getLayoutInflater(this).inflate(R.layout.item_provider,
				win_content);
		TextView mView = (TextView) findViewById(R.id.txt_provider_info);
		mView.setText("��Ӧ���б�");

		// û�й�Ӧ���ݵ�ʱ����ʾ����ʾ��Ϣ
		noDataView = ContextUtil.getLayoutInflater(SupplierDetailActivity.this)
				.inflate(R.layout.item_reply_state, null);
		noDataView.setVisibility(View.GONE);
		win_content.addView(noDataView);

		// ��Ӧ��Ϣ�б�
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.list_inscroll_view, null);
		InScrollListView listView = (InScrollListView) view
				.findViewById(R.id.lst_in_scroll);
		// ���listViewǶ����scrollView�����Ȼ���ʾListView������
		listView.setFocusable(false);
		// ����ListView�Ĺ�����
		listView.setVerticalScrollBarEnabled(true);

		orderAdapter = new MyPurchaseOrderAdapter(this);
		// ��״̬��ʾ��ʾȫ��״̬
		orderAdapter.setState(DATA_ALL);
		listView.setAdapter(orderAdapter);
		listView.setOnItemClickListener(this);

		win_content.addView(view);

		// mScrollView.scrollTo(x, y)

	}

	/**
	 * ListView�ĵ���¼�
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		GoodsSourceInfo info = (GoodsSourceInfo) parent
				.getItemAtPosition(position);

		goActivity(PurchaseIntentionOrderActivity.class,
				info.getGoodSPurchaseOrderId());

	}

	// ����״̬
	private static final int COMMIT_STATE = 0;
	// ����״̬
	private static final int CANCEL_STATE = 1;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.lin_pur_order_huan:
			goActivity(EditPurchaselActivity.class,
					info.getGoodSPurchaseOrderId(), true);
			break;
		case R.id.btn_commit:// �����ɹ�

			// // ��ǰ��Ϣ������ʱ��
			// String pubTime = purInfo.getGoodSPutTime();
			// // ��ǰ��Ϣ������ʱ��
			// String endTime = purInfo.getGoodSEndTime();
			// // ʱ����ʾ��
			// String timeHintInfo = HintUtils.timeHint(pubTime, endTime);
			// if (!"OK".equals(timeHintInfo)) {
			// displayToast(timeHintInfo);
			// return;
			// }

			getResult(
					AppNetUrl.getPubPurchaseUrl(info.getGoodSPurchaseOrderId()),
					COMMIT_STATE);
			break;
		case R.id.btn_cancel_form:// �����ɹ�
			getResult(
					AppNetUrl.getRevPurchaseUrl(info.getGoodSPurchaseOrderId()),
					CANCEL_STATE);
			break;

		}

	}

	public void getResult(String url, final int btnState) {

		JsonLoader jsonLoader = new JsonLoader(this);
		jsonLoader.setUrl(url);
		jsonLoader.setPostData(NullJsonData.getPostData());
		jsonLoader.addRequestHeader("token", CmallApplication.getUserInfo()
				.getToken());
		jsonLoader.addRequestHeader("token", token);
		jsonLoader.addRequestHeader("iscompany", iscompany);
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

				if (COMMIT_STATE == btnState) {
					// �����ɹ������ط�����ť����ʾ������ť
					mBtnFaBu.setVisibility(View.GONE);
					mBtnCheXiao.setVisibility(View.VISIBLE);
					mTxtPurPublicState.setText("����");
				} else if (CANCEL_STATE == btnState) {
					// �����ɹ������س�����ť����ʾ������ť
					mBtnCheXiao.setVisibility(View.GONE);
					mBtnFaBu.setVisibility(View.VISIBLE);
					mTxtPurPublicState.setText("��������");
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);

	}

	// �����ɹ�
	private TextView mBtnFaBu;
	// �����ɹ�
	private TextView mBtnCheXiao;

	private void initFoot() {

		ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_form_foot, win_menu);
		win_menu.setVisibility(View.GONE);
		mBtnFaBu = (TextView) findViewById(R.id.btn_commit);
		mBtnCheXiao = (TextView) findViewById(R.id.btn_cancel_form);
		mBtnFaBu.setText("�����ɹ�");
		mBtnCheXiao.setText("��������");
		mBtnFaBu.setOnClickListener(this);
		mBtnCheXiao.setOnClickListener(this);

		setShow();

	}

	/**
	 * ����״̬�жϰ�ť����ʾ
	 * 
	 * ����״̬ (0:δ����,1:�ѷ���,-1:��������)
	 */
	private void setShow() {
		int pubState = info.getPubState();
		String stateName = info.getIntentionOrderState();
		if (!stateName.equals("�����")) {
			win_menu.removeAllViews();
			return;
		}
		if (pubState == 0 || pubState == -1) {
			mBtnCheXiao.setVisibility(View.GONE);
		}
		if (pubState == 1) {
			mBtnFaBu.setVisibility(View.GONE);
		}

	}

	/**
	 * ������������
	 */
	private void initPurData() {

		bar.start();
		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getUpdateDetailUrl(purId + ""));
		loader.setPostData(NullJsonData.getPostData());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.addRequestHeader("token", token);
		loader.addRequestHeader("iscompany", iscompany);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum,
					long totalByteNum) {
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				bar.stop();
				displayToast(errorMessage);
			}

			@Override
			public void onCompelete(Object tag, Object result) {

				bar.stop();
				JSONArray array = (JSONArray) result;

				// ����������ʧ�ܽ��
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);
				// ��û������ʱ������toast��ʾ��
				if (1 != info.getStatus()) {
					displayToast(info.getMsg());
					return;
				}

				// ������������
				purInfo = MyPurchaseManager.getUpdatePurchaseDetail(array);
				datas = purInfo.getOfferInfos();
				updatePurInfo();

				/*
				 * ��û�й�Ӧ���ݵ�ʱ����ʾ---���޹�Ӧ��Ϣ����ʾ
				 */
				if (Utils.isStringEmpty(datas)) {

					noDataView.setVisibility(View.VISIBLE);
				} else {
					noDataView.setVisibility(View.GONE);
				}
				orderAdapter.setData(datas);
				win_menu.setVisibility(View.VISIBLE);

			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (NumEntity.RESULT_CANCEL == resultCode) {
			finish();
			return;
		}

		initPurData();

		if (100 == resultCode) {
			win_menu.removeAllViews();
		}
	}

}
