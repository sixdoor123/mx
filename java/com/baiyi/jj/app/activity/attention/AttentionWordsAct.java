package com.baiyi.jj.app.activity.attention;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baiyi.core.file.Preference;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.attention.net.AttetionNet;
import com.baiyi.jj.app.activity.main.AttentionPager;
import com.baiyi.jj.app.adapter.SearchAttentionAdapter;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.imgtools.GlideTool;
import com.baiyi.jj.app.manager.ChannelDataManager;
import com.baiyi.jj.app.utils.ArticleHistoryUtils;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.utils.XMLName;
import com.baiyi.jj.app.views.LetterSpacingTextView;
import com.baiyi.jj.app.views.MyScrollView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.turbo.turbo.mexico.R;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ��ע�ȴʽ���
 * ���ߣ�lizl on 2016/11/28 11:02
 */
public class AttentionWordsAct extends BaseActivity {

    @Bind(R.id.img_search)
    ImageView imgSearch;
    @Bind(R.id.title_name)
    LetterSpacingTextView titleName;
    @Bind(R.id.attention_menu)
    AttentionMenuView menuView;
    @Bind(R.id.attention_list)
    XRecyclerView mListView;
    @Bind(R.id.img_channel_pic)
    ImageView channalPic;
    @Bind(R.id.tv_channel_title)
    TextView channalName;
    @Bind(R.id.scroll)
    MyScrollView scrollView;
    private String channelID;
    private SearchAttentionAdapter attentionAdapter;

//    @Bind(R.id.guide_layout)
//    RelativeLayout guideLayout;
//    @Bind(R.id.btn_enter)
//    ImageButton btnEnter;

    private GestureDetector gestureDetector;
    private int normalWidth = getDensity_int(92);

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);

        View titleView = ContextUtil.getLayoutInflater(this).inflate(R.layout.title_left, null);
        win_title.addView(titleView);
        View view = ContextUtil.getLayoutInflater(AttentionWordsAct.this).inflate(R.layout.act_attention_words, null);
        win_content.addView(view);
        ButterKnife.bind(this);

        titleName.setText(getResources().getString(R.string.txt_attention_more));
        imgSearch.setVisibility(View.VISIBLE);

        gestureDetector = new GestureDetector(gestureListener);
        scrollView.setGestureDetector(gestureDetector);
        scrollView.getLayoutParams().width = getScreenWidth() * 3 / 4;

        initRecycleView();
        loadMenu();
    }

    private void initRecycleView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mListView.setLayoutManager(layoutManager);
        mListView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mListView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mListView.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                loadUnAttentionList(true, channelID);
            }

            @Override
            public void onLoadMore() {
                mListView.loadMoreComplete();
            }
        });
        attentionAdapter = new SearchAttentionAdapter(this);
        mListView.setAdapter(attentionAdapter);

    }

    /**
     * ��ȡ����������
     */
    private void loadUnAttentionList(boolean isNet, final String channelid) {

        new AttetionNet().loadAllWordsList(this, channelid, new AttetionNet.LoadWordsCallBack() {
            @Override
            public void callBack(final List<AttentionWordsEntity> listEntities) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mListView.isRefreshing()) {
                            mListView.refreshComplete();
                        }
                        if (channelID.equals(channelid)) {
                            attentionAdapter.setData(listEntities);
                            initGuideView();
                        }
                    }
                });
            }
        }, isNet);

    }

    private void initGuideView(){
        if (!Preference.getInstance().getBoolean(XMLName.XML_First_Tag_Guide, true)) {
            return;
        }
        Preference.getInstance().Set(XMLName.XML_First_Tag_Guide, String.valueOf(false));
        Preference.getInstance().saveConfig();
//        guideLayout.setVisibility(View.VISIBLE);
    }
//    @OnClick(R.id.btn_enter)
//    public void onButtonClick() {
//        guideLayout.setVisibility(View.GONE);
//    }

    /**
     * ˢ��Ƶ���б�����
     */
    private void loadMenu() {

        loadAnonymity(new AnonymityLister() {

            @Override
            public void setAnonymityLister(UserInfoEntity infoEntity) {
                if (infoEntity == null) {
                    return;
                }
                ChannelDataManager.getInstance(AttentionWordsAct.this).loadCacheChannel(ChannelDataManager.ChannelType_News, new ChannelDataManager.ChannelResultCallBack() {
                    @Override
                    public void onResultCallBack(List<ChannelItem> userChannelList, List<ChannelItem> otherChannelList) {
                        ArrayList<ChannelItem> allChannelList = new ArrayList<ChannelItem>();
                        allChannelList.clear();
                        if (!Utils.isStringEmpty(userChannelList)) {
                            allChannelList.addAll(userChannelList);
                        }
                        if (!Utils.isStringEmpty(otherChannelList)) {
                            allChannelList.addAll(otherChannelList);
                        }
                        if (Utils.isStringEmpty(allChannelList)) {
                            return;
                        }
                        for (int k = 0; k < allChannelList.size(); k++) {
                            if (allChannelList.get(k).getChannel_name().equals("Top")) {
                                allChannelList.remove(k);
                            }
                        }
                        displayMenu(allChannelList);
                        channelID = allChannelList.get(0).getCid();
                        channalName.setText(allChannelList.get(0).getChannel_name());
                        GlideTool.getListImage(AttentionWordsAct.this, allChannelList.get(0).getConvert_img(), channalPic);
                        loadUnAttentionList(false, channelID);
                    }
                });
            }
        });

    }

    /**
     * ��ʾmenu������
     */
    protected void displayMenu(ArrayList<ChannelItem> channelEntities) {

        if (!mListView.isRefreshing()) {
            mListView.refresh();
        }
        menuView.displayViews(0, channelEntities, new AttentionMenuView.MenuOnclickLister() {
            @Override
            public void setMenuOnclickLister(int position, ChannelItem entity) {

                channalName.setText(entity.getChannel_name());
                GlideTool.getListImage(AttentionWordsAct.this, entity.getConvert_img(), channalPic);

                channelID = entity.getCid();
                attentionAdapter.setDataNull();
                if (!mListView.isRefreshing()) {
                    mListView.smoothScrollToPosition(0);
                    mListView.refresh();
                    mListView.init();
                }
                loadUnAttentionList(false, entity.getCid());
            }
        }, new AttentionMenuView.MenuCheckOnclickLister() {
            @Override
            public void setMenuCheckOnclickLister() {
                if (scrollView.getLayoutParams().width == menuView.maxWidth){
                    menuView.setMenuWidth(true, scrollView, normalWidth);
                }
            }
        });
    }

    @OnClick({com.turbo.turbo.mexico.R.id.img_back, R.id.img_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                exit();
                break;
            case R.id.img_search:
                Intent intent = new Intent(this, SearchAttentionAct.class);
                startActivity(intent);
                break;
        }
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        exit();
        return true;
    }

    private void exit() {
        setResult(AttentionPager.AttetCode);
        setExitSwichLayout();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AttentionPager.AttetCode) {
            mListView.refresh();
            loadUnAttentionList(false, channelID);

        }
    }

    GestureDetector.OnGestureListener gestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
//            if (motionEvent1.getRawX()<normalWidth){
//                return  false;
//            }
//            menuView.setMenuWidth(false, scrollView, (int) (motionEvent1.getX()));
            return true;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float v1, float v2) {
            if (e2.getX() - e1.getX() > 100 && Math.abs(v1) > 50) {
                menuView.setMenuWidth(false, scrollView, menuView.maxWidth);
            } else if (e1.getX() - e2.getX() > 100 && Math.abs(v2) > 50) {
                menuView.setMenuWidth(true, scrollView, 0);
            } else {
                return false;
            }
            return true;
        }
    };


}
