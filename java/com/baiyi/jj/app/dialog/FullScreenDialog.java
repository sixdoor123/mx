package com.baiyi.jj.app.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.core.basedialog.DialogBase;
import com.turbo.turbo.mexico.R;

/**
 * 切换语言
 */
public class FullScreenDialog extends DialogBase {

    private Activity activity;
    public FullScreenDialog(Context context, Activity activity) {
        super(context, Win_Center);
        this.activity = activity;
    }

    public void setTitleContent() {
    }

    @Override
    public void setContainer() {

        this.setCanceledOnTouchOutside(false);

        View view = ContextUtil.getLayoutInflater(getContext()).inflate(R.layout.pop_fullscreen, null);
        addView(view);

        ImageView imageView = (ImageView) view.findViewById(R.id.img_sss);
        int abimgW = Config.getInstance().getScreenWidth(activity);
        int abimgH = Config.getInstance().getHasVirtualKey(activity);
        imageView.getLayoutParams().height = abimgH;
        imageView.getLayoutParams().width = abimgW;
//
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.show();
//        Window window = dialog.getWindow();
//        window.getDecorView().setPadding(0, 0, 0, 0);
//        window.setGravity(Gravity.CENTER);
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = WindowManager.LayoutParams.FILL_PARENT;
//        lp.height = WindowManager.LayoutParams.FILL_PARENT;
//        window.setAttributes(lp);

    }

    @Override
    public void OnClickListenEvent(View v) {

    }

}
