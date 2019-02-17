package com.baiyi.jj.app.views.pageview;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baiyi.jj.app.activity.attention.AttentionWordsEntity;
import com.baiyi.jj.app.entity.NewsImgEntity;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.entity.article.GifEntity;
import com.baiyi.jj.app.utils.JsonParseBase;
import com.baiyi.jj.app.utils.TagUtils;
import com.baiyi.jj.app.utils.Utils;

import static com.baiyi.jj.app.utils.JsonParse.getImageInfo;

public class JsonParse_Collect extends JsonParseBase {

    /**
     * @param array
     * @return
     */
    public static List<NewsListEntity> getCollectionList(JSONArray array) {
        List<NewsListEntity> dataList = new ArrayList<NewsListEntity>();
        if (array == null) {
            return dataList;
        }
        try {
            JSONObject root = array.getJSONObject(0);

            JSONArray ja = getDataArray(root);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject o = ja.getJSONObject(i);
                JSONObject article = o.getJSONObject("article");
                NewsListEntity item = new NewsListEntity();

                item.setArticle_title(getStringNodeValue(article, "title"));
//                item.setArticle_pubDate(getStringNodeValue(article, "create_date"));
                item.setChannel_id(getStringNodeValue(article, "channel_id"));
                item.setCategory(getStringNodeValue(article, "category"));
                String pageurl = getStringNodeValue(article, "page_url");
                item.setPageUrl(pageurl);
                if (!Utils.isStringEmpty(item.getCategory())&&item.getCategory().equals("video")){
                    item.setType(TagUtils.StorType_video);
                    if (!Utils.isStringEmpty(pageurl)&&pageurl.contains("v=")){
                        item.setReservezone4(pageurl.substring(pageurl.indexOf("v=")+2));
                    }
                }

                item.setCollect_num(getIntNodeValue(article, "collect_num"));
                item.setArticle_source(getStringNodeValue(article, "source"));
                item.setArticle_comment_num(getIntNodeValue(article, "comment_num"));
                if (!article.isNull("cover")) {
                    item.updateImageList(getImageInfo(article));
                }
//                String storytype = String.valueOf(getIntNodeValue(o,"story_type"));
//                if (storytype.equals(TagUtils.StorType_videoJson)){
//                    item.setType(TagUtils.StorType_video);
//                }
                item.setMy_coll_id(getStringNodeValue(o, "id"));
                item.setArticle_id(getStringNodeValue(o, "article_id"));
                item.setUser_Id(getIntNodeValue(o, "user_id"));
                item.setUser_Name(getStringNodeValue(o, "user_name"));
                item.setArticle_pubDate(getStringNodeValue(o, "collect_date"));


                dataList.add(item);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * 文章详情中的相关阅读接口
     *
     * @param array
     * @return
     */
    public static List<NewsListEntity> getReadingList(String array) {
        List<NewsListEntity> dataList = new ArrayList<NewsListEntity>();
        if (Utils.isStringEmpty(array)) {
            return dataList;
        }
        try {
            JSONObject root = new JSONObject(array);
            JSONArray article = getDataArray(root);
            if (article == null) {
                return dataList;
            }
            for (int i = 0; i < article.length(); i++) {
                JSONObject o = article.getJSONObject(i);
                NewsListEntity item = new NewsListEntity();

                item.setArticle_id(getStringNodeValue(o, "id"));
                item.setArticle_title(getStringNodeValue(o, "title"));
                item.setArticleType(getStringNodeValue(o, "category"));
                item.setArticle_comment_num(getIntNodeValue(o, "comment_num"));
                if (!o.isNull("cover")) {
                    item.updateImageList(getImageInfo(o));
                }
                item.setPageUrl(getStringNodeValue(o, "page_url"));
                item.setIs_Original(false);
                dataList.add(item);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dataList;
    }

    /**
     * 文章详情中的请您关注接口
     *
     * @param array
     * @return
     */
    public static List<AttentionWordsEntity> getPleaseAttention(String array) {
        List<AttentionWordsEntity> dataList = new ArrayList<AttentionWordsEntity>();
        if (Utils.isStringEmpty(array)) {
            return dataList;
        }
        try {
            JSONObject root = new JSONObject(array);
            JSONArray dataArray = getDataArray(root);
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject object = (JSONObject) dataArray.get(i);
                JSONObject dataObj = object.getJSONObject("data");
                AttentionWordsEntity entity = new AttentionWordsEntity();
                entity.setChannel_id(getStringNodeValue(dataObj, "channel_id"));
                entity.setHotword_id(getIntNodeValue(dataObj, "id"));
                entity.setAlias(getStringNodeValue(dataObj, "alias"));
                entity.setWords(getStringNodeValue(dataObj, "words"));
                entity.setChannel_name(getStringNodeValue(dataObj, "channel_name"));

                dataList.add(entity);
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dataList;
    }

    public static List<GifEntity> getGifList(String s) {
        List<GifEntity> dataList = new ArrayList<GifEntity>();
        try {
            JSONObject object = new JSONObject(s);
            JSONArray data = getDataArray(object);
            for (int i = 0; i < data.length(); i++) {
                JSONObject dataObject = data.getJSONObject(i);
                GifEntity item = new GifEntity();

                item.setArticle_id(getStringNodeValue(dataObject, "id"));
                item.setChannel_id(getStringNodeValue(dataObject, "channel_id"));
                item.setCreate_date(getStringNodeValue(dataObject, "create_date"));
                item.setCategory(getStringNodeValue(dataObject, "category"));
                item.setLanguage(getStringNodeValue(dataObject, "language"));
                item.setPage_url(getStringNodeValue(dataObject, "page_url"));
                item.setSource(getStringNodeValue(dataObject, "source"));
                item.setTemplate(getIntNodeValue(dataObject, "template"));
                item.setTitle(getStringNodeValue(dataObject, "title"));
                JSONArray imagesArray = dataObject.getJSONArray("images");
                item.setImgString(imagesArray.toString());
                dataList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
