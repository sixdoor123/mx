package com.baiyi.cmall.listitem;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.cmall.entity.MessageEntity;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.pageview.msg.SystemNewsPage;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 消息列表
 * 
 * @author tangkun
 * 
 */
public class ListItemNews extends LinearLayout {
	private TextView newsTitleView = null;
	private TextView newsTimeView = null;
	private TextView newsContentView = null;

	private MessageEntity newsEntity;

	private String newsName;
	private String state = "未读";
	private Context context;

	public ListItemNews(Context context, MessageEntity newsEntity,
			String newsName) {
		super(context);
		this.newsEntity = newsEntity;
		this.newsName = newsName;
		this.context = context;
		ContextUtil.getLayoutInflater(context).inflate(R.layout.list_item_msg,
				this);

		findViews();

		displayViews();
	}

	private void findViews() {
		newsTitleView = (TextView) findViewById(R.id.news_title);
		newsTimeView = (TextView) findViewById(R.id.news_time);
		newsContentView = (TextView) findViewById(R.id.news_content);

	}

	private void displayViews() {
		if (newsEntity == null) {
			return;
		}
		if (newsEntity.getMsgState().equals("未读")) {
			if (newsName.equals(SystemNewsPage.TAG)) {
				newsTitleView.setSelected(false);
			} else {
				newsTitleView.setSelected(true);
			}
		} else {
			newsTitleView.setSelected(false);
		}
		newsTitleView.setText(newsEntity.getMsgTitle());
		String dateString = newsEntity.getMsgCreateDate().substring(0,
				newsEntity.getMsgCreateDate().indexOf("T"));
		newsTimeView.setText(dateString);
		newsContentView.setText(Utils.ToDBC(newsEntity.getMsgContent()));
	}

	public void setData(MessageEntity newsEntity) {
		this.newsEntity = newsEntity;
		displayViews();
	}

	public MessageEntity getData() {
		return newsEntity;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
