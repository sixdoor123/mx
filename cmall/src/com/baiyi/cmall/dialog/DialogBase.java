package com.baiyi.cmall.dialog;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public abstract class DialogBase extends Dialog implements DialogInterface {

	private Window window = null;
	private int winType;

	public static final int Win_Bottom = 1;
	public static final int Win_Center = 2;
	public static final int Win_Top = 3;
	public static final int Win_bottom_fill = 4;
	//���µ���
	public static final int AnimalBottom = 0;
	//���ϵ���
	public static final int AnimalTop = 1;
	
	public static final float widthPren = 0.8f;
	public static final float widthBottom = 0.34f;

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
		if(animType == -1)
		{
			return;
		}
		window = getWindow(); // �õ��Ի���
		
		window.setWindowAnimations(animType == 0 ? R.style.popwindow_anim_bottom : R.style.popwindow_anim); // ���ô��ڵ�������
		window.setBackgroundDrawableResource(R.color.transparent); // ���öԻ��򱳾�Ϊ͸��
		WindowManager.LayoutParams wl = window.getAttributes();
		setCanceledOnTouchOutside(true);
		// ����x��y�������ô�����Ҫ��ʾ��λ��
		wl.x = x; // xС��0���ƣ�����0����
		wl.y = y; // yС��0���ƣ�����0����
		// wl.alpha = 0.6f; //����͸����
		// wl.gravity = Gravity.BOTTOM; //��������
		window.setAttributes(wl);
	}

	public void setWindowParams() {
		WindowManager.LayoutParams p = window.getAttributes(); // ��ȡ�Ի���ǰ�Ĳ���ֵ
		int width = Config.getInstance().getScreenWidth(getContext());
		if (winType == Win_Top) {
			p.width = width;
		} else if (winType == Win_Center) {
			p.width = (int) (width * widthPren);
		}else if(winType == Win_Bottom)
		{
			p.width = (int) (width * widthBottom);
		}else if(winType == Win_bottom_fill)
		{
			p.width = width;
		}
		window.setAttributes(p);
		if (winType == Win_Bottom || winType == Win_bottom_fill) {
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
	public void showDialogAnimal(int animType)
	{
		windowDeploy(0, 0, animType);
		show();
	}
	/**
	 * 
	 * @param animType 0:���µ��� 1�����ϵ���
	 */
	public void showDialog(int animType, int x, int y)
	{
		windowDeploy(x, y, animType);
		show();
	}
	public void showDialog(int offx,int animType)
	{
//		windowDeploy(offx, 0, animType);
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
}