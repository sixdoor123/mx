/**
 * 
 */
package com.baiyi.cmall.activities.main.mall.holder;

import com.baiyi.cmall.R;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * 商城-详情-评论
 * @author tangkun
 *
 */
public class CommentHolder extends RecyclerView.ViewHolder{
	
	public ImageView imgHead = null;
	public TextView txtName = null;
	public TextView txtTime = null;
	public TextView txtContent = null;
	public RatingBar rbCount = null;
	public LinearLayout buyTipGroup = null;

	/**
	 * @param arg0
	 */
	public CommentHolder(View view) {
		super(view);
		imgHead = (ImageView)view.findViewById(R.id.img_mall_detail_comment_head);
		txtName = (TextView)view.findViewById(R.id.txt_mall_detail_comment_name);
		txtTime = (TextView)view.findViewById(R.id.txt_mall_detail_comment_time);
		txtContent = (TextView)view.findViewById(R.id.txt_mall_detail_comment_content);
		rbCount = (RatingBar)view.findViewById(R.id.rating_mall_detail_comment);
		buyTipGroup = (LinearLayout)view.findViewById(R.id.mall_detail_comment_buytip_group);
	}

}
