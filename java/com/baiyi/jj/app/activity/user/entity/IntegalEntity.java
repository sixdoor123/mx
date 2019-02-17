package com.baiyi.jj.app.activity.user.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/29 0029.
 */
public class IntegalEntity implements Serializable{

    private int minMoney; //可兑换最小面值
    private int maxMoney;//可兑换最大面值
    private String currency; //钱符号
    private int maxDayPoint; //一天最大可兑换积分
    private double exchange; // 兑换比例

    public int getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(int minMoney) {
        this.minMoney = minMoney;
    }

    public int getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(int maxMoney) {
        this.maxMoney = maxMoney;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getMaxDayPoint() {
        return maxDayPoint;
    }

    public void setMaxDayPoint(int maxDayPoint) {
        this.maxDayPoint = maxDayPoint;
    }

    public double getExchange() {
        return exchange;
    }

    public void setExchange(double exchange) {
        this.exchange = exchange;
    }
}
