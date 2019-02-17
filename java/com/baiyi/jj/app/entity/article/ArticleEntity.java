/**
 * 
 */
package com.baiyi.jj.app.entity.article;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baiyi.core.database.AbstractBaseModel;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.Utils;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author tangkun
 *
 */
@DatabaseTable(tableName = "tb_articlerec")
public class ArticleEntity extends AbstractBaseModel{

	@DatabaseField(generatedId = true)
	private int id;
	private ArticleRecommend articleRecommend;
	@DatabaseField
	private String articleRecommendData;
	private List<ArticleChannel> ArticleChannelList;
	@DatabaseField
	private String ArticleChannelListData;
	@DatabaseField
	private String tableName;
	private List<NewsListEntity> newsList;
	@DatabaseField
	private String ArticleChannelData;
	private ArticleChannel articleChannel;
	@DatabaseField
	private String last_trend_channels;
	@DatabaseField
	private String last_new_channels;
	@DatabaseField
	private String last_uf_trend_channels;
	@DatabaseField
	private String last_uf_new_channels;
	//��һƵ��ʹ��
	@DatabaseField
	private String isRecomment;
	@DatabaseField
	private String channelId;

	private String fidStr;

	public ArticleEntity() {
	}

	public String getFidStr() {
		return fidStr;
	}

	public void setFidStr(String fidStr) {
		this.fidStr = fidStr;
	}

	public String getArticleRecommendData() {
		return articleRecommendData;
	}
	public void setArticleRecommendData(String articleRecommendData) {
		this.articleRecommendData = articleRecommendData;
	}
	public String getArticleChannelListData() {
		return ArticleChannelListData;
	}
	public void setArticleChannelListData(String articleChannelListData) {
		ArticleChannelListData = articleChannelListData;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public List<NewsListEntity> toNewsList() {
		return newsList;
	}
	public void updateNewsList(List<NewsListEntity> newsList) {
		this.newsList = newsList;
	}
	
	public ArticleRecommend toArticleRecommend()
	{
		return articleRecommend;
	}
	public void updateArticleRecommend(ArticleRecommend articleRecommend)
	{
		this.articleRecommend = articleRecommend;
	}
	public void updateArticleRecommend(String articleRecommendData)
	{
		if(Utils.isStringEmpty(articleRecommendData))
		{
			return;
		}
		try {
			this.articleRecommend = JsonParse.getArticleRecommend(new JSONObject(articleRecommendData));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public List<ArticleChannel> toArticleChannelList()
	{
		return ArticleChannelList;
	}
	public void updateArticleChannelList(List<ArticleChannel> ArticleChannelList)
	{
		this.ArticleChannelList = ArticleChannelList;
	}
	public void updateArticleChannelList(String ArticleChannelListData)
	{
		if(Utils.isStringEmpty(ArticleChannelListData))
		{
			return;
		}
//		try {
//			this.ArticleChannelList = JsonParse.getArticleChannelList(new JSONArray(ArticleChannelListData), ChannelDataUtils.getChannelType(tableName));
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
	}
	
	
	public String getArticleChannelData() {
		return ArticleChannelData;
	}
	public void setArticleChannelData(String articleChannelData) {
		ArticleChannelData = articleChannelData;
	}
	public ArticleChannel toArticleChannel()
	{
		return this.articleChannel;
	}
	public void updateArticleChannel(ArticleChannel articleChannel)
	{
		this.articleChannel = articleChannel;
	}
	public void updateArticleChannel(String articleChannelData)
	{
		if(Utils.isStringEmpty(articleChannelData))
		{
			return;
		}
		try {
			this.articleChannel = JsonParse.getArticleChannel(new JSONObject(articleChannelData));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String getLast_trend_channels() {
		return last_trend_channels;
	}
	public void setLast_trend_channels(String last_trend_channels) {
		this.last_trend_channels = last_trend_channels;
	}
	public String getLast_new_channels() {
		return last_new_channels;
	}
	public void setLast_new_channels(String last_new_channels) {
		this.last_new_channels = last_new_channels;
	}
	public String getLast_uf_trend_channels() {
		return last_uf_trend_channels;
	}
	public void setLast_uf_trend_channels(String last_uf_trend_channels) {
		this.last_uf_trend_channels = last_uf_trend_channels;
	}
	public String getLast_uf_new_channels() {
		return last_uf_new_channels;
	}
	public void setLast_uf_new_channels(String last_uf_new_channels) {
		this.last_uf_new_channels = last_uf_new_channels;
	}
	public String getIsRecomment() {
		return isRecomment;
	}
	public void setIsRecomment(String isRecomment) {
		this.isRecomment = isRecomment;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

}
