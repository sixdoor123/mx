package com.baiyi.jj.app.activity.attention;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.views.MyScrollView;
import com.turbo.turbo.mexico.R;

import java.util.ArrayList;

/**
 * ��ע����˵
 * ���ߣ�lizl on 2016/11/28 11:06
 */
public class AttentionMenuView extends RadioGroup {

    private int normalWidth = ((BaseActivity)getContext()).getDensity_int(92);//menu正常的宽度a
    private int normalHeight = ((BaseActivity)getContext()).getDensity_int(60);//menu正常的高度Set
    public int maxWidth = ((BaseActivity)getContext()).getScreenWidth()*3/4;//menu大的宽度
    private RadioButton[] rbs = null;
    private MenuOnclickLister lister = null;
    private MenuCheckOnclickLister checkLister = null;

    public AttentionMenuView(Context context) {
        super(context);
    }

    public AttentionMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void displayViews(int selectIndex, final ArrayList<ChannelItem> menuTexts, MenuOnclickLister menuOnclickLister, final MenuCheckOnclickLister checkLister) {
        this.lister = menuOnclickLister;
        this.checkLister = checkLister;
        if (menuTexts == null || menuTexts.size() == 0) {
            return;
        }
        rbs = new RadioButton[menuTexts.size()];

        for (int i = 0; i < rbs.length; i++) {
            rbs[i] = (RadioButton) ContextUtil.getLayoutInflater(getContext()).inflate(R.layout.view_attention_radiobutton, null);
            rbs[i].setId(i);
            rbs[selectIndex].setChecked(true);
            rbs[i].setText(menuTexts.get(i).getChannel_name());
            rbs[i].setHeight(normalHeight);
            rbs[i].setWidth(maxWidth);
            this.addView(rbs[i]);
            rbs[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkLister != null) {
                        checkLister.setMenuCheckOnclickLister();
                    }
                }
            });
        }
        this.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int position) {
                if (lister != null) {
                    lister.setMenuOnclickLister(position, menuTexts.get(position));
                }
            }
        });
    }

    public interface MenuOnclickLister {
        public void setMenuOnclickLister(int position, ChannelItem entity);
    }

    public interface MenuCheckOnclickLister {
        public void setMenuCheckOnclickLister();
    }

    /**
     * normal为true则表示显示正常宽度  TLog.e(TAG, "From: " + from + "=======" + defaultStr);
     TLog.e(TAG, "Message: " + message + "====" + url + "====" + msgid + "----" + title + "----" + abstractStr);
     TLog.e(TAG, "NEWWWW: " + template + "===" + show + "==="+iimgurls+"==="+imgurls);
     * normal为false则表示将menu显示最大宽度
     * @param normal
     */
    public void setMenuWidth(boolean normal, MyScrollView scrollView,int width){
        if (normal){
            scrollView.getLayoutParams().width = normalWidth;
            for (int i = 0; i <rbs.length ; i++) {
                rbs[i].setWidth(normalWidth) ;
            }
        }else{
            scrollView.getLayoutParams().width = width;
            for (int i = 0; i <rbs.length ; i++) {
                rbs[i].setWidth(width);
            }
        }
    }
}
