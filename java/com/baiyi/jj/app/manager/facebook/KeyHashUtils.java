package com.baiyi.jj.app.manager.facebook;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.baiyi.jj.app.activity.BaseActivity;
import com.turbo.turbo.mexico.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2016/9/23.
 */

public class KeyHashUtils {

    /**
     * @param context
     * @param packageName app package name
     */
    public static void getKeyHash(Context context, String packageName) {
        try {
            PackageInfo info = null;
            info = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest messageDigest = null;
                messageDigest = MessageDigest.getInstance("SHA");
                messageDigest.update(signature.toByteArray());
                String hs = Base64.encodeToString(messageDigest.digest(), Base64.DEFAULT);
                ((BaseActivity)context).displayToast(hs);
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static void shareCopyLink(Context context,String str) {
        ClipboardManager copy = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        copy.setText(str);
        ((BaseActivity) context).displayToast("copy to");
    }
}
