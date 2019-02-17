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
 * ������ʷ��¼������
 * 
 * @author lizl
 * 
 */
public class HistoryAdapter extends BaseAdapter {

	private Context context;
	// ������ʷ��¼����Դ
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

		// ÿ����Ŀ���ַ���Ϣ
		final String info = historyInfo.get(position);
		holdler.mTvHistory.setText(info);

//		if ("nothing".equals(info)) {
//			// ����ɾ����ť
//			holdler.mLinDelete.setVisibility(View.GONE);
//			// ���ÿؼ����ܲ���
//			holdler.mTvHistory.setEnabled(false);
//		}
		// ��ʷ��¼����¼�����������
		holdler.mTvHistory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listener.onSetSearchInfo(info);
			}
		});

		// ��ʷ��¼��������¼���ɾ��������ʷ��¼
		holdler.mTvHistory.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				listener.onBtnDeleteAll();
				return true;
			}
		});

		// ɾ��������ʷ��¼
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
	 * ˢ�����ݣ�֪ͨ����Դ�����ı�
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
	 * ɾ����ť�ļ�����
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
