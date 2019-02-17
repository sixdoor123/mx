package com.baiyi.jj.app.activity.attention;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public class MyAttentionEntity implements Serializable{

    private String AtteName;
    private String AtteImg;
    private int AtteId;

    public String getAtteName() {
        return AtteName;
    }

    public void setAtteName(String atteName) {
        AtteName = atteName;
    }

    public String getAtteImg() {
        return AtteImg;
    }

    public void setAtteImg(String atteImg) {
        AtteImg = atteImg;
    }

    public int getAtteId() {
        return AtteId;
    }

    public void setAtteId(int atteId) {
        AtteId = atteId;
    }
}
