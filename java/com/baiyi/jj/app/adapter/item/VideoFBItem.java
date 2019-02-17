package com.baiyi.jj.app.adapter.item;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.entity.localnews.LocalNewsBean;
import com.baiyi.jj.app.entity.localnews.LocalVideoEntity;
import com.baiyi.jj.app.manager.VideoPlayManager;
import com.baiyi.jj.app.utils.Utils;
import com.example.facebookvideo.FBVideoViewControl;
import com.turbo.turbo.mexico.R;

/**
 * Created by Administrator on 2017/5/26 0026.
 */
public class VideoFBItem extends RecyclerView.ViewHolder {

    private FBVideoViewControl webFBVideo;
    private Context context;

    public VideoFBItem(View itemView, Context context) {
        super(itemView);
        this.context = context;
        initView(itemView);
    }

    private void initView(View convertView) {
        webFBVideo = (FBVideoViewControl) convertView.findViewById(R.id.fb_web_view);
        int abimgW = Config.getInstance().getScreenWidth(context)- BaseActivity.getDensity_int(30);
        int abimgH = (int) (abimgW * 0.56);
        webFBVideo.setFbViewH(abimgH);
    }

    public void setData(LocalNewsBean bean) {

        if (bean != null && bean.getVideoEntity() != null) {
            LocalVideoEntity entity = bean.getVideoEntity();
            if (!Utils.isStringEmpty(entity.getVideoUrl())) ;

//            webFBVideo.initialize("https://www.facebook.com/nelos26/videos/10213187497666076/");
            webFBVideo.initialize(entity.getVideoUrl());
            webFBVideo.setPlayListener(new FBVideoViewControl.OnClickPlayListener() {
                @Override
                public void playListener() {
                    //// TODO
                    VideoPlayManager.getInstance().addPlayViewFB(webFBVideo);
                }
            });
        }
    }
}
