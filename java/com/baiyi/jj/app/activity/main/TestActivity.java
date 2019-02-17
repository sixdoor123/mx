package com.baiyi.jj.app.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.NewsLocalAct;
import com.baiyi.jj.app.activity.main.news.BaseHolder;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.entity.article.ArticleEntity;
import com.baiyi.jj.app.utils.IntentClassChangeUtils;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.TextLengthUtils;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.LetterSpacingTextView;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.turbo.turbo.mexico.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/5 0005.
 */
public class TestActivity extends BaseActivity implements YouTubePlayer.OnInitializedListener{

    @Bind(R.id.list_myattent)
    XRecyclerView mListView;
    @Bind(R.id.fullscreen_lin)
   LinearLayout fullScreenLin;
    @Bind(R.id.line_btn)
    LinearLayout linearLayout;

//    @Bind(R.id.title_name)
//    LetterSpacingTextView titleName;
//    @Bind(R.id.img_back)
//    ImageView imgBack;
//    @Bind(R.id.img_search)
//    ImageView imgRight;
    private NewsRecyclerAdapter newsAdapter;

    int currentVideo = -1;

    List<NewsListEntity> listEntities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_myattentionlist);
        ButterKnife.bind(this);
//        initTitle();
        linearLayout.setVisibility(View.VISIBLE);
        initScreeView();
        initListView();
        initData();

        YouTubeInitializationResult errorReason =
                YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(this);




//        YouTubePlayerSupportFragment
    }

    @OnClick({R.id.btn_you, R.id.btn_fb})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_you:
                loadTestList(7);
                break;
            case R.id.btn_fb:
                loadTestList(6);
                break;
        }
    }

    private void initScreeView(){
        int abimgW = Config.getInstance().getScreenWidth(this);
        int abimgH = Config.getInstance().getHasVirtualKey(this);
        fullScreenLin.getLayoutParams().height = abimgW;
        fullScreenLin.getLayoutParams().width = abimgH;
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            fullScreenLin.setVisibility(View.GONE);
//            fullScreenLin.removeAllViews();
//        }else {
//
//        }
//    }

//    @Override
//    protected void initWin(boolean hasScrollView, boolean isAnimal) {
//        super.initWin(false, isAnimal);
//        View titleView = ContextUtil.getLayoutInflater(this).inflate(
//                R.layout.title_left, null);
//        win_title.addView(titleView);
//        View contentView = ContextUtil.getLayoutInflater(this).inflate(
//                R.layout.act_myattentionlist, null);
//        win_content.addView(contentView);
//        ButterKnife.bind(this);
//
//        initTitle();
//        initListView();
//        initData();
//    }

    private void initData(){
        loadTestList(7);
//        String videotest = TextLengthUtils.getAssetsStr(this, "videotest");
//        TLog.e("vide0",videotest);
//        if (Utils.isStringEmpty(videotest)) {
//            return;
//        }
//        try {
//            JSONArray array1 = new JSONArray("["+videotest+"]");
//            ArticleEntity articleEntity = JsonParse.getArticleEntity2(array1, false,false,true);
//            if (articleEntity != null){
//                List<NewsListEntity> newsListEntities = articleEntity.toNewsList();
//                if (!Utils.isStringEmpty(newsListEntities)){
//                    newsAdapter.setData(newsListEntities);
//                }
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }



    private void initListView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mListView.setLayoutManager(layoutManager);

        mListView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mListView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
//        mListView.refresh();

        mListView.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                    }

                }, 2000);
            }

            @Override
            public void onLoadMore() {
            }
        });
        mListView.addOnScrollListener(new OnVideoChangerListner());
        newsAdapter = new NewsRecyclerAdapter(this, mListView, true,
                CmsApplication.isGprs,false,fullScreenLin);

        mListView.setAdapter(newsAdapter);

        newsAdapter.setRecyclerViewItemClick(new BaseHolder.RecyclerViewItemClick() {

            @Override
            public void setOnItemClickLister(int position) {

                NewsListEntity entity = (NewsListEntity) newsAdapter.getNewsListEntity(position-1);
                IntentClassChangeUtils.startNewsDetail(TestActivity.this,entity,position,0,false);

            }
        });
        newsAdapter.setOnPlayerClickLisner(new NewsRecyclerAdapter.OnPlayerClickLisner() {
            @Override
            public void clickId(int postion) {
                currentVideo = postion;
            }
        });
    }

    class OnVideoChangerListner extends RecyclerView.OnScrollListener{
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            RecyclerView.LayoutManager layoutManager1 = recyclerView.getLayoutManager();
            if (layoutManager1 instanceof LinearLayoutManager) {
                LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager1;
                //获取最后一个可见view的位置
                int lastItemPosition = linearManager.findLastVisibleItemPosition();
                //获取第一个可见view的位置
                int firstItemPosition = linearManager.findFirstVisibleItemPosition();

            }
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    public boolean isShowTitle() {
        return true;
    }
    @Override
    public int returnStateColor() {
        return R.color.day_bg_color_fafafa;
    }

    private void loadTestList(int temp){
        OkHttpClient client = new OkHttpClient.Builder().build();
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                getPostData(temp));
        Request request = new Request.Builder()
                .post(requestBody)
                .url(NetUtils.getTestList())
                .addHeader(Constant.HEAD_NAME,CmsApplication.getUserToken())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String strJson = response.body().string();
                TLog.e("str","---"+strJson);
                try{
                    JSONArray array = new JSONArray("["+strJson+"]");
                    ArticleEntity  articleEntity = JsonParse.getArticleEntity2(array, false, false,false);
                    listEntities = articleEntity.toNewsList();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            newsAdapter.setData(listEntities);
                        }
                    });


                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    private static String getPostData(int temple) {
        JSONObject object = new JSONObject();
        try {
            object.put("template", temple);
        } catch (JSONException e) {
            e.toString();
        }
        return object.toString();
    }
}
