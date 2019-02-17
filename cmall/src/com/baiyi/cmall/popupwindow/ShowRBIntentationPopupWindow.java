package com.baiyi.cmall.popupwindow;

import java.util.ArrayList;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.adapter.IntentionSelectionAdapter;
import com.baiyi.cmall.adapter.PopoupWindowAdapter;
import com.baiyi.cmall.entity.IntentionTypeInfo;
import com.baiyi.cmall.utils.MobileStateUtils;
import com.baiyi.cmall.utils.PopoupWindowUtil;
import com.baiyi.cmall.views.itemview.MyRadioButton;
import com.baiyi.cmall.views.pulldownview.PullToRefreshListView;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase.Mode;
import com.baiyi.core.util.ContextUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.baiyi.cmall.R;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
public class ShowRBIntentationPopupWindow extends PopupWindow
		implements OnItemClickListener, PopupWindowInterface, OnDismissListener {
	// ������
	private PopupWindow popupWindow;
	// ��ˢ�µ�ListView
	private PullToRefreshListView listView;
	// ����Դ
	private ArrayList<IntentionTypeInfo> strings;

	// ������
	private Context context;
	private Animation animIn = null;
	private Animation animOut = null;

	public ShowRBIntentationPopupWindow(ArrayList<IntentionTypeInfo> strings) {
		this.strings = strings;

	}

	// ��ʾ���֣������б��Ŀؼ�
	private TextView mTxtItemSel;
	// ������
	private IntentionSelectionAdapter adapter;

	// ��һ�ε��������
	private String words;
	private View view = null;

	/**
	 * ��ʾ
	 * 
	 * @param words
	 * 
	 * @parmar gravity ��ʾ��λ��
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public void showPop(String words, int height, Context context, View v, int gravity) {
		this.context = context;

		animIn = AnimationUtils.loadAnimation(context, R.anim.fade_in_anim);
		animOut = AnimationUtils.loadAnimation(context, R.anim.fade_out_anim);

		this.words = words;
		adapter = new IntentionSelectionAdapter(context, strings, words);

		view = ContextUtil.getLayoutInflater(context).inflate(R.layout.list_view_pop, null);
		view.startAnimation(animIn);
		
		listView = (PullToRefreshListView) view.findViewById(R.id.lst_industry_trends);

		// ����������������ˢ��
		listView.setMode(Mode.DISABLED);

		setWidth(Config.getInstance().getScreenWidth(context));
		setHeight(MobileStateUtils.getTotalHeightofListView(adapter, listView));
		if (!isShowing()) {

			setFocusable(true);
			setOutsideTouchable(true);
			setBackgroundDrawable(new BitmapDrawable());
			listView.setAdapter(adapter);
			// listView.setScrollbarFadingEnabled(false);
			setContentView(view);

			listView.setOnItemClickListener(this);
			showAtLocation(v, gravity, 0, height + 2);
			setOnDismissListener(this);
		} else {

			view.startAnimation(animOut);
			dismiss();
			update();
		}

		getContentView().setOnTouchListener(new OnTouchListener() {
			// Java���� �ղش���
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				setFocusable(false);
				dismiss();
				return true;
			}

		});
	}

	// ѡ�е���Ŀ�ϵ�����
	private IntentionTypeInfo info = null;
	private int position;

	/**
	 * ListView�ĵ���¼�
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		ImageView imageView = (ImageView) view.findViewById(R.id.img_select);
		imageView.setVisibility(View.VISIBLE);
		view.startAnimation(animOut);
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
		view.startAnimation(animOut);
		dismiss();
	}

	/**
	 * �����ڽ���ı�ʱ
	 */
	public void onWindowFocusChange(boolean hasFocus, PullToRefreshListView listView, View view) {
		if (hasFocus) {
			// activity ��ý���
			// ���ڱ�������
			if (listView != null) {
				listView.setAlpha(1f);
				view.setBackgroundColor(context.getResources().getColor(R.color.bg_white));
				listView.setEnabled(true);
				view.setEnabled(true);
			}
		} else {
			// activity ʧȥ����
			// ���ڱ����䰵
			listView.setAlpha(0.2f);
			view.setBackgroundColor(context.getResources().getColor(R.color.bg_hui1));
			listView.setEnabled(false);
			view.setEnabled(false);
		}
	}

	public void onWindowFocusChange(boolean hasFocus, XRecyclerView recyclerView, View view) {
		if (hasFocus) {
			// activity ��ý���
			// ���ڱ�������
			if (recyclerView != null) {
				recyclerView.setAlpha(1f);
				view.setBackgroundColor(context.getResources().getColor(R.color.bg_white));
				recyclerView.setEnabled(true);
				view.setEnabled(true);
			}
		} else {
			// activity ʧȥ����
			// ���ڱ����䰵
			recyclerView.setAlpha(0.2f);
			view.setBackgroundColor(context.getResources().getColor(R.color.bg_hui1));
			recyclerView.setEnabled(false);
			view.setEnabled(false);
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
		void onItemChecked(IntentionTypeInfo info, int postion);

		void onItemNotCheked(IntentionTypeInfo info, int postion);
	}

	// ��pop��ʧʱ����
	@Override
	public void onDismiss() {
		view.startAnimation(animOut);
		if (null != listener) {
			listener.onItemNotCheked(info, position);
		}
	}

	@Override
	public void showPop(int height, View v, int gravity) {
		// TODO Auto-generated method stub

	}

}
