package com.baiyi.cmall.activities.user.merchant.order.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import org.json.JSONObject;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.home.itemview.DividerItemDecoration;
import com.baiyi.cmall.activities.main.total.TotalUtils;
import com.baiyi.cmall.activities.user.merchant.order.adapter.MerchantProductOrderAdapter;
import com.baiyi.cmall.activities.user.merchant.order.product.detail.ProductOrderDetailActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.LoginInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.pageviews.BasePageView;
import com.baiyi.cmall.request.manager.IntentionManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.LoadingBar;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;
import com.jcodecraeer.xrecyclerview.port.OnRecycleViewItemClickListener;

/**
 * 产品订单的子分类
 * 
 * @author sunxy
 */
public class ProductOrderCategoryViewPager extends BasePageView implements OnRecycleViewItemClickListener {

	private Context context = null;
	private String orderState;
	private int pageIndex = 1;
	private boolean barFlag = true;
	private String userId;
	private ArrayList<GoodsSourceInfo> datas = null;

	public ProductOrderCategoryViewPager(Context context, String orderState) {
		super(context);
		this.context = context;
		this.orderState = orderState;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initData();
		initContent();
		pageIndex = 1;
		loadData();
	}

	private void initData() {
		LoginInfo loginInfo = CmallApplication.getUserInfo();
		if (null != loginInfo) {
			userId = loginInfo.getUserID();
		}
	}

	private LoadingBar loadingBar = null;

	/**
	 * 加载数据
	 */
	private void loadData() {

		if (barFlag) {
			loadingBar = new LoadingBar(getContext());
			loadingBar.start();
		}

		JsonLoader loader = new JsonLoader(getContext());
		loader.setUrl(AppNetUrl.getIntentionOrderCategoryUrl(userId, orderState, pageIndex, Config.LIST_ITEM_COUNT));
		loader.setPostData(new JSONObject().toString());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.addRequestHeader("token", token);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long curByteNum, long totalByteNum) {
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				((BaseActivity) context).displayToast(arg2);
				if (barFlag) {
					loadingBar.stop();
				}
			}

			@Override
			public void onCompelete(Object arg0, Object result) {
				GoodsSourceInfo info = IntentionManager.getProductInfo(result);
				Log.d("TAG", "产品订单----" + result);
				if (barFlag) {
					loadingBar.stop();
				}

				// 显示暂无数据
				if (/* TotalUtils.getIntence().getTotal() <= 0 */Utils.isStringEmpty(datas) && pageIndex == 1) {
					mTxtNoData.setVisibility(View.VISIBLE);
				} else {
					mTxtNoData.setVisibility(View.GONE);
				}

				if (info != null) {
					RequestNetResultInfo resultInfo = info.getResultInfo();
					if (1 != resultInfo.getStatus()) {
//						((BaseActivity) context).displayToast(resultInfo.getMsg());
						return;
					}
					datas = info.getPurInfos();
				}

				upViewData();
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);

	}

	@SuppressWarnings("unchecked")
	protected void upViewData() {
		if (mRecyclerView != null) {
			if (1 == pageIndex) {
				mRecyclerView.refreshComplete();
			} else {
				mRecyclerView.loadMoreComplete();
			}
		}
		if (1 == pageIndex) {
			adapter.setDatas(datas);
		} else {
			adapter.addDatas(datas);
		}
	}

	private XRecyclerView mRecyclerView;
	private MerchantProductOrderAdapter adapter;

	/**
	 * 初始化内容
	 */
	private void initContent() {

		View view = ContextUtil.getLayoutInflater(context)
				//
				.inflate(R.layout.activity_shop_bus, null);
		addView(view);

		findRecycleViewViewById(view);
	}

	private TextView mTxtNoData = null;

	/**
	 * 找控件
	 * 
	 * @param view
	 */
	private void findRecycleViewViewById(View view) {
		mRecyclerView = (XRecyclerView) view.findViewById(R.id.recycleview);
		mTxtNoData = (TextView) view.findViewById(R.id.no_data);

		// 设置布局管理器
		LinearLayoutManager layout = new LinearLayoutManager(context);
		mRecyclerView.setLayoutManager(layout);

		// 设置Item增加、移除动画
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		// 添加分割线
		mRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
		// 设置adapter
		adapter = new MerchantProductOrderAdapter(context);
		mRecyclerView.setAdapter(adapter);

		adapter.setItemClickListener(this);

		mRecyclerView.setLoadingMoreEnabled(true);
		mRecyclerView.setLoadingListener(new LoadingListener() {

			@Override
			public void onRefresh() {
				barFlag = false;
				pageIndex = 1;
				loadData();
				Log.d("TAG", "------------onRefresh----------");
			}

			@Override
			public void onLoadMore() {
				barFlag = false;
				pageIndex++;
				loadData();
				Log.d("TAG", "----------onLoadMore------------");
			}
		});

	}

	/**
	 * 条目的点击事件
	 * 
	 * @param view
	 * @param position
	 * @param t
	 */
	@Override
	public <T> void onItemClick(View view, int position, T t) {
		GoodsSourceInfo info = (GoodsSourceInfo) t;
		String id = info.getId();
		((BaseActivity) context).goActivity(id, ProductOrderDetailActivity.class, Integer.parseInt(orderState));
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

	}

}
