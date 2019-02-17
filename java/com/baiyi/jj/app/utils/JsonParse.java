package com.baiyi.jj.app.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baiyi.jj.app.adapter.NewsLocalAdapter;
import com.baiyi.jj.app.entity.localnews.AdsEntity;
import com.baiyi.jj.app.entity.localnews.LocalImageEntity;
import com.baiyi.jj.app.entity.localnews.LocalNewsBean;
import com.baiyi.jj.app.entity.CreditsEntity;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.entity.localnews.LocalInfoEntity;
import com.baiyi.jj.app.entity.NewsDetailCommentEntity;
import com.baiyi.jj.app.entity.NewsImgEntity;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.entity.VideoMedieInfo;
import com.baiyi.jj.app.entity.WithdrawEntity;
import com.baiyi.jj.app.entity.article.ArticleChannel;
import com.baiyi.jj.app.entity.article.ArticleEntity;
import com.baiyi.jj.app.entity.article.ArticleRecommend;
import com.baiyi.jj.app.entity.localnews.LocalVideoEntity;
import com.baiyi.jj.app.listitem.news.ListItemBaseNews;
import com.baiyi.jj.app.manager.CountryMannager;
import com.baiyi.jj.app.manager.RandomManager;

public class JsonParse extends JsonParseBase {

    public static List<List<ChannelItem>> getChannelList2(JSONArray array,
                                                          String channelType) {
        List<List<ChannelItem>> dataList = new ArrayList<List<ChannelItem>>();
        List<ChannelItem> defalutList = new ArrayList<ChannelItem>();
        List<ChannelItem> otherList = new ArrayList<ChannelItem>();
        try {

            JSONObject root = array.getJSONObject(0);

            JSONArray resultArray = getDataArray(root);

            for (int i = 0; i < resultArray.length(); i++) {
                ChannelItem item = new ChannelItem();
                JSONObject o = resultArray.getJSONObject(i);

                item.setChannel_name(getStringNodeValue(o, "channel_name"));
                item.setCid(String.valueOf(getIntNodeValue(o, "channel_id")));
                item.setIs_default(String.valueOf(getBooleanNodeValue(o, "is_subscribed")));
                item.setConvert_img(getStringNodeValue(o, "cover_img"));
                item.setChannel_index(getIntNodeValue(o, "order_index"));
                item.setChannel_type(channelType);
                if (getBooleanNodeValue(o, "is_subscribed")) {
                    defalutList.add(item);
                } else {
                    otherList.add(item);
                }
            }
            dataList.add(defalutList);
            dataList.add(otherList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;

    }


    public static List<List<ChannelItem>> getChannelList(JSONArray array,
                                                         String channelType, String mid) {
        List<List<ChannelItem>> dataList = new ArrayList<List<ChannelItem>>();
        List<ChannelItem> defalutList = new ArrayList<ChannelItem>();
        List<ChannelItem> otherList = new ArrayList<ChannelItem>();
        try {

            JSONObject root = array.getJSONObject(0);

            JSONArray resultArray = getDataArray(root);

            for (int i = 0; i < resultArray.length(); i++) {
                ChannelItem item = new ChannelItem();
                JSONObject o = resultArray.getJSONObject(i);

                item.setChannel_name(getStringNodeValue(o, "channel_name"));
                item.setCid(String.valueOf(getIntNodeValue(o, "channel_id")));
                item.setIs_default(String.valueOf(getBooleanNodeValue(o, "is_subscribed")));
                item.setConvert_img(getStringNodeValue(o, "cover_img"));
                item.setChannel_index(getIntNodeValue(o, "order_index"));
                item.setChannel_type(channelType);
                item.setMid(mid);
                if (getBooleanNodeValue(o, "is_subscribed")) {
                    defalutList.add(item);
                } else {
                    otherList.add(item);
                }
            }
            dataList.add(defalutList);
            dataList.add(otherList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;

    }

    /**
     * ���������б�
     *
     * @param data
     * @return
     */

    public static ArrayList<NewsListEntity> getSearchDetailList(JSONArray data) {

        ArrayList<NewsListEntity> dataList = new ArrayList<NewsListEntity>();

        JSONObject object;
        try {
            object = data.getJSONObject(0);
            JSONObject dataObject = getResultObj(object);
            JSONArray resultArray = dataObject.getJSONArray("articles");

            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject o = resultArray.getJSONObject(i);

                NewsListEntity entity = setArticleItemEntity(o, i, true, String.valueOf(false), null, false);
                if (entity.getType().equals(TagUtils.StorType_video)) {
                    continue;
                }
                dataList.add(entity);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;

    }

    private static NewsListEntity setArticleItemEntity(JSONObject o, int i, boolean isCache, String isMixed, String channelId, boolean isoffline) {
        NewsListEntity item = new NewsListEntity();
        item.setStatus(getIntNodeValue(o, "status"));
        item.setArticle_abstract(getStringNodeValue(o, "content"));
//        item.setIs_Original(getBooleanNodeValue(o, "is_original"));
        item.setArticle_comment_num(getIntNodeValue(o,
                "comment_num"));
        item.setInterest_agree_num(getIntNodeValue(o, "agree_num"));
        item.setPageUrl(getStringNodeValue(o, "page_url"));
        item.setArticle_id(getStringNodeValue(o, "id"));
//        item.setArticle_pubDate(getStringNodeValue(o,"update_date"));
        setNewsTime(item, o, i, isCache, "update_date");
        item.setArticle_source(getStringNodeValue(o, "source"));
        item.setArticle_title(getStringNodeValue(o, "title"));
        if (!Utils.isStringEmpty(channelId)) {
            item.setChannel_id(channelId);
        } else {
            item.setChannel_id(getStringNodeValue(o, "channel_id"));
        }
        item.setReservezone2(String.valueOf(CountryMannager.getInstance().getCurrentCountry()));
        item.setCollect_num(getIntNodeValue(o, "collect_num"));
        item.setLanguage(getStringNodeValue(o, "language"));
        item.setCategory(getStringNodeValue(o, "category"));
        item.setCountry(getStringNodeValue(o, "country"));
        item.setUser_Id(getIntNodeValue(o, "user_id"));
        item.setUser_Name(getStringNodeValue(o, "user_name"));
        item.setUser_Avatar(getStringNodeValue(o, "user_avatar"));
        item.setTemplate(getStringNodeValue(o, "template"));
        item.setReservezone4(getStringNodeValue(o, "video_id"));
        item.setReservezone1(isMixed);
        item.setDownLoadTime(System.currentTimeMillis());
        item.setType(TagUtils.StorType_Real);
        item.setReservezone3(String.valueOf(isoffline));
        String storytype = String.valueOf(getIntNodeValue(o, "story_type"));
        if (storytype.equals(TagUtils.StorType_videoJson) || item.getCategory().equals("video")) {
            item.setType(TagUtils.StorType_video);
        }
        if (!o.isNull("tag")) {
            try {
                JSONArray array = o.getJSONArray("tag");
//                TLog.e("tag","tag==="+array.toString());
                List<Integer> tagList = getTagList(array, item);
                item.updateTags(tagList);
                setTagsData(item, Utils.getString2ListInteger(tagList));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!o.isNull("cover")) {
            item.updateImageList(getImageInfo(o));
            setArticleImgsData(item, o);
        }
        return item;
    }

    public static ArrayList<Integer> getTagList(JSONArray array, NewsListEntity entity) {
        ArrayList<Integer> tagsList = new ArrayList<Integer>();
        try {
            for (int i = 0; i < array.length(); i++) {
                tagsList.add(array.getInt(i));
//                if (entity.getType().equals(TagUtils.StorType_eidtion) && array.getInt(i)==TagUtils.Tag_TOP){
//                    entity.setType(TagUtils.StorType_eidtion_top);
//                }
                if (array.getInt(i) == TagUtils.Tag_TOP) {
                    entity.setType(TagUtils.StorType_eidtion_top);
                } else if (array.getInt(i) == TagUtils.Tag_EDITION) {
                    if (!entity.getType().equals(TagUtils.StorType_eidtion_top)) {
                        entity.setType(TagUtils.StorType_eidtion);
                    }
                }

//                if (array.get(i).equals("trending")){
//                    tagsList.add(ConfigUtis.NewsType_Recom);
//                }
//                if (array.get(i).equals("subscription")){
//                    tagsList.add(ConfigUtis.NewsType_Subscri);
//                }
//                if (array.get(i).equals("new")){
//                    tagsList.add(ConfigUtis.NewsType_New);
//                }
//                if (array.get(i).equals("breaking")){
//                    tagsList.add(ConfigUtis.NewsType_Hot);
//                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tagsList;
    }

    private static void setNewsTime(NewsListEntity item, JSONObject o, int i,
                                    boolean isCache, String jsonStr) {
        if (isCache) {
            item.setArticle_pubDate(getStringNodeValue(o, jsonStr));
        } else {
            long currentTime = System.currentTimeMillis();
            long offsetTime = i * 1000;
            long time = currentTime - offsetTime;
            item.setArticle_pubDate(Utils.getCurrentTime(time));
        }
    }

    public static void setArticleImgsData(NewsListEntity item,
                                          JSONObject imgsArray) {
        item.setImgData(imgsArray.toString());
    }

    public static void setTagsData(NewsListEntity item, String tags) {
        item.setTagsData(tags);
    }

    public static ArticleChannel getArticleChannel(JSONObject o) {
        ArticleChannel item = new ArticleChannel();
        int channel_id = getIntNodeValue(o, "channel_id");
        item.setChannel_id(channel_id);
        item.setTrend_index(getIntNodeValue(o, "trend_index"));
        item.setTrend_version(getIntNodeValue(o, "trend_version"));
        item.setNew_index(getIntNodeValue(o, "new_index"));
        item.setNew_version(getIntNodeValue(o, "new_version"));
        item.setSub(String.valueOf(getBooleanNodeValue(o, "sub")));
        return item;
    }

    public static ArticleEntity getArticleChannel(JSONArray array, boolean isCache, String cid,String curentArea) {
        ArticleEntity item = new ArticleEntity();
        JSONObject object;
        try {
            object = array.getJSONObject(0);
            JSONObject root = getResultObj(object);
            if (!root.isNull("recommend")) {
                JSONObject o = root.getJSONObject("recommend");
                item.setArticleRecommendData(o.toString());
                item.updateArticleRecommend(getArticleRecommend(o));
            }
            // getReadList
            JSONArray dataArray = root.getJSONArray("article");

            item.updateNewsList(getNewsItemList(dataArray, isCache, String.valueOf(false), cid, false,curentArea));
            if (!root.isNull("fid")) {
                item.setFidStr(getStringNodeValue(root, "fid"));
            }
            if (!root.isNull("channel")) {
                JSONObject arrayChannel = root.getJSONObject("channel");
                item.setArticleChannelData(arrayChannel.toString());
                item.updateArticleChannel(getArticleChannel(arrayChannel));
                TLog.e("NewsBasePager", "channel:" + arrayChannel.toString());
            }
            item.setChannelId(cid);
            item.setIsRecomment("false");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return item;
        }

        return item;
    }

    public static ArticleEntity getArticleEntity2(JSONArray array,
                                                  boolean isCache, boolean isoffline, boolean isVideoList) {
        ArticleEntity item = new ArticleEntity();
        JSONObject object;
        try {
            if (array == null || array.length() <= 0) {
                return null;
            }
            object = array.getJSONObject(0);
            JSONObject root = getResultObj(object);
            if (!root.isNull("recommend")) {
                JSONObject o = root.getJSONObject("recommend");
                item.setArticleRecommendData(o.toString());
                item.updateArticleRecommend(getArticleRecommend(o));
            }
            // getReadList
            JSONArray dataArray = root.getJSONArray("article");
            item.updateNewsList(getNewsItemList(dataArray, isCache, true + (isVideoList ? "video" : ""), null, isoffline,null));
            if (!root.isNull("fid")) {
                item.setFidStr(getStringNodeValue(root, "fid"));
            }
//            TLog.e("size---4444",item.toNewsList().size()+"---");
            if (!root.isNull("ads")) {
                JSONArray adArray = root.getJSONArray("ads");
                TLog.e("ads",adArray.toString());
                for (int i = 0; i < adArray.length(); i++) {
                    JSONObject adObj = adArray.getJSONObject(i);
                    int index = adObj.optInt("pos_index",-1);
                    boolean isshow = adObj.optBoolean("show",false);
                    if (!isshow || index==-1){
                        continue;
                    }
                    NewsListEntity entity = new NewsListEntity();
                    entity.setType(String.valueOf(ListItemBaseNews.Type_ads));
                    entity.setTemplate(String.valueOf(adObj.optInt("type",0)));
                    entity.setArticle_source(adObj.optString("source",""));
                    entity.setDownLoadTime(System.currentTimeMillis());
//                    entity.setTemplate("2");
                    if (!Utils.isStringEmpty(item.toNewsList()) && item.toNewsList().size()>index){
                        item.toNewsList().add(index,entity);
                    }

                }
            }
//            TLog.e("size---5555",item.toNewsList().size()+"---");
            item.setIsRecomment("true");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return item;
    }


    private static List<NewsListEntity> getNewsItemList(JSONArray resultArray, boolean isCache, String isMixed, String channelId, boolean isoffline,String areaname) {

        List<NewsListEntity> dataList = new ArrayList<NewsListEntity>();
        try {

            for (int i = 0; i < resultArray.length(); i++) {
                NewsListEntity item = new NewsListEntity();
                JSONObject o = resultArray.getJSONObject(i);
                NewsListEntity entity = setArticleItemEntity(o, i, isCache, isMixed, channelId, isoffline);
//                if (entity.getType().equals(TagUtils.StorType_video)){
//                    continue;
//                }
                if (!Utils.isStringEmpty(areaname)){
                    entity.setReservezone5(areaname);
                }
                dataList.add(entity);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;

    }

    public static List<NewsImgEntity> getImageInfo(JSONObject object) {
        List<NewsImgEntity> abstrctList = new ArrayList<NewsImgEntity>();
//            Log.d("cover","1--"+object.toString());
        try {
            if (Utils.isStringEmpty(object.getString("cover"))) {
                return abstrctList;
            }
//            Log.d("cover","2--"+object.getString("cover"));
            String[] Covers = Utils.split(object.getString("cover"), ",");


            for (int j = 0; j < Covers.length; j++) {
                NewsImgEntity entity = new NewsImgEntity();
                entity.setMedia_path(Covers[j]);
                int temp = getIntNodeValue(object, "template");
                if (object.has("type") && Covers.length > 0) {
                    //1-normal 0-zhiding
                    temp = ListItemBaseNews.Temp_Image_small;
                }
                entity.setPicture_type(ListItemBaseNews.getImageTypeByTemp(temp));
                abstrctList.add(entity);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return abstrctList;
    }

    public static ArticleRecommend getArticleRecommend(JSONObject o)
            throws JSONException {
        ArticleRecommend item = new ArticleRecommend();
        item.setIndex(getIntNodeValue(o, "index"));
        item.setVersion(getIntNodeValue(o, "version"));
        return item;
    }

    public static List<ArticleChannel> getArticleChannelList(JSONArray array, String
            channeltype)
            throws JSONException {
        List<ArticleChannel> dataList = new ArrayList<ArticleChannel>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject o = array.getJSONObject(i);
            ArticleChannel item = new ArticleChannel();
            int channel_id = getIntNodeValue(o, "channel_id");
            item.setChannel_id(channel_id);
            item.setIsSub("true");
            item.setTrend_index(getIntNodeValue(o, "trend_index"));
            item.setTrend_version(getIntNodeValue(o, "trend_version"));
            item.setNew_index(getIntNodeValue(o, "new_index"));
            item.setNew_version(getIntNodeValue(o, "new_version"));
            item.setSub(String.valueOf(getBooleanNodeValue(o, "sub")));
            item.setTableName(channeltype);
            item.setIsSub("true");
            dataList.add(item);
        }
        return dataList;
    }

    public static void setLastTagsList(ArticleEntity entity, JSONArray array)
            throws JSONException {

        List<Integer> last_trend_channels = new ArrayList<Integer>();
        List<Integer> last_new_channels = new ArrayList<Integer>();
        List<Integer> last_uf_trend_channels = new ArrayList<Integer>();
        List<Integer> last_uf_new_channels = new ArrayList<Integer>();

        for (int i = 0; i < array.length(); i++) {
            JSONObject o = array.getJSONObject(i);
            int channel_id = getIntNodeValue(o, "channel_id");
            if (!o.isNull("trend_index") && !o.isNull("trend_version")) {
                last_trend_channels.add(channel_id);
            }
            if (!o.isNull("new_index") && !o.isNull("new_version")) {
                last_new_channels.add(channel_id);
            }
            if (o.isNull("sub") || !getBooleanNodeValue(o, "sub")) {
                if (!o.isNull("trend_index") && !o.isNull("trend_version")) {
                    last_uf_trend_channels.add(channel_id);
                }
                if (!o.isNull("new_index") && !o.isNull("new_version")) {
                    last_uf_new_channels.add(channel_id);
                }
            }
        }
        entity.setLast_trend_channels(Utils
                .getString2ListInteger(last_trend_channels));
        entity.setLast_new_channels(Utils
                .getString2ListInteger(last_new_channels));
        entity.setLast_uf_trend_channels(Utils
                .getString2ListInteger(last_uf_trend_channels));
        entity.setLast_uf_new_channels(Utils
                .getString2ListInteger(last_uf_new_channels));
    }

    public static List<VideoMedieInfo> getMedieInfo(JSONArray array) {
        ArrayList<VideoMedieInfo> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object;
            try {
                object = array.getJSONObject(i);
                VideoMedieInfo entity = new VideoMedieInfo();
                entity.setCover_img_url(getStringNodeValue(object, "cover_img_url"));
                entity.setMedia_backup_path(getStringNodeValue(object, "media_backup_path"));
                entity.setMedia_path(getStringNodeValue(object, "media_path"));
                entity.setMedia_remark(getStringNodeValue(object, "media_remark"));
                entity.setMedia_type(getStringNodeValue(object, "media_type"));
                entity.setWifi(getBooleanNodeValue(object, "wifi"));
                entity.setMedia_codec(getStringNodeValue(object, "media_codec"));
                entity.setMedia_height(getIntNodeValue(object, "media_height"));
                entity.setMedia_width(getIntNodeValue(object, "media_width"));
                entity.setMedia_duration(getDoubleNodeValue(object, "media_duration"));
                list.add(entity);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    /**
     * �������������б�
     *
     * @param array
     * @return
     */

    public static List<NewsDetailCommentEntity> getDetailCommentList(
            JSONArray array) {
        List<NewsDetailCommentEntity> dataList = new ArrayList<NewsDetailCommentEntity>();
        try {

            JSONObject root = array.getJSONObject(0);

            JSONArray resultArray = getDataArray(root);
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject o = resultArray.getJSONObject(i);
                NewsDetailCommentEntity entity = new NewsDetailCommentEntity();
                entity.setComId(getStringNodeValue(o, "id"));
                entity.setArticleId(getIntNodeValue(o, "article_id"));
                entity.setComContent(getStringNodeValue(o, "comment_content"));
                entity.setComAgree(getIntNodeValue(o, "agreen_num"));
                entity.setComTime(getStringNodeValue(o, "create_date"));
                entity.setComUserHead(getStringNodeValue(o,
                        "user_avatar"));
                entity.setComUserName(getStringNodeValue(o, "user_name"));
                entity.setComUserId(getStringNodeValue(o, "user_id"));
                dataList.add(entity);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * ��ȡ������ʷ��¼
     *
     * @param array
     * @return
     */
    public static List<WithdrawEntity> getWithdrawHistoryList(JSONArray array) {
        List<WithdrawEntity> dataList = new ArrayList<WithdrawEntity>();
        try {

            JSONObject root = array.getJSONObject(0);
//			if (!getState200(root)) {
//				return dataList;
//			}
            JSONArray resultArray = getDataArray(root);
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject o = resultArray.getJSONObject(i);
                WithdrawEntity entity = new WithdrawEntity();
                entity.setAudit(getIntNodeValue(o, "audit"));
                entity.setAmount(getDoubleNodeValue(o, "amount"));
                entity.setApply_date(getStringNodeValue(o, "apply_date"));
                entity.setAudit_date(getStringNodeValue(o, "audit_date"));
                entity.setDesc(getStringNodeValue(o, "desc"));
                dataList.add(entity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public static List<CreditsEntity> getCreditsHistoryList(JSONArray array) {
        List<CreditsEntity> dataList = new ArrayList<CreditsEntity>();
        try {

            JSONObject root = array.getJSONObject(0);
//			if (!getState200(root)) {
//				return dataList;
//			}
            JSONArray resultArray = getDataArray(root);
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject o = resultArray.getJSONObject(i);
                CreditsEntity entity = new CreditsEntity();
                entity.setCreated_at(getStringNodeValue(o, "created_at"));
                entity.setIntegral_num(getIntNodeValue(o, "integral_num"));
                entity.setIntegral_action(getStringNodeValue(o, "integral_action"));
                entity.setUser_name(getStringNodeValue(o, "user_name"));
                dataList.add(entity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public static List<NewsListEntity> getWordList(String objStr) {
        List<NewsListEntity> entities = new ArrayList<>();
        JSONObject object;
        try {
            object = new JSONObject(objStr);
            JSONObject dataObject = getResultObj(object);
            JSONArray dataArray = dataObject.getJSONArray("articles");
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject o = dataArray.getJSONObject(i);
                NewsListEntity entity = setArticleItemEntity(o, i, true, String.valueOf(false), null, false);
                if (entity.getType().equals(TagUtils.StorType_video)) {
                    continue;
                }
                entities.add(entity);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return entities;
    }

    public static String getAfterStr(String s) {
        JSONObject object;
        try {
            object = new JSONObject(s);
            JSONObject dataObject = getResultObj(object);
            return getStringNodeValue(dataObject, "search_after");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static LocalInfoEntity getDetailList(String s) {
        LocalInfoEntity entity = new LocalInfoEntity();
        try {
            JSONObject object = new JSONObject(s);
            if (!object.has("data") || !getState200(object)) {
                return null;
            }
            JSONObject dataJson = getResultObj(object);
            JSONArray dataArray = dataJson.getJSONArray("body");
            entity.setTitle(getStringNodeValue(dataJson, "title"));
            entity.setDate(getStringNodeValue(dataJson, "create_date"));
            entity.setSource(getStringNodeValue(dataJson, "source"));
            entity.setType(getIntNodeValue(dataJson, "type"));
            entity.setNewsApi(getStringNodeValue(dataJson, "news_api"));
            List<LocalNewsBean> list = new ArrayList<>();
            List<String> allImage = new ArrayList<>();
            int index = 0;
            for (int i = 0; i < dataArray.length(); i++) {
                LocalNewsBean bean = new LocalNewsBean();
                JSONObject jsonObject = dataArray.getJSONObject(i);
                int type = getIntNodeValue(jsonObject, "type");
                bean.setParaType(type);
                JSONObject contentJson = jsonObject.getJSONObject("content");
                if (type == NewsLocalAdapter.flagtext) {
                    if (getIntNodeValue(dataJson, "type") == NewsLocalAdapter.newstype_funny) {
                        continue;
                    }
                    String text = getStringNodeValue(contentJson, "text");
                    if (Utils.isStringEmpty(text)) {
                        continue;
                    }
                    bean.setNewsContent(text);
                } else if (type == NewsLocalAdapter.flagimg) {
                    LocalImageEntity imageEntity = new LocalImageEntity();
                    imageEntity.setImgIndex(index);
                    index++;
                    imageEntity.setImgInfo(getStringNodeValue(contentJson, "alt"));
                    imageEntity.setRatio(getDoubleNodeValue(contentJson, "ratio"));
                    imageEntity.setSmallImg(getStringNodeValue(contentJson.getJSONObject("thumbnail"), "url"));
                    String largeurl = getStringNodeValue(contentJson.getJSONObject("image"), "url");
                    imageEntity.setLargeImg(largeurl);
                    allImage.add(largeurl);
                    bean.setImageEntity(imageEntity);
                    if (Utils.isStringEmpty(largeurl)) {
                        continue;
                    }
                } else if (type == NewsLocalAdapter.flagvideo) {
                    LocalVideoEntity videoEntity = new LocalVideoEntity();
                    JSONObject jsonObject1 = contentJson.getJSONObject("video");
                    videoEntity.setVideoCover(getStringNodeValue(jsonObject1, "image_url"));
                    String videoUrl = getStringNodeValue(jsonObject1, "video_url");
                    String videoType = getStringNodeValue(jsonObject1, "video_type");
                    videoEntity.setVideoUrl(videoUrl);
                    videoEntity.setVideoType(videoType);
                    if (!Utils.isStringEmpty(videoType) && !Utils.isStringEmpty(videoUrl) && videoType.equals("youtube")) {
                        String videoidStr = (videoUrl.substring(videoUrl.lastIndexOf("/") + 1));
                        videoEntity.setVideoId(videoidStr);
                        TLog.e("abc--", videoidStr);
                    }
                    bean.setVideoEntity(videoEntity);
                }
                list.add(bean);
            }

            if (!dataJson.isNull("ads")) {
                JSONArray adArray = dataJson.getJSONArray("ads");
                TLog.e("ads",adArray.toString());
                List<AdsEntity> adsEntityList = new ArrayList<>();
                for (int i = 0; i < adArray.length(); i++) {
                    JSONObject adObj = adArray.getJSONObject(i);
                    int posIndex = adObj.optInt("pos_index",-1);
                    boolean isshow = adObj.optBoolean("show",false);
                    if (!isshow){
                        continue;
                    }
                    AdsEntity adsEntity = new AdsEntity();
                    adsEntity.setType(adObj.getInt("type"));
                    adsEntity.setUnitType(adObj.getInt("unit_type"));
                    adsEntity.setAdSource(adObj.getString("source"));
//                    entity.setTemplate("2");
                    adsEntityList.add(adsEntity);
                }
                entity.setAdsList(adsEntityList);
            }

            entity.setLocalNewsBeanList(list);
            entity.setAllImgList(allImage);
            return entity;
        } catch (JSONException e) {
            e.printStackTrace();
            entity = null;
        }
        return entity;
    }

//    private static void setLocalImageList(LocalNewsBean bean, List<String> allImage){
//        if (Utils.isStringEmpty(bean.getSmallImgDatas()) || Utils.isStringEmpty(bean.getLargeImgDatas())){
//            return;
//        }
//        String[] smallList = Utils.split(bean.getSmallImgDatas(),",");
//        String[] largeList = Utils.split(bean.getLargeImgDatas(),",");
//        bean.setSmallImgList(Utils.switchToStringList(smallList));
//        List<String> largeLists = Utils.switchToStringList(largeList);
//        bean.setLargeImgList(largeLists);
//        allImage.addAll(largeLists);
//    }

    public static String getDetailUrls(String str, String verName) {
        try {
            JSONObject object = new JSONObject(str);
            if (object.has("state") && JsonParse.getState200(object)) {
                JSONArray array = getDataArray(object);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object1 = array.getJSONObject(i);
                    String verson = getStringNodeValue(object1, "ver");
                    if (verName.matches(verson)) {
                        JSONObject object2 = object1.getJSONObject("config");
                        JSONObject object3 = object2.getJSONObject("value");
                        String detalUrl = getStringNodeValue(object3, "url");
                        return detalUrl;
                    }
                }
            }
        } catch (JSONException e) {

        }
        return null;
    }

}
