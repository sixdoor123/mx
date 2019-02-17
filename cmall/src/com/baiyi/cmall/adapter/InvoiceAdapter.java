package com.baiyi.cmall.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;
import com.baiyi.cmall.entity.OrderEntity;
import com.baiyi.cmall.utils.Utils;

/**
 * ���ǲɹ��̡� ��Ʊ�б�������
 * 
 * @author lizl
 * 
 */
public class InvoiceAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<OrderEntity> datas;
	// ������
	private LayoutInflater inflater;
	private int max;

	public InvoiceAdapter(Context context) {
		this.context = context;
		this.datas = new ArrayList<OrderEntity>();
		this.inflater = ContextUtil.getLayoutInflater(context);
	}

	/**
	 * �������µ����ݣ����----max��
	 */
	@Override
	public int getCount() {

		return datas.size() > max ? max : datas.size();
	}

	@Override
	public Object getItem(int position) {

		return datas.get(position);

	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_invoice_info, null);

			holder.isDefault = (ImageView) convertView
					.findViewById(R.id.img_is_select);
			holder.imgEdit = (ImageView) convertView
					.findViewById(R.id.img_invoice_edit);
			holder.invoiceTitle = (TextView) convertView
					.findViewById(R.id.tv_invoice_title);
			holder.invoiceContent = (TextView) convertView
					.findViewById(R.id.tv_invoice_content);
			holder.invoiceType = (TextView) convertView
					.findViewById(R.id.tv_invoice_type);
			holder.delete = (TextView) convertView.findViewById(R.id.delete);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final OrderEntity info = datas.get(position);

		boolean isDefault = info.isDefault();
		// Ĭ�ϵ���ӱ�־
		if (isDefault) {
			holder.isDefault.setVisibility(View.VISIBLE);
		} else {
			holder.isDefault.setVisibility(View.GONE);
		}
		holder.invoiceTitle.setText(info.getTitle());// ��Ʊ̧ͷ
		holder.invoiceContent.setText(info.getContext());// ����
		holder.invoiceType.setText(info.getInvoicetypename());// ����
		holder.imgEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				listener.onBtnEdit(info);
			}
		});
		holder.delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listener.onBtnDelete(info);
			}
		});

		return convertView;
	}

	static class ViewHolder {
		TextView invoiceTitle;// ̧ͷ
		TextView invoiceContent;// ����
		TextView invoiceType;// ����
		ImageView isDefault;// �Ƿ�Ĭ��
		ImageView imgEdit;// �༭
		TextView delete;// ɾ��

	}

	/**
	 * �������ݣ�����ˢ���������
	 * 
	 * @param datas
	 */
	public void setData(ArrayList<OrderEntity> datas, int max) {
		if (Utils.isStringEmpty(datas)) {
			this.datas = new ArrayList<OrderEntity>();
		}
		this.max = max;
		this.datas.clear();
		this.datas.addAll(datas);
		notifyDataSetChanged();
	}

	/**
	 * �������ݣ�����ˢ���������
	 * 
	 * @param datas
	 */
	public void addData(ArrayList<OrderEntity> datas) {
		if (Utils.isStringEmpty(datas)) {
			this.datas = new ArrayList<OrderEntity>();
		}

		this.datas.addAll(datas);
		notifyDataSetChanged();
	}

	/**
	 * ɾ����ť�ļ�����
	 */
	private OnInVoiceEditClickListener listener;

	public void setEditListener(OnInVoiceEditClickListener listener) {
		this.listener = listener;
	}

	public interface OnInVoiceEditClickListener {

		void onBtnEdit(OrderEntity entity);

		void onBtnDelete(OrderEntity entity);

	}

}
