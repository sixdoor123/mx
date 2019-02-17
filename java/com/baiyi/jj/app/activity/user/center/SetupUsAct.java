package com.baiyi.jj.app.activity.user.center;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baiyi.core.file.Preference;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.BaseAnalyticsActivity;
import com.baiyi.jj.app.activity.attention.net.AttetionNet;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.cache.Dao.ArticleCacheDao;
import com.baiyi.jj.app.cache.Dao.ArticleChannelDao;
import com.baiyi.jj.app.cache.Dao.AttetionWordsDao;
import com.baiyi.jj.app.cache.Dao.ConfigDao;
import com.baiyi.jj.app.cache.Dao.GifDetailDao;
import com.baiyi.jj.app.cache.Dao.HistoryDao;
import com.baiyi.jj.app.cache.Dao.NewsListDao;
import com.baiyi.jj.app.cache.Dao.WebDetailDao;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.imgtools.GlideTool;
import com.baiyi.jj.app.manager.ChannelDataManager;
import com.baiyi.jj.app.manager.CountryMannager;
import com.baiyi.jj.app.theme.FontUtil;
import com.baiyi.jj.app.theme.ThemeUtil;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.FileUtil;
import com.baiyi.jj.app.views.FontSetView;
import com.baiyi.jj.app.views.LetterSpacingTextView;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.onesignal.OneSignal;
import com.turbo.turbo.mexico.R;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/24 0024.
 */
public class SetupUsAct extends BaseActivity {

    @Bind(com.turbo.turbo.mexico.R.id.title_name)
    LetterSpacingTextView titleName;
    @Bind(R.id.rg_theme)
    RadioGroup rgTheme;
    @Bind(R.id.rg_font)
    RadioGroup rgFont;
    @Bind(R.id.rg_abstract)
    RadioGroup rgAbstract;
    @Bind(R.id.rg_country)
    RadioGroup rgCountry;
    @Bind(R.id.rg_wifibig)
    RadioGroup rgWifiBig;
    @Bind(R.id.rg_notifiction)
    RadioGroup rgShowNoti;
    @Bind(R.id.loading)
    MyLoadingBar progressBar;
    @Bind(R.id.txt_cache_size)
    TextView txtCacheSize;
    @Bind(R.id.img_back)
    ImageView imgBack;

    private FontSetView setViewFont = null;
    private FontSetView setViewTheme = null;
    private FontSetView setViewAbstract = null;
    private FontSetView setViewCountry = null;
    private FontSetView setViewWifi = null;
    private FontSetView setViewNoti = null;
    private Preference pref = null;

    private String[] fontStrs = new String[4];
    private String[] themeStrs = new String[2];
    private String[] AbstractStrs = new String[2];
    private String[] CountryStrs = new String[2];
    private String[] WifiStrs = new String[3];
    private String[] NotiStrs = new String[2];

    int newsIsPush=0;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);

        View titleView = ContextUtil.getLayoutInflater(this).inflate(
                R.layout.title_left, null);
        win_title.addView(titleView);
        View contentView = ContextUtil.getLayoutInflater(this).inflate(
                R.layout.act_setupus, null);
        win_content.addView(contentView);
        ButterKnife.bind(this);

        pref = Preference.getInstance();
        initRbName();
        initView();
    }

    @OnClick(R.id.img_back)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                setExitSwichLayout();
                break;
        }
    }

    private void initRbName() {
        fontStrs[0] = getResources().getString(R.string.txt_theme_small);
        fontStrs[1] = getResources().getString(R.string.txt_theme_middle);
        fontStrs[2] = getResources().getString(R.string.txt_theme_large);
        fontStrs[3] = getResources().getString(R.string.txt_theme_morelarge);

        themeStrs[0] = getResources().getString(R.string.txt_theme_day);
        themeStrs[1] = getResources().getString(R.string.txt_theme_night);

        AbstractStrs[0] = getResources().getString(R.string.txt_abstract_unshow);
        AbstractStrs[1] = getResources().getString(R.string.txt_abstract_show);

        CountryStrs[0] = getResources().getString(R.string.txt_country_us);
        CountryStrs[1] = getResources().getString(R.string.txt_country_india);

        WifiStrs[0] = getResources().getString(R.string.txt_wifi_hide);
        WifiStrs[1] = getResources().getString(R.string.txt_wifi_sd);
        WifiStrs[2] = getResources().getString(R.string.txt_wifi_hd);

        NotiStrs[0] = getResources().getString(R.string.txt_yes);
        NotiStrs[1] = getResources().getString(R.string.txt_no);
    }

    private void initView() {
        titleName.setText(getResources().getString(R.string.txt_set));
        long totalSize = FileUtil.getFileSize(new File(FileUtil.getCachePath(this) + GlideTool.GLIDE_CACHE));
//        long frescoCacheSize = Fresco.getImagePipelineFactory().getMainDiskStorageCache().getSize();
        txtCacheSize.setText(FileUtil.formatFileSize(totalSize));

        setViewFont = new FontSetView(this, rgFont, fontStrs, AccountManager.getInstance().getCurrentSize());
        setViewTheme = new FontSetView(this, rgTheme, themeStrs, ThemeUtil.getAppTheme());
        setViewAbstract = new FontSetView(this, rgAbstract, AbstractStrs, AccountManager.getInstance().getSummary_Is_Display());
        setViewCountry = new FontSetView(this, rgCountry, CountryStrs, CountryMannager.getInstance().getCurrentCountry());
        setViewWifi = new FontSetView(this, rgWifiBig, WifiStrs, AccountManager.getInstance().getWifi_Is_Display_Img());
        setViewNoti = new FontSetView(this, rgShowNoti, NotiStrs, AccountManager.getInstance().getNews_Is_Push());
        rgTheme.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                if(checkedId == R.id.id_rb_0)
//                {
////                    ThemeUtil.onResetSkin();
//                    SwitchLanguageUtils.selectLanguage(SetupUsAct.this, MemberConfig.Chinese);
//                }else if(checkedId == R.id.id_rb_1)
//                {
//                    SwitchLanguageUtils.selectLanguage(SetupUsAct.this,MemberConfig.English);
                ThemeUtil.onChangeSkin(SetupUsAct.this, progressBar);
//                }
                new ConfigDao(SetupUsAct.this).updateTheme();
                onRefresh();
            }
        });
        rgFont.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                AccountManager manager = AccountManager.getInstance();
                if (checkedId == R.id.id_rb_0) {
                    manager.setCurrentSize(FontUtil.Font_Min);
                } else if (checkedId == R.id.id_rb_1) {
                    manager.setCurrentSize(FontUtil.Font_middle);
                } else if (checkedId == R.id.id_rb_2) {
                    manager.setCurrentSize(FontUtil.Font_Max);
                } else if (checkedId == R.id.id_rb_3) {
                    manager.setCurrentSize(FontUtil.Font_Big_Max);
                }
                new ConfigDao(SetupUsAct.this).updateTheme();
            }
        });
        rgAbstract.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                AccountManager manager = AccountManager.getInstance();
                if (checkedId == R.id.id_rb_0) {
                    manager.setSummary_Is_Display(0);
                } else if (checkedId == R.id.id_rb_1) {
                    manager.setSummary_Is_Display(1);
                } else if (checkedId == R.id.id_rb_2) {
                    manager.setSummary_Is_Display(2);
                }
                new ConfigDao(SetupUsAct.this).updateTheme();
            }
        });
        rgCountry.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.start();
                CountryMannager countryMannager = CountryMannager.getInstance();
                if (checkedId == R.id.id_rb_0) {
                    countryMannager.setCurrent_Country(0);
                } else if (checkedId == R.id.id_rb_1) {
                    countryMannager.setCurrent_Country(1);
                } else if (checkedId == R.id.id_rb_2) {
                    countryMannager.setCurrent_Country(2);
                }
                switchCountryLogin();
            }
        });
        rgWifiBig.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                AccountManager manager = AccountManager.getInstance();
                if (checkedId == R.id.id_rb_0) {
                    manager.setWifi_Is_Display_Img(0);
                } else if (checkedId == R.id.id_rb_1) {
                    manager.setWifi_Is_Display_Img(1);
                } else if (checkedId == R.id.id_rb_2) {
                    manager.setWifi_Is_Display_Img(2);
                }
            }
        });

        rgShowNoti.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                AccountManager manager = AccountManager.getInstance();
                if (checkedId == R.id.id_rb_0) {
                    manager.setNews_Is_Push(0);
                    OneSignal.setSubscription(true);
                } else if (checkedId == R.id.id_rb_1) {
                    manager.setNews_Is_Push(1);
                    OneSignal.setSubscription(false);
                    addEnvent(BaseAnalyticsActivity.Envent_Close_Noti);
                }
//                AWSManager.toggleNotifation(SetupUsAct.this,manager.getNews_Is_Push() == 0);

            }
        });
    }

    private void switchCountryLogin() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                AWSManager.updateEndpointData(SetupUsAct.this);
//            }
//        }).start();

        ChannelDataManager.getInstance(SetupUsAct.this).updateSQLChannel(ChannelDataManager.ChannelType_News, new ChannelDataManager.ChannelResultCallBack() {
            @Override
            public void onResultCallBack(List<ChannelItem> userChannelList, List<ChannelItem> otherChannelList) {
                progressBar.setVisibility(View.GONE);
                progressBar.stop();
                new AttetionNet().loadAllTagChannel(SetupUsAct.this, new AttetionNet.LoadChannelCallBack() {
                    @Override
                    public void callBack(final List<ChannelItem> listEntities) {
                    }
                }, true);
                new AttetionWordsDao(SetupUsAct.this).deleteCountryTag();
            }
        });

    }

    @OnClick(R.id.btn_clear_cache)
    public void OnClearCacheClick() {

        GlideTool.clearCache(this);

        ClearNewsList();
        txtCacheSize.setText(0 + "B");
        displayToast(getResources().getString(R.string.tip_clearcahe_success));
    }

    private void ClearNewsList() {
        new NewsListDao(this).deleteAll();
        new HistoryDao(this).deleteAll();
        new ArticleCacheDao(this).deleteAll();
        new ArticleChannelDao(this).deleteAll();
        new AttetionWordsDao(this).deleteAll();
        new WebDetailDao(this).deleteAll();
        new GifDetailDao(this).deleteAll();
    }
}
