/**
 * 
 */
package com.baiyi.jj.app.activity.main.news;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.media.Image;
import android.text.TextPaint;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.cache.Dao.EditionReadDao;
import com.baiyi.jj.app.cache.Dao.ReadDao;
import com.baiyi.jj.app.utils.TextLengthUtils;
import com.baiyi.jj.app.views.pop.DislikePop;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.BaseSkinFragmentActivity;
import com.baiyi.jj.app.application.accont.AccountManager;
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
 * �������
 * 
 * @author tangkun
 * 
 */
public class CenterImgRender extends BaseHolder /*implements GdapterTypeRender*/ {

	protected TextView txtAbstract = null;
	private TextView txtTitle = null;
	private TextView txtCommentNum = null;
	private TextView txtSource = null;
	private TextView txtOffline = null;
	private ImageView imgMore = null;
	private TextView txtTime = null;
	private TextView txtCollect = null;
	private LinearLayout linAddTag = null;

	private LinearLayout imgGruop = null;

	protected RemoteImageView imgAbtract = null;
	private MixtureTextView mAbstract = null;
	private RelativeLayout abstractGroup = null;
	private LinearLayout groupAbstract = null;
	private RemoteImageView imgFillAbstract = null;
	private TextView abstractTextFill = null;

	private boolean isReaded;

	private RemoteImageView imgCenterOne = null;

	private Context context = null;

	private NewsListEntity data = null;
	private boolean isVisibleAbstract;

	private RelativeLayout relaGroupLine = null;
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
	 * ����ƫ�ƣ����Ͼۼ��¾�
	 */
	private LinearLayout linParent = null;

	private boolean isJustTime = true;

	/**
	 * @param arg0
	 */
	public CenterImgRender(View arg0, Context context, boolean isVisibleAbstract,boolean isJustTime)
	{
		super(arg0);
		this.context = context;
		this.isVisibleAbstract = isVisibleAbstract;
		this.isJustTime = isJustTime;
		setEvents(arg0);
	}

	public void setBitmap(NewsListEntity data) {
		if (imgCenterOne.getVisibility() == View.VISIBLE) {
			imgCenterOne.loadImg(context,data.toImgList().get(0).getMedia_path());
			return;
		}
		if (imgAbtract.getVisibility() == View.VISIBLE) {
			imgAbtract.loadImg(context,data.toImgList().get(0).getMedia_path());
		}
		if (imgFillAbstract.getVisibility() == View.VISIBLE) {
			imgFillAbstract.loadImg(context,data.toImgList().get(0).getMedia_path());
		}
	}

	public void setDataNull()
	{
		if(data != null)
		{
			data = null;
		}
	}

	public void setImgNull()
	{
		if(imgCenterOne != null)
		{
			imgCenterOne.setImageBitmap(null);
		}
		if(imgAbtract != null)
		{
			imgAbtract.setImageBitmap(null);
		}
		if(imgFillAbstract != null)
		{
			imgFillAbstract.setImageBitmap(null);
		}
	}

	public void setEvents(View convertView) {
		// TODO Auto-generated method stub
		txtTitle = (TextView) convertView.findViewById(R.id.txt_newstitle2);
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
		abstractTextFill = (TextView) convertView
				.findViewById(R.id.txt_abstract_fill);

		imgCenterOne = (RemoteImageView) convertView
				.findViewById(R.id.img_one_fill);
		int imgW = Config.getInstance().getScreenWidth(context)
				- ContextUtil.dip2px(context,
						ListItemBaseNews.Center_Dip_Offset);
		int imgH = (int) (imgW * ListItemBaseNews.Center_Img_Percent);
		imgCenterOne.getLayoutParams().width = imgW;
		imgCenterOne.getLayoutParams().height = imgH;

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

		relaGroupLine = (RelativeLayout) convertView.findViewById(R.id.rela_line);
		lineTimeGruopFill = (LinearLayout) convertView
				.findViewById(R.id.title_fill_line);
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

		data = entity;
		if (data == null) {
			return;
		}

		isReaded = new ReadDao(context).isSupport(data.getArticle_id());
		setTitle(data);
		setMorePop(position);
		setSgin(data);
		if (isVisibleAbstract) {
			setAbstract(data);
		} else {
			groupAbstract.setVisibility(View.GONE);
			imgCenterOne.setVisibility(View.VISIBLE);
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
		more_Fill.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				showPop(position,more_Fill);
			}
		});
	}

	private void showPop(final int position, ImageView view){
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
		linParent.setPadding(BaseActivity.getDensity_int(NoImgRender.left_Right),FontUtil.getTopMaigrn(),
				BaseActivity.getDensity_int(NoImgRender.left_Right),FontUtil.getTopMaigrn());
		
		int size = AccountManager.getInstance().getCurrentSize();
		
		/**
		 * ��ժҪʱ��������Ϊ3dp��Ϊ����ժҪ������
		 */
		LinearLayout.LayoutParams layoutParams = (LayoutParams) relaGroupLine
				.getLayoutParams();
		if (groupAbstract.getVisibility() == View.VISIBLE && abstractGroup.getVisibility() == View.VISIBLE) {
			layoutParams.setMargins(0,BaseActivity.getDensity_int(size<2?5:6),0,0);
		}else {
			layoutParams.setMargins(0,BaseActivity.getDensity_int(size<2?4:6),0,0);
		}
		relaGroupLine.setLayoutParams(layoutParams);
		
		/**
		 *  ͼƬƫ����
		 */
		LinearLayout.LayoutParams layoutParams2 = (LayoutParams) imgCenterOne
				.getLayoutParams();
		if (groupAbstract.getVisibility() == View.VISIBLE && abstractTextFill.getVisibility() == View.VISIBLE) {
			layoutParams2.setMargins(0,BaseActivity.getDensity_int(size<2?5:6),0,0);
		}else {
			layoutParams2.setMargins(0,BaseActivity.getDensity_int(size<2?4:5),0,0);
		}
		imgCenterOne.setLayoutParams(layoutParams2);
		
//		/**
//		 *  ժҪ
//		 */
//		LinearLayout.LayoutParams layoutParams3 = (LayoutParams) abstractTextFill
//				.getLayoutParams();
//		layoutParams3.setMargins(0,BaseActivity.getDensity_int(FontUtil.getLineMaigrn4_5()),0,0);
//		abstractTextFill.setLayoutParams(layoutParams3);
		
	}

	public void setTitle(NewsListEntity data) {
		// ���±���
		float textSize = FontUtil.getNewsTitleTextSize(context);
		txtTitle.setTextSize(textSize);
		txtTitle.setTypeface(CmsApplication.getListTitleFace(context));
		TextLengthUtils.getInstance(context).setChar(txtTitle,data.getArticle_title().replace("\n", ""),2);
//		txtTitle.setSelected(isReaded);
		if (data.getType().equals(TagUtils.StorType_eidtion_top)){
			txtTitle.setSelected(false);
		} else if (data.getType().equals(TagUtils.StorType_eidtion)){
			txtTitle.setSelected(new EditionReadDao(context).isSupport(data.getArticle_id()));
		}else {
			txtTitle.setSelected(isReaded);
		}
//		if (isJustTime){
//			txtTitle.setSelected(isReaded);
//		}else {
//			txtTitle.setSelected(EditionManager.getInstance().isRead(data.getArticle_id()));
//		}
	}

	public void setSgin(NewsListEntity data) {

		// ���·���ʱ��
		String timeN = Utils.getTimeName(context, data.getArticle_pubDate(),!isJustTime);
		txtTime.setText(timeN);
		// ������Դ
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
		// �ղ�����
		txtCollect.setText(String.valueOf(data.getCollect_num()));
		// ����������Ŀ
		txtCommentNum.setText(data.getArticle_comment_num() + context.getResources().getString(R.string.txt_comment));
		setLabel(linAddTag, data);
	}

	public void setAbstract(NewsListEntity data) {
		if (BaseHolder.isVisibleTitleImg(data.getArticle_abstract())) {
			groupAbstract.setVisibility(View.GONE);
			imgCenterOne.setVisibility(View.VISIBLE);
			relaGroupLine.setVisibility(View.VISIBLE);
			lineTimeGruopFill.setVisibility(View.GONE);

			// imgAbtract.setVisibility(View.GONE);
			// // imgLeft.setVisibility(View.VISIBLE);
			// mAbstract.setVisibility(View.GONE);
			// abstractGroup.setVisibility(View.GONE);
			// displayTitle();
		} else {
			int visbileType = BaseHolder.isAbstractType(data);
			if (visbileType == BaseHolder.Visible_Type_RightImg) {
				groupAbstract.setVisibility(View.VISIBLE);
				abstractGroup.setVisibility(View.VISIBLE);
				abstractTextFill.setVisibility(View.GONE);
				imgFillAbstract.setVisibility(View.GONE);
				imgCenterOne.setVisibility(View.GONE);
				relaGroupLine.setVisibility(View.VISIBLE);
				lineTimeGruopFill.setVisibility(View.GONE);
				displayAbtractPic(data);
			} else if (visbileType == BaseHolder.Visible_Type_FillImg) {
				groupAbstract.setVisibility(View.VISIBLE);
				abstractGroup.setVisibility(View.GONE);
				abstractTextFill.setVisibility(View.VISIBLE);
				imgFillAbstract.setVisibility(View.VISIBLE);
				imgCenterOne.setVisibility(View.GONE);
//				mAbstract.setVisibility(View.GONE);
				displayAbstractToFillImg(data);

				relaGroupLine.setVisibility(View.GONE);
				lineTimeGruopFill.setVisibility(View.VISIBLE);
			} else {
				groupAbstract.setVisibility(View.GONE);
				imgCenterOne.setVisibility(View.VISIBLE);
				relaGroupLine.setVisibility(View.VISIBLE);
				lineTimeGruopFill.setVisibility(View.GONE);
			}
		}
	}

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
				R.color.day_text_color_737373));
		
	}

	private void displayAbstractToFillImg(NewsListEntity data) {
		int abtractLines = AccountManager.getInstance().getSummary_Is_Display();
		String ab = data.getArticle_abstract().replaceAll("\\r\\n", "");
		abstractTextFill.setText(Utils.ToDBC(ab));
		abstractTextFill.setEllipsize(TruncateAt.END);
		abstractTextFill.setMaxLines(TagUtils.getLine(abtractLines));
		abstractTextFill.setTextSize(FontUtil.getAbstratTextSize(context));

		/**
		 * fill�ı����
		 */
		source_Fill.setText(data.getArticle_source());
		if (!Utils.isStringEmpty(data.getReservezone3()) && data.getReservezone3().equals("true")) {
			source_Fill.setVisibility(View.VISIBLE);
		} else {
			source_Fill.setVisibility(View.GONE);
		}
		comment_Fill.setText(data.getArticle_comment_num() + context.getResources().getString(R.string.txt_comment));
		String timeN = Utils.getTimeName(context, data.getArticle_pubDate(),!isJustTime);
		time_Fill.setText(timeN);

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
