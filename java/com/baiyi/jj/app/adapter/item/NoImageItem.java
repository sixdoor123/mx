package com.baiyi.jj.app.adapter.item;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.entity.localnews.LocalNewsBean;
import com.baiyi.jj.app.theme.FontUtil;
import com.turbo.turbo.mexico.R;

/**
 * Created by Administrator on 2017/3/27 0027.
 */
public class NoImageItem extends RecyclerView.ViewHolder{

    private TextView txtContent;
    private Context context;

    public NoImageItem(View itemView, Context context) {
        super(itemView);
        this.context = context;
        initView(itemView);
    }

    private void initView(View contentView){
        txtContent = (TextView) contentView.findViewById(R.id.txt_noimg_content);
        txtContent.setTypeface(CmsApplication.getDetailConten(context));
    }

    public void setData(LocalNewsBean bean){
        if (bean != null){
            float textSize = FontUtil.getNewsContentSize();
            txtContent.setLineSpacing(FontUtil.getLineSpaceNews(),1);
            txtContent.setTextSize(textSize);
            txtContent.setText(bean.getNewsContent());
        }
    }
}
