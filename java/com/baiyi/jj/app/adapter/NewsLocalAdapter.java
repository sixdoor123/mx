package com.baiyi.jj.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.activity.main.news.VideoRender;
import com.baiyi.jj.app.adapter.item.GIfItem;
import com.baiyi.jj.app.adapter.item.LargeImageItem;
import com.baiyi.jj.app.adapter.item.NoImageItem;
import com.baiyi.jj.app.adapter.item.SmallImageItem;
import com.baiyi.jj.app.adapter.item.VideoFBItem;
import com.baiyi.jj.app.adapter.item.VideoMPfourItem;
import com.baiyi.jj.app.adapter.item.VideoPlayItem;
import com.baiyi.jj.app.adapter.item.VideoYoutubeItem;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.entity.localnews.LocalNewsBean;
import com.baiyi.jj.app.entity.localnews.LocalVideoEntity;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.video.VideoPlayView;
import com.turbo.turbo.mexico.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
public class NewsLocalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ItemTypeNoImage = 1;
    public static final int ItemTypeSmallImage = 2;
    public static final int ItemTypeLargeImage = 3;
    public static final int ItemTypeVideofb = 6;
    public static final int ItemTypeVideoyoutu = 7;
    public static final int ItemTypeGif = 5;
    public static final int ItemTypeVideomp4 = 8;

    public static final int flagtext = 0;
    public static final int flagimg = 1;
    public static final int flagvideo = 2;
    public static final int newstype_funny = 4;

    private OnImageClickListner imageClickListner;

    private List<LocalNewsBean> datas = new ArrayList<>();
    private Activity context;
    private int contentType;

    private List<WebView> webViewList;

    private OnMp4PlayerClickLisner onMp4PlayerClickLisner = null;
    private OnPlayerClickLisner onPlayerClickLisner = null;
    int currentPlayId = -1;
    private int currentPosition = -1;

    public NewsLocalAdapter(Activity context) {
        this.context = context;
    }

    public void setDatas(List<LocalNewsBean> datalist, int contentType) {
        this.contentType = contentType;
        if (Utils.isStringEmpty(datalist)) {
            return;
        }
        datas.clear();
        datas.addAll(datalist);
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder vh = null;
        if (viewType == ItemTypeNoImage) {
            view = ContextUtil.getLayoutInflater(context).inflate(
                    R.layout.item_localdetail_noimg, parent, false);
            vh = new NoImageItem(view,context);
        } else if (viewType == ItemTypeSmallImage) {
            view = ContextUtil.getLayoutInflater(context).inflate(
                    R.layout.item_localdetail_hasimg, parent, false);
            vh = new SmallImageItem(view, context);
        } else if (viewType == ItemTypeLargeImage) {
            view = ContextUtil.getLayoutInflater(context).inflate(
                    R.layout.item_localdetail_hasimg, parent, false);
            vh = new LargeImageItem(view, context);
        } else if (viewType == ItemTypeGif) {
            View gifView = ContextUtil.getLayoutInflater(context).inflate(
                    R.layout.item_gif_image, parent, false);
            vh = new GIfItem(gifView, context);
        } else if (viewType == ItemTypeVideoyoutu) {
            view = ContextUtil.getLayoutInflater(context).inflate(
                    R.layout.item_localdetail_videoyou, parent, false);
            vh = new VideoYoutubeItem(view, context);
        } else if (viewType == ItemTypeVideofb) {
            view = ContextUtil.getLayoutInflater(context).inflate(
                    R.layout.item_localdetail_videofb, parent, false);
            vh = new VideoFBItem(view, context);
//        }else if (viewType == ItemTypeVideomp4) {
//            view = ContextUtil.getLayoutInflater(context).inflate(
//                    R.layout.item_mpfour, parent, false);
//            vh = new VideoMPfourItem(view, context);
        }else if (viewType == ItemTypeVideomp4) {
            view = ContextUtil.getLayoutInflater(context).inflate(
                    R.layout.video_play_items, parent, false);
            vh = new VideoPlayItem(view, context);
        } else {
            view = ContextUtil.getLayoutInflater(context).inflate(
                    R.layout.item_localdetail_noimg, parent, false);
            vh = new NoImageItem(view,context);
        }

        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int itemType = getItemViewType(position);
        if (itemType == ItemTypeNoImage) {
            ((NoImageItem) holder).setData(datas.get(position));
        } else if (itemType == ItemTypeSmallImage) {
            ((SmallImageItem) holder).setData(datas.get(position));
            ((SmallImageItem) holder).setOnLargeClick(new LargeImageItem.OnLargeClick() {
                @Override
                public void clickimg(int index,boolean isglide) {
                    if (imageClickListner != null) {
                        imageClickListner.clickImage(index,isglide);
                    }
                }
            });
        } else if (itemType == ItemTypeLargeImage) {
            ((LargeImageItem) holder).setData(datas.get(position));
            ((LargeImageItem) holder).setOnLargeClick(new LargeImageItem.OnLargeClick() {
                @Override
                public void clickimg(int index,boolean isglide) {
                    if (imageClickListner != null) {
                        imageClickListner.clickImage(index,isglide);
                    }
                }
            });
        } else if (itemType == ItemTypeGif) {
            ((GIfItem) holder).setData(datas.get(position));
        }else if (itemType == ItemTypeVideoyoutu) {
            ((VideoYoutubeItem) holder).setData(datas.get(position));
            ((VideoYoutubeItem) holder).setPlayClick(new VideoYoutubeItem.OnPlayClick() {
                @Override
                public void playClick() {
                    currentPlayId = position;
                    if (onPlayerClickLisner != null){
                        onPlayerClickLisner.clickId(currentPlayId);
                    }
                }
            });
        }else if (itemType == ItemTypeVideofb) {
            ((VideoFBItem) holder).setData(datas.get(position));
//        }else if (itemType == ItemTypeVideomp4) {
//            ((VideoMPfourItem) holder).setData(datas.get(position),position);
//            ((VideoMPfourItem) holder).setPlayClick(new VideoMPfourItem.onPlayClick() {
//                @Override
//                public void onPlayclick(int position, RelativeLayout image) {
//                    if (onMp4PlayerClickLisner != null){
//                        onMp4PlayerClickLisner.clickId(position,image);
//                    }
//                }
//            });
        }else if (itemType == ItemTypeVideomp4) {
            ((VideoPlayItem) holder).setData(datas.get(position),position,currentPosition);
            ((VideoPlayItem) holder).setPlayClick(new VideoPlayItem.onPlayClick() {
                @Override
                public void onPlayclick(int curentposition, View conventView, VideoPlayView playView) {
                    currentPosition = curentposition;
                    if (onMp4PlayerClickLisner!=null){
                        onMp4PlayerClickLisner.clickId(curentposition,conventView,playView);
                    }
                    notifyDataSetChanged();
                }
            });
            ((VideoPlayItem)holder).setOnPlayError(new VideoPlayItem.onPlayError() {
                @Override
                public void onError() {
                    if (onPlayError!=null){
                        onPlayError.onError();
                    }
                }
            });
        }
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    @Override
    public int getItemViewType(int position) {
        boolean isGprs = CmsApplication.isGprs;
        boolean isShowSmall = AccountManager.getInstance().getWifi_Is_Display_Img() != 2;
        LocalNewsBean bean = datas.get(position);
        if (contentType == newstype_funny) {
            return ItemTypeGif;
        } else if (bean.getParaType() == flagtext) {
            return ItemTypeNoImage;
        } else if (bean.getParaType() == flagimg) {
            if (isGprs && isShowSmall) {
                return ItemTypeSmallImage;
            } else {
                return ItemTypeLargeImage;
            }
        } else if (bean.getParaType() == flagvideo) {
            LocalVideoEntity entity = bean.getVideoEntity();
            if (entity == null || Utils.isStringEmpty(entity.getVideoType())){
                return ItemTypeNoImage;
            }
            if (entity.getVideoType().equals("youtube")){
                return ItemTypeVideoyoutu;
            }else if (entity.getVideoType().equals("facebook")){
                return ItemTypeVideofb;
            }else if (entity.getVideoType().equals("mp4")){
                return ItemTypeVideomp4;
            }else {
                return ItemTypeNoImage;
            }

        } else {
            return ItemTypeNoImage;
        }
    }


    public void refresh() {
        this.notifyDataSetChanged();
    }

    public interface OnImageClickListner {
        public void clickImage(int index,boolean isglide);
    }

    public void setImageClickListner(OnImageClickListner imageClickListner) {
        this.imageClickListner = imageClickListner;
    }

    public interface OnPlayerClickLisner{
        void clickId(int postion);
    }

    public void setOnPlayerClickLisner(OnPlayerClickLisner onPlayerClickLisner) {
        this.onPlayerClickLisner = onPlayerClickLisner;
    }


    public interface OnMp4PlayerClickLisner{
        void clickId(int curentposition, View conventView,VideoPlayView playView);
    }

    public void setOnMp4PlayerClickLisner(OnMp4PlayerClickLisner onPlayerClickLisner) {
        this.onMp4PlayerClickLisner = onPlayerClickLisner;
    }
    private onPlayError onPlayError;

    public void setOnPlayError(onPlayError playclick) {
        this.onPlayError = playclick;
    }

    public interface onPlayError {
        void onError();
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
