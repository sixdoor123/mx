/**
 * 
 */
package com.baiyi.cmall.activities.main.mall.pop;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import cn.lightsky.infiniteindicator.InfiniteIndicatorLayout;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView;
import cn.lightsky.infiniteindicator.slideview.DefaultSliderView;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView.OnSliderClickListener;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.mall.JsonParseMall;
import com.baiyi.cmall.activities.main.mall.UrlNet;
import com.baiyi.cmall.activities.main.mall.adapter.MallCategoryAdapter;
import com.baiyi.cmall.activities.main.mall.adapter.MallHeadAdapter;
import com.baiyi.cmall.activities.main.mall.adapter.BaseRecyclerAdapter.OnItemClickListener;
import com.baiyi.cmall.activities.main.mall.adapter.MallHeadAdapter.OnItemClickCategory;
import com.baiyi.cmall.activities.main.mall.entity.MallContentProEntity;
import com.baiyi.cmall.activities.main.mall.entity.MallListAllEntity;
import com.baiyi.cmall.activities.main.mall.entity.MallMainHeadCategory;
import com.baiyi.cmall.activities.main.mall.entity.MallMainHeadPisc;
import com.baiyi.cmall.activities.main.mall.entity.MallMainMenuEntity;
import com.baiyi.cmall.activities.main.mall.entity.MallProListEntity;
import com.baiyi.cmall.activities.main.mall.views.MallMenuView;
import com.baiyi.cmall.activities.main.mall.views.MallMenuView.MenuOnclickLister;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.listitem.InScrollGridView;
import com.baiyi.cmall.utils.IntentClassChangeUtils;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.pop.BasePopupWindow;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * 商城-分类
 * 
 * @author tangkun
 * 
 */
public class PopMallMenuCagetory extends BasePopupWindow {

	private Context context = null;

	private MallMenuView menu = null;
	private XRecyclerView listView = null;

	private MallCategoryAdapter adapter = null;

	private InScrollGridView categoryList = null;
	private MallHeadAdapter categoryAdapter = null;

	private InfiniteIndicatorLayout infiniteIndicatorLayout;

	/**
	 * @param contentView
	 * @param context
	 * @param height
	 */
	public PopMallMenuCagetory(View contentView, Context context, int width, int height) {
		super(contentView, context, width, height);
		// TODO Auto-generated constructor stub
		this.context = context;
		initView(contentView);
	}

	private static final String[] menuTexts = { "生物及医药化学品", "有机原料与中间体", "精细化学品", "专用和特种化学品", "无机化学品", "石油化学品",
			"试剂及研发化合物" };
	// 暂无数据
	private TextView mTxtNoData;

	private MyLoadingBar loadingBar;

	private void initView(View view) {

		menu = (MallMenuView) view.findViewById(R.id.mall_category_menu);
		// menu.init(0, menuTexts, new MenuOnclickLister() {
		//
		// @Override
		// public void setMenuOnclickLister(int position) {
		// // TODO Auto-generated method stub
		//
		// }
		// });
		listView = (XRecyclerView) view.findViewById(R.id.mallcategory__listview);
		mTxtNoData = (TextView) view.findViewById(R.id.no_data);
		loadingBar = (MyLoadingBar) view.findViewById(R.id.load_category);

		listView.setPullRefreshEnabled(false);
		GridLayoutManager layoutManager = new GridLayoutManager(context, 3);
		listView.setLayoutManager(layoutManager);
		initListHead();
		loadMenu();
	}

	private View header = null;

	private void initListHead() {
		header = ContextUtil.getLayoutInflater(context).inflate(R.layout.head_mall, null);
		listView.addHeaderView(header);

		infiniteIndicatorLayout = (InfiniteIndicatorLayout) header.findViewById(R.id.infinite_anim_circle);

		categoryList = (InScrollGridView) header.findViewById(R.id.gdv_category_list);
		categoryList.setSelector(new ColorDrawable(Color.TRANSPARENT));
	}

	private void start() {
		if (loadingBar != null) {
			loadingBar.setVisibility(View.VISIBLE);
			loadingBar.start();
			loadingBar.setProgressInfo("加载中,请稍后...");
		}
	}

	private void stop() {
		if (loadingBar != null) {
			loadingBar.setVisibility(View.GONE);
			loadingBar.stop();
		}
	}

	private void loadMenu() {

		start();

		JsonLoader loader = new JsonLoader(context);
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
				((BaseActivity) context).displayToast(arg2);
				stop();
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {

				stop();

				ArrayList<MallMainMenuEntity> data = JsonParseMall.getMainMenu((JSONArray) arg1);
				if (Utils.isStringEmpty(data)) {
					// displayToast("没数据");
					return;
				}
				menu.displayViews(0, data, new MenuOnclickLister() {

					@Override
					public void setMenuOnclickLister(int position, MallMainMenuEntity entity) {
						// TODO Auto-generated method stub
						loadContent(entity.getCc());
					}
				});
				loadContent(data.get(0).getCc());

			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	private void loadContent(String cd) {

		start();

		JsonLoader loader = new JsonLoader(context);
		loader.setUrl(UrlNet.getMallMain(cd));
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setPostData("");
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum, long totalByteNum) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(Object tag, int responseCode, String errorMessage) {
				((BaseActivity) context).displayToast(errorMessage);

				stop();
			}

			@Override
			public void onCompelete(Object tag, Object result) {

				stop();

				MallListAllEntity entity = JsonParseMall.getMallListAllEntity((JSONArray) result);
				displayAd(entity.getPicsList());
				displayHeadCategory(entity.getCategoryList());
				displayProList(entity.getProListEntity().getProList());

			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * 显示广告
	 */
	private void displayAd(ArrayList<MallMainHeadPisc> dataList) {

		if (Utils.isStringEmpty(dataList)) {
			// displayToast("广告图片数据空");
			infiniteIndicatorLayout.setVisibility(View.GONE);
			return;
		}

		infiniteIndicatorLayout.setVisibility(View.VISIBLE);
		for (int i = 0; i < dataList.size(); i++) {
			DefaultSliderView textSliderView = new DefaultSliderView(context);
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
			categoryAdapter = new MallHeadAdapter(context);
			categoryAdapter.setItemClick(new OnItemClickCategory() {

				@Override
				public void setItemOnclick(int position, MallMainHeadCategory entity) {
					// TODO Auto-generated method stub
					IntentClassChangeUtils.startCategoryMall(context, entity.getCc());
				}
			});

			categoryList.setAdapter(categoryAdapter);
		}
		categoryAdapter.setData(dataList);
	}

	/**
	 * 显示产品列表
	 */
	private void displayProList(ArrayList<MallContentProEntity> dataList) {
		// 显示暂无数据
		if (/* TotalUtils.getIntence().getTotal() <= 0 */Utils.isStringEmpty(dataList)) {
			mTxtNoData.setPadding(0, ((BaseActivity) context).getScreenHeight() - header.getHeight() * 2, 0, 0);
			mTxtNoData.setVisibility(View.VISIBLE);
		} else {
			mTxtNoData.setVisibility(View.GONE);
		}
		if (Utils.isStringEmpty(dataList)) {
			// displayToast("产品列表数据空");
			adapter.setDatas(null);
			return;
		}

		if (adapter == null) {
			adapter = new MallCategoryAdapter(context);
			listView.setAdapter(adapter);
			adapter.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(int position, View itemView) {
					String id = adapter.getDataList().get(position).getId() + "";
					IntentClassChangeUtils.startMallDetail(context, id);
				}
			});
		}
		adapter.setDatas(dataList);
	}

}
