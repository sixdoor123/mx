package com.baiyi.jj.app.activity.main;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baiyi.core.file.Preference;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.activity.BaseAnalyticsActivity;
import com.baiyi.jj.app.activity.MorningNewsListAct;
import com.baiyi.jj.app.activity.NewsLocalAct;
import com.baiyi.jj.app.activity.NewsVideoAct;
import com.baiyi.jj.app.activity.main.fragment.NewsFragment;
import com.baiyi.jj.app.activity.main.fragment.UserFragment;
import com.baiyi.jj.app.activity.main.fragment.VideoFragment;
import com.baiyi.jj.app.activity.user.login.BaseLoginActivity;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.dialog.StartUpThreeToShareDialog;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.language.SwitchLanguageUtils;
import com.baiyi.jj.app.manager.InitService;
import com.baiyi.jj.app.manager.NotifactionManager;
import com.baiyi.jj.app.manager.UpdateAppManager;
import com.baiyi.jj.app.manager.UserHeadManager;
import com.baiyi.jj.app.manager.VideoPlayManager;
import com.baiyi.jj.app.net.NewsDetailUtils;
import com.baiyi.jj.app.utils.ArticleHistoryUtils;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.utils.XMLName;
import com.baiyi.jj.app.views.viewpager.NoScrollViewPager;
import com.baiyi.jj.core.basedialog.DialogBase;
import com.facebook.ads.AdSettings;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.iid.FirebaseInstanceId;
import com.onesignal.OSPermissionSubscriptionState;
import com.onesignal.OneSignal;
import com.turbo.turbo.mexico.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/1/10 0010.
 */
public class HomeTabAct extends BaseLoginActivity {

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    NoScrollViewPager viewPager;

    PageAdapter adapter = null;

    TabLayout.Tab newsTab;
    TabLayout.Tab videoTab;
    TabLayout.Tab userTab;

    private List<String> tabList = new ArrayList<>();

    private int[] tabImagesNormal = {R.drawable.tab_icon_news, R.drawable.tab_icon_video, R.drawable.tab_icon_user};
    private int[] tabImagesSelect = {R.drawable.tab_icon_news_select, R.drawable.table_icon_video_select, R.drawable.tab_icon_user_select};

    NewsFragment newsFragment;
    VideoFragment videoFragment;
    UserFragment userFragment;

    public int messageUnread = 0;
    TextView msgText;

    @Bind(R.id.rela_touch)
    RelativeLayout showTouch;
    @Bind(R.id.lin_touch1)
    LinearLayout linearLayout1;
    @Bind(R.id.lin_touch2)
    LinearLayout linearLayout2;
    @Bind(R.id.lin_touch3)
    LinearLayout linearLayout3;
    @Bind(R.id.lin_touch4)
    LinearLayout linearLayout4;
    @Bind(R.id.lin_touch5)
    LinearLayout linearLayout5;
    @Bind(R.id.tv_pager)
    TextView tvPager;

    private Activity instance;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);
        View contentView = ContextUtil.getLayoutInflater(this).inflate(R.layout.act_hometab, null);
        win_content.addView(contentView);
        ButterKnife.bind(this);
        initTitle();
        initPager();
        goDetail();
        getDetailUrl();
        isThreeStartUp();
        InitService.startHomeinit(this);
        initFCM();
        instance = this;
        TLog.e("home--","-------------"+System.currentTimeMillis());

//        if (!Config.isRelease){
//            AdRequest adRequest = new AdRequest.Builder().addTestDevice("0C744402985A442CE2A2EEFBAB43D121").build();
//            AdSettings.addTestDevice("f89ab1d7c2eef304b9f8738a52a5e425");
//        }
    }

    private void isThreeStartUp() {
        Preference preference = Preference.getInstance();
        int num = preference.getInt(XMLName.XML_Three_Start, 1);
        if (num == 3) {
            StartUpThreeToShareDialog dialog = new StartUpThreeToShareDialog(this, DialogBase.Win_Center);
            dialog.showDialog(DialogBase.AnimalTop);
        }
        preference.Set(XMLName.XML_Three_Start, String.valueOf(++num));
        preference.saveConfig();

        if (num != 4){
            UpdateAppManager.loadGooglePlayVer(this);
        }
    }


    private void goDetail() {
        Bundle bundle = getIntent().getBundleExtra(Define.EXTRA_BUNDLE);
        if (bundle != null) {
            String type = bundle.getString(Define.NoticaType);
            if (!Utils.isStringEmpty(type) && type.equals("1")){
                Intent intent1 = new Intent(this, MorningNewsListAct.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.setAction(String.valueOf(System.currentTimeMillis()));
                startActivity(intent1);
                return;
            }

            String url = bundle.getString(Define.ArticleUrl);
            String artiId = bundle.getString(Define.ArticleId);
            String message = bundle.getString(Define.ArticleTitle);
            String template = bundle.getString(Define.ArticleTemp);
            String source = bundle.getString(Define.ArticleSource);
            Intent intent = new Intent(HomeTabAct.this, NewsLocalAct.class);
            if (template.equals(String.valueOf(NotifactionManager.tem_video))) {
                if (!Utils.isStringEmpty(url) && url.contains("v=")) {
                    intent = new Intent(HomeTabAct.this, NewsVideoAct.class);
                    String videoid = url.substring(url.indexOf("v=") + 2);
                    intent.putExtra(Define.VideoId, videoid);
                }
            }
            intent.putExtra(Define.ArticleUrl, url);
            intent.putExtra(Define.ArticleId, artiId);
            intent.putExtra(Define.ArticleTitle, message);
            intent.putExtra(Define.ArticleSource, source);
            intent.putExtra(Define.ArticleType, ArticleHistoryUtils.ArticleType_News);
            intent.putExtra(Define.IsPush, true);
            startActivity(intent);
        }
    }

    private void initTitle() {
        tabList.add(getResources().getString(R.string.name_news));
        tabList.add(getResources().getString(R.string.name_video));
        tabList.add(getResources().getString(R.string.name_mine));

//        String iiii = getIntent().getStringExtra(Define.FirstToHome);
//        if (Utils.isStringEmpty(iiii)){
//            return;
//        }
//        if (iiii.equals("PlanA")){
//            TLog.e("plan","lableA------------------"+System.currentTimeMillis());
//            addEnventFirst(BaseAnalyticsActivity.Envent_PlanA_Yes_Enter);
//        } else if (iiii.equals("PlanB")) {
//            TLog.e("plan","lableB------------------"+System.currentTimeMillis());
//            addEnventFirst(BaseAnalyticsActivity.Envent_PlanB_No_Enter);
//        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
        }
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        TLog.e("test", "restore--");
        if (savedInstanceState != null) {
            lag = SwitchLanguageUtils.getCurrentLanguage();
            SwitchLanguageUtils.selectLanguagebyName(HomeTabAct.this, lag);
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    String lag = "";


    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
        lag = SwitchLanguageUtils.getCurrentLanguage();
    }

    private FragmentManager fragmentManager;

    private void initPager() {
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setNoScroll(true);
        viewPager.setOffscreenPageLimit(3);
        fragmentManager = getSupportFragmentManager();
        adapter = new PageAdapter(fragmentManager);
        newsFragment = NewsFragment.newInstance(getResources().getString(R.string.name_news));
        adapter.addFragment(newsFragment, getResources().getString(R.string.name_news));
        videoFragment = VideoFragment.newInstance(getResources().getString(R.string.name_video));
        adapter.addFragment(videoFragment, getResources().getString(R.string.name_video));
        userFragment = UserFragment.newInstance(getResources().getString(R.string.name_mine));
        adapter.addFragment(userFragment, getResources().getString(R.string.name_mine));
        viewPager.setAdapter(adapter);

        newsTab = tabLayout.getTabAt(0);
        videoTab = tabLayout.getTabAt(1);
        userTab = tabLayout.getTabAt(2);

        newsTab.setCustomView(getTabView(0));
        videoTab.setCustomView(getTabView(1));
        userTab.setCustomView(getTabView(2));


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab == tabLayout.getTabAt(0)) {
                    changeSelect(0, tab);
                    setStateColor();
                    viewPager.setCurrentItem(0);
                } else if (tab == tabLayout.getTabAt(1)) {
                    changeSelect(1, tab);
                    setStateColor();
                    viewPager.setCurrentItem(1);
                } else if (tab == tabLayout.getTabAt(2)) {
                    changeSelect(2, tab);
                    viewPager.setCurrentItem(2);
                }
                if (tab == tabLayout.getTabAt(1) && videoFragment != null) {
                    videoFragment.refreshTag();
                }
                VideoPlayManager manager = VideoPlayManager.getInstance();
                manager.pausePlayVideo();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab == tabLayout.getTabAt(0)) {
                    changeToNormal(0, tab);
                } else if (tab == tabLayout.getTabAt(1)) {
                    changeToNormal(1, tab);
                } else if (tab == tabLayout.getTabAt(2)) {
                    changeToNormal(2, tab);
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab == tabLayout.getTabAt(0) && newsFragment != null) {
                    newsFragment.RefreshList();
                }
                if (tab == tabLayout.getTabAt(1) && videoFragment != null) {
                    videoFragment.RefreshList();
                }
            }
        });
    }

    private void changeSelect(int postion, TabLayout.Tab tab) {
        View view = tab.getCustomView();
        TextView txt_title = (TextView) view.findViewById(R.id.txt_tab);
        txt_title.setTextColor(getResources().getColor(R.color.day_text_color_red));
        ImageView img_title = (ImageView) view.findViewById(R.id.img_tab);
        img_title.setImageResource(tabImagesSelect[postion]);
        if (4 == postion) {
            resetMsgNum();
        }
        if (0 == postion) {
            newsFragment.refreshCountry();
        }
    }

    public void resetMsgNum() {
        messageUnread = 0;
        msgText.setVisibility(View.GONE);
    }

    private void changeToNormal(int postion, TabLayout.Tab tab) {
        View view = tab.getCustomView();
        TextView txt_title = (TextView) view.findViewById(R.id.txt_tab);
        txt_title.setTextColor(getResources().getColor(R.color.day_text_color_737373));
        ImageView img_title = (ImageView) view.findViewById(R.id.img_tab);
        img_title.setImageResource(tabImagesNormal[postion]);
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_tab, null);
        TextView txt_title = (TextView) view.findViewById(R.id.txt_tab);
        txt_title.setText(tabList.get(position));
        ImageView img_title = (ImageView) view.findViewById(R.id.img_tab);
        img_title.setImageResource(tabImagesNormal[position]);
        if (position == 0) {
            txt_title.setTextColor(getResources().getColor(R.color.day_text_color_red));
            img_title.setImageResource(tabImagesSelect[position]);
        }
        if (position == 4) {
            msgText = (TextView) view.findViewById(R.id.txt_msg_num);
        }
        return view;
    }

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

        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (newsFragment!=null)
        newsFragment.onActivityResult(resultCode, resultCode, data);
        if (videoFragment!=null)
        videoFragment.onActivityResult(resultCode, resultCode, data);
        UserHeadManager.onActivityResult(requestCode, resultCode, data);
    }

    private long exitTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {


            if (VideoPlayManager.getInstance().isFullScreen()) {
                VideoPlayManager.getInstance().exitFullScreen();
                return true;
            }

            if (viewPager.getCurrentItem() == 0) {
                if (newsFragment.getChannelIsVisiable()) {
                    return false;
                }
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.txt_exit_app),
                            Toast.LENGTH_SHORT).show();

                    exitTime = System.currentTimeMillis();

                } else {
                    destory();
                    finish();
                    System.exit(0);
                }
            } else {
                viewPager.setCurrentItem(0);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    protected void netWorkChange(boolean isGprs) {
        // TODO Auto-generated method stub
        super.netWorkChange(isGprs);
        if (newsFragment != null) {
            newsFragment.onRefreshToNetWorkChange(isGprs);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!SwitchLanguageUtils.isMatched(HomeTabAct.this)) {
            lag = SwitchLanguageUtils.getCurrentLanguage();
            SwitchLanguageUtils.selectLanguagebyName(HomeTabAct.this, lag);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    private void getDetailUrl() {
        try {
            String pkName = this.getPackageName();
            String versionName = this.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            if (versionName.contains("-")) {
                versionName = versionName.substring(0, versionName.indexOf("-"));
            }
            new NewsDetailUtils().loadDetailUrls(versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void initFCM() {

        if (!NotifactionManager.checkPlayServices(this)) {
            return;
        }

        getOneSiDada();
    }

    private void getOneSiDada() {

        OSPermissionSubscriptionState status = OneSignal.getPermissionSubscriptionState();
        OneSignal.setSubscription(true);
        boolean isEnabled = status.getPermissionStatus().getEnabled();
        boolean isSubscribed = status.getSubscriptionStatus().getSubscribed();
        boolean subscriptionSetting = status.getSubscriptionStatus().getUserSubscriptionSetting();
        String userID = status.getSubscriptionStatus().getUserId();
        String pushToken = status.getSubscriptionStatus().getPushToken();
//        OneSignal.setSubscription(true);
        if (!Utils.isStringEmpty(pushToken)) {
            OneSignal.sendTag("deviceToken", pushToken);
        }
        TLog.e("userid", userID + "---" + isEnabled + "---" + isSubscribed);
        TLog.e("ONEToken", "==" + pushToken);
        Preference preference = Preference.getInstance();
        int num = preference.getInt(XMLName.XML_NoticInit_Num, 0);
        if (num>3){
            return;
        }
        OneSignal.sendTag("Country", "MX");
        OneSignal.sendTag("Language Code", "es");

        UserInfoEntity userInfoEntity = CmsApplication.getUserInfoEntity();
        if (Utils.isStringEmpty(userID) || userInfoEntity == null || Utils.isStringEmpty(userInfoEntity.getMID())){
            return;
        }
        TLog.e("test","----------------------000000000000000");
        NotifactionManager.updatePlayId(userInfoEntity.getMID(),userID);
        preference.Set(XMLName.XML_NoticInit_Num, String.valueOf(++num));
        preference.saveConfig();

    }

    private void updatePlayId() {

    }

    public int returnStateColor() {
        return R.color.day_text_color_red;
    }

    public boolean isBlackBarText() {
        return true;
    }

    private int num = 1;

    public void showTouchView() {
        num = 1;
        showTouch.setVisibility(View.VISIBLE);
        tvPager.setText("1");
        showTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (++num) {

                    case 2:
                        tvPager.setText("2");
                        linearLayout1.setVisibility(View.GONE);
                        linearLayout4.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        tvPager.setVisibility(View.GONE);
                        linearLayout4.setVisibility(View.GONE);
                        showTouch.setVisibility(View.GONE);
//                        tvPager.setText("3");
//                        linearLayout4.setVisibility(View.GONE);
//                        linearLayout5.setVisibility(View.VISIBLE);
                        break;
//                    case 4:
//                        tvPager.setVisibility(View.GONE);
//                        linearLayout5.setVisibility(View.GONE);
//                        showTouch.setVisibility(View.GONE);
//                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppEventsLogger.deactivateApp(this);
    }
}
