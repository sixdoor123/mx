package com.baiyi.jj.app.net;

import android.content.Context;

import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.attention.AttentionWordsEntity;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.cache.Dao.GifDetailDao;
import com.baiyi.jj.app.cache.Dao.SupportDao;
import com.baiyi.jj.app.cache.bean.SupportBean;
import com.baiyi.jj.app.entity.NewsDetailCommentEntity;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.entity.article.GifEntity;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.pageview.JsonParse_Collect;
import com.turbo.turbo.mexico.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewsDetailUtils {

    public static final int State_Success = 1;
    public static final int State_Failure = 2;
    public static final int State_NoLogin = 3;
    public static final int State_Repeat = 99;

    public static final int State_true = 1;
    public static final int State_false = 0;
    public static final int State_Agree = 1;
    public static final int State_DisAgree = 0;

    public int PageSize = 15;

    /**
     * ��ȡ��������
     */
    public void loadCommentData(final Context context, String ArticleId,
                                int comPageNum, final CommentListCallBack callBack) {

        if (Utils.isStringEmpty(CmsApplication.getUserToken())) {
            return;
        }

        JsonLoader loader = new JsonLoader(context);
        loader.setUrl(NetUtils.getCommentListByAID(ArticleId, comPageNum, PageSize));
        loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
        loader.setMethod(BaseNetLoder.Method_Get);
        loader.setLoaderListener(new LoaderListener() {

            public void onProgress(Object arg0, long arg1, long arg2) {

            }

            public void onError(Object arg0, int arg1, String arg2) {

                ((BaseActivity) context).displayToast(context.getResources()
                        .getString(R.string.tip_loaddata_failure));
                if (callBack != null) {
                    callBack.ComListBack(null);
                }
            }

            public void onCompelete(Object arg0, Object arg1) {

                try {
                    JSONArray array = (JSONArray) arg1;
                    if (!JsonParse.getState200(array.getJSONObject(0))) {
                        if (callBack != null) {
                            callBack.ComListBack(null);
                            return;
                        }
                    }
                    List<NewsDetailCommentEntity> dataList = JsonParse
                            .getDetailCommentList(array);

                    if (callBack != null) {
                        callBack.ComListBack(dataList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.ComListBack(null);
                    }
                }

                // ((BaseActivity)context).displayToast(context.getResources().getString(
                // R.string.tip_comment_get_suc));

            }
        });

        CmsApplication.getDataStratey().startLoader(loader);
    }

    public void loadSendComment(String ArticleType, final Context context,
                                String ArticleId, String content,
                                final SendCommentCallBack callBack) {

        JsonLoader loader = new JsonLoader(context);
        loader.setMethod(BaseNetLoder.Method_Post);
        loader.setPostData(getPostData(ArticleId, content));
        loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
        TLog.e("head", Constant.HEAD_NAME + CmsApplication.getUserToken());
        TLog.e("url", NetUtils.getSendComment());
        TLog.e("post", getPostData(ArticleId, content));
        loader.setUrl(NetUtils.getSendComment());
        loader.setUrlName("Sent Comment");

        loader.setLoaderListener(new LoaderListener() {

            public void onProgress(Object arg0, long arg1, long arg2) {

            }

            public void onError(Object arg0, int arg1, String arg2) {
                ((BaseActivity) context).displayToast(context.getResources().getString(R.string.tip_loaddata_failure));
                // displayToast(getResources().getString(
                // R.string.tip_comment_send_fal));
                if (callBack != null) {
                    callBack.sendCallBack(State_Failure);
                }
            }

            public void onCompelete(Object arg0, Object arg1) {

                JSONArray array = (JSONArray) arg1;
                TLog.d(array.toString());
                try {
                    if (!JsonParse.getState200(array.getJSONObject(0))) {
                        if (callBack != null) {
                            callBack.sendCallBack(State_Failure);
                            ((BaseActivity) context).displayToast(context
                                    .getResources().getString(
                                            R.string.tip_comment_send_fal));
                            return;
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (callBack != null) {
                    callBack.sendCallBack(State_Success);
                }

//                ((BaseActivity) context).displayToast(context.getResources()
//                        .getString(R.string.tip_comment_send_suc));

            }
        });

        CmsApplication.getDataStratey().startLoader(loader);

    }


    private String getPostData(String articleid, String Content) {
        JSONObject o = new JSONObject();
        try {
            o.put("article_id", articleid);
            o.put("content", Content);
        } catch (JSONException e) {
        }
        return o.toString();
    }

    private String getAddReadPostData(String articleid) {
        JSONObject o = new JSONObject();
        try {
            o.put("article_id", articleid);
        } catch (JSONException e) {
        }
        return o.toString();
    }

    public void loadArticleVote(boolean isAninymity, final Context context,
                                final String ArticleId, String ArticleType,
                                final ArticleVoteCallBack callBack) {

        JsonLoader loader = new JsonLoader(context);
        loader.setMethod(BaseNetLoder.Method_Post);
        loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
        TLog.e("head", Constant.HEAD_NAME + CmsApplication.getUserToken());
        loader.setPostData(geVoteData(ArticleId));
        loader.setUrl(NetUtils.getArticlesvote());
        loader.setLoaderListener(new LoaderListener() {

            public void onProgress(Object arg0, long arg1, long arg2) {

            }

            public void onError(Object arg0, int arg1, String arg2) {
                if (callBack != null) {
                    callBack.voteCallBack(State_Failure);
                }
            }

            public void onCompelete(Object arg0, Object arg1) {
                JSONArray array = (JSONArray) arg1;
                JSONObject o;
                try {
                    o = array.getJSONObject(0);
                    int state = JsonParse.getIntState(o);
                    if (state == 200) {
                        if (callBack != null) {
                            callBack.voteCallBack(State_Success);
                        }

                        if (CmsApplication.getUserInfoEntity() != null) {
                            SupportBean bean = new SupportBean(ArticleId, CmsApplication.getUserInfoEntity().getMID());
                            new SupportDao(context).add(bean);
                        }
                    } else if (state == 520) {
                        ((BaseActivity) context).displayToast(context.getResources().
                                getString(R.string.tip_newsdetail_vote_repeat));
                        if (callBack != null) {
                            callBack.voteCallBack(State_Repeat);
                        }
                    } else {
                        if (callBack != null) {
                            callBack.voteCallBack(State_Failure);
                        }
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

        CmsApplication.getDataStratey().startLoader(loader);

    }


    private String geVoteData(String articleid) {
        JSONObject o = new JSONObject();
        try {
            o.put("article_id", Integer.parseInt(articleid));
        } catch (JSONException e) {
        }
        return o.toString();
    }

    public void loadCommentVote(final Context context,
                                final String CommentId, final int isAgree,
                                final CommentVoteCallBack callBack) {


        JsonLoader loader = new JsonLoader(context);
        // loader.setPostData(commentVoteData(mid, CommentId, isAgree));
        loader.setMethod(BaseNetLoder.Method_Post);
        loader.setPostData(commentVoteData(CommentId));
        loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
        loader.setUrl(NetUtils.getcommentVote());
        loader.setLoaderListener(new LoaderListener() {

            public void onProgress(Object arg0, long arg1, long arg2) {

            }

            public void onError(Object arg0, int arg1, String arg2) {
                if (callBack != null) {
                    callBack.voteCallBack(State_Failure);
                }
            }

            public void onCompelete(Object arg0, Object arg1) {

                JSONArray array = (JSONArray) arg1;
                try {
                    JSONObject o = array.getJSONObject(0);
                    int state = JsonParse.getIntState(o);
                    String msg = JsonParse.getMsg(array);

                    if (state == 200) {
                        if (callBack != null) {
                            callBack.voteCallBack(State_Success);
                        }
                        if (CmsApplication.getUserInfoEntity() != null) {
                            SupportBean bean = new SupportBean(CommentId, CmsApplication.getUserInfoEntity().getMID());
                            new SupportDao(context).add(bean);
                        }
                        return;
                    } else if (state == -2) {
                        ((BaseActivity) context).displayToast(context.getResources().
                                getString(R.string.tip_newsdetail_vote_repeat));
                    }
                    if (callBack != null) {
                        callBack.voteCallBack(State_Repeat);
                    }
                } catch (JSONException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        CmsApplication.getDataStratey().startLoader(loader);

    }

    private String commentVoteData(String CommentId) {

        JSONObject o = new JSONObject();
        try {
            o.put("comment_id", CommentId);
        } catch (JSONException e) {
        }
        return o.toString();

    }


    public void loadAddRead(final Context context,  String objId) {

        JsonLoader loader = new JsonLoader(context);
        loader.setMethod(BaseNetLoder.Method_Post);
        loader.setUrl(NetUtils.getAddRead());
        loader.setPostData(getAddReadPostData(objId));
        loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
        TLog.e("sidread",objId+"--------");
        loader.setLoaderListener(new LoaderListener() {

            public void onProgress(Object arg0, long arg1, long arg2) {

            }

            public void onError(Object arg0, int arg1, String arg2) {
            }

            public void onCompelete(Object arg0, Object arg1) {
                try {
                    JSONArray array = (JSONArray) arg1;
//                    TLog.e("ok","read----------"+array.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        CmsApplication.getDataStratey().startLoader(loader);

    }

    public void loadRelatedReading(String ArticleId, final RelatedReadingCallBack readingCallBack) {

        if (Utils.isStringEmpty(CmsApplication.getUserToken())) {
            return;
        }


        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .url(NetUtils.getNewsReading(ArticleId))
                .get()
                .header(Constant.HEAD_NAME, CmsApplication.getUserToken())
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                final List<NewsListEntity> list = JsonParse_Collect.getReadingList(str);
                if (Utils.isStringEmpty(list)) {
                    return;
                }
                readingCallBack.relatedReadingBack(list);
            }
        });
    }

    public void loadPleaseAttention(String ArticleId, final PleaseAttentionCallBack readingCallBack) {

        if (Utils.isStringEmpty(CmsApplication.getUserToken())) {
            return;
        }
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .url(NetUtils.getPleaseAttention(ArticleId))
                .get()
                .header(Constant.HEAD_NAME, CmsApplication.getUserToken())
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                final List<AttentionWordsEntity> list = JsonParse_Collect.getPleaseAttention(str);

                if (Utils.isStringEmpty(list)) {
                    return;
                }
                readingCallBack.pleaseAttentionBack(list);
            }
        });
    }

    public void loadDetailImage(String ArticleId, final ImageListCallback callback) {

        if (Utils.isStringEmpty(CmsApplication.getUserToken())) {
            return;
        }
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .url(NetUtils.getDetailImage(ArticleId))
                .get()
                .header(Constant.HEAD_NAME, CmsApplication.getUserToken())
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                try {
                    JSONObject object = new JSONObject(str);
                    if (object.has("images")) {
                        JSONArray array = object.getJSONArray("images");
                        List<String> imageList = new ArrayList<String>();
                        for (int i = 0; i < array.length(); i++) {
                            imageList.add(array.getString(i));
                        }
                        if (callback != null) {
                            callback.callback(imageList);
                        }
                    }
                } catch (JSONException e) {

                }
            }
        });
    }

    public void loadGifList(final Context context, String articleId, final GetGifCallBack gifCallBack) {

        GifEntity entity = new GifDetailDao(context).getGifEntityToId(articleId);
        if (null != entity) {
            List<GifEntity> list = new ArrayList<>();
            list.add(entity);
            gifCallBack.getGifBack(list);
            return;
        }
        if (Utils.isStringEmpty(CmsApplication.getUserToken())) {
            return;
        }
        OkHttpClient client = new OkHttpClient.Builder().build();
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                getAddPostBody(articleId));
        Request request = new Request.Builder()
                .post(requestBody)
                .url(NetUtils.getGifList())
                .header(Constant.ContentType, Constant.ContentType_Json)
                .header(Constant.HEAD_NAME, CmsApplication.getUserToken())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String strJson = response.body().string();
                List<GifEntity> arrayList = JsonParse_Collect.getGifList(strJson);
                if (!Utils.isStringEmpty(arrayList)) {
                    new GifDetailDao(context).add(arrayList.get(0));
                }
                gifCallBack.getGifBack(arrayList);
            }
        });

    }

    private String getAddPostBody(String articleId) {
        JSONObject object = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray.put(Integer.parseInt(articleId));
            object.put("article_ids", jsonArray);
        } catch (JSONException e) {
            e.toString();
        }
        return object.toString();
    }

    public void loadDetailUrls(final String verName) {

        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .url(NetUtils.getNewsDetailUrl())
                .get()
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               try {
                   String str = response.body().string();
                   String detalurl = JsonParse.getDetailUrls(str,verName);
                   if (!Utils.isStringEmpty(detalurl)){
//                    TLog.e("detalurl",detalurl);
                       AccountManager.getInstance().setNewsDetailUrl(detalurl);
                   }else {
//                    TLog.e("detalurl","null----");
                   }
               }catch (Exception e){
                   e.printStackTrace();
               }

            }
        });
    }



    public interface ImageListCallback {
        public void callback(List<String> list);
    }

    public interface ArticleVoteCallBack {
        public void voteCallBack(int state);
    }

    public interface CommentVoteCallBack {
        public void voteCallBack(int state);
    }

    public interface SendCommentCallBack {
        public void sendCallBack(int state);
    }

    public interface CommentListCallBack {
        public void ComListBack(List<NewsDetailCommentEntity> list);
    }

    public interface RelatedReadingCallBack {
        public void relatedReadingBack(List<NewsListEntity> list);
    }

    public interface PleaseAttentionCallBack {
        public void pleaseAttentionBack(List<AttentionWordsEntity> list);
    }

    public interface GetGifCallBack {
        public void getGifBack(List<GifEntity> list);
    }

}
