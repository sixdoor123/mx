package com.baiyi.jj.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.adapter.base.BaseItemAdapter;
import com.baiyi.jj.app.adapter.base.BaseViewHolder;
import com.baiyi.jj.app.adapter.base.UIDataBase;
import com.baiyi.jj.app.entity.CreditsEntity;
import com.baiyi.jj.app.imgtools.GlideTool;
import com.baiyi.jj.app.listitem.news.ListItemBaseNews;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.RemoteImageView;
import com.turbo.turbo.mexico.R;

/**
 * GIf动图适配器
 * Created by lizl on 2016/11/17 0017.
 */
public class GifListAdatper extends BaseItemAdapter<String> {


    private Context context;

    public GifListAdatper(Context context) {
        this.context = context;
    }

    @Override
    public int getLayout() {
        return R.layout.item_gif_image;
    }

    @Override
    public BaseViewHolder getBindingHolder(View view) {
        return new CreditsViewHold(view);
    }

    @Override
    public UIDataBase getUIDataItem(String gif) {
        return new UIDataGif(gif);
    }

    class UIDataGif extends UIDataBase<String> {
        public UIDataGif(String data) {
            super(data);
        }
    }

    class CreditsViewHold extends BaseViewHolder<UIDataGif> {

        RemoteImageView imgGif;
        ImageView imgMenBan;

        public CreditsViewHold(View itemView) {
            super(itemView);
            imgGif = (RemoteImageView) itemView.findViewById(R.id.img_gif_item);
            imgMenBan = (ImageView) itemView.findViewById(R.id.img_men_ban);
//            int abimgW = Config.getInstance().getScreenWidth(context)
//                    - ContextUtil.dip2px(context, ListItemBaseNews.Center_Dip_Offset);
////            int abimgH = (int) (abimgW * ListItemBaseNews.Abstract_Img_Percent);
//            imgGif.getLayoutParams().width = abimgW;
//            imgGif.getLayoutParams().height = abimgH;
        }

        @Override
        public void bind(@NonNull UIDataGif data, int position) {

            final String imgInfo = data.data;
            Config.getInstance().setGifH(context, imgInfo, imgGif);
            if (!imgInfo.contains("gif")) {
                imgMenBan.setVisibility(View.GONE);
            }else{
                imgMenBan.setVisibility(View.VISIBLE);
            }
            GlideTool.getListImage_BigSrc(context,imgInfo,imgGif);
            imgMenBan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (imgInfo.contains("gif")) {
                        imgMenBan.setVisibility(View.GONE);
                        GlideTool.getListGifImage_BigSrc(context,imgInfo.substring(0, imgInfo.indexOf("gif") + 3),imgGif);
                    }
                }
            });
        }
    }
}
