package com.baiyi.cmall.activities.main._public;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.baiyi.cmall.dialog.DialogBase;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 自动选择提示框 不带底部确认按钮
 * 
 * @author lizl
 * 
 */
public class AutoSelectView extends DialogBase implements
		OnCheckedChangeListener {

	// 标题控件
	private TextView msgbox_title;
	// 选择内容
	private RadioGroup mRgGroup; 
	// 标题
	private String title;
	@SuppressWarnings("unused")
	private Context context;
	private View view;

	public AutoSelectView(Context context, String title, int gravity) {
		super(context, gravity);
		this.title = title;
		this.context = context;
	}

	private RadioButton shi;
	private RadioButton fou;

	@Override
	public void setTitleContent() {
	}

	/**
	 * 设置内容信息
	 */
	@SuppressLint("InflateParams")
	@Override
	public void setContainer() {
		Log.d("TT", "setContainer");
		view = ContextUtil.getLayoutInflater(getContext()).inflate(
				R.layout.auto_select_dialog_message, null);
		this.addView(view);
		msgbox_title = (TextView) view.findViewById(R.id.msgbox_title);
		mRgGroup = (RadioGroup) view.findViewById(R.id.rg_content);
		msgbox_title.setText(title);
		mRgGroup.setOnCheckedChangeListener(this);
		shi = (RadioButton) view.findViewById(R.id.rb_shi);
		fou = (RadioButton) view.findViewById(R.id.rb_fou);

	}

	/**
	 * 设置默认的radio被选中
	 * 
	 * @param defaultText
	 */
	public void setDefault(String defaultText) {

		Log.d("TT", "setDefault");
		if (defaultText.equals(shi.getText().toString())) {

			shi.setChecked(true);
		} else if (defaultText.equals(fou.getText().toString())) {

			fou.setChecked(true);
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		RadioButton button = (RadioButton) group.findViewById(checkedId);
		button.setOnClickListener(viewOnClickListen);
		listener.onClickListener(button.getText().toString());

	}

	private AutoOnClickListener listener;

	public void setInfoOnClickListener(AutoOnClickListener listener) {
		this.listener = listener;
	}

	public interface AutoOnClickListener {
		public void onClickListener(String content);
	}

	@Override
	public void OnClickListenEvent(View v) {

		this.dismiss();
	}

}
