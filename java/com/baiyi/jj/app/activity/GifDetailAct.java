package com.baiyi.jj.app.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baiyi.jj.app.adapter.GifListAdatper;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.entity.article.GifEntity;
import com.baiyi.jj.app.net.NewsDetailUtils;
import com.baiyi.jj.app.theme.FontUtil;
import com.baiyi.jj.app.utils.DateUtils;
import com.baiyi.jj.app.utils.Utils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.turbo.turbo.mexico.R;

import java.util.List;

import butterknife.Bind;

/**
 * Created by Administrator on 2017/3/23.
 */

public class GifDetailAct extends BaseNewsDetailAct {

    TextView tvGifTitle;
    TextView tvGifTime;

    @Bind(R.id.gif_list)
    XRecyclerView xRecyclerView;
    @Bind(R.id.item_gif_list)
    LinearLayout linGifParent;

    GifListAdatper gifListAdatper;

    public void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(true, true);
        linGifParent.setVisibility(View.VISIBLE);
    }

    @Override
    public void initContent() {
        initAdapter();
        setWebTextSize(0);
        startProgress();
        newsDetailUtils.loadGifList(this, ArticleId, new NewsDetailUtils.GetGifCallBack() {
            @Override
            public void getGifBack(final List<GifEntity> list) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        stopProgress();
                        if (Utils.isStringEmpty(list)) {
                            return;
                        }
                        GifEntity gifEntity = list.get(0);
                        tvGifTitle.setText(gifEntity.getTitle());
                        tvGifTime.setText(gifEntity.getSource() + "    " +
                                DateUtils.getTimeYMDHMS(DateUtils.getTimeSecond(gifEntity.getCreate_date(), false)));
                        gifListAdatper.setData(gifEntity.getImgList());
                    }
                });
            }
        });

    }

    private void initAdapter() {
        gifListAdatper = new GifListAdatper(this);
        //解决滑动卡动，重写canScrollVertically()方法，返回false
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        xRecyclerView.setPullRefreshEnabled(false);
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setAdapter(gifListAdatper);
        initHeadView();

    }

    private void initHeadView() {
        View headview = LayoutInflater.from(this).inflate(R.layout.head_localnews, null);
        tvGifTitle = (TextView) headview.findViewById(R.id.local_title);
        tvGifTitle.setTypeface(CmsApplication.getListTitleFace(this));
        tvGifTime = (TextView) headview.findViewById(R.id.local_time);
        xRecyclerView.addHeaderView(headview);
    }

    @Override
    public void setWebTextSize(int a) {
        float titleSize = FontUtil.getGifTitleSize();
        tvGifTitle.setTextSize(titleSize);
    }
}
