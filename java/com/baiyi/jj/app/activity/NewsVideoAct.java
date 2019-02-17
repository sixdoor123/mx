package com.baiyi.jj.app.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.user.net.AppUtils;
import com.baiyi.jj.app.adapter.CommentAdapter;
import com.baiyi.jj.app.adapter.NewsLocalAdapter;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.cache.Dao.WebDetailDao;
import com.baiyi.jj.app.cache.bean.WebDetailBean;
import com.baiyi.jj.app.entity.NewsDetailCommentEntity;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.entity.localnews.LocalInfoEntity;
import com.baiyi.jj.app.entity.localnews.LocalNewsBean;
import com.baiyi.jj.app.manager.WebViewManager;
import com.baiyi.jj.app.net.NewsDetailUtils;
import com.baiyi.jj.app.theme.FontUtil;
import com.baiyi.jj.app.utils.DateUtils;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.IntentDefine;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.TagUtils;
import com.baiyi.jj.app.utils.Utils;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.pierfrancescosoffritti.youtubeplayer.AbstractYouTubeListener;
import com.pierfrancescosoffritti.youtubeplayer.FullScreenManager;
import com.pierfrancescosoffritti.youtubeplayer.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.youtubeplayer.YouTubePlayerView;
import com.turbo.turbo.mexico.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/9 0009.
 */
public class NewsVideoAct extends BaseNewsDetailAct{

    @Bind(R.id.lin_article_null)
    LinearLayout xLinArticleNull;
    @Bind(R.id.gif_list)
    XRecyclerView xRecyclerView;
    @Bind(R.id.item_gif_list)
    LinearLayout linContParent;
    @Bind(R.id.rela_video)
    RelativeLayout videoLayout;
    @Bind(R.id.img_video_back)
    ImageView imageViewBack;
    @Bind(R.id.youtube_player_view)
    YouTubePlayerView youTubePlayerView;
    @Bind(R.id.fullscreen_lin)
    LinearLayout fullscreenView;
    @Bind(R.id.frame_video)
    FrameLayout frameLayoutVideo;

    TextView textVideoTitle;
    TextView textVideoTime;
    LinearLayout linNoComment;

    private CommentAdapter adapter = null;

    private float startPoint = 0;
    private boolean isFullScreen = false;
    private boolean isPause = false;

    long startvalue = 0;
    boolean isAddReq = true;

    private int comPageNum = 1;


    private FullScreenManager fullScreenManager;

    public void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(true, true);
        linContParent.setVisibility(View.VISIBLE);
    }



    private void setListData() {

        if (!isGif) {
            mLinBottom.setVisibility(View.VISIBLE);
        }else{
            mLinBottom.setVisibility(View.GONE);
        }

    }


    @Override
    public void initContent() {


        startPoint = getIntent().getIntExtra(Define.VideoStartPoint,0);
        initTitle();
        initAdapter();
        initVideoView();
        initVideoTitle();
        loadData();
    }

    private void initVideoTitle(){
        if (Utils.isStringEmpty(ArticleTitle)){
            textVideoTitle.setVisibility(View.GONE);
        }else {
            textVideoTitle.setVisibility(View.VISIBLE);
            textVideoTitle.setText(Utils.isStringEmpty(ArticleTitle) ? "" : ArticleTitle);
        }
        Typeface detailTItle = Typeface.createFromAsset(getAssets(), "fonts/detail_black.ttf");
        textVideoTitle.setTypeface( detailTItle);
        float textSize = FontUtil.getNewsDetailTitleSize();
        textVideoTitle.setLineSpacing(FontUtil.getLineSpaceNews(),1);
        textVideoTitle.setTextSize(textSize);
        String source = Utils.isStringEmpty(SourceStr) ? "" : SourceStr;
        String time = Utils.isStringEmpty(TimeStr) ? "" : (getResources().getString(R.string.txt_point)+" " + TimeStr);
        textVideoTime.setText(source + time);

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (isFullScreen && youTubePlayerView != null){
                youTubePlayerView.toggleFullScreen();
                exitScreen();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    private void initTitle(){
//        imageViewBack.setVisibility(View.GONE);
        linTitle.setVisibility(View.GONE);
//        int mHideFlags =
//                View.SYSTEM_UI_FLAG_LOW_PROFILE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//        imageViewBack.setSystemUiVisibility(mHideFlags);

    }
    @OnClick(R.id.img_video_back)
    public void onClick(View view) {
        if (isFullScreen){
            exitScreen();
            return;
        }
        setExitSwichLayout();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constant.Result_Comment){
            comPageNum = 1;
            loadData();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (youTubePlayerView != null){
            youTubePlayerView.pauseVideo();
            isPause = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPause && youTubePlayerView!=null){
            youTubePlayerView.playVideo();
            isPause = false;
        }
    }

    private void initVideoView(){
        videoLayout.setVisibility(View.VISIBLE);
        fullScreenManager = new FullScreenManager(this);
        //https://www.youtube.com/watch?v=IHhPpTkxW9o"
        youTubePlayerView.initialize(new AbstractYouTubeListener() {

            @Override
            public void onReady() {
                TLog.e("ready",videoId+"======"+startPoint);
                youTubePlayerView.loadVideo(videoId, startPoint);
            }

        }, true);
        youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
            @Override
            public void onYouTubePlayerEnterFullScreen() {
//                startAnimation(youTubePlayerView);
                isFullScreen = true;
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                fullScreenManager.enterFullScreen();
                if (fullscreenView == null){
                    return;
                }
                ViewGroup viewGroup = (ViewGroup) youTubePlayerView.getParent();
                if (viewGroup != null) {
                    viewGroup.removeAllViews();
                    fullscreenView.addView(youTubePlayerView);
                    fullscreenView.setVisibility(View.VISIBLE);
                    int mHideFlags =
                            View.SYSTEM_UI_FLAG_LOW_PROFILE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                    fullscreenView.setSystemUiVisibility(mHideFlags);
                }


//                Intent intent = YouTubeStandalonePlayer.createVideoIntent(context, TestActivity.apiKey, data.getReservezone4());
//                context.startActivity(intent);

//                youTubePlayerView.setCustomActionRight(ContextCompat.getDrawable(context, R.drawable.ic_pause_36dp), new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        youTubePlayerView.pauseVideo();
//                    }
//                });
            }

            @Override
            public void onYouTubePlayerExitFullScreen() {


//                fullScreenManager.exitFullScreen();
                    exitScreen();

//                youTubePlayerView.setCustomActionRight(ContextCompat.getDrawable(context, R.drawable.ic_pause_36dp), null);
            }
        });
        startvalue = System.currentTimeMillis();
        youTubePlayerView.setOnStartPlayListner(new YouTubePlayerView.OnStartPlayListner() {
            @Override
            public void onStart() {
                long current = System.currentTimeMillis();
                long value = current-startvalue-1000;
//                TLog.e("value",current+"---------"+startvalue+"-------"+value);
                if (isAddReq){
                addRequestTime("NewsVideoAct", BaseAnalyticsActivity.Category_web,BaseAnalyticsActivity.Web_Load_Detail_Video,value);
                    isAddReq = false;
                }

            }
        });

        addEnvent(BaseAnalyticsActivity.Envent_detail_VideoClick);
    }

    private void exitScreen(){
        if (fullscreenView == null){
            return;
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        isFullScreen = false;
        fullscreenView.setVisibility(View.GONE);
        fullscreenView.removeAllViews();
        frameLayoutVideo.removeAllViews();
        ViewGroup last = (ViewGroup) youTubePlayerView.getParent();//找到videoitemview的父类，然后remove
        if (last != null) {
            last.removeAllViews();
        }
        frameLayoutVideo.addView(youTubePlayerView);
        int mShowFlags =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        fullscreenView.setSystemUiVisibility(mShowFlags);
    }

    private void initAdapter() {
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
        xRecyclerView.setLoadingMoreEnabled(true);

        xRecyclerView.setLayoutManager(layoutManager);

        xRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);

        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                comPageNum = 1;
                loadData();
            }

            @Override
            public void onLoadMore() {
                comPageNum++;
                loadData();
            }
        });

        adapter = new CommentAdapter(this);
        xRecyclerView.setAdapter(adapter);

        adapter.setAgreeOnclick(new CommentAdapter.ItemAgreeOnclick() {

            @Override
            public void Onclick(NewsDetailCommentEntity entity, int postion) {
                loadCommentVote(postion, entity, 1);
            }
        });

        initHeadView();
        setWebTextSize(0);
    }
    private void initHeadView() {
        View headview = LayoutInflater.from(this).inflate(R.layout.head_video_detail, null);
        textVideoTitle = (TextView) headview.findViewById(R.id.video_detail_title);
        textVideoTime = (TextView) headview.findViewById(R.id.video_detail_time);
        linNoComment = (LinearLayout) headview.findViewById(R.id.lin_shownocomment);

        xRecyclerView.addHeaderView(headview);
    }

    @Override
    public void setWebTextSize(int a) {
//        if (adapter != null) {
//            adapter.refresh();
//            headTitle.setTextSize(FontUtil.getNewsDetailTitleSize());
//        }

        if (adapter !=null){
            textVideoTitle.setTextSize(FontUtil.getNewsDetailTitleSize());
            adapter.refresh();
        }

    }

    private void loadData() {

        newsDetailUtils.loadCommentData(this, ArticleId, comPageNum,
                new NewsDetailUtils.CommentListCallBack() {

                    @Override
                    public void ComListBack(List<NewsDetailCommentEntity> list) {


                        if (null == list) {
                            list = new ArrayList<>();

                        }
                        if (Utils.isStringEmpty(list)){
                            if (adapter != null && adapter.getItemCount() == 0){
                                linNoComment.setVisibility(View.VISIBLE);
                            }
                        }else {
                            linNoComment.setVisibility(View.GONE);
                        }
//                        Collections.reverse(list);
                        if (comPageNum == 1) {
                            adapter.setData(list);
                            xRecyclerView.refreshComplete();
                        } else {
                            xRecyclerView.loadMoreComplete();
                            adapter.addData(list);
                        }

                    }
                });
    }

    private void loadCommentVote(final int position,
                                 final NewsDetailCommentEntity entity, final int isAgree) {

        checkLogin(new AnonymityLister() {

            @Override
            public void setAnonymityLister(UserInfoEntity infoEntity) {
                if (infoEntity == null) {
                    return;
                }
                if (infoEntity.getMID().equals(entity.getComUserId())) {
//                    displayToast(getResources().getString(
//                            R.string.tip_comm_vote_myself));
                    return;
                }
                if (Utils.isStringEmpty(entity.getComId())) {
                    return;
                }

                newsDetailUtils.loadCommentVote(
                        NewsVideoAct.this, entity.getComId(), isAgree,
                        new NewsDetailUtils.CommentVoteCallBack() {

                            public void voteCallBack(int state) {
                                if (state == NewsDetailUtils.State_Success) {
                                    adapter.updateItem(position);
                                    if (isAgree == 1) {
                                        entity.setComAgree(entity.getComAgree() + 1);
                                    }
                                    return;
                                } else if (state == NewsDetailUtils.State_Repeat) {
                                    // adapter.setItemSelect(position, entity);
                                }
                            }
                        });
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (youTubePlayerView!= null){
            youTubePlayerView.release();
        }
    }

    @Override
    public boolean isShowTitle() {
        return true;
    }
    @Override
    public int returnStateColor() {
        return R.color.transparent;
    }
}
