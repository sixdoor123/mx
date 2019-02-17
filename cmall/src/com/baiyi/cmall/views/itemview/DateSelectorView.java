package com.baiyi.cmall.views.itemview;

import com.baiyi.cmall.dialog.DialogBase;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.view.View.OnClickListener;

/**
 * �Զ��������ѡ����
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-25 ����2:10:01
 */
public class DateSelectorView extends DialogBase implements OnClickListener {

	// ���ڱ���
	private String dateTitle;
	// ������
	private Context context;
	// ��ǰ��ʱ��
	long time = System.currentTimeMillis();

	public DateSelectorView(Context context, String dateTitle) {
		super(context, DialogBase.Win_Center);
		this.context = context;
		this.dateTitle = dateTitle;

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_ok:
			int year = mCalendar.getYear();
			int month = mCalendar.getMonth() + 1;
			int day = mCalendar.getDayOfMonth();
			if (null != listener) {
				listener.onDateSelected(Utils.getDateYYYYMMDD(year, month, day));
			}
			dismiss();
			break;
		case R.id.btn_canel:
			dismiss();
			break;
		}
	}

	// ����
	private TextView mTxtDateTitle;
	// ����
	private DatePicker mCalendar;
	// ȷ����ť
	private Button mBtnOk;
	// ȡ����ť
	private Button mBtnCancel;

	/**
	 * ��ʼʱ��ѡ��Ľ���
	 * 
	 * */
	@Override
	public void setContainer() {
		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.item_date_select_dialog, null);
		addView(view);

		mTxtDateTitle = (TextView) view.findViewById(R.id.txt_date_title);
		mTxtDateTitle.setText(dateTitle);

		mCalendar = (DatePicker) view.findViewById(R.id.calendar);

		mBtnOk = (Button) view.findViewById(R.id.btn_ok);
		mBtnCancel = (Button) view.findViewById(R.id.btn_canel);

		mBtnOk.setOnClickListener(this);
		mBtnCancel.setOnClickListener(this);

		// mCalendar.setCalendarViewShown(false);
	}

	private OnDateSelectedClickListener listener;

	public void setListener(OnDateSelectedClickListener listener) {
		this.listener = listener;
	}

	/**
	 * �ڲ��ص��ӿ�
	 */
	public interface OnDateSelectedClickListener {
		void onDateSelected(String date);
	}

	@Override
	public void setTitleContent() {

	}

	@Override
	public void OnClickListenEvent(View v) {

	}

}
