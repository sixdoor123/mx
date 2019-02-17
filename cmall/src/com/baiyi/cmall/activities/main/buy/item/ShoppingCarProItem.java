/**
 * 
 */
package com.baiyi.cmall.activities.main.buy.item;

import java.security.KeyStore.PrivateKeyEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.buy.ShoppingCarActivity;
import com.baiyi.cmall.activities.main.buy.entity.UclwmEntity;
import com.baiyi.cmall.activities.main.buy.entity.UcrnvmEntity;
import com.baiyi.cmall.activities.main.buy.entity.UcwmlEntity;
import com.baiyi.cmall.activities.main.buy.req.ReqOrder;
import com.baiyi.cmall.activities.main.mall.views.BuyNumView;
import com.baiyi.cmall.activities.main.mall.views.BuyNumView.OnNumberChangerListener;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.loader.ImageLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import net.sf.json.JSONObject;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 购物车-商家-产品item
 * 
 * @author tangkun
 *
 */

public class ShoppingCarProItem extends LinearLayout {

	private View[] view = null;
	private ArrayList<UcwmlEntity> dataList = null;
	@SuppressWarnings("unused")
	private List<ReqOrder> selectList = null;
	private int position;

	private List<CheckBox> boxs = null;

	private OnItemAllCheckedChangeListener itemCheckedChangeListener = null;

	private Context context = null;

	private boolean isAllChecked = false;

	// 选中数据源
	private List<UcwmlEntity> selectedList = null;

	@SuppressLint("UseSparseArrays")
	public Map<Integer, View[]> maps = new HashMap<Integer, View[]>();

	public ShoppingCarProItem(Context context) {
		super(context);
	}

	public ShoppingCarProItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		this.setOrientation(VERTICAL);
	}

	public void setItemCheckedChangeListener(OnItemAllCheckedChangeListener itemCheckedChangeListener) {
		this.itemCheckedChangeListener = itemCheckedChangeListener;
	}

	@SuppressLint("InflateParams")
	public void displayViews(int shopIndex, final UclwmEntity ucwm, List<ReqOrder> selectList, boolean isAllChecked) {
		Log.d("TAG", "displayViews --------  ");

		ArrayList<UcwmlEntity> childList = ucwm.getUcwmlList();
		// 商家ID

		if (Utils.isStringEmpty(childList)) {
			return;
		}
		if (Utils.isStringEmpty(selectedList)) {
			this.selectedList = childList;
		}
		this.isAllChecked = isAllChecked;
		this.position = shopIndex;
		this.dataList = childList;
		this.selectList = selectList;
		this.removeAllViews();
		if (view == null) {
			view = new View[childList.size()];
		}

		// if (boxs == null) {
		boxs = new ArrayList<CheckBox>();
		// }

		for (int i = 0; i < view.length; i++) {
			final int temp = i;
			final UcwmlEntity entity = childList.get(i);
			view[i] = ContextUtil.getLayoutInflater(getContext())//
					.inflate(R.layout.item_shoppingcar_pro, null);
			TextView name = (TextView) view[i].findViewById(R.id.txt_pro_name);
			TextView sp = (TextView) view[i].findViewById(R.id.txt_pro_sp);
			TextView price = (TextView) view[i].findViewById(R.id.txt_pro_price);
			ImageView imgIcon = (ImageView) view[i].findViewById(R.id.img_shop_car);

			// 数量
			BuyNumView numView = (BuyNumView) view[i].findViewById(R.id.layout_buynum);
			// 得到数据
			numView.setNumberChangerListener(new OnNumberChangerListener() {

				@Override
				public void onChange(int count) {
					entity.setRc(count);
					entity.setCi(ucwm.getCi());
					selectedList.add(temp, entity);
					if (null != changerListener) {
						changerListener.onNumChanger();
					}
					// 在选中的情况下 总金额也要改变
				}
			});

			final CheckBox cb = (CheckBox) view[i].findViewById(R.id.checkbox_shoppingcar_pro);
			cb.setChecked(isAllChecked);
			boxs.add(i, cb);
			cb.setId(i);
			cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					int index = cb.getId();
					ShoppingCarActivity.selectList.get(position).getOswmList().get(index).setSelect(isChecked);
					// if (getIsAllCheckeds() || !getIsAllNotCheckeds()) {
					if (itemCheckedChangeListener != null) {
						itemCheckedChangeListener.setItemCheckedChangeLister();
					}
					// }

					entity.setSelected(isChecked);
					entity.setCi(ucwm.getCi());
					selectedList.add(temp, entity);
				}
			});
			// cb.setChecked(selectList.get(position).getOswmList().get(i).isSelect());

			loadImg(/*"http://pic25.nipic.com/20121112/5955207_224247025000_2.jpg"*/entity.getIp(), imgIcon);
			name.setText(entity.getPn());
			sp.setText(getSp(entity.getUcrnvmList()));
			price.setText(entity.getPr());
			numView.setNumerView(entity.getRc() + "");
			numView.setProNum(entity.getRc());
			addView(view[i]);

			maps.put(i, view);
		}
	}

	/**
	 * 图片下载
	 * 
	 * @param urlStr
	 * @param view
	 */

	private void loadImg(String urlStr, final ImageView view) {

		ImageLoader loader = new ImageLoader(context, true);
		loader.setUrl(urlStr);
		loader.setPostData(new JSONObject().toString());
		loader.setMethod(BaseNetLoder.Method_Get);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum, long totalByteNum) {
			}

			@Override
			public void onError(Object tag, int responseCode, String errorMessage) {
				Log.d("TT", "errorMessage");
			}

			@Override
			public void onCompelete(Object tag, Object result) {
				BitmapDrawable drawable = (BitmapDrawable) result;
				view.setBackground(drawable);
				Log.d("TT", "onCompelete");
			}
		});

		CmallApplication.getDataStratey().startLoader(loader);
	}

	public String getSp(ArrayList<UcrnvmEntity> ucrnvmList) {
		StringBuilder sb = new StringBuilder();
		for (UcrnvmEntity entity : ucrnvmList) {
			sb.append(entity.getNn());
			sb.append(":");
			sb.append(entity.getNv());
			sb.append(" ");
		}
		return sb.toString();
	}

	boolean[] isAllCheckeds;

	public void setAllItemCheckedLister() {
		isAllCheckeds = new boolean[view.length];
		for (int i = 0; i < view.length; i++) {
			// CheckBox cb =
			// (CheckBox)view[i].findViewById(R.id.checkbox_shoppingcar_pro);
			CheckBox cb = boxs.get(i);
			if (cb != null) {
				isAllCheckeds[i] = cb.isChecked();
			}
		}
	}

	public boolean getIsAllCheckeds() {

		for (CheckBox i : boxs) {
			if (i.isChecked() == false) {
				return i.isChecked();
			}
		}
		return true;
	}

	public boolean getIsAllNotCheckeds() {

		for (CheckBox i : boxs) {
			if (i.isChecked() == true) {
				return i.isChecked();
			}
		}
		return false;
	}

	/**
	 * 有一个被选中总条目就被选中
	 * 
	 * @return
	 */
	public boolean getIsSingleChecked() {

		for (CheckBox i : boxs) {
			if (i.isChecked() == true) {
				return i.isChecked();
			}
		}
		return false;
	}

	/**
	 * 设置单一产品
	 * 
	 * @param position
	 * @param isChecked
	 */
	public void setChecked(int position, boolean isChecked) {
		CheckBox cb = (CheckBox) view[position].findViewById(R.id.checkbox_shoppingcar_pro);
		cb.setChecked(isChecked);
	}

	/**
	 * 设置商家下所有产品
	 * 
	 * @param position
	 * 
	 * @param position
	 * 
	 * @param isChecked
	 */
	public void setAllChecked(int position, boolean isChecked) {
		if (!Utils.isStringEmpty(boxs)) {
			Log.d("TAG", "boxs.size() = " + boxs.size());
			for (int i = 0; i < boxs.size(); i++) {
				// CheckBox cb =
				// (CheckBox)view[i].findViewById(R.id.checkbox_shoppingcar_pro);
				CheckBox cb = boxs.get(i);
				if (cb != null) {
					cb.setChecked(isChecked);
				}
			}
		}
		// Log.d("TAG", "位置---" + position);
		// View[] views = maps.get(position);
		// for (View v : views) {
		// CheckBox cb = (CheckBox)
		// v.findViewById(R.id.checkbox_shoppingcar_pro);
		// cb.setChecked(isChecked);
		// Log.d("TAG", "CheckBox cb ");
		// }
	}

	/**
	 * 获取选中产品
	 * 
	 * @return
	 */
	public ArrayList<UcwmlEntity> getSelectItem() {
		ArrayList<UcwmlEntity> selectList = new ArrayList<UcwmlEntity>();
		for (int i = 0; i < view.length; i++) {
			CheckBox cb = (CheckBox) view[i].findViewById(R.id.checkbox_shoppingcar_pro);
			if (cb.isChecked()) {
				selectList.add(dataList.get(i));
			}
		}
		return selectList;
	}

	/**
	 * 商家未选中，产品选中时，商家应当也选中
	 * 
	 * @author tangkun
	 *
	 */
	public interface OnItemAllCheckedChangeListener {
		public void setItemCheckedChangeLister();
	}

	/**
	 * 得到价格(小计)
	 * 
	 * @return
	 */
	public String getPrice() {
		double d = 0;
		for (int i = 0; i < view.length; i++) {
			TextView priceView = (TextView) view[i].findViewById(R.id.txt_pro_price);
			// 数量
			BuyNumView numView = (BuyNumView) view[i].findViewById(R.id.layout_buynum);

			int num = Integer.parseInt(numView.getNumerView());

			d += Double.parseDouble(priceView.getText().toString()) * num;
		}
		return String.valueOf(d);
	}

	/**
	 * 得到选中的价格
	 */
	public double getSelectedPrice() {
		double num = 0;

		for (int i = 0; i < boxs.size(); i++) {
			if (boxs.get(i).isChecked()) {
				BuyNumView numView = (BuyNumView) view[i].findViewById(R.id.layout_buynum);
				int temp = Integer.parseInt(numView.getNumerView());
				TextView priceView = (TextView) view[i].findViewById(R.id.txt_pro_price);
				double price = Double.parseDouble(Utils.getNumberOfString(priceView.getText().toString().trim()));

				num += temp * price;
			}
		}
		return num;
	}

	/**
	 * 得到选中的产品的数量
	 */
	public int getTotalNum() {
		int num = 0;
		// for (int i = 0; i < view.length; i++) {
		// CheckBox cb = (CheckBox)
		// view[i].findViewById(R.id.checkbox_shoppingcar_pro);
		// if (cb.isChecked()) {
		// // 数量
		// BuyNumView numView = (BuyNumView)
		// view[i].findViewById(R.id.layout_buynum);
		//
		// num += Integer.parseInt(numView.getNumerView());
		// }
		// }

		for (int i = 0; i < boxs.size(); i++) {
			if (boxs.get(i).isChecked()) {
				BuyNumView numView = (BuyNumView) view[i].findViewById(R.id.layout_buynum);
				num += Integer.parseInt(numView.getNumerView());
			}
		}
		return num;
	}

	/**
	 * 得到选中的数据列表
	 */
	public List<UcwmlEntity> getSelectedList() {

		List<UcwmlEntity> list = null;
		for (UcwmlEntity ucwmlEntity : selectedList) {
			if (ucwmlEntity.isSelected()) {
				if (!Utils.isStringEmpty(list)) {
					boolean b = false;
					for (UcwmlEntity entity : list) {
						if (entity.getId() != ucwmlEntity.getId()) {
							b = true;
						}
					}
					if (b) {
						list.add(ucwmlEntity);

					}
				} else {
					list = new ArrayList<UcwmlEntity>();
					list.add(ucwmlEntity);
				}
			}
		}
		return list;
	}

	/**
	 * 当数量改变时 小计 总价... 变
	 */
	public interface OnCountChangeListener {
		void onNumChanger();
	}

	private OnCountChangeListener changerListener;

	public void setChangerListener(OnCountChangeListener changerListener) {
		this.changerListener = changerListener;
	}
}
