package com.baiyi.jj.app.activity.user.channel;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.ChannelUtils;
import com.baiyi.jj.app.activity.main.HomeTabAct;
import com.baiyi.jj.app.activity.main.NewsPager;
import com.baiyi.jj.app.cache.Dao.ChannelDao;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.manager.ChannelDataManager;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.LetterSpacingTextView;
import com.baiyi.jj.app.views.RecyScrollView;
import com.baiyi.jj.app.views.pulldownview.PullRefreshRecyScrollView;
import com.baiyi.jj.app.views.pulldownview.PullToRefreshBase;
import com.baiyi.jj.app.views.pulldownview.PullToRefreshScrollView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.turbo.turbo.mexico.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/19 0019.
 */
public class UsChannelNewAct extends BaseActivity {
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.title_name)
    LetterSpacingTextView titleName;
    @Bind(R.id.img_search)
    ImageView imgEdit;
    @Bind(R.id.txt_addmore)
    TextView addMoreView;
    @Bind(R.id.channel_list_top)
    RecyclerView topRecyclerView;
    @Bind(R.id.channel_list_bottom)
    RecyclerView bottomRecyclerView;
    @Bind(R.id.refrsh_pull)
    PullRefreshRecyScrollView mPullRefreshScrollView;

    ChannelNewAdapter topAdapter;
    ChannelNewAdapter bottomAdapter;

    boolean isListChange = false;

    ChannelDataManager channelDataManager;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);

        View titleView = ContextUtil.getLayoutInflater(this).inflate(R.layout.title_left, null);
        win_title.addView(titleView);
        View contentView = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_channel_us2, null);
        win_content.addView(contentView);
        ButterKnife.bind(this);

        channelDataManager = ChannelDataManager.getInstance(UsChannelNewAct.this);
        initTitle();
        initList();
        loadChannelByUser(false);
    }

    private void initTitle() {
        titleName.setText(getResources().getString(R.string.title_channel));
//        titleName.setTypeface(CmsApplication.getTitleFace(this));
        imgBack.setVisibility(View.VISIBLE);
        imgEdit.setImageResource(R.drawable.btn_edit_done);
        imgEdit.setVisibility(View.VISIBLE);
        imgEdit.setSelected(false);

    }

    private void initList() {
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        topRecyclerView.setLayoutManager(lm);

        mPullRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<RecyScrollView>() {

            @Override
            public void onRefresh(PullToRefreshBase<RecyScrollView> refreshView) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        loadChannelByUser(true);
                    }
                }, 2000);
            }
        });

//        topRecyclerView.setNoMore();
//        topRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
//
//            @Override
//            public void onRefresh() {
//                // TODO Auto-generated method stub
//                new Handler().postDelayed(new Runnable() {
//                    public void run() {
//                        loadChannelByUser(true);
//                    }
//
//                }, 2000);
//            }
//
//            @Override
//            public void onLoadMore() {
//                // TODO Auto-generated method stub
//
//            }
//        });

        LinearLayoutManager lm2 = new LinearLayoutManager(this);
        lm2.setOrientation(LinearLayoutManager.VERTICAL);
        bottomRecyclerView.setLayoutManager(lm2);

        topAdapter = new ChannelNewAdapter(this, true);
        bottomAdapter = new ChannelNewAdapter(this, false);
        topRecyclerView.setAdapter(topAdapter);
        bottomRecyclerView.setAdapter(bottomAdapter);

        bottomAdapter.setBottomCheckClick(new ChannelNewAdapter.BottomCheckClick() {
            @Override
            public void onClick(ChannelItem item, View contentView, int postion) {
//                if (isMove){
//                    return;
//                }
//                AddMoveDown();
//                moveToTop(contentView,item,postion);
                item.setIs_default("true");
                topAdapter.addItem(item);
                bottomAdapter.removeItem(postion);
                isListChange = true;
            }
        });

        topAdapter.setTopCheckClick(new ChannelNewAdapter.TopCheckClick() {
            @Override
            public void onClick(ChannelItem item, View contentView, int postion) {

                topAdapter.removeItem(postion);
                item.setIs_default("false");
                bottomAdapter.addItem(item, 0);
                isListChange = true;
            }
        });

    }

    @OnClick({R.id.img_back, R.id.img_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                back();
                break;
            case R.id.img_search: //编辑
                if (!imgEdit.isSelected()) {
                    showEditView();
                } else {
                    hideEditView();
                }
                break;
        }
    }

    private void showEditView() {
        imgEdit.setSelected(true);
        topAdapter.setChecked(false);
    }

    private void hideEditView() {
        imgEdit.setSelected(false);
        topAdapter.setChecked(true);
    }

    private void back() {
        if (!imgEdit.isSelected()) {
            List<ChannelItem> topList = topAdapter.getListDatas();
            List<ChannelItem> bottomList = bottomAdapter.getListDatas();
            saveChannel(topList, bottomList);
        } else {
            hideEditView();
        }
    }

    private void loadChannelByUser(final boolean isLoadNet) {

        if (isLoadNet) {
            channelDataManager.loadNetChannelList(ChannelDataManager.ChannelType_News, new ChannelDataManager.ChannelResultCallBack() {
                @Override
                public void onResultCallBack(List<ChannelItem> userChannelList, List<ChannelItem> otherChannelList) {
                    setListData(userChannelList, otherChannelList);
                }
            });
            return;
        }

        channelDataManager.loadCacheChannel(ChannelDataManager.ChannelType_News, new ChannelDataManager.ChannelResultCallBack() {
            @Override
            public void onResultCallBack(List<ChannelItem> userChannelList, List<ChannelItem> otherChannelList) {
                setListData(userChannelList, otherChannelList);
            }
        });


    }

    private void setListData(List<ChannelItem> userChannelList, List<ChannelItem> otherChannelList) {
        mPullRefreshScrollView.onRefreshComplete();
        if (Utils.isStringEmpty(userChannelList) && Utils.isStringEmpty(otherChannelList)) {
            return;
        }
        topAdapter.setData(userChannelList);
        bottomAdapter.setData(otherChannelList);
    }

    private void saveChannel(final List<ChannelItem> topChannel, final List<ChannelItem> bottomChannel) {

        if (topAdapter != null && topAdapter.getItemCount() == 0 && bottomAdapter.getItemCount() == 0) {
            exit();
            return;
        }
        if (Utils.isStringEmpty(topChannel) || topChannel.size() < 3) {
            displayToast(getResources().getString(R.string.txt_choose_3channel));
//            exit();
            return;
        }
        if (!isListChange) {
            exit();
            return;
        }
        final List<ChannelItem> lItems = new ArrayList<ChannelItem>();
        lItems.clear();
        lItems.addAll(topChannel);

        checkLogin(new AnonymityLister() {

            @Override
            public void setAnonymityLister(UserInfoEntity infoEntity) {
                if (infoEntity == null) {
                    return;
                }
                TLog.e("channel","call--back--login");

                channelDataManager.updateNetChannel(ChannelDataManager.ChannelType_News, topChannel, bottomChannel, new ChannelDataManager.ChannelSaveCallBack() {
                    @Override
                    public void onSaveCallBack(boolean isComplete, String errorMsg) {
                        TLog.e("channel", "call--back--");
                        if (isComplete) {
                            backNewsList(lItems, false);
                        }
                    }
                },"class-channelnew--back");
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        back();
        return true;
    }

    private void backNewsList(List<ChannelItem> lItems, boolean isSingle) {
        int result = 0;
        Intent intent = new Intent(this, HomeTabAct.class);
        result = ChannelUtils.Result_News;
        intent.putExtra(Define.ChannelId, (Serializable) lItems);
        intent.putExtra(Define.IsChannelSingle, isSingle);
        setResult(result, intent);
        exit();

    }

    private void exit() {
        setExitSwichLayout();
    }


}
