package com.baiyi.cmall.adapter;

import java.util.ArrayList;

import com.baiyi.cmall.R;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.utils.PopoupWindowUtil;
import com.baiyi.core.util.ContextUtil;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * �����б������������Ӧ���ɹ���
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-4 ����10:30:39
 */
public class PopoupWindowAdapter extends BaseAdapter {

	// ������
	private Context context;
	// ����
	private ArrayList<SelectedInfo> datas;
	// ״̬ 0��ʾһ���˵�����
	// 1 ��ʾ�����˵�����
	private int state;

	// ���ֽ�����
	private LayoutInflater inflater;
	// ѡ�е�����
	private String selectWord;

	public PopoupWindowAdapter(Context context, ArrayList<SelectedInfo> datas,
			int state, String selectWord) {
		this.context = context;
		this.datas = datas;
		this.state = state;
		this.selectWord = selectWord;

		inflater = ContextUtil.getLayoutInflater(context);
	}

	@Override
	public int getCount() {
		return datas == null ? 0 : datas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {

			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.list_item_popoupwindow,
					null);

			holder.mImgSelected = (ImageView) convertView
					.findViewById(R.id.img_selected);
			holder.mTxtItemSel = (TextView) convertView
					.findViewById(R.id.txt_item_sel);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String itemWord = datas.get(position).getCm_categoryname();
		if (itemWord.equals(selectWord)) {
			convertView.setBackgroundColor(context.getResources().getColor(
					R.color.bg_hui3));
			holder.mTxtItemSel.setTextColor(context.getResources().getColor(
					R.color.bg_buyer));
			holder.mImgSelected.setImageResource(R.drawable.selected_red);
			// holder.mImgRight.setVisibility(View.VISIBLE);
			holder.mImgSelected.setVisibility(View.VISIBLE);
			PopoupWindowUtil.setImageViews(holder.mImgSelected);
			PopoupWindowUtil.setTextViews(holder.mTxtItemSel);
			PopoupWindowUtil.setViews(convertView);
			Log.d("TAG", PopoupWindowUtil.getImageViews().size()
					+ "-------itemWord--------" + itemWord);
		} else {
			convertView.setBackgroundColor(context.getResources().getColor(
					R.color.bg_white));
			holder.mTxtItemSel.setTextColor(context.getResources().getColor(
					R.color.bg_black));
			holder.mImgSelected.setImageResource(R.drawable.selected_red);
			holder.mImgSelected.setVisibility(View.GONE);
		}
		holder.mTxtItemSel.setText(itemWord);

		return convertView;
	}

	static class ViewHolder {
		TextView mTxtItemSel;
		ImageView mImgSelected;
		// ImageView mImgRight;
	}

	private OnSelectedSeconedItemListener itemListener;

	public void setItemListener(OnSelectedSeconedItemListener itemListener) {
		this.itemListener = itemListener;
	}

	/**
	 * ��ѡ�������Ŀʱ���ı�������ɫ��״̬
	 */
	public interface OnSelectedSeconedItemListener {
		void onSelectedSeconedItem(ImageView mImgSelected, TextView mTextSel);
	}

	public void setData(ArrayList<SelectedInfo> state) {
		this.datas = state;
		notifyDataSetChanged();
	}

	public void setFirstWord(String firstWords) {
		this.selectWord = firstWords;
	}

	public void setSortWords(String sortWords) {
		this.selectWord = sortWords;
		notifyDataSetChanged();
	}
}
