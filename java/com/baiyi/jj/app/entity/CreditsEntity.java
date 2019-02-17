package com.baiyi.jj.app.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/17 0017.
 */
public class CreditsEntity implements Serializable{

    private String integral_action;
    private int integral_num;
    private String user_name;
    private String created_at;

    public String getIntegral_action() {
        return integral_action;
    }

    public void setIntegral_action(String integral_action) {
        this.integral_action = integral_action;
    }

    public int getIntegral_num() {
        return integral_num;
    }

    public void setIntegral_num(int integral_num) {
        this.integral_num = integral_num;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
