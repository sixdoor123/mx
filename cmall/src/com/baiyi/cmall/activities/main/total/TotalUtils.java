package com.baiyi.cmall.activities.main.total;

/**
 * ��¼����
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

	// ��¼����
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
