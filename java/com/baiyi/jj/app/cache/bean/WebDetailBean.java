package com.baiyi.jj.app.cache.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/13 0013.
 */
@DatabaseTable(tableName = "tb_webdetail")
public class WebDetailBean implements Serializable {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String articleid;
    @DatabaseField
    private String webcontent;
    @DatabaseField
    private long downloadtime;
    @DatabaseField
    private String reservezone1;
    @DatabaseField
    private String reservezone2;

    public WebDetailBean() {
    }
    public int getId() {
        return id;
    }

    public long getDownload() {
        return downloadtime;
    }

    public void setDownload(long download) {
        this.downloadtime = download;
    }

    public String getArticleid() {
        return articleid;
    }

    public void setArticleid(String articleid) {
        this.articleid = articleid;
    }

    public String getWebcontent() {
        return webcontent;
    }

    public void setWebcontent(String webcontent) {
        this.webcontent = webcontent;
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
}
