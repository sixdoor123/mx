package com.baiyi.cmall.activities.msg;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.MessageEntity;
import com.baiyi.cmall.entity.UserInfoEntity;
import com.baiyi.cmall.utils.Define;
import com.baiyi.cmall.utils.JsonParseBase;
import com.baiyi.cmall.utils.UserNetUrl;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

public class MessageItemDetailActivity extends BaseActivity {
	@SuppressWarnings("unused")
	private TextView newsTitleView = null;
	private TextView newsTimeView = null;
	private TextView newsCouponView = null;
	private TextView newsContentView = null;

	private MessageEntity newsEntity;
	private int itemPosition;
	private boolean isRead = false;
	private int state;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(hasScrollView);

		EventTopTitleView titleView = new EventTopTitleView(this, true);
		titleView.setEventName("消息内容");
		titleView.setNewsPopItemOnclick(new NewsPopItemOnclick() {
			
			@Override
			public void setNewsPopItemOnclickLister(int state) {
				// TODO Auto-generated method stub
				
			}
		});
		win_title.addView(titleView);

		View contentView = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_msg_detail, null);
		win_content.addView(contentView);

		newsEntity = (MessageEntity) getIntent().getSerializableExtra(
				Define.NewsEntity);
		state = getIntent().getIntExtra(Define.NewsPlatState, 0);

		findViews();

		displayViews();

		itemPosition = getIntent().getIntExtra(Define.ITEM_POSTION, 0);
		if (state == MyMsgListActivity.Result_System) {
			return;
		}
		setReadData(newsEntity.getMsgId());
	}

	private void findViews() {
		newsTitleView = (TextView) findViewById(R.id.news_title);
		newsCouponView = (TextView) findViewById(R.id.news_coupon_title);
		newsTimeView = (TextView) findViewById(R.id.news_time);
		newsContentView = (TextView) findViewById(R.id.news_content);
	}

	private void displayViews() {
		if (newsEntity == null) {
			return;
		}
		// newsTitleView.setText("来源点城网");
		newsCouponView.setText(newsEntity.getMsgTitle());
		String dateString = newsEntity.getMsgCreateDate().substring(0,
				newsEntity.getMsgCreateDate().indexOf("T"));
		newsTimeView.setText(dateString);
		newsContentView.setText(newsEntity.getMsgContent());
	}

	private void setReadData(String messageID) {
		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(UserNetUrl.getMarkRead(messageID));
//		 loader.setPostData(getPostData(messageID));
		loader.setMethod(BaseNetLoder.Method_Get);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum,
					long totalByteNum) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onCompelete(Object tag, Object result) {
				// TODO Auto-generated method stub
				JSONArray array = (JSONArray) result;
				try {
					if (JsonParseBase.getState(array.getJSONObject(0))) {
						isRead = true;
						UserInfoEntity user = CmallApplication
								.getUserInfoEntity();
						CmallApplication.setNews(
								MessageItemDetailActivity.this,
								user.getMultiMaxID(),
								user.getUserUnReadCount() - 1);
						return;
					}
					isRead = false;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	public void callBackResult() {
		Intent intent = new Intent();
		if (isRead) {
			intent.putExtra(Define.NewsMessageId, newsEntity.getMsgId());
			intent.putExtra(Define.ITEM_POSTION, itemPosition);
		}
		this.setResult(MyMsgListActivity.Result_User, intent);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		callBackResult();
		super.onBackPressed();
	}

}
