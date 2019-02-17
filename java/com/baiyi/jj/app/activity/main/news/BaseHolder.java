/**
 *
 */
package com.baiyi.jj.app.activity.main.news;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;

import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.listitem.news.ListItemBaseNews;
import com.baiyi.jj.app.utils.Utils;


/**
 * @author tangkun
 *
 */
public class BaseHolder extends RecyclerView.ViewHolder{

	private RecyclerViewItemClick recyclerViewItemClick = null;

	/**
	 * @param arg0
	 */
	public BaseHolder(View arg0) {
		super(arg0);
		arg0.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	if(recyclerViewItemClick != null)
            	{
            		recyclerViewItemClick.setOnItemClickLister(getPosition());
            	}
            }
        });
		// TODO Auto-generated constructor stub
	}

	public void setRecyclerViewItemClick(RecyclerViewItemClick recyclerViewItemClick) {
		this.recyclerViewItemClick = recyclerViewItemClick;
	}

	public interface RecyclerViewItemClick
	{
		public void setOnItemClickLister(int position);
	}

	public static final int Visible_Type_Gone = 0;
	public static final int Visible_Type_RightImg = 1;
	public static final int Visible_Type_FillImg = 2;

	public static final int AbstractMaxSize = 120;

	public PopMoreOnclick popMoreOnclick = null;
	public PopDislikeOnclick popDislikeOnclick = null;

	public static boolean isVisibleTitleImg(String txtAbstract)
	{
		int abtractLines = AccountManager.getInstance().getSummary_Is_Display();
		if(abtractLines == 0 || Utils.isStringEmpty(txtAbstract))
		{
			return true;
		}
		return false;
	}

	//0 ����ʾժҪ����ʾ��ͼ 1.��С 2ͨ��
	public static int isAbstractType(NewsListEntity data)
	{
		if(BaseHolder.isVisibleTitleImg(data.getArticle_abstract()))
		{
			return 0;
		}
		if(Utils.isStringEmpty(data.getTemplate()))
		{
			return 0;
		}
		if(data.getTemplate().equals(String.valueOf(ListItemBaseNews.Temp_Image_small)) ||
				data.getTemplate().equals(String.valueOf(ListItemBaseNews.Temp_Image_three)))
		{
			if(data.getArticle_abstract().length() < AbstractMaxSize)
			{
				return 0;
			}else
			{
				return Visible_Type_RightImg;
			}
		}else
		{
			if(data.getArticle_abstract().length() < AbstractMaxSize)
			{
				return 0;
			}else
			{
				return Visible_Type_FillImg;
			}
		}
	}

	public void setPopMoreOnclick(PopMoreOnclick popMoreOnclick) {
		this.popMoreOnclick = popMoreOnclick;
	}

	public interface PopMoreOnclick
	{
		public void setPopMoreOnclick(int position, NewsListEntity entity);
	}

	public void setPopDislikeOnclick(PopDislikeOnclick popDislikeOnclick) {
		this.popDislikeOnclick = popDislikeOnclick;
	}

	public interface PopDislikeOnclick
	{
		public void setPopClick(int position);
	}
}
