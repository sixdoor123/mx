package com.baiyi.jj.app.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.baiyi.jj.app.views.viewpager.WelcomeView;
import com.turbo.turbo.mexico.R;

/**
 *  欢迎界面适配器
 */
public class WelcomeAdapter extends PagerAdapter{
    private Context context;
    private int[] bitmaps;
    private LayoutInflater inflater;
    private ImageView mImgBig;

    public WelcomeAdapter(Context context, int[] bitmaps) {
        this.context = context;
        this.bitmaps = bitmaps;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view==o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view;
        if (position<2){
            view = inflater.inflate(R.layout.item_welcome,null);
            mImgBig = (ImageView) view.findViewById(R.id.img_welcome);
            mImgBig.setBackgroundResource(bitmaps[position]);
        }else{
            view = new WelcomeView(context);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //要删除回调父类的方法，否则回报异常
        container.removeView((View) object);
    }
}
