/**
 * 
 */
package com.baiyi.cmall.activities.user.merchant.product.pager;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.lightsky.infiniteindicator.InfiniteIndicatorLayout;
import cn.lightsky.infiniteindicator.PageInfo;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView.OnSliderClickListener;
import cn.lightsky.infiniteindicator.slideview.DefaultSliderView;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.business.page.BaseViewPagerFragment;
import com.baiyi.cmall.activities.main.home.itemview.DividerItemDecoration;
import com.baiyi.cmall.activities.main.mall.page.ProductDetailManager;
import com.baiyi.cmall.activities.main.mall.page.ProductDetailNet;
import com.baiyi.cmall.activities.user.merchant.product.ProductNetUrl;
import com.baiyi.cmall.adapter.MyProductADetailInfodapter;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.IndutryArgumentInfo;
import com.baiyi.cmall.model.Bim;
import com.baiyi.cmall.model.Cbi;
import com.baiyi.cmall.model.Pbi;
import com.baiyi.cmall.model.Pdi;
import com.baiyi.cmall.model.RequestNetResultInfo;
import com.baiyi.cmall.model.Rpv;
import com.baiyi.cmall.request.manager.DelegationManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.DataUtils;
import com.baiyi.cmall.utils.IntentClassChangeUtils;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;

/**
 * 商城-详情-产品信息
 * 
 * @author tangkun
 * 
 */
public class MyProductlInfoPage extends BaseViewPagerFragment implements OnClickListener {

	public static MyProductlInfoPage newInstance(int index) {
		MyProductlInfoPage fragment = new MyProductlInfoPage();
		Bundle args = new Bundle();
		args.putInt(BUNDLE_FRAGMENT_INDEX, index);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return initView();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		loadData();
	}

	// 商家基本信息cbi
	private Cbi cbi = null;
	// 产品基本信息pbi
	private Pbi pbi = null;
	// 轮播广告
	private List<Bim> bims = null;

	private void loadData() {
		loaderProgress();

		JsonLoader loader = new JsonLoader(getActivity());
		loader.setUrl(ProductDetailNet.getProductlInfoPageUrl(id));
		loader.setPostData(new JSONObject().toString());
		loader.addRequestHeader("token", CmallApplication.getUserInfo().getToken());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				((BaseActivity) getActivity()).displayToast(arg2);
				stopProgress();
			}

			@SuppressWarnings("rawtypes")
			@Override
			public void onCompelete(Object arg0, Object arg1) {
				// 解析数据
				RequestNetResultInfo info = ProductDetailManager.parseProductInfoPageDatas(arg1);
				stopProgress();

				if (null != info) {

					pbi = info.getPbi();
					cbi = info.getCbi();
					bims = info.getBims();

					if (1 != info.getStatus()) {
						((BaseActivity) getActivity()).displayToast(info.getMsg());
						return;
					}
				}

				updataView();

				// 图片进来后只要成功后在不做刷新
				if (picIsFirstRefresh) {
					// mLinclassifyTitle.setVisibility(View.VISIBLE);
					displayImgs();
					picIsFirstRefresh = false;
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	protected void updataView() {
		List<IndutryArgumentInfo> datas = DataUtils.getPageInfo(pbi);
		adapter.setDatas(datas);
	}

	// 标记图片第一次刷新成功
	private boolean picIsFirstRefresh = true;
	// 图片位置
	private int position;
	private XRecyclerView mRecyclerView;
	private View circleView = null;
	private InfiniteIndicatorLayout circlePic;
	private MyProductADetailInfodapter adapter;

	private View initView() {
		circleView = ContextUtil.getLayoutInflater(getActivity()).inflate(R.layout.activity_circle_product_detail,
				null);
		View view = ContextUtil.getLayoutInflater(getActivity()).inflate(R.layout.activity_shop_bus, null);
		circlePic = (InfiniteIndicatorLayout) circleView.findViewById(R.id.infinite_anim_circle);
		mRecyclerView = (XRecyclerView) view.findViewById(R.id.recycleview);

		// 添加轮播图片
		mRecyclerView.addHeaderView(circleView);

		// 设置布局管理器
		LinearLayoutManager layout = new LinearLayoutManager(getActivity());
		mRecyclerView.setLayoutManager(layout);

		// 设置Item增加、移除动画
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		// 添加分割线
		mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
		// 设置adapter
		adapter = new MyProductADetailInfodapter(getActivity());
		mRecyclerView.setAdapter(adapter);
		adapter.setDatas(null);

		mRecyclerView.setPullRefreshEnabled(false);
		mRecyclerView.setLoadingMoreEnabled(false);
		mRecyclerView.setLoadingListener(new LoadingListener() {

			@Override
			public void onRefresh() {
				mRecyclerView.refreshComplete();
				Log.d("TAG", "------------onRefresh----------");
			}

			@Override
			public void onLoadMore() {

				mRecyclerView.loadMoreComplete();
				Log.d("TAG", "----------onLoadMore------------");
			}
		});

		initButton();

		return view;
	}

	/**
	 * 显示首页轮播网页图片
	 */
	private void displayImgs() {

		for (int i = 0; i < bims.size(); i++) {
			position = i;
			DefaultSliderView textSliderView = new DefaultSliderView(getActivity());
			 textSliderView.image(bims.get(i).getFp());
			textSliderView/*.image(R.drawable.img_test_mall_detail)*/.setScaleType(BaseSliderView.ScaleType.Fit)
					.setOnSliderClickListener(new OnSliderClickListener() {

						@Override
						public void onSliderClick(BaseSliderView slider) {

							// 点击链接跳转至新闻
							// goActivity(NewsWebViewActivity.class,
							// pi.get(position).getPicLink());
						}
					});
			circlePic.addSlider(textSliderView);
		}

		// 设置开始轮播
		circlePic.start();
		// 设置指示器的显示位置
		circlePic.setIndicatorPosition(InfiniteIndicatorLayout.IndicatorPosition.Center_Bottom);
		// 设置自动开始轮播，默认向右
		circlePic.startAutoScroll();
		// 设置轮播的时间为5 秒
		circlePic.setInterval(5 * 1000);
	}

	// 撤销发布
	private TextView mBtnUnDo;
	// 评价维护
	private TextView mBtnMaintain;

	/**
	 * 初始化按键
	 */
	private void initButton() {
		View view = ContextUtil.getLayoutInflater(getActivity()).inflate(R.layout.activity_button, null);
		// addView(view);
		mRecyclerView.addFootView(view);
		mBtnUnDo = (TextView) view.findViewById(R.id.btn_commit_modify);
		if (1 == state) {
			// 需要撤销发布 -1
			mBtnUnDo.setText("撤销发布");
		} else {
			// 需要发布状态 1
			mBtnUnDo.setText("发布产品");
		}

		mBtnMaintain = (TextView) view.findViewById(R.id.btn_cancel_modify);
		mBtnMaintain.setText("评价维护");

		mBtnUnDo.setOnClickListener(this);
		mBtnMaintain.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_commit_modify:// 修改产品状态
			if (1 == state) {
				// 需要撤销发布 -1
				opOrderState(ProductNetUrl.getUndoPublishUrl(id, -1));
			} else {
				// 需要发布状态 1
				opOrderState(ProductNetUrl.getPublishUrl(id, 1));
			}
			break;
		case R.id.btn_cancel_modify:// 评价维护
			// ((BaseActivity) getActivity()).displayToast("评价维护");
			IntentClassChangeUtils.startEvaluateMaintain(getActivity(), id);
			break;
		}
	}

	private void opOrderState(String url) {

		((BaseActivity) getActivity()).startLoading();

		JsonLoader loader = new JsonLoader(getActivity());
		loader.addRequestHeader("token", CmallApplication.getUserInfo().getToken());
		loader.setUrl(url);
		loader.setPostData(new JSONObject().toString());
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				((BaseActivity) getActivity()).displayToast(arg2);
				((BaseActivity) getActivity()).stopLoading();
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				com.baiyi.cmall.entity.RequestNetResultInfo info = DelegationManager.getCancelAttentionResultInfo(arg1,
						0);
				// displayToast(arg1.toString());
				((BaseActivity) getActivity()).stopLoading();
				// 更新界面
				if (1 == info.getStatus()) {
					Intent intent = new Intent();
					((BaseActivity) getActivity()).displayToast("操作成功");
					((BaseActivity) getActivity()).setResult(1, intent);
					((BaseActivity) getActivity()).finish();
				} else {
					((BaseActivity) getActivity()).displayToast("操作失败");
					return;
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	@Override
	public boolean isViewBeingDragged(MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

}
