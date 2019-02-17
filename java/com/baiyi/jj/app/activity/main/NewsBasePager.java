package com.baiyi.jj.app.activity.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.baiyi.core.loader.BaseLoader;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.LoaderStrategy;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.ChannelUtils;
import com.baiyi.jj.app.activity.EditionAct;
import com.baiyi.jj.app.activity.NewsDetailAct;
import com.baiyi.jj.app.activity.channelmanager.ChannelManager;
import com.baiyi.jj.app.activity.main.news.BaseHolder.RecyclerViewItemClick;
import com.baiyi.jj.app.activity.main.news.NewsRefreshRender.RefreshListOnclick;
import com.baiyi.jj.app.activity.main.task.NewsLoaderManager;
import com.baiyi.jj.app.activity.main.task.Task;
import com.baiyi.jj.app.activity.user.login.UsInfoCallBack;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.cache.Dao.ArticleCacheDao;
import com.baiyi.jj.app.cache.Dao.ArticleChannelDao;
import com.baiyi.jj.app.cache.Dao.ArticleDao;
import com.baiyi.jj.app.cache.Dao.ChannelTendDao;
import com.baiyi.jj.app.cache.Dao.EditionReadDao;
import com.baiyi.jj.app.cache.Dao.NewsListDao;
import com.baiyi.jj.app.cache.bean.EditionReadBean;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.entity.article.ArticleChannel;
import com.baiyi.jj.app.entity.article.ArticleEntity;
import com.baiyi.jj.app.entity.article.ArticleCacheEntity;
import com.baiyi.jj.app.entity.article.ArticleSeqEntity;
import com.baiyi.jj.app.utils.ArticleHistoryUtils;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.TagUtils;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.turbo.turbo.mexico.R;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;

import org.json.JSONArray;

import java.util.List;

/**
 * @author tangkun
 */
public abstract class NewsBasePager extends ChannelManager {

    private MyLoadingBar progressBar = null;
    private XRecyclerView mListView;
    private NewsRecyclerAdapter newsAdapter;
    private String postChannel = null;
    // �������а�
    private boolean isLoadMoreNet = false; //�����ж��ǲ��������а���,��Ҫ����page+1
    private boolean isDuanZiM = false; // �����ж��ǲ��Ƕ���

    private NewsLoaderManager newsLoaderManager = null;

//	private String newPostUrl = null;

    private OnRefreshComplete onComplete = null;
    private boolean isDisplayAbstract = false;

    /**
     * �ײ����� ����Ԫ��
     */
//    private List<ArticleCacheEntity> dataListArticleCache = new ArrayList<ArticleCacheEntity>();
    private int bottomPageIndex = 0;
    //��һ�ε��û���ʱ�������������һ��Ļ������ȡһ������
    private boolean isNextPage = true;

    private int FirstDownId = 0;

    /**
     * ��һ�б���Ԫ��
     */
//    private List<ArticleSeqEntity> channelArticleCache = new ArrayList<ArticleSeqEntity>();
    private int channelPageIndex = 0;
    private int duanziIndex = 1;
    private int videoIndex = 1;
    private int refreshType = LoadingBasePager.Refresh_Type_New;

    private ArticleEntity articleEntity = null;

    private String tablename = null;

    private String channelId;

    private int newsPageSize;

    private int channePageSize;

    private String fidStr = "";

//    private boolean isFirstLoad = true;

    /**
     * @param context
     * @param themeChangeCallBack
     * @param tableName
     * @param TabId
     * @param imgArrow
     * @param isDisplayAbstract
     */
    public NewsBasePager(Context context,
                         ThemeChangeCallBack themeChangeCallBack,
                         String tableName, int TabId, ImageView imgArrow, boolean isDisplayAbstract) {
        super(context, tableName, TabId, imgArrow);
        // TODO Auto-generated constructor stub
        this.isDisplayAbstract = isDisplayAbstract;
        this.newsLoaderManager = new NewsLoaderManager();
        this.tablename = tableName;
        initViews();
        initPage();
    }

    private void initViews() {

        initNewCont();
        initListView();
        progressBar = (MyLoadingBar) findViewById(R.id.loading);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        initData();
    }

    /**
     * ��ʼ��list ҳ��
     */
    public void initPage() {
        bottomPageIndex = 0;
        isNextPage = true;
        channelPageIndex = 0;
        duanziIndex = 1;
        videoIndex = 1;
    }

    /**
     * back
     */
    public void goRecomment() {
        setLoadMoreNet(false);
        backToAllList(getTitleName());
    }

    /**
     * ����listView�ײ���ʾ
     *
     * @param newsDataListSize
     */
    public void setListViewBottomViewVisible(int newsDataListSize) {
//		((BaseActivity) getContext()).setListView(mListView, newsDataListSize);
    }

    private void initListView() {
        mListView = (XRecyclerView) findViewById(R.id.news_feeds);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mListView.setLayoutManager(layoutManager);

        mListView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mListView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
//        mListView.setArrowImageView(R.drawable.iconfont_downgrey);

        mListView.setLoadingListener(new LoadingListener() {

            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (!isVisibleManager()) {
                            TLog.e("refr", "99999999999999");
                            loadChannelData(true);
                        }
                    }

                }, 2000);


            }

            @Override
            public void onLoadMore() {
                // TODO Auto-generated method stub
//                if (isLoadMoreNet()) {
//                    loadVideoData();
//                    return;
//                }
                if (isRecomment()) {
//                    if (Utils.isStringEmpty(dataListArticleCache)) {
                    if (bottomPageIndex >= newsPageSize) {
                        mListView.loadMoreComplete();
//						setListViewBottomViewVisible(0);
                        return;
                    }
                    loadCacheData();
                } else {
//                    Log.e("singel22",channePageSize+"===="+channelPageIndex);
//                    if (Utils.isStringEmpty(channelArticleCache)) {
                    if (channelPageIndex >= channePageSize) {
                        mListView.loadMoreComplete();
//						setListViewBottomViewVisible(0);
                        return;
                    }
                    loadCacheData();
                }
            }
        });
        newsAdapter = new NewsRecyclerAdapter((Activity) getContext(), mListView, isDisplayAbstract,
                CmsApplication.isGprs, true,null);
        mListView.setAdapter(newsAdapter);

        newsAdapter.setRecyclerViewItemClick(new RecyclerViewItemClick() {

            @Override
            public void setOnItemClickLister(int position) {

                // ���item����ת������ҳ
                NewsListEntity entity = (NewsListEntity) newsAdapter.getNewsListEntity(position - 1);

                if (entity.getType().equals(TagUtils.StorType_eidtion_top) || entity.getType().equals(TagUtils.StorType_eidtion)) {
                    Intent intent = new Intent(getContext(), EditionAct.class);
                    TLog.e("item", entity.getArticle_title() + "--" + entity.getArticle_title());
                    intent.putExtra(Define.ArticleId, entity.getArticle_id());
                    intent.putExtra(Define.IsPoint, entity.getType().equals(TagUtils.StorType_eidtion_top));
                    intent.putExtra(Define.ITEM_POSTION, (position - 1));
                    if (entity.getType().equals(TagUtils.StorType_eidtion)) {
                        new EditionReadDao(getContext()).add(new EditionReadBean(entity.getArticle_id()));
                    }
                    ((BaseActivity) getContext()).startActivityForResult(intent, 1);
                    return;
                }

                String img = "";
                if (!Utils.isStringEmpty(entity.toImgList())) {
                    img = entity.toImgList().get(0).getMedia_path();
                }

                Intent intent = new Intent(getContext(), NewsDetailAct.class);

                intent.putExtra(Define.ArticleId, entity.getArticle_id());
                intent.putExtra(Define.ArticleTitle, entity.getArticle_title());
                intent.putExtra(Define.ArticleType, ArticleHistoryUtils.getArticleType(getTableName()));
                intent.putExtra(Define.CommentNum, entity.getArticle_comment_num());
                intent.putExtra(Define.ArticleImage, img);
                intent.putExtra(Define.Detail_Result, true);
                intent.putExtra(Define.ITEM_POSTION, (position - 1));
                intent.putExtra(Define.ArticleUrl, entity.getPageUrl());
                intent.putExtra(Define.IsOrigin, entity.is_Original());
                intent.putExtra(Define.ArticleZT, !entity.getType().equals("-1"));

                ((BaseActivity) getContext()).startActivityForResult(intent, 1);
            }
        });

        newsAdapter.setRefreshListOnclick(new RefreshListOnclick() {

            @Override
            public void setOnRefreshListLister() {
                setListRefreshing();
            }
        });
    }


    /**
     * ��ȡListViewˢ��Post����
     */
    private void loadDataBaseArticleReq() {

        articleEntity = new ArticleDao(getContext()).getArticleEntity(isRecomment(), postChannel);

    }

    /**
     * ��ȡ���ݿ�Ԫ���б�
     */
    protected void loadDataBaseSearch() {

//        dataListArticleCache = new ArticleCacheDao(getContext()).queryByTablename(getTableName());

        int datasize = new NewsListDao(getContext()).getNewspagesize(true);
        newsPageSize = datasize / 10;
        if (newsPageSize > 0) {
            loadCacheData();
        } else {
            if (!isFY_FirstDayTimes()) {
                setListRefreshing();
            }
        }

    }

    public void setChannelSeq(String channelSeq) {
        this.channelId = channelSeq;
    }

    public String getChannelId() {
        return channelId;
    }

    /**
     * ��ȡ���ݿ�Ԫ���б�
     */
    protected void loadDataBaseSingleSearch() {

        int datasize = new NewsListDao(getContext()).getPagesizebyChannel(getChannelId());
        channePageSize = datasize / 10;
    }

    private void loadCacheData() {

        if (!isRecomment()) {
            loadSingleCacheData();
        } else {
            loadRecomData();
        }
    }


    private void loadRecomData() {
        if (bottomPageIndex >= newsPageSize) {
            mListView.loadMoreComplete();
            return;
        }
        List<NewsListEntity> listEntities = new NewsListDao(getContext()).getNewsByPage(bottomPageIndex, FirstDownId,true);
        if (bottomPageIndex == 0 && !Utils.isStringEmpty(listEntities)) {
            FirstDownId = listEntities.get(0).getSqlId();
        }

        if (Utils.isStringEmpty(listEntities)) {
            setListViewBottomViewVisible(0);
            mListView.loadMoreComplete();
            mListView.refreshComplete();
            return;
        }
        disCacheData(listEntities);

    }

    private void disCacheData(List<NewsListEntity> newsList) {
        if (Utils.isStringEmpty(newsAdapter.getData())) {
            refreshType = LoadingBasePager.Refresh_Type_New;
            if (isRecomment()) {
                bottomPageIndex++;
            } else {
                channelPageIndex++;
            }

        } else {
            refreshType = LoadingBasePager.Refresh_Type_More;
        }

        displayList(newsList, false);
    }

    private void loadSingleCacheData() {
        if (channelPageIndex >= channePageSize) {
            mListView.loadMoreComplete();
            return;
        }
        List<NewsListEntity> listEntities = new NewsListDao(getContext()).getNewsByChannidByPage(getChannelId(), bottomPageIndex, FirstDownId);
        if (channelPageIndex == 0 && !Utils.isStringEmpty(listEntities)) {
            FirstDownId = listEntities.get(0).getSqlId();
        }
        if (Utils.isStringEmpty(listEntities)) {
            setListViewBottomViewVisible(0);
            mListView.loadMoreComplete();
            mListView.refreshComplete();
            return;
        }
        disCacheData(listEntities);

    }


    /**
     * �ӵ�һ�б��ص��ۺ��б�
     */
    public void backToAllList(String titleName) {
        if (listChanged != null) {
            listChanged.changeList(0, titleName,
                    View.GONE);
        }

        initPage();
        isLoadMoreNet = false;
        isDuanZiM = false;
        FirstDownId = 0;
        refreshType = LoadingBasePager.Refresh_Type_New;
        newsAdapter.clear();
        setRecomment(true);
        mListView.init();
        loadDataBaseSearch();
        loadDataBaseArticleReq();
        setListRefreshing();
    }

    /**
     * ���ۺ��б�������һ�б�
     *
     * @param channelName
     */
    public void goToSingleList(String channelName, String channelid) {

        if (listChanged != null) {
            listChanged.changeList(0, channelName,
                    View.VISIBLE);
        }

        initPage();
        refreshType = LoadingBasePager.Refresh_Type_New;
        isLoadMoreNet = false;
        postChannel = channelid;
        FirstDownId = 0;
        setRecomment(false);
        mListView.init();
        loadDataBaseArticleReq();
        setListRefreshing();

        /**
         *  ��ȡ��һ�б�����
         */
        channelPageIndex = 0;
        setChannelSeq(channelid);
        loadDataBaseSingleSearch();
    }

    public void setListChanged(OnListChanged listChanged) {
        this.listChanged = listChanged;
    }

    /**
     * �Ƿ��Ƕ���Ƶ��
     *
     * @return
     */
    public boolean isLoadMoreNet() {
        return isLoadMoreNet;
    }

    public void setLoadMoreNet(boolean isLoadMoreNet) {

        if (isLoadMoreNet) {
            refreshType = LoadingBasePager.Refresh_Type_New;
            duanziIndex = 1;
            mListView.init();
        }
        this.isLoadMoreNet = isLoadMoreNet;
    }

    public boolean isDuanZiM() {
        return isDuanZiM;
    }

    public String getCurrentChannel() {
        return postChannel;
    }

    @Override
    public void setTableName(String tableName) {
        // TODO Auto-generated method stub
//		if (tableName.equals(ArticleHistoryUtils.Tablename_Video)) {
//			refreshType = LoadingBasePager.Refresh_Type_New;
//			videoIndex = 1;
//			mListView.init();
//		}
        super.setTableName(tableName);
    }

    private void initData() {

        loadDataBaseArticleReq();
        loadDataBaseSearch();
//        if (!isFY_FirstDayTimes()) {
//        if (RefreshManager.getInstance().isRefresh(System.currentTimeMillis())) {
        // 三小时内第一次登陆调用
        if (!isFY_FirstDayTimes()) {
//            setListRefreshing();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onRefreshListView();
//                   setListRefreshing();
                }
            }, 1000);
//            onRefreshListView();
//            loadChannelData(true);
//            mListView.smoothScrollToPosition(0);
//            mListView.refresh();
        } else {
        }
//            RefreshManager.getInstance().setLastTime(System.currentTimeMillis());
//        } else {
//
//        }

    }

    @Override
    public void loadArticleData(String mid, List<ChannelItem> userChannelList) {
        // TODO Auto-generated method stub
        if (Utils.isStringEmpty(userChannelList)) {
            mListView.refreshComplete();
            if (onComplete != null) {
                onComplete.OnClick();
            }
            stopLoading();
            setVisibleChannelManager(View.VISIBLE, false);
            return;
        }

//        String url = getDataUrl(isRecomment(), isLoadMoreNet(), mid,
//                !isLoadMoreNet ? 1 : duanziIndex, postChannel);
//        List<ArticleChannel> articleChannelList = new ChannelTendDao(getContext()).getChannelList(ChannelDataUtils.getChannelType(getTableName()));
//        String postData = getPostData(isRecomment(), isLoadMoreNet(), mid, articleEntity, articleChannelList, userChannelList, postChannel, fidStr);
//        loadData(url, postData);

    }

    /**
     * ��ȡ��Ѷ�б�
     *
     * @param mid
     * @param page
     * @param isFirstRefresh
     */
    private JsonLoader loader = null;

    /**
     * ���������б�����
     *
     * @param url
     * @param postData
     */
    private void loadData(final String url, String postData) {
        LoaderStrategy taskLoader = CmsApplication.getDataStratey();

        Task task = new Task();
        task.setImgLoader(loader);
        task.setTag(url);
        newsLoaderManager.addTask(task);

        startLoading();
        loader = new JsonLoader(getContext());

        if (isLoadMoreNet()) {
            loader.setMethod(BaseNetLoder.Method_Get);
        } else {
            loader.setMethod(BaseNetLoder.Method_Post);
            loader.setPostData(postData);
            loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
        }
        loader.setUrl(url);
        TLog.e("url", url);
        loader.setLoaderListener(new LoaderListener() {

            public void onProgress(Object arg0, long arg1, long arg2) {

            }

            public void onError(Object arg0, int arg1, String arg2) {

                mListView.refreshComplete();
                if (onComplete != null) {
                    onComplete.OnClick();
                }
                stopLoading();
                progressBar.setVisibility(View.GONE);
                progressBar.stop();

                if (arg1 == 401) {
                    new UsInfoCallBack(getContext(), null).refreshToken(new UsInfoCallBack.RefreshTComplete() {
                        @Override
                        public void refreshComplete(boolean isSuccess, String token) {
                            if (isSuccess) {
                            }
                        }
                    });
                    displayRefreshCount(0, true);
                    return;
                }

                TLog.e("listerror", arg1 + "=======");
                if (arg1 == BaseLoader.Result_Code_NetTimeOut) {
                    displayRefreshCount(0, true);
                } else {
                    ((BaseActivity) getContext()).displayToast(getContext().getString(R.string.tip_loaddata_timeout));
                }
//                if (isFirstLoad){
//                    loadCacheData();
//                }
                setListViewBottomViewVisible(0);

            }

            public void onCompelete(Object arg0, Object arg1) {
                stopLoading();
                progressBar.setVisibility(View.GONE);
                if (onComplete != null) {
                    onComplete.OnClick();
                }
                // if(newsLoaderManager.isHasTask())
                // {
                // return;
                // }
                JSONArray array = (JSONArray) arg1;
                TLog.e("list", array.toString());
                articleEntity = getParseData(array, isRecomment, isLoadMoreNet(), false);
                if (articleEntity != null) {
                    TLog.e("articlet", articleEntity.getFidStr());
                    fidStr = articleEntity.getFidStr();
                    if (isLoadMoreNet) {
                        displayDZList(articleEntity.toNewsList());
                        return;
                    }
//                    if (Utils.isStringEmpty(articleEntity.toNewsList())){
//                        mListView.loadMoreComplete();
//                    mListView.refreshComplete();
//                        return;}
                    displayList(articleEntity.toNewsList(), true);
                } else {
//                    if (isFirstLoad){
//                        loadCacheData();
//                    }
                }
//                isFirstLoad = false;

            }
        });

        taskLoader.startLoader(loader);
    }

    /**
     * ��ʾ�б�
     *
     * @param newsList
     * @param isSaveCache
     */
    private void displayList(List<NewsListEntity> newsList, boolean isSaveCache) {

//		if (Utils.isStringEmpty(newsList) && Utils.isStringEmpty(dataListArticleCache))) {
//
//			List<NewsListEntity> newList = new ArrayList<NewsListEntity>();
//			mListView.setAdapter(newsAdapter);
//			newsAdapter.setData(newList);
//			progressBar.stop();
//			progressBar.setVisibility(View.VISIBLE);
//			progressBar.setProgressLoadError(getResources()
//					.getString(R.string.tip_loaddata_null),
//					MyLoadingBar.type_sample);
//			((BaseActivity) getContext()).setListView(mListView, newsList.size());
//			return;
//		}

//		saveDataBaseNewsList(newsList);
        saveCacheDataBase(newsList, isSaveCache);
        saveReqDataBase(isSaveCache);


        if (refreshType == LoadingBasePager.Refresh_Type_Pull) {
            if (!Utils.isStringEmpty(newsAdapter.getData())) {
                NewsListEntity topNews = newsAdapter.getData().get(0);
                if (topNews.getType().equals(TagUtils.StorType_eidtion_top) && !Utils.isStringEmpty(newsList)) {
                    newsAdapter.remove(0);
                    NewsListEntity newTop = newsList.get(0);
                    TLog.e("newtop", newTop.getType() + "---999--" + newTop.getArticle_title());
                    if (!newTop.getType().equals(TagUtils.StorType_eidtion_top)) {
                        newsList.add(0, topNews);
                    }

                }
            }
            newsAdapter.addPullData(newsList);
            if (isSaveCache) {
                displayRefreshCount(Utils.isStringEmpty(newsList) ? 0 : newsList.size(), false);
            }
        } else if (refreshType == LoadingBasePager.Refresh_Type_New) {
            newsAdapter.setData(newsList);
            refreshType = LoadingBasePager.Refresh_Type_Pull;
            if (isSaveCache) {
                displayRefreshCount(Utils.isStringEmpty(newsList) ? 0 : newsList.size(), false);
            }
//            if (isFirstLoad){
//                loadCacheData();
//            }
        } else if (refreshType == LoadingBasePager.Refresh_Type_More) {
            newsAdapter.addData(newsList);
            if (isRecomment()) {
                bottomPageIndex++;
            } else {
                channelPageIndex++;
            }
            refreshType = LoadingBasePager.Refresh_Type_Pull;
        }
        // �����з���ǰ���Ӱ��getLayoutManager().getItemCount()������
        if (!isSaveCache) {
            mListView.loadMoreComplete();
        }
        mListView.refreshComplete();
    }

    private void displayDZList(List<NewsListEntity> newsList) {

        if (refreshType == LoadingBasePager.Refresh_Type_New) {
            duanziIndex++;
            newsAdapter.setData(newsList);
            refreshType = LoadingBasePager.Refresh_Type_More;

        } else if (refreshType == LoadingBasePager.Refresh_Type_More) {
            newsAdapter.addDataDz(newsList);
            duanziIndex++;
            refreshType = LoadingBasePager.Refresh_Type_More;
        }
        // �����з���ǰ���Ӱ��getLayoutManager().getItemCount()������
        mListView.loadMoreComplete();
        mListView.refreshComplete();

    }

    /**
     * �Ƿ���Ҫ���汾��
     * 1.�´�����������
     * 2.�б�����
     *
     * @param isSaveCache
     * @return
     */
    public boolean isSaveCache(boolean isSaveCache) {
        if (articleEntity == null) {
            return false;
        }
        if (refreshType == LoadingBasePager.Refresh_Type_More) {
            return false;
        }
        if (!isSaveCache) {
            return false;
        }
        return true;
    }

    /**
     * ���ر��� �´�ˢ����������
     *
     * @param isSaveCache
     */
    private void saveReqDataBase(boolean isSaveCache) {
        if (!isSaveCache(isSaveCache)) {
            return;
        }
        articleEntity.setTableName(getTableName());

        new ArticleDao(getContext()).insertOrUpdateArticle(articleEntity, isRecomment(), postChannel);

        if (Utils.isStringEmpty(articleEntity.toArticleChannelList())) {
            return;
        }
        new ChannelTendDao(getContext()).insertOrUpdateArticle(articleEntity.toArticleChannelList());

    }


    /**
     * �����б�����
     *
     * @param newsList
     * @param isSaveCache
     */
    private void saveCacheDataBase(final List<NewsListEntity> newsList, boolean isSaveCache) {
//		if(!isRecomment())
//		{
//			return;
//		}
        if (Utils.isStringEmpty(newsList)) {
            return;
        }
        if (refreshType == LoadingBasePager.Refresh_Type_More) {
            return;
        }
        if (!isSaveCache) {
            return;
        }

        if (!isRecomment()) {
            ArticleSeqEntity entity = new ArticleSeqEntity();
            entity.setTime(System.currentTimeMillis());
            entity.setChannelseq(postChannel);
            new ArticleChannelDao(getContext()).add(entity);
            saveDataBaseNewsList(newsList, null, entity);

        } else {
            ArticleCacheEntity entity = new ArticleCacheEntity();
            entity.setTableName(getTableName());
            entity.setTime(System.currentTimeMillis());
//            ArticleCacheLoader loader = new ArticleCacheLoader();

            new ArticleCacheDao(getContext()).add(entity);
            saveDataBaseNewsList(newsList, entity, null);

        }


    }

    /**
     * ���� news�б�
     *
     * @param newsList
     * @param entity
     */
    private void saveDataBaseNewsList(List<NewsListEntity> newsList, ArticleCacheEntity entity, ArticleSeqEntity entity2) {
        if (entity != null) {
            for (NewsListEntity item : newsList) {
                item.setDownLoadTime(entity.getTime());
                item.setTablename(entity.getTableName());
            }
        } else {
            for (NewsListEntity item : newsList) {
                item.setDownLoadTime(entity2.getTime());
                item.setTablename(entity2.getChannelseq());
            }
        }

//        ArticleLoader loader = new ArticleLoader();
//        loader.setInsertList(newsList);
//        CmsApplication.getDataStratey().startLoader(loader);

        new NewsListDao(getContext()).add(newsList);
    }

    @Override
    public void onItemClilck(ChannelItem entity) {
        // TODO Auto-generated method stub
        if (!Utils.isStringEmpty(entity.getChannel_name())
                && entity.getChannel_name().equals(getResources().getString(R.string.name_comprehensive))) {
            backToAllList(getTitleName());
            return;
        }
        if (Utils.isStringEmpty(entity.getCid())) {
            return;
        }
        goToSingleList(entity.getChannel_name(), entity.getCid());

    }

    /**
     * ��ȡ��ʾtitle
     *
     * @return
     */
    private String getTileText() {
        String text = "";
//        if (getTableName().equals(ArticleHistoryUtils.Tablename_Photo)) {
//            // ����
//        } else if (getTableName().equals(ArticleHistoryUtils.Tablename_Read)) {
//            // ֪ʶ
//            text = "֪ʶ";
//        } else if (getTableName().equals(ArticleHistoryUtils.Tablename_News)) {
//            // ��ѯ
//            text = "��Ѷ";
//        } else if (getTableName()
//                .equals(ArticleHistoryUtils.Tablename_Interest)) {
//            // Ȥζ
//        }
        return text;
    }

    @Override
    public void onRefreshListView() {
        // TODO Auto-generated method stub
        super.onRefreshListView();
        startLoading();
        setListRefreshing();
        // loadDataToUser(LoadingBasePager.Refresh_Type_New);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (data == null) {
            return;
        }

        if (resultCode == ChannelUtils.Result_ChannelSigle) {
            ChannelItem item = (ChannelItem) data.getSerializableExtra(Define.ChannelName);
            goToSingleList(item.getChannel_name(), item.getCid());
            return;
        }

        List<ChannelItem> lItems = (List<ChannelItem>) data
                .getSerializableExtra(Define.ChannelId);

        if (resultCode == ChannelUtils.Result_News) {
            // // ��Ƶ������ص���Ѷ�б����
            if (data.getBooleanExtra(Define.IsChannelSingle, false)) {
                // ����һ�б�
                goToSingleList(lItems.get(0)
                        .getChannel_name() + getTileText(), lItems.get(0)
                        .getChannel_seq());
            } else {
                // ���ۺ��б�
                backToAllList(getTitleName());
            }

        } else if (resultCode == ChannelUtils.Result_Read) {
            // ��Ƶ������ص���Ѷ�б����
            if (data.getBooleanExtra(Define.IsChannelSingle, false)) {
                // ����һ�б�
                goToSingleList(lItems.get(0)
                        .getChannel_name() + getTileText(), lItems.get(0)
                        .getChannel_seq());
            } else {
                // ���ۺ��б�
                backToAllList(getTitleName());
            }
        } else if (resultCode == ChannelUtils.Result_Detail_Comment) {
            int comNum = data.getIntExtra(Define.CommentNum, -1);
            if (comNum != -1) {
                int position = data.getIntExtra(Define.ITEM_POSTION, -1);
                if (position != -1) {
                    newsAdapter.refreshItem2Com(position, comNum);
                }
            }
            int oppoNum = data.getIntExtra(Define.AriticleOppoNum, -1);
            int suppNum = data.getIntExtra(Define.AriticleSuppNum, -1);
            if (oppoNum != 0 || suppNum != 0) {
                int position = data.getIntExtra(Define.ITEM_POSTION, -1);
                if (position != -1) {
                    newsAdapter.refreshItemOppoNum(position, oppoNum, suppNum);
                }
            }
            int playNum = data.getIntExtra(Define.VideoPlayNum, -1);
            if (playNum != 0 && playNum != -1) {
                int position = data.getIntExtra(Define.ITEM_POSTION, -1);
                if (position != -1) {
                    newsAdapter.refreshItemPlayNum(position, playNum);
                }
            }
        }
    }

    @Override
    public void onRefresh(boolean isFontChange) {
        // TODO Auto-generated method stub
        super.onRefresh(isFontChange);
        if (isFontChange) {
            refreshList();
        }
    }

    @Override
    public void onRefreshToNetWorkChange(boolean isGprs) {
        // TODO Auto-generated method stub
        super.onRefreshToNetWorkChange(isGprs);
        // ����仯ʱ �л��б���ʾ
        newsAdapter.changeItem(isGprs);
    }

    /**
     * ����ˢ��ListView
     */
    public void refreshList() {
        if (newsAdapter != null) {
            newsAdapter.refresh();
        }
    }

    /**
     * ���ListVIew
     */
    public void clearDataList() {
        if (newsAdapter != null) {
            newsAdapter.clear();
        }
    }

    /**
     * ListView pull����
     */
    public void setListRefreshing() {
        if (!mListView.isRefreshing()) {
            loadChannelData(true);
            mListView.smoothScrollToPosition(0);
            mListView.refresh();
        }
//		if (mListView.isRefreshing()) {
//			loadChannelData(true);
//		} else {
//			mListView.setRefreshing();
//			// mlist.setSelection(0);
//		}
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (newsLoaderManager != null) {
            newsLoaderManager.clear();
            newsLoaderManager = null;
        }
    }

    /**
     * @param isRecomment true:�Ƽ����ۺ����� false����һ����
     * @param isDuanzi    �Ƿ�Ϊ����Ƶ��
     * @param mid
     * @param page
     * @param postChannel ��һƵ��ID
     * @return
     */
    public abstract String getDataUrl(boolean isRecomment, boolean isDuanzi,
                                      String mid, int page, String postChannel);

    public abstract String getPostData(boolean isRecomment, boolean isDuanzi, String mid,
                                       ArticleEntity articleEntity, List<ArticleChannel> aChannelList,
                                       List<ChannelItem> userChannelList, String channelId, String fid);

    /**
     * ���� ����List����
     *
     * @return
     */
    public abstract ArticleEntity getParseData(JSONArray array,
                                               boolean isRecomment, boolean isDuanzi, boolean isCache);

    /**
     * �ص�titleName
     *
     * @return
     */
    public abstract String getTitleName();

    public abstract String getFirstTimeXmlName();

    public void setOnComplete(OnRefreshComplete onComplete) {
        this.onComplete = onComplete;
    }


    public interface OnRefreshComplete {
        public void OnClick();
    }


}