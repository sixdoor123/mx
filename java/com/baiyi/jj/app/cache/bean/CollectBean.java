package com.baiyi.jj.app.cache.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Wzd on 2016/10/17 0017.
 */
@DatabaseTable(tableName = "tb_collect")
public class CollectBean implements Serializable{

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String mid;
    @DatabaseField
    private String newsId;
    @DatabaseField
    private String newsTable;

    public CollectBean() {
    }

    public CollectBean(String mid, String newsId, String newsTable) {
        this.mid = mid;
        this.newsId = newsId;
        this.newsTable = newsTable;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsTable() {
        return newsTable;
    }

    public void setNewsTable(String newsTable) {
        this.newsTable = newsTable;
    }
}
