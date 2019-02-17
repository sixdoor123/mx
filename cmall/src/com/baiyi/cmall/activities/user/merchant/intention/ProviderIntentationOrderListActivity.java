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
 * 供应意向单列表
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-16 下午1:58:25
 */

public class ProviderIntentationOrderListActivity extends BaseActivity
		implements OnClickListener, OnRecycleViewItemClickListener {

	// 数据源
	private ArrayList<GoodsSourceInfo> datas;
	private String token;
	// 当前显示的页码
	public int pageIndex = 1;
	// 只有在首次采显示进度
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

	// 意向类型
	private String type = 2 + "";
	// 意向状态
	private String orderState = 5 + "";
	// // 商家ID
	// private String companyId = 36 + "";

	/**
	 * 加载列表数据
	 */
	private void loadListData() {
		String userId = XmlUtils.getInstence(this).getXmlStringValue(XmlName.USER_ID);
		if (TextUtils.isEmpty(userId)) {
			displayToast("用户未登录,请登录后重试");
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

				// 显示暂无数据
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
	 * 更新界面ListView显示数据
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

	// 类型的数据
	private ArrayList<IntentionTypeInfo> categoryItems;
	// 状态的数据
	private ArrayList<IntentionTypeInfo> statusItems;

	/**
	 * 加载数据
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
	// 自定义的ListView
	private XRecyclerView mRecyclerView = null;
	// 加载进度条
	private MyLoadingBar loadingBar;
	// 适配器
	private IntentionOrderAdapter adapter;

	private TextView mTxtNoData = null;

	/**
	 * 初始化列表显示的内容
	 */
	@SuppressLint("ResourceAsColor")
	private void initListView() {
		view = ContextUtil.getLayoutInflater(this)//
				.inflate(R.layout.activity_shop_bus, null);
		win_content.addView(view);

		loadingBar = (MyLoadingBar) view.findViewById(R.id.loading);
		mRecyclerView = (XRecyclerView) view.findViewById(R.id.recycleview);

		mTxtNoData = (TextView) view.findViewById(R.id.no_data);

		// 设置布局管理器
		LinearLayoutManager layout = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(layout);

		// 设置Item增加、移除动画
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		// 添加分割线
		mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
		// 设置adapter
		adapter = new IntentionOrderAdapter(this);
		mRecyclerView.setAdapter(adapter);

		// 条目点击事件
		adapter.setItemClickListener(this);

		mRecyclerView.setLoadingListener(new LoadingListener() {

			@SuppressLint("ResourceAsColor")
			@Override
			public void onRefresh() {
//				mRbCategory.setmTxtName("类型");
//				mRbCategory.setmTxtNameColor(R.color.bg_hui1);
//				mRbCategory.setmImgChoice(R.drawable.choice_down);
//				mRbStatus.setmTxtName("状态");
//				mRbStatus.setmTxtNameColor(R.color.bg_hui1);
//				mRbStatus.setmImgChoice(R.drawable.choice_down);
//				// 只能下拉刷新
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

	// 自定义标题
	private EventTopTitleView topTitleView;

	/**
	 * 标题
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
		topTitleView.setEventName("供应意向单");
		win_title.addView(topTitleView);
	}

	// 分类组
	private RadioGroup mRgGroup;
	// 分类列表
	private MyRadioButton mRbCategory;
	// 地区列表
	private MyRadioButton mRbStatus;

	/**
	 * 加载选择框
	 */
	private void initSelectView() {
		View view = ContextUtil.getLayoutInflater(this)//
				.inflate(R.layout.activity_filter_intentation, null);
		win_title.addView(view);
		mRgGroup = (RadioGroup) findViewById(R.id.rg_grop_category);
		mRbCategory = (MyRadioButton) view.findViewById(R.id.rb_category);
		mRbStatus = (MyRadioButton) view.findViewById(R.id.rb_area);

		mRbCategory.setmTxtName("类型");
		mRbStatus.setmTxtName("状态");

		mRbCategory.setEnabled(false);
		mRbStatus.setEnabled(false);

		mRbStatus.setOnClickListener(this);
		mRbCategory.setOnClickListener(this);
	}

	/**
	 * RadioButton的添加事件
	 */
	@Override
	public void onClick(View v) {
		pageIndex = 1;
		switch (v.getId()) {
		case R.id.rb_category:// 类型弹出框按钮点击
			categoryPopWindow(v);
			//
			break;
		case R.id.rb_area:// 状态弹出框按钮
			statusPopWindow(v);

			break;
		}
	}

	// 记录状态上一次选中的条目
	private String stateWords;
	// 类型
	private String typeWorde;

	/**
	 * 状态选择
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
			 * 当没有条目被选择时
			 */
			@Override
			public void onItemNotCheked(IntentionTypeInfo info, int postion) {

				popupWindow.onWindowFocusChange(true, mRecyclerView, view);
				popupWindow.onNotCelected(mRbStatus, "状态");

			}
		});
	}

	// 显示短话匡
	private ShowRBIntentationPopupWindow popupWindow;

	/**
	 * 类型选择
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
			 * 当没有条目被选择时
			 */
			@Override
			public void onItemNotCheked(IntentionTypeInfo info, int postion) {

				popupWindow.onWindowFocusChange(true, mRecyclerView, view);
				popupWindow.onNotCelected(mRbCategory, "类型");

			}
		});
	}

	// 计算标题栏的高度 保存在此变量中
	private int titleHeight;

	/**
	 * 计算PopupWindow显示的位置到屏幕上边沿的距离
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
	 * ListView条目点击事件
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
