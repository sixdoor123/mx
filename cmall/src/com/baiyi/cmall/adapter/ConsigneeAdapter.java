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
 * ���ǲɹ��̡� �ջ����б�������
 * 
 * @author lizl
 * 
 */
public class ConsigneeAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<OrderEntity> datas;
	// ������
	private LayoutInflater inflater;

	public ConsigneeAdapter(Context context) {
		this.context = context;
		this.datas = new ArrayList<OrderEntity>();
		inflater = ContextUtil.getLayoutInflater(context);
	}

	@Override
	public int getCount() {
		return datas.size() > 8 ? 8 : datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);

	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_consignee_address,
					null);

			holder.isDefault = (ImageView) convertView
					.findViewById(R.id.img_is_select);
			holder.imgEdit = (ImageView) convertView
					.findViewById(R.id.img_consignee_edit);
			holder.receivePerson = (TextView) convertView
					.findViewById(R.id.tv_receive_person);
			holder.phone = (TextView) convertView
					.findViewById(R.id.tv_tel_phone);

			holder.address = (TextView) convertView
					.findViewById(R.id.tv_goods_address);
			holder.city = (TextView) convertView.findViewById(R.id.tv_city);
			holder.delete = (TextView) convertView.findViewById(R.id.delete);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final OrderEntity info = datas.get(position);

		boolean isDefault = info.isDefault();
		// Ĭ�ϵĵ�ַ��ӱ�־
		if (isDefault) {
			holder.isDefault.setVisibility(View.VISIBLE);
		} else {
			holder.isDefault.setVisibility(View.GONE);
		}
		holder.receivePerson.setText(info.getReceivername());// �ջ���
		holder.phone.setText(info.getPhone());// �绰
		holder.address.setText(info.getAddress());// �ջ���ַ
		holder.city.setText(info.getOrderCity());// ����
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
		TextView receivePerson;// �ջ���
		TextView phone;// �绰
		TextView address;// �ջ���ַ
		TextView city;// �ջ���ַ
		ImageView isDefault;// �Ƿ�Ĭ��
		ImageView imgEdit;// �༭
		TextView delete;// ɾ��

	}

	/**
	 * �������ݣ�����ˢ���������
	 * 
	 * @param datas
	 */
	public void setData(ArrayList<OrderEntity> datas) {
		if (Utils.isStringEmpty(datas)) {
			this.datas = new ArrayList<OrderEntity>();
		}
		this.datas.clear();
		this.datas.addAll(datas);
		notifyDataSetChanged();
	}

	/**
	 * �������ݣ���ҳ����������
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
	private OnEditClickListener listener;

	public void setListener(OnEditClickListener listener) {
		this.listener = listener;
	}

	public interface OnEditClickListener {

		void onBtnEdit(OrderEntity entity);

		void onBtnDelete(OrderEntity entity);
	}

}
