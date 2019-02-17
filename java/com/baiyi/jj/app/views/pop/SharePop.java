package com.baiyi.jj.app.views.pop;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.views.ShareView;
import com.baiyi.jj.app.views.ShareView.OnFinishClick;

/**
 *  ��������
 * @author Administrator
 *
 */
public class SharePop extends BasePopWindow{
	
	private LinearLayout viewParent = null;
	private Button btnCancel = null;
	private ShareView shareView = null;
	private LinearLayout linAdd = null;


	public SharePop(View contentView, final Activity context,int height,int Type,String title,
			String content,String url,String imgUrl) {
		super(contentView,context, height,context.getResources().getString(R.string.name_title_share));
		// TODO Auto-generated constructor stub
		
		popupWindow.setAnimationStyle(R.style.popwindow_anim);
		btnCancel = (Button) contentView.findViewById(R.id.btn_cancel);
		btnCancel.setOnClickListener(viewOnClickListen);
		linAdd = (LinearLayout) contentView.findViewById(R.id.lin_add);
		shareView = new ShareView(context, height, Type, title, content, url, imgUrl);
		shareView.setFinishClick(new OnFinishClick() {
			
			@Override
			public void onClick() {
				dismiss();
				
			}
		});
		linAdd.addView(shareView);
	}
	
	/**
	 *  ����¼�
	 */
	OnClickListener viewOnClickListen = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.btn_cancel) {
				dismiss();
			}
			
		}
	};


	@Override
	public void showPop(ViewGroup parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dismissPop(ViewGroup parent) {
		// TODO Auto-generated method stub
		
	}

}
