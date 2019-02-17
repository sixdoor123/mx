package com.baiyi.cmall.activities.main.home_pager;

import android.widget.TextView;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.home_pager.entity.ZiXunEntity;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.core.util.ContextUtil;

/**
 * ��ҳ
 * 
 * @author lizl
 * 
 */
public class ZiXunDetailActivity extends BaseActivity {

	private EventTopTitleView topTitleView;
	private ZiXunEntity ziXunEntity;

	private TextView mTvTitle;// ��Ŀ
	private TextView mTvType;// ����
	private TextView mTvName;// ����
	private TextView mTvTime;// ʱ��
	private TextView mTvContent;// ����

	@Override
	protected void initWin(boolean hasScrollView) {

		super.initWin(true);
		initTitle();
		initData();
		initView();
		updataData();
	}

	/**
	 * ��ʼ������
	 */
	private void initTitle() {

		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("�һ��ܡ��Ѷ������");
		win_title.addView(topTitleView);
	}

	private void initData() {
		ziXunEntity = (ZiXunEntity) getIntent().getSerializableExtra("intent");
	}

	/**
	 * ��ʼ���ռ�
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
	 * ��������
	 */
	private void updataData() {

		mTvTitle.setText(ziXunEntity.getTitle() + ":");
	}
}
