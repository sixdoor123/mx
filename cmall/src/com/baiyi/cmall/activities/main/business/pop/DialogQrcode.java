package com.baiyi.cmall.activities.main.business.pop;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.business.BusinessUrlNet;
import com.baiyi.cmall.activities.main.mall.UrlNet;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.dialog.DialogBase;
import com.baiyi.cmall.request.manager.NullJsonData;
import com.baiyi.core.loader.ImageLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;

/**
 * 商家-店铺二维码
 * @author tangkun
 *
 */
public class DialogQrcode extends DialogBase{
	//二维码
	private ImageView imgQrCode = null;
	//商家id
	private int ci;
	private Context context;

	public DialogQrcode(Context context, int winType,int ci) {
		super(context, winType);
		this.ci =  ci ;
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setTitleContent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setContainer() {
		this.setCanceledOnTouchOutside(true);
		imgQrCode = new ImageView(getContext());
		addView(imgQrCode);
		
		loadQrCode(ci);
	}
	
	private void loadQrCode(int ci)
	{
		((BaseActivity)context).startLoading("正在加载二维码...");
		ImageLoader loader = new ImageLoader(getContext(), true);
		loader.setUrl(BusinessUrlNet.getQRCodeApp(ci));
		loader.setPostData(NullJsonData.getPostData());
		loader.addRequestHeader("token", CmallApplication.getUserInfo().getToken());
		loader.setMethod(BaseNetLoder.Method_Get);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum,
					long totalByteNum) {
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				((BaseActivity)context).stopLoading();
			}

			@Override
			public void onCompelete(Object tag, Object result) {
				((BaseActivity)context).stopLoading();
				if(result == null)
				{
					return;
				}
				BitmapDrawable drawable = (BitmapDrawable) result;
				imgQrCode.setImageBitmap(drawable.getBitmap());

			}
		});

		CmallApplication.getImageStrategy().startLoader(loader);
	}

	@Override
	public void OnClickListenEvent(View v) {
		// TODO Auto-generated method stub
		
	}


}
