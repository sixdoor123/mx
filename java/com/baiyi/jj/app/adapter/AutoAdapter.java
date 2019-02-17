package com.baiyi.jj.app.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baiyi.jj.app.adapter.base.AutoPagerBaseAdapter;
import com.baiyi.jj.app.imgtools.GlideTool;
import com.baiyi.jj.app.utils.Utils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/14 0014.
 */
public class AutoAdapter extends AutoPagerBaseAdapter {

    private Context context;
    private List<String> imageIdList;

    private int           size;
    private boolean       isInfiniteLoop;

    public AutoAdapter(Context context, List<String> imageIdList) {
        this.context = context;
        this.imageIdList = imageIdList;
        this.size = getImageSize(imageIdList);
        isInfiniteLoop = false;
    }

    @Override
    public int getCount() {
        // Infinite loop
        return isInfiniteLoop ? Integer.MAX_VALUE : getImageSize(imageIdList);
    }

    private int getImageSize(List<String> list){
        if (Utils.isStringEmpty(list)){
            return 0;
        }
        return list.size();
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = holder.imageView = new ImageView(context);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }
        GlideTool.getListImage(context,imageIdList.get(getPosition(position)),holder.imageView);
        return view;
    }

    private static class ViewHolder {
        ImageView imageView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public AutoAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }


}
