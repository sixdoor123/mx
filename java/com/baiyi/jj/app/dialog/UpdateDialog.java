package com.baiyi.jj.app.dialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baiyi.core.file.Preference;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.utils.XMLName;
import com.baiyi.jj.core.basedialog.DialogBase;
import com.turbo.turbo.mexico.R;

/**
 * Created by Administrator on 2017/7/3 0003.
 */
public class UpdateDialog extends DialogBase{


    Context context;
    String verStr;

    public UpdateDialog(Context context,String newVerion, int winType) {
        super(context, winType);
        this.context = context;
        this.verStr = newVerion;
    }

    @Override
    public void setTitleContent() {

    }

    @Override
    public void setContainer() {
        this.setCanceledOnTouchOutside(false);

        View view = ContextUtil.getLayoutInflater(getContext()).inflate(R.layout.dialog_update, null);
        addView(view);

        TextView txtVerion = (TextView) view.findViewById(R.id.tip_content);
        txtVerion.setText(context.getResources().getString(R.string.tip_dialog_updatetitle)+verStr);
        TextView buttonUpdate = (TextView) view.findViewById(R.id.txt_nowupdate);
        TextView buttonSkip = (TextView) view.findViewById(R.id.txt_skip);
        TextView buttonCancel = (TextView) view.findViewById(R.id.txt_cancel);
        buttonUpdate.setOnClickListener(viewOnClickListen);
        buttonSkip.setOnClickListener(viewOnClickListen);
        buttonCancel.setOnClickListener(viewOnClickListen);
    }

    @Override
    public void OnClickListenEvent(View v) {
        if (v.getId() == R.id.txt_nowupdate){
            String packageName = context.getPackageName();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id="+packageName));
            context.startActivity(intent);

        }else if (v.getId() == R.id.txt_skip){
            Preference preference = Preference.getInstance();
            String currentVer = Config.getInstance().getVersionName(context);
            preference.Set(XMLName.XML_SkipVerion+currentVer,String.valueOf(true));
            preference.saveConfig();
        }else if (v.getId() == R.id.txt_cancel){
        }
        dismiss();
    }
}
