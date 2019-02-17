package com.baiyi.cmall.activities.user.other;

import java.io.File;
import android.content.ContextWrapper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.baiyi.cmall.activities.base.BaseMsgActivity;
import com.baiyi.cmall.main.cache.DataCache;
import com.baiyi.cmall.utils.FileSizeUtils;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * ��ջ���
 * 
 * @author sunxy
 * 
 */
public class ClearCacheActivity extends BaseMsgActivity implements
		OnClickListener {

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(false);

		initTitle();
		initContent();
	}

	// �����С
	private TextView mTxtCacheCount;
	// ��ջ��水ť
	private TextView mBtnClearCache;

	/**
	 * ����
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_clear_cache, null);
		win_content.addView(view, getScreenWidth(), getScreenHeight());
		mTxtCacheCount = (TextView) view.findViewById(R.id.txt_cache_count);

		File file = new ContextWrapper(this).getCacheDir();
		mTxtCacheCount.setText(FileSizeUtils.getAutoFileOrFilesSize(file
				.getAbsolutePath()));
		mBtnClearCache = (TextView) view.findViewById(R.id.btn_clear_cache);
		mBtnClearCache.setOnClickListener(this);
	}

	/**
	 * ������
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("��ջ���");
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
				MsgItemUtil.onclickPopItem(state, ClearCacheActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_clear_cache:// ��ջ���
			clearCache();
			break;

		default:
			break;
		}
	}

	/**
	 * �������
	 */
	private void clearCache() {

		new Thread(new Runnable() {
			
			@Override
			public void run() {
				DataCache.getInstence(ClearCacheActivity.this).clear();
			}
		}).start();
		
		mTxtCacheCount.setText("0B");
	}
}
