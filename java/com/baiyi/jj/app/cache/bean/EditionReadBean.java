package com.baiyi.jj.app.cache.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/7 0007.
 */
@DatabaseTable(tableName = "tb_editionread")
public class EditionReadBean implements Serializable {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String newsId;

    public EditionReadBean() {
    }

    public int getId() {
        return id;
    }

    public EditionReadBean(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }
}
