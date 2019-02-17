package com.baiyi.jj.app.activity.user.channel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.ChannelUtils;
import com.baiyi.jj.app.activity.main.NewsPager;
import com.baiyi.jj.app.activity.user.entity.LanguageEntities;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.cache.Dao.ChannelDao;
import com.baiyi.jj.app.cache.Dao.OfflineChannelDao;
import com.baiyi.jj.app.cache.bean.OfflineChannelBean;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.imgtools.GlideTool;
import com.baiyi.jj.app.language.SwitchLanguageUtils;
import com.baiyi.jj.app.manager.ChannelDataManager;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.FontSetView;
import com.baiyi.jj.app.views.LetterSpacingTextView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.turbo.turbo.mexico.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/14 0014.
 */
public class OfflineAct extends BaseActivity{

    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.title_name)
    LetterSpacingTextView titleName;
    @Bind(R.id.img_search)
    ImageView imgEdit;
    @Bind(R.id.txt_addmore)
    TextView addMoreView;
    @Bind(R.id.offline_list_check)
    RecyclerView topRecyclerView;
    @Bind(R.id.offline_list_uncheck)
    RecyclerView bottomRecyclerView;

    @Bind(R.id.lin_downsize)
    LinearLayout linDownSize;
    @Bind(R.id.lin_cleardown)
    LinearLayout linClearDown;
    @Bind(R.id.rg_downsize)
    RadioGroup rgOffline;
    private FontSetView setViewFont = null;
    private String[] OfflineSizeStr = new String[3];

    private OfflineAdapter topAdapter;
    private OfflineAdapter bottomAdapter;

    private boolean isMove = false;

    int itemHeight = 60;
    int animDur = 1000;

    private OfflineChannelDao channelDao;
    boolean isListChange = false;

    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);

        View titleView = ContextUtil.getLayoutInflater(this).inflate(R.layout.title_left, null);
        win_title.addView(titleView);
        View contentView = ContextUtil.getLayoutInflater(this).inflate(R.layout.act_offline, null);
        win_content.addView(contentView);
        ButterKnife.bind(this);

        initTitle();
        initRadio();
        initList();
        channelDao = new OfflineChannelDao(this);
        loadChannelByUser(false);
    }
    private void initTitle() {
        titleName.setText(getResources().getString(R.string.txt_offline_reading));
//        titleName.setTypeface(CmsApplication.getTitleFace(this));
        imgBack.setVisibility(View.VISIBLE);
        imgEdit.setImageResource(R.drawable.btn_edit_done);
        imgEdit.setVisibility(View.VISIBLE);
        imgEdit.setSelected(false);

        linDownSize.setVisibility(View.VISIBLE);
        linClearDown.setVisibility(View.GONE);
    }

    @OnClick({R.id.img_back, R.id.img_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                exit();
                break;
            case R.id.img_search: //编辑
                if (!imgEdit.isSelected()){
                    showEditView();
                }else {
                    hideEditView();
                }
                break;
        }
    }
    private void showEditView(){
        imgEdit.setSelected(true);
        topAdapter.setChecked(false);
//        linClearDown.setVisibility(View.VISIBLE);
//        linDownSize.setVisibility(View.GONE);
        linClearDown.setVisibility(View.GONE);
        linDownSize.setVisibility(View.VISIBLE);
    }
    private void hideEditView(){
        imgEdit.setSelected(false);
        topAdapter.setChecked(true);
        linClearDown.setVisibility(View.GONE);
        linDownSize.setVisibility(View.VISIBLE);
    }

    private void exit() {
        if (!imgEdit.isSelected()) {
            saveOfflineChannel();
            setExitSwichLayout();
        } else {
            hideEditView();
        }
    }

    private void initRadio(){
        OfflineSizeStr[0] = "10";
        OfflineSizeStr[1] = "20";
        OfflineSizeStr[2] = "30";
        setViewFont = new FontSetView(this, rgOffline, OfflineSizeStr, AccountManager.getInstance().getOffline_Size());
        rgOffline.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                AccountManager manager = AccountManager.getInstance();
                if(checkedId == R.id.id_rb_0)
                {
                    manager.setOffline_Size(0);
                }else if(checkedId == R.id.id_rb_1)
                {
                    manager.setOffline_Size(1);
                }else if(checkedId == R.id.id_rb_2)
                {
                    manager.setOffline_Size(2);
                }
            }
        });
    }
    private void loadChannelByUser(final boolean isLoadNet) {
        loadAnonymity(new AnonymityLister() {

            @Override
            public void setAnonymityLister(UserInfoEntity infoEntity) {
                if (infoEntity == null){
                    return;
                }
                loadChannelData(infoEntity.getMID(), isLoadNet);
            }
        });


    }

    private void saveOfflineChannel(){
        if (!isListChange){
            return;
        }
        List<OfflineChannelBean> allList = new ArrayList<>();
        List<OfflineChannelBean> topChannel = topAdapter.getListDatas();
//        for (OfflineChannelBean item : topChannel){
//            item.setIsoffline(true);
//            allList.add(item);
//        }
        List<OfflineChannelBean> bottomChannel = bottomAdapter.getListDatas();
//        for (OfflineChannelBean item : bottomChannel){
//            item.setIsoffline(false);
//            allList.add(item);
//        }
        allList.addAll(topChannel);
        allList.addAll(bottomChannel);
        String language = SwitchLanguageUtils.getCurrentLanguage();
        channelDao.deletebyLanguage(language);
        channelDao.add(allList);
        List<OfflineChannelBean> topList = channelDao.queryByOffline(true,language);
        TLog.e("abc",topList.size()+"========"+language);

    }
    public class SortComparator implements Comparator {
        @Override
        public int compare(Object lhs, Object rhs) {
            ChannelItem item = (ChannelItem) lhs;
            ChannelItem item2 = (ChannelItem) rhs;


            return (item2.getChannel_index() - item.getChannel_index());
        }
    }


    private void loadChannelData(String mid, boolean isLoadnet) {

        String language = SwitchLanguageUtils.getCurrentLanguage();
        List<OfflineChannelBean> topList = channelDao.queryByOffline(true,language);
        List<OfflineChannelBean> bottomList = channelDao.queryByOffline(false,language);
        if (!Utils.isStringEmpty(topList) || !Utils.isStringEmpty(bottomList)){
//            ChannelItem item = new ChannelItem();
//            item.setChannel_name("For you");
            if ((topList.size()+bottomList.size())>5){
                topAdapter.setData(topList);
                bottomAdapter.setData(bottomList);
                return;
            }
        }
        ChannelDataManager.getInstance(this).loadCacheChannel(ChannelDataManager.ChannelType_News, new ChannelDataManager.ChannelResultCallBack() {
            @Override
            public void onResultCallBack(List<ChannelItem> userChannelList, List<ChannelItem> otherChannelList) {
                if (Utils.isStringEmpty(userChannelList) && Utils.isStringEmpty(otherChannelList)) {
                    return;
                }
                List<OfflineChannelBean> topList = swichToOffline(userChannelList,true);
                List<OfflineChannelBean> bottomList = swichToOffline(otherChannelList,false);
                topAdapter.setData(topList);
                bottomAdapter.setData(bottomList);
                isListChange = true;
            }
        });

    }

    private List<OfflineChannelBean> swichToOffline(List<ChannelItem> userlist,boolean isOffline){
        List<OfflineChannelBean> channelBeanList = new ArrayList<>();
        String language = SwitchLanguageUtils.getCurrentLanguage();
        for (ChannelItem item : userlist){
            OfflineChannelBean bean = new OfflineChannelBean();
            bean.setChannelid(item.getCid());
            if (item.getCid().equals("26073")){
                continue;
            }
            bean.setChannelname(item.getChannel_name());
            bean.setChannelcover(item.getConvert_img());
            bean.setIsoffline(isOffline);
            bean.setLanguage(language);
            channelBeanList.add(bean);
        }
        return channelBeanList;
    }

    private void initList(){
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        topRecyclerView.setLayoutManager(lm);
        LinearLayoutManager lm2 = new LinearLayoutManager(this);
        lm2.setOrientation(LinearLayoutManager.VERTICAL);
        bottomRecyclerView.setLayoutManager(lm2);

        topAdapter = new OfflineAdapter(this,true);
        bottomAdapter = new OfflineAdapter(this,false);
        topRecyclerView.setAdapter(topAdapter);
        bottomRecyclerView.setAdapter(bottomAdapter);

        bottomAdapter.setBottomCheckClick(new OfflineAdapter.BottomCheckClick() {
            @Override
            public void onClick(OfflineChannelBean item, View contentView,int postion) {
//                if (isMove){
//                    return;
//                }
//                AddMoveDown();
//                moveToTop(contentView,item,postion);
                item.setIsoffline(true);
                topAdapter.addItem(item);
                bottomAdapter.removeItem(postion);
                isListChange = true;
            }
        });

        topAdapter.setTopCheckClick(new OfflineAdapter.TopCheckClick() {
            @Override
            public void onClick(OfflineChannelBean item, View contentView, int postion) {
                if (item.getChannelid().equals("-100")){
                    return;
                }
                topAdapter.removeItem(postion);
                item.setIsoffline(false);
                bottomAdapter.addItem(item,0);
                isListChange = true;
            }
        });

    }

    private void AddMoveDown(){
        downAnim(addMoreView,BaseActivity.getDensity_int(itemHeight));
        downAnim(bottomRecyclerView,BaseActivity.getDensity_int(itemHeight));
    }
    private void downAnim(View view,int offset){
        view.clearAnimation();
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, offset);
        animation.setDuration(animDur);
//        animation.setFillAfter(true);
        view.startAnimation(animation);
    }
    private void upAnim(View view,int offset){
        view.clearAnimation();
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -offset);
        animation.setDuration(animDur);
        animation.setFillAfter(true);

        view.startAnimation(animation);
    }


    private void moveToTop(View startView,final OfflineChannelBean item,final int postion){
        final int[] startLocation = new int[2];
        startView.getLocationInWindow(startLocation);
        final int[] endLocation = new int[2];
        topRecyclerView.getChildAt(topAdapter.getItemCount()-1).getLocationInWindow(endLocation);

        final View moveView = LayoutInflater.from(this).inflate(R.layout.item_us_channel,null);
        final TextView name = (TextView) moveView.findViewById(R.id.txt_name);
        final ImageView content = (ImageView) moveView.findViewById(R.id.img_head);
        name.setText(item.getChannelname());
        GlideTool.getListImage(this,item.getChannelcover(), content);

//        bottomAdapter.removeItem(postion);
        item.setIsoffline(true);
        topAdapter.addItem(item);
        bottomAdapter.removeItem(postion);
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                try {
//                    MoveAnim(moveView,startLocation,endLocation,item);
//                    bottomAdapter.removeItem(postion);
//                } catch (Exception localException) {
//                }
//            }
//        }, 50L);

    }

    private void MoveAnim(View moveView, int[] startLocation,
                          int[] endLocation, final ChannelItem item) {
        int[] initLocation = new int[2];
        // 获取传递过来的VIEW的坐标
        moveView.getLocationInWindow(initLocation);
        // 得到要移动的VIEW,并放入对应的容中

        final ViewGroup moveViewGroup = getMoveViewGroup();
        final View mMoveView = getMoveView(moveViewGroup, moveView,
                initLocation);

        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        float density = dm.density;

        // 创建移动动画
        TranslateAnimation moveAnimation = new TranslateAnimation(
                startLocation[0], endLocation[0], startLocation[1], endLocation[1]+BaseActivity.getDensity_int(itemHeight));
        moveAnimation.setDuration(animDur);// 动画时间
        // 动画配置
        AnimationSet moveAnimationSet = new AnimationSet(true);
        moveAnimationSet.setFillAfter(false);// 动画效果执行完毕后，View对象不保留在终止的位置
        moveAnimationSet.addAnimation(moveAnimation);
        mMoveView.startAnimation(moveAnimationSet);
        moveAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                isMove = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                moveViewGroup.removeView(mMoveView);
//                topAdapter.addItem(item);
                isMove = false;
            }
        });
    }

    /**
     * 获取移动的VIEW，放入对应ViewGroup布局容器
     *
     * @param viewGroup
     * @param view
     * @param initLocation
     * @return
     */
    private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
        int x = initLocation[0];
        int y = initLocation[1];
        viewGroup.addView(view);
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mLayoutParams.leftMargin = x;
        mLayoutParams.topMargin = y;
        view.setLayoutParams(mLayoutParams);
        return view;
    }

    /**
     * 创建移动的ITEM对应的ViewGroup布局容器
     */
    private ViewGroup getMoveViewGroup() {
        ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
        LinearLayout moveLinearLayout = new LinearLayout(this);
        moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        moveViewGroup.addView(moveLinearLayout);
        return moveLinearLayout;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
