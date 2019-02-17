package com.baiyi.cmall.activities.main.home_pager;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import cn.lightsky.infiniteindicator.InfiniteIndicatorLayout;
import cn.lightsky.infiniteindicator.PageInfo;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView.OnSliderClickListener;
import cn.lightsky.infiniteindicator.slideview.DefaultSliderView;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.header_top.PublishPurchaseActivity;
import com.baiyi.cmall.activities.main.header_top.PublishSupplyActivity;
import com.baiyi.cmall.activities.main.home_pager.adapter.FlashCaleAdapter;
import com.baiyi.cmall.activities.main.home_pager.adapter.HotBusinessAdapter;
import com.baiyi.cmall.activities.main.home_pager.entity.MainEntity;
import com.baiyi.cmall.activities.main.home_pager.entity.ZiXunEntity;
import com.baiyi.cmall.activities.main.home_pager.view.PublicNoticeView;
import com.baiyi.cmall.activities.main.provider.GoodSMainProviderDetailsActivity;
import com.baiyi.cmall.activities.main.purchase.PurchaseDetailsActivity;
import com.baiyi.cmall.activities.user.buyer.adapter.BaseRCAdapter.OnItemClickListeners;
import com.baiyi.cmall.activities.user.login.MemberLoginActivity;
import com.baiyi.cmall.activities.user.login.web.MerchantEntryActivity;
import com.baiyi.cmall.activities.webview.NewsWebViewActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.dialog.MsgBoxNoticeE.MsgBoxEOnClickListener;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.listitem.InScrollGridView;
import com.baiyi.cmall.request.manager.MarketPriceSourceManager;
import com.baiyi.cmall.request.manager.NullJsonData;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.IntentClassChangeUtils;
import com.baiyi.cmall.utils.NetUtils;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.itemview.CommonSearchView;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;

/**
 * 首页
 * 
 * @author lizl
 * 
 */
public class MainActivity extends BaseActivity implements OnClickListener {

	// 首页图片翻页容器
	private InfiniteIndicatorLayout picViewPager = null;
	// ListView头部显示的内容控件
	private View headerView;
	// 图片位置
	private int position;
	// 标记图片第一次刷新成功
	private boolean picIsFirstRefresh = true;

	// 免费入驻
	private TextView mTvHomeSettled;
	// 发布供应
	private TextView mTvHomeSupply;
	// 委托采购
	private TextView mTvHomePurchase;
	// 资讯---------------------------
	private PublicNoticeView noticeView;

	// 限时抢购
	private XRecyclerView mRcFlashSale;
	private FlashCaleAdapter flashCaleAdapter;
	// 推荐商家
	private InScrollGridView mGdvHotBusiness;
	private HotBusinessAdapter businessAdapter;

	// 新闻数据源
	private ArrayList<MainEntity> newsList = null;
	// 资讯数据源
	private ArrayList<ZiXunEntity> zixunS = null;
	// 限时抢购数据源
	private ArrayList<GoodsSourceInfo> flashCaleList = null;
	// 推荐商家数据源
	private ArrayList<MainEntity> hotBrandS = null;

	private ImageView imageNo;

	@Override
	protected void initWin(boolean hasScrollView) {

		super.initWin(false);
		initTitle();
		initView();
		initNetData();

	}

	/**
	 * 初始化标题
	 */
	private void initTitle() {

		CommonSearchView searchView = new CommonSearchView(this);
		win_title.addView(searchView);
		searchView.showAppView();

	}

	/**
	 * 添加主界面内容
	 */
	private void initView() {

		ContextUtil.getLayoutInflater(this).inflate(R.layout.recycleview_list,
				win_content);

		/*
		 * 所要添加的头部VIEW初始化
		 */
		headerView = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_main_content, null);

		mRcFlashSale = (XRecyclerView) findViewById(R.id.rc_list);
		mRcFlashSale.addHeaderView(headerView);

		initNewsView();
		initEnterView();
		initZiXunView();
		initHotBusinessView();
		initFlashSaleView();
	}

	/**
	 * 限时抢购初始化
	 */
	private void initFlashSaleView() {

		mRcFlashSale.setLayoutManager(new LinearLayoutManager(this,
				LinearLayoutManager.VERTICAL, false));

		flashCaleAdapter = new FlashCaleAdapter(this);
		mRcFlashSale.setAdapter(flashCaleAdapter);

		mRcFlashSale.setLoadingListener(new LoadingListener() {

			@Override
			public void onRefresh() {
				initNetData();
			}

			@Override
			public void onLoadMore() {
				mRcFlashSale.loadMoreComplete();
			}
		});

		flashCaleAdapter.setOnItemClickListener(new OnItemClickListeners() {
			@Override
			public void onItemClick(int position, View itemView) {

				goActivity(HomePagerPurActivity.class, flashCaleAdapter
						.getDataList().get(position).getId());

			}
		});
	}

	/**
	 * 新闻初始化-------------------------------------------
	 */
	private void initNewsView() {

		picViewPager = (InfiniteIndicatorLayout) headerView
				.findViewById(R.id.infinite_anim_circle);
		picViewPager = (InfiniteIndicatorLayout) headerView
				.findViewById(R.id.infinite_anim_circle);
		imageNo = (ImageView) headerView.findViewById(R.id.img_no);
		setViewPagerHeight();
	}

	/**
	 * 按照 宽/高=320/800 设置显示新闻的尺寸
	 */
	private void setViewPagerHeight() {

		int height = Math.round(getScreenWidth() * (320f / 800f));

		LayoutParams linearParams = new LayoutParams(getScreenWidth(), height);

		picViewPager.setLayoutParams(linearParams);

	}

	/**
	 * 免费入驻/发布供应/委托采购 初始化----------------------------
	 */
	private void initEnterView() {

		mTvHomeSettled = (TextView) headerView
				.findViewById(R.id.tv_home_settled);
		mTvHomeSupply = (TextView) headerView.findViewById(R.id.tv_home_supply);
		mTvHomePurchase = (TextView) headerView
				.findViewById(R.id.tv_home_purchase);
		mTvHomeSettled.setOnClickListener(this);
		mTvHomeSupply.setOnClickListener(this);
		mTvHomePurchase.setOnClickListener(this);
	}

	/**
	 * 资讯初始化----------------------------------------------
	 */
	private void initZiXunView() {

		noticeView = (PublicNoticeView) headerView.findViewById(R.id.pnv_zixun);
	}

	/**
	 * 推荐商家初始化
	 */
	private void initHotBusinessView() {

		mGdvHotBusiness = (InScrollGridView) headerView
				.findViewById(R.id.gdv_list);
		// GridView自带点击选择器，边框为黄色，在此去掉
		mGdvHotBusiness.setSelector(new ColorDrawable(Color.TRANSPARENT));
		businessAdapter = new HotBusinessAdapter(this);
		mGdvHotBusiness.setAdapter(businessAdapter);
		mGdvHotBusiness.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// TODO 在此做条目点击跳转事件
				IntentClassChangeUtils.startShopDetailActivity(
						MainActivity.this,
						Utils.isStringEmpty(hotBrandS.get(position).getId()) ? 1
								: Integer.valueOf(hotBrandS.get(position)
										.getId()));
			}
		});
	}

	/**
	 * 加载基本的JSON数据
	 */
	private void initNetData() {

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getMainPagerUrl());
		loader.setPostData(NullJsonData.getPostData());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setLoaderListener(new LoaderListener() {

			public void onProgress(Object tag, long curByteNum,
					long totalByteNum) {
			}

			public void onError(Object tag, int responseCode,
					String errorMessage) {
				displayToast(errorMessage);
			}

			public void onCompelete(Object tag, Object result) {
				Log.d("TAG", "result = " + result);
				if ((result.toString().startsWith("{") && result.toString()
						.length() < 4)
						|| (result).toString().startsWith("<html>")) {
					return;
				}

				JSONArray array = (JSONArray) result;
				MainEntity entity = MarketPriceSourceManager
						.getMainActivityInfo(array);

				newsList = (ArrayList<MainEntity>) entity.getNewsInfoList();
				zixunS = (ArrayList<ZiXunEntity>) entity.getZixunInfoList();
				flashCaleList = (ArrayList<GoodsSourceInfo>) entity
						.getFlashCaleList();
				hotBrandS = (ArrayList<MainEntity>) entity.getHotBusinessList();
				Log.d("TAG",
						newsList.size() + "-----" + zixunS.size() + "------"
								+ flashCaleList.size() + "----"
								+ hotBrandS.size());

				refreshDispaly();
				mRcFlashSale.refreshComplete();
			}

		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * 刷新界面的 数据
	 */
	private void refreshDispaly() {

		/*
		 * 刷新新闻的数据
		 */
		if (!Utils.isStringEmpty(newsList)) {

			picViewPager.setVisibility(View.VISIBLE);
			imageNo.setVisibility(View.GONE);

			// 图片进来后只要成功后在不做刷新
			if (picIsFirstRefresh) {
				displayImgs();
				picIsFirstRefresh = false;
			}
		} else {

			picViewPager.setVisibility(View.GONE);
			imageNo.setVisibility(View.VISIBLE);
		}

		/*
		 * 刷新资讯的数据
		 */
		if (!Utils.isStringEmpty(zixunS)) {

			noticeView.bindNotices(zixunS);
		}

		/*
		 * 刷新推荐商家数据
		 */
		if (!Utils.isStringEmpty(hotBrandS)) {

			businessAdapter.setData(hotBrandS);
		}

		/*
		 * 刷新限时抢购列表
		 */
		if (!Utils.isStringEmpty(flashCaleList)) {

			flashCaleAdapter.setDatas(flashCaleList);
		}
	}

	/**
	 * 显示首页轮播网页图片--------------------------------------------------------
	 */
	private void displayImgs() {

		final List<PageInfo> pi = new ArrayList<PageInfo>();
		for (int i = 0; i < newsList.size(); i++) {
			PageInfo p = new PageInfo("pageInfo" + i, newsList.get(i)
					.getPicPath(), newsList.get(i).getUrl());
			pi.add(p);
		}

		for (int i = 0; i < pi.size(); i++) {
			position = i;
			DefaultSliderView textSliderView = new DefaultSliderView(this);

			Log.d("TAG", pi.get(position).getUrl());
			// textSliderView.image(R.drawable.banner);
			textSliderView.image(pi.get(position).getUrl())
					.setScaleType(BaseSliderView.ScaleType.Fit)
					.setOnSliderClickListener(new OnSliderClickListener() {

						@Override
						public void onSliderClick(BaseSliderView slider) {

							// 点击链接跳转至新闻
							goActivity(NewsWebViewActivity.class,
									pi.get(position).getPicLink());
						}
					});
			picViewPager.addSlider(textSliderView);
		}
		// 设置开始轮播
		picViewPager.start();
		// 设置指示器的显示位置
		picViewPager
				.setIndicatorPosition(InfiniteIndicatorLayout.IndicatorPosition.Center_Bottom);
		// 设置自动开始轮播，默认向右
		picViewPager.startAutoScroll();
		// 设置轮播的时间为5 秒
		picViewPager.setInterval(5 * 1000);
	}

	/**
	 * 点击事件----------------------------------------------
	 */
	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.tv_home_settled:
			enterRecruitment();
			break;
		case R.id.tv_home_supply:
			goActivity(PublishSupplyActivity.class);
			break;
		case R.id.tv_home_purchase:
			goActivity(PublishPurchaseActivity.class);
			break;
		}
	}

	/**
	 * 进入供应商入驻界面
	 */
	private void enterRecruitment() {

		if (!NetUtils.isAlreadyLogin(this)) {

			displayMsgBoxE("提示", "尚未登录,是否进入登录界面 ? ",
					new MsgBoxEOnClickListener() {

						@Override
						public void onClickListener() {
							goActivity(MemberLoginActivity.class);
						}
					});
		} else {

			if (NetUtils.isCompany(this)) {
				displayToast("已经是商家");
			} else {
				goActivity(MerchantEntryActivity.class);
			}

		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		picViewPager.startAutoScroll();
	}

	@Override
	protected void onPause() {
		super.onPause();
		picViewPager.stopAutoScroll();
	}

}
