/**
 * 
 */
package com.baiyi.cmall.activities.main.buy.holder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.buy.item.ShoppingCarProItem;

/**
 * 商城-详情-评论
 * 
 * @author tangkun
 *
 */
public class BuyShoppingCarHolder extends RecyclerView.ViewHolder {
	// 店铺选择
	public CheckBox checkBoxShop;
	// 店铺名字
	public TextView txtShopName;
	// 商品容器
	public ShoppingCarProItem layoutPro;
	// 店铺结算
	public TextView txtSettlement;

	boolean bool = true;

	private int position = -1;

	/**
	 * @param arg0
	 */
	public BuyShoppingCarHolder(View view) {
		super(view);
		checkBoxShop = (CheckBox) view.findViewById(R.id.checkbox_shoppingcar_shop);
		txtShopName = (TextView) view.findViewById(R.id.txt_shoppingcar_shopname);
		layoutPro = (ShoppingCarProItem) view.findViewById(R.id.layout_shoppingcar_pro);
		txtSettlement = (TextView) view.findViewById(R.id.txt_shoppingcar_settlement);

//		checkBoxShop.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if (bool) {
//					layoutPro.setAllChecked(position, bool);
//					bool = false;
//				} else {
//					layoutPro.setAllChecked(position, bool);
//					bool = true;
//				}
//			}
//		});

//		checkBoxShop.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				Log.d("TAG", "店铺选择---------" + isChecked);
//
////				if (layoutPro.getIsAllCheckeds()) {
////					layoutPro.setAllChecked(position, isChecked);
////				}
////				 checkBoxShop.setChecked(isChecked);
//			}
//		});
	}

	public void setPosition(int position) {
		this.position = position;
	}

}
