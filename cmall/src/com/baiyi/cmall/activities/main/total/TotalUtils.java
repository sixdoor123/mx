package com.baiyi.cmall.activities.main.total;

/**
 * 记录总数
 * 
 * @author sunxy
 */
public class TotalUtils {

	private static TotalUtils utils = null;

	private TotalUtils() {
	}

	public static TotalUtils getIntence() {
		if (utils == null) {
			utils = new TotalUtils();
		}
		return utils;
	}

	// 记录总数
	private int total;

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}
}
