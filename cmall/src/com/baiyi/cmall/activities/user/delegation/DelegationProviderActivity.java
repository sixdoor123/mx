package com.baiyi.cmall.activities.user.delegation;

import java.util.ArrayList;

import org.json.JSONObject;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.home.itemview.DividerItemDecoration;
import com.baiyi.cmall.activities.main.total.TotalUtils;
import com.baiyi.cmall.activities.user.delegation.adapter.DelegationProAdapter;
import com.baiyi.cmall.activities.user.delegation.adapter.DelegationPurAdapter;
import com.baiyi.cmall.activities.user.login.MemberLoginActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.LoginInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.request.manager.UserLogisticsManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase;
import com.baiyi.cmall.views.pulldownview.PullToRefreshListView;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase.OnLastItemVisibleListener;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase.OnRefreshListener;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;
import com.jcodecraeer.xrecyclerview.port.OnRecycleViewItemClickListener;
import com.baiyi.cmall.R;

/**
 * 
 * 我的委托 - 委托供应
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-23 下午2:17:29
 */
public class DelegationProviderActivity extends BaseActivity implements OnRecycleViewItemClickListener {

	private String token;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(false);
		initData();
		initTitle();
		initContent();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		loadData();
	}

	// 数据源
	private ArrayList<GoodsSourceInfo> datas;
	// 用户ID
	private String userID;

	// 当前显示的页码
	public int pageIndex = 1;
	// 只有在首次采显示进度
	private boolean barFlag = true;

	/**
	 * 数据源
	 */
	private void initData() {
		LoginInfo info = CmallApplication.getUserInfo();
		token = info.getToken();
		userID = info.getUserID();
		if (TextUtils.isEmpty(XmlUtils.getInstence(this).getXmlStringValue("token"))) {
			displayToast("用户未登录,请登录后重试");
			// goActivity(LoginActivity.class);
			return;
		}

	}

	/**
	 * 从网络上请求数据
	 */
	private void loadData() {
		if (barFlag) {
			// loadingBar.setVisibility(View.VISIBLE);
			// loadingBar.setProgressInfo("正在加载中...");
			// loadingBar.start();
			startLoading();
		}

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getLogisticsProviderListUrl(pageIndex, Config.LIST_ITEM_COUNT));
		loader.addRequestHeader("token", token);
		loader.setPostData(new JSONObject().toString());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
				loadingBar.setProgressInfo("正在解析...");
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				if (barFlag) {
					// loadingBar.setVisibility(View.GONE);
					// loadingBar.stop();
					stopLoading();
				}
				displayToast(arg2);
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				GoodsSourceInfo info = UserLogisticsManager.getLogisticsProviderResultInfo(arg1);

				if (barFlag) {
					stopLoading();
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
					if (/*TotalUtils.getIntence().getTotal() <= 0 */Utils.isStringEmpty(datas)&& pageIndex == 1) {
						mTxtNoData.setVisibility(View.VISIBLE);
					}else {
						mTxtNoData.setVisibility(View.GONE);
					}
					
					RequestNetResultInfo resultInfo = info.getResultInfo();
					if (1 == resultInfo.getStatus()) {
						upView();
					} else {
						displayToast(resultInfo.getMsg());
						return;
					}
				} else {
					displayToast("查询失败 ！");
					return;
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * 刷新界面
	 */
	protected void upView() {

		if (1 == pageIndex) {
			adapter.setDatas(datas);
		} else {
			adapter.addDatas(datas);
		}

	}

	// 进度条
	private MyLoadingBar loadingBar;
	// 适配器
	private DelegationProAdapter adapter;
	private View view;

	private XRecyclerView mRecyclerView = null;
	
	private TextView mTxtNoData = null;

	/**
	 * 内容
	 */
	private void initContent() {
		view = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_shop_bus, null);
		win_content.addView(view);

		loadingBar = (MyLoadingBar) view.findViewById(R.id.loading);
		mRecyclerView = (XRecyclerView) view.findViewById(R.id.recycleview);
		
		mTxtNoData = (TextView) view.findViewById(R.id.no_data);
		
		// 设置布局管理器
		LinearLayoutManager layout = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(layout);

		// 设置Item增加、移除动画
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		// 添加分割线
		mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
		// 设置adapter
		adapter = new DelegationProAdapter(this);
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

	// 自定义标题栏
	protected EventTopTitleView topTitleView = null;

	/**
	 * 标题栏
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("委托供应");
		topTitleView.setTitleNewsOnclick(new TitleNewsOnclick() {

			@Override
			public void setTitleNewsOnclickLister(boolean isPop) {

				topTitleView.displayPoP(MsgItemUtil.Pop_Default_txt, MsgItemUtil.Pop_Default_img);

			}
		});
		topTitleView.setNewsPopItemOnclick(new NewsPopItemOnclick() {

			@Override
			public void setNewsPopItemOnclickLister(int state) {
				MsgItemUtil.onclickPopItem(state, DelegationProviderActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}

	/**
	 * 条目点击事件
	 * 
	 * @param parent
	 * @param view
	 * @param position
	 * @param id
	 */
	@Override
	public <T> void onItemClick(View view, int position, T t) {
		GoodsSourceInfo info = (GoodsSourceInfo) t;
		goActivity(info, DelegationProviderDetailsActivity.class);
	}
}
