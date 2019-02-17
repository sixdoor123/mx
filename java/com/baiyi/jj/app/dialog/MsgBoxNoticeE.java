package com.baiyi.jj.app.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.theme.ThemeUtil;
import com.baiyi.jj.core.basedialog.DialogBase;

public class MsgBoxNoticeE extends DialogBase {
	
	private LinearLayout viewParent;
	private TextView msgbox_title;
	private TextView msgbox_content;
	private Button msgbox_btn_ok;
	private Button msgbox_btn_canel;
	
	private String title;
	private String content;
	
	
	private MsgBoxEOnClickListener listener;

	public MsgBoxNoticeE(Context context, String title,String content, int wintype) {
		super(context, wintype);
		this.title=title;
		this.content=content;
	}

	
	@Override
	public void setTitleContent() {
		
	}

	
	@Override
	public void setContainer() {

		View view = ContextUtil.getLayoutInflater(getContext()).inflate(R.layout.msgbox_message_e, null);
		addView(view);
		viewParent = (LinearLayout)view.findViewById(R.id.view_parent);
		msgbox_title=(TextView) view.findViewById(R.id.msgbox_title);
		msgbox_content=(TextView) view.findViewById(R.id.msgbox_txt1);
		msgbox_btn_ok = (Button) view.findViewById(R.id.btn_ok);
		msgbox_btn_canel = (Button)view.findViewById(R.id.btn_canel);
	
		msgbox_title.setText(title);
		msgbox_content.setText(content);
		
		msgbox_btn_ok.setOnClickListener(viewOnClickListen);
		msgbox_btn_canel.setOnClickListener(viewOnClickListen);
//		setChangTheme(getParentView());
	}

	public LinearLayout getParentView()
	{
		if(viewParent != null)
		{
			if(ThemeUtil.getAppTheme() == ThemeUtil.Theme_day)
			{
				viewParent.setBackgroundResource(R.drawable.search_eidt);
			}else
			{
//				viewParent.setBackgroundResource(R.drawable.night_search_eidt);
			}
		}
		return viewParent;
	}

	@Override
	public void OnClickListenEvent(View v) {
		int id = v.getId();
		if(id == R.id.btn_ok)
		{
			if(listener!=null){
				listener.onClickListener();
			}
			dismiss();
		}else if(id == R.id.btn_canel)
		{
			dismiss();
		}
	}
	
	public void setMsgOnClickListener(MsgBoxEOnClickListener listener){
		this.listener = listener;
	}
	
	public interface MsgBoxEOnClickListener{
		public void onClickListener();
	}

}
