/**
 *
 */
package com.baiyi.jj.app.activity.main.news;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextPaint;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baiyi.core.file.Preference;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.cache.Dao.EditionReadDao;
import com.baiyi.jj.app.cache.Dao.ReadDao;
import com.baiyi.jj.app.manager.EditionManager;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.TextLengthUtils;
import com.baiyi.jj.app.views.pop.DislikePop;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.BaseSkinFragmentActivity;
import com.baiyi.jj.app.adapter.base.GdapterTypeRender;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.application.accont.PageSet;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.listitem.news.ListItemBaseNews;
import com.baiyi.jj.app.theme.FontUtil;
import com.baiyi.jj.app.utils.TagUtils;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.MixtureTextView;
import com.baiyi.jj.app.views.RemoteImageView;
import com.baiyi.jj.skin.entity.AttrFactory;
import com.baiyi.jj.skin.entity.DynamicAttr;

/**
 * @author tangkun
 */
public class H3ImgRender extends BaseHolder /*implements GdapterTypeRender */ {
    private TextView txtTitle = null;
    private TextView txtCommentNum = null;
    private TextView txtSource = null;
    private TextView txtOffline = null;
    private ImageView imgMore = null;
    private TextView txtTime = null;
    private TextView txtCollect = null;
    private LinearLayout linAddTag = null;

    private RemoteImageView[] imgs = null;
    private LinearLayout imgGruop = null;

    protected RemoteImageView imgAbtract = null;
    private MixtureTextView mAbstract = null;
    private RelativeLayout abstractGroup = null;
    private RemoteImageView imgFillAbstract = null;
    private LinearLayout groupAbstract = null;
    private TextView abstractTextFill = null;

    private Context context;
    private boolean isReaded;

    private NewsListEntity data = null;
    private boolean isVisibleAbstract;

    /**
     * ����ƫ�ƣ����Ͼۼ��¾�
     */
    private LinearLayout linParent = null;
    // �����
    private RelativeLayout linLine = null;

    private boolean isJustTime = true;

    public H3ImgRender(View arg0, Context context,
                       boolean isVisibleAbstract, boolean isJustTime) {
        super(arg0);
        this.context = context;
        this.isVisibleAbstract = isVisibleAbstract;
        this.isJustTime = isJustTime;
        setEvents(arg0);
    }

    public void setBitmap(NewsListEntity data) {
        if (imgGruop.getVisibility() == View.VISIBLE) {
            for (int i = 0; i < data.toImgList().size(); i++) {
                imgs[i].loadImg(context, data.toImgList().get(i).getMedia_path());
            }
            return;
        }
        if (imgAbtract.getVisibility() == View.VISIBLE) {
            imgAbtract.loadImg(context, data.toImgList().get(0).getMedia_path());
        }
        if (imgFillAbstract.getVisibility() == View.VISIBLE) {
            imgFillAbstract.loadImg(context, data.toImgList().get(0).getMedia_path());
        }
    }

    public void setDataNull() {
        if (data != null) {
            data = null;
        }
    }

    public void setImgNull() {
        if (data != null && !Utils.isStringEmpty(data.toImgList()))
            for (int i = 0; i < data.toImgList().size(); i++) {
                imgs[i].setImageBitmap(null);
            }
        if (imgAbtract != null) {
            imgAbtract.setImageBitmap(null);
        }
        if (imgFillAbstract != null) {
            imgFillAbstract.setImageBitmap(null);
        }
    }

    public void setEvents(View convertView) {
        // TODO Auto-generated method stub
        txtTitle = (TextView) convertView.findViewById(R.id.txt_newstitle3);
        imgAbtract = (RemoteImageView) convertView
                .findViewById(R.id.img_abstract_right);
        int abimgW = (int) ((Config.getInstance().getScreenWidth(context) - ContextUtil
                .dip2px(context, ListItemBaseNews.RightImg_Dip_Offset)) * 0.5);
        int abimgH = (int) (abimgW * ListItemBaseNews.Abstract_Img_Percent);
        imgAbtract.getLayoutParams().width = abimgW;
        imgAbtract.getLayoutParams().height = abimgH;
        mAbstract = (MixtureTextView) convertView
                .findViewById(R.id.txt_abstract_item);
        abstractGroup = (RelativeLayout) convertView
                .findViewById(R.id.abstract_group);
        abstractTextFill = (TextView) convertView
                .findViewById(R.id.txt_abstract_fill);

        groupAbstract = (LinearLayout) convertView
                .findViewById(R.id.group_abstract_img);
        imgFillAbstract = (RemoteImageView) convertView
                .findViewById(R.id.img_abstract_fill_img);
        int imgFillW = Config.getInstance().getScreenWidth(context)
                - ContextUtil.dip2px(context,
                ListItemBaseNews.Center_Dip_Offset);
        int imgFillH = (int) (imgFillW * ListItemBaseNews.Center_Img_Percent);
        imgFillAbstract.getLayoutParams().width = imgFillW;
        imgFillAbstract.getLayoutParams().height = imgFillH;

        imgGruop = (LinearLayout) convertView.findViewById(R.id.lin_img_three);
        imgs = new RemoteImageView[3];
        imgs[0] = (RemoteImageView) convertView.findViewById(R.id.img_three_1);
        imgs[1] = (RemoteImageView) convertView.findViewById(R.id.img_three_2);
        imgs[2] = (RemoteImageView) convertView.findViewById(R.id.img_three_3);
        int imgW = (Config.getInstance().getScreenWidth(context) - ContextUtil
                .dip2px(context, ListItemBaseNews.ThreeImg_Dip_Offset)) / 3;
        int imgH = (int) (imgW * ListItemBaseNews.Defult_Img_Percent);
        imgs[0].getLayoutParams().width = imgW;
        imgs[1].getLayoutParams().width = imgW;
        imgs[2].getLayoutParams().width = imgW;
        imgs[0].getLayoutParams().height = imgH;
        imgs[1].getLayoutParams().height = imgH;
        imgs[2].getLayoutParams().height = imgH;

        txtCommentNum = (TextView) convertView
                .findViewById(R.id.txt_comment1_3line);
        txtSource = (TextView) convertView.findViewById(R.id.txt_source_3line);
        txtOffline = (TextView) convertView.findViewById(R.id.txt_offline3);
        imgMore = (ImageView) convertView.findViewById(R.id.news_more_3line);
        txtTime = (TextView) convertView.findViewById(R.id.txt_time_3line);
        txtCollect = (TextView) convertView
                .findViewById(R.id.txt_collect1_3line);
        linAddTag = (LinearLayout) convertView
                .findViewById(R.id.lin_addlabel_3line);
        linParent = (LinearLayout) convertView.findViewById(R.id.lin_parent);
        linLine = (RelativeLayout) convertView.findViewById(R.id.lin_line);
    }

    public void setDatas(int position, NewsListEntity entity) {

        data = entity;
        if (data == null) {
            return;
        }

        isReaded = new ReadDao(context).isSupport(data.getArticle_id());
        setTitle(data);
        setSgin(data);
        setMorePop(position);
        if (isVisibleAbstract) {
            setAbstract(data);
        } else {
            groupAbstract.setVisibility(View.GONE);
            imgGruop.setVisibility(View.VISIBLE);
        }
        setBitmap(data);

        setmarign();

    }

    private void setMorePop(final int position) {
        imgMore.setOnClickListener(new OnClickListener() {

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

    private void setmarign() {

        linParent.setPadding(BaseActivity.getDensity_int(NoImgRender.left_Right),
                FontUtil.getTopMaigrn(), 0, FontUtil.getTopMaigrn());

        LinearLayout.LayoutParams layoutParams = (LayoutParams) linLine
                .getLayoutParams();

        if (groupAbstract.getVisibility() == View.VISIBLE) {
            layoutParams.setMargins(0, BaseActivity.getDensity_int(4),
                    BaseActivity.getDensity_int(NoImgRender.left_Right), 0);
        } else {
            layoutParams.setMargins(0, BaseActivity.getDensity_int(FontUtil.getLineMaigrn5_7()),
                    BaseActivity.getDensity_int(NoImgRender.left_Right), 0);
        }
        linLine.setLayoutParams(layoutParams);

        LinearLayout.LayoutParams layoutParams2 = (LayoutParams) imgGruop
                .getLayoutParams();
        layoutParams2.setMargins(0, BaseActivity.getDensity_int(FontUtil.getLineMaigrn4_5()),
                BaseActivity.getDensity_int(NoImgRender.left_Right), 0);
        imgGruop.setLayoutParams(layoutParams2);
    }

    public void setTitle(NewsListEntity data) {

        float textSize = FontUtil.getNewsTitleTextSize(context);
        txtTitle.setTextSize(textSize);
        txtTitle.setTypeface(CmsApplication.getListTitleFace(context));
        TextLengthUtils.getInstance(context).setChar(txtTitle, data.getArticle_title(), 1);
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
            txtSource.setText(data.getArticle_source().replace("\n", ""));
        }
        if (!Utils.isStringEmpty(data.getReservezone3()) && data.getReservezone3().equals("true")) {
            txtOffline.setVisibility(View.VISIBLE);
        } else {
            txtOffline.setVisibility(View.GONE);
        }
        txtCollect.setText(String.valueOf(data.getCollect_num()));
        txtCommentNum.setText(data.getArticle_comment_num() + context.getResources().getString(R.string.txt_comment));
        setLabel(linAddTag, data);
    }

    public void setAbstract(NewsListEntity data) {
        if (BaseHolder.isVisibleTitleImg(data.getArticle_abstract())) {
            groupAbstract.setVisibility(View.GONE);
            imgGruop.setVisibility(View.VISIBLE);
        } else {
            int visbileType = BaseHolder.isAbstractType(data);
            if (visbileType == BaseHolder.Visible_Type_RightImg) {
                groupAbstract.setVisibility(View.VISIBLE);
                abstractGroup.setVisibility(View.VISIBLE);
                abstractTextFill.setVisibility(View.GONE);
                imgFillAbstract.setVisibility(View.GONE);
                imgGruop.setVisibility(View.GONE);
                displayAbtractPic(data);
            } else if (visbileType == BaseHolder.Visible_Type_FillImg) {
                groupAbstract.setVisibility(View.VISIBLE);
                abstractGroup.setVisibility(View.GONE);
                abstractTextFill.setVisibility(View.VISIBLE);
                imgFillAbstract.setVisibility(View.VISIBLE);
                imgGruop.setVisibility(View.GONE);
                displayAbstractToFillImg(data);
            } else {
                groupAbstract.setVisibility(View.GONE);
                imgGruop.setVisibility(View.VISIBLE);
            }
        }
    }

    private void displayAbtractPic(NewsListEntity data) {

        String ab = data.getArticle_abstract().replaceAll("\\r\\n", "");
        mAbstract.setText(Utils.ToDBC(ab));
        float size = (((BaseActivity) context).getDensity())
                * FontUtil.getAbstratTextSize(context);
        mAbstract.setTextSize((int) size);
        mAbstract.setTextColor(context.getResources().getColor(
                R.color.day_text_color_737373));
    }

    private void displayAbstractToFillImg(NewsListEntity data) {
        int abtractLines = AccountManager.getInstance().getSummary_Is_Display();
        String ab = data.getArticle_abstract().replaceAll("\\r\\n", "");
        abstractTextFill.setText(Utils.ToDBC(ab));
        abstractTextFill.setEllipsize(TruncateAt.END);
        abstractTextFill.setMaxLines(TagUtils.getLine(abtractLines));
        abstractTextFill.setTextSize(FontUtil.getAbstratTextSize(context));
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
        if (size == 0) {
            linAdd.setVisibility(View.GONE);
        }
    }

}
