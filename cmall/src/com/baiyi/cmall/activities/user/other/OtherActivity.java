package com.baiyi.cmall.activities.user.other;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.cmall.activities.base.BaseMsgActivity;
import com.baiyi.cmall.activities.user.login.ExitLogin;
import com.baiyi.cmall.activities.user.login.web.MemberCenterWebActivity;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 其他
 * 
 * @author sunxy
 * 
 */
public class OtherActivity extends BaseMsgActivity implements OnClickListener {

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(false);

		initTitle();
		initContent();
	}

	// 条目标题
	private TextView mTxtItemOne;
	private TextView mTxtItemTwo;
	private TextView mTxtItemThree;
	// 控制条目的线性布局
	private LinearLayout mLlItemOne;
	private LinearLayout mLlItemTwo;
	private LinearLayout mLlItemThree;

	/**
	 * 内容
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_helpe_content, null);
		win_content.addView(view);

		mTxtItemOne = (TextView) view.findViewById(R.id.txt_item_one);
		mTxtItemOne.setText("清空缓存");
		mTxtItemTwo = (TextView) view.findViewById(R.id.txt_item_two);
		mTxtItemTwo.setText("检查新版本");
		mTxtItemThree = (TextView) view.findViewById(R.id.txt_item_three);
		mTxtItemThree.setText("关于CMALL");

		mLlItemOne = (LinearLayout) view.findViewById(R.id.ll_item_one);
		mLlItemOne.setOnClickListener(this);
		mLlItemTwo = (LinearLayout) view.findViewById(R.id.ll_item_two);
		mLlItemTwo.setOnClickListener(this);
		mLlItemThree = (LinearLayout) view.findViewById(R.id.ll_item_three);
		mLlItemThree.setVisibility(View.VISIBLE);
		mLlItemThree.setOnClickListener(this);
		
		LinearLayout mLinTuiChu = (LinearLayout) view.findViewById(R.id.ll_tuichu);
		mLinTuiChu.setVisibility(View.VISIBLE);
		mLinTuiChu.setOnClickListener(this);
		
	}

	/**
	 * 标题栏
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("其他");
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
				MsgItemUtil.onclickPopItem(state, OtherActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_item_one:// 清空缓存
			goActivity(ClearCacheActivity.class);
			break;
		case R.id.ll_item_two:// 检查版本更新
			goActivity(VersionUpdateActivity.class);
			break;
		case R.id.ll_item_three:// 关于DMALL
			goActivity(AboutCMALLActivity.class);
			break;
		case R.id.ll_tuichu:// 退出
			ExitLogin.getInstence(this).cleer();
			displayToast("退出成功");
			break;
		default:
			break;
		}
	}
}
