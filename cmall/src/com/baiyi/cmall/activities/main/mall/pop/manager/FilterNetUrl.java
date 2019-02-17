package com.baiyi.cmall.activities.main.mall.pop.manager;

import com.baiyi.cmall.Config;

/**
 *
 * @author sunxy
 */
public class FilterNetUrl {

	/**
	 * Ò»¼¶É¸Ñ¡
	 * 
	 * @return
	 */
	public static String getFilfterFirstUrl() {
		String url = Config.ROOT_URL+"Index/Mall/Fif";
		return url;
	}

	public static String getFilfterSeconedUrl(String qp) {
		String url = Config.ROOT_URL+"Index/Mall/Fif/%s";
		url = String.format(url, qp);
		return url;
	}

}
