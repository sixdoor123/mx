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
 * RadioButton״̬�µ�PopupWindow����
 * 
 * @author sunxy
 * 
 */
@SuppressLint("ResourceAsColor")
public class ShowRBPopupWindow extends PopupWindow implements
		OnItemClickListener, PopupWindowInterface, OnDismissListener {
	// ��ˢ�µ�ListView
	private PullToRefreshListView listView;
	// ����Դ
	private ArrayList<SelectedInfo> strings;

	// ״̬ 0��ʾһ���˵�����
	// 1 ��ʾ�����˵�����
	private int state;

	// ������
	private Context context;

	public ShowRBPopupWindow(ArrayList<SelectedInfo> strings, int state) {
		this.strings = strings;
		this.state = state;
	}

	// ��ʾ���֣������б��Ŀؼ�
	private TextView mTxtItemSel;
	// ������
	private PopoupWindowAdapter adapter;
	private String fitstWords;

	/**
	 * ��ʾ
	 * 
	 * @param fitstWords
	 * 
	 * @parmar gravity ��ʾ��λ��
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

		// ����������������ˢ��
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

		// ����������������ˢ��
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
	 * ��ʾ
	 * 
	 * @param fitstWords
	 * 
	 * @parmar gravity ��ʾ��λ��
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

		// ����������������ˢ��
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

	// ѡ�е���Ŀ�ϵ�����
	private SelectedInfo info = null;
	// ����
	private View mLastView;
	// ��¼ǰһ����ʾ�ԺŵĿؼ�
	private ImageView mImg;
	private TextView mTxtTextView;

	/**
	 * ListView�ĵ���¼�
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
	 * ��û��ѡ���б���Ŀ��ȡ���������б�
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
	 * ȡ��
	 */
	@Override
	public void dismissPop() {
		if (isShowing()) {
			dismiss();
		}
	}

	/**
	 * �����ڽ���ı�ʱ
	 */
	public void onWindowFocusChange(Context context, boolean hasFocus,
			PullToRefreshListView listView, View view) {
		if (hasFocus) {
			// activity ��ý���
			// ���ڱ�������
			setFocusable(false);
			if (listView != null) {
				listView.setAlpha(1f);
				view.setBackgroundColor(context.getResources().getColor(
						R.color.bg_white));
			}
		} else {
			// activity ʧȥ����
			// ���ڱ����䰵
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
	 * ������Ŀ��ѡ��ʱ�ص�
	 * 
	 * @author sunxy
	 * 
	 *         date 2015-11-19
	 */
	public interface OnItemCheckedClickListener {
		void onItemChecked(SelectedInfo info);

		void onItemNotCheked(SelectedInfo info, boolean flag);
	}

	// ��pop��ʧʱ����
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
