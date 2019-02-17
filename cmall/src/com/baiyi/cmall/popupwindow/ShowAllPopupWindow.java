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
 * RadioButton״̬�µ�PopupWindow����
 * 
 * ���ΪƵĻ�Ŀ��
 * 
 */
@SuppressLint("ResourceAsColor")
public class ShowAllPopupWindow implements OnItemClickListener,
		PopupWindowInterface, OnDismissListener {
	// ������
	private PopupWindow popupWindow;
	// ��ˢ�µ�ListView
	private PullToRefreshListView listView;
	// ����Դ
	private ArrayList<IntentionTypeInfo> styleItems;

	// ������
	private Context context;

	public ShowAllPopupWindow(ArrayList<IntentionTypeInfo> styleItems) {
		this.styleItems = styleItems;
	}

	// ������
	private IntentionSelectionAdapter adapter;

	/**
	 * ��ʾ
	 * 
	 * @param typeWorde
	 * 
	 * @parmar gravity ��ʾ��λ��
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
		// ����������������ˢ��
		listView.setMode(Mode.DISABLED);
		// ����һ��PopupWindow
		// ����1��contentView ָ��PopupWindow������
		// ����2��width ָ��PopupWindow��width
		// ����3��height ָ��PopupWindow��height

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

	// ѡ�е���Ŀ�ϵ�����
	private IntentionTypeInfo info = null;
	private int position;

	/**
	 * ListView�ĵ���¼�
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
		if (null != popupWindow && popupWindow.isShowing()) {
			popupWindow.dismiss();
		}
	}

	/**
	 * �����ڽ���ı�ʱ
	 */
	public void onWindowFocusChange(Context context, boolean hasFocus,
			XRecyclerView listView, View view) {
		if (hasFocus) {
			// activity ��ý���
			// ���ڱ�������
			if (listView != null) {
				listView.setAlpha(1f);
				if (view != null) {
					view.setBackgroundColor(context.getResources().getColor(
							R.color.bg_white));
				}
			}
		} else {
			// activity ʧȥ����
			// ���ڱ����䰵
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
	 * ������Ŀ��ѡ��ʱ�ص�
	 * 
	 */
	public interface OnItemCheckedClickListener {
		// ��Ŀ���ʱ����
		void onItemChecked(IntentionTypeInfo info, int postion);

		// ��Ŀ��ȡ��ʱ�����
		void onItemNotCheked(IntentionTypeInfo info, int postion);
	}

	/**
	 * ��pop��ʧ��ʱ���Զ�����
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
