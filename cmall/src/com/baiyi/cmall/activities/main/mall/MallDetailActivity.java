/**
 * 
 */
package com.baiyi.cmall.activities.main.mall;

import java.util.Collection;
import java.util.Map;
import org.json.JSONArray;
import me.relex.seamlessviewpagerheader.tools.ScrollableFragmentListener;
import me.relex.seamlessviewpagerheader.widget.SlidingTabLayout;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.RadioGroup.LayoutParams;
import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.BaseViewpagerActivity;
import com.baiyi.cmall.activities.main.buy.BuyNetUrl;
import com.baiyi.cmall.activities.main.buy.ShoppingCarActivity;
import com.baiyi.cmall.activities.main.header_top.PublishPurchaseActivity;
import com.baiyi.cmall.activities.main.header_top.PublishSupplyActivity;
import com.baiyi.cmall.activities.main.mall.entity.MallDetailProductEntity;
import com.baiyi.cmall.activities.main.mall.entity.PbiEntity;
import com.baiyi.cmall.activities.main.mall.entity.PnvmlEntity;
import com.baiyi.cmall.activities.main.mall.page.MalllCommentPage;
import com.baiyi.cmall.activities.main.mall.page.MalllDetailPage;
import com.baiyi.cmall.activities.main.mall.page.MalllInfoPage;
import com.baiyi.cmall.activities.main.mall.page.MalllInfoPage.BuyPopCallBack;
import com.baiyi.cmall.activities.main.mall.page.MalllInfoPage.OnPicFileDetailClickListener;
import com.baiyi.cmall.activities.main.mall.page.MalllInfoPage.OnScrollViewBottomListener;
import com.baiyi.cmall.activities.main.mall.pop.PopMallDetailBuy;
import com.baiyi.cmall.activities.main.mall.pop.PopMallDetailBuy.OnBuyNowOrderListener;
import com.baiyi.cmall.activities.main.mall.pop.PopMallDetailBuy.OnJoinShopCarSuccess;
import com.baiyi.cmall.activities.main.mall.toast.JoinShopCarSuccess;
import com.baiyi.cmall.activities.user.login.ExitLogin;
import com.baiyi.cmall.activities.user.login.MemberLoginActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.pageviews.MyViewPager;
import com.baiyi.cmall.utils.IntentClassChangeUtils;
import com.baiyi.cmall.utils.JsonParseBase;
import com.baiyi.cmall.utils.MobileStateUtils;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.utils.XmlName;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.tencent.map.b.p;

/**
 * @author tangkun
 *
 */
public class MallDetailActivity extends BaseViewpagerActivity implements ScrollableFragmentListener {

	// ���ذ�ť
	private ImageView backImageView;
	// ��һҳ
	private ImageView nextImageView;
	// ����
	private Button btnMenuShop = null;
	// ��ע
	private Button btnMenuCollect = null;
	// ���ﳵ
	private Button btnMenuShopCar = null;
	// ���빺�ﳵ
	private Button btnBuyShop = null;
	// ��������
	private Button btnBuy = null;

	private PopMallDetailBuy popDetailBuy = null;

	private static String[] titles = { "��Ʒ", "����", "����" };

	private String pi;

	private String token;

	private int followState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		init();

		initMenu();

		loadDate(UrlNet.getFollowFocus(pi), LOAD_FOLLOW_FOCUS);
	}

	private void init() {
		pi = super.id;
		token = XmlUtils.getInstence(this).getXmlStringValue(XmlName.TOKEN);
	}

	private void initMenu() {

		backImageView = (ImageView) findViewById(R.id.mall_detail_back);
		nextImageView = (ImageView) findViewById(R.id.mall_detail_next);

		btnMenuShop = (Button) findViewById(R.id.btn_mall_detail_menu_shop);
		btnMenuCollect = (Button) findViewById(R.id.btn_mall_detail_menu_collect);
		btnMenuShopCar = (Button) findViewById(R.id.btn_mall_detail_menu_shoppingcar);

		btnBuyShop = (Button) findViewById(R.id.btn_mall_detail_menu_buy_shoppingcar);
		btnBuy = (Button) findViewById(R.id.btn_mall_detail_menu_buy);

		backImageView.setOnClickListener(new MenuOnclick());
		nextImageView.setOnClickListener(new MenuOnclick());
		btnMenuShop.setOnClickListener(new MenuOnclick());
		btnMenuCollect.setOnClickListener(new MenuOnclick());
		btnMenuShopCar.setOnClickListener(new MenuOnclick());

		btnBuyShop.setOnClickListener(new MenuOnclick());
		btnBuy.setOnClickListener(new MenuOnclick());
	}

	
	private class MenuOnclick implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.btn_mall_detail_menu_shop) {

				// �̼ҵ���
				IntentClassChangeUtils.startShopDetailActivity(MallDetailActivity.this,
						MalllInfoPage.newInstance(0).getShopId());
				
				finish();

			} else if (id == R.id.btn_mall_detail_menu_collect) {

				if (TextViewUtil.isStringEmpty(token)) {
					displayToast("���ȵ�¼");
					return;
				}
				// ��ע
				if (followState == 0) {
					loadDate(UrlNet.addFollowProduct(pi), ADD_FOLLOW);
				} else if (followState == 1) {
					loadDate(UrlNet.FollowCancel(pi), CANCEL_FOLLOW);
				}
			} else if (id == R.id.btn_mall_detail_menu_shoppingcar) {

				if (TextViewUtil.isStringEmpty(token)) {
					displayToast("���ȵ�¼");
					return;
				}
				// ���ﳵ
				IntentClassChangeUtils.startShoppingCarActivity(MallDetailActivity.this);
			} else if (id == R.id.btn_mall_detail_menu_buy_shoppingcar) {

				// ���빺�ﳵ
				displayPopDetail();
			} else if (id == R.id.btn_mall_detail_menu_buy) {

				// ��������
				displayPopDetail();
			} else if (id == R.id.mall_detail_back) {

				finish();
			} else if (id == R.id.mall_detail_next) {

				showMorePop();
			}
		}

	}

	private static final int LOAD_FOLLOW_FOCUS = 1;
	private static final int ADD_FOLLOW = 2;
	private static final int CANCEL_FOLLOW = 3;

	// ��ǰ�û��Ƿ���Թ�ע��ǰ��Ʒ
	private void loadDate(String url, final int state) {

		startLoading();

		JsonLoader jsonLoader = new JsonLoader(MallDetailActivity.this);
		jsonLoader.setUrl(url);
		jsonLoader.setMethod(BaseNetLoder.Method_Post);
		jsonLoader.addRequestHeader("token", token);
		jsonLoader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		jsonLoader.setPostData("");
		jsonLoader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum, long totalByteNum) {

			}

			@Override
			public void onError(Object tag, int responseCode, String errorMessage) {
				displayToast(errorMessage);
				stopLoading();
			}

			@Override
			public void onCompelete(Object tag, Object result) {

				stopLoading();
				switch (state) {
				case LOAD_FOLLOW_FOCUS:// �ж��Ƿ��Ѿ���ע

					RequestNetResultInfo requestNetResultInfo = JsonParseMall.getResultInfo((JSONArray) result);
					followState = JsonParseMall.getDataInt((JSONArray) result);

					if (followState == 0) {
						Drawable drawable = MallDetailActivity.this.getResources().getDrawable(R.drawable.icon_follow);
						btnMenuCollect.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);
					} else if (followState == 1) {
						Drawable drawable = MallDetailActivity.this.getResources()
								.getDrawable(R.drawable.icon_follow_select);
						btnMenuCollect.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);
					} else if (requestNetResultInfo.getStatus() == 2) {
						displayToast(requestNetResultInfo.getMsg());
					}
					break;
				case ADD_FOLLOW:// ��ӹ�ע
					if (NumEntity.PLEASE_LOG.equals(JsonParseBase.getMsg((JSONArray) result))) {
						ExitLogin.getInstence(MallDetailActivity.this).cleer();
						displayToast(NumEntity.PLEASE_LOG);
						goActivity(MemberLoginActivity.class);
						return;
					}
					if (JsonParseMall.getResultInfo((JSONArray) result).getStatus() == 1) {

						followState = 1;
						Drawable drawable1 = MallDetailActivity.this.getResources()
								.getDrawable(R.drawable.icon_follow_select);
						btnMenuCollect.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable1, null, null);
					}
					displayToast(JsonParseBase.getMsg((JSONArray) result));
					break;
				case CANCEL_FOLLOW:// ȡ����ע
					if (NumEntity.PLEASE_LOG.equals(JsonParseBase.getMsg((JSONArray) result))) {
						ExitLogin.getInstence(MallDetailActivity.this).cleer();
						displayToast(NumEntity.PLEASE_LOG);
						goActivity(MemberLoginActivity.class);
						return;
					}
					if (JsonParseMall.getResultInfo((JSONArray) result).getStatus() == 1) {
						followState = 0;
						Drawable drawable2 = MallDetailActivity.this.getResources().getDrawable(R.drawable.icon_follow);
						btnMenuCollect.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable2, null, null);
					}
					displayToast(JsonParseBase.getMsg((JSONArray) result));
					break;
				}

			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);
	}

	// ���򵯴�
	private void displayPopDetail() {

		PbiEntity entity = page.getPbi();
		String io = entity.getIo();
		if (TextViewUtil.isStringEmpty(io)) {
			// δ��¼
			displayToast("���ȵ�¼");
			ExitLogin.getInstence(this).cleer();
			return;
		} else if (!Boolean.parseBoolean(io)) {
			displayToast("�Լ��Ĳ�Ʒ�����Թ���");
			return;
		}

		if (popDetailBuy == null) {
			popDetailBuy = new PopMallDetailBuy(
					ContextUtil.getLayoutInflater(MallDetailActivity.this)//
							.inflate(R.layout.pop_mall_detail_buy, null),
					MallDetailActivity.this, getScreenWidth() * 82 / 100,
					getScreenHeight() - MobileStateUtils.getStatusHeight(this), pi,  page.getProName());
			popDetailBuy.setAnimationStyle(R.style.pop_right_to_left);
		}
		popDetailBuy.showPopAtLocation(win_content, Gravity.RIGHT | Gravity.BOTTOM);

		popDetailBuy.setOnJoinShopCarSuccess(new OnJoinShopCarSuccess() {

			@Override
			public void onJoinSuccess(Object result) {
				JoinShopCarSuccess shopCarSuccess //
				= new JoinShopCarSuccess(MallDetailActivity.this, result);
				shopCarSuccess.show();
			}
		});

		popDetailBuy.setBuyNowOrderListener(new OnBuyNowOrderListener() {

			@Override
			public void onBuyNow(Map<Integer, PnvmlEntity> maps, String currentPrice, String number) {

				BuyNowOrder(maps, currentPrice, number);
			}
		});
	}

	@Override
	public SlidingTabLayout getSlidingTabLayout(View view) {
		// TODO Auto-generated method stub
		SlidingTabLayout slidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.tabs);
		slidingTabLayout.setDistributeEvenly(false);
		slidingTabLayout.setSelectStripColor(0xffffffff);
		slidingTabLayout.setTitleColor(0xffffffff);
		return slidingTabLayout;
	}

	private MyViewPager mViewPager;

	@Override
	public MyViewPager getMyViewPager(View view) {
		mViewPager = (MyViewPager) view.findViewById(R.id.mall_detail_pager);
		return mViewPager;
	}

	@Override
	public String[] getTitles() {
		return titles;
	}

	private MalllInfoPage page;

	@Override
	public Fragment getFrament(int position) {
		// TODO Auto-generated method stub
		if (position == 0) {
			page = MalllInfoPage.newInstance(position);
			page.setBuyPopCallBack(new BuyPopCallBack() {

				@Override
				public void setBuyPopCallBackListener() {
					// TODO Auto-generated method stub
					displayPopDetail();
				}
			});

			page.setPicFileDetailClickListener(new OnPicFileDetailClickListener() {

				@Override
				public void onPicFileClick() {
					// ���뵽����ҳ��
					mViewPager.setCurrentItem(1);
				}
			});

			return page.setId(id);
		} else if (position == 1) {
			return MalllDetailPage.newInstance(position).setId(id);
		} else {
			return MalllCommentPage.newInstance(position).setId(id);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.baiyi.cmall.activities.main.BaseViewpagerActivity#getContentView()
	 */
	@Override
	public int getContentLayout() {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			return R.layout.title_mall_detail_h;
		}
		return R.layout.title_mall_detail_l;
	}

	private PopupWindow popupWindow;

	/**
	 * ��ʾpop�Ի���
	 */
	public void showMorePop() {
		View popView = ContextUtil.getLayoutInflater(MallDetailActivity.this).inflate(R.layout.pop_main_more, null);
		// pop��ʾ�Ĵ�С
		popupWindow = new PopupWindow(popView, ((BaseActivity) MallDetailActivity.this).getScreenWidth() / 2,
				LayoutParams.WRAP_CONTENT);
		if (!popupWindow.isShowing()) {
			popupWindow.setFocusable(true);
			popupWindow.setOutsideTouchable(true);
			popupWindow.setBackgroundDrawable(new BitmapDrawable());
			// pop��ʾ��λ��

			float density = MallDetailActivity.this.getResources().getDisplayMetrics().density;
			int y = (int) (density * 15);
			popupWindow.showAsDropDown(nextImageView, ((BaseActivity) MallDetailActivity.this).getScreenWidth() / 2,
					(int) -(y - 10));

		} else {
			popupWindow.dismiss();

		}
		initPopView(popView);
	}

	/**
	 * ��ʼ����������pop����
	 * 
	 * @param popView
	 */
	private void initPopView(View popView) {
		TextView purchaseView = (TextView) popView.findViewById(R.id.tv_purchase);
		TextView supplyMaterialView = (TextView) popView.findViewById(R.id.tv_supply_material);
		TextView delegationWuLiu = (TextView) popView.findViewById(R.id.tv_delegation);
		/**
		 * �ɹ�
		 */
		purchaseView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(MallDetailActivity.this, PublishPurchaseActivity.class);
				MallDetailActivity.this.startActivity(intent);
				popupWindow.dismiss();
			}
		});
		/**
		 * ��Ӧ
		 */
		supplyMaterialView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MallDetailActivity.this, PublishSupplyActivity.class);
				MallDetailActivity.this.startActivity(intent);
				popupWindow.dismiss();
			}
		});
		/**
		 * ����
		 */
		delegationWuLiu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Intent intent = new Intent(context,
				// ShoppingBusActivity.class);
				Intent intent = new Intent(MallDetailActivity.this, ShoppingCarActivity.class);
				MallDetailActivity.this.startActivity(intent);
				popupWindow.dismiss();
			}
		});
	}

	/**
	 * ��������
	 * 
	 * @param currentPrice
	 * @param maps
	 * @param number
	 */
	protected void BuyNowOrder(Map<Integer, PnvmlEntity> maps, final String currentPrice, String number) {
		loaderProgress();
		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(BuyNetUrl.getSendOrder());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setPostData(getSureOrderPostData(maps, currentPrice, number));
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
				popDetailBuy.PopDismiss();
				// �ɹ��Ժ����ȥ�˶�������
				IntentClassChangeUtils.startOrderBuyActivity(MallDetailActivity.this, result, currentPrice);
				// finish();
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * ȷ�϶��� ����
	 * 
	 * @param currentPrice
	 * @param PnvmlMaps
	 * @param number
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getSureOrderPostData(Map<Integer, PnvmlEntity> PnvmlMaps, String currentPrice, String number) {
		int i = 0;
		MallDetailProductEntity productEntity = page.getEntity();
		PbiEntity entity = productEntity.getPbiEntity();
		if (null == page) {
			displayToast("δ�������");
			return "";
		}
		StringBuilder builder = new StringBuilder();
		builder.append("st").append("=").append(1).append("&");

		StringBuilder builder2 = new StringBuilder();
		// builder.append("oswml[" + i +
		// "].id").append("=").append(entity.getId()).append("&");
		// ��Ʒid
		builder.append("oswml[" + i + "].pid").append("=").append(entity.getId()).append("&");
		//// ��Ʒ����
		builder.append("oswml[" + i + "].pn").append("=").append(entity.getPn()).append("&");
		// ����
		builder.append("oswml[" + i + "].pc").append("=").append(number).append("&");

		// ��Ʒ��� nvids
		Collection<PnvmlEntity> collections = PnvmlMaps.values();
		int j = 0;
		for (PnvmlEntity pnvmlEntity : collections) {
			builder.append("oswml[" + i + "].nvids[" + j + "]")//
					.append("=").append(pnvmlEntity.getId()).append("&");
			j++;
		}
		i++;
		return builder.toString();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		token = null;
		page = null;

	}
}
