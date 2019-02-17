package com.baiyi.jj.app.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.user.UsRegistAct;
import com.baiyi.jj.app.activity.user.MyCollectActivity;
import com.baiyi.jj.app.activity.user.UsLoginAct;
import com.baiyi.jj.app.activity.user.center.CountrySetAct;
import com.baiyi.jj.app.activity.user.center.CreditsHistoryAct;
import com.baiyi.jj.app.activity.user.center.PayPalAct;
import com.baiyi.jj.app.activity.user.center.PayPalSetAct;
import com.baiyi.jj.app.activity.user.center.UserInfoAct;
import com.baiyi.jj.app.activity.user.center.WithdrawAct;
import com.baiyi.jj.app.activity.user.center.WithdrawHistoryAct;
import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.language.SwitchLanguageUtils;
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
import com.baiyi.jj.app.utils.UserHeadUtils;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.ShareView;
import com.baiyi.jj.app.views.pageview.BasePageView;
import com.baiyi.jj.core.basedialog.DialogBase;
import com.baiyi.jj.skin.entity.DynamicAttr;
import com.turbo.turbo.mexico.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 主页tab－设置
 * Created by tangkun on 16/9/8.
 */
public class SettingPager extends BasePageView {

    @Bind(R.id.img_head)
    CircleImageView imageHead;

    @Bind(R.id.txt_name)
    TextView txtName;

    // 退出登录
    @Bind(R.id.lin_logoutbtn)
    LinearLayout linLogoutBtn;

    @Bind(R.id.lin_have_login)
    LinearLayout linHaveLogin;
    @Bind(R.id.lin_no_login)
    LinearLayout linNoLogin;

    private Context context;

    public SettingPager(Context context) {
        super(context);
        this.context = context;
        initViews();
    }

    private void initViews() {

        ContextUtil.getLayoutInflater(getContext()).inflate(R.layout.activity_tab_set, this);
        ButterKnife.bind(this);

        loadMemInfo();
    }

    private void updateViewDate(UserInfoEntity entities) {

        if (entities != null) {
            if (entities.getUserType() != UserInfoEntity.UserType_Gust) {
                showUnLoginedView(View.VISIBLE);
            } else {
                showUnLoginedView(View.GONE);
            }
            txtName.setText(Utils.isStringEmpty(entities.getName()) ? entities.getDisplay() : entities.getName());
            UserHeadUtils.getInstance(getContext()).loadImage(imageHead, Utils.getNoNullString(entities.getAvatar()));
        } else {
            showUnLoginedView(View.GONE);
        }

    }

    /**
     * 是否显示未登录view
     */
    private void showUnLoginedView(int v) {
        linLogoutBtn.setVisibility(v);
        if (v == View.VISIBLE){
            linHaveLogin.setVisibility(View.VISIBLE);
            linNoLogin.setVisibility(View.GONE);
        }else{
            linHaveLogin.setVisibility(View.GONE);
            linNoLogin.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void notifyVisitorChanged() {
        super.notifyVisitorChanged();
    }

    @Override
    protected void setCreate(boolean isCreate) {
        super.setCreate(isCreate);
    }

    @Override
    public void visible() {
        super.visible();
    }

    @Override
    public void disVisible() {
        super.disVisible();
    }

    @Override
    protected void setVisible(boolean visible) {
        super.setVisible(visible);
    }


    @Override
    public void onResume() {
        super.onResume();
        UserInfoEntity user = CmsApplication.getUserInfoEntity();
        if (user != null) {
            updateViewDate(user);
            loadIntegral();
        } else {
            loadMemInfo();
        }

    }

    private void loadIntegral() {
        new UserNetCallBack().loadIntegral(new UserNetCallBack.CallIntegralBack() {
            @Override
            public void callIntegralBackLister(final String s) {
                ((BaseActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!Utils.isStringEmpty(s)) {
                        }
                    }
                });
            }
        });
    }

    private void loadMemInfo() {
        ((BaseActivity) getContext()).loadAnonymity(new BaseActivity.AnonymityLister() {
            @Override
            public void setAnonymityLister(UserInfoEntity userinfo) {
                updateViewDate(userinfo);
            }
        }, true);

//        UserInfoEntity infoEntity = CmsApplication.getUserInfoEntity();
//        if (infoEntity == null){
//            return;
//        }
//
//        new UsInfoCallBack(context, new UsInfoCallBack.CallBack() {
//            @Override
//            public void callBack(int state, UserInfoEntity entity) {
//                if (UsInfoCallBack.SUCCESS == state) {
//                    CmsApplication.setUserInfoEntity(entity, getContext());
//                    updateViewDate(entity);
//                } else {
////                    ((BaseActivity) context).displayToast(entity.getMsg());
//                    return;
//                }
//            }
//        }).loadMemberInfo(infoEntity.getUserType());
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public boolean isVisible() {
        return super.isVisible();
    }

    @Override
    public boolean isCreated() {
        return super.isCreated();
    }

    @Override
    public void dynamicAddView(View arg0, List<DynamicAttr> arg1) {
        super.dynamicAddView(arg0, arg1);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRefresh(boolean isFontChange) {
        super.onRefresh(isFontChange);
    }


    @Override
    public void onRefreshToNetWorkChange(boolean isGprs) {
        super.onRefreshToNetWorkChange(isGprs);
    }

    @OnClick({R.id.lin_loginview,
            R.id.btn_center_paypal, R.id.btn_center_withdraw,
            R.id.btn_withdraw_history, R.id.btn_credits_history,
            R.id.txt_sign_in,
            R.id.btn_logout, R.id.btn_center_contactus,
            R.id.btn_center_collection, R.id.btn_sharetofriend})
    public void onClick(View view) {
        int id = view.getId();
        UserInfoEntity userInfoEntity = CmsApplication.getUserInfoEntity();
        switch (id) {
            case R.id.txt_sign_in:
                Intent signIntent = new Intent(getContext(), UsLoginAct.class);
                getContext().startActivity(signIntent);
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
                String msgTitle = getContext().getString(R.string.name_jj);

                String url = NetUrl.getShareToFriend();
                String msgContent = getContext().getString(R.string.txt_share_content) + " " + url;
                ShareView.shareMsg(getContext(), msgTitle, msgContent, null);
                break;
            case R.id.btn_center_contactus:
                // contanct us
                ShareEMail();
                break;
            case R.id.btn_logout:
                String Str = getResources().getString(R.string.tip_dialog_title12);
                QuitDialog dialog = new QuitDialog(getContext(), DialogBase.Win_Center,Str);
                dialog.setQuitOnClickListener(new QuitDialog.QuitOnClickListener() {
                    @Override
                    public void onClickListener() {
                        // facebook
                        FaceBookLogin.getInstence(getContext()).logout();
                        new ConfigDao(getContext()).updateOtherToken("", String.valueOf(false));
                        // 不保存密码
                        new ConfigDao(getContext()).updateAccoutAndPwd(AccountManager.getInstance().getAccount(), "");
                        AccountManager.getInstance().reset(false);
                        UserInfoEntity gustinfo = new UserDao(getContext()).getUserInfo(UserInfoEntity.UserType_Gust);
                        CmsApplication.setUserInfoEntity(gustinfo, getContext());
                        updateViewDate(gustinfo);
                        ((BaseActivity) getContext()).loadAnonymity(new BaseActivity.AnonymityLister() {
                            @Override
                            public void setAnonymityLister(UserInfoEntity entity) {
                                updateViewDate(entity);
                            }
                        }, true);
                    }
                });
                dialog.showDialog(DialogBase.AnimalTop);
                break;
        }
    }

    private void contr() {
        Intent intentCountry = new Intent(getContext(), CountrySetAct.class);
        intentCountry.putExtra(MemberConfig.Country, "");
        ((BaseActivity) getContext()).startActivityForResult(intentCountry, MemberConfig.Member_RequestCode);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (MemberConfig.Login_Success == resultCode) {
            updateViewDate(CmsApplication.getUserInfoEntity());
            showUnLoginedView(View.VISIBLE);
        }
    }

}
