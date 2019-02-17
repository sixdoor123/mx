package com.baiyi.cmall.activities.base;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.views.itemview.EventTopTitleView;

/**
 * @author tangkun
 *
 */
public class BaseMsgActivity extends BaseActivity {
	// 自定义标题栏
	protected EventTopTitleView topTitleView = null;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setMsg();
	}

	public void setMsg() {
		if (topTitleView != null) {
			topTitleView.setMsg();
		}
	}
}
