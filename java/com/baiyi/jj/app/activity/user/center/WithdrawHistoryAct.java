package com.baiyi.jj.app.activity.user.center;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.adapter.WithdrawHisAdapter;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.entity.WithdrawEntity;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.views.LetterSpacingTextView;
import com.turbo.turbo.mexico.R;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONArray;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ��ֵ��¼����
 * Created by tangkun on 16/9/8.
 */
public class WithdrawHistoryAct extends BaseActivity {

    @Bind(R.id.title_name)
    LetterSpacingTextView titleName;
    @Bind(R.id.list_credits)
    XRecyclerView listCredits;
    @Bind(R.id.img_back)
    ImageView imgBack;
    private int page = 1;

    private List<WithdrawEntity> dataList = null;
    private WithdrawHisAdapter withdrawHisAdapter;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);

        View titleView = ContextUtil.getLayoutInflater(this).inflate(R.layout.title_left, null);
        win_title.addView(titleView);
        View contentView = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_withdraw_history, null);
        win_content.addView(contentView);
        ButterKnife.bind(this);
        titleName.setText(getResources().getString(R.string.txt_withdraw_history));
//        titleName.setTypeface(CmsApplication.getTitleFace(this));
        initListView();
    }

    /**
     * 初始化历史记录列表
     */
    private void initListView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listCredits.setLayoutManager(layoutManager);

        listCredits.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        listCredits.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        listCredits.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                // TODO Auto-generated method stub
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        page = 1;
                        loadData();
                    }

                }, 2000);
            }

            @Override
            public void onLoadMore() {
                // TODO Auto-generated method stub
                page++;
                loadData();

            }
        });
        withdrawHisAdapter = new WithdrawHisAdapter(this);
        listCredits.setAdapter(withdrawHisAdapter);

        if (!listCredits.isRefreshing()) {
            loadData();
            listCredits.smoothScrollToPosition(0);
            listCredits.refresh();
        }
    }

    private void loadData() {
        JsonLoader loader = new JsonLoader(this);
        loader.setUrl(NetUtils.getWithdrawHistoryList(page));
        loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
        loader.setMethod(BaseNetLoder.Method_Get);
        loader.setLoaderListener(new Loader.LoaderListener() {

            public void onProgress(Object arg0, long arg1, long arg2) {

            }

            public void onError(Object arg0, int arg1, String arg2) {
                listCredits.refreshComplete();
                listCredits.loadMoreComplete();
                displayToast(getString(R.string.tip_loaddata_failure));
            }

            public void onCompelete(Object arg0, Object arg1) {

                JSONArray array = (JSONArray) arg1;
                Log.d("TAG",array.toString());
                dataList = JsonParse.getWithdrawHistoryList(array);

                if (1 == page){
                    withdrawHisAdapter.setData(dataList);
                    listCredits.refreshComplete();
                }else{
                    withdrawHisAdapter.addData(dataList);
                    listCredits.loadMoreComplete();
                }

            }
        });

        CmsApplication.getDataStratey().startLoader(loader);

    }

    @OnClick(R.id.img_back)
    public void onClick() {
        setExitSwichLayout();
    }

}
