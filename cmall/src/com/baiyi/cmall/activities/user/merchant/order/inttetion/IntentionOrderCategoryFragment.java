package com.baiyi.cmall.activities.user.merchant.order.inttetion;

import java.util.ArrayList;

import org.json.JSONObject;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.base.TipBaseActivity;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.business.page.BaseViewPagerFragment;
import com.baiyi.cmall.activities.main.home.itemview.DividerItemDecoration;
import com.baiyi.cmall.activities.user.merchant.MyProviderOrderDetailsActivity;
import com.baiyi.cmall.activities.user.merchant.order.adapter.IntentionOrederAdapter;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.LoginInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.request.manager.IntentionManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.slidetab.weight.PagerSlidingTabStrip;
import com.baiyi.cmall.views.LoadingBar;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;
import com.jcodecraeer.xrecyclerview.port.OnRecycleViewItemClickListener;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * 意向订单主界面面
 * 
 * @author sunxy
 */
public class IntentionOrderCategoryFragment extends Fragment {

	private String orderState;
	private String companyId = null;
	private String token = null;
	private int pageIndex = 1;
	private boolean barFlag = true;
	private String userId;
	private Context context = null;
	private ArrayList<GoodsSourceInfo> datas = null;

	public IntentionOrderCategoryFragment(String orderState) {
		this.orderState = orderState;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		initData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.context = getActivity();
		View view = ContextUtil.getLayoutInflater(getActivity()).inflate(R.layout.activity_shop_bus, container, false);
		findRecycleViewViewById(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		this.context = getActivity();
		loadData();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		LoginInfo info = CmallApplication.getUserInfo();
		if (null != info) {
			companyId = info.getCompanyID();
			token = info.getToken();
			userId = info.getUserID();
		}
	}

	private XRecyclerView mRecyclerView;
	private IntentionOrederAdapter adapter;

	/**
	 * 找控件
	 * 
	 * @param view
	 */
	private void findRecycleViewViewById(View view) {
		mRecyclerView = (XRecyclerView) view.findViewById(R.id.recycleview);

		// 设置布局管理器
		LinearLayoutManager layout = new LinearLayoutManager(context);
		mRecyclerView.setLayoutManager(layout);

		// 设置Item增加、移除动画
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		// 添加分割线
		mRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
		// 设置adapter
		adapter = new IntentionOrederAdapter(context);
		mRecyclerView.setAdapter(adapter);
		adapter.setDatas(null);

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

		adapter.setItemClickListener(new OnRecycleViewItemClickListener() {

			@Override
			public <T> void onItemClick(View view, int position, T t) {
				((TipBaseActivity) getActivity()).displayToast("点击了---" + position);
				int state = 0;
				if (Integer.parseInt(orderState) == -1) {
					state = 0;
				} else if (Integer.parseInt(orderState) == 1) {
					state = 1;
				} else if (Integer.parseInt(orderState) == 2) {
					state = 2;
				} else if (Integer.parseInt(orderState) == 3) {
					state = 3;
				}
				// 跳到供应订单详情界面
				((BaseActivity) context).goActivity((GoodsSourceInfo) t, MyProviderOrderDetailsActivity.class, state);
			}
		});
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
				Log.d("TAG", "意向订单----" + result);
				if (barFlag) {
					loadingBar.stop();
				}

				if (info != null) {
					RequestNetResultInfo resultInfo = info.getResultInfo();
					if (1 != resultInfo.getStatus()) {
						((BaseActivity) context).displayToast(resultInfo.getMsg());
						return;
					}
					datas = info.getPurInfos();
				}

				upViewData();
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

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
}
