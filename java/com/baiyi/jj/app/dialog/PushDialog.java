package com.baiyi.jj.app.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.activity.NewsLocalAct;
import com.baiyi.jj.app.theme.ThemeUtil;
import com.baiyi.jj.app.utils.ArticleHistoryUtils;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.core.basedialog.DialogBase;
import com.turbo.turbo.mexico.R;

public class PushDialog extends DialogBase {
    private Context context;
    private LinearLayout viewParent;
    private TextView tipContent = null;
    private TextView tipTitle = null;

    private String articleId;
    private String title;
    private String pageUrl;
    private String articleType;

    public PushDialog(Context context, String articleid, String title, String pageUrl,String artileType) {
        super(context, Win_Center);
        this.context = context;
        this.articleId = articleid;
        this.title = title;
        this.pageUrl = pageUrl;
        this.articleType = artileType;
    }

    //����dialog����
    public void setTitleContent() {

    }

    //�Զ���dialog content
    @Override
    public void setContainer() {
        this.setCanceledOnTouchOutside(false);
        View view = ContextUtil.getLayoutInflater(getContext()).inflate(R.layout.dialog_push, null);
        addView(view);
        viewParent = (LinearLayout) view.findViewById(R.id.view_parent);

        Button quitCancel = (Button) view.findViewById(R.id.btn_cancel);
        Button quitSure = (Button) view.findViewById(R.id.btn_ok);

        quitCancel.setOnClickListener(viewOnClickListen);

        quitSure.setOnClickListener(viewOnClickListen);
        tipContent = (TextView) view.findViewById(R.id.tip_content);
        tipContent.setText(title);
    }



    @Override
    public void OnClickListenEvent(View v) {
        int id = v.getId();
        if (id == R.id.quit_cancel) {
            dismiss();
        } else if (id == R.id.quit_sure) {
            Intent intent = new Intent(context, NewsLocalAct.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(Define.ArticleUrl, pageUrl);
            intent.putExtra(Define.ArticleId, articleId);
            intent.putExtra(Define.ArticleTitle, title);
            intent.putExtra(Define.ArticleType, articleType);
            dismiss();
        }
    }


}

