/**
 *
 */
package com.baiyi.jj.app.activity.main.news;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.Layout;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.baiyi.core.file.Preference;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.cache.Dao.EditionReadDao;
import com.baiyi.jj.app.cache.Dao.ReadDao;
import com.baiyi.jj.app.manager.CountryMannager;
import com.baiyi.jj.app.manager.EditionManager;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.TextLengthUtils;
import com.baiyi.jj.app.views.pop.DislikePop;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.BaseSkinFragmentActivity;
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
public class RightImgRender extends BaseHolder /*implements GdapterTypeRender*/ {

    private TextView txtTitle = null;
    private RemoteImageView imgLeft = null;
    private TextView txtSource = null;
    private TextView txtOffline = null;
    private TextView txtTime = null;
    private TextView txtCommentNum = null;
    private ImageView imgMore = null;

    private LinearLayout line2TimeGruop = null;
    private LinearLayout line3TimeGruop = null;

    private FrameLayout fram2Line = null;
    private RelativeLayout fram3Line = null;

    private TextView source_3line = null;
    private TextView offline_3line = null;
    private TextView comment_3line = null;
    private TextView time_3line = null;
    private ImageView more_3line = null;

    /**
     * ��ͼ�ı����
     */
    private LinearLayout lineTimeGruopFill = null;
    private TextView source_Fill = null;
    private TextView offline_Fill = null;
    private TextView comment_Fill = null;
    private TextView time_Fill = null;
    private ImageView more_Fill = null;
    private LinearLayout linAddTagFill = null;

    /**
     * ��ǩ
     */
    private LinearLayout linAddTag = null;
    private LinearLayout linAddTag3Line = null;

    protected RemoteImageView imgAbtract = null;
    private MixtureTextView mAbstract = null;
    private RelativeLayout abstractGroup = null;
    private RemoteImageView imgFillAbstract = null;
    private LinearLayout groupAbstract = null;
    private TextView abstractTextFill = null;

    private Context context = null;

    private NewsListEntity data = null;
    private boolean isVisibleAbstract;
    private boolean isGprs = false;

    /**
     * ����ƫ�ƣ����Ͼۼ��¾�
     */
    private LinearLayout linParent = null;

    private boolean isJustTime = true;

    /**
     * @param context
     */
    public RightImgRender(View arg0, Context context,
                          boolean isVisibleAbstract, boolean isGprs, boolean isJustTime) {
        super(arg0);
        this.context = context;
        this.isVisibleAbstract = isVisibleAbstract;
        this.isJustTime = isJustTime;
        this.isGprs = isGprs;
        setEvents(arg0);
    }

    public void setBitmap(NewsListEntity data) {
        if (imgLeft.getVisibility() == View.VISIBLE) {
//            String testimg = "https://d25hg5j0eq13gp.cloudfront.net/dcfda30f-0218-11e7-a2a2-382c4a6ef969";
//            String testimg2 = "http://cloudimg.hotoday.in/v1/icon?id=2356986846657198162&width=220&height=158";
//            imgLeft.loadImg(context,testimg);
            imgLeft.loadImg(context, data.toImgList().get(0).getMedia_path());
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
        if (imgLeft != null) {
            imgLeft.setImageBitmap(null);
        }
        if (imgAbtract != null) {
            imgAbtract.setImageBitmap(null);
        }
        if (imgFillAbstract != null) {
            imgFillAbstract.setImageBitmap(null);
        }
    }

    public void setEvents(View convertView) {
        // TODO Auto-generated method
        imgLeft = (RemoteImageView) convertView.findViewById(R.id.img_one_left);
        int imgW = (Config.getInstance().getScreenWidth(context) - ContextUtil
                .dip2px(context, ListItemBaseNews.ThreeImg_Dip_Offset)) / 3;
        int imgH = (int) (imgW * ListItemBaseNews.Defult_Img_Percent);
        imgLeft.getLayoutParams().width = imgW;
        imgLeft.getLayoutParams().height = imgH;
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

        txtCommentNum = (TextView) convertView.findViewById(R.id.txt_comment1);
        txtTitle = (TextView) convertView.findViewById(R.id.txt_newstitle1);
        // ����һ��TxtTitle������
        ViewTreeObserver vto = txtTitle.getViewTreeObserver();
        vto.addOnPreDrawListener(new OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {

                if (imgLeft.getVisibility() == View.VISIBLE) {
//					System.out.println("has-----------"+txtTitle.getLineCount());
                    displayTitle(txtTitle.getLineCount());
                }
                return true;
            }
        });

        txtTime = (TextView) convertView.findViewById(R.id.txt_time1);
        txtSource = (TextView) convertView.findViewById(R.id.txt_source1);
        txtOffline = (TextView) convertView.findViewById(R.id.txt_offline1);

        line2TimeGruop = (LinearLayout) convertView
                .findViewById(R.id.title_2line);
        line3TimeGruop = (LinearLayout) convertView
                .findViewById(R.id.title_3line);

        fram2Line = (FrameLayout) convertView.findViewById(R.id.fram_2line);
        fram3Line = (RelativeLayout) convertView.findViewById(R.id.fram_3line);

        lineTimeGruopFill = (LinearLayout) convertView
                .findViewById(R.id.title_fill_line);

        linAddTag = (LinearLayout) convertView.findViewById(R.id.lin_addlabel1);
        linAddTag3Line = (LinearLayout) convertView.findViewById(R.id.lin_addlabel_3line);
        imgMore = (ImageView) convertView.findViewById(R.id.news_more1);

        source_3line = (TextView) convertView
                .findViewById(R.id.txt_source_3line);
        offline_3line = (TextView) convertView
                .findViewById(R.id.txt_offline3);
        comment_3line = (TextView) convertView
                .findViewById(R.id.txt_comment1_3line);
        time_3line = (TextView) convertView.findViewById(R.id.txt_time_3line);
        more_3line = (ImageView) convertView.findViewById(R.id.news_more_3line);

        source_Fill = (TextView) convertView.findViewById(R.id.txt_source_fill);
        offline_Fill = (TextView) convertView.findViewById(R.id.txt_offline_fill);
        comment_Fill = (TextView) convertView
                .findViewById(R.id.txt_comment_fill);
        time_Fill = (TextView) convertView.findViewById(R.id.txt_time_fill);
        more_Fill = (ImageView) convertView.findViewById(R.id.news_more_fill);
        linAddTagFill = (LinearLayout) convertView
                .findViewById(R.id.lin_addlabel_fill);


        linParent = (LinearLayout) convertView.findViewById(R.id.lin_parent);
    }

    public void setDatas(int position, NewsListEntity entity) {
        // TODO Auto-generated method stub

        data = entity;
        if (data == null) {
            return;
        }
        setMorePop(position);
        displayOneCenter(data);
        if (isVisibleAbstract) {
            setAbstract(data);

        } else {
            groupAbstract.setVisibility(View.GONE);
            imgLeft.setVisibility(View.VISIBLE);
//			displayTitle(data);
        }

        setBitmap(data);

        setMargin();
    }

    private void setMorePop(final int position) {
        imgMore.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                showPop(position,imgMore);
            }
        });
        more_3line.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                showPop(position,more_3line);
            }
        });
        more_Fill.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                showPop(position,more_Fill);
            }
        });
    }

    private void showPop(final int position,ImageView view){
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

    private void setMargin() {
        // TODO Auto-generated method stub
        // ���Ͼ�
        linParent.setPadding(BaseActivity.getDensity_int(NoImgRender.left_Right),
                FontUtil.getTopMaigrn(), 0,
                FontUtil.getTopMaigrn());

    }

    private void displayTitle(int line) {

//        if (line <= 2) {
//            line3TimeGruop.setVisibility(View.GONE);
//            line2TimeGruop.setVisibility(View.VISIBLE);
//            fram3Line.setVisibility(View.GONE);
//            fram2Line.setVisibility(View.VISIBLE);
//
//            LinearLayout.LayoutParams layoutParams = (LayoutParams) fram2Line
//                    .getLayoutParams();
//            layoutParams.setMargins(0, BaseActivity.getDensity_int(FontUtil.getTitleMaigrn3_4()),
//                    0, 0);
//            fram2Line.setLayoutParams(layoutParams);
//        } else {
            displayMark3Line();

            LinearLayout.LayoutParams layoutParams = (LayoutParams) fram3Line
                    .getLayoutParams();
            layoutParams.setMargins(0, BaseActivity.getDensity_int(FontUtil.getTitleMaigrn3line()),
                    BaseActivity.getDensity_int(NoImgRender.left_Right), 0);
            fram3Line.setLayoutParams(layoutParams);
//        }

    }


    private void displayMark3Line() {
        line2TimeGruop.setVisibility(View.GONE);
        line3TimeGruop.setVisibility(View.VISIBLE);
        fram2Line.setVisibility(View.GONE);
        fram3Line.setVisibility(View.VISIBLE);
    }

    public void setAbstract(NewsListEntity data) {
//		if (isGprs) {
//			groupAbstract.setVisibility(View.VISIBLE);
//			abstractTextFill.setVisibility(View.VISIBLE);
//			imgFillAbstract.setVisibility(View.GONE);
//			abstractGroup.setVisibility(View.GONE);
//			imgLeft.setVisibility(View.VISIBLE);
//			displayTitle(data);
//			return;
//		}
        if (BaseHolder.isVisibleTitleImg(data.getArticle_abstract())) {
            groupAbstract.setVisibility(View.GONE);
            imgLeft.setVisibility(View.VISIBLE);
            imgAbtract.setVisibility(View.GONE);
            imgFillAbstract.setVisibility(View.GONE);
//			displayTitle(data);

        } else {
            int visbileType = BaseHolder.isAbstractType(data);
            if (isGprs && visbileType == BaseHolder.Visible_Type_FillImg) {
                visbileType = BaseHolder.Visible_Type_RightImg;
            }
//            if (CountryMannager.getInstance().getCurrentCountry() == CountryMannager.Country_India && visbileType == BaseHolder.Visible_Type_FillImg) {
//                visbileType = BaseHolder.Visible_Type_RightImg;
//            }
            if (visbileType == BaseHolder.Visible_Type_RightImg) {
                groupAbstract.setVisibility(View.VISIBLE);
                abstractGroup.setVisibility(View.VISIBLE);
                abstractTextFill.setVisibility(View.GONE);
                imgAbtract.setVisibility(View.VISIBLE);
                imgFillAbstract.setVisibility(View.GONE);
                imgLeft.setVisibility(View.GONE);
                displayAbtractPic(data);
                displayMark3Line();
            } else if (visbileType == BaseHolder.Visible_Type_FillImg) {
                groupAbstract.setVisibility(View.VISIBLE);
                abstractGroup.setVisibility(View.GONE);
                abstractTextFill.setVisibility(View.VISIBLE);
                imgFillAbstract.setVisibility(View.VISIBLE);
                imgAbtract.setVisibility(View.GONE);
                imgLeft.setVisibility(View.GONE);
                displayAbstractToFillImg(data);
                displayMark3Line();

                line3TimeGruop.setVisibility(View.GONE);
                lineTimeGruopFill.setVisibility(View.VISIBLE);
            } else {
                groupAbstract.setVisibility(View.GONE);
                imgLeft.setVisibility(View.VISIBLE);
                imgAbtract.setVisibility(View.GONE);
                imgFillAbstract.setVisibility(View.GONE);
//				displayTitle(data);
            }
        }
    }

    /**
     * ��ʾ СͼժҪ
     *
     * @param data
     */
    private void displayAbtractPic(NewsListEntity data) {
        /**
         * ���ժҪ
         */
        String ab = data.getArticle_abstract().replaceAll("\\r\\n", "");
        mAbstract.setText(Utils.ToDBC(ab));
        float size = (((BaseActivity) context).getDensity())
                * FontUtil.getAbstratTextSize(context);
        mAbstract.setTextSize((int) size);
        mAbstract.setTextColor(context.getResources().getColor(
                R.color.day_text_color_888888));
        abstractTextFill.setVisibility(View.GONE);

        setAbsShowSpace();

    }

    /**
     * ����ʾժҪʱ�����ñ�������еľ���
     */
    private void setAbsShowSpace() {
//		// ����
//		LinearLayout.LayoutParams layoutParams = (LayoutParams) txtTitle
//				.getLayoutParams();
//		layoutParams.setMargins(0, 0, BaseActivity.getDensity_int(5),
//				BaseActivity.getDensity_int(FontUtil.getTitleMaigrn()));
//		txtTitle.setLayoutParams(layoutParams);


        LinearLayout.LayoutParams layoutParams = (LayoutParams) fram3Line
                .getLayoutParams();
//		layoutParams.setMargins(0, BaseActivity.getDensity_int(FontUtil.getLineMaigrn3_4()),
        layoutParams.setMargins(0, BaseActivity.getDensity_int(4),
                BaseActivity.getDensity_int(NoImgRender.left_Right), 0);
        fram3Line.setLayoutParams(layoutParams);

    }

    private void displayAbstractToFillImg(NewsListEntity data) {
        String ab = data.getArticle_abstract().replaceAll("\\r\\n", "");
        abstractTextFill.setText(Utils.ToDBC(ab));

        setAbsShowSpace();
    }

    private void displayOneCenter(NewsListEntity data) {
        String timeN = Utils.getTimeName(context, data.getArticle_pubDate(), !isJustTime);

        // TextPaint mTextPaint=txtTitle.getPaint();
        // int
        // mTextViewWidth=(int)mTextPaint.measureText(newsEntity.getArtcle_title());
        // ���±���
        String titleName = Utils.ToDBC(data.getArticle_title());
        float textSize = FontUtil.getNewsTitleTextSize(context);
        txtTitle.setTypeface(CmsApplication.getListTitleFace(context));
        if (imgLeft.getVisibility() == View.VISIBLE){
            TextLengthUtils.getInstance(context).setChar(txtTitle,titleName,0);
        }else {
            TextLengthUtils.getInstance(context).setChar(txtTitle,titleName,2);
        }


        txtTitle.setTextSize(textSize);
//        if (isJustTime) {
//            txtTitle.setSelected(new ReadDao(context).isSupport(data.getArticle_id()));
//        } else {
//            txtTitle.setSelected(EditionManager.getInstance().isRead(data.getArticle_id()));
//        }

        if (data.getType().equals(TagUtils.StorType_eidtion_top)){
            txtTitle.setSelected(false);
        } else if (data.getType().equals(TagUtils.StorType_eidtion)){
            txtTitle.setSelected(new EditionReadDao(context).isSupport(data.getArticle_id()));
        }else {
            txtTitle.setSelected(new ReadDao(context).isSupport(data.getArticle_id()));
        }

        // ���·���ʱ��
        txtTime.setText(timeN);
        // ������Դ
        txtSource.setText(data.getArticle_source());
        // ����������Ŀ
        txtCommentNum.setText(data.getArticle_comment_num() + context.getResources().getString(R.string.txt_comment));
        if (!Utils.isStringEmpty(data.getReservezone3()) && data.getReservezone3().equals("true")) {
            txtOffline.setVisibility(View.VISIBLE);
        } else {
            txtOffline.setVisibility(View.GONE);
        }

        source_3line.setText(data.getArticle_source());
        comment_3line.setText(data.getArticle_comment_num() + context.getResources().getString(R.string.txt_comment));
        time_3line.setText(timeN);
        if (!Utils.isStringEmpty(data.getReservezone3()) && data.getReservezone3().equals("true")) {
            offline_3line.setVisibility(View.VISIBLE);
        } else {
            offline_3line.setVisibility(View.GONE);
        }

        setLabel(linAddTag, data);
        setLabel(linAddTag3Line, data);

        /**
         * fill�ı����
         */
        source_Fill.setText(data.getArticle_source());
        comment_Fill.setText(data.getArticle_comment_num() + context.getResources().getString(R.string.txt_comment));
        time_Fill.setText(timeN);
        if (!Utils.isStringEmpty(data.getReservezone3()) && data.getReservezone3().equals("true")) {
            offline_Fill.setVisibility(View.VISIBLE);
        } else {
            offline_Fill.setVisibility(View.GONE);
        }

        setLabel(linAddTagFill, data);

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
//            ImageView iv = new ImageView(context);
//            iv.setImageDrawable(context.getResources().getDrawable(resId[i]));
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -2);
//            lp.rightMargin = BaseActivity.getDensity_int(5);
//            linAdd.addView(iv, lp);
//
//            List<DynamicAttr> mDynamicAttr = new ArrayList<DynamicAttr>();
//            mDynamicAttr.add(new DynamicAttr(AttrFactory.BACKGROND_DRAWABLE,
//                    resId[i]));
//            ((BaseSkinFragmentActivity) context).dynamicAddView(iv,
//                    mDynamicAttr);
//			if (Utils.isStringEmpty(tagStrs[i])) {
//				continue;
//			}
//			TextView textView = new TextView(context);
//			textView.setText(tagStrs[i]);
//			textView.setTextSize(8);
//			textView.setTextColor(context.getResources().getColor(R.color.day_text_color_red));
//			textView.setBackgroundResource(R.drawable.label_bg);
//			textView.setGravity(Gravity.CENTER);
//			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -2);
//
//			lp.rightMargin = BaseActivity.getDensity_int(5);
//			lp.height = BaseActivity.getDensity_int(13);
//			linAdd.addView(textView, lp);
        }
        if (size==0){
            linAdd.setVisibility(View.GONE);
        }
    }
}
