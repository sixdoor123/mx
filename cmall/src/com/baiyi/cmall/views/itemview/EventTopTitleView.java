package com.baiyi.cmall.views.itemview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.buy.ShoppingCarActivity;
import com.baiyi.cmall.activities.main.header_top.PublishPurchaseActivity;
import com.baiyi.cmall.activities.main.header_top.PublishSupplyActivity;
import com.baiyi.cmall.application.AppUtils;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.UserInfoEntity;
import com.baiyi.cmall.utils.IntentClassChangeUtils;
import com.baiyi.cmall.views.pop.PopView;
import com.baiyi.cmall.views.pop.PopView.PopItemOnclick;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 公共的头部标题，用事只需要new出来加到布局中即可 通过setEventName设置名字
 * 
 * @author lizl
 * 
 */
public class EventTopTitleView extends LinearLayout {

	private Context context;
	// 时间名字
	private TextView mTvEventName;
	// 返回
	public LinearLayout mImgBack;
	// 主标题控件
	private View view;
	// 消息数量
	private TextView msgCount;
	// 消息按钮
	private ImageView btnMsg = null;
	// 是否显示Pop
	private boolean isDisPop = false;
	// Pop弹窗
	private PopView pv = null;

	private TitleNewsOnclick titleNewsOnclick = null;

	private NewsPopItemOnclick newsPopItemOnclick = null;

	/**
	 * 
	 * @param context
	 * @param isDisPop
	 *            true：弹窗 false：进入消息界面
	 */
	public EventTopTitleView(Context context, boolean isDisPop) {
		super(context);
		this.context = context;
		this.isDisPop = isDisPop;
		setOrientation(VERTICAL);
		initView(context);
		initMsg();
	}

	private void initView(final Context context) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			view = inflate(context, R.layout.item_enent_top_title_h, null);
		} else {
			view = inflate(context, R.layout.item_enent_top_title_l, null);
		}
		addView(view);
		mTvEventName = (TextView) findViewById(R.id.tv_event_name);

		mImgBack = (LinearLayout) findViewById(R.id.img_back);
		mImgBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				((Activity) context).finish();
			}
		});
	}

	private FrameLayout newsLayout;

	public void setNewsLayoutVisible(int visible) {
		newsLayout.setVisibility(visible);
	}

	private void initMsg() {

		msgCount = (TextView) findViewById(R.id.news_count);
		newsLayout = (FrameLayout) findViewById(R.id.msg_layout);
		newsLayout.setVisibility(View.INVISIBLE);
		btnMsg = (ImageView) findViewById(R.id.btn_news);
		newsLayout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// onclickMsg();
				showMorePop();
			}
		});
		if (isDisPop) {
			btnMsg.setImageResource(R.drawable.selector_title_msg_more);
		} else {
			btnMsg.setImageResource(R.drawable.selector_title_msg);
		}
	}

	private PopupWindow popupWindow;

	/**
	 * 显示pop对话框
	 */
	private void showMorePop() {
		View popView = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.pop_main_more, null);
		// pop显示的大小
		popupWindow = new PopupWindow(popView,
				((BaseActivity) context).getScreenWidth() / 2,
				LayoutParams.WRAP_CONTENT);
		Log.d("TAG", "结果：" + popupWindow.isShowing());
		if (!popupWindow.isShowing()) {
			popupWindow.setFocusable(true);
			popupWindow.setOutsideTouchable(true);
			popupWindow.setBackgroundDrawable(new BitmapDrawable());
			// pop显示的位置

			float density = context.getResources().getDisplayMetrics().density;
			int y = (int) (density * 15);
			popupWindow.showAsDropDown(newsLayout,
					((BaseActivity) context).getScreenWidth() / 2, (int) -(y));

		} else {
			popupWindow.dismiss();

		}
		initPopView(popView);
	}

	/**
	 * 初始化更多頁面的pop布局
	 * 
	 * @param popView
	 */
	private void initPopView(View popView) {
		TextView purchaseView = (TextView) popView
				.findViewById(R.id.tv_purchase);
		TextView supplyMaterialView = (TextView) popView
				.findViewById(R.id.tv_supply_material);
		TextView delegationWuLiu = (TextView) popView
				.findViewById(R.id.tv_delegation);
		/**
		 * 采购
		 */
		purchaseView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(context,
						PublishPurchaseActivity.class);
				context.startActivity(intent);
				popupWindow.dismiss();
			}
		});
		/**
		 * 供应
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
		 * 物流
		 */
		delegationWuLiu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, ShoppingCarActivity.class);
				context.startActivity(intent);
				popupWindow.dismiss();
			}
		});
	}

	private void onclickMsg() {
		if (titleNewsOnclick != null) {
			titleNewsOnclick.setTitleNewsOnclickLister(isDisPop);
		}
		if (isDisPop) {
			return;
		}
		UserInfoEntity user = CmallApplication.getUserInfoEntity();
		if (user == null) {
			AppUtils.loginRemind(getContext(), getContext().getResources()
					.getString(R.string.tip_dialog_title), getContext()
					.getResources().getString(R.string.tip_dialog_unlongin));
			return;
		}
		IntentClassChangeUtils.startMsg(getContext());
	}

	public void setMsg() {
		UserInfoEntity user = CmallApplication.getUserInfoEntity();
		if (user != null) {
			((BaseActivity) getContext()).setNews(msgCount,
					(user.getMultiMaxID() + user.getUserUnReadCount()));
		} else {
			msgCount.setVisibility(View.GONE);
		}
	}

	/**
	 * 显示弹窗
	 * 
	 * @param names
	 *            名称数组
	 * @param resIds
	 *            icon id数组
	 */
	public void displayPoP(String[] names, int[] resIds) {
		pv = new PopView(getContext(), names, resIds);
		pv.showPop(btnMsg);
		pv.setPopItemOnclick(new PopItemOnclick() {

			@Override
			public void setOnclickLister(int state) {
				if (newsPopItemOnclick != null) {
					newsPopItemOnclick.setNewsPopItemOnclickLister(state);
				}
			}
		});

	}

	/**
	 * 设置标题名字
	 * 
	 * @param enentName
	 */
	public void setEventName(String enentName) {
		mTvEventName.setText(enentName);
	}

	public TitleNewsOnclick getTitleNewsOnclick() {
		return titleNewsOnclick;
	}

	public void setTitleNewsOnclick(TitleNewsOnclick titleNewsOnclick) {
		this.titleNewsOnclick = titleNewsOnclick;
	}

	public NewsPopItemOnclick getNewsPopItemOnclick() {
		return newsPopItemOnclick;
	}

	public void setNewsPopItemOnclick(NewsPopItemOnclick newsPopItemOnclick) {
		this.newsPopItemOnclick = newsPopItemOnclick;
	}

	public interface TitleNewsOnclick {
		public void setTitleNewsOnclickLister(boolean isPop);
	}

	public interface NewsPopItemOnclick {
		public void setNewsPopItemOnclickLister(int state);
	}

}
