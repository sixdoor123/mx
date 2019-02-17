package com.baiyi.jj.app.activity.main.fragment;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.baiyi.jj.app.Config;
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
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.cache.Dao.ChannelDao;
import com.baiyi.jj.app.cache.Dao.NewsListDao;
import com.baiyi.jj.app.cache.Dao.WebDetailDao;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.entity.article.ArticleEntity;
import com.baiyi.jj.app.manager.ChannelDataManager;
import com.baiyi.jj.app.manager.CountryMannager;
import com.baiyi.jj.app.manager.NewsListDataManager;
import com.baiyi.jj.app.manager.VideoPlayManager;
import com.baiyi.jj.app.manager.WebViewManager;
import com.baiyi.jj.app.utils.ArticleHistoryUtils;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.IntentClassChangeUtils;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.TagUtils;
import com.baiyi.jj.app.utils.TimerCountCallBack;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.utils.XMLName;
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
 * Created by Administrator on 2017/5/5.
 */

public class VideoListFragment extends Fragment {

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

    private NewsRecyclerAdapter newsAdapter;

    private boolean isMixed;
    private String ChannelId;
    private List<ChannelItem> channelItems = new ArrayList<>();

    private int bottomPageIndex = 0;
    private int newsPageSize;
    private String fidStr = "";
    private int FirstDownId = 0;
    private int refreshType = NewsListDataManager.Refresh_Type_New;

    private View rootView;
    private int fontType;
    private int abstractLines;
    private int countryType;
    private int imagequality;

    private int countryStr;
    boolean isCreat;
    private String firstID = "";
    private List<ChannelItem> channelItemList = new ArrayList<>();

    private Preference preference;

    private WebLoadManager webLoadManager;

    int currentVideo = -1;

    LinearLayout fullScreenLin;


    public static VideoListFragment newInstance(List<ChannelItem> list, boolean isMix) {
        VideoListFragment fragment = new VideoListFragment();
        fragment.setArguments(null);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Define.Channel_Group, (Serializable) list);
        bundle.putBoolean(Define.Channel_Type, isMix);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void updateArguments(List<ChannelItem> list, boolean isMix) {
        this.channelItemList = list;
        this.isMixed = isMix;
        isCreat = true;

        Bundle args = getArguments();
        if (args != null) {
            args.putSerializable(Define.Channel_Group, (Serializable) list);
            args.putBoolean(Define.Channel_Type, isMix);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference = Preference.getInstance();
        isCreat = true;
        isMixed = getArguments().getBoolean(Define.Channel_Type);
        List<ChannelItem> list = (ArrayList<ChannelItem>) getArguments().getSerializable(Define.Channel_Group);
        channelItems.clear();
        channelItems.addAll(list);
        this.newsLoaderManager = new NewsLoaderManager();
        this.webLoadManager = new WebLoadManager();
        if (!isMixed && !Utils.isStringEmpty(channelItems)) {
            ChannelId = channelItems.get(0).getCid();
            firstID = channelItems.get(0).getCid();
            fidStr = preference.Get(XMLName.XML_VideoFID + ChannelId, "");
        } else {
            fidStr = preference.Get(XMLName.XML_VideoFID, "");
        }
        fontType = AccountManager.getInstance().getCurrentSize();
        abstractLines = AccountManager.getInstance().getSummary_Is_Display();
        countryType = CountryMannager.getInstance().getCurrentCountry();
        countryStr = CountryMannager.getInstance().getCurrentCountry();
        imagequality = AccountManager.getInstance().getWifi_Is_Display_Img();
    }

    public void setChannelId(String channelId) {
        ChannelId = channelId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        List<ChannelItem> channelItemList = (ArrayList<ChannelItem>) getArguments().getSerializable(Define.Channel_Group);
        if (!Utils.isStringEmpty(channelItemList)) {
            ChannelId = channelItemList.get(0).getCid();
        }
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
        new Handler().postDelayed(new Runnable(){
            public void run() {
                //execute the task
                creatData();
            }
        }, 300);
    }

    private void creatData(){

        if (countryStr != CountryMannager.getInstance().getCurrentCountry()) {
            if (mListView != null) {
                newsAdapter.clear();
                FirstDownId = 0;
                initData();
                setListRefreshing();
            }
            countryStr = CountryMannager.getInstance().getCurrentCountry();
        } else {
            initData();
        }
//        setUserVisibleHint();
        isCreat = false;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (newsLoaderManager != null) {
            newsLoaderManager.clear();
            newsLoaderManager = null;
        }

    }

    private void saveFid() {
        if (preference != null) {
            if (isMixed) {
                preference.Set(XMLName.XML_VideoFID, fidStr);
            } else {
                if (ChannelId != null) {
                    preference.Set(XMLName.XML_VideoFID + ChannelId, fidStr);
                }
            }
            preference.saveConfig();
        }
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();

        if (null != rootView) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }

    }

    private void showNoCacheLoading() {
        linFirstShow.setVisibility(View.VISIBLE);
        loadingBar.setVisibility(View.VISIBLE);
        loadingBar.start();
    }

    private void initData() {


        if (Preference.getInstance().getBoolean(XMLName.XML_First_Load, true)) {
            showNoCacheLoading();
            Preference.getInstance().Set(XMLName.XML_First_Load, String.valueOf(false));
            Preference.getInstance().saveConfig();
        }
        if (newsAdapter != null && newsAdapter.getItemCount() > 0) {
            if (isMixed) {
                return;
            }
            if (!Utils.isStringEmpty(firstID) && firstID.equals(ChannelId)) {
                return;
            }
            firstID = ChannelId;
        }
        if (!isMixed) {
            fidStr = preference.Get(XMLName.XML_VideoFID + ChannelId, "");
        } else {
            fidStr = preference.Get(XMLName.XML_VideoFID, "");
        }
        bottomPageIndex = 0;
        FirstDownId = 0;
        newsAdapter.clear();
        int datasize;
        if (isMixed) {
            datasize = new NewsListDao(getContext()).getNewspagesize(true);
        } else {
            datasize = new NewsListDao(getContext()).getPagesizebyChannel(ChannelId);
        }
        newsPageSize = datasize / 10;
        if (newsPageSize > 0) {
            loadCacheData();
        } else {
            if (isCreat) {
                loadCacheData();
                if (datasize == 0) {
                    showNoCacheLoading();
                }
                setListRefreshing();
                return;
            }
        }

//        if (!NewsListDataManager.isFY_FirstDayTimes() && isMixed && isCreat) {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mListView.init();
//                    setListRefreshing();
//                }
//            }, 1000);
//        }


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

        boolean isCountrySet = !(countryType == CountryMannager.getInstance().getCurrentCountry());
        countryType = isCountrySet ? CountryMannager.getInstance().getCurrentCountry()
                : countryType;

        boolean isImageChange = !(imagequality == AccountManager.getInstance().getWifi_Is_Display_Img());
        imagequality = isImageChange ? AccountManager.getInstance().getWifi_Is_Display_Img()
                : imagequality;


        return isFontChange | isAbstractChange | isCountrySet | isImageChange;
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
        pauseVideo();
        if (mListView != null && !mListView.isRefreshing()) {
            loadNetData();
            mListView.smoothScrollToPosition(0);
            mListView.refresh();
        }
    }

    private void loadCacheData() {

        if (newsPageSize != 0 && bottomPageIndex >= newsPageSize) {
            mListView.loadMoreComplete();
            return;
        }
        List<NewsListEntity> listEntities;
        if (isMixed) {
            listEntities = new NewsListDao(getContext()).getNewsByPageType(TagUtils.StorType_video, bottomPageIndex, FirstDownId,true);
        } else {
            listEntities = new NewsListDao(getContext()).getNewsByChannidByPage(ChannelId, bottomPageIndex, FirstDownId);
        }
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

        saveCacheDataBase(newsList, isSaveCache);

        if (isSaveCache) {
            List<NewsListEntity> listEntities = new ArrayList<>();
            for (int i = 0; i < newsList.size(); i++) {
                if (i > 4) {
                    break;
                }
                listEntities.add(newsList.get(i));
            }
            loadWebText(listEntities);
        }

        if (refreshType == NewsListDataManager.Refresh_Type_Pull) {
            if (!Utils.isStringEmpty(newsAdapter.getData())) {
                NewsListEntity topNews = newsAdapter.getData().get(0);
                if (topNews.getType().equals(TagUtils.StorType_eidtion_top) && !Utils.isStringEmpty(newsList)) {
                    newsAdapter.remove(0);
                    NewsListEntity newTop = newsList.get(0);
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

    private JsonLoader loader = null;
    private NewsLoaderManager newsLoaderManager = null;
    private ArticleEntity articleEntity = null;

    private void loadNetData() {
        if (linShowNoNews != null) {
            linShowNoNews.setVisibility(View.GONE);
            linShowNoNet.setVisibility(View.GONE);
        }

        if (!isMixed) {
            fidStr = preference.Get(XMLName.XML_VideoFID + ChannelId, "");
        } else {
            fidStr = preference.Get(XMLName.XML_VideoFID, "");
        }

        final long start = System.currentTimeMillis();
        UserInfoEntity infoEntity = CmsApplication.getUserInfoEntity();
        List<ChannelItem> allList = new ArrayList<>();
        allList.clear();
        if (infoEntity != null) {
            allList = new ChannelDao(getActivity()).queryByTypeSub(ChannelDataManager.ChannelType_Video);
        }

//        if (!Utils.isStringEmpty(fidStr) && !Utils.checkHasOther(fidStr)) {
//            fidStr = Utils.checkHasOths(fidStr);
//        }

        String url = NewsListDataManager.getUrl(isMixed);
        String postData;
        boolean iswifi = !ContextUtil.isGprs(getContext());
        if (Utils.isStringEmpty(allList)) {
            postData = NewsListDataManager.getPostData(isMixed, channelItems, ChannelId, fidStr, iswifi,true, getContext());
        } else {
            postData = NewsListDataManager.getPostData(isMixed, allList, ChannelId, fidStr, iswifi,true, getContext());
        }
        LoaderStrategy taskLoader = CmsApplication.getDataStratey();

        Task task = new Task();
        task.setImgLoader(loader);
        task.setTag(url);
        newsLoaderManager.addTask(task);
        loader = new JsonLoader(getContext());
        loader.setMethod(BaseNetLoder.Method_Post);
        loader.setPostData(postData);
        loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());

//        ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
//        cm.setText(postData + "  "+ Constant.HEAD_NAME + " " + CmsApplication.getUserToken());

//        TLog.e("cms","aa----"+CmsApplication.getUserToken());
        loader.setUrl(url);
//        TLog.e("url", url);
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

                if (arg1 == 401) {
//                    ((BaseActivity) getActivity()).loadAnonymity(new BaseActivity.AnonymityLister() {
//                        @Override
//                        public void setAnonymityLister(UserInfoEntity userinfo) {
//                            if (userinfo != null) {
//                            }
//                        }
//                    }, true);

                    displayRefreshCount(0, true);
                    return;
                } else if (arg1 == 500) {
                    if (Utils.isStringEmpty(channelItems)) {

                    }
                }
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

                long value = System.currentTimeMillis() - start;
                ((BaseActivity) getContext()).addRequestTime(this.getClass().getName(), BaseAnalyticsActivity.Category_net, BaseAnalyticsActivity.Net_Article_Pull, value);
                try {
                    JSONArray array = (JSONArray) arg1;
                    articleEntity = NewsListDataManager.getParseData(array, isMixed, false, ChannelId,true,null);
                    if (articleEntity != null) {
                        TLog.e("list" + ChannelId, array.toString());
                        fidStr = articleEntity.getFidStr();
                        saveFid();
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
                    }
                }catch (Exception e){
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
                        ((BaseActivity)getActivity()).addEnvent(BaseAnalyticsActivity.Envent_Pull_Refresh);

                        pauseVideo();

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
                pauseVideo();
                loadCacheData();
            }
        });
        mListView.init();
        newsAdapter = new NewsRecyclerAdapter(getActivity(), mListView, true,
                CmsApplication.isGprs, true,fullScreenLin);
        mListView.setAdapter(newsAdapter);

        newsAdapter.setOnPlayerClickLisner(new NewsRecyclerAdapter.OnPlayerClickLisner() {
            @Override
            public void clickId(int postion) {
                currentVideo = postion;
            }
        });

        newsAdapter.setRecyclerViewItemClick(new BaseHolder.RecyclerViewItemClick() {

            @Override
            public void setOnItemClickLister(int position) {
                NewsListEntity entity = (NewsListEntity) newsAdapter.getNewsListEntity(position-1);
                IntentClassChangeUtils.startNewsDetail(getContext(),entity,position,0,false);

            }
        });

        newsAdapter.setOnEnterClickLisner2(new NewsRecyclerAdapter.OnEnterClickLisner2() {
            @Override
            public void click(int postion,int currenttime) {
                NewsListEntity entity = (NewsListEntity) newsAdapter.getNewsListEntity(postion);
                IntentClassChangeUtils.startNewsDetail(getContext(),entity,postion,currenttime,false);
            }
        });
        newsAdapter.setRefreshListOnclick(new NewsRefreshRender.RefreshListOnclick() {

            @Override
            public void setOnRefreshListLister() {
                setListRefreshing();
            }
        });

        mListView.addOnScrollListener(new VideoRecyclerViewListener());
    }


    class VideoRecyclerViewListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            RecyclerView.LayoutManager layoutManager1 = recyclerView.getLayoutManager();
            if (layoutManager1 instanceof LinearLayoutManager) {
                LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager1;
                //��ȡ���һ���ɼ�view��λ��
                int lastItemPosition = linearManager.findLastVisibleItemPosition();
                //��ȡ��һ���ɼ�view��λ��
                int firstItemPosition = linearManager.findFirstVisibleItemPosition();

//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    int size = newsAdapter.getData().size();
//                    if (size > lastItemPosition && size > firstItemPosition + 1) {
//                        List<NewsListEntity> listEntities = new ArrayList<>();
//                        int offset = (lastItemPosition - firstItemPosition) < 5 ? 0 : (lastItemPosition - firstItemPosition - 5);
////                        TLog.e("test",firstItemPosition+"========="+lastItemPosition+"=========="+offset);
//                        for (int i = 0; i < lastItemPosition - firstItemPosition - offset; i++) {
//                            listEntities.add(newsAdapter.getData().get(firstItemPosition + i + (offset / 2)));
//                        }
//                        loadWebText(listEntities);
//                    }
//                }

                if (currentVideo == -1 || currentVideo == -2){
                    return;
                }
                if (currentVideo < firstItemPosition-1 || currentVideo > lastItemPosition+1){
//                    TLog.e("realse","--------------scrll--"+firstItemPosition+"==="+lastItemPosition+"==="+currentVideo);
                    currentVideo = -1;
                    VideoPlayManager.getInstance().pausePlayVideo();
                }
            }
            super.onScrollStateChanged(recyclerView, newState);
        }
    }

    @Override
    public void onPause() {
        pauseVideo();
        super.onPause();
    }

    private void pauseVideo(){
        VideoPlayManager.getInstance().pausePlayVideo();
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
}
