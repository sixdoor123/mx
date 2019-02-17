package com.baiyi.jj.app.activity.user;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.adapter.CollectAdapter;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.dialog.QuitDialog;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.listitem.search.SearchHolder;
import com.baiyi.jj.app.net.CollectUtils;
import com.baiyi.jj.app.net.CollectUtils.DeleteCollectCall;
import com.baiyi.jj.app.utils.ArticleHistoryUtils;
import com.baiyi.jj.app.utils.IntentClassChangeUtils;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.TagUtils;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.LetterSpacingTextView;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.baiyi.jj.app.views.pageview.JsonParse_Collect;
import com.baiyi.jj.core.basedialog.DialogBase;
import com.turbo.turbo.mexico.R;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的收藏界面
 */
public class MyCollectActivity extends BaseActivity {

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.title_name)
    LetterSpacingTextView titleName;
    @Bind(R.id.img_search)
    ImageView imgEdit;
    @Bind(R.id.img_other)
    ImageView imgOk;
    @Bind(R.id.loading)
    MyLoadingBar progressBar;
    @Bind(R.id.xr_collects)
    XRecyclerView listCollect;
    @Bind(R.id.lin_bottom)
    LinearLayout linBottom;

    private List<NewsListEntity> collectList = null;
    boolean isShow = true;

    private int pageIndex = 1;
    private CollectAdapter collectAdapter = null;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);

        View titleView = LayoutInflater.from(this).inflate(R.layout.title_left, null);
        win_title.addView(titleView);
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_mycollect, null);
        win_content.addView(contentView);
        ButterKnife.bind(this);
        initTitleView();
        initContentView();
    }


    private void initTitleView() {
        imgBack.setVisibility(View.VISIBLE);
        titleName.setText(getResources().getString(R.string.name_title_collect));
        imgOk.setVisibility(View.GONE);
        linBottom.setVisibility(View.GONE);
        imgEdit.setVisibility(View.VISIBLE);
        imgEdit.setImageResource(R.drawable.btn_edit_collect);
        imgEdit.setEnabled(false);
    }

    private void initContentView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listCollect.setLayoutManager(layoutManager);

        listCollect.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        listCollect.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        listCollect.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                pageIndex = 1;
                loadMyCollect();
            }

            @Override
            public void onLoadMore() {
                pageIndex++;
                loadMyCollect();
            }
        });

        if (!listCollect.isRefreshing()) {
            loadMyCollect();
            listCollect.smoothScrollToPosition(0);
            listCollect.refresh();
        }

        collectAdapter = new CollectAdapter(this);
        collectAdapter.setRecycleItemClick(new SearchHolder.OnItemClick() {

            @Override
            public void OnClick(int position) {
                if (position < 1 || position > collectAdapter.getItemCount()) {
                    return;
                }
                NewsListEntity entity = (NewsListEntity) collectAdapter
                        .getItem(position - 1);
                IntentClassChangeUtils.startNewsDetail(MyCollectActivity.this, entity, position, 0, true);
            }
        });
        listCollect.setAdapter(collectAdapter);

        collectAdapter.setItemIsNullCallBack(new CollectAdapter.ItemIsNullCallBack() {
            @Override
            public void isNull() {
                listCollect.init();
                errorProgressBar();
                exit();
            }
        });
    }


    /**
     * 清楚全部收藏
     */
    private void clearAll() {
        displayProgressBar();
        CollectUtils collectUtils = new CollectUtils(this, null, null);
        collectUtils.loadClearCollect(new DeleteCollectCall() {

            @Override
            public void CallBack(int state) {
                progressBarGone();
                if (state == CollectUtils.State_Success) {
                    errorProgressBar();
                    collectAdapter.clearAll();
                    listCollect.init();
                    collectList.clear();
                    isShow = false;
                    exit();
                } else {
                    displayToast(getString(R.string.tip_canel_collect_fal));
                }
            }
        });
    }

    public void loadMyCollect() {

        progressBarGone();
        JsonLoader loader = new JsonLoader(this);
        loader.setUrl(NetUtils.getMyCollectionList(pageIndex));
        loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
        loader.setMethod(BaseNetLoder.Method_Get);
        loader.setLoaderListener(new Loader.LoaderListener() {

            public void onProgress(Object arg0, long arg1, long arg2) {

            }

            public void onError(Object arg0, int arg1, String arg2) {
                listCollect.loadMoreComplete();
                listCollect.refreshComplete();
                displayToast(getResources()
                        .getString(R.string.tip_loaddata_failure));
            }

            public void onCompelete(Object arg0, Object arg1) {
                JSONArray array = (JSONArray) arg1;
                TLog.e("abc", array.toString());
                collectList = JsonParse_Collect.getCollectionList(array);

                listCollect.loadMoreComplete();
                listCollect.refreshComplete();
                if (Utils.isStringEmpty(collectList)) {

                    if (pageIndex == 1) {
                        collectAdapter.setData(new ArrayList<NewsListEntity>());
                        collectAdapter.clearAll();
                        errorProgressBar();
                    } else {
                        listCollect.loadMoreComplete();
                    }
                    return;
                }
                setLisViewSize(collectList.size());
                if (pageIndex == 1) {
                    collectAdapter.setData(collectList);
                } else {
                    collectAdapter.addData(collectList);
                }
                imgEdit.setEnabled(true);
            }
        });

        CmsApplication.getDataStratey().startLoader(loader);

    }

    private void progressBarGone() {
        progressBar.stop();
        progressBar.setVisibility(View.GONE);
    }

    private void errorProgressBar() {
        imgEdit.setEnabled(false);
        progressBar.stop();
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgressLoadError(getResources().getString(
                R.string.tip_loaddata_null), MyLoadingBar.type_sample);
    }

    private void displayProgressBar() {
        progressBar.start();
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgressInfo(getResources().getString(R.string.txt_progress_loading3));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (!isShow) {
                exit();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (isShow) {
            setExitSwichLayout();
        } else {
            collectAdapter.setEdit(isShow);
            imgEdit.setVisibility(View.VISIBLE);
            imgOk.setVisibility(View.GONE);
            linBottom.setVisibility(View.GONE);
            isShow = true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (MemberConfig.Refresh_My_Collect == resultCode) {
//            if (data.getBooleanExtra(MemberConfig.My_Collect_Is_Refresh, false)) {
            loadMyCollect();
            listCollect.smoothScrollToPosition(0);
            listCollect.refresh();
//            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.img_back, R.id.img_other, R.id.img_search, R.id.btn_clear_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                exit();
                break;
            case R.id.img_other:
                isShow = false;
                exit();
                break;
            case R.id.btn_clear_all:
                String Str = getString(R.string.name_title_collect_title);
                QuitDialog dialog = new QuitDialog(MyCollectActivity.this, DialogBase.Win_Center, Str);
                dialog.setQuitOnClickListener(new QuitDialog.QuitOnClickListener() {

                    @Override
                    public void onClickListener() {
                        clearAll();
                    }
                });
                dialog.showDialog(DialogBase.AnimalTop);
                break;
            case R.id.img_search:
                if (isShow) {//显示编辑状态
                    collectAdapter.setEdit(isShow);
                    imgEdit.setVisibility(View.GONE);
                    imgOk.setVisibility(View.VISIBLE);
                    linBottom.setVisibility(View.VISIBLE);
                    isShow = false;
                }
                break;
        }
    }
}
