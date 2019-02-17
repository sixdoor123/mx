package com.baiyi.jj.app.entity.localnews;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/4 0004.
 */
public class AdsEntity implements Serializable {

    private boolean isShow;
    private String adSource;
    private int UnitType;
    private int type;
    private int postion;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getAdSource() {
        return adSource;
    }

    public void setAdSource(String adSource) {
        this.adSource = adSource;
    }

    public int getUnitType() {
        return UnitType;
    }

    public void setUnitType(int unitType) {
        UnitType = unitType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }
}
