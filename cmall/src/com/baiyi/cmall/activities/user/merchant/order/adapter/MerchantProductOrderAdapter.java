package com.baiyi.cmall.activities.user.merchant.order.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.user.merchant.order.MainCategoryItemView;
import com.baiyi.cmall.activities.user.merchant.order.PayMoneyAmountItemView;
import com.baiyi.cmall.activities.user.merchant.order.ProductInformationItemView;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.core.util.ContextUtil;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * �̼Ҷ���������
 * 
 * @author sunxy
 * @param <T>
 */
public class MerchantProductOrderAdapter<T> extends BaseRecycleViewAdapter<T> {

	public MerchantProductOrderAdapter(Context context) {
		super(context);
	}

	// �ܷ���
	private MainCategoryItemView mainView = null;
	// ��Ʒ
	private ProductInformationItemView proItemView = null;
	// ʵ������Ŀ
	private PayMoneyAmountItemView payItemView = null;

	public static class MyViewHolder extends ViewHolder {

		static LinearLayout root;

		public MyViewHolder(View view) {
			super(view);
			root = (LinearLayout) view.findViewById(R.id.root);
		}

	}

	// private Map<Integer, String> maps = new HashMap<Integer, String>();

	@Override
	public void onInitViewHolder(ViewHolder holder, int position) {
		GoodsSourceInfo sourceInfo = (GoodsSourceInfo) datas.get(position);
		// �ܷ���
		mainView = new MainCategoryItemView(context);
		mainView.disPlayView(sourceInfo);
		mainView.setId(position);
		((MyViewHolder) holder).root.addView(mainView);
		ArrayList<GoodsSourceInfo> datas = sourceInfo.getPurInfos();

		double tempPrice = 0;
		for (int i = 0; i < datas.size(); i++) {
			// ��Ʒ
			proItemView = new ProductInformationItemView(context);
			proItemView.displayView(datas.get(i));
			proItemView.setId(i);
			((MyViewHolder) holder).root.addView(proItemView);

			tempPrice += Double.parseDouble(proItemView.getTotalMoney());
		}
		// ʵ������Ŀ
		payItemView = new PayMoneyAmountItemView(context);
		payItemView.displayView(tempPrice + "");
		payItemView.setId(position);
		((MyViewHolder) holder).root.addView(payItemView);
	}

	@Override
	public ViewHolder reateViewHolder(ViewGroup parent, int viewType) {
		View view = ContextUtil.getLayoutInflater(context).inflate(R.layout.item_merchant_order, parent, false);
		MyViewHolder holder = new MyViewHolder(view);
		return holder;
	}
}
