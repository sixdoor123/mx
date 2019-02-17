package com.baiyi.jj.app.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baiyi.core.file.Preference;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.activity.main.NewsRecyclerAdapter;
import com.baiyi.jj.app.adapter.NewsLocalAdapter;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.cache.Dao.WebDetailDao;
import com.baiyi.jj.app.cache.bean.WebDetailBean;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.entity.localnews.AdsEntity;
import com.baiyi.jj.app.entity.localnews.LocalNewsBean;
import com.baiyi.jj.app.entity.localnews.LocalInfoEntity;
import com.baiyi.jj.app.entity.localnews.LocalVideoEntity;
import com.baiyi.jj.app.entity.localnews.LocationEntity;
import com.baiyi.jj.app.manager.AdsManager;
import com.baiyi.jj.app.manager.CountryMannager;
import com.baiyi.jj.app.manager.VideoPlayManager;
import com.baiyi.jj.app.manager.WebViewManager;
import com.baiyi.jj.app.theme.FontUtil;
import com.baiyi.jj.app.utils.DateUtils;
import com.baiyi.jj.app.utils.IntentDefine;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.TextLengthUtils;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.FontSetView;
import com.baiyi.jj.app.views.video.VideoPlayView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.turbo.turbo.mexico.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
public class NewsLocalAct extends BaseNewsDetailAct{

    @Bind(R.id.lin_article_null)
    LinearLayout xLinArticleNull;
    @Bind(R.id.gif_list)
    XRecyclerView xRecyclerView;
    @Bind(R.id.item_gif_list)
    LinearLayout linContParent;
    @Bind(R.id.news_detail_local)
    WebView webViewLocal;
    @Bind(R.id.lin_quick_view)
    LinearLayout linQuickView;
    @Bind(R.id.rg_news_top)
    RadioGroup RgTopWebChange;
    @Bind(R.id.fullscreen_lin)
    LinearLayout fullscreenView;

    private FontSetView setViewWeb = null;
    private String[] webStr = new String[2];

    TextView headTitle;
    TextView headSource;
    NewsLocalAdapter adapter;


    private LocalInfoEntity localInfoEntity;
    private List<String> allImageList;

    long start;
    boolean isLoadWeb = true;

    private int currentPosition = -1;
    private boolean isPlaying = false;
    private VideoPlayView playView;

    public void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(true, true);
        linContParent.setVisibility(View.VISIBLE);
    }

    private void loadData() {
//        String content = TextLengthUtils.getAssetsStr(this, "detailtxt");

//        ArticleId = "350420";
        start = System.currentTimeMillis();
//        ArticleId = "322190";
        WebDetailDao webDetailDao = new WebDetailDao(this);
        WebDetailBean bean = webDetailDao.getDetaiText(ArticleId);
//        TLog.e("json-" +  ArticleId, "----"+bean.getWebcontent());
        if (!webDetailDao.isLoaded(ArticleId)) {
            startProgress();
            loadLocalText();


            return;
        }
        TLog.e("json-" + ArticleId, bean.getWebcontent());
        String content = bean.getWebcontent();

        setListData(content, true);
//        setListData(detailtxt, true);

    }



    private void setListData(String detailtxt, boolean isloadAgain) {

        localInfoEntity = JsonParse.getDetailList(detailtxt);
        if (localInfoEntity == null) {
            xRecyclerView.setVisibility(View.GONE);
            xLinArticleNull.setVisibility(View.VISIBLE);
            initWeb(true);
            return;
        }
        if (Utils.isStringEmpty(localInfoEntity.getLocalNewsBeanList())){
            xRecyclerView.setVisibility(View.GONE);
            xLinArticleNull.setVisibility(View.VISIBLE);
            initWeb(true);
            return;
        }

        if (localInfoEntity.isNewsApi().equals("1") || localInfoEntity.isNewsApi().equals("true")){
            initWeb(false);
            showNewsApiView();
        }

        List<LocalNewsBean> delBeanList = localInfoEntity.getLocalNewsBeanList();
        if (Utils.isStringEmpty(delBeanList) && isloadAgain) {
            WebDetailDao webDetailDao = new WebDetailDao(NewsLocalAct.this);
            WebDetailBean bean = webDetailDao.getDetaiText(ArticleId);
            webDetailDao.delete(bean);
            loadLocalText();
            return;
        }

        allImageList = localInfoEntity.getAllImgList();
//        TLog.e("size",delBeanList.size()+"+============"+allImageList.size());
        String ss = "Im sure she said ‘listen, this is horrible stuff.";
        headTitle.setText(localInfoEntity.getTitle());
        Typeface detailTItle = Typeface.createFromAsset(getAssets(), "fonts/detail_black.ttf");
        Typeface detailSorce = Typeface.createFromAsset(getAssets(), "fonts/detail_bold.ttf");
        headTitle.setTypeface( detailTItle);
        float textSize = FontUtil.getNewsDetailTitleSize();
        headTitle.setLineSpacing(FontUtil.getLineSpaceNews(),1);
        headTitle.setTextSize(textSize);
        String time = DateUtils.getTimeYMDHMS(DateUtils.getTimeSecond(localInfoEntity.getDate(), true));
//        if (is6){
//            time = DateUtils.getTimeYMDHMS(System.currentTimeMillis());
//        }else{
//        time = DateUtils.getTimeYMDHMS(DateUtils.getTimeSecond(localInfoEntity.getDate(), true));
//        }
        headSource.setText(localInfoEntity.getSource() +" "+getResources().getString(R.string.txt_point)+" " + time);
        headSource.setTypeface(detailSorce);
        adapter.setDatas(delBeanList, localInfoEntity.getType());

        if (!isGif) {
            mLinBottom.setVisibility(View.VISIBLE);
        }else{
            mLinBottom.setVisibility(View.GONE);
        }
        mLinAd.setVisibility(View.VISIBLE);

        long value = System.currentTimeMillis() - start;
        addRequestTime(this.getClass().getName(), BaseAnalyticsActivity.Category_web, BaseAnalyticsActivity.Web_Load_DetailContent, value);

        showAds();
    }

    private void showAds(){
        if (Utils.isStringEmpty(localInfoEntity.getAdsList())){
            return;
        }
        AdsEntity adsEntity = localInfoEntity.getAdsList().get(0);
        if (Utils.isStringEmpty(adsEntity.getAdSource())){
            setGoogleAd(AdsManager.AD_TYPE_Large);
            return;
        }
        if (adsEntity.getAdSource().equals("facebook")){
            setFBAd(adsEntity.getUnitType());
        }else{
            setGoogleAd(adsEntity.getUnitType());
        }
    }

    private void loadLocalText() {
        final long starttime = System.currentTimeMillis();
        WebViewManager.loadLocalDetail(null, NewsLocalAct.this, ArticleId, new WebViewManager.LoadWebComplete() {
            @Override
            public void loadComplete(final WebDetailBean beans) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        stopProgress();
                        long value = System.currentTimeMillis()-starttime;
                        addRequestTime(this.getClass().getName(), BaseAnalyticsActivity.Category_web, BaseAnalyticsActivity.Web_Load_Detail_Interface, value);
                        setListData(beans.getWebcontent(), false);
                    }
                });
            }
        });
    }


    @Override
    public void initContent() {

        getIntentData();
        initTopWeb();
        initAdapter();
        loadData();


//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//            }
//        }, 300);

    }

    private void getIntentData(){
        Intent intent = getIntent();//在这个Activity里，我们可以通过getIntent()，来获取外部跳转传过来的信息。
        String data = intent.getDataString();//接收到网页传过来的数据：sharetest://data/http://www.huxiu.com/
        if (Utils.isStringEmpty(data)){
            return;
        }
        TLog.e("data",data+"====");
        if (data.contains("sharecallback")){
            String aid = data.substring(data.lastIndexOf("=")+1);
            if (Utils.isStringEmpty(aid)){
                return;
            }
            ArticleId = aid;
            TLog.e("artilc",ArticleId+"--------");
        }
//        String[] split = data.split("data/");//以data/切割data字符串
    }

    private void initTopWeb(){
        webStr[0] = getResources().getString(R.string.tip_title_speedy);
        webStr[1] = getResources().getString(R.string.tip_title_web);
        setViewWeb = new FontSetView(this, RgTopWebChange, webStr, 0,true);
        RgTopWebChange.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.id_rb_0){
//                    TLog.e("check","-------------1");
                    showSpeedView();
                }else {
//                    TLog.e("check","-------------2");
                    showWebView();
                }

            }
        });

        linQuickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RgTopWebChange.check(R.id.id_rb_1);
                linQuickView.setVisibility(View.GONE);

            }
        });
    }
    private void showNewsApiView(){
        RgTopWebChange.setVisibility(View.VISIBLE);
        linQuickView.setVisibility(View.VISIBLE);
//        imgSeeSource.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable(){
            public void run() {
                //execute the task
                linQuickView.setVisibility(View.GONE);
            }
        }, 3000);
        showSpeedView();

    }

    private void showWebView(){
        xRecyclerView.setVisibility(View.GONE);
        webViewLocal.setVisibility(View.VISIBLE);
        mLinAd.setVisibility(View.GONE);
        mLinBottom.setVisibility(View.GONE);
        start = System.currentTimeMillis();
        if (isLoadWeb){
//            PageUrl = "https://www.facebook.com/nelos26/videos/10213187497666076/";
            webViewLocal.loadUrl(PageUrl);
        }
        if (!isLoadComplete){
            startProgress();
        }
        isLoadWeb = false;

    }
    private void showSpeedView(){
        xRecyclerView.setVisibility(View.VISIBLE);
        webViewLocal.setVisibility(View.GONE);
        mLinAd.setVisibility(View.VISIBLE);
        mLinBottom.setVisibility(View.VISIBLE);
        stopProgress();
//        webViewLocal.stopLoading();
    }


    private void initAdapter() {
        adapter = new NewsLocalAdapter(this);
        //���������������дcanScrollVertically()����������false

        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        ;
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setAdapter(adapter);
        adapter.setImageClickListner(new NewsLocalAdapter.OnImageClickListner() {
            @Override
            public void clickImage(int index, boolean isglide) {
                if (Utils.isStringEmpty(allImageList)) {
                    return;
                }
                Intent intent = new Intent(NewsLocalAct.this, ImageContainerActivity.class);
                intent.putExtra(IntentDefine.PicEntity, (Serializable) allImageList);
                intent.putExtra(IntentDefine.Start_Img_Index, index);
                intent.putExtra(IntentDefine.IMAGE_TYPE, isglide);
                startActivity(intent);
            }
        });
        adapter.setOnPlayerClickLisner(new NewsLocalAdapter.OnPlayerClickLisner() {
            @Override
            public void clickId(int postion) {
                currentVideo = postion;
            }
        });
        xRecyclerView.addOnScrollListener(new VideoRecyclerViewListener());

        adapter.setOnMp4PlayerClickLisner(new NewsLocalAdapter.OnMp4PlayerClickLisner() {
            @Override
            public void clickId(int curentposition, View conventView, VideoPlayView playView) {
                currentPosition = curentposition;
//                currentItemView = conventView;
                isPlaying = true;
                setPlayView(playView);
            }
        });
        adapter.setOnPlayError(new NewsLocalAdapter.onPlayError() {
            @Override
            public void onError() {
                closeVideo();
            }
        });


        initHeadView();
        setWebTextSize(0);
    }

    int currentVideo = -1;
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

                if ((currentPosition < firstItemPosition || currentPosition > lastItemPosition) && isPlaying && getResources().getConfiguration().orientation
                        == Configuration.ORIENTATION_PORTRAIT) {
                    closeVideo();
                }

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

    private void initHeadView() {
        View headview = LayoutInflater.from(this).inflate(R.layout.head_localnews, null);
        headTitle = (TextView) headview.findViewById(R.id.local_title);
        headSource = (TextView) headview.findViewById(R.id.local_time);

        xRecyclerView.addHeaderView(headview);
    }

    @Override
    public void setWebTextSize(int a) {
        if (adapter != null) {
            adapter.refresh();
            headTitle.setTextSize(FontUtil.getNewsDetailTitleSize());
        }
    }

    public void initWeb(boolean isLoadWeb) {

        if (isLoadWeb){
            webViewLocal.setVisibility(View.VISIBLE);
        }
        final WebSettings seting2 = webViewLocal.getSettings();
        seting2.setJavaScriptEnabled(true);
        seting2.setAllowFileAccess(true);
        seting2.setSavePassword(false);
        seting2.setBuiltInZoomControls(false);

        if (ContextUtil.isNetWorking(NewsLocalAct.this)) {
            seting2.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            seting2.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webViewLocal.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                stopProgress();
                isLoadComplete = true;
                super.onReceivedError(view, request, error);
            }


            @JavascriptInterface
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // setWebTheme(ThemeUtil.getAppTheme());
                stopProgress();
                isLoadComplete = true;
                seting2.setBlockNetworkImage(false);
                long value = System.currentTimeMillis() - start;
                addRequestTime(this.getClass().getName(), BaseAnalyticsActivity.Category_web, BaseAnalyticsActivity.Web_Load_DetailPageUrl, value);
            }
        });
        if (Utils.isStringEmpty(PageUrl)){
            return;
        }
        if (isLoadWeb){
            startProgress();
            webViewLocal.loadUrl(PageUrl);
        }
//        TLog.e("pageurl",PageUrl);
//        webViewLocal.loadUrl(PageUrl);

    }

    public boolean isBlackBarText() {
        return true;
    }

    private void closeVideo() {
        currentPosition = -1;
        adapter.setCurrentPosition(currentPosition);
        isPlaying = false;
        playView.stop();
        adapter.notifyDataSetChanged();
        playView = null;
//        currentItemView = null;
    }

    public void setPlayView(VideoPlayView playView) {
        if (this.playView != null && this.playView.isPlaying()){
            this.playView.pause();
        }
        this.playView = playView;
//        TLog.e("taf","curent---"+playView.getmCurrentState());
        if (playView.getmCurrentState() > 3)
        {
            this.playView.play();
        }else {
            this.playView.openVideo();
        }
    }

    //    private void onPlayClick(int position, RelativeLayout image){
//        image.setVisibility(View.GONE);
//        if (player.isPlaying() && lastPostion == position){
//            return;
//        }
//
//        postion = position;
//        if (player.getVideoStatus() == IjkVideoView.STATE_PAUSED) {
//            if (position != lastPostion) {
//                player.stopPlayVideo();
//                player.release();
//            }
//        }
//        if (lastPostion != -1) {
//            player.showView(R.id.adapter_player_control);
//        }
//
//        View view = xRecyclerView.findViewHolderForAdapterPosition(position).itemView;
//        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.adapter_super_video);
//        frameLayout.removeAllViews();
//        player.showView(R.id.adapter_player_control);
//        frameLayout.addView(player);
//        List<LocalNewsBean> localNewsBeanList = localInfoEntity.getLocalNewsBeanList();
//        LocalVideoEntity entity = localNewsBeanList.get(position).getVideoEntity();
//        if (entity != null && Utils.isStringEmpty(entity.getVideoUrl())){
//            player.play(entity.getVideoUrl());
//            lastPostion = position;
//        }
//    }
    /**
     * 下面的这几个Activity的生命状态很重要
     */

    @Override
    protected void onDestroy() {
        if (webViewLocal != null){
            webViewLocal.destroy();
        }
        super.onDestroy();
//        if (player != null) {
//            player.onDestroy();
//        }
        if (playView != null) {
            playView.stop();
        }
        if (!Utils.isStringEmpty(allImageList)){
            allImageList.clear();
        }
    }

    @Override
    protected void onPause() {
        if (adapter != null){
//            adapter.
            VideoPlayManager manager = VideoPlayManager.getInstance();
            manager.pausePlayVideo();
        }
        if (playView != null) {
            playView.pause();
        }
        super.onPause();
//        if (player != null) {
//            player.onPause();
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (playView != null) {
            playView.play();
        }
//        if (player != null) {
//            player.onResume();
//        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                if (playView != null) {
                    playView.setExpendBtn(false);
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * 由于视频在 listView 中,横屏后仍然后滑动
     * 此处判断当处于横屏时将滑动事件消耗掉
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    return true;
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
