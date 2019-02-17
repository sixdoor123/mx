package com.baiyi.jj.app.adapter;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;

import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.adapter.base.GdapterTypeRender;
import com.baiyi.jj.app.adapter.base.ScrollAdapter;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.listitem.search.SearchNoImgItem;
import com.baiyi.jj.app.listitem.search.SearchNoImgItem.OnDeleteClick;
import com.baiyi.jj.app.listitem.search.SearchRighImgItem;
import com.baiyi.jj.app.listitem.search.SearchRighImgItem.OnYDeleteClick;
import com.baiyi.jj.app.net.CollectUtils;
import com.baiyi.jj.app.net.CollectUtils.DeleteCollectCall;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.pulldownview.PullToRefreshListView;

public class SearchDetailAdapter extends ScrollAdapter<NewsListEntity> {

    public static final int Type_NoImage = 1;
    public static final int Type_ImgLeft = 2;
    public static final int Type_NoImage_Edit = 3;
    public static final int Type_ImgLeft_Edit = 4;

    private Context context = null;

    private boolean isEdit = false;

    /**
     *
     */
    public SearchDetailAdapter(Context context, PullToRefreshListView mListView, List<NewsListEntity> data) {
        super(mListView, data);
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        NewsListEntity entity = getData().get(position);
        if (!isEdit) {
            return getItemType(entity);
        } else {
            return getItemTypeEdit(entity);
        }

    }

    public void setEdit(boolean isEdit) {
        this.isEdit = isEdit;
        this.notifyDataSetChanged();
    }

    public boolean getEdit() {
        return isEdit;
    }

    public int getItemTypeEdit(NewsListEntity entity) {
        if (Utils.isStringEmpty(entity.toImgList())) {
            return Type_NoImage_Edit;
        } else {
            return Type_ImgLeft_Edit;
        }
    }

    public int getItemType(NewsListEntity entity) {
        if (Utils.isStringEmpty(entity.toImgList())) {
            return Type_NoImage;
        } else {
            return Type_ImgLeft;
        }
    }

    public void refresh() {
        this.notifyDataSetChanged();
    }

    public void changeItem(boolean isGprs) {
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
        notifyDataSetChanged();
    }

    public void setData(List<NewsListEntity> list, int pageNum) {
        if (Utils.isStringEmpty(list)) {
            List<NewsListEntity> noList = new ArrayList<NewsListEntity>();
            this.getData().clear();
            this.getData().addAll(noList);
            notifyDataSetChanged();
            return;
        }
        this.getData().clear();
        this.getData().addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return 7;
    }


    @Override
    public GdapterTypeRender getAdapterTypeRender(final int position) {
        // TODO Auto-generated method stub
        GdapterTypeRender typeRender = null;
        switch (getItemViewType(position)) {
            case Type_NoImage:
                typeRender = new SearchNoImgItem(context, this, isEdit, null);
                break;
            case Type_ImgLeft:
                typeRender = new SearchRighImgItem(context, this, isEdit, null);
                break;
            case Type_NoImage_Edit:
                typeRender = new SearchNoImgItem(context, this, isEdit, new OnDeleteClick() {

                    @Override
                    public void OnClick(NewsListEntity entity, int position) {
                        deleteCollet(entity, position);
                    }
                });
                break;
            case Type_ImgLeft_Edit:
                typeRender = new SearchRighImgItem(context, this, isEdit, new OnYDeleteClick() {

                    @Override
                    public void OnClick(NewsListEntity entity, int position) {

                        deleteCollet(entity, position);
                    }
                });
                break;
            default:
                typeRender = new SearchNoImgItem(context, this, isEdit, null);
                break;
        }
        return typeRender;
    }

    private void deleteCollet(NewsListEntity entity, final int position) {
        new CollectUtils(context, entity.getArticle_id(), entity.getArticleType())
                .loadDeleteCollect(new DeleteCollectCall() {

                    public void CallBack(int state) {
                        if (state == CollectUtils.State_Success) {
                            deleteItem(position);
                            return;
                        }
                    }
                });

    }

    public void deleteItem(int postion) {
        getData().remove(postion);
        this.notifyDataSetChanged();
    }

    public void clearAll() {
        getData().clear();
        this.notifyDataSetChanged();
    }

    @Override
    public void displayScrollStop() {
        setIsImgLoading(true);

        int childCount = 0;
        if (getListView().getRefreshableView().getFooterViewsCount() != 0) {
            childCount--;
        }

        int headCount = getListView().getRefreshableView().getHeaderViewsCount();
        int start = getListView().getRefreshableView().getFirstVisiblePosition();
        int end = getListView().getRefreshableView().getLastVisiblePosition();
        System.out.println(headCount + "--" + start + "---" + end);
        for (int i = start, j = end; i <= j; i++) {
            int position = i + headCount + childCount;
            NewsListEntity data = (NewsListEntity) getListView().getRefreshableView().getItemAtPosition(position);
            if (data == null) {
                continue;
            }
            View view = getListView().getRefreshableView().getChildAt(i - start + headCount + childCount);
            GdapterTypeRender render = (GdapterTypeRender) view.getTag(R.id.id_adapter_item_type_render);
            displayBitmap(data, render);
        }
    }

    public void displayBitmap(NewsListEntity data, GdapterTypeRender render) {
        if (render instanceof SearchRighImgItem) {
            ((SearchRighImgItem) render).setBitmap(data, true);
        }
    }

    /* (non-Javadoc)
     * @see android.widget.AbsListView.RecyclerListener#onMovedToScrapHeap(android.view.View)
     */
    @Override
    public void onMovedToScrapHeap(View view) {
        // TODO Auto-generated method stub

    }

}
