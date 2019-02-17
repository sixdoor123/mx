package com.baiyi.cmall.activities.main.mall;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.mall.adapter.MallAdapter;
import com.baiyi.cmall.activities.main.mall.adapter.BaseRecyclerAdapter.OnItemClickListener;
import com.baiyi.cmall.activities.main.mall.entity.MallProEntity;
import com.baiyi.cmall.activities.main.mall.pop.PopMallMenuCagetory;
import com.baiyi.cmall.activities.main.mall.pop.PopMallMenuFilter;
import com.baiyi.cmall.activities.main.mall.pop.PopMallMenuFilter.FilterItemOnclick;
import com.baiyi.cmall.activities.main.mall.pop.entity.Qcm;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.request.manager.MallManager;
import com.baiyi.cmall.utils.IntentClassChangeUtils;
import com.baiyi.cmall.utils.MobileStateUtils;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.itemview.CommonSearchView;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;

/**
 * @author tangkun
 * 
 */
public class MallCagetoryActivity extends BaseActivity {

	private LinearLayout btnCatory = null;
	private LinearLayout btnSalas = null;
	private LinearLayout btnPrice = null;
	private LinearLayout btnFilter = null;

	private ImageView imgPrice = null;
	private ImageView ImgSalas = null;

	private XRecyclerView listView = null;
	private TextView mTvEmpty;

	private MallAdapter adapter = null;

	private PopMallMenuCagetory popMallMenuCagetory = null;
	private PopMallMenuFilter popMallMenuFilter = null;

	private String cc;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(false);

		initTitle();
		ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_pro_price, win_content);
		initMenu();
		initListView();

		initData();
	}

	// 1：表示 搜索
	private int state = -1;

	private void initData() {
		cc = getIntent().getStringExtra(MallDefine.CC);
		state = getIntent().getIntExtra(MallDefine.State, 0);
		if (1 == state) {
			searchView.setMTvSearch(cc);
		}
		loadData(cc, state, qcms);
	}

	private void initMenu() {
		btnCatory = (LinearLayout) findViewById(R.id.btn_mall_catory);
		btnSalas = (LinearLayout) findViewById(R.id.btn_mall_sales);
		btnPrice = (LinearLayout) findViewById(R.id.btn_mall_price);
		btnFilter = (LinearLayout) findViewById(R.id.btn_mall_filter);
		btnCatory.setOnClickListener(new MenuOnClick());
		btnSalas.setOnClickListener(new MenuOnClick());
		btnPrice.setOnClickListener(new MenuOnClick());
		btnFilter.setOnClickListener(new MenuOnClick());

		ImgSalas = (ImageView) findViewById(R.id.img_mall_sales);
		imgPrice = (ImageView) findViewById(R.id.img_mall_price);
	}

	private void initListView() {

		mTvEmpty = (TextView) findViewById(R.id.empty);

		listView = (XRecyclerView) findViewById(R.id.pro_listview);
		GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
		listView.setLayoutManager(layoutManager);
		adapter = new MallAdapter(this);
		listView.setAdapter(adapter);

		// 条目点击
		adapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(int position, View itemView) {
				// TODO Auto-generated method stub
				IntentClassChangeUtils.startMallDetail(MallCagetoryActivity.this,
						adapter.getDataList().get(position).getId() + "");
			}
		});

		// 刷新/加载数据
		listView.setLoadingListener(new LoadingListener() {

			@Override
			public void onRefresh() {
				pi = 1;
				loadData("", -1, qcms);
			}

			@Override
			public void onLoadMore() {
				pi++;
				loadData("", -1, qcms);
			}
		});

	}

	private int pi = 1;
	private int ps = 10;
	// 筛选回传
	private List<Qcm> qcms = null;

	private void loadData(String cc, int state, List<Qcm> qcms) {

		startLoading();

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(UrlNet.getMallCategoryList());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setPostData(MallManager.getCategoryListData(cc, state, qcms, pi, ps));
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum, long totalByteNum) {
			}

			@Override
			public void onError(Object tag, int responseCode, String errorMessage) {
				displayToast(errorMessage);
				stopLoading();
			}

			@Override
			public void onCompelete(Object tag, Object result) {
				// TODO Auto-generated method stub
				stopLoading();
				ArrayList<MallProEntity> data = JsonParseMall.getCategoryProList((JSONArray) result);
				displayList(data);
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	private void displayList(final ArrayList<MallProEntity> data) {

		if (pi == 1) {
			adapter.setDatas(data);
			listView.refreshComplete();
		} else {
			adapter.addDatas(data);
			listView.loadMoreComplete();
		}

		if (pi == 1 && Utils.isStringEmpty(data)) {
			mTvEmpty.setVisibility(View.VISIBLE);
			return;
		} else {
			mTvEmpty.setVisibility(View.GONE);
		}

	}

	private CommonSearchView searchView = null;

	/**
	 * 初始化标题
	 */
	private void initTitle() {
		searchView = new CommonSearchView(this);
		win_title.addView(searchView);
		searchView.showAppView();
	}

	/**
	 * 全部分类
	 */
	private void displayCategory() {
		if (popMallMenuCagetory == null) {
			popMallMenuCagetory = new PopMallMenuCagetory(
					ContextUtil.getLayoutInflater(this).inflate(R.layout.pop_mall_category, null),
					MallCagetoryActivity.this, getScreenWidth() * 82 / 100,
					getScreenHeight() - MobileStateUtils.getStatusHeight(this));
			popMallMenuCagetory.setAnimationStyle(R.style.pop_left_to_right);
		}
		popMallMenuCagetory.showPopAtLocation(win_content, Gravity.LEFT | Gravity.BOTTOM);
	}

	/**
	 * 筛选
	 */
	private void displayFilter() {
		if (popMallMenuFilter == null) {
			popMallMenuFilter = new PopMallMenuFilter(
					ContextUtil.getLayoutInflater(this)//
							.inflate(R.layout.activity_filter, null),
					MallCagetoryActivity.this, getScreenWidth() * 82 / 100,
					getScreenHeight() - MobileStateUtils.getStatusHeight(this));
			popMallMenuFilter.setAnimationStyle(R.style.pop_right_to_left);
		}
		popMallMenuFilter.showPopAtLocation(win_content, Gravity.RIGHT | Gravity.BOTTOM);

		popMallMenuFilter.setFilterItemOnclick(new FilterItemOnclick() {

			@Override
			public void onFilterItemOnclickLister(List<Qcm> qcms) {
				MallCagetoryActivity.this.qcms = qcms;
				loadData("", 4, qcms);
			}
		});
	}

	private class MenuOnClick implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.btn_mall_catory) {// 分类
				displayCategory();
			} else if (id == R.id.btn_mall_sales) {// 销量
				if (ImgSalas.isSelected()) {
					// 升序
					ImgSalas.setSelected(false);
					loadData("4", 2, qcms);
				} else {
					// 降序
					ImgSalas.setSelected(true);
					loadData("3", 2, qcms);
				}
			} else if (id == R.id.btn_mall_price) {// 价格
				if (imgPrice.isSelected()) {
					// 升序
					imgPrice.setSelected(false);
					loadData("4", 3, qcms);
				} else {
					// 降序
					imgPrice.setSelected(true);
					loadData("3", 3, qcms);
				}
			} else if (id == R.id.btn_mall_filter) {// 筛选
				displayFilter();
			}
		}

	}

}
