package com.baiyi.jj.app.entity.localnews;

/**
 * Created by Administrator on 2017/3/28 0028.
 */
public class LocalImageEntity {

    private String smallImg;
    private String largeImg;
    private int imgIndex;
    private String imgInfo;
    private double ratio;

    public String getSmallImg() {
        return smallImg;
    }

    public void setSmallImg(String smallImg) {
        this.smallImg = smallImg;
    }

    public String getLargeImg() {
        return largeImg;
    }

    public void setLargeImg(String largeImg) {
        this.largeImg = largeImg;
    }

    public int getImgIndex() {
        return imgIndex;
    }

    public void setImgIndex(int imgIndex) {
        this.imgIndex = imgIndex;
    }

    public String getImgInfo() {
        return imgInfo;
    }

    public void setImgInfo(String imgInfo) {
        this.imgInfo = imgInfo;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
}
