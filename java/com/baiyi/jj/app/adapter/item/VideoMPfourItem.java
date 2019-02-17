package com.baiyi.jj.app.adapter.item;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.entity.localnews.LocalNewsBean;
import com.baiyi.jj.app.entity.localnews.LocalVideoEntity;
import com.baiyi.jj.app.imgtools.GlideTool;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.turbo.turbo.mexico.R;

/**
 * Created by Administrator on 2017/8/3 0003.
 */
public class VideoMPfourItem extends RecyclerView.ViewHolder{

    private VideoView videoView;
    private RelativeLayout videoLayout;
    private ImageView imgRepeat;
    private ImageView imgCover;
    private ImageView imgPlay;
    private ProgressBar progressBar;
    private Context mContext;

    public VideoMPfourItem(View itemView,Context context) {
        super(itemView);
        this.mContext = context;
        initView(itemView);
    }

    private void initView(View itemView){
        videoView = (VideoView) itemView.findViewById(R.id.video_item);
        videoLayout = (RelativeLayout)itemView.findViewById(R.id.rela_video);
        imgRepeat = (ImageView) itemView.findViewById(R.id.img_restart);
        imgCover = (ImageView) itemView.findViewById(R.id.img_cover);
        imgPlay = (ImageView) itemView.findViewById(R.id.img_start);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        int screenWith = Config.getInstance().getScreenWidth(mContext);
        videoView.getLayoutParams().height = (int) (screenWith * 0.5652f);
        videoView.getLayoutParams().width = screenWith;
        videoLayout.getLayoutParams().height = (int) (screenWith * 0.5652f);
        //设置视频控制器
        videoView.setMediaController(new MediaController(mContext));
        //播放完成回调
        videoView.setOnCompletionListener( new MyPlayerOnCompletionListener());
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                progressBar.setVisibility(View.GONE);
            }
        });
        //设置视频路径
        imgRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgRepeat.setVisibility(View.GONE);
                videoView.start();
            }
        });
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgCover.setVisibility(View.GONE);
                imgPlay.setVisibility(View.GONE);
                videoView.start();
                progressBar.setVisibility(View.VISIBLE);
            }
        });

    }

    public void setData(LocalNewsBean bean,final int position){
        TLog.e("video","----------------setdata");
        if (videoView != null && videoView.isPlaying()){
            return;
        }
        TLog.e("video","----------------setdata---2");
        LocalVideoEntity videoEntity = bean.getVideoEntity();
        if (videoEntity== null || Utils.isStringEmpty(videoEntity.getVideoUrl())){
            videoLayout.setVisibility(View.GONE);
            return;
        }
//        String videoUrl2 = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
        Uri uri = Uri.parse(videoEntity.getVideoUrl());
        videoView.setVideoURI(uri);
        GlideTool.getListImage(mContext,videoEntity.getVideoCover(),imgCover);
        //开始播放视频
//        videoView.start();

    }

    private onPlayClick playclick;

    public void setPlayClick(onPlayClick playclick) {
        this.playclick = playclick;
    }

    public interface onPlayClick {
        void onPlayclick(int position, RelativeLayout image);
    }

    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
//            Toast.makeText(mContext, "complete", Toast.LENGTH_SHORT).show();
            imgRepeat.setVisibility(View.VISIBLE);
        }
    }
}
