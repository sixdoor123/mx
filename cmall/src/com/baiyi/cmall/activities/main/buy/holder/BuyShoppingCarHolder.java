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
 * �̳�-����-����
 * 
 * @author tangkun
 *
 */
public class BuyShoppingCarHolder extends RecyclerView.ViewHolder {
	// ����ѡ��
	public CheckBox checkBoxShop;
	// ��������
	public TextView txtShopName;
	// ��Ʒ����
	public ShoppingCarProItem layoutPro;
	// ���̽���
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
//				Log.d("TAG", "����ѡ��---------" + isChecked);
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
