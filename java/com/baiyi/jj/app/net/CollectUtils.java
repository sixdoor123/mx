package com.baiyi.jj.app.net;

import android.content.Context;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.cache.Dao.CollectDao;
import com.baiyi.jj.app.cache.bean.CollectBean;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.utils.ArticleHistoryUtils;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.utils.RefreshManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

public class CollectUtils {

	private Context context = null;
	private String articleId = null;
	private String ArticleType = null;

	public static final int State_Success = 1;
	public static final int State_Failure = 2;
	public static final int State_NoLogin = 3;

	public CollectUtils(Context context, String articleId, String ArticleType) {
		this.context = context;
		this.articleId = articleId;
		this.ArticleType = ArticleType;
	}

	/**
	 */
	public void loadAddCollect(boolean isAninymity, final AddCollectCall collectCall) {
		addCollect(collectCall);
	}

	private void addCollect(final AddCollectCall collectCall) {
		JsonLoader loader = new JsonLoader(context);
		loader.setUrl(NetUtils.getAddCollection());
		loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setPostData(getCollectPostData(articleId));
		loader.setLoaderListener(new LoaderListener() {

			public void onProgress(Object arg0, long arg1, long arg2) {
				System.out.println("  arg1  =   " + arg1);
				System.out.println("  arg0  =   " + arg0);
			}

			public void onError(Object arg0, int arg1, String arg2) {
				if (collectCall != null) {
					collectCall.CallBack(State_Failure);
				}
			}

			public void onCompelete(Object arg0, Object arg1) {
				JSONArray array = (JSONArray) arg1;
				if (JsonParse.getState200(array)) {
					collectCall.CallBack(State_Success);
					// 加数据库
					if (CmsApplication.getUserInfoEntity() != null) {
						CollectBean bean = new CollectBean(CmsApplication.getUserInfoEntity().getMID(), articleId, ArticleHistoryUtils.getTableName(ArticleType));
						new CollectDao(context).add(bean);
					}
				} else {
					collectCall.CallBack(State_Failure);
				}

			}
		});

		CmsApplication.getDataStratey().startLoader(loader);
	}

	private String getCollectPostData(String articleId) {
		JSONObject o = new JSONObject();
		try {
			o.put("article_id", articleId);
		} catch (JSONException e) {
		}
		return o.toString();

	}

	public void loadDeleteCollect(final DeleteCollectCall collectCall) {
		if (CmsApplication.getUserInfoEntity() == null) {
			return;
		}
		JsonLoader loader = new JsonLoader(context);
		loader.setUrl(NetUtils.getDeleteCollection());
		loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setPostData(getCollectPostData(articleId));
		loader.setLoaderListener(new LoaderListener() {

			public void onProgress(Object arg0, long arg1, long arg2) {

			}

			public void onError(Object arg0, int arg1, String arg2) {
				if (collectCall != null) {
					collectCall.CallBack(State_Failure);
				}
			}

			public void onCompelete(Object arg0, Object arg1) {
				JSONArray array = (JSONArray) arg1;
				if (collectCall != null) {
					if (JsonParse.getState200(array)) {
						collectCall.CallBack(State_Success);
						RefreshManager.getInstance().add(RefreshManager.TAG_COLLECT_DEL);

						new CollectDao(context).delete(articleId, CmsApplication.getUserInfoEntity().getMID());

					} else {
						collectCall.CallBack(State_Failure);
					}
				}
			}
		});
		CmsApplication.getDataStratey().startLoader(loader);

	}


	public void loadClearCollect(final DeleteCollectCall collectCall) {

		JsonLoader loader = new JsonLoader(context);
		loader.setUrl(NetUtils.getClearCollection());
		loader.setType(BaseNetLoder.APPLICATION_JSON);
		loader.addRequestHeader("Authorization", CmsApplication.getUserToken());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setPostData("");
		loader.setLoaderListener(new LoaderListener() {

			public void onProgress(Object arg0, long arg1, long arg2) {

			}

			public void onError(Object arg0, int arg1, String arg2) {
				if (collectCall != null) {
					if (arg1 == 204) {
						collectCall.CallBack(State_Success);
						new CollectDao(context).deleteAll();
					}else{
						collectCall.CallBack(State_Failure);
					}
				}
			}

			public void onCompelete(Object arg0, Object arg1) {
				JSONArray array = (JSONArray) arg1;
				if (collectCall != null) {
					if (JsonParse.getState200(array)) {
						collectCall.CallBack(State_Success);
						new CollectDao(context).deleteAll();
					} else {
						collectCall.CallBack(State_Failure);
					}
				}

			}
		});
		CmsApplication.getDataStratey().startLoader(loader);

	}

	public interface AddCollectCall {
		public void CallBack(int state);
	}

	public interface DeleteCollectCall {
		public void CallBack(int state);
	}

	public interface MyCollectCall {
		public void CallBack(List<NewsListEntity> list);
	}

}
