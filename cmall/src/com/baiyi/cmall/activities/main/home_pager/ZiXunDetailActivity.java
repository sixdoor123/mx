package com.baiyi.cmall.activities.main.home_pager;

import android.widget.TextView;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.home_pager.entity.ZiXunEntity;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.core.util.ContextUtil;

/**
 * 首页
 * 
 * @author lizl
 * 
 */
public class ZiXunDetailActivity extends BaseActivity {

	private EventTopTitleView topTitleView;
	private ZiXunEntity ziXunEntity;

	private TextView mTvTitle;// 题目
	private TextView mTvType;// 类型
	private TextView mTvName;// 名字
	private TextView mTvTime;// 时间
	private TextView mTvContent;// 内容

	@Override
	protected void initWin(boolean hasScrollView) {

		super.initWin(true);
		initTitle();
		initData();
		initView();
		updataData();
	}

	/**
	 * 初始化标题
	 */
	private void initTitle() {

		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("找化塑●快讯●详情");
		win_title.addView(topTitleView);
	}

	private void initData() {
		ziXunEntity = (ZiXunEntity) getIntent().getSerializableExtra("intent");
	}

	/**
	 * 初始化空间
	 */
	private void initView() {

		ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_zixun_detail, win_content);

		mTvTitle = (TextView) findViewById(R.id.tv_zixun_title);
		mTvType = (TextView) findViewById(R.id.tv_zixun_type);
		mTvName = (TextView) findViewById(R.id.tv_zixun_name);
		mTvTime = (TextView) findViewById(R.id.tv_zixun_time);
		mTvContent = (TextView) findViewById(R.id.tv_zixun_content);
	}

	/**
	 * 更新数据
	 */
	private void updataData() {

		mTvTitle.setText(ziXunEntity.getTitle() + ":");
	}
}
