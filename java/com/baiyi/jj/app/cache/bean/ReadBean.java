package com.baiyi.jj.app.cache.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
@DatabaseTable(tableName = "tb_read")
public class ReadBean implements Serializable{

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String newsId;

    public ReadBean() {
    }

    public int getId() {
        return id;
    }

    public ReadBean(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }
}
