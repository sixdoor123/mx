package com.baiyi.cmall.popupwindow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.cmall.popupwindow.ShowRBPopupWindow.OnItemCheckedClickListener;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 *	
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-18 
 *       ����2:27:54
 */
public class FirstViewPager extends LinearLayout implements OnClickListener{

	//������
	private Context context;
	
	//����Դ
	private String datas;
	
	public FirstViewPager(Context context) {
		this(context,null);
		
	}
	

	public FirstViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		this.setBackgroundColor(context.getResources()
				.getColor(R.color.bg_white));
	
		initView();
		setOnClickListener(this);
	}


	//��ʾ���ֿؼ�
	private TextView mTxtView;
	
	private void initView() {
		View view = ContextUtil.getLayoutInflater(context)
				.inflate(R.layout.first_view, null);
		addView(view);
		mTxtView = (TextView) view.findViewById(R.id.text);
	}

	/**
	 * ��ʾ����
	 * @param string
	 */
	public void setData(String string) {
		mTxtView.setText(string);
		this.datas = string;
	}

	/**
	 * ����¼�
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		if (null != listener) {
			listener.onItemChecked(datas);
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
		void onItemChecked(String itemWords);

//		void onItemNotCheked(String itemWord);
	}

}
