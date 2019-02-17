package com.baiyi.jj.app.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baiyi.core.database.manager.SimpleSQLDataBaseManager;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.core.util.CoreLog;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.activity.user.login.UsInfoCallBack;
import com.baiyi.jj.app.activity.user.net.AppUtils;
import com.baiyi.jj.app.activity.user.net.NetUrl;
import com.baiyi.jj.app.activity.user.net.UserNetCallBack;
import com.baiyi.jj.app.activity.user.net.UserNetCallBack.CallBack;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.cache.AdCacheManager;
import com.baiyi.jj.app.cache.AdCacheManager2;
import com.baiyi.jj.app.cache.Dao.ConfigDao;
import com.baiyi.jj.app.cache.Dao.UserDao;
import com.baiyi.jj.app.cache.ReadedCacheManager;
import com.baiyi.jj.app.cache.bean.ConfigurationBean;
import com.baiyi.jj.app.dialog.MsgBoxNotice;
import com.baiyi.jj.app.dialog.MsgBoxNoticeE;
import com.baiyi.jj.app.dialog.MsgBoxNoticeE.MsgBoxEOnClickListener;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.theme.ThemeManager;
import com.baiyi.jj.app.theme.ThemeUtil;
import com.baiyi.jj.app.theme.swichlayout.SwichLayoutInterFace;
import com.baiyi.jj.app.utils.Base64;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.Installation;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.TitleUtils;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.baiyi.jj.app.views.pulldownview.PullToRefreshListView;
import com.baiyi.jj.core.basedialog.DialogBase;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.turbo.turbo.mexico.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tangkun
 */
public abstract class BaseActivity extends BaseAnalyticsActivity implements SwichLayoutInterFace {

    public static BaseActivity gCurrentActivity = null;
    private static final int PROGRESS_DIALOG_ID = 1;
    private String progressDialogMessage = null;
    private String progressDialogTitle = null;
    private boolean isDialogShow = false;
    public static float density;
    public RelativeLayout root;
    protected LinearLayout win_title;
    protected LinearLayout win_content;
    protected LinearLayout win_menu;
    public LinearLayout win_fullscreen;
    public static int win_width;
    public static int win_height;

    public ScrollView mScrollView;

    private String TAG = "BaseActivity";

    protected void onCreate(Bundle savedInstanceState, boolean isHasTitle) {
        super.onCreate(savedInstanceState);

        ActivityStackControlUtil.add(this);
//        initORM();
        if (!isHasTitle) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        DisplayMetrics dm = this.getApplicationContext().getResources()
                .getDisplayMetrics();
        density = dm.density;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        long startTime = System.currentTimeMillis();
        super.onCreate(savedInstanceState);
//        TLog.e("performInit begin----:" + System.currentTimeMillis());
//        initORM();
        ActivityStackControlUtil.add(this);

        if (!isShowTitle()){
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
//        TLog.e("performInit begin----2:" + System.currentTimeMillis());
        setStateColor();
//        TLog.e("performInit begin----3:" + System.currentTimeMillis());
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        TLog.e("performInit begin----4:" + System.currentTimeMillis());

        DisplayMetrics dm = this.getApplicationContext().getResources()
                .getDisplayMetrics();
//        TLog.e("performInit begin----5:" + System.currentTimeMillis());
        density = dm.density;
//        TLog.e("performInit begin----6:" + System.currentTimeMillis());
        initWin(true, true);
//        TLog.e("performInit end----:" + System.currentTimeMillis());
        TLog.e("testtime",this.getClass().getSimpleName()+"--onCreat---"+(System.currentTimeMillis()-startTime));
    }

    /**
     * 设置状态栏的颜色
     */
    public void setStateColor() {
        if (isShowTitle()){
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {


            //透明导航栏
//
            if (!isLogo() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Window window = getWindow();
                //透明状态栏
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(getResources().getColor(returnStateColor()));// SDK21
//            }else if (isLogo()){
//                Window window = getWindow();
//                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            } else {
                setStatusBar();
                if (isBlackBarText()) {
                    return;
                }
                if (TitleUtils.isFlyme() || TitleUtils.isMeizuFlymeOS()) {
//                    setStatusBar();
                    TitleUtils.setMeizuStatusBarDarkIcon(this, true);
                } else if (TitleUtils.isMIUI()) {
//                    setStatusBar();
                    TitleUtils.setMiuiStatusBarDarkMode(this, true);
                } else {

                }
            }

        }
    }

    private void setStatusBar() {
        Window window = getWindow();
        //透明状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);
        // set a custom status bar drawable
        tintManager.setStatusBarTintResource(returnStateColor());
    }

    public int returnStateColor() {
        return R.color.day_bg_color_fafafa;
//        return Color.TRANSPARENT;
    }

    public boolean isLogo() {
        return false;
    }
    public boolean isShowTitle() {
        return false;
    }

    public boolean isBlackBarText() {
        return false;
    }

//    private void initORM() {
//        SimpleSQLDataBaseManager manager = SimpleSQLDataBaseManager
//                .getInstance();
//        String[] beans = this.getResources().getStringArray(
//                R.array.single_db_beans);
//        int[] versions = this.getResources().getIntArray(
//                R.array.single_db_version);
//        manager.init(this, Utils.getDataBasePath(), beans, versions);
//    }


    public int getThemeIdentifier(Context context, String name, String defType) {
        return ThemeManager.getThemeManager().getIdentifier(context, name,
                defType);
    }

    public void setListView(PullToRefreshListView listView, int dataSize) {
        if (dataSize < Config.LIST_ITEM_COUNT) {
            listView.SetFooterCanUse(false);
        } else {
            listView.SetFooterCanUse(true);
        }
    }

    public void showProgressDialog(String title, String message) {
        this.progressDialogMessage = message;
        this.progressDialogTitle = title;
        showDialog(PROGRESS_DIALOG_ID);
        isDialogShow = true;
    }

    public void dismissProgressDialog() {
        if (isDialogShow) {
            try {
                dismissDialog(PROGRESS_DIALOG_ID);
                removeDialog(PROGRESS_DIALOG_ID);
            } catch (Exception e) {
            }
            isDialogShow = false;
        }
    }

    @Override
    public Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case PROGRESS_DIALOG_ID:
                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage(progressDialogMessage);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setTitle(progressDialogTitle);
                dialog = progressDialog;
                break;
        }
        return dialog;
    }

    public void checkLogin(AnonymityLister lister) {
        if (!ContextUtil.isNetWorking(this)) {
            displayMsgBox(getResources().getString(R.string.tip_dialog_title), getResources().getString(R.string.tip_net_fault));
            return;
        }
        if (CmsApplication.getUserInfoEntity() != null) {
            lister.setAnonymityLister(CmsApplication.getUserInfoEntity());
            return;
        }
        if (AccountManager.getInstance().isLogin()) {
            loadAutoLogin(lister, UserInfoEntity.UserType_Accout);
        } else {
            loadAnonymity(lister);
        }
    }

    public void checkLogin(AnonymityLister lister, MyLoadingBar progressBar) {
        if (!ContextUtil.isNetWorking(this)) {
            progressBar.stop();
            progressBar.setVisibility(View.GONE);
            displayMsgBox(getResources().getString(R.string.tip_dialog_title),
                    getResources().getString(R.string.tip_net_fault));
            lister.setAnonymityLister(CmsApplication.getUserInfoEntity());
            return;
        }

        if (AccountManager.getInstance().isLogin()) {
            if (CmsApplication.getUserInfoEntity() != null) {
                lister.setAnonymityLister(CmsApplication.getUserInfoEntity());
                return;
            }
            loadAutoLogin(lister, UserInfoEntity.UserType_Accout);
        } else {
            if (CmsApplication.getUserInfoEntity() != null) {
                lister.setAnonymityLister(CmsApplication.getUserInfoEntity());
                return;
            }
            loadAnonymity(lister);
        }
    }

    /**
     * 普arg1用户登陆
     */
    private void loadAutoLogin(final AnonymityLister lister, int UserType) {

//        final String account = AccountManager.getInstance().getAccount();
//        final String pwd = AccountManager.getInstance().getPwd();
        ConfigurationBean bean = new ConfigDao(this).queryCofig();
        if (bean == null) {
            loadAnonymity(lister);
            return;
        }
        final String account = bean.getUserAccount();

        final String pwd = Base64.Base64ToStr(bean.getUserKey());
        final String userToken = bean.getUserToken();
        if (Utils.isStringEmpty(account) || Utils.isStringEmpty(pwd) || Utils.isStringEmpty(userToken)) {
            loadAnonymity(lister);
            return;
        }
        CmsApplication.setUserToken("Bearer " + bean.getUserToken());
        new UserNetCallBack(this, new CallBack() {

            @Override
            public void callBackLister(int state, UserInfoEntity entity) {
                if (state == UserNetCallBack.SUCCESS) {
                    if (entity == null) {
                        lister.setAnonymityLister(null);
                        return;
                    }
                    entity.setAccount(account);
                    entity.setPwd(pwd);
                    AccountManager.getInstance().setMid(entity.getMID());
                    AccountManager.getInstance().setAccount(account);
                    AccountManager.getInstance().setPwd(pwd);
                    AccountManager.getInstance().reset(true);
                    CmsApplication.setUserInfoEntity(entity, BaseActivity.this);
                }
                lister.setAnonymityLister(CmsApplication.getUserInfoEntity());
            }
        }).getTicketKey(userToken, account, pwd, UserType);
    }

    public void loadGetUserInfo(AnonymityLister lister, int UserType) {
        UserInfoEntity entity = new UserDao(this).getUserInfo(UserType);
        if (entity != null) {
            lister.setAnonymityLister(entity);
        }
    }

    /**
     * 匿名用户登陆
     *
     * @param lister
     */
    public void loadAnonymity(final AnonymityLister lister) {
        UserInfoEntity entity = CmsApplication.getUserInfoEntity();
        if (entity != null) {
            lister.setAnonymityLister(entity);
            return;
        }
        TLog.e(TAG, "wangluodenglu---------1");
        loadLoginInfo(lister);


    }

    public void loadAnonymity(final AnonymityLister lister, boolean isNet) {
        TLog.e(TAG, "wangluodenglu---------2");
        if (isNet) {
            loadLoginInfo(lister);
        }

    }

    private void loadLoginInfo(final AnonymityLister lister) {
        ConfigurationBean configBean = new ConfigDao(this).queryCofig();
        if (configBean != null) {
            if (!Utils.isStringEmpty(configBean.getUserAccount()) && !Utils.isStringEmpty(configBean.getUserKey())) {
                AccountManager.getInstance().setAccount(configBean.getUserAccount());
                AccountManager.getInstance().setPwd(Base64.Base64ToStr(configBean.getUserKey()));
                loadAutoLogin(lister, UserInfoEntity.UserType_Accout);
                return;
            }
            if (!Utils.isStringEmpty(configBean.getOtherToken()) && !Utils.isStringEmpty(configBean.getIsthirdlogin()) && configBean.getIsthirdlogin().equals("true")) {
                TLog.e(TAG + "other", configBean.getOtherToken());
                CmsApplication.setUserToken("Bearer " + configBean.getOtherToken());
                new UserNetCallBack(this, new CallBack() {

                    @Override
                    public void callBackLister(int state, UserInfoEntity entity) {
                        if (state == UserNetCallBack.SUCCESS) {
                            if (entity == null) {
                                lister.setAnonymityLister(null);
                                return;
                            }
                            AccountManager.getInstance().setMid(entity.getMID());
                            AccountManager.getInstance().reset(true);
                            CmsApplication.setUserInfoEntity(entity, BaseActivity.this);
                        }
                        lister.setAnonymityLister(CmsApplication.getUserInfoEntity());
                    }
                }).getTicketKey(CmsApplication.getUserToken(), null, null, UserInfoEntity.UserType_Three);
                return;

            }
        }
//        if (!Utils.isStringEmpty(AccountManager.getInstance().getAccount()) && !Utils.isStringEmpty(AccountManager.getInstance().getPwd())) {
//            loadAutoLogin(lister);
//            return;
//        }
//        if (AccountManager.getInstance().isAutoLogin()) {
//            loadLogin(lister);
//            return;
//        }

        new UserNetCallBack(this, new CallBack() {

            @Override
            public void callBackLister(int state, UserInfoEntity entity) {
                if (state == UserNetCallBack.SUCCESS) {
                    if (entity == null) {
                        return;
                    }
                    CmsApplication.setUserInfoEntity(entity, BaseActivity.this);
                    entity.setAccount(entity.getAccount());
                    AccountManager.getInstance().setGustMid(entity.getMID());
//                    AccountManager.getInstance().setAccount(entity.getAccount());
                    AccountManager.getInstance().reset(true);
                    lister.setAnonymityLister(entity);
                } else {
                    lister.setAnonymityLister(CmsApplication.getUserInfoEntity());
                }

            }
        }).loadInitGust(false);
    }


    public interface AnonymityLister {
        public void setAnonymityLister(UserInfoEntity userinfo);
    }

//    String tokens;
//    public String refreshToken(){
//        new UsInfoCallBack(this,null).refreshToken(new UsInfoCallBack.RefreshTComplete() {
//            @Override
//            public void refreshComplete(boolean isSuccess, String token) {
//               if (isSuccess){
//                   tokens = token;
//               }
//            }
//        });
//        return tokens;
//    }

    public void setNews(TextView newsCount, int count) {
        UserInfoEntity user = CmsApplication
                .getUserInfoEntity();
        if (user != null) {
            if (count > 0) {
//				newsCount.setVisibility(View.VISIBLE);
                newsCount.setVisibility(View.VISIBLE);
                newsCount.setText("" + count);
            } else {
                newsCount.setVisibility(View.GONE);
            }
        }
    }

    protected void remove(String name) {

    }

    protected void startLoading(MyLoadingBar myProgressBar, String loadingText) {
        myProgressBar.setVisibility(View.VISIBLE);
        myProgressBar.setProgressInfo(loadingText);
        myProgressBar.start();
    }

    protected void stopLoading(MyLoadingBar myProgressBar) {
        myProgressBar.stop();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        root = new RelativeLayout(this);
        setContentView(root);
        if (!isShowTitle()){
            root.setClipToPadding(true);
            root.setFitsSystemWindows(true);
        }
        if (isAnimal) {
            setEnterSwichLayout();
        }
        mScrollView = new ScrollView(this);
        mScrollView.setId(R.id.win_mScrollView);

        win_title = new LinearLayout(this);
        win_title.setOrientation(LinearLayout.VERTICAL);
        win_title.setId(R.id.win_title);
        win_menu = new LinearLayout(this);
        win_menu.setId(R.id.win_menu);
        win_content = new LinearLayout(this);
        win_content.setOrientation(LinearLayout.VERTICAL);
        win_content.setId(R.id.win_content);
        win_fullscreen = new LinearLayout(this);
        win_fullscreen.setOrientation(LinearLayout.VERTICAL);
        win_fullscreen.setId(R.id.win_fullscreen);

        LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        root.addView(win_title, lp);

        lp = new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        root.addView(win_menu, lp);

        lp = new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT);
        lp.addRule(RelativeLayout.BELOW, win_title.getId());
        lp.addRule(RelativeLayout.ABOVE, win_menu.getId());

        if (hasScrollView) {
            mScrollView.addView(win_content, lp);
            mScrollView.setVerticalFadingEdgeEnabled(false);
        }
        root.addView(hasScrollView ? mScrollView : win_content, lp);
        Display display = getWindowManager().getDefaultDisplay();
        win_width = display.getWidth();
        win_height = display.getHeight();

        lp = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        root.addView(win_fullscreen,lp);

    }

    public void onRefresh() {
//        int themeType = ThemeUtil.getAppTheme();
//        if (win_content == null) {
//            return;
//        }
//        if (themeType == ThemeUtil.Theme_day) {
////            root.setBackgroundResource(R.color.day_bg_color);
//            getBackResource();
//        } else {
//            root.setBackgroundResource(R.color.night_bg);
//        }
    }

    public void getBackResource() {
        root.setBackgroundResource(R.color.day_bg_color);
    }

    public float getDensity() {
        return density;
    }

    public static int getDensity_int(int mun) {
        return (int) (mun * density);
    }

    public void setDensity(float density) {
        BaseActivity.density = density;
    }

    public int getScreenHeight() {
        return getWindowManager().getDefaultDisplay().getHeight();
    }

    public int getScreenWidth() {
        return getWindowManager().getDefaultDisplay().getWidth();
    }

    @Override
    protected void onStart() {
        super.onStart();

        gCurrentActivity = this;
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getWindow().setWindowAnimations(0);
    }

    @Override
    protected void onResume() {
        onRefresh();
        super.onResume();
//        MobclickAgent.onResume(this); //����ͳ��session
    }


    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this); //����ͳ��session
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private int dataListSize;

    public void setLisViewSize(int size) {
        this.dataListSize = size;
    }

    public boolean isListViewBottom() {
        if (dataListSize < Config.LIST_ITEM_COUNT) {
            return false;
        }
        return true;
    }

    public void displayToast(String text) {
        if (text == null)
            return;
        displayByselfToast(this, text, Toast.LENGTH_SHORT).show();
    }

    private static Toast result = null;
    public static Toast displayByselfToast(Context content, CharSequence text,
                                           int duration) {
        if (null != result) {
            result.cancel();
        }
        result = new Toast(content);
        LayoutInflater inflater = (LayoutInflater) content
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.myself_toast, null);
        TextView tv = (TextView) layout.findViewById(R.id.message_toast);
        tv.setText(text);
        int get_height = win_height / 4;
        result.setView(layout);
        result.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, get_height);
        result.setDuration(duration);

        return result;
    }

    public void displayMsgBox(String title, String content) {
        final MsgBoxNotice boxNotice = new MsgBoxNotice(this, title, content,
                DialogBase.Win_Center);
        boxNotice.showDialog(DialogBase.AnimalTop);
    }

    public void displayMsgBoxE(String title, String content,
                               MsgBoxEOnClickListener lister) {
        MsgBoxNoticeE mbn = new MsgBoxNoticeE(this, title, content,
                DialogBase.Win_Center);
        mbn.showDialog(DialogBase.AnimalTop);
        mbn.setMsgOnClickListener(lister);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            setExitSwichLayout();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void OnClickListenEvent(View v) {

    }

    public OnClickListener viewOnClickListen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            OnClickListenEvent(v);
        }
    };

    public interface ScrollViewRefreshLister {
        public void callBack();
    }

    @Override
    public void setEnterSwichLayout() {
        overridePendingTransition(R.anim.slide_in_from_right,
                R.anim.slide_out_from_right);
    }

    @Override
    public void setExitSwichLayout() {
        finish();
        overridePendingTransition(R.anim.slide_in_from_left,
                R.anim.slide_out_from_left);
    }

    protected void destory() {
        ActivityStackControlUtil.finishProgram();
        SimpleSQLDataBaseManager manager = SimpleSQLDataBaseManager
                .getInstance();
        manager.closeAll();
        CmsApplication.getDataStratey().clear();
        CmsApplication.getImageStrategy().clear();
        ReadedCacheManager.getInstance().clear();
        AdCacheManager.getInstance().clear();
        AdCacheManager2.getInstance().clear();
    }

    public static class ActivityStackControlUtil {
        private static List<Activity> activityList = new ArrayList<Activity>();

        public static int getCounter() {
            return activityList.size();
        }

        public static void remove(Activity activity) {
            activityList.remove(activity);
        }

        public static void add(Activity activity) {
            activityList.add(activity);
        }

        public static void finishProgram() {
            for (Activity activity : activityList) {
                if (activity != null) {
                    activity.finish();
                }
            }
            System.gc();
        }

        public static void finishAllActivityWithout(Activity withoutActivity) {

            for (int index = activityList.size() - 1; index >= 0; index--) {
                if (withoutActivity != activityList.get(index)) {
                    activityList.get(index).finish();
                    activityList.remove(index);
                }
            }
        }
    }

    /**
     * 强制隐藏
     */
    public void hideInput(View view) {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            textChanged();
        }
    };

    public void textChanged() {
    }

    ;
}