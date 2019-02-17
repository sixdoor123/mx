package com.baiyi.jj.app.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.theme.FontUtil;
import com.baiyi.jj.app.theme.ThemeUtil;

public class PopNewsView extends LinearLayout{
	
	private LinearLayout newsLayout = null;
	
	private int tag;
	
	private PopItemClick popItemClickLister = null;
	
	private String[] names = null;
	private int[] resIds = null;
	private int[] nightResIds = null;
	
	public static final int State_Ticket = 1;
	public static final int State_PaySuccess = 2;
	private int themeType;
	private PopViewTheme popViewTheme = null;
	
//	ActivityStackControlUtil.finishProgram();

	public PopNewsView(Context context, String[] names, int[] resIds, int[] nightResIds, PopViewTheme popViewTheme) {
		super(context);
		// TODO Auto-generated constructor stub
		this.names = names;
		this.resIds = resIds;
		this.nightResIds = nightResIds;
		this.popViewTheme = popViewTheme;
		findViews();
		addItem();
	}
	
	
	public PopNewsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		findViews();
		addItem();
	}
	
	private void findViews()
	{
		ContextUtil.getLayoutInflater(getContext()).inflate(R.layout.pop_news, this);
		
		newsLayout = (LinearLayout)findViewById(R.id.pop_layout);
		if(popViewTheme != null)
		{
			newsLayout.setBackgroundDrawable(getContext().getResources().getDrawable(popViewTheme.getPopViewBg()));
		}
	}
	
	public void setThemeType(int themeType) {
		this.themeType = themeType;
	}

	private void addItem()
	{
		for(int i = 0; i < names.length; i++)
		{
			View lineView = new View(getContext());
			if(themeType == ThemeUtil.Theme_day)
			{
				lineView.setBackgroundResource(R.color.day_line1_color);
			}else
			{
//				lineView.setBackgroundResource(R.color.night_line1_color);
			}
			LinearLayout.LayoutParams lp = new LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 1);
			lp.setMargins(BaseActivity.getDensity_int(10), 0, BaseActivity.getDensity_int(10), 0);
			if(i != 0)
			{
				newsLayout.addView(lineView, lp);
			}
			int itemResId = popViewTheme == null ? R.layout.pop_news_item : popViewTheme.getPopItemView();
			View itemViews = ContextUtil.getLayoutInflater(getContext()).inflate(itemResId, null);
			setThemeItem(itemViews, i, itemResId == R.layout.pop_news_item);
//			lp = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, BaseActivity.getDensity_int(45));
//			lp.setMargins(BaseActivity.getDensity_int(10), 0, BaseActivity.getDensity_int(10), 0);
//			newsLayout.addView(itemViews, lp);
			newsLayout.addView(itemViews);
		}
	}
	
	private void setThemeItem(View itemViews, int index, boolean isNews)
	{
		itemViews.setId(index);
		itemViews.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(popItemClickLister != null)
				{
					popItemClickLister.setPopItemClickLister(v.getId());
				}
			}
		});
		ImageView iconNews = (ImageView)itemViews.findViewById(R.id.icon_news);
		TextView txtNews = (TextView)itemViews.findViewById(R.id.txt_news);
		if(resIds != null)
		{
			if(ThemeUtil.getAppTheme() == ThemeUtil.Theme_day)
			{
				iconNews.setBackgroundResource(resIds[index]);
			}else
			{
				iconNews.setBackgroundResource(nightResIds[index]);
			}
			iconNews.setVisibility(View.VISIBLE);
		}else
		{
			iconNews.setVisibility(View.GONE);
		}
		txtNews.setText(names[index]);
		if(ThemeUtil.getAppTheme() == ThemeUtil.Theme_day)
		{
			if(isNews)
			{
//				itemViews.setBackgroundResource(R.drawable.selector_gray_tran);
//				txtNews.setTextColor(getContext().getResources().getColor(R.color.word_white_red_press));
			}else
			{
//				itemViews.setBackgroundResource(R.drawable.selector_gray_tran);
//				txtNews.setTextColor(getContext().getResources().getColor(R.color.word_white_red_press));
			}
		}else
		{
			if(isNews)
			{
//				itemViews.setBackgroundResource(R.drawable.selector_gray_tran);
//				txtNews.setTextColor(getContext().getResources().getColor(R.color.night_default_43));
			}else
			{
//				itemViews.setBackgroundResource(R.drawable.selector_gray_tran);
//				txtNews.setTextColor(getContext().getResources().getColor(R.color.word_white_red_press));
			}
		}
	}
	public void setDataNull()
	{
		if(names != null)
		{
			names = null;
		}
		if(resIds != null)
		{
			resIds = null;
		}
		if(nightResIds != null)
		{
			nightResIds = null;
		}
	}
//	Mia Khalifa
	public PopItemClick getPopItemClickLister() {
		return popItemClickLister;
	}

	public void setPopItemClickLister(PopItemClick popItemClickLister) {
		this.popItemClickLister = popItemClickLister;
	}

	public interface PopItemClick
	{
		public void setPopItemClickLister(int id);
	}
	
	public interface PopViewTheme
	{
		public int getPopViewBg();
		public int getPopItemView();
	}

}
