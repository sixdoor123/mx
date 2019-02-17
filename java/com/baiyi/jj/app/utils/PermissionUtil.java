package com.baiyi.jj.app.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Ȩ���빤����
 */
public class PermissionUtil {

    public static final int CAMERA = 1; //����ͷȨ��
    public static final int WRITE_EXTERNAL_STORAGE = 2; //д��sd��Ȩ��
    public static final int READ_EXTERNAL_STORAGE = 3; //��sd��Ȩ��

    public static PermissionUtil tools;
    public Activity context;

    public PermissionUtil(Activity context) {
        this.context = context;
    }

    public static PermissionUtil getInstance(Activity context) {
        if (tools == null) {
            tools = new PermissionUtil(context);
        }
        return tools;
    }

    /**
     *     ����CAMERAȨ��
     */
    public boolean permissionCamera() {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CAMERA},
                    PermissionUtil.CAMERA);
            return true;
        }
        return false;
    }

    /**
     *      ����SD��дȨ��
     */
    public boolean permissionWrite() {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PermissionUtil.CAMERA);
            return true;
        }
        return false;
    }

    /**
     *      ����SD����Ȩ��
     */
    public boolean permissionRead() {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PermissionUtil.CAMERA);
            return true;
        }
        return false;
    }

}
