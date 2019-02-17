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
 * ��ҳ
 * 
 * @author lizl
 * 
 */
public class MainActivity extends BaseActivity implements OnClickListener {

	// ��ҳͼƬ��ҳ����
	private InfiniteIndicatorLayout picViewPager = null;
	// ListViewͷ����ʾ�����ݿؼ�
	private View headerView;
	// ͼƬλ��
	private int position;
	// ���ͼƬ��һ��ˢ�³ɹ�
	private boolean picIsFirstRefresh = true;

	// �����פ
	private TextView mTvHomeSettled;
	// ������Ӧ
	private TextView mTvHomeSupply;
	// ί�вɹ�
	private TextView mTvHomePurchase;
	// ��Ѷ---------------------------
	private PublicNoticeView noticeView;

	// ��ʱ����
	private XRecyclerView mRcFlashSale;
	private FlashCaleAdapter flashCaleAdapter;
	// �Ƽ��̼�
	private InScrollGridView mGdvHotBusiness;
	private HotBusinessAdapter businessAdapter;

	// ��������Դ
	private ArrayList<MainEntity> newsList = null;
	// ��Ѷ����Դ
	private ArrayList<ZiXunEntity> zixunS = null;
	// ��ʱ��������Դ
	private ArrayList<GoodsSourceInfo> flashCaleList = null;
	// �Ƽ��̼�����Դ
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
	 * ��ʼ������
	 */
	private void initTitle() {

		CommonSearchView searchView = new CommonSearchView(this);
		win_title.addView(searchView);
		searchView.showAppView();

	}

	/**
	 * �������������
	 */
	private void initView() {

		ContextUtil.getLayoutInflater(this).inflate(R.layout.recycleview_list,
				win_content);

		/*
		 * ��Ҫ��ӵ�ͷ��VIEW��ʼ��
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
	 * ��ʱ������ʼ��
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
	 * ���ų�ʼ��-------------------------------------------
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
	 * ���� ��/��=320/800 ������ʾ���ŵĳߴ�
	 */
	private void setViewPagerHeight() {

		int height = Math.round(getScreenWidth() * (320f / 800f));

		LayoutParams linearParams = new LayoutParams(getScreenWidth(), height);

		picViewPager.setLayoutParams(linearParams);

	}

	/**
	 * �����פ/������Ӧ/ί�вɹ� ��ʼ��----------------------------
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
	 * ��Ѷ��ʼ��----------------------------------------------
	 */
	private void initZiXunView() {

		noticeView = (PublicNoticeView) headerView.findViewById(R.id.pnv_zixun);
	}

	/**
	 * �Ƽ��̼ҳ�ʼ��
	 */
	private void initHotBusinessView() {

		mGdvHotBusiness = (InScrollGridView) headerView
				.findViewById(R.id.gdv_list);
		// GridView�Դ����ѡ�������߿�Ϊ��ɫ���ڴ�ȥ��
		mGdvHotBusiness.setSelector(new ColorDrawable(Color.TRANSPARENT));
		businessAdapter = new HotBusinessAdapter(this);
		mGdvHotBusiness.setAdapter(businessAdapter);
		mGdvHotBusiness.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// TODO �ڴ�����Ŀ�����ת�¼�
				IntentClassChangeUtils.startShopDetailActivity(
						MainActivity.this,
						Utils.isStringEmpty(hotBrandS.get(position).getId()) ? 1
								: Integer.valueOf(hotBrandS.get(position)
										.getId()));
			}
		});
	}

	/**
	 * ���ػ�����JSON����
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
	 * ˢ�½���� ����
	 */
	private void refreshDispaly() {

		/*
		 * ˢ�����ŵ�����
		 */
		if (!Utils.isStringEmpty(newsList)) {

			picViewPager.setVisibility(View.VISIBLE);
			imageNo.setVisibility(View.GONE);

			// ͼƬ������ֻҪ�ɹ����ڲ���ˢ��
			if (picIsFirstRefresh) {
				displayImgs();
				picIsFirstRefresh = false;
			}
		} else {

			picViewPager.setVisibility(View.GONE);
			imageNo.setVisibility(View.VISIBLE);
		}

		/*
		 * ˢ����Ѷ������
		 */
		if (!Utils.isStringEmpty(zixunS)) {

			noticeView.bindNotices(zixunS);
		}

		/*
		 * ˢ���Ƽ��̼�����
		 */
		if (!Utils.isStringEmpty(hotBrandS)) {

			businessAdapter.setData(hotBrandS);
		}

		/*
		 * ˢ����ʱ�����б�
		 */
		if (!Utils.isStringEmpty(flashCaleList)) {

			flashCaleAdapter.setDatas(flashCaleList);
		}
	}

	/**
	 * ��ʾ��ҳ�ֲ���ҳͼƬ--------------------------------------------------------
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

							// ���������ת������
							goActivity(NewsWebViewActivity.class,
									pi.get(position).getPicLink());
						}
					});
			picViewPager.addSlider(textSliderView);
		}
		// ���ÿ�ʼ�ֲ�
		picViewPager.start();
		// ����ָʾ������ʾλ��
		picViewPager
				.setIndicatorPosition(InfiniteIndicatorLayout.IndicatorPosition.Center_Bottom);
		// �����Զ���ʼ�ֲ���Ĭ������
		picViewPager.startAutoScroll();
		// �����ֲ���ʱ��Ϊ5 ��
		picViewPager.setInterval(5 * 1000);
	}

	/**
	 * ����¼�----------------------------------------------
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
	 * ���빩Ӧ����פ����
	 */
	private void enterRecruitment() {

		if (!NetUtils.isAlreadyLogin(this)) {

			displayMsgBoxE("��ʾ", "��δ��¼,�Ƿ�����¼���� ? ",
					new MsgBoxEOnClickListener() {

						@Override
						public void onClickListener() {
							goActivity(MemberLoginActivity.class);
						}
					});
		} else {

			if (NetUtils.isCompany(this)) {
				displayToast("�Ѿ����̼�");
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
