package com.baiyi.jj.app.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.core.basedialog.DialogBase;
import com.turbo.turbo.mexico.R;

/**
 * 切换语言
 */
public class SetOfflineDialog extends DialogBase {
    private LanguageOnClickListener listener;
    private Context context;

    public SetOfflineDialog(Context context, LanguageOnClickListener listener) {
        super(context, Win_Center);
        this.context = context;
        this.listener = listener;
    }

    public void setTitleContent() {
    }

    @Override
    public void setContainer() {

        this.setCanceledOnTouchOutside(false);

        View view = ContextUtil.getLayoutInflater(getContext()).inflate(R.layout.dialog_quitlogin, null);
        addView(view);

        TextView content = (TextView) view.findViewById(R.id.tip_content);
        Button yes = (Button) view.findViewById(R.id.quit_sure);
        Button no = (Button) view.findViewById(R.id.quit_cancel);
        content.setText(R.string.txt_offline_mode);
        yes.setText(R.string.txt_yes);
        no.setText(R.string.txt_no);
        yes.setOnClickListener(viewOnClickListen);
        no.setOnClickListener(viewOnClickListen);
    }

    @Override
    public void OnClickListenEvent(View v) {
        int id = v.getId();
        if (id == R.id.quit_cancel) {
            dismiss();
        } else if (id == R.id.quit_sure) {
            if (listener != null) {
                listener.onClickListener();
            }
            dismiss();
        }
    }

    public interface LanguageOnClickListener {
        public void onClickListener();
    }


}
