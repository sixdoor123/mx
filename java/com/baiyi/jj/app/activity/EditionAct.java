package com.baiyi.jj.app.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.main.HomeTabAct;
import com.baiyi.jj.app.activity.main.NewsRecyclerAdapter;
import com.baiyi.jj.app.activity.main.news.BaseHolder;
import com.baiyi.jj.app.activity.user.net.NetUrl;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.cache.AttentionCacheManager;
import com.baiyi.jj.app.cache.Dao.AttetionWordsDao;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.manager.EditionManager;
import com.baiyi.jj.app.utils.ArticleHistoryUtils;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.IntentClassChangeUtils;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.LetterSpacingTextView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.turbo.turbo.mexico.R;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/30 0030.
 */
public class EditionAct extends BaseActivity {
    /**
     * Created by Administrator on 2016/11/29 0029.
     */
    @Bind(R.id.list_myattent)
    XRecyclerView mListView;
    @Bind(R.id.title_name)
    LetterSpacingTextView titleName;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.img_search)
    ImageView imgRight;
    private NewsRecyclerAdapter newsAdapter;

    private int pageIndex = 1;
    private String articleId;
    private boolean isTop;
    private int itemPosition = -1;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, isAnimal);
        View titleView = ContextUtil.getLayoutInflater(this).inflate(
                R.layout.title_left, null);
        win_title.addView(titleView);
        View contentView = ContextUtil.getLayoutInflater(this).inflate(
                R.layout.act_myattentionlist, null);
        win_content.addView(contentView);
        ButterKnife.bind(this);

        initData();
        initTitle();
        initListView();
        loadEditionList();
    }

    private void initData() {
        articleId = getIntent().getStringExtra(Define.ArticleId);
        isTop = getIntent().getBooleanExtra(Define.IsPoint,false);
        itemPosition = getIntent().getIntExtra(Define.ITEM_POSTION, -1);
    }

    private void initTitle() {
        imgBack.setVisibility(View.VISIBLE);
        if (isTop){
            titleName.setText(getResources().getString(R.string.txt_editition_top));
        }else {
            titleName.setText(getResources().getString(R.string.txt_editition_title));
        }

        imgRight.setVisibility(View.GONE);

//        titleName.setTypeface(CmsApplication.getTitleFace(this));
    }

    @OnClick({R.id.img_back, R.id.img_search})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.img_back:
                exit();
                break;
        }
    }


    private void initListView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mListView.setLayoutManager(layoutManager);

        mListView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mListView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mListView.refresh();

        mListView.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        pageIndex=1;
                        loadEditionList();
                    }

                }, 2000);
            }

            @Override
            public void onLoadMore() {
                // TODO Auto-generated method stub
                pageIndex ++;
                loadEditionList();
            }
        });
        newsAdapter = new NewsRecyclerAdapter(this, mListView, true,
                CmsApplication.isGprs,false,null);
        mListView.setAdapter(newsAdapter);

        newsAdapter.setRecyclerViewItemClick(new BaseHolder.RecyclerViewItemClick() {

            @Override
            public void setOnItemClickLister(int position) {

                // ???item????????????
                NewsListEntity entity = (NewsListEntity) newsAdapter.getNewsListEntity(position - 1);

                IntentClassChangeUtils.startNewsDetail(EditionAct.this,entity,position,0,false);
            }
        });

    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        refreshList();
    }

    public void refreshList() {
        if (newsAdapter != null) {
            newsAdapter.refresh();
        }
    }
    private void loadEditionList() {


        if (Utils.isStringEmpty(CmsApplication.getUserToken())) {
            return;
        }
        OkHttpClient httpClient = new OkHttpClient.Builder().build();
        TLog.e("abc1", NetUrl.getEditionList(pageIndex, Constant.limit_10, articleId));
        Request request = new Request.Builder()
                .url(NetUrl.getEditionList(pageIndex, Constant.limit_10, articleId))
                .get()
                .header("Content-Type", "application/json")
                .header("Authorization", CmsApplication.getUserToken())
                .build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String json = response.body().string();
                TLog.e("abc", "json-===" + json);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<NewsListEntity> attentionList = JsonParse.getWordList(json);
                        mListView.loadMoreComplete();
                        mListView.refreshComplete();
                        if (Utils.isStringEmpty(attentionList)) {
                            return;
                        }
                        if (pageIndex == 1) {
                            mListView.init();
                            newsAdapter.setData(attentionList);
                        } else {
                            newsAdapter.addData(attentionList);
                        }

                    }
                });


            }
        });
    }

    private void exit() {
        if (true) {
            Intent intent = new Intent(getApplicationContext(),
                    HomeTabAct.class);
            int num = 0;
            intent.putExtra(Define.CommentNum, num);
            intent.putExtra(Define.ITEM_POSTION, itemPosition);
            setResult(ChannelUtils.Result_Detail_Comment, intent);
        }
        setExitSwichLayout();

    }

    /**
     * ??????
     *
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        exit();
        return true;
    }


}
