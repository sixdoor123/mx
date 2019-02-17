package com.baiyi.jj.app.adapter;

import java.util.ArrayList;
import java.util.List;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.cache.bean.HistoryBean;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.utils.Utils;

import android.R.integer;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchHistoryAdapter extends
		RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private Context context = null;
	private List<HistoryBean> keyList = null;

	private OnItemClick itemClick;

	private int footCount = 1;

	public static final int ITEM_TYPE_CONTENT = 1;
	public static final int ITEM_TYPE_BOTTOM = 2;

	public SearchHistoryAdapter(Context context) {
		this.context = context;
		keyList = new ArrayList<HistoryBean>();

	}

	public void setData(List<HistoryBean> list) {
		if (Utils.isStringEmpty(list)) {
			return;
		}
		keyList.clear();
		keyList.addAll(list);
		this.notifyDataSetChanged();
	}

	public void addData(HistoryBean string) {
		if (string == null || string.getHisName() == null) {
			return;
		}
		keyList.add(string);
		this.notifyDataSetChanged();
	}

	public List<HistoryBean> getData() {
		return keyList;
	}

	public void clear() {
		if (Utils.isStringEmpty(keyList)) {
			return;
		}
		keyList.clear();
		this.notifyDataSetChanged();
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		if (Utils.isStringEmpty(keyList)) {
			return 0;
		}
		return keyList.size() + footCount;
	}

	public int getContentItemCount() {
		return keyList.size();
	}

	@Override
	public int getItemViewType(int position) {
		int dataItemCount = getContentItemCount();
		if (footCount != 0 && position >= dataItemCount) {
			return ITEM_TYPE_BOTTOM;
		} else {
			return ITEM_TYPE_CONTENT;
		}
	}
	

	@Override
	public void onBindViewHolder(ViewHolder arg0, int arg1) {
		if (arg0 instanceof BottomViewHolder) {
			((BottomViewHolder) arg0).linItem
					.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {
							clear();
							if (itemClick != null) {
								itemClick.onClick(null);
							}
						}
					});
		} else if (arg0 instanceof MyViewHolder) {

			final String string = keyList.get(arg1).getHisName();
			if (!Utils.isStringEmpty(string)) {
				((MyViewHolder) arg0).txtWords.setText(string);
				((MyViewHolder) arg0).btnItem
						.setOnClickListener(new OnClickListener() {

							public void onClick(View v) {
								if (itemClick != null) {
									itemClick.onClick(string);
								}
							}
						});
			}
		}

	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		if (arg1 == ITEM_TYPE_BOTTOM) {
			BottomViewHolder holder = new BottomViewHolder(LayoutInflater.from(
					context).inflate(R.layout.foot_clearhis, arg0, false));
			return holder;
		} else if (arg1 == ITEM_TYPE_CONTENT) {

			View view = ContextUtil.getLayoutInflater(context).inflate(
					R.layout.item_history, arg0, false);
			MyViewHolder viewHolder = new MyViewHolder(view);
			return viewHolder;
		}
		return null;

	}

	class MyViewHolder extends RecyclerView.ViewHolder {

		public MyViewHolder(View arg0) {
			super(arg0);
			btnItem = (LinearLayout) arg0.findViewById(R.id.btn_item);
			txtWords = (TextView) arg0.findViewById(R.id.txt_historyword);
			imgHis = (ImageView) arg0.findViewById(R.id.img_history);
			txtClear = (TextView) arg0.findViewById(R.id.txt_clear);
		}

		LinearLayout btnItem;
		TextView txtWords = null;
		ImageView imgHis = null;
		TextView txtClear = null;
	}

	public class BottomViewHolder extends RecyclerView.ViewHolder {
		public BottomViewHolder(View itemView) {
			super(itemView);
			linItem = (LinearLayout) itemView.findViewById(R.id.lin_item);
		}

		LinearLayout linItem;
	}

	public interface OnItemClick {
		public void onClick(String string);
	}

	public void setItemClick(OnItemClick itemClick) {
		this.itemClick = itemClick;
	}

}
