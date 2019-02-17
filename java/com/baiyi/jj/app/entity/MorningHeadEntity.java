package com.baiyi.jj.app.entity;

import com.baiyi.core.database.AbstractBaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/14 0014.
 */
public class MorningHeadEntity extends AbstractBaseModel {

    private String id;
    private String paperid;
    private int template;
    private String titleStr;
    private int index;
    private List<String> imageList;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getPaperid() {
        return paperid;
    }

    public void setPaperid(String paperid) {
        this.paperid = paperid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTemplate() {
        return template;
    }

    public void setTemplate(int template) {
        this.template = template;
    }

    public String getTitleStr() {
        return titleStr;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }
}
