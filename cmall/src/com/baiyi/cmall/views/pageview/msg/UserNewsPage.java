package com.baiyi.cmall.views.pageview.msg;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.msg.MyMsgListActivity;
import com.baiyi.cmall.adapter.MyMsgAdapter;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.MessageBaseEntity;
import com.baiyi.cmall.entity.MessageEntity;
import com.baiyi.cmall.entity.UserInfoEntity;
import com.baiyi.cmall.pageviews.BasePageView;
import com.baiyi.cmall.utils.JsonParse_User;
import com.baiyi.cmall.utils.UserNetUrl;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase;
import com.baiyi.cmall.views.pulldownview.PullToRefreshListView;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase.OnLastItemVisibleListener;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase.OnRefreshListener;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 用户消息
 * 
 * @author tangkun
 * 
 */
public class UserNewsPage extends BasePageView {
	public static final String TAG = UserNewsPage.class.getName();
	private PullToRefreshListView mPullToRefreshListView = null;
	private MyMsgAdapter adapter = null;
	private MyLoadingBar progressBar = null;
	private NewsOnItemClick newsOnItemClick = null;
	private int pageNum = 1;
	// 消息类型 state：［0:全部，1:未读，2:已读］
	private int state = 0;

	public UserNewsPage(Context context) {
		super(context);
		ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_user_msg, this);
		setListView();
	}

	private void setListView() {

		progressBar = (MyLoadingBar) findViewById(R.id.loading);
		mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.listview_news);
		mPullToRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(getContext(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);

						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						pageNum = 1;
						loadData(state);
					}
				});

		mPullToRefreshListView
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						if (!((BaseActivity) getContext()).isListViewBottom()) {
							return;
						}
						pageNum++;
						loadData(state);
					}
				});
		mPullToRefreshListView.addFootView();
		mPullToRefreshListView.SetFooterCanUse(true);
		ListView listView = mPullToRefreshListView.getRefreshableView();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MessageEntity entity = (MessageEntity) adapter
						.getItem(position - 1);
				if (entity == null) {
					return;
				}
				if (newsOnItemClick != null) {
					newsOnItemClick.setNewsOnItemClick(position, entity);
				}
			}
		});

		adapter = new MyMsgAdapter(getContext());
		listView.setAdapter(adapter);

	}

	private void loadData(int state) {
		progressBar.setVisibility(View.VISIBLE);
		progressBar.setProgressInfo("正在加载中...");
		progressBar.start();
		UserInfoEntity user = CmallApplication.getUserInfoEntity();
		if (user == null) {
			return;
		}
		JsonLoader loader = new JsonLoader(getContext());
		loader.setUrl(UserNetUrl.getUserMessageList(user.getMID(), state,
				pageNum, Config.LIST_ITEM_COUNT_Message));
		loader.setMethod(BaseNetLoder.Method_Get);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum,
					long totalByteNum) {
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				mPullToRefreshListView.onRefreshComplete();
				progressBar.setVisibility(View.GONE);
				progressBar.stop();

			}

			@Override
			public void onCompelete(Object tag, Object result) {
				JSONArray array = (JSONArray) result;
				MessageBaseEntity entity = JsonParse_User.getNewsList(
						getContext(), array, TAG);
				displayView(entity);
				((MyMsgListActivity) getContext()).displayNewsCount();
				if (entity.getDataList().size() < Config.LIST_ITEM_COUNT) {
					mPullToRefreshListView.SetFooterCanUse(false);
				}

			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	private void displayView(MessageBaseEntity entity) {
		mPullToRefreshListView.onRefreshComplete();
		progressBar.setVisibility(View.GONE);
		progressBar.stop();

		if (entity == null || Utils.isStringEmpty(entity.getDataList())) {
			if (pageNum == 1) {
				progressBar.stop();
				progressBar.setVisibility(View.VISIBLE);
				progressBar.setProgressLoadError(
						getResources().getString(R.string.tip_newslist_noall),
						MyLoadingBar.type_sample);
			} else {
				((BaseActivity) getContext()).displayToast(getResources()
						.getString(R.string.tip_loaddata_bottom));
			}
			return;
		}

		((BaseActivity) getContext()).setLisViewSize(entity.getDataList()
				.size());

		if (pageNum == 1) {
			adapter.setNewsName(entity.getNewsName());
			adapter.setData(entity.getDataList());
		} else {
			adapter.addData(entity.getDataList());
		}

	}

	public void setReadAll(final ReadOnCompelete lister) {
		progressBar.setVisibility(View.VISIBLE);
		progressBar.setProgressInfo("正在加载中...");
		JsonLoader loader = new JsonLoader(getContext());
		loader.setUrl(UserNetUrl.getUserAllMarkRead(CmallApplication
				.getUserInfoEntity().getMID()));
		loader.setMethod(BaseNetLoder.Method_Get);
		
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum,
					long totalByteNum) {
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				progressBar.setVisibility(View.GONE);
				if (lister != null) {
					lister.setReadOnCompeleteLister(false);
				}
			}

			@Override
			public void onCompelete(Object tag, Object result) {
				progressBar.setVisibility(View.GONE);
				if (lister != null) {
					lister.setReadOnCompeleteLister(true);
				}
				displayRead();
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	public void displayRead() {
		if (adapter == null) {
			return;
		}
		List<MessageEntity> newList = new ArrayList<MessageEntity>();
		List<MessageEntity> dataList = adapter.getDataList();
		if (Utils.isStringEmpty(dataList)) {
			return;
		}
		for (MessageEntity entity : dataList) {
			entity.setMsgState("已读");
			newList.add(entity);
		}
		adapter.setData(newList);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		loadData(state);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

	}

	@Override
	public void onDestroy() {
	}

	public void notifyDataSetChanged(int itemPostion) {
		ListView listView = mPullToRefreshListView.getRefreshableView();
		int visiblePosition = listView.getFirstVisiblePosition();
		View view = listView.getChildAt(itemPostion - visiblePosition);
		listView.getAdapter().getView(itemPostion, view, listView);

		TextView title = (TextView) view.findViewById(R.id.news_title);
		title.setTextColor(0xFF454545);

		List<MessageEntity> newList = new ArrayList<MessageEntity>();
		List<MessageEntity> dataList = adapter.getDataList();
		MessageEntity entity = dataList.get(itemPostion - 1);
		entity.setMsgState("已读");
		newList.addAll(dataList);
		adapter.setData(newList);
	}

	public void setNewsOnItemClick(NewsOnItemClick newsOnItemClick) {
		this.newsOnItemClick = newsOnItemClick;
	}

	public interface NewsOnItemClick {
		public void setNewsOnItemClick(int itemPosition, MessageEntity entity);
	}

	public interface ReadOnCompelete {
		public void setReadOnCompeleteLister(boolean isCompelete);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.baiyi.dmall.pageviews.PageView#onActivityResult(int, int,
	 * android.content.Intent)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	}

}
