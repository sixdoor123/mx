/**
 *
 */
package com.baiyi.jj.app.views;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.NewsLocalAct;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.theme.FontUtil;
import com.baiyi.jj.app.utils.ArticleHistoryUtils;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.IntentClassChangeUtils;
import com.baiyi.jj.app.utils.Utils;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.theme.ThemeUtil;

import java.util.List;

/**
 * ��������������Ķ���������
 * Created by lizhilong on 2016/12/21.
 */
public class LinNewsReadingView extends LinearLayout {

    private View[] views = null;
    private TextView[] textViews = null;
    private List<NewsListEntity> list = null;

    public LinNewsReadingView(Context context) {
        this(context, null);
    }

    public LinNewsReadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void initView(List<NewsListEntity> list) {
        this.list = list;
        views = new View[this.list.size()];
        textViews = new TextView[this.list.size()];
        for (int i = 0; i < this.list.size(); i++) {
            initItemView(i);
        }
    }

    private void initItemView(final int index) {
        views[index] = ContextUtil.getLayoutInflater(getContext()).inflate(R.layout.item_new_reading, null);
        addView(views[index]);

        final NewsListEntity entity = this.list.get(index);
        textViews[index] = (TextView) views[index].findViewById(R.id.tv_reading);
        textViews[index].setText(entity.getArticle_title());
        textViews[index].setTypeface(CmsApplication.getDetailConten(getContext()));
        textViews[index].setTextSize(FontUtil.getRelatedReadingTextSize());
        views[index].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentClassChangeUtils.startNewsDetail(getContext(),entity,-1,0,false);
            }
        });
    }

    /**
     * �޸�����Ĵ�С
     */
    public void setTextSize() {
        if (null == textViews){
            return;
        }
        for (int i = 0; i < this.textViews.length; i++) {
            textViews[i].setTextSize(FontUtil.getRelatedReadingTextSize());
        }
    }

}
