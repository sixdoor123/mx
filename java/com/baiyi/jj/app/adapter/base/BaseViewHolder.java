package com.baiyi.jj.app.adapter.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by tangkun on 2016/6/30 0030.
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(@NonNull T data, int position);
}
