/**
 *
 */
package com.baiyi.jj.app.activity.main.news;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextPaint;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.BaseAnalyticsActivity;
import com.baiyi.jj.app.activity.BaseSkinFragmentActivity;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.cache.Dao.EditionReadDao;
import com.baiyi.jj.app.cache.Dao.ReadDao;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.imgtools.GlideTool;
import com.baiyi.jj.app.listitem.news.ListItemBaseNews;
import com.baiyi.jj.app.manager.RandomManager;
import com.baiyi.jj.app.theme.FontUtil;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.TagUtils;
import com.baiyi.jj.app.utils.TextLengthUtils;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.MixtureTextView;
import com.baiyi.jj.app.views.RemoteImageView;
import com.baiyi.jj.app.views.pop.DislikePop;
import com.baiyi.jj.skin.entity.AttrFactory;
import com.baiyi.jj.skin.entity.DynamicAttr;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.turbo.turbo.mexico.R;

import java.util.ArrayList;
import java.util.List;

/**
 * �������
 *
 * @author tangkun
 */
public class CenterGifRender extends BaseHolder /*implements GdapterTypeRender*/ {

    private TextView txtTitle = null;//标题
    private RemoteImageView imgCenterOne = null;//图片
    private ImageView imgMore = null;//不感兴趣X
    private TextView txtTime = null;//时间
    private TextView txtSource = null;//文章来源
    private TextView txtOffline = null;//离线图标
    private LinearLayout linAddTag = null;//标签

    private RelativeLayout relaGroupLine = null;
    private LinearLayout linParent = null;

    private boolean isReaded;

    private Context context = null;

    private NewsListEntity data = null;

    private boolean isJustTime = true;

    /**
     * @param arg0
     */
    public CenterGifRender(View arg0, Context context, boolean isVisibleAbstract, boolean isJustTime) {
        super(arg0);
        this.context = context;
        this.isJustTime = isJustTime;
        setEvents(arg0);
    }
    public void setBitmap(NewsListEntity data) {
        if (Utils.isStringEmpty(data.toImgList().get(0).getMedia_path())) {
            return;
        }
        Config.getInstance().setGifH_padding10(context, data.toImgList().get(0).getMedia_path(), imgCenterOne);
        getImage(data.toImgList().get(0).getMedia_path());
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//       GlideTool.getListImage_BigSrc(context,imgInfo,imgCenterOne);
//            }
//        }, 1);
    }

    private void getImage(String imgUrl){
        int ranNum = RandomManager.getInstance().getRandomNum();
        int placeholder = GlideTool.placeholders[ranNum];
        imgCenterOne.setImageResource(placeholder);
        Glide.with(context).load(imgUrl).asBitmap()
                .error(placeholder)
                .placeholder(placeholder)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        int width = resource.getWidth();
                        int height = resource.getHeight();
                        int imgW = Config.getInstance().getScreenWidth(context) - ContextUtil.dip2px(context, ListItemBaseNews.ThreeImg_Dip_Offset);
                        int imgH = (int) (height / width > 1.6 ? imgW * 1.6 : imgW * height / width);
                        imgCenterOne.getLayoutParams().height = imgH;
                        imgCenterOne.setImageBitmap(resource);
                    }
                });
    }

    public void setEvents(View convertView) {
        // TODO Auto-generated method stub
        txtTitle = (TextView) convertView.findViewById(R.id.txt_newstitle2);
        imgCenterOne = (RemoteImageView) convertView.findViewById(R.id.img_one_fill);
        txtSource = (TextView) convertView.findViewById(R.id.txt_source_3line);
        txtOffline = (TextView) convertView.findViewById(R.id.txt_offline3);
        imgMore = (ImageView) convertView.findViewById(R.id.news_more_3line);
        txtTime = (TextView) convertView.findViewById(R.id.txt_time_3line);
        linAddTag = (LinearLayout) convertView.findViewById(R.id.lin_addlabel_3line);
        relaGroupLine = (RelativeLayout) convertView.findViewById(R.id.rela_line);
        linParent = (LinearLayout) convertView.findViewById(R.id.lin_parent);
        int imgW = Config.getInstance().getScreenWidth(context) - ContextUtil.dip2px(context, ListItemBaseNews.ThreeImg_Dip_Offset);
//        int imgH = (int) (imgW * 0.48);
        imgCenterOne.getLayoutParams().width = imgW;
//        imgCenterOne.getLayoutParams().height = imgH;
    }

    public void setDatas(int position, NewsListEntity entity) {

        data = entity;
        if (data == null) {
            return;
        }
        isReaded = new ReadDao(context).isSupport(data.getArticle_id());
        setTitle(data);
        setMorePop(position);
        setSgin(data);
        setBitmap(data);
    }

    private void setMorePop(final int position) {
        imgMore.setOnClickListener(new OnClickListener() {

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


    public void setTitle(NewsListEntity data) {
        float textSize = FontUtil.getNewsTitleTextSize(context);
        txtTitle.setTextSize(textSize);
        txtTitle.setTypeface(CmsApplication.getListTitleFace(context));
        TextLengthUtils.getInstance(context).setChar(txtTitle, data.getArticle_title().replace("\n", ""), 2);
        if (data.getType().equals(TagUtils.StorType_eidtion_top)) {
            txtTitle.setSelected(false);
        } else if (data.getType().equals(TagUtils.StorType_eidtion)) {
            txtTitle.setSelected(new EditionReadDao(context).isSupport(data.getArticle_id()));
        } else {
            txtTitle.setSelected(isReaded);
        }
    }

    public void setSgin(NewsListEntity data) {

        String timeN = Utils.getTimeName(context, data.getArticle_pubDate(), !isJustTime);
        txtTime.setText(timeN);
        if (Utils.isStringEmpty(data.getArticle_source())) {
            txtSource.setVisibility(View.GONE);
        } else {
            txtSource.setVisibility(View.VISIBLE);
            txtSource.setText(data.getArticle_source());
        }
        if (!Utils.isStringEmpty(data.getReservezone3()) && data.getReservezone3().equals("true")) {
            txtOffline.setVisibility(View.VISIBLE);
        } else {
            txtOffline.setVisibility(View.GONE);
        }
        setLabel(linAddTag, data);
    }

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