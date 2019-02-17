package com.baiyi.jj.app.adapter.item;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
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

import java.util.List;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
public class LargeImageItem extends RecyclerView.ViewHolder{

    private ImageView imgLocal;
    private ImageView imgLocalsmal;
    private TextView textInfo;
    private Context context;
//    private SimpleDraweeView draweeView;

    private OnLargeClick onLargeClick;

    int screenWith;
    double rotioRate;

    public LargeImageItem(View itemView,Context context) {
        super(itemView);
        this.context = context;
        initView(itemView);
    }

    private void initView(View contentView){
        imgLocal = (ImageView) contentView.findViewById(R.id.img_local_big);
        imgLocalsmal = (ImageView) contentView.findViewById(R.id.img_local);
        imgLocal.setVisibility(View.VISIBLE);
        imgLocalsmal.setVisibility(View.GONE);
        textInfo = (TextView) contentView.findViewById(R.id.txt_img_info);
        textInfo.setTypeface(CmsApplication.getDetailConten(context));
        screenWith = Config.getInstance().getScreenWidth(context);
        imgLocal.getLayoutParams().width = screenWith;

//        draweeView = (SimpleDraweeView) contentView.findViewById(R.id.my_image_view);

    }

    public void setData(final LocalNewsBean bean){
        if (bean != null && bean.getImageEntity() != null && !Utils.isStringEmpty(bean.getImageEntity().getLargeImg())){
            if (Utils.isStringEmpty(bean.getImageEntity().getImgInfo())){
                textInfo.setVisibility(View.GONE);
            }else {
                textInfo.setText(bean.getImageEntity().getImgInfo());
                float textSize = FontUtil.getNewsContentSize();
                textInfo.setLineSpacing(FontUtil.getLineSpaceNews(),1);
                textInfo.setTextSize(textSize);
            }
            rotioRate = bean.getImageEntity().getRatio();
            if (rotioRate != 0){
                int abimgH = (int) (screenWith * rotioRate);
                imgLocal.getLayoutParams().height = abimgH;
            }else {
                int abimgH = (int) (screenWith * ListItemBaseNews.Abstract_Img_Percent);
                imgLocal.getLayoutParams().height = abimgH;
            }
           getImage(context,bean.getImageEntity().getLargeImg());
            imgLocal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLargeClick!=null){
                        onLargeClick.clickimg(bean.getImageEntity().getImgIndex(),false);
                    }
                }
            });
        }
    }

    public void getImage(final Context context, String imgUrl) {
//        GlideTool.loadProgressJpg(context,imgUrl,imgLocal,rotioRate);
        int ranNum = RandomManager.getInstance().getRandomNum();
        int placeholder = GlideTool.placeholders[ranNum];
        imgLocal.setBackgroundResource(placeholder);
        if (Utils.isStringEmpty(imgUrl)) {
            return;
        }
        final long start = System.currentTimeMillis();
//        TLog.e("time","start-------"+start);
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
                        double percentH = (double) height/(double) width;
//                        TLog.e("he",width+"====="+height+"======="+imgLocal.getWidth()+"====="+percentH+"====="+imgLocal.getWidth()*percentH);
                        if (rotioRate == 0){
                            imgLocal.getLayoutParams().height = (int)(imgLocal.getLayoutParams().width*percentH);
                        }
                        imgLocal.setImageBitmap(resource);
                        long stop = System.currentTimeMillis();
//                        TLog.e("time","stop-------"+stop);
                        long value =stop-start;
                        if ((value) > 200 && value <1000*120){
                            ((BaseActivity)context).addRequestTime("NewsLocalAct", BaseAnalyticsActivity.Category_img,BaseAnalyticsActivity.Img_Load_LargeImage,value);
                        }
                    }
                });

    }


    public void setOnLargeClick(OnLargeClick onLargeClick) {
        this.onLargeClick = onLargeClick;
    }

    public interface OnLargeClick{
        public void clickimg(int index,boolean isglide);
    }


    private void addImage(List<String> list){
//        for (int i = 0;i<list.size();i++){
//            ImageView imageView = new ImageView(context);
//            int abimgW = Config.getInstance().getScreenWidth(context)
//                    - ContextUtil.dip2px(context, ListItemBaseNews.Center_Dip_Offset);
//            int abimgH = (int) (abimgW * ListItemBaseNews.Abstract_Img_Percent);
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(abimgW,abimgH);
//            layoutParams.setMargins(0, BaseActivity.getDensity_int(10),0,0);
//            imageView.setLayoutParams(layoutParams);
//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
//            GlideTool.getListImage(context, list.get(i), imageView);
//
//            linContainer.addView(imageView);
//        }

    }
}
