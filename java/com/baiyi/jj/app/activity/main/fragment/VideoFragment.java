package com.baiyi.jj.app.activity.main.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.manager.ChannelDataManager;
import com.baiyi.jj.app.manager.VideoPlayManager;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.turbo.turbo.mexico.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/5.
 */

public class VideoFragment extends Fragment {

    @Bind(R.id.video_tablayout)
    TabLayout tabLayout;
    @Bind(R.id.video_viewPager)
    ViewPager viewPager;
    @Bind(R.id.img_rightchannel)
    ImageView imageRight;

    VideoPageAdapter adapter;

    public static final int TopTitleSmall = 16;
    public static final int TopTitleBig = 19;
    public static final int Padding = BaseActivity.getDensity_int(10);
    public static final int PaddingStart = BaseActivity.getDensity_int(15);

    public List<ChannelItem> userList = new ArrayList<>();

    boolean isRefreshSuc = false;

    public static VideoFragment newInstance(String channelId) {
        VideoFragment fragment = new VideoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", channelId);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        ButterKnife.bind(this, view);
        initPager();
        return view;
    }

    private void initPager() {

        adapter = new VideoPageAdapter(getChildFragmentManager());
        imageRight.setVisibility(View.GONE);
        loadNetChannel(false);
    }

    private void loadNetChannel(final boolean isShow) {

        if (CmsApplication.getUserToken() == null) {
            TLog.e("nu","nullll------------");
            loadCacheTag();
            return;
        }
        ChannelDataManager.getInstance(getContext()).loadNetChannelList(ChannelDataManager.ChannelType_Video, new ChannelDataManager.ChannelResultCallBack() {
            @Override
            public void onResultCallBack(List<ChannelItem> userChannelList, List<ChannelItem> otherChannelList) {

                if (Utils.isStringEmpty(userChannelList)) {
                    return;
                }
                isRefreshSuc = true;
                if (isShow)
                displayTag(userChannelList);
            }
        });
    }
    public void refreshTag(){
        if (adapter != null && adapter.getCount() == 0){
            if (isRefreshSuc){
                loadCacheTag();
            }else {
                loadNetChannel(true);
            }
        }

    }

    private void loadCacheTag(){
                ChannelDataManager.getInstance(getContext()).loadCacheChannel(ChannelDataManager.ChannelType_Video, new ChannelDataManager.ChannelResultCallBack() {
            @Override
            public void onResultCallBack(List<ChannelItem> userChannelList, List<ChannelItem> otherChannelList) {
                if (Utils.isStringEmpty(userChannelList)) {
                    loadNetChannel(true);
                    return;
                }
                displayTag(userChannelList);
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    private void displayTag(List<ChannelItem> userChannelList) {

        userList.clear();
        userList.addAll(userChannelList);
        adapter.clear();

        VideoListFragment videoListFragment = VideoListFragment.newInstance(userChannelList, true);
        adapter.addFragment(videoListFragment, getResources().getString(R.string.txt_foryou));

        for (int i = 0; i < userChannelList.size(); i++) {
            List<ChannelItem> itemList = new ArrayList<>();
            itemList.add(userChannelList.get(i));
            VideoListFragment fragment = VideoListFragment.newInstance(itemList, false);
            fragment.setChannelId(userChannelList.get(i).getCid());
            adapter.addFragment(fragment, userChannelList.get(i).getChannel_name());
        }

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        for (int j = 0; j <= userChannelList.size(); j++) {
            TabLayout.Tab tab = tabLayout.getTabAt(j);
            tab.setCustomView(getTabView(j));
        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeSelect(tab);
                VideoPlayManager manager = VideoPlayManager.getInstance();
//                if (manager.isPlaying()){
                manager.pausePlayVideo();
//                }
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

    private void setTextBold(TextView txt_title, boolean isBold) {
        if (isBold) {
            //android中为textview动态设置字体为粗体
            txt_title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        } else {
            //设置不为加粗
            txt_title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }
    }

    public void RefreshList() {
//        NewsListFragment fragment = (NewsListFragment) adapter.getItem(viewPager.getCurrentItem());
        if (adapter == null) {
            return;
        }
        VideoListFragment fragment = adapter.getCurrentFragment();
//        TLog.e("tag", viewPager.getCurrentItem() + "==============sss");
        if (fragment != null) {
            fragment.setListRefreshing();
        }
    }

        class VideoPageAdapter extends FragmentPagerAdapter {

        private VideoListFragment mCurrentFragment;

        private List<VideoListFragment> mFragments = new ArrayList<>();
        private List<String> mFragmentTitles = new ArrayList<>();

        public VideoPageAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(VideoListFragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            mCurrentFragment = (VideoListFragment) object;
            super.setPrimaryItem(container, position, object);
        }

        public VideoListFragment getCurrentFragment() {
            return mCurrentFragment;
        }

        public void clear() {
            mFragments.clear();
            mFragmentTitles.clear();
        }

        @Override
        public int getItemPosition(Object object) {
            return VideoFragment.VideoPageAdapter.POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (Utils.isStringEmpty(userList)) {
                return super.instantiateItem(container, position);
            }
            VideoListFragment fragment = (VideoListFragment) super.instantiateItem(container, position);
            List<ChannelItem> itemList = new ArrayList<>();
            if (position == 0) {
                itemList.addAll(userList);
            } else {
                itemList.add(userList.get(position - 1));
            }
            fragment.updateArguments(itemList, position == 0);
            return fragment;
        }

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
}
