/**
 *
 */
package com.baiyi.jj.app.entity.article;

import com.baiyi.core.database.AbstractBaseModel;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tangkun
 */
@DatabaseTable(tableName = "tb_gif")
public class GifEntity implements Serializable {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String article_id;
    @DatabaseField
    private String channel_id;
    @DatabaseField
    private String create_date;
    @DatabaseField
    private String category;
    @DatabaseField
    private String language;
    @DatabaseField
    private String page_url;
    @DatabaseField
    private String source;
    @DatabaseField
    private int template;
    @DatabaseField
    private String title;
    @DatabaseField
    private String imgString;
    private List<String> imgList;

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPage_url() {
        return page_url;
    }

    public void setPage_url(String page_url) {
        this.page_url = page_url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getTemplate() {
        return template;
    }

    public void setTemplate(int template) {
        this.template = template;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgString() {
        return imgString;
    }

    public void setImgString(String imgString) {
        this.imgString = imgString;
    }

    public List<String> getImgList() {
        List<String> list = new ArrayList<>();
        try {
            JSONArray imagesArray = new JSONArray(imgString);
            for (int j = 0; j < imagesArray.length(); j++) {
                list.add(imagesArray.getString(j));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
