package com.baiyi.cmall;

import com.baiyi.cmall.activities.base.BaseMsgActivity;
import com.baiyi.cmall.activities.main.business.BusinessUrlNet;
import com.baiyi.cmall.activities.main.business.entity.CbmEntity;
import com.baiyi.cmall.activities.main.mall.MallDefine;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.request.manager.NullJsonData;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.core.loader.ImageLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class QrCodeActivity extends BaseMsgActivity {

	// 二维码图片控件
	private ImageView qrCodeIamgeView;
	private TextView mTvBusinessName;
	private TextView mTvBusinessFollowNum;

	private int ci;
	private CbmEntity cbmEntity = null;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(hasScrollView);
		
		initData();
		titleView();
		contentView();
		loadQrCode(ci);
	}

	// 数据
	private void initData() {
		Intent intent = getIntent();
		ci = intent.getIntExtra(MallDefine.QrCode, -1);
		cbmEntity = (CbmEntity) intent.getSerializableExtra(MallDefine.CBM);
	}

	// 标题
	private void titleView() {

		topTitleView = new EventTopTitleView(QrCodeActivity.this, true);
		topTitleView.setEventName("店铺二维码");
		win_title.addView(topTitleView);
	}

	// 内容控件

	private void contentView() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_qr_code, null);
		win_content.addView(view);
		qrCodeIamgeView = (ImageView) this.findViewById(R.id.qe_code_imageview);
		mTvBusinessName = (TextView) findViewById(R.id.tv_qr_business_name);
		mTvBusinessFollowNum = (TextView) findViewById(R.id.tv_qr_business_follow);
		
		if (null!=cbmEntity) {
			mTvBusinessName.setText(cbmEntity.getCn());
			mTvBusinessFollowNum.setText(cbmEntity.getCf() + "人关注");
		}

	}

	private void loadQrCode(int ci) {
		QrCodeActivity.this.startLoading("正在加载二维码...");
		ImageLoader loader = new ImageLoader(QrCodeActivity.this, true);
		loader.setUrl(BusinessUrlNet.getQRCodeApp(ci));
		loader.setPostData(NullJsonData.getPostData());
		loader.addRequestHeader("token", CmallApplication.getUserInfo()
				.getToken());
		loader.setMethod(BaseNetLoder.Method_Get);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum,
					long totalByteNum) {
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				QrCodeActivity.this.stopLoading();
			}

			@Override
			public void onCompelete(Object tag, Object result) {
				QrCodeActivity.this.stopLoading();
				if (result == null) {
					return;
				}
				BitmapDrawable drawable = (BitmapDrawable) result;
				qrCodeIamgeView.setImageBitmap(drawable.getBitmap());

			}
		});

		CmallApplication.getImageStrategy().startLoader(loader);
	}

}
