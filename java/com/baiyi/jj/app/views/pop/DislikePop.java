package com.baiyi.jj.app.views.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.user.net.AppUtils;
import com.baiyi.jj.app.activity.user.net.NetUrl;
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
public class DislikePop{

    private Context context = null;
    private PopupWindow popupWindow = null;

    /**
     *
     */
    private LinearLayout linDislike = null;

    private ImageView imgClose = null;

    private PopDisLike disLikeCallBack = null;

    public static final int Dis_Success = 1;
    public static final int Dis_Fail = 2;

    private String ArticleId;

    public DislikePop(Context context, String articleId) {
        this.context = context;
        this.ArticleId = articleId;
        initPopView();

    }

    private void initPopView() {
        View view = LayoutInflater.from(context).inflate(
                R.layout.pop_dislike, null);
        popupWindow = new PopupWindow(view);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
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

        linDislike = (LinearLayout) view.findViewById(R.id.lin_dislike);
        linDislike.setOnClickListener(new OnPopClick());
        imgClose = (ImageView) view.findViewById(R.id.img_close);
        imgClose.setOnClickListener(new OnPopClick());
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


    public void showPopView(View parent,int xoff) {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            int[] position = new int[2];
            parent.getLocationOnScreen(position);
//            TLog.e("scren",((BaseActivity)context).getScreenWidth()+"=========="+((BaseActivity)context).getScreenHeight());
//            TLog.e("pos",position[0]+"-------------"+position[1]);
            int leftoff = ((BaseActivity)context).getScreenWidth() - position[0] -BaseActivity.getDensity_int(30); //135  20*3=60
            int bottomoff = position[1]+BaseActivity.getDensity_int(15);
            float densty = BaseActivity.density;
            popupWindow.showAtLocation(parent,Gravity.RIGHT|Gravity.TOP,leftoff,bottomoff);
//            popupWindow.showAsDropDown(parent,BaseActivity.getDensity_int(-20),BaseActivity.getDensity_int(-30));
//            if (position[1] > centy) {
//                popupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, position[0]+xoff,
//                        position[1]-BaseActivity.getDensity_int(182));
//
//                return;
//            }
        }
    }


    class OnPopClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.lin_dislike) {

                if (disLikeCallBack != null) {
                    disLikeCallBack.Callback(Dis_Success);
                }
                ((BaseActivity) context).displayToast(context.getResources().getString(R.string.txt_dislike_reduce));

                ((BaseActivity) context).loadAnonymity(new BaseActivity.AnonymityLister() {
                    @Override
                    public void setAnonymityLister(UserInfoEntity userinfo) {
                        if(userinfo == null)
                        {
                            return;
                        }
                        sendNotinterest(ArticleId);
                    }
                });
            }else if (v.getId() == R.id.img_close){
                popupWindow.dismiss();
            }

        }

    }

    private void sendNotinterest(final String articleId) {

        popupWindow.dismiss();
        if (Utils.isStringEmpty(CmsApplication.getUserToken())) {
            return;
        }

        OkHttpClient client = new OkHttpClient.Builder().build();
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                getAddPostBody(articleId));
        Request request = new Request.Builder()
                .post(requestBody)
                .url(NetUtils.getNotinterest())
                .header(Constant.ContentType, Constant.ContentType_Json)
                .header(Constant.HEAD_NAME, CmsApplication.getUserToken())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                if (disLikeCallBack != null) {
//                    disLikeCallBack.Callback(Dis_Fail);
//                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String StrJson = response.body().string();
                TLog.e("AddDislike", StrJson);
                if (JsonParse.getState200(StrJson)) {
                    new NewsListDao(context).deleteOneNews(articleId);
                }
//                if (disLikeCallBack != null) {
//                    if (JsonParse.getState200(StrJson)) {
//                        disLikeCallBack.Callback(Dis_Success);
//                    } else {
//                        disLikeCallBack.Callback(Dis_Fail);
//                    }
//                }
            }
        });

    }
    private String getAddPostBody(String articleId) {
        JSONObject object = new JSONObject();
        try {
            object.put("article_id", articleId);
        } catch (JSONException e) {
            e.toString();
        }
        return object.toString();
    }


    public void setDisLike(PopDisLike disLike) {
        this.disLikeCallBack = disLike;
    }

    public interface PopDisLike{
        public void Callback(int isSuccess);
    }
}
