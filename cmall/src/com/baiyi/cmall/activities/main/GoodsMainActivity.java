package com.baiyi.cmall.activities.main;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.broadcast.MyBroadCastReceiver;
import com.baiyi.cmall.activities.main.broadcast.MyBroadCastReceiver.OnFlushClickListener;
import com.baiyi.cmall.activities.main.home.itemview.DividerItemDecoration;
import com.baiyi.cmall.activities.main.provider.GoodSMainProviderDetailsActivity;
import com.baiyi.cmall.activities.main.provider.ProvideListAdapter;
import com.baiyi.cmall.activities.main.provider.pop.MultiLevelPop;
import com.baiyi.cmall.activities.main.provider.pop.MultiLevelPop.MultiPopListItemOnclick;
import com.baiyi.cmall.activities.main.total.TemporarilyNoData;
import com.baiyi.cmall.activities.main.total.TotalUtils;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.application.UserApplication;
import com.baiyi.cmall.entity.GoodSOriginInfo;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.request.manager.ProviderSourceManager;
import com.baiyi.cmall.request.manager.PublicActivityManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.LoadingBar;
import com.baiyi.cmall.views.itemview.CommonSearchView;
import com.baiyi.cmall.views.itemview.MyRadioButton;
import com.baiyi.cmall.views.pop.PopDoubleList;
import com.baiyi.cmall.views.pop.PopListItemOnclick;
import com.baiyi.cmall.views.pop.PopShowOrDismissLister;
import com.baiyi.cmall.views.pop.PopSortList;
import com.baiyi.cmall.views.pop.PopSortList.OnPopItemClickListener;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.cache.SimpleCacheLoader;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;
import com.jcodecraeer.xrecyclerview.port.OnRecycleViewItemClickListener;

/**
 * ��Ӧ��ҳ
 * 
 * @author tangkun
 * 
 */
@SuppressLint({ "NewApi", "ResourceAsColor", "InflateParams", "HandlerLeak" })
public class GoodsMainActivity extends BaseActivity implements OnClickListener, OnRecycleViewItemClickListener {
	// ����ʱ��֪ͨ
	// CountDownTimer
	// ������
	private RadioGroup mRgGroup;
	// �����б�
	private MyRadioButton mRbCategory;
	// �����б�
	private MyRadioButton mRbArea;
	// Ĭ���б�
	private MyRadioButton mRbDefaultSort;

	private View darkview = null;
	// �����б��������
	@SuppressWarnings("unused")
	private ArrayAdapter<?> adapter;

	// �����Ʒ��Ϣ�ļ���
	private ArrayList<GoodSOriginInfo> infos;

	// ����������ĸ߶� �����ڴ˱�����
	@SuppressWarnings("unused")
	private int titleHeight;
	// ���ListView��View
	private View view;

	// ���ܷ�������
	private GoodsSourceInfo sourceInfo;

	@Override
	protected void initWin(boolean hasScrollView) {
		super.initWin(false);
		initTitle();
		initSelectData();
		initView();
		initContent();
		initData();
		
	}

	private String keyString = null;

	@Override
	protected void onResume() {
		super.onResume();
		MyBroadCastReceiver.setListener(new OnFlushClickListener() {
			@Override
			public void onFlush(Intent intent) {
				keyString = intent.getStringExtra("key");
				if ("THREE".equals(keyString)) {
					reInit();
					loadData("", -1);
				}
			}
		});
		
		if (keyString == null) {
			pageIndex = 1;
			loadData("", -1);
		}

	}

	/**
	 * ���渴λ
	 */
	protected void reInit() {
		multiPop = null;
		categoryPop = null;
		sortList = null;
		mRbCategory.setmTxtName("����");
		mRbCategory.setmTxtNameColor(R.color.bg_hui1);
		mRbCategory.setmImgChoice(R.drawable.choice_down);
		mRbArea.setmTxtName("�����");
		mRbArea.setmTxtNameColor(R.color.bg_hui1);
		mRbArea.setmImgChoice(R.drawable.choice_down);
		mRbDefaultSort.setmTxtName("Ĭ������");
		mRbDefaultSort.setmTxtNameColor(R.color.bg_hui1);
		mRbDefaultSort.setmImgChoice(R.drawable.choice_down);
		sortWords = "";
	}

	/**
	 * ����ѡ������
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
			public void onProgress(Object arg0, long curByteNum, long totalByteNum) {
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				// displayToast("ѡ�����ݣ�" + arg2);
			}

			@Override
			public void onCompelete(Object arg0, Object result) {
				sourceInfo = PublicActivityManager.getSelectedData(GoodsMainActivity.this, result);

				UserApplication.setBaseDataInfo(sourceInfo);
				cacheBaseData(GoodsMainActivity.this, ((JSONArray) result).toString().getBytes());
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
	 * �����������
	 * 
	 * @param context
	 * @param data
	 */
	private static void cacheBaseData(Context context, byte[] data) {
		SimpleCacheLoader cache = new SimpleCacheLoader(CmallApplication.getBaseDataCache(context));
		cache.setUpdate(CmallApplication.BaseDataFileName, data);
		CmallApplication.getDataStratey().startLoader(cache);
	}

	private XRecyclerView mRecyclerView = null;
	@SuppressWarnings("rawtypes")
	private ProvideListAdapter ListAdapter;
	private TextView mTxtNoData = null;

	/**
	 * ��ʼ���б�
	 */
	@SuppressWarnings("rawtypes")
	private void initContent() {
		view = ContextUtil.getLayoutInflater(this).inflate(R.layout.recycleview_ff, null);
		win_content.addView(view);

		darkview = (View) view.findViewById(R.id.darkview);
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
		ListAdapter = new ProvideListAdapter(this);
		mRecyclerView.setAdapter(ListAdapter);

		// ��Ŀ����¼�
		ListAdapter.setItemClickListener(this);

		mRecyclerView.setLoadingListener(new LoadingListener() {

			@Override
			public void onRefresh() {
				pageIndex = 1;
				loadData("", 0);
			}

			@Override
			public void onLoadMore() {
				pageIndex += 1;
				loadData("", 0);
			}
		});
	}

	private String msg;
	@SuppressWarnings("unused")
	private Handler handler = new Handler() {
		@SuppressLint("HandlerLeak")
		public void handleMessage(android.os.Message msg) {
			GoodsMainActivity.this.displayToast(GoodsMainActivity.this.msg);
		};
	};

	/**
	 * ���Դӷ�������ȡ����
	 */
	@SuppressLint("InflateParams")
	private void loadData(String arg, int select) {

		startLoading();

		final JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getGoodsListUrl());
		loader.setPostData(ProviderSourceManager.getPostData(arg, select, pageIndex, Config.LIST_SIX_COUNT));
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum, long totalByteNum) {
			}

			public void onError(Object tag, int responseCode, String errorMessage) {
				stopLoading();
				displayToast(errorMessage);
			}

			@Override
			public void onCompelete(Object tag, Object result) {
				stopLoading();
				complete(result);

			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * ����������ɹ���
	 * 
	 * @param result
	 */
	protected void complete(Object result) {
		infos = ProviderSourceManager.getGoodSList(this, result);
		content();

		mRbArea.setEnabled(true);
		mRbCategory.setEnabled(true);
		mRbDefaultSort.setEnabled(true);
	}

	/**
	 * ��ʼ��������
	 */
	private void initTitle() {
		CommonSearchView searchView = new CommonSearchView(this);
		win_title.addView(searchView);
		searchView.showAppView();
	}

	private int pageIndex = 1;

	/**
	 * ���������б�
	 */
	@SuppressWarnings("unchecked")
	private void content() {
		if (null != mRecyclerView) {
			if (1 == pageIndex) {
				mRecyclerView.refreshComplete();
			} else {
				mRecyclerView.loadMoreComplete();
			}
		}
		// ��ʾ��������
		if (TotalUtils.getIntence().getTotal() <= 0 && pageIndex == 1) {
			mTxtNoData.setVisibility(View.VISIBLE);

		} else {
			mTxtNoData.setVisibility(View.GONE);
		}

		if (TotalUtils.getIntence().getTotal() <= 0 && pageIndex == 1) {
			TemporarilyNoData.getInstance(this).showWindow();
		}

		if (1 == pageIndex) {
			ListAdapter.setDatas(infos);
		} else {
			ListAdapter.addDatas(infos);
		}
	}

	// �����ؼ���
	private String searchInfo = "";

	/**
	 * ��ʼ�������б������
	 */
	private void initData() {

		// �����ؼ���
		searchInfo = getIntent().getStringExtra("temp");

		int searchState = getIntent().getIntExtra("id", 0);
		switch (searchState) {
		case 1:// ����ҳkey����ɸѡ
			ProviderSourceManager.getPostData("", -1, pageIndex, Config.LIST_SIX_COUNT);
			if (!TextUtils.isEmpty(searchInfo)) {
				loaderProgress();
				loadData(searchInfo, 4);
				return;
			}
			break;
		}

	}

	private View Cateview;

	/**
	 * ��ʼ�������View
	 */
	@SuppressLint("InflateParams")
	private void initView() {
		Cateview = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_filter_item, null);
		win_content.addView(Cateview);
		mRgGroup = (RadioGroup) findViewById(R.id.rg_grop_category);
		mRbCategory = (MyRadioButton) Cateview.findViewById(R.id.rb_category);
		mRbArea = (MyRadioButton) Cateview.findViewById(R.id.rb_area);
		mRbDefaultSort = (MyRadioButton) Cateview.findViewById(R.id.rb_default_sort);

		mRbCategory.setmTxtName("����");
		mRbArea.setmTxtName("�����");
		mRbDefaultSort.setmTxtName("Ĭ������");

		mRbArea.setOnClickListener(this);
		mRbCategory.setOnClickListener(this);
		mRbDefaultSort.setOnClickListener(this);
		mRbArea.setEnabled(false);
		mRbCategory.setEnabled(false);
		mRbDefaultSort.setEnabled(false);

	}

	public void setRadioButtonColor(String word, MyRadioButton rb, boolean isSelect) {
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

	public void setRadioButtonPicture(String word, MyRadioButton rb, boolean isSelect) {
		if (word.equals(rb.getmTxtName())) {
			rb.setmImgChoice(isSelect ? R.drawable.red_choice_up : R.drawable.choice_down);
		} else {
			rb.setmImgChoice(isSelect ? R.drawable.red_choice_up : R.drawable.red_choice_down);
		}
	}

	private PopDoubleList categoryPop = null;
	private SelectedInfo childInfo = null;
	private SelectedInfo catefirstInfo = null;

	/**
	 * �����б�
	 * 
	 * @param v
	 */
	private void categoryPopWindow(final View v) {

		ArrayList<SelectedInfo> firstCateSelects = null;
		if (null == sourceInfo) {
			sourceInfo = CmallApplication.getBaseDataInfo();
		}
		if (sourceInfo == null) {
			displayToast("�������ڽ�����...");
			return;
		}
		firstCateSelects = sourceInfo.getCategoryDatas();
		if (categoryPop == null) {
			childInfo = null;
			catefirstInfo = null;
			categoryPop = new PopDoubleList(ContextUtil.getLayoutInflater(this)//
					.inflate(R.layout.two_listview, null),
					this, getScreenWidth(), getScreenHeight() / 2, firstCateSelects, "");
			categoryPop.setPopShowOrDismissLister(new PopShowOrDismissLister() {

				@Override
				public void setPopShow() {
					setRadioButtonColor("����", mRbCategory, true);
					setRadioButtonPicture("����", mRbCategory, true);
				}

				@Override
				public void setPopDismiss() {
					setRadioButtonColor("����", mRbCategory, false);
					setRadioButtonPicture("����", mRbCategory, false);
					if (childInfo == null && catefirstInfo == null) {
						categoryPop = null;
					}
				}
			});
			categoryPop.setPopListItemOnclick(new PopListItemOnclick() {

				@Override
				public void setPopListItemOnclick(int position, SelectedInfo parentInfo, SelectedInfo childInfo,
						boolean isParent) {
					GoodsMainActivity.this.childInfo = childInfo;
					GoodsMainActivity.this.catefirstInfo = parentInfo;
					setRadioButtonColor("����", mRbCategory, false);
					setRadioButtonPicture("����", mRbCategory, false);
					if (isParent) {
						setRadioButtonName(mRbCategory, parentInfo.getCm_categoryname());
						boolean isAll = "ȫ��".equals(parentInfo.getCm_categoryname());
						deliSecoWords = "";
						if (isAll) {
							loadData("", 1);
						}

					} else {
						boolean isAll = "ȫ��".equals(childInfo.getCm_categoryname());

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

	// ��¼����ض����˵��ϵ�����
	@SuppressWarnings("unused")
	private String deliSecoWords;

	private MultiLevelPop multiPop = null;

	private void deliveryMultiPop(View v) {

		ArrayList<SelectedInfo> firstCitySelects = null;
		if (null == sourceInfo) {
			sourceInfo = CmallApplication.getBaseDataInfo();
		}
		if (sourceInfo == null) {
			displayToast("�������ڽ�����...");
			return;
		}
		firstCitySelects = sourceInfo.getDeliveryDatas();
		if (Utils.isStringEmpty(firstCitySelects)) {
			displayToast("�����ݻ��߽����д���");
			return;
		}
		if (multiPop == null) {
			multiPop = new MultiLevelPop(new LinearLayout(this), this, getScreenWidth(),
					getScreenHeight() / 2 /** 2 / 3 */
					, firstCitySelects, "");
			multiPop.setPopShowOrDismissLister(new PopShowOrDismissLister() {

				@Override
				public void setPopShow() {
					setRadioButtonColor("�����", mRbArea, true);
					setRadioButtonPicture("�����", mRbArea, true);
				}

				@Override
				public void setPopDismiss() {
					setRadioButtonColor("�����", mRbArea, false);
					setRadioButtonPicture("�����", mRbArea, false);
				}
			});
			multiPop.setItemOnclick(new MultiPopListItemOnclick() {

				@Override
				public void setPopListItemOnclick(int position, Map<Integer, SelectedInfo> mapInfo,
						SelectedInfo currentInfo, boolean isParent) {

					if ("ȫ��".equals(currentInfo.getCm_categoryname())) {
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

	// ��¼��¼Ĭ������һ���˵��ϵ�����
	private String sortWords;

	private PopSortList sortList;

	@SuppressWarnings("unused")
	private ArrayList<SelectedInfo> getList() {
		ArrayList<SelectedInfo> datas = new ArrayList<SelectedInfo>();

		for (int i = 0; i < 8; i++) {
			datas.add(new SelectedInfo("12" + i, "����" + i));
		}

		return datas;
	}

	/**
	 * Ĭ������
	 */
	@SuppressLint("ResourceAsColor")
	private void defaultSortPopWindow(final View v) {
		ArrayList<SelectedInfo> sortWays = null;
		if (null == sourceInfo) {
			sourceInfo = CmallApplication.getBaseDataInfo();
		}
		if (sourceInfo == null) {
			displayToast("�������ڽ�����...");
			return;
		}
		sortWays = sourceInfo.getSortWays();
		if (Utils.isStringEmpty(sortWays)) {
			displayToast("��������");
			return;
		}

		if (sortList == null) {
			sortList = new PopSortList(ContextUtil.getLayoutInflater(this).inflate(R.layout.list_view_pop, null), this,
					getScreenWidth(), LayoutParams.WRAP_CONTENT, sortWays, sortWords);

			sortList.setPopShowOrDismissLister(new PopShowOrDismissLister() {

				@Override
				public void setPopShow() {
					setRadioButtonColor("Ĭ������", mRbDefaultSort, true);
					setRadioButtonPicture("Ĭ������", mRbDefaultSort, true);
				}

				@Override
				public void setPopDismiss() {
					setRadioButtonColor("Ĭ������", mRbDefaultSort, false);
					setRadioButtonPicture("Ĭ������", mRbDefaultSort, false);
				}
			});

			sortList.setPopListener(new OnPopItemClickListener() {

				@Override
				public void onItemClick(SelectedInfo info) {
					sortWords = info.getCm_categoryname();
					mRbDefaultSort.setmTxtName(sortWords);
					setRadioButtonColor("Ĭ������", mRbDefaultSort, false);
					setRadioButtonPicture("Ĭ������", mRbDefaultSort, false);
					loadData(info.getCm_categorycode(), 3);
				}
			});
		}

		sortList.showPopupWindow(null, (ViewGroup) v, 2, darkview);

	}

	@SuppressWarnings("unused")
	private int flag = 0;

	/**
	 * RadioButton������¼�
	 */
	@Override
	public void onClick(View v) {
		pageIndex = 1;
		switch (v.getId()) {
		case R.id.rb_category:// ���൯����ť���
			flag = 1;
			categoryPopWindow(v);
			break;
		case R.id.rb_area:// ����������ť
			flag = 2;
			deliveryMultiPop(v);
			break;
		case R.id.rb_default_sort:// Ĭ�����򵯳���ť
			defaultSortPopWindow(v);
			break;
		}
	}

	@Override
	public <T> void onItemClick(View view, int position, T t) {

		// GoodSOriginInfo info = (GoodSOriginInfo) parent
		// .getItemAtPosition(position);
		if (null != t) {
			GoodSOriginInfo info2 = (GoodSOriginInfo) t;
			goActivity(info2.getId(), GoodSMainProviderDetailsActivity.class);
		}
	}

}
