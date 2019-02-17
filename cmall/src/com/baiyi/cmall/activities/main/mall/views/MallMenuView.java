/**
 * 
 */
package com.baiyi.cmall.activities.main.mall.views;

import java.util.ArrayList;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.mall.entity.MallMainMenuEntity;
import com.baiyi.core.util.ContextUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * 商城-分类-tab
 * @author tangkun
 *
 */
public class MallMenuView extends RadioGroup{
	
	private RadioButton[] rbs = null;
	private MenuOnclickLister lister = null;

	public MallMenuView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	
	public MallMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public void displayViews(int selectIndex, final ArrayList<MallMainMenuEntity> menuTexts, MenuOnclickLister menuOnclickLister)
	{
		this.lister = menuOnclickLister;
		if(menuTexts == null || menuTexts.size() == 0)
		{
			return;
		}
		rbs = new RadioButton[menuTexts.size()];
		for(int i = 0; i < rbs.length; i++)
		{
			rbs[i] = (RadioButton)ContextUtil.getLayoutInflater(getContext()).inflate(R.layout.view_radiobutton, null);
			rbs[i].setId(i);
			rbs[selectIndex].setChecked(true);
			rbs[i].setText(menuTexts.get(i).getCn());
			LayoutParams lp = new LayoutParams(-2, -1);
//			lp.setMargins(BaseActivity.getDensity_int(10), 0, BaseActivity.getDensity_int(10), 0);
			lp.weight = 1;
			this.addView(rbs[i], lp);
		}
		this.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				rbs[checkedId].performClick();
				if(lister != null)
				{
					lister.setMenuOnclickLister(checkedId, menuTexts.get(checkedId));
				}
			}
		});
	}

	public void setLister(MenuOnclickLister lister) {
		this.lister = lister;
	}

	public interface MenuOnclickLister
	{
		public void setMenuOnclickLister(int position, MallMainMenuEntity entity);
	}
}
