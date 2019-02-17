package com.baiyi.cmall.activities.main._public;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;

/**
 * 价格方式
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-21 上午10:29:15
 */
public class PriceMethodsActivity extends BaseActivity {

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(false);
		initTitle();
	}

	// 自定义标题
	private EventTopTitleView topTitleView;

	/**
	 * 初始化标题栏
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setTitleNewsOnclick(new TitleNewsOnclick() {

			@Override
			public void setTitleNewsOnclickLister(boolean isPop) {

				topTitleView.displayPoP(MsgItemUtil.Pop_Default_txt,
						MsgItemUtil.Pop_Default_img);

			}
		});
		topTitleView.setNewsPopItemOnclick(new NewsPopItemOnclick() {

			@Override
			public void setNewsPopItemOnclickLister(int state) {
				MsgItemUtil.onclickPopItem(state, PriceMethodsActivity.this);
			}
		});
		topTitleView.setEventName("价格方式");
		win_title.addView(topTitleView);
	}
}
