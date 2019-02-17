package com.baiyi.jj.app.cache.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/14 0014.
 */
@DatabaseTable(tableName = "tb_history")
public class HistoryBean implements Serializable{

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "hisname",canBeNull = false)
    private String hisname;

    public HistoryBean() {
    }

    public HistoryBean(String hisName) {
        hisname = hisName;
    }

    public String getHisName() {
        return hisname;
    }

    public void setHisName(String hisName) {
        hisname = hisName;
    }

    public String toString()
    {
        return "HistoryBean [id=" + id + ", hisname=" + hisname + "]";
    }
}
