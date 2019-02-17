/**
 * 
 */
package com.baiyi.jj.app.views.pop;

import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.theme.ThemeUtil;
import com.baiyi.jj.app.views.PopNewsView.PopViewTheme;

/**
 * @author tangkun
 *
 */
public class PopViewThemeUtils {
	
	public static final PopViewTheme popMoreTheme = new PopViewTheme() {
		
		@Override
		public int getPopViewBg() {
			// TODO Auto-generated method stub
			int resId = ThemeUtil.getAppTheme() == ThemeUtil.Theme_day ? R.drawable.pop_entertainment_bg : R.drawable.pop_entertainment_bg;
			return resId;
		}

		@Override
		public int getPopItemView() {
			// TODO Auto-generated method stub
			return R.layout.pop_more_item;
		}
	};
	
	public static final PopViewTheme popNewsTheme = new PopViewTheme() {
		
		@Override
		public int getPopViewBg() {
			// TODO Auto-generated method stub
			int resId = ThemeUtil.getAppTheme() == ThemeUtil.Theme_day ? R.drawable.bg_pop : R.drawable.bg_pop;
			return resId;
		}

		@Override
		public int getPopItemView() {
			// TODO Auto-generated method stub
			return R.layout.pop_news_item;
		}
	};

}
