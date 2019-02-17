package com.baiyi.jj.core.basedialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.baiyi.jj.app.activity.BaseActivity;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.Config;

public abstract class DialogBase extends Dialog implements DialogInterface {

    private Window window = null;
    private int winType;

    public static final int Win_Bottom = 1;
    public static final int Win_Center = 2;
    public static final int Win_Top = 3;

    public static final int AnimalTop = 1;

    public static final float widthPren = 0.8f;

    public DialogBase(Context context, int winType) {
        super(context, R.style.my_dialog);
        window = getWindow();
        this.winType = winType;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContent();
        setContainer();
        setWindowParams();
    }

    public void windowDeploy(int x, int y, int animType) {
        if (animType == -1) {
            return;
        }
        window.setWindowAnimations(animType == 0 ? R.style.popwindow_anim_bottom : R.style.popwindow_anim);
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams wl = window.getAttributes();
        setCanceledOnTouchOutside(true);
        wl.x = x;
        wl.y = y;
        // wl.alpha = 0.6f;
        // wl.gravity = Gravity.BOTTOM;
        window.setAttributes(wl);
    }

    public void setWindowParams() {
        WindowManager.LayoutParams p = window.getAttributes(); // ��ȡ�Ի���ǰ�Ĳ���ֵ
        int width = Config.getInstance().getScreenWidth(getContext());
        if (winType == Win_Bottom || winType == Win_Top) {
            p.width = width;
        } else if (winType == Win_Center) {
            p.width = (int) (width * widthPren);
        }
        window.setAttributes(p);
        if (winType == Win_Bottom) {
            window.setGravity(Gravity.BOTTOM);
        } else if (winType == Win_Top) {
            window.setGravity(Gravity.TOP);
        }
    }

    public void addView(View view) {
        if (window == null) {
            return;
        }
        window.setContentView(view);
    }

    public void showDialogAnimal(int animType) {
        windowDeploy(0, 0, animType);
        show();
    }

    public void showDialog(int animType) {
        window.setBackgroundDrawableResource(R.color.transparent);
        show();
    }

    @Override
    public void dismiss() {
        // TODO Auto-generated method stub
        super.dismiss();
    }

    protected void onStop() {
    }

    public android.view.View.OnClickListener viewOnClickListen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            OnClickListenEvent(v);
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

}