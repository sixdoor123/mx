package com.baiyi.jj.app.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.BaseAnalyticsActivity;
import com.baiyi.jj.app.activity.user.net.NetUrl;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.language.SwitchLanguageUtils;
import com.baiyi.jj.app.views.ShareView;
import com.baiyi.jj.core.basedialog.DialogBase;
import com.turbo.turbo.mexico.R;

/**
 * 第三次启动提示分享对话框
 */
public class StartUpThreeToShareDialog extends DialogBase {

    public StartUpThreeToShareDialog(Context context, int winType) {
        super(context, winType);
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
        quitSure.setText(R.string.name_title_share);
        TextView tipContent = (TextView) view.findViewById(R.id.tip_content);
        tipContent.setText(R.string.txt_share_tofriend);
        TextView tipTitle = (TextView) view.findViewById(R.id.txt_title);
        tipTitle.setText(R.string.tip_dialog_title);

        quitCancel.setOnClickListener(viewOnClickListen);
        quitSure.setOnClickListener(viewOnClickListen);
    }

    @Override
    public void OnClickListenEvent(View v) {
        int id = v.getId();
        if (id == R.id.quit_cancel) {
            dismiss();
        } else if (id == R.id.quit_sure) {
            share();
            dismiss();
        }
    }

    private void share() {
        String msgTitle = getContext().getString(R.string.name_jj);
        String url = NetUrl.getShareToFriend();
        String msgContent = getContext().getString(R.string.txt_share_content) + " " + url;
        ShareView.shareMsg(getContext(), msgTitle, msgContent, null);
    }

}
