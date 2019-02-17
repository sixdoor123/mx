package com.baiyi.cmall.activities.main;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiyi.cmall.activities.base.BaseMsgActivity;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 成功界面
 * 
 * @author sunxy
 * 
 */
public class SucceeMainActivity extends BaseMsgActivity implements OnClickListener {

	// 返回供应列表的按钮
	private TextView mBtnSuccessBack;

	// 回退减的图标
	@SuppressWarnings("unused")
	private ImageView mImgSuccessBack;

	// 成功信息
	private TextView mTxtSeccessHint;

	// 选择是哪个的成功界面
	// 0 委托采购 返回TWO
	// 1 委托供应 返回THREE
	// 2供应单界面 返回TWO
	// 3采购单界面 返回THREE
	private int select;

	@Override
	protected void initWin(boolean hasScrollView) {
		super.initWin(false);
		initData();
		initTitle();
		initContent();
	}

	/**
	 * 得到数据
	 */
	private void initData() {
		select = getIntent().getIntExtra("temp", 0);
	}

	/**
	 * 初始化内容
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_delegate_success, null);
		win_content.addView(view);
		mBtnSuccessBack = (TextView) view.findViewById(R.id.btn_success_back);
		mTxtSeccessHint = (TextView) view.findViewById(R.id.tv_success_hint);
		switch (select) {
		case 0:
			mTxtSeccessHint.setText("委托采购成功");
			mBtnSuccessBack.setText("返回供应列表");
			break;
		case 1:
			mTxtSeccessHint.setText("委托供应成功");
			mBtnSuccessBack.setText("返回采购列表");
			break;
		case 2:
			mTxtSeccessHint.setText("供应单提交成功");
			mBtnSuccessBack.setText("返回供应列表");
			break;
		case 3:
			mTxtSeccessHint.setText("采购单提交成功");
			mBtnSuccessBack.setText("返回采购列表");
			break;

		default:
			break;
		}
		mBtnSuccessBack.setOnClickListener(this);
	}

	/**
	 * 标题栏
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		switch (select) {
		case 0:
			topTitleView.setEventName("采购成功");
			break;
		case 1:
			topTitleView.setEventName("供应成功");
			break;
		case 2:
			topTitleView.setEventName("供应单");
			break;
		case 3:
			topTitleView.setEventName("采购单");
			break;

		default:
			break;
		}
		topTitleView.setTitleNewsOnclick(new TitleNewsOnclick() {

			@Override
			public void setTitleNewsOnclickLister(boolean isPop) {

				topTitleView.displayPoP(MsgItemUtil.Pop_Default_txt, MsgItemUtil.Pop_Default_img);

			}
		});
		topTitleView.setNewsPopItemOnclick(new NewsPopItemOnclick() {

			@Override
			public void setNewsPopItemOnclickLister(int state) {
				MsgItemUtil.onclickPopItem(state, SucceeMainActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_success_back:
			back();
			break;
		default:
			break;
		}
	}

	/**
	 * 返回供应(采购)列表的按钮的点击事件
	 */
	private void back() {
		switch (select) {
		case 0:
			backHomePage("THREE");
			break;
		case 1:
			backHomePage("FOUR");
			break;
		case 2:
			backHomePage("THREE");
			break;
		case 3:
			backHomePage("FOUR");
			break;
		default:
			break;
		}
	}

}
