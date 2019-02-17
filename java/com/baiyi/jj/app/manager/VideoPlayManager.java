package com.baiyi.jj.app.manager;

import android.view.View;
import android.widget.RelativeLayout;

import com.example.facebookvideo.FBVideoViewControl;
import com.pierfrancescosoffritti.youtubeplayer.YouTubePlayerView;

/**
 * Created by Administrator on 2017/5/12 0012.
 */
public class VideoPlayManager {

    private YouTubePlayerView playerView;
    private RelativeLayout layoutCover;
    private FBVideoViewControl webFBVideo;

    public static VideoPlayManager instance;

    public static VideoPlayManager getInstance(){
        if(instance == null)
        {
            instance = new VideoPlayManager();
        }
        return instance;
    }

    public VideoPlayManager() {

    }

    public boolean isPlaying(){
        if (playerView == null){
            return false;
        }else {
            return playerView.IsPlaying();
        }
    }

    public void pausePlayVideo(){
        if (playerView != null && layoutCover != null){
            if (layoutCover.getVisibility() == View.GONE){
                playerView.pauseVideo();
                layoutCover.setVisibility(View.VISIBLE);
                this.playerView = null;
                this.layoutCover = null;
            }
        }
        if (webFBVideo != null){
            webFBVideo.pauseVideo();
        }
    }

    public boolean isFullScreen(){
        if (playerView != null){
            return playerView.isFullScreen();
        }else {
            return false;
        }
    }
    public void exitFullScreen(){
        if (playerView != null){
            playerView.toggleFullScreen();
        }
    }

    public void addPlayView(YouTubePlayerView youTubePlayerView,RelativeLayout layoutCover){
        if (playerView == null){
            this.playerView = youTubePlayerView;
            this.layoutCover = layoutCover;
        }else {
            this.layoutCover.setVisibility(View.VISIBLE);
            this.playerView.pauseVideo();
            this.playerView = youTubePlayerView;
            this.layoutCover = layoutCover;

        }
        if (webFBVideo != null){
            webFBVideo.pauseVideo();
            this.webFBVideo = null;
        }
    }

    public void addPlayViewFB(FBVideoViewControl webFBVideo2){
        if (webFBVideo == null){
            this.webFBVideo = webFBVideo2;
        }else {
            this.webFBVideo.pauseVideo();
            this.webFBVideo = webFBVideo2;
        }
        if (playerView != null){
            this.layoutCover.setVisibility(View.VISIBLE);
            this.playerView.pauseVideo();
            this.playerView = null;
            this.layoutCover = null;
        }
    }

    public void releaseVideo(){
        this.playerView = null;
    }
}
