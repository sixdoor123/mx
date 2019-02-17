package com.baiyi.cmall.activities.user.help;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.baiyi.cmall.activities.base.BaseMsgActivity;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * ��������
 * 
 * @author sunxy
 * 
 */
public class HelperCenterActivity extends BaseMsgActivity implements
		OnClickListener {

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(false);
		initTitle();
		initContent();
	}

	// ֧������
	private LinearLayout mLlPayHelp;
	// �������
	private LinearLayout mLlFeedBack;
	// �������
	private LinearLayout mLlInviteFriend;

	/**
	 * ����
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_helpe_content, null);
		win_content.addView(view);
		mLlFeedBack = (LinearLayout) view.findViewById(R.id.ll_item_two);
		mLlPayHelp = (LinearLayout) view.findViewById(R.id.ll_item_one);
		mLlInviteFriend = (LinearLayout) view.findViewById(R.id.ll_item_three);

		mLlFeedBack.setOnClickListener(this);
		mLlPayHelp.setOnClickListener(this);
		mLlInviteFriend.setOnClickListener(this);

	}

	/**
	 * ������
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("��������");
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
				MsgItemUtil.onclickPopItem(state, HelperCenterActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_item_one:// ֧�ְ���
			goActivity(SupportHelpeActivity.class);
			break;
		case R.id.ll_item_two:// �������
			goActivity(FeedBackActivity.class);
			break;
		case R.id.ll_item_three:// �������
			goActivity(ShareActivity.class);
			break;

		default:
			break;
		}
	}

}
