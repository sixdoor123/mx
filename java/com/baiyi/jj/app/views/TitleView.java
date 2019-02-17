package com.baiyi.jj.app.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.utils.IntentClassChangeUtils;
import com.baiyi.jj.app.views.PopNewsView.PopViewTheme;
import com.baiyi.jj.app.views.PopView.PopItemOnclick;
import com.turbo.turbo.mexico.R;

public class TitleView extends LinearLayout {

	private ImageView titleBg = null;
	private ImageView btnMore = null;

	private ImageView btnBack = null;
	private ImageView btnNews = null;
	private ImageView btnMoreChanel = null;

	private int rightImgResId;

	private TextView txtTitle = null;

	private TextView newsCount = null;

	private PopView pv = null;

	// ֻ����Ϣ û��popview
	public static final int Type_Pop_0 = 0;
	// ��Ϣ ����
	public static final int Type_Pop_2 = 1;
	// ��Ϣ ���� ����
	public static final int Type_Pop_3 = 2;
	private boolean isPop = false;
	// Ƶ������title
	private boolean isChannel = false;

	private LinearLayout linRefresh = null;
	private OnRefreshClick refreshClick = null;

	private TitleNewsOnclick titleNewsOnclick = null;

	private TitleMoreOnclick titleMoreOnclick = null;

	private NewsPopItemOnclick newsPopItemOnclick = null;

	public enum PopThemeType {
		PopThemeType_News, PopThemeType_More
	}

	/**
	 *
	 * @param context
	 * @param isPop 是否弹窗
	 * @param isChannel 是否是频道管理
     */
	public TitleView(Context context, boolean isPop, boolean isChannel, int rightImgResId) {
		super(context);
		this.isPop = isPop;
		this.isChannel = isChannel;
		this.rightImgResId = rightImgResId;
		// TODO Auto-generated constructor stub
		ContextUtil.getLayoutInflater(getContext()).inflate(
				R.layout.title_left, this);
		initLeft();
		initTitle(context);
		initRight();
		initTitleRefresh();
	}

	public TitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		ContextUtil.getLayoutInflater(getContext()).inflate(
				R.layout.title_left, this);

		initLeft();
		initTitle(context);
		initRight();
		initTitleRefresh();
	}

	private void initTitle(Context context){
		txtTitle = (TextView) findViewById(R.id.title_name);

	}
	
	public void setTitleText(String tvTitle){
		txtTitle.setText(tvTitle);
//		txtTitle.setTypeface(CmsApplication.getTitleFace(getContext()));
	}

	public void setRightImg(int rightImgResId)
	{
		if(rightImgResId <= 0)
		{
			findViewById(R.id.btn_news).setVisibility(View.GONE);
			return;
		}
		findViewById(R.id.btn_news).setVisibility(View.VISIBLE);
		((ImageView)findViewById(R.id.btn_news)).setImageDrawable(getResources().getDrawable(rightImgResId));
	}

	private void initLeft() {
		titleBg = (ImageView) findViewById(R.id.title_bg);
		ImageView btnBack = (ImageView) findViewById(R.id.img_back);
		btnBack.setVisibility(View.GONE);
		btnMore = (ImageView) findViewById(R.id.btn_left_more);
		btnMore.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (titleMoreOnclick != null) {
					titleMoreOnclick.setTitleMoreOnclickLister(isPop);
					if (isPop) {
						return;
					}
				}
			}
		});
	}

	private void initRight() {
		newsCount = (TextView) findViewById(R.id.news_count);
		newsCount.setVisibility(GONE);
		FrameLayout newsLayout = (FrameLayout) findViewById(R.id.news_layout);
		newsLayout.setVisibility(View.VISIBLE);
		findViewById(R.id.btn_more).setVisibility(View.VISIBLE);
		btnBack = (ImageView) findViewById(R.id.img_back);

		btnNews = (ImageView) findViewById(R.id.btn_more);
		if (isPop) {
			findViewById(R.id.btn_news).setVisibility(View.GONE);
			findViewById(R.id.btn_more).setVisibility(View.VISIBLE);
		} else {
			if(rightImgResId > 0)
			{
				findViewById(R.id.btn_news).setVisibility(View.VISIBLE);
				((ImageView)findViewById(R.id.btn_news)).setImageDrawable(getResources().getDrawable(rightImgResId));
			}else
			{
				findViewById(R.id.btn_news).setVisibility(View.GONE);
			}

			findViewById(R.id.btn_more).setVisibility(View.GONE);
		}
		newsLayout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (titleNewsOnclick != null) {
					titleNewsOnclick.setTitleNewsOnclickLister(isPop);
				}
				if (isPop) {
					return;
				}

			}
		});
	}

	private void initTitleRefresh() {
		linRefresh = (LinearLayout) findViewById(R.id.lin_refresh);
		linRefresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (refreshClick != null) {
					refreshClick.onClick();
				}
			}
		});

	}

	public interface OnRefreshClick {
		void onClick();
	}

	public OnRefreshClick getRefreshClick() {
		return refreshClick;
	}

	public void setRefreshClick(OnRefreshClick refreshClick) {
		this.refreshClick = refreshClick;
	}

	public void setGoneBack() {
		btnBack.setVisibility(View.GONE);
	}

	public void setNews() {
		UserInfoEntity user = CmsApplication.getUserInfoEntity();
		if (user != null) {
			//国际版不显示消息数量
			newsCount.setVisibility(View.GONE);
//			((BaseActivity) getContext()).setNews(newsCount,
//					(user.getMultiMaxID() + user.getUserUnReadCount()));
//			((BaseActivity) getContext()).setNews(newsCount,AccountManager.getInstance().getMsgReadedCount());
		} else {
			newsCount.setVisibility(View.GONE);
		}
	}

	public void displayPoP(String[] names, int[] resIds, int[] nightResIds,
			PopThemeType type, PopViewTheme popTheme) {
		pv = new PopView(getContext(), names, resIds, nightResIds, popTheme,
				type);
		View view = null;
		if (isChannel) {
			view = type == PopThemeType.PopThemeType_News ? btnMoreChanel
					: titleBg;
		} else {
			view = type == PopThemeType.PopThemeType_News ? btnNews : titleBg;
		}

		pv.showPop(view, type);
		pv.setPopItemOnclick(new PopItemOnclick() {

			@Override
			public void setOnclickLister(int state) {
				if (newsPopItemOnclick != null) {
					newsPopItemOnclick.setNewsPopItemOnclickLister(state);
				}
			}
		});

	}

	public void displayPopLeft(String[] names, int[] resIds) {

	}

	public TitleNewsOnclick getTitleNewsOnclick() {
		return titleNewsOnclick;
	}

	public void setTitleNewsOnclick(TitleNewsOnclick titleNewsOnclick) {
		this.titleNewsOnclick = titleNewsOnclick;
	}

	public NewsPopItemOnclick getNewsPopItemOnclick() {
		return newsPopItemOnclick;
	}

	public void setNewsPopItemOnclick(NewsPopItemOnclick newsPopItemOnclick) {
		this.newsPopItemOnclick = newsPopItemOnclick;
	}

	public void setTitleMoreOnclick(TitleMoreOnclick titleMoreOnclick) {
		this.titleMoreOnclick = titleMoreOnclick;
	}

	public interface TitleNewsOnclick {
		public void setTitleNewsOnclickLister(boolean isPop);
	}

	public interface TitleMoreOnclick {
		public void setTitleMoreOnclickLister(boolean isPop);
	}

	public interface NewsPopItemOnclick {
		public void setNewsPopItemOnclickLister(int state);
	}

}
