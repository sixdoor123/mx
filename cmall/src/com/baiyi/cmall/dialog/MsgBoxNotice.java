package com.baiyi.cmall.dialog;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 不带按钮 提示dialog
 * @author tangkun
 *
 */
public class MsgBoxNotice extends DialogBase {

	private TextView msgbox_title;
	private TextView msgbox_content;
	private LinearLayout btnTitle = null;
	private LinearLayout btnContent = null;
	
	private String title;
	private String content;
	
	public MsgBoxNotice(Context context, String title,String content, int wintype) {
		super(context, wintype);
		this.title=title;
		this.content=content;
	}

	
	@Override
	public void setTitleContent() {
		
	}

	@Override
	public void setContainer() {
		this.setCanceledOnTouchOutside(true);
		View view = ContextUtil.getLayoutInflater(getContext()).
				inflate(R.layout.dialog_message, null);
		addView(view);
		msgbox_title=(TextView) view.findViewById(R.id.msgbox_title);
		msgbox_content=(TextView) view.findViewById(R.id.msgbox_txt1);
		btnTitle = (LinearLayout)view.findViewById(R.id.btn_title);
		btnContent = (LinearLayout)view.findViewById(R.id.btn_content);
		
		btnTitle.setOnClickListener(viewOnClickListen);
		btnContent.setOnClickListener(viewOnClickListen);
	
		msgbox_title.setText(title);
		msgbox_content.setText(content);
		
	}

	@Override
	public void OnClickListenEvent(View v) {
		dismiss();
	}

}
