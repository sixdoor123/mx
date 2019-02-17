package com.baiyi.cmall.activities.user.attention.buyer.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.business.page.BaseViewPagerFragment;
import com.baiyi.cmall.activities.main.home.itemview.DividerItemDecoration;
import com.baiyi.cmall.activities.main.total.TemporarilyNoData;
import com.baiyi.cmall.activities.main.total.TotalUtils;
import com.baiyi.cmall.activities.user.attention.AttentionDetailsActivity;
import com.baiyi.cmall.activities.user.attention.buyer.adapter.AttentionProAdapter;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.request.manager.AttentionManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.cmall.views.LoadingBar;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;
import com.jcodecraeer.xrecyclerview.port.OnRecycleViewItemClickListener;

/**
 * 关注的商家
 * 
 * @author sunxy
 */
public class MerAttentionFragment extends BaseViewPagerFragment implements OnRecycleViewItemClickListener {

	private Context context = null;

	public static MerAttentionFragment newInsetence(int index) {
		MerAttentionFragment fragment = new MerAttentionFragment();
		Bundle args = new Bundle();
		args.putInt(BUNDLE_FRAGMENT_INDEX, index);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		context = getActivity();
		View view = ContextUtil.getLayoutInflater(context).inflate(R.layout.activity_shop_bus, container, false);
		findViewById(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		context = getActivity();
		initData();
		loadData();
	}

	private int pageIndex = 1;
	private XRecyclerView mRecyclerView = null;
	private boolean barFlag = true;
	private ArrayList<GoodsSourceInfo> datas = null;
	private AttentionProAdapter adapter = null;
	
	private TextView mTxtNoData = null;

	private void findViewById(View view) {
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
		adapter = new AttentionProAdapter(context);
		mRecyclerView.setAdapter(adapter);

		// 条目点击事件
		adapter.setItemClickListener(this);

		mRecyclerView.setLoadingListener(new LoadingListener() {

			@Override
			public void onRefresh() {
				barFlag = false;
				pageIndex = 1;
				loadData();
			}

			@Override
			public void onLoadMore() {
				barFlag = false;
				pageIndex += 1;
				loadData();
			}
		});
	}

	private String token = "";
	private String userId = "";
	

	private void initData() {
		userId = CmallApplication.getUserInfo().getUserID();
	}

	private LoadingBar loadingBar = null;

	/**
	 * 加载数据
	 */
	private void loadData() {
		token = XmlUtils.getInstence(context).getXmlStringValue("token");
		if (TextUtils.isEmpty(token)) {
			((BaseActivity) context).displayToast("用户未登录,请登录后重试");
			return;
		}

		if (barFlag) {
			loadingBar = new LoadingBar(context);
			loadingBar.start();
		}

		JsonLoader loader = new JsonLoader(context);
		loader.setUrl(AppNetUrl.getAttentionMerUrl(userId, pageIndex, Config.LIST_ITEM_COUNT));
		loader.addRequestHeader("token", CmallApplication.getUserInfo().getToken());
		loader.setPostData(AttentionManager.getProviderPostData());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				if (barFlag) {
					loadingBar.stop();
				}
				((BaseActivity) context).displayToast(arg2.toString());
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				GoodsSourceInfo info = AttentionManager.getAttentionMerchantListInfo(arg1);
				if (barFlag) {
					loadingBar.stop();
				}
				if (null != mRecyclerView) {
					if (1 == pageIndex) {
						mRecyclerView.refreshComplete();
					} else {
						mRecyclerView.loadMoreComplete();
					}
				}

				if (null != info) {
					datas = info.getPurInfos();
					

					// 显示暂无数据
					if (TotalUtils.getIntence().getTotal() <= 0 && pageIndex == 1) {
						mTxtNoData.setVisibility(View.VISIBLE);
					}else {
						mTxtNoData.setVisibility(View.GONE);
					}
					
					RequestNetResultInfo resultInfo = info.getResultInfo();
					if (1 == resultInfo.getStatus()) {
						updataView();
					} else {
//						((BaseActivity) context).displayToast(resultInfo.getMsg());
						return;
					}
				} else {
					((BaseActivity) context).displayToast("查询失败 ！");
					return;
				}

			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * 更新数据
	 */
	protected void updataView() {

		if (1 == pageIndex) {
			adapter.setDatas(datas);
		} else {
			adapter.addDatas(datas);
		}
	}

	/**
	 * 条目点击事件
	 */
	@Override
	public <T> void onItemClick(View view, int position, T t) {
		GoodsSourceInfo info = (GoodsSourceInfo) t;
		// AttentionDetailsActivity.class
		((BaseActivity) context).goActivity(info, AttentionDetailsActivity.class, 2);
	}

	@Override
	public boolean isViewBeingDragged(MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
}
