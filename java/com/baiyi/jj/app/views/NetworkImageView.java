package com.baiyi.jj.app.views;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.BaseAnalyticsActivity;
import com.baiyi.jj.app.activity.MorningNewsListAct;
import com.baiyi.jj.app.imgtools.GlideTool;
import com.bigkoo.convenientbanner.holder.Holder;

/**
 * Created by tangkun on 2016/6/28.
 */

public class NetworkImageView implements Holder<String> {
    private ImageView imageView;
    @Override
    public View createView(final Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MorningNewsListAct.class);
                context.startActivity(intent);
                ((BaseActivity) context).addEnvent(BaseAnalyticsActivity.Envent_Click_MorningPager);
            }
        });
        return imageView;
    }

    @Override
    public void UpdateUI(Context context,int position, String data) {
        GlideTool.getListImage(context,data,imageView);
    }
}
