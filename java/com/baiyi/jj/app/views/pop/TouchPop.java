//package com.baiyi.jj.app.views.pop;
//
//import android.content.Context;
//import android.graphics.drawable.ColorDrawable;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.LinearLayout;
//import android.widget.PopupWindow;
//
//import com.baiyi.jj.app.Config;
//import com.baiyi.jj.app.activity.BaseActivity;
//import com.turbo.turbo.mexico.R;
//
///**
// * Created by Administrator on 2017/4/27.
// */
//
//public class TouchPop {
//
//    private Context context = null;
//    private PopupWindow popupWindow = null;
//
//    LinearLayout linearLayout1;
//    LinearLayout linearLayout2;
//    LinearLayout linearLayout3;
//    LinearLayout linearLayout4;
//    LinearLayout linearLayout5;
//
//    private int num = 1;
//
//    public TouchPop(Context context) {
//        this.context = context;
//    }
//
//    public void showPopView(View parent) {
//        View view = LayoutInflater.from(context).inflate(
//                R.layout.layout_touch_pop, null);
//        popupWindow = new PopupWindow(view);
//        popupWindow.setOutsideTouchable(true);
//        popupWindow.setWidth(Config.getInstance().getScreenWidth(context));
//        popupWindow.setHeight(Config.getInstance().getScreenHeight(context));
//        popupWindow.setTouchable(true);
//        popupWindow.setFocusable(true);
//        ColorDrawable dw = new ColorDrawable(0000000000);
//        popupWindow.setBackgroundDrawable(dw);
//        popupWindow.update();
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//
//            @Override
//            public void onDismiss() {
//                backGroundAlpha(1f);
//            }
//        });
//        popupWindow.showAtLocation(parent, Gravity.TOP, 0, 0);
//        initView(view);
//    }
//
//    private void initView(View view) {
//
//        linearLayout1 = (LinearLayout) view.findViewById(R.id.lin_touch1);
//        linearLayout2 = (LinearLayout) view.findViewById(R.id.lin_touch2);
//        linearLayout3 = (LinearLayout) view.findViewById(R.id.lin_touch3);
//        linearLayout4 = (LinearLayout) view.findViewById(R.id.lin_touch4);
//        linearLayout5 = (LinearLayout) view.findViewById(R.id.lin_touch5);
//
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                switch (++num) {
//                    case 1:
//                        linearLayout1.setVisibility(View.VISIBLE);
//                        break;
//                    case 2:
//                        linearLayout1.setVisibility(View.GONE);
//                        linearLayout2.setVisibility(View.VISIBLE);
//                        break;
//                    case 3:
//                        linearLayout2.setVisibility(View.GONE);
//                        linearLayout3.setVisibility(View.VISIBLE);
//                        break;
//                    case 4:
//                        linearLayout3.setVisibility(View.GONE);
//                        linearLayout4.setVisibility(View.VISIBLE);
//                        break;
//                    case 5:
//                        linearLayout4.setVisibility(View.GONE);
//                        linearLayout5.setVisibility(View.VISIBLE);
//                        break;
//
//                    case 6:
//                        linearLayout5.setVisibility(View.GONE);
//                        popupWindow.dismiss();
//                        break;
//                }
//
//            }
//        });
//    }
//
//    private void backGroundAlpha(float alp) {
//        WindowManager.LayoutParams lParams = ((BaseActivity) context)
//                .getWindow().getAttributes();
//        lParams.alpha = alp;
//        ((BaseActivity) context).getWindow().setAttributes(lParams);
//
//    }
//}
