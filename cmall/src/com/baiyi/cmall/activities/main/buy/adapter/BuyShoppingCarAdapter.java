/**
 * 
 */
package com.baiyi.cmall.activities.main.buy.adapter;

import java.security.KeyStore.PrivateKeyEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.buy.entity.UclwmEntity;
import com.baiyi.cmall.activities.main.buy.entity.UcwmlEntity;
import com.baiyi.cmall.activities.main.buy.holder.BuyShoppingCarHolder;
import com.baiyi.cmall.activities.main.buy.item.ShoppingCarProItem;
import com.baiyi.cmall.activities.main.buy.item.ShoppingCarProItem.OnCountChangeListener;
import com.baiyi.cmall.activities.main.buy.item.ShoppingCarProItem.OnItemAllCheckedChangeListener;
import com.baiyi.cmall.activities.main.buy.req.ReqOrder;
import com.baiyi.cmall.activities.main.mall.adapter.BaseRecyclerAdapter;
import com.baiyi.cmall.utils.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * 商城主页 item
 * 
 * @author tangkun
 *
 */
@SuppressLint("UseSparseArrays")
public class BuyShoppingCarAdapter extends BaseRecyclerAdapter<UclwmEntity> {

	// 保存Item
	private Map<Integer, ShoppingCarProItem> items = null;
	private Map<Integer, CheckBox> boxs = null;

	private List<ReqOrder> selectList = null;

	private Map<Integer, UclwmEntity> datas = null;

	/**
	 * @param objects
	 */
	@SuppressLint("UseSparseArrays")
	public BuyShoppingCarAdapter(Context context, ArrayList<UclwmEntity> objects, List<ReqOrder> selectList) {
		super(context, objects);
		this.selectList = selectList;
		if (items == null) {
			items = new HashMap<Integer, ShoppingCarProItem>();
		}
		if (boxs == null) {
			boxs = new HashMap<Integer, CheckBox>();
		}

		if (null == datas) {
			this.datas = new HashMap<Integer, UclwmEntity>();
		}
	}

	@Override
	public ViewHolder onCreateVH(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())//
				.inflate(R.layout.item_shoppingcar_shop, parent, false);
		final BuyShoppingCarHolder vh = new BuyShoppingCarHolder(view);

		return vh;
	}

	public void setAllChecked(List<ReqOrder> selectList) {
		this.selectList = selectList;
		handler.sendEmptyMessage(0);
	}

	@Override
	public void onBindVH(final ViewHolder holder, final int position) {

		UclwmEntity ucwm = objects.get(position);
		Log.d("TAG", "objects.size()  = " + objects.size());
		((BuyShoppingCarHolder) holder).checkBoxShop.setChecked(isChecked);
		((BuyShoppingCarHolder) holder).txtShopName.setText(ucwm.getCn());
		((BuyShoppingCarHolder) holder).layoutPro.displayViews(position, ucwm, selectList, isChecked);

		this.items.put(position, ((BuyShoppingCarHolder) holder).layoutPro);
		this.boxs.put(position, ((BuyShoppingCarHolder) holder).checkBoxShop);

		((BuyShoppingCarHolder) holder).layoutPro.setItemCheckedChangeListener(new OnItemAllCheckedChangeListener() {

			@Override
			public void setItemCheckedChangeLister() {

				if (((BuyShoppingCarHolder) holder).layoutPro.getIsAllCheckeds()) {
					boxs.get(position).setChecked(true);
				} else {
					if (!((BuyShoppingCarHolder) holder).layoutPro.getIsAllNotCheckeds()) {
						boxs.get(position).setChecked(false);
					}
				}

				if (checkBoxChanged != null) {
					checkBoxChanged.setCheckBoxChanged(getSelectedTotalNum(), isAllSelected());
				}
			}
		});
		// 当数量改变时
		((BuyShoppingCarHolder) holder).layoutPro.setChangerListener(new OnCountChangeListener() {

			@Override
			public void onNumChanger() {
				((BuyShoppingCarHolder) holder).txtSettlement
						.setText("小计: ¥ " + Utils.twoDecimals(((BuyShoppingCarHolder) holder).layoutPro.getPrice()));

				// 总价回调
				if (null != totalMoneyChangeListener) {
					totalMoneyChangeListener.onToTalChange(getSelectedPrice(), getSelectedTotalNum());
				}
			}
		});
		((BuyShoppingCarHolder) holder).checkBoxShop.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Log.d("TAG", "店铺选择---------" + isChecked);
				((BuyShoppingCarHolder) holder).layoutPro.setAllChecked(position, isChecked);
				// if (checkBoxChanged != null) {
				// checkBoxChanged.isAllSelcted(isAllSelected());
				// }

				// 1.当有一个不全选时，最下面的全选视未选中

			}
		});

		((BuyShoppingCarHolder) holder).txtSettlement
				.setText("小计: ¥ " + ((BuyShoppingCarHolder) holder).layoutPro.getPrice());

	}

	private CheckBoxChanged checkBoxChanged = null;

	public void setCheckBoxChanged(CheckBoxChanged checkBoxChanged) {
		this.checkBoxChanged = checkBoxChanged;
	}

	public interface CheckBoxChanged {
		public void setCheckBoxChanged(int total, boolean isAllSelect);

		public void isAllSelcted(boolean isAllSelected);
	}

	private boolean isChecked = false;

	public void setIsChecked(boolean isChecked) {
		this.isChecked = isChecked;
		Log.d("TAG", "是否全部选择-----" + isChecked);
		handler.sendEmptyMessage(0);
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				BuyShoppingCarAdapter.this.notifyDataSetChanged();
				break;

			default:
				break;
			}
		};
	};

	/**
	 * 得到所选产品的总数
	 * 
	 */
	public int getSelectedTotalNum() {
		int num = 0;
		Collection<ShoppingCarProItem> collection = items.values();
		for (ShoppingCarProItem item : collection) {
			num += item.getTotalNum();
		}
		Log.d("TAG", "数量  = " + num);
		return num;
	}

	/**
	 * 判断是否全部选完(商家总条目)
	 */
	public boolean isAllSelected() {
		Collection<CheckBox> collection = boxs.values();
		for (CheckBox item : collection) {
			if (false == item.isChecked()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 获取选中的价格
	 * 
	 */
	public String getSelectedPrice() {
		double price = 0;
		Collection<ShoppingCarProItem> collection = items.values();
		for (ShoppingCarProItem item : collection) {
			price += item.getSelectedPrice();
		}
		return String.valueOf(price);
	}

	/**
	 * 得到选中条目的数据
	 */
	public Map<Integer,UcwmlEntity> getSelectedItemData() {
		// List<UcwmlEntity> list = null;
		// Collection<ShoppingCarProItem> collection = items.values();
		// for (ShoppingCarProItem item : collection) {
		// for (UcwmlEntity entity : item.getSelectedList()) {
		// if (!Utils.isStringEmpty(list)) {
		// boolean bool = false;
		// for (UcwmlEntity lEntity : list) {
		// if (entity.getId() != lEntity.getId()) {
		// bool = true;
		// }
		// }
		// if (bool) {
		// list.add(entity);
		// }
		// } else {
		// list = new ArrayList<UcwmlEntity>();
		// list.add(entity);
		// }
		// }
		// }
		// return list;

		Map<Integer, UcwmlEntity> map = new HashMap<Integer, UcwmlEntity>();

		Collection<ShoppingCarProItem> collection = items.values();
		for (ShoppingCarProItem item : collection) {
			if (item != null) {
				List<UcwmlEntity> list = item.getSelectedList();
				if (!Utils.isStringEmpty(list)) {
					for (UcwmlEntity entity : list) {
						map.put(entity.getId(), entity);
					}
				}
			}
		}
		return map;
	}

	/**
	 * 当数量改变时，总价开始改变
	 * 
	 * @author Administrator
	 *
	 */
	public interface OnNumChangeTotalMoneyChangeListener {
		void onToTalChange(String totalMoney, int totalNumber);
	}

	private OnNumChangeTotalMoneyChangeListener totalMoneyChangeListener;

	public void setTotalMoneyChangeListener(OnNumChangeTotalMoneyChangeListener totalMoneyChangeListener) {
		this.totalMoneyChangeListener = totalMoneyChangeListener;
	}
}
