package com.baiyi.cmall.popupwindow;

import java.util.ArrayList;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.adapter.PopoupWindowAdapter;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.utils.MobileStateUtils;
import com.baiyi.cmall.utils.PopoupWindowUtil;
import com.baiyi.cmall.views.itemview.MyRadioButton;
import com.baiyi.cmall.views.pulldownview.PullToRefreshListView;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase.Mode;
import com.baiyi.core.util.ContextUtil;

import com.baiyi.cmall.R;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.ClipData.Item;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

/**
 * RadioButton状态下的PopupWindow弹窗
 * 
 * @author sunxy
 * 
 */
@SuppressLint("ResourceAsColor")
public class ShowRBPopupWindow extends PopupWindow implements
		OnItemClickListener, PopupWindowInterface, OnDismissListener {
	// 带刷新的ListView
	private PullToRefreshListView listView;
	// 数据源
	private ArrayList<SelectedInfo> strings;

	// 状态 0表示一级菜单调用
	// 1 表示二级菜单调用
	private int state;

	// 上下文
	private Context context;

	public ShowRBPopupWindow(ArrayList<SelectedInfo> strings, int state) {
		this.strings = strings;
		this.state = state;
	}

	// 显示文字（下拉列表）的控件
	private TextView mTxtItemSel;
	// 适配器
	private PopoupWindowAdapter adapter;
	private String fitstWords;

	/**
	 * 显示
	 * 
	 * @param fitstWords
	 * 
	 * @parmar gravity 显示的位置
	 */
	@SuppressWarnings("deprecation")
	public void showPop(String fitstWords, int height, Context context, View v,
			int gravity) {
		this.context = context;
		this.fitstWords = fitstWords;

		adapter = new PopoupWindowAdapter(context, strings, state, fitstWords);

		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.list_view_pop, null);
		listView = (PullToRefreshListView) view
				.findViewById(R.id.lst_industry_trends);

		// 不能下拉或者上拉刷新
		listView.setMode(Mode.DISABLED);

		int h = MobileStateUtils.getTotalHeightofListView(adapter, listView) > (Config
				.getInstance().getScreenHeight(context) - height) ? (Config
				.getInstance().getScreenHeight(context) - height)
				: MobileStateUtils.getTotalHeightofListView(adapter, listView);

		setWidth(Config.getInstance().getScreenWidth(context) / 2 - 1);
		setHeight(h);
		if (!isShowing()) {
			setFocusable(true);
			setOutsideTouchable(true);
			setBackgroundDrawable(new BitmapDrawable());
			listView.setAdapter(adapter);
			// listView.setScrollbarFadingEnabled(false);
			listView.setOnItemClickListener(this);
			setContentView(view);
			showAtLocation(v, gravity, 0, height + 2);

			this.setOnDismissListener(this);
		}
		getContentView().setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				setFocusable(false);
				// dismiss();
				return true;
			}
		});
	}

	private boolean flag = true;

	public void showPop2(String secondWords, int height, final Context context,
			View v, int gravity) {
		this.context = context;

		adapter = new PopoupWindowAdapter(context, strings, state, secondWords);

		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.list_view_pop, null);
		listView = (PullToRefreshListView) view
				.findViewById(R.id.lst_industry_trends);

		// 不能下拉或者上拉刷新
		listView.setMode(Mode.DISABLED);

		int h = MobileStateUtils.getTotalHeightofListView(adapter, listView) > (Config
				.getInstance().getScreenHeight(context) - height) ? (Config
				.getInstance().getScreenHeight(context) - height)
				: MobileStateUtils.getTotalHeightofListView(adapter, listView);

		setWidth(Config.getInstance().getScreenWidth(context) / 2 - 1);
		setHeight(h);
		if (!isShowing()) {
			setFocusable(true);
			setOutsideTouchable(true);
			setBackgroundDrawable(new BitmapDrawable());
			listView.setAdapter(adapter);
			setContentView(view);

			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {

					// for (int i = 0; i <
					// PopoupWindowUtil.getImageViews().size(); i++) {
					// PopoupWindowUtil.getImageViews().get(i)
					// .setVisibility(View.GONE);
					// PopoupWindowUtil
					// .getTextViews()
					// .get(i)
					// .setTextColor(
					// context.getResources().getColor(
					// R.color.bg_black));
					// }
					TextView mTextView = (TextView) view
							.findViewById(R.id.txt_item_sel);
					ImageView mImageView = (ImageView) view
							.findViewById(R.id.img_selected);
					mTextView.setTextColor(context.getResources().getColor(
							R.color.bg_buyer));
					mImageView.setImageResource(R.drawable.selected_red);
					mImageView.setVisibility(View.VISIBLE);

					view.setBackgroundColor(context.getResources().getColor(
							R.color.bg_hui3));

					info = (SelectedInfo) parent.getItemAtPosition(position);

					if (null != listener) {
						listener.onItemChecked(info);
					}
				}
			});
			showAtLocation(v, gravity, 0, height + 2);

			setOnDismissListener(this);

		}
	}

	/**
	 * 显示
	 * 
	 * @param fitstWords
	 * 
	 * @parmar gravity 显示的位置
	 */
	@SuppressLint("InflateParams")
	@SuppressWarnings("deprecation")
	public void showPop1(String fitstWords, int height, Context context,
			View v, int gravity) {
		this.context = context;

		adapter = new PopoupWindowAdapter(context, strings, state, fitstWords);

		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.list_view_pop, null);
		listView = (PullToRefreshListView) view
				.findViewById(R.id.lst_industry_trends);

		// 不能下拉或者上拉刷新
		listView.setMode(Mode.DISABLED);
		setWidth(Config.getInstance().getScreenWidth(context));
		setHeight(MobileStateUtils.getTotalHeightofListView(adapter, listView));
		if (!isShowing()) {
			setFocusable(true);
			setOutsideTouchable(true);
			setBackgroundDrawable(new BitmapDrawable());
			listView.setAdapter(adapter);
			setContentView(view);
			// listView.setScrollbarFadingEnabled(false);
			listView.setOnItemClickListener(this);
			showAtLocation(v, gravity, 0, height + 2);

			setOnDismissListener(this);
		}
		getContentView().setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				setFocusable(false);
				return true;
			}
		});
	}

	// 选中的条目上的文字
	private SelectedInfo info = null;
	// 背景
	private View mLastView;
	// 记录前一次显示对号的控件
	private ImageView mImg;
	private TextView mTxtTextView;

	/**
	 * ListView的点击事件
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		info = (SelectedInfo) parent.getItemAtPosition(position);
		if (null != mImg) {
			mImg.setImageResource(R.drawable.no_selected);
			mImg.setVisibility(View.GONE);
			mTxtTextView.setTextColor(context.getResources().getColor(
					R.color.bg_black));
			mLastView.setBackgroundColor(context.getResources().getColor(
					R.color.bg_white));
		}

		// for (int i = 0; i < PopoupWindowUtil.getImageViews().size(); i++) {
		// PopoupWindowUtil.getImageViews().get(i).setVisibility(View.GONE);
		// PopoupWindowUtil
		// .getTextViews()
		// .get(i)
		// .setTextColor(
		// context.getResources().getColor(R.color.bg_black));
		// PopoupWindowUtil
		// .getViews()
		// .get(i)
		// .setBackgroundColor(
		// context.getResources().getColor(R.color.bg_white));
		// }

		TextView mTextView = (TextView) view.findViewById(R.id.txt_item_sel);
		ImageView mImageView = (ImageView) view.findViewById(R.id.img_selected);
		mTextView.setTextColor(context.getResources().getColor(R.color.bg_buyer));
		mImageView.setImageResource(R.drawable.selected_red);
		mImageView.setVisibility(View.VISIBLE);

		view.setBackgroundColor(context.getResources()
				.getColor(R.color.bg_hui3));

		if (null != listener) {
			listener.onItemChecked(info);
		}
		// mLastImg = imageView;
		mImg = mImageView;
		mTxtTextView = mTextView;
		mLastView = view;
	}

	/**
	 * 当没有选择列表项目，取消了下拉列表
	 * 
	 * @param button
	 * @param word
	 */
	public void onNotCelected(MyRadioButton button, String word) {
		if (button.getmTxtName().equals(word)) {
			button.setmTxtNameColor(R.color.bg_hui1);
			button.setmImgChoice(R.drawable.choice_down);
		} else {
			button.setmImgChoice(R.drawable.red_choice_down);
		}
	}

	/**
	 * 取消
	 */
	@Override
	public void dismissPop() {
		if (isShowing()) {
			dismiss();
		}
	}

	/**
	 * 当窗口焦点改变时
	 */
	public void onWindowFocusChange(Context context, boolean hasFocus,
			PullToRefreshListView listView, View view) {
		if (hasFocus) {
			// activity 获得焦点
			// 窗口背景变亮
			setFocusable(false);
			if (listView != null) {
				listView.setAlpha(1f);
				view.setBackgroundColor(context.getResources().getColor(
						R.color.bg_white));
			}
		} else {
			// activity 失去焦点
			// 窗口背景变暗
			listView.setAlpha(0.3f);
			view.setBackgroundColor(context.getResources().getColor(
					R.color.bg_hui1));
			setFocusable(true);
		}
	}

	private OnItemCheckedClickListener listener;

	public void setListener(OnItemCheckedClickListener listener) {
		this.listener = listener;
	}

	/**
	 * 监听条目被选择时回调
	 * 
	 * @author sunxy
	 * 
	 *         date 2015-11-19
	 */
	public interface OnItemCheckedClickListener {
		void onItemChecked(SelectedInfo info);

		void onItemNotCheked(SelectedInfo info, boolean flag);
	}

	// 当pop消失时调用
	@Override
	public void onDismiss() {

		setFocusable(false);
		if (null != listener) {
			listener.onItemNotCheked(info, flag);
		}

	}

	@Override
	public void showPop(int height, View v, int gravity) {

	}

}
