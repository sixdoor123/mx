package com.baiyi.jj.app.activity.user.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/29 0029.
 */
public class IntegalEntity implements Serializable{

    private int minMoney; //�ɶһ���С��ֵ
    private int maxMoney;//�ɶһ������ֵ
    private String currency; //Ǯ����
    private int maxDayPoint; //һ�����ɶһ�����
    private double exchange; // �һ�����

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
