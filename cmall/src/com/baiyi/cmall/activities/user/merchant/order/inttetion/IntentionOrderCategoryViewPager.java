package com.baiyi.cmall.activities.user.merchant.order.inttetion;

import java.util.ArrayList;
import org.json.JSONObject;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.home.itemview.DividerItemDecoration;
import com.baiyi.cmall.activities.main.total.TotalUtils;
import com.baiyi.cmall.activities.user.merchant.MyProviderOrderDetailsActivity;
import com.baiyi.cmall.activities.user.merchant.order.adapter.IntentionOrederAdapter;
import com.baiyi.cmall.activities.user.merchant.order.inttetion.detail.IntentionOrderDetailActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.LoginInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.pageviews.BasePageView;
import com.baiyi.cmall.request.manager.IntentionManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.views.LoadingBar;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;
import com.jcodecraeer.xrecyclerview.port.OnRecycleViewItemClickListener;

/**
 * ���򶩵����ӷ���
 * 
 * @author sunxy
 */
public class IntentionOrderCategoryViewPager extends BasePageView {

	private Context context = null;
	private String orderState;
	private String companyId = null;
	private String token = null;
	private ArrayList<GoodsSourceInfo> datas = null;

	public IntentionOrderCategoryViewPager(Context context, String orderState) {
		super(context);
		this.context = context;
		this.orderState = orderState;

	}

	private int pageIndex = 1;
	private boolean barFlag = true;
	private String userId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initData();
		initContent();
		loadData();
	}

	/**
	 * ��ʼ������
	 */
	private void initData() {
		LoginInfo info = CmallApplication.getUserInfo();
		if (null != info) {
			companyId = info.getCompanyID();
			token = info.getToken();
			userId = info.getUserID();
		}
	}

	private LoadingBar loadingBar = null;

	/**
	 * ��������
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
				Log.d("TAG", "���򶩵�----" + result);
				if (barFlag) {
					loadingBar.stop();
				}

				// ��ʾ��������
				if (TotalUtils.getIntence().getTotal() <= 0 && pageIndex == 1) {
					mTxtNoData.setVisibility(View.VISIBLE);
				}else {
					mTxtNoData.setVisibility(View.GONE);
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
	private IntentionOrederAdapter adapter;

	/**
	 * ��ʼ������
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(context)//
				.inflate(R.layout.activity_shop_bus, null);
		addView(view);
		findRecycleViewViewById(view);
	}

	private TextView mTxtNoData = null;
	/**
	 * �ҿؼ�
	 * 
	 * @param view
	 */
	private void findRecycleViewViewById(View view) {
		mRecyclerView = (XRecyclerView) view.findViewById(R.id.recycleview);
		mTxtNoData = (TextView) view.findViewById(R.id.no_data);

		// ���ò��ֹ�����
		LinearLayoutManager layout = new LinearLayoutManager(context);
		mRecyclerView.setLayoutManager(layout);

		// ����Item���ӡ��Ƴ�����
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		// ���ӷָ���
		mRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
		// ����adapter
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
				((BaseActivity) context).displayToast("�����---" + position);
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
				// ������Ӧ�����������
				((BaseActivity) context).goActivity((GoodsSourceInfo) t, IntentionOrderDetailActivity.class, state);
			}
		});

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