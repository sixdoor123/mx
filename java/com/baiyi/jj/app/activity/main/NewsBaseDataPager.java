/**
 * 
 */
package com.baiyi.jj.app.activity.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.entity.article.ArticleChannel;
import com.baiyi.jj.app.entity.article.ArticleEntity;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangkun
 *
 */
public abstract class NewsBaseDataPager extends NewsBasePager{

	private String dateType = "week";
	
	/**
	 * @param context
	 * @param themeChangeCallBack
	 * @param tableName
	 * @param TabId
	 * @param imgArrow
	 * @param isDisplayAbstract
	 * 
	 */
	public NewsBaseDataPager(Context context,
			ThemeChangeCallBack themeChangeCallBack,
			String tableName, int TabId, ImageView imgArrow,
			boolean isDisplayAbstract) {
		super(context, themeChangeCallBack, tableName, TabId, imgArrow,
				isDisplayAbstract);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public String getDataUrl(boolean isRecomment, boolean isDuanzi, String mid, int page,
			String postChannel) {
		// TODO Auto-generated method stub
		String tableName = getTableName();
		
		
//		if (tableName.equals(ArticleHistoryUtils.Tablename_Photo) ) {
//			if (isRecomment) {
//				return NetUtils.getDiscoverList( mid, page);
//			} else {
//				return NetUtils.getDiscoverToChannelId(postChannel, page);
//			}
//
//		}
		
//		if (tableName.equals(ArticleHistoryUtils.Tablename_Video) ) {
//			if (isRecomment) {
//				return NetUtils.getVideoList( mid, page);
//			} else {
//				return NetUtils.getVideoToChannelList(postChannel, page);
//			}
//		}
		
//		if (tableName.equals(ArticleHistoryUtils.Tablename_Photo_SheY) ||
//				tableName.equals(ArticleHistoryUtils.Tablename_Photo_ShiJ)) {
//			tableName = ArticleHistoryUtils.Tablename_Photo;
//		}
		if(isRecomment)
		{
			//Log.e("home","---"+NetUtils.getHomeNewsList(tableName));
			return NetUtils.getHomeNewsList(tableName);
			
		}else
		{
//			if (isDuanzi) {
//				return NetUtils.getInterestByDate(ListItemBaseNews.ChannelId_DZ, getDateType(), page, 10);
//			}else {
				return NetUtils.getNewsToChannelId(tableName);
//			}
		}
		
	}
	
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	public String getDateType() {
		return dateType;
	}

	@Override
	public ArticleEntity getParseData(JSONArray array, boolean isRecomment, boolean isDuanzi, boolean isCache) {
		// TODO Auto-generated method stub
		
		if(isRecomment)
		{
			return JsonParse.getArticleEntity2(array, isCache,false,true);
			
		}else
		{
//			return JsonParse.getArticleChannelEntity(array, isCache, getTableName(), isDuanzi, getCurrentChannel());
//			return JsonParse.getNewsListToChannel(array, isCache);
			return JsonParse.getArticleChannel(array, isCache, getCurrentChannel(),null);
		}
	}
	
	@Override
	public String getPostData(boolean isRecomment, boolean isDuanzi, String mid, ArticleEntity articleEntity
			, List<ArticleChannel> aChannelList, List<ChannelItem> userChannelList, String channelId,String fidStr) {
		// TODO Auto-generated method stub
		if(isRecomment)
		{
//			return getRecommentPostData(mid, articleEntity, aChannelList, userChannelList);
			return getMixPostData(userChannelList,fidStr);
		}else
		{
//			return getChannelPostData(mid, articleEntity, channelId);
			return getUnMixPostData(channelId,fidStr);
		}
	}

	private String getMixPostData(List<ChannelItem> list,String fid){
		JSONObject o = new JSONObject();
		try {
			o.put("channel_ids", getChannelList(list));
			o.put("fid", fid);
			o.put("mixed_channel", true);
		}catch (Exception e){

		}
		TLog.e("mixpost",o.toString());
		return o.toString();
	}

	private String getUnMixPostData(String channelid,String fid){
		JSONObject o = new JSONObject();
		if (Utils.isStringEmpty(channelid)){
			return o.toString();
		}
		if (fid == null){
			TLog.e("abc","null----------");
			fid = "";
		}
		try {
			o.put("channel_ids", new JSONArray().put(Integer.parseInt(channelid)));
			o.put("fid", fid);
			o.put("mixed_channel", false);
		}catch (Exception e){

		}
		TLog.e("unmixpost",o.toString());
		return o.toString();
	}

	private JSONArray getChannelList(List<ChannelItem> channelItems){
		JSONArray array = new JSONArray();
		if(Utils.isStringEmpty(channelItems))
		{
			return array;
		}
		for(int i = 0; i < channelItems.size(); i++)
		{
			array.put(Integer.parseInt(channelItems.get(i).getCid()));
		}
		return array;
	}
	
	/**
	 * ��һƵ�� postData
	 * @param mid
	 * @param articleEntity
	 * @param channelId
	 * @return
	 */
	public String getChannelPostData(String mid, ArticleEntity articleEntity, String channelId)
	{
		JSONObject o = new JSONObject();
		try {
			o.put("user_id", mid);
			o.put("module", getModule());
			o.put("channel_id", Utils.isStringEmpty(channelId) ? 0 : Integer.parseInt(channelId));
			int last_selection_version = 0;
			int last_selection_index = 0;
			int last_new_version = 0;
			int last_new_index = 0;
			if(articleEntity != null && articleEntity.toArticleChannel() != null)
			{
//				last_selection_version = Utils.isStringEmpty(articleEntity.toArticleChannel().getTrend_version()) ? 0 : Integer.parseInt(articleEntity.toArticleChannel().getTrend_version());
				last_selection_version = articleEntity.toArticleChannel().getTrend_version();
				 last_selection_index = articleEntity.toArticleChannel().getTrend_index();
				last_new_version = articleEntity.toArticleChannel().getNew_version();
				last_new_index = articleEntity.toArticleChannel().getNew_index();
			}
			o.put("last_selection_version", last_selection_version);
			o.put("last_selection_index", last_selection_index);
			o.put("last_new_version", last_new_version);
			o.put("last_new_index", last_new_index);
			o.put("size", 10);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o.toString();
	}
	
	/**
	 * �ۺ�Ƶ�� postData
	 * @param mid
	 * @param articleEntity
	 * @param aChannelList
	 * @param userChannelList
	 * @return
	 */
	public String getRecommentPostData(String mid, ArticleEntity articleEntity, List<ArticleChannel> aChannelList, List<ChannelItem> userChannelList)
	{
		
		JSONObject o = new JSONObject();
		try {

			o.put("user_id", mid);
			o.put("module", getModule());
			int versition = 0;
			int index = 0;
//			List<ArticleChannel> articleChannelList = null;
			if(articleEntity != null && articleEntity.toArticleRecommend() != null)
			{
				versition = articleEntity.toArticleRecommend().getVersion();
				index = articleEntity.toArticleRecommend().getIndex();
			}
//			if(!Utils.isStringEmpty(aChannelList))
//			{
//				articleChannelList = aChannelList;
//			}
//			o.put("last_selection_version", Utils.isStringEmpty(versition) ? 0 : Integer.parseInt(versition));

			o.put("last_selection_version", versition);
			o.put("last_selection_index", index);
			o.put("channels", getChannelsArray(aChannelList, userChannelList));
			
			String last_trend_channels = null;
			String last_new_channels = null;
			String last_uf_trend_channels = null;
			String last_uf_new_channels = null;
			if(articleEntity != null)
			{
				last_trend_channels = articleEntity.getLast_trend_channels();
				last_new_channels = articleEntity.getLast_new_channels();
				last_uf_trend_channels = articleEntity.getLast_uf_trend_channels();
				last_uf_new_channels = articleEntity.getLast_uf_new_channels();
			}
			o.put("last_trend_channels", getLast_trend_channels(Utils.getTagsList(last_trend_channels)));
			o.put("last_new_channels", getLast_trend_channels(Utils.getTagsList(last_new_channels)));
			o.put("last_uf_trend_channels", getLast_trend_channels(Utils.getTagsList(last_uf_trend_channels)));
			o.put("last_uf_new_channels", getLast_trend_channels(Utils.getTagsList(last_uf_new_channels)));
			o.put("size", 10);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Log.e("home__json",o.toString());
		return o.toString();
	}
	
	public JSONArray getLast_trend_channels(List<Integer> last_trend_channels)
	{
		JSONArray array = new JSONArray();
		if(Utils.isStringEmpty(last_trend_channels))
		{
			return array;
		}
		for(int i = 0; i < last_trend_channels.size(); i++)
		{
			array.put(last_trend_channels.get(i));
		}
		return array;
	}
	
	public JSONArray getChannelsArray(List<ArticleChannel> ArticleChannelList, List<ChannelItem> userChannelList) throws JSONException
	{
		JSONArray array = new JSONArray();
		if(Utils.isStringEmpty(ArticleChannelList))
		{
			return array;
		}
		for(ArticleChannel channel : ArticleChannelList)
		{
			JSONObject o = new JSONObject();
			o.put("channel_id", channel.getChannel_id());
			o.put("last_trend_version", channel.getTrend_version());
			o.put("last_trend_index", channel.getTrend_index());
			o.put("last_new_version", channel.getNew_version());
			o.put("last_new_index", channel.getNew_index());
			array.put(o);
		}
		return array;
	}

}
