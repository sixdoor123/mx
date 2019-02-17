package com.baiyi.jj.app.activity.main.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baiyi.core.file.Preference;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.BaseAnalyticsActivity;
import com.baiyi.jj.app.activity.ChannelUtils;
import com.baiyi.jj.app.activity.SearchDetailActivity;
import com.baiyi.jj.app.activity.main.HomeTabAct;
import com.baiyi.jj.app.activity.user.channel.OfflineAct;
import com.baiyi.jj.app.activity.user.channel.UsChannelNewAct;
import com.baiyi.jj.app.adapter.GridSpacingItemDecoration;
import com.baiyi.jj.app.adapter.MyRecyclerGridAdapter;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.cache.Dao.ConfigDao;
import com.baiyi.jj.app.cache.Dao.OfflineChannelDao;
import com.baiyi.jj.app.cache.Dao.UserDao;
import com.baiyi.jj.app.cache.bean.ConfigurationBean;
import com.baiyi.jj.app.cache.bean.OfflineChannelBean;
import com.baiyi.jj.app.dialog.SetOfflineDialog;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.language.SwitchLanguageUtils;
import com.baiyi.jj.app.manager.ChannelDataManager;
import com.baiyi.jj.app.manager.CountryMannager;
import com.baiyi.jj.app.manager.OfflineDownManager;
import com.baiyi.jj.app.manager.SortChannelComparator;
import com.baiyi.jj.app.manager.VideoPlayManager;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.TextLengthUtils;
import com.baiyi.jj.app.utils.TimerCountCallBack;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.utils.XMLName;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.turbo.turbo.mexico.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/1/10 0010.
 */
public class NewsFragment extends Fragment {


    @Bind(R.id.page_channel_manager)
    RelativeLayout pageChannelManagerGroup = null;
    @Bind(R.id.channer_recycler)
    RecyclerView recyclerView = null;
    MyRecyclerGridAdapter recyclerGridAdapter = null;
    @Bind(R.id.news_new_count_group)
    RelativeLayout newCountGroup = null;
    @Bind(R.id.news_new_count)
    protected TextView newCountTv = null;

    @Bind(R.id.lin_shownochannel)
    LinearLayout linNoChannel;
    @Bind(R.id.btn_addchannel)
    Button btnAddChannel;
    @Bind(R.id.btn_exit)
    Button btnExit = null;
    @Bind(R.id.channel_manager_loading)
    MyLoadingBar progressBar = null;


    @Bind(R.id.news_tablayout)
    TabLayout tabLayout;
    @Bind(R.id.news_viewPager)
    ViewPager viewPager;

    PageAdapter adapter;

    private String name;
    private int currentCountry;
    public List<ChannelItem> userList = new ArrayList<>();

    public static final String NewsListAction = "android.intent.action.messageNewsReceiver";
    public static final int TopTitleSmall = 16;
    public static final int TopTitleBig = 19;
    private String Mid;
    public static final int Padding = BaseActivity.getDensity_int(10);
    public static final int PaddingStart = BaseActivity.getDensity_int(0);


    private ChannelDataManager channelDataManager;

    public static NewsFragment newInstance(String channelId) {
        NewsFragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", channelId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getArguments().getString("name");
        if (CmsApplication.getUserInfoEntity() != null) {
            Mid = CmsApplication.getUserInfoEntity().getMID();
        }
        currentCountry = CountryMannager.getInstance().getCurrentCountry();
        regiesterReceiver();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);
        channelDataManager = ChannelDataManager.getInstance(getContext());
        initPager();
        return view;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initPager() {
        pageChannelManagerGroup.setVisibility(View.GONE);
        initRecycle();
        loadChannelData(true, false,"initpager");
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (adapter == null){
//            ReStartAPPTool.restartAPP(getContext());
//            return;
//        }
        if (CmsApplication.getUserInfoEntity() != null && !Utils.isStringEmpty(Mid) && !Mid.equals(CmsApplication.getUserInfoEntity().getMID())) {
            Mid = CmsApplication.getUserInfoEntity().getMID();
            loadChannelData(true, true,"onresume");
        }
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        TLog.e("save","--------------11");
//        adapter = null;
//    }
//
//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//        TLog.e("save","--------------22");
//    }

    public void refreshCountry() {
        if (currentCountry != CountryMannager.getInstance().getCurrentCountry()) {
            TLog.e("coutry", currentCountry + "=====" +
                    "======" + CountryMannager.getInstance().getCurrentCountry());
            loadChannelData(true, false,"refreshcountry");
            currentCountry = CountryMannager.getInstance().getCurrentCountry();
        }
    }

    BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onResume();
        }
    };

    private void regiesterReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(NewsListAction);
        getContext().registerReceiver(messageReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (messageReceiver != null) {
            getContext().unregisterReceiver(messageReceiver);
        }
    }

    private void initRecycle() {


        recyclerGridAdapter = new MyRecyclerGridAdapter(getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, BaseActivity.getDensity_int(4), false));

        recyclerView.setAdapter(recyclerGridAdapter);
        recyclerGridAdapter.setItemClick(new MyRecyclerGridAdapter.OnItemClick() {

            @Override
            public void onClick(ChannelItem entity, int postion) {
                pageChannelManagerGroup.setVisibility(View.GONE);
                if (entity == null) {
                    Intent intent = new Intent(getActivity(), UsChannelNewAct.class);
                    startActivityForResult(intent, 1);
                } else {
                    onItemClilck(entity, postion);
                }
            }
        });
        adapter = new PageAdapter(getChildFragmentManager());
//        FragmentManager fragmentManager = getChildFragmentManager();
//        fragmentManager.findFragmentByTag()
    }


    public void onItemClilck(ChannelItem entity, int postion) {
        // TODO Auto-generated method stub

        if (Utils.isStringEmpty(entity.getCid()) || postion < 0) {
            return;
        }
        int curentId = postion;
        for (int i = 0; i < userList.size(); i++) {
            if (entity.getCid().equals(userList.get(i).getCid())) {
                curentId = i;
                break;
            }
        }
        viewPager.setCurrentItem(curentId + 1);

    }


    FragmentIdListener listener;

    public interface FragmentIdListener {
        void callback(String channelid);
    }

    public void setListener(FragmentIdListener listener) {
        this.listener = listener;
    }


    private void displayTag(List<ChannelItem> list) {

        userList.clear();
        userList.addAll(list);
        adapter.clear();
        NewsMixFragment newsMixFragment = NewsMixFragment.newInstance(list);
        adapter.addFragment(newsMixFragment,getResources().getString(R.string.txt_foryou));
//        NewsListFragment newsListFragment = NewsListFragment.newInstance(list, true);
//        adapter.addFragment(newsListFragment, getResources().getString(R.string.txt_foryou));
        initTouch(newsMixFragment);

        NewsLocalFragment localFragment = NewsLocalFragment.newInstance();
        adapter.addFragment(localFragment,"Local");

        for (int i = 0; i < list.size(); i++) {
            List<ChannelItem> itemList = new ArrayList<>();
            itemList.add(list.get(i));
            NewsUnMixFragment fragment = NewsUnMixFragment.newInstance(list.get(i).getCid());
            adapter.addFragment(fragment, list.get(i).getChannel_name());
        }

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        for (int j = 0; j <= list.size()+1; j++) {
            TabLayout.Tab tab = tabLayout.getTabAt(j);
            tab.setCustomView(getTabView(j));
        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeSelect(tab);
                VideoPlayManager manager = VideoPlayManager.getInstance();
                manager.pausePlayVideo();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeToNormal(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void initTouch(NewsBaseFragment newsListFragment) {
        newsListFragment.setOnComplete(new NewsBaseFragment.OnRefreshComplete() {
            @Override
            public void OnClick() {
                if (Preference.getInstance().getBoolean(XMLName.XML_First_Touch, true)) {
                    Preference.getInstance().Set(XMLName.XML_First_Touch, String.valueOf(false));
                    Preference.getInstance().saveConfig();
//                    TouchPop touchPop = new TouchPop(getContext());
//                    touchPop.showPopView(viewPager);
                    ((HomeTabAct) getContext()).showTouchView();
                }
            }
        });
    }

    private void changeSelect(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        if (null == view) {
            return;
        }
        TextView txt_title = (TextView) view.findViewById(R.id.txt_news_top);
        setTextBold(txt_title, true);
        txt_title.setTextSize(TopTitleBig);
    }

    private void changeToNormal(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        if (null == view) {
            return;
        }
        TextView txt_title = (TextView) view.findViewById(R.id.txt_news_top);

        setTextBold(txt_title, false);
        txt_title.setTextSize(TopTitleSmall);
    }

    public View getTabView(int position) {
        TextView txt_title = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_news_top, null);
        txt_title.setText(adapter.getPageTitle(position));
        if (position == 0) {
            setTextBold(txt_title, true);
            txt_title.setTextSize(TopTitleBig);
            txt_title.setPadding(PaddingStart, 0, Padding, 0);
        } else {
            setTextBold(txt_title, false);
            txt_title.setTextSize(TopTitleSmall);
            txt_title.setPadding(Padding, 0, Padding, 0);
        }
        return txt_title;
    }


    private void setTextBold(TextView txt_title, boolean isBold) {
        if (isBold) {
            //android中为textview动态设置字体为粗体
            txt_title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        } else {
            //设置不为加粗
            txt_title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }
    }


    @OnClick({R.id.img_rightchannel, R.id.btn_exit, R.id.btn_search, R.id.img_search, R.id.btn_addchannel, R.id.lin_left})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.img_rightchannel:
//                setVisibleChannelManager(View.VISIBLE, false);
                Intent intentChan = new Intent(getActivity(), UsChannelNewAct.class);
                startActivityForResult(intentChan, 1);
                break;
            case R.id.btn_exit:
                break;
            case R.id.btn_search:
//                Intent intentSerch = new Intent(getActivity(), SearchDetailActivity.class);
//                startActivity(intentSerch);
                break;
            case R.id.img_search:
//                setVisibleChannelManager(View.GONE, false);
                break;
            case R.id.btn_addchannel:
                break;
            case R.id.lin_left:
                Intent intentSerch = new Intent(getActivity(), SearchDetailActivity.class);
                startActivity(intentSerch);
                break;

        }

    }


    public void RefreshList() {
//        NewsListFragment fragment = (NewsListFragment) adapter.getItem(viewPager.getCurrentItem());
        if (adapter == null) {
            return;
        }
        NewsBaseFragment fragment = adapter.getCurrentFragment();
//        TLog.e("tag", viewPager.getCurrentItem() + "==============sss");
        if (fragment != null) {
            fragment.setListRefreshing();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ChannelUtils.Result_News) {
            loadChannelData(true, false,"onactivityresult-news");
        } else {
            if (adapter == null) {
                return;
            }
            NewsBaseFragment fragment = adapter.getCurrentFragment();
            if (fragment != null) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }

    }

    boolean isDisplay = true;

    int tag = 1;

    protected void loadChannelData(final boolean isFirst, final boolean isShowPage,String source) {
        linNoChannel.setVisibility(View.GONE);
        isDisplay = true;
        Preference preference = Preference.getInstance();
        int num = preference.getInt(XMLName.XML_Three_Start, 1);
        TLog.e("num","num------------"+num+"----"+source);
        if (num <= 2 && source.equals("initpager")){
            loadChannelfirst();
            return;
        }
        loadCache(isFirst);
    }
    private void loadChannelfirst(){
        String channel = TextLengthUtils.getAssetsStr(getContext(), "channel_init");
        try {
            JSONArray array1 = new JSONArray(channel);
            List<List<ChannelItem>> dataList = JsonParse.getChannelList2(array1, ChannelDataManager.ChannelType_News);
            List<ChannelItem> defaultUserChannels = dataList.get(0);
            List<ChannelItem> defaultOtherChannels = dataList.get(1);
            Comparator comp = new SortChannelComparator();
            Collections.sort(defaultUserChannels, comp);
            displayTag(defaultUserChannels);
            channelDataManager.updateNetChannel(ChannelDataManager.ChannelType_News, defaultUserChannels, defaultOtherChannels, null,"class-newsfragment--loadchannelfirst");
        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    private void loadCache(final boolean isFirst){
        tag = 1;
        channelDataManager.loadCacheChannel(ChannelDataManager.ChannelType_News, new ChannelDataManager.ChannelResultCallBack() {
            @Override
            public void onResultCallBack(List<ChannelItem> userChannelList, List<ChannelItem> otherChannelList) {
                if (tag > 1) {
                    return;
                }
                tag++;
                if (Utils.isStringEmpty(userChannelList) && Utils.isStringEmpty(otherChannelList)) {
                    List<List<ChannelItem>> dataList = channelDataManager.loadFirstChannel(null, ChannelDataManager.ChannelType_News);
                    if (!Utils.isStringEmpty(dataList)) {
                        userChannelList = dataList.get(0);
                        otherChannelList = dataList.get(1);
                    }
                }

                if (Utils.isStringEmpty(userChannelList) && !Utils.isStringEmpty(otherChannelList)) {
                    List<ChannelItem> uselist = new ArrayList<ChannelItem>();
                    List<ChannelItem> othelist = new ArrayList<ChannelItem>();
                    if (!Utils.isStringEmpty(otherChannelList)) {
                        int othersize = otherChannelList.size() > 4 ? 5 :otherChannelList.size();
                        for (int i = 0; i < othersize; i++) {
                            if (i < 5) {
                                uselist.add(otherChannelList.get(i));
                            } else {
                                othelist.add(otherChannelList.get(i));
                            }
                        }
                    }
                    userChannelList.addAll(uselist);
                    channelDataManager.updateNetChannel(ChannelDataManager.ChannelType_News, uselist, othelist, null,"class-newsfragment--cachenull");
                }
                Comparator comp = new SortChannelComparator();
                Collections.sort(userChannelList, comp);
                if (isFirst && isDisplay) {
                    displayTag(userChannelList);
                    isDisplay = false;
                }
            }
        });
    }
    private void startLoad() {
        progressBar.start();
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgressInfo(getResources().getString(R.string.txt_progress_loading));
    }
    private void stopLoad() {
        progressBar.stop();
        progressBar.setVisibility(View.GONE);
    }

    public boolean isVisibleManager() {
        if (pageChannelManagerGroup == null) {
            return false;
        }
        return (pageChannelManagerGroup.getVisibility() == View.VISIBLE);
    }

    public void setVisibleChannelManager(final int visible, boolean isDisArtile) {

        pageChannelManagerGroup.setVisibility(visible);
        if (visible == View.VISIBLE) {
            if (recyclerView != null && recyclerGridAdapter != null
                    && !Utils.isStringEmpty(recyclerGridAdapter.getData())) {
                recyclerView.smoothScrollToPosition(0);
            }
            startMove(pageChannelManagerGroup, true);

        } else if (visible == View.GONE) {
            startMove(pageChannelManagerGroup, false);
        }

    }

    private void startMove(View moveView, final boolean isEnter) {
//		// ???????????
        float height = ((BaseActivity) getContext()).getScreenHeight();
        Animation moveAnimation = new TranslateAnimation(0, 0, -height + BaseActivity.getDensity_int(45), 0);
        if (!isEnter) {
            moveAnimation = new TranslateAnimation(0, 0, 0, -height);
        }
        moveAnimation.setDuration(500L);
        AnimationSet moveAnimationSet = new AnimationSet(true);
        moveAnimationSet.setFillAfter(false);// ????��?????????View?????????????��??

        moveAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                loadChannelData(false, false,"anima-end");
            }
        });

        moveAnimationSet.addAnimation(moveAnimation);
        moveView.startAnimation(moveAnimationSet);

    }

    public void onRefreshToNetWorkChange(boolean isGprs) {
        if (adapter == null) {
            return;
        }
        NewsBaseFragment fragment = adapter.getCurrentFragment();
        if (fragment != null) {
            fragment.changeItem(isGprs);
        }

    }

    class PageAdapter extends FragmentPagerAdapter {

        private NewsBaseFragment mCurrentFragment;

        private List<NewsBaseFragment> mFragments = new ArrayList<>();
        private List<String> mFragmentTitles = new ArrayList<>();

        FragmentManager fragmentManager;

        public PageAdapter(FragmentManager fm) {
            super(fm);
            this.fragmentManager = fm;
        }

        public void addFragment(NewsBaseFragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            mCurrentFragment = (NewsBaseFragment) object;
            super.setPrimaryItem(container, position, object);
        }

        public NewsBaseFragment getCurrentFragment() {
            return mCurrentFragment;
        }

        public void clear() {
            mFragments.clear();
            mFragmentTitles.clear();
        }

        @Override
        public int getItemPosition(Object object) {
            return PageAdapter.POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (Utils.isStringEmpty(userList)) {
                return super.instantiateItem(container, position);
            }
            NewsBaseFragment fragment = (NewsBaseFragment) super.instantiateItem(container, position);
            List<ChannelItem> itemList = new ArrayList<>();
            if (position == 0 || position == 1) {
                itemList.addAll(userList);
            } else {
                itemList.add(userList.get(position - 2));
            }
//            TLog.e("size", itemList.size() + "---------" + userList.get(0).getChannel_name());
            fragment.updateArguments(itemList, position);
            return fragment;
        }
//        public Object instantiateItem(ViewGroup container, int position) {
//                return super.instantiateItem(container, position);}

        //        @Override
//        public Fragment getItem(int position) {
//            TabLayout.Tab tab = tabs.get(position);
//            return NewsListFragment.newInstance(tab.pageType, tab.category);
//        }

//        public Fragment getItem(int position) {
//            return mFragments.get(position);
//        }


        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    public boolean getChannelIsVisiable() {
        if (pageChannelManagerGroup == null) {
            return false;
        }

        if (pageChannelManagerGroup.getVisibility() == View.VISIBLE) {
            pageChannelManagerGroup.setVisibility(View.GONE);
            startMove(pageChannelManagerGroup, false);
            return true;
        }
        return false;
    }
}
