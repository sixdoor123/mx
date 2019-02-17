package com.baiyi.jj.app.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.activity.user.UsLoginAct;
import com.baiyi.jj.app.activity.user.UsRegistAct;
import com.baiyi.jj.core.basedialog.DialogBase;
import com.turbo.turbo.mexico.R;

/**
 * û�е�¼�ĵ�����
 */
public class NoLoginDialog extends DialogBase {

    private Context context = null;
    private String message = null;

    public NoLoginDialog(Context context, String message) {
        super(context, Win_Center);
        this.context = context;
        this.message = message;
    }

    public void setTitleContent() {

    }

    public void setContainer() {

        this.setCanceledOnTouchOutside(false);
        View view = ContextUtil.getLayoutInflater(context).inflate(R.layout.dialog_nologin, null);
        addView(view);

        Button btnSignIn = (Button) view.findViewById(R.id.btn_sign_in);
        Button btnSignUp = (Button) view.findViewById(R.id.btn_sign_up);
        ImageView btnClose = (ImageView) view.findViewById(R.id.img_close);

        TextView txtContent = (TextView) view.findViewById(R.id.nologin_content);
        txtContent.setText(message);

        btnSignIn.setOnClickListener(viewOnClickListen);
        btnSignUp.setOnClickListener(viewOnClickListen);
        btnClose.setOnClickListener(viewOnClickListen);
    }


    public void OnClickListenEvent(View v) {
        if (v.getId() == R.id.img_close) {
            dismiss();
        } else if (v.getId() == R.id.btn_sign_in) {
            Intent intent = new Intent();
            intent.setClass(context, UsLoginAct.class);
            context.startActivity(intent);
            dismiss();
        } else if (v.getId() == R.id.btn_sign_up) {
            Intent intent = new Intent();
            intent.setClass(context, UsRegistAct.class);
            context.startActivity(intent);
            dismiss();
        }
    }


}
