package com.baiyi.cmall.activities.main.business.page;

import org.json.JSONArray;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.business.BusinessUrlNet;
import com.baiyi.cmall.activities.main.business.JsonParseBusiness;
import com.baiyi.cmall.activities.main.business.adapter.ShopProAdapter;
import com.baiyi.cmall.activities.main.business.entity.CbmEntity;
import com.baiyi.cmall.activities.main.business.entity.CpimEntity;
import com.baiyi.cmall.activities.main.mall.MallDefine;
import com.baiyi.cmall.activities.main.mall.adapter.BaseRecyclerAdapter.OnItemClickListener;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.request.manager.MallManager;
import com.baiyi.cmall.utils.IntentClassChangeUtils;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AbsListView;
import android.widget.TextView;
import me.relex.seamlessviewpagerheader.delegate.AbsListViewDelegate;

/**
 * 全部商品
 * 
 * @author tangkun
 * 
 */
public class ProFragment extends BaseViewPagerFragment {

	private TextView mTvEmpty;
	private XRecyclerView mListView;
	private ShopProAdapter adapter;
	private AbsListViewDelegate mAbsListViewDelegate = new AbsListViewDelegate();
	private int ci;

	public static ProFragment newInstance(int index, int ci) {
		ProFragment fragment = new ProFragment();
		Bundle args = new Bundle();
		args.putInt(BUNDLE_FRAGMENT_INDEX, index);
		args.putInt(MallDefine.CI, ci);
		fragment.setArguments(args);
		return fragment;
	}

	public ProFragment() {
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
		View view = inflater.inflate(R.layout.fragment_list_view, container,
				false);

		initList(view);
		initHeadView();
		loadDetail(ci);
		return view;
	}

	private void initList(View view) {

		mTvEmpty = (TextView) view.findViewById(R.id.empty);

		mListView = (XRecyclerView) view.findViewById(R.id.pro_listview);
		GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),
				2);
		mListView.setLayoutManager(layoutManager);
		adapter = new ShopProAdapter(getActivity());
		mListView.setAdapter(adapter);

		// 条目点击
		adapter.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(int position, View itemView) {
				IntentClassChangeUtils.startMallDetail(getActivity(), adapter
						.getDataList().get(position).getId()
						+ "");
			}
		});

		// 刷新/加载数据
		mListView.setLoadingListener(new LoadingListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				pi = 1;
				loadDetail(ci);
			}

			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				pi++;
				loadDetail(ci);
			}
		});

	}

	private void initHeadView() {
		View view = ContextUtil.getLayoutInflater(getActivity()).inflate(
				R.layout.head_business_filter, null);
		mListView.addHeaderView(view);
	}

	private int pi = 1;// 页码
	private int ps = 10;// 每页加载数量

	private void loadDetail(int ci) {
		JsonLoader loader = new JsonLoader(getContext());
		loader.setUrl(BusinessUrlNet.getShopProductList(ci));
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setPostData(MallManager.getListData( pi, ps));
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
				CpimEntity entity = JsonParseBusiness
						.getCpimEntity((JSONArray) result);

				if (null == entity) {
					return;
				}
				// 给acticity回传数据
				if (null != businessDataListener) {
					if (null != entity) {
						businessDataListener.getBusinessData(entity.getCbmEntity());
					}
				}
				displayList(entity);
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	private void displayList(final CpimEntity entity) {

		if (pi == 1) {
			adapter.setDatas(entity.getPbiList());
			mListView.refreshComplete();
		} else {
			adapter.addDatas(entity.getPbiList());
			mListView.loadMoreComplete();
		}

		if (pi == 1 && Utils.isStringEmpty(entity.getPbiList())) {
			mTvEmpty.setVisibility(View.VISIBLE);
			return;
		} else {
			mTvEmpty.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean isViewBeingDragged(MotionEvent event) {
		return mAbsListViewDelegate.isViewBeingDragged(event, mListView);
	}

	private OnGetBusinessDataListener businessDataListener;

	public ProFragment setOnGetBusinessData(
			OnGetBusinessDataListener businessDataListener) {
		this.businessDataListener = businessDataListener;
		return this;
	}

	public interface OnGetBusinessDataListener {
		void getBusinessData(CbmEntity cbmEntity);
	}
}
