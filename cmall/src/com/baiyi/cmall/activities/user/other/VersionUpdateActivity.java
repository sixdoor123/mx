package com.baiyi.cmall.activities.user.other;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.baiyi.cmall.activities.base.BaseMsgActivity;
import com.baiyi.cmall.entity.AppVersionInfo;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * �汾����
 * 
 * @author sunxy
 * 
 */
public class VersionUpdateActivity extends BaseMsgActivity implements
		OnClickListener {

	private AppVersionInfo versionInfo;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(false);
		getAppInfo();
		initTitle();
		initContent();
	}

	// ��ǰ�汾
	private TextView mTxtCurrentVersion;
	// ���°汾
	private TextView mTxtLastestVersion;
	// �������ذ�ť
	private TextView mBtnReLoad;

	/**
	 * ����
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_version_update, null);
		win_content.addView(view, getScreenWidth(), getScreenHeight());
		mBtnReLoad = (TextView) view.findViewById(R.id.btn_reload);
		mBtnReLoad.setOnClickListener(this);
		mTxtCurrentVersion = (TextView) view
				.findViewById(R.id.txt_current_version);
		mTxtLastestVersion = (TextView) view
				.findViewById(R.id.txt_lastest_version);

		mTxtCurrentVersion.setText(versionInfo.getVersionCode() + "");
	}

	public String getAppInfo() {
		versionInfo = new AppVersionInfo();
		try {
			String pkName = this.getPackageName();
			String versionName = this.getPackageManager().getPackageInfo(
					pkName, 0).versionName;
			int versionCode = this.getPackageManager()
					.getPackageInfo(pkName, 0).versionCode;
			versionInfo.setPkName(pkName);
			versionInfo.setVersionCode(versionCode);
			versionInfo.setVersionName(versionName);

			return pkName + "   " + versionName + "  " + versionCode;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * ������
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("�汾����");
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
				MsgItemUtil.onclickPopItem(state, VersionUpdateActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_reload:// ��������
			reLoad();
			break;
		}
	}

	/**
	 * ��������App.apk
	 */
	private void reLoad() {
		// ����Service
		Intent intent = new Intent();
		intent.setAction("cmall.reload.MY_SERVICE");
		startService(intent);
	}

	/**
	 * �رշ���
	 */
	private void closeService() {
		// �ر�Service
		Intent intent = new Intent();
		intent.setAction("cmall.reload.MY_SERVICE");
		stopService(intent);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		closeService();
	}
}
