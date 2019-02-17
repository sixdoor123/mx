/**
 *
 */
package com.baiyi.jj.app.activity.main.news;

import android.content.Context;
import android.nfc.Tag;
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

import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.BaseSkinFragmentActivity;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.cache.Dao.EditionReadDao;
import com.baiyi.jj.app.cache.Dao.ReadDao;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.listitem.news.ListItemBaseNews;
import com.baiyi.jj.app.theme.FontUtil;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.TagUtils;
import com.baiyi.jj.app.utils.TextLengthUtils;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.pop.DislikePop;
import com.baiyi.jj.skin.entity.AttrFactory;
import com.baiyi.jj.skin.entity.DynamicAttr;
import com.turbo.turbo.mexico.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangkun
 *
 */
public class NoImgRender extends BaseHolder/* implements GdapterTypeRender */{

	private TextView txtOffline = null;
	protected TextView txtAbstract = null;
	private TextView txtTitle = null;
	private TextView txtCommentNum = null;
	private TextView txtSource = null;
	private ImageView imgMore = null;
	private TextView txtTime = null;
	private TextView txtCollect = null;
	private LinearLayout linAddTag = null;

	private RelativeLayout linTagLine = null;

	private Context context;
	private boolean isReaded;
	private NewsListEntity data = null;

	private boolean isVisibleAbstract;

	private LinearLayout line3TimeGruop = null;

	private LinearLayout linParent = null;

	public static final int left_Right = 15;

	private boolean isJustTime = true;

	public NoImgRender(View arg0, Context context,
					   boolean isVisibleAbstract,boolean isJustTime) {
		super(arg0);
		this.context = context;
		this.isVisibleAbstract = isVisibleAbstract;
		this.isJustTime = isJustTime;
		setEvents(arg0);
	}

	public void setEvents(View convertView) {
		// TODO Auto-generated method stub
		txtTitle = (TextView) convertView.findViewById(R.id.txt_newstitle);
		txtCommentNum = (TextView) convertView
				.findViewById(R.id.txt_comment1_3line);
		txtSource = (TextView) convertView.findViewById(R.id.txt_source_3line);
		imgMore = (ImageView) convertView.findViewById(R.id.news_more_3line);
		txtTime = (TextView) convertView.findViewById(R.id.txt_time_3line);
		txtOffline = (TextView) convertView.findViewById(R.id.txt_offline3);
		txtCollect = (TextView) convertView
				.findViewById(R.id.txt_collect1_3line);
		linAddTag = (LinearLayout) convertView
				.findViewById(R.id.lin_addlabel_3line);
		linTagLine = (RelativeLayout) convertView
				.findViewById(R.id.lin_tagline);
		txtAbstract = (TextView) convertView
				.findViewById(R.id.txt_abstract_nopic);

		line3TimeGruop = (LinearLayout) convertView
				.findViewById(R.id.title_3line);

		linParent = (LinearLayout) convertView.findViewById(R.id.lin_parent);
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

	/**
	 * ���ñ���о���
	 */
	private void setLineRowSpace() {
		// �����
		LinearLayout.LayoutParams layoutParams = (LayoutParams) linTagLine
				.getLayoutParams();
		if (txtAbstract.getVisibility() == View.VISIBLE) {
			layoutParams.setMargins(0,
					BaseActivity.getDensity_int(FontUtil.getLineMaigrn4_5()), 0, 0);
		}else {
			layoutParams.setMargins(0,
					BaseActivity.getDensity_int(FontUtil.getTitleMaigrn2_3()), 0, 0);
		}
		linTagLine.setLayoutParams(layoutParams);

		// ժҪ
		LinearLayout.LayoutParams layoutParams2 = (LayoutParams) txtAbstract
				.getLayoutParams();
		layoutParams2.setMargins(0,
				BaseActivity.getDensity_int(FontUtil.getLineMaigrn4_5()), 0, 0);
		txtAbstract.setLayoutParams(layoutParams2);

		// ���Ͼ�s
		linParent.setPadding(BaseActivity.getDensity_int(left_Right),
				FontUtil.getTopMaigrn(),BaseActivity.getDensity_int(left_Right),
				FontUtil.getTopMaigrn());
	}

	public void setDataNull()
	{
		if(data != null)
		{
			data = null;
		}
	}

	public void setDatas(int position, NewsListEntity entity) {

		setLineRowSpace();
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

		txtCollect.setText(String.valueOf(data.getCollect_num()));

		txtCommentNum.setText(data.getArticle_comment_num() + context.getResources().getString(R.string.txt_comment));
		setLabel(linAddTag, data);

		if (isVisibleAbstract) {
			int abtractLines = AccountManager.getInstance().getSummary_Is_Display();
			if (abtractLines == 0
					|| Utils.isStringEmpty(data.getArticle_abstract())) {
				txtAbstract.setVisibility(View.GONE);
			} else {
				if (data.getArticle_abstract().length() < BaseHolder.AbstractMaxSize) {
					txtAbstract.setVisibility(View.GONE);
				} else {
					txtAbstract.setVisibility(View.VISIBLE);
					txtAbstract.setText(data.getArticle_abstract());
					txtAbstract.setEllipsize(TruncateAt.END);
					txtAbstract.setMaxLines(TagUtils.getLine(abtractLines));
					txtAbstract.setTextSize(FontUtil
							.getAbstratTextSize(context));
				}
			}
		} else {
			txtAbstract.setVisibility(View.GONE);
		}

		if (!Utils.isStringEmpty(data.getChannel_id())) {
			if (data.getChannel_id().equals(ListItemBaseNews.ChannelId_DZ)
					|| data.getChannel_id().equals(
					ListItemBaseNews.ChannelId_NH)) {
				line3TimeGruop.setVisibility(View.GONE);
			} else {
				line3TimeGruop.setVisibility(View.VISIBLE);
			}
		}

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
