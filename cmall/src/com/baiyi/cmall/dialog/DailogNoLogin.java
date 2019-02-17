package com.baiyi.cmall.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

public class DailogNoLogin extends DialogBase{

	private Context context = null;
	private String message = null;
	
	public DailogNoLogin(Context context,String message, int wintype) {
		super(context, wintype);
		this.context = context;
		this.message = message;
	}

	public void setTitleContent() {
		
	}

	public void setContainer() {
		View view = ContextUtil.getLayoutInflater(context)
				.inflate(R.layout.dialog_nologin, null);
		addView(view);
		
		Button loginSure = (Button) view.findViewById(R.id.login_sure);
		Button loginCancel = (Button) view.findViewById(R.id.login_cancel);
		
		TextView txtContent = (TextView) view.findViewById(R.id.nologin_content);
		txtContent.setText(message);
	
		loginSure.setOnClickListener(viewOnClickListen);
		loginCancel.setOnClickListener(viewOnClickListen);
	}

	public void OnClickListenEvent(View v) {
		if (v.getId() == R.id.login_sure) {
			Intent intent = new Intent();
//			intent.setClass(context, LoginActivity.class);
			context.startActivity(intent);
			dismiss();
		}else if (v.getId() == R.id.login_cancel) {
			dismiss();
		}
	}
	
	

}
