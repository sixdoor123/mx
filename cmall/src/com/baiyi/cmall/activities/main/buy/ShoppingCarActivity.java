/**
 * 
 */
package com.baiyi.cmall.activities.main.buy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import com.baiyi.cmall.R;
import com.baiyi.cmall.TLog;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.UserActivity;
import com.baiyi.cmall.activities.main.buy.adapter.BuyShoppingCarAdapter;
import com.baiyi.cmall.activities.main.buy.adapter.BuyShoppingCarAdapter.CheckBoxChanged;
import com.baiyi.cmall.activities.main.buy.adapter.BuyShoppingCarAdapter.OnNumChangeTotalMoneyChangeListener;
import com.baiyi.cmall.activities.main.buy.entity.UclwmEntity;
import com.baiyi.cmall.activities.main.buy.entity.UcrnvmEntity;
import com.baiyi.cmall.activities.main.buy.entity.UcwmlEntity;
import com.baiyi.cmall.activities.main.buy.req.ReqOpm;
import com.baiyi.cmall.activities.main.buy.req.ReqOrder;
import com.baiyi.cmall.activities.main.buy.req.ReqOswm;
import com.baiyi.cmall.activities.main.home.itemview.DividerItemDecoration;
import com.baiyi.cmall.activities.main.mall.JsonParseMall;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.utils.IntentClassChangeUtils;
import com.baiyi.cmall.utils.JsonParseBase;
import com.baiyi.cmall.utils.JsonParse_User;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.utils.XmlName;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;
import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * ���ﳵ
 * 
 * @author tangkun
 *
 */
public class ShoppingCarActivity extends BaseActivity {

	private TextView btnEdit = null;
	private ImageView backImage;
	private XRecyclerView listView = null;
	private BuyShoppingCarAdapter adapter = null;
	// ֧��
	private Button btnPay = null;
	private TextView txtTotalPrice = null;
	private TextView txtPrice = null;
	// ����ȫѡ
	private CheckBox mCkAllSelected = null;
	// ɾ�����ﳵ��Ʒ
	private Button btnDelete = null;
	// �༭ȫѡ
	private CheckBox mCkAllEdit;

	// ɾ��
	private LinearLayout delelteGroup = null;
	// ����
	private LinearLayout setlementGroup = null;

	private ArrayList<UclwmEntity> uclwmList = null;

	public static List<ReqOrder> selectList = null;

	private boolean isEdit = false;
	private String totalPrice = null;

	@Override
	protected void initWin(boolean hasScrollView) {
		super.initWin(false);

		selectList = new ArrayList<ReqOrder>();

		initTitle();

		initListView();

		initMenuView();

		setVisibleMenu(false);
	}

	@Override
	protected void onStart() {
		super.onStart();
		loadData();
		updataView();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (!Utils.isStringEmpty(selectList)) {
			selectList = null;
		}
		if (!Utils.isStringEmpty(uclwmList)) {
			uclwmList = null;
		}
	}

	@SuppressLint("InflateParams")
	private void initTitle() {
		View titleView = null;

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			titleView = ContextUtil.getLayoutInflater(this)//
					.inflate(R.layout.title_buy_shopcar_h, null);
		} else {

			titleView = ContextUtil.getLayoutInflater(this)//
					.inflate(R.layout.title_buy_shopcar_l, null);
		}
		win_title.addView(titleView);
		backImage = (ImageView) titleView.findViewById(R.id.shopping_back);
		backImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				ShoppingCarActivity.this.finish();

			}
		});

		btnEdit = (TextView) titleView.findViewById(R.id.btn_edit);
		btnEdit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				clickEdit();
			}
		});
	}

	public void clickEdit() {
		isEdit = isEdit ? false : true;
		setVisibleMenu(isEdit);
		boolean isCheck = isEdit;
		String editText = isEdit ? "���" : "�༭";
		btnEdit.setText(editText);
		setAllChecked(!isCheck);
	}

	private TextView mTxtNoData;
	// �����ע
	private TextView mTxtJoinFollow;

	private void initListView() {
		ContextUtil.getLayoutInflater(this)//
				.inflate(R.layout.activity_shoppingcar, win_content);

		listView = (XRecyclerView) findViewById(R.id.recycleview);

		mTxtNoData = (TextView) findViewById(R.id.no_data);

		mTxtJoinFollow = (TextView) findViewById(R.id.join_follow);
		mTxtJoinFollow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// �����ע
				joinFollow();
			}
		});
		
		// ���ò��ֹ�����
		LinearLayoutManager layout = new LinearLayoutManager(this);
		listView.setLayoutManager(layout);

		// ����Item���ӡ��Ƴ�����
		listView.setItemAnimator(new DefaultItemAnimator());
		// ��ӷָ���
		listView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

		listView.setLoadingListener(new LoadingListener() {

			@Override
			public void onRefresh() {
				loadData();
			}

			@Override
			public void onLoadMore() {
				listView.loadMoreComplete();
			}
		});
	}

	/**
	 * �����ע http://u1q4567516.iask.in/cmallwebservice/User/Cart/Follow
	 */
	protected void joinFollow() {
		
		ms  = adapter.getSelectedItemData();
		if (ms == null || ms.size()<= 0) {
			displayToast("��δѡ����Ʒ");
			return;
		}
		
		loaderProgress();

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(BuyNetUrl.getJoinFollow());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setPostData(getJoinFollowPostData());
		loader.addRequestHeader("token", token);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum, long totalByteNum) {
			}

			@Override
			public void onError(Object tag, int responseCode, String errorMessage) {
				displayToast(errorMessage + ":" + responseCode);
				stopProgress();
			}

			@Override
			public void onCompelete(Object tag, Object result) {

				stopProgress();
				RequestNetResultInfo info = JsonParseMall.getJoinShopCarResultInfo(result);
				if (null != info) {
					if (1 == info.getStatus()) {
						displayToast("�����ע�ɹ�");
						loadData();
					} else {
						displayToast(info.getMsg());
						return;
					}
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	Map<Integer,UcwmlEntity> ms = null;
	/**
	 * �����ע
	 * 
	 * @return
	 */
	private String getJoinFollowPostData() {

		StringBuilder builder = new StringBuilder();
	
		Collection<UcwmlEntity> vs = ms.values();
		int i = 0;
		for (UcwmlEntity ucwmlEntity : vs) {
			builder.append("ucml[" + i + "].id").append("=").append(ucwmlEntity.getId()).append("&");
			builder.append("ufml[" + i + "].ti").append("=").append(ucwmlEntity.getRi()).append("&");

			i++;
		}
		String data = builder.toString();
		if ("&".endsWith(data)) {
			data = data.substring(0, data.lastIndexOf("&"));
		}

		return data;
	}

	private void initMenuView() {
		delelteGroup = (LinearLayout) findViewById(R.id.layout_delete);
		setlementGroup = (LinearLayout) findViewById(R.id.layout_settlement);

		txtTotalPrice = (TextView) findViewById(R.id.txt_total_price);
		txtPrice = (TextView) findViewById(R.id.txt_price);
		btnPay = (Button) findViewById(R.id.btn_pay);
		btnPay.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				int num = Integer.parseInt(Utils.getNumberOfString(btnPay.getText().toString().trim()));
				if (num <= 0) {
					displayToast("δѡ����Ʒ");
					return;
				}

				// �õ�ѡ�е�����Դ
				maps = adapter.getSelectedItemData();
				// ȷ�϶���
				// sureOrder();
				// ��֤�Ƿ���ͳһ�ϼҵĲ�Ʒ
				verificationProduct();
			}
		});

		btnDelete = (Button) findViewById(R.id.btn_delete);
		btnDelete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				loadDeleteCart();
			}
		});

		mCkAllSelected = (CheckBox) findViewById(R.id.checkbox_shoppingcar_shop_buy);
		mCkAllSelected.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Log.d("TAG", "checkbox_shoppingcar_shop------------" + isChecked);
				// ȫѡ
				if (adapter != null) {
					adapter.setIsChecked(isChecked);
					btnPay.setText("ȥ����(" + adapter.getSelectedTotalNum() + ")");
				}
				// setAllChecked(isChecked);
			}
		});

		mCkAllEdit = (CheckBox) findViewById(R.id.checkbox_shoppingcar_shop_edit);
		mCkAllEdit.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (adapter != null) {
					adapter.setIsChecked(isChecked);
				}
			}
		});
	}

	/**
	 * ��֤�Ƿ���ͬ���̼ҵĲ�Ʒ
	 */
	protected void verificationProduct() {
		Map<Integer, UcwmlEntity> productMaps = new HashMap<Integer, UcwmlEntity>();

		if (maps == null || maps.size() <= 0) {
			displayToast("δѡ��Ʒ");
			return;
		}

		Collection<UcwmlEntity> entities = maps.values();
		for (UcwmlEntity ucwmlEntity : entities) {
			productMaps.put(ucwmlEntity.getCi(), ucwmlEntity);
		}

		if (productMaps.size() > 1) {
			displayToast("ѡ�в�Ʒ����ͬһ��Ӧ�̵�");
			return;
		}

		sureOrder();
	}

	/**
	 * �õ�ѡ�е�����Դ
	 */
	private Map<Integer, UcwmlEntity> maps = null;

	/**
	 * ȷ�϶���
	 */
	protected void sureOrder() {

		loaderProgress();

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(BuyNetUrl.getSendOrder());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setPostData(getSureOrderPostData());
		// loader.setContentTextList(getSendOrderPostDataList());
		loader.addRequestHeader("token", token);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum, long totalByteNum) {
			}

			@Override
			public void onError(Object tag, int responseCode, String errorMessage) {
				displayToast(errorMessage + ":" + responseCode);
				stopProgress();
			}

			@Override
			public void onCompelete(Object tag, Object result) {
				Log.d("TAG", "ȷ�϶��� = " + result);
				stopProgress();
				RequestNetResultInfo info = JsonParseMall.getJoinShopCarResultInfo(result);
				if (null == info || info.getStatus() != 1) {
					displayToast("��ѯʧ��");
					return;
				}

				// �ɹ��Ժ����ȥ�˶�������

				IntentClassChangeUtils.startOrderBuyActivity(ShoppingCarActivity.this, result, totalPrice);
				// finish();
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * ȷ�϶��� ����
	 * 
	 * @return
	 */
	private String getSureOrderPostData() {
		int i = 0;
		// Map<Integer, UcwmlEntity> maps = adapter.getSelectedItemData();
		Collection<UcwmlEntity> vs = maps.values();
		StringBuilder builder = new StringBuilder();
		builder.append("st").append("=").append(1).append("&");
		for (UcwmlEntity entity : vs) {

			StringBuilder builder2 = new StringBuilder();
			builder.append("oswml[" + i + "].id").append("=").append(entity.getId()).append("&");
			builder.append("oswml[" + i + "].pid").append("=").append(entity.getRi()).append("&");
			builder.append("oswml[" + i + "].pn").append("=").append(entity.getPn()).append("&");
			builder.append("oswml[" + i + "].pc").append("=").append(entity.getRc()).append("&");

			// ��Ʒ��� nvids
			ArrayList<UcrnvmEntity> ucrnvmList = entity.getUcrnvmList();
			int j = 0;
			for (UcrnvmEntity ucrnvmEntity : ucrnvmList) {
				builder.append("oswml[" + i + "].nvids[" + j + "]")//
						.append("=").append(ucrnvmEntity.getNvi()).append("&");
				j++;
			}
			i++;
		}
		return builder.toString();
	}

	private void setVisibleMenu(boolean isVisibleDelete) {
		int deleteVisible = isVisibleDelete ? View.VISIBLE : View.GONE;
		delelteGroup.setVisibility(deleteVisible);
		setlementGroup.setVisibility(deleteVisible == View.VISIBLE ? View.GONE : View.VISIBLE);
	}

	private void loadData() {
		loaderProgress();
		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(BuyNetUrl.getShopCar());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setPostData("");
		loader.addRequestHeader("token", XmlUtils.getInstence(this).getXmlStringValue(XmlName.TOKEN));
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum, long totalByteNum) {

			}

			@Override
			public void onError(Object tag, int responseCode, String errorMessage) {
				stopProgress();
			}

			@Override
			public void onCompelete(Object tag, Object result) {
				stopProgress();
				listView.refreshComplete();
				if (!JsonParseBase.getstatus((JSONArray) result)) {
					finish();
					displayToast(JsonParseBase.getMsg((JSONArray) result));
					return;
				}
				uclwmList = JsonParseBuy.getUclwmList((JSONArray) result);
				setReqSelectMap();
				displayViews(uclwmList);
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	private void setReqSelectMap() {

		if (Utils.isStringEmpty(uclwmList)) {
			return;
		}

		for (int i = 0; i < uclwmList.size(); i++) {
			ReqOrder order = new ReqOrder();
			order.setSt(1);

			UclwmEntity uclwm = uclwmList.get(i);
			ReqOpm reqOpm = new ReqOpm();
			// ��������
			reqOpm.setOn("");
			// �̼�id
			reqOpm.setCi(uclwm.getCi());
			// �̼�����
			reqOpm.setCn(uclwm.getCn());

			double price = 0;
			for (int j = 0; j < uclwm.getUcwmlList().size(); j++) {
				price = price + Double.parseDouble(uclwm.getUcwmlList().get(j).getPr());
			}
			// �����ܶ�
			reqOpm.setRa(Double.toString(price));
			// �ջ�������
			reqOpm.setRn(CmallApplication.getUserInfo().getUserName());
			// �ջ��˳���
			reqOpm.setRci(0);
			// �ջ�����ϸ��ַ
			reqOpm.setRadd("");
			// �ջ��˵绰
			reqOpm.setRp(CmallApplication.getUserInfo().getMobile());

			order.setOpmEntity(reqOpm);
			List<ReqOswm> oswmList = new ArrayList<>();
			for (int j = 0; j < uclwm.getUcwmlList().size(); j++) {
				UcwmlEntity ucwml = uclwm.getUcwmlList().get(j);
				ReqOswm oswm = new ReqOswm();
				oswm.setId(ucwml.getId());
				oswm.setPc(ucwml.getRc());
				oswm.setPid(ucwml.getRi());
				oswm.setPn(ucwml.getPn());
				oswm.setPr(ucwml.getPr());

				oswm.setSelect(true);
				ArrayList<Long> nvids = new ArrayList<>();
				ArrayList<String> spList = new ArrayList<>();
				oswm.setId(ucwml.getId());
				for (int k = 0; k < ucwml.getUcrnvmList().size(); k++) {
					nvids.add((long) ucwml.getUcrnvmList().get(k).getNvi());
					spList.add(ucwml.getUcrnvmList().get(k).getNn() + ":" + ucwml.getUcrnvmList().get(k).getNv());
				}
				oswm.setNvids(nvids);
				oswm.setSpList(spList);
				oswmList.add(oswm);
			}
			order.setOswmList(oswmList);
			if (null != selectList) {
				selectList.add(order);
			}
		}
		printlnLog();
	}

	private void printlnLog() {

		for (ReqOrder order : selectList) {
			TLog.e("ReqOrder", "" + order.getSt());
			for (int i = 0; i < order.getOswmList().size(); i++) {
				TLog.e("ReqOrder", "oswm=" + order.getOswmList().get(i).getPostData(i) + "\n");
			}
		}
	}

	/**
	 * ��ʾ���ﳵ�б�
	 * 
	 * @param dataList
	 */
	private void displayViews(ArrayList<UclwmEntity> dataList) {

		// displayTotalPrice();
			adapter = new BuyShoppingCarAdapter(this, dataList, selectList);
			adapter.setCheckBoxChanged(new CheckBoxChanged() {

				@Override
				public void setCheckBoxChanged(int total, boolean isAllSelected) {
					displayTotalPrice();
					btnPay.setText("ȥ����(" + total + ")");
				}

				@Override
				public void isAllSelcted(boolean isAllSelected) {
					mCkAllSelected.setChecked(isAllSelected);
					mCkAllEdit.setChecked(isAllSelected);
				}
			});
			listView.setAdapter(adapter);
			
		// �ж��Ƿ�������
		if (Utils.isStringEmpty(dataList)) {
			mTxtNoData.setVisibility(View.VISIBLE);
			btnEdit.setVisibility(View.INVISIBLE);
			setlementGroup.setVisibility(View.GONE);
			delelteGroup.setVisibility(View.GONE);
			adapter.setDatas(null);
			return;
		} else {
			btnEdit.setVisibility(View.VISIBLE);
			if (!isEdit) {
				setlementGroup.setVisibility(View.VISIBLE);
			}
			mTxtNoData.setVisibility(View.GONE);
		}
		// ���������ĸı��޸��ܼ�
		adapter.setTotalMoneyChangeListener(new OnNumChangeTotalMoneyChangeListener() {

			@Override
			public void onToTalChange(String totalMoney, int totalNumber) {
				updatTotalMoney(totalMoney);
				btnPay.setText("ȥ����(" + totalNumber + ")");
			}
		});
	}

	/**
	 * ������Ŀ�ļ۸�
	 */
	private void displayTotalPrice() {
		// totalPrice = getSelectTotalPrice();
		totalPrice = adapter.getSelectedPrice();
		updatTotalMoney(totalPrice);
	}

	private void updatTotalMoney(String totalPrice) {
		txtTotalPrice.setText("�ϼ�:��" + totalPrice);
		txtPrice.setText("�ܶ�:��" + totalPrice);
	}

	public String getSelectTotalPrice() {
		double totalPrice = 0;
		if (!Utils.isStringEmpty(selectList)) {
			for (ReqOrder order : selectList) {
				for (ReqOswm oswm : order.getOswmList()) {
					if (!Utils.isStringEmpty(oswm.getPr())) {
						if (oswm.isSelect()) {
							totalPrice += Double.parseDouble(oswm.getPr());
						}
					}

				}
			}
		}
		return Utils.twoDecimals(String.valueOf(totalPrice));
	}

	/**
	 * ɾ�����ﳵ�в�Ʒ
	 */
	private void loadDeleteCart() {
		
		ms = adapter.getSelectedItemData();
		if (ms == null || ms.size()<= 0) {
			displayToast("��δѡ����Ʒ");
			return;
		}
		
		startLoading();

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(BuyNetUrl.deleteCart());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_FORM);
		loader.setContentTextList(getDeleteCartPost());
		loader.addRequestHeader("token", token);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum, long totalByteNum) {
			}

			@Override
			public void onError(Object tag, int responseCode, String errorMessage) {
				displayToast(errorMessage + ":" + responseCode);
				stopLoading();
			}

			@Override
			public void onCompelete(Object tag, Object result) {
				stopLoading();
				// ���������ؽ��
				RequestNetResultInfo info = JsonParse_User.getResultInfo((JSONArray) result);
				if (info.getStatus() >= 1) {
					loadData();
					updataView();
				}
				displayToast(info.getMsg());
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	protected void updataView() {
		updatTotalMoney("0.00");
		btnPay.setText("ȥ����(" + 0 + ")");
		mCkAllSelected.setChecked(false);
		mCkAllEdit.setChecked(false);
	}

	@SuppressWarnings("unused")
	private ArrayList<String[]> getNoTinterestPostData(String articleId) {
		ArrayList<String[]> dataList = new ArrayList<String[]>();
		dataList.add(new String[] { "article_list", articleId });
		return dataList;
		// ArrayList<String[]> textList = new ArrayList<String[]>();
		// textList.add(new String[] { "article_list", articleId });
		// return textList;
	}

	private ArrayList<String[]> getDeleteCartPost() {
		Map<Integer, UcwmlEntity> maps = ms;

		ArrayList<String[]> dataList = new ArrayList<String[]>();

		Collection<UcwmlEntity> entities = maps.values();
		int id = 0;
		for (UcwmlEntity ucwmlEntity : entities) {
			Log.d("TAG", "ɾ�����ﳵ - " + ("ucml[" + id + "].id" + "," + String.valueOf(ucwmlEntity.getId())));
			dataList.add(new String[] { "ucml[" + id + "].id", String.valueOf(ucwmlEntity.getId()) });
			id++;
		}

		return dataList;
	}

	/**
	 * �༭����check״̬ ɾ����ȫ��ѡ,�ύ������ȫѡ
	 * 
	 * @param isCheck
	 */
	private void setAllChecked(boolean isCheck) {
		for (ReqOrder order : selectList) {
			for (ReqOswm oswm : order.getOswmList()) {
				oswm.setSelect(isCheck);
			}

		}
		// adapter.setAllChecked(selectList);
	}

}
