package com.baiyi.jj.app.activity.user.entity;

import com.baiyi.jj.app.adapter.base.UIDataBase;

/**
 * Created by Administrator on 2016/9/18.
 */

public class LanguageEntities
{

    private String id;
    private String name;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "LanguageEntities{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
