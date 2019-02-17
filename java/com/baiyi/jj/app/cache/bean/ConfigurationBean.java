package com.baiyi.jj.app.cache.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/20 0020.
 */
@DatabaseTable(tableName = "db_config")
public class ConfigurationBean implements Serializable{
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String uuid;
    @DatabaseField
    private String gustid;
    @DatabaseField
    private String gustInfoStr;
    @DatabaseField
    private String userAccount;
    @DatabaseField
    private String userKey;
    @DatabaseField
    private String userToken;
    @DatabaseField
    private String gustToken;
    @DatabaseField
    private String refreshGustToken;
    @DatabaseField
    private String refreshToken;
    @DatabaseField
    private String scope;
    @DatabaseField
    private String otherToken;
    @DatabaseField
    private String isthirdlogin;
    @DatabaseField
    private int userType;
    @DatabaseField
    private int fontsize;
    @DatabaseField
    private int abstractshow;
    @DatabaseField
    private int themeid;

    public int getFontsize() {
        return fontsize;
    }

    public void setFontsize(int fontsize) {
        this.fontsize = fontsize;
    }

    public int getAbstractshow() {
        return abstractshow;
    }

    public void setAbstractshow(int abstractshow) {
        this.abstractshow = abstractshow;
    }

    public int getThemeid() {
        return themeid;
    }

    public void setThemeid(int themeid) {
        this.themeid = themeid;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getRefreshGustToken() {
        return refreshGustToken;
    }

    public void setRefreshGustToken(String refreshGustToken) {
        this.refreshGustToken = refreshGustToken;
    }

    public String getIsthirdlogin() {
        return isthirdlogin;
    }

    public void setIsthirdlogin(String isthirdlogin) {
        this.isthirdlogin = isthirdlogin;
    }

    public String getOtherToken() {
        return otherToken;
    }

    public void setOtherToken(String otherToken) {
        this.otherToken = otherToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public ConfigurationBean() {
    }


    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getGustToken() {
        return gustToken;
    }

    public void setGustToken(String gustToken) {
        this.gustToken = gustToken;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getGustid() {
        return gustid;
    }

    public void setGustid(String gustid) {
        this.gustid = gustid;
    }

    public String getGustInfoStr() {
        return gustInfoStr;
    }

    public void setGustInfoStr(String gustInfoStr) {
        this.gustInfoStr = gustInfoStr;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }
}
