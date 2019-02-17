package com.baiyi.jj.app.adapter.item;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.entity.localnews.LocalNewsBean;
import com.baiyi.jj.app.imgtools.GlideTool;
import com.baiyi.jj.app.listitem.news.ListItemBaseNews;
import com.baiyi.jj.app.manager.RandomManager;
import com.baiyi.jj.app.theme.FontUtil;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.RemoteImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.turbo.turbo.mexico.R;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
public class GIfItem extends RecyclerView.ViewHolder {

    private RemoteImageView imgGif;
    private ImageView imgMenBan;
    private Context context;

    int screenWith;
    double rotioRate;

    public GIfItem(View itemView, Context context) {
        super(itemView);
        this.context = context;
        initView(itemView);
    }

    private void initView(View contentView) {
        imgGif = (RemoteImageView) contentView.findViewById(R.id.img_gif_item);
        imgMenBan = (ImageView) contentView.findViewById(R.id.img_men_ban);
        screenWith = Config.getInstance().getScreenWidth(context);
    }

    public void setData(LocalNewsBean bean) {
        //maidian
        if (bean != null && bean.getImageEntity() != null) {
            final String imgInfo = bean.getImageEntity().getLargeImg();
//            final String imgInfo ="http://statics.juxia.com/uploadfile/content/2014/10/2014101512115155.gif";
//            Config.getInstance().setGifH(context, imgInfo, imgGif);
            if (!imgInfo.contains(".gif")) {
                imgMenBan.setVisibility(View.GONE);
            } else {
                imgMenBan.setVisibility(View.VISIBLE);
            }

            rotioRate = bean.getImageEntity().getRatio();
            if (rotioRate != 0) {
                int abimgH = (int) (screenWith * rotioRate);
                imgGif.getLayoutParams().height = abimgH;
            } else {
                int abimgH = (int) (screenWith * ListItemBaseNews.Abstract_Img_Percent);
                imgGif.getLayoutParams().height = abimgH;
            }
            if (rotioRate == 0) {
                getImage(context, imgInfo);
            } else {
                GlideTool.getListImage_BigSrc(context, imgInfo, imgGif);
            }
            imgMenBan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (imgInfo.contains(".gif")) {
                        imgMenBan.setVisibility(View.GONE);
                        GlideTool.getListGifImage_BigSrc(context, imgInfo, imgGif);
                    }
                }
            });
        }
    }

    public void getImage(Context context, String imgUrl) {
//        GlideTool.loadProgressJpg(context,imgUrl,imgLocal,rotioRate);
        int ranNum = RandomManager.getInstance().getRandomNum();
        int placeholder = GlideTool.placeholders[ranNum];
        imgGif.setBackgroundResource(placeholder);
        if (Utils.isStringEmpty(imgUrl)) {
            return;
        }
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
                        double percentH = (double) height / (double) width;
                        imgGif.getLayoutParams().height = (int) (imgGif.getLayoutParams().width * percentH);
                        imgGif.setImageBitmap(resource);

                    }
                });
    }
}
