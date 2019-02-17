package com.baiyi.jj.app.adapter.item;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.main.news.VideoRender;
import com.baiyi.jj.app.entity.localnews.LocalNewsBean;
import com.baiyi.jj.app.entity.localnews.LocalVideoEntity;
import com.baiyi.jj.app.imgtools.GlideTool;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.video.VideoPlayView;
import com.turbo.turbo.mexico.R;

/**
 * Created by Administrator on 2017/8/8 0008.
 */
public class VideoPlayItem extends RecyclerView.ViewHolder{

    private VideoPlayView videoPlayView;
    private ImageView playBtn;
    private ImageView imgCover;
    private FrameLayout showLayout;
    private Context mContext;
    private View convertView;
    private LinearLayout fullscreenView;
    private RelativeLayout rootView;
    private LinearLayout linContentView;

    public VideoPlayItem(View itemView, Activity context) {
        super(itemView);
        this.mContext = context;
        fullscreenView = ((BaseActivity) context).win_fullscreen;
        rootView = ((BaseActivity) context).root;
        initView(itemView);
    }

    private void initView(View itemView){
        playBtn = (ImageView) itemView.findViewById(R.id.play_btn);
        showLayout = (FrameLayout) itemView.findViewById(R.id.show_layout);
        videoPlayView = (VideoPlayView) itemView.findViewById(R.id.video_play_view);
        linContentView = (LinearLayout) itemView.findViewById(R.id.lin_contentview2);
        int abimgW = Config.getInstance().getScreenWidth(mContext);
        int abimgH = (int) (abimgW * 0.55);
        linContentView.getLayoutParams().width = abimgW;
        linContentView.getLayoutParams().height = abimgH;
        convertView = itemView.findViewById(R.id.lin_contentview);
        imgCover = (ImageView) itemView.findViewById(R.id.img_cover);

        videoPlayView.setMediaPlayerListenr(new VideoPlayView.MediaPlayerImpl() {
            @Override
            public void onError() {
//                videoPlayView.stop();
                if (onPlayError!=null){
                    onPlayError.onError();
                }
            }

            @Override
            public void onExpend() {
                ((BaseActivity)mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                if (fullscreenView == null) {
                    return;
                }
                rootView.setClipToPadding(false);
                rootView.setFitsSystemWindows(false);
                ViewGroup viewGroup = (ViewGroup) convertView.getParent();
                if (viewGroup != null) {
                    viewGroup.removeAllViews();
                    fullscreenView.addView(convertView);
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
            public void onShrik() {
                ((BaseActivity)mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                if (fullscreenView == null) {
                    return;
                }
                rootView.setClipToPadding(true);
                rootView.setFitsSystemWindows(true);
                fullscreenView.setVisibility(View.GONE);
                fullscreenView.removeAllViews();
                linContentView.removeAllViews();
                ViewGroup last = (ViewGroup) convertView.getParent();//???videoitemview????????remove
                if (last != null) {
                    last.removeAllViews();
                }
                linContentView.addView(convertView);
                int mShowFlags =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                fullscreenView.setSystemUiVisibility(mShowFlags);
            }
        });
    }

    public void setData(final LocalNewsBean bean, final int position,int currentPosition){
        if (currentPosition == position) {
            videoPlayView.setVisibility(View.VISIBLE);
        } else {
            videoPlayView.setVisibility(View.GONE);
            showLayout.setVisibility(View.VISIBLE);
            videoPlayView.stop();
        }
        LocalVideoEntity videoEntity = bean.getVideoEntity();
        if (videoEntity ==null || Utils.isStringEmpty(bean.getVideoEntity().getVideoUrl())){
            return;
        }
        GlideTool.getListImage(mContext,bean.getVideoEntity().getVideoCover(),imgCover);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLayout.setVisibility(View.GONE);
                videoPlayView.setUrl(bean.getVideoEntity().getVideoUrl());
                TLog.e("url--","--"+bean.getVideoEntity().getVideoUrl());
//                videoPlayView.openVideo();
                if (playclick != null){
                    playclick.onPlayclick(position,convertView,videoPlayView);
                }
            }
        });

    }

    private onPlayClick playclick;

    public void setPlayClick(onPlayClick playclick) {
        this.playclick = playclick;
    }

    public interface onPlayClick {
        void onPlayclick(int curentposition, View conventView,VideoPlayView playView);
    }

    private onPlayError onPlayError;

    public void setOnPlayError(onPlayError playclick) {
        this.onPlayError = playclick;
    }

    public interface onPlayError {
        void onError();
    }
}
