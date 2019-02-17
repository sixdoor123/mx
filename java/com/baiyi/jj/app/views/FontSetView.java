/**
 *
 */
package com.baiyi.jj.app.views;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.baiyi.core.util.ContextUtil;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.activity.BaseActivity;

/**
 * @author tangkun
 *
 */
public class FontSetView {

	private String[] itemText = null;
	private Context context = null;
	private RadioButton[] items = null;

	private RadioGroup group = null;
	private int focusId;

	private int itemSize = 0;
	private boolean isNewsWeb = false;
	/**
	 * @param context
	 */
	public FontSetView(Context context,RadioGroup group, String[] itemText, int focusId) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.itemText = itemText;
		this.group = group;
		this.focusId = focusId;
		itemSize = itemText.length;
		if(itemSize == 0)
		{
//			((BaseActivity)context).displayToast("��������Ϊ0");
			return;
		}
		if(itemSize != itemText.length)
		{
//			((BaseActivity)context).displayToast("��Ŀ���������ֳ��Ȳ�һ��");
			return;
		}
		items = new RadioButton[itemSize];

		initView();
	}
	public FontSetView(Context context,RadioGroup group, String[] itemText, int focusId,boolean isNewsWeb) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.itemText = itemText;
		this.group = group;
		this.focusId = focusId;
		this.isNewsWeb = isNewsWeb;
		itemSize = itemText.length;
		if(itemSize == 0)
		{
//			((BaseActivity)context).displayToast("��������Ϊ0");
			return;
		}
		if(itemSize != itemText.length)
		{
//			((BaseActivity)context).displayToast("��Ŀ���������ֳ��Ȳ�һ��");
			return;
		}
		items = new RadioButton[itemSize];

		initView();
	}

	private void initView()
	{
		for(int i = 0; i < itemSize; i++)
		{
			intItem(i);
		}
	}

	private void intItem(final int position)
	{
		View itemView = ContextUtil.getLayoutInflater(context).inflate(R.layout.item_font_set_middle, null);
		items[position] = (RadioButton)itemView.findViewById(R.id.set_font_button);
		items[position].setId(getid(position));

		if (isNewsWeb){
			items[position].setBackgroundResource(R.drawable.btn_set_font_center2);
			items[position].setTextColor(context.getResources().getColorStateList(R.color.word_red_white_checked2));

		}
		if(position == focusId)
		{
			items[focusId].setChecked(true);
		}
		items[position].setText(itemText[position]);
		if (isNewsWeb){
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(BaseActivity.getDensity_int(80),
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			group.addView(items[position],lp);
		}else {
			group.addView(items[position]);
		}
	}


	private int getid(int id){
		if (id == 0){
			return R.id.id_rb_0;
		}else if (id == 1){
			return R.id.id_rb_1;
		}else if (id == 2){
			return R.id.id_rb_2;
		}else if (id == 3){
			return R.id.id_rb_3;
		}else if (id == 4){
			return R.id.id_rb_4;
		}else {
			return -1;
		}

	}

	public void selectItem(int position)
	{
		items[position].performClick();
	}

}
