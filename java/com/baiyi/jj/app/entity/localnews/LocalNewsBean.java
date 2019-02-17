package com.baiyi.jj.app.entity.localnews;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
public class LocalNewsBean implements Serializable{

    private String newsContent;
    private LocalImageEntity imageEntity;
    private LocalVideoEntity videoEntity;
    private int paraType; // 0 wenzi  1 tupian

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public LocalVideoEntity getVideoEntity() {
        return videoEntity;
    }

    public void setVideoEntity(LocalVideoEntity videoEntity) {
        this.videoEntity = videoEntity;
    }

    public LocalImageEntity getImageEntity() {
        return imageEntity;
    }

    public void setImageEntity(LocalImageEntity imageEntity) {
        this.imageEntity = imageEntity;
    }

    public int getParaType() {
        return paraType;
    }

    public void setParaType(int paraType) {
        this.paraType = paraType;
    }
}
