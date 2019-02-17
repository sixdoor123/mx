package com.baiyi.jj.app.views;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.turbo.turbo.mexico.R;


/**
 * 
 * ��һ�仰���ܼ�����<br>
 * ��������ϸ������ ���������
 */
public class SecurityPasswordEditText2 extends LinearLayout {
	private EditText mEditText;
	private ImageView oneTextView;
	private ImageView twoTextView;
	private ImageView threeTextView;
	private ImageView fourTextView;
	private ImageView fiveTextView;
	private ImageView sixTextView;

	private TextView iconView1;
	private TextView iconView2;
	private TextView iconView3;
	private TextView iconView4;
	private TextView iconView5;
	private TextView iconView6;
	LayoutInflater inflater;
	private TextView[] iconViews;
	private ImageView[] imageViews;
	View contentView;

	public SecurityPasswordEditText2(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflater = LayoutInflater.from(context);
		builder = new StringBuilder();
		initWidget();
	}

	private void initWidget() {
		contentView = inflater.inflate(R.layout.edit_box2, null);
		mEditText = (EditText) contentView
				.findViewById(R.id.sdk2_pwd_edit_simple);
		oneTextView = (ImageView) contentView
				.findViewById(R.id.sdk2_pwd_one_img);
		twoTextView = (ImageView) contentView
				.findViewById(R.id.sdk2_pwd_two_img);
		fourTextView = (ImageView) contentView
				.findViewById(R.id.sdk2_pwd_four_img);
		fiveTextView = (ImageView) contentView
				.findViewById(R.id.sdk2_pwd_five_img);
		sixTextView = (ImageView) contentView
				.findViewById(R.id.sdk2_pwd_six_img);
		threeTextView = (ImageView) contentView
				.findViewById(R.id.sdk2_pwd_three_img);

		iconView1 = (TextView) contentView.findViewById(R.id.icon_1);
		iconView2 = (TextView) contentView.findViewById(R.id.icon_2);
		iconView3 = (TextView) contentView.findViewById(R.id.icon_3);
		iconView4 = (TextView) contentView.findViewById(R.id.icon_4);
		iconView5 = (TextView) contentView.findViewById(R.id.icon_5);
		iconView6 = (TextView) contentView.findViewById(R.id.icon_6);
		LinearLayout.LayoutParams lParams = new LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		mEditText.addTextChangedListener(mTextWatcher);
		mEditText.setOnKeyListener(keyListener);
		iconViews = new TextView[] { iconView1, iconView2, iconView3,
				iconView4, iconView5, iconView6 };
		imageViews = new ImageView[] { oneTextView, twoTextView, threeTextView,
				fourTextView, fiveTextView, sixTextView };
		this.addView(contentView, lParams);
	}

	TextWatcher mTextWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void afterTextChanged(Editable s) {

			if (s.toString().length() == 0) {
				return;
			}

			if (builder.length() < 6) {
				builder.append(s.toString());
				setTextValue();
			}
			s.delete(0, s.length());
		}

	};

	OnKeyListener keyListener = new OnKeyListener() {

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {

			if (keyCode == KeyEvent.KEYCODE_DEL
					&& event.getAction() == KeyEvent.ACTION_UP) {
				delTextValue();
				return true;
			}
			return false;
		}
	};

	private void setTextValue() {

		String str = builder.toString();
		int len = str.length();

		if (len <= 6) {
			iconViews[len - 1].setVisibility(View.VISIBLE);
			if(len == 1)
			{
				imageViews[0].setBackgroundResource(R.drawable.member_center_edit2x);
			}else if(len == 6)
			{
				imageViews[len - 1].setBackgroundResource(R.drawable.member_center_edit2x);
			}else
			{
				imageViews[len - 1].setBackgroundResource(R.drawable.member_center_edit2x);
			}

		}
		if (len == 6) {
			// LogUtils.i("�ص�");
			// LogUtils.i("֧������" + str);
			if (mListener != null) {
				mListener.onNumCompleted(str);
			}
			// LogUtils.i("jone", builder.toString());
			// FunctionUtils.hideSoftInputByView(getContext(), mEditText);
		}
	}

	private void delTextValue() {
		String str = builder.toString();
		int len = str.length();
		if (len == 0) {
			return;
		}
		if (len > 0 && len <= 6) {
			builder.delete(len - 1, len);
		}
		iconViews[len - 1].setVisibility(View.INVISIBLE);
		if(len == 1)
		{
			imageViews[0].setBackgroundResource(R.drawable.member_center_edit_selected2x);
		}else if(len == 6)
		{
			imageViews[len - 1].setBackgroundResource(R.drawable.member_center_edit_selected2x);
		}else
		{
			imageViews[len - 1].setBackgroundResource(R.drawable.member_center_edit_selected2x);
		}
	}

	StringBuilder builder;

	public interface SecurityEditCompleListener {
		public void onNumCompleted(String num);
	}

	public SecurityEditCompleListener mListener;

	public void setSecurityEditCompleListener(
			SecurityEditCompleListener mListener) {
		this.mListener = mListener;
	}

	public void clearSecurityEdit() {
		if (builder != null) {
			if (builder.length() == 6) {
				builder.delete(0, 6);
			}
		}
		for (TextView tv : iconViews) {
			tv.setVisibility(View.INVISIBLE);
		}
		for (ImageView img : imageViews) {
			img.setBackgroundResource(R.drawable.member_center_edit_selected2x);
		}
		

	}

	public String getEditNumber() {
		return builder.toString();
	}

	public EditText getSecurityEdit() {
		return this.mEditText;
	}
}