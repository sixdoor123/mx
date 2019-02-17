package com.baiyi.jj.app.activity.user.center;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.user.about.MemberWebViewActivity;
import com.baiyi.jj.app.adapter.CreditsHisAdatper;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.entity.CreditsEntity;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.utils.TLog;
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
 * ������ʷ����
 */
public class   CreditsHistoryAct extends BaseActivity {

    @Bind(R.id.text_right)
    TextView titleRight;
    @Bind(R.id.title_name)
    LetterSpacingTextView titleName;
    @Bind(R.id.list_credits)
    XRecyclerView listCredits;
    @Bind(R.id.img_back)
    ImageView imgBack;
    private int page = 1;

    private List<CreditsEntity> dataList = null;
    private CreditsHisAdatper creditsHisAdatper;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);

        View titleView = ContextUtil.getLayoutInflater(this).inflate(R.layout.title_left, null);
        win_title.addView(titleView);
        View contentView = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_credits_history, null);
        win_content.addView(contentView);
        ButterKnife.bind(this);
        titleName.setText(getResources().getString(R.string.txt_credits_history));
//        titleName.setTypeface(CmsApplication.getTitleFace(this));
        titleRight.setText(getResources().getString(R.string.txt_policies));
        titleRight.setVisibility(View.VISIBLE);
        initListView();
    }

    /**
     * ��ʼ����ʷ��¼�б�
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
        creditsHisAdatper = new CreditsHisAdatper(this);
        listCredits.setAdapter(creditsHisAdatper);

        if (!listCredits.isRefreshing()) {
            loadData();
            listCredits.smoothScrollToPosition(0);
            listCredits.refresh();
        }
    }

    private void loadData() {
        JsonLoader loader = new JsonLoader(this);
        loader.setUrl(NetUtils.getCreditsStatementList(page));
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
                TLog.d("TAG",array.toString());
                dataList = JsonParse.getCreditsHistoryList(array);

                if (1 == page){
                    creditsHisAdatper.setData(dataList);
                    listCredits.refreshComplete();
                }else{
                    creditsHisAdatper.addData(dataList);
                    listCredits.loadMoreComplete();
                }

            }
        });

        CmsApplication.getDataStratey().startLoader(loader);

    }

    @OnClick(R.id.img_back)
    public void onBackClick() {
        setExitSwichLayout();
    }
    @OnClick(R.id.text_right)
    public void onRightClick() {
        Intent intent = new Intent(this, MemberWebViewActivity.class);
        intent.putExtra(Define.MemberNAME, "credits");
        startActivity(intent);
    }

}
