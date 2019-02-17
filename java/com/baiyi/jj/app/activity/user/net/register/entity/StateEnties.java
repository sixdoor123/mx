package com.baiyi.jj.app.activity.user.net.register.entity;

/**
 * Created by sunxy on 2016/9/13.
 */

public class StateEnties {

    private int state;
    private String msg;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "StateEnties{" +
                "state=" + state +
                ", msg='" + msg + '\'' +
                '}';
    }
}
