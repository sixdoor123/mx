package com.baiyi.jj.app.entity;

import com.baiyi.core.database.AbstractBaseModel;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.Utils;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

@DatabaseTable(tableName = "tb_newslist")
public class NewsListEntity extends AbstractBaseModel{


	private String small_url;
	private int interest_opposition_num;
	private int field3;

	private int play_num;
	//big small
	private String big_url;
	private int seq;
	private String my_coll_id;
	private List<VideoMedieInfo> medieInfo;
	private String videoStr;


	@DatabaseField
	private String reservezone1; // 是否为综合列表 true false truevideo//视频的综合列表
	@DatabaseField
	private String reservezone2; // 国家
	@DatabaseField
	private String reservezone3; //isoffline
	@DatabaseField
	private String reservezone4; //videoid
	@DatabaseField
	private String reservezone5; // areaName
	@DatabaseField
	private String reservezone6;

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String type;
	@DatabaseField
	private String hotword;
	@DatabaseField
	private String imgData;
	@DatabaseField
	private String tagsData;
	private List<NewsImgEntity> imgList;
	private List<Integer> tags;
	@DatabaseField
	private String template;
	// 通过downloadTime查数据库
	@DatabaseField
	private long downLoadTime;

	// 文章信息
	@DatabaseField
	private String article_title;
	@DatabaseField
	private String article_id;
	@DatabaseField
	private int article_comment_num;
	@DatabaseField
	private String article_pubDate;
	@DatabaseField
	private int interest_agree_num;
	@DatabaseField
	private String articleType;
	@DatabaseField
	private String article_source;
	@DatabaseField
	private boolean is_Original;
	@DatabaseField
	private String Category; //原来的tablename
	@DatabaseField
	private int collect_num;
	@DatabaseField
	private String article_abstract;
	@DatabaseField
	private String tablename;
	@DatabaseField
	private String channel_id;
	@DatabaseField
	private int channel_seq;
	//  原文链接 url
	@DatabaseField
	private String PageUrl;

	// 其他
	@DatabaseField
	private int User_Id;
	@DatabaseField
	private String User_Name;
	@DatabaseField
	private String User_Avatar;
	@DatabaseField
	private int Status;
	@DatabaseField
	private String Language;
	@DatabaseField
	private String Country;

	public String getPageUrl() {
		return PageUrl;
	}

	public void setPageUrl(String pageUrl) {
		PageUrl = pageUrl;
	}

	private boolean article_is_foucs;
	private boolean field1;  // �Ƿ���
	private boolean field4;  // new��ǩ
	private boolean article_is_recommend;

	public String getHotword() {
		return hotword;
	}

	public void setHotword(String hotword) {
		this.hotword = hotword;
	}

	public String getReservezone1() {
		return reservezone1;
	}

	public void setReservezone1(String reservezone1) {
		this.reservezone1 = reservezone1;
	}

	public String getReservezone2() {
		return reservezone2;
	}

	public void setReservezone2(String reservezone2) {
		this.reservezone2 = reservezone2;
	}

	public String getReservezone3() {
		return reservezone3;
	}

	public void setReservezone3(String reservezone3) {
		this.reservezone3 = reservezone3;
	}

	public String getReservezone4() {
		return reservezone4;
	}

	public void setReservezone4(String reservezone4) {
		this.reservezone4 = reservezone4;
	}

	public String getReservezone5() {
		return reservezone5;
	}

	public void setReservezone5(String reservezone5) {
		this.reservezone5 = reservezone5;
	}

	public String getReservezone6() {
		return reservezone6;
	}

	public void setReservezone6(String reservezone6) {
		this.reservezone6 = reservezone6;
	}

	public int getSqlId() {
		return id;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public int getUser_Id() {
		return User_Id;
	}

	public void setUser_Id(int user_Id) {
		User_Id = user_Id;
	}

	public String getUser_Name() {
		return User_Name;
	}

	public void setUser_Name(String user_Name) {
		User_Name = user_Name;
	}

	public String getUser_Avatar() {
		return User_Avatar;
	}

	public void setUser_Avatar(String user_Avatar) {
		User_Avatar = user_Avatar;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public String getLanguage() {
		return Language;
	}

	public void setLanguage(String language) {
		Language = language;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public boolean is_Original() {
		return is_Original;
	}

	public void setIs_Original(boolean is_Original) {
		this.is_Original = is_Original;
	}

	public int getPlay_num() {
		return play_num;
	}

	public void setPlay_num(int play_num) {
		this.play_num = play_num;
	}

	public String getTablename() {
		return tablename;
	}

	public String getVideoStr() {
		return videoStr;
	}

	public void setVideoStr(String videoStr) {
		this.videoStr = videoStr;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public boolean isField4() {
		return field4;
	}

	public void setField4(boolean field4) {
		this.field4 = field4;
	}

	public int getField3() {
		return field3;
	}

	public void setField3(int field3) {
		this.field3 = field3;
	}

	public String getArticle_title() {
		return article_title;
	}
	
	public void setArticle_title(String article_title) {
		this.article_title = article_title;
	}
	public String getArticle_source() {
		return article_source;
	}
	public void setArticle_source(String article_source) {
		this.article_source = article_source;
	}
	public int getChannel_seq() {
		return channel_seq;
	}
	public void setChannel_seq(int channel_seq) {
		this.channel_seq = channel_seq;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
//	public List<NewsImgEntity> getImgList() {
//		return imgList;
//	}
//	public void setImgList(List<NewsImgEntity> imgList) {
//		this.imgList = imgList;
//	}
	public boolean isArticle_is_foucs() {
		return article_is_foucs;
	}
	public void setArticle_is_foucs(boolean article_is_foucs) {
		this.article_is_foucs = article_is_foucs;
	}
	public boolean isField1() {
		return field1;
	}
	public void setField1(boolean field1) {
		this.field1 = field1;
	}
	public boolean isArticle_is_recommend() {
		return article_is_recommend;
	}
	public void setArticle_is_recommend(boolean article_is_recommend) {
		this.article_is_recommend = article_is_recommend;
	}
	public String getArticle_pubDate() {
		return article_pubDate;
	}
	public void setArticle_pubDate(String article_pubDate) {
		this.article_pubDate = article_pubDate;
	}
	public int getArticle_comment_num() {
		return article_comment_num;
	}
	public void setArticle_comment_num(int article_comment_num) {
		this.article_comment_num = article_comment_num;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getArticle_abstract() {
		return article_abstract;
	}
	public void setArticle_abstract(String article_abstract) {
		this.article_abstract = article_abstract;
	}
	public int getCollect_num() {
		return collect_num;
	}
	public void setCollect_num(int collect_num) {
		this.collect_num = collect_num;
	}
	public String getArticle_id() {
		return article_id;
	}
	public void setArticle_id(String article_id) {
		this.article_id = article_id;
	}
	public String getArticleType() {
		return articleType;
	}
	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}
	public int getInterest_opposition_num() {
		return interest_opposition_num;
	}
	public void setInterest_opposition_num(int interest_opposition_num) {
		this.interest_opposition_num = interest_opposition_num;
	}
	public String getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}
	public int getInterest_agree_num() {
		return interest_agree_num;
	}
	public void setInterest_agree_num(int interest_agree_num) {
		this.interest_agree_num = interest_agree_num;
	}
//	public List<Integer> getTags() {
//		return tags;
//	}
//	public void setTags(List<Integer> tags) {
//		this.tags = tags;
//	}

	public String getSmall_url() {
		return small_url;
	}

	public void setSmall_url(String small_url) {
		this.small_url = small_url;
	}

	public String getBig_url() {
		return big_url;
	}

	public void setBig_url(String big_url) {
		this.big_url = big_url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMy_coll_id() {
		return my_coll_id;
	}

	public void setMy_coll_id(String my_coll_id) {
		this.my_coll_id = my_coll_id;
	}

	public String getImgData() {
		return imgData;
	}

	public void setImgData(String imgData) {
		this.imgData = imgData;
	}

	public String getTagsData() {
		return tagsData;
	}

	public void setTagsData(String tagsData) {
		this.tagsData = tagsData;
	}

	public long getDownLoadTime() {
		return downLoadTime;
	}

	public void setDownLoadTime(long downLoadTime) {
		this.downLoadTime = downLoadTime;
	}
	
	public List<Integer> toTags()
	{
		return tags;
	}
	
	public void updateTags(List<Integer> tags)
	{
		this.tags = tags;
	}
	
	public void updateTags(String tagsData)
	{
		if(Utils.isStringEmpty(tagsData))
		{
			return;
		}
		tags = Utils.getTagsList(tagsData);
	}

	public List<NewsImgEntity> toImgList()
	{
		return imgList;
	}

	public void updateImageList(List<NewsImgEntity> imgList)
	{
		this.imgList = imgList;
	}

	public void updateImageList(String imgData)
	{
		if(Utils.isStringEmpty(imgData))
		{
			return;
		}
//		JSONArray imgArray;
//		try {
//			imgArray = new JSONArray(imgData);
//			this.imgList = JsonParse.getImgsList(imgArray);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
		JSONObject imgArray;
		try {
			imgArray = new JSONObject(imgData);
			this.imgList = JsonParse.getImageInfo(imgArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}


	}
	
	public List<VideoMedieInfo> toVideoList()
	{
		return medieInfo;
	}
	
	public void updateVideoList(List<VideoMedieInfo> medieInfo)
	{
		this.medieInfo = medieInfo;
	}
	
	public void updateVideoList(String imgData)
	{
		if(Utils.isStringEmpty(imgData))
		{
			return;
		}
		JSONArray imgArray;
		try {
			imgArray = new JSONArray(imgData);
			this.medieInfo = JsonParse.getMedieInfo(imgArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
