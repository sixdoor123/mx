package com.baiyi.jj.app.cache.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/18 0018.
 */
@DatabaseTable(tableName = "tb_offlinechannel")
public class OfflineChannelBean implements Serializable{

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String channelid;
    @DatabaseField
    private String channelname;
    @DatabaseField
    private String channelcover;
    @DatabaseField
    private boolean isoffline;
    @DatabaseField
    private String mid;
    @DatabaseField
    private int channelindex;
    @DatabaseField
    private String language;
    @DatabaseField
    private String reservezone1;
    @DatabaseField
    private String reservezone2;
    @DatabaseField
    private String reservezone3;

    public OfflineChannelBean() {
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public String getChannelname() {
        return channelname;
    }

    public void setChannelname(String channelname) {
        this.channelname = channelname;
    }

    public String getChannelcover() {
        return channelcover;
    }

    public void setChannelcover(String channelcover) {
        this.channelcover = channelcover;
    }

    public boolean isoffline() {
        return isoffline;
    }

    public void setIsoffline(boolean isoffline) {
        this.isoffline = isoffline;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public int getChannelindex() {
        return channelindex;
    }

    public void setChannelindex(int channelindex) {
        this.channelindex = channelindex;
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
}
