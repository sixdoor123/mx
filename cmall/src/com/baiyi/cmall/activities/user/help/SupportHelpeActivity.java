package com.baiyi.cmall.activities.user.help;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 支持帮助界面
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-27 下午3:20:26
 */
public class SupportHelpeActivity extends BaseActivity {

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(false);
		initTitle();
		initContent();

	}

	/**
	 * 初始化内容
	 */
	private void initContent() {
		ContextUtil.getLayoutInflater(this)
	.inflate(R.layout.activity_support_helper, win_content);
	}

	// 自定义标题栏
	protected EventTopTitleView topTitleView = null;

	/**
	 * 标题栏
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("支持帮助");
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
				MsgItemUtil.onclickPopItem(state, SupportHelpeActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}
}
