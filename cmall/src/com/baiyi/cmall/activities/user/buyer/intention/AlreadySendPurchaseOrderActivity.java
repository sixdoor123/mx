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
 * 我是采购商-已经发出的采购（采购意向）
 * 
 * @author lizl
 * 
 */
@SuppressLint("ResourceAsColor")
public class AlreadySendPurchaseOrderActivity extends BaseActivity implements
		OnClickListener, OnItemClickListeners {

	private EventTopTitleView topTitleView = null;
	private ArrayList<GoodsSourceInfo> datas;
	// 切换按钮组
	private RadioGroup mRgGroup;
	// 类型
	private MyRadioButton mRbStyle;
	// 状态
	private MyRadioButton mRbState;
	// 计算标题栏的高度 保存在此变量中
	private int titleHeight;
	private AlreadySendPurchaseAdapter adapter;
	// 存放ListView的View
	private View view;
	// 数据显示的list控件
	private XRecyclerView listView;
	private ShowAllPopupWindow popupWindow;
	// 类型的数据
	private ArrayList<IntentionTypeInfo> styleItems;
	// 状态的数据
	private ArrayList<IntentionTypeInfo> stateItems;
	// 用戶ID获取
	private String userId;
	private String token;

	private int pageIndex = 1;
	// 类型
	private String typeWorde;
	// 状态
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
	 * 重新进入此界面需要刷新数据
	 */
	@Override
	protected void onStart() {
		super.onStart();
		initNetData();
	}

	/**
	 * 初始化用户ID
	 */
	private void intUserId() {

		userId = CmallApplication.getUserInfo().getUserID();
		token = CmallApplication.getUserInfo().getToken();
	}

	/**
	 * 类型、状态条目赋值
	 */
	private void initArrayData() {
		styleItems = DataUtils.getPurTypeArrayList();
		stateItems = DataUtils.getPurStateArrayList();
	}

	/**
	 * 标题
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("采购意向单");
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
	 * 初始化切换按钮
	 */
	private void initRb() {

		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_already_send_item, null);
		win_content.addView(view);
		mRgGroup = (RadioGroup) findViewById(R.id.rg_grop_send);
		mRbStyle = (MyRadioButton) view.findViewById(R.id.rb_style);
		mRbState = (MyRadioButton) view.findViewById(R.id.rb_state);

		mRbStyle.setmTxtName("类型");
		mRbState.setmTxtName("状态");

		mRbStyle.setEnabled(false);
		mRbState.setEnabled(false);

		mRbStyle.setOnClickListener(this);
		mRbState.setOnClickListener(this);

	}

	private TextView mTxtNoData = null;
	/**
	 * 初始化内容
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
//				mRbStyle.setmTxtName("类型");
//				mRbStyle.setmTxtNameColor(R.color.bg_hui1);
//				mRbStyle.setmImgChoice(R.drawable.choice_down);
//				mRbState.setmTxtName("状态");
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

	// 类型---获取同步的数据
	private int type = 3;
	// 状态---获取同步的数据
	private int state = -1;

	/**
	 * 获取数据
	 */
	public void initNetData() {

		loaderProgress();
		JsonLoader jsonLoader = new JsonLoader(this);

		// 参数：---用户ID、意向类型、意向状态、页码、每页条数
		// 类型：1---我发出的采购意向 2---我收到的供应意向 3---全部
		// 意向状态：1. 已创建 2. 交流中 3. 已拒绝 5. 已下单 -1.全部

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

				// 服务器返回结果
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
				
				// 设置选择控件可操作
				mRbStyle.setEnabled(true);
				mRbState.setEnabled(true);

				// 当成功时设置适配器显示数据
				datas = MyPurAttentionManager.getMyPurchaseAttentionList(array);

				// 显示暂无数据
				if (TotalUtils.getIntence().getTotal() <= 0 && pageIndex == 1) {
					mTxtNoData.setVisibility(View.VISIBLE);
				}else {
					mTxtNoData.setVisibility(View.GONE);
				}

				
				
				if (pageIndex == 1) {// 刷新数据
					adapter.setDatas(datas);
					listView.refreshComplete();
				} else {// 下拉刷新其他的数据
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
		case R.id.rb_style:// 类型
			stylePopWindow(v);
			break;
		case R.id.rb_state:// 状态
			statePopWindow(v);
			break;
		}
	}

	/**
	 * 意向分类列表
	 * 
	 * @param v
	 */
	private void stylePopWindow(final View v) {
		popupWindow = new ShowAllPopupWindow(styleItems);
		popupWindow.showPop(typeWorde, getPopDistence(), this, v, Gravity.TOP);
		popupWindow.onWindowFocusChange(this, false, listView, view);
		// 设置选择时的字体颜色
		mRbStyle.setmTxtNameColor(R.color.bg_buyer);
		// 设置选择时的图标
		mRbStyle.setmImgChoice(R.drawable.red_choice_up);
		popupWindow.setListener(new OnItemCheckedClickListener() {

			// pop条目取消时调用
			@Override
			public void onItemNotCheked(IntentionTypeInfo info, int postion) {
				// TODO Auto-generated method stub
				popupWindow.onWindowFocusChange(
						AlreadySendPurchaseOrderActivity.this, true, listView,
						view);
				// 文字显示要是類型，说明没有选择，文字背景设置为默认的灰色
				// 发生改变、则显示选择后的状态-红色
				popupWindow.onNotCelected(mRbStyle, "类型");
			}

			// 当点击了pop条目时调用
			@Override
			public void onItemChecked(IntentionTypeInfo info, int postion) {

				popupWindow.onWindowFocusChange(
						AlreadySendPurchaseOrderActivity.this, true, listView,
						view);
				// 在此处，必须先设置文字，在关闭pop，否则第一次选择并且pop消失后文字颜色还是灰色
				mRbStyle.setmTxtName(info.getContent());
				typeWorde = info.getContent();
				popupWindow.dismissPop();
				type = Integer.valueOf(info.getId());

				initNetData();
			}

		});
	}

	/**
	 * 交流状态列表
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

			// pop条目取消时调用
			@Override
			public void onItemNotCheked(IntentionTypeInfo info, int postion) {
				// TODO Auto-generated method stub

				popupWindow.onWindowFocusChange(
						AlreadySendPurchaseOrderActivity.this, true, listView,
						view);
				// 文字显示要是状态，说明没有选择，文字背景设置为默认的灰色
				// 发生改变、则显示选择后的状态-红色
				popupWindow.onNotCelected(mRbState, "状态");
			}

			// 当点击了pop条目时调用
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
	 * 计算PopupWindow显示的位置到屏幕上边沿的距离
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
