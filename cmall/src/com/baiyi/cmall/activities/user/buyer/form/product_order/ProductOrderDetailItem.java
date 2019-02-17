package com.baiyi.cmall.activities.user.buyer.form.product_order;

import com.baiyi.cmall.entity.OrderEntity;
import com.baiyi.cmall.utils.IntentClassChangeUtils;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 我是采购商——我的产品订单——订单详情 ——详情条目
 * 
 * @author lizl
 * 
 */
public class ProductOrderDetailItem {

	private Context context;
	// 商品名称
	private TextView mTvGoodsName;
	// 规格
	private TextView mTvGuiGe;
	// 数量
	private TextView mTvWeight;
	// 价格
	private TextView mTvPrice;
	// 总价格
	private TextView mTvAllPrice;
	// 子条目
	private View viewItem;
	// 解析器
	private LayoutInflater inflater;

	public ProductOrderDetailItem(Context context) {
		this.context = context;
		// 添加一个线性布局
		inflater = ContextUtil.getLayoutInflater(context);
	}

	/**
	 * 显示具体的数据
	 * 
	 * @param classifyInfoEntity
	 */
	public View disItem(final OrderEntity entity) {

		viewItem = inflater.inflate(R.layout.item_pur_product_order, null);

		viewItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/**
				 * 跳转至采购详情页面
				 */
				IntentClassChangeUtils.startMallDetail(context, Utils.stringToInt(entity.getId())+"");

			}
		});
		// 商品名称
		mTvGoodsName = (TextView) viewItem.findViewById(R.id.tv_good_name);
		// 规格
		mTvGuiGe = (TextView) viewItem.findViewById(R.id.tv_guige);
		// 数量
		mTvWeight = (TextView) viewItem.findViewById(R.id.tv_weight);
		// 价格
		mTvPrice = (TextView) viewItem.findViewById(R.id.tv_price);
		// 总价格
		mTvAllPrice = (TextView) viewItem.findViewById(R.id.tv_all_price);

		// 商品名称
		mTvGoodsName.setText("DMF(N,N-二甲基甲酰胺)(CAS:68-12-22)");
		// 规格
		mTvGuiGe.setText(entity.getGuige());
		// 数量
		mTvWeight.setText(entity.getInventory() + "吨");
		// 价格
		mTvPrice.setText(Utils.twoDecimals(entity.getPrice()) + "元/吨");
		/*
		 * 总额= 单价*数量
		 */
		String allMoney = (Double.parseDouble(entity.getPrice()) * Long
				.parseLong(entity.getInventory())) + "";
		// 总价格
		mTvAllPrice.setText(Utils.twoDecimals(allMoney) + "元");

		return viewItem;
	}
}
