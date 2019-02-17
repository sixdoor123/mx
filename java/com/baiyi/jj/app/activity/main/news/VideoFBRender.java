package com.baiyi.jj.app.activity.main.news;

import android.content.Context;
import android.media.Image;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.cache.Dao.EditionReadDao;
import com.baiyi.jj.app.cache.Dao.ReadDao;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.listitem.news.ListItemBaseNews;
import com.baiyi.jj.app.theme.FontUtil;
import com.baiyi.jj.app.utils.TagUtils;
import com.baiyi.jj.app.utils.TextLengthUtils;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.RemoteImageView;
import com.baiyi.jj.app.views.pop.DislikePop;
import com.turbo.turbo.mexico.R;

import java.util.List;

/**
 * Created by Administrator on 2017/5/26 0026.
 */
public class VideoFBRender extends BaseHolder{

    private RemoteImageView imgLeft = null;
    private ImageView imgPlay = null;
    private TextView txtOffline = null;
    private TextView txtTitle = null;
    private TextView txtSource = null;
    private ImageView imgMore = null;
    private TextView txtTime = null;
    private LinearLayout linAddTag = null;
    private RelativeLayout RelaRight = null;


    private Context context;
    private boolean isReaded;
    private NewsListEntity data = null;

    private boolean isJustTime = true;

    public VideoFBRender(View arg0, Context context, boolean isJustTime) {
        super(arg0);
        this.context = context;
        this.isJustTime = isJustTime;
        setEvents(arg0);
    }

    public void setEvents(View convertView) {
        // TODO Auto-generated method stub
        imgLeft = (RemoteImageView) convertView.findViewById(R.id.img_one_left);
        int imgW = (Config.getInstance().getScreenWidth(context) - ContextUtil
                .dip2px(context, ListItemBaseNews.ThreeImg_Dip_Offset)) / 3;
        int imgH = (int) (imgW * ListItemBaseNews.Defult_Img_Percent);
        imgLeft.getLayoutParams().width = imgW;
        imgLeft.getLayoutParams().height = imgH;
        RelaRight = (RelativeLayout) convertView.findViewById(R.id.rela_left);
        RelaRight.getLayoutParams().width = imgW;
        RelaRight.getLayoutParams().height = imgH;

        imgPlay = (ImageView) convertView.findViewById(R.id.img_play_icon);
        txtTitle = (TextView) convertView.findViewById(R.id.txt_newstitle1);
        txtSource = (TextView) convertView.findViewById(R.id.txt_source_3line);
        imgMore = (ImageView) convertView.findViewById(R.id.news_more_3line);
        txtTime = (TextView) convertView.findViewById(R.id.txt_time_3line);
        txtOffline = (TextView) convertView.findViewById(R.id.txt_offline3);
        linAddTag = (LinearLayout) convertView
                .findViewById(R.id.lin_addlabel_3line);

    }

    private void setMorePop(final int position) {
        imgMore.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                DislikePop pop = new DislikePop(context, data.getArticle_id());
                pop.showPopView(imgMore, 0);
                pop.setDisLike(new DislikePop.PopDisLike() {
                    @Override
                    public void Callback(int isSuccess) {
                        if (popDislikeOnclick != null) {
                            popDislikeOnclick.setPopClick(position);
                        }
                    }
                });
            }
        });
    }
    public void setBitmap(NewsListEntity data) {
        if (Utils.isStringEmpty(data.toImgList())){
            imgLeft.setBackgroundResource(R.color.day_text_color_black);
            imgPlay.setVisibility(View.VISIBLE);
            return;
        }
        imgPlay.setVisibility(View.VISIBLE);
//        if (!Utils.isStringEmpty(data.getTemplate()) && data.getTemplate().equals(String.valueOf(ListItemBaseNews.Temp_Image_videoyutube))){
//            imgPlay.setVisibility(View.VISIBLE);
//        }
        if (imgLeft.getVisibility() == View.VISIBLE) {
            imgLeft.loadImg(context, data.toImgList().get(0).getMedia_path());
        }
    }


    public void setDatas(int position, NewsListEntity entity) {

        this.data = entity;
        setMorePop(position);
        float textSize = FontUtil.getNewsTitleTextSize(context);
        txtTitle.setTextSize(textSize);
        txtTitle.setTypeface(CmsApplication.getListTitleFace(context));
        TextLengthUtils.getInstance(context).setChar(txtTitle,data.getArticle_title(),1);
        isReaded = new ReadDao(context).isSupport(data.getArticle_id());
//		txtTitle.setSelected(isReaded);
        if (data.getType().equals(TagUtils.StorType_eidtion_top)){
            txtTitle.setSelected(false);
        } else if (data.getType().equals(TagUtils.StorType_eidtion)){
            txtTitle.setSelected(new EditionReadDao(context).isSupport(data.getArticle_id()));
        }else {
            txtTitle.setSelected(isReaded);
        }

        String timeN = Utils.getTimeName(context, data.getArticle_pubDate(),!isJustTime);
        txtTime.setText(timeN);

        if (Utils.isStringEmpty(data.getArticle_source())) {
            txtSource.setVisibility(View.GONE);
        } else {
            txtSource.setVisibility(View.VISIBLE);
            txtSource.setText(data.getArticle_source().replace("\n", ""));
        }
        if (!Utils.isStringEmpty(data.getReservezone3()) && data.getReservezone3().equals("true")) {
            txtOffline.setVisibility(View.VISIBLE);
        } else {
            txtOffline.setVisibility(View.GONE);
        }

        setLabel(linAddTag, data);
        setBitmap(data);

    }

    public void setLabel(LinearLayout linAdd, NewsListEntity newsEntity) {
        List<Integer> tags = newsEntity.toTags();
        if (Utils.isStringEmpty(tags)) {
            linAdd.setVisibility(View.GONE);
            return;
        }
//		int[] resId = TagUtils.getNewsTagByTagId(tags,context);
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
//			textView.setTextColor(resColor[i]);
            TextPaint tp = textView.getPaint();
            tp.setFakeBoldText(true);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -2);
            lp.rightMargin = BaseActivity.getDensity_int(5);
            linAdd.addView(textView,lp);

//			ImageView iv = new ImageView(context);
//			iv.setImageDrawable(context.getResources().getDrawable(resId[i]));
//			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -2);
//			lp.rightMargin = BaseActivity.getDensity_int(5);
//			linAdd.addView(iv, lp);
//
//			List<DynamicAttr> mDynamicAttr = new ArrayList<DynamicAttr>();
//			mDynamicAttr.add(new DynamicAttr(AttrFactory.BACKGROND_DRAWABLE,
//					resId[i]));
//			((BaseSkinFragmentActivity) context).dynamicAddView(iv,
//					mDynamicAttr);
        }
        if (size==0){
            linAdd.setVisibility(View.GONE);
        }

    }

}
