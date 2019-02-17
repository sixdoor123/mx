package com.baiyi.cmall.views.itemview;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * �Զ����RadioButton
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-2 ����11:46:58
 */
public class MyRadioButton extends LinearLayout {

	public MyRadioButton(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public MyRadioButton(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	// ɽ���Ķ���
	private Context context;

	public MyRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		initView();
	}

	/**
	 * ��ʼ������
	 */
	private void initView() {
		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.item_radio_button, null);
		addView(view);

		// �ҿؼ�
		findViewById(view);
	}

	// ѡ���������
	private TextView mTxtName;
	// ѡ�����ͼ��
	private ImageView mImgChoice;

	/**
	 * �ҿؼ�
	 * 
	 * @param view
	 */
	private void findViewById(View view) {
		mImgChoice = (ImageView) view.findViewById(R.id.img_choice);
		mTxtName = (TextView) view.findViewById(R.id.txt_name);

	}

	/**
	 * ����ѡ�����
	 * 
	 * @param mTxtName
	 *            the mTxtName to set
	 */
	public void setmTxtName(String mName) {
		this.mTxtName.setText(mName);
	}

	/**
	 * ��ȡѡ�����
	 * 
	 * @return
	 */
	public String getmTxtName() {
		return mTxtName.getText().toString().trim();
	}

	/**
	 * ����ѡ�������ɫֵ
	 * 
	 * @param mName
	 */
	public void setmTxtNameColor(int colorId) {
		this.mTxtName.setTextColor(context.getResources().getColor(colorId));
	}

	/**
	 * ����ͼ�귽��
	 * 
	 * @param mImgChoice
	 *            the mImgChoice to set
	 */
	public void setmImgChoice(int resId) {
		this.mImgChoice.setBackgroundResource(resId);
	}

}
