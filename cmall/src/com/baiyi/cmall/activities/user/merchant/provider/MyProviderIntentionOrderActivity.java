package com.baiyi.cmall.activities.user.merchant.provider;

import org.json.JSONObject;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.baiyi.cmall.activities.base.BaseMsgActivity;
import com.baiyi.cmall.activities.user.merchant.MyProviderDetailsActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.IntentionProviderDetailInfo;
import com.baiyi.cmall.entity.IntentionPurchaseDetailInfo;
import com.baiyi.cmall.request.manager.MerchantCenterManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.DataUtils;
import com.baiyi.cmall.utils.MsgItemUtil;
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
 * ���ǹ�Ӧ��-��Ӧ�Ĳɹ����б����-��Ӧ����
 * 
 * @author sunxy
 * 
 */
public class MyProviderIntentionOrderActivity extends BaseMsgActivity implements
		OnClickListener {

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);
		initData();
		initTitle();
		loadData();
	}

	// ��Ӧ��Ϣ�������ɹ���Ϣ��
	private IntentionProviderDetailInfo proinfo;

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
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				proinfo = MerchantCenterManager
						.getProviderIntentationDetail(arg1);
				win_content.removeView(myLoadingBar);
				myLoadingBar.stop();

				initPurchaseContent();
				initProviderContent();
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

	/**
	 * ����Դ
	 */
	private void initData() {
		id = getIntent().getStringExtra("temp");
		// id = info.getGoodSID();

	}

	/**
	 * ��Ӧ��Ϣ
	 */
	private void initProviderContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_provider_intention_detail, null);
		win_content.addView(view);
		// �ҿؼ�
		findProviderViewById(view);
		// ������Ϣ
		updateProviderView();
	}

	// ��Ӧ����
	private MyLoadingBar bar;
	// ����
	private TextView mEditProName;
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
	private TextView mEdtInventory;
	// �۸�
	private TextView mEdtPrice;
	// Ԥ�����
	private TextView mEdtPrepayment;
	// �����
	private LinearLayout mLlProDelivery;
	// ��¼�����
	private TextView mTxtDelivery;
	// ��ϸ��Ϣ
	private TextView mTxtProDetails;

	// ��Ӧ��Ϣ��Ŀ������һ��Ŀ¼
	private LinearLayout mLlPro;

	/**
	 * �ҹ�Ӧ��Ϣ�Ŀؼ�
	 * 
	 * @param view
	 */
	private void findProviderViewById(View view) {
		mEditProName = (TextView) view.findViewById(R.id.edt_goods_provider_name);
		mLlProCategory = (LinearLayout) view.findViewById(R.id.ll_provider_categatory);
		mTxtCategory = (TextView) view.findViewById(R.id.txt_category);
		mLlProCoalType = (LinearLayout) view.findViewById(R.id.ll_provider_coal_type);
		mTxtCoal = (TextView) view.findViewById(R.id.txt_coal_type);
		mLlProPlace = (LinearLayout) view.findViewById(R.id.ll_provider_place);
		mTxtPlace = (TextView) view.findViewById(R.id.txt_place);
		mEdtInventory = (TextView) view.findViewById(R.id.edt_goods_provider_weight);
		mEdtPrice = (TextView) view.findViewById(R.id.edt_goods_provider_price);
		mEdtPrepayment = (TextView) view.findViewById(R.id.edt_goods_provider_prepayment);
		mLlProDelivery = (LinearLayout) view.findViewById(R.id.ll_provider_delivery);
		mTxtDelivery = (TextView) view.findViewById(R.id.txt_delivery);
		mTxtProDetails = (TextView) view.findViewById(R.id.txt_pro_details);

		bar = (MyLoadingBar) view.findViewById(R.id._load);
		
		mLlProCategory.setOnClickListener(this);
		mLlProCoalType.setOnClickListener(this);
		mLlProPlace.setOnClickListener(this);
		mLlProDelivery.setOnClickListener(this);

		mLlPro = (LinearLayout) view.findViewById(R.id.ll_control);
		mLlPro.setOnClickListener(this);
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
			mEdtPrice.setText(proinfo.getPrice()+"Ԫ/��");
			mEdtPrepayment.setText(proinfo.getPrepayment()+"Ԫ");
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
		win_content.addView(view);
		// �ҿؼ�
		findPurchaseViewById(view);
		// ���½�������
		updatePurchaseView();
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
	// �ɹ���Ϣ��Ŀ������һ��
	private LinearLayout mLlPur;

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

	}

	/**
	 * ���½�����Ϣ
	 * 
	 * @param info
	 */
	private void updatePurchaseView() {
		if (null != proinfo) {
			IntentionPurchaseDetailInfo purInfo = proinfo
					.getPurchaseDetailInfo();
			if (null != purInfo) {
				mTxtOrderPurName.setText(purInfo.getPurchasename());
				mTxtOrderCategory.setText(purInfo.getCategoryname());
				mTxtOrderCoalType.setText(purInfo.getBrandname());
				mTxtOrderPlace.setText(purInfo.getOriginplacename());
				mTxtOrderWeight.setText(purInfo.getAmount() + "��");
				mTxtOrderPrePrice.setText(purInfo.getPrice() + "Ԫ/��");
				mTxtOrderDelivery.setText(purInfo.getDeliveryplacename());
				mTxtOrderPayment.setText(purInfo.getPrepayment());
				mTxtOrderDetails.setText(purInfo.getPurchasedetail());
			}
		}
	}

	/**
	 * ������
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("��Ӧ����");
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
						MyProviderIntentionOrderActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_provider_delivery:// �����
			break;
		case R.id.ll_provider_coal_type:// Ʒ��
			break;
		case R.id.ll_provider_place:// ����
			break;
		case R.id.ll_provider_categatory:// ����
			break;
		case R.id.lin_pur_order_huan:// �ɹ���Ŀ������һ��(��ϸ��Ϸ)
			goActivity(id, MyProviderDetailsActivity.class);
			break;
		case R.id.ll_control:// ��Ӧ��Ŀ������һ�����༭���棩
			goActivity(proinfo.getId(), EditMyProviderStandardArgDetailActivity.class);
			break;
		default:
			break;
		}
	}

}
