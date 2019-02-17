package com.baiyi.jj.app.adapter.item;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.BaseAnalyticsActivity;
import com.baiyi.jj.app.activity.main.news.BaseHolder;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.entity.localnews.LocalNewsBean;
import com.baiyi.jj.app.entity.localnews.LocalVideoEntity;
import com.baiyi.jj.app.imgtools.GlideTool;
import com.baiyi.jj.app.manager.VideoPlayManager;
import com.baiyi.jj.app.theme.FontUtil;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.pierfrancescosoffritti.youtubeplayer.AbstractYouTubeListener;
import com.pierfrancescosoffritti.youtubeplayer.FullScreenManager;
import com.pierfrancescosoffritti.youtubeplayer.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.youtubeplayer.YouTubePlayerView;
import com.turbo.turbo.mexico.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/26 0026.
 */
public class VideoYoutubeItem extends RecyclerView.ViewHolder{

    private Activity context;
    private FrameLayout frameLayoutVideo;
    private YouTubePlayerView youTubePlayerView;
    private ImageView imgCover = null;
    private RelativeLayout layoutCover = null;
    private RelativeLayout layoutbg = null;



    private FullScreenManager fullScreenManager;
    private LinearLayout fullscreenView;
    private RelativeLayout rootView;

    private boolean isReady = false;
    private List<WebView> webViewList;


    public VideoYoutubeItem(View itemView, Activity context) {
        super(itemView);
        this.context = context;
        fullscreenView = ((BaseActivity) context).win_fullscreen;
        rootView = ((BaseActivity) context).root;
        initView(itemView);
    }

    private void initView(View convertView){
        imgCover = (ImageView) convertView.findViewById(R.id.img_cover);
        layoutCover = (RelativeLayout) convertView.findViewById(R.id.rela_vedio_cover);
        youTubePlayerView = (YouTubePlayerView) convertView.findViewById(R.id.youtube_player_view);
        frameLayoutVideo = (FrameLayout) convertView.findViewById(R.id.frame_video);
        layoutbg = (RelativeLayout) convertView.findViewById(R.id.rela_vedio);

//        int abimgW = Config.getInstance().getScreenWidth(context);
        int abimgW = Config.getInstance().getScreenWidth(context)- BaseActivity.getDensity_int(30);
        int abimgH = (int) (abimgW * 0.55);
        layoutbg.getLayoutParams().height = abimgH;
        youTubePlayerView.getLayoutParams().height = abimgH;
        imgCover.getLayoutParams().width = abimgW;
        imgCover.getLayoutParams().height = abimgH;

        fullScreenManager = new FullScreenManager(context);
        //https://www.youtube.com/watch?v=IHhPpTkxW9o"
        youTubePlayerView.initialize(new AbstractYouTubeListener() {

            @Override
            public void onReady() {
//                youTubePlayerView.loadVideo("IHhPpTkxW9o", 0);
                isReady = true;
            }
        }, false);
        youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
            @Override
            public void onYouTubePlayerEnterFullScreen() {
//                startAnimation(youTubePlayerView);
                context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                fullScreenManager.enterFullScreen();
                if (fullscreenView == null) {
                    return;
                }
                rootView.setClipToPadding(false);
                rootView.setFitsSystemWindows(false);
                ViewGroup viewGroup = (ViewGroup) youTubePlayerView.getParent();
                if (viewGroup != null) {
                    viewGroup.removeAllViews();
                    fullscreenView.addView(youTubePlayerView);
                    fullscreenView.setVisibility(View.VISIBLE);
                    int mHideFlags =
                            View.SYSTEM_UI_FLAG_LOW_PROFILE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                    fullscreenView.setSystemUiVisibility(mHideFlags);
                }
            }

            @Override
            public void onYouTubePlayerExitFullScreen() {
                context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                fullScreenManager.exitFullScreen();
                if (fullscreenView == null) {
                    TLog.e("null","null----------2");
                    return;
                }
                rootView.setClipToPadding(true);
                rootView.setFitsSystemWindows(true);
                fullscreenView.setVisibility(View.GONE);
                fullscreenView.removeAllViews();
                frameLayoutVideo.removeAllViews();
                ViewGroup last = (ViewGroup) youTubePlayerView.getParent();//???videoitemview????????remove
                if (last != null) {
                    last.removeAllViews();
                }
                frameLayoutVideo.addView(youTubePlayerView);
                int mShowFlags =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                fullscreenView.setSystemUiVisibility(mShowFlags);

//                youTubePlayerView.setCustomActionRight(ContextCompat.getDrawable(context, R.drawable.ic_pause_36dp), null);
            }
        });
        youTubePlayerView.HideFullScreen();

    }

    public void setData(final LocalNewsBean bean){
        if (bean != null && bean.getVideoEntity() != null){
            final LocalVideoEntity entity = bean.getVideoEntity();
            layoutCover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    ((BaseActivity)context).addEnvent(BaseAnalyticsActivity.Envent_List_VideoClick);
                    VideoPlayManager.getInstance().addPlayView(youTubePlayerView, layoutCover);
                    if (isReady){
                        if (!youTubePlayerView.getCurrentVideoID().equals(entity.getVideoId())){
                            youTubePlayerView.loadVideo(entity.getVideoId(), 0);
                        }else {
                            if (youTubePlayerView.getCurremtTime() > 1) {
                                youTubePlayerView.playVideo();
                            } else {
                                youTubePlayerView.loadVideo(entity.getVideoId(), 0);
                            }
                        }
                    }else {
                        youTubePlayerView.initialize(new AbstractYouTubeListener() {

                            @Override
                            public void onReady() {
                                isReady = true;
                                youTubePlayerView.loadVideo(entity.getVideoId(), 0);
                            }
                        }, false);
                    }
                    layoutCover.setVisibility(View.GONE);

                    if (playClick != null) {
                        playClick.playClick();
                    }

                }
            });

            if (!Utils.isStringEmpty(entity.getVideoCover())) {
                GlideTool.getVideoListImage(context, entity.getVideoCover(), imgCover);
            }
        }


    }

    private OnPlayClick playClick;

    public void setPlayClick(OnPlayClick playClick) {
        this.playClick = playClick;
    }

    public interface OnPlayClick {
        void playClick();
    }

}
