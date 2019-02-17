package com.baiyi.cmall.activities.main.home_pager;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.home_pager.adapter.ZiXunAdapter;
import com.baiyi.cmall.activities.main.home_pager.entity.ZiXunEntity;
import com.baiyi.cmall.activities.user.buyer.adapter.BaseRCAdapter.OnItemClickListeners;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.request.manager.NullJsonData;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.IndustryTrendsUtil;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;

/**
 * 首页
 * 
 * @author lizl
 * 
 */
public class ZiXunActivity extends BaseActivity {

	private EventTopTitleView topTitleView;
	// 资讯列表RC
	private XRecyclerView mRcZiXun;
	private ZiXunAdapter ziXunAdapter;
	private ArrayList<ZiXunEntity> ziXunEntitys;

	@Override
	protected void initWin(boolean hasScrollView) {

		super.initWin(false);
		initTitle();
		initData();
		// initNetData();
		initView();

	}

	/**
	 * 初始化标题
	 */
	private void initTitle() {

		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("找化塑●快讯");
		win_title.addView(topTitleView);
	}

	@SuppressWarnings("unchecked")
	private void initData() {
		ziXunEntitys = (ArrayList<ZiXunEntity>) getIntent().getSerializableExtra("data");
	}

	/**
	 * 添加主界面内容
	 */
	private void initView() {

		ContextUtil.getLayoutInflater(this).inflate(R.layout.recycleview_list,
				win_content);

		mRcZiXun = (XRecyclerView) findViewById(R.id.rc_list);
		mRcZiXun.setLayoutManager(new LinearLayoutManager(this,
				LinearLayoutManager.VERTICAL, false));
		ziXunAdapter = new ZiXunAdapter(this);
		mRcZiXun.setAdapter(ziXunAdapter);
		mRcZiXun.setLoadingListener(new LoadingListener() {

			@Override
			public void onRefresh() {
				mRcZiXun.refreshComplete();
			}

			@Override
			public void onLoadMore() {
				mRcZiXun.loadMoreComplete();
			}
		});

		ziXunAdapter.setOnItemClickListener(new OnItemClickListeners() {
			@Override
			public void onItemClick(int position, View itemView) {

				ZiXunEntity entity = ziXunAdapter.getDataList().get(position);

				Intent intent = new Intent(ZiXunActivity.this,
						ZiXunDetailActivity.class);
				intent.putExtra("intent", entity);
				startActivity(intent);
			}
		});

		// TODO 加载完数据后刷新数据
		ziXunAdapter.setDatas(ziXunEntitys);
	}

	/**
	 * 加载基本的JSON数据
	 */
	private void initNetData() {

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getZiXunUrl());
		loader.setPostData(NullJsonData.getPostData());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.addRequestHeader("token", token);
		loader.addRequestHeader("iscompany", iscompany + "");
		loader.setLoaderListener(new LoaderListener() {

			public void onProgress(Object tag, long curByteNum,
					long totalByteNum) {
			}

			public void onError(Object tag, int responseCode,
					String errorMessage) {
				displayToast(errorMessage);
			}

			public void onCompelete(Object tag, Object result) {
				JSONArray array = (JSONArray) result;
				IndustryTrendsUtil.getTrendsInfo(array);
			}

		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

}
