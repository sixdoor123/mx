package com.baiyi.jj.app.manager;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.user.net.head.UserHeadNet;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.utils.UserHeadUtils;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.turbo.turbo.mexico.R;
import com.ycl.chooseavatar.library.OnChoosePictureListener;
import com.ycl.chooseavatar.library.YCLTools;

/**
 * ���ߣ�lizl on 2017/1/13 16:59
 */
public class UserHeadManager implements OnChoosePictureListener {

    private static UserHeadManager manager;
    private static MyLoadingBar loading;
    private BaseActivity context;

    public UserHeadManager(Context context) {
        this.context = ((BaseActivity)context);
        YCLTools.getInstance().setOnChoosePictureListener(this);
    }

    public static UserHeadManager getInstance(Context context,MyLoadingBar loading,OnHeadCall onHeadCall) {
        if (null == manager) {
            manager = new UserHeadManager(context);
            UserHeadManager.loading = loading;
            UserHeadManager.onHeadCall = onHeadCall;
        }
        return manager;
    }

    @Override
    public void OnChoose(String filePath) {

        startLoading();
        new UserHeadNet(new UserHeadNet.OnUpLoadHeadCallBack() {
            @Override
            public void isSuccessful(final boolean success) {

                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        stopLoading();
                        if (success) {
//                            context.displayToast(context.getString(R.string.txt_save_success));
                            UserHeadUtils.getInstance(context).clearBitmap(CmsApplication.getUserInfoEntity().getAvatar());
                            onHeadCall.onHeadCall();
                        } else {
                            context.displayToast(context.getString(R.string.tip_change_avatar_fail));
                        }
                    }
                });

            }
        }).UpLoadHead(filePath);
    }

    @Override
    public void OnCancel() {

    }


    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null != data || requestCode == YCLTools.MODE_UPLOAD_IMAGE_CAMERA) {
            YCLTools.getInstance().upLoadImage(requestCode, resultCode, data);
        }
    }

    private static OnHeadCall onHeadCall;
    public static interface OnHeadCall{
        void onHeadCall();
    }

    private void startLoading() {
        loading.setVisibility(View.VISIBLE);
        loading.setProgressInfo(context.getString(R.string.txt_progress_loading));
        loading.start();
    }

    private void stopLoading() {
        if (loading != null) {
            loading.stop();
            loading.setVisibility(View.GONE);
        }
    }
}
