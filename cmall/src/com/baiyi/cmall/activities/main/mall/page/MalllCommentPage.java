/**
 * 
 */
package com.baiyi.cmall.activities.main.mall.page;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.business.page.BaseViewPagerFragment;
import com.baiyi.cmall.activities.main.mall.adapter.MallDetailCommentAdapter;
import com.baiyi.cmall.activities.main.mall.entity.CtmlEntity;
import com.baiyi.cmall.activities.main.mall.entity.MallDetailCommentEntity;
import com.baiyi.cmall.activities.main.mall.entity.MallDetailCommentTipEntity;
import com.baiyi.cmall.activities.main.total.TotalUtils;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;

/**
 * 商城-详情-评论列表
 * 
 * @author tangkun
 * 
 */
public class MalllCommentPage extends BaseViewPagerFragment implements OnClickListener {

	private XRecyclerView mListView = null;
	private TextView mTxtNoCommentData = null;
	private MallDetailCommentAdapter adapter = null;
	private int commentType = 0;
	private int pegeIndex = 1;
	ArrayList<CtmlEntity> data = new ArrayList<CtmlEntity>();

	// 所有评价
	TextView commemtAll = null;
	// 好评
	TextView commentGood = null;
	// 差评
	TextView commemtBad = null;
	// 晒图
	TextView commentPicture = null;

	// 所有评价布局
	LinearLayout commemtAllLinear = null;
	// 好评
	LinearLayout commentGoodLinear = null;
	// 差评
	LinearLayout commemtBadLinear = null;
	// 晒图
	LinearLayout commentPictureLinear = null;

	public static MalllCommentPage newInstance(int index) {
		MalllCommentPage fragment = new MalllCommentPage();
		Bundle args = new Bundle();
		args.putInt(BUNDLE_FRAGMENT_INDEX, index);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		adapter = new MallDetailCommentAdapter(getActivity());
		mListView.setAdapter(adapter);
		loadData();
		mListView.setLoadingListener(new LoadingListener() {

			@Override
			public void onRefresh() {
				pegeIndex = 1;
				loadData();
			}

			@Override
			public void onLoadMore() {
				pegeIndex++;
				loadData();
			}
		});
	}

	/**
	 * 评价类型
	 * 0 全部评论 		1 好评	2 中评	3 差评	 晒图		5 回复
	 */
	private void loadData() {
		loaderProgress();

		JsonLoader loader = new JsonLoader(getActivity());
		// 意向订单详情:AppNetUrl.getProviderOrderDetailUrl(orderId)
		loader.setUrl(ProductDetailNet.getCommentPageUrl(id, commentType, pegeIndex, Config.LIST_ITEM_COUNT));
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
				mListView.loadMoreComplete();
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {

				stopProgress();
				// 解析数据
				MallDetailCommentEntity mallDetailCommentEntity = ProductDetailManager.parseCommentPageDatas(arg1);

				if (pegeIndex == 1) {
					if (adapter != null) {
						adapter.setDatas(
								mallDetailCommentEntity == null ? null : mallDetailCommentEntity.getCtmlList());
					}

					if (mallDetailCommentEntity != null) {
						mListView.refreshComplete();
						// 刷新头部的数据
						commemtAll.setText(mallDetailCommentEntity.getAc() + "");
						commentGood.setText(mallDetailCommentEntity.getGc() + "");
						commemtBad.setText(mallDetailCommentEntity.getBc() + "");
						commentPicture.setText(mallDetailCommentEntity.getRep() + "");
						if (Utils.isStringEmpty(mallDetailCommentEntity.getCtmlList())) {
							mTxtNoCommentData.setVisibility(View.VISIBLE);
						} else {
							mTxtNoCommentData.setVisibility(View.GONE);
						}
					}

				} else { 
					if (mallDetailCommentEntity != null) {
						adapter.addDatas(mallDetailCommentEntity.getCtmlList());
						mListView.loadMoreComplete();
					}
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = ContextUtil.getLayoutInflater(getActivity()).inflate(R.layout.page_mall_detail_comment, container,
				false);
		mListView = (XRecyclerView) view.findViewById(R.id.commnet_listview);
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mListView.setLayoutManager(layoutManager);

		mTxtNoCommentData = (TextView) view.findViewById(R.id.no_comment_data);
		initHead();
		return view;
	}

	private void initHead() {
		View view = ContextUtil.getLayoutInflater(getActivity()).inflate(R.layout.head_mall_detail_comment, null);
		commemtAll = (TextView) view.findViewById(R.id.comment_all);
		commentGood = (TextView) view.findViewById(R.id.comment_good);
		commemtBad = (TextView) view.findViewById(R.id.comment_bad);
		commentPicture = (TextView) view.findViewById(R.id.comment_picture);

		commemtAllLinear = (LinearLayout) view.findViewById(R.id.comment_all_linear);
		commentGoodLinear = (LinearLayout) view.findViewById(R.id.comment_good_linear);
		commemtBadLinear = (LinearLayout) view.findViewById(R.id.comment_bad_linear);
		commentPictureLinear = (LinearLayout) view.findViewById(R.id.comment_picture_linear);
		setTextColors();
		commemtAllLinear.setOnClickListener(this);
		commentGoodLinear.setOnClickListener(this);
		commemtBadLinear.setOnClickListener(this);
		commentPictureLinear.setOnClickListener(this);

		mListView.addHeaderView(view);
	}

	@Override
	public boolean isViewBeingDragged(MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 1 全部评论 2 好评 3 中评 4 差评 5 晒图
	 * 
	 * 
	 */
	@Override
	public void onClick(View arg0) {

		pegeIndex = 1;

		switch (arg0.getId()) {
		case R.id.comment_all_linear:
			commentType = 0;
			setTextColors();
			loadData();
			break;

		case R.id.comment_good_linear:
			commentType = 1;
			setTextColors();
			loadData();
			break;

		case R.id.comment_bad_linear:
			commentType = 3;
			setTextColors();
			loadData();
			break;
		case R.id.comment_picture_linear:
			commentType = 4;
			setTextColors();
			loadData();
			break;
		}

	}

	private void setTextColors() {

		switch (commentType) {
		case 0:
			commemtAllLinear.setActivated(true);
			commentGoodLinear.setActivated(false);
			commemtBadLinear.setActivated(false);
			commentPictureLinear.setActivated(false);
			break;
		case 1:
			commemtAllLinear.setActivated(false);
			commentGoodLinear.setActivated(true);
			commemtBadLinear.setActivated(false);
			commentPictureLinear.setActivated(false);
			break;
		case 3:
			commemtAllLinear.setActivated(false);
			commentGoodLinear.setActivated(false);
			commemtBadLinear.setActivated(true);
			commentPictureLinear.setActivated(false);
			break;
		case 4:
			commemtAllLinear.setActivated(false);
			commentGoodLinear.setActivated(false);
			commemtBadLinear.setActivated(false);
			commentPictureLinear.setActivated(true);
			break;
		}
	}

}
