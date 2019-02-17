/**
 * 
 */
package com.baiyi.cmall.activities.main;

import java.util.ArrayList;

import org.json.JSONArray;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.mall.JsonParseMall;
import com.baiyi.cmall.activities.main.mall.UrlNet;
import com.baiyi.cmall.activities.main.mall.adapter.BaseRecyclerAdapter.OnItemClickListener;
import com.baiyi.cmall.activities.main.mall.adapter.MallCategoryAdapter;
import com.baiyi.cmall.activities.main.mall.adapter.MallHeadAdapter;
import com.baiyi.cmall.activities.main.mall.adapter.MallHeadAdapter.OnItemClickCategory;
import com.baiyi.cmall.activities.main.mall.entity.MallContentProEntity;
import com.baiyi.cmall.activities.main.mall.entity.MallListAllEntity;
import com.baiyi.cmall.activities.main.mall.entity.MallMainHeadCategory;
import com.baiyi.cmall.activities.main.mall.entity.MallMainHeadPisc;
import com.baiyi.cmall.activities.main.mall.entity.MallMainMenuEntity;
import com.baiyi.cmall.activities.main.mall.views.MallMenuView;
import com.baiyi.cmall.activities.main.mall.views.MallMenuView.MenuOnclickLister;
import com.baiyi.cmall.activities.main.total.TotalUtils;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.listitem.InScrollGridView;
import com.baiyi.cmall.utils.IntentClassChangeUtils;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.itemview.CommonSearchView;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import cn.lightsky.infiniteindicator.InfiniteIndicatorLayout;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView;
import cn.lightsky.infiniteindicator.slideview.DefaultSliderView;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView.OnSliderClickListener;

/**
 * 
 * 商城
 * 
 * @author tangkun
 * 
 */
public class ShopingMallActivity extends BaseActivity {

	private MallMenuView menu = null;
	private XRecyclerView listView = null;
	private MallCategoryAdapter adapter = null;

	private InScrollGridView categoryList = null;
	private MallHeadAdapter categoryAdapter = null;

	private InfiniteIndicatorLayout infiniteIndicatorLayout;

	// 暂无数据
	private TextView mTxtNoData;
	// 重新加载
	private TextView mTxtReload;

	View view = null;

	@Override
	protected void initWin(boolean hasScrollView) {
		super.initWin(false);
		CommonSearchView searchView = new CommonSearchView(this);
		win_title.addView(searchView);

		intitContent();
		loadMenu();
	}

	private void intitContent() {
		view = ContextUtil.getLayoutInflater(ShopingMallActivity.this).inflate(R.layout.pop_mall_category, null);

		initView(view);
		win_content.addView(view);
	}

	private void initView(View view) {

		menu = (MallMenuView) view.findViewById(R.id.mall_category_menu);
		listView = (XRecyclerView) view.findViewById(R.id.mallcategory__listview);
		mTxtNoData = (TextView) view.findViewById(R.id.no_data);
		mTxtReload = (TextView) view.findViewById(R.id.tv_reload);
		mTxtReload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loadMenu();
			}
		});
		listView.setPullRefreshEnabled(false);
		GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
		listView.setLayoutManager(layoutManager);
		initListHead();

	}

	private View header;

	private void initListHead() {
		header = ContextUtil.getLayoutInflater(this)//
				.inflate(R.layout.head_mall, null);
		listView.addHeaderView(header);

		infiniteIndicatorLayout = (InfiniteIndicatorLayout) header.findViewById(R.id.infinite_anim_circle);

		categoryList = (InScrollGridView) header.findViewById(R.id.gdv_category_list);
		categoryList.setSelector(new ColorDrawable(Color.TRANSPARENT));
	}

	private ArrayList<MallMainMenuEntity> data = null;

	private void loadMenu() {
		mTxtReload.setVisibility(View.GONE);
		loaderProgress();

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(UrlNet.getMallMainMenu());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setPostData("");
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				// TODO Auto-generated method stub
				stopProgress();
				mTxtReload.setVisibility(View.VISIBLE);
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				// TODO Auto-generated method stub
				data = JsonParseMall.getMainMenu((JSONArray) arg1);
				if (Utils.isStringEmpty(data)) {
					stopProgress();
					displayToast("没数据");
					return;
				}
				mTxtReload.setVisibility(View.GONE);

				loadContent(data.get(0).getCc());

				displayMenu();
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	private void loadContent(String cd) {

		loaderProgress();

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(UrlNet.getMallMain(cd));
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setPostData("");
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum, long totalByteNum) {

			}

			@Override
			public void onError(Object tag, int responseCode, String errorMessage) {

				stopProgress();
			}

			@Override
			public void onCompelete(Object tag, Object result) {
				// TODO Auto-generated method stub
				stopProgress();
				MallListAllEntity entity = JsonParseMall.getMallListAllEntity((JSONArray) result);

				displayHeadCategory(entity.getCategoryList());
				displayProList(entity.getProListEntity().getProList());
				displayAd(entity.getPicsList());

			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	protected void displayMenu() {

		menu.displayViews(0, data, new MenuOnclickLister() {

			@Override
			public void setMenuOnclickLister(int position, MallMainMenuEntity entity) {

				loadContent(entity.getCc());
			}
		});
	}

	/**
	 * 显示广告
	 */
	private void displayAd(ArrayList<MallMainHeadPisc> dataList) {

		if (Utils.isStringEmpty(dataList)) {
			// displayToast("广告图片数据空");
			infiniteIndicatorLayout.setVisibility(View.GONE);
			return;
		} else {
			infiniteIndicatorLayout.setVisibility(View.VISIBLE);
		}
		Log.d("AA", "dataList.size()  =   " + dataList.size());
		for (int i = 0; i < dataList.size(); i++) {
			DefaultSliderView textSliderView = new DefaultSliderView(this);
			String picUrl = dataList.get(i).getFp();
			if (!TextViewUtil.isStringEmpty(picUrl)) {
				textSliderView.image(picUrl);
			} else {
				textSliderView.image(R.drawable.test_mall_head_conver);
			}

			textSliderView.setScaleType(BaseSliderView.ScaleType.Fit)
					.setOnSliderClickListener(new OnSliderClickListener() {

						@Override
						public void onSliderClick(BaseSliderView slider) {

						}
					});
			
			infiniteIndicatorLayout.addSlider(textSliderView);

		}

		// 设置开始轮播
					infiniteIndicatorLayout.start();
		// 设置指示器的显示位置
		infiniteIndicatorLayout.setIndicatorPosition(InfiniteIndicatorLayout.IndicatorPosition.Center_Bottom);
		// 设置自动开始轮播，默认向右
		infiniteIndicatorLayout.startAutoScroll();
		// 设置轮播的时间为5 秒
		infiniteIndicatorLayout.setInterval(5 * 1000);

	}

	/**
	 * 显示关键字分类
	 */
	private void displayHeadCategory(ArrayList<MallMainHeadCategory> dataList) {

		if (null == categoryAdapter) {
			categoryAdapter = new MallHeadAdapter(this);
			categoryList.setAdapter(categoryAdapter);
			categoryAdapter.setItemClick(new OnItemClickCategory() {

				@Override
				public void setItemOnclick(int position, MallMainHeadCategory entity) {
					// TODO Auto-generated method stub
					IntentClassChangeUtils.startCategoryMall(ShopingMallActivity.this, entity.getCc());
				}
			});
		}
		categoryAdapter.setData(dataList);
	}

	/**
	 * 显示产品列表
	 */
	private void displayProList(final ArrayList<MallContentProEntity> dataList) {

		if (adapter == null) {
			adapter = new MallCategoryAdapter(this);
			listView.setAdapter(adapter);
			adapter.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(int position, View itemView) {
					String id = adapter.getDataList().get(position).getId() + "";
					IntentClassChangeUtils.startMallDetail(ShopingMallActivity.this, id);
				}
			});
		}
		adapter.setDatas(dataList);

		// 显示暂无数据
		if (Utils.isStringEmpty(dataList)) {
			mTxtNoData.setPadding(0, getScreenHeight() - header.getHeight() * 2, 0, 0);
			mTxtNoData.setVisibility(View.VISIBLE);
			adapter.setDatas(null);
			return;
		} else {
			mTxtNoData.setVisibility(View.GONE);
		}

	}

}
