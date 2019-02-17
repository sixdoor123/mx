package com.baiyi.jj.app.activity.attention;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.attention.net.AttetionNet;
import com.baiyi.jj.app.activity.main.AttentionPager;
import com.baiyi.jj.app.entity.ChannelItem;
import com.turbo.turbo.mexico.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/1/10 0010.
 */
public class AllAttetionAct extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    @Bind(R.id.title_back)
    ImageView imageBack;
    @Bind(R.id.title_search)
    ImageView imageSearch;
    @Bind(R.id.txt_title)
    TextView txtTitle;

    @Bind(R.id.appbar)
    AppBarLayout appBarLayout;
    @Bind(R.id.cd)
    View cd;

    PageAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_allattetion);
        ButterKnife.bind(this);

        toolbar.setTitle("attetion");
        initToolBar();
        initViewPager();

        // test
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                int fragment = cd.getHeight() - toolbar.getHeight() - tabLayout.getHeight();

                Log.e("Main", "fragment " + fragment);

            }
        }, 2000);
    }

    private void initToolBar() {
        toolbar.setTitle("");
        txtTitle.setText(getResources().getString(R.string.txt_attention_more));

    }

    @OnClick({R.id.title_back, R.id.title_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                exit();
                break;
            case R.id.title_search:
                Intent intent = new Intent(this, SearchAttentionAct.class);
                startActivity(intent);
                break;
        }
    }

    private void exit() {
        setResult(AttentionPager.AttetCode);
        finish();
        overridePendingTransition(R.anim.slide_in_from_left,
                R.anim.slide_out_from_left);
    }

    private void initViewPager() {
//        adapter = new PageAdapter(getSupportFragmentManager());
//        for (int i = 0; i < 10; i++) {
//            adapter.addFragment(AttetionFragment.newInstance("channeid" + i), "tag" + i);
//        }
//
//        viewPager.setAdapter(adapter);
        loadMenu();
    }

    private void loadMenu() {

        new AttetionNet().loadAllTagChannel(this, new AttetionNet.LoadChannelCallBack() {
            @Override
            public void callBack(final List<ChannelItem> listEntities) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        displayTag(listEntities);
                    }
                });
            }
        },false);

    }


    private void displayTag(List<ChannelItem> list){
        adapter = new PageAdapter(getSupportFragmentManager());
        for (int i = 0; i < list.size(); i++) {
            adapter.addFragment(AttetionFragment.newInstance(list.get(i).getCid()), list.get(i).getChannel_name());
        }

        viewPager.setAdapter(adapter);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == TagFragment.AttetCode) {
//            adapter.getItem(viewPager.getCurrentItem()).onActivityResult(requestCode,resultCode,data);
//        }
//
//    }

    static class PageAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        exit();
        return true;
    }

}
