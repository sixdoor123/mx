package com.baiyi.cmall.popupwindow;

import java.util.ArrayList;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.adapter.IntentionSelectionAdapter;
import com.baiyi.cmall.entity.IntentionTypeInfo;
import com.baiyi.cmall.utils.MobileStateUtils;
import com.baiyi.cmall.views.itemview.MyRadioButton;
import com.baiyi.cmall.views.pulldownview.PullToRefreshListView;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase.Mode;
import com.baiyi.core.util.ContextUtil;

import com.baiyi.cmall.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

/**
 * RadioButton状态下的PopupWindow弹窗
 * 
 * 宽度为频幕的宽度
 * 
 */
@SuppressLint("ResourceAsColor")
public class ShowAllPopupWindow implements OnItemClickListener,
		PopupWindowInterface, OnDismissListener {
	// 弹出窗
	private PopupWindow popupWindow;
	// 带刷新的ListView
	private PullToRefreshListView listView;
	// 数据源
	private ArrayList<IntentionTypeInfo> styleItems;

	// 上下文
	private Context context;

	public ShowAllPopupWindow(ArrayList<IntentionTypeInfo> styleItems) {
		this.styleItems = styleItems;
	}

	// 适配器
	private IntentionSelectionAdapter adapter;

	/**
	 * 显示
	 * 
	 * @param typeWorde
	 * 
	 * @parmar gravity 显示的位置
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void showPop(String worde, int height, Context context, View v,
			int gravity) {
		this.context = context;

		adapter = new IntentionSelectionAdapter(context, styleItems, worde);

		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.list_have_divider, null);
		listView = (PullToRefreshListView) view
				.findViewById(R.id.lst_industry_trends);
		// 不能下拉或者上拉刷新
		listView.setMode(Mode.DISABLED);
		// 创建一个PopupWindow
		// 参数1：contentView 指定PopupWindow的内容
		// 参数2：width 指定PopupWindow的width
		// 参数3：height 指定PopupWindow的height

		popupWindow = new PopupWindow(view, Config.getInstance()
				.getScreenWidth(context),
				MobileStateUtils.getTotalHeightofListView(adapter, listView),
				true);
		if (!popupWindow.isShowing()) {
			popupWindow.setFocusable(true);
			popupWindow.setOutsideTouchable(true);
			popupWindow.setBackgroundDrawable(new BitmapDrawable());
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(this);
			popupWindow.showAtLocation(v, gravity, 0, height + 2);

		}

		popupWindow.setOnDismissListener(this);
	}

	// 选中的条目上的文字
	private IntentionTypeInfo info = null;
	private int position;

	/**
	 * ListView的点击事件
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		info = (IntentionTypeInfo) parent.getItemAtPosition(position);
		this.position = position;
		if (null != listener) {
			listener.onItemChecked(info, position);
		}

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
		if (null != popupWindow && popupWindow.isShowing()) {
			popupWindow.dismiss();
		}
	}

	/**
	 * 当窗口焦点改变时
	 */
	public void onWindowFocusChange(Context context, boolean hasFocus,
			XRecyclerView listView, View view) {
		if (hasFocus) {
			// activity 获得焦点
			// 窗口背景变亮
			if (listView != null) {
				listView.setAlpha(1f);
				if (view != null) {
					view.setBackgroundColor(context.getResources().getColor(
							R.color.bg_white));
				}
			}
		} else {
			// activity 失去焦点
			// 窗口背景变暗
			listView.setAlpha(0.3f);
			view.setBackgroundColor(context.getResources().getColor(
					R.color.bg_hui1));
		}
	}

	private OnItemCheckedClickListener listener;

	public void setListener(OnItemCheckedClickListener listener) {
		this.listener = listener;
	}

	/**
	 * 监听条目被选择时回调
	 * 
	 */
	public interface OnItemCheckedClickListener {
		// 条目点击时调用
		void onItemChecked(IntentionTypeInfo info, int postion);

		// 条目被取消时候调用
		void onItemNotCheked(IntentionTypeInfo info, int postion);
	}

	/**
	 * 放pop消失的时候自动调用
	 */
	@Override
	public void onDismiss() {

		listener.onItemNotCheked(info, position);
	}

	@Override
	public void showPop(int height, View v, int gravity) {
		// TODO Auto-generated method stub

	}

}
