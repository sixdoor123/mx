/**
 * 
 */
package com.baiyi.cmall.activities.main.mall.page;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.business.page.BaseViewPagerFragment;
import com.baiyi.cmall.activities.main.mall.JsonParseMall;
import com.baiyi.cmall.activities.main.mall.UrlNet;
import com.baiyi.cmall.activities.main.mall.entity.CbiEntity;
import com.baiyi.cmall.activities.main.mall.entity.MallDetailProductEntity;
import com.baiyi.cmall.activities.main.mall.entity.MallMainHeadPisc;
import com.baiyi.cmall.activities.main.mall.entity.PbiEntity;
import com.baiyi.cmall.activities.user.login.ExitLogin;
import com.baiyi.cmall.activities.user.login.MemberLoginActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.request.manager.MallManager;
import com.baiyi.cmall.request.manager.NullJsonData;
import com.baiyi.cmall.utils.IntentClassChangeUtils;
import com.baiyi.cmall.utils.JsonParseBase;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.loader.ImageLoader;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import cn.lightsky.infiniteindicator.InfiniteIndicatorLayout;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView;
import cn.lightsky.infiniteindicator.slideview.BaseSliderView.OnSliderClickListener;
import cn.lightsky.infiniteindicator.slideview.DefaultSliderView;

/**
 * 商城-详情-产品信息
 * 
 * @author tangkun
 * 
 */
public class MalllInfoPage extends BaseViewPagerFragment {

	// 产品信息-----------------------------
	// 产品名称
	private TextView proName;
	// 产品价格
	private TextView proPice;

	// 商家信息-------------------------------
	// 进入店铺
	private LinearLayout btnShop;
	// 商家-关注
	private LinearLayout btnCollect;
	private TextView tvCollect;
	// 商家名称
	private TextView shopName;
	// 商家信息
	private TextView shopInfo;
	// 商家关注
	private TextView shopFollow;
	// 商家全部产品数量
	private TextView shopProNum;
	// 商家 全部评论
	private TextView shopCommnetNum;
	// 商家封面
	private ImageView imgShop;
	// 选择(已选)加入购物车
	private LinearLayout btnShopCarDictionary;
	// 已选规格
	private InfiniteIndicatorLayout infiniteIndicatorLayout;
	private ImageView mImgNoPic;

	private TextView mTvSelect;

	private MallDetailProductEntity entity = null;
	// 产品id
	// private int pi;

	private static MalllInfoPage fragment = null;
	private BuyPopCallBack buyPopCallBack = null;

	public static MalllInfoPage newInstance(int index) {
		if (fragment == null) {
			fragment = new MalllInfoPage();
			Bundle args = new Bundle();
			args.putInt(BUNDLE_FRAGMENT_INDEX, index);
			fragment.setArguments(args);
		}
		return fragment;
	}

	public MallDetailProductEntity getMallDetailProductEntity() {
		return entity;
	}

	// 商家id
	public int getShopId() {
		return entity == null ? 0 : entity.getCbiEntity().getId();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
		loadData(UrlNet.getMallDetailProduct(id), "", LOAD_DATA);
	}

	// 图文详情条目
	private LinearLayout mLlPicFileDetail;

	private ScrollView mScrollview;

	// 图文详情
	private LinearLayout mLlPicFileContent;
	private ScrollView MyScrollViewContent;

	// 图文显示箭头
	private ImageView mImgUpDown;
	// 内容
	private TextView mTxtContent;

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = ContextUtil.getLayoutInflater(getActivity())//
				.inflate(R.layout.page_mall_detail_info, container, false);
		infiniteIndicatorLayout = (InfiniteIndicatorLayout) view.findViewById(R.id.infinite_anim_circle);
		mImgNoPic = (ImageView) view.findViewById(R.id.img_no_pic);

		proName = (TextView) view.findViewById(R.id.txt_info_specifications);
		proPice = (TextView) view.findViewById(R.id.txt_price);
		mTvSelect = (TextView) view.findViewById(R.id.select_dictionary);
		shopName = (TextView) view.findViewById(R.id.mall_shop_name);
		shopInfo = (TextView) view.findViewById(R.id.mall_shop_info);
		shopFollow = (TextView) view.findViewById(R.id.mall_shop_follow);
		shopProNum = (TextView) view.findViewById(R.id.mall_shop_pronum);
		shopCommnetNum = (TextView) view.findViewById(R.id.mall_shop_commentnum);
		imgShop = (ImageView) view.findViewById(R.id.img_mall_shop);
		btnShop = (LinearLayout) view.findViewById(R.id.btn_shop);
		btnCollect = (LinearLayout) view.findViewById(R.id.btn_collect);
		tvCollect = (TextView) view.findViewById(R.id.tv_collect);

		mLlPicFileDetail = (LinearLayout) view.findViewById(R.id.txt_pic_file_detail);
		mLlPicFileDetail.setOnClickListener(new ShopOnclick());
		mScrollview = (ScrollView) view.findViewById(R.id.scrollview);

		// 图文详情
		mLlPicFileContent = (LinearLayout) view.findViewById(R.id.ll_pic_file);
		MyScrollViewContent = (ScrollView) view.findViewById(R.id.scrollview_content);
		mImgUpDown = (ImageView) view.findViewById(R.id.img_up_down);
		mTxtContent = (TextView) view.findViewById(R.id.txt_content);

		// 显示图文详情
		mScrollview.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					break;
				case MotionEvent.ACTION_UP:
					floatsX.clear();
					floatsY.clear();
					break;
				case MotionEvent.ACTION_MOVE:
					int sh = mScrollview.getChildAt(0).getMeasuredHeight();
					int vhy = v.getHeight() + v.getScrollY();
					if (v.getScrollY() <= 0) {
						// 到顶
					} else if (sh <= vhy) {
						// 到低
						// if (scrollViewBottomListener != null) {
						// scrollViewBottomListener.onScrollViewBottom();
						// }
						floatsY.add(event.getY());
						floatsX.add(event.getX());

						// 得到起始页
						float startX = floatsX.get(0);
						float startY = floatsY.get(0);

						// 得到结束点
						float endX = floatsX.get(floatsX.size() - 1);
						float endY = floatsY.get(floatsY.size() - 1);

						Log.d("TAG", "(endY - startY) = " + ((endY - startY)));

						if ((startY - endY) > 150) {
							// 箭头向下
							mImgUpDown.setBackgroundResource(R.drawable.down_arrow);
							mTxtContent.setText("下拉收起图文详情");

							Animation animationIn = AnimationUtils.loadAnimation(getActivity(),
									R.anim.slide_vp_in_bottom);
							mLlPicFileContent.setVisibility(View.VISIBLE);
							// 从下面进入
							mLlPicFileContent.setAnimation(animationIn);

							Animation animationOut = AnimationUtils//
									.loadAnimation(getActivity(), R.anim.slide_vp_out_top);
							mScrollview.setAnimation(animationOut);

							mScrollview.setVisibility(View.GONE);

							floatsX.clear();
							floatsY.clear();
						}

					}
					return false;
				}
				return false;
			}
		});

		// 隐藏图文详情
		MyScrollViewContent.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					break;
				case MotionEvent.ACTION_UP:
					floatsX.clear();
					floatsY.clear();

					num = 0;
					break;
				case MotionEvent.ACTION_MOVE:

					floatsY.add(event.getY());
					floatsX.add(event.getX());

					// 得到起始页
					float startX = floatsX.get(0);
					float startY = floatsY.get(0);

					// 得到结束点
					float endX = floatsX.get(floatsX.size() - 1);
					float endY = floatsY.get(floatsY.size() - 1);

					if ((endY - startY) > 150) {
						num++;
						Log.d("TAG", "num ---------- >>>>>>>>>>  " + num);
						// 到顶
						if (num <= 2) {
							return false;
						}

						Animation animationOut = AnimationUtils//
								.loadAnimation(getActivity(), R.anim.slide_in_from_top_s);
						mLlPicFileContent.setAnimation(animationOut);

						Animation animationIn = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_from_top);
						mScrollview.setVisibility(View.VISIBLE);
						mScrollview.setAnimation(animationIn);

						mLlPicFileContent.setVisibility(View.GONE);
						// 箭头向上
						mImgUpDown.setBackgroundResource(R.drawable.up_arrow);
						mTxtContent.setText("上拉查看图文详情");

					} else if (mScrollview.getChildAt(0).getMeasuredHeight() <= v.getHeight() + v.getScrollY()) {
						// 到低
					}
					return false;
				}
				return false;
			}
		});

		btnShopCarDictionary = (LinearLayout) view.findViewById(R.id.btn_shopcar_dictionary);

		btnShopCarDictionary.setOnClickListener(new SelectDictionaryOnclick());
		btnCollect.setOnClickListener(new ShopOnclick());
		btnShop.setOnClickListener(new ShopOnclick());

		// 数据加载完之后才可点击
		btnShopCarDictionary.setEnabled(false);
		btnCollect.setEnabled(false);
		btnShop.setEnabled(false);
		return view;
	}

	// 计次
	int num = 0;

	List<Float> floatsY = new ArrayList<Float>();
	List<Float> floatsX = new ArrayList<Float>();
	// 加载本页面数据
	private static final int LOAD_DATA = 1;
	// 判断是否有没有关注
	private static final int FOLLOW_FOCUS = 2;
	// 添加关注
	private static final int ADD_FOLLOW = 3;

	private void loadData(String url, String postData, final int state) {
		loaderProgress();
		JsonLoader loader = new JsonLoader(getContext());
		loader.setUrl(url);
		loader.addRequestHeader("token", CmallApplication.getUserInfo().getToken());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setPostData(postData);
		if (state != LOAD_DATA) {
			loader.addRequestHeader("token", CmallApplication.getUserInfo().getToken());
		}
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
				switch (state) {
				case LOAD_DATA:
					entity = JsonParseMall.getMallDetailProductEntity((JSONArray) result);
					if (entity == null) {
						return;
					}

					displayViews();
					// 获取数据成功后，用商家id判断当前商家有没有关注
					loadData(UrlNet.getFollowFocusCompany(getShopId() + ""), "", FOLLOW_FOCUS);
					break;

				case FOLLOW_FOCUS:
					RequestNetResultInfo requestNetResultInfo = JsonParseMall.getResultInfo((JSONArray) result);
					/*
					 * followState=0时：当前用户没有关注当前商家 followState=1时：当前用户已经关注当前商家
					 * followState=2时：因某些原因不能对当前商家进行关注，原因会在msg中
					 */
					int followState = JsonParseMall.getDataInt((JSONArray) result);
					if (followState == 0) {
						btnCollect.setEnabled(true);
					} else if (followState == 1) {
						tvCollect.setText("已关注");
						btnCollect.setEnabled(false);
					} else if (followState == 2) {
						((BaseActivity) getActivity()).displayToast(requestNetResultInfo.getMsg());
					} else {
						btnCollect.setEnabled(true);
					}
					break;
				case ADD_FOLLOW:

					if (NumEntity.PLEASE_LOG.equals(JsonParseBase.getMsg((JSONArray) result))) {
						ExitLogin.getInstence(getActivity()).cleer();
						((BaseActivity) getActivity()).displayToast(NumEntity.PLEASE_LOG);
						return;
					}
					if (JsonParseBase.getstatus((JSONArray) result)) {
						tvCollect.setText("已关注");
						btnCollect.setEnabled(false);
					}
					((BaseActivity) getActivity()).displayToast(JsonParseBase.getMsg((JSONArray) result));
					break;
				}

			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	private void displayShopImg(String shopImg) {
		ImageLoader loader = new ImageLoader(getContext(), true);
		loader.setUrl(shopImg);
		loader.setPostData(NullJsonData.getPostData());
		loader.setMethod(BaseNetLoder.Method_Get);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum, long totalByteNum) {
			}

			@Override
			public void onError(Object tag, int responseCode, String errorMessage) {
			}

			@Override
			public void onCompelete(Object tag, Object result) {
				if (result == null) {
					imgShop.setBackgroundResource(R.drawable.img_feed_picture);
					return;
				}
				BitmapDrawable drawable = (BitmapDrawable) result;
				imgShop.setImageDrawable(drawable);

			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	private void displayViews() {
		if (entity == null) {
			return;
		}

		displayPics(entity.getPicsList());
		displayPbi(entity.getPbiEntity());
		disPlaySelected(entity.getPbiEntity());
		displayCbi(entity.getCbiEntity());
		// displayShopImg(entity.getCbiEntity().getLg());
	}

	/**
	 * 
	 * @param picsList
	 */
	// 显示产品图片
	private void displayPics(ArrayList<MallMainHeadPisc> picsList) {
		if (Utils.isStringEmpty(picsList)) {
			mImgNoPic.setVisibility(View.VISIBLE);
			infiniteIndicatorLayout.setVisibility(View.GONE);
			return;
		} else {
			mImgNoPic.setVisibility(View.GONE);
			infiniteIndicatorLayout.setVisibility(View.VISIBLE);
		}

		for (int i = 0; i < picsList.size(); i++) {
			DefaultSliderView textSliderView = new DefaultSliderView(getContext());
			// textSliderView.image(pi.get(position).getUrl())
			textSliderView.image(R.drawable.img_test_mall_detail).setScaleType(BaseSliderView.ScaleType.Fit)
					.setOnSliderClickListener(new OnSliderClickListener() {

						@Override
						public void onSliderClick(BaseSliderView slider) {

						}
					});
			infiniteIndicatorLayout.addSlider(textSliderView);
		}

		// 设置开始轮播
		infiniteIndicatorLayout.start();
		// 设置指示器的显示位置
		infiniteIndicatorLayout.setIndicatorPosition(InfiniteIndicatorLayout.IndicatorPosition.Center_Bottom);
		// 设置自动开始轮播，默认向右
		infiniteIndicatorLayout.startAutoScroll();
		// 设置轮播的时间为5 秒
		infiniteIndicatorLayout.setInterval(5 * 1000);
	}

	private StringBuilder sb = null;

	// 显示产品信息
	private void displayPbi(PbiEntity entity) {
		if (entity == null) {
			return;
		}
		sb = new StringBuilder();
		sb.append(entity.getPn());
		sb.append("(");
		sb.append(entity.getSn());
		sb.append(")(");
		sb.append(entity.getBn());
		sb.append(")");
		proName.setText(sb.toString());
		proPice.setText("￥ " + entity.getPp());
	}

	// 显示已选商品信息
	private void disPlaySelected(PbiEntity entity) {

		// 数据加载完之后才可点击
		btnShopCarDictionary.setEnabled(true);
		btnShop.setEnabled(true);

		mTvSelect.setText(entity.getPn());
	}

	private CbiEntity cbiEntity;

	// 显示商家信息
	private void displayCbi(CbiEntity cbiEntity) {
		this.cbiEntity = cbiEntity;
		if (cbiEntity == null) {
			return;
		}
		shopName.setText(cbiEntity.getCm());
		shopInfo.setText(cbiEntity.getCd());
		shopFollow.setText(String.valueOf(cbiEntity.getFc()));
		shopProNum.setText(String.valueOf(cbiEntity.getAp()));
		shopCommnetNum.setText(String.valueOf(cbiEntity.getAc()));
	}

	public void setBuyPopCallBack(BuyPopCallBack buyPopCallBack) {
		this.buyPopCallBack = buyPopCallBack;
	}

	class SelectDictionaryOnclick implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			if (buyPopCallBack != null) {
				buyPopCallBack.setBuyPopCallBackListener();
			}
		}

	}

	class ShopOnclick implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int clickId = v.getId();
			if (clickId == R.id.btn_shop) {
				// 进入店铺
				IntentClassChangeUtils.startShopDetailActivity(getContext(), getShopId());

				getActivity().finish();
			} else if (clickId == R.id.btn_collect) {
				// 关注
				loadData(UrlNet.addFollowCompany(), MallManager.getAddCompanyData(cbiEntity.getId()), ADD_FOLLOW);
			} else if (clickId == R.id.txt_pic_file_detail) {
				// 图文详情
				if (picFileDetailClickListener != null) {
					picFileDetailClickListener.onPicFileClick();
				}
			}
		}

	}

	public interface BuyPopCallBack {
		public void setBuyPopCallBackListener();
	}

	@Override
	public boolean isViewBeingDragged(MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		infiniteIndicatorLayout.startAutoScroll();
	}

	@Override
	public void onPause() {
		super.onPause();
		infiniteIndicatorLayout.stopAutoScroll();
	}

	public MallDetailProductEntity getEntity() {
		return entity;
	}

	/**
	 * 全部商品数量
	 * 
	 * @return
	 */
	public int getAp() {
		if (null == cbiEntity) {
			return 0;
		}
		return cbiEntity.getAp();
	}

	public String getProName() {
		if (sb != null) {
			return sb.toString();
		}
		return "暂无";
	}

	/**
	 * 点击图文详情
	 * 
	 * @author sunxy
	 *
	 */
	public interface OnPicFileDetailClickListener {
		void onPicFileClick();
	}

	private OnPicFileDetailClickListener picFileDetailClickListener;

	public void setPicFileDetailClickListener(OnPicFileDetailClickListener picFileDetailClickListener) {
		this.picFileDetailClickListener = picFileDetailClickListener;
	}

	/**
	 * 上拉查看图文详情
	 * 
	 * @author sunxy
	 *
	 */
	public interface OnScrollViewBottomListener {
		void onScrollViewBottom();
	}

	private OnScrollViewBottomListener scrollViewBottomListener;

	public void setScrollViewBottomListener(OnScrollViewBottomListener scrollViewBottomListener) {
		this.scrollViewBottomListener = scrollViewBottomListener;
	}

	public PbiEntity getPbi() {
		return entity.getPbiEntity();
	}
}
