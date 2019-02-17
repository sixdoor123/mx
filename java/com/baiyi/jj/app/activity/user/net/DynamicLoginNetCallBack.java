///**
// *
// */
//package com.baiyi.jj.app.activity.user.net;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//
//import android.content.Context;
//
//import com.baiyi.core.loader.JsonLoader;
//import com.baiyi.core.loader.Loader.LoaderListener;
//import com.baiyi.core.loader.net.BaseNetLoder;
//import com.baiyi.jj.app.application.CmsApplication;
//import com.baiyi.jj.app.entity.UserInfoEntity;
//import com.baiyi.jj.app.utils.JsonParse;
//import com.baiyi.jj.app.utils.Utils;
//
///**
// * 动态登录
// * 获取用户信息
// *
// * @author wangf
// *
// */
//public class DynamicLoginNetCallBack {
//
//	public static final int SUCCESS = 1;
//	public static final int FAIL = 2;
//
//	public DynamicLoginNetCallBack() {
//
//	}
//
//	public void loadLogin(final Context context, String url,
//			final DynamicLoginCallBack lister) {
//		JsonLoader loader = new JsonLoader(context);
//		loader.setUrl(url);
//		loader.setUrlName("动态登录");
//		loader.setMethod(BaseNetLoder.Method_Get);
//		loader.setLoaderListener(new LoaderListener() {
//			@Override
//			public void onProgress(Object tag, long curByteNum,
//					long totalByteNum) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onError(Object tag, int responseCode,
//					String errorMessage) {
//				// TODO Auto-generated method stub
//				lister.callBackLister(FAIL, null,null);
//
//			}
//
//			@Override
//			public void onCompelete(Object tag, Object result) {
//
//				JSONArray array = (JSONArray) result;
//				try {
//
//					if(!JsonParse.getState(array.getJSONObject(0))){
//						lister.callBackLister(JsonParse.getIntState(array.getJSONObject(0)),
//								null,null);
//						return;
//					}
//
//					UserInfoEntity entity = JsonParse_User.getToken(array);
//					if(entity == null)
//					{
//						lister.callBackLister(FAIL, null,null);
//						return;
//					}
//					getTicketKey(context, entity.getToken(), entity.getPublicKey(),lister);
//
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		CmsApplication.getDataStratey().startLoader(loader);
//	}
//
//	public void getTicketKey(final Context context, final String token,
//			final String publicKey, final DynamicLoginCallBack lister) {
//		if(Utils.isStringEmpty(token) || Utils.isStringEmpty(publicKey))
//		{
//			lister.callBackLister(FAIL, null,null);
//			return;
//		}
//		JsonLoader loader = new JsonLoader(context);
//		loader.setUrl(NetUrl.getTicketKey());
//		loader.setUrlName("获取票据");
//		loader.setMethod(BaseNetLoder.Method_Get);
//		loader.setLoaderListener(new LoaderListener() {
//
//			@Override
//			public void onProgress(Object tag, long curByteNum,
//					long totalByteNum) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onError(Object tag, int responseCode,
//					String errorMessage) {
//				// TODO Auto-generated method stub
//				lister.callBackLister(FAIL, null,null);
//			}
//
//			@Override
//			public void onCompelete(Object tag, Object result) {
//				// TODO Auto-generated method stub
//				JSONArray array = (JSONArray) result;
////				try {
////					if (!JsonParse.getState(array.getJSONObject(0))) {
////						lister.callBackLister(FAIL, null,JsonParse.getMsg(array));
////						return;
////					}
////				} catch (JSONException e) {
////					e.printStackTrace();
////				}
//
//				UserInfoEntity newEntity = JsonParse_User.getUserInfoEntity(array);
//
//				if (newEntity == null) {
//					lister.callBackLister(FAIL, null,null);
//					return;
//				}
//				newEntity.setToken(token);
//				newEntity.setPublicKey(publicKey);
//
//				lister.callBackLister(SUCCESS, newEntity,null);
//			}
//		});
//		CmsApplication.getDataStratey().startLoader(loader);
//	}
//
//
//	public interface DynamicLoginCallBack {
//		public void callBackLister(int state, UserInfoEntity entity,String msg);
//	}
//
//}
