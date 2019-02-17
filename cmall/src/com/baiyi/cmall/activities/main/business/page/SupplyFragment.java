package com.baiyi.cmall.activities.main.business.page;

import java.util.ArrayList;

import org.json.JSONArray;

import me.relex.seamlessviewpagerheader.delegate.AbsListViewDelegate;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.business.BusinessUrlNet;
import com.baiyi.cmall.activities.main.business.JsonParseBusiness;
import com.baiyi.cmall.activities.main.business.adapter.ShopSupplyAdapter;
import com.baiyi.cmall.activities.main.mall.MallDefine;
import com.baiyi.cmall.activities.main.mall.adapter.BaseRecyclerAdapter.OnItemClickListener;
import com.baiyi.cmall.activities.main.total.TotalUtils;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.model.Blm;
import com.baiyi.cmall.utils.IntentClassChangeUtils;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.activities.main.provider.GoodSMainProviderDetailsActivity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;

/**
 * 供应 ----------------------延期处理，等待新的接口ing。。。
 * 
 * @author tangkun
 * 
 */
public class SupplyFragment extends BaseViewPagerFragment implements
		AbsListView.OnItemClickListener {

	private XRecyclerView mListView;
	private ShopSupplyAdapter adapter;
	private AbsListViewDelegate mAbsListViewDelegate = new AbsListViewDelegate();
	private TextView mTxtNoData = null;
	private int pageIndex = 1;
	private int pageSize = Config.LIST_ITEM_COUNT;
	// 商家ID
	private int ci;
	private ArrayList<Blm> blms;

	public static SupplyFragment newInstance(int index, int ci) {
		SupplyFragment fragment = new SupplyFragment();
		Bundle args = new Bundle();
		args.putInt(BUNDLE_FRAGMENT_INDEX, index);
		args.putInt(MallDefine.CI, ci);
		fragment.setArguments(args);
		return fragment;
	}

	public SupplyFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		switch (mFragmentIndex) {
		case 1:
			break;
		case 2:
			break;
		default:
			break;
		}
		ci = getArguments().getInt(MallDefine.CI);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_shop_bus, container,
				false);
		initView(view);
		loadDetail();
		return view;
	}

	private void initView(View view) {
		mListView = (XRecyclerView) view.findViewById(R.id.recycleview);
		mTxtNoData = (TextView) view.findViewById(R.id.no_data);
		initHeadView();

		LinearLayoutManager layoutManager = new LinearLayoutManager(
				getActivity(), LinearLayoutManager.VERTICAL, false);
		mListView.setLayoutManager(layoutManager);
		adapter = new ShopSupplyAdapter(getActivity());
		mListView.setAdapter(adapter);
		mListView.setLoadingListener(new LoadingListener() {

			@Override
			public void onRefresh() {
				pageIndex = 1;
				loadDetail();
			}

			@Override
			public void onLoadMore() {
				pageIndex++;
				loadDetail();
			}
		});
		adapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(int position, View itemView) {
				// TODO Auto-generated method stub
				((BaseActivity)getActivity()).goActivity(adapter
						.getDataList().get(position).getId(), GoodSMainProviderDetailsActivity.class);
			}
		});
	}

	/**
	 * 添加头部控件
	 */
	private void initHeadView() {
		View view = ContextUtil.getLayoutInflater(getActivity()).inflate(
				R.layout.view_line_15, null);
		mListView.addHeaderView(view);
	}

	/**
	 * 加载供应数据
	 * 
	 * @param ci
	 */
	private void loadDetail() {
		JsonLoader loader = new JsonLoader(getContext());
		loader.setUrl(BusinessUrlNet.getShopSupplyList(ci, pageIndex, pageSize));
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType("application/json");
		loader.setPostData("");
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum,
					long totalByteNum) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				// TODO Auto-generated method stub
				mListView.refreshComplete();
			}

			@Override
			public void onCompelete(Object tag, Object result) {
				// TODO Auto-generated method stub
				mListView.refreshComplete();
				blms = JsonParseBusiness.getAllProduct((JSONArray) result);
				if (pageIndex == 1) {
					adapter.setDatas(blms);
					mListView.refreshComplete();
				} else {
					adapter.addDatas(blms);
					mListView.loadMoreComplete();
				}
				// 显示暂无数据
				if (TotalUtils.getIntence().getTotal() <= 0 && pageIndex == 1) {
					mTxtNoData.setVisibility(View.VISIBLE);
				} else {
					mTxtNoData.setVisibility(View.GONE);
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
	}

	@Override
	public boolean isViewBeingDragged(MotionEvent event) {
		return mAbsListViewDelegate.isViewBeingDragged(event, mListView);
	}
}
