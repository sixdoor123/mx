package com.baiyi.jj.app.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import com.baiyi.core.file.Preference;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.listitem.news.ListItemBaseNews;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TextLengthUtils {

    private static TextLengthUtils instance = null;
    private static final int OFFSET = 150;
    private int screenWidth;//��Ļ���
    private int imgW;//ͼƬ���

    private BaseActivity context;

    private TextLengthUtils(Context context) {
        this.context = (BaseActivity) context;
        screenWidth = this.context.getScreenWidth();
        imgW = (screenWidth - ContextUtil.dip2px(context, ListItemBaseNews.ThreeImg_Dip_Offset)) / 3;
    }

    public static TextLengthUtils getInstance(Context context) {
        if (instance == null) {
            instance = new TextLengthUtils(context);
        }
        return instance;
    }

    public int getStringLeg(int type) {
        if (0 == type) {
            int StringLength = screenWidth - imgW - context.getDensity_int(15 + 15 + 10);
            return StringLength * 3 - OFFSET;
        } else if (1 == type) {
            int StringLength = screenWidth - context.getDensity_int(15 + 15);
            return StringLength * 3 - OFFSET;
        } else if (2 == type) {
            int StringLength = screenWidth - context.getDensity_int(15 + 15);
            return StringLength * 2 - OFFSET;
        }
        return 0;
    }

    public void setChar(TextView textView, String s, int type) {
        CharSequence charSequence = TextUtils.ellipsize(s,
                textView.getPaint(), getStringLeg(type), TextUtils.TruncateAt.END);
        textView.setText(charSequence);
    }

    public static String getAssetsStr(Context context,String assetStr){
        String textStr="";
        try {
            InputStream is = context.getResources().getAssets().open(assetStr);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuffer stringBuffer = new StringBuffer();
            String str = null;
            while((str = br.readLine())!=null){
                stringBuffer.append(str);
            }
            textStr = stringBuffer.toString();
//            TLog.e("1111111",textStr);
//            InputStreamReader inputReader = new InputStreamReader( context.getResources().getAssets().open(assetStr) );
//            BufferedReader bufReader = new BufferedReader(inputReader);
//            String line="";
//            textStr = "";
//            while((line = bufReader.readLine()) != null)
//                textStr += line;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return textStr;
    }

}
