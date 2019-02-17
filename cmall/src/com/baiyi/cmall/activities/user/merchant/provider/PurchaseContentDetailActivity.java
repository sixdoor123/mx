package com.baiyi.cmall.activities.user.merchant.provider;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.baiyi.cmall.Config;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.user.buyer.HintUtils;
import com.baiyi.cmall.activities.user.merchant.intention.ProviderIntentionOrderActivity;
import com.baiyi.cmall.adapter.PurchaseContentAdapter;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.request.manager.PublicActivityManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.JsonParseBase;
import com.baiyi.cmall.utils.MobileStateUtils;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.LoadingBar;
import com.baiyi.cmall.views.MyLoadingBar;
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
 * ���ǹ�Ӧ��-�б�-��Ӧ��Ϣ��Ŀ ��Ӧ�Ĺ�Ӧ����
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-20 ����12:04:13
 */
public class PurchaseContentDetailActivity extends BaseActivity implements
		OnItemClickListener, OnClickListener {

	// ����Դ
	private ArrayList<GoodsSourceInfo> datas;
	private GoodsSourceInfo info;
	// ��ϸ��Ϣ
	private GoodsSourceInfo detailInfo;

	@Override
	protected void initWin(boolean hasScrollView) {

		super.initWin(true);
		initTitle();
		initData();
		// loadData();
		initProviderContent();

	}

	/**
	 * ����������Ϣ
	 */
	private void loadData() {
		final LoadingBar loadingBar = new LoadingBar(this);
		loadingBar.start();
		
		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getProInfosUrl(info.getGoodSID()));
		loader.setPostData(new JSONObject().toString());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.addRequestHeader("token", CmallApplication.getUserInfo()
				.getToken());
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				displayToast(arg2);
				loadingBar.stop();
				finish();
			}

			@Override
			public void onCompelete(Object arg0, Object result) {
				detailInfo = PublicActivityManager.getProInfos(result);
				updateProviderView();
				initDetailList();
				loadingBar.stop();
				if (null != detailInfo) {
					if (1 == detailInfo.getState()) {
						initRootView();
					} else {
						if (null != menuView) {
							win_menu.removeView(menuView);
							return;
						}
					}
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	// ������Ӧ���򵥰�ť
	private TextView mBtnPutProIntenOrder;
	// ������Ӧ���򵥰�ť
	private TextView mBtnCancelProOrder;
	View menuView;

	/**
	 * ��ʼ����View
	 */
	private void initRootView() {
		if (null != menuView) {
			win_menu.removeView(menuView);
		}
		menuView = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_button, null);
		win_menu.addView(menuView);

		// ���طָ���
		// View line = view.findViewById(R.id.view_line_15);
		// line.setVisibility(View.GONE);

		mBtnPutProIntenOrder = (TextView) menuView
				.findViewById(R.id.btn_commit_modify);
		mBtnCancelProOrder = (TextView) menuView
				.findViewById(R.id.btn_cancel_modify);

		VisibaleOrGone(info.getPublicstate());
	}

	/**
	 * �ж��Ǹ���ť��ʾ��������Ӧ���򡢳�����Ӧ����
	 * 
	 * @param publicstate
	 */
	private void VisibaleOrGone(int publicstate) {
		if (1 == publicstate) {
			mBtnCancelProOrder.setText("��������");
			mBtnCancelProOrder.setOnClickListener(this);
			mBtnCancelProOrder.setVisibility(View.VISIBLE);
			mBtnPutProIntenOrder.setVisibility(View.GONE);
		} else if (0 == publicstate || -1 == publicstate) {
			mBtnPutProIntenOrder.setText("������Ӧ");
			mBtnPutProIntenOrder.setOnClickListener(this);
			mBtnPutProIntenOrder.setVisibility(View.VISIBLE);
			mBtnCancelProOrder.setVisibility(View.GONE);
		}
	}

	/**
	 * ��ʼ������
	 */
	private void initData() {
		info = (GoodsSourceInfo) getIntent().getSerializableExtra("key");
		// GoodSSourceLoader loader = new GoodSSourceLoader();
		// loader.deleteAll();
		// DmallApplication.getDataStratey().startLoader(loader);
	}

	// ����
	private EventTopTitleView topTitleView;

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
				MsgItemUtil.onclickPopItem(state,
						PurchaseContentDetailActivity.this);
			}
		});
		topTitleView.setEventName("��Ӧ����");
		win_title.addView(topTitleView);
	}

	// ��Ӧ�� ��Ӧ������
	private TextView mTxtProviderMerchant;
	// ����-��Ʒ����
	private TextView mTxtProviderName;
	// ״̬����� δ��ˣ�
	private TextView mTxtProviderState;
	// ��Ʒ����
	private TextView mTxtCategoryName;
	// Ʒ��
	private TextView mTxtProviderCoalCategory;
	// ����
	private TextView mTxtProviderPlace;
	// ���
	private TextView mTxtProviderInverory;
	// �۸�
	private TextView mTxtProviderPrice;
	// �����
	private TextView mTxtProviderDelivery;
	// Ԥ�����
	private TextView mTxtProviderPayment;
	// ����ʱ��
	private TextView mTxtProviderPutTime;
	// ����ʱ��
	private TextView mTxtProviderEndTime;
	// ����״̬
	private TextView mTxtPublicState;

	// �����������ϸ����Ϣ�ı༭����
	private LinearLayout mLlControlLayout;

	// ����
	private MyLoadingBar loadingBar;

	/**
	 * ��Ӧ��Ϣ
	 */
	private void initProviderContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_provider_intention, null);
		win_content.addView(view);
		// �ҿؼ�
		findProviderViewById(view);
	}

	// ���Ʊ�ע�Ĳ���
	private LinearLayout mLlRemark;
	// ��ʾ��ע��Ϣ
	private TextView mTxtProviderRemark;

	/**
	 * �ҹ�Ӧ��Ϣ�Ŀؼ�
	 * 
	 * @param view
	 */
	private void findProviderViewById(View view) {
		mTxtProviderMerchant = (TextView) view
				.findViewById(R.id.txt_provider_merchant);
		mTxtProviderName = (TextView) view.findViewById(R.id.txt_provider_name);
		mTxtProviderState = (TextView) view
				.findViewById(R.id.txt_provider_state);
		mTxtCategoryName = (TextView) view.findViewById(R.id.txt_provider_type);
		mTxtProviderCoalCategory = (TextView) view
				.findViewById(R.id.txt_privoder_coal_type);
		mTxtProviderPlace = (TextView) view
				.findViewById(R.id.txt_provider_place);
		mTxtProviderInverory = (TextView) view
				.findViewById(R.id.txt_provider_invetory);
		mTxtProviderPrice = (TextView) view
				.findViewById(R.id.txt_provider_price);
		mTxtProviderDelivery = (TextView) view
				.findViewById(R.id.txt_provider_delivery);
		mTxtProviderPayment = (TextView) view
				.findViewById(R.id.txt_provider_payment);
		mTxtProviderPutTime = (TextView) view
				.findViewById(R.id.txt_provider_put_time);
		mTxtProviderEndTime = (TextView) view
				.findViewById(R.id.txt_provider_end_time);
		mTxtPublicState = (TextView) view.findViewById(R.id.txt_public_state);

		mLlControlLayout = (LinearLayout) view.findViewById(R.id.ll_control);

		loadingBar = (MyLoadingBar) view.findViewById(R.id.load);

		mLlControlLayout.setOnClickListener(this);

		mLlRemark = (LinearLayout) view.findViewById(R.id.ll_remark);
		mTxtProviderRemark = (TextView) view
				.findViewById(R.id.txt_provider_remark);
		if (null != info) {
			if (-1 == info.getState()) {
				mLlRemark.setVisibility(View.VISIBLE);
			} else {
				mLlRemark.setVisibility(View.GONE);
			}
		}
	}

	// �޸ĺ����Ϣʵ��
	private GoodsSourceInfo sourceInfo;

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// ��ȡ�޸ĵ�����
		// GoodSSourceLoader loader = new GoodSSourceLoader();
		// loader.setSelect(null);
		// loader.setId(123);
		// LoaderResult result = loader.selectById();
		// ArrayList<GoodsSourceInfo> dataArrayList =
		// (ArrayList<GoodsSourceInfo>) result
		// .getResult();
		// if (dataArrayList != null && dataArrayList.size() > 0) {
		// sourceInfo = dataArrayList.get(0);
		// // �����޸ĺ������
		// updataView(sourceInfo);
		// }
		//
		// DmallApplication.getDataStratey().startLoader(loader);
		loadData();
	}

	/**
	 * ���¹�Ӧ��Ϣ�Ľ���
	 */
	private void updateProviderView() {
		if (null != info) {
			if (null != detailInfo) {
				info = detailInfo;
			}
			mTxtProviderMerchant.setText(info.getGoodSMerchant());
			mTxtProviderName.setText(info.getGoodSName());
			mTxtProviderState.setText(info.getStatename());
			mTxtCategoryName.setText(info.getGoodSCategory());
			mTxtProviderCoalCategory.setText(info.getGoodSBrand());
			mTxtProviderPlace.setText(info.getGoodSPlace());
			mTxtProviderInverory.setText(info.getGoodSWeight() + "��");
			String price = Utils.twoDecimals(info.getGoodSPrePrice());

			mTxtProviderPrice.setText(price + "Ԫ/��");
			mTxtProviderDelivery.setText(info.getGoodSDelivery());
			String prepayment = Utils.twoDecimals(info.getPrepayment());

			mTxtProviderPayment.setText(prepayment + "Ԫ");
			mTxtProviderPutTime.setText(info.getGoodSPutTime());
			mTxtProviderEndTime.setText(info.getGoodSEndTime());
			mTxtPublicState.setText(info.getPublicstatename());
			mTxtProviderRemark.setText(info.getGoodSRemark());
		}
	}

	// �ҵĹ�Ӧ����������
	private PurchaseContentAdapter orderAdapter;
	View list;
	View listview;
	View noView;

	/**
	 * ��Ӧ�����б�
	 */
	private void initDetailList() {

		if (null != list) {
			win_content.removeView(list);
		}
		if (null != listview) {
			win_content.removeView(listview);
		}
		if (null != noView) {
			win_content.removeView(noView);
		}
		list = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.item_provider, null);
		win_content.addView(list);

		ImageView imageView = (ImageView) findViewById(R.id.img);
		imageView.setImageResource(R.drawable.buyers);
		TextView mView = (TextView) findViewById(R.id.txt_provider_info);
		mView.setText("�ɹ����б�");

		if (!Utils.isStringEmpty(detailInfo == null ? null : detailInfo
				.getPurInfos())) {
			orderAdapter = new PurchaseContentAdapter(this);
			listview = ContextUtil.getLayoutInflater(this).inflate(
					R.layout.list_have_divider, null);

			PullToRefreshListView listView = (PullToRefreshListView) listview
					.findViewById(R.id.lst_industry_trends);
			// ����������������ˢ��
			listView.setMode(Mode.DISABLED);
			// ����ListView�Ĺ�����
			listView.setVerticalScrollBarEnabled(true);
			if (null != detailInfo) {
				orderAdapter.setData(detailInfo.getPurInfos());
			}
			listView.setAdapter(orderAdapter);
			listView.setOnItemClickListener(this);

			win_content.addView(listview,
					Config.getInstance().getScreenWidth(this), MobileStateUtils
							.getTotalHeightofListView(orderAdapter, listView));
		} else {
			// ������ʱ�����ʾ
			noView = ContextUtil.getLayoutInflater(this).inflate(
					R.layout.item_reply_state, null);
			win_content.addView(noView);
			TextView hintView = (TextView) findViewById(R.id.tv_tint);
		}
	}

	/**
	 * ListView�ĵ���¼�
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		GoodsSourceInfo info = (GoodsSourceInfo) parent
				.getItemAtPosition(position);
		// goActivity(info.getId(), MyProviderIntentionOrderActivity.class);
		goActivity(info, ProviderIntentionOrderActivity.class);
	}

	/**
	 * ����¼�
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_control:// �����������
			goActivity(info.getGoodSID(),
					EditMyProviderStandardArgDetailActivity.class, 9);
			break;
		case R.id.btn_commit_modify:// ������Ӧ����
			publishOffer();
			break;
		case R.id.btn_cancel_modify:// ������Ӧ����
			reOffer();
			break;
		}
	}

	/**
	 * ������Ӧ����
	 */
	private void publishOffer() {
		String startTime = info.getGoodSPutTime();
		String endTime = info.getGoodSEndTime();

		String result = HintUtils.timeHint(startTime, endTime);
		if (!"OK".equals(result)) {
			displayToast(result);
			return;
		}
		orderOpration(AppNetUrl.getPubOfferUrl(info.getGoodSID()));
	}

	/**
	 * ������Ӧ����
	 */
	private void reOffer() {
		orderOpration(AppNetUrl.getRevOfferUrl(info.getGoodSID()));

	}

	// ���ʵ��
	private RequestNetResultInfo resultInfo;

	/**
	 * ������Ӧ���򵥡�������Ӧ����
	 * 
	 * @param url
	 */
	private void orderOpration(String url) {
		loadingBar.setVisibility(View.VISIBLE);
		loadingBar.setProgressInfo("���ڼ�����...");
		loadingBar.start();

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(url);
		loader.setPostData(new JSONObject().toString());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.addRequestHeader("token", CmallApplication.getUserInfo()
				.getToken());
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
				loadingBar.setProgressInfo("���ڽ���");
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				displayToast(arg2);
				loadingBar.setVisibility(View.GONE);
				loadingBar.stop();
			}

			@SuppressWarnings("unused")
			@Override
			public void onCompelete(Object arg0, Object arg1) {
				resultInfo = JsonParseBase.getResultInfo((JSONArray) arg1);
				loadingBar.setVisibility(View.GONE);
				loadingBar.stop();
				displayToast(resultInfo.getMsg());
				int state = -1;
				if (1 == resultInfo.getStatus()) {
					if ("�����ɹ�".equals(resultInfo.getMsg())) {
						VisibaleOrGone(0);
						mTxtPublicState.setText("��������");
					} else if ("�����ɹ�".equals(resultInfo.getMsg())) {
						VisibaleOrGone(1);
						mTxtPublicState.setText("�ѷ���");
					}
				} else {
					return;
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 10) {
			// win_menu.removeView(menuView);
			// finish();
		} else if (resultCode == 12) {
			finish();
		}
	}
}
