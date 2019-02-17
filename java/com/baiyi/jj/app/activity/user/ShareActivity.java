package com.baiyi.jj.app.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.views.ShareView;
import com.baiyi.jj.app.views.ShareView.OnFinishClick;

public class ShareActivity extends BaseActivity {

	// "471031afe2018b18560a4529aa3cb12230b2571d"
	// dc88113397f0cc0b6a4a4ea89c98ca44

	private Button btnCancel = null;

	/**
	 * ���������
	 */
	private String TitleStr = null;
	private String ContentStr = null;
	private String UrlStr = null;
	private String ImageUrlStr = null;
	
	/**
	 * ����ҳ������
	 */
	public static int TitleId_Share = 0;
	public static int TitleId_ShareSet = 1;
	public static int TitleId_ShareInvite = 2;
	private int type = 0;

	private ShareView shareView = null;
	private LinearLayout linAdd = null;

	@Override
	protected void initWin(boolean hasScrollView, boolean isAnimal) {
		super.initWin(false, true);
		View titleView = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.title_left, null);
		win_title.addView(titleView);
		View contentView = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_share, null);
		win_content.addView(contentView);

		type  = getIntent().getIntExtra(Define.ShartType, 0);
		TitleStr = getIntent().getStringExtra(Define.ShareTitle);
		ContentStr = getIntent().getStringExtra(Define.ShareContent);
		UrlStr = getIntent().getStringExtra(Define.ShareUrl);
		ImageUrlStr = getIntent().getStringExtra(Define.ShareImageUrl);

		findTitleViews();
		init();

	}

	private void findTitleViews() {
		TextView txt = (TextView) findViewById(R.id.title_name);
		txt.setText(getResources().getString(R.string.name_title_share));
//		txt.setTypeface(CmsApplication.getTitleFace(this));
		ImageView imgBack = (ImageView) findViewById(R.id.img_back);
		imgBack.setVisibility(View.VISIBLE);
		imgBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				setExitSwichLayout();
			}
		});
	}

	private void init() {
		btnCancel = (Button) findViewById(R.id.btn_cancel);
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setExitSwichLayout();
			}
		});

		linAdd = (LinearLayout) findViewById(R.id.lin_add);
		shareView = new ShareView(ShareActivity.this, 0, type, TitleStr, ContentStr, UrlStr, ImageUrlStr);
		shareView.setFinishClick(new OnFinishClick() {
			
			@Override
			public void onClick() {
//				finish();
				
			}
		});
		linAdd.addView(shareView);
	}

	/**
	 * Called when an activity you launched exits, giving you the requestCode
	 * you started it with, the resultCode it returned, and any additional
	 * data from it.  The <var>resultCode</var> will be
	 * {@link #RESULT_CANCELED} if the activity explicitly returned that,
	 * didn't return any result, or crashed during its operation.
	 *
	 * <p>You will receive this call immediately before onResume() when your
	 * activity is re-starting.
	 *
	 * <p>This method is never invoked if your activity sets
	 * {@link android.R.styleable#AndroidManifestActivity_noHistory noHistory} to
	 * <code>true</code>.
	 *
	 * @param requestCode The integer request code originally supplied to
	 *                    startActivityForResult(), allowing you to identify who this
	 *                    result came from.
	 * @param resultCode The integer result code returned by the child activity
	 *                   through its setResult().
	 * @param data An Intent, which can return result data to the caller
	 *               (various data can be attached to Intent "extras").
	 *
	 * @see #startActivityForResult
	 * @see #createPendingResult
	 * @see #setResult(int)
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		shareView.onActivityResult(requestCode,resultCode,data);
	}


}
