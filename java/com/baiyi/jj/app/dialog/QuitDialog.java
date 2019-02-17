package com.baiyi.jj.app.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.theme.ThemeUtil;
import com.baiyi.jj.core.basedialog.DialogBase;

/**
 * 退出登录
 */
public class QuitDialog extends DialogBase {
    private QuitOnClickListener listener;
    private TextView tipContent = null;

    private String content;

    public QuitDialog(Context context, int winType, String content) {
        super(context, winType);
        this.content = content;
    }

    public void setTitleContent() {

    }

    @Override
    public void setContainer() {

        this.setCanceledOnTouchOutside(false);

        View view = ContextUtil.getLayoutInflater(getContext()).inflate(R.layout.dialog_quitlogin, null);
        addView(view);

        Button quitCancel = (Button) view.findViewById(R.id.quit_cancel);
        Button quitSure = (Button) view.findViewById(R.id.quit_sure);
        quitCancel.setOnClickListener(viewOnClickListen);
        quitSure.setOnClickListener(viewOnClickListen);

        tipContent = (TextView) view.findViewById(R.id.tip_content);
        tipContent.setText(content);
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

    public void setQuitOnClickListener(QuitOnClickListener listener) {
        this.listener = listener;
    }

    public interface QuitOnClickListener {
        public void onClickListener();
    }


}
