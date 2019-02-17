//package com.baiyi.jj.wbshare;
//
//import android.content.Context;
//
//import com.turbo.turbo.mexico.R;
//import com.baiyi.jj.app.activity.BaseActivity;
//import com.sina.weibo.sdk.api.share.BaseResponse;
//import com.sina.weibo.sdk.api.share.IWeiboHandler;
//import com.sina.weibo.sdk.constant.WBConstants;
//
//public class WeiboResponse implements IWeiboHandler.Response{
//
//
//	private Context context;
//	public WeiboResponse(Context context) {
//		this.context = context;
//	}
//	@Override
//	public void onResponse(BaseResponse baseResp) {
//		// TODO Auto-generated method stub
//		if(baseResp!= null){
//            switch (baseResp.errCode) {
//            case WBConstants.ErrorCode.ERR_OK:
//            	((BaseActivity)context).displayToast(((BaseActivity)context).getResources().getString((R.string.tip_share_success)));
//                break;
//            case WBConstants.ErrorCode.ERR_CANCEL:
//            	((BaseActivity)context).displayToast(((BaseActivity)context).getResources().getString(R.string.tip_share_cancel));
//                break;
//            case WBConstants.ErrorCode.ERR_FAIL:
//            	((BaseActivity)context).displayToast(((BaseActivity)context).getResources().getString(R.string.tip_share_failure)+"Error Message: " + baseResp.errMsg);
//                break;
//            }
//        }
//	}
//
//}
