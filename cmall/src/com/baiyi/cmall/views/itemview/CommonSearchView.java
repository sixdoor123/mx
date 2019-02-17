package com.baiyi.cmall.views.itemview;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.buy.ShoppingCarActivity;
import com.baiyi.cmall.activities.main.header_top.PublishPurchaseActivity;
import com.baiyi.cmall.activities.main.header_top.PublishSupplyActivity;
import com.baiyi.cmall.activities.main.header_top.SearchMainActivity;
import com.baiyi.cmall.activities.msg.MyMsgListActivity;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * ������������title���õ�ʱ��ֻ��new�����ӵ�activity�м���
 * 
 * @author lizl
 * 
 */
public class CommonSearchView extends LinearLayout {

	private Context context;
	// ����
	private ImageView mImgSearch;
	// ��ͼ��
	private ImageView mImgApp;
	// ����
	private TextView mTvSearch;
	// ������ؼ�
	private View view;
	// ����
	private ImageView mImgMore;

	public CommonSearchView(Context context) {
		super(context);
		this.context = context;
		setOrientation(VERTICAL);
		initView(context);
	}

	private void initView(final Context context) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			view = inflate(context, R.layout.item_main_title_h, null);
		} else {
			view = inflate(context, R.layout.item_main_title_l, null);
		}
		addView(view);

		mTvSearch = (TextView) view.findViewById(R.id.tv_search);
		mImgSearch = (ImageView) view.findViewById(R.id.img_search);
		mImgApp = (ImageView) view.findViewById(R.id.img_app);

		mTvSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(context, SearchMainActivity.class);
				context.startActivity(intent);
			}
		});
		mImgSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(context, SearchMainActivity.class);
				context.startActivity(intent);
			}
		});
		mImgMore = (ImageView) findViewById(R.id.btn_more);
		mImgMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				showMorePop();
			}
		});

	}

	private PopupWindow popupWindow;

	/**
	 * ��ʾpop�Ի���
	 */
	public  void showMorePop() {
		View popView = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.pop_main_more, null);
		// pop��ʾ�Ĵ�С
		popupWindow = new PopupWindow(popView, ((BaseActivity) context).getScreenWidth() / 2,
				LayoutParams.WRAP_CONTENT);
		Log.d("TAG", "�����" + popupWindow.isShowing());
		if (!popupWindow.isShowing()) {
			popupWindow.setFocusable(true);
			popupWindow.setOutsideTouchable(true);
			popupWindow.setBackgroundDrawable(new BitmapDrawable());
			// pop��ʾ��λ��

			float density = context.getResources().getDisplayMetrics().density;
			int y = (int) (density * 15);
			popupWindow.showAsDropDown(mImgMore, ((BaseActivity) context).getScreenWidth() / 2, (int) -(y - 10));

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

				Intent intent = new Intent(context, PublishPurchaseActivity.class);
				context.startActivity(intent);
				popupWindow.dismiss();
			}
		});
		/**
		 * ��Ӧ
		 */
		supplyMaterialView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, PublishSupplyActivity.class);
				context.startActivity(intent);
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
				Intent intent = new Intent(context, ShoppingCarActivity.class);
				context.startActivity(intent);
				popupWindow.dismiss();
			}
		});

	}

	public void showBack() {
		Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_back);
		mImgApp.setImageBitmap(bm);
		mImgApp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((BaseActivity) context).finish();
			}
		});
	}

	/**
	 * ��ʾ������ť
	 */
	public void showSearchView() {
		mImgSearch.setVisibility(View.VISIBLE);
		mImgApp.setVisibility(View.GONE);
	}

	/**
	 * ��ʾAPPͼ��
	 */
	public void showAppView() {
		mImgApp.setVisibility(View.VISIBLE);
		mImgSearch.setVisibility(View.GONE);
	}
	
	public void setMTvSearch(String text){
		mTvSearch.setText(text);
	}

}
