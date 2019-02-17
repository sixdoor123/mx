
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

import com.baiyi.cmall.Config;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.msg.MessageItemDetailActivity;
import com.baiyi.cmall.activities.msg.MyMsgListActivity;
import com.baiyi.cmall.adapter.MyMsgAdapter;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.MessageBaseEntity;
import com.baiyi.cmall.entity.MessageEntity;
import com.baiyi.cmall.pageviews.BasePageView;
import com.baiyi.cmall.utils.Define;
import com.baiyi.cmall.utils.JsonParse_User;
import com.baiyi.cmall.utils.UserNetUrl;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.utils.XmlName;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase;
import com.baiyi.cmall.views.pulldownview.PullToRefreshListView;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase.OnLastItemVisibleListener;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase.OnRefreshListener;
import com.baiyi.core.file.Preference;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 系统消息
 * @author tangkun
 *
 */
public class SystemNewsPage extends BasePageView{
	
	public static final String TAG = SystemNewsPage.class.getName();
	private PullToRefreshListView mPullToRefreshListView = null;
	private MyMsgAdapter adapter = null;
	private MyLoadingBar progressBar = null;
	private int pageNum = 1;

	public SystemNewsPage(Context context) {
		super(context);
		ContextUtil.getLayoutInflater(context).inflate(R.layout.activity_user_msg, this);
		setListView();
	}

	private void setListView() {

		progressBar = (MyLoadingBar)findViewById(R.id.loading);
		mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.listview_news);
		mPullToRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(
								getContext(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);

						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						pageNum = 1;
						loadData();
					}
				});

		mPullToRefreshListView
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						if(!((BaseActivity)getContext()).isListViewBottom())
						{
							return;
						}
						pageNum++;
						loadData();
					}
				});
		mPullToRefreshListView.addFootView();
		mPullToRefreshListView.SetFooterCanUse(true);
		ListView listView = mPullToRefreshListView.getRefreshableView();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MessageEntity entity = (MessageEntity)adapter.getItem(position - 1);
				if(entity == null)
				{
					return;
				}
				Intent intent = new Intent(getContext(), MessageItemDetailActivity.class);
				intent.putExtra(Define.ITEM_POSTION, MyMsgListActivity.Result_System);
				intent.putExtra(Define.NewsEntity, entity);
				getContext().startActivity(intent);
			}
		});

		adapter = new MyMsgAdapter(getContext());
		listView.setAdapter(adapter);

	}
	
	private void loadData()
	{
		progressBar.setVisibility(View.VISIBLE);
		progressBar.setProgressInfo("正在加载中...");
		progressBar.start();
//		UserInfoEntity user = CmsApplication.getUserInfoEntity();
//		if(user == null)
//		{
//			return;
//		}
		JsonLoader loader = new JsonLoader(getContext());
		loader.setUrl(UserNetUrl.getMultiUserMessageList( pageNum, Config.LIST_ITEM_COUNT_Message));
		loader.setMethod(BaseNetLoder.Method_Get);
		loader.setLoaderListener(new LoaderListener() {
			
			@Override
			public void onProgress(Object tag, long curByteNum, long totalByteNum) {
			}
			
			@Override
			public void onError(Object tag, int responseCode, String errorMessage) {
				mPullToRefreshListView.onRefreshComplete();
				progressBar.setVisibility(View.GONE);
				progressBar.stop();
				
			}
			
			@Override
			public void onCompelete(Object tag, Object result) {
				JSONArray array = (JSONArray)result;
				MessageBaseEntity entity = JsonParse_User.getNewsList(getContext(), array, TAG);
				displayView(entity);
				
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}
	
	private void displayView(MessageBaseEntity entity)
	{
		mPullToRefreshListView.onRefreshComplete();
		progressBar.setVisibility(View.GONE);
		progressBar.stop();
		if (entity == null || Utils.isStringEmpty(entity.getDataList())) {
			if (pageNum == 1) {
				progressBar.stop();
				progressBar.setVisibility(View.VISIBLE);
				progressBar.setProgressLoadError(getResources().getString(R.string.tip_systemynews_noall), MyLoadingBar.type_sample);
				
			} else {
				((BaseActivity) getContext()).displayToast(
						getResources().getString(R.string.tip_loaddata_bottom));
			}
			return;
		}
		
		((BaseActivity)getContext()).setLisViewSize(entity.getDataList().size());
		
		int multiMaxID = CmallApplication.getUserInfoEntity().getMultiMaxID();
		int userUnReadCount = CmallApplication.getUserInfoEntity().getUserUnReadCount();
		if(multiMaxID > 0)
		{
			Preference.getInstance().Set(XmlName.NAME_MultiMaxID + CmallApplication.getUserInfoEntity().getMID(), String.valueOf(multiMaxID));
			Preference.getInstance().saveConfig();
		}
		CmallApplication.setNews(getContext(), multiMaxID, userUnReadCount);
		((MyMsgListActivity)getContext()).displayNewsCount();
		
		if (pageNum == 1) {
			adapter.setNewsName(entity.getNewsName());
			adapter.setData(entity.getDataList());
		} else {
			adapter.addData(entity.getDataList());
		}

		if(entity.getDataList().size() < Config.LIST_ITEM_COUNT)
		{
			mPullToRefreshListView.SetFooterCanUse(false);
		}
		
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		loadData();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	}

	@Override
	public void onDestroy() {
	}
	
	
	public void setReadAll(final ReadSysOnCompelete lister)
	{
		progressBar.setVisibility(View.VISIBLE);
		progressBar.setProgressInfo("正在加载中...");
		JsonLoader loader = new JsonLoader(getContext());
		loader.setUrl(UserNetUrl.getUserAllMarkRead(CmallApplication.getUserInfoEntity().getMID()));
		loader.setMethod(BaseNetLoder.Method_Get);
		loader.setLoaderListener(new LoaderListener() {
			
			@Override
			public void onProgress(Object tag, long curByteNum, long totalByteNum) {
			}
			
			@Override
			public void onError(Object tag, int responseCode, String errorMessage) {
				progressBar.setVisibility(View.GONE);
				if(lister != null)
				{
					lister.setReadSysOnCompeleteLister(false);
				}
			}
			
			@Override
			public void onCompelete(Object tag, Object result) {
				progressBar.setVisibility(View.GONE);
				if(lister != null)
				{
					lister.setReadSysOnCompeleteLister(true);
				}
				displayRead();
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}
	
	
	private void displayRead()
	{
		List<MessageEntity> newList = new ArrayList<MessageEntity>();
		List<MessageEntity> dataList = adapter.getDataList();
		for(MessageEntity entity : dataList)
		{
			entity.setMsgState("已读");
			newList.add(entity);
		}
		adapter.setData(newList);
	}
	
	public interface ReadSysOnCompelete
	{
		public void setReadSysOnCompeleteLister(boolean isCompelete);
	}

	/* (non-Javadoc)
	 * @see com.baiyi.jj.app.views.pageview.PageView#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	}

}
