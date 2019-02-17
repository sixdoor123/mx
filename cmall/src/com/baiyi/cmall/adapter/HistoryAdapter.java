package com.baiyi.cmall.adapter;

import java.util.ArrayList;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 搜索历史记录适配器
 * 
 * @author lizl
 * 
 */
public class HistoryAdapter extends BaseAdapter {

	private Context context;
	// 搜索历史记录数据源
	private ArrayList<String> historyInfo;

	public HistoryAdapter(Context context) {
		this.context = context;
		this.historyInfo = new ArrayList<String>();
	}

	@Override
	public int getCount() {
		if (historyInfo.size() > 5) {
			return 5;
		}
		return historyInfo.size();
	}

	@Override
	public Object getItem(int position) {
		return historyInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		viewHold holdler = null;
		if (convertView == null) {
			convertView = ContextUtil.getLayoutInflater(context).inflate(R.layout.item_history_info, null);
			holdler = new viewHold();
			holdler.mTvHistory = (TextView) convertView.findViewById(R.id.tv_his);
			holdler.mLinDelete = (LinearLayout) convertView.findViewById(R.id.lin_his_delete);
			convertView.setTag(holdler);
		} else {
			holdler = (viewHold) convertView.getTag();
		}

		// 每个条目的字符信息
		final String info = historyInfo.get(position);
		holdler.mTvHistory.setText(info);

//		if ("nothing".equals(info)) {
//			// 隐藏删除按钮
//			holdler.mLinDelete.setVisibility(View.GONE);
//			// 设置控件不能操作
//			holdler.mTvHistory.setEnabled(false);
//		}
		// 历史记录点击事件，设置数据
		holdler.mTvHistory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listener.onSetSearchInfo(info);
			}
		});

		// 历史记录长按点击事件，删除所有历史记录
		holdler.mTvHistory.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				listener.onBtnDeleteAll();
				return true;
			}
		});

		// 删除单个历史记录
		holdler.mLinDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				listener.onBtnDelete(position, info);
			}
		});
		return convertView;
	}

	static class viewHold {

		TextView mTvHistory;
		LinearLayout mLinDelete;
	}

	/**
	 * 刷新数据，通知数据源发生改变
	 * 
	 * @param historyInfo
	 */
	public void setData(ArrayList<String> historyInfo) {
//		this.historyInfo.clear();
		this.historyInfo = historyInfo;
		Log.d("TAG", "setData()---"+historyInfo.size());
		notifyDataSetChanged();
		if (null != listener) {
			listener.onBtnShow(historyInfo.size() <= 0 ? false : true);
		}
	}

	/**
	 * 删除按钮的监听器
	 */
	private OnBtnDeleteClickListener listener;

	public void setListener(OnBtnDeleteClickListener listener) {
		this.listener = listener;
	}

	public interface OnBtnDeleteClickListener {
		void onBtnDelete(int position, String info);

		void onSetSearchInfo(String info);

		void onBtnDeleteAll();

		void onBtnShow(Boolean isShow);
	}

}
