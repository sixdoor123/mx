package com.baiyi.jj.app.activity.main.news;

import android.accounts.Account;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.BaseAnalyticsActivity;
import com.baiyi.jj.app.activity.main.NewsRecyclerAdapter;
import com.baiyi.jj.app.activity.main.TestActivity;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.cache.Dao.EditionReadDao;
import com.baiyi.jj.app.cache.Dao.ReadDao;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.imgtools.GlideTool;
import com.baiyi.jj.app.listitem.news.ListItemBaseNews;
import com.baiyi.jj.app.manager.VideoPlayManager;
import com.baiyi.jj.app.theme.FontUtil;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.TagUtils;
import com.baiyi.jj.app.utils.TextLengthUtils;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.MixtureTextView;
import com.baiyi.jj.app.views.RemoteImageView;
import com.baiyi.jj.app.views.pop.DislikePop;
import com.baiyi.jj.app.views.pop.ScreenPop;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.pierfrancescosoffritti.youtubeplayer.AbstractYouTubeListener;
import com.pierfrancescosoffritti.youtubeplayer.FullScreenManager;
import com.pierfrancescosoffritti.youtubeplayer.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.youtubeplayer.YouTubePlayerView;
import com.turbo.turbo.mexico.R;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Administrator on 2017/5/5 0005.
 */
public class VideoRender extends BaseHolder {


    private TextView txtSource = null;
    private ImageView imgMore = null;
    private TextView txtTime = null;

    private TextView txtTitle = null;
    private TextView txtVideoDur = null;
    private ImageView imgCover = null;
    private ImageView imgEnter = null;
    private RelativeLayout layoutCover = null;
    private RelativeLayout layoutbg = null;
    private LinearLayout linAddTag = null;


    private FrameLayout frameLayoutVideo;
    private YouTubePlayerView youTubePlayerView;
    private Activity context = null;
    private NewsListEntity data = null;

    private FullScreenManager fullScreenManager;

    private boolean isJustTime = true;

    private OnPlayClick playClick;

    private LinearLayout fullscreenView;
    private RelativeLayout rootView;

    private boolean isReaded;
    private boolean isReady = false;

    /**
     * @param arg0
     */
    public VideoRender(View arg0, Activity context, boolean isJustTime) {
        super(arg0);
        this.context = context;
        this.isJustTime = isJustTime;
        setEvents(arg0);

        // ugIhH3MXzZ0  eb2YTaGUZyY   PLwy2HS8HBA
    }

    public VideoRender(View arg0, Activity context, boolean isJustTime, LinearLayout fullscreen) {
        super(arg0);
        this.context = context;
        this.isJustTime = isJustTime;
//        this.fullscreenView = fullscreen;
        fullscreenView = ((BaseActivity) context).win_fullscreen;
        rootView = ((BaseActivity) context).root;
        setEvents(arg0);

        // ugIhH3MXzZ0  eb2YTaGUZyY   PLwy2HS8HBA
    }

    public void setEvents(View convertView) {
        // TODO Auto-generated method stub

        txtSource = (TextView) convertView.findViewById(R.id.txt_source_3line);
        imgMore = (ImageView) convertView.findViewById(R.id.news_more_3line);
        txtTime = (TextView) convertView.findViewById(R.id.txt_time_3line);
        linAddTag = (LinearLayout) convertView
                .findViewById(R.id.lin_addlabel_3line);

        txtTitle = (TextView) convertView.findViewById(R.id.txt_videotitle);
        txtVideoDur = (TextView) convertView.findViewById(R.id.txt_video_dur);
        imgCover = (ImageView) convertView.findViewById(R.id.img_cover);
        imgEnter = (ImageView) convertView.findViewById(R.id.img_enter_detail);
        layoutCover = (RelativeLayout) convertView.findViewById(R.id.rela_vedio_cover);
        layoutbg = (RelativeLayout) convertView.findViewById(R.id.rela_vedio);
        youTubePlayerView = (YouTubePlayerView) convertView.findViewById(R.id.youtube_player_view);
        frameLayoutVideo = (FrameLayout) convertView.findViewById(R.id.frame_video);

        int abimgW = Config.getInstance().getScreenWidth(context);
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

//                    ScreenPop pop = new ScreenPop(context);
//                    pop.showPopView(txtSource);
//                    FullScreenDialog dialog = new FullScreenDialog(context,context);
//                    dialog.show();
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


//                Intent intent = YouTubeStandalonePlayer.createVideoIntent(context, TestActivity.apiKey, data.getReservezone4());
//                context.startActivity(intent);

//                youTubePlayerView.setCustomActionRight(ContextCompat.getDrawable(context, R.drawable.ic_pause_36dp), new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        youTubePlayerView.pauseVideo();
//                    }
//                });
            }

            @Override
            public void onYouTubePlayerExitFullScreen() {
                context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                fullScreenManager.exitFullScreen();
                if (fullscreenView == null) {
                    return;
                }
                rootView.setClipToPadding(true);
                rootView.setFitsSystemWindows(true);
                fullscreenView.setVisibility(View.GONE);
                fullscreenView.removeAllViews();
                frameLayoutVideo.removeAllViews();
                ViewGroup last = (ViewGroup) youTubePlayerView.getParent();//�ҵ�videoitemview�ĸ��࣬Ȼ��remove
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
    }


    public void setDatas(final int position, final NewsListEntity entity) {

        data = entity;
        if (data == null) {
            return;
        }

        layoutCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity)context).addEnvent(BaseAnalyticsActivity.Envent_List_VideoClick);
                VideoPlayManager.getInstance().addPlayView(youTubePlayerView, layoutCover);
                if (isReady){
                    if (!youTubePlayerView.getCurrentVideoID().equals(data.getReservezone4())){
                        youTubePlayerView.loadVideo(data.getReservezone4(), 0);
                    }else {
                        if (youTubePlayerView.getCurremtTime() > 1) {
                            youTubePlayerView.playVideo();
                        } else {
                            youTubePlayerView.loadVideo(data.getReservezone4(), 0);
                        }
                    }

                }else {
                    youTubePlayerView.initialize(new AbstractYouTubeListener() {

                        @Override
                        public void onReady() {
//                youTubePlayerView.loadVideo("IHhPpTkxW9o", 0);
                            isReady = true;
                            youTubePlayerView.loadVideo(data.getReservezone4(), 0);
                        }
                    }, false);
                }
                layoutCover.setVisibility(View.GONE);
                // qiu 017  bigbeach 08 94  batman 23 82  zongyi33 60  4caise 04 36

//                youTubePlayerView.playVideo();
                if (playClick != null) {
                    playClick.playClick();
                }
            }
        });
        imgEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEnterClickLisner != null) {
                    if (youTubePlayerView != null) {
                        onEnterClickLisner.click(position, youTubePlayerView.getCurremtTime());
                    }

                }
            }
        });

        setMorePop(position);
        setShowData(data);
    }

//    OnReleaseLisner onReleaseLisner;
//
//    public void setOnReleaseLisner(OnReleaseLisner onReleaseLisner) {
//        this.onReleaseLisner = onReleaseLisner;
//    }
//
//    public interface OnReleaseLisner{
//        void releaseid(int postion);
//    }

    private void setMorePop(final int position) {
        imgMore.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                showPop(position, imgMore);
            }
        });
    }

    private void showPop(final int position, ImageView view) {
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

        String timeN = Utils.getTimeName(context, data.getArticle_pubDate(), !isJustTime);
        txtTime.setText(timeN);
        if (Utils.isStringEmpty(data.getArticle_source())) {
            txtSource.setVisibility(View.GONE);
        } else {
            txtSource.setVisibility(View.VISIBLE);
            txtSource.setText(data.getArticle_source());
        }
        float textSize = FontUtil.getNewsTitleTextSize(context);
        isReaded = new ReadDao(context).isSupport(data.getArticle_id());
        txtTitle.setTextSize(textSize);
        txtTitle.setTypeface(CmsApplication.getListTitleFace(context));
        if (Utils.isStringEmpty(data.getArticle_title())) {
            txtTitle.setVisibility(View.GONE);
        } else {
            txtTitle.setVisibility(View.VISIBLE);
            txtTitle.setText(data.getArticle_title());
        }
        txtTitle.setSelected(isReaded);
//        txtVideoDur.setText("09:08");
        if (!Utils.isStringEmpty(data.toImgList())) {
            GlideTool.getVideoListImage(context, data.toImgList().get(0).getMedia_path(), imgCover);
        }
        setLabel(linAddTag, data);

    }

    public void setPlayClick(OnPlayClick playClick) {
        this.playClick = playClick;
    }

    public interface OnPlayClick {
        void playClick();
    }

    OnEnterClickLisner onEnterClickLisner;

    public void setOnEnterClickLisner(OnEnterClickLisner onEnterClickLisner) {
        this.onEnterClickLisner = onEnterClickLisner;
    }

    public interface OnEnterClickLisner {
        void click(int postion, int currentTime);
    }

    public void setLabel(LinearLayout linAdd, NewsListEntity newsEntity) {
        List<Integer> tags = newsEntity.toTags();
        if (Utils.isStringEmpty(tags)) {
            linAdd.setVisibility(View.GONE);
            return;
        }
//		int[] resId = TagUtils.getNewsTagByTagId(tags,context);
        String[] resStr = TagUtils.getTagStrByTagId(tags, context);
        int[] resColor = TagUtils.getTagStrColor(tags);
        linAdd.removeAllViews();
        linAdd.setVisibility(View.VISIBLE);
        int size = tags.size();
        for (int i = 0; i < resStr.length; i++) {
            if (Utils.isStringEmpty(resStr[i])) {
                size--;
                continue;
            }
            TextView textView = new TextView(context);
            textView.setText(resStr[i]);
            textView.setTextSize(11);
            textView.setTextColor(context.getResources().getColor(resColor[i]));
//			textView.setTextColor(resColor[i]);
            TextPaint tp = textView.getPaint();
            tp.setFakeBoldText(true);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -2);
            lp.rightMargin = BaseActivity.getDensity_int(5);
            linAdd.addView(textView, lp);

//			ImageView iv = new ImageView(context);
//			iv.setImageDrawable(context.getResources().getDrawable(resId[i]));
//			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -2);
//			lp.rightMargin = BaseActivity.getDensity_int(5);
//			linAdd.addView(iv, lp);
//
//			List<DynamicAttr> mDynamicAttr = new ArrayList<DynamicAttr>();
//			mDynamicAttr.add(new DynamicAttr(AttrFactory.BACKGROND_DRAWABLE,
//					resId[i]));
//			((BaseSkinFragmentActivity) context).dynamicAddView(iv,
//					mDynamicAttr);
        }
        if (size == 0) {
            linAdd.setVisibility(View.GONE);
        }

    }

}
