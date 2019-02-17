package com.baiyi.jj.app.utils;

import android.os.Handler;
import android.view.View;

/**
 * ·ÀÖ¹Ë«»÷Ö´ÐÐÁ½´Î
 * Created by lizl on 2017/4/27.
 */

public class PreventDoubleClickUtils {

    public static void execute(final View view) {
        view.setClickable(false);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                view.setClickable(true);
            }
        }, 500);

    }
}
