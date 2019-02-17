package com.baiyi.jj.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.BaseAnalyticsActivity;
import com.baiyi.jj.app.adapter.base.BaseItemAdapter;
import com.baiyi.jj.app.adapter.base.BaseViewHolder;
import com.baiyi.jj.app.adapter.base.UIDataBase;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.imgtools.BitmapFilter;
import com.baiyi.jj.app.imgtools.GlideTool;
import com.baiyi.jj.app.imgtools.RoundAngleImageView;
import com.baiyi.jj.app.listitem.news.ListItemBaseNews;
import com.baiyi.jj.app.manager.RandomManager;
import com.baiyi.jj.app.theme.FontUtil;
import com.baiyi.jj.app.utils.IntentClassChangeUtils;
import com.baiyi.jj.app.utils.TagUtils;
import com.baiyi.jj.app.utils.TextLengthUtils;
import com.baiyi.jj.app.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.turbo.turbo.mexico.R;

import java.util.List;

/**
 * Created by Administrator on 2017/6/15 0015.
 */
public class MorningNewsAdapter extends BaseItemAdapter<NewsListEntity> {

    private Context context;

    public MorningNewsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getLayout() {
        return R.layout.item_morningnews;
    }

    @Override
    public BaseViewHolder getBindingHolder(View view) {
        return new NewsListHolder(view);
    }

    @Override
    public UIDataBase getUIDataItem(NewsListEntity live) {
        return new UIDataNewsList(live);
    }

    class UIDataNewsList extends UIDataBase<NewsListEntity> {
        public UIDataNewsList(NewsListEntity data) {
            super(data);
        }
    }

    class NewsListHolder extends BaseViewHolder<UIDataNewsList>{

//        RoundAngleImageView imgOneCenter;
        ImageView imgOneCenter;
        TextView txtTitle;
        LinearLayout linAddTag;
        TextView txtSource;
        TextView txtTime;
        LinearLayout linItem;

        public NewsListHolder(View itemView) {
            super(itemView);
            imgOneCenter = (ImageView) itemView.findViewById(R.id.img_onecenter);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_title);
            txtSource = (TextView) itemView.findViewById(R.id.txt_source);
            txtTime = (TextView) itemView.findViewById(R.id.txt_time);
            linAddTag = (LinearLayout) itemView.findViewById(R.id.lin_addlable);
            linItem = (LinearLayout) itemView.findViewById(R.id.lin_item);
            int imgFillW = Config.getInstance().getScreenWidth(context)
                    - ContextUtil.dip2px(context,40);
            int imgFillH = (int) (imgFillW * 0.6);
            imgOneCenter.getLayoutParams().width = imgFillW;
            imgOneCenter.getLayoutParams().height = imgFillH;
        }

        @Override
        public void bind(@NonNull final UIDataNewsList data, final int position) {
            if (data.data!=null){
                float textSize = FontUtil.getNewsTitleTextSize(context);
                txtTitle.setTextSize(textSize);
                txtTitle.setTypeface(CmsApplication.getListTitleFace(context));
                TextLengthUtils.getInstance(context).setChar(txtTitle,data.data.getArticle_title().replace("\n", ""),2);

                String timeN = Utils.getTimeName(context, data.data.getArticle_pubDate(),false);
                txtTime.setText(timeN);
                txtSource.setText(data.data.getArticle_source());

                setLabel(linAddTag,data.data);
                if(!Utils.isStringEmpty(data.data.toImgList())){
//                    getBitmap(imgOneCenter,data.data.toImgList().get(0).getMedia_path());
                    GlideTool.getListImage(context,data.data.toImgList().get(0).getMedia_path(),imgOneCenter);
                }
                linItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NewsListEntity entity = data.data;
                        IntentClassChangeUtils.startNewsDetail(context,entity,-1,0,false);
                    }
                });


            }
        }
    }
//    private void getBitmap(final ImageView imageView,String imgUrl){
//        int ranNum = RandomManager.getInstance().getRandomNum();
//        int placeholder = GlideTool.placeholders[ranNum];
//        imageView.setBackgroundResource(placeholder);
//        if (Utils.isStringEmpty(imgUrl)) {
//            imageView.setBackgroundResource(placeholder);
//            return;
//        }
//        Glide.with(context).load(imgUrl).asBitmap()
//                .error(placeholder)
//                .placeholder(placeholder)
//                .centerCrop()
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        Drawable drawable =new BitmapDrawable(resource);
//                        imageView.setBackground(drawable);
//                    }
//                });
//    }

    public void setLabel(LinearLayout linAdd, NewsListEntity newsEntity) {
        List<Integer> tags = newsEntity.toTags();
        if (Utils.isStringEmpty(tags)) {
            linAdd.setVisibility(View.GONE);
            return;
        }
        String[] resStr = TagUtils.getTagStrByTagId(tags,context);
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
            TextPaint tp = textView.getPaint();
            tp.setFakeBoldText(true);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -2);
            lp.rightMargin = BaseActivity.getDensity_int(5);
            linAdd.addView(textView,lp);
        }
        if (size==0){
            linAdd.setVisibility(View.GONE);
        }
    }

}
