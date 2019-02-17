package com.baiyi.cmall.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.utils.Utils;

/**
 * �ҵĲɹ�����������
 * 
 * @author lizl
 * 
 */
public class MyPurchaseOrderAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<GoodsSourceInfo> datas;
	// ������
	private LayoutInflater inflater;
	// Ĭ��״̬
	private int state = 0;

	public MyPurchaseOrderAdapter(Context context) {
		this.context = context;
		this.datas = new ArrayList<GoodsSourceInfo>();
		inflater = ContextUtil.getLayoutInflater(context);
	}

	@Override
	public int getCount() {
		int count = datas.size();
		// ״̬Ϊ0�����ʾ��ʾ�����������
		// ״̬Ϊ1�����ʾ��ʾȫ������
		if (state == 1) {
			return count;
		} else {
			if (count >= 3) {
				return 3;
			}
			return datas.size() == 0 ? 0 : datas.size();
		}
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_5_view, null);

			holder.merchantNmae = (TextView) convertView
					.findViewById(R.id.tv_content1);
			holder.auditState = (TextView) convertView.findViewById(R.id.tv_content2);

			holder.companyName = (TextView) convertView.findViewById(R.id.tv_content3);
			holder.numberTextView = (TextView) convertView
					.findViewById(R.id.tv_content4);
			holder.price = (TextView) convertView.findViewById(R.id.tv_content5);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final GoodsSourceInfo info = datas.get(position);
		holder.merchantNmae.setText(info.getGoodSName());
		holder.auditState.setText(info.getIntentionOrderState());
		holder.companyName.setText(info.getGoodSCompanyNmae());
		holder.numberTextView.setText(Utils.getWeight(info.getKuCun()) + "��");
		holder.price.setText(Utils.dealPrice(info.getGoodSPrice()) + "Ԫ/��");

		return convertView;
	}

	static class ViewHolder {
		TextView merchantNmae;// ��Ӧ������
		TextView auditState;// ״̬
		TextView companyName;// ��˾����
		TextView numberTextView;// ����
		TextView price;// �۸�Χ
	}

	/**
	 * �������ݣ�����ˢ���������
	 * 
	 * @param datas
	 */
	public void setData(ArrayList<GoodsSourceInfo> datas) {
		if (Utils.isStringEmpty(datas)) {
			datas = new ArrayList<GoodsSourceInfo>();
		}
		this.datas.clear();
		this.datas.addAll(datas);
		notifyDataSetChanged();
	}

	/**
	 * ������������״̬
	 * 
	 * @param state
	 *            ״̬
	 */
	public void setState(int state) {
		this.state = state;

	}
}
