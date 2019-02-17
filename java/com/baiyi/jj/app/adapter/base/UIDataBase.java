package com.baiyi.jj.app.adapter.base;

/**
 * Created by tangkun on 2016/7/4.
 */

public abstract class UIDataBase<T>
{
    public final T data;

    public UIDataBase(T data)
    {
        this.data = data;
    }
}