package com.baiyi.cmall.activities.msg;

import java.util.ArrayList;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.MessageEntity;
import com.baiyi.cmall.entity.UserInfoEntity;
import com.baiyi.cmall.pageviews.MyViewPager;
import com.baiyi.cmall.pageviews.PageView;
import com.baiyi.cmall.pageviews.ViewPagerSelected;
import com.baiyi.cmall.utils.Define;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.cmall.views.pageview.msg.SystemNewsPage;
import com.baiyi.cmall.views.pageview.msg.UserNewsPage;
import com.baiyi.cmall.views.pageview.msg.UserNewsPage.NewsOnItemClick;
import com.baiyi.cmall.views.pageview.msg.UserNewsPage.ReadOnCompelete;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

public class MyMsgListActivity extends BaseActivity implements
		OnCheckedChangeListener {

	private EventTopTitleView titleView = null;
	private MyViewPager viewPager = null;
	private ArrayList<PageView> pageViews = null;

	private RadioGroup newsGroup = null;
	
	private RadioButton userRadio = null;
	private RadioButton systemRadio = null;

	private ImageView userChoose = null;
	private ImageView systemChoose = null;

	private UserNewsPage userNewsPage = null;
	private SystemNewsPage systemNewsPage = null;

	private FrameLayout newsLayout = null;
	private TextView newsCount;

	public static final int Result_User = 99;
	public static final int Result_System = 88;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(false);

		titleView = new EventTopTitleView(this, true);
		titleView.setEventName("我的消息");
		win_title.addView(titleView);

		View contentView = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_message_list, null);
		win_content.addView(contentView);

		findTitleViews();

		findViews();

		initViewPager();

	}

	private void findTitleViews() {
		newsCount = (TextView) findViewById(R.id.news_count);
		newsLayout = (FrameLayout) findViewById(R.id.msg_layout);
		newsLayout.setVisibility(View.VISIBLE);
		titleView.setTitleNewsOnclick(new TitleNewsOnclick() {

			@Override
			public void setTitleNewsOnclickLister(boolean isPop) {

				titleView.displayPoP(MsgItemUtil.Pop_Msg_Txt, MsgItemUtil.Pop_Msg_Img);

			}
		});
		titleView.setNewsPopItemOnclick(new NewsPopItemOnclick() {

			@Override
			public void setNewsPopItemOnclickLister(int state) {
				onclickPopItem(state);
			}
		});
	}
	
	private void onclickPopItem(int state) {
		if (state == 0) {
			userNewsPage.setReadAll(new ReadOnCompelete() {

				public void setReadOnCompeleteLister(boolean isCompelete) {
					// TODO Auto-generated method stub
					if (isCompelete) {
						CmallApplication.clearUserNews();
						UserInfoEntity user = CmallApplication.getUserInfoEntity();
						setNews(newsCount, user.getUserUnReadCount());
					}

				}
			});
		}  else if (state == 1) { //首页
			
			ActivityStackControlUtil.finishProgram();
			 
		}
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		super.onRefresh();
		displayNewsCount();
	}

	public void displayNewsCount() {
		UserInfoEntity user = CmallApplication.getUserInfoEntity();
		if (user != null) {
			setNews(newsCount, user.getUserUnReadCount());
		}
	}


	private void findViews() {
		viewPager = (MyViewPager) findViewById(R.id.news_viewpager);

		newsGroup = (RadioGroup) findViewById(R.id.news_group);
		
		userRadio = (RadioButton)findViewById(R.id.news_user);
		systemRadio = (RadioButton) findViewById(R.id.news_system);
		newsGroup.setOnCheckedChangeListener(this);

		userChoose = (ImageView) findViewById(R.id.news_user_choose);
		systemChoose = (ImageView) findViewById(R.id.news_system_choose);
	}

	/**
	 * 初始化ViewPager
	 */
	private void initViewPager() {
		pageViews = new ArrayList<PageView>();

		userNewsPage = new UserNewsPage(this);
		userNewsPage.setNewsOnItemClick(new NewsOnItemClick() {

			@Override
			public void setNewsOnItemClick(int position, MessageEntity entity) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MyMsgListActivity.this,
						MessageItemDetailActivity.class);
				intent.putExtra(Define.NewsPlatState, Result_User);
				intent.putExtra(Define.NewsEntity, entity);
				intent.putExtra(Define.ITEM_POSTION, position);
				startActivityForResult(intent, Result_User);

			}
		});
		systemNewsPage = new SystemNewsPage(this);
		pageViews.add(userNewsPage);
		pageViews.add(systemNewsPage);


		viewPager.init(pageViews, 0);
		viewPager.setViewPageSelectedLister(new ViewPagerSelected() {
			
			@Override
			public void onPageSelected(int currentNum) {
				// TODO Auto-generated method stub
				setButtonPerformClick(currentNum);
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Result_User) {
			String messageId = data.getStringExtra(Define.NewsMessageId);
			if (!Utils.isStringEmpty(messageId)) {
				int itemPosition = data.getIntExtra(Define.ITEM_POSTION, 0);
				userNewsPage.notifyDataSetChanged(itemPosition);

			}
		} else if (requestCode == Result_System) {

		}
	}

	public void setButtonPerformClick(int position) {
		if (position == 0) {
			userRadio.performClick();
			newsLayout.setVisibility(View.VISIBLE);
//			btnHomes.setVisibility(View.GONE);

		} else if (position == 1) {
			systemRadio.performClick();
			newsLayout.setVisibility(View.VISIBLE);
//			btnHomes.setVisibility(View.GONE);
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		int id = 0;
		if (checkedId == R.id.news_user) {
			id = 0;
			userChoose.setVisibility(View.VISIBLE);
			systemChoose.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.news_system) {
			id = 1;
			userChoose.setVisibility(View.INVISIBLE);
			systemChoose.setVisibility(View.VISIBLE);
		}
		viewPager.setPageIndex(id);
		viewPager.setCurrentItem(id);
	}

}
