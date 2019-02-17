package com.baiyi.jj.app.cache.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Wzd on 2016/10/17 0017.
 * 储存本地点赞信息
 */
@DatabaseTable(tableName = "tb_support")
public class SupportBean implements Serializable{

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String newsId;
    @DatabaseField
    private String mid;

    public SupportBean(String newsId, String mid) {
        this.newsId = newsId;
        this.mid = mid;
    }

    public SupportBean() {
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}
