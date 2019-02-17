package com.baiyi.jj.app.activity.main.fragment;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baiyi.core.file.Preference;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.BaseAnalyticsActivity;
import com.baiyi.jj.app.activity.main.TestActivity;
import com.baiyi.jj.app.activity.user.MyCollectActivity;
import com.baiyi.jj.app.activity.user.UsLoginAct;
import com.baiyi.jj.app.activity.user.about.OtherAct;
import com.baiyi.jj.app.activity.user.center.CreditsHistoryAct;
import com.baiyi.jj.app.activity.user.center.LanguageSetAct;
import com.baiyi.jj.app.activity.user.center.PayPalAct;
import com.baiyi.jj.app.activity.user.center.PayPalSetAct;
import com.baiyi.jj.app.activity.user.center.SetupUsAct;
import com.baiyi.jj.app.activity.user.center.UserInfoAct;
import com.baiyi.jj.app.activity.user.center.WithdrawAct;
import com.baiyi.jj.app.activity.user.center.WithdrawHistoryAct;
import com.baiyi.jj.app.activity.user.channel.OfflineAct;
import com.baiyi.jj.app.activity.user.channel.UsChannelAct;
import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.activity.user.entity.LanguageEntities;
import com.baiyi.jj.app.activity.user.login.BaseLoginActivity;
import com.baiyi.jj.app.activity.user.net.setting.SettingManager;
import com.baiyi.jj.app.activity.user.net.setting.SettingUrl;
import com.baiyi.jj.app.language.SwitchLanguageUtils;
import com.baiyi.jj.app.manager.CountryMannager;
import com.baiyi.jj.app.manager.NotifactionManager;
import com.baiyi.jj.app.manager.RandomManager;
import com.baiyi.jj.app.manager.facebook.FaceBookLogin;
import com.baiyi.jj.app.activity.user.net.AppUtils;
import com.baiyi.jj.app.activity.user.net.NetUrl;
import com.baiyi.jj.app.activity.user.net.UserNetCallBack;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.cache.Dao.ConfigDao;
import com.baiyi.jj.app.cache.Dao.UserDao;
import com.baiyi.jj.app.dialog.QuitDialog;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.PreventDoubleClickUtils;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.UserHeadUtils;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.utils.XMLName;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.baiyi.jj.app.views.ShareView;
import com.baiyi.jj.core.basedialog.DialogBase;
import com.google.android.gms.analytics.Tracker;
import com.turbo.turbo.mexico.R;

import org.json.JSONArray;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/1/10 0010.
 */
public class UserFragment extends Fragment {

    private String name;

    @Bind(R.id.img_head)
    CircleImageView imageHead;
    @Bind(R.id.txt_name)
    TextView txtName;
    @Bind(R.id.lin_logoutbtn)
    LinearLayout linLogoutBtn;
    @Bind(R.id.lin_have_login)
    LinearLayout linHaveLogin;
    @Bind(R.id.lin_no_login)
    LinearLayout linNoLogin;
    @Bind(R.id.lin_loginview)
    RelativeLayout linLoginView;
    @Bind(R.id.facebook_login)
    ImageView facebookLogin;
    @Bind(R.id.my_loading)
    MyLoadingBar loading;
    @Bind(R.id.btn_sharetofriend)
    LinearLayout linShare;

    @Bind(R.id.bg_have_login)
    ImageView bg_have_login;
    @Bind(R.id.bg_no_login)
    ImageView bg_no_login;
    @Bind(R.id.txt_currentver)
    TextView txtCurrentVer;
    @Bind(R.id.img_newver)
    ImageView imgNewVer;
    @Bind(R.id.img_newver_arrow)
    ImageView imgNewVerArrow;
    @Bind(R.id.btn_newver)
    LinearLayout linNewVer;


    public static final String NewsVersionAction = "android.intent.action.newVersionReceiver";


    public static UserFragment newInstance(String titleName) {
        UserFragment fragment = new UserFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", titleName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getArguments().getString("name");
        regiesterReceiver();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_tab_set, container, false);
        ButterKnife.bind(this, view);
//        bg_have_login .getLayoutParams().height = linHaveLogin.getLayoutParams().height;
//        bg_no_login .getLayoutParams().height = linNoLogin.getLayoutParams().height;

        txtCurrentVer.setText(getResources().getString(R.string.tip_currentver)+" "+Config.getInstance().getVersionName(getContext()));
        linNewVer.setClickable(false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadMemInfo();
    }

    BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (imgNewVer != null){
                imgNewVer.setVisibility(View.VISIBLE);
                imgNewVerArrow.setVisibility(View.VISIBLE);
                linNewVer.setClickable(true);
            }
        }
    };
    private void regiesterReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(NewsVersionAction);
        getContext().registerReceiver(messageReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (messageReceiver != null) {
            getContext().unregisterReceiver(messageReceiver);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (receiver != null){
//            registerReceiver();
//        }
        if (loading != null && loading.getVisibility() == View.VISIBLE){
            return;
        }
        UserInfoEntity user = CmsApplication.getUserInfoEntity();
        if (user != null) {
            updateViewDate(user);
//            if (Preference.getInstance().getBoolean(XMLName.XML_UpdateLanguage+user.getMID(), true)) {
//                TLog.e("save","save-------------");
//                loadSaveLanguage();
//            }
//            loadIntegral();
        } else {

            loadMemInfo();
        }
    }

//    private void loadIntegral() {
//        new UserNetCallBack().loadIntegral(new UserNetCallBack.CallIntegralBack() {
//            @Override
//            public void callIntegralBackLister(final String s) {
//                if (null == getContext()) {
//                    return;
//                }
//                ((BaseActivity) getContext()).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (!Utils.isStringEmpty(s)) {
//                        }
//                    }
//                });
//            }
//        });
//    }

    private void loadMemInfo() {
        if (Utils.isStringEmpty(CmsApplication.getUserToken())){
            return;
        }
        Preference preference = Preference.getInstance();
        int num = preference.getInt(XMLName.XML_Three_Start, 2);
        if (num == 2){
            return;
        }
        ((BaseActivity) getContext()).loadAnonymity(new BaseActivity.AnonymityLister() {
            @Override
            public void setAnonymityLister(UserInfoEntity userinfo) {
                updateViewDate(userinfo);
                if (userinfo== null)
                    return;
                Preference preference = Preference.getInstance();
                preference.Set(XMLName.XML_UserID,userinfo.getMID());
                preference.saveConfig();
                loadSaveLanguage();
            }
        }, true);
    }

    private void updateViewDate(UserInfoEntity entities) {

        if (null == getContext()) {
            return;
        }
        if (entities != null) {
            if (entities.getUserType() != UserInfoEntity.UserType_Gust) {
                showUnLoginedView(View.VISIBLE, entities);
            } else {
                showUnLoginedView(View.GONE, entities);
            }
        } else {
            showUnLoginedView(View.GONE, entities);
        }
    }

    /**
     * 登录与未登录
     */
    private void showUnLoginedView(int v, UserInfoEntity entities) {
        linLogoutBtn.setVisibility(v);
        if (v == View.VISIBLE) {//当前为登录状态
            linHaveLogin.setVisibility(View.VISIBLE);
            bg_have_login.setVisibility(View.VISIBLE);
            linNoLogin.setVisibility(View.GONE);
            bg_no_login.setVisibility(View.GONE);
            linLoginView.setClickable(true);
            txtName.setText(Utils.isStringEmpty(entities.getName()) ? entities.getDisplay() : entities.getName());
            UserHeadUtils.getInstance(getContext()).loadImage(imageHead, Utils.getNoNullString(entities.getAvatar()));
        } else {//当前为未登录状态
            linHaveLogin.setVisibility(View.GONE);
            bg_have_login.setVisibility(View.GONE);
            linNoLogin.setVisibility(View.VISIBLE);
            bg_no_login.setVisibility(View.VISIBLE);
            linLoginView.setClickable(false);
        }
    }

    @OnClick({R.id.lin_loginview, R.id.btn_setting,
            R.id.btn_center_paypal, R.id.btn_center_withdraw,
            R.id.btn_withdraw_history, R.id.btn_credits_history,
            R.id.btn_offline, R.id.txt_sign_in,R.id.btn_language,
            R.id.btn_logout, R.id.btn_center_contactus,
            R.id.btn_center_collection, R.id.btn_sharetofriend,R.id.facebook_login,R.id.btn_other,R.id.btn_newver})
    public void onClick(View view) {
        int id = view.getId();
        UserInfoEntity userInfoEntity = CmsApplication.getUserInfoEntity();
        switch (id) {
            case R.id.txt_sign_in:
                Intent signIntent = new Intent(getContext(), UsLoginAct.class);
                getContext().startActivity(signIntent);
                break;
            case R.id.btn_setting:
                Intent intentSet = new Intent(getContext(), SetupUsAct.class);
                getContext().startActivity(intentSet);
//                new UserNetCallBack(getContext(), new UserNetCallBack.CallBack() {
//                    @Override
//                    public void callBackLister(int state, UserInfoEntity entity) {
//
//                    }
//                });
                break;
            case R.id.lin_loginview:
                Intent infointent = new Intent(getContext(), UserInfoAct.class);
                getContext().startActivity(infointent);
                break;
            case R.id.btn_center_paypal:
                UserInfoEntity infoEntity = CmsApplication.getUserInfoEntity();
                if (infoEntity != null && infoEntity.getUserType() != UserInfoEntity.UserType_Gust) {
                    if (!Utils.isStringEmpty(infoEntity.getPaypal())) {
                        Intent intentPaypal = new Intent(getContext(), PayPalAct.class);
                        getContext().startActivity(intentPaypal);
                    } else {
                        Intent intentPaypal = new Intent(getContext(), PayPalSetAct.class);
                        getContext().startActivity(intentPaypal);
                    }
                } else {
                    AppUtils.loginRemind(getContext(), "", "");
                }

                break;
            case R.id.btn_withdraw_history:
                if (userInfoEntity != null && userInfoEntity.getUserType() != UserInfoEntity.UserType_Gust) {
                    Intent intentWithdrawHistory = new Intent(getContext(), WithdrawHistoryAct.class);
                    getContext().startActivity(intentWithdrawHistory);
                } else {
                    AppUtils.loginRemind(getContext(), "", "");
                }

                break;
            case R.id.btn_center_withdraw:
                if (userInfoEntity != null && userInfoEntity.getUserType() != UserInfoEntity.UserType_Gust) {
                    Intent intentWithdraw = new Intent(getContext(), WithdrawAct.class);
                    getContext().startActivity(intentWithdraw);
                } else {
                    AppUtils.loginRemind(getContext(), "", "");
                }

                break;
            case R.id.btn_credits_history:
                Intent intentCreditsHistory = new Intent(getContext(), CreditsHistoryAct.class);
                getContext().startActivity(intentCreditsHistory);
                break;

            case R.id.btn_center_collection:
                Intent intentCollection = new Intent(getContext(), MyCollectActivity.class);
                getContext().startActivity(intentCollection);
                break;

            case R.id.btn_sharetofriend:

                PreventDoubleClickUtils.execute(linShare);

                String msgTitle = getContext().getString(R.string.name_jj);

                String url = NetUrl.getShareToFriend();
                String msgContent = getContext().getString(R.string.txt_share_content) + " " + url;
                ((BaseActivity)getActivity()).addEnvent(BaseAnalyticsActivity.Envent_share_APP);
                ShareView.shareMsg(getContext(), msgTitle, msgContent, null);

                if (!Config.isRelease){
                    ((BaseActivity)getActivity()).sendDownLoadTest_Tofriend();
                }

//                Intent intents = new Intent(getContext(), UsChannelAct.class);
//                startActivity(intents);
//
//                PreventDoubleClickUtils.execute(linShare);
//
//                String msgTitle = getContext().getString(R.string.name_jj);
//
//                String url = NetUrl.getShareToFriend(CmsApplication.getUserInfoEntity() != null ? CmsApplication.getUserInfoEntity().getMID() : "",
//                        Constant.APP_ID, SwitchLanguageUtils.getCurrentLanguage());
//                String msgContent = getContext().getString(R.string.txt_share_content) + " " + url;
//                ShareView.shareMsg(getContext(), msgTitle, msgContent, null);
                break;
            case R.id.btn_language:
                Intent intentLanguage = new Intent(getContext(), LanguageSetAct.class);
                getContext().startActivity(intentLanguage);
                break;
            case R.id.btn_offline:
                Intent intent = new Intent(getContext(),OfflineAct.class);
                startActivity(intent);

//                String imgurl3 = "http://lumen-mx.fission.arityapp.com/0c273f86-5087-11e7-bb2b-06bccf2cfc5c.jpg?thumbnail/3/690x314/quality/60";
//                CmsApplication application3 = CmsApplication.getApp();
//                Tracker mTracker3 = application3.getDefaultTracker();
//                NotifactionManager.sendNotifica(getContext(),mTracker3,"testtitle","test----content","http://","100001",imgurl3,"2",true,null);
                break;
            case R.id.btn_other:
                Intent oterintent = new Intent(getContext(),OtherAct.class);
//                Intent oterintent = new Intent(getContext(),TestActivity.class);
                startActivity(oterintent);
//                TLog.e("suiji--","----"+RandomManager.getInstance().getRandomNum());

//                String imgurl = "https://i.ytimg.com/vi/Cdl9RP7v_VE/hqdefault.jpg?thumbnail/3/480x360/quality/60";
//                CmsApplication application = CmsApplication.getApp();
//                Tracker mTracker = application.getDefaultTracker();
//                NotifactionManager.sendNotifica(getContext(),mTracker,"testtitle","test----content","http://","100001","","0",true,null);

                break;
            case R.id.btn_center_contactus:
                // contanct us

                ShareEMail();
//                String imgurl2 = "http://lumen-mx.fission.arityapp.com/0c273f86-5087-11e7-bb2b-06bccf2cfc5c.jpg?thumbnail/3/690x314/quality/60";
//                CmsApplication application2 = CmsApplication.getApp();
//                Tracker mTracker2 = application2.getDefaultTracker();
//                NotifactionManager.sendNotifica(getContext(),mTracker2,1,"testtitle","test----content","http://","100001","","1",true,null);

                break;
            case R.id.btn_logout:
                String Str = getResources().getString(R.string.tip_dialog_title12);
                QuitDialog dialog = new QuitDialog(getContext(), DialogBase.Win_Center, Str);
                dialog.setQuitOnClickListener(new QuitDialog.QuitOnClickListener() {
                    @Override
                    public void onClickListener() {
                        // facebook
                        FaceBookLogin.getInstence(getContext()).logout();
//                        GoogleLoginMannager.getInstence(getActivity()).signOut();
                        new ConfigDao(getContext()).updateOtherToken("", String.valueOf(false));
                        // 不保存密码
                        new ConfigDao(getContext()).updateAccoutAndPwd(AccountManager.getInstance().getAccount(), "");
                        AccountManager.getInstance().reset(false);
                        final UserInfoEntity gustinfo = new UserDao(getContext()).getUserInfo(UserInfoEntity.UserType_Gust);
                        CmsApplication.setUserInfoEntity(gustinfo, getContext());
                        updateViewDate(gustinfo);
                        if (gustinfo != null) {
                            sendCast();
                        }
                        ((BaseActivity) getContext()).loadAnonymity(new BaseActivity.AnonymityLister() {
                            @Override
                            public void setAnonymityLister(UserInfoEntity entity) {
                                if (gustinfo == null) {
                                    sendCast();
                                }
                                updateViewDate(entity);
                            }
                        }, true);
                    }
                });
                dialog.showDialog(DialogBase.AnimalTop);
                break;
            case R.id.facebook_login:
                ((BaseLoginActivity)getActivity()).initEdit(getActivity(),null,null,loading);
                ((BaseLoginActivity)getActivity()).facebookLogin();
                ((BaseLoginActivity)getActivity()).setLoginComplete(new BaseLoginActivity.LoginComplete() {
                    @Override
                    public void callComplete() {
                        UserInfoEntity user = CmsApplication.getUserInfoEntity();
                        if (user != null) {
                            updateViewDate(user);
                        } else {
                            loadMemInfo();
                        }
                    }
                });
                break;
            case R.id.btn_newver:
                String packageName = getContext().getPackageName();
                Intent intentToNew = new Intent(Intent.ACTION_VIEW);
                intentToNew.setData(Uri.parse("market://details?id="+packageName));
                getContext().startActivity(intentToNew);
                break;
        }
    }


    private void sendCast() {
        Intent intent = new Intent();
        intent.setAction(NewsFragment.NewsListAction);
        getActivity().sendBroadcast(intent);
    }

    private void ShareEMail() {
        Intent email = new Intent(android.content.Intent.ACTION_SEND);
//		email.setType("plain/text");
        // i.setType("text/plain"); //模拟器请使用这行
        email.setType("message/rfc822"); // 真机上使用这行
        email.putExtra(Intent.EXTRA_EMAIL,
                new String[]{Constant.Lumen_Email});
        email.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.txt_contact_us));
//		startActivity(Intent.createChooser(email,
//				"Select email application."),1001);
        ((BaseActivity) getContext()).startActivityForResult(
                Intent.createChooser(email, getResources().getString(R.string.txt_share2)), 1001);

    }

    private void loadSaveLanguage() {

        JsonLoader loader = new JsonLoader(getContext());
        loader.setUrl(SettingUrl.getModifyLanguageUrl());
        loader.setUrlName(MemberConfig.Modify_Language);
        loader.setType(BaseNetLoder.APPLICATION_JSON);
        loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
        loader.setPostData(SettingManager.getModifyLanguagePostData2(SwitchLanguageUtils.languageid_ES));
        loader.setMethod(BaseNetLoder.Method_Post);
        loader.setLoaderListener(new Loader.LoaderListener() {

            @Override
            public void onProgress(Object tag, long curByteNum,
                                   long totalByteNum) {
            }

            @Override
            public void onError(Object tag, int responseCode,
                                String errorMessage) {
            }

            @Override
            public void onCompelete(Object tag, Object result) {
                // TODO Auto-generated method stub
                try {
                    JSONArray array = (JSONArray) result;
                    if (JsonParse.getState200(array)){
                        UserInfoEntity userinfo = CmsApplication.getUserInfoEntity();
                        Preference.getInstance().Set(XMLName.XML_UpdateLanguage+userinfo.getMID(),String.valueOf(false));
                        Preference.getInstance().saveConfig();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        CmsApplication.getDataStratey().startLoader(loader);
    }
}
