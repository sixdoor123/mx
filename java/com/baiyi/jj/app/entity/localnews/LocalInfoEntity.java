package com.baiyi.jj.app.entity.localnews;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
public class LocalInfoEntity implements Serializable{

    private List<LocalNewsBean> localNewsBeanList;
    private List<String> allImgList;
    private String source;
    private String date;
    private String title;
    private int type;
    private String newsApi;
    private List<AdsEntity> adsList;

    public String getNewsApi() {
        return newsApi;
    }

    public List<AdsEntity> getAdsList() {
        return adsList;
    }

    public void setAdsList(List<AdsEntity> adsList) {
        this.adsList = adsList;
    }

    public void setNewsApi(String newsApi) {
        this.newsApi = newsApi;
    }

    public String isNewsApi() {
        return newsApi;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getAllImgList() {
        return allImgList;
    }

    public void setAllImgList(List<String> allImgList) {
        this.allImgList = allImgList;
    }

    public List<LocalNewsBean> getLocalNewsBeanList() {
        return localNewsBeanList;
    }

    public void setLocalNewsBeanList(List<LocalNewsBean> localNewsBeanList) {
        this.localNewsBeanList = localNewsBeanList;
    }
}
