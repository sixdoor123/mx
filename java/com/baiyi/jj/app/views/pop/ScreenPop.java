package com.baiyi.jj.app.views.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.cache.Dao.NewsListDao;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.turbo.turbo.mexico.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/2 0002.
 */
public class ScreenPop {

    private Context context = null;
    private PopupWindow popupWindow = null;


    public ScreenPop(Context context) {
        this.context = context;
        initPopView();

    }

    private void initPopView() {
        View view = LayoutInflater.from(context).inflate(
                R.layout.pop_fullscreen, null);
        popupWindow = new PopupWindow(view);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0000000000);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.update();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                backGroundAlpha(1f);
            }
        });
    }

    /**
     * ���ñ���Ϊ��͸��
     *
     * @param alp
     */
    private void backGroundAlpha(float alp) {
        WindowManager.LayoutParams lParams = ((BaseActivity) context)
                .getWindow().getAttributes();
        lParams.alpha = alp;
        ((BaseActivity) context).getWindow().setAttributes(lParams);

    }


    public void showPopView(View parent) {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            popupWindow.showAsDropDown(parent,0,-100);
        }
    }

}
