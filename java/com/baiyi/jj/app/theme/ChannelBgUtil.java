package com.baiyi.jj.app.theme;

import com.baiyi.jj.app.activity.ChannelUtils;
import com.turbo.turbo.mexico.R;

public class ChannelBgUtil extends ThemeUtil{

	public static int getTextcolor(String type) {
		if (type.equals(ChannelUtils.group_kown)) {
			return getAppTheme()==Theme_day ? R.color.day_text_color : R.color.nightd_day_text_color;
		}else if (type.equals(ChannelUtils.group_front)) {
			return getAppTheme()==Theme_day ? R.color.day_text_color_red : R.color.nightd_day_text_blue;
		}else if (type.equals(ChannelUtils.group_other)) {
			return getAppTheme()==Theme_day ? R.color.day_text_red : R.color.nightd_day_text_red;
		}else {
			return getAppTheme()==Theme_day ? R.color.day_text_color : R.color.nightd_day_text_color;
		}
		
	}

}
