package com.baiyi.cmall.activities.main.baseList;

import java.io.Serializable;
import java.util.ArrayList;

import android.view.View;
import android.widget.ListView;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.utils.MsgItemUtil;
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
import com.baiyi.cmall.R;

/**
 * ֻ��ListView�Ļ���
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-1-21 ����1:30:42
 */
public abstract class BaseListActivity extends BaseActivity {

	// ����Դ
	public ArrayList<Serializable> datas;
	// ҳ��
	public int pageIndex = 1;
	// ���λ ֻ�ڵ�һ�μ�������ʱ����ʾ
	public boolean flag = true;
	// ������
	public BaseListAdapter adapter;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(false);
		initTitle();
		initListView();
	}

	@Override
	protected void onStart() {
		super.onStart();
		loadData();
	}

	// �Զ������
	private EventTopTitleView topTitleView;

	/**
	 * ������
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName(getTitleName());
		topTitleView.setTitleNewsOnclick(new TitleNewsOnclick() {

			@Override
			public void setTitleNewsOnclickLister(boolean isPop) {

				topTitleView.displayPoP(MsgItemUtil.Pop_Default_txt,
						MsgItemUtil.Pop_Default_img);

			}
		});
		topTitleView.setNewsPopItemOnclick(new NewsPopItemOnclick() {

			@Override
			public void setNewsPopItemOnclickLister(int state) {
				MsgItemUtil.onclickPopItem(state, BaseListActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}

	/**
	 * ��ñ�������
	 * 
	 * @return
	 */
	public abstract String getTitleName();

	public PullToRefreshListView listView;
	// ����
	private MyLoadingBar loadingBar;

	/**
	 * ��ʼ��ListView
	 */
	private void initListView() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.list_have_divider, null);
		win_content.addView(view);

		listView = (PullToRefreshListView) view
				.findViewById(R.id.lst_industry_trends);
		loadingBar = (MyLoadingBar) view.findViewById(R.id.load);

		// ����������
		adapter = getListAdapter();
		listView.setAdapter(adapter);

		listView.SetFooterCanUse(true);
		// ����ˢ��
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				flag = false;
				pageIndex = 1;
				loadData();
			}
		});

		// ����ˢ��
		listView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				flag = false;
				++pageIndex;
				loadData();
			}
		});
	}

	/**
	 * ��������
	 */
	private void loadData() {
		if (flag) {
			loadingBar.setVisibility(View.VISIBLE);
			loadingBar.setProgressInfo("������,���Ժ�...");
			loadingBar.start();
		}

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(getRequestUrl());
		loader.setPostData(getRequstPostData());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.addRequestHeader("token", getToken());
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
				loadingBar.setProgressInfo("���ڽ���");
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				if (flag) {
					loadingBar.setVisibility(View.GONE);
					loadingBar.stop();
				}
				displayToast(arg2);
			}

			@Override
			public void onCompelete(Object arg0, Object result) {
				datas = getParseData(result);
				if (flag) {
					loadingBar.setVisibility(View.GONE);
					loadingBar.stop();
				}
				updateListView();
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * ����ListView��������
	 */
	protected void updateListView() {
		if (null != listView) {
			listView.onRefreshComplete();
		}
		if (pageIndex == 1) {
			adapter.setData(datas);
		} else {
			adapter.addData(datas);
		}
		if (null != datas && datas.size() > 0) {
			setListView(listView, datas.size());
			return;
		} else {
			setListView(listView, 0);
			return;
		}
	}

	/**
	 * �õ��ʺϵ�������
	 * 
	 * @return
	 */
	protected abstract BaseListAdapter getListAdapter();

	/**
	 * ��������·��
	 * 
	 * @return
	 */
	protected abstract String getRequestUrl();

	/**
	 * ��������ʱ��Я��������
	 * 
	 * @return
	 */
	protected abstract String getRequstPostData();

	/**
	 * ��ȡToken
	 * 
	 * @return
	 */
	protected abstract String getToken();

	/**
	 * ��������
	 * 
	 * @param result
	 * @return
	 */
	protected abstract ArrayList<Serializable> getParseData(Object result);

}
