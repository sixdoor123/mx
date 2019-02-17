package com.baiyi.jj.app.dialog;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.utils.PermissionUtil;
import com.baiyi.jj.core.basedialog.DialogBase;
import com.turbo.turbo.mexico.R;
import com.ycl.chooseavatar.library.YCLTools;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

/**
 * 上传头像弹出框
 * 作者：lizl on 2016/11/24 11:30
 */
public class UpLoadHeadImageDialog extends DialogBase {
    TextView tv_take_picture;
    TextView tv_choose_gallery;
    TextView tv_Title;

    Activity activity;

    public UpLoadHeadImageDialog(Context context) {
        super(context, Win_Bottom);
        activity = (Activity) context;
    }

    @Override
    public void setTitleContent() {
    }

    @Override
    public void setContainer() {

        setCancelable(true);
        setCanceledOnTouchOutside(true);
        View view = ContextUtil.getLayoutInflater(getContext()).inflate(R.layout.upload_headimg, null);
        addView(view);
        tv_take_picture = (TextView) findViewById(R.id.tv_take_picture);
        tv_choose_gallery = (TextView) findViewById(R.id.tv_choose_gallery);
        tv_Title = (TextView) findViewById(R.id.textView1);
        tv_take_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpLoadHeadImageDialog.this.dismiss();
                if (PermissionUtil.getInstance(activity).permissionCamera()) {
                    return;
                }
                if (PermissionUtil.getInstance(activity).permissionWrite()) {
                    return;
                }
                if (PermissionUtil.getInstance(activity).permissionRead()) {
                    return;
                }
                YCLTools.getInstance().startChoose(activity, 0);
            }
        });
        tv_choose_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpLoadHeadImageDialog.this.dismiss();
                if (PermissionUtil.getInstance(activity).permissionWrite()) {
                    return;
                }
                if (PermissionUtil.getInstance(activity).permissionRead()) {
                    return;
                }
                YCLTools.getInstance().startChoose(activity, 1);
            }
        });
    }

    @Override
    public void OnClickListenEvent(View v) {
    }

    /**
     * 开启权限后继续进行拍照片传照片
     *
     * @param requestCode
     * @param grantResults
     */
    public void doNext(int requestCode, int[] grantResults) {
        if (requestCode == PermissionUtil.CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
        if (requestCode == PermissionUtil.READ_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
        if (requestCode == PermissionUtil.WRITE_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    ;
}
