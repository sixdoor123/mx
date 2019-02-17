package com.baiyi.jj.app.activity.main.news;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.imgtools.GlideTool;
import com.baiyi.jj.app.listitem.news.ListItemBaseNews;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.pop.DislikePop;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.turbo.turbo.mexico.R;

/**
 * Created by Administrator on 2017/5/8 0008.
 */
public class VideoListRender extends BaseHolder{

    private TextView txtSource = null;
    private ImageView imgMore = null;
    private TextView txtTime = null;

    private TextView txtTitle = null;
    private TextView txtVideoDur = null;
    private ImageView imgCover = null;
    private RelativeLayout layoutCover = null;
    private RelativeLayout layoutbg = null;

    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer tubePlayer;
    private Activity context = null;
    private NewsListEntity data = null;

    private boolean isJustTime = true;

    private OnPlayClick playClick;

    boolean isInitSuc = false;


    /**
     * @param arg0
     */
    public VideoListRender(View arg0, Activity context, boolean isJustTime)
    {
        super(arg0);
        this.context = context;
        this.isJustTime = isJustTime;
        setEvents(arg0);

        // ugIhH3MXzZ0  eb2YTaGUZyY   PLwy2HS8HBA
    }

    public void setEvents(View convertView) {
        // TODO Auto-generated method stub

        txtSource = (TextView) convertView.findViewById(R.id.txt_source_3line);
        imgMore = (ImageView) convertView.findViewById(R.id.news_more_3line);
        txtTime = (TextView) convertView.findViewById(R.id.txt_time_3line);

        txtTitle = (TextView) convertView.findViewById(R.id.txt_videotitle);
        txtVideoDur = (TextView) convertView.findViewById(R.id.txt_video_dur);
        imgCover = (ImageView) convertView.findViewById(R.id.img_cover);
        layoutCover = (RelativeLayout) convertView.findViewById(R.id.rela_vedio_cover);
        layoutbg = (RelativeLayout) convertView.findViewById(R.id.rela_vedio);

        int abimgW = Config.getInstance().getScreenWidth(context);
        int abimgH = (int) (abimgW * ListItemBaseNews.Abstract_Img_Percent);
        layoutbg.getLayoutParams().height = abimgH;

        //https://www.youtube.com/watch?v=IHhPpTkxW9o"
        youTubePlayerView = (YouTubePlayerView) convertView.findViewById(R.id.youtube_view);
    }

    private void initialize(final String videoId){
        youTubePlayerView.initialize(Constant.GoogleApiKey, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                isInitSuc = true;
                tubePlayer = youTubePlayer;
//                tubePlayer.cueVideo(videoId);
                tubePlayer.loadVideo(videoId);
                youTubePlayer.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {

                    }

                    @Override
                    public void onLoaded(String s) {

                    }

                    @Override
                    public void onAdStarted() {

                    }

                    @Override
                    public void onVideoStarted() {

                    }

                    @Override
                    public void onVideoEnded() {

                    }

                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {

                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                isInitSuc = false;
            }
        });
    }

    public void releaseVideo(){
        layoutCover.setVisibility(View.VISIBLE);
        if (tubePlayer!= null){
            tubePlayer.release();
            tubePlayer = null;
        }
    }

    public void setDatas(int position, NewsListEntity entity) {

        data = entity;
        if (data == null) {
            return;
        }

        layoutCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = YouTubeStandalonePlayer.createVideoIntent(context, apiKey, data.getReservezone4());
//                context.startActivity(intent);

                layoutCover.setVisibility(View.GONE);
                if (tubePlayer == null){
                    String videoId = data.getReservezone4();
                    initialize(videoId);
                }
                if (playClick!= null){
                    playClick.playClick();
                }
            }
        });

        setMorePop(position);
        setShowData(data);
    }
    private void setMorePop(final int position) {
        imgMore.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                showPop(position,imgMore);
            }
        });
    }

    private void showPop(final int position, ImageView view){
        DislikePop pop = new DislikePop(context, data.getArticle_id());
        pop.showPopView(view, 0);
        pop.setDisLike(new DislikePop.PopDisLike() {
            @Override
            public void Callback(int isSuccess) {
                if (popDislikeOnclick != null) {
                    popDislikeOnclick.setPopClick(position);
                }
            }
        });
    }


    public void setShowData(NewsListEntity data) {

        String timeN = Utils.getTimeName(context, data.getArticle_pubDate(),!isJustTime);
        txtTime.setText(timeN);
        if (Utils.isStringEmpty(data.getArticle_source())) {
            txtSource.setVisibility(View.GONE);
        } else {
            txtSource.setVisibility(View.VISIBLE);
            txtSource.setText(data.getArticle_source());
        }

        txtTitle.setText(data.getArticle_title());
//        txtVideoDur.setText("09:08");
        if (!Utils.isStringEmpty(data.toImgList())){
            GlideTool.getListImage(context, data.toImgList().get(0).getMedia_path(), imgCover);
        }


    }

    public void setPlayClick(OnPlayClick playClick) {
        this.playClick = playClick;
    }

    public interface OnPlayClick{
        void playClick();
    }

}
