//package com.baiyi.jj.app.activity.user.channel;
//
//import android.content.Intent;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.Button;
//
//import com.baiyi.core.util.ContextUtil;
//import com.baiyi.jj.app.activity.BaseActivity;
//import com.baiyi.jj.app.activity.main.HomeTabAct;
//import com.baiyi.jj.app.adapter.GridSpacingItemDecoration;
//import com.baiyi.jj.app.adapter.MyRecyclerGridAdapter;
//import com.baiyi.jj.app.application.CmsApplication;
//import com.baiyi.jj.app.cache.Dao.ChannelDao;
//import com.baiyi.jj.app.entity.ChannelItem;
//import com.baiyi.jj.app.entity.UserInfoEntity;
//import com.baiyi.jj.app.utils.ArticleHistoryUtils;
//import com.baiyi.jj.app.utils.Utils;
//import com.baiyi.jj.app.views.MyLoadingBar;
//import com.turbo.turbo.mexico.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
///**
// * Created by Administrator on 2016/11/3 0003.
// */
//public class ChannelInitActi extends BaseActivity{
//
//    @Bind(R.id.channer_recycler)
//    RecyclerView recyclerView;
//    @Bind(R.id.btn_startread)
//    Button btnStartRead;
//    @Bind(R.id.channel_manager_loading)
//    MyLoadingBar progressBar;
//
//    private MyRecyclerGridAdapter recyclerGridAdapter = null;
//
//    private List<ChannelItem> UserList;
//    private List<ChannelItem> OtherList;
//
//    private int CheckedNum;
//
//    @Override
//    protected void initWin(boolean hasScrollView, boolean isAnimal) {
//        super.initWin(false, true);
//
//        View contentView = ContextUtil.getLayoutInflater(this).inflate(R.layout.act_channelinit, null);
//        win_content.addView(contentView);
//        ButterKnife.bind(this);
//
//        btnStartRead.setEnabled(false);
//        btnStartRead.setClickable(false);
//
//        initRecycle();
//        loadChannelData();
//    }
//
//
//    @OnClick(R.id.btn_startread)
//    public void onClick() {
////        startLoad();
//        UserInfoEntity entity = CmsApplication.getUserInfoEntity();
//        if (entity == null){
////            startLoad();
//            Intent intent = new Intent(ChannelInitActi.this, HomeTabAct.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//            startActivity(intent);
//            return;
//        }
//        String[] Channelid = Utils.getStringToList(recyclerGridAdapter.getCheckData());
//        new ChannelDao(this).add(recyclerGridAdapter.getCheckData());
//        ChannelDataUtils.getInstance().updateNetChannel(this, entity.getMID(),
//                Channelid,new ChannelDataUtils.ChannelSaveCallBack(){
//                    @Override
//                    public void onSaveCallBack(boolean isComplete, String errorMsg) {
////                        startLoad();
////                        Intent intent = new Intent(ChannelInitActi.this, HomeTabAct.class);
////                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
////                        startActivity(intent);
//                    }
//                });
//        Intent intent = new Intent(ChannelInitActi.this, HomeTabAct.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//        startActivity(intent);
//    }
//    private void initRecycle() {
//
//
//        recyclerGridAdapter = new MyRecyclerGridAdapter(this);
//        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
//
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, BaseActivity.getDensity_int(4), false));
//
//        recyclerView.setAdapter(recyclerGridAdapter);
//        recyclerGridAdapter.setItemClick(new MyRecyclerGridAdapter.OnItemClick() {
//
//            @Override
//            public void onClick(ChannelItem entity,int postion) {
//                if (recyclerGridAdapter.getCheckData().size()>=3){
//                    btnStartRead.setEnabled(true);
//                    btnStartRead.setClickable(true);
//                }else {
//                    btnStartRead.setEnabled(false);
//                    btnStartRead.setClickable(false);
//                }
//            }
//        });
//    }
//
//
//    protected void loadChannelData() {
//        startLoad();
//        loadAnonymity(new AnonymityLister() {
//            @Override
//            public void setAnonymityLister(UserInfoEntity infoEntity) {
//                if (infoEntity == null){
//                    stopLoad();
//                    return;
//                }
//                ChannelDataUtils.getInstance().loadChannelList(ChannelInitActi.this, new ChannelDataUtils.ChannelResultCallBack() {
//
//                            @Override
//                            public void onResultCallBack(
//                                    List<ChannelItem> userChannelList,
//                                    List<ChannelItem> otherChannelList) {
//
//                                stopLoad();
//                                btnStartRead.setEnabled(true);
//                                btnStartRead.setClickable(true);
//                                setAdapterData(userChannelList,otherChannelList);
//                            }
//                        },
//                        ChannelDataUtils.Channel_News,
//                        infoEntity.getMID(),false);
//            }
//        });
//
//    }
//
//    private void setAdapterData(List<ChannelItem> userChannelList,
//                                List<ChannelItem> otherChannelList){
//            List<ChannelItem> list = new ArrayList<>();
//        if (userChannelList == null || otherChannelList == null){
//            return;
//        }
//        list.addAll(userChannelList);
//        list.addAll(otherChannelList);
//
//        recyclerGridAdapter.setData(list);
//        recyclerGridAdapter.setCheckData(userChannelList);
//    }
//
//
//    private void startLoad() {
//        progressBar.start();
//        progressBar.setVisibility(View.VISIBLE);
//        progressBar.setProgressInfo(getResources().getString(R.string.txt_progress_loading));
//
//    }
//    private void stopLoad() {
//        progressBar.stop();
//        progressBar.setVisibility(View.GONE);
//
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        return true;
//    }
//}
