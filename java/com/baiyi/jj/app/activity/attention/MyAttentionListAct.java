package com.baiyi.jj.app.activity.attention;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.NewsLocalAct;
import com.baiyi.jj.app.activity.attention.net.AttetionNet;
import com.baiyi.jj.app.activity.main.AttentionPager;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/29 0029.
 */
public class MyAttentionListAct extends BaseActivity {

    @Bind(R.id.list_myattent)
    XRecyclerView mListView;
    @Bind(R.id.title_name)
    LetterSpacingTextView titleName;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.img_search)
    ImageView imgRight;

    private NewsRecyclerAdapter newsAdapter;

    private String after = "";
    private AttentionWordsEntity wordsEntity;
    private boolean isAtte;

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
        loadHotWordsList();
    }

    private void initData(){
        wordsEntity = (AttentionWordsEntity) getIntent().getSerializableExtra(Define.HotWord);
        isAtte = getIntent().getBooleanExtra(Define.IsAttention,false);
    }

    private void initTitle() {
        imgBack.setVisibility(View.VISIBLE);
        titleName.setText(wordsEntity.getAlias());
        imgRight.setVisibility(View.VISIBLE);
        imgRight.setImageDrawable(getResources().getDrawable(R.drawable.btn_add_attetion));
//        imgRight.setSelected(wordsEntity.isAttet());
        imgRight.setSelected(isAtte);
        imgRight.setVisibility(View.GONE);
//        titleName.setTypeface(CmsApplication.getTitleFace(this));
    }

    @OnClick({R.id.img_back, R.id.img_search})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.img_back:
                exit();
                break;
            case R.id.img_search:
                if (imgRight.isSelected()) {
                    imgRight.setSelected(false);
                    delAttetion(wordsEntity);
                } else {
                    imgRight.setSelected(true);
                    addAttetion(wordsEntity);
                }
                break;
        }
    }

    private void addAttetion(final AttentionWordsEntity entity) {
        new AttetionNet().loadAddAttention(String.valueOf(entity.getHotword_id()), new AttetionNet.addCallBack() {
            @Override
            public void callBack(final int state) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (state == AttetionNet.State_Success) {
                            imgRight.setSelected(true);
                            AttentionCacheManager.getInstance().addOneCaChe(entity.getChannel_id()+""+entity.getHotword_id(),entity);
                            entity.setAttet(true);
                            wordsEntity.setAttet(true);
                            new AttetionWordsDao(MyAttentionListAct.this).updateOrAdd(entity);
                        } else {
                            displayToast(getResources().getString(R.string.txt_attention_addf));
                        }

                    }
                });

            }
        });
    }

    private void delAttetion(final AttentionWordsEntity entity) {
        new AttetionNet().loaddelAttention(String.valueOf(entity.getHotword_id()), new AttetionNet.delCallBack() {
            @Override
            public void callBack(final int state) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (state == AttetionNet.State_Success) {
                            imgRight.setSelected(false);
                            AttentionCacheManager.getInstance().deleteOneCache(entity.getChannel_id()+""+entity.getHotword_id());
                            entity.setAttet(false);
                            wordsEntity.setAttet(false);
                            new AttetionWordsDao(MyAttentionListAct.this).updateOrAdd(entity);
                        } else {
                            displayToast(getResources().getString(R.string.txt_attention_canf));
                        }

                    }
                });

            }
        });
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
                        after = "";
                        loadHotWordsList();
                    }

                }, 2000);
            }

            @Override
            public void onLoadMore() {
                // TODO Auto-generated method stub
                loadHotWordsList();
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

                String img = "";
                if (!Utils.isStringEmpty(entity.toImgList())) {
                    img = entity.toImgList().get(0).getMedia_path();
                }
//                EditionManager.getInstance().addReadNews(entity.getArticle_id());
                IntentClassChangeUtils.startNewsDetail(MyAttentionListAct.this,entity,position,0,false);
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

    private void loadHotWordsList() {


        if (Utils.isStringEmpty(CmsApplication.getUserToken())) {
            return;
        }
        OkHttpClient httpClient = new OkHttpClient.Builder().build();
        TLog.e("abc1", NetUrl.getWordsList(after, Constant.limit_10, wordsEntity.getWords()));
        Request request = new Request.Builder()
                .url(NetUrl.getWordsList(after, Constant.limit_10, wordsEntity.getWords()))
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
                        if (Utils.isStringEmpty(after)) {
                            mListView.init();
                            newsAdapter.setData(attentionList);
                        } else {
                            newsAdapter.addData(attentionList);
                        }
                        after = JsonParse.getAfterStr(json);
                    }
                });


            }
        });
    }

    private void exit() {
        //�жϵ������ע��ť֮��Ż�ȥˢ��
        if (isAtte != imgRight.isSelected()) {
            setResult(AttentionPager.AttetCode);
            AttentionCacheManager.getInstance().setTagRefresh(true);
        }
        setExitSwichLayout();
    }

    /**
     * ���ذ�ť
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        exit();
        return true;
    }

}
