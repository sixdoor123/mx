package com.baiyi.cmall.activities.main.mall.page;

import com.baiyi.cmall.Config;

/**
 *
 * @author sunxy
 */
public class ProductDetailNet {

	/**
	 * ∆¿¬€
	 * 
	 * @param id
	 * @param commentType
	 * @param pegeIndex
	 * @param listItemCount
	 * @return
	 */
	public static String getCommentPageUrl(String id, int commentType, int pegeIndex, int listItemCount) {
		String url = Config.ROOT_URL + "Index/Product/%s/%d/%d/%d";
		url = String.format(url, id, commentType, pegeIndex, listItemCount);
		return url;
	}

	/**
	 * …Ã∆∑
	 * @param id
	 * @return
	 */
	public static String getProductlInfoPageUrl(String id) {
		String url = Config.ROOT_URL + "Index/Product/%s";
		url = String.format(url, id);
		return url;
	}

}
