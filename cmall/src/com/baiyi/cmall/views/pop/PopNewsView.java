package com.baiyi.cmall.views.pop;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

public class PopNewsView extends LinearLayout{
	
	private LinearLayout newsLayout = null;
	
	private int tag;
	
	private PopItemClick popItemClickLister = null;
	
	private String[] names = null;
	private int[] resIds = null;
	
	public static final int State_Ticket = 1;
	public static final int State_PaySuccess = 2;
	
//	ActivityStackControlUtil.finishProgram();

	public PopNewsView(Context context, String[] names, int[] resIds) {
		super(context);
		// TODO Auto-generated constructor stub
		this.names = names;
		this.resIds = resIds;
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
		ContextUtil.getLayoutInflater(getContext()).inflate(R.layout.pop_msg, this);
		
		newsLayout = (LinearLayout)findViewById(R.id.pop_layout);
	}
	
	private void addItem()
	{
		for(int i = 0; i < names.length; i++)
		{
			View lineView = new View(getContext());
			lineView.setBackgroundResource(R.color.bg_line2_color);
			LinearLayout.LayoutParams lp = new LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 1);
			lp.setMargins(BaseActivity.getDensity_int(10), 0, BaseActivity.getDensity_int(10), 0);
			if(i != 0)
			{
				newsLayout.addView(lineView, lp);
			}
			View itemViews = ContextUtil.getLayoutInflater(getContext()).inflate(R.layout.pop_msg_item, null);
			itemViews.setId(i);
			ImageView iconNews = (ImageView)itemViews.findViewById(R.id.icon_news);
			final TextView txtNews = (TextView)itemViews.findViewById(R.id.txt_news);
			if(resIds != null)
			{
				iconNews.setBackgroundResource(resIds[i]);
				iconNews.setVisibility(View.VISIBLE);
			}else
			{
				iconNews.setVisibility(View.GONE);
			}
			txtNews.setText(names[i]);
			itemViews.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(popItemClickLister != null)
					{
						popItemClickLister.setPopItemClickLister(v.getId());
					}
				}
			});
			lp = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, BaseActivity.getDensity_int(45));
			lp.setMargins(BaseActivity.getDensity_int(10), 0, BaseActivity.getDensity_int(10), 0);
//			newsLayout.addView(itemViews, lp);
			newsLayout.addView(itemViews);
		}
	}
	
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

}
