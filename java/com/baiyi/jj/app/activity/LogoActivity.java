package com.baiyi.jj.app.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baiyi.core.file.Preference;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.main.HomeTabAct;
import com.baiyi.jj.app.activity.user.UsLoginAct;
import com.baiyi.jj.app.activity.user.UsRegistAct;
import com.baiyi.jj.app.activity.user.channel.ChannelDataUtils;
import com.baiyi.jj.app.activity.user.channel.UsChannelAct;
import com.baiyi.jj.app.activity.user.channel.UsChannelTagAct;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.cache.Dao.ChannelDao;
import com.baiyi.jj.app.cache.Dao.WebDetailDao;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.language.PreferenceUtil;
import com.baiyi.jj.app.manager.ChannelDataManager;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.cache.Dao.ConfigDao;
import com.baiyi.jj.app.cache.Dao.NewsListDao;
import com.baiyi.jj.app.cache.Dao.UserDao;
import com.baiyi.jj.app.cache.ReadedCacheManager;
import com.baiyi.jj.app.cache.bean.ConfigurationBean;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.manager.CountryMannager;
import com.baiyi.jj.app.manager.RandomManager;
import com.baiyi.jj.app.theme.ThemeUtil;
import com.baiyi.jj.app.utils.ArticleHistoryUtils;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.LogCatHelper;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.TextLengthUtils;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.utils.XMLName;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.facebook.appevents.AppEventsLogger;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.onesignal.OSPermissionSubscriptionState;
import com.onesignal.OneSignal;
import com.turbo.turbo.mexico.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 欢迎界面
 *
 * @author tangkun
 */
public class LogoActivity extends BaseActivity {
    private final static String TAG = LogoActivity.class.getName();

    @Bind(R.id.logo_img)
    ImageView imgLogo;
    @Bind(R.id.lin_firstlogin)
    LinearLayout linFirstLogo;
    @Bind(R.id.btn_sign_in)
    Button btnSignIn;
    @Bind(R.id.btn_sign_up)
    Button btnSignUp;
    @Bind(R.id.btn_skip_now)
    Button btnSkipNow;
    @Bind(R.id.loading)
    MyLoadingBar loadingBar;

    long firststart;
    long lasttime;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);
        View contentView = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_logo, null);
        win_content.addView(contentView);
        ButterKnife.bind(this);

        if (!isTaskRoot()) {
            finish();
            return;
        }

        ActivityStackControlUtil.finishAllActivityWithout(this);

//		LogCatHelper.getInstance(LogoActivity.this,null).start();
        CountryMannager.getInstance().setCurrent_Country(CountryMannager.Country_Mexico);
        TLog.e("skip", "open app-------------------" + System.currentTimeMillis());
        lasttime = System.currentTimeMillis();
        firststart = System.currentTimeMillis();
        if (Preference.getInstance().getBoolean(XMLName.XML_First_Logo, true)) {
//            TLog.e("skiptime","files-----"+System.currentTimeMillis()+"-----"+(System.currentTimeMillis()-lasttime));
            initCoutry();
            AccountManager.getInstance().setWifi_Is_Display_Img(1);
        } else {
            login();
        }

        initMixPannel();
        // 谷歌统计发送屏幕信息
        sendScreenImageName(TAG);

    }

    private void initMixPannel() {
        if (Config.isRelease) {
            String projectToken = "cf8d8abf8487bca780ba9e9aae4d08ab"; // e.g.: "1ef7e30d2a58d27f4b90c42e31d6d7ad"
            MixpanelAPI mixpanel = MixpanelAPI.getInstance(this, projectToken);
        }
    }


    private void initCoutry() {

        TLog.e("skiptime", "initnewsss-----" + System.currentTimeMillis() + "-----" + (System.currentTimeMillis() - lasttime));
        lasttime = System.currentTimeMillis();
        Intent intent = new Intent(this, HomeTabAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        TLog.e("skiptime","startjoo--------"+System.currentTimeMillis()+"-----"+(System.currentTimeMillis()-lasttime));
        lasttime = System.currentTimeMillis();
        ChannelDataManager.getInstance(this).loadFirstChannel(null, ChannelDataManager.ChannelType_News);
        TLog.e("skiptime","initmews5555--------"+System.currentTimeMillis()+"-----"+(System.currentTimeMillis()-lasttime));
        lasttime = System.currentTimeMillis();
        ChannelDataManager.getInstance(this).loadFirstChannel(null, ChannelDataManager.ChannelType_Video);
        TLog.e("skiptime","initvideo--------"+System.currentTimeMillis()+"-----"+(System.currentTimeMillis()-lasttime));
        lasttime = System.currentTimeMillis();
        Preference.getInstance().Set(XMLName.XML_First_Logo, String.valueOf(false));
        Preference.getInstance().saveConfig();
        long value = System.currentTimeMillis() - firststart;
        TLog.e("first---skip", value + "----");
        addRequestTime(this.getClass().getName(), BaseAnalyticsActivity.Category_init, BaseAnalyticsActivity.Init_FirstOpen, value);
        loadAnonymity(new BaseActivity.AnonymityLister() {
            @Override
            public void setAnonymityLister(UserInfoEntity infoEntity) {
                stopLoading();
                TLog.e("skip", "skhip0000--------net  stop get user------" + (System.currentTimeMillis() - lasttime));
                lasttime = System.currentTimeMillis();
                if (infoEntity != null) {
                    Preference preference = Preference.getInstance();
                    preference.Set(XMLName.XML_UserID, infoEntity.getMID());
                    preference.saveConfig();
                    addEnvent(BaseAnalyticsActivity.Envent_INITUser_SUC);
                } else {
                    addEnvent(BaseAnalyticsActivity.Envent_INITUser_FAI);
                }
            }
        });
//		addRequestTime(this.getClass().getName(), BaseAnalyticsActivity.Category_init, BaseAnalyticsActivity.Init_FirstOpen,value);

    }

    private void login() {

        goTimerTask();
//		KeyHashUtils.getKeyHash(this,"com.turbo.turbo.mexico");
        ConfigurationBean configBean = new ConfigDao(this).queryCofig();
        if (configBean != null) {
            int UserType = configBean.getUserType();
            UserInfoEntity entity = new UserDao(this).getUserInfo(UserType);
            if (entity != null) {
                TLog.e("user", "userbunuoo===========");
                if (entity.getUserType() == UserInfoEntity.UserType_Gust) {
                    CmsApplication.setUserToken("Bearer " + configBean.getGustToken());
                } else if (entity.getUserType() == UserInfoEntity.UserType_Accout) {
                    CmsApplication.setUserToken("Bearer " + configBean.getUserToken());
                } else if (entity.getUserType() == UserInfoEntity.UserType_Three) {
                    CmsApplication.setUserToken("Bearer " + configBean.getOtherToken());
                }
                CmsApplication.setUserInfoEntity(entity, this);
//                goTimerTask();
                return;
            }
        }


        loadAnonymity(new AnonymityLister() {
            @Override
            public void setAnonymityLister(UserInfoEntity infoEntity) {
                if (infoEntity == null) {
                    displayToast(getResources().getString(R.string.tip_login_fail));
//                    goTimerTask();
                    return;
                }
            }
        });
    }


    protected void goTimerTask() {
        new TimerCountTask().execute();
    }

    class TimerCountTask extends AsyncTask<Boolean, Boolean, Boolean> {
        @Override
        protected void onProgressUpdate(Boolean... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(Boolean... booleen) {
            lasttime = System.currentTimeMillis();
            deleteCache();
            lasttime = System.currentTimeMillis();
            return true;
        }


        @Override
        protected void onPostExecute(Boolean flag) {
            // TODO Auto-generated method stub
            super.onPostExecute(flag);
            ReadedCacheManager.getInstance().init(LogoActivity.this);
            IntentToHome();
        }
    }

    public void IntentToHome() {
        TLog.e("skip", "skhip6--------staaaaart  to  home ------" + (System.currentTimeMillis() - lasttime) + "----" + System.currentTimeMillis());
        Intent intent = new Intent(this, HomeTabAct.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        if (getIntent().getBundleExtra(Define.EXTRA_BUNDLE) != null) {
            intent.putExtra(Define.EXTRA_BUNDLE,
                    getIntent().getBundleExtra(Define.EXTRA_BUNDLE));
        }
        startActivity(intent);
    }

    int randonNum = -1;

    private boolean isGoChannel() {
        return false;
//        TLog.e("plan", "random----==" + randonNum);
//        if (randonNum != -1) {
//            return randonNum > 4;
//        } else {
//            randonNum = RandomManager.getInstance().getRandomNum();
//            return randonNum > 4;
//        }
    }

    private void deleteCache() {

        NewsListDao dao = new NewsListDao(this);

        long twoDayAgo = System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000;
        int size = dao.getAllListSize();

        if (size > 300) {
            dao.deleteCache(ArticleHistoryUtils.Tablename_News, twoDayAgo);
        }

        WebDetailDao webDetailDao = new WebDetailDao(this);
        webDetailDao.delete200();

        if (!Preference.getInstance().getBoolean(XMLName.XML_deleteSql_web, false)) {
            webDetailDao.deleteAll();
            Preference.getInstance().Set(XMLName.XML_deleteSql_web, String.valueOf(true));
            Preference.getInstance().saveConfig();
        }
    }


    private long exitTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                exitTime = System.currentTimeMillis();
            } else {
                destory();
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startLoading() {
        loadingBar.setVisibility(View.VISIBLE);
        loadingBar.start();
    }

    public void stopLoading() {
        if (loadingBar != null) {
            loadingBar.stop();
            loadingBar.setVisibility(View.GONE);
        }
    }

    @Override
    public int returnStateColor() {
        return R.color.day_text_color_write;
    }

    @Override
    public boolean isLogo() {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
