package com.baiyi.cmall.activities.main.business.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.business.entity.FeedBackEntity;
import com.baiyi.cmall.activities.main.business.help.FeedBackSystemHolder;
import com.baiyi.cmall.activities.main.business.help.FeedBackUserHolder;
import com.baiyi.cmall.activities.main.business.help.FeedBackUserImgHolder;
import com.baiyi.cmall.activities.main.business.help.FeedBackUserNoImgHolder;
import com.baiyi.cmall.activities.main.mall.adapter.BaseRecyclerAdapter;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.util.ContextUtil;

public class FeedBackAdapter extends BaseRecyclerAdapter<FeedBackEntity> {

	public static final int Type_User = 2;
	public static final int Type_User_NoImg = 3;
	public static final int Type_User_Img = 4;
	public static final int Type_System = 1;

	/**
	 * @param context
	 * @param objects
	 */
	public FeedBackAdapter(Context context, ArrayList<FeedBackEntity> objects) {
		super(context, objects);
		// TODO Auto-generated constructor stub
	}

	public void addItem(FeedBackEntity entity) {
		getDataList().add(entity);
		this.notifyDataSetChanged();
	}

	public void deleteItem(int postion) {
		getDataList().remove(postion);
		this.notifyItemRemoved(postion);
		// this.notifyDataSetChanged();
	}

	public void clearAll() {
		getDataList().clear();
		this.notifyDataSetChanged();
	}

	@Override
	public ViewHolder onCreateVH(ViewGroup parent, int viewType) {
		// TODO Auto-generated method stub
		View v = null;
		ViewHolder holer = null;
		switch (viewType) {
		case Type_User:
			v = ContextUtil.getLayoutInflater(parent.getContext()).inflate(
					R.layout.item_feedback_user, parent, false);
			holer = new FeedBackUserHolder(v, context);
			break;
		case Type_User_Img:
			v = ContextUtil.getLayoutInflater(parent.getContext()).inflate(
					R.layout.item_feedback_user, parent, false);
			holer = new FeedBackUserImgHolder(v, context);
			break;
		case Type_User_NoImg:
			v = ContextUtil.getLayoutInflater(parent.getContext()).inflate(
					R.layout.item_feedback_user, parent, false);
			holer = new FeedBackUserNoImgHolder(v);
			break;
		case Type_System:
			v = ContextUtil.getLayoutInflater(parent.getContext()).inflate(
					R.layout.item_feedback_system, parent, false);
			holer = new FeedBackSystemHolder(v);
			break;
		}
		return holer;
	}

	@Override
	public void onBindVH(ViewHolder holder, int position) {
		// TODO Auto-generated method stub
		FeedBackEntity entity = getDataList().get(position);
		switch (getItemViewType(position)) {
		case Type_User:
			FeedBackUserHolder userHolder = (FeedBackUserHolder) holder;
			userHolder.txtContent.setText(entity.getAnswer());
			userHolder.txtTime.setText(entity.getReply_time());
			userHolder.imgHead.setImageDrawable(context.getResources()
					.getDrawable(R.drawable.img_feed_system));
			break;
		case Type_User_Img:
			FeedBackUserImgHolder userImgHolder = (FeedBackUserImgHolder) holder;
			userImgHolder.txtContent.setVisibility(View.GONE);
			if (!Utils.isStringEmpty(entity.getImgList())) {
				userImgHolder.linAddImg.removeAllViews();
				for (int i = 0; i < entity.getImgList().size(); i++) {
					// ImageView img = new ImageView(context);
					View view = LayoutInflater.from(context).inflate(
							R.layout.item_feedback_image, null);
					ImageView img = (ImageView) view
							.findViewById(R.id.imageview);
					userImgHolder.setBitmap(true, entity.getImgList().get(i),
							img, false);
					userImgHolder.linAddImg.addView(view);
				}
			}
			userImgHolder.txtTime.setText(entity.getCreated_at());
			// setBitmap(true, imgHeadUrl, imgHead, true);
			// userImgHolder.imgHead.setImageDrawable(context.getDrawable(R.drawable.img_feed_system));
			break;
		case Type_User_NoImg:
			FeedBackUserNoImgHolder userNoImgHolder = (FeedBackUserNoImgHolder) holder;
			userNoImgHolder.txtContent.setText(entity.getQuestion());
			userNoImgHolder.txtTime.setText(entity.getCreated_at());
			// setBitmap(true,imgHeadUrl,imgHead,true);
			// userNoImgHolder.imgHead.setImageDrawable(context.getDrawable(R.drawable.img_feed_system));
			break;
		case Type_System:
			FeedBackSystemHolder sysHolder = (FeedBackSystemHolder) holder;
			sysHolder.txtContent.setText(entity.getAnswer());
			sysHolder.txtTime.setText(entity.getReply_time());
			break;
		}

	}

	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return getDataList().get(position).getUserType();
	}

	// @Override
	// public int itemViewType(int position) {
	// // TODO Auto-generated method stub
	// return data.get(position).getUserType();
	// // if (entity.getUserType() == Type_System) {
	// // return Type_System;
	// // } else if (entity.getUserType() == Type_User) {
	// // return Type_User;
	// // } else if (entity.getUserType() == Type_User_NoImg) {
	// // return Type_User_NoImg;
	// // } else if (entity.getUserType() == Type_User_Img) {
	// // return Type_User_Img;
	// // } else {
	// // return Type_User_NoImg;
	// // }
	// }
}
