package com.baiyi.cmall.activities.user.merchant.product;

import com.baiyi.cmall.Config;

/**
*
*@author sunxy
*/
public class ProductNetUrl {

	/**
	 * 撤销发布
	 * @param id
	 * @param state
	 * @return
	 */
	public static String getUndoPublishUrl(String id, int state) {
		String url = Config.ROOT_URL+"Company/Product/unpublish/%s";
		url = String.format(url, id);
		return url;
	}
	/**
	 * 发布发布产品
	 * @param id
	 * @param state
	 * @return
	 */
	public static String getPublishUrl(String id, int state) {
		String url = Config.ROOT_URL+"Company/Product/publish/%s";
		url = String.format(url, id);
		return url;
	}

}
