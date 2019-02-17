package com.baiyi.jj.app.adapter.base;

/**
 * Created by tangkun on 16/9/9.
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.RecyclerView;

import com.baiyi.jj.app.utils.Utils;


/**
 * Created by weizd on 2016/07/04 0030.
 *
 *  һ������Adapter���࣬recycleView��Adapter������ֱ�Ӽ̳�ʹ��
 *  ��Ҫʵ����������
 *  item��layout�ļ����̳�UIDataBase��ʵ�壬�̳�BaseViewHolder��Holder
 */

public abstract class BaseItemAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    public ArrayList<UIDataBase> datas = new ArrayList<>();
    protected int selectId = 0;

    public int getSelectId() {
        return selectId;
    }

    public void setSelectId(int selectId) {
        this.selectId = selectId;
        this.notifyDataSetChanged();
    }
    public void refreshList(){
        this.notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bind(datas.get(position), position);
    }

    public void setData(List<T> data)
    {
        datas.clear();
        if(Utils.isStringEmpty(data))
        {
            return;
        }
        for(T live : data)
        {
            datas.add(getUIDataItem(live));
        }
        notifyDataSetChanged();
    }

    public void addData(List<T> data)
    {
        if(Utils.isStringEmpty(data))
        {
            return;
        }
        for(T live : data)
        {
            datas.add(getUIDataItem(live));
        }
        notifyDataSetChanged();
    }

    public List<T> getListDatas(){
        List<T> list = new ArrayList<>();
        for (UIDataBase dataBase : datas){
            list.add((T) dataBase.data);
        }
        return list;
    }

    public void setDataNull()
    {
        datas.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public ArrayList<UIDataBase> getDatas() {
        return datas;
    }



    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(getLayout(), parent, false);
        return getBindingHolder(itemView);
    }

    public abstract int getLayout();
    public abstract BaseViewHolder getBindingHolder(View view);
    public abstract UIDataBase getUIDataItem(T live);




}

