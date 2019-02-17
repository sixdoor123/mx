package com.baiyi.jj.app.adapter.item;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.BaseAnalyticsActivity;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.entity.localnews.LocalNewsBean;
import com.baiyi.jj.app.imgtools.GlideTool;
import com.baiyi.jj.app.listitem.news.ListItemBaseNews;
import com.baiyi.jj.app.manager.RandomManager;
import com.baiyi.jj.app.theme.FontUtil;
import com.baiyi.jj.app.theme.ThemeUtil;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.turbo.turbo.mexico.R;


/**
 * Created by Administrator on 2017/3/27 0027.
 */
public class SmallImageItem extends RecyclerView.ViewHolder {


    private ImageView imgLocal;
    private ImageView imgLocalBig;
    private TextView textInfo;

    private Context context;

    boolean isSmall = true;

    double rotioRate = 0;

    private int screenWith;

    private LargeImageItem.OnLargeClick onLargeClick;

    public SmallImageItem(View itemView, Context context) {
        super(itemView);
        this.context = context;
        initView(itemView);
    }

    private void initView(View contentView) {
        imgLocal = (ImageView) contentView.findViewById(R.id.img_local);
        imgLocalBig = (ImageView) contentView.findViewById(R.id.img_local_big);
        textInfo = (TextView) contentView.findViewById(R.id.txt_img_info);
        textInfo.setTypeface(CmsApplication.getDetailConten(context));
        screenWith = Config.getInstance().getScreenWidth(context)
                - ContextUtil.dip2px(context, ListItemBaseNews.Center_Dip_Offset);
        int abimgW = (int) (Config.getInstance().getScreenWidth(context) * 0.45);
        int abimgH = (int) (abimgW * ListItemBaseNews.Abstract_Img_Percent);
        imgLocal.getLayoutParams().width = abimgW;
        imgLocal.getLayoutParams().height = abimgH;

        int abimgW2 = Config.getInstance().getScreenWidth(context);
        int abimgH2 = (int) (abimgW * ListItemBaseNews.Abstract_Img_Percent);
        imgLocalBig.getLayoutParams().width = abimgW2;
        imgLocalBig.getLayoutParams().height = abimgH2;
    }

    public void setData(final LocalNewsBean bean) {
        if (bean != null && bean.getImageEntity() != null && !Utils.isStringEmpty(bean.getImageEntity().getSmallImg())) {
            if (Utils.isStringEmpty(bean.getImageEntity().getImgInfo())) {
                textInfo.setVisibility(View.GONE);
            } else {
                textInfo.setText(bean.getImageEntity().getImgInfo());
                float textSize = FontUtil.getNewsContentSize();
                textInfo.setLineSpacing(FontUtil.getLineSpaceNews(),1);
                textInfo.setTextSize(textSize);
            }
            rotioRate = bean.getImageEntity().getRatio();
            if (rotioRate != 0){
                imgLocal.getLayoutParams().height =(int) (imgLocal.getLayoutParams().width*rotioRate);
                imgLocalBig.getLayoutParams().height =(int) (imgLocalBig.getLayoutParams().width*rotioRate);
            }else{
                imgLocal.getLayoutParams().height =(int) (imgLocal.getLayoutParams().width*ListItemBaseNews.Abstract_Img_Percent);
                imgLocalBig.getLayoutParams().height =(int) (imgLocalBig.getLayoutParams().width*ListItemBaseNews.Abstract_Img_Percent);
            }
            getImage(context, bean.getImageEntity().getSmallImg(), imgLocal,true);
            imgLocal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isSmall) {
                        isSmall = false;
                        imgLocal.setVisibility(View.GONE);
                        imgLocalBig.setVisibility(View.VISIBLE);
                        getImage(context, bean.getImageEntity().getSmallImg(), imgLocalBig,false);
                        getImage(context, bean.getImageEntity().getLargeImg(), imgLocalBig,false);

                    }
                }
            });
            imgLocalBig.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLargeClick != null) {
                        onLargeClick.clickimg(bean.getImageEntity().getImgIndex(),true);
                    }
                }
            });
        }
    }

    public void setOnLargeClick(LargeImageItem.OnLargeClick onLargeClick) {
        this.onLargeClick = onLargeClick;
    }

    public void getImage(final Context context, String imgUrl, final ImageView imageView, final boolean isSmall) {
        int ranNum = RandomManager.getInstance().getRandomNum();
        int placeholder = GlideTool.placeholders[ranNum];
        imageView.setBackgroundResource(placeholder);
        if (Utils.isStringEmpty(imgUrl)) {
            return;
        }
//        GlideTool.loadProgressJpg(context,imgUrl,imageView,rotioRate);
                final long start = System.currentTimeMillis();

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
//                        TLog.e("he",width+"====="+height+"======="+imgLocal.getWidth()+"====="+percentH+"====="+imgLocal.getWidth()*percentH);
                        if (rotioRate == 0){
                            rotioRate = (double) height/(double) width;
                            imageView.getLayoutParams().height = (int)(imageView.getLayoutParams().width*rotioRate);
                        }
                        imageView.setImageBitmap(resource);
                        long stop = System.currentTimeMillis();
                        long value =stop-start;
                        if ((value) > 200){
                            if (isSmall){
                                ((BaseActivity)context).addRequestTime("NewsLocalAct", BaseAnalyticsActivity.Category_img,BaseAnalyticsActivity.Img_Load_SmallImage,value);
                            }else {
                                ((BaseActivity)context).addRequestTime("NewsLocalAct", BaseAnalyticsActivity.Category_img,BaseAnalyticsActivity.Img_Load_LargeImage,value);
                            }

                        }
                    }
                });

    }

//    private void addImage(List<String> smalllist, final List<String> largeList){
//        for (int i = 0;i<smalllist.size();i++){
//            ImageView imageView = new ImageView(context);
//            int abimgW = (int) (Config.getInstance().getScreenWidth(context)*0.45);
//            int abimgH = (int) (abimgW * ListItemBaseNews.Abstract_Img_Percent);
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(abimgW,abimgH);
//            layoutParams.setMargins(0, BaseActivity.getDensity_int(10),0,0);
//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int abimgW = Config.getInstance().getScreenWidth(context)
//                            - ContextUtil.dip2px(context, ListItemBaseNews.Center_Dip_Offset);
//                    int abimgH = (int) (abimgW * ListItemBaseNews.Abstract_Img_Percent);
//                    v.setLayoutParams(new LinearLayout.LayoutParams(abimgW,abimgH));
//                }
//            });
//            GlideTool.getListImage(context, smalllist.get(i), imageView);
//
//            linContainer.addView(imageView);
//        }
//
//    }
}
