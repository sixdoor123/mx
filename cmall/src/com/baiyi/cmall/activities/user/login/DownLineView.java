package com.baiyi.cmall.activities.user.login;

import com.baiyi.cmall.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * EditText���»���
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-3-31 ����11:16:07
 */
public class DownLineView extends View {

	private Context context;

	private Paint paint;

	private int screenWidth = 0;
	private int screenHeight = 0;

	private int lineColor = R.color.bg_hui3;

	public DownLineView(Context context) {
		this(context, null);
	}

	public DownLineView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

		init();
	}

	/**
	 * ��ʼ��
	 */
	private void init() {
		paint = new Paint();
		paint.setColor(context.getResources().getColor(R.color.bg_buyer));
	}

	public void setLineColor(int resId) {
		paint.setColor(context.getResources().getColor(resId));
		postInvalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.save();
		screenWidth = getWidth();
		screenHeight = getHeight();

		canvas.drawColor(context.getResources().getColor(R.color.bg_white));
		canvas.drawLine(screenWidth -1, 0, screenWidth -1, screenHeight,
				paint);// �ұ�
		canvas.drawLine(1, 0, 1, screenHeight, paint);// ���
		// canvas.drawLine(0, 0, screenWidth, 0, paint);//�ϱ�
		canvas.drawLine(0, screenHeight - 1, screenWidth, screenHeight - 1,
				paint);// �±�

		// ��������
		canvas.restore();

	}

}
