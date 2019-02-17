package com.baiyi.jj.app.entity;

import com.baiyi.core.database.AbstractBaseModel;

/**
 * 充值历史记录
 * Created by lizhilong on 2016/9/20.
 */

public class WithdrawEntity extends AbstractBaseModel {

    //"audit": 0,
    private int audit;
    //"amount": 2,
    private double amount;
    // "apply_date": "2016-09-19 02:04:00",
    private String apply_date;
    //"audit_date": "2016-09-19 02:04:00",
    private String audit_date;
    // "desc": ""
    private String desc;

    public int getAudit() {
        return audit;
    }

    public void setAudit(int audit) {
        this.audit = audit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getApply_date() {
        return apply_date;
    }

    public void setApply_date(String apply_date) {
        this.apply_date = apply_date;
    }

    public String getAudit_date() {
        return audit_date;
    }

    public void setAudit_date(String audit_date) {
        this.audit_date = audit_date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
