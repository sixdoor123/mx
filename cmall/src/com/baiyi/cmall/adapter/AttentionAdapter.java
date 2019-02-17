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
 * 关注的适配器
 * 
 * @author sunxy
 * 
 */
public class AttentionAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<GoodsSourceInfo> datas;
	private LayoutInflater inflater;

	// 记录当前处于哪一个ViewPage中 是收藏采购还是收藏供应；
	// 0 表示收藏采购 1 表示收藏供应
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
		holder.mTxtSubWeight.setText(info.getGoodSWeight() + "吨");
		if (0 == temp) {
			holder.mTxtPurchaseContent.setText(info.getGoodSName() + "("
					+ info.getGoodSCategory() + "/" + info.getGoodSPlace()
					+ "/" + info.getGoodSMerchant() + ")");
			holder.mTxtPrePrice.setText(info.getGoodSPrePrice() + "元/吨");
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
		// 分类
		TextView mTxtSpecification;
		// 数量
		TextView mTxtSubWeight;
		// 价格
		TextView mTxtPrePrice;
		// 状态
		TextView mTxtSubPiceWay;
	}

	/**
	 * 更新数据源
	 * 
	 * @param datas
	 */
	public void setData(ArrayList<GoodsSourceInfo> datas) {

		this.datas.clear();
		this.datas.addAll(datas);
		notifyDataSetChanged();
	}

	/**
	 * 添加数据
	 */
	public void addData(ArrayList<GoodsSourceInfo> datas) {
		this.datas.addAll(datas);
		notifyDataSetChanged();
	}
}
