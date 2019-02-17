package com.baiyi.jj.app.activity.user.center;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.user.MemberNameActivity;
import com.baiyi.jj.app.activity.user.UsLoginAct;
import com.baiyi.jj.app.activity.user.UsRegistAct;
import com.baiyi.jj.app.activity.user.ValidateEmailActivity;
import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.activity.user.login.UsInfoCallBack;
import com.baiyi.jj.app.activity.user.net.head.UserHeadNet;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.dialog.UpLoadHeadImageDialog;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.UserHeadUtils;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.LetterSpacingTextView;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.turbo.turbo.mexico.R;
import com.ycl.chooseavatar.library.OnChoosePictureListener;
import com.ycl.chooseavatar.library.YCLTools;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 用户基本信息
 * 作者：lizl on 2016/11/23 14:59
 */
public class UserInfoAct extends BaseActivity implements OnChoosePictureListener {

    @Bind(R.id.title_name)
    LetterSpacingTextView titleName;
    @Bind(R.id.img_back)
    ImageView imgBack;

    @Bind(R.id.my_center_name_txt)
    TextView myCenterNameTxt;
    @Bind(R.id.my_center_email)
    TextView myCenterEmail;
    @Bind(R.id.user_pwd_text)
    TextView userPwdText;
    @Bind(R.id.img_head)
    CircleImageView userHead;
    @Bind(R.id.txt_center_accout)
    TextView txtAccount;
    @Bind(R.id.loading)
    MyLoadingBar loading;
    @Bind(R.id.btn_sign_in)
    Button btnSignIn;
    @Bind(R.id.btn_sign_up)
    Button btnSignUp;

    UpLoadHeadImageDialog headImageDialog;


    private UserInfoEntity user;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);

        View titleView = ContextUtil.getLayoutInflater(this).inflate(
                R.layout.title_left, null);
        win_title.addView(titleView);
        View contentView = ContextUtil.getLayoutInflater(this).inflate(
                R.layout.act_user_info, null);
        win_content.addView(contentView);
        ButterKnife.bind(this);
        initTitle();
        YCLTools.getInstance().setOnChoosePictureListener(this);

        loadMemInfo(false);
    }

    private void initTitle() {
        imgBack.setVisibility(View.VISIBLE);
        titleName.setText(getResources().getString(R.string.txt_base_info));
//        titleName.setTypeface(CmsApplication.getTitleFace(this));
    }

    @Override
    public void onResume() {
        super.onResume();

        user = CmsApplication.getUserInfoEntity();
        if (null != user) {
            updateViewDate(user);
        } else {
            loadMemInfo(false);
        }
    }

    private void loadMemInfo(boolean isNet) {
       loadAnonymity(new AnonymityLister() {
           @Override
           public void setAnonymityLister(UserInfoEntity userinfo) {
               updateViewDate(userinfo);
           }
       },isNet);
    }

    private void updateViewDate(UserInfoEntity entities) {

        if (entities != null) {
            if (entities.getUserType()!=UserInfoEntity.UserType_Gust){
                myCenterNameTxt.setText(Utils.getNoNullString(entities.getName()));
                myCenterEmail.setText(Utils.getNoNullString(entities.getEmail()));
                userPwdText.setText(getResources().getString(R.string.txt_set));
                txtAccount.setText(Utils.getNoNullString(entities.getDisplay()));
                btnSignIn.setVisibility(View.GONE);
                btnSignUp.setVisibility(View.GONE);
                UserHeadUtils.getInstance(this).loadImage(userHead, Utils.getNoNullString(entities.getAvatar()));
            }else {
                myCenterNameTxt.setText(Utils.isStringEmpty(entities.getName())?getResources().getString(R.string.txt_gust_show):entities.getName());
                myCenterEmail.setText("");
                userPwdText.setText(getResources().getString(R.string.txt_set));
                txtAccount.setText("");
                btnSignIn.setVisibility(View.VISIBLE);
                btnSignUp.setVisibility(View.VISIBLE);
                UserHeadUtils.getInstance(this).loadImage(userHead, Utils.getNoNullString(entities.getAvatar()));
            }

        }
    }

    @OnClick({R.id.img_back, R.id.btn_center_head, R.id.btn_center_name, R.id.btn_center_email,
            R.id.btn_center_loginpassword, R.id.btn_center_accout,R.id.btn_sign_in,R.id.btn_sign_up})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.img_back:
                setExitSwichLayout();
                break;
            case R.id.btn_center_head:
                if (null==headImageDialog){
                    headImageDialog = new UpLoadHeadImageDialog(this);
                }
                headImageDialog.show();
                break;
            case R.id.btn_center_name:
                Intent intentName = new Intent(this, NameSetAct.class);
                startActivity(intentName);
                break;
            case R.id.btn_center_email:
                if (user == null || user.getUserType()==UserInfoEntity.UserType_Gust) {
                    goLogin();
                    return;
                }
                Intent intentMail = new Intent(this, ValidateEmailActivity.class);
                intentMail.putExtra(MemberConfig.Key, MemberConfig.Modify_Email);
                startActivity(intentMail);
                break;
            case R.id.btn_center_loginpassword:
                if (user == null || user.getUserType()==UserInfoEntity.UserType_Gust) {
                    goLogin();
                    return;
                }
                Intent intentSetPwd = new Intent(this, PwdSetAct.class);
                intentSetPwd.putExtra(Define.PwdType, PwdSetAct.Type_LoginPwd);
                startActivity(intentSetPwd);
                break;
            case R.id.btn_center_accout:
                if (user == null || user.getUserType()==UserInfoEntity.UserType_Gust) {
                    goLogin();
                    return;
                }
                Intent intentMemberName = new Intent(this, MemberNameActivity.class);
                startActivity(intentMemberName);
                break;
            case R.id.btn_sign_in:
                Intent intent = new Intent(this, UsLoginAct.class);
                startActivity(intent);
                break;
            case R.id.btn_sign_up:
                Intent intent1 = new Intent(this, UsRegistAct.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data || requestCode == YCLTools.MODE_UPLOAD_IMAGE_CAMERA) {
            YCLTools.getInstance().upLoadImage(requestCode, resultCode, data);
        }
    }

    @Override
    public void OnChoose(final String filePath) {
        startLoading();
        new UserHeadNet(new UserHeadNet.OnUpLoadHeadCallBack() {
            @Override
            public void isSuccessful(final boolean success) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        stopLoading();
                        if (success) {
//                            displayToast(getString(R.string.txt_save_success));
                            UserHeadUtils.getInstance(UserInfoAct.this).clearBitmap(user.getAvatar());
                            loadMemInfo(true);
                        } else {
                            displayToast(getString(R.string.tip_change_avatar_fail));
                        }
                    }
                });

            }
        }).UpLoadHead(filePath);
    }

    @Override
    public void OnCancel() {
    }

    public void goLogin() {
        Intent intent = new Intent(this, UsLoginAct.class);
        this.startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (null==headImageDialog){
            headImageDialog = new UpLoadHeadImageDialog(this);
        }
        headImageDialog.doNext(requestCode,grantResults);
    }

    private void startLoading() {
        loading.setVisibility(View.VISIBLE);
        loading.setProgressInfo(getResources().getString(R.string.txt_progress_loading));
        loading.start();
    }

    private void stopLoading() {
        if (loading != null) {
            loading.stop();
            loading.setVisibility(View.GONE);
        }
    }
}
