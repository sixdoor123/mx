package com.baiyi.jj.app.listitem.search;

import com.baiyi.jj.app.entity.NewsListEntity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;

public class SearchHolder extends RecyclerView.ViewHolder{
	
	private OnDeleteColClick onDeleteColClick = null;
	
	private OnItemClick RecycleItemClick = null;
	
	public SearchHolder(View arg0) {
		super(arg0);
		arg0.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (RecycleItemClick != null) {
					RecycleItemClick.OnClick(getPosition());
				}
			}
		});
	}
	
	

	public void setOnDeleteColClick(OnDeleteColClick onDeleteColClick) {
		this.onDeleteColClick = onDeleteColClick;
	}

	public interface OnDeleteColClick{
		public void OnClick(NewsListEntity entity, int position);
	}

	public void setRecycleItemClick(OnItemClick recycleItemClick) {
		RecycleItemClick = recycleItemClick;
	}

	public interface OnItemClick{
		public void OnClick (int position);
	}
}
