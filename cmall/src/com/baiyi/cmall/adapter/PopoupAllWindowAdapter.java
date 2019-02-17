package com.baiyi.cmall.adapter;

import com.baiyi.cmall.utils.PopoupWindowUtil;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * �����б������������Ӧ���ɹ���
 * 
 */
public class PopoupAllWindowAdapter extends BaseAdapter {

	// ������
	private Context context;
	// ����
	private String[] datas;

	// ���ֽ�����
	private LayoutInflater inflater;

	public PopoupAllWindowAdapter(Context context, String[] datas) {
		this.context = context;
		this.datas = datas;

		inflater = ContextUtil.getLayoutInflater(context);
	}

	@Override
	public int getCount() {
		return datas.length == 0 ? 0 : datas.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datas[position];
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
//			holder.mImgRight = (ImageView) convertView
//					.findViewById(R.id.img_right_arrow);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
			holder.mImgSelected.setVisibility(View.VISIBLE);
			holder.mImgRight.setVisibility(View.GONE);
			if (0 == position) {
				holder.mImgSelected.setImageResource(R.drawable.selected_red);
				holder.mTxtItemSel.setTextColor(context.getResources()
						.getColor(R.color.bg_buyer));
				
//				PopoupWindowUtil.setTextViews(holder.mTxtItemSel);
				PopoupWindowUtil.setImageViews(holder.mImgSelected);
			} else {
				holder.mImgSelected.setImageResource(R.drawable.no_selected);
			}
		holder.mTxtItemSel.setText(datas[position]);

		return convertView;
	}

	static class ViewHolder {
		TextView mTxtItemSel;
		ImageView mImgSelected;
		ImageView mImgRight;
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

}
