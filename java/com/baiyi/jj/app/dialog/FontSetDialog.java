package com.baiyi.jj.app.dialog;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.cache.Dao.ConfigDao;
import com.baiyi.jj.app.theme.FontUtil;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.FontSetView;
import com.baiyi.jj.core.basedialog.DialogBase;
import com.turbo.turbo.mexico.R;

/**
 *  �������õ�����
 */
public class FontSetDialog extends DialogBase {

    private RadioGroup fontGroup = null;
    private FontSetView setView = null;
    private RadioGroup abstractGrop = null;
    private FontSetView abstractView = null;
    private RadioGroup wiftiGroup = null;
    private FontSetView wifiSetView = null;
    // ��������
    private SeekBar seekBar = null;

    private TextView txtTitle = null;
    private ImageView imgBack = null;
    private ImageView btnWebso = null;

    private FontOnItemClick fontItemClickLister = null;

    private AbstractOnItemClick abstractOnItemClick = null;

    private String pageUrl;
    private Activity context = null;

    public FontSetDialog(Activity context, int winType,String pageurl) {
        super(context, winType);
        this.context = context;
        this.pageUrl = pageurl;
    }

    @Override
    public void setContainer() {
        View view = ContextUtil.getLayoutInflater(context).inflate(R.layout.pop_font_set, null);
        addView(view);
        initView(view);
    }

    private void initView(View contentView) {

        fontGroup = (RadioGroup) contentView.findViewById(R.id.pop_font_group);
        abstractGrop = (RadioGroup) contentView.findViewById(R.id.pop_abstract);
        setView = new FontSetView(context, fontGroup, new String[]{
                context.getResources().getString(R.string.txt_theme_small),
                context.getResources().getString(R.string.txt_theme_middle),
                context.getResources().getString(R.string.txt_theme_large),
                context.getResources().getString(R.string.txt_theme_morelarge)}, AccountManager.getInstance().getCurrentSize());
        abstractView = new FontSetView(context, abstractGrop, new String[]{
                context.getResources().getString(R.string.txt_abstract_unshow),
                context.getResources().getString(R.string.txt_abstract_show)}, AccountManager.getInstance().getSummary_Is_Display());
        fontGroup.setOnCheckedChangeListener(new OnCheckChange());

        wiftiGroup = (RadioGroup) contentView.findViewById(R.id.pop_rg_wifibig);
        wifiSetView = new FontSetView(context,wiftiGroup,new String[]{context.getResources().getString(R.string.txt_wifi_hide),
                context.getResources().getString(R.string.txt_wifi_sd), context.getResources().getString(R.string.txt_wifi_hd)}
                ,AccountManager.getInstance().getWifi_Is_Display_Img());

        imgBack = (ImageView) contentView.findViewById(R.id.btn_back_channel_white);
        imgBack.setOnClickListener(viewOnClickListen);
        txtTitle = (TextView) contentView.findViewById(R.id.title_name_channel_white);
        txtTitle.setText(context.getResources().getString(R.string.txt_set));
        btnWebso = (ImageView) contentView.findViewById(R.id.btn_webso);
        btnWebso.setOnClickListener(viewOnClickListen);

        fontGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                AccountManager manager = AccountManager.getInstance();
                if (checkedId == R.id.id_rb_0) {
                    manager.setCurrentSize(FontUtil.Font_Min);
                } else if (checkedId == R.id.id_rb_1) {
                    manager.setCurrentSize(FontUtil.Font_middle);
                } else if (checkedId == R.id.id_rb_2) {
                    manager.setCurrentSize(FontUtil.Font_Max);
                } else if (checkedId == R.id.id_rb_3) {
                    manager.setCurrentSize(FontUtil.Font_Big_Max);
                }
                if (fontItemClickLister != null) {
                    fontItemClickLister.setFontOnItemClickLister(manager.getCurrentSize());
                }
                new ConfigDao(context).updateTheme();
            }
        });
        abstractGrop.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                AccountManager manager = AccountManager.getInstance();
                if(checkedId == R.id.id_rb_0)
                {
                    manager.setSummary_Is_Display(0);
                }else if(checkedId == R.id.id_rb_1)
                {
                    manager.setSummary_Is_Display(1);
                }else if(checkedId == R.id.id_rb_2)
                {
                    manager.setSummary_Is_Display(2);
                }
                new ConfigDao(context).updateTheme();
            }
        });
        wiftiGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                AccountManager manager = AccountManager.getInstance();
                if(checkedId == R.id.id_rb_0)
                {
                    manager.setWifi_Is_Display_Img(0);
                }else if(checkedId == R.id.id_rb_1)
                {
                    manager.setWifi_Is_Display_Img(1);
                }else if(checkedId == R.id.id_rb_2)
                {
                    manager.setWifi_Is_Display_Img(2);
                }
            }
        });

        seekBar = (SeekBar) contentView.findViewById(R.id.seekbar_bright);
        seekBar.setMax(255);
        seekBar.setProgress(Utils.getBright(context));
        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar seekBar) {
                int currPro = seekBar.getProgress();

                if (currPro < 50) {
                    currPro = 50;
                }
                Utils.setBright(context, currPro);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

            }
        });

    }

    public void setFontItemClickLister(FontOnItemClick fontItemClickLister) {
        this.fontItemClickLister = fontItemClickLister;
    }


    public interface FontOnItemClick {
        public void setFontOnItemClickLister(int fontType);
    }

    public void setAbstractOnItemClick(AbstractOnItemClick abstractOnItemClick) {
        this.abstractOnItemClick = abstractOnItemClick;
    }

    public interface AbstractOnItemClick {
        public void setAbstractOnItemClickLister(int fontType);
    }

    class OnCheckChange implements OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            setView.selectItem(checkedId);
        }

    }

    @Override
    public void setTitleContent() {
    }

    @Override
    public void OnClickListenEvent(View v) {
        if (v.getId() == R.id.btn_back_channel_white) {
            dismiss();
        }else if (v.getId() == R.id.btn_webso){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(pageUrl));
            context.startActivity(intent);
        }
    }

}
