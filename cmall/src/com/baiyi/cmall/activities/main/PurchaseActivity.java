/**
 * 
 */
package com.baiyi.cmall.activities.main;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.activities.base.BaseMsgActivity;
import com.baiyi.cmall.activities.main.broadcast.MyBroadCastReceiver;
import com.baiyi.cmall.activities.main.broadcast.MyBroadCastReceiver.OnFlushClickListener;
import com.baiyi.cmall.activities.main.provider.pop.MultiLevelPop;
import com.baiyi.cmall.activities.main.provider.pop.MultiLevelPop.MultiPopListItemOnclick;
import com.baiyi.cmall.activities.main.purchase.PurchaseDetailsActivity;
import com.baiyi.cmall.activities.main.total.TotalUtils;
import com.baiyi.cmall.activities.user.buyer.adapter.BaseRCAdapter.OnItemClickListeners;
import com.baiyi.cmall.adapter.PurchaseAdapter;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.popupwindow.ShowRBPopupWindow;
import com.baiyi.cmall.request.manager.ProviderSourceManager;
import com.baiyi.cmall.request.manager.PublicActivityManager;
import com.baiyi.cmall.request.manager.PurchaseSourceManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.MobileStateUtils;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.itemview.CommonSearchView;
import com.baiyi.cmall.views.itemview.MyRadioButton;
import com.baiyi.cmall.views.pop.PopDoubleList;
import com.baiyi.cmall.views.pop.PopListItemOnclick;
import com.baiyi.cmall.views.pop.PopShowOrDismissLister;
import com.baiyi.cmall.views.pop.PopSortList;
import com.baiyi.cmall.views.pop.PopSortList.OnPopItemClickListener;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;

/**
 * 采购主页
 * 
 * @author tangkun
 * 
 */
@SuppressLint("ResourceAsColor")
public class PurchaseActivity extends BaseMsgActivity implements
		OnClickListener, OnItemClickListeners {

	// 弹出对话框
	@SuppressWarnings("unused")
	private ShowRBPopupWindow popupWindow;
	private GoodsSourceInfo sourceInfo;

	// 分类组
	private RadioGroup mRgGroup;
	// 分类列表
	private MyRadioButton mRbCategory;
	// 地区列表
	private MyRadioButton mRbArea;
	// 默认列表
	private MyRadioButton mRbDefaultSort;

	private View darkview = null;

	// 存放上平信息的集合
	private ArrayList<GoodsSourceInfo> datas;

	// 计算标题栏的高度 保存在此变量中
	private int titleHeight;
	// 适配器
	private PurchaseAdapter purchaseAdapter;
	// 存放ListView的View
	private View view;
	private int pageIndex = 1;

	@Override
	protected void initWin(boolean hasScrollView) {
		super.initWin(false);

		initTitle();
		initSelectData();
		initCatetogory();
		initView();
		loaderProgress();
		loadData("", 0);
		
		
	}
	

	@Override
	protected void onResume() {
		super.onResume();
		MyBroadCastReceiver.setListener(new OnFlushClickListener() {

			@Override
			public void onFlush(Intent intent) {
				if ("FOUR".equals(intent.getStringExtra("key"))) {

					categoryPop = null;
					multiPop = null;
					sortList = null;
					mRbCategory.setmTxtName("分类");
					mRbCategory.setmTxtNameColor(R.color.bg_hui1);
					mRbCategory.setmImgChoice(R.drawable.choice_down);
					mRbArea.setmTxtName("交割地");
					mRbArea.setmTxtNameColor(R.color.bg_hui1);
					mRbArea.setmImgChoice(R.drawable.choice_down);
					mRbDefaultSort.setmTxtName("默认排序");
					mRbDefaultSort.setmTxtNameColor(R.color.bg_hui1);
					mRbDefaultSort.setmImgChoice(R.drawable.choice_down);
					sortWords = "";
					pageIndex = 1;
					loaderProgress();
					loadData("", -1);
				}
			}
		});
	}

	/**
	 * 搜索标题栏
	 */
	private void initTitle() {
		CommonSearchView searchView = new CommonSearchView(this);
		win_title.addView(searchView);
		searchView.showAppView();
	}

	/**
	 * 加载选择数据
	 */
	private void initSelectData() {
		sourceInfo = CmallApplication.getBaseDataInfo();
		if (null != sourceInfo) {
			return;
		}

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getPublicUrl());
		loader.setPostData(new JSONObject().toString());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
			}

			@Override
			public void onCompelete(Object arg0, Object result) {
				sourceInfo = PublicActivityManager.getSelectedData(
						PurchaseActivity.this, result);
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	@Override
	protected void onDestroy() {
		sourceInfo = null;
		super.onDestroy();
	}

	/**
	 * 初始化头部选择控件 当没有数据的时候不能点击
	 */
	@SuppressLint("InflateParams")
	private void initCatetogory() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_filter_item, null);
		win_content.addView(view);
		mRgGroup = (RadioGroup) findViewById(R.id.rg_grop_category);
		mRbCategory = (MyRadioButton) view.findViewById(R.id.rb_category);
		mRbArea = (MyRadioButton) view.findViewById(R.id.rb_area);
		mRbDefaultSort = (MyRadioButton) view
				.findViewById(R.id.rb_default_sort);

		mRbCategory.setmTxtName("分类");
		mRbArea.setmTxtName("交割地");
		mRbDefaultSort.setmTxtName("默认排序");

		mRbCategory.setEnabled(false);
		mRbArea.setEnabled(false);
		mRbDefaultSort.setEnabled(false);

		mRbArea.setOnClickListener(this);
		mRbCategory.setOnClickListener(this);
		mRbDefaultSort.setOnClickListener(this);
	}

	private TextView mTxtNoData = null;
	/**
	 * 初始化进度条
	 */
	@SuppressLint("InflateParams")
	private void initView() {
		view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_shop_bus, null);
		win_content.addView(view);
		darkview = (View) findViewById(R.id.darkview);
		listView = (XRecyclerView) view.findViewById(R.id.recycleview);
		
		mTxtNoData = (TextView) view.findViewById(R.id.no_data);
		
		listView.setLayoutManager(new LinearLayoutManager(this,
				LinearLayoutManager.VERTICAL, false));

		listView.setLoadingListener(new LoadingListener() {

			@Override
			public void onRefresh() {
				pageIndex = 1;
				loadData("", 0);
			}

			@Override
			public void onLoadMore() {
				pageIndex++;
				loadData("", 0);
			}
		});

		purchaseAdapter = new PurchaseAdapter(this);
		listView.setAdapter(purchaseAdapter);
		purchaseAdapter.setOnItemClickListener(this);
	}

	/**
	 * 从网络获取数据
	 */
	private void loadData(String arg, int select) {

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getPurchaseListUrl());
		loader.setPostData(ProviderSourceManager.getPostData(arg, select,
				pageIndex, Config.LIST_SIX_COUNT));
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum,
					long totalByteNum) {
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {

				stopProgress();

				if (1 == pageIndex) {
					listView.refreshComplete();
				} else {
					listView.loadMoreComplete();
				}
				
				displayToast(errorMessage);
			}

			@Override
			public void onCompelete(Object tag, Object result) {

				stopProgress();
				datas = PurchaseSourceManager.getPurchaseListInfo(result);

				// 获取数据成功后将按钮设置为可操作状态
				mRbArea.setEnabled(true);
				mRbCategory.setEnabled(true);
				mRbDefaultSort.setEnabled(true);

				if (pageIndex == 1) {
					purchaseAdapter.setDatas(datas);
					listView.refreshComplete();
				} else {
					purchaseAdapter.addDatas(datas);
					listView.loadMoreComplete();
				}
				
				// 显示暂无数据
				if (TotalUtils.getIntence().getTotal() <= 0 && pageIndex == 1) {
					mTxtNoData.setVisibility(View.VISIBLE);
				}else {
					mTxtNoData.setVisibility(View.GONE);
				}


			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	private XRecyclerView listView;

	/**
	 * 计算PopupWindow显示的位置到屏幕上边沿的距离
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private int getPopDistence() {
		titleHeight = win_title.getHeight();
		int height = mRgGroup.getHeight()
				+ MobileStateUtils.getStatusHeight(this) + titleHeight;
		return height;
	}

	// 记录分类一级菜单上的文字
	@SuppressWarnings("unused")
	private String fitstWords = "";
	// 记录分类二级菜单上的文字
	@SuppressWarnings("unused")
	private String secondWords = "";

	private PopDoubleList categoryPop = null;

	public void setRadioButtonColor(String word, MyRadioButton rb,
			boolean isSelect) {
		if (word.equals(rb.getmTxtName())) {
			rb.setmTxtNameColor(isSelect ? R.color.bg_buyer : R.color.bg_hui1);
		} else {
			rb.setmTxtNameColor(isSelect ? R.color.bg_buyer : R.color.bg_buyer);
		}
	}

	public void setRadioButtonName(MyRadioButton rb, String titleName) {
		if (!Utils.isStringEmpty(titleName)) {
			rb.setmTxtName(titleName);
		}
	}

	public void setRadioButtonPicture(String word, MyRadioButton rb,
			boolean isSelect) {
		if (word.equals(rb.getmTxtName())) {
			rb.setmImgChoice(isSelect ? R.drawable.red_choice_up
					: R.drawable.choice_down);
		} else {
			rb.setmImgChoice(isSelect ? R.drawable.red_choice_up
					: R.drawable.red_choice_down);
		}
	}

	/**
	 * 分类列表
	 * 
	 * @param v
	 */
	@SuppressLint("InflateParams")
	private void categoryPopWindow(final View v) {

		ArrayList<SelectedInfo> firstCateSelects = null;
		if (null == sourceInfo) {
			sourceInfo = CmallApplication.getBaseDataInfo();
		}
		if (sourceInfo == null) {
			displayToast("数据正在解析中...");
			return;
		}
		firstCateSelects = sourceInfo.getCategoryDatas();
		if (categoryPop == null) {
			categoryPop = new PopDoubleList(ContextUtil.getLayoutInflater(this)
					.inflate(R.layout.two_listview, null), this,
					getScreenWidth(), getScreenHeight() / 2, firstCateSelects,
					"");
			categoryPop.setPopShowOrDismissLister(new PopShowOrDismissLister() {

				@Override
				public void setPopShow() {
					setRadioButtonColor("分类", mRbCategory, true);
					setRadioButtonPicture("分类", mRbCategory, true);
				}

				@Override
				public void setPopDismiss() {
					setRadioButtonColor("分类", mRbCategory, false);
					setRadioButtonPicture("分类", mRbCategory, false);

				}
			});
			categoryPop.setPopListItemOnclick(new PopListItemOnclick() {

				@Override
				public void setPopListItemOnclick(int position,
						SelectedInfo parentInfo, SelectedInfo childInfo,
						boolean isParent) {
					setRadioButtonColor("分类", mRbCategory, false);
					setRadioButtonPicture("分类", mRbCategory, false);
					if (isParent) {
						setRadioButtonName(mRbCategory, parentInfo.getCm_categoryname());
						boolean isAll = "全部".equals(parentInfo.getCm_categoryname());
						deliSecoWords = "";
						if (isAll) {
							loadData("", 1);
						}

					} else {
						boolean isAll = "全部".equals(childInfo.getCm_categoryname());

						setRadioButtonName(mRbCategory,
								isAll ? parentInfo.getCm_categoryname() : childInfo.getCm_categoryname());
						if (!isAll) {
							loadData(childInfo.getCm_categorycode(), 1);
						} else {
							loadData(parentInfo.getCm_categorycode(), 1);
						}
					}

				}
			});
		}
		categoryPop.showPopupWindow(null, (ViewGroup) v, 2, darkview);

	}

	// 记录交割地一级菜单上的文字
	@SuppressWarnings("unused")
	private String deliWords;
	// 记录交割地二级菜单上的文字
	@SuppressWarnings("unused")
	private String deliSecoWords;

	private MultiLevelPop multiPop = null;

	private void deliveryMultiPop(View v) {

		ArrayList<SelectedInfo> firstCitySelects = null;
		if (null == sourceInfo) {
			sourceInfo = CmallApplication.getBaseDataInfo();
		}
		if (sourceInfo == null) {
			displayToast("数据正在解析中...");
			return;
		}
		firstCitySelects = sourceInfo.getDeliveryDatas();
		if (Utils.isStringEmpty(firstCitySelects)) {
			displayToast("无数据或者解析有错误");
			return;
		}
		if (multiPop == null) {
			multiPop = new MultiLevelPop(new LinearLayout(this), this, getScreenWidth(),
					getScreenHeight() / 2 /** 2 / 3 */
					, firstCitySelects, "");
			multiPop.setPopShowOrDismissLister(new PopShowOrDismissLister() {

				@Override
				public void setPopShow() {
					setRadioButtonColor("交割地", mRbArea, true);
					setRadioButtonPicture("交割地", mRbArea, true);
				}

				@Override
				public void setPopDismiss() {
					setRadioButtonColor("交割地", mRbArea, false);
					setRadioButtonPicture("交割地", mRbArea, false);
					
				}
			});
			multiPop.setItemOnclick(new MultiPopListItemOnclick() {

				@Override
				public void setPopListItemOnclick(int position, Map<Integer, SelectedInfo> mapInfo,
						SelectedInfo currentInfo, boolean isParent) {

					if ("全部".equals(currentInfo.getCm_categoryname())) {
						if (currentInfo.getFlag() != NumEntity.FIRST) {
							SelectedInfo parentInfo = mapInfo.get(currentInfo.getFlag() - 1);
							mRbArea.setmTxtName(parentInfo.getCm_categoryname());
							loadData(parentInfo.getCm_categorycode(), 2);
						} else {
							mRbArea.setmTxtName(currentInfo.getCm_categoryname());
							loadData(currentInfo.getCm_categorycode(), 2);
						}
					} else {
						if (NumEntity.FOUR == currentInfo.getFlag()) {
							mRbArea.setmTxtName(currentInfo.getCm_categoryname());
							loadData(currentInfo.getCm_categorycode(), 2);
						}
					}
				}
			});
		}
		multiPop.showPopupWindow(null, (ViewGroup) mRgGroup, 2, darkview);
	}
	
	
//	private PopDoubleList areaPop = null;

	/**
	 * 地区列表
	 */
	@SuppressLint("InflateParams")
//	private void areaPopWindow(final View v) {
//
//		ArrayList<SelectedInfo> firstCitySelects = null;
//		if (null == sourceInfo) {
//			sourceInfo = CmallApplication.getBaseDataInfo();
//		}
//		if (sourceInfo == null) {
//			displayToast("数据正在解析中...");
//			return;
//		}
//		firstCitySelects = sourceInfo.getDeliveryDatas();
//
//		if (Utils.isStringEmpty(firstCitySelects)) {
//			displayToast("无数据或者解析有错误");
//			return;
//		}
//		if (areaPop == null) {
//			areaPop = new PopDoubleList(ContextUtil.getLayoutInflater(this)
//					.inflate(R.layout.two_listview, null), this,
//					getScreenWidth(), getScreenHeight() / 2, firstCitySelects,
//					"");
//			areaPop.setPopShowOrDismissLister(new PopShowOrDismissLister() {
//
//				@SuppressLint("InflateParams")
//				@Override
//				public void setPopShow() {
//					setRadioButtonColor("交割地", mRbArea, true);
//					setRadioButtonPicture("交割地", mRbArea, true);
//				}
//
//				@Override
//				public void setPopDismiss() {
//					setRadioButtonColor("交割地", mRbArea, false);
//					setRadioButtonPicture("交割地", mRbArea, false);
//				}
//			});
//			areaPop.setPopListItemOnclick(new PopListItemOnclick() {
//
//				@Override
//				public void setPopListItemOnclick(int position,
//						SelectedInfo parentInfo, SelectedInfo childInfo,
//						boolean isParent) {
//					setRadioButtonColor("交割地", mRbArea, false);
//					setRadioButtonPicture("交割地", mRbArea, false);
//					if (isParent) {
//						setRadioButtonName(mRbArea,
//								parentInfo.getCm_categoryname());
//						deliSecoWords = "";
//						loadData("", 2);
//					} else {
//						boolean isAll = "全部".equals(childInfo
//								.getCm_categoryname());
//						setRadioButtonName(mRbArea,
//								isAll ? parentInfo.getCm_categoryname()
//										: childInfo.getCm_categoryname());
//						loadData(childInfo.getCm_categorycode(), 2);
//					}
//				}
//			});
//		}
//		areaPop.showPopupWindow(null, (ViewGroup) v, 2, darkview);
//	}

	// 记录记录默认排序一级菜单上的文字
	private String sortWords;
	private PopSortList sortList;

	/**
	 * 默认排序
	 */
	@SuppressLint({ "ResourceAsColor", "InflateParams" })
	private void defaultSortPopWindow(final View v) {

		ArrayList<SelectedInfo> sortWays = null;
		if (null == sourceInfo) {
			sourceInfo = CmallApplication.getBaseDataInfo();
		}
		if (sourceInfo == null) {
			displayToast("数据正在解析中...");
			return;
		}
		sortWays = sourceInfo.getSortWays();
		if (Utils.isStringEmpty(sortWays)) {
			displayToast("暂无数据");
			return;
		}
		if (sortList == null) {
			sortList = new PopSortList(ContextUtil.getLayoutInflater(this)
					.inflate(R.layout.list_view_pop, null), this,
					getScreenWidth(), LayoutParams.WRAP_CONTENT, sortWays,
					sortWords);

			sortList.setPopShowOrDismissLister(new PopShowOrDismissLister() {

				@SuppressLint("InflateParams")
				@Override
				public void setPopShow() {
					setRadioButtonColor("默认排序", mRbDefaultSort, true);
					setRadioButtonPicture("默认排序", mRbDefaultSort, true);
				}

				@Override
				public void setPopDismiss() {
					setRadioButtonColor("默认排序", mRbDefaultSort, false);
					setRadioButtonPicture("默认排序", mRbDefaultSort, false);
				}
			});

			sortList.setPopListener(new OnPopItemClickListener() {

				@Override
				public void onItemClick(SelectedInfo info) {
					sortWords = info.getCm_categoryname();
					mRbDefaultSort.setmTxtName(sortWords);
					setRadioButtonColor("默认排序", mRbDefaultSort, false);
					setRadioButtonPicture("默认排序", mRbDefaultSort, false);
					loadData(info.getCm_categorycode(), 3);
				}
			});
		}

		sortList.showPopupWindow(null, (ViewGroup) v, 2, darkview);
	}

	@SuppressWarnings("unused")
	private int flag = 0;

	/**
	 * RadioButton的添加事件
	 */
	@Override
	public void onClick(View v) {
		pageIndex = 1;
		switch (v.getId()) {
		case R.id.rb_category:// 分类弹出框按钮点击
			flag = 1;
			categoryPopWindow(v);
			break;
		case R.id.rb_area:// 地区弹出框按钮
			flag = 2;
//			areaPopWindow(v);
			deliveryMultiPop(v);
			break;
		case R.id.rb_default_sort:// 默认排序弹出框按钮
			defaultSortPopWindow(v);
			break;
		}
	}

	@Override
	public void onItemClick(int position, View itemView) {
		GoodsSourceInfo info = (GoodsSourceInfo) purchaseAdapter.getDataList()
				.get(position);
		// 采购ID
		String purID = info.getGoodSID();
		goActivity(purID, PurchaseDetailsActivity.class, 0);
	}
}
