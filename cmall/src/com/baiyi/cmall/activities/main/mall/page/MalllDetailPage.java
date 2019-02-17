package com.baiyi.cmall.activities.main.mall.page;

import java.util.ArrayList;
import java.util.List;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.business.page.BaseViewPagerFragment;
import com.baiyi.cmall.activities.main.mall.UrlNet;
import com.baiyi.cmall.activities.main.mall.adapter.MallDetailAdapter;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.listitem.PurStandardView;
import com.baiyi.cmall.model.Pdi;
import com.baiyi.cmall.model.Pvm;
import com.baiyi.cmall.model.RequestNetResultInfo;
import com.baiyi.cmall.model.Rpv;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 商城-详情-产品详情
 * 
 * @author tangkun
 * 
 */
public class MalllDetailPage extends BaseViewPagerFragment {

	// 产品描述
	private Pdi pdi = null;
	// 规格参数
	private List<Rpv> rpvs = null;
	// 详情数据
	private ArrayList<Pvm> pvms = null;

	//规格参数
	private LinearLayout mLinParameterlayout;
	private TextView mTxtNoParameterData = null;
	
	private XRecyclerView detailRecyclerView;
	private TextView mTxtNoMallDeatilData = null;

	// 规格参数的适配器
	private MallDetailAdapter detailAdapter = null;

	public static MalllDetailPage newInstance(int index) {
		MalllDetailPage fragment = new MalllDetailPage();
		Bundle args = new Bundle();
		args.putInt(BUNDLE_FRAGMENT_INDEX, index);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = ContextUtil.getLayoutInflater(getActivity()).inflate(
				R.layout.recycleview_list, container, false);

		View headView = ContextUtil.getLayoutInflater(getActivity())//
				.inflate(R.layout.page_mall_detail, container, false);
		initListView(view, headView);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		loadData(id);
	}

	/**
	 * 找控件
	 * 
	 * @param view
	 */
	private void initListView(View view, View headView) {

		mLinParameterlayout = (LinearLayout) headView.findViewById(R.id.mall_deail_parameter_group);
		mTxtNoParameterData = (TextView) headView.findViewById(R.id.no_parameter_data);
		mTxtNoMallDeatilData = (TextView) headView.findViewById(R.id.no_mall_detail_data);
		
		detailRecyclerView = (XRecyclerView) view.findViewById(R.id.rc_list);
		detailRecyclerView.addHeaderView(headView);
		detailRecyclerView.setLayoutManager(new LinearLayoutManager(
				getActivity(), LinearLayoutManager.VERTICAL, false));
		// 设置Item增加、移除动画
		detailRecyclerView.setItemAnimator(new DefaultItemAnimator());

		// 设置adapter
		detailAdapter = new MallDetailAdapter(getActivity());
		detailRecyclerView.setAdapter(detailAdapter);
		detailRecyclerView.setLoadingListener(new LoadingListener() {

			@Override
			public void onRefresh() {
				loadData(id);
			}

			@Override
			public void onLoadMore() {
				detailRecyclerView.loadMoreComplete();
			}
		});
	}

	private void loadData(String pi) {
		loaderProgress();

		JsonLoader loader = new JsonLoader(getContext());
		loader.setUrl(UrlNet.getMallDetail(pi));
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.addRequestHeader("token", CmallApplication.getUserInfo()
				.getToken());
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setPostData("");
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum,
					long totalByteNum) {
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				((BaseActivity) getActivity()).displayToast(errorMessage);
				stopProgress();
				detailRecyclerView.refreshComplete();
			}

			@Override
			public void onCompelete(Object tag, Object result) {
				// 解析数据
				@SuppressWarnings("rawtypes")
				RequestNetResultInfo info = ProductDetailManager
						.parseDetailPageDatas(result);
				stopProgress();
				detailRecyclerView.refreshComplete();
				if (null != info) {
					if (1 != info.getStatus()) {
						((BaseActivity) getActivity()).displayToast(info
								.getMsg());
						return;
					}

					pdi = info.getPdi();
					rpvs = info.getRpvs();
					if (null != pdi) {
						pvms = pdi.getPvm();
					}
					updateView();
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * 更新界面
	 */
	protected void updateView() {
		// 得到分类数据
		List<Rpv> rs = MallDetailUtil.getCategoryRpv(rpvs);
		
		if (Utils.isStringEmpty(rs)) {
			mTxtNoParameterData.setVisibility(View.VISIBLE);
		}else{
			mLinParameterlayout.removeAllViews();
			for (int i = 0; i < rs.size(); i++) {

				PurStandardView argmentView = new PurStandardView(getActivity());
				argmentView.setmTxtTypeName(rs.get(i).getPtname(), i);
				argmentView.setMallDetailListView(rs.get(i).getRpvs());
				mLinParameterlayout.addView(argmentView);
			}
		}
		
		if (Utils.isStringEmpty(pvms)) {
			mTxtNoMallDeatilData.setVisibility(View.VISIBLE);
		}else{
			mTxtNoMallDeatilData.setVisibility(View.GONE);
			// 详情刷新数据
			detailAdapter.setDatas(pvms);
		}

	}

	@Override
	public boolean isViewBeingDragged(MotionEvent event) {
		return false;
	}

}
