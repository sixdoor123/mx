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
 * �̳�-����-��Ʒ��Ϣ
 * 
 * @author tangkun
 * 
 */
public class MalllInfoPage extends BaseViewPagerFragment {

	// ��Ʒ��Ϣ-----------------------------
	// ��Ʒ����
	private TextView proName;
	// ��Ʒ�۸�
	private TextView proPice;

	// �̼���Ϣ-------------------------------
	// �������
	private LinearLayout btnShop;
	// �̼�-��ע
	private LinearLayout btnCollect;
	private TextView tvCollect;
	// �̼�����
	private TextView shopName;
	// �̼���Ϣ
	private TextView shopInfo;
	// �̼ҹ�ע
	private TextView shopFollow;
	// �̼�ȫ����Ʒ����
	private TextView shopProNum;
	// �̼� ȫ������
	private TextView shopCommnetNum;
	// �̼ҷ���
	private ImageView imgShop;
	// ѡ��(��ѡ)���빺�ﳵ
	private LinearLayout btnShopCarDictionary;
	// ��ѡ���
	private InfiniteIndicatorLayout infiniteIndicatorLayout;
	private ImageView mImgNoPic;

	private TextView mTvSelect;

	private MallDetailProductEntity entity = null;
	// ��Ʒid
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

	// �̼�id
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

	// ͼ��������Ŀ
	private LinearLayout mLlPicFileDetail;

	private ScrollView mScrollview;

	// ͼ������
	private LinearLayout mLlPicFileContent;
	private ScrollView MyScrollViewContent;

	// ͼ����ʾ��ͷ
	private ImageView mImgUpDown;
	// ����
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

		// ͼ������
		mLlPicFileContent = (LinearLayout) view.findViewById(R.id.ll_pic_file);
		MyScrollViewContent = (ScrollView) view.findViewById(R.id.scrollview_content);
		mImgUpDown = (ImageView) view.findViewById(R.id.img_up_down);
		mTxtContent = (TextView) view.findViewById(R.id.txt_content);

		// ��ʾͼ������
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
						// ����
					} else if (sh <= vhy) {
						// ����
						// if (scrollViewBottomListener != null) {
						// scrollViewBottomListener.onScrollViewBottom();
						// }
						floatsY.add(event.getY());
						floatsX.add(event.getX());

						// �õ���ʼҳ
						float startX = floatsX.get(0);
						float startY = floatsY.get(0);

						// �õ�������
						float endX = floatsX.get(floatsX.size() - 1);
						float endY = floatsY.get(floatsY.size() - 1);

						Log.d("TAG", "(endY - startY) = " + ((endY - startY)));

						if ((startY - endY) > 150) {
							// ��ͷ����
							mImgUpDown.setBackgroundResource(R.drawable.down_arrow);
							mTxtContent.setText("��������ͼ������");

							Animation animationIn = AnimationUtils.loadAnimation(getActivity(),
									R.anim.slide_vp_in_bottom);
							mLlPicFileContent.setVisibility(View.VISIBLE);
							// ���������
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

		// ����ͼ������
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

					// �õ���ʼҳ
					float startX = floatsX.get(0);
					float startY = floatsY.get(0);

					// �õ�������
					float endX = floatsX.get(floatsX.size() - 1);
					float endY = floatsY.get(floatsY.size() - 1);

					if ((endY - startY) > 150) {
						num++;
						Log.d("TAG", "num ---------- >>>>>>>>>>  " + num);
						// ����
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
						// ��ͷ����
						mImgUpDown.setBackgroundResource(R.drawable.up_arrow);
						mTxtContent.setText("�����鿴ͼ������");

					} else if (mScrollview.getChildAt(0).getMeasuredHeight() <= v.getHeight() + v.getScrollY()) {
						// ����
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

		// ���ݼ�����֮��ſɵ��
		btnShopCarDictionary.setEnabled(false);
		btnCollect.setEnabled(false);
		btnShop.setEnabled(false);
		return view;
	}

	// �ƴ�
	int num = 0;

	List<Float> floatsY = new ArrayList<Float>();
	List<Float> floatsX = new ArrayList<Float>();
	// ���ر�ҳ������
	private static final int LOAD_DATA = 1;
	// �ж��Ƿ���û�й�ע
	private static final int FOLLOW_FOCUS = 2;
	// ��ӹ�ע
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
					// ��ȡ���ݳɹ������̼�id�жϵ�ǰ�̼���û�й�ע
					loadData(UrlNet.getFollowFocusCompany(getShopId() + ""), "", FOLLOW_FOCUS);
					break;

				case FOLLOW_FOCUS:
					RequestNetResultInfo requestNetResultInfo = JsonParseMall.getResultInfo((JSONArray) result);
					/*
					 * followState=0ʱ����ǰ�û�û�й�ע��ǰ�̼� followState=1ʱ����ǰ�û��Ѿ���ע��ǰ�̼�
					 * followState=2ʱ����ĳЩԭ���ܶԵ�ǰ�̼ҽ��й�ע��ԭ�����msg��
					 */
					int followState = JsonParseMall.getDataInt((JSONArray) result);
					if (followState == 0) {
						btnCollect.setEnabled(true);
					} else if (followState == 1) {
						tvCollect.setText("�ѹ�ע");
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
						tvCollect.setText("�ѹ�ע");
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
	// ��ʾ��ƷͼƬ
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

		// ���ÿ�ʼ�ֲ�
		infiniteIndicatorLayout.start();
		// ����ָʾ������ʾλ��
		infiniteIndicatorLayout.setIndicatorPosition(InfiniteIndicatorLayout.IndicatorPosition.Center_Bottom);
		// �����Զ���ʼ�ֲ���Ĭ������
		infiniteIndicatorLayout.startAutoScroll();
		// �����ֲ���ʱ��Ϊ5 ��
		infiniteIndicatorLayout.setInterval(5 * 1000);
	}

	private StringBuilder sb = null;

	// ��ʾ��Ʒ��Ϣ
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
		proPice.setText("�� " + entity.getPp());
	}

	// ��ʾ��ѡ��Ʒ��Ϣ
	private void disPlaySelected(PbiEntity entity) {

		// ���ݼ�����֮��ſɵ��
		btnShopCarDictionary.setEnabled(true);
		btnShop.setEnabled(true);

		mTvSelect.setText(entity.getPn());
	}

	private CbiEntity cbiEntity;

	// ��ʾ�̼���Ϣ
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
				// �������
				IntentClassChangeUtils.startShopDetailActivity(getContext(), getShopId());

				getActivity().finish();
			} else if (clickId == R.id.btn_collect) {
				// ��ע
				loadData(UrlNet.addFollowCompany(), MallManager.getAddCompanyData(cbiEntity.getId()), ADD_FOLLOW);
			} else if (clickId == R.id.txt_pic_file_detail) {
				// ͼ������
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
	 * ȫ����Ʒ����
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
		return "����";
	}

	/**
	 * ���ͼ������
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
	 * �����鿴ͼ������
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
