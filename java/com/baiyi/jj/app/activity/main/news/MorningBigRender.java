package com.baiyi.jj.app.activity.main.news;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.MorningNewsListAct;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.manager.MorningNewsManager;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.utils.XMLName;
import com.baiyi.jj.app.views.NetworkImageView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.turbo.turbo.mexico.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/17 0017.
 */
public class MorningBigRender extends BaseHolder{

    ConvenientBanner convenientBanner;
    TextView textViewTitle;
    LinearLayout linearLayout;

    Context context;
    OnMornItemClick onMornItemClick;

    public MorningBigRender(View arg0, Context context) {
        super(arg0);
        this.context = context;
    }

    private void initView(View headView){
        convenientBanner = (ConvenientBanner) headView.findViewById(R.id.morning_pager);
        int imgW = (Config.getInstance().getScreenWidth(context) - BaseActivity.getDensity_int(30))/ 3;
        convenientBanner.getLayoutParams().width = imgW*2;

        textViewTitle = (TextView) headView.findViewById(R.id.title_morning);
        // Noticias de la mañana

        textViewTitle.getLayoutParams().width = imgW;

    }

    private void setData(NewsListEntity entity, final int position){
        textViewTitle.setText(MorningNewsManager.getDayTitle(context));
        if (!Utils.isStringEmpty(entity.toImgList())){
            List<String> imgList = new ArrayList<>();
            for (int i = 0;i<entity.toString().length();i++){
                imgList.add(entity.toImgList().get(i).getMedia_path());
            }
            convenientBanner.setPages(new CBViewHolderCreator() {
                @Override
                public NetworkImageView createHolder() {
                    return new NetworkImageView();
                }
            },imgList).setPageIndicator(new int[]{R.drawable.current_point_ico,R.drawable.icon_noaccess_point});
        }
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                preference.Set(XMLName.XML_ClickMorning,String.valueOf(true));
//                preference.saveConfig();
                if (onMornItemClick != null){
                    onMornItemClick.itemClick(position);
                }

            }
        });
    }

    public interface OnMornItemClick{
        void itemClick(int postion);
    }
}
