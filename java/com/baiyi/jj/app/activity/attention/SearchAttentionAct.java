package com.baiyi.jj.app.activity.attention;

import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.attention.net.JsonAttention;
import com.baiyi.jj.app.activity.user.net.NetUrl;
import com.baiyi.jj.app.adapter.SearchAttentionAdapter;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.turbo.turbo.mexico.R;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;
import java.io.IOException;
import java.util.ArrayList;
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
 * ���ߣ�lizl on 2016/11/28 15:00
 */
public class SearchAttentionAct extends BaseActivity {

    @Bind(R.id.edit_search_attention)
    EditText edtSearch;
    @Bind(R.id.img_delete_attention)
    ImageView imgDelete;
    @Bind(R.id.loading)
    MyLoadingBar progressBar;
    @Bind(R.id.list_search_attention)
    XRecyclerView mListView = null;

    private int pageNum = 1;
    private int limit = 10;
    private SearchAttentionAdapter attentionAdapter;

    private String keyword;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);

        View contentView = LayoutInflater.from(this).inflate(
                R.layout.act_search_attention, null);
        win_content.addView(contentView);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        edtSearch.findFocus();
        edtSearch.addTextChangedListener(textWatcher);
        initListView();
        setKeySearchOnclick();
    }

    private void initListView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mListView.setLayoutManager(layoutManager);
        mListView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mListView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mListView.setLoadingListener(new LoadingListener() {

            @Override
            public void onRefresh() {
                pageNum = 1;
                loadKeySearchDetail();
            }

            @Override
            public void onLoadMore() {
                pageNum++;
                loadKeySearchDetail();
            }
        });
        attentionAdapter = new SearchAttentionAdapter(this);
        mListView.setAdapter(attentionAdapter);
    }


    /**
     * 获取并加载数据c
     */
    private void loadKeySearchDetail() {

        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url(NetUrl.getSearchNoFollowUrl(keyword, pageNum, limit))
                .header(Constant.HEAD_NAME, CmsApplication.getUserToken())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                final List<AttentionWordsEntity> listEntities = JsonAttention.getNoFollowWords(json);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (pageNum == 1) {
                            mListView.refreshComplete();
                        } else {
                            mListView.loadMoreComplete();
                        }
                        if (Utils.isStringEmpty(listEntities)) {
                            return;
                        }
                        attentionAdapter.setData(listEntities);
                    }
                });
            }
        });

    }

    @OnClick({R.id.img_search_back, R.id.img_delete_attention, R.id.img_search_attention})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_search_back:
                setExitSwichLayout();
                break;
            case R.id.img_delete_attention:
                edtSearch.setText("");
                break;
            case R.id.img_search_attention:
                hideInput(edtSearch);
                keyword = edtSearch.getText().toString().trim();
                if (Utils.isStringEmpty(keyword)) {
                    displayToast(getResources().getString(R.string.tip_search_key_null));
                    return;
                }
                pageNum = 1;
                if (!mListView.isRefreshing()) {
                    mListView.smoothScrollToPosition(0);
                    mListView.refresh();
                    mListView.init();
                }
                loadKeySearchDetail();
                break;
        }
    }

    TextWatcher textWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            if (cs.length() >= 1) {
                imgDelete.setVisibility(View.VISIBLE);
            } else {
                imgDelete.setVisibility(View.INVISIBLE);
            }
        }

        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }

        public void afterTextChanged(Editable arg0) {
        }
    };

    private void setKeySearchOnclick() {
        edtSearch.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    hideInput(edtSearch);
                    keyword = edtSearch.getText().toString().trim();
                    if (Utils.isStringEmpty(keyword)) {
                        displayToast(getResources().getString(R.string.tip_search_key_null));
                        return false;
                    }
                    pageNum = 1;
                    if (!mListView.isRefreshing()) {
                        mListView.smoothScrollToPosition(0);
                        mListView.refresh();
                        mListView.init();
                    }
                    loadKeySearchDetail();
                    return true;
                }
                return false;
            }
        });
    }
    @Override
    public int returnStateColor() {
        return R.color.day_bg_color_e0e0;
    }

}
