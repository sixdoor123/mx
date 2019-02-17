package com.baiyi.jj.app.utils;


public class ArticleHistoryUtils {

	/**
	 * ��������
	 */
	public static final String ArticleType_News = "article_forapp"; // ��Ѷ
	public static final String ArticleType_ZiXun = "explore_forapp"; // ֪ʶ
	public static final String ArticleType_Video = "video_forapp"; // Ȥζ
	public static final String ArticleType_Interest = "interest_forapp"; // Ȥζ
	public static final String ArticleType_Photo = "photo_forapp"; // �Ӿ�
	public static final String ArticleType_discover = "discover_forapp"; // ����
	public static final String ArticleType_Comment = "comment_forapp"; // ����

	public static final String Tablename_News = "article";
	public static final String Tablename_Read = "explore";
	public static final String Tablename_Video = "video";
	public static final String Tablename_Interest = "interest";
	public static final String Tablename_Photo = "photo";
	public static final String Tablename_Discover = "discover";
	public static final String Tablename_Comment = "comment";

	public static String getTableName(String articletype) {
		if (articletype.equals(ArticleType_News)) {
			return Tablename_News;
		} else if (articletype.equals(ArticleType_ZiXun)) {
			return Tablename_Read;
		} else if (articletype.equals(ArticleType_Interest)) {
			return Tablename_Interest;
		} else if (articletype.equals(ArticleType_Video)) {
			return Tablename_Video;
		} else if (articletype.equals(ArticleType_Photo)) {
			return Tablename_Photo;
		} else if (articletype.equals(ArticleType_Comment)) {
			return Tablename_Comment;
		}
		return Tablename_News;
	}


	public static String getArticleType(String tablename) {
		if (tablename.equals(Tablename_News)) {
			return ArticleType_News;
		} else if (tablename.equals(Tablename_Read)) {
			return ArticleType_ZiXun;
		} else if (tablename.equals(Tablename_Interest)) {
			return ArticleType_Interest;
		} else if (tablename.equals(Tablename_Video)) {
			return ArticleType_Video;
		} else if (tablename.equals(Tablename_Photo)) {
			return ArticleType_Photo;
		} else if (tablename.equals(Tablename_Discover)) {
			return ArticleType_discover;
		}
		return ArticleType_News;
	}

}
