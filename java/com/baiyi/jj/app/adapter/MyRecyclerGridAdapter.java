package com.baiyi.jj.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.ImageCorner;
import com.baiyi.jj.app.views.SideTextView;
import com.turbo.turbo.mexico.R;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerGridAdapter extends
		RecyclerView.Adapter<MyRecyclerGridAdapter.MyViewHolder> {

	private LayoutInflater inflater;
	private OnItemClickListener onItemClickListener;
	private Context context;
	private int imgW;
	private int imgH;
	private List<ChannelItem> data = new ArrayList<ChannelItem>();

	private List<ChannelItem> UserList = new ArrayList<>();

	private OnItemClick itemClick;

	private boolean isShowCheck = false;
	private boolean isCheckNull = false;

	public MyRecyclerGridAdapter(Context context) {
		this.context = context;

		this.inflater=LayoutInflater.from(context);
		imgW = (int) ((Config.getInstance().getScreenWidth(context) - ContextUtil
				.dip2px(context, 4)) * 0.5);
//		imgH = (int) imgW * 3 / 4;
		imgH = BaseActivity.getDensity_int(139);
	}

	public boolean isDataChange(List<ChannelItem> newData)
	{
		if (Utils.isStringEmpty(newData)){
			return false;
		}
		return !newData.equals(data);
	}

	public void setData(List<ChannelItem> list)
	{
		if(Utils.isStringEmpty(list))
		{
			return;
		}
		data.clear();
		data.addAll(list);
		this.notifyDataSetChanged();
	}

	public void setCheckData(List<ChannelItem> userList)
	{
		isShowCheck = true;
		if (Utils.isStringEmpty(userList)){
			isCheckNull = true;
			UserList.clear();
		}else {
			UserList.clear();
			UserList.addAll(userList);
			isCheckNull = false;
		}
		this.notifyDataSetChanged();
	}

	public List<ChannelItem> getData()
	{
		return data;
	}

	public List<ChannelItem> getCheckData()
	{
		return UserList;
	}

	public void clear()
	{
		if(Utils.isStringEmpty(data))
		{
			return;
		}
		data.clear();
		this.notifyDataSetChanged();
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		if (Utils.isStringEmpty(data)) {
			return 0;
		}
		return data.size();
//		if (isShowCheck){
//			return data.size();
//		}else {
//			return data.size()+1;
//		}

	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		View view = inflater
				.inflate(R.layout.item_channel_manager, arg0, false);
		MyViewHolder holder = new MyViewHolder(view);
		return holder;
	}

	class MyViewHolder extends RecyclerView.ViewHolder {

		public MyViewHolder(View convertView) {
			super(convertView);
			btnItem = (RelativeLayout) convertView.findViewById(R.id.btn_item);
			imgChannel = (ImageCorner) convertView.findViewById(R.id.channel_img);
			imgChannel.getLayoutParams().width = imgW;
			imgChannel.getLayoutParams().height = imgH;
			linRetrun = (LinearLayout) convertView.findViewById(R.id.lin_retunhome);
			linRetrun.getLayoutParams().width = imgW;
			linRetrun.getLayoutParams().height = imgH;

			nameChannel = (TextView) convertView
					.findViewById(R.id.channel_name);
			nameMore = (TextView) convertView
					.findViewById(R.id.channel_name_more);
			imgCheck = (ImageView) convertView.findViewById(R.id.img_check);
			imgCheck.getLayoutParams().width = imgW;
			imgCheck.getLayoutParams().height = imgH;
		}

		RelativeLayout btnItem;
		ImageCorner imgChannel;
		TextView nameChannel;
		TextView nameMore;
		ImageView imgCheck;
		LinearLayout linRetrun;

	}

	@Override
	public void onBindViewHolder(final MyViewHolder holder, final int position) {
		if (position < data.size()) {
			holder.nameMore.setVisibility(View.GONE);
			final ChannelItem entity = data.get(position);
			if (entity.getCid().equals("-1")){
				holder.nameChannel.setVisibility(View.GONE);
				holder.imgChannel.setVisibility(View.GONE);
				holder.linRetrun.setVisibility(View.VISIBLE);

				holder.btnItem.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						if (itemClick != null) {
							itemClick.onClick(null,-1);
						}
					}
				});
				return;
			}
			holder.linRetrun.setVisibility(View.GONE);
			holder.nameChannel.setVisibility(View.VISIBLE);
			holder.nameChannel.setText(entity.getChannel_name());
			holder.imgChannel.setVisibility(View.VISIBLE);
			holder.imgChannel.loadImg(context,entity.getConvert_img());
//			loadImg(entity.getConvert_img(), holder);
			holder.btnItem.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					if (isShowCheck) {

						isCheckNull = false;
						if (holder.imgCheck.getVisibility() == View.VISIBLE) {
							holder.imgCheck.setVisibility(View.INVISIBLE);
							holder.imgCheck.setSelected(false);
							data.get(position).setIs_default("false");
							removeChannel(UserList,entity);
						} else {
							holder.imgCheck.setVisibility(View.VISIBLE);
							holder.imgCheck.setSelected(true);
							data.get(position).setIs_default("true");
							if (!isContain(UserList,entity)) {
								UserList.add(entity);
							}
						}
					}
//					for (int i =0;i<UserList.size();i++){
//						Log.e("user","--------------"+UserList.get(i).getChannel_name()+entity.getIs_default());
//					}
					if (itemClick != null) {
						itemClick.onClick(entity,position);
					}
				}
			});

			if (isShowCheck && !isCheckNull){
				if (data.get(position).getIs_default().equals("true")) {
					holder.imgCheck.setVisibility(View.VISIBLE);
					holder.imgCheck.setSelected(true);
				} else {
					holder.imgCheck.setVisibility(View.INVISIBLE);
					holder.imgCheck.setSelected(false);
				}

			}else if (isShowCheck && isCheckNull){
				if (position<5){
					holder.imgCheck.setVisibility(View.VISIBLE);
					holder.imgCheck.setSelected(true);
					if (!UserList.contains(entity)) {
						data.get(position).setIs_default("true");
						UserList.add(entity);
					}
				}else {
					holder.imgCheck.setVisibility(View.INVISIBLE);
					holder.imgCheck.setSelected(false);
				}

			}

		}
	}
	private void removeChannel(List<ChannelItem> list,ChannelItem item){
		for (int i = 0;i<list.size();i++){
			if(list.get(i).getChannel_name().equals(item.getChannel_name())){
				list.remove(i);
				break;
			}
		}
	}

	private boolean isContain(List<ChannelItem> list,ChannelItem item){
		for (int i = 0;i<list.size();i++){
			if(list.get(i).getChannel_name().equals(item.getChannel_name())){
				return true;
			}
		}
		return false;
	}

	public interface OnItemClick {
		public void onClick(ChannelItem entity,int postion);
	}

	public void setItemClick(OnItemClick itemClick) {
		this.itemClick = itemClick;
	}

	public interface OnItemClickListener {
		void onItemClick(View view, int position);

		void onItemLongClick(View view, int position);
	}

	public void setOnClick(final MyViewHolder viewHolder) {
		if (onItemClickListener != null) {

			// viewHolder.content.setOnClickListener(new View.OnClickListener()
			// {
			// @Override
			// public void onClick(View v) {
			// onItemClickListener.onItemClick(viewHolder.itemView,
			// viewHolder.getLayoutPosition());
			// }
			// });
			// viewHolder.content.setOnLongClickListener(new
			// View.OnLongClickListener() {
			// @Override
			// public boolean onLongClick(View v) {
			//
			// onItemClickListener.onItemLongClick(viewHolder.itemView,
			// viewHolder.getLayoutPosition());
			// return false;
			// }
			// });

		}
	}


}
