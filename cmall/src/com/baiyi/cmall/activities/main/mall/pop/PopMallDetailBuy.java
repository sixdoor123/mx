/**
 * 
 */
package com.baiyi.cmall.activities.main.mall.pop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.mall.JsonParseMall;
import com.baiyi.cmall.activities.main.mall.UrlNet;
import com.baiyi.cmall.activities.main.mall.adapter.OrderPurityPopGvAdapter;
import com.baiyi.cmall.activities.main.mall.adapter.OrderPurityPopGvAdapter.ItemOnclick;
import com.baiyi.cmall.activities.main.mall.entity.DictionaryEntity;
import com.baiyi.cmall.activities.main.mall.entity.PnmEntity;
import com.baiyi.cmall.activities.main.mall.entity.PnpdmlEntity;
import com.baiyi.cmall.activities.main.mall.entity.PnvmlEntity;
import com.baiyi.cmall.activities.main.mall.entity.PnwmlEntity;
import com.baiyi.cmall.activities.main.mall.req.ReqUcrnvm;
import com.baiyi.cmall.activities.main.mall.req.ReqUscawm;
import com.baiyi.cmall.activities.main.mall.views.BuyNumView;
import com.baiyi.cmall.activities.main.mall.views.BuyNumView.PriceCallBack;
import com.baiyi.cmall.activities.main.mall.views.MyGridView;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.utils.IntentClassChangeUtils;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.LoadingBar;
import com.baiyi.cmall.views.pop.BasePopupWindow;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.tencent.connect.avatar.b;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * �̳�-ɸѡPop
 * 
 * @author tangkun
 *
 */
@SuppressLint("UseSparseArrays")
public class PopMallDetailBuy extends BasePopupWindow {

	private BuyNumView buyNumView;

	private TextView proPrice = null;
	// ���빺�ﳵ
	private Button btnBuyShopping = null;
	// ��������
	private Button btnBuy = null;

	private LinearLayout gridViewGroup = null;

	private MyGridView[] girdViews = null;
	private TextView[] nameViews = null;
	private OrderPurityPopGvAdapter[] orPopGvAdapters = null;
	// ��Ʒid
	private String pi;

	private DictionaryEntity dictionaryEntity = null;

	private int[] selectIndex = null;
	private String currentPrice = null;

	// ��Ʒ����
	private String productName = null;

	/**
	 * @param contentView
	 * @param context
	 * @param width
	 * @param height
	 * @param ap
	 * @param productName
	 */
	public PopMallDetailBuy(View contentView, Context context, int width, int height, String pi, 
			String productName) {
		super(contentView, context, width, height);
		// TODO Auto-generated constructor stub
		this.pi = pi;
		this.productName = productName;
		initViews(contentView);

	}

	// ���
	private TextView mTxtInventory;
	// ��Ʒ����
	private TextView mTxtProductName;

	private void initViews(View view) {
		gridViewGroup = (LinearLayout) view.findViewById(R.id.mall_detail_buy_grid_group);

		proPrice = (TextView) view.findViewById(R.id.pro_price);
		buyNumView = (BuyNumView) view.findViewById(R.id.layout_buynum);

		buyNumView.setPriceCallBack(new PriceCallBack() {

			@Override
			public void setPriceCallBack(String price) {
				currentPrice = price;
				proPrice.setText(String.format(context.getResources().getString(R.string.pop_price), currentPrice));
			}
		});
		btnBuyShopping = (Button) view.findViewById(R.id.btn_mall_detail_menu_buy_shoppingcar);
		btnBuy = (Button) view.findViewById(R.id.btn_mall_detail_menu_buy);
		btnBuyShopping.setOnClickListener(new BuyOnclick());
		btnBuy.setOnClickListener(new BuyOnclick());

		// ��Ʒ����
		mTxtProductName = (TextView) view.findViewById(R.id.product_name);
		mTxtProductName.setText(productName);
		// ���
		mTxtInventory = (TextView) view.findViewById(R.id.txt_inventory);
		
	}

	/**
	 * ��Ӧ��Ʒ�Ĺ�����Ժͼ۸��ֵ�
	 */
	private void loadPriceDictionary() {

		loaderProgress();

		JsonLoader loader = new JsonLoader(context);
		loader.setUrl(UrlNet.getShopCarData(pi));
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setPostData("");
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum, long totalByteNum) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(Object tag, int responseCode, String errorMessage) {
				// TODO Auto-generated method stub
				stopProgress();
			}

			@Override
			public void onCompelete(Object tag, Object result) {
				// TODO Auto-generated method stub
				dictionaryEntity = JsonParseMall.getPnwmlEntity((JSONArray) result);
				stopProgress();
				displayViews();
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	// ѡ�еĹ�����
	private Map<Integer, PnvmlEntity> maps = null;

	private void displayViews() {

		if (maps == null) {
			maps = new HashMap<Integer, PnvmlEntity>();

			for (int i = 0; i < dictionaryEntity.getPnwmlList().size(); i++) {
				maps.put(i, dictionaryEntity.getPnwmlList().get(i).getPnvmlList().get(0));
			}
		}

		girdViews = new MyGridView[dictionaryEntity.getPnwmlList().size()];
		nameViews = new TextView[dictionaryEntity.getPnwmlList().size()];
		orPopGvAdapters = new OrderPurityPopGvAdapter[dictionaryEntity.getPnwmlList().size()];
		for (int i = 0; i < dictionaryEntity.getPnwmlList().size(); i++) {
			final int index = i;
			View view = ContextUtil.getLayoutInflater(context)//
					.inflate(R.layout.item_grid_detail_buy, null);
			gridViewGroup.addView(view);

			nameViews[i] = (TextView) view.findViewById(R.id.name);
			nameViews[i].setText(dictionaryEntity.getPnwmlList().get(i).getPnmEntity().getNn());
			girdViews[i] = (MyGridView) view.findViewById(R.id.gridview_mall_buy);
			orPopGvAdapters[i] = new OrderPurityPopGvAdapter(context);
			girdViews[i].setAdapter(orPopGvAdapters[i]);
			orPopGvAdapters[i].setData(dictionaryEntity.getPnwmlList().get(i).getPnvmlList());
			orPopGvAdapters[i].setOnclick(new ItemOnclick() {

				@Override
				public void setItemOnclick(int position, PnvmlEntity entity) {
					Log.d("TAG", "position = " + position + "   " + "i = " + index + "���ֵ --- " + entity);
					if (dictionaryEntity == null) {
						return;
					}

					maps.put(index, entity);

					orPopGvAdapters[index].setSelect(position);
					displayPrice(index, position);
				}
			});
		}
		
		setInventor(dictionaryEntity.getPnpdmlList().get(0).getIn());
		initGridViewSelect();
	}

	/**
	 * ���
	 * @param in
	 */
	private void setInventor(int in){
		mTxtInventory.setText(String.valueOf(in) + "��");
		buyNumView.setAp(in);
		
	}
	/**
	 * ��ʼ��ѡ�й��
	 */
	private void initGridViewSelect() {
		if (selectIndex == null) {
			selectIndex = new int[dictionaryEntity.getPnwmlList().size()];
		}
		for (int i = 0; i < selectIndex.length; i++) {
			selectIndex[i] = 0;
		}
		currentPrice = getProPrice(selectIndex);
		proPrice.setText(String.format(context.getResources().getString(R.string.pop_price), currentPrice));

		buyNumView.setPrice(Double.parseDouble(currentPrice));
	}

	/**
	 * ��ʾ���ռ۸�
	 * 
	 * @param parentIndex
	 *            ��parentIndex����
	 * @param childIndex
	 *            ����µ�childIndex��ֵ
	 */
	private void displayPrice(int parentIndex, int childIndex) {
		selectIndex[parentIndex] = childIndex;
		String price = getProPrice(selectIndex);
		double fPrice = Double.parseDouble(price == null ? "0" : price);
		buyNumView.setPrice(fPrice);
		currentPrice = buyNumView.getCountPrice(fPrice);
		proPrice.setText(String.format(context.getResources().getString(R.string.pop_price), currentPrice));
	}

	/**
	 * ��ȡѡ�������ļ۸�
	 * 
	 * @param selectIndex
	 * @return
	 */
	public String getProPrice(int[] selectIndex) {
		int[] dictionarys = new int[dictionaryEntity.getPnwmlList().size()];

		for (int i = 0; i < selectIndex.length; i++) {
			dictionarys[i] = dictionaryEntity.getPnwmlList().get(i).getPnvmlList().get(selectIndex[i]).getId();
		}

		ArrayList<ArrayList<PnpdmlEntity>> d = new ArrayList<ArrayList<PnpdmlEntity>>();
		int tmpId = -1;
		ArrayList<PnpdmlEntity> tmpList = null;
		for (int i = 0; i < dictionaryEntity.getPnpdmlList().size(); i++) {
			PnpdmlEntity entity = dictionaryEntity.getPnpdmlList().get(i);

			if (tmpId != entity.getId()) {
				if (i != 0) {
					d.add(tmpList);
				}
				tmpList = new ArrayList<>();
				tmpList.add(entity);
				// tmpList.clear();
				tmpId = entity.getId();
				d.add(tmpList);
			} else {
				tmpList.add(entity);
				if (i == dictionaryEntity.getPnpdmlList().size() - 1) {
					d.add(tmpList);
				}
			}
		}
		return equals(d, dictionarys);
	}

	/**
	 * ����-���ռ۸�
	 * 
	 * @param data
	 * @param dictionarys
	 * @return
	 */
	public String equals(ArrayList<ArrayList<PnpdmlEntity>> data, int[] dictionarys) {
		//
		String price = null;
		boolean isOk = false;
		for (int i = 0; i < data.size(); i++) {
			for (int j = 0; j < data.get(i).size(); j++) {
				PnpdmlEntity entity = data.get(i).get(j);
				if (entity.getNvi() == dictionarys[j]) {
					isOk = true;
					price = entity.getPr();
					setInventor(entity.getIn());
				} else {
					price = null;
					isOk = false;
					break;
				}
			}
			if (isOk) {
				return price;
			} else {
				price = null;
			}
		}
		return price;
	}

	@Override
	public void showPopAtLocation(View view, int gravity) {
		// TODO Auto-generated method stub
		super.showPopAtLocation(view, gravity);
		if (dictionaryEntity != null) {
			return;
		}
		loadPriceDictionary();
	}

	private class BuyOnclick implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.btn_mall_detail_menu_buy_shoppingcar) {
				// ���빺�ﳵ
				sendBuyCarData();
			} else if (id == R.id.btn_mall_detail_menu_buy) {
				// ��������
				// sendBuyCarData();
				BuyNowData();
			}
		}

	}

	/**
	 * ���빺�ﳵ
	 */
	private void sendBuyCarData() {

		if (!isJoinCarOrBuy()) {
			return;
		}

		loaderProgress();

		JsonLoader loader = new JsonLoader(context);
		loader.setUrl(UrlNet.getAddUserCart());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setPostData(getBuyCarPostData(selectIndex));
		loader.addRequestHeader("token", CmallApplication.getUserInfo().getToken());
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum, long totalByteNum) {
			}

			@Override
			public void onError(Object tag, int responseCode, String errorMessage) {
				((BaseActivity) context).displayToast(errorMessage);
				stopProgress();
			}

			@Override
			public void onCompelete(Object tag, Object result) {
				stopProgress();
				dismiss();
				if (onJoinShopCarSuccess != null) {
					onJoinShopCarSuccess.onJoinSuccess(result);
				}
				// IntentClassChangeUtils.startShoppingCarActivity(context);
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * ��������
	 * 
	 * @author Administrator
	 *
	 */
	public interface OnBuyNowOrderListener {
		void onBuyNow(Map<Integer, PnvmlEntity> maps, String currentPrice, String number);
	}

	private OnBuyNowOrderListener buyNowOrderListener;

	public void setBuyNowOrderListener(OnBuyNowOrderListener buyNowOrderListener) {
		this.buyNowOrderListener = buyNowOrderListener;
	}

	/**
	 * ��������
	 */
	public void BuyNowData() {
		if (!isJoinCarOrBuy()) {
			return;
		}
		if (null != buyNowOrderListener) {
			buyNowOrderListener.onBuyNow(maps, currentPrice, buyNumView.getNumerView());
		}
	}

	private boolean isJoinCarOrBuy() {

		// �жϵ�ǰ�м�����Ʒ
		
		int num = Integer.parseInt(Utils.getNumberOfString(mTxtInventory.getText().toString()));
		
		if (Integer.parseInt(buyNumView.getNumerView()) <= 0) {
			if (num <= 0) {
				((BaseActivity) context).displayToast("���޿��");
			}else {
				((BaseActivity) context).displayToast("���ٵ���һ����Ʒ");
			}
			
			return false;
		}
		return true;
	}

	private OnJoinShopCarSuccess onJoinShopCarSuccess;

	public void setOnJoinShopCarSuccess(OnJoinShopCarSuccess onJoinShopCarSuccess) {
		this.onJoinShopCarSuccess = onJoinShopCarSuccess;
	}

	/**
	 * ���빺�ﳵ�ɹ�
	 * 
	 * @author Administrator
	 *
	 */
	public interface OnJoinShopCarSuccess {
		void onJoinSuccess(Object result);
	}

	/**
	 * ���ﳵ�������
	 * 
	 * @param selectIndex
	 * @return
	 */
	private String getBuyCarPostData(int[] selectIndex) {
		if (dictionaryEntity == null) {
			return null;
		}
		ReqUscawm reqUscawm = new ReqUscawm();
		reqUscawm.setPc(buyNumView.getProNum());
		reqUscawm.setPi(String.valueOf(pi));
		reqUscawm.setPp(currentPrice);

		ArrayList<ReqUcrnvm> ucrnvmList = new ArrayList<ReqUcrnvm>();
		ArrayList<PnwmlEntity> pnwmlList = dictionaryEntity.getPnwmlList();
		for (int i = 0; i < pnwmlList.size(); i++) {
			PnmEntity pnm = pnwmlList.get(i).getPnmEntity();
			String nv = pnwmlList.get(i).getPnvmlList().get(selectIndex[i]).getNv();
			int nvi = pnwmlList.get(i).getPnvmlList().get(selectIndex[i]).getId();
			ReqUcrnvm ucrnvm = new ReqUcrnvm();
			ucrnvm.setNn(pnm.getNn());
			ucrnvm.setNu(pnm.getNu());
			ucrnvm.setNv(nv);
			ucrnvm.setNvi(nvi);
			ucrnvmList.add(ucrnvm);
		}
		reqUscawm.setUcrnvmList(ucrnvmList);
		Log.d("TAG", reqUscawm.toPostString());
		return reqUscawm.toPostString();
	}

	public void PopDismiss() {
		dismiss();
	}
}
