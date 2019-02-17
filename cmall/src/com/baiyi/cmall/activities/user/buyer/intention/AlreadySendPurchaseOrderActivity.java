package com.baiyi.cmall.activities.user.buyer.intention;

import java.util.ArrayList;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.total.TotalUtils;
import com.baiyi.cmall.activities.user.buyer.adapter.BaseRCAdapter.OnItemClickListeners;
import com.baiyi.cmall.activities.user.login.ExitLogin;
import com.baiyi.cmall.adapter.AlreadySendPurchaseAdapter;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.IntentionTypeInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.popupwindow.ShowAllPopupWindow;
import com.baiyi.cmall.popupwindow.ShowAllPopupWindow.OnItemCheckedClickListener;
import com.baiyi.cmall.request.manager.MyPurAttentionManager;
import com.baiyi.cmall.request.manager.NullJsonData;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.DataUtils;
import com.baiyi.cmall.utils.JsonParse_User;
import com.baiyi.cmall.utils.MobileStateUtils;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.LoadingBar;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.MyRadioButton;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase;
import com.baiyi.cmall.views.pulldownview.PullToRefreshListView;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase.OnLastItemVisibleListener;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase.OnRefreshListener;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;

/**
 * ���ǲɹ���-�Ѿ������Ĳɹ����ɹ�����
 * 
 * @author lizl
 * 
 */
@SuppressLint("ResourceAsColor")
public class AlreadySendPurchaseOrderActivity extends BaseActivity implements
		OnClickListener, OnItemClickListeners {

	private EventTopTitleView topTitleView = null;
	private ArrayList<GoodsSourceInfo> datas;
	// �л���ť��
	private RadioGroup mRgGroup;
	// ����
	private MyRadioButton mRbStyle;
	// ״̬
	private MyRadioButton mRbState;
	// ����������ĸ߶� �����ڴ˱�����
	private int titleHeight;
	private AlreadySendPurchaseAdapter adapter;
	// ���ListView��View
	private View view;
	// ������ʾ��list�ؼ�
	private XRecyclerView listView;
	private ShowAllPopupWindow popupWindow;
	// ���͵�����
	private ArrayList<IntentionTypeInfo> styleItems;
	// ״̬������
	private ArrayList<IntentionTypeInfo> stateItems;
	// �Ñ�ID��ȡ
	private String userId;
	private String token;

	private int pageIndex = 1;
	// ����
	private String typeWorde;
	// ״̬
	private String stateWorde;

	@Override
	protected void initWin(boolean hasScrollView) {
		super.initWin(false);
		initTitle();
		intUserId();
		initRb();
		initView();
		loaderProgress();
		initArrayData();

	}

	/**
	 * ���½���˽�����Ҫˢ������
	 */
	@Override
	protected void onStart() {
		super.onStart();
		initNetData();
	}

	/**
	 * ��ʼ���û�ID
	 */
	private void intUserId() {

		userId = CmallApplication.getUserInfo().getUserID();
		token = CmallApplication.getUserInfo().getToken();
	}

	/**
	 * ���͡�״̬��Ŀ��ֵ
	 */
	private void initArrayData() {
		styleItems = DataUtils.getPurTypeArrayList();
		stateItems = DataUtils.getPurStateArrayList();
	}

	/**
	 * ����
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("�ɹ�����");
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
						AlreadySendPurchaseOrderActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}

	/**
	 * ��ʼ���л���ť
	 */
	private void initRb() {

		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_already_send_item, null);
		win_content.addView(view);
		mRgGroup = (RadioGroup) findViewById(R.id.rg_grop_send);
		mRbStyle = (MyRadioButton) view.findViewById(R.id.rb_style);
		mRbState = (MyRadioButton) view.findViewById(R.id.rb_state);

		mRbStyle.setmTxtName("����");
		mRbState.setmTxtName("״̬");

		mRbStyle.setEnabled(false);
		mRbState.setEnabled(false);

		mRbStyle.setOnClickListener(this);
		mRbState.setOnClickListener(this);

	}

	private TextView mTxtNoData = null;
	/**
	 * ��ʼ������
	 */
	protected void initView() {

		view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_shop_bus, null);

		listView = (XRecyclerView) view.findViewById(R.id.recycleview);
		mTxtNoData = (TextView) view.findViewById(R.id.no_data);
		
		listView.setLayoutManager(new LinearLayoutManager(this,
				LinearLayoutManager.VERTICAL, false));
		listView.setLoadingListener(new LoadingListener() {

			@Override
			public void onRefresh() {
//				mRbStyle.setmTxtName("����");
//				mRbStyle.setmTxtNameColor(R.color.bg_hui1);
//				mRbStyle.setmImgChoice(R.drawable.choice_down);
//				mRbState.setmTxtName("״̬");
//				mRbState.setmTxtNameColor(R.color.bg_hui1);
//				mRbState.setmImgChoice(R.drawable.choice_down);
//				typeWorde = "";
//				stateWorde = "";
				pageIndex = 1;
				initNetData();
			}

			@Override
			public void onLoadMore() {
				pageIndex++;
				initNetData();
			}
		});

		LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		win_content.addView(view, lp);

		adapter = new AlreadySendPurchaseAdapter(
				AlreadySendPurchaseOrderActivity.this);
		listView.setAdapter(adapter);
		adapter.setOnItemClickListener(this);
	}

	// ����---��ȡͬ��������
	private int type = 3;
	// ״̬---��ȡͬ��������
	private int state = -1;

	/**
	 * ��ȡ����
	 */
	public void initNetData() {

		loaderProgress();
		JsonLoader jsonLoader = new JsonLoader(this);

		// ������---�û�ID���������͡�����״̬��ҳ�롢ÿҳ����
		// ���ͣ�1---�ҷ����Ĳɹ����� 2---���յ��Ĺ�Ӧ���� 3---ȫ��
		// ����״̬��1. �Ѵ��� 2. ������ 3. �Ѿܾ� 5. ���µ� -1.ȫ��

		jsonLoader.setUrl(AppNetUrl.getPurAttentionListUrl(userId, type, state,
				pageIndex, Config.LIST_ITEM_COUNT));
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
				displayToast(errorMessage);
				stopProgress();
				listView.refreshComplete();
				listView.loadMoreComplete();
			}

			@Override
			public void onCompelete(Object arg0, Object result) {
				JSONArray array = (JSONArray) result;

				stopProgress();

				// ���������ؽ��
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);
				if (1 != info.getStatus()) {

					displayToast(info.getMsg());
					if (NumEntity.PLEASE_LOG.equals(info.getMsg())) {

						ExitLogin.getInstence(
								AlreadySendPurchaseOrderActivity.this).cleer();
						finish();
					}
					return;
				}
				
				// ����ѡ��ؼ��ɲ���
				mRbStyle.setEnabled(true);
				mRbState.setEnabled(true);

				// ���ɹ�ʱ������������ʾ����
				datas = MyPurAttentionManager.getMyPurchaseAttentionList(array);

				// ��ʾ��������
				if (TotalUtils.getIntence().getTotal() <= 0 && pageIndex == 1) {
					mTxtNoData.setVisibility(View.VISIBLE);
				}else {
					mTxtNoData.setVisibility(View.GONE);
				}

				
				
				if (pageIndex == 1) {// ˢ������
					adapter.setDatas(datas);
					listView.refreshComplete();
				} else {// ����ˢ������������
					adapter.addDatas(datas);
					listView.loadMoreComplete();
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.rb_style:// ����
			stylePopWindow(v);
			break;
		case R.id.rb_state:// ״̬
			statePopWindow(v);
			break;
		}
	}

	/**
	 * ��������б�
	 * 
	 * @param v
	 */
	private void stylePopWindow(final View v) {
		popupWindow = new ShowAllPopupWindow(styleItems);
		popupWindow.showPop(typeWorde, getPopDistence(), this, v, Gravity.TOP);
		popupWindow.onWindowFocusChange(this, false, listView, view);
		// ����ѡ��ʱ��������ɫ
		mRbStyle.setmTxtNameColor(R.color.bg_buyer);
		// ����ѡ��ʱ��ͼ��
		mRbStyle.setmImgChoice(R.drawable.red_choice_up);
		popupWindow.setListener(new OnItemCheckedClickListener() {

			// pop��Ŀȡ��ʱ����
			@Override
			public void onItemNotCheked(IntentionTypeInfo info, int postion) {
				// TODO Auto-generated method stub
				popupWindow.onWindowFocusChange(
						AlreadySendPurchaseOrderActivity.this, true, listView,
						view);
				// ������ʾҪ����ͣ�˵��û��ѡ�����ֱ�������ΪĬ�ϵĻ�ɫ
				// �����ı䡢����ʾѡ����״̬-��ɫ
				popupWindow.onNotCelected(mRbStyle, "����");
			}

			// �������pop��Ŀʱ����
			@Override
			public void onItemChecked(IntentionTypeInfo info, int postion) {

				popupWindow.onWindowFocusChange(
						AlreadySendPurchaseOrderActivity.this, true, listView,
						view);
				// �ڴ˴����������������֣��ڹر�pop�������һ��ѡ����pop��ʧ��������ɫ���ǻ�ɫ
				mRbStyle.setmTxtName(info.getContent());
				typeWorde = info.getContent();
				popupWindow.dismissPop();
				type = Integer.valueOf(info.getId());

				initNetData();
			}

		});
	}

	/**
	 * ����״̬�б�
	 * 
	 * @param v
	 */
	private void statePopWindow(final View v) {
		popupWindow = new ShowAllPopupWindow(stateItems);
		popupWindow.showPop(stateWorde, getPopDistence(), this, v, Gravity.TOP);
		popupWindow.onWindowFocusChange(this, false, listView, view);
		mRbState.setmTxtNameColor(R.color.bg_buyer);
		mRbState.setmImgChoice(R.drawable.red_choice_up);
		popupWindow.setListener(new OnItemCheckedClickListener() {

			// pop��Ŀȡ��ʱ����
			@Override
			public void onItemNotCheked(IntentionTypeInfo info, int postion) {
				// TODO Auto-generated method stub

				popupWindow.onWindowFocusChange(
						AlreadySendPurchaseOrderActivity.this, true, listView,
						view);
				// ������ʾҪ��״̬��˵��û��ѡ�����ֱ�������ΪĬ�ϵĻ�ɫ
				// �����ı䡢����ʾѡ����״̬-��ɫ
				popupWindow.onNotCelected(mRbState, "״̬");
			}

			// �������pop��Ŀʱ����
			@Override
			public void onItemChecked(IntentionTypeInfo info, int postion) {

				popupWindow.onWindowFocusChange(
						AlreadySendPurchaseOrderActivity.this, true, listView,
						view);
				mRbState.setmTxtName(info.getContent());
				stateWorde = info.getContent();
				popupWindow.dismissPop();
				state = Integer.valueOf(info.getId());

				initNetData();
			}
		});
	}

	/**
	 * ����PopupWindow��ʾ��λ�õ���Ļ�ϱ��صľ���
	 * 
	 * @return
	 */
	private int getPopDistence() {
		titleHeight = win_title.getHeight();
		int height = mRgGroup.getHeight()
				/*+ MobileStateUtils.getStatusHeight(this)*/ + titleHeight;
		return height;
	}

	@Override
	public void onItemClick(int position, View itemView) {

		GoodsSourceInfo info = adapter.getDataList().get(position);

		goActivity(PurchaseIntentionOrderActivity.class,
				info.getGoodSPurchaseOrderId());
	}
}
