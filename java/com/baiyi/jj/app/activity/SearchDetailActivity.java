package com.baiyi.jj.app.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.main.NewsBaseActivity;
import com.baiyi.jj.app.adapter.CollectAdapter;
import com.baiyi.jj.app.adapter.SearchHistoryAdapter;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.cache.Dao.HistoryDao;
import com.baiyi.jj.app.cache.bean.HistoryBean;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.listitem.search.SearchHolder.OnItemClick;
import com.baiyi.jj.app.utils.ArticleHistoryUtils;
import com.baiyi.jj.app.utils.IntentClassChangeUtils;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.TagUtils;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.baiyi.jj.app.views.TitleView;
import com.turbo.turbo.mexico.R;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 搜索界面
 *
 * @author Weizd
 */
public class SearchDetailActivity extends NewsBaseActivity {

    private TitleView titleView = null;
    private EditText edtSearch = null;
    private ImageView imgSearch = null;
    private ImageView imgDelete = null;
    private LinearLayout lineNoData = null;

    private XRecyclerView mListView = null;
    private CollectAdapter detailAdapter = null;
    private List<NewsListEntity> detailEntities = null;

    private MyLoadingBar progressBar = null;

    private int index = 1;
    private int limit = 10;


    private RecyclerView listHistory;
    private SearchHistoryAdapter historyAdapter;

    private HistoryDao historyLite;


    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);

        titleView = new TitleView(this, false, false, -1);
        win_title.addView(titleView);

        View contentView = LayoutInflater.from(this).inflate(
                R.layout.activity_searchdetail, null);
        win_content.addView(contentView);

        progressBar = (MyLoadingBar) findViewById(R.id.loading);

        initView();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
    }

    /**
     * ?????View
     */
    private void initView() {
        initTitleView();
        edtSearch = (EditText) findViewById(R.id.edit_search);
        edtSearch.addTextChangedListener(textWatcher);
        imgSearch = (ImageView) findViewById(R.id.img_search_btn);
        imgSearch.setOnClickListener(new OnSearchClick());
        imgDelete = (ImageView) findViewById(R.id.img_delete);
        imgDelete.setOnClickListener(new OnSearchClick());
        lineNoData = (LinearLayout) findViewById(R.id.lin_shownodata);
        initListView();
        initHistView();
        setKeyBoadSearchOnclick();
    }

    /**
     * 标题栏
     */
    private void initTitleView() {
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setVisibility(View.VISIBLE);
        imgBack.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                setExitSwichLayout();
            }
        });
        titleView.setTitleText(getResources().getString(R.string.title_search));
    }

    /**
     * 初始化搜索内容列表
     */
    private void initListView() {
        mListView = (XRecyclerView) findViewById(R.id.list_search_detail);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mListView.setLayoutManager(layoutManager);

        mListView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mListView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mListView.setLoadingListener(new LoadingListener() {

            @Override
            public void onRefresh() {
                index = 1;
                afterArray = new JSONArray();
                loadKeySearchDetail();
            }

            @Override
            public void onLoadMore() {
                index++;
                loadKeySearchDetail();
            }
        });

        detailAdapter = new CollectAdapter(this);
        mListView.setAdapter(detailAdapter);
        detailAdapter.setRecycleItemClick(new OnItemClick() {

            @Override
            public void OnClick(int position) {
                if (position < 1) {
                    return;
                }
                int index = position - 1;
                int totalSize = detailAdapter.getItemCount();
                if (index >= totalSize) {
                    return;
                }
                NewsListEntity entity = (NewsListEntity) detailAdapter.getItem(position - 1);
                IntentClassChangeUtils.startNewsDetail(SearchDetailActivity.this,entity,position,0,false);
            }
        });
    }

    /**
     * 初始化历史记录
     */
    private void initHistView() {
        listHistory = (RecyclerView) findViewById(R.id.list_history);
        historyAdapter = new SearchHistoryAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listHistory.setLayoutManager(layoutManager);
        historyAdapter.setItemClick(new SearchHistoryAdapter.OnItemClick() {
            @Override
            public void onClick(String string) {
                if (Utils.isStringEmpty(string)) {
                    loadClearHistory();
                } else {
                    listHistory.setVisibility(View.GONE);
                    listHistory.invalidate();
                    edtSearch.setText(string);
                    hideInput(edtSearch);
                    startProgress();
                    loadKeySearchDetail();
                }
            }
        });
        listHistory.setAdapter(historyAdapter);
        loadHistoryCache();
    }

    private void loadHistoryCache() {

        listHistory.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);

        historyLite = new HistoryDao(this);
        List<HistoryBean> historyBeens = historyLite.queryForAll();
        if (Utils.isStringEmpty(historyBeens)) {
            return;
        }
        historyAdapter.setData(getNotRepeatBeans(historyBeens));
    }

    private List<HistoryBean> getNotRepeatBeans(List<HistoryBean> historyBeens) {
        Collections.reverse(historyBeens);
        List<HistoryBean> beans = new ArrayList<HistoryBean>();
        for (HistoryBean bean : historyBeens) {
            if (Utils.isStringEmpty(beans)) {
                beans.add(bean);
            } else {
                if (!isExits(beans, bean.getHisName())) {
                    beans.add(bean);
                }
            }
        }
        return beans;
    }

    private boolean isExits(List<HistoryBean> beans, String name) {
        for (HistoryBean bean : beans) {
            if (bean.getHisName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    private void loadClearHistory() {

        listHistory.setVisibility(View.GONE);
        historyLite.deleteAll();
        historyAdapter.clear();
    }


    String words;

    private void stopProgress() {
        progressBar.stop();
        progressBar.setVisibility(View.GONE);
    }

    private void startProgress() {
        progressBar.start();
        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * 获取搜索内容LIST
     */
    private void loadKeySearchDetail() {
        mListView.setVisibility(View.VISIBLE);
        lineNoData.setVisibility(View.GONE);
        words = edtSearch.getText().toString().trim();
        if (Utils.isStringEmpty(words)) {
            stopProgress();
//            displayToast(getResources().getString(R.string.tip_search_key_null));
            return;
        }
        if (!words.equals(keyWord)) {
            HistoryBean bean = new HistoryBean(words);
            historyLite.add(bean);
        }

        if (!Utils.isStringEmpty(detailEntities)) {
            detailEntities.clear();
        }
        keyWord = words;

        JsonLoader loader = new JsonLoader(this);
        loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
        loader.setUrl(NetUtils.getSearchDetail());
        loader.setPostData(getPostData());
        loader.setType(BaseNetLoder.APPLICATION_JSON);
        loader.setMethod(BaseNetLoder.Method_Post);
        loader.setLoaderListener(new LoaderListener() {

            public void onProgress(Object arg0, long arg1, long arg2) {

            }

            public void onError(Object arg0, int arg1, String arg2) {
                mListView.loadMoreComplete();
                mListView.refreshComplete();
                stopProgress();
                displayToast(getResources().getString(R.string.tip_loaddata_failure));

            }

            public void onCompelete(Object arg0, Object arg1) {

                mListView.loadMoreComplete();
                mListView.refreshComplete();
                stopProgress();
                JSONArray array = (JSONArray) arg1;
                detailEntities = JsonParse.getSearchDetailList(array);

                setAfterArray(array);

                if (Utils.isStringEmpty(detailEntities)) {

                    if (index == 1) {
                        mListView.init();
                        detailAdapter.setData(new ArrayList<NewsListEntity>());
                        lineNoData.setVisibility(View.VISIBLE);
//                        displayToast(getResources().getString(R.string.tip_search_content_null));
                    }
                    return;
                }

                if (index == 1) {
                    mListView.init();
                    detailAdapter.setData(detailEntities);
                } else {
                    detailAdapter.addData(detailEntities);
                }
            }
        });

        CmsApplication.getDataStratey().startLoader(loader);
    }

    private String keyWord = null;

    /**
     * 搜索
     */
    class OnSearchClick implements OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.img_delete) {//清空搜索框
                edtSearch.setText("");
            } else if (v.getId() == R.id.img_search_btn) {  //搜索
                listHistory.setVisibility(View.GONE);
                hideInput(edtSearch);
                startProgress();
                index = 1;
                afterArray = new JSONArray();
                loadKeySearchDetail();

            } else if (v.getId() == R.id.edit_search) {//搜索框焦点
                edtSearch.setFocusable(true);
                edtSearch.setFocusableInTouchMode(true);
                loadHistoryCache();
            }
        }
    }

    /**
     * 搜索框文字改变事件
     */
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

    /**
     * ť ļ
     */
    private void setKeyBoadSearchOnclick() {
        edtSearch.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    listHistory.setVisibility(View.GONE);
                    hideInput(edtSearch);
                    startProgress();
                    index = 1;
                    afterArray = new JSONArray();
                    loadKeySearchDetail();
                    return true;
                }
                return false;
            }
        });
    }


    private JSONArray afterArray = new JSONArray();

    private String getPostData() {

        JSONObject object = new JSONObject();
        try {
            object.put("key", words);
            object.put("limit", limit);
            object.put("after", afterArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    public void setAfterArray(JSONArray array) {
        try {
            JSONObject object = array.getJSONObject(0);
            JSONObject dataObject = JsonParse.getResultObj(object);
            afterArray = dataObject.getJSONArray("last_after");
            return;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        afterArray = new JSONArray();
    }
}
