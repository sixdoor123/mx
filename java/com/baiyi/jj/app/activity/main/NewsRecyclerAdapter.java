/**
 *
 */
package com.baiyi.jj.app.activity.main;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.main.news.AdRender;
import com.baiyi.jj.app.activity.main.news.BaseHolder;
import com.baiyi.jj.app.activity.main.news.BaseHolder.RecyclerViewItemClick;
import com.baiyi.jj.app.activity.main.news.CenterGifRender;
import com.baiyi.jj.app.activity.main.news.CenterImgRender;
import com.baiyi.jj.app.activity.main.news.H3ImgRender;
import com.baiyi.jj.app.activity.main.news.NewsRefreshRender;
import com.baiyi.jj.app.activity.main.news.NewsRefreshRender.RefreshListOnclick;
import com.baiyi.jj.app.activity.main.news.NoImgRender;
import com.baiyi.jj.app.activity.main.news.RightImgRender;
import com.baiyi.jj.app.activity.main.news.VideoFBRender;
import com.baiyi.jj.app.activity.main.news.VideoRender;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.cache.Dao.NewsListDao;
import com.baiyi.jj.app.entity.NewsImgEntity;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.listitem.news.ListItemBaseNews;
import com.baiyi.jj.app.manager.CountryMannager;
import com.baiyi.jj.app.manager.VideoPlayManager;
import com.baiyi.jj.app.manager.WebViewManager;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.TagUtils;
import com.baiyi.jj.app.utils.Utils;
import com.google.android.gms.ads.NativeExpressAdView;
import com.turbo.turbo.mexico.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangkun
 */
public class NewsRecyclerAdapter extends RecyclerView.Adapter<BaseHolder> {
    private Activity context = null;
    private List<NewsListEntity> data = new ArrayList<NewsListEntity>();
    private boolean isVisibleAbstract;
    private boolean isGprs = false;
    private XRecyclerView mListView = null;
    private RecyclerViewItemClick recyclerViewItemClick = null;
    private boolean isJustTime = true;

    private OnPlayerClickLisner onPlayerClickLisner = null;

    private LinearLayout fullScreen;
//    private NativeExpressAdView expressAdView;

    int currentPlayId = -1;

//    public NewsRecyclerAdapter(Activity context, XRecyclerView mListView,
//                               boolean isVisibleAbstract, boolean isGprs, boolean isJustTime) {
//        this.context = context;
//        this.mListView = mListView;
//        this.isVisibleAbstract = isVisibleAbstract;
//        this.isJustTime = isJustTime;
//        this.isGprs = isGprs;
//    }
    public NewsRecyclerAdapter(Activity context, XRecyclerView mListView,
                               boolean isVisibleAbstract, boolean isGprs, boolean isJustTime, LinearLayout fullScreenView) {
        this.context = context;
        this.mListView = mListView;
        this.isVisibleAbstract = isVisibleAbstract;
        this.isJustTime = isJustTime;
        this.isGprs = isGprs;
        this.fullScreen = fullScreenView;
    }
//    public NewsRecyclerAdapter(Activity context, XRecyclerView mListView,
//                               boolean isVisibleAbstract, boolean isGprs, boolean isJustTime, LinearLayout fullScreenView, NativeExpressAdView adView) {
//        this.context = context;
//        this.mListView = mListView;
//        this.isVisibleAbstract = isVisibleAbstract;
//        this.isJustTime = isJustTime;
//        this.isGprs = isGprs;
//        this.fullScreen = fullScreenView;
//        expressAdView = adView;
//    }

    public boolean isGprs() {
        return isGprs;
    }

    public List<NewsListEntity> getData() {
        return data;
    }

    public NewsListEntity getNewsListEntity(int position) {
        if (Utils.isStringEmpty(data)) {
            return null;
        }
        if (position>=data.size()){
            return null;
        }
        return data.get(position);
    }

    public void addData(List<NewsListEntity> data) {
        int positionStart = getData().size() - 1;
        int itemSize = data.size();
        this.data.addAll(data);
        this.notifyItemRangeChanged(getData().size(), itemSize);
        // this.notifyItemRangeChanged(positionStart, itemSize);
        // this.notifyDataSetChanged();
    }

    public void addDataDz(List<NewsListEntity> data) {
        if (Utils.isStringEmpty(data)) {
            return;
        }
        this.data.addAll(data);
        this.notifyDataSetChanged();
    }

    public void clear() {
        this.data.clear();
        this.notifyDataSetChanged();
    }

    public void remove(int postion) {
//        TLog.e("adapter","hh---"+this.data.get(0).getArticle_title());
        this.data.remove(postion);
        this.notifyDataSetChanged();
    }

    private RefreshListOnclick refreshListOnclick = null;

    public void setRefreshListOnclick(RefreshListOnclick refreshListOnclick) {
        this.refreshListOnclick = refreshListOnclick;
    }

    public void setRecyclerViewItemClick(
            RecyclerViewItemClick recyclerViewItemClick) {
        this.recyclerViewItemClick = recyclerViewItemClick;
    }


    @Override
    public BaseHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = null;
        BaseHolder vh = null;
        if (viewType == ListItemBaseNews.Type_NoImage) {
            view = ContextUtil.getLayoutInflater(context).inflate(
                    R.layout.layout_newslist_noimg, viewGroup, false);
            vh = new NoImgRender(view, context, isVisibleAbstract, isJustTime);
        } else if (viewType == ListItemBaseNews.Type_ImgLeft) {
            view = ContextUtil.getLayoutInflater(context).inflate(
                    R.layout.layout_newslist_oneleft, viewGroup, false);
            vh = new RightImgRender(view, context, isVisibleAbstract, isGprs, isJustTime);
        } else if (viewType == ListItemBaseNews.Type_ImgCenter) {
            view = ContextUtil.getLayoutInflater(context).inflate(
                    R.layout.layout_newslist_onecenter, viewGroup, false);
            vh = new CenterImgRender(view, context, isVisibleAbstract, isJustTime);
        } else if (viewType == ListItemBaseNews.Type_GifCenter) {
            view = ContextUtil.getLayoutInflater(context).inflate(
                    R.layout.layout_newslist_gif, viewGroup, false);
            vh = new CenterGifRender(view, context, isVisibleAbstract, isJustTime);
        } else if (viewType == ListItemBaseNews.Type_ImgThree) {
            view = ContextUtil.getLayoutInflater(context).inflate(
                    R.layout.layout_newslist_three, viewGroup, false);
            vh = new H3ImgRender(view, context, isVisibleAbstract, isJustTime);
        } else if (viewType == ListItemBaseNews.Type_Refresh) {
            view = ContextUtil.getLayoutInflater(context).inflate(
                    R.layout.layout_listitem_refresh, viewGroup, false);
            vh = new NewsRefreshRender(view, context);
            ((NewsRefreshRender) vh)
                    .setRefreshListOnclick(new RefreshListOnclick() {

                        @Override
                        public void setOnRefreshListLister() {
                            if (refreshListOnclick != null) {
                                refreshListOnclick.setOnRefreshListLister();
                            }
                        }
                    });
        } else if (viewType == ListItemBaseNews.Type_video) {
            view = ContextUtil.getLayoutInflater(context).inflate(
                    R.layout.item_news_video, viewGroup, false);
            vh = new VideoRender(view, context, isJustTime,fullScreen);
            ((VideoRender)vh).setOnEnterClickLisner(new VideoRender.OnEnterClickLisner() {
                @Override
                public void click(int postion,int currenttime) {
                    if (onEnterClickLisner != null){
                        onEnterClickLisner.click(postion,currenttime);
                    }
                }
            });
        }else if (viewType == ListItemBaseNews.Type_videoFacebook || viewType == ListItemBaseNews.Type_videoyoutubo) {
            view = ContextUtil.getLayoutInflater(context).inflate(
                    R.layout.layout_newslist_fbvideo, viewGroup, false);
            vh = new VideoFBRender(view, context, isJustTime);
        }else if (viewType == ListItemBaseNews.Type_ads){
            view = ContextUtil.getLayoutInflater(context).inflate(R.layout.layout_newslist_ad,viewGroup,false);
            vh = new AdRender(view,context);
        }else {
            view = ContextUtil.getLayoutInflater(context).inflate(
                    R.layout.layout_newslist_noimg, viewGroup, false);
            vh = new NoImgRender(view, context, isVisibleAbstract, isJustTime);
        }
        if (viewType != ListItemBaseNews.Type_Refresh && viewType != ListItemBaseNews.Type_ads) {
            vh.setRecyclerViewItemClick(new RecyclerViewItemClick() {

                @Override
                public void setOnItemClickLister(int position) {
                    if (recyclerViewItemClick != null) {
                        recyclerViewItemClick.setOnItemClickLister(position);
                    }
                }
            });
        }

        if (viewType != ListItemBaseNews.Type_Refresh) {
            vh.setPopDislikeOnclick(new BaseHolder.PopDislikeOnclick() {
                @Override
                public void setPopClick(final int position) {
                    ((BaseActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            TLog.e("abc","9999999999999");
                            VideoPlayManager.getInstance().pausePlayVideo();
                            remove(position);
                        }
                    });
                }
            });
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(final BaseHolder arg0, final int arg1) {
        // TODO Auto-generated method stub
        int itemType = getItemViewType(arg1);
        if (itemType == ListItemBaseNews.Type_NoImage) {
            ((NoImgRender) arg0).setDatas(arg1, data.get(arg1));
        } else if (itemType == ListItemBaseNews.Type_ImgLeft) {
            ((RightImgRender) arg0).setDatas(arg1, data.get(arg1));
        } else if (itemType == ListItemBaseNews.Type_ImgCenter) {
            ((CenterImgRender) arg0).setDatas(arg1, data.get(arg1));
        } else if (itemType == ListItemBaseNews.Type_ImgThree) {
            ((H3ImgRender) arg0).setDatas(arg1, data.get(arg1));
        } else if (itemType == ListItemBaseNews.Type_GifCenter) {
            ((CenterGifRender) arg0).setDatas(arg1, data.get(arg1));
        }else if (itemType == ListItemBaseNews.Type_video) {
            ((VideoRender) arg0).setDatas(arg1, data.get(arg1));
            ((VideoRender)arg0).setPlayClick(new VideoRender.OnPlayClick() {
                @Override
                public void playClick() {
                    currentPlayId = arg1;
                    if (onPlayerClickLisner != null){
                        onPlayerClickLisner.clickId(currentPlayId);
                    }
                }
            });
        }else if (itemType == ListItemBaseNews.Type_videoFacebook || itemType == ListItemBaseNews.Type_videoyoutubo) {
            ((VideoFBRender) arg0).setDatas(arg1, data.get(arg1));
        }else if ((itemType == ListItemBaseNews.Type_ads)){
            ((AdRender) arg0).setDatas(arg1, data.get(arg1));
        }

    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        NewsListEntity entity = getData().get(position);

        if (!Utils.isStringEmpty(entity.getType()) && entity.getType().equals(String.valueOf(ListItemBaseNews.Type_ads))){
            return ListItemBaseNews.Type_ads;
        }

        if (!Utils.isStringEmpty(entity.toImgList())) {
            if (entity.toImgList().get(0).getPicture_type()
                    .equals(ListItemBaseNews.Item_Type_Gif)) {
                return ListItemBaseNews.Type_GifCenter;
            }
        }
        int wifitype = AccountManager.getInstance().getWifi_Is_Display_Img();
        isGprs = CmsApplication.isGprs;
        if (isGprs) {
            if (wifitype == 0) {
                return getItemHideImage(entity);
            } else if (wifitype == 1) {
                return getItemTypeToGprs(entity);
            } else {
                return getItemType(entity);
            }
        } else {
            return getItemType(entity);
        }
    }

    public int getItemHideImage(NewsListEntity entity) {
        if (Utils.isStringEmpty(entity.toImgList())) {
            return ListItemBaseNews.Type_NoImage;
        } else {
            if (entity.toImgList().get(0).getPicture_type()
                    .equals(ListItemBaseNews.Item_Type_Refresh)) {
                return ListItemBaseNews.Type_Refresh;
            }
            return ListItemBaseNews.Type_NoImage;
        }
    }

    public int getItemTypeToGprs(NewsListEntity entity) {

//        if (entity.getTemplate().equals("11")){
//            return ListItemBaseNews.Type_videoFacebook;
//        }

        if (Utils.isStringEmpty(entity.toImgList())) {
            return ListItemBaseNews.Type_NoImage;
        } else {

            if (entity.toImgList().get(0).getPicture_type()
                    .equals(ListItemBaseNews.Item_Type_Refresh)) {
                return ListItemBaseNews.Type_Refresh;
            }
            return ListItemBaseNews.Type_ImgLeft;
        }
    }

    public int getItemType(NewsListEntity entity) {

        if (Utils.isStringEmpty(entity.toImgList())) {
            return ListItemBaseNews.Type_NoImage;
        }
        if (entity.toImgList().get(0).getPicture_type()
                .equals(ListItemBaseNews.Item_Type_Refresh)) {
            return ListItemBaseNews.Type_Refresh;
        }
        if (isVisibleAbstract
                && AccountManager.getInstance().getSummary_Is_Display() != 0) {
            if (BaseHolder.isAbstractType(entity) == 0) {
                return ListItemBaseNews.Type_NoImage;
            }
        }
        if (entity.toImgList().get(0).getPicture_type()
                .equals(ListItemBaseNews.Item_Type_One_Small)) {
            return ListItemBaseNews.Type_ImgLeft;
        } else if (entity.toImgList().get(0).getPicture_type()
                .equals(ListItemBaseNews.Item_Type_One_big)) {
            return ListItemBaseNews.Type_ImgCenter;
        } else if (entity.toImgList().get(0).getPicture_type()
                .equals(ListItemBaseNews.Item_Type_One_three)) {
            return ListItemBaseNews.Type_ImgThree;
        } else if (entity.toImgList().get(0).getPicture_type()
                .equals(ListItemBaseNews.Item_Type_One_three_spacial)) {
            return ListItemBaseNews.Type_ImgThree_sheying;
        } else if (entity.toImgList().get(0).getPicture_type()
                .equals(ListItemBaseNews.Item_Type_One_Two)) {
            return ListItemBaseNews.Type_ImgTwo;
        } else if (entity.toImgList().get(0).getPicture_type()
                .equals(ListItemBaseNews.Item_Type_video)) {
            return ListItemBaseNews.Type_video;
        } else if (entity.toImgList().get(0).getPicture_type()
                .equals(ListItemBaseNews.Item_Type_video_youtube)) {
            return ListItemBaseNews.Type_videoyoutubo;
        }else if (entity.toImgList().get(0).getPicture_type()
                .equals(ListItemBaseNews.Item_Type_video_fb)) {
            return ListItemBaseNews.Type_videoFacebook;
        }

        return ListItemBaseNews.Type_NoImage;
    }

    public void refresh() {
        this.notifyDataSetChanged();
    }

    public void refreshItemPlayNum(int position, int playnum) {
        getData().get(position).setPlay_num(playnum);
        this.notifyItemChanged(position + 1);
    }

    public void refreshItem2Com(int position, int comNum) {
        if (Utils.isStringEmpty(getData())) {
            return;
        }
//        NewsListEntity data = getNewsListEntity(position);
//        getData().get(position).setArticle_comment_num(comNum);
        this.notifyItemChanged(position + 1);
//        insertItemCom(data);
    }

    private void insertItemCom(NewsListEntity data) {
        new NewsListDao(context).updateNewsComment(data.getArticle_comment_num(), data.getArticle_id());
    }

    public void refreshItemOppoNum(int position, int oppoNum, int suppNum) {

    }

    public void changeItem(boolean isGprs) {
        this.isGprs = isGprs;
        this.notifyDataSetChanged();
    }

    public void setSelectItem(int position) {
        this.notifyItemChanged(position);
    }

    /**
     * ȥ��
     *
     * @param list
     * @return
     */
    public List<NewsListEntity> getNewList(List<NewsListEntity> list) {
        List<NewsListEntity> newList = list;
        List<NewsListEntity> oldList = getData();
        for (int i = newList.size() - 1; i >= 0; i--) {
            NewsListEntity newData = newList.get(i);
            for (NewsListEntity oldData : oldList) {
                if (Utils.isStringEmpty(newData.getArticle_id()))
                    continue;
                if (newData.getArticle_id().equals(oldData.getArticle_id())) {
                    if (!newData.getType().equals(TagUtils.StorType_eidtion_top)) {
                        newList.remove(i);
                    }
                    continue;
                }
            }
        }
        return newList;

    }

    public void addPullData(List<NewsListEntity> dataList1) {
        if (Utils.isStringEmpty(dataList1)) {
            return;
        }
//        List<NewsListEntity> newDataList = getNewList(dataList1);
        List<NewsListEntity> newDataList = new ArrayList<>();
        newDataList.clear();
        newDataList.addAll(dataList1);

        if (Utils.isStringEmpty(newDataList)) // �ж�ȥ�غ�������Ƿ�Ϊ��
        {
            return;
        }
        if (!Utils.isStringEmpty(getData())) {
            for (int i = getData().size() - 1; i >= 0; i--) {
                List<NewsImgEntity> imgList = getData().get(i).toImgList();

                if (Utils.isStringEmpty(imgList)) {
                    continue;
                }
                if (imgList.get(0).getPicture_type()
                        .equals(ListItemBaseNews.Item_Type_Refresh)) {
                    getData().remove(i);
                    break;
                }
            }

            List<NewsListEntity> data = new ArrayList<NewsListEntity>();
            data.addAll(newDataList);

            NewsListEntity refreshEntity = new NewsListEntity();
            List<NewsImgEntity> artcle_list = new ArrayList<NewsImgEntity>();
            NewsImgEntity imgEntity = new NewsImgEntity();
            imgEntity.setPicture_type(ListItemBaseNews.Item_Type_Refresh);
            artcle_list.add(imgEntity);
            refreshEntity.updateImageList(artcle_list);
            data.add(refreshEntity);

            data.addAll(getData());
            getData().clear();

            this.getData().addAll(data);
        } else {
            this.getData().addAll(newDataList);
        }
        this.notifyDataSetChanged();
    }

    public void setData(List<NewsListEntity> list) {
        if (Utils.isStringEmpty(list)) {
            List<NewsListEntity> noList = new ArrayList<NewsListEntity>();
            this.getData().clear();
            this.getData().addAll(noList);
            notifyDataSetChanged();
            return;
        }
        this.getData().clear();
        this.getData().addAll(list);
        this.notifyDataSetChanged();
    }

    public void setData(List<NewsListEntity> list,
                        List<NewsListEntity> newList, int pageNum) {
        if (Utils.isStringEmpty(list)) {
            List<NewsListEntity> noList = new ArrayList<NewsListEntity>();
            this.getData().clear();
            this.getData().addAll(noList);
            notifyDataSetChanged();
            return;
        }
        this.getData().clear();
        if (!Utils.isStringEmpty(newList)) {
            this.getData().addAll(newList);
        }
        this.getData().addAll(list);
        notifyDataSetChanged();
    }

    public List<NewsListEntity> getNewListAndRefreshItem(
            List<NewsListEntity> list, boolean isFirstRefresh) {
        if (!isFirstRefresh) {
            return null;
        }
        List<NewsListEntity> addList = new ArrayList<NewsListEntity>();
        if (Utils.isStringEmpty(getData())) {
            return addList;
        }
        if (Utils.isStringEmpty(list)) {
            return addList;
        }
        addList.addAll(list);
        for (int i = list.size() - 1; i >= 0; i--) {
            for (NewsListEntity oldEntity : getData()) {
                if (list.get(i).getArticle_id()
                        .equals(oldEntity.getArticle_id())) {
                    addList.remove(i);
                    break;
                }
            }
        }
        if (Utils.isStringEmpty(addList)) {
            return null;
        }
        NewsListEntity refreshEntity = new NewsListEntity();
        List<NewsImgEntity> artcle_list = new ArrayList<NewsImgEntity>();
        NewsImgEntity imgEntity = new NewsImgEntity();
        imgEntity.setPicture_type(ListItemBaseNews.Item_Type_Refresh);
        artcle_list.add(imgEntity);
        refreshEntity.updateImageList(artcle_list);
        addList.add(refreshEntity);
        return addList;
    }

    public interface OnPlayerClickLisner{
        void clickId(int postion);
    }

    public void setOnPlayerClickLisner(OnPlayerClickLisner onPlayerClickLisner) {
        this.onPlayerClickLisner = onPlayerClickLisner;
    }

    OnEnterClickLisner2 onEnterClickLisner;

    public void setOnEnterClickLisner2(OnEnterClickLisner2 onEnterClickLisner) {
        this.onEnterClickLisner = onEnterClickLisner;
    }

    public interface OnEnterClickLisner2{
        void click(int postion,int currenttime);
    }
}
