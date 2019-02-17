/**
 * 
 */
package com.baiyi.jj.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.EditionAct;
import com.baiyi.jj.app.activity.NewsDetailAct;
import com.baiyi.jj.app.activity.NewsLocalAct;
import com.baiyi.jj.app.activity.NewsVideoAct;
import com.baiyi.jj.app.activity.main.HomeTabAct;
import com.baiyi.jj.app.activity.user.about.MemberWebViewActivity;
import com.baiyi.jj.app.activity.user.ShareActivity;
import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.cache.Dao.EditionReadDao;
import com.baiyi.jj.app.cache.bean.EditionReadBean;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.listitem.news.ListItemBaseNews;

/**
 * @author tangkun
 * 
 */
public class IntentClassChangeUtils {

	/**
	 * ��Ϣ
	 * 
	 * @param context
	 */
	public static void startMyNews(Context context) {
//		Intent intent = new Intent(context, MyMsgListActivity.class);
//		context.startActivity(intent);
	}

	/**
	 * �ص���ҳ
	 * 
	 * @param context
	 */
	public static void startHome(Context context, int tabId) {
		Intent intent = new Intent(context, HomeTabAct.class);
		intent.putExtra(Define.TabId, tabId);
		context.startActivity(intent);
	}

	public static void startShare(Context context, int Type, String title,
			String content, String url, String imgUrl) {
		Intent intent = new Intent(context, ShareActivity.class);
		intent.putExtra(Define.ShartType, Type);
		intent.putExtra(Define.ShareTitle, title);
		intent.putExtra(Define.ShareContent, content);
		intent.putExtra(Define.ShareUrl, url);
		intent.putExtra(Define.ShareImageUrl, imgUrl);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		context.startActivity(intent);
	}

	public static void startNewsDetail(Context context, NewsListEntity entity,int position,int currentTime,boolean isCollect) {
		if (entity == null){
			return;
		}
		if (!Utils.isStringEmpty(entity.getType()) && entity.getType().equals(TagUtils.StorType_video)){
			intentVideo(context,entity,currentTime,position,isCollect);
			return;
		}

		if (!Utils.isStringEmpty(entity.getType()) &&( entity.getType().equals(TagUtils.StorType_eidtion_top) || entity.getType().equals(TagUtils.StorType_eidtion))) {
			intentToEdition(context,entity,position);
			return;
		}

		intentNormal(context,entity,position,isCollect);
	}


	private static void intentVideo(Context context, NewsListEntity entity, int currentTime, int postion,boolean isCollect){
		Intent intent = new Intent(context, NewsVideoAct.class);
		String img = "";
		if (!Utils.isStringEmpty(entity.toImgList())) {
			img = entity.toImgList().get(0).getMedia_path();
		}
		intent.putExtra(Define.VideoId,entity.getReservezone4());
		intent.putExtra(Define.VideoStartPoint,currentTime);
		intent.putExtra(Define.ArticleSource,entity.getArticle_source());
		intent.putExtra(Define.ArticleTime,entity.getArticle_pubDate());
		intent.putExtra(Define.Detail_Result, false);

		intent.putExtra(Define.IsGIf, true);
		intent.putExtra(Define.ArticleImage, img);
		intent.putExtra(Define.ArticleId, entity.getArticle_id());
		intent.putExtra(Define.ArticleTitle, entity.getArticle_title());
		intent.putExtra(Define.CommentNum, entity.getArticle_comment_num());
		intent.putExtra(Define.ITEM_POSTION, (postion - 1));
		intent.putExtra(Define.ArticleUrl, entity.getPageUrl());
		if (isCollect){
			intent.putExtra(MemberConfig.My_Collect_Entry,MemberConfig.My_Collect);
		}
		((BaseActivity)context).startActivityForResult(intent, 1);
	}

	private static void intentToEdition(Context context,NewsListEntity entity,int position){
		Intent intent = new Intent(context, EditionAct.class);
		intent.putExtra(Define.ArticleId, entity.getArticle_id());
		intent.putExtra(Define.IsPoint, entity.getType().equals(TagUtils.StorType_eidtion_top));
		intent.putExtra(Define.ITEM_POSTION, (position - 1));
		if (entity.getType().equals(TagUtils.StorType_eidtion)) {
			new EditionReadDao(context).add(new EditionReadBean(entity.getArticle_id()));
		}
		((BaseActivity)context).startActivityForResult(intent, 1);
	}

	private static void intentNormal(Context context,NewsListEntity entity,int postion,boolean isCollect){
		Intent intent = new Intent(context, NewsLocalAct.class);
		String img = "";
		if (!Utils.isStringEmpty(entity.toImgList())) {
			img = entity.toImgList().get(0).getMedia_path();
			//判断是否是趣图频道，如果是则不加载相关阅读
			if (entity.toImgList().get(0).getPicture_type() == ListItemBaseNews.Item_Type_Gif) {
				intent.putExtra(Define.IsGIf, true);
			}
		}
		intent.putExtra(Define.ArticleImage, img);
		intent.putExtra(Define.ArticleId, entity.getArticle_id());
		intent.putExtra(Define.ArticleTitle, entity.getArticle_title());
		intent.putExtra(Define.CommentNum, entity.getArticle_comment_num());
		intent.putExtra(Define.Detail_Result, postion == -1 ? false : true);
		intent.putExtra(Define.ITEM_POSTION, (postion - 1));
		intent.putExtra(Define.ArticleUrl, entity.getPageUrl());
		if (isCollect){
			intent.putExtra(MemberConfig.My_Collect_Entry,MemberConfig.My_Collect);
		}
		((BaseActivity)context).startActivityForResult(intent, 1);
	}



	public static void startMemberWeb(Context context, String Url, String Name,
			boolean isMarket) {
		Intent intent = new Intent(context, MemberWebViewActivity.class);
		intent.putExtra(Define.MemberURL, Url);
		intent.putExtra(Define.MemberNAME, Name);
		intent.putExtra(Define.IsMarket, isMarket);
		context.startActivity(intent);
	}

}
