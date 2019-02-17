package com.baiyi.cmall.activities.user.merchant.intention;

import java.util.ArrayList;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.home.itemview.DividerItemDecoration;
import com.baiyi.cmall.activities.main.total.TotalUtils;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.activities.user.login.ExitLogin;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.IntentionTypeInfo;
import com.baiyi.cmall.entity.LoginInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.popupwindow.ShowRBIntentationPopupWindow;
import com.baiyi.cmall.popupwindow.ShowRBIntentationPopupWindow.OnItemCheckedClickListener;
import com.baiyi.cmall.request.manager.MerchantCenterManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.DataUtils;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.utils.XmlName;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.MyRadioButton;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;
import com.jcodecraeer.xrecyclerview.port.OnRecycleViewItemClickListener;
import com.baiyi.cmall.R;

/**
 * ��Ӧ�����б�
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-16 ����1:58:25
 */

public class ProviderIntentationOrderListActivity extends BaseActivity
		implements OnClickListener, OnRecycleViewItemClickListener {

	// ����Դ
	private ArrayList<GoodsSourceInfo> datas;
	private String token;
	// ��ǰ��ʾ��ҳ��
	public int pageIndex = 1;
	// ֻ�����״β���ʾ����
	private boolean barFlag = true;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(false);
		topTitle();
		initSelectData();
		initSelectView();
		initListView();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		pageIndex = 1;
		loadListData();

	}

	// ��������
	private String type = 2 + "";
	// ����״̬
	private String orderState = 5 + "";
	// // �̼�ID
	// private String companyId = 36 + "";

	/**
	 * �����б�����
	 */
	private void loadListData() {
		String userId = XmlUtils.getInstence(this).getXmlStringValue(XmlName.USER_ID);
		if (TextUtils.isEmpty(userId)) {
			displayToast("�û�δ��¼,���¼������");
			return;
		}
		if (barFlag) {
			startLoading();
		}

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getMyProviderOrderListUrl(type + "", orderState, pageIndex, Config.LIST_ITEM_COUNT));
		loader.setPostData(new JSONObject().toString());
		loader.addRequestHeader("token", token);
		loader.addRequestHeader("iscompany", iscompany);
		loader.setMethod(BaseNetLoder.Method_Post);

		loader.setLoaderListener(new LoaderListener() {
			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				if (barFlag) {
					stopLoading();
				}
				displayToast(arg2);
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				GoodsSourceInfo info = MerchantCenterManager
						.getProviderIntentationOrderList(ProviderIntentationOrderListActivity.this, arg1);
				if (null != mRecyclerView) {
					if (1 == pageIndex) {
						mRecyclerView.refreshComplete();
					} else {
						mRecyclerView.loadMoreComplete();
					}
				}
				if (barFlag) {
					stopLoading();
				}

				// ��ʾ��������
				if (TotalUtils.getIntence().getTotal() <= 0 && pageIndex == 1) {
					mTxtNoData.setVisibility(View.VISIBLE);
				} else {
					mTxtNoData.setVisibility(View.GONE);
				}

				if (null != info) {
					RequestNetResultInfo resultInfo = info.getResultInfo();
					if (null != resultInfo) {
						if (1 != resultInfo.getStatus()) {
							if (NumEntity.PLEASE_LOG.equals(resultInfo.getMsg())) {
								displayToast(resultInfo.getMsg());
								ExitLogin.getInstence(getApplicationContext()).cleer();
								return;
							} else {
								displayToast(resultInfo.getMsg());
								return;
							}
						} else {
							datas = info.getOfferInfos();
							updataListView();
						}
					}
					mRbCategory.setEnabled(true);
					mRbStatus.setEnabled(true);
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * ���½���ListView��ʾ����
	 * 
	 * @param datas1
	 */
	@SuppressWarnings("unchecked")
	private void updataListView() {

		if (1 == pageIndex) {
			adapter.setDatas(datas);
		} else {
			adapter.addDatas(datas);
		}
	}

	// ���͵�����
	private ArrayList<IntentionTypeInfo> categoryItems;
	// ״̬������
	private ArrayList<IntentionTypeInfo> statusItems;

	/**
	 * ��������
	 */
	private void initSelectData() {
		categoryItems = DataUtils.getTypeArrayList();
		statusItems = DataUtils.getStateArrayList();

		type = categoryItems.get(0).getId();
		orderState = statusItems.get(0).getId();
		LoginInfo info = CmallApplication.getUserInfo();
		token = info.getToken();
	}

	//
	private View view;
	// �Զ����ListView
	private XRecyclerView mRecyclerView = null;
	// ���ؽ�����
	private MyLoadingBar loadingBar;
	// ������
	private IntentionOrderAdapter adapter;

	private TextView mTxtNoData = null;

	/**
	 * ��ʼ���б���ʾ������
	 */
	@SuppressLint("ResourceAsColor")
	private void initListView() {
		view = ContextUtil.getLayoutInflater(this)//
				.inflate(R.layout.activity_shop_bus, null);
		win_content.addView(view);

		loadingBar = (MyLoadingBar) view.findViewById(R.id.loading);
		mRecyclerView = (XRecyclerView) view.findViewById(R.id.recycleview);

		mTxtNoData = (TextView) view.findViewById(R.id.no_data);

		// ���ò��ֹ�����
		LinearLayoutManager layout = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(layout);

		// ����Item���ӡ��Ƴ�����
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		// ��ӷָ���
		mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
		// ����adapter
		adapter = new IntentionOrderAdapter(this);
		mRecyclerView.setAdapter(adapter);

		// ��Ŀ����¼�
		adapter.setItemClickListener(this);

		mRecyclerView.setLoadingListener(new LoadingListener() {

			@SuppressLint("ResourceAsColor")
			@Override
			public void onRefresh() {
//				mRbCategory.setmTxtName("����");
//				mRbCategory.setmTxtNameColor(R.color.bg_hui1);
//				mRbCategory.setmImgChoice(R.drawable.choice_down);
//				mRbStatus.setmTxtName("״̬");
//				mRbStatus.setmTxtNameColor(R.color.bg_hui1);
//				mRbStatus.setmImgChoice(R.drawable.choice_down);
//				// ֻ������ˢ��
//				orderState = "-1";
//				type = "3";
//				typeWorde = "";
//				stateWords = "";

				barFlag = false;
				pageIndex = 1;
				loadListData();

			}

			@Override
			public void onLoadMore() {
				barFlag = false;
				pageIndex += 1;
				loadListData();
			}
		});
	}

	// �Զ������
	private EventTopTitleView topTitleView;

	/**
	 * ����
	 */
	public void topTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setTitleNewsOnclick(new TitleNewsOnclick() {

			@Override
			public void setTitleNewsOnclickLister(boolean isPop) {

				topTitleView.displayPoP(MsgItemUtil.Pop_Default_txt, MsgItemUtil.Pop_Default_img);

			}
		});
		topTitleView.setNewsPopItemOnclick(new NewsPopItemOnclick() {

			@Override
			public void setNewsPopItemOnclickLister(int state) {
				MsgItemUtil.onclickPopItem(state, ProviderIntentationOrderListActivity.this);

			}
		});
		topTitleView.setEventName("��Ӧ����");
		win_title.addView(topTitleView);
	}

	// ������
	private RadioGroup mRgGroup;
	// �����б�
	private MyRadioButton mRbCategory;
	// �����б�
	private MyRadioButton mRbStatus;

	/**
	 * ����ѡ���
	 */
	private void initSelectView() {
		View view = ContextUtil.getLayoutInflater(this)//
				.inflate(R.layout.activity_filter_intentation, null);
		win_title.addView(view);
		mRgGroup = (RadioGroup) findViewById(R.id.rg_grop_category);
		mRbCategory = (MyRadioButton) view.findViewById(R.id.rb_category);
		mRbStatus = (MyRadioButton) view.findViewById(R.id.rb_area);

		mRbCategory.setmTxtName("����");
		mRbStatus.setmTxtName("״̬");

		mRbCategory.setEnabled(false);
		mRbStatus.setEnabled(false);

		mRbStatus.setOnClickListener(this);
		mRbCategory.setOnClickListener(this);
	}

	/**
	 * RadioButton������¼�
	 */
	@Override
	public void onClick(View v) {
		pageIndex = 1;
		switch (v.getId()) {
		case R.id.rb_category:// ���͵�����ť���
			categoryPopWindow(v);
			//
			break;
		case R.id.rb_area:// ״̬������ť
			statusPopWindow(v);

			break;
		}
	}

	// ��¼״̬��һ��ѡ�е���Ŀ
	private String stateWords;
	// ����
	private String typeWorde;

	/**
	 * ״̬ѡ��
	 * 
	 * @param v
	 */
	@SuppressLint("ResourceAsColor")
	private void statusPopWindow(View v) {
		popupWindow = new ShowRBIntentationPopupWindow(statusItems);
		popupWindow.showPop(stateWords, getPopDistence(), this, v, Gravity.TOP);
		popupWindow.onWindowFocusChange(false, mRecyclerView, view);
		mRbStatus.setmTxtNameColor(R.color.bg_buyer);
		mRbStatus.setmImgChoice(R.drawable.red_choice_up);
		popupWindow.setListener(new OnItemCheckedClickListener() {

			@Override
			public void onItemChecked(IntentionTypeInfo info, int postion) {
				pageIndex = 1;
				mRbStatus.setmTxtName(info.getContent());
				stateWords = info.getContent();
				popupWindow.dismissPop();
				orderState = info.getId();
				loadListData();
			}

			/**
			 * ��û����Ŀ��ѡ��ʱ
			 */
			@Override
			public void onItemNotCheked(IntentionTypeInfo info, int postion) {

				popupWindow.onWindowFocusChange(true, mRecyclerView, view);
				popupWindow.onNotCelected(mRbStatus, "״̬");

			}
		});
	}

	// ��ʾ�̻���
	private ShowRBIntentationPopupWindow popupWindow;

	/**
	 * ����ѡ��
	 * 
	 * @param v
	 */
	@SuppressLint("ResourceAsColor")
	private void categoryPopWindow(View v) {

		popupWindow = new ShowRBIntentationPopupWindow(categoryItems);
		popupWindow.showPop(typeWorde, getPopDistence(), this, v, Gravity.TOP);
		popupWindow.onWindowFocusChange(false, mRecyclerView, view);
		mRbCategory.setmTxtNameColor(R.color.bg_buyer);
		mRbCategory.setmImgChoice(R.drawable.red_choice_up);
		popupWindow.setListener(new OnItemCheckedClickListener() {

			@Override
			public void onItemChecked(IntentionTypeInfo info, int postion) {
				pageIndex = 1;
				mRbCategory.setmTxtName(info.getContent());
				popupWindow.dismissPop();
				typeWorde = info.getContent();
				type = info.getId();
				loadListData();

			}

			/**
			 * ��û����Ŀ��ѡ��ʱ
			 */
			@Override
			public void onItemNotCheked(IntentionTypeInfo info, int postion) {

				popupWindow.onWindowFocusChange(true, mRecyclerView, view);
				popupWindow.onNotCelected(mRbCategory, "����");

			}
		});
	}

	// ����������ĸ߶� �����ڴ˱�����
	private int titleHeight;

	/**
	 * ����PopupWindow��ʾ��λ�õ���Ļ�ϱ��صľ���
	 * 
	 * @return
	 */
	private int getPopDistence() {
		titleHeight = topTitleView.getHeight();
		int height = mRgGroup.getHeight()
				+ /* MobileStateUtils.getStatusHeight(this) */ +titleHeight;
		return height;
	}

	/**
	 * ListView��Ŀ����¼�
	 * 
	 * @param parent
	 * @param view
	 * @param position
	 * @param id
	 */
	@Override
	public <T> void onItemClick(View view, int position, T t) {
		GoodsSourceInfo info = (GoodsSourceInfo) t;
		goActivity(info, ProviderIntentionOrderActivity.class);
	}
}
