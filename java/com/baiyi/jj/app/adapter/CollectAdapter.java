package com.baiyi.jj.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.listitem.search.SearchHolder;
import com.baiyi.jj.app.listitem.search.SearchHolder.OnItemClick;
import com.baiyi.jj.app.listitem.search.SearchNoImgRender;
import com.baiyi.jj.app.listitem.search.SearchNoImgRender.OnDeleteClick;
import com.baiyi.jj.app.listitem.search.SerachRightImgRender;
import com.baiyi.jj.app.listitem.search.SerachRightImgRender.OnYDeleteClick;
import com.baiyi.jj.app.net.CollectUtils;
import com.baiyi.jj.app.net.CollectUtils.DeleteCollectCall;
import com.baiyi.jj.app.utils.Utils;
import com.turbo.turbo.mexico.R;

import java.util.ArrayList;
import java.util.List;

public class CollectAdapter extends RecyclerView.Adapter<SearchHolder> {

    public static final int Type_NoImage = 1;
    public static final int Type_ImgLeft = 2;
    public static final int Type_NoImage_Edit = 3;
    public static final int Type_ImgLeft_Edit = 4;
    public static final int Type_Video = 5;
    public static final int Type_Video_Edit = 6;
    private boolean isEdit = false;
    private OnItemClick recycleItemClick = null;

    private Context context = null;
    private List<NewsListEntity> datas = new ArrayList<NewsListEntity>();

    public CollectAdapter(Context context) {
        this.context = context;

    }

    public List<NewsListEntity> getDatas() {
        return datas;
    }

    public NewsListEntity getItem(int position) {
        if (Utils.isStringEmpty(datas)) {
            return null;
        }
        return datas.get(position);
    }

    public void addData(List<NewsListEntity> list) {
        if (Utils.isStringEmpty(list)) {
            return;
        }
        datas.addAll(list);
        this.notifyDataSetChanged();
    }

    public void setData(List<NewsListEntity> list) {
        datas.clear();
        datas.addAll(list);
        this.notifyDataSetChanged();
    }

    public void deleteItem(int postion) {
        if (postion >= getDatas().size()) {
            return;
        }
        getDatas().remove(postion);
        this.notifyDataSetChanged();
        if (null!=itemIsNullCallBack&&Utils.isStringEmpty(getDatas())){
            itemIsNullCallBack.isNull();
        }

    }

    public void clearAll() {
        getDatas().clear();
        this.notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        if (Utils.isStringEmpty(datas)) {
            return 0;
        }
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        NewsListEntity entity = getDatas().get(position);
        if (!isEdit) {
            return getItemType(entity);
        } else {
            return getItemTypeEdit(entity);
        }
    }

    @Override
    public void onBindViewHolder(SearchHolder arg0, int arg1) {
        int itemtype = getItemViewType(arg1);
        if (itemtype == Type_NoImage) {
            ((SearchNoImgRender) arg0).setDatas(arg1, getItem(arg1));
        } else if (itemtype == Type_NoImage_Edit) {
            ((SearchNoImgRender) arg0).setDatas(arg1, getItem(arg1));
        } else if (itemtype == Type_ImgLeft) {
            ((SerachRightImgRender) arg0).setDatas(arg1, getItem(arg1));
        } else if (itemtype == Type_ImgLeft_Edit) {
            ((SerachRightImgRender) arg0).setDatas(arg1, getItem(arg1));
        }

    }

    @Override
    public SearchHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
        View view = null;
        SearchHolder holder = null;
        if (arg1 == Type_ImgLeft) {
            view = ContextUtil.getLayoutInflater(context).inflate(R.layout.list_item_collect_oneleft,
                    arg0, false);
            holder = new SerachRightImgRender(view, context, isEdit, null);
        } else if (arg1 == Type_ImgLeft_Edit) {
            view = ContextUtil.getLayoutInflater(context).inflate(R.layout.list_item_collect_oneleft,
                    arg0, false);
            holder = new SerachRightImgRender(view, context, isEdit, new OnYDeleteClick() {

                @Override
                public void OnClick(NewsListEntity entity, int position) {
                    deleteCollet(entity, position);
                }
            });
        } else if (arg1 == Type_NoImage) {
            view = ContextUtil.getLayoutInflater(context).inflate(R.layout.list_item_collect_noimg,
                    arg0, false);
            holder = new SearchNoImgRender(view, context, isEdit, null);
        } else if (arg1 == Type_NoImage_Edit) {
            view = ContextUtil.getLayoutInflater(context).inflate(R.layout.list_item_collect_noimg,
                    arg0, false);
            holder = new SearchNoImgRender(view, context, isEdit, new OnDeleteClick() {

                @Override
                public void OnClick(NewsListEntity entity, int position) {
                    deleteCollet(entity, position);
                }
            });
        }
        holder.setRecycleItemClick(new OnItemClick() {

            @Override
            public void OnClick(int position) {
                if (recycleItemClick != null) {
                    recycleItemClick.OnClick(position);
                }
            }
        });
        return holder;
    }


    public void setRecycleItemClick(OnItemClick recycleItemClick) {
        this.recycleItemClick = recycleItemClick;
    }

    private void deleteCollet(final NewsListEntity entity, final int position) {
        new CollectUtils(context, entity.getArticle_id(), entity.getArticleType())//
                .loadDeleteCollect(new DeleteCollectCall() {
                    @Override
                    public void CallBack(int state) {
                        if (state == CollectUtils.State_Success) {
//                    ((BaseActivity)context).displayToast(context.getString(R.string.tip_canel_collect_suc));
                            deleteItem(position);
                        } else {
                            ((BaseActivity) context).displayToast(context.getString(R.string.tip_canel_collect_fal));
                        }
                    }
                });


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

    public ItemIsNullCallBack itemIsNullCallBack;

    public void setItemIsNullCallBack(ItemIsNullCallBack itemIsNullCallBack) {
        this.itemIsNullCallBack = itemIsNullCallBack;
    }

    public interface ItemIsNullCallBack {
        void isNull();
    }
}
