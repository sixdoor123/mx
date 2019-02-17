package com.baiyi.jj.app.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.activity.main.news.BaseHolder;
import com.baiyi.jj.app.adapter.MorningNewsAdapter;
import com.baiyi.jj.app.entity.MorningHeadEntity;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.manager.MorningNewsManager;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.IntentClassChangeUtils;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.LetterSpacingTextView;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.turbo.turbo.mexico.R;

import org.json.JSONException;
import org.json.JSONObject;

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
 * Created by Administrator on 2017/6/15 0015.
 */
public class MorningNewsListAct extends BaseActivity{

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.title_name)
    LetterSpacingTextView titleName;
    @Bind(R.id.img_search)
    ImageView imgEdit;
    @Bind(R.id.list_morning)
    XRecyclerView mListView;
    @Bind(R.id.loading)
    MyLoadingBar progressBar;

    MorningNewsAdapter newsAdapter;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);

        View titleView = ContextUtil.getLayoutInflater(this).inflate(R.layout.title_left, null);
        win_title.addView(titleView);
        View contentView = ContextUtil.getLayoutInflater(this).inflate(R.layout.act_morningnews, null);
        win_content.addView(contentView);
        ButterKnife.bind(this);

        initTitle();
        initList();
    }

    @OnClick(R.id.img_back)
    public void onClick(View view) {
        setExitSwichLayout();
    }
    private void initTitle() {
        titleName.setText(getResources().getString(R.string.tip_morning_title));
        imgBack.setVisibility(View.VISIBLE);
    }


    private void initList() {

        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        mListView.setLayoutManager(lm);
        mListView.setNoMore();
        mListView.setPullRefreshEnabled(false);
        newsAdapter = new MorningNewsAdapter(this);
        mListView.setAdapter(newsAdapter);

        loadLocaData();
    }

    private void loadLocaData(){
//        List<MorningHeadEntity> morningHeadEntities = (List<MorningHeadEntity>) getIntent().getSerializableExtra(Define.MorningDataList);
//        if (!Utils.isStringEmpty(morningHeadEntities)){
//
//            return;
//        }
        startProgress();
        loadData();
    }

    List<NewsListEntity> entities;
    private void loadData(){

        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .url(NetUtils.getMorningDataList())
                .get()
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        stopProgress();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();

                if (Utils.isStringEmpty(str)){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            stopProgress();
                        }
                    });
                    return;
                }else {
//                    TLog.e("str",str);
                    entities = MorningNewsManager.getMorningDataList(str);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            stopProgress();
                            newsAdapter.setData(entities);
                        }
                    });
                }
            }
        });
    }

    public void stopProgress() {
        progressBar.stop();
        progressBar.setVisibility(View.GONE);
    }

    public void startProgress() {
        progressBar.start();
        progressBar.setVisibility(View.VISIBLE);
    }
}
