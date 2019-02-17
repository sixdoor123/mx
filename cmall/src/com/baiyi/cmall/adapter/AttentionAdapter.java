package com.baiyi.cmall.adapter;

import java.util.ArrayList;

import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * ��ע��������
 * 
 * @author sunxy
 * 
 */
public class AttentionAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<GoodsSourceInfo> datas;
	private LayoutInflater inflater;

	// ��¼��ǰ������һ��ViewPage�� ���ղزɹ������ղع�Ӧ��
	// 0 ��ʾ�ղزɹ� 1 ��ʾ�ղع�Ӧ
	private int temp = 0;

	public AttentionAdapter(Context context, int temp) {
		this.context = context;
		this.temp = temp;
		this.datas = new ArrayList<GoodsSourceInfo>();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_5_view, null);

			holder.mTxtPurchaseContent = (TextView) convertView
					.findViewById(R.id.tv_content1);
			holder.mTxtSubPiceWay = (TextView) convertView
					.findViewById(R.id.tv_content2);
			holder.mTxtSpecification = (TextView) convertView
					.findViewById(R.id.tv_content3);
			holder.mTxtSubWeight = (TextView) convertView
					.findViewById(R.id.tv_content4);
			holder.mTxtPrePrice = (TextView) convertView
					.findViewById(R.id.tv_content5);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final GoodsSourceInfo info = datas.get(position);

		holder.mTxtSpecification.setText(info.getGoodSSpecification());
		holder.mTxtSubWeight.setText(info.getGoodSWeight() + "��");
		if (0 == temp) {
			holder.mTxtPurchaseContent.setText(info.getGoodSName() + "("
					+ info.getGoodSCategory() + "/" + info.getGoodSPlace()
					+ "/" + info.getGoodSMerchant() + ")");
			holder.mTxtPrePrice.setText(info.getGoodSPrePrice() + "Ԫ/��");
		} else {
			holder.mTxtPurchaseContent.setText(info.getGoodSPurchaseContent()
					+ "(" + info.getGoodSCategory() + "/"
					+ info.getGoodSBrand() + ")");
			holder.mTxtPrePrice.setText(info.getGoodSpriceInterval());
		}
		holder.mTxtSubPiceWay.setText(info.getIntentionOrderState());

		return convertView;
	}

	static class ViewHolder {
		TextView mTxtPurchaseContent;
		// ����
		TextView mTxtSpecification;
		// ����
		TextView mTxtSubWeight;
		// �۸�
		TextView mTxtPrePrice;
		// ״̬
		TextView mTxtSubPiceWay;
	}

	/**
	 * ��������Դ
	 * 
	 * @param datas
	 */
	public void setData(ArrayList<GoodsSourceInfo> datas) {

		this.datas.clear();
		this.datas.addAll(datas);
		notifyDataSetChanged();
	}

	/**
	 * �������
	 */
	public void addData(ArrayList<GoodsSourceInfo> datas) {
		this.datas.addAll(datas);
		notifyDataSetChanged();
	}
}
