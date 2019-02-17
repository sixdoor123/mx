/**
 * 
 */
package com.baiyi.jj.app.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.views.ShareView;
import com.baiyi.jj.app.views.ShareView.OnFinishClick;
import com.baiyi.jj.app.views.ShareView.OnSinaClick;
import com.baiyi.jj.core.basedialog.DialogBase;

/**
 *  ����
 * @author tangkun
 *
 */
public class ShareDialog extends DialogBase {
	
	private LinearLayout viewParent = null;
	private Button btnCancel = null;
	private ShareView shareView = null;
	private LinearLayout linAdd = null;
	private TextView txtTitle = null;
	private ImageView imgBack = null;

	private String imgUrl = null ;
	
	private OnSinaClickCall sinaClickCall;
	
	/**
	 * @param context
	 * @param winType
	 */
	public ShareDialog(Context context, int winType,int Type,String title,
			String content,String url,String imgUrl) {
		super(context, winType);
		this.imgUrl = imgUrl;
		
		View view = ContextUtil.getLayoutInflater(getContext()).inflate(R.layout.dialog_share, null);
		addView(view);
		
		viewParent = (LinearLayout)findViewById(R.id.view_parent);
		btnCancel = (Button) findViewById(R.id.btn_cancel);
		btnCancel.setOnClickListener(viewOnClickListen);
		
		imgBack = (ImageView) findViewById(R.id.btn_back_channel_white);
		imgBack.setOnClickListener(viewOnClickListen);
		txtTitle = (TextView) findViewById(R.id.title_name_channel_white);
		txtTitle.setText(getContext().getResources().getString(R.string.name_title_share));
		
		linAdd = (LinearLayout) view.findViewById(R.id.lin_add);
		shareView = new ShareView(context, 0, Type, title, content, url, imgUrl);
		shareView.setFinishClick(new OnFinishClick() {
			
			@Override
			public void onClick() {
				dismiss();
			}
		});
		shareView.setSinaClick(new OnSinaClick() {
			
			@Override
			public void onclick() {
				if (sinaClickCall != null) {
					sinaClickCall.onclick();
				}
			}
		});
		linAdd.addView(shareView);

	}

	@Override
	public void setTitleContent() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setContainer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnClickListenEvent(View v) {
		if (v.getId() == R.id.btn_cancel) {
			dismiss();
		}else if (v.getId() == R.id.btn_back_channel_white) {
			dismiss();
		}
		
	}


	public interface OnSinaClickCall{
		public void onclick(); 
	}
	public void setSinaClickCall(OnSinaClickCall sinaClickCall) {
		this.sinaClickCall = sinaClickCall;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (shareView != null)
		{
			shareView.onActivityResult( requestCode,  resultCode,  data);
		}
	}
}
