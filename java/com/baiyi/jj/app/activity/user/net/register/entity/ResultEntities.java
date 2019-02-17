package com.baiyi.jj.app.activity.user.net.register.entity;

/**
 * Created by sunxy on 2016/9/13.
 */

public class ResultEntities {

    private String id;
    private String userName;

    private StateEnties stateEnties;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public StateEnties getStateEnties() {
        return stateEnties;
    }

    public void setStateEnties(StateEnties stateEnties) {
        this.stateEnties = stateEnties;
    }

    @Override
    public String toString() {
        return "ResultEntities{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", stateEnties=" + stateEnties.toString() +
                '}';
    }
}
