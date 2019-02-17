package com.baiyi.cmall.activities.user.merchant.product.detail;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.mall.MallDefine;
import com.baiyi.cmall.views.itemview.EventTopTitleView;

/**
 * 评论列表
 * 
 * @author sunxy
 */
public class EvaluateMaintainActivity extends BaseActivity {

	private String id;

	@Override
	protected void initWin(boolean hasScrollView) {
		super.initWin(false);

		initData();
		initTitle();
	}

	private void initData() {
		id = getIntent().getStringExtra(MallDefine.Id);
	}

	private EventTopTitleView topTitleView = null;

	/**
	 * 标题
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("评论列表");
		win_title.addView(topTitleView);
	}
}
