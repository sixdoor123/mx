package com.baiyi.jj.app.activity.main.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baiyi.core.file.Preference;
import com.baiyi.core.loader.BaseLoader;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader;
import com.baiyi.core.loader.LoaderStrategy;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.BaseAnalyticsActivity;
import com.baiyi.jj.app.activity.ChannelUtils;
import com.baiyi.jj.app.activity.main.NewsRecyclerAdapter;
import com.baiyi.jj.app.activity.main.news.BaseHolder;
import com.baiyi.jj.app.activity.main.news.NewsRefreshRender;
import com.baiyi.jj.app.activity.main.task.NewsLoaderManager;
import com.baiyi.jj.app.activity.main.task.Task;
import com.baiyi.jj.app.activity.main.task.WebLoadManager;
import com.baiyi.jj.app.adapter.AutoAdapter;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.cache.Dao.NewsListDao;
import com.baiyi.jj.app.cache.Dao.WebDetailDao;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.entity.article.ArticleEntity;
import com.baiyi.jj.app.manager.NewsListDataManager;
import com.baiyi.jj.app.manager.VideoPlayManager;
import com.baiyi.jj.app.manager.WebViewManager;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.IntentClassChangeUtils;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.TagUtils;
import com.baiyi.jj.app.utils.TimerCountCallBack;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.turbo.turbo.mexico.R;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/1/10 0010.
 */
public abstract class NewsBaseFragment extends BaseFragment {

    @Bind(R.id.news_list)
    XRecyclerView mListView;
    @Bind(R.id.news_new_count_group)
    RelativeLayout newCountGroup = null;
    @Bind(R.id.news_new_count)
    protected TextView newCountTv = null;
    @Bind(R.id.home_loading)
    MyLoadingBar loadingBar;
    @Bind(R.id.lin_shownonews)
    LinearLayout linShowNoNews;
    @Bind(R.id.lin_shownonet)
    LinearLayout linShowNoNet;
    @Bind(R.id.lin_firstshow)
    LinearLayout linFirstShow;

    LinearLayout fullScreenLin;
    private AutoAdapter autoAdapter;
    private NewsRecyclerAdapter newsAdapter;
    private View rootView;

    private int bottomPageIndex = 0;
    private int newsPageSize;
    private int FirstDownId = 0;
    private int refreshType = NewsListDataManager.Refresh_Type_New;

    private int fontType;
    private int abstractLines;
    private int imagequality;

    boolean isCreat;
    Preference preference;

    private OnRefreshComplete onComplete = null;
    private WebLoadManager webLoadManager;

    int currentVideo = -1;

    public void updateArguments(List<ChannelItem> list,int postion) {
        isCreat = true;
        TLog.e("update","updateargument------------------*********");
        Bundle args = getArguments();
        if (args != null) {
            if (postion == 0){
                args.putSerializable(Define.Channel_Group, (Serializable) list);
            }else {
                args.putString(Define.ChannelId, list.get(0).getCid());
            }
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference = Preference.getInstance();
        isCreat = true;
        this.newsLoaderManager = new NewsLoaderManager();
        this.webLoadManager = new WebLoadManager();
        fontType = AccountManager.getInstance().getCurrentSize();
        abstractLines = AccountManager.getInstance().getSummary_Is_Display();
        imagequality = AccountManager.getInstance().getWifi_Is_Display_Img();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_newslist, container, false);
            ButterKnife.bind(this, rootView);
            initListView();
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //execute the task
                creatData();

            }
        }, 300);
    }

    private void creatData() {
        initData();
        isCreat = false;
    }

    public abstract void saveFidStr(String fid);
    public abstract void initCreatData(boolean isShowNoData);
    public abstract int getCacheSize();
    public abstract List<NewsListEntity> getCacheData(int page,int id);
    public abstract boolean hasHeadView();
    public abstract String getChannelId();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (newsLoaderManager != null) {
            newsLoaderManager.clear();
            newsLoaderManager = null;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != rootView) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
    }

    void showNoCacheLoading() {
        linFirstShow.setVisibility(View.VISIBLE);
        loadingBar.setVisibility(View.VISIBLE);
        loadingBar.start();
    }
    void hideNoCacheLoading() {
        linFirstShow.setVisibility(View.GONE);
        loadingBar.setVisibility(View.GONE);
        loadingBar.start();
    }

    private void initData() {

        showNoCacheLoading();
        bottomPageIndex = 0;
        FirstDownId = 0;
        newsAdapter.clear();
        int datasize = getCacheSize();

        newsPageSize = datasize / 10;
        if (newsPageSize > 0) {
            loadCacheData();
        }
        initCreatData(newsPageSize<=0);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isRefresh()) {
            refreshList();
        }
        if (newsAdapter != null && newsAdapter.getItemCount() != 0) {
            linShowNoNet.setVisibility(View.GONE);
            linShowNoNews.setVisibility(View.GONE);
        }
    }

    public boolean isRefresh() {
        int lines = AccountManager.getInstance().getSummary_Is_Display();
        boolean isAbstractChange = !(abstractLines == lines);
        abstractLines = isAbstractChange ? lines : abstractLines;

        boolean isFontChange = !(fontType == AccountManager.getInstance()
                .getCurrentSize());
        fontType = isFontChange ? AccountManager.getInstance().getCurrentSize()
                : fontType;

        boolean isImageChange = !(imagequality == AccountManager.getInstance().getWifi_Is_Display_Img());
        imagequality = isImageChange ? AccountManager.getInstance().getWifi_Is_Display_Img()
                : imagequality;

        return isFontChange | isAbstractChange | isImageChange;
    }

    public void refreshList() {
        if (newsAdapter != null) {
            newsAdapter.refresh();
        }
    }

    public void changeItem(boolean isGPRS) {
        if (newsAdapter != null) {
            newsAdapter.changeItem(isGPRS);
        }
    }

    public void setListRefreshing() {
//        TLog.e("sss", newsPageSize + "---------222----------" + isMixed + "--------" + isCreat);
        if (mListView != null && !mListView.isRefreshing()) {
            loadNetData();
            mListView.smoothScrollToPosition(0);
            mListView.refresh();
        }
    }

    public void loadOfflineData(long downloadTime, long downloadTime2) {
        List<NewsListEntity> listEntities = new NewsListDao(getContext()).getNewsOffline(downloadTime, downloadTime2);
        if (!Utils.isStringEmpty(listEntities)) {
            newsAdapter.addPullData(listEntities);
        }

    }

    private void loadCacheData() {

        if (newsPageSize != 0 && bottomPageIndex >= newsPageSize) {
            mListView.loadMoreComplete();
            return;
        }
        List<NewsListEntity> listEntities = getCacheData(bottomPageIndex,FirstDownId);
        if (bottomPageIndex == 0 && !Utils.isStringEmpty(listEntities)) {
            FirstDownId = listEntities.get(0).getSqlId();
        }
        if (Utils.isStringEmpty(listEntities)) {
            mListView.loadMoreComplete();
            mListView.refreshComplete();
            return;
        }
        disCacheData(listEntities);
    }

    private void disCacheData(List<NewsListEntity> newsList) {
        if (Utils.isStringEmpty(newsAdapter.getData())) {
            refreshType = NewsListDataManager.Refresh_Type_New;
            bottomPageIndex++;
        } else {
            refreshType = NewsListDataManager.Refresh_Type_More;
        }
        displayList(newsList, false);
    }

    private void displayList(List<NewsListEntity> newsList, boolean isSaveCache) {
//        if (!isLocal())
        saveCacheDataBase(newsList, isSaveCache);

        if (isSaveCache) {
            List<NewsListEntity> listEntities = new ArrayList<>();
            boolean iswifi = !ContextUtil.isGprs(getContext());
            int savesize = iswifi ? 4 : 2;
            for (int i = 0; i < newsList.size(); i++) {
                if (i > savesize) {
                    break;
                }
                listEntities.add(newsList.get(i));
            }
//            TLog.e("acss","ss----------"+listEntities.size());
            loadWebText(listEntities);
        }

        if (refreshType == NewsListDataManager.Refresh_Type_Pull) {
            if (!Utils.isStringEmpty(newsAdapter.getData())) {
                NewsListEntity topNews = newsAdapter.getData().get(0);
                if (topNews.getType().equals(TagUtils.StorType_eidtion_top) && !Utils.isStringEmpty(newsList)) {
                    newsAdapter.remove(0);
                    NewsListEntity newTop = newsList.get(0);
//                    TLog.e("newtop", newTop.getType() + "---999--" + newTop.getArticle_title());
                    if (!newTop.getType().equals(TagUtils.StorType_eidtion_top)) {
                        newsList.add(0, topNews);
                    }

                }
            }
            newsAdapter.addPullData(newsList);
            if (isSaveCache) {
                displayRefreshCount(Utils.isStringEmpty(newsList) ? 0 : newsList.size(), false);
            }
        } else if (refreshType == NewsListDataManager.Refresh_Type_New) {
            newsAdapter.setData(newsList);
            refreshType = NewsListDataManager.Refresh_Type_Pull;
            if (isSaveCache) {
                displayRefreshCount(Utils.isStringEmpty(newsList) ? 0 : newsList.size(), false);
            }

        } else if (refreshType == NewsListDataManager.Refresh_Type_More) {
            newsAdapter.addData(newsList);
            bottomPageIndex++;
            refreshType = NewsListDataManager.Refresh_Type_Pull;
        }
        if (!isSaveCache) {
            mListView.loadMoreComplete();
        }
        mListView.refreshComplete();
    }

    public void saveCacheDataBase(final List<NewsListEntity> newsList, boolean isSaveCache) {
        if (Utils.isStringEmpty(newsList)) {
            return;
        }
        if (refreshType == NewsListDataManager.Refresh_Type_More) {
            return;
        }
        if (!isSaveCache) {
            return;
        }
        new NewsListDao(getContext()).add(newsList);
    }


    public abstract boolean isLocal();
    public abstract String getUrl();
    public abstract String getPostData(boolean isWifi);
    public abstract ArticleEntity getAricleEntity(JSONArray array);
    public abstract void addMorningHead();

    private JsonLoader loader = null;
    private NewsLoaderManager newsLoaderManager = null;
    private ArticleEntity articleEntity = null;

    private void loadNetData() {
        if (linShowNoNews != null) {
            linShowNoNews.setVisibility(View.GONE);
            linShowNoNet.setVisibility(View.GONE);
        }
        final long start = System.currentTimeMillis();
        String url = getUrl();
        boolean iswifi = !ContextUtil.isGprs(getContext());
        String postData = getPostData(iswifi);
        LoaderStrategy taskLoader = CmsApplication.getDataStratey();

        Task task = new Task();
        task.setImgLoader(loader);
        task.setTag(url);
        newsLoaderManager.addTask(task);
        loader = new JsonLoader(getContext());
        loader.setMethod(BaseNetLoder.Method_Post);
        loader.setPostData(postData);
        if (!Utils.isStringEmpty(CmsApplication.getUserToken())){
            loader.setUrl(url);
//            if (!isLocal())
            loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
        }else {
            loader.setUrl(NewsListDataManager.getUrlV5());
        }
        loader.setLoaderListener(new Loader.LoaderListener() {

            public void onProgress(Object arg0, long arg1, long arg2) {

            }

            public void onError(Object arg0, int arg1, String arg2) {

                mListView.refreshComplete();
                loadingBar.setVisibility(View.GONE);
                loadingBar.stop();
                linFirstShow.setVisibility(View.GONE);

                if (newsAdapter != null && newsAdapter.getItemCount() == 0) {
                    mListView.init();
                    if (!ContextUtil.isNetWorking(getContext())) {
                        linShowNoNet.setVisibility(View.VISIBLE);
                    } else {
                        linShowNoNews.setVisibility(View.VISIBLE);
                    }
                }
                if (onComplete != null) {
                    onComplete.OnClick();
                }
                if (arg1 == 401) {
                    displayRefreshCount(0, true);
                    return;
                } else if (arg1 == 500) {

                }
                TLog.e("listerror", arg1 + "=======" + arg2);
                if (arg1 == BaseLoader.Result_Code_NetTimeOut) {
                    displayRefreshCount(0, true);
                } else {
                    ((BaseActivity) getContext()).displayToast(getContext().getString(R.string.tip_loaddata_timeout));
                }
            }

            public void onCompelete(Object arg0, Object arg1) {
                loadingBar.setVisibility(View.GONE);
                loadingBar.stop();
                linFirstShow.setVisibility(View.GONE);
                if (onComplete != null) {
                    onComplete.OnClick();
                }
                long value = System.currentTimeMillis() - start;
                try {
                    ((BaseActivity) getActivity()).addRequestTime(this.getClass().getName(), BaseAnalyticsActivity.Category_net, BaseAnalyticsActivity.Net_Article_Pull, value);
                }catch (Exception e){
                    e.printStackTrace();
                }
                addMorningHead();
                try {
                    JSONArray array = (JSONArray) arg1;
                    TLog.e("list--" + getChannelId(), array.toString());
                    articleEntity = getAricleEntity(array);
                    if  (articleEntity != null) {
                        String fid = articleEntity.getFidStr();
                        saveFidStr(fid);
                        displayList(articleEntity.toNewsList(), true);
                        if (Utils.isStringEmpty(articleEntity.toNewsList())) {
                            if (newsAdapter != null && newsAdapter.getItemCount() == 0) {
                                mListView.init();
                                linShowNoNews.setVisibility(View.VISIBLE);
                            }
                        } else {
                            linShowNoNet.setVisibility(View.GONE);
                            linShowNoNews.setVisibility(View.GONE);
                        }
                    } else {
                        if (newsAdapter != null && newsAdapter.getItemCount() == 0) {
                            mListView.init();
                            linShowNoNews.setVisibility(View.VISIBLE);
                        }
                        mListView.refreshComplete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mListView.refreshComplete();
                    if (newsAdapter != null && newsAdapter.getItemCount() == 0) {
                        mListView.init();
                        linShowNoNews.setVisibility(View.VISIBLE);
                    }
                }

            }
        });

        taskLoader.startLoader(loader);
    }


    private void initListView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mListView.setLayoutManager(layoutManager);

        mListView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mListView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
//        mListView.setArrowImageView(R.drawable.iconfont_downgrey);

        mListView.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        ((BaseActivity) getActivity()).addEnvent(BaseAnalyticsActivity.Envent_Pull_Refresh);
                        loadNetData();

                    }

                }, 2000);


            }

            @Override
            public void onLoadMore() {
                // TODO Auto-generated method stub
                if (bottomPageIndex >= newsPageSize) {
                    mListView.loadMoreComplete();
                    return;
                }
                loadCacheData();
            }
        });
        newsAdapter = new NewsRecyclerAdapter(getActivity(), mListView, true,
                CmsApplication.isGprs, true, fullScreenLin);
        mListView.setAdapter(newsAdapter);

        newsAdapter.setRecyclerViewItemClick(new BaseHolder.RecyclerViewItemClick() {

            @Override
            public void setOnItemClickLister(int position) {
                int pos = position;
                if (hasHeadView() && position > 1) {
                    pos--;
                }
                NewsListEntity entity = (NewsListEntity) newsAdapter.getNewsListEntity(pos - 1);
                IntentClassChangeUtils.startNewsDetail(getContext(), entity, position, 0,false);

            }
        });

        newsAdapter.setOnEnterClickLisner2(new NewsRecyclerAdapter.OnEnterClickLisner2() {
            @Override
            public void click(int postion, int currenttime) {
                NewsListEntity entity = (NewsListEntity) newsAdapter.getNewsListEntity(postion);
                IntentClassChangeUtils.startNewsDetail(getContext(), entity, postion, currenttime,false);
            }
        });

        newsAdapter.setRefreshListOnclick(new NewsRefreshRender.RefreshListOnclick() {

            @Override
            public void setOnRefreshListLister() {
                setListRefreshing();
            }
        });

        newsAdapter.setOnPlayerClickLisner(new NewsRecyclerAdapter.OnPlayerClickLisner() {
            @Override
            public void clickId(int postion) {
                currentVideo = postion;
            }
        });

        mListView.addOnScrollListener(new RecyclerViewListener());

    }

    @Override
    public void onPause() {
        super.onPause();
        pauseVideo();
    }
    private void pauseVideo(){
        VideoPlayManager.getInstance().pausePlayVideo();
    }

    class RecyclerViewListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            RecyclerView.LayoutManager layoutManager1 = recyclerView.getLayoutManager();
            if (layoutManager1 instanceof LinearLayoutManager) {
                LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager1;
                //获取最后一个可见view的位置
                int lastItemPosition = linearManager.findLastVisibleItemPosition();
                //获取第一个可见view的位置
                int firstItemPosition = linearManager.findFirstVisibleItemPosition();

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int size = newsAdapter.getData().size();
                    if (size > lastItemPosition && size > firstItemPosition + 1) {
                        List<NewsListEntity> listEntities = new ArrayList<>();
                        int offset = (lastItemPosition - firstItemPosition) < 5 ? 0 : (lastItemPosition - firstItemPosition - 5);
//                        TLog.e("test",firstItemPosition+"========="+lastItemPosition+"=========="+offset);
                        for (int i = 0; i < lastItemPosition - firstItemPosition - offset; i++) {
                            listEntities.add(newsAdapter.getData().get(firstItemPosition + i + (offset / 2)));
                        }
                        loadWebText(listEntities);
                    }

                }
                if (currentVideo == -1 || currentVideo == -2) {
                    return;
                }
                if (currentVideo < firstItemPosition - 1 || currentVideo > lastItemPosition + 1) {
                    currentVideo = -1;
                    VideoPlayManager.getInstance().pausePlayVideo();
                }

            }
            super.onScrollStateChanged(recyclerView, newState);
        }
    }

    private void loadWebText(List<NewsListEntity> articleList) {

        WebDetailDao detailDao = new WebDetailDao(getContext());
        for (int i = 0; i < articleList.size(); i++) {
            String articleid = articleList.get(i).getArticle_id();
            if (detailDao.isLoaded(articleid)) {
                continue;
            }
            WebViewManager.loadLocalDetail(webLoadManager, getContext(), articleid, null);
        }
    }


    private TimerCountCallBack timer = null;

    public void displayRefreshCount(final int newCount, final boolean isError) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                disPlayCout(newCount, isError);
            }

        }, 500);

    }

    private void disPlayCout(final int newCount, final boolean isError) {
        if (timer == null) {
            timer = new TimerCountCallBack(2000, 1000);
        }
        timer.cancel();
        timer.setCallBack(new TimerCountCallBack.CallBack() {
            @Override
            public void callBackOnTick(long millisUntilFinished) {
                newCountGroup.setVisibility(View.VISIBLE);
                if (newCountTv == null){return; }
                if (isError) {
                    newCountTv.setText(getContext().getResources().getString(R.string.tip_loaddata_timeout));
                } else {
                    if (newCount == 0) {
                        newCountTv.setText(getContext().getResources().getString(R.string.tip_loaddata_pushnull));
                    } else {
                        newCountTv.setText(getContext().getResources().getString(R.string.tip_recomment1) + " " + newCount + " " + getContext().getResources().getString(R.string.tip_recomment2));
                    }
                }
            }

            @Override
            public void callBackOnFinish() {
                newCountGroup.setVisibility(View.GONE);
            }
        });
        timer.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ChannelUtils.Result_Detail_Comment) {
            int comNum = data.getIntExtra(Define.CommentNum, -1);
            if (comNum != -1) {
                int position = data.getIntExtra(Define.ITEM_POSTION, -1);
                if (position != -1 && newsAdapter != null) {
                    newsAdapter.refreshItem2Com(position, comNum);
                }
            }
        }
    }

    public void setOnComplete(OnRefreshComplete onComplete) {
        this.onComplete = onComplete;
    }


    public interface OnRefreshComplete {
        public void OnClick();
    }

}
